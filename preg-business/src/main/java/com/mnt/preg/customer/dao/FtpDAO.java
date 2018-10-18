
package com.mnt.preg.customer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.utils.times.JodaTimeTools;
import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

/**
 * Ftp服务器DAO
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-22 zcq 初版
 */
@Repository
public class FtpDAO extends HibernateTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(FtpDAO.class);

    /**
     * 检索同步数据
     * 
     * @author zcq
     * @param tableName
     * @return
     */
    public List<Object[]> queryDataForFTP(String tableName, String ftpDate) {
        if (StringUtils.isNotEmpty(ftpDate)) {
            try {
                ftpDate = JodaTimeTools.toString(JodaTimeTools.toDateTime(ftpDate, JodaTimeTools.FORMAT_7),
                        JodaTimeTools.FORMAT_6);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        List<Object[]> list = null;
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            // 获取JNDI数据库连接配置信息
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java://comp/env/jdbc/core-db");
            conn = ds.getConnection();
            // 连接数据库
            if (conn == null) {
                return null;
            }
            String sql = "SELECT * FROM " + tableName + " WHERE create_time LIKE '%" + ftpDate + "%'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            list = rsToList(rs);
        } catch (CommunicationsException | MySQLNonTransientConnectionException e) {
            LOGGER.info("MySQL数据连接失效，自动清空并重新建立连接", e);
            return queryDataForFTP(tableName, ftpDate);
        } catch (MySQLSyntaxErrorException e) {
            LOGGER.error("表：【" + tableName + "】不存在或表结构不正确，下载失败！");
        } catch (SQLException e) {
            LOGGER.error("JDBC连接失败！", e);
        } catch (NamingException e) {
            LOGGER.error("JDBC连接失败！", e);
        } finally {
            try {
                rs.close();
                ps.close();
                // 注意不是关闭,是放回连接池
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 数据结果集转换成bean
     * 
     * @author zcq
     * @param rs
     * @param clazz
     * @return
     */
    public List<Object[]> rsToList(ResultSet rs) {
        // 返回的正文文本
        List<Object[]> resultList = new ArrayList<Object[]>();
        try {
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int columnCount = rsMetaData.getColumnCount();
            // 添加表的字段名称
            Object[] columName = new Object[columnCount];
            for (int i = 0; i < columnCount; i++) {
                columName[i] = rsMetaData.getColumnLabel(i + 1).toLowerCase();
            }
            resultList.add(columName);
            // 添加表值
            while (rs.next()) {
                Object[] obj = new Object[columnCount];
                for (int j = 0; j < columnCount; j++) {
                    obj[j] = rs.getString(j + 1);
                }
                resultList.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }
}

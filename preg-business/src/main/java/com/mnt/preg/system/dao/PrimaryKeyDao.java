
package com.mnt.preg.system.dao;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.mnt.health.core.annotation.AnnotationUtil;
import com.mnt.health.core.exception.ServiceException;
import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.preg.main.enums.Flag;
import com.mnt.preg.main.results.ResultCode;

/**
 * 
 * 主键生成规则
 * 
 * @author mnt_zhangjing
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-10-27 mnt_zhangjing 初版
 */
@Repository
public class PrimaryKeyDao extends HibernateTemplate {

    /**
     * 检验编码是否重复
     * 
     * @author zcq
     * @param fieldName
     * @param fieldValue
     * @param clazz
     * @return
     */
    public int validCode(String fieldName, String fieldValue, Class<?> clazz) {
        if (StringUtils.isEmpty(fieldName) || StringUtils.isEmpty(fieldValue)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        String sql = "SELECT COUNT(flag) "
                + "   FROM " + AnnotationUtil.getTableName(clazz)
                + "   WHERE " + DaoUtils.nameFieldToColumn(fieldName) + "=:fieldValue AND flag = :flag";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("fieldValue", fieldValue);
        paramMap.put("flag", Flag.normal.getValue());
        return this.SQLCount(sql, paramMap);
    }

    /**
     * 检验名称是否重复
     * 
     * @author zcq
     * @param conditionMap
     * @param clazz
     * @return
     */
    public Integer validName(Map<String, Object> conditionMap, Class<?> clazz) {
        if (MapUtils.isEmpty(conditionMap)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }

        // 设置条件值
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("flag", Flag.normal.getValue());

        // 拼接条件语句
        StringBuffer conditionSQL = new StringBuffer(" flag = :flag ");
        for (String fieldName : conditionMap.keySet()) {
            conditionSQL.append(" AND " + DaoUtils.nameFieldToColumn(fieldName) + "=:" + fieldName);
            paramMap.put(fieldName, conditionMap.get(fieldName));
        }

        String sql = "SELECT COUNT(flag) "
                + "   FROM " + AnnotationUtil.getTableName(clazz)
                + "   WHERE " + conditionSQL.toString();

        return this.SQLCount(sql, paramMap);
    }

    /**
     * 生成顺序号主键
     * 
     * @author zcq
     * @param tableName
     *            表名称
     * @param filedName
     *            主键字段名称
     * @return 顺序号
     */
    public String getOrderNo(String tableName, String filedName) {
        String sql = "SELECT MAX(CAST(" + filedName + " as SIGNED)) FROM " + tableName;
        BigInteger maxId = (BigInteger) this.SQLQueryFirst(sql, null);
        return String.valueOf(((maxId == null) ? BigInteger.ZERO : maxId).add(BigInteger.valueOf(1)));
    }

}

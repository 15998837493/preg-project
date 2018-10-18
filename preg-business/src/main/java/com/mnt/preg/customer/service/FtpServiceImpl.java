/**
 * 
 */

package com.mnt.preg.customer.service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.core.jdbc.DBUtil;
import com.mnt.health.utils.files.ZipUtils;
import com.mnt.health.utils.ftp.FtpUtil;
import com.mnt.preg.customer.dao.FtpDAO;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.system.dao.InstitutionDAO;
import com.mnt.preg.system.dao.UserDao;
import com.mnt.preg.system.entity.User;
import com.mnt.preg.system.pojo.UserPojo;

/**
 * Ftp服务
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-8-1 zcq 初版
 */
@Service
public class FtpServiceImpl extends BaseService implements FtpService {

    @Resource
    private InstitutionDAO institutionDAO;

    @Resource
    private UserDao userDAO;

    @Resource
    private FtpDAO ftpDAO;

    private static final Logger LOGGER = LoggerFactory.getLogger(FtpServiceImpl.class);

    @Override
    public String queryDataForFTP(String ftpDate) {
        FtpUtil ftpUtil = new FtpUtil();
        // 资源文件路径
        UserPojo userPojo = null;
        userPojo = userDAO.getUserByUserCode("admin");
        if (userPojo == null) {
            List<UserPojo> userList = userDao.queryUser(new User());
            userPojo = userList.get(0);
        }
        String srcPath = publicConfig.getResourceAbsolutePath() + "resource/core_db/" + userPojo.getCreateInsId() + "_"
                + ftpDate + "/";
        // 检索数据生成SQL文件
        List<String> queryList = Arrays.asList(tableArray);
        if (CollectionUtils.isNotEmpty(queryList)) {
            // 清空要写入的目录
            if (new File(srcPath).exists()) {
                try {
                    FileUtils.deleteDirectory(new File(srcPath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 写入SQL语句
            int count = 0;
            for (String tableName : queryList) {
                List<Object[]> list = ftpDAO.queryDataForFTP(tableName, ftpDate);
                String insertSQL = DBUtil.getResultContent(list, tableName);
                if (StringUtils.isNotEmpty(insertSQL)) {
                    ftpUtil.writeFile(srcPath + tableName + ".sql", insertSQL);
                    count++;
                }
            }
            LOGGER.info("同步数据下载成功！本次下载共有【" + count + "】张表");
        }

        // 压缩文件
        String compressPath = publicConfig.getResourceAbsolutePath() + "resource/core_db/";
        String fileName = userPojo.getCreateInsId() + "_" + ftpDate + ".zip";
        File mulu = new File(srcPath);
        String resultPath = "";
        if (mulu.exists()) {
            // 压缩临时目录
            ZipUtils.compressExe(srcPath, compressPath + fileName);
            try {
                FileUtils.deleteDirectory(mulu);
            } catch (IOException e) {
                e.printStackTrace();
            }
            resultPath = "resource/core_db/" + fileName;
        }
        return resultPath;

        // // 把文件上传到FTP服务器上
        // try {
        // // 压缩文件路径
        // String compressPath = publicConfig.getResourceAbsolutePath() + "resource/core_db/";
        // String fileName = userVo.getCreateInsId() + "_" + yesterday + ".zip";
        // // 解压的文件夹
        // File mulu = new File(srcPath);
        // // 压缩后的文件夹
        // File zipFile = new File(compressPath + fileName);
        // if (mulu.exists()) {
        // // 压缩临时目录
        // ZipUtils.compressExe(srcPath, compressPath + fileName);
        // // 删除临时目录
        // FileUtils.deleteDirectory(mulu);
        // // 连接FTP服务器
        // String ip = this.getParamValue(SystemParamCode.FTP_IP);
        // String port = this.getParamValue(SystemParamCode.FTP_PORT);
        // String user = this.getParamValue(SystemParamCode.FTP_USER);
        // String password = this.getParamValue(SystemParamCode.FTP_PASSWORD);
        // port = StringUtils.isEmpty(port) ? "21" : port;
        // ftpUtil.connect(ip, Integer.valueOf(port), user, password, false);
        // // 设置目录
        // ftpUtil.setWorkingDirectory("");
        // // 上传文件
        // ftpUtil.upload(fileName, zipFile);
        // LOGGER.info("同步文件【" + fileName + "】上传FTP服务器成功！");
        // // 删除临时文件
        // zipFile.delete();
        // }
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
    }

    /**
     * 执行SQL
     * 
     * @author zcq
     */
    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void executeReadSQL(String path) {
        String unZipPath = path.substring(0, path.lastIndexOf(".")) + "/";
        ZipUtils.unZipFiles(new File(path), unZipPath);
        if (new File(unZipPath).exists()) {
            File[] tempList = new File(unZipPath).listFiles();
            if (tempList != null && tempList.length > 0) {
                for (File file : tempList) {
                    try {
                        String sql = FileUtils.readFileToString(file, "UTF-8");
                        if (StringUtils.isNotEmpty(sql)) {
                            String[] sqlArray = sql.split("INSERT INTO");
                            for (String executeSQL : sqlArray) {
                                if (StringUtils.isNotEmpty(executeSQL)) {
                                    ftpDAO.executeSQL("INSERT INTO " + executeSQL);
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ConstraintViolationException e) {
                        LOGGER.error(file.getName() + "：主键重复，违反主键唯一约束性！");
                    }
                }
            }
            LOGGER.info("诊疗系统数据同步完成！");
            // 删除解压文件，保留压缩文件
            try {
                FileUtils.deleteDirectory(new File(unZipPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 要同步的表
    private String[] tableArray = {
            "cus_customer"
    };
}

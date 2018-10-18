
package com.mnt.preg.customer.service;

import org.springframework.validation.annotation.Validated;

/**
 * Ftp服务
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-8-1 zcq 初版
 */
@Validated
public interface FtpService {

    /**
     * 批量获取SQL语句
     * 
     * @author zcq
     * @param ftpDate
     * @return
     */
    String queryDataForFTP(String ftpDate);

    /**
     * 获取文档并执行SQL
     * 
     * @author zcq
     * @param path
     */
    void executeReadSQL(String path);
}

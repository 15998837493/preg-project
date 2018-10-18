
package com.mnt.preg.system.service;

import java.util.List;
import java.util.Map;

import org.springframework.validation.annotation.Validated;

import com.mnt.preg.system.condition.PrintCondition;
import com.mnt.preg.system.pojo.PrintPojo;

/**
 * 打印选项事务
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-26 zcq 初版
 */
@Validated
public interface PrintService {

    /**
     * 检索打印选项
     * 
     * @author zcq
     * @param condition
     * @return
     */
    List<PrintPojo> queryPrint(PrintCondition condition);

    /**
     * 获取去打印选项MAP
     * 
     * @author zcq
     * @return
     */
    Map<String, List<PrintPojo>> getPrintListMap();

}

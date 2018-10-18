
package com.mnt.preg.customer.service;

import org.springframework.validation.annotation.Validated;

import com.mnt.preg.customer.pojo.BirthEndingBaseInfoPojo;

/**
 * 
 * 分娩结局_录入_分娩信息<br>
 * 
 * <dl>
 * <dt>功能描述</dt>
 * <dd>1、查询</dd>
 * <dd>2、增加</dd>
 * <dd>4、更新</dd>
 * </dl>
 * 
 * 
 * @author wjc
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-08-06 wjc 初版
 */
@Validated
public interface BirthEndingBaseInfoService {

    /**
     * 
     * 分娩信息新增
     * 
     * @param babyInfo
     * @return
     */
    BirthEndingBaseInfoPojo saveBirthBabyInfo(BirthEndingBaseInfoPojo basePojo);

    /**
     * 
     * 通过birthId获取分娩基础信息
     * 
     * @param birthId
     * @return
     */
    BirthEndingBaseInfoPojo getBaseByBirthId(String birthId);

    /**
     * 
     * 通过birthId删除分娩基础信息
     * 
     * @param birthId
     * @return
     */
    void deleteBirthBaseByBirthId(String birthId);

}

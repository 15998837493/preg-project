
package com.mnt.preg.platform.service;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import com.mnt.preg.customer.pojo.PregArchivesPojo;
import com.mnt.preg.platform.entity.PregArchives;

/**
 * 
 * 孕期建档管理
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-1 gss 初版
 */
@Validated
public interface PregArchivesService {

    /**
     * 获取最近一次的孕期档案
     * 
     * @author zcq
     * @param custId
     * @return
     */
    PregArchivesPojo getLastPregnancyArchive(String custId);

    /**
     * 修改孕期建档信息
     * 
     * @author gss
     * @param pregnancyArchives
     */
    void updatePregnancyArchives(PregArchives pregnancyArchives);

    /**
     * 物理删除孕期档案表信息
     * 
     * @author gss
     * @return
     */
    public void deleteAndSavePregnancyArchives(PregArchives pregnancyArchives);

    /**
     * 
     * 获取客户建档记录
     * 
     * @author scd
     * @param custId
     * @return
     */
    List<PregArchivesPojo> queryCustomerPregRecprd(@NotBlank String custId);

    /**
     * 
     * 根据主键获取建档信息
     * 
     * @author scd
     * @param id
     * @return
     */
    PregArchivesPojo getPregArchivesPojoById(String id);
}

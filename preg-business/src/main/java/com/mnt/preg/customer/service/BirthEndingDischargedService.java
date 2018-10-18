
package com.mnt.preg.customer.service;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.mnt.preg.customer.entity.BirthEndingDischarged;
import com.mnt.preg.customer.pojo.BirthEndingDischargedPojo;

/**
 * 
 * 分娩结局_录入_出院诊断<br>
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
public interface BirthEndingDischargedService {

    /**
     * 
     * 更新出院诊断信息
     * 
     * @param discharge
     */
    void updateBirthDischarged(BirthEndingDischarged discharge);

    /**
     * 
     * 新增出院诊断信息
     * 
     * @param discharge
     */
    BirthEndingDischargedPojo saveBirthDischarged(BirthEndingDischarged discharge);

    /**
     * 
     * 通过birthId获取出院诊断
     * 
     * @param birthId
     * @return
     */
    List<BirthEndingDischargedPojo> getDischargedByBirthId(String birthId);

    /**
     * 
     * 删除新生儿诊断(新生儿Id)
     * 
     * @param babyId
     */
    void deleteDischargedByBabyId(String babyId);

    /**
     * 
     * 删除新生儿诊断(分娩id)
     * 
     * @param birthId
     */
    void deleteDischargedByBirthId(String birthId);

}

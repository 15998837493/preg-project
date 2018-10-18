
package com.mnt.preg.customer.service;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.mnt.preg.customer.condition.BirthEndingBabyInfoCondition;
import com.mnt.preg.customer.entity.BirthEndingBabyInfo;
import com.mnt.preg.customer.pojo.BirthEndingBabyInfoPojo;

/**
 * 
 * 分娩结局_录入_新生儿信息<br>
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
public interface BirthEndingBabyInfoService {

    /**
     * 
     * 新增新生儿
     * 
     * @param babyInfo
     * @return
     */
    BirthEndingBabyInfoPojo saveBirthBabyInfo(BirthEndingBabyInfo babyInfo);

    /**
     * 
     * 更新新生儿
     * 
     * @param babyInfo
     * @return
     */
    void updateBirthBabyInfo(BirthEndingBabyInfo babyInfo);

    /**
     * 
     * 通过birthId获取新生儿列表
     * 
     * @param birthId
     * @return
     */
    List<BirthEndingBabyInfoPojo> getBabyListByBirthId(String birthId);

    /**
     * 
     * 获取新生儿列表
     * 
     * @param condition
     * @return
     */
    List<BirthEndingBabyInfoPojo> getBabyListByCondition(BirthEndingBabyInfoCondition condition);

    /**
     * 
     * 获取新生儿数量最大值
     * 
     * @param condition
     * @return
     */
    BirthEndingBabyInfoPojo getMaxBabyListByCondition(BirthEndingBabyInfoCondition babyContion);

    /**
     * 
     * 删除新生儿(主键)
     * 
     * @param babyId
     */
    void deleteBabyById(String babyId);

    /**
     * 
     * 删除新生儿（分娩id）
     * 
     * @param birthId
     */
    void deleteBabyByBirthId(String birthId);

}

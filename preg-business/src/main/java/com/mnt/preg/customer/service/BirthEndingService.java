
package com.mnt.preg.customer.service;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.mnt.preg.customer.condition.BirthEndingCondition;
import com.mnt.preg.customer.entity.BirthEnding;
import com.mnt.preg.customer.pojo.BirthEndingPojo;

/**
 * 
 * 分娩结局管理<br>
 * 
 * <dl>
 * <dt>功能描述</dt>
 * <dd>1、分娩结局CRUD</dd>
 * <dd>2、分娩结局分页</dd>
 * </dl>
 * 
 * 
 * @author wjc
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-11-6 wjc 初版
 */
@Validated
public interface BirthEndingService {

    /**
     * 检索分娩结局信息
     * 
     * @author
     * @return
     */
    List<BirthEndingPojo> queryBirthEndingList(BirthEndingCondition condition);

    /**
     * 
     * 添加分娩结局
     *
     * @param birthCondition
     * @return
     */
    BirthEndingPojo addBirthEnding(BirthEnding birthEnding);

    /**
     *
     * 添加分娩结局
     *
     * @param custId
     * @return
     */
    BirthEndingPojo addBirthEnding(String custId);

    /**
     * 
     * 修改分娩结局
     *
     * @param birthCondition
     * @return
     */
    BirthEndingPojo updateBirthEnding(BirthEnding birthEnding);

    /**
     * 
     * 根据主键查询
     *
     * @param birthId
     * @return
     */
    BirthEndingPojo getBirthEndingById(String birthId);

    /**
     * 
     * 根据主键删除
     *
     * @param birthId
     */
    void deleteBirthEnding(String birthId);

}

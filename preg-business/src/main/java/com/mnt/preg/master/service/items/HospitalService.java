
package com.mnt.preg.master.service.items;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import com.mnt.preg.master.condition.items.HospitalCondition;
import com.mnt.preg.master.entity.items.Hospital;
import com.mnt.preg.master.pojo.items.HospitalPojo;

/**
 * 
 * 医院信息数据接口
 * 
 * @author dhs
 * @version 1.7
 * 
 *          变更履历：
 *          v1.7 2018-08-07 dhs 初版
 */
@Validated
public interface HospitalService {

    /**
     * 
     * 添加医院信息
     * 
     * @author dhs
     * @param hospitalPojo
     */
    HospitalPojo saveHospital(@Valid @NotNull Hospital hospital);

    /**
     * 
     * 修改医院信息
     * 
     * @author dhs
     * @param hospitalPojo
     * @param id
     *            主键
     */
    HospitalPojo updateHospital(@Valid @NotNull Hospital hospital);

    /**
     * 
     * 删除医院信息（物理删除）
     * 
     * @author dhs
     * @param id
     */
    void removeHospital(@NotBlank String id);

    /**
     * 
     * 删除医院信息(逻辑删除)
     * 
     * @author dhs
     * @param id
     */
    void removeHospitalByType(String type, String itemType, String itemClassify);

    /**
     * 
     * 验证医院名字唯一性
     * 
     * @author dhs
     * @param id
     */
    Integer checkHospitalName(@NotBlank String itemName, @NotBlank String itemType, @NotBlank String itemClassify);

    /**
     * 
     * 条件检索医院信息
     * 
     * @author dhs
     * @param condition
     *            查询条件
     */
    public List<HospitalPojo> queryHospital(HospitalCondition condition);

    /**
     * 
     * 根据主键查询医院信息
     * 
     * @author dhs
     * @param id
     *            主键
     */
    HospitalPojo getHospitalById(@NotBlank String hospitalId);

    /**
     * 
     * 查询大分类
     * 
     * @author dhs
     * @param id
     *            主键
     */
    List<HospitalPojo> queryHospitalType();

    /**
     * 
     * 修改ztree节点名称
     * 
     * @author dhs
     * @param 主键
     */
    void updateHospitalType(String oldType, String newType, String type, String pType);

}

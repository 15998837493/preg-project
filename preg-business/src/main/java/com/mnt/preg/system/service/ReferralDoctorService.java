
package com.mnt.preg.system.service;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import com.mnt.preg.system.condition.ReferralDoctorCondition;
import com.mnt.preg.system.entity.ReferralDoctor;
import com.mnt.preg.system.pojo.ReferralDoctorPojo;

@Validated
public interface ReferralDoctorService {

    /**
     * 查询转诊医生列表
     * 
     * @author dhs
     * @param condition
     * @return
     */
    List<ReferralDoctorPojo> queryDoctors(ReferralDoctorCondition referralDoctor);

    /**
     * 
     * 增加转诊医生
     * 
     * @author dhs
     * @param nutrient
     * @return ReferralDoctorPojo
     */
    ReferralDoctorPojo saveReferralDoctor(ReferralDoctor referralDoctor);

    /**
     * 
     * 修改转诊医生
     * 
     * @author dhs
     * @param nutrientVo
     * @param doctorId
     *            主键
     */
    ReferralDoctorPojo updateReferralDoctor(ReferralDoctor referralDoctor);

    /**
     * 
     * 根据主键删除元素记录
     * 
     * @author dhs
     * @param id
     */
    void removeReferralDoctor(@NotBlank String id);

    /**
     * 
     * 验证不能重复
     * 
     * @author dhs
     * @param id
     */
    Integer validCode(String filedValue, String property);

    /***
     * 
     * 检索全部转诊医生以及该转诊医生所在的科室
     * 
     * @author scd
     * @param referralDoctor
     * @return
     */
    List<ReferralDoctorPojo> queryDoctorDept(ReferralDoctor referralDoctor);
}

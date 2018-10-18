
package com.mnt.preg.system.service;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import com.mnt.preg.platform.condition.DiagnosisCondition;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;
import com.mnt.preg.system.entity.Doctor;
import com.mnt.preg.system.pojo.DoctorPojo;

@Validated
public interface DoctorService {

    /**
     * 条件检索一周课程配置基础数据
     * 
     * @author dhs
     * @param condition
     * @return
     */
    List<DoctorPojo> queryDoctorByCondition(Doctor condition);

    /**
     * 
     * 增加记录
     * 
     * @author gss
     * @param nutrient
     * @return NutrientVo
     */
    DoctorPojo saveDoctor(Doctor doctor);

    /**
     * 
     * 修改记录
     * 
     * @author gss
     * @param nutrientVo
     * @param nutrientId
     *            主键
     */
    DoctorPojo updateDoctor(Doctor doctor);

    /**
     * 
     * 根据主键删除记录
     * 
     * @author gss
     * @param id
     */
    void removeDoctor(@NotBlank String id);
    
    /**
     * 
     * 查询预约人数
     * 
     * @author gss
     * @param id
     */
    List<PregDiagnosisPojo> queryBooks(DiagnosisCondition condition);
}

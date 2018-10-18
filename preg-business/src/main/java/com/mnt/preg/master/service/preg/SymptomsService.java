
package com.mnt.preg.master.service.preg;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import com.mnt.preg.master.condition.preg.SymptomsCondition;
import com.mnt.preg.master.entity.preg.Symptoms;
import com.mnt.preg.master.pojo.preg.SymptomsPojo;

/**
 * 
 * 功能症状表接口
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-4-5 gss 初版
 */
@Validated
public interface SymptomsService {

    /**
     * 
     * 功能症状记录查询
     * <dl>
     * <dt>1 功能描述</dt>
     * <dd>功能症状记录查询</dd>
     * </dl>
     * 
     * @author dhs
     * @param condition
     *            查询条件
     */
    List<SymptomsPojo> querySymptoms(SymptomsCondition condition);

    /**
     * 
     * 根据symptomsId查询问题基本信息
     * 
     * @author dhs
     * @param symptomsId
     * @return
     */
    SymptomsPojo getSymptomsBySymptomsId(String id);

    /**
     * 
     * 增加功能症状信息
     * 
     * @author dhs
     * @param Symptoms
     * @return SymptomsPojo
     */
    SymptomsPojo saveSymptoms(@Valid @NotNull Symptoms symptoms);

    /**
     * 
     * 根据主键删除功能症状记录
     * 
     * @author dhs
     * @param id
     */
    void removeSymptoms(@NotBlank String id);

    /**
     * 
     * 修改功能症状记录
     * 
     * @author dhs
     * @param symptoms
     *            主键
     */
    SymptomsPojo updateSymptoms(@Valid @NotNull Symptoms symptoms);
}

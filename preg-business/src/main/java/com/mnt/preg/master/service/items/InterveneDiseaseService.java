
package com.mnt.preg.master.service.items;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import com.mnt.preg.master.condition.items.DiseaseNutrientCondition;
import com.mnt.preg.master.condition.items.InterveneDiseaseCondition;
import com.mnt.preg.master.condition.items.MasInterveneDiseaseInspectCondition;
import com.mnt.preg.master.condition.items.MasPrescriptionCondition;
import com.mnt.preg.master.entity.items.DiseaseItem;
import com.mnt.preg.master.entity.items.DiseaseNutrient;
import com.mnt.preg.master.entity.items.InterveneDiaeaseInsepectPay;
import com.mnt.preg.master.pojo.items.DiseaseNutrientPojo;
import com.mnt.preg.master.pojo.items.HospitalInspectPayPojo;
import com.mnt.preg.master.pojo.items.InterveneDiseasePojo;
import com.mnt.preg.platform.entity.DiseasePrescription;
import com.mnt.preg.platform.pojo.DiseasePrescriptionPojo;

/**
 * 
 * 干预疾病接口
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历： v1.0 2016-4-5 gss 初版
 */
@Validated
public interface InterveneDiseaseService {

    /**
     * 
     * 获取疾病表最大编码（过后维护成可配置表名，转移到共有接口中。）
     * 
     * @author scd
     * @return
     */
    String getMaxDiseaseCode();

    /**
     * 
     * 增加干预疾病记录
     * 
     * @author gss
     * @param interveneDiseaseVo
     * @return 主键
     */
    String saveInterveneDisease(@Valid @NotNull InterveneDiseasePojo interveneDiseaseVo);

    /**
     * 
     * 修改干预疾病记录
     * 
     * @author gss
     * @param interveneDiseaseVo
     * @param interveneDiseaseId
     *            主键
     */
    void updateInterveneDisease(InterveneDiseasePojo interveneDiseaseVo, String diseaseCode);

    /**
     * 
     * 根据主键删除干预疾病记录
     * 
     * @author gss
     * @param diseaseCode
     */
    void removeInterveneDisease(@NotBlank String diseaseCode);

    /**
     * 
     * 干预疾病记录查询
     * <dl>
     * <dt>1 功能描述</dt>
     * <dd>干预疾病记录查询</dd>
     * </dl>
     * 
     * @author gss
     * @param condition
     *            查询条件
     */
    List<InterveneDiseasePojo> queryInterveneDisease(InterveneDiseaseCondition condition);

    /**
     * 
     * 根据diseaseCode查询问题基本信息
     * 
     * @author gss
     * @param diseaseCode
     * @return
     */
    InterveneDiseasePojo getInterveneDiseaseById(@NotBlank String diseaseCode);

    /**
     * 校验干预疾病编码
     * 
     * @author xdc
     * @param diseaseCode
     * @return
     */
    Integer checkDiseaseCodeValid(String diseaseCode);

    /**
     * 校验干预疾病名称
     * 
     * @author scd
     * @param diseaseName
     * @return
     */
    Integer checkDiseaseNameValid(String diseaseName);

    /**
     * 添加疾病检测指标
     * 
     * @author zcq
     * @param diseaseItem
     * @return
     */
    String addDiseaseItem(DiseaseItem diseaseItem);

    /**
     * 查询诊断营养处方
     * 
     * @author scd
     * @return
     */
    List<DiseasePrescriptionPojo> queryMasPrescription(MasPrescriptionCondition condition);

    /**
     * 
     * 添加诊断营养处方
     * 
     * @param Prescription
     * @return
     */
    String addPrescription(DiseasePrescription Prescription);

    /**
     * 
     * 根据主键获得营养处方
     * 
     * @param PrescriptionId
     * @return
     */
    DiseasePrescriptionPojo getPrescription(String PrescriptionId);

    /**
     * 
     * 删除营养处方
     * 
     * @param PrescriptionId
     * @return
     */
    void deletePrescription(String PrescriptionId);

    /**
     * 查询辅助检查项目
     * 
     * @author scd
     * @return
     */
    List<HospitalInspectPayPojo> queryInspectConfig(MasInterveneDiseaseInspectCondition condition);

    /**
     * 
     * 添加辅助检查项目
     * 
     * @author scd
     * @param config
     * @return
     */
    String addInspectConfig(InterveneDiaeaseInsepectPay config);

    /**
     * 
     * 根据组件获取辅助检查项目
     * 
     * @author scd
     * @param id
     * @return
     */
    HospitalInspectPayPojo getInspectConfigById(String id);

    /**
     * 
     * 删除辅助检查项目
     * 
     * @author scd
     * @param id
     */
    void deleteInspect(String id);

    /**
     * 
     * 条件检索疾病关联的元素
     * 
     * @author scd
     * @param condition
     * @return
     */
    List<DiseaseNutrientPojo> queryDiseaseNutrient(DiseaseNutrientCondition condition);

    /**
     * 
     * 添加疾病关联元素
     * 
     * @author scd
     * @param config
     * @return
     */
    String addDiseaseNutrientConfig(DiseaseNutrient config);

    /**
     * 
     * 根据关联主键获取疾病关联的元素
     * 
     * @author scd
     * @param id
     * @return
     */
    DiseaseNutrientPojo getNutrientConfigById(String id);

    /**
     * 
     * 删除疾病关联的元素
     * 
     * @author scd
     * @param id
     */
    void deleteNutrient(String id);
}

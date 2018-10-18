
package com.mnt.preg.master.service.items;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.main.utils.AutomaticGenerationCoding;
import com.mnt.preg.master.condition.items.DiseaseNutrientCondition;
import com.mnt.preg.master.condition.items.InterveneDiseaseCondition;
import com.mnt.preg.master.condition.items.MasInterveneDiseaseInspectCondition;
import com.mnt.preg.master.condition.items.MasPrescriptionCondition;
import com.mnt.preg.master.dao.items.InterveneDiseaseDAO;
import com.mnt.preg.master.entity.items.DiseaseItem;
import com.mnt.preg.master.entity.items.DiseaseNutrient;
import com.mnt.preg.master.entity.items.InterveneDiaeaseInsepectPay;
import com.mnt.preg.master.entity.items.InterveneDisease;
import com.mnt.preg.master.pojo.items.DiseaseNutrientPojo;
import com.mnt.preg.master.pojo.items.HospitalInspectPayPojo;
import com.mnt.preg.master.pojo.items.InterveneDiseasePojo;
import com.mnt.preg.platform.entity.DiseasePrescription;
import com.mnt.preg.platform.pojo.DiseasePrescriptionPojo;

/**
 * 干预疾病字典接口实现
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历： v1.0 2016-4-5 gss 初版
 */
@Service
public class InterveneDiseaseServiceImpl extends BaseService implements InterveneDiseaseService {

    @Resource
    private InterveneDiseaseDAO interveneDiseaseDAO;

    @Override
    @Transactional(readOnly = true)
    public String getMaxDiseaseCode() {
        String insId = this.getInsId() == null ? "" : this.getInsId();
        String maxDiseaseCode = interveneDiseaseDAO.getMaxDiseaseCode(insId);
        String newCode = AutomaticGenerationCoding.getInstance().getNextCashNumber(maxDiseaseCode, 6);
        return insId + newCode;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String saveInterveneDisease(InterveneDiseasePojo interveneDiseaseVo) {
        InterveneDisease interveneDisease = TransformerUtils.transformerProperties(InterveneDisease.class,
                interveneDiseaseVo);
        interveneDisease.setStatus(String.valueOf(1));
        return (String) interveneDiseaseDAO.save(interveneDisease);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateInterveneDisease(InterveneDiseasePojo interveneDiseaseVo, String diseaseCode) {
        InterveneDisease interveneDisease = TransformerUtils.transformerProperties(InterveneDisease.class,
                interveneDiseaseVo);
        interveneDiseaseDAO.updateInterveneDisease(interveneDisease, diseaseCode);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void removeInterveneDisease(String diseaseId) {
        InterveneDiseasePojo interveneDiseaseVo = interveneDiseaseDAO.getInterveneDiseaseByInspectId(diseaseId);
        InterveneDisease interveneDisease = TransformerUtils.transformerProperties(InterveneDisease.class,
                interveneDiseaseVo);
        interveneDisease.setFlag(0);
        interveneDiseaseDAO.updateInterveneDisease(interveneDisease, diseaseId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InterveneDiseasePojo> queryInterveneDisease(InterveneDiseaseCondition condition) {
        List<InterveneDiseasePojo> interveneDiseaseVos = interveneDiseaseDAO.queryInterveneDisease(condition);
        return interveneDiseaseVos;
    }

    @Override
    @Transactional(readOnly = true)
    public InterveneDiseasePojo getInterveneDiseaseById(String diseaseCode) {
        return (InterveneDiseasePojo) interveneDiseaseDAO.getTransformerBean(diseaseCode, InterveneDisease.class,
                InterveneDiseasePojo.class);
    }

    @Override
    public Integer checkDiseaseCodeValid(String diseaseCode) {
        return this.validCode("diseaseCode", diseaseCode, InterveneDisease.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer checkDiseaseNameValid(String diseaseName) {
        return this.validCode("diseaseName", diseaseName, InterveneDisease.class);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addDiseaseItem(DiseaseItem diseaseItem) {
        return (String) interveneDiseaseDAO.save(diseaseItem);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiseasePrescriptionPojo> queryMasPrescription(MasPrescriptionCondition condition) {
        return interveneDiseaseDAO.queryMasPrescription(condition);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addPrescription(DiseasePrescription Prescription) {
        return (String) interveneDiseaseDAO.save(Prescription);
    }

    @Override
    @Transactional(readOnly = true)
    public DiseasePrescriptionPojo getPrescription(String PrescriptionId) {
        return interveneDiseaseDAO.getPrescriptionPojo(PrescriptionId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deletePrescription(String PrescriptionId) {
        interveneDiseaseDAO.deletePrescription(PrescriptionId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HospitalInspectPayPojo> queryInspectConfig(MasInterveneDiseaseInspectCondition condition) {
        return interveneDiseaseDAO.queryInspectConfig(condition);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addInspectConfig(InterveneDiaeaseInsepectPay config) {
        return (String) interveneDiseaseDAO.save(config);
    }

    @Override
    @Transactional(readOnly = true)
    public HospitalInspectPayPojo getInspectConfigById(String id) {
        return interveneDiseaseDAO.getHospitalInspect(id);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteInspect(String id) {
        interveneDiseaseDAO.deleteInspect(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiseaseNutrientPojo> queryDiseaseNutrient(DiseaseNutrientCondition condition) {
        return interveneDiseaseDAO.queryDiseaseNutrient(condition);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addDiseaseNutrientConfig(DiseaseNutrient config) {
        return (String) interveneDiseaseDAO.save(config);
    }

    @Override
    @Transactional(readOnly = true)
    public DiseaseNutrientPojo getNutrientConfigById(String id) {
        return interveneDiseaseDAO.getNutrientConfigById(id);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteNutrient(String id) {
        interveneDiseaseDAO.deleteNutrient(id);
    }

}


package com.mnt.preg.master.dao.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mnt.health.core.exception.ServiceException;
import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.core.utils.HQLSymbol;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.main.results.ResultCode;
import com.mnt.preg.master.condition.items.DiseaseNutrientCondition;
import com.mnt.preg.master.condition.items.InterveneDiseaseCondition;
import com.mnt.preg.master.condition.items.MasInterveneDiseaseInspectCondition;
import com.mnt.preg.master.condition.items.MasPrescriptionCondition;
import com.mnt.preg.master.entity.items.InterveneDisease;
import com.mnt.preg.master.pojo.items.DiseaseNutrientPojo;
import com.mnt.preg.master.pojo.items.HospitalInspectPayPojo;
import com.mnt.preg.master.pojo.items.InterveneDiseasePojo;
import com.mnt.preg.platform.pojo.DiseasePrescriptionPojo;

/**
 * 干预疾病表DAO
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历： v1.0 2016-4-7 gss 初版
 */
@Repository
public class InterveneDiseaseDAO extends HibernateTemplate {

    /**
     * 获取疾病表最大编码（过后维护成可配置表名，转移到共有接口中。）
     * 
     * @author scd
     * @return
     */
    public String getMaxDiseaseCode(String insId) {
        String sql = "SELECT MAX(SUBSTR(disease_code,INSTR(disease_code,'" + insId + "')+" + insId.length()
                + ")) FROM mas_intervene_disease"
                + "  WHERE create_ins_id =:insId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("insId", insId);
        return this.SQLQueryFirst(sql, paramMap);
    }

    /**
     * 根据id查询检查项目
     * 
     * @param inspectId
     *            主键
     * @return InterveneDiseaseVo
     */
    public InterveneDiseasePojo getInterveneDiseaseByInspectId(String diseaseId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(InterveneDiseasePojo.class, "InterveneDiseasePojo")
                + "   FROM mas_intervene_disease AS InterveneDiseasePojo"
                + "   WHERE InterveneDiseasePojo.disease_id=:diseaseId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("diseaseId", diseaseId);
        return this.SQLQueryAliasFirst(sql, paramMap, InterveneDiseasePojo.class);
    }

    /**
     * 根据查询条件查询干预疾病表记录
     * 
     * @param condition
     *            查询条件
     * @return List<ItemVo> 检查项目信息列表
     */
    public List<InterveneDiseasePojo> queryInterveneDisease(InterveneDiseaseCondition condition) {
        if (condition == null) {
            condition = new InterveneDiseaseCondition();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "InterveneDiseasePojo");
        String sql = "SELECT " + DaoUtils.getSQLFields(InterveneDiseasePojo.class, "InterveneDiseasePojo")
                + "   FROM mas_intervene_disease AS InterveneDiseasePojo " + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), InterveneDiseasePojo.class);
    }

    /**
     * 
     * 更新干预疾病表记录
     * 
     * @author gss
     * @param interveneDisease
     * @param id
     *            主键
     */
    public void updateInterveneDisease(InterveneDisease interveneDisease, String diseaseId) {
        if (interveneDisease == null || diseaseId == null) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("disease_id", HQLSymbol.EQ.toString(), diseaseId));
        this.updateHQL(interveneDisease, conditionParams);
    }

    /**
     * 
     * 根据疾病编号查询疾病信息
     * 
     * @author mnt_zhangjing
     * @param diseaseCode
     * @return
     */
    public List<InterveneDiseasePojo> queryInterveneDiseaseByCode(String diseaseCode) {
        String sql = "SELECT " + DaoUtils.getSQLFields(InterveneDiseasePojo.class, "InterveneDiseasePojo")
                + "   FROM mas_intervene_disease AS InterveneDiseasePojo"
                + "   WHERE InterveneDiseasePojo.disease_code=:diseaseCode AND flag= :flag";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("diseaseCode", diseaseCode);
        paramMap.put("flag", 1);
        return this.SQLQueryAlias(sql, paramMap, InterveneDiseasePojo.class);
    }

    /****************************************************** 诊断项目与营养处方关联 *********************************************************************/
    /**
     * 
     * 条件检索诊断营养处方
     * 
     * @author scd
     * @param condition
     * @return
     */
    public List<DiseasePrescriptionPojo> queryMasPrescription(MasPrescriptionCondition condition) {
        if (condition == null) {
            condition = new MasPrescriptionCondition();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "MasPrescription");
        String querySQL = "SELECT " + DaoUtils.getSQLFields(DiseasePrescriptionPojo.class, "MasPrescription")
                + "        FROM user_disease_prescription AS MasPrescription"
                + "        JOIN mas_product AS product ON MasPrescription.product_id= product.product_id"
                + queryCondition.getQueryString() + queryCondition.getOrderString();
        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), DiseasePrescriptionPojo.class);
    }

    /**
     * 
     * 根据主键获取诊断营养处方
     * 
     * @author scd
     * @param PrescriptionId
     * @return
     */
    public DiseasePrescriptionPojo getPrescriptionPojo(String PrescriptionId) {
        String querySQL = "SELECT " + DaoUtils.getSQLFields(DiseasePrescriptionPojo.class, "MasPrescription")
                + "        FROM user_disease_prescription AS MasPrescription"
                + "        JOIN mas_product AS product ON MasPrescription.product_id= product.product_id"
                + "        WHERE MasPrescription.prescription_id=:id";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", PrescriptionId);
        return this.SQLQueryAliasFirst(querySQL, paramMap, DiseasePrescriptionPojo.class);
    }

    /**
     * 删除检查项目与指标
     * 
     * @author scd
     * @param id
     */
    public void deletePrescription(String id) {
        String sql = "DELETE FROM user_disease_prescription WHERE prescription_id=:id";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        this.executeSQL(sql, paramMap);
    }

    /**************************************************** 诊断项目与收费项目 *********************************************************************************/
    /**
     * 
     * 条件检索辅助检查项目
     * 
     * @author scd
     * @param condition
     * @return
     */
    public List<HospitalInspectPayPojo> queryInspectConfig(MasInterveneDiseaseInspectCondition condition) {
        if (condition == null) {
            condition = new MasInterveneDiseaseInspectCondition();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "config");
        String querySQL = "SELECT " + DaoUtils.getSQLFields(HospitalInspectPayPojo.class, "inspect") + ",config.id"
                + "        FROM mas_intervene_disease_inspect_pay AS config"
                + "        JOIN mas_hospital_inspect_pay AS inspect ON config.inspect_id= inspect.inspect_id"
                + queryCondition.getQueryString() + queryCondition.getOrderString();
        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), HospitalInspectPayPojo.class);
    }

    /**
     * 
     * 根据主键获取诊断与收费项目的关联
     * 
     * @author scd
     * @param id
     * @return
     */
    public HospitalInspectPayPojo getHospitalInspect(String id) {
        String querySQL = "SELECT " + DaoUtils.getSQLFields(HospitalInspectPayPojo.class, "inspect") + ",config.id"
                + "        FROM mas_intervene_disease_inspect_pay AS config"
                + "        JOIN mas_hospital_inspect_pay AS inspect ON config.inspect_id= inspect.inspect_id"
                + "        WHERE config.id=:id";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        return this.SQLQueryAliasFirst(querySQL, paramMap, HospitalInspectPayPojo.class);
    }

    /**
     * 删除诊断与收费项目的关联
     * 
     * @author scd
     * @param id
     */
    public void deleteInspect(String id) {
        String sql = "DELETE FROM mas_intervene_disease_inspect_pay WHERE id=:id";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        this.executeSQL(sql, paramMap);
    }

    /****************************************************** 诊断项目与元素关联 *********************************************************************/
    /**
     * 
     * 条件检索疾病关联的元素
     * 
     * @author scd
     * @param condition
     * @return
     */
    public List<DiseaseNutrientPojo> queryDiseaseNutrient(DiseaseNutrientCondition condition) {
        if (condition == null) {
            condition = new DiseaseNutrientCondition();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "config");
        String querySQL = "SELECT " + DaoUtils.getSQLFields(DiseaseNutrientPojo.class, "config")
                + "        FROM mas_intervene_disease_nutrient AS config"
                + "        JOIN mas_nutrient AS nutrient ON config.nutrient_id= nutrient.nutrient_id"
                + queryCondition.getQueryString() + queryCondition.getOrderString();
        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), DiseaseNutrientPojo.class);
    }

    /**
     * 
     * 根据关联主键获取疾病关联的元素
     * 
     * @author scd
     * @param id
     * @return
     */
    public DiseaseNutrientPojo getNutrientConfigById(String id) {
        String querySQL = "SELECT " + DaoUtils.getSQLFields(DiseaseNutrientPojo.class, "config")
                + "        FROM mas_intervene_disease_nutrient AS config"
                + "        JOIN mas_nutrient AS nutrient ON config.nutrient_id= nutrient.nutrient_id"
                + "        WHERE config.id=:id";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        return this.SQLQueryAliasFirst(querySQL, paramMap, DiseaseNutrientPojo.class);
    }

    /**
     * 删除疾病关联的元素
     * 
     * @author scd
     * @param id
     */
    public void deleteNutrient(String id) {
        String sql = "DELETE FROM mas_intervene_disease_nutrient WHERE id=:id";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        this.executeSQL(sql, paramMap);
    }
}

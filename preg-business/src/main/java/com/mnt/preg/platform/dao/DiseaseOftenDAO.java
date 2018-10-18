/*
 * DiseaseOftenDAO.java    1.0  2017-11-21
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.platform.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.platform.condition.DiseaseOftenCondition;
import com.mnt.preg.platform.entity.DiseaseOften;

/**
 * 常用诊断项目DAO
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-11-21 scd 初版
 */
@Repository
public class DiseaseOftenDAO extends HibernateTemplate {

    /**
     * 
     * 条件检索常用诊断项目
     * 
     * @author scd
     * @param daiseaseOften
     * @return
     */
    public List<DiseaseOften> queryDiseaseOften(DiseaseOftenCondition diseaseOften) {
        if (diseaseOften == null) {
            diseaseOften = new DiseaseOftenCondition();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(diseaseOften, "diseaseOften");

        String sql = "SELECT " + DaoUtils.getSQLFields(DiseaseOften.class, "diseaseOften")
                + "   FROM user_disease_often AS diseaseOften"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), DiseaseOften.class);
    }

    /**
     * 
     * 逻辑删除常用诊断项目
     * 
     * @author scd
     * @param diseaseId
     * @return
     */
    public Integer removeDiseaseOften(String diseaseId) {
        String sql = "UPDATE user_disease_often SET flag= :flag WHERE disease_id =:diseaseId";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("flag", 0);
        queryParams.put("diseaseId", diseaseId);
        return this.executeSQL(sql, queryParams);
    }

    /**
     * 
     * 根据机构ID用户ID获取diseaseCode
     * 
     * @author scd
     * @param insId
     * @param userId
     * @return
     */
    public String getDiseaseCode(String insId, String userId) {
        int size = insId.length() + userId.length();
        String sql = "SELECT MAX(SUBSTR(disease_code,INSTR(disease_code,'" + insId + userId + "')+"
                + size + ")) FROM user_disease_often"
                + "  WHERE create_ins_id =:insId AND create_user_id =:userId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("insId", insId);
        paramMap.put("userId", userId);
        return this.SQLQueryFirst(sql, paramMap);

    }
}

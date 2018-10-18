
package com.mnt.preg.master.dao.preg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.master.condition.items.IntervenePointsCondition;
import com.mnt.preg.master.pojo.items.InterveneDiseasePointsPojo;
import com.mnt.preg.master.pojo.preg.PregCourseDetailPojo;
import com.mnt.preg.master.pojo.preg.PregIntervenePointsPojo;

/**
 * 干预方案要点表DAO
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-4-7 gss 初版
 */
@Repository
public class PregIntervenePointsDAO extends HibernateTemplate {

    /**
     * 根据查询条件查询干预方案要点记录
     * 
     * @param condition
     *            查询条件
     * @return List<ItemVo> 疾病表信息列表
     */
    public List<PregIntervenePointsPojo> queryIntervenePoints(IntervenePointsCondition condition) {
        if (condition == null)
            condition = new IntervenePointsCondition();
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "PregIntervenePointsPojo");
        String querySQL = "SELECT " + DaoUtils.getSQLFields(PregIntervenePointsPojo.class, "PregIntervenePointsPojo")
                + "        FROM mas_intervene_points AS PregIntervenePointsPojo"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), PregIntervenePointsPojo.class);
    }

    /**
     * 
     * 根据pointId查询健康要点与疾病关联记录
     * <dl>
     * <dt>1 功能描述</dt>
     * <dd>根据pointId查询健康要点与疾病关联记录</dd>
     * </dl>
     * 
     * @author gss
     * @param pointId
     */
    public List<InterveneDiseasePointsPojo> queryInterveneDiseasePointsByPointId(String pointId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(InterveneDiseasePointsPojo.class, "InterveneDiseasePointsPojo")
                + "       ,InterveneDisease.disease_name AS diseaseName"
                + "   FROM mas_intervene_disease_point AS InterveneDiseasePointsPojo"
                + "       JOIN mas_intervene_disease AS InterveneDisease"
                + "           ON InterveneDiseasePointsPojo.disease_id=InterveneDisease.disease_id"
                + "   WHERE InterveneDiseasePointsPojo.point_id=:pointId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("pointId", pointId);
        return this.SQLQueryAlias(sql, paramMap, InterveneDiseasePointsPojo.class);
    }

    /**
     * 删除干预要点与疾病关联
     * 
     * @author zcq
     * @param pointId
     */
    public void deleteInterveneDiseasePoints(String pointId) {
        String sql = "DELETE FROM mas_intervene_disease_point WHERE point_id=:pointId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("pointId", pointId);
        this.executeSQL(sql, paramMap);
    }

    /**
     * 查询诊断项目课程
     * 
     * @author zcq
     * @param diseaseCodeList
     * @return
     */
    public List<PregCourseDetailPojo> queryDisesaseCourse(List<String> diseaseCodeList) {
        if (CollectionUtils.isEmpty(diseaseCodeList)) {
            return null;
        }
        String querySQL = "SELECT"
                + "            point.point_subclass AS pregDeName,"
                + "            point.point_desc AS courseDesc,"
                + "            disease.disease_name AS diseaseName"
                + "        FROM mas_intervene_points AS point"
                + "            JOIN mas_intervene_disease_point AS relation ON point.point_id=relation.point_id"
                + "                AND point.point_type=:pointType"
                + "            JOIN mas_intervene_disease AS disease ON disease.disease_id=relation.disease_id"
                + "        WHERE point.flag=:flag"
                + "                AND disease.disease_code IN (:diseaseCodeList)";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("diseaseCodeList", diseaseCodeList);
        paramMap.put("pointType", "30");
        paramMap.put("flag", 1);
        return this.SQLQueryAlias(querySQL, paramMap, PregCourseDetailPojo.class);
    }

    /**
     * 通过所选干预疾病查询健康要点信息
     * 
     * @author zcq
     * @param diseaseCodeList
     *            所选干预疾病列表
     * @param pointType
     *            要点大类
     * @return List<IntervenePointsVo>
     *         符合条件的健康要点
     */
    public List<PregIntervenePointsPojo> queryIntervenePointsByPlanDiseaseCodes(List<String> diseaseCodeList,
            String pointType, String pregStage) {
        List<PregIntervenePointsPojo> pointList = new ArrayList<PregIntervenePointsPojo>();
        // 第一部分：根据孕期查询
        if (StringUtils.isNotEmpty(pregStage)) {
            String pregSQL = "SELECT "
                    + DaoUtils.getSQLFields(PregIntervenePointsPojo.class, "PregIntervenePointsPojo")
                    + "       FROM mas_intervene_points AS PregIntervenePointsPojo "
                    + "       WHERE PregIntervenePointsPojo.preg_stage LIKE:pregStage"
                    + "           AND PregIntervenePointsPojo.point_type=:pointType"
                    + "           AND PregIntervenePointsPojo.flag=:flag";
            Map<String, Object> paramsMap = new HashMap<String, Object>();
            paramsMap.put("pointType", pointType);
            paramsMap.put("pregStage", pregStage);
            paramsMap.put("flag", 1);
            List<PregIntervenePointsPojo> pregList = this.SQLQueryAlias(pregSQL, paramsMap,
                    PregIntervenePointsPojo.class);
            if (CollectionUtils.isNotEmpty(pregList)) {
                pointList.addAll(pregList);
            }
        }
        // 第二部分：根据疾病查询
        if (CollectionUtils.isNotEmpty(diseaseCodeList)) {
            String diseSQL = "SELECT DISTINCT"
                    + DaoUtils.getSQLFields(PregIntervenePointsPojo.class, "PregIntervenePointsPojo")
                    + "       FROM mas_intervene_points AS PregIntervenePointsPojo "
                    + "           JOIN mas_intervene_disease_point AS relation"
                    + "               ON PregIntervenePointsPojo.point_id=relation.point_id"
                    + "           LEFT JOIN mas_intervene_disease AS masInterveneDisease ON masInterveneDisease.disease_id=relation.disease_id"
                    + "       WHERE (PregIntervenePointsPojo.preg_stage = '' OR ISNULL(PregIntervenePointsPojo.preg_stage))"
                    + "           AND masInterveneDisease.disease_code IN (:diseaseCodeList)"
                    + "           AND PregIntervenePointsPojo.point_type=:pointType"
                    + "           AND PregIntervenePointsPojo.flag=:flag";
            Map<String, Object> paramsMap = new HashMap<String, Object>();
            paramsMap.put("diseaseCodeList", diseaseCodeList);
            paramsMap.put("pointType", pointType);
            paramsMap.put("flag", 1);
            List<PregIntervenePointsPojo> diseList = this.SQLQueryAlias(diseSQL, paramsMap,
                    PregIntervenePointsPojo.class);
            if (CollectionUtils.isNotEmpty(diseList)) {
                pointList.addAll(diseList);
            }
        }
        return pointList;
    }
}

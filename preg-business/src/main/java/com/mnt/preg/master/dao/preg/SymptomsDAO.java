
package com.mnt.preg.master.dao.preg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.main.enums.Flag;
import com.mnt.preg.master.condition.preg.SymptomsCondition;
import com.mnt.preg.master.pojo.preg.SymptomsPojo;

/**
 * 功能症状DAO
 * 
 * @author dhs
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-22 zcq 初版
 */
@Repository
public class SymptomsDAO extends HibernateTemplate {

    /**
     * 条件检索功能症状信息
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public List<SymptomsPojo> querySymptoms(SymptomsCondition condition) {
        if (condition == null) {
            condition = new SymptomsCondition();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "SymptomsPojo");
        // 设置性别过滤条件
        if (StringUtils.isNotEmpty(condition.getSympSex())) {
            queryCondition
                    .setQueryString(queryCondition.getQueryString()
                            + " AND (SymptomsPojo.symp_sex=:sympSex OR ISNULL(SymptomsPojo.symp_sex) OR SymptomsPojo.symp_sex='')");
            queryCondition.getQueryParams().put("sympSex", condition.getSympSex());
        }
        String sql = "SELECT " + DaoUtils.getSQLFields(SymptomsPojo.class, "SymptomsPojo")
                + "   FROM mas_symptoms AS SymptomsPojo"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), SymptomsPojo.class);
    }

    /**
     * 根据id查询检查项目
     * 
     * @param inspectId
     *            主键
     * @return SymptomsPojo
     */
    public SymptomsPojo getSymptomsBySymptomsId(String symptomsId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(SymptomsPojo.class, "SymptomsPojo")
                + "   FROM mas_symptoms AS SymptomsPojo"
                + "   WHERE SymptomsPojo.symp_code=:symptomsId AND flag= :flag";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("symptomsId", symptomsId);
        paramMap.put("flag", Flag.normal.getValue());
        return this.SQLQueryAliasFirst(sql, paramMap, SymptomsPojo.class);
    }

    /**
     * 
     * 根据元素Id物理删除功能症状明细
     * 
     * @author dhs
     * @param symptomsId
     */
    public void removeSymptomsBySymptomsId(String symptomsId) {
        String sql = "UPDATE mas_symptoms SET flag= :flag where symp_code = :symptomsId";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("symptomsId", symptomsId);
        params.put("flag", Flag.deleted.getValue());

        this.executeSQL(sql, params);
    }
}

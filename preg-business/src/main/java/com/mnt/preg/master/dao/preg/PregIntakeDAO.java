
package com.mnt.preg.master.dao.preg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.master.condition.preg.IntakeCondition;
import com.mnt.preg.master.pojo.preg.PregIntakeDetailPojo;
import com.mnt.preg.master.pojo.preg.PregIntakePojo;

/**
 * 膳食干预方案-膳食模板DAO
 * 
 * @author wsy
 * @version 1.0
 * 
 *          变更履历： v1.0 2017-3-22 wsy 初版
 */
@Repository
public class PregIntakeDAO extends HibernateTemplate {

    /**
     * 分页检索膳食模版
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public List<PregIntakePojo> queryIntake(IntakeCondition condition) {
        if (condition == null) {
            condition = new IntakeCondition();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "PregIntakePojo");
        // 模板分为：系统 和 个人
        String addSQL = "";
        if (StringUtils.isNotEmpty(condition.getDataType())) {
            addSQL = " AND PregIntakePojo.dataType=:dataType";
            queryCondition.getQueryParams().put("dataType", condition.getDataType());
            if (StringUtils.isNotEmpty(condition.getCreateUserId())) {
                addSQL = " AND (PregIntakePojo.data_type=:dataType OR PregIntakePojo.create_user_id=:createUserId)";
                queryCondition.getQueryParams().put("createUserId", condition.getCreateUserId());
            }
        }
        queryCondition.setQueryString(queryCondition.getQueryString() + addSQL);

        String querySQL = "SELECT " + DaoUtils.getSQLFields(PregIntakePojo.class, "PregIntakePojo")
                + "            ,codeVo.code_name AS intakeModeName"
                + "            ,user.user_name as createUserName "
                + "        FROM mas_intake AS PregIntakePojo"
                + "            LEFT JOIN mas_code_info AS codeVo ON codeVo.code_value=PregIntakePojo.intake_mode"
                + "                AND codeVo.code_kind='INTAKE_MODE'"
                + "            LEFT JOIN sys_user AS user ON PregIntakePojo.create_user_id = user.user_id "
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();

        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), PregIntakePojo.class);
    }

    /**
     * 删除膳食执行清单
     * 
     * @author gss
     * @param intakeId
     */
    public void removeIntakeDetailByIntakeId(String intakeId) {
        String sql = "DELETE FROM mas_intake_detail WHERE intake_id=:intakeId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("intakeId", intakeId);
        this.executeSQL(sql, paramMap);
    }

    /**
     * 删除膳食模版饮食清单
     * 
     * @author zcq
     * @param intakeId
     */
    public void deleteIntakeDetails(String intakeId) {
        String sql = "DELETE FROM mas_intake_detail WHERE intake_id=:intakeId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("intakeId", intakeId);
        this.executeSQL(sql, paramMap);
    }

    /**
     * 查询膳食模版明细
     * 
     * @author zcq
     * @param intakeId
     * @return List<BusIntakeVo>
     */
    public List<PregIntakeDetailPojo> queryIntakeDetailByIntakeId(String intakeId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(PregIntakeDetailPojo.class, "IntakeDetailPojo")
                + "   FROM mas_intake_detail AS IntakeDetailPojo"
                + "       LEFT JOIN mas_intake_type AS intakeType ON intakeType.code=IntakeDetailPojo.intake_type"
                + "       LEFT JOIN mas_code_info AS codeVo ON codeVo.code_value=IntakeDetailPojo.intake_mealtype"
                + "           AND codeVo.code_kind='MEALSTYPE'"
                + "   WHERE IntakeDetailPojo.intake_id=:intakeId"
                + "        AND IntakeDetailPojo.flag=:flag"
                + "   ORDER BY IntakeDetailPojo.intake_mealtype ASC";
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("intakeId", intakeId);
        paramsMap.put("flag", 1);
        return this.SQLQueryAlias(sql, paramsMap, PregIntakeDetailPojo.class);
    }

}

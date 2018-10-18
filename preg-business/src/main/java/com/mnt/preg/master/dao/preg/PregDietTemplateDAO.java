
package com.mnt.preg.master.dao.preg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.mnt.health.core.exception.ServiceException;
import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.main.results.ResultCode;
import com.mnt.preg.master.condition.preg.DietTemplateCondition;
import com.mnt.preg.master.condition.preg.DietTemplateDetailCondition;
import com.mnt.preg.master.pojo.preg.PregDietTemplateDetailPojo;
import com.mnt.preg.master.pojo.preg.PregDietTemplatePojo;

/**
 * 膳食干预方案-食谱模板DAO
 * 
 * @author wsy
 * @version 1.0
 * 
 *          变更履历： v1.0 2017-3-22 wsy 初版
 */
@Repository
public class PregDietTemplateDAO extends HibernateTemplate {

    /**
     * 查询食谱模板
     * 
     * @author xdc
     * @param condition
     * @return
     */
    public List<PregDietTemplatePojo> queryDietTemplate(DietTemplateCondition condition) {
        if (condition == null) {
            condition = new DietTemplateCondition();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "PregDietTemplatePojo");
        String querySQL = "SELECT "
                + DaoUtils.getSQLFields(PregDietTemplatePojo.class, "PregDietTemplatePojo")
                + "            ,user.user_name as createUserName"
                + "        FROM mas_diet_template AS PregDietTemplatePojo"
                + "             LEFT JOIN sys_user AS user ON PregDietTemplatePojo.create_user_id = user.user_id "
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();

        /*
         * String countSQL = "SELECT COUNT(PregDietTemplatePojo.diet_Id)"
         * + "        FROM mas_diet_template AS PregDietTemplatePojo"
         * + queryCondition.getQueryString();
         */
        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), PregDietTemplatePojo.class);
    }

    /**
     * 获取去模板信息
     * 
     * @author xdc
     * @param dietId
     *            模板Id
     * @return
     */
    public PregDietTemplatePojo getDietTemplateByDietId(String dietId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(PregDietTemplatePojo.class, "DietTemplateVo")
                + "   ,user.user_name as createUserName "
                + "   FROM mas_diet_template AS DietTemplateVo"
                + "        LEFT JOIN sys_user AS user ON DietTemplateVo.create_user_id = user.user_id "
                + "   WHERE DietTemplateVo.diet_id= :dietId"
                + "        AND DietTemplateVo.flag= :flag"
                + "        AND user.user_status= :status";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("dietId", dietId);
        queryParams.put("flag", 1);
        queryParams.put("status", "1");
        return this.SQLQueryAliasFirst(sql, queryParams, PregDietTemplatePojo.class);
    }

    /**
     * 根据模板id查询模板下分类的名称
     * 
     * @author xdc
     * @param dietId
     *            模板ID
     * @return
     */
    public List<PregDietTemplateDetailPojo> queryDietTemplateDetailNamesbyTemplateId(String dietId) {
        if (StringUtils.isEmpty(dietId)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        String sql = "SELECT "
                + "       id AS id,"
                + "       food_day AS foodDay"
                + "   FROM mas_diet_template_detail "
                + "   WHERE diet_id= :dietId "
                + "         AND flag= :flag"
                + "   GROUP BY food_day";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("dietId", dietId);
        paramMap.put("flag", 1);
        List<PregDietTemplateDetailPojo> list = this.SQLQueryAlias(sql, paramMap, PregDietTemplateDetailPojo.class);
        return list;
    }

    /**
     * 检索食物种类明细表
     * 
     * @author xdc
     * @param condition
     *            包含Id和Name
     * @return
     */
    public List<PregDietTemplateDetailPojo> queryDietTemplateDetail(DietTemplateDetailCondition condition) {
        if (condition == null)
            condition = new DietTemplateDetailCondition();
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "DietTemplateDetailVo");
        String sql = "SELECT " + DaoUtils.getSQLFields(PregDietTemplateDetailPojo.class, "DietTemplateDetailVo")
                + "       ,codeVo.code_name AS foodMealName"
                + "   FROM mas_diet_template_detail AS DietTemplateDetailVo"
                + "       LEFT JOIN mas_code_info AS codeVo ON codeVo.code_value=DietTemplateDetailVo.food_meal"
                + "           AND codeVo.code_kind='MEALSTYPE'"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), PregDietTemplateDetailPojo.class);
    }

    /**
     * 移除foodDay
     * 
     * @author xdc
     * @param condition
     *            包含Id和Name
     * @return
     */
    public void removeFoodDayByCondition(String dietId, String foodDay) {
        String sql = "UPDATE mas_diet_template_detail AS DietTemplateDetailVo SET flag= :flag "
                + "   WHERE diet_id= :dietId AND food_day= :foodDay";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("flag", 0);
        paramMap.put("dietId", dietId);
        paramMap.put("foodDay", foodDay);
        this.executeSQL(sql, paramMap);
    }
}

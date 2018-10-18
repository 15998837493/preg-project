/*
 * FoodDAO.java	 1.0   2015-1-15
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.master.dao.basic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.mnt.health.core.annotation.AnnotationUtil;
import com.mnt.health.core.exception.ServiceException;
import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.examitem.condition.FoodCondition;
import com.mnt.preg.examitem.pojo.EvaluateFoodLibraryPojo;
import com.mnt.preg.examitem.pojo.FoodBasePojo;
import com.mnt.preg.main.enums.Flag;
import com.mnt.preg.main.results.ResultCode;
import com.mnt.preg.master.entity.basic.FoodMaterialList;
import com.mnt.preg.master.pojo.basic.FoodPojo;

/**
 * 食物DAO
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2015-1-15 zy 初版
 */
@Repository
public class FoodDAO extends HibernateTemplate {

    /**
     * 检索食物基础信息
     * 
     * @param condition
     *            检索条件
     * @return List<FoodVo>
     */
    public List<FoodPojo> queryFood(FoodCondition condition) {
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "FoodPojo");
        String addQueryString = "";
        // 设置食物名称
        String foodName = condition.getFoodName();
        if (!StringUtils.isEmpty(foodName)) {
            addQueryString = " AND (FoodPojo.food_name LIKE :foodName OR FoodPojo.food_alias LIKE :foodName OR FoodPojo.food_aspell LIKE :foodName OR FoodPojo.food_sspell LIKE :foodName)";
            queryCondition.getQueryParams().put("foodName", "%" + foodName + "%");
        }
        // 追加检索条件
        queryCondition.setQueryString(queryCondition.getQueryString() + addQueryString);

        String querySQL = "SELECT" + DaoUtils.getSQLFields(FoodPojo.class, "FoodPojo")
                + "        FROM mas_food AS FoodPojo"
                + queryCondition.getQueryString()
                + "        ORDER BY LENGTH(food_name) ";
        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), FoodPojo.class);
    }

    /**
     * 食物库分页查询
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public FoodCondition queryFoodForPage(FoodCondition condition) {
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "FoodPojo");
        String addQueryString = "";
        // 设置食物名称
        String foodName = condition.getFoodName();
        if (!StringUtils.isEmpty(foodName)) {
            addQueryString = " AND (FoodPojo.food_name LIKE :foodName OR FoodPojo.food_alias LIKE :foodName OR FoodPojo.food_aspell LIKE :foodName OR FoodPojo.food_sspell LIKE :foodName)";
            queryCondition.getQueryParams().put("foodName", "%" + foodName + "%");
        }
        // 追加检索条件
        queryCondition.setQueryString(queryCondition.getQueryString() + addQueryString);

        String querySQL = "SELECT" + DaoUtils.getSQLFields(FoodPojo.class, "FoodPojo")
                + "        FROM mas_food AS FoodPojo "
                + queryCondition.getQueryString()
                + "        ORDER BY LENGTH(food_name) ";
        String countSQL = "SELECT COUNT(FoodPojo.food_id) FROM mas_food AS FoodPojo "
                + queryCondition.getQueryString();

        return (FoodCondition) this.queryForPage(condition, querySQL, countSQL, queryCondition.getQueryParams(),
                FoodPojo.class);
    }

    /**
     * 检索食物元素信息  (待删除)
     * 
     * @param condition
     *            检索条件
     * @return List<FoodExtVo>
     */
//    public List<FoodExtPojo> queryFoodExt(FoodInfo condition) {
//        if (condition == null) {
//            condition = new FoodInfo();
//        }
//        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "FoodExtVo");
//        String querySQL = "SELECT " + DaoUtils.getSQLFields(FoodExtPojo.class, "FoodExtVo")
//                + "        FROM mas_food_ext AS FoodExtVo"
//                + queryCondition.getQueryString()
//                + queryCondition.getOrderString();
//        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), FoodExtPojo.class);
//    }

    /**
     * 检索食物基本信息
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public FoodCondition queryFoodBaseForPage(FoodCondition condition) {
        
        if (condition == null) {
            condition = new FoodCondition();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "FoodBasePojo");
        StringBuffer addQueryString = new StringBuffer("");
        // 设置食物名称
        String foodName = condition.getFoodName();
        if (!StringUtils.isEmpty(foodName)) {
            addQueryString
                    .append(" AND (FoodBasePojo.food_name LIKE :foodName OR FoodBasePojo.food_alias LIKE :foodName OR FoodBasePojo.food_aspell LIKE :foodName OR FoodBasePojo.food_sspell LIKE :foodName)");
            queryCondition.getQueryParams().put("foodName", "%" + foodName + "%");
        }
        // 设置食材名称
        String fmName = condition.getFmName();
        if (!StringUtils.isEmpty(fmName)) {
            addQueryString
                    .append(" AND (FoodBasePojo.fm_name LIKE :fmName OR FoodBasePojo.fm_alias LIKE :fmName OR FoodBasePojo.fm_aspell LIKE :fmName OR FoodBasePojo.fm_sspell LIKE :fmName)");
            queryCondition.getQueryParams().put("fmName", "%" + fmName + "%");
        }
        // 追加检索条件
        queryCondition.setQueryString(queryCondition.getQueryString() + addQueryString.toString());

        String querySQL = "SELECT FoodBasePojo.food_id AS foodId, FoodBasePojo.food_name AS foodName, FoodBasePojo.food_pic AS foodPic, FoodBasePojo.E00001 AS foodEnergy, "+ 
        "e2.E00002 AS foodProtid, e3.E00003 AS foodFat, e4.E00004 AS foodCbr FROM "+
        "(SELECT food_1.food_id , food_1.food_name, food_1.food_pic , food_1.food_meal_type, food_1.flag, food_1.food_cuisine, fm_1.fm_name, fm_1.fm_alias, fm_1.fm_aspell,"
        + " food_1.food_sspell, food_1.food_alias, food_1.food_aspell, fm_1.fm_sspell, "+
        "ROUND(SUM(list_1.fml_amount * fm_1.fm_esculent * nutrient_1.nutrient_dosage / 100) / SUM(list_1.fml_amount),    1) AS E00001 "+
        "FROM (SELECT food_id, food_name, food_pic, food_meal_type, flag, food_cuisine, food_sspell, food_alias, food_aspell FROM mas_food WHERE food_id IN "
        + "(SELECT food_id FROM mas_food WHERE flag = '1')) food_1 "+
        "LEFT JOIN mas_food_material_list list_1 ON food_1.food_id = list_1.food_id LEFT JOIN mas_food_material fm_1 ON list_1.fm_id = fm_1.fm_id "+
        "LEFT JOIN (SELECT corres_id, nutrient_id, nutrient_dosage FROM mas_nutrient_amount WHERE corres_type = 'foodMaterial' AND nutrient_id = 'E00001' "+
        "AND corres_id IN (SELECT DISTINCT fm_id FROM mas_food_material_list WHERE food_id IN (SELECT food_id FROM mas_food WHERE flag = '1')) "+
        ") nutrient_1 ON fm_1.fm_id = nutrient_1.corres_id GROUP BY food_1.food_id) FoodBasePojo "+
        "JOIN ( "+
        "    SELECT food_1.food_id, food_1.food_name, food_1.food_pic, "+ 
        "ROUND(SUM(list_1.fml_amount * fm_1.fm_esculent * nutrient_1.nutrient_dosage / 100) / SUM(list_1.fml_amount),    1) AS E00002 "+
        "FROM (SELECT food_id, food_name, food_pic FROM mas_food WHERE food_id IN (SELECT food_id FROM mas_food WHERE flag = '1')) food_1 "+
        "LEFT JOIN mas_food_material_list list_1 ON food_1.food_id = list_1.food_id LEFT JOIN mas_food_material fm_1 ON list_1.fm_id = fm_1.fm_id "+
        "LEFT JOIN (SELECT corres_id, nutrient_id, nutrient_dosage FROM mas_nutrient_amount WHERE corres_type = 'foodMaterial' AND nutrient_id = 'E00002' "+
        "AND corres_id IN (SELECT DISTINCT fm_id FROM mas_food_material_list WHERE food_id IN (SELECT food_id FROM mas_food WHERE flag = '1')) "+
        ") nutrient_1 ON fm_1.fm_id = nutrient_1.corres_id GROUP BY food_1.food_id) e2 ON FoodBasePojo.food_id = e2.food_id "+
        "JOIN ( "+
            "SELECT food_1.food_id, food_1.food_name, food_1.food_pic, "+ 
        "ROUND(SUM(list_1.fml_amount * fm_1.fm_esculent * nutrient_1.nutrient_dosage / 100) / SUM(list_1.fml_amount),    1) AS E00003 "+
        "FROM (SELECT food_id, food_name, food_pic FROM mas_food WHERE food_id IN (SELECT food_id FROM mas_food WHERE flag = '1')) food_1 "+
        "LEFT JOIN mas_food_material_list list_1 ON food_1.food_id = list_1.food_id LEFT JOIN mas_food_material fm_1 ON list_1.fm_id = fm_1.fm_id "+
        "LEFT JOIN (SELECT corres_id, nutrient_id, nutrient_dosage FROM mas_nutrient_amount WHERE corres_type = 'foodMaterial' AND nutrient_id = 'E00003' "+
        "AND corres_id IN (SELECT DISTINCT fm_id FROM mas_food_material_list WHERE food_id IN (SELECT food_id FROM mas_food WHERE flag = '1')) "+
        ") nutrient_1 ON fm_1.fm_id = nutrient_1.corres_id GROUP BY food_1.food_id) e3 ON e2.food_id = e3.food_id "+
        "JOIN ( "+
            "SELECT food_1.food_id, food_1.food_name, food_1.food_pic, "+ 
        "ROUND(SUM(list_1.fml_amount * fm_1.fm_esculent * nutrient_1.nutrient_dosage / 100) / SUM(list_1.fml_amount),    1) AS E00004 "+
        "FROM (SELECT food_id, food_name, food_pic FROM mas_food WHERE food_id IN (SELECT food_id FROM mas_food WHERE flag = '1')) food_1 "+
        "LEFT JOIN mas_food_material_list list_1 ON food_1.food_id = list_1.food_id LEFT JOIN mas_food_material fm_1 ON list_1.fm_id = fm_1.fm_id "+
        "LEFT JOIN (SELECT corres_id, nutrient_id, nutrient_dosage FROM mas_nutrient_amount WHERE corres_type = 'foodMaterial' AND nutrient_id = 'E00004' "+
        "AND corres_id IN (SELECT DISTINCT fm_id FROM mas_food_material_list WHERE food_id IN (SELECT food_id FROM mas_food WHERE flag = '1')) "+
        ") nutrient_1 ON fm_1.fm_id = nutrient_1.corres_id GROUP BY food_1.food_id) e4 ON e3.food_id = e4.food_id" + queryCondition.getQueryString();
        
        String countSQL = 
        "SELECT count(FoodBasePojo.food_id) FROM "+
        "(SELECT food_1.food_id , food_1.food_name, food_1.food_pic , food_1.food_meal_type, food_1.flag, food_1.food_cuisine, fm_1.fm_name, fm_1.fm_alias,"
        + " fm_1.fm_aspell, food_1.food_sspell, food_1.food_alias, food_1.food_aspell, fm_1.fm_sspell, "+ 
        "ROUND(SUM(list_1.fml_amount * fm_1.fm_esculent * nutrient_1.nutrient_dosage / 100) / SUM(list_1.fml_amount),    1) AS E00001 "+
        "FROM (SELECT food_id, food_name, food_pic, food_meal_type, flag, food_cuisine, food_sspell, food_alias, food_aspell FROM mas_food WHERE food_id"
        + " IN (SELECT food_id FROM mas_food WHERE flag = '1')) food_1 "+
        "LEFT JOIN mas_food_material_list list_1 ON food_1.food_id = list_1.food_id LEFT JOIN mas_food_material fm_1 ON list_1.fm_id = fm_1.fm_id "+
        "LEFT JOIN (SELECT corres_id, nutrient_id, nutrient_dosage FROM mas_nutrient_amount WHERE corres_type = 'foodMaterial' AND nutrient_id = 'E00001' "+
        "AND corres_id IN (SELECT DISTINCT fm_id FROM mas_food_material_list WHERE food_id IN (SELECT food_id FROM mas_food WHERE flag = '1')) "+
        ") nutrient_1 ON fm_1.fm_id = nutrient_1.corres_id GROUP BY food_1.food_id) FoodBasePojo" + queryCondition.getQueryString();
                        
        return (FoodCondition) this.queryForPage(condition, querySQL, countSQL, queryCondition.getQueryParams(),
                FoodBasePojo.class);
    }

    /**
     * 通过食物ID删除食物的组成
     * 
     * @param foodId
     * @return Integer
     * 
     */
    public Integer deleteFmlByFoodId(String foodId) {
        String hql = "DELETE FROM " + FoodMaterialList.class.getName() + " WHERE foodId=:foodId";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("foodId", foodId);
        return this.executeHQL(hql, queryParams);
    }

    /**
     * 修改食物应用次数
     * 
     * @author zcq
     * @param foodId
     * @return
     */
    public Integer updateFoodUseRecord(String foodId) {
        String sql = "UPDATE mas_food SET food_counts=food_counts+1 WHERE food_id=:foodId";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("foodId", foodId);
        return this.executeSQL(sql, queryParams);
    }

    /**
     * 
     * 查询评估食物库信息
     * 
     * @author mnt_zhangjing
     * @return
     */
    public List<EvaluateFoodLibraryPojo> queryEvaluateFoodLibrary() {
        String sql = "SELECT " + DaoUtils.getSQLFields(EvaluateFoodLibraryPojo.class, "EvaluateFoodLibraryPojo")
                + " FROM mas_evaluate_food_library AS EvaluateFoodLibraryPojo"
                + " WHERE flag= :flag";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("flag", Flag.normal.getValue());
        return this.SQLQueryAlias(sql, queryParams, EvaluateFoodLibraryPojo.class);
    }
    
    /**
     * 检验编码是否重复
     * 
     * @author zcq
     * @param fieldName
     * @param fieldValue
     * @param clazz
     * @return
     */
    public int countField(String fieldName, String fieldValue, Class<?> clazz) {
        if (StringUtils.isEmpty(fieldName) || StringUtils.isEmpty(fieldValue)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        String sql = "SELECT COUNT(flag) "
                + "   FROM " + AnnotationUtil.getTableName(clazz)
                + "   WHERE " + DaoUtils.nameFieldToColumn(fieldName) + "=:fieldValue AND flag= :flag";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("fieldValue", fieldValue);
        paramMap.put("flag", Flag.normal.getValue());

        return this.SQLCount(sql, paramMap);
    }
}

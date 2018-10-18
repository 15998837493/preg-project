/*
 * FoodMaterialDAO.java    1.0  2018-1-25
 *
 * 北京麦芽健康管理有限公司
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
import com.mnt.preg.main.enums.Flag;
import com.mnt.preg.main.results.ResultCode;
import com.mnt.preg.master.condition.basic.FoodMaterialCondition;
import com.mnt.preg.master.entity.basic.FoodMaterial;
import com.mnt.preg.master.entity.basic.FoodMaterialExt;
import com.mnt.preg.master.pojo.basic.FoodMaterialListInfoPojo;
import com.mnt.preg.master.pojo.basic.FoodMaterialPojo;
import com.mnt.preg.master.pojo.basic.NutrientAmountPojo;

/**
 * 食材DAO
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-25 scd 初版
 */
@Repository
public class FoodMaterialDAO extends HibernateTemplate {

    /**
     * 
     * 检索食材信息(分页)
     * 
     * @author scd
     * @param condition
     * @return
     */
    public FoodMaterialCondition queryFoodMaterialByPage(FoodMaterialCondition condition) {
        String fmName = "";
        String addQueryString = "";
        if (!StringUtils.isEmpty(condition.getFmName())) {
            fmName = condition.getFmName();
            condition.setFmName(null);
            addQueryString = " AND (FoodMaterialPojo.fm_name LIKE :fmName OR FoodMaterialPojo.fm_alias LIKE :fmName)";
        }

        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "FoodMaterialPojo");
        if (!StringUtils.isEmpty(fmName)) {
            queryCondition.getQueryParams().put("fmName", "%" + fmName + "%");
            queryCondition.setQueryString(queryCondition.getQueryString() + addQueryString);
        }

        String querySQL = "SELECT" + DaoUtils.getSQLFields(FoodMaterialPojo.class, "FoodMaterialPojo")
                + "  , tree.tree_name AS treeName       FROM mas_food_material AS FoodMaterialPojo"
                + "        LEFT JOIN mas_food_tree AS tree ON FoodMaterialPojo.fm_type = tree.tree_id AND tree.tree_kind = 'food_material'"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();

        String countSQL = "SELECT COUNT(FoodMaterialPojo.fm_id) FROM mas_food_material AS FoodMaterialPojo"
                + queryCondition.getQueryString();
        return (FoodMaterialCondition) this.queryForPage(condition, querySQL, countSQL,
                queryCondition.getQueryParams(),
                FoodMaterialPojo.class);
    }

    /**
     * 查询食物的组成
     * 
     * @param foodId
     * @return List<FoodMaterialListInfoVo>
     * 
     */
    public List<FoodMaterialListInfoPojo> queryFoodMaterialListByFoodId(String foodId) {
        String sql = "SELECT"
                + DaoUtils.getSQLFields(FoodMaterialListInfoPojo.class, "fml")
                + ", fm.fm_name AS fmName"
                + " FROM mas_food_material_list AS fml JOIN mas_food_material as fm ON fml.fm_id=fm.fm_id AND fml.food_id=:foodId";
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("foodId", foodId);
        return this.SQLQueryAlias(sql, queryMap, FoodMaterialListInfoPojo.class);
    }

    /**
     * 检索食材信息
     * 
     * @param condition
     * @return List<FoodMaterialVo>
     * 
     */
    public List<FoodMaterialPojo> queryFoodMaterial(FoodMaterial condition) {
        String fmName = "";
        String addQueryString = "";
        if (!StringUtils.isEmpty(condition.getFmName())) {
            fmName = condition.getFmName();
            condition.setFmName(null);
            addQueryString = " AND (FoodMaterial.fm_name LIKE :fmName OR FoodMaterial.fm_alias LIKE :fmName)";
        }

        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "FoodMaterialPojo");
        queryCondition.getQueryParams().put("flag", condition.getFlag());
        if (!StringUtils.isEmpty(fmName)) {
            queryCondition.getQueryParams().put("fmName", "%" + fmName + "%");
            queryCondition.setQueryString(queryCondition.getQueryString() + addQueryString);
        }
        String querySQL = "SELECT" + DaoUtils.getSQLFields(FoodMaterialPojo.class, "FoodMaterialPojo")
                + "        FROM mas_food_material AS FoodMaterialPojo"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), FoodMaterialPojo.class);
    }
    
    /**
     * 
     * 检索相同类型下的食材信息
     *
     * @author zhang_jing
     * @param fmType
     * @return
     */
    public List<FoodMaterialPojo> queryFoodMaterialByType(String fmType) {
        String querySQL = "SELECT" + DaoUtils.getSQLFields(FoodMaterialPojo.class, "FoodMaterialPojo")
                        + " FROM mas_food_material AS FoodMaterialPojo"
                        + " WHERE FoodMaterialPojo.fm_type LIKE :fmType AND FoodMaterialPojo.flag= :flag";
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("fmType", fmType+"%");
        queryMap.put("flag", Flag.normal.getValue());
        return this.SQLQueryAlias(querySQL, queryMap, FoodMaterialPojo.class);
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
    
    /**
     * 查询食材对应的所有元素
     * @param fmId
     * @return
     */
    public List<NutrientAmountPojo> queryElements(String fmId) {
        if (StringUtils.isEmpty(fmId) || StringUtils.isEmpty(fmId)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        String querySQL = "SELECT " + DaoUtils.getSQLFields(NutrientAmountPojo.class, "NutrientAmountPojo") 
        		 + ",nutrient.nutrient_name AS nutrientName,nutrient.nutrient_unit AS nutrientUnit FROM mas_nutrient_amount AS NutrientAmountPojo LEFT JOIN mas_nutrient nutrient On NutrientAmountPojo.nutrient_id = nutrient.nutrient_id"
                 + " WHERE NutrientAmountPojo.corres_id = :fmId AND NutrientAmountPojo.flag= :flag";
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("fmId", fmId);
        queryMap.put("flag", Flag.normal.getValue());
        return this.SQLQueryAlias(querySQL, queryMap, NutrientAmountPojo.class);
    }
    
    /**
     * 查询原食材对应所有元素表数据（导数据）
     * @return
     */
    public List<FoodMaterialExt> queryFoodMaterialExt() {
        String querySQL = "SELECT " + DaoUtils.getSQLFields(FoodMaterialExt.class, "pojo") 
		 + " FROM mas_food_material_ext AS pojo";
		return this.SQLQueryAlias(querySQL, FoodMaterialExt.class);
    }
    
    /**
     * 
     * 通过食材名称找食材id
     *
     * @author zj
     * @param fmName
     * @return
     */
    public String queryFmId(String fmName) {
        String sql = "SELECT fm_id FROM mas_food_material fm WHERE fm.fm_name = :fmName ";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("fmName", fmName);
        return this.SQLQueryFirst(sql, queryParams);
    }

}


package com.mnt.preg.master.dao.basic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.main.enums.Flag;
import com.mnt.preg.master.entity.basic.Element;
import com.mnt.preg.master.pojo.basic.ElementPojo;

/**
 * 元素表DAO
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-4-7 gss 初版
 */
@Repository
public class ElementDAO extends HibernateTemplate {

    /**
     * 根据id查询检查项目
     * 
     * @param inspectId
     *            主键
     * @return ElementPojo
     */
    public ElementPojo getNutrientByNutrientId(String nutrientId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(ElementPojo.class, "ElementPojo")
                + "   FROM mas_nutrient AS ElementPojo"
                + "   WHERE ElementPojo.nutrient_id=:nutrientId AND flag= :flag";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("nutrientId", nutrientId);
        paramMap.put("flag", Flag.normal.getValue());
        return this.SQLQueryAliasFirst(sql, paramMap, ElementPojo.class);
    }

    /**
     * 
     * 根据查询条件查询元素表记录
     * 
     * @author zhangjing
     * @param condition
     * @return List<ElementPojo>
     */
    public List<ElementPojo> queryNutrient(Element element) {
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(element, "ElementPojo");
        String sql = "SELECT " + DaoUtils.getSQLFields(ElementPojo.class, "ElementPojo")
                + "       ,code.code_name AS nutrientUnitName"
                + "   FROM mas_nutrient AS ElementPojo"
                + "   LEFT JOIN mas_code_info AS code ON ElementPojo.nutrient_unit=code.code_value"
                + "       AND code.code_kind='PRODUCTUNIT'"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), ElementPojo.class);
    }

    /**
     * 
     * 根据元素Id物理删除课程信息明细
     * 
     * @author gss
     * @param pregId
     */
    public void removeProductElementByNutrientId(String nutrientId) {
        String sql = "UPDATE mas_nutrient_amount SET flag= :flag where nutrient_id = :nutrientId";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("nutrientId", nutrientId);
        params.put("flag", Flag.deleted);
        this.executeSQL(sql, params);
    }
}

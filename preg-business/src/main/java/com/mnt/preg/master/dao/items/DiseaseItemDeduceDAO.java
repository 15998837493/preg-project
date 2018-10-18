
package com.mnt.preg.master.dao.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.master.condition.items.DiseaseItemDeduceCondition;
import com.mnt.preg.master.condition.items.DiseaseItemDeduceGroupCondition;
import com.mnt.preg.master.pojo.items.DiseaseItemDeduceGroupPojo;
import com.mnt.preg.master.pojo.items.DiseaseItemDeducePojo;
import com.mnt.preg.master.pojo.items.ItemPojo;

/**
 * 指标推断疾病DAO
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-11-17 zcq 初版
 */
@Repository
public class DiseaseItemDeduceDAO extends HibernateTemplate {

    /**
     * 条件检索推断指标信息
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public List<DiseaseItemDeducePojo> queryDiseaseItemDeduce(DiseaseItemDeduceCondition condition) {
        if (condition == null) {
            condition = new DiseaseItemDeduceCondition();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "DiseaseItemDeducePojo");

        // 保证列表检验项目唯一性
        if (StringUtils.isBlank(condition.getItemId()) && CollectionUtils.isEmpty(condition.getItemIdList())) {
            queryCondition.setQueryString(queryCondition.getQueryString()
                    + " GROUP BY DiseaseItemDeducePojo.disease_id,DiseaseItemDeducePojo.item_id");
        }
        String querySQL = "SELECT "
                + DaoUtils.getSQLFields(DiseaseItemDeducePojo.class, "DiseaseItemDeducePojo")
                + "            ,item.item_code AS itemCode "
                + "            ,item.item_name AS itemName "
                + "            ,item.item_ref_value AS itemRefValue "
                + "            ,item.item_unit AS itemUnit "
                + "            ,item.item_classify AS itemClassify "
                + "            ,item.item_type AS itemType "
                + "        FROM mas_intervene_disease_item_deduce AS DiseaseItemDeducePojo"
                + "        JOIN mas_item AS item ON DiseaseItemDeducePojo.item_id=item.item_id"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();

        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), DiseaseItemDeducePojo.class);
    }

    /**
     * 获取推断指标信息
     * 
     * @author zcq
     * @param id
     * @return
     */
    public ItemPojo getDeduceItem(String id) {
        String querySQL = "SELECT " + DaoUtils.getSQLFields(ItemPojo.class, "ItemPojo")
                + "            ,itemDeduce.id AS id"
                + "        FROM mas_intervene_disease_item_deduce AS itemDeduce"
                + "        JOIN mas_item AS ItemPojo ON itemDeduce.item_id=ItemPojo.item_id"
                + "        WHERE itemDeduce.id=:id";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        return this.SQLQueryAliasFirst(querySQL, paramMap, ItemPojo.class);
    }

    /**
     * 根据主键删除推断指标
     * 
     * @author zcq
     * @param id
     */
    public void deleteDiseaseItemDeduceById(String id) {
        String sql = "DELETE FROM mas_intervene_disease_item_deduce WHERE id=:id";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        this.executeSQL(sql, paramMap);
    }

    /**
     * 删除疾病检测指标(诊断+检验)
     * 
     * @author mlq
     * @param diseaseId
     * @param itemId
     */
    public void deleteDiseaseItemDeduce(String diseaseId, String itemId) {
        String sql = "DELETE FROM mas_intervene_disease_item_deduce WHERE disease_id=:diseaseId AND item_id=:itemId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("diseaseId", diseaseId);
        paramMap.put("itemId", itemId);
        this.executeSQL(sql, paramMap);
    }

    /************************************************* 【指标推断组合】 **************************************************/

    /**
     * 检索推断指标组合列表
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public List<DiseaseItemDeduceGroupPojo> queryDiseaseItemDeduceGroup(DiseaseItemDeduceGroupCondition condition) {
        if (condition == null) {
            condition = new DiseaseItemDeduceGroupCondition();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "DiseaseItemDeduceGroupPojo");
        String sql = "SELECT " + DaoUtils.getSQLFields(DiseaseItemDeduceGroupPojo.class, "DiseaseItemDeduceGroupPojo")
                + "   FROM mas_intervene_disease_item_deduce_group AS DiseaseItemDeduceGroupPojo"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();

        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), DiseaseItemDeduceGroupPojo.class);
    }

    /**
     * 初步检索符合条件的组合
     * 
     * @author zcq
     * @param itemIdList
     * @return
     */
    public List<DiseaseItemDeduceGroupPojo> queryFitDiseaseItemDeduceGroup(List<String> itemIdList) {
        if (CollectionUtils.isEmpty(itemIdList)) {
            return null;
        }
        String fieldString = DaoUtils.getSQLFields(DiseaseItemDeduceGroupPojo.class, "DiseaseItemDeduceGroupPojo");
        StringBuilder unionSQL = new StringBuilder("");
        for (String itemId : itemIdList) {
            if (unionSQL.length() == 0) {
                unionSQL.append(" SELECT ").append(fieldString)
                        .append(" FROM mas_intervene_disease_item_deduce_group AS DiseaseItemDeduceGroupPojo")
                        .append(" WHERE item_ids LIKE '%").append(itemId).append("%'");
            } else {
                unionSQL.append(" UNION ")
                        .append(" SELECT ").append(fieldString)
                        .append(" FROM mas_intervene_disease_item_deduce_group AS DiseaseItemDeduceGroupPojo")
                        .append(" WHERE item_ids LIKE '%").append(itemId).append("%'");
            }
        }
        return this.SQLQueryAlias(unionSQL.toString(), null, DiseaseItemDeduceGroupPojo.class);
    }

    /**
     * 根据主键删除推断指标组合
     * 
     * @author zcq
     * @param id
     */
    public void deleteDiseaseItemDeduceGroupById(String id) {
        String sql = "DELETE FROM mas_intervene_disease_item_deduce_group WHERE id=:id";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        this.executeSQL(sql, paramMap);
    }

    /**
     * 根据指标主键删除推断指标组合
     * 
     * @author zcq
     * @param itemId
     */
    public void deleteDiseaseItemDeduceGroupByItemId(String itemId) {
        String sql = "DELETE FROM mas_intervene_disease_item_deduce_group WHERE item_ids LIKE:itemId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("itemId", "%" + itemId + "%");
        this.executeSQL(sql, paramMap);
    }

    /**
     * 根据标签名称删除推断指标组合
     * 
     * @author zcq
     * @param itemNames
     */
    public void deleteDiseaseItemDeduceGroupByitemNames(String itemNames, String diseaseId) {
        String sql = "DELETE FROM mas_intervene_disease_item_deduce_group WHERE item_names=:itemNames AND disease_id=:diseaseId ";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("itemNames", itemNames);
        paramMap.put("diseaseId", diseaseId);
        this.executeSQL(sql, paramMap);
    }

}

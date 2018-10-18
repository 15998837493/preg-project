
package com.mnt.preg.master.dao.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.core.utils.HQLSymbol;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.main.enums.Flag;
import com.mnt.preg.master.condition.items.ItemCondition;
import com.mnt.preg.master.entity.items.Item;
import com.mnt.preg.master.pojo.items.ItemPojo;

/**
 * 体检项目字典表DAO
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-4-7 gss 初版
 */
@Repository
public class ItemDAO extends HibernateTemplate {

    /**
     * 根据条件查询所有
     * 
     * @author xdc
     * @param condition
     * @return
     */
    public List<ItemPojo> queryItemByCondition(ItemCondition condition) {
        if (condition == null) {
            condition = new ItemCondition();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "ItemPojo");

        String sql = "SELECT " + DaoUtils.getSQLFields(ItemPojo.class, "ItemPojo")
                + "   FROM mas_item AS ItemPojo"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), ItemPojo.class);
    }

    /**
     * 根据指标编码查询指标异常
     * 
     * @author zcq
     * @param itemCodeList
     * @return
     */
    public List<String> queryItemAbnomalByItemCodes(List<String> itemCodeList) {
        if (CollectionUtils.isEmpty(itemCodeList)) {
            return null;
        }
        String querySQL = "SELECT item_abnormal FROM mas_item WHERE item_code IN(:itemCodeList) AND item_analyze=:itemAnalyze AND flag=:flag";

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("itemCodeList", itemCodeList);
        paramMap.put("itemAnalyze", "1");// 是否是分析指标 1=是
        paramMap.put("flag", Flag.normal.getValue());

        return this.SQLQuery(querySQL, paramMap);
    }

    /**
     * 
     * 更新体检项目字典表
     * 
     * @author gss
     * @param item
     * @param id
     *            主键
     */
    public void updateItem(Item item, String id) {
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("itemId", HQLSymbol.EQ.toString(), id));
        this.updateHQL(item, conditionParams);
    }

    /**
     * 
     * 逻辑删除体检项目字典记录
     * 
     * @author gss
     * @param id
     *            主键
     */
    public int deleteItem(String id) {
        String sql = "UPDATE mas_item SET flag= :flag WHERE item_id= :id";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("flag", 0);
        queryParams.put("id", id);
        return this.executeSQL(sql, queryParams);
    }

    /**
     * 根据查询条件查询体检项目字典表记录
     * 
     * @param condition
     *            查询条件
     * @return List<ItemPojo> 体检项目字典信息列表
     */
    public List<ItemPojo> queryItem(ItemCondition condition) {
        if (condition == null) {
            condition = new ItemCondition();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "ItemPojo");
        String diseaseCode = condition.getDiseaseCode();
        if (StringUtils.isNotEmpty(diseaseCode)) {
            queryCondition.getQueryParams().put("diseaseCode", diseaseCode);
        }
        String querySQL = "SELECT "
                + DaoUtils.getSQLFields(ItemPojo.class, "ItemPojo")
                + "        FROM mas_item AS ItemPojo "
                + (StringUtils.isEmpty(diseaseCode) ? ""
                        : "JOIN mas_intervene_disease_item AS relation ON ItemPojo.item_code=relation.item_code AND relation.disease_code =:diseaseCode")
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), ItemPojo.class);
    }

    /**
     * 根据查询条件查询系统体检项目字典表记录
     * 
     * @param itemCode
     *            机构项目编码
     * @return ItemPojo 系统体检项目字典信息
     */
    public ItemPojo getItemByItemCode(String itemCode) {
        String sql = "SELECT " + DaoUtils.getSQLFields(ItemPojo.class, "ItemPojo")
                + "   FROM mas_item AS ItemPojo"
                + "   WHERE ItemPojo.item_code=:itemCode";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("itemCode", itemCode);
        return this.SQLQueryAliasFirst(sql, paramMap, ItemPojo.class);
    }

    /**
     * 根据查询条件查询系统体检项目字典表记录
     * 
     * @param itemCode
     *            机构项目编码
     * @return ItemPojo 系统体检项目字典信息
     */
    public ItemPojo getItemByItemId(String itemId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(ItemPojo.class, "ItemPojo")
                + "   FROM mas_item AS ItemPojo"
                + "   WHERE ItemPojo.item_id=:itemId AND"
                + "         ItemPojo.flag =:flag";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("itemId", itemId);
        paramMap.put("flag", Flag.normal.getValue());
        return this.SQLQueryAliasFirst(sql, paramMap, ItemPojo.class);
    }

    /**
     * 生成编码
     * 
     * @author scd
     * @return
     */
    public String getMaxItemCode(String insId) {
        String sql = "SELECT MAX(SUBSTR(item_code,INSTR(item_code,'" + insId + "')+" + insId.length()
                + ")) FROM mas_item";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        return this.SQLQueryFirst(sql, paramMap);
    }

    /**
     * 
     * 批量删除指标
     * 
     * @author scd
     * @param itemIds
     *            主键
     */
    public int removeItems(List<String> itemIds) {
        String sql = "UPDATE mas_item SET flag= :flag WHERE item_id in (:itemIds)";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("flag", 0);
        queryParams.put("itemIds", itemIds);
        return this.executeSQL(sql, queryParams);
    }

    /**
     * 根据条件查询所有
     * 
     * @author xdc
     * @param condition
     * @return
     */
    public List<ItemPojo> queryItemType() {

        String sql = "SELECT DISTINCT ItemPojo.item_type as itemType, ItemPojo.item_classify AS itemClassify "
                + "   FROM mas_item AS ItemPojo"
                + "   WHERE ItemPojo.item_type IS NOT NULL "
                + "     AND ItemPojo.item_classify IS NOT NULL "
                + "     AND flag = 1";
        return this.SQLQueryAlias(sql, ItemPojo.class);
    }

    /**
     * 
     * 指标排序
     * 
     * @author xdc
     * @param menuId
     * @param menuOrder
     */
    public void updateItemOrder(String itemId, Integer itemOrder) {
        String executeSQL = "UPDATE mas_item SET item_order=:itemOrder WHERE item_id=:itemId";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("itemId", itemId);
        queryParams.put("itemOrder", itemOrder);
        this.executeSQL(executeSQL, queryParams);
    }

    /**
     * 更新指标类别
     * 
     * @author xdc
     * @param oldName
     * @param newName
     * @param type
     *            修改的类型
     * @param pName
     *            父类型（如果是修改itemType则为空）
     */
    public void updateItemType(String oldType, String newType, String type, String pType) {
        Map<String, Object> queryParams = new HashMap<String, Object>();
        String executeSQL = "";
        if ("itemClassify".equals(type)) {
            executeSQL = "UPDATE mas_item SET item_classify=:newType WHERE item_classify=:oldType AND item_type=:pType";
            queryParams.put("newType", newType);
            queryParams.put("oldType", oldType);
            queryParams.put("pType", pType);
        } else {
            executeSQL = "UPDATE mas_item SET item_type=:newType WHERE item_type=:oldType";
            queryParams.put("newType", newType);
            queryParams.put("oldType", oldType);
        }
        this.executeSQL(executeSQL, queryParams);
    }

    /**
     * 根据类别移除指标
     * 
     * @author xdc
     * @param type
     * @param itemType
     * @param itemClassify
     */
    public void removeItemByType(String type, String itemType, String itemClassify) {
        Map<String, Object> queryParams = new HashMap<String, Object>();
        String executeSQL = "";
        if ("itemClassify".equals(type)) {
            executeSQL = "UPDATE mas_item SET flag=:flag WHERE item_classify=:itemClassify AND item_type=:itemType";
            queryParams.put("itemType", itemType);
            queryParams.put("itemClassify", itemClassify);
        } else {
            executeSQL = "UPDATE mas_item SET flag=:flag WHERE item_type=:itemType";
            queryParams.put("itemType", itemType);
        }
        queryParams.put("flag", Flag.deleted.getValue());
        this.executeSQL(executeSQL, queryParams);
    }
}

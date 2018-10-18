
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
import com.mnt.preg.master.entity.basic.FoodTree;
import com.mnt.preg.master.pojo.basic.TreePojo;

/**
 * 商品类别DAO
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-11-19 gss 初版
 */
@Repository
public class TreeDAO extends HibernateTemplate {

    /**
     * 查询树级表
     * 
     * @author xdc
     * @param condition
     * @return
     */
    public List<TreePojo> queryTreeByCondition(FoodTree condition) {
        if (condition == null) {
            condition = new FoodTree();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "Tree");
        String sql = "SELECT " + DaoUtils.getSQLFields(TreePojo.class, "Tree")
                + "   FROM mas_food_tree AS Tree "
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), TreePojo.class);
    }

    /**
     * 根据类型类别编号删除树级表
     * 
     * @author xdc
     * @param treeId
     */
    public void deleteTreeByTreeId(String treeId, String treeKind) {
        String executeSQL = "UPDATE mas_food_tree SET flag=:flag WHERE tree_id LIKE:treeId AND tree_kind=:treeKind";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("flag", 0);
        queryParams.put("treeId", treeId + "%");
        queryParams.put("treeKind", treeKind);
        this.executeSQL(executeSQL, queryParams);
    }

    /**
     * 根据父节点id得到最大子节点id
     * 
     * @author xdc
     * @param catalogParent
     * @return
     */
    public String getSonMaxTreeId(String parentTreeId, String treeKind) {
        String sql = "SELECT MAX(tree_id) FROM mas_food_tree WHERE parent_tree_id=:parentTreeId AND tree_kind =:treeKind";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("parentTreeId", parentTreeId);
        queryParams.put("treeKind", treeKind);
        return this.SQLQueryFirst(sql, queryParams);
    }

    /**
     * 根据类别和名称判断名称是否重复(已废弃，需要在兄弟级别判断重复)
     * 
     * @author xdc
     * @param treeName
     * @param treeKind
     * @return
     */
    public Integer checkTreeName(String treeName, String treeKind) {
        String countSQL = "SELECT COUNT(tree_name) FROM mas_food_tree WHERE tree_name=:treeName AND tree_kind =:treeKind AND flag=:flag";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("treeName", treeName);
        queryParams.put("treeKind", treeKind);
        queryParams.put("flag", Flag.normal.getValue());
        return this.SQLCount(countSQL, queryParams);
    }
    
    /**
     * 
     * 通过名称获取treeId
     *
     * @author zj
     * @param treeName
     * @return
     */
    public String queryTreeId(String treeName, String type, String type2) {
        String sql = "SELECT tree_id FROM mas_food_tree WHERE tree_kind = :type2 AND tree_name = :treeName "
                + "AND parent_tree_id = :type";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("treeName", treeName);
        queryParams.put("type", type);
        queryParams.put("type2", type2);
        return this.SQLQueryFirst(sql, queryParams);
    }
    
    
    /**
     * 
     * 在兄弟层级判断类别重复
     *
     * @author zj
     * @param treeName
     * @param parentId
     * @return
     */
    public Integer checkTreeNameFromBrother(String treeName, String parentId) {
        String countSQL = "SELECT COUNT(tree_name) FROM mas_food_tree WHERE tree_name=:treeName AND parent_tree_id =:parentId AND flag=:flag AND tree_kind = :treeKind";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("treeName", treeName);
        queryParams.put("parentId", parentId);
        queryParams.put("treeKind", "food_material");
        queryParams.put("flag", Flag.normal.getValue());
        return this.SQLCount(countSQL, queryParams);
    }

    /**
     * 根据id修改排序
     * 
     * @author xdc
     * @param treeId
     * @param treeOrder
     */
    public void updateTreeOrder(String treeId, Integer treeOrder) {
        String executeSQL = "UPDATE mas_food_tree SET tree_order=:treeOrder WHERE tree_id=:treeId";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("treeId", treeId);
        queryParams.put("treeOrder", treeOrder);
        this.executeSQL(executeSQL, queryParams);
    }
    
    /**
     * 
     * 清空食材表（导数据用）
     *
     * @author zj
     */
    public void delFm() {
        String executeSQL = "DELETE FROM mas_food_material";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        this.executeSQL(executeSQL, queryParams);
    }
    
    /**
     * 
     * 清空食材元素关系表（导数据用）
     *
     * @author zj
     */
    public void delNur() {
        String executeSQL = "DELETE FROM mas_nutrient_amount";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        this.executeSQL(executeSQL, queryParams);
    }
    
    /**
     * 
     * 清空菜谱库
     *
     * @author zj
     */
    public void delFood() {
        String executeSQL = "DELETE FROM mas_food";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        this.executeSQL(executeSQL, queryParams);
    }
    
    /**
     * 
     * 清空菜谱食材关联库
     *
     * @author zj
     */
    public void delFoodList() {
        String executeSQL = "DELETE FROM mas_food_material_list";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        this.executeSQL(executeSQL, queryParams);
    }
    
    /**
     * 
     * 判断是否存在子结点
     *
     * @author zj
     * @param treeId
     * @param treeKind
     * @return
     */
    public Integer checkTreeHaveSub(String treeId, String treeKind) {
        String countSQL = "SELECT count(*) from mas_food_tree where parent_tree_id = :treeId AND tree_kind = :treeKind and flag = :flag";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("treeId", treeId);
        queryParams.put("treeKind", treeKind);
        queryParams.put("flag", Flag.normal.getValue());
        return this.SQLCount(countSQL, queryParams);
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

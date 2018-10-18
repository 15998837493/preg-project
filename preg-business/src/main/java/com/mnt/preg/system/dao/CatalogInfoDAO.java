/*
 * MasCatalogDAO.java    1.0  2017-10-20
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.system.entity.CatalogInfo;
import com.mnt.preg.system.pojo.CatalogInfoPojo;

/**
 * 类别管理业务DAO
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-10-20 scd 初版
 */
@Repository
public class CatalogInfoDAO extends HibernateTemplate {

    /**
     * 
     * 条件检索类别数据
     * 
     * @author scd
     * @param condition
     * @return
     */
    public List<CatalogInfoPojo> queryCatalogByType(CatalogInfo condition) {
        if (condition == null) {
            condition = new CatalogInfo();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "CatalogInfoPojo");
        String sql = "SELECT " + DaoUtils.getSQLFields(CatalogInfoPojo.class, "CatalogInfoPojo")
                + "   FROM mas_catalog AS CatalogInfoPojo "
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), CatalogInfoPojo.class);
    }

    /**
     * 获取子级最大ID
     * 
     * @author scd
     * @param catalogId
     * @return
     */
    public String getSonMaxProductCatalogId(String catalogParent) {
        String sql = "SELECT MAX(catalog_id) FROM mas_catalog WHERE catalog_parent_id=:catalogParent";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("catalogParent", catalogParent);
        return this.SQLQueryFirst(sql, queryParams);
    }

    /**
     * 逻辑删除类别
     * 
     * @author scd
     * @param catalogId
     */
    public void deleteCatalogInfo(String catalogId) {
        String executeSQL = "UPDATE mas_catalog SET flag=:flag WHERE catalog_id LIKE:catalogId";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("flag", 0);
        queryParams.put("catalogId", catalogId + "%");
        this.executeSQL(executeSQL, queryParams);
    }

    /**
     * 修改商品类别排序
     * 
     * @author scd
     * @param catalogId
     * @param productCatalogOrder
     */
    public void updateCatalogInfoOrder(String catalogId, Integer catalogOrder) {
        String executeSQL = "UPDATE mas_catalog SET catalog_order=:catalogOrder WHERE catalog_id=:catalogId";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("catalogId", catalogId);
        queryParams.put("catalogOrder", catalogOrder);
        this.executeSQL(executeSQL, queryParams);
    }

}

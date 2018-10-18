
package com.mnt.preg.system.dao;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.system.entity.SystemParam;
import com.mnt.preg.system.pojo.SystemParamPojo;

/**
 * 系统参数DAO（数据库表：sys_param）
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：v1.0 2014-12-18 zcq 初版
 */
@Repository
public class SystemParamDao extends HibernateTemplate {

    /**
     * 查询系统参数信息--通用检索
     * 
     * @param condition
     *            检索条件
     * @return List<SystemParamVo>
     *         系统参数信息
     */
    public List<SystemParamPojo> querySystemParam(SystemParam condition) {
        if (condition == null) {
            condition = new SystemParam();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "SystemParamPojo");
        String sql = "SELECT " + DaoUtils.getSQLFields(SystemParamPojo.class, "SystemParamPojo")
                + "   FROM sys_param AS SystemParamPojo "
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();

        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), SystemParamPojo.class);
    }

    /**
     * 修改系统参数信息
     * 
     * @param updateSystemParam
     *            参数类
     * @param conditionParams
     *            更新条件
     */
    @CacheEvict(value = "systemParamCache", key = "#updateSystemParam.paramId")
    public void updateParam(SystemParam updateSystemParam, List<HQLConditionParam> conditionParams) {
        this.updateHQL(updateSystemParam, conditionParams);
    }

    /**
     * 查询系统参数信息--根据【参数主键】
     * 
     * @param paramId
     *            主键ID
     * @return SystemParamVo
     *         系统参数信息
     */
    @Transactional(readOnly = true)
    @Cacheable(value = "systemParamCache", key = "#paramId")
    public SystemParamPojo getSystemParam(String paramId) {
        return this.getTransformerBean(paramId, SystemParam.class, SystemParamPojo.class);
    }

    /**
     * 根据表明和字段名查询去重的数据
     * 
     * @author xdc
     * @param tableName
     * @param fieldName
     * @return
     */
    public List<String> queryDistinctByField(String tableName, String fieldName) {
        String sql = "SELECT DISTINCT " + fieldName + " FROM " + tableName + " WHERE LENGTH(" + fieldName + ")>0";
        return this.SQLQuery(sql);
    }
}

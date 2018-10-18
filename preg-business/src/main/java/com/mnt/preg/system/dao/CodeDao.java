
package com.mnt.preg.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.system.entity.CodeInfo;
import com.mnt.preg.system.pojo.ChinaPojo;
import com.mnt.preg.system.pojo.CodePojo;

/**
 * 代码表DAO（主表：sys_code_info）
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2015-1-8 zy 初版
 */
@Repository
public class CodeDao extends HibernateTemplate {

    /**
     * 条件查询代码表信息
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public List<CodePojo> queryCode(CodeInfo condition) {
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "codeVo");
        String sql = "SELECT " + DaoUtils.getSQLFields(CodePojo.class, "codeVo")
                + "   FROM mas_code_info AS codeVo "
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), CodePojo.class);
    }

    /**
     * 条件查询代码表信息
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public List<CodePojo> queryCodeView(CodeInfo condition) {
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "codeVo");
        String sql = "SELECT " + DaoUtils.getSQLFields(CodePojo.class, "codeVo")
                + "   FROM mas_code_info AS codeVo "
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), CodePojo.class);
    }

    /**
     * 删除代码表信息
     * 
     * @author zcq
     * @param codeIdList
     * @return
     */
    public Integer deleteCodes(List<String> codeIdList) {
        String sql = "UPDATE mas_code_info SET flag=:flag WHERE code_id IN(:codeIdList)";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("flag", 0);
        params.put("codeIdList", codeIdList);
        return this.executeSQL(sql, params);
    }

    /**
     * 校验代码表信息是否可用
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public Integer checkCodeValid(CodeInfo condition) {
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "codeVo");
        String sql = "SELECT COUNT(code_id) FROM mas_code_info AS codeVo " + queryCondition.getQueryString();
        return this.SQLCount(sql, queryCondition.getQueryParams());
    }

    /**
     * 修改排序
     * 
     * @author zcq
     * @param codeId
     * @param codeOrder
     */
    public void updateCodeOrder(String codeId, Integer codeOrder) {
        String executeSQL = "UPDATE mas_code_info SET code_order=:codeOrder WHERE code_id=:codeId";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("codeId", codeId);
        queryParams.put("codeOrder", codeOrder);
        this.executeSQL(executeSQL, queryParams);
    }

    /**
     * 查询省市信息
     * 
     * @author zcq
     * @param pId
     * @return
     */
    public List<ChinaPojo> queryChina(Integer pId, String type) {
        String whereSql = "";
        if (StringUtils.isEmpty(type)) {
            whereSql = "   WHERE ChinaVo.pid=:pId";
        }
        String sql = "SELECT " + DaoUtils.getSQLFields(ChinaPojo.class, "ChinaVo")
                + "   FROM mas_china_info AS ChinaVo "
                + whereSql;
        Map<String, Object> queryParams = new HashMap<String, Object>();
        if (StringUtils.isEmpty(type)) {
            queryParams.put("pId", pId);
        }
        return this.SQLQueryAlias(sql, queryParams, ChinaPojo.class);
    }

    /**
     * 
     * 根据父节点类型查询最大排序号
     * 
     * @author gss
     * @param parentCodeKind
     * @return
     */
    public Integer getCodeMaxOrderByParentCodeKind(String parentCodeKind) {
        String sql = "SELECT MAX(code_order) AS codeOrder"
                + "   FROM mas_code_info AS CodeVo"
                + "   WHERE CodeVo.parent_code_kind=:parentCodeKind";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("parentCodeKind", parentCodeKind);
        List<CodePojo> codeVos = this.SQLQueryAlias(sql, paramMap, CodePojo.class);
        if (CollectionUtils.isEmpty(codeVos)) {
            return 0;
        }
        if (codeVos.get(0).getCodeOrder() == null) {
            return 0;
        }
        return codeVos.get(0).getCodeOrder();
    }

    /**
     * 条件查询代码表信息
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public List<CodePojo> queryAll() {
        String sql = "SELECT " + DaoUtils.getSQLFields(CodePojo.class, "codeVo")
                + "   FROM mas_code_info AS codeVo ";
        return this.SQLQueryAlias(sql, null, CodePojo.class);
    }
}

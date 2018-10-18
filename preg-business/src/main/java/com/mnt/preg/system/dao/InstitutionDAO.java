/*
 * InstitutionDAO.java	 1.0   2014-12-16
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.main.enums.Flag;
import com.mnt.preg.system.condition.InstitutionCondition;
import com.mnt.preg.system.condition.PrintCondition;
import com.mnt.preg.system.pojo.InstitutionPojo;
import com.mnt.preg.system.pojo.MenuPojo;
import com.mnt.preg.system.pojo.PrintPojo;

/**
 * 机构DAO（主表：sys_institution）
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：v1.0 2014-12-16 zcq 初版
 */
@Repository
public class InstitutionDAO extends HibernateTemplate {

    /**
     * 查询机构信息--通用检索
     * 
     * @param condition
     *            检索条件
     * @return List<InstitutionPojo>
     *         机构信息
     */
    public List<InstitutionPojo> queryIns(InstitutionCondition condition) {
        // 初始化变量-检索条件类
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "ins");

        String querySQL = "SELECT" + DaoUtils.getSQLFields(InstitutionPojo.class, "ins")
                + "        FROM sys_institution AS ins"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), InstitutionPojo.class);
    }

    /**
     * 查询机构信息--根据【机构主键】
     * 
     * @author zcq
     * @param insId
     * @return InstitutionPojo
     */
    public InstitutionPojo getIns(String insId) {
        String querySQL = "SELECT" + DaoUtils.getSQLFields(InstitutionPojo.class, "ins")
                + "        FROM sys_institution AS ins"
                + "        WHERE ins.ins_id=:insId AND ins.flag=:flag";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("insId", insId);
        params.put("flag", 1);
        return this.SQLQueryAliasFirst(querySQL, params, InstitutionPojo.class);
    }

    /**
     * 检验机构编码是否可用
     * 
     * @author zcq
     * @param insId
     * @return
     */
    public Integer checkInsIdValid(String insId) {
        String countSQL = "SELECT COUNT(ins_id) FROM sys_institution WHERE ins_id=:insId AND flag=:flag";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("insId", insId);
        params.put("flag", 1);
        return this.SQLCount(countSQL, params);
    }

    /**
     * 查询功能菜单信息--根据【机构主键】
     * 
     * @author zcq
     * @param insId
     *            机构主键
     * @return List<ModuleVo>
     *         功能菜单信息
     */
    public List<MenuPojo> queryMenuByInsId(String insId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(MenuPojo.class, "menu")
                + "   FROM sys_menu AS menu"
                + "       JOIN sys_institution_menu AS ins_menu ON ins_menu.menu_id=menu.menu_id"
                + "           AND ins_menu.ins_id=:insId"
                + "           AND menu.flag = :flag";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("insId", insId);
        queryParams.put("flag", 1);
        return this.SQLQueryAlias(sql, queryParams, MenuPojo.class);
    }

    /**
     * 删除机构功能菜单--根据【机构主键】
     * 
     * @param insId
     *            机构主键
     * @return 删除条数
     */
    public Integer deleteInsMenu(String insId) {
        String sql = "DELETE FROM sys_institution_menu WHERE ins_id=:insId";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("insId", insId);
        return this.executeSQL(sql, queryParams);
    }

    /**
     * 校验机构名称是否可用
     * 
     * @author zcq
     * @param insName
     * @return
     */
    public Integer checkInsNameValid(String insName) {
        String sql = "SELECT COUNT(ins_id) FROM sys_institution WHERE ins_name=:insName";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("insName", insName);
        return this.SQLCount(sql, queryParams);
    }

    /**
     * 查询机构打印选项配置
     * 
     * @author zcq
     * @param insId
     * @return
     */
    public List<String> queryInsPrintIds(String insId) {
        if (StringUtils.isBlank(insId)) {
            return null;
        }
        String sql = "SELECT print_id FROM sys_institution_print WHERE ins_id=:insId";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("insId", insId);
        return this.SQLQuery(sql, queryParams);
    }

    /**
     * 条件检索机构打印配置
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public List<PrintPojo> queryInsPrint(PrintCondition condition) {
        if (condition == null || StringUtils.isBlank(condition.getInsId())) {
            return null;
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "PrintPojo");
        queryCondition.setQueryString(queryCondition.getQueryString() + " AND print.ins_id=:insId ");
        queryCondition.getQueryParams().put("insId", condition.getInsId());

        String sql = "SELECT " + DaoUtils.getSQLFields(PrintPojo.class, "PrintPojo")
                + "   FROM sys_print AS PrintPojo"
                + "       JOIN sys_institution_print AS print ON print.print_id=PrintPojo.print_id"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();

        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), PrintPojo.class);
    }

    /**
     * 查询打印配置信息--根据【机构主键】
     * 
     * @author zcq
     * @param insId
     * @return
     */
    public List<PrintPojo> queryPrintByInsId(String insId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(PrintPojo.class, "PrintPojo")
                + "   FROM sys_print AS PrintPojo"
                + "       JOIN sys_institution_print AS print ON print.print_id=PrintPojo.print_id"
                + "           AND print.ins_id=:insId"
                + "           AND print.flag = :flag";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("insId", insId);
        queryParams.put("flag", Flag.normal.getValue());

        return this.SQLQueryAlias(sql, queryParams, PrintPojo.class);
    }

    /**
     * 删除机构打印选项配置--根据【机构主键】
     * 
     * @param insId
     *            机构主键
     * @return 删除条数
     */
    public Integer deleteInsPrint(String insId) {
        String sql = "DELETE FROM sys_institution_print WHERE ins_id=:insId";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("insId", insId);
        return this.executeSQL(sql, queryParams);
    }

}


package com.mnt.preg.system.service;

import java.util.List;
import java.util.Map;

import com.mnt.preg.system.condition.InstitutionCondition;
import com.mnt.preg.system.condition.PrintCondition;
import com.mnt.preg.system.entity.Institution;
import com.mnt.preg.system.pojo.InstitutionPojo;
import com.mnt.preg.system.pojo.MenuPojo;
import com.mnt.preg.system.pojo.PrintPojo;

/**
 * 机构管理事务
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-10-27 zcq 初版
 */
public interface InstitutionService {

    /**
     * 查询机构信息--条件检索
     * 
     * <dl>
     * <dt>1 功能描述</dt>
     * <dd>查询机构信息</dd>
     * </dl>
     * 
     * @author zcq
     * @param condition
     *            条件
     * @return 查询机构信息
     */
    List<InstitutionPojo> queryIns(InstitutionCondition condition);

    /**
     * 查询机构信息--根据【机构主键】
     * 
     * <dl>
     * <dt>1 功能描述</dt>
     * <dd>查询机构信息</dd>
     * </dl>
     * 
     * @author zcq
     * @param insId
     *            机构主键
     * @return 机构信息
     */
    InstitutionPojo getIns(String insId);

    /**
     * 添加机构信息
     * 
     * <dl>
     * <dt>1 功能描述</dt>
     * <dd>添加机构信息</dd>
     * </dl>
     * 
     * @author zcq
     * @param insInfo
     *            机构信息
     * @return 机构主键
     */
    String addIns(Institution insInfo);

    /**
     * 修改机构信息
     * 
     * <dl>
     * <dt>1 功能描述</dt>
     * <dd>修改机构信息</dd>
     * </dl>
     * 
     * @author zcq
     * @param insInfo
     *            机构信息
     */
    InstitutionPojo editIns(Institution insInfo);

    /**
     * 查询机构功能菜单
     * 
     * @author zcq
     * @param insId
     *            机构主键
     * @return 机构功能菜单
     */
    List<MenuPojo> queryInsMenu(String insId);

    /**
     * 编辑机构功能菜单
     * 
     * <dl>
     * <dt>1 功能描述</dt>
     * <dd>编辑机构功能菜单</dd>
     * </dl>
     * 
     * @author zcq
     * @param insId
     *            机构信息
     * @param menuIdList
     *            功能菜单
     */
    void saveInsMenu(String insId, List<String> menuIdList);

    /**
     * 检验机构编码是否可用
     * 
     * @author zcq
     * @param insId
     * @return
     */
    Integer checkInsIdValid(String insId);

    /**
     * 检验机构名称是否可用
     * 
     * @author zcq
     * @param insName
     * @return
     */
    Integer checkInsNameValid(String insName);

    /**
     * 检索机构打印选项配置
     * 
     * @author zcq
     * @param insId
     * @return
     */
    List<String> queryInsPrintIds(String insId);

    /**
     * 条件检索机构打印配置
     * 
     * @author zcq
     * @param condition
     * @return
     */
    List<PrintPojo> queryInsPrint(PrintCondition condition);

    /**
     * 查询打印配置信息--根据【机构主键】
     * 
     * @author zcq
     * @param insId
     * @return
     */
    Map<String, List<PrintPojo>> getInsPrintListMap(String insId);

    /**
     * 保存机构打印选项配置
     * 
     * @author zcq
     * @param insId
     * @param printIdList
     * @return
     */
    void saveInsPrint(String insId, List<String> printIdList);

}

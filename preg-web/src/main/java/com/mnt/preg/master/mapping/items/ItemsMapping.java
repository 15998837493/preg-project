/*
 * ItemsMapping.java    1.0  2017-6-26
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.master.mapping.items;

import com.mnt.preg.web.anoton.FrontCache;

/**
 * 检查项目映射路径
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-6-26 scd初版
 */
@FrontCache(space = "item")
public class ItemsMapping {

    /********************************* 系统检查项目维护 ************************************/
    /** 增加检查项目初始化页面 */
    public static final String ADD_INIT_INSPECTITEM = "/page/master/add_init_inspectItem.action";

    /** 检查项目主键验证 */
    public static final String INSPECTITEM_VALIDATE = "/page/master/inspectitem_validate.action";

    /** 增加检查项目 */
    public static final String ADD_INSPECTITEM = "/page/master/inspectItem/inspectItem_add.action";

    /** 修改检查项目初始化页面 */
    public static final String UPDATE_INIT_INSPECTITEM = "/page/master/update_init_inspectItem.action";

    /** 修改检查项目 */
    public static final String INSPECTITEM_UPDATE = "/page/master/inspectItem_update.action";

    /** 删除检查项目 */
    public static final String REMOVE_INSPECTITEM = "/page/master/remove_inspectItem.action";

    /** 查询检查项目 */
    public static final String INSPECTITEM_QUERY = "/page/master/inspectItem_query.action";

    /** 配置检查项目初始化页面 */
    public static final String CONFIG_INIT_INSPECTITEM = "/page/master/config_init_inspectitem.action";

    /** 异步检索体检项目中的检查项目详情字典 */
    public static final String INSPECTITEM_DETAILS_QUERY = "/page/exam/item/inspectitem_details_query.action";

    /** 检查项目明细增加 */
    public static final String INSPECTITEM_DETAILS_ADD = "/page/exam/item/inspectitem_details_add.action";

    /** 删除体检项目详情字典 */
    public static final String INSPECTITEM_DETAIL_REMOVE = "/page/exam/item/inspectitem_detail_remove.action";

    /** 查询机构检查项目 */
    public static final String DOCTOR_INSPECTITEM_QUERY = "/page/master/doctor_inspectItem_query.action";

    /********************************* 检查项目维护 ************************************/

    /********************************* 系统指标维护 ************************************/
    public static final String ITEMCODE_VALID = "/page/exam/item/item_code_valid.action";

    /** 异步检索体检项目字典 */
    public static final String ITEM_QUERY = "/page/exam/item/query_item.action";

    /** 验证指标名称是否重复 */
    public static final String ITEMNAME_VALID = "/page/exam/item/item_name_valid.action";

    /** 删除体检项目字典 */
    public static final String ITEM_REMOVE = "/page/exam/item/query_remove.action";

    /** 增加体检项目字典初始化页面 */
    public static final String ITEM_ADD_INIT = "/page/exam/item/add_init_item.action";

    /** 增加体检项目字典页面 */
    public static final String ITEM_ADD = "/page/exam/item/add_item.action";

    /** 修改体检项目字典初始化页面 */
    public static final String ITEM_UPDATE_INIT = "/page/exam/item/update_item_init.action";

    /** 修改体检项目字典页面 */
    public static final String ITEM_UPDATE = "/page/exam/item/update_item.action";

    public static final String ITEM_ORDER = "/page/exam/item/item_order.action";

    public static final String UPDATE_ITEM_TYPE = "/page/exam/item/update_item_type.action";

    public static final String REMOVE_ITEM_BY_TYPE = "/page/exam/item/remove_item_by_type.action";

    /********************************* 系统指标维护 ************************************/
    // ****************************************检查项目模板*****************************************************
    /** 异步检索体检项目模板字典 */
    public static final String INSPECT_ITEM_TEMPLET_VIEW = "/page/exam/item/inspect_item_templet_view.action";

    /** 异步检索体检项目模板字典 */
    public static final String INSPECT_ITEM_TEMPLET_QUERY = "/page/exam/item/inspect_item_templet_query.action";

    /** 删除体检项目模板字典 */
    public static final String INSPECT_ITEM_TEMPLET_REMOVE = "/page/exam/item/inspect_item_templet_remove.action";

    /** 增加体检项目模板字典初始化页面 */
    public static final String INSPECT_ITEM_TEMPLET_ADD_INIT = "/page/exam/item/inspect_item_templet_add_init.action";

    /** 增加体检项目模板字典页面 */
    public static final String INSPECT_ITEM_TEMPLET_ADD = "/page/exam/item/inspect_item_templet_add.action";

    /** 增加检测项目模板by Code */
    public static final String ADD_INSPECT_ITEM_TEMPLET_BYCODE = "/page/exam/item/add_inspect_item_templet_bycode.action";

    /** 修改体检项目模板字典初始化页面 */
    public static final String INSPECT_ITEM_TEMPLET_UPDATE_INIT = "/page/exam/item/inspect_item_templet_update_init.action";

    /** 修改体检项目模板字典页面 */
    public static final String INSPECT_ITEM_TEMPLET_UPDATE = "/page/exam/item/inspect_item_templet_update.action";

    /** 异步检索体检项目模板中的检查项目详情字典 */
    public static final String INSPECT_ITEM_DETAILS_QUERY = "/page/exam/item/inspect_item_details_query.action";

    /** 检查项目明细增加 */
    public static final String INSPECT_ITEM_DETAILS_ADD = "/page/exam/item/inspect_item_details_add.action";

    /** 删除体检项目详情字典 */
    public static final String INSPECT_ITEM_TEMPLET_DETAIL_REMOVE = "/page/exam/item/inspect_item_templet_detail_remove.action";

    /** 团检报告信息检索 */
    public static final String TEAM_EXAMINE_QUERY = "/page/exam/team_examine_query.action";

    // ****************************************检查项目模板*****************************************************
    /********************************** 干预重点模块 **********************************/

    /** 验证干预重点编码 */
    public static final String DISEASECODE_VALID = "/page/master/plan/disease_code_valid.action";

    /** 验证干预重点名称 */
    public static final String DISEASENAME_VALID = "/page/master/plan/disease_name_valid.action";

    /** 增加干预重点初始化页面 */
    public static final String ADD_INIT_INTERVENEDISEASE = "/page/master/plan/add_init_intervene_disease.action";

    /** 增加干预重点 */
    public static final String ADD_INTERVENEDISEASE = "/page/master/plan/add_intervene_disease.action";

    /** 修改干预重点初始化页面 */
    public static final String UPDATE_INIT_INTERVENEDISEASE = "/page/master/plan/update_init_intervene_disease.action";

    /** 修改干预重点 */
    public static final String UPDATE_INTERVENEDISEASE = "/page/master/plan/update_intervene_disease.action";

    /** 删除干预重点 */
    public static final String REMOVE_INTERVENEDISEASE = "/page/master/plan/remove_intervene_disease.action";

    /** 代码表信息查询 */
    public static final String QUERY_INTERVENEDISEASE = "/page/master/plan/code_intervene_disease.action";

    /** 验证干预疾病主键是否重复 */
    public static final String INTERVENEDISEASE_CODE_VALIDATE = "/page/master/plan/intervenedisease_code_validate.action";

    /********************************** 干预重点模块 **********************************/
    /** 添加疾病指标 */
    public static final String INSEPCT_ITEM_DISEASE_QUERY = "/page/disease/inspect_item_disease_query.action";

    /** 添加疾病指标 */
    public static final String INSPECT_ITEM_DISEASE_ADD = "/page/disease/inspect_item_disease_add.action";

    public static final String DISEASE_ITEM_GROUP_QUERY = "/page/disease/disease_item_group_query.action";

    public static final String DISEASE_ITEM_GROUP_ADD = "/page/disease/disease_item_group_add.action";

    public static final String DISEASE_ITEM_GROUP_DELETE = "/page/disease/disease_item_group_delete.action";

    /** 疾病指标 */
    public static final String DISEASE_QUOTA_VIEW = "/page/disease/disease_quota_view.action";

    /** 疾病指标 */
    public static final String INSPECT_DISEASE_QUOTA_VIEW = "/page/disease/inspect_disease_quota_view.action";

    /** 诊断营养处方配置 */
    public static final String DISEASE_PRESCRIPTION_VIEW = "/page/disease/disease_prescription.action";

    /** 诊断辅助检查配置 */
    public static final String DISEASE_INSPECT_VIEW = "/page/disease/disease_inspect.action";

    /** 诊断元素配置 */
    public static final String DISEASE_NUTRIENT_VIEW = "/page/disease/disease_nutrient.action";

    /** 指标 */
    public static final String ITEM_QUERY_ALL = "/page/disease/item_query_all.action";

    public static final String DISEASE_ITEM_UPDATE = "/page/disease/disease_item_update.action";

    /** 删除疾病指标 */
    public static final String INSEPCT_ITEM_DISEASE_DELETE = "/page/disease/inspect_item_disease_delete.action";

    /** 添加诊断营养处方 */
    public static final String ADD_PRESCRIPTION = "/page/disease/add_prescription.action";

    /** 查询诊断营养处方 */
    public static final String QUERY_PRESCRIPTION = "/page/disease/query_prescription.action";

    /** 删除诊断营养处方 */
    public static final String DELETE_PRESCRIPTION = "/page/disease/delete_prescription.action";

    /** 查询辅助检查项目 */
    public static final String QUERY_INSPECT = "/page/disease/query_inspect.action";

    /** 查询疾病关联的元素 */
    public static final String QUERY_NUTRIENT = "/page/disease/query_nutrient.action";

    /** 添加辅助检查项目 */
    public static final String ADD_INSPECT = "/page/disease/add_inspect.action";

    /** 添加疾病关联的元素 */
    public static final String ADD_NUTRIENT = "/page/disease/add_nutrient.action";

    /** 删除辅助检查项目 */
    public static final String DELETE_INSPECT = "/page/disease/delete_inspect.action";

    /** 删除疾病关联的元素 */
    public static final String DELETE_NUTRIENT = "/page/disease/delete_nutrient.action";

    /********************************* 医院检查项目维护开始 ************************************/
    /** 跳转检查项目配置页面 */
    public static final String HOSPITAL_INSPECT_VIEW = "/page/master/hospital_inspect_view.action";

    /** 检查项目名称验证 */
    public static final String HOSPITALINSPECT_NAME_VALIDATE = "/page/master/hospital_inspect_name_validate.action";

    /** 添加检查项目 */
    public static final String HOSPITAL_INSPECT_ADD = "/page/master/hospital_inspect_add.action";

    /** 修改检查项目 */
    public static final String HOSPITAL_INSPECT_UPDATE = "/page/master/hospital_inspect_update.action";

    /** 验证检查项目是否已经被收费项目配置 */
    public static final String HOSPITAL_INSPECT_IS_CONFIGURE = "/page/master/hospital_inspect_is_configure.action";

    /** 检查项目删除 */
    public static final String HOSPITAL_INSPECT_REMOVE = "/page/master/hospital_inspect_remove.action";

    /********************************* 医院检查项目维护结束 ************************************/

    /********************************* 医院收费项目 ************************************/

    /** 收费项目配置页面 */
    public static final String INSPECT_PAY_VIEW = "/page/master/inspect_pay_view.action";

    /** 查询医院收费项目 */
    public static final String INSPECT_PAY_QUERY = "/page/master/inspect_pay_query.action";

    /** 增加医院收费项目 */
    public static final String INSPECT_PAY_SAVE = "page/master/inspect_pay_save.action";

    /** 删除医院收费项目项目 */
    public static final String INSPECT_PAY_REMOVE = "/page/master/inspect_pay_remove.action";

    /** 获取收费项目包含的检验项目 */
    public static final String INSPECT_PAY_ITEMS_GET = "/page/master/inspect_pay_items_get.action";

    /** 医院收费项目名称验证 */
    public static final String INSPECT_PAY_NAME_VALIDATE = "/page/master/inspect_pay_name_validate.action";

    /** 机构检查项目页面初始化 */
    public static final String HOSPITAL_INIT = "/page/master/hospital_init.action";

    /** 辅助检查项目名称验证 */
    public static final String TEMPLETNAME_VALID = "/page/master/templetName_Valid.action";

    /** 删除辅助检查项目套餐 */
    public static final String TEMPLETNAME_REMOVE = "/page/master/templetName_remove.action";

    /** 根据诊断项目主键获取诊断项目配置的检查项目 */
    public static final String DISEASE_INSPECT_CONFIG = "/page/master/disease_inspect_config.action";

    /** 检索组收费项目关联的检查项目 */
    public static final String QUERY_RELATION_CONFIG = "/page/master/query_relation_config.action";

    /** 检索组合检查项目关联的单一检查项目 */
    public static final String QUERY_RELATION_DEAIL = "/page/master/query_relation_deail.action";

    /** 单一检查项目转换成组合检查项目时，删除掉所有作为单一检查项目时的数据 */
    public static final String CHECK_RELATION_DEAIL = "/page/master/check_relation_deail.action";

    /** 异步获取检查项目 */
    public static final String HOSPITALINSPECT_QUERY = "/page/master/hospital_inspect_query.action";

    /** 验证收费项目是否已经被套餐配置 */
    public static final String HOSPITAL_CHEACK_TEMPLATE_CONFIG = "/page/master/hospital_config.action";

    /** 通过收费项目查询其下所有的指标 */
    public static final String GET_HOSPITAL_INSPECT_PAY_ITEM = "/page/master/get_hospital_inspect_pay_item.action";

    /** 通过收费项目查询其下所有的指标 */
    public static final String QUERY_HOSPITAL_INSPECT_PAY_INSPECT = "/page/master/query_hospital_inspect_pay_inspect.action";

    /********************************* 医院收费项目结束 ************************************/

    /********************************* 医院信息维护开始 ************************************/

    /** 跳转医院信息配置页面 */
    public static final String HOSPITAL_VIEW = "/page/master/items/hospital_view.action";

    /** 检索医院信息 */
    public static final String HOSPITAL_QUERY = "/page/master/items/hospital_query.action";

    /** 添加医院信息 */
    public static final String HOSPITAL_ADD = "/page/master/items/hospital_add.action";

    /** 修改医院信息 */
    public static final String HOSPITAL_UPDATE = "/page/master/items/hospital_update.action";

    /** 修改节点名称 */
    public static final String HOSPITAL_FATHER_NAME_UPDATE = "/page/master/items/hospital_father_name_update.action";

    /** 验证医院名称唯一 */
    public static final String HOSPITAL_VALID = "/page/master/items/hospital_valid.action";

    /** 删除医院信息（物理删除） */
    public static final String HOSPITAL_DELETE = "/page/master/items/hospital_delete.action";

    /** 删除医院信息（逻辑删除） */
    public static final String HOSPITAL_REMOVE = "/page/master/items/hospital_remove.action";

    /********************************* 医院信息维护结束 ************************************/
}


package com.mnt.preg.platform.mapping;

import com.mnt.preg.web.anoton.FrontCache;

/**
 * 诊疗中心mapping映射
 *
 * @author zcq
 * @version 1.0
 * <p>
 * 变更履历：
 * v1.0 2017-5-13 zcq 初版
 */
@FrontCache(space = "Platform")
public class PlatformMapping {

    // =================================【DiagnosisController】=========================================
    public static final String DIAGNOSIS_PLATFORM_VIEW = "/page/platform/platform_customer_view.action";

    public static final String USER_INSPECT_ITEM_SAVE = "/page/platform/user_inspect_item_save.action";

    public static final String DIAGNOSIS_QUERY_MORE = "/page/plan/diagnosis_query_more.action";

    public static final String DIAGNOSIS_QUERY_MORE_EVALUATE = "/page/plan/diagnosis_query_more_evaluate.action";

    public static final String DIAGNOSIS_LAST_DISEASE = "/page/plan/diagnosis_last_disease.action";

    public final static String DIAGNOSIS_ADD = "/page/plan/diagnosis_add.action";

    public final static String DIAGNOSIS_UPDATE = "/page/plan/diagnosis_update.action";

    public final static String DIAGNOSIS_OBSTETRICAL_UPDATE = "/page/plan/diagnosis_obstetrical_update.action";

    public final static String DIAGNOSIS_UPDATE_ARCHIVES = "/page/plan/diagnosis_update_archives.action";

    public final static String DIAGNOSIS_DISEASE_ADD = "/page/plan/diagnosis_disease_add.action";

    public final static String DIAGNOSIS_HOSPITAL_ITEM_QUERY = "/page/platform/diagnosis_hospital_item_query.action";

    public final static String DIAGNOSIS_HOSPITAL_REPORT_QUERY = "/page/platform/diagnosis_hospital_report_query.action";

    public final static String DIAGNOSIS_DISEASE_UPDATE = "/page/plan/diagnosis_disease_update.action";

    public final static String DIAGNOSIS_DISEASE_DELETE = "/page/plan/diagnosis_disease_delete.action";

    public final static String DIAGNOSIS_DISEASE_BATCH_SAVE = "/page/plan/diagnosis_disease_batch_save.action";

    public final static String DIAGNOSIS_DISEASE_GET = "/page/plan/diagnosis_disease_get.action";

    public final static String DIAGNOSIS_RESET = "/page/plan/diagnosis_reset.action";

    public final static String DIAGNOSIS_ADD_BOOKDATE = "/page/plan/diagnosis_bookdate_add.action";

    public final static String DIAGNOSIS_UPDATE_BOOKDATE = "/page/plan/diagnosis_bookdate_update.action";

    public final static String DIAGNOSIS_DELETE_BOOKDATE = "/page/plan/diagnosis_bookdate_delete.action";

    public final static String DIAGNOSIS_QUERY_BOOKDATE = "/page/plan/diagnosis_bookdate_query.action";

    public final static String DIAGNOSIS_ARCHIVE_GET = "/page/plan/diagnosis_archive_get.action";

    public final static String REGISTRATION_ADD = "/page/plan/registration_add.action";

    public final static String WEIGHT_PICTURE = "/page/plan/weight_picture.action";

    public final static String DIAGNOSIS_INSPECT_QUERY = "/page/platform/diagnosis_inspect_query.action";

    public final static String ECHARTS_PICTURE_GET_DATA = "/page/plan/echarts_picture_get_data.action";

    public final static String SAVE_ECHARTS_WEIGHT_RESULT = "/page/plan/save_echarts_weight_result.action";

    public final static String QUERY_ECHARTS_WEIGHT_RESULT = "/page/plan/query_echarts_weight_result.action";

    public final static String QUERY_DIAGNOSISES = "/page/plan/query_diagnosises.action";

    public final static String SHOW_DIARYPDF = "/page/plan/show_diarypdf.action";

    public final static String DIAGNOSIS_SAVE_INSPECT_RESULT = "/page/plan/save_inspect_result.action";

    public final static String UPDATE_DIAGNOSIS_STATUS = "/page/plan/update_diagnosis_status.action";

    public final static String UPDATE_DIAGNOSIS_ASSISSTENT_STATUS = "/page/plan/update_diagnosis_assisstent_status.action";

    public static final String DISEASE_OFTEN_QUERY = "/page/disease/disease_often_query.action";

    public static final String DISEASE_OFTEN_ADD = "/page/disease/disease_often_add.action";

    public static final String DISEASE_OFTEN_REMOVE = "/page/disease/disease_often_remove.action";

    public static final String DISEASE_OFTEN_CHECK_NAME = "/page/disease/disease_often_check_name.action";

    public static final String DISEASE_INFO_UPDATE = "/page/disease/disease_info_update.action";

    public static final String DIAGNOSIS_LAST_INFO = "/page/plan/diagnosis_last_info.action";

    public static final String DIAGNOSIS_REPORT_INFO = "/page/plan/diagnosis_report_info.action";

    // =================================【DiagnosisInspectController】=====================================
    public static final String USER_INPSECT_RESET = "/page/plan/user_inspect_reset.action";

    public static final String DIAGNOSIS_INSPECT_ADD = "/page/platform/diagnosis_inspect_add.action";

    public final static String PLAN_INSPECTSTATUS_UPDATE = "/page/plan/plan_inspectstatus_update.action";

    public final static String DIAGNOSIS_INSPECT_DELETE = "/page/platform/diagnosis_inspect_delete.action";

    public final static String DIAGNOSIS_INSPECT_SEND = "/page/platform/diagnosis_inspect_send.action";

    public final static String DIAGNOSISITEM_INSPECTSTATUS_UPDATE = "/page/platform/diagnosisitem_inspectstatus_update.action";

    // =================================【DiagnosisItemController】=====================================

    public final static String DIAGNOSIS_ITEM_ADD = "/page/evaluate/diagnosis_item_add.action";

    public final static String DIAGNOSIS_ITEM_UPDATE = "/page/evaluate/diagnosis_item_update.action";

    public final static String DIAGNOSIS_EXAMINE_UPDATE = "/page/evaluate/diagnosis_examine_update.action";

    public final static String DIAGNOSIS_ITEM_DELETE = "/page/evaluate/diagnosis_item_delete.action";

    public final static String DIAGNOSIS_EXAMINE_DELETE = "/page/evaluate/diagnosis_examine_delete.action";

    public final static String DIAGNOSIS_LAST_ITEM_LIST = "/page/plan/diagnosis_last_item_list.action";

    public static final String DISEASE_ITEM_GROUP_FIT = "/page/disease/disease_item_group_fit.action";

    public final static String DIAGNOSIS_ITEM_SEND = "/page/platform/diagnosis_item_send.action";

    public final static String DIAGNOSIS_ITEMS_SEND = "/page/platform/diagnosis_items_send.action";// 批量修改检验项目

    public final static String DIAGNOSIS_REPORTS_QUERY = "/page/platform/diagnosis_reports_query.action";// 异步加载报告单信息

    public final static String DIAGNOSIS_REPORT_ADD = "/page/platform/diagnosis_report_add.action";// 添加报告单信息

    public final static String DIAGNOSIS_REPORT_UPDATE = "/page/platform/diagnosis_report_update.action";// 修改报告单信息

    // =================================【DiagnosisPlanController】=====================================
    /**
     * 医嘱页面——补充剂嵌入页
     */
    public final static String PLAN_GET_EXTENDER_YIZHU = "/page/plan/get_extender_yizhu.action";

    public final static String PLAN_MAIN_VIEW = "/page/plan/plan_main_view.action";

    public final static String RECEIVE_PLAN_ADJUST = "/page/plan/receive_plan_adjust.action";// 干预计划展示

    public final static String DIAGNOSIS_PRESCRIPTION_SAVE = "/page/plan/diagnosis_prescription_save.action";

    public final static String PLAN_INTAKE_GET = "/page/plan/plan_intake_get.action";

    public final static String PLAN_DIETLIST_QUERY = "/page/plan/plan_dietlist_query.action";

    public final static String PLAN_DIETDETAIL_GET = "/page/plan/plan_dietdetail_get.action";

    public final static String PLAN_DIETDETAIL_ADD = "/page/plan/plan_dietdetail_add.action";

    public final static String PLAN_DIETDETAIL_UPDATE = "/page/plan/plan_dietdetail_update.action";

    public final static String PLAN_DIETDETAIL_DELETE = "/page/plan/plan_dietdetail_delete.action";

    public final static String RECEIVE_PLAN_ADD = "/page/plan/receive_plan_add.action";// 添加干预计划设置

    public final static String RECEIVE_PLAN_TEXT_ADD = "/page/plan/receive_plan_text_add.action";// 保存膳食方案备注

    public final static String PDF_ISOK = "/page/plan/pdf_isok.action";// 检验pdf报告是否完成

    public final static String RECEIVE_PLAN_DIET = "/page/plan/receive_plan_diet.action";

    public final static String PERSON_INTAKE_DETAIL_QUERY = "/page/plan/person_intake_detail_query.action";

    public final static String PERSON_INTAKE_REMOVE = "/page/plan/person_intake_remove.action";

    public final static String RECEIVE_CREATE_PDF = "/page/plan/receive_create_pdf.action";

    public final static String REGENERATE_REPORT = "/page/plan/regenerate_report.action";

    public final static String PLAN_COURSE = "/page/plan/plan_course.action";

    public final static String ADD_PERSON_INTAKE = "/page/plan/add_person_intake.action";

    public final static String PERSON_INTAKENAME_VALIDATE = "/page/plan/person_intakename_validate.action";

    public final static String ADD_AUXILIARY = "/page/plan/add_auxiliary.action";

    public final static String REMOVE_AUXILIARY = "/page/plan/remove_auxiliary.action";

    public final static String ADD_HOSPITAL = "/page/plan/add_hospital.action";

    public final static String QUERY_HOSPITAL = "/page/plan/query_hospital.action";

    // =================================【DiagnosisToolbarController】===================================
    public final static String PLAN_GUIDE_PAGE = "/page/plan/guide_page.action";

    public final static String PLATFORM_RECEIVE_MAIN = "/page/platform/receive_main.action";

    public final static String PLAN_GET_CHECK_ITEMS = "/page/diagnosis/get_check_items.action";

    public final static String PLAN_GET_JIEZHEN = "/page/plan/get_jiezhen.action";

    public final static String PLAN_GET_PLAN = "/page/plan/get_plan.action";

    public static final String DIAGNOSIS_RECORD = "/page/plan/diagnosis_record.action";

    public static final String DIAGNOSIS_HISTORY_QUERY = "/page/plan/diagnosis_history_query.action";

    public final static String QUERY_REPORT_VIEW = "/page/plan/query_report_view.action";

    public final static String QUERY_REPORT_LIST = "/page/plan/query_report_list.action";

    public final static String CHECK_FILE_EXIST = "/page/plan/check_file_exist.action";

    public final static String PLAN_GET_BOOKING = "/page/plan/get_plan_booking.action";

    /**
     * 医嘱页面-复查预约嵌入页
     */
    public final static String PLAN_GET_BOOKING_YIZHU = "/page/plan/get_plan_booking_yizhu.action";

    public final static String TRANSFORM_JUMP = "/page/plan/transformJump.action";

    /**
     * 营养病例小节页面
     */
    public final static String DIAGNOSIS_SUMMARY_PAGE = "/page/diagnosis/diagnosis_summary_page.action";

    /**
     * 营养病例小节数据查询
     */
    public final static String DIAGNOSIS_INSPECT_RESULT = "/page/diagnosis/get_diangosis_inspect_result.action";

    public final static String DIAGNOSIS_INITAL_VIEW_INIT = "/page/plan/diagnosis_inital_init.action";

    public final static String DIAGNOSIS_INITAL_CHECK = "/page/plan/diagnosis_inital_check.action";

    public final static String ADJUST_QUERY_HISTORY = "/page/plan/adjust_query_history.action";

    /**
     * 移除建档信息的永久诊断项目
     */
    public final static String REMOVE_PERMANENT_DISEASE = "/page/plan/remove_permanent_disease.action";

    // =================================【预约课程部分】===================================

    /**
     * 查询预约课程
     */
    public final static String QUERY_PREG_SCHEDULE_COURSE = "/page/plan/query_preg_schedule_course.action";

    /**
     * 预约课程添加
     */
    public final static String PREG_COURSE_BOOKING_SAVE = "/page/plan/preg_course_booking_save.action";

    /**
     * 删除预约课程
     */
    public final static String PREG_COURSE_BOOKING_DEL = "/page/plan/preg_course_booking_del.action";

    /**
     * 查询课程反馈
     */
    public final static String PREG_BOOKING_FEEDBACK_QUERY = "/page/plan/preg_booking_feedback_query.action";

    /**
     * 添加课程反馈
     */
    public final static String PREG_BOOKING_FEEDBACK_ADD = "/page/plan/preg_booking_feedback_add.action";

    /**
     * 修改课程反馈
     */
    public final static String PREG_COURSE_BOOKING_UPDATE = "/page/plan/preg_course_booking_update.action";
}

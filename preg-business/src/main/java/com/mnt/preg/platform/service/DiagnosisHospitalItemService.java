
package com.mnt.preg.platform.service;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.mnt.preg.platform.condition.DiagnosisHospitalInspectReportCondition;
import com.mnt.preg.platform.condition.DiagnosisHospitalItemCondition;
import com.mnt.preg.platform.entity.DiagnosisHospitalInspectPay;
import com.mnt.preg.platform.entity.DiagnosisHospitalInspectReport;
import com.mnt.preg.platform.entity.DiagnosisHospitalItem;
import com.mnt.preg.platform.pojo.DiagnosisHospitalInspectPayPojo;
import com.mnt.preg.platform.pojo.DiagnosisHospitalInspectReportPojo;
import com.mnt.preg.platform.pojo.DiagnosisHospitalItemPojo;

/**
 * 接诊实验室检验项目管理
 * 
 * @author mlq
 * 
 *         变更履历：
 *         2018-06-21 mlq
 */
@Validated
public interface DiagnosisHospitalItemService {

    /**
     * 根据问诊id查询报告单信息
     * 
     * @author mlq
     * @param diagnosisId
     */
    List<DiagnosisHospitalInspectReportPojo> queryDiagnosisReports(String diagnosisId);

    /**
     * 根据报告单id查询报告单信息
     * 
     * @author mlq
     * @param reportId
     */
    DiagnosisHospitalInspectReportPojo queryDiagnosisReportById(String reportId);

    /**
     * 添加报告单
     * 
     * @author mlq
     * @param report
     */
    DiagnosisHospitalInspectReportPojo addReport(DiagnosisHospitalInspectReport report);

    /**
     * 批量添加报告
     * 
     * @author mlq
     * @param inspectReportList
     * @param diagnosisId
     */
    void addPregDiagnosisInspectReports(List<DiagnosisHospitalInspectReportPojo> inspectReportList,
            String diagnosisId);

    /**
     * 更新报告单(结果结论)
     * 
     * @author mlq
     * @param report
     */
    void updateDiagnosisHospitalItemReport(DiagnosisHospitalInspectReport report);

    /**
     * 更新报告单(状态)
     * 
     * @author mlq
     * @param diagnosisId
     * @param status
     */
    void updateDiagnosisHospitalItemReportByDiagnosisId(String diagnosisId, int status);

    /**
     * 删除报告
     * 
     * @author mlq
     * @param reportId
     */
    void deleteDiagnosisHospitalInspectReport(String reportId);

    /**
     * 批量添加收费项目
     * 
     * @author mlq
     * @param inspectPayList
     * @param reportId
     */
    List<DiagnosisHospitalInspectPay> addPregDiagnosisInspectPays(List<DiagnosisHospitalInspectPay> inspectPayList,
            String reportId);

    /**
     * 删除收费项目
     * 
     * @author mlq
     * @param reportId
     * @param inspectId
     */
    void deleteDiagnosisHospitalInspectPay(String reportId, String inspectId);

    /**
     * 根据问诊id查询检验项目信息
     * 
     * @author mlq
     * @param diagnosisId
     * @return
     */
    List<DiagnosisHospitalItemPojo> queryDiagnosisItemsByDiagnosisId(String diagnosisId);

    /**
     * 根据报告单id查询检验项目信息
     * 
     * @author mlq
     * @param reportId
     * @return
     */
    List<DiagnosisHospitalItemPojo> queryDiagnosisItems(String reportId);

    /**
     * 批量添加检验项目
     * 
     * @author mlq
     * @param detailList
     * @param custId
     * @param reportId
     * @param inspectId
     */
    List<DiagnosisHospitalItem> addPregDiagnosisItems(List<DiagnosisHospitalItem> itemList, String reportId);

    /**
     * 更新检验项目
     * 
     * @author mlq
     * @param inspect
     */
    void updateDiagnosisHospitalItem(DiagnosisHospitalItem inspect);

    /**
     * 根据主键删除检验项目
     * 
     * @author mlq
     * @param id
     */
    void deleteDiagnosisHospitalItemById(String id);

    /**
     * 删除检验项目
     * 
     * @author mlq
     * @param reportId
     * @param inspectId
     * @param itemId
     */
    void deleteDiagnosisHospitalItem(String reportId, String inspectId, String itemId);

    /**
     * 删除未勾选的检验项目
     * 
     * @author mlq
     * @param custId
     * @param diagnosisId
     * @param ids
     */
    void deleteDiagnosisHospitalItems(String custId, String diagnosisId, String ids);

    /**
     * 删除空的收费项目
     * 
     * @author mlq
     * @param custId
     * @param diagnosisId
     */
    void deleteDiagnosisHospitalEmptyPay(String custId, String diagnosisId);

    /**
     * 删除空的报告单
     * 
     * @author mlq
     * @param diagnosisId
     */
    void deleteDiagnosisHospitalEmptyReport(String diagnosisId);

    /**
     * 查询上次接诊的所有收费项目
     * 
     * @author mlq
     * @param diagnosisId
     */
    List<DiagnosisHospitalInspectPayPojo> queryLastDiagnosisInspects(String diagnosisId);

    // ===================================================【历史检验数据查询】=======================================================

    /**
     * 条件检索历史检验项目
     * 
     * @author zcq
     * @param condition
     * @return
     */
    List<DiagnosisHospitalItemPojo> queryDiagnosisHospitalItemByCondition(DiagnosisHospitalItemCondition condition);

    /**
     * 条件检索历史检验报告
     * 
     * @author zcq
     * @param condition
     * @return
     */
    List<DiagnosisHospitalInspectReportPojo> queryDiagnosisHospitalInspectReportByCondition(
            DiagnosisHospitalInspectReportCondition condition);

    /**
     * 条件检索检验项目(统计)
     * 
     * @author mlq
     * @param condition
     * @return
     */
    List<DiagnosisHospitalItemPojo> queryStatisticHospitalItemByCondition(DiagnosisHospitalItemCondition condition);

}

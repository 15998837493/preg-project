
package com.mnt.preg.platform.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import com.mnt.preg.examitem.pojo.ExamResultRecordPojo;
import com.mnt.preg.master.pojo.items.HospitalInspectPayPojo;
import com.mnt.preg.master.pojo.items.ItemPojo;
import com.mnt.preg.platform.condition.DiagnosisBookingCondition;
import com.mnt.preg.platform.condition.DiagnosisCondition;
import com.mnt.preg.platform.condition.DiagnosisItemsCondition;
import com.mnt.preg.platform.condition.DiagnosisObstetricalCondition;
import com.mnt.preg.platform.condition.DiagnosisPrescriptionCondition;
import com.mnt.preg.platform.condition.DiagnosisQuotaItemCondition;
import com.mnt.preg.platform.entity.DiagnosisBooking;
import com.mnt.preg.platform.entity.DiagnosisQuotaItem;
import com.mnt.preg.platform.entity.PregDiagnosis;
import com.mnt.preg.platform.entity.PregDiagnosisExtender;
import com.mnt.preg.platform.entity.PregDiagnosisItems;
import com.mnt.preg.platform.entity.PregDiagnosisObstetrical;
import com.mnt.preg.platform.pojo.DiagnosisBookingPojo;
import com.mnt.preg.platform.pojo.DiagnosisPrescriptionPojo;
import com.mnt.preg.platform.pojo.DiagnosisQuotaItemVo;
import com.mnt.preg.platform.pojo.PregDiagnosisAnalysisPojo;
import com.mnt.preg.platform.pojo.PregDiagnosisExtenderPojo;
import com.mnt.preg.platform.pojo.PregDiagnosisItemsVo;
import com.mnt.preg.platform.pojo.PregDiagnosisObstetricalPojo;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;

/**
 * 诊疗服务
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-6-14 zcq 初版
 */
@Validated
public interface PregDiagnosisService {

    /**
     * 获取最近一次接诊信息
     * 
     * @author zcq
     * @param custId
     * @return
     */
    PregDiagnosisPojo getLastDiagnosis(String custId);

    /**
     * 添加诊疗登记
     * 
     * @author zcq
     * @param userDiagnosis
     * @return
     */
    String addDiagnosis(PregDiagnosis userDiagnosis);

    /**
     * 删除接诊相关的信息
     * 
     * @author zcq
     * @param diagnosisId
     */
    void deleteDiagnosisRelation(String diagnosisId);

    /**
     * 查询预约信息(根据主键)
     * 
     * @author dhs
     * @param bookingId
     */
    DiagnosisBookingPojo queryDiagnosisBooking(String bookingId);

    /**
     * 查询预约信息
     * 
     * @author dhs
     * @param diagnosisBooking
     */
    List<DiagnosisBookingPojo> queryDiagnosisBookings(DiagnosisBookingCondition condition);

    /**
     * 接诊表添加预约的信息（定时任务）
     * 
     * @author dhs
     * @param PregDiagnosis
     */
    void addDiagnosisTimer();

    /**
     * 添加预约信息
     * 
     * @author dhs
     * @param diagnosisBooking
     */
    String addDiagnosisBookDate(DiagnosisBooking diagnosisBooking);

    /**
     * 修改预约信息
     * 
     * @author dhs
     * @param diagnosisBooking
     */
    void updateDiagnosisBookDate(DiagnosisBooking diagnosisBooking);

    /**
     * 取消预约
     * 
     * @author dhs
     * @param bookingId
     */
    void deleteBooking(String bookingId);

    /**
     * 修改诊疗登记
     * 
     * @author zcq
     * @param userDiagnosis
     */
    void updateDiagnosis(PregDiagnosis userDiagnosis);

    /**
     * 修改体重为空
     * 
     * @author dhs
     * @param diagnosisId
     */
    public void updateDiagnosisWeight(String diagnosisId);

    /**
     * 修改接诊信息为空
     * 
     * @author dhs
     * @param diagnosisId
     */
    public void updateDiagnosisObstetricalNull(PregDiagnosisObstetrical obstetrical);

    /**
     * 条件分页检索
     * 
     * @author zcq
     * @param condition
     * @return
     */
    List<PregDiagnosisPojo> queryDiagnosis(DiagnosisCondition condition);

    /**
     * 条件分页检索
     * 
     * @author zcq
     * @param condition
     * @return
     */
    List<PregDiagnosisPojo> queryDiagnosisMore(DiagnosisCondition condition);

    /**
     * 条件分页检索--根据实验室指标和检查项目
     * 
     * @author xdc
     * @param condition
     * @return
     */
    List<PregDiagnosisPojo> queryDiagnosisMoreEvaluate(DiagnosisCondition condition);

    /**
     * 条件分页检索--助理端患者一览
     * 
     * @author dhs
     * @param condition
     * @return
     */
    List<PregDiagnosisPojo> queryDiagnosisMoreEvaluateByOrder(DiagnosisCondition condition);

    /**
     * 获取接诊信息
     * 
     * @author zcq
     * @param diagnosisId
     * @return
     */
    PregDiagnosisPojo getDiagnosis(String diagnosisId);

    /**
     * 校验客户编码是否登记
     * 
     * @author zcq
     * @param custId
     * @param date
     * @return
     */
    Integer checkDiagnosisCustId(String custId, Date date);

    /**
     * 完善接诊信息
     * 
     * @author zcq
     * @param userDiagnosis
     * @return
     */
    PregDiagnosisPojo completeDiagnosis(String diagnosisId);

    // **************************************************【接诊表基本功能结束】*******************************************

    // **************************************************【产科信息】*******************************************
    /**
     * 添加产科信息
     * 
     * @author dhs
     * @param PregDiagnosisObstetrical
     * @return
     */
    String addDiagnosisObstetrical(PregDiagnosisObstetrical pregDiagnosisObstetrical);

    /**
     * 修改产科信息
     * 
     * @author dhs
     * @param pregDiagnosisObstetrical
     */
    void updateDiagnosisObstetrical(PregDiagnosisObstetrical pregDiagnosisObstetrical);

    /**
     * 根据条件检索
     * 
     * @author dhs
     * @param condition
     * @return
     */
    List<PregDiagnosisObstetricalPojo> queryDiagnosisObstetricals(DiagnosisObstetricalCondition condition);

    /**
     * 根据接诊ID检索
     * 
     * @author dhs
     * @param diagnosisId
     * @return
     */
    PregDiagnosisObstetricalPojo getObstetricalByDiagnosisId(String diagnosisId);

    // *******************************************【医学营养评价项目】******************************************
    /**
     * 保存检测项目结论
     * 
     * @author zcq
     * @param diagnosisId
     * @return
     */
    PregDiagnosisPojo saveDiagnosisItemResult(String diagnosisId);

    /**
     * 条件检索接诊项目
     * 
     * @author zcq
     * @param condition
     * @return
     */
    List<PregDiagnosisItemsVo> queryDiagnosisItem(DiagnosisItemsCondition condition);

    /**
     * 主键获取接诊检测项目信息
     * 
     * @author zcq
     * @param inspectId
     * @return
     */
    PregDiagnosisItemsVo getDiagnosisItem(@NotBlank String inspectId);

    /**
     * 根据返回码和检测项目编码查询接诊项目信息
     * 
     * @author zcq
     * @param inspectCode
     * @param inspectItem
     * @return
     */
    PregDiagnosisItemsVo getDiagnosisItemsByInspectCodeAndInspectItem(String inspectCode, String inspectItem);

    /**
     * 
     * 根据接诊单号和检测项目编码查询接诊项目记录
     * 
     * @author gss
     * @param diagnosisId
     *            接诊单号
     * @param inspectItem
     *            检测项目编码
     * @return
     */
    PregDiagnosisItemsVo getDiagnosisItemsByDiagnosisIdAndInspectItem(String diagnosisId, String inspectItem);

    /**
     * 添加接诊评价项目
     * 
     * @author zcq
     * @param diagnosisItem
     * @return
     */
    String addDiagnosisItem(PregDiagnosisItems diagnosisItem);

    /**
     * 保存接诊项目
     * 
     * @author zcq
     * @param diagnosisId
     * @param inspectItemList
     */
    void saveDiagnosisItems(String diagnosisId, List<String> inspectItemList);

    /**
     * 重新评估
     * 
     * @author zcq
     * @param inspectId
     */
    List<PregDiagnosisItemsVo> resetDiagnosisItem(String inspectId);

    /**
     * 修改接诊项目
     * 
     * @author zcq
     * @param diagnosisItem
     */
    void updateDiagnosisItem(PregDiagnosisItems diagnosisItem);

    /**
     * 更改评价项目状态
     * 
     * @author zcq
     * @param inspectCodeList
     * @param inspectStatus
     * @param diagnosisId
     */
    void updateDiagnosisItemInspectStatus(List<String> inspectCodeList, String inspectStatus, String diagnosisId);

    /**
     * 删除营养评价项目
     * 
     * @author zcq
     * @param inspectCodeList
     * @param diagnosisId
     */
    void deleteDiagnosisInspect(List<String> inspectCodeList, String diagnosisId);

    // --------------------------------------------【医学营养评价项目】-------------------------------------------

    // *******************************************【诊疗补充剂】******************************************

    /**
     * 计量评估--检索补充剂列表
     * 
     * @author zcq
     * @param diagnosisId
     * @return
     */
    List<PregDiagnosisExtenderPojo> queryDiagnosisExtenderByDiagnosisId(String diagnosisId);

    /**
     * 计量评估--保存补充剂信息
     * 
     * @author zcq
     * @param diagnosisId
     * @param diagnosisExtenderList
     */
    void saveDiagnosisExtender(String diagnosisId, List<PregDiagnosisExtender> diagnosisExtenderList);

    /**
     * 删除商品数据
     * 
     * @author xdc
     * @param diagnosisId
     */
    void deleteDiagnosisExtender(String diagnosisId);

    /**
     * 营养处方--检索补充剂列表
     * 
     * @author zcq
     * @param diagnosisId
     * @return
     */
    List<DiagnosisPrescriptionPojo> queryDiagnosisPrescriptionByDiagnosisId(String diagnosisId);

    /**
     * 营养处方--检索补充剂列表（统计）
     * 
     * @author mlq
     * @param condition
     * @return
     */
    List<DiagnosisPrescriptionPojo> queryDiagnosisPrescriptionByCondition(DiagnosisPrescriptionCondition condition);

    /**
     * 营养处方--检索补充剂列表（最大值）
     * 
     * @author mlq
     * @param condition
     * @return
     */
    DiagnosisPrescriptionPojo getMaxProductListByCondition(DiagnosisPrescriptionCondition condition);

    /**
     * 营养处方--保存补充剂信息
     * 
     * @author zcq
     * @param diagnosisId
     * @param diagnosisExtenderList
     */
    void saveDiagnosisPrescription(String diagnosisId, List<DiagnosisPrescriptionPojo> diagnosisExtenderList);

    /**
     * 营养处方--检索最近一次营养处方
     * 
     * @author zcq
     * @param custId
     * @return
     */
    List<DiagnosisPrescriptionPojo> queryLastDiagnosisPrescription(String custId);

    // --------------------------------------------【诊疗补充剂】-------------------------------------------

    // *******************************************【接诊检测--组合数据】***************************************

    /**
     * 获取本次接诊的诊断分析信息
     * 
     * @author zcq
     * @param diagnosisId
     * @return
     */
    Map<String, ExamResultRecordPojo> getDiseaseInspectResult(String diagnosisId);

    /**
     * 查询诊断分析信息
     * 
     * @author zcq
     * @param diagnosisId
     * @return
     */
    PregDiagnosisAnalysisPojo getDiagnosisAnalysis(String diagnosisId);

    // -------------------------------------------【接诊检测--组合数据】---------------------------------------
    /**
     * 批量保存患者辅助检测项目
     * 
     * @author Administrator
     * @param
     * @return
     */
    public String saveDiagnosisQuotaItem(List<DiagnosisQuotaItem> list, String diagnosisId);

    /**
     * 根据主键删除患者辅助检测项目
     * 
     * @author wsy
     * @param id
     * @return
     */
    public void deleteDiagnosisQuotaItem(String id);

    /**
     * 查询患者接诊辅助检测项目
     * 
     * @author wsy
     * @param condition
     * @return
     */
    public List<DiagnosisQuotaItemVo> queryDiagnosisQuotaItem(DiagnosisQuotaItemCondition condition);

    /*********************************************** 辅助检查 ***********************************************************/
    /**
     * 
     * 添加辅助检查项目
     * 
     * @author scd
     * @param hospitalInspect
     * @return
     */
    public String addDiagnosisQuotaItem(DiagnosisQuotaItem diagnosisQuotaItem);

    /**
     * 
     * 根据id获取辅助检查项目
     * 
     * @author scd
     * @param inspectItemId
     * @return
     */
    DiagnosisQuotaItem getDiagnosisQuotaItemById(String inspectItemId);

    /**
     * 删除辅助检查项目
     * 
     * @author scd
     * @param id
     */
    void removeQuotaItem(String id);

    /**
     * 
     * 根据诊断项目主键获取诊断项目配置的检查项目
     * 
     * @author scd
     * @param diseaseId
     * @return
     */
    List<HospitalInspectPayPojo> queryInspectConfig(String diseaseId);

    /**
     * 根据接诊Id查询其下辅助检查项目对应的指标
     * 
     * @author xdc
     * @param diagnosisId
     * @return
     */
    List<ItemPojo> queryQuotaItemByDiagnosisId(String diagnosisId);
}

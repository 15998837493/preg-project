
package com.mnt.preg.examitem.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mnt.health.core.exception.ServiceException;
import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.health.utils.pdf.ReportForm;
import com.mnt.preg.customer.mapping.CustomerMapping;
import com.mnt.preg.customer.mapping.CustomerPageName;
import com.mnt.preg.customer.pojo.CustomerPojo;
import com.mnt.preg.customer.service.CustomerService;
import com.mnt.preg.examitem.constant.ExamItemConstant;
import com.mnt.preg.examitem.entity.DietaryFrequencyItem;
import com.mnt.preg.examitem.entity.ExamResultRecord;
import com.mnt.preg.examitem.entity.LifeStyleDetail;
import com.mnt.preg.examitem.entity.LifeStyleRecord;
import com.mnt.preg.examitem.pojo.EvaluateFoodLibraryPojo;
import com.mnt.preg.examitem.pojo.LifeStyleQuestionAnswerPojo;
import com.mnt.preg.examitem.pojo.LifeStyleRecoedPojo;
import com.mnt.preg.examitem.pojo.PregDietReportPojo;
import com.mnt.preg.examitem.service.LifeStyleService;
import com.mnt.preg.examitem.util.ExamItemUtil;
import com.mnt.preg.examitem.util.ExamResultRecordUtil;
import com.mnt.preg.examitem.util.life.DietaryFrequencyAnalysisUtil;
import com.mnt.preg.main.results.ResultCode;
import com.mnt.preg.master.mapping.QuestionMapping;
import com.mnt.preg.platform.pojo.PregDiagnosisItemsVo;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;
import com.mnt.preg.platform.service.PregDiagnosisService;
import com.mnt.preg.web.constants.WebMsgConstant;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pdf.DietaryFrequencyPdf;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 膳食及生活方式评估Controller
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-17 zcq 初版
 */
@Controller
public class DietaryFrequencyController extends BaseController {

    @Resource
    private CustomerService customerService;

    @Resource
    private LifeStyleService lifeStyleService;

    @Resource
    private PregDiagnosisService diagnosisService;

    @Resource
    public ExamResultRecordUtil examResultRecordUtil;

    @Resource
    public ExamItemUtil examItemUtil;

    /**
     * 
     * 初始化膳食及生活方式问卷
     * 
     * @author mnt_zhangjing
     * @param custId
     * @param diagnosisId
     * @param model
     * @return
     */
    @RequestMapping(value = CustomerMapping.PREG_DIETARY_FREQUENCY)
    public String intLifeStyle(String custId, String diagnosisId, String id, Model model) {
        String recordId = this.addLifeStyleRecord(custId, diagnosisId);
        model.addAttribute("recordId", recordId);
        model.addAttribute("inspectId", id);
        model.addAttribute("diagnosisId", diagnosisId);
        String custName = customerService.getCustomer(custId).getCustName();
        model.addAttribute("custName", custName);
        return CustomerPageName.DIETARY_FREQUENCY;
    }

    /**
     * 保存膳食及生活方式问卷记录
     * 
     * @author mnt_zhangjing
     * @param diagnosisId
     * @param questionAnswers
     * @return
     */
    @RequestMapping(value = QuestionMapping.SAVE_DIETARY_FREQUENCY_QUESTION)
    public @ResponseBody
    WebResult<String> savelifeStyleQuestionInfo(String recordId, String diagnosisId, String questionAnswers) {
        WebResult<String> webResult = new WebResult<String>();
        Gson gson = new Gson();
        List<LifeStyleQuestionAnswerPojo> questionAnswerBos = gson.fromJson(questionAnswers,
                new TypeToken<List<LifeStyleQuestionAnswerPojo>>(){}.getType());
        this.saveLifeStyleRecordDetail(recordId, diagnosisId, questionAnswerBos);
        webResult.setSuc(recordId, WebMsgConstant.success_message);
        return webResult;
    }

    /**
     * 查看膳食分析报告
     * 
     * @author zcq
     * @param foodRecordId
     * @param diagnosisId
     * @param model
     * @return
     */
    @RequestMapping(value = QuestionMapping.DIETARY_FREQUENCY_REPOER)
    public @ResponseBody
    WebResult<String> getListStyleReport(String id) {
        WebResult<String> webResult = new WebResult<String>();
        webResult.setSuc(this.getLifeStyleReport(id));
        return webResult;
    }

    /********************************************** 辅助工具方法 ********************************************/

    /**
     * 添加问卷记录
     * 
     * @author xdc
     * @param custId
     * @param diagnosisId
     * @return
     */
    public String addLifeStyleRecord(String custId, String diagnosisId) {
        CustomerPojo customerVo = customerService.getCustomer(custId);
        if (customerVo == null) {
            return "";
        }
        LifeStyleRecord lifeStyleRecord = TransformerUtils.transformerProperties(LifeStyleRecord.class, customerVo);
        lifeStyleRecord.setCustAge(customerVo.getCustAge());
        lifeStyleRecord.setRecordDatetime(new Date());
        return lifeStyleService.addLifeStyleRecord(lifeStyleRecord);
    }

    public String getLifeStyleReport(String inspectId) {
        // 第一步：实例化对象
        DietaryFrequencyPdf lifePdf = new DietaryFrequencyPdf(){

            @Override
            public PregDietReportPojo beforeCreatePdf(ReportForm reportForm) {
                PregDietReportPojo dietReportVo = new PregDietReportPojo();
                // 获取分析结果信息
                PregDiagnosisItemsVo diagnosisItem = diagnosisService.getDiagnosisItem(reportForm.getReportCode());
                String resultCode = diagnosisItem.getResultCode();
                PregDiagnosisPojo diagnosis = diagnosisService.getDiagnosis(diagnosisItem.getDiagnosisId());
                dietReportVo.setDiagnosis(diagnosis);
                dietReportVo.setDiagnosisItem(diagnosisItem);
                dietReportVo.setCustomer(customerService.getCustomer(diagnosis.getDiagnosisCustomer()));
                dietReportVo.setPregnancyArchives(pregArchivesService.getLastPregnancyArchive(diagnosis
                        .getDiagnosisCustomer()));
                dietReportVo.setExamMap(examItemUtil.queryExamItemMapByResultCode(
                        ExamItemConstant.EXAM_ITEM_TABLE.B00008, resultCode));// 指标结果
                dietReportVo.setInsId(getLoginUser().getUserPojo().getCreateInsId());// 机构号
                return dietReportVo;
            }
        };
        // 第二步：创建PDF
        String lifePdfpath = lifePdf.create(inspectId);

        return lifePdfpath;
    }

    /**
     * 数据分析
     * 
     * @author xdc
     * @param recordId
     * @param diagnosisId
     * @param lifeStyleQuestionAnswerBos
     */
    public String saveLifeStyleRecordDetail(String recordId, String diagnosisId,
            List<LifeStyleQuestionAnswerPojo> lifeStyleQuestionAnswerBos) {
        if (CollectionUtils.isEmpty(lifeStyleQuestionAnswerBos)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }

        LifeStyleRecoedPojo lifeStyleRecoedVo = lifeStyleService.getLifeStyleRecoed(recordId);
        if (lifeStyleRecoedVo == null) {
            throw new ServiceException(ResultCode.ERROR_90001);
        }
        // 查询诊疗信息
        PregDiagnosisPojo diagnosis = diagnosisService.getDiagnosis(diagnosisId);
        if (diagnosis == null) {
            throw new ServiceException(ResultCode.ERROR_90001);
        }
        // 查询评估食物库信息
        List<EvaluateFoodLibraryPojo> evaluateFoodLibraryVos = foodService.queryEvaluateFoodLibrary();
        // 保存记录的明细，答案记录
        List<LifeStyleDetail> lifeStyleDetails = TransformerUtils.transformerList(LifeStyleDetail.class,
                lifeStyleQuestionAnswerBos);

        // 保存报告分析结果
        List<DietaryFrequencyItem> lifeStyleItems = new ArrayList<DietaryFrequencyItem>();

        // 实例化膳食及生活方式分析工具
        DietaryFrequencyAnalysisUtil analysisUtil = new DietaryFrequencyAnalysisUtil(lifeStyleQuestionAnswerBos,
                evaluateFoodLibraryVos, recordId);

        // 保存分析结果总表记录
        ExamResultRecord examResultRecord = new ExamResultRecord();
        examResultRecord.setCustId(lifeStyleRecoedVo.getCustId());
        examResultRecord.setExamCode(recordId);
        examResultRecord.setExamDate(new Date());
        examResultRecord.setExamCategory(ExamItemConstant.EXAM_ITEM_CODE.B00008);
        String examId = examResultRecordUtil.addExamResultRecord(examResultRecord);

        // 水
        lifeStyleItems.add(analysisUtil.getIntakeWater(examId, diagnosis.getDiagnosisPregnantStage()));
        // 油
        lifeStyleItems.add(analysisUtil.getIntakeOil(examId));
        // 谷类
        lifeStyleItems.add(analysisUtil.getIntakeCereal(examId, diagnosis.getDiagnosisPregnantStage()));
        // 薯类
        lifeStyleItems.add(analysisUtil.getIntakePotatoes(examId, diagnosis.getDiagnosisPregnantStage()));
        // 蔬菜
        lifeStyleItems.add(analysisUtil.getIntakeVeg(examId, diagnosis.getDiagnosisPregnantStage()));
        // 水果
        lifeStyleItems.add(analysisUtil.getIntakeFruit(examId, diagnosis.getDiagnosisPregnantStage()));
        // 鱼肉蛋
        lifeStyleItems.add(analysisUtil.getIntakeFme(examId, diagnosis.getDiagnosisPregnantStage()));
        // 乳类
        lifeStyleItems.add(analysisUtil.getIntakeMilk(examId, diagnosis.getDiagnosisPregnantStage()));
        // 豆类
        lifeStyleItems.add(analysisUtil.getIntakeBean(examId, diagnosis.getDiagnosisPregnantStage()));
        // 坚果类
        lifeStyleItems.add(analysisUtil.getIntakeNut(examId, diagnosis.getDiagnosisPregnantStage()));
        // 动物内脏
        lifeStyleItems.add(analysisUtil.getIntakeOrgans(examId));
        // 菌类
        // lifeStyleItems.add(analysisUtil.getIntakeFungus(examId));
        // 藻类
        // lifeStyleItems.add(analysisUtil.getIntakeAlgae(examId));
        // 粗杂粮
        lifeStyleItems.add(analysisUtil.getIntakeCZL(examId));
        // 绿叶蔬菜
        lifeStyleItems.add(analysisUtil.getIntakesVegetables(examId));
        // 优质蛋白质
        lifeStyleItems.add(analysisUtil.getIntakesGoodProtid(examId));
        // 荤素比
        lifeStyleItems.add(analysisUtil.getIntakeMeatRatio(examId));
        // 实际一日总能量
        lifeStyleItems.add(analysisUtil.getActualEnergy(examId, diagnosis.getDiagnosisCustHeight(),
                diagnosis.getDiagnosisPregnantStage()));
        // 普通食物能量占比
        lifeStyleItems.add(analysisUtil.getEnergyPrec(examId, diagnosis.getDiagnosisCustHeight(),
                diagnosis.getDiagnosisPregnantStage()));
        // 滋补品食物能量占比
        lifeStyleItems.add(analysisUtil.getEnergyYingyang(examId, diagnosis.getDiagnosisCustHeight(),
                diagnosis.getDiagnosisPregnantStage()));
        // 零食食物能量占比
        lifeStyleItems.add(analysisUtil.getEnergyLingshi(examId, diagnosis.getDiagnosisCustHeight(),
                diagnosis.getDiagnosisPregnantStage()));
        // 碳水化合物摄入量
        lifeStyleItems.add(analysisUtil.getIntakeCbr(examId, diagnosis.getDiagnosisCustHeight(),
                diagnosis.getDiagnosisPregnantStage()));
        // 蛋白质摄入量
        lifeStyleItems.add(analysisUtil.getIntakeProtid(examId, diagnosis.getDiagnosisCustWeight()));
        // 脂肪摄入量
        lifeStyleItems.add(analysisUtil.getIntakeFat(examId, diagnosis.getDiagnosisCustHeight(),
                diagnosis.getDiagnosisPregnantStage()));
        // 碳水化合物供能比
        lifeStyleItems.add(analysisUtil.getIntakeCbrRatio(examId));
        // 蛋白质供能比
        lifeStyleItems.add(analysisUtil.getIntakeProtidRatio(examId));
        // 脂肪供能比
        lifeStyleItems.add(analysisUtil.getIntakeFatRatio(examId));
        // 摄入不足的元素
        lifeStyleItems.add(analysisUtil.analysisLackingElement(examId, diagnosis.getDiagnosisPregnantStage(),
                diagnosis.getDiagnosisCustWeight()));

        // 摄入频率记录（命名的序号和pdf的顺序对应）
        lifeStyleItems.add(analysisUtil.analysisFre1(examId, diagnosis.getDiagnosisPregnantStage()));
        lifeStyleItems.add(analysisUtil.analysisFre2(examId, diagnosis.getDiagnosisPregnantStage()));
        lifeStyleItems.add(analysisUtil.analysisFre3(examId, diagnosis.getDiagnosisPregnantStage()));
        lifeStyleItems.add(analysisUtil.analysisFre4(examId, diagnosis.getDiagnosisPregnantStage()));
        lifeStyleItems.add(analysisUtil.analysisFre5(examId, diagnosis.getDiagnosisPregnantStage()));
        lifeStyleItems.add(analysisUtil.analysisFre6(examId, diagnosis.getDiagnosisPregnantStage()));
        lifeStyleItems.add(analysisUtil.analysisFre7(examId, diagnosis.getDiagnosisPregnantStage()));
        lifeStyleItems.add(analysisUtil.analysisFre8(examId, diagnosis.getDiagnosisPregnantStage()));
        lifeStyleItems.add(analysisUtil.analysisFre9(examId, diagnosis.getDiagnosisPregnantStage()));
        lifeStyleItems.add(analysisUtil.analysisFre10(examId, diagnosis.getDiagnosisPregnantStage()));
        lifeStyleItems.add(analysisUtil.analysisFre11(examId, diagnosis.getDiagnosisPregnantStage()));
        lifeStyleItems.add(analysisUtil.analysisFre12(examId, diagnosis.getDiagnosisPregnantStage()));
        lifeStyleItems.add(analysisUtil.analysisFre13(examId, diagnosis.getDiagnosisPregnantStage()));
        lifeStyleItems.add(analysisUtil.analysisFre14(examId, diagnosis.getDiagnosisPregnantStage()));
        lifeStyleItems.add(analysisUtil.analysisFre15(examId, diagnosis.getDiagnosisPregnantStage()));
        lifeStyleItems.add(analysisUtil.analysisFre16(examId, diagnosis.getDiagnosisPregnantStage()));
        lifeStyleItems.add(analysisUtil.analysisFre17(examId, diagnosis.getDiagnosisPregnantStage()));
        lifeStyleItems.add(analysisUtil.analysisFre18(examId, diagnosis.getDiagnosisPregnantStage()));

        // 营养食品/医学食品/特膳食品
        lifeStyleItems.add(analysisUtil.analysisYingyangFood(examId, diagnosis.getDiagnosisPregnantStage()));

        // 保存分析结果
        lifeStyleService.saveDietaryRecoedDetail(examId, lifeStyleDetails, lifeStyleItems);

        // 更新记录表结论
        examResultRecordUtil.updateExamResultRecordForExam(examResultRecord, ExamItemConstant.EXAM_ITEM_TABLE.B00008);

        return examId;
    }
}

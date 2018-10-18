
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
import com.mnt.inbody.results.ResultCode;
import com.mnt.preg.customer.mapping.CustomerMapping;
import com.mnt.preg.customer.mapping.CustomerPageName;
import com.mnt.preg.customer.pojo.CustomerPojo;
import com.mnt.preg.customer.service.CustomerService;
import com.mnt.preg.examitem.constant.ExamItemConstant;
import com.mnt.preg.examitem.entity.ExamResultRecord;
import com.mnt.preg.examitem.entity.LifeStyleDetail;
import com.mnt.preg.examitem.entity.LifeStyleItem;
import com.mnt.preg.examitem.entity.LifeStyleRecord;
import com.mnt.preg.examitem.pojo.EvaluateFoodLibraryPojo;
import com.mnt.preg.examitem.pojo.LifeStyleQuestionAnswerPojo;
import com.mnt.preg.examitem.pojo.LifeStyleRecoedPojo;
import com.mnt.preg.examitem.pojo.PregDietReportPojo;
import com.mnt.preg.examitem.service.LifeStyleService;
import com.mnt.preg.examitem.util.ExamItemUtil;
import com.mnt.preg.examitem.util.ExamResultRecordUtil;
import com.mnt.preg.examitem.util.life.LifeStyleAnalysisUtil;
import com.mnt.preg.master.mapping.QuestionMapping;
import com.mnt.preg.platform.pojo.PregDiagnosisItemsVo;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;
import com.mnt.preg.platform.service.PregDiagnosisService;
import com.mnt.preg.web.constants.WebMsgConstant;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pdf.PregnancyLifeStylePdf;
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
public class LifeStyleController extends BaseController {

    @Resource
    private CustomerService customerService;

    @Resource
    private LifeStyleService lifeStyleService;

    @Resource
    private PregDiagnosisService diagnosisService;

    @Resource
    private ExamItemUtil examItemUtil;

    @Resource
    public ExamResultRecordUtil examResultRecordUtil;

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
    @RequestMapping(value = CustomerMapping.INIT_LIFE_STYLE)
    public String intLifeStyle(String custId, String diagnosisId, String id, Model model) {
        String recordId = this.addLifeStyleRecord(custId, diagnosisId);
        model.addAttribute("recordId", recordId);
        model.addAttribute("inspectId", id);
        model.addAttribute("diagnosisId", diagnosisId);
        String custName = customerService.getCustomer(custId).getCustName();
        model.addAttribute("custName", custName);
        return CustomerPageName.LIFE_STYLE;
    }

    /**
     * 保存膳食及生活方式问卷记录
     * 
     * @author mnt_zhangjing
     * @param diagnosisId
     * @param questionAnswers
     * @return
     */
    @RequestMapping(value = QuestionMapping.SAVE_LIFE_STYLE_QUESTION)
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
    @RequestMapping(value = QuestionMapping.LIFE_STYLE_REPOER)
    public @ResponseBody
    WebResult<String> getListStyleReport(String id) {
        WebResult<String> webResult = new WebResult<String>();
        webResult.setSuc(this.getLifeStyleReport(id));
        return webResult;
    }

    /********************************************** 原business方法 ********************************************/

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
        PregnancyLifeStylePdf lifePdf = new PregnancyLifeStylePdf(){

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
                dietReportVo.setExamMap(examItemUtil.queryExamItemMapByResultCode(ExamItemConstant.EXAM_ITEM_TABLE.B00002,
                        resultCode));// 指标结果
                dietReportVo.setInsId(getLoginUser().getUserPojo().getCreateInsId());// 机构号
                return dietReportVo;
            }
        };
        // 第二步：创建PDF
        String lifePdfpath = lifePdf.create(inspectId);

        return lifePdfpath;
    }

    public void saveLifeStyleRecordDetail(String recordId, String diagnosisId,
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
        // 保存记录的明细
        List<LifeStyleDetail> lifeStyleDetails = TransformerUtils.transformerList(LifeStyleDetail.class,
                lifeStyleQuestionAnswerBos);

        // 保存报告分析结果
        List<LifeStyleItem> lifeStyleItems = new ArrayList<LifeStyleItem>();

        // 保存分析结果总表记录
        ExamResultRecord examResultRecord = new ExamResultRecord();
        examResultRecord.setCustId(lifeStyleRecoedVo.getCustId());
        examResultRecord.setExamCode(recordId);
        examResultRecord.setExamDate(new Date());
        examResultRecord.setExamCategory(ExamItemConstant.EXAM_ITEM_CODE.B00002);
        String examId = examResultRecordUtil.addExamResultRecordForExam(examResultRecord,
                ExamItemConstant.EXAM_ITEM_TABLE.B00002);

        // 实例化膳食及生活方式分析工具
        LifeStyleAnalysisUtil analysisUtil = new LifeStyleAnalysisUtil(lifeStyleQuestionAnswerBos,
                evaluateFoodLibraryVos, examId);
        // 设置生活方式调查-饮食
        lifeStyleItems.add(analysisUtil.analysisDiet(examId));
        // 设置生活方式调查-运动
        lifeStyleItems.add(analysisUtil.analysisSport(examId));
        // 设置生活方式调查-睡眠
        lifeStyleItems.add(analysisUtil.analysisSleep(examId));
        // 设置生活方式调查-心理
        lifeStyleItems.add(analysisUtil.analysisMental(examId));
        // 设置生活方式调查-烟酒
        lifeStyleItems.add(analysisUtil.analysisDrinking(examId));
        // 设置生活方式调查-环境
        lifeStyleItems.add(analysisUtil.analysisEnvironment(examId));
        // 就餐时间-早餐
        lifeStyleItems.add(analysisUtil.getBreakfastTime(examId));
        // 就餐时间-上午加餐
        lifeStyleItems.add(analysisUtil.getMorningMealTime(examId));
        // 就餐时间-午餐
        lifeStyleItems.add(analysisUtil.getLunchTime(examId));
        // 就餐时间-下午加餐
        lifeStyleItems.add(analysisUtil.getAfternoonSnacksTime(examId));
        // 就餐时间-晚餐
        lifeStyleItems.add(analysisUtil.getDinnerTime(examId));
        // 就餐时间-夜宵
        lifeStyleItems.add(analysisUtil.getSupperTime(examId));
        // 摄入不足的元素
        lifeStyleItems.add(analysisUtil.analysisLackingElement(examId, diagnosis.getDiagnosisPregnantStage()));
        // 实际一日总能量
        lifeStyleItems.add(analysisUtil.getActualEnergy(examId, diagnosis.getDiagnosisCustHeight(),
                diagnosis.getDiagnosisPregnantStage()));
        // 推荐一日总能量
        lifeStyleItems.add(analysisUtil.getRecommendEnergy(examId, diagnosis.getDiagnosisCustHeight(),
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
        // 水
        lifeStyleItems.add(analysisUtil.getIntakeWater(examId, diagnosis.getDiagnosisPregnantStage()));
        // 粗杂粮
        lifeStyleItems.add(analysisUtil.getIntakeCZL(examId));
        // 绿叶蔬菜
        lifeStyleItems.add(analysisUtil.getIntakesVegetables(examId));
        // 优质蛋白质
        lifeStyleItems.add(analysisUtil.getIntakesGoodProtid(examId));
        // 动物内脏
        lifeStyleItems.add(analysisUtil.getIntakeOrgans(examId));
        // 用油量
        lifeStyleItems.add(analysisUtil.getIntakeOil(examId));
        // 海参
        lifeStyleItems.add(analysisUtil.getIntakeHS(examId));
        // 燕窝
        lifeStyleItems.add(analysisUtil.getIntakeYW(examId));
        // 饼干点心
        lifeStyleItems.add(analysisUtil.getIntakeBGDX(examId));
        // 蛋糕
        lifeStyleItems.add(analysisUtil.getIntakeDG(examId));
        // 起酥面包
        lifeStyleItems.add(analysisUtil.getIntakeQSMB(examId));
        // 海苔
        lifeStyleItems.add(analysisUtil.getIntakeHT(examId));
        // 糖果
        lifeStyleItems.add(analysisUtil.getIntakeTG(examId));
        // 蜜饯
        lifeStyleItems.add(analysisUtil.getIntakeMJ(examId));
        // 巧克力
        lifeStyleItems.add(analysisUtil.getIntakeQKL(examId));
        // 碳酸饮料
        lifeStyleItems.add(analysisUtil.getIntakeTSYL(examId));
        // 冷饮雪糕
        lifeStyleItems.add(analysisUtil.getIntakeLYXG(examId));
        // 膨化食品
        lifeStyleItems.add(analysisUtil.getIntakePHSP(examId));
        // 蜂蜜
        lifeStyleItems.add(analysisUtil.getIntakeFM(examId));
        // 菌类
        lifeStyleItems.add(analysisUtil.getIntakeFungus(examId));
        // 藻类
        lifeStyleItems.add(analysisUtil.getIntakeAlgae(examId));

        // 记录非报告指标
        analysisUtil.addP101Result();
        analysisUtil.addP102Result();
        analysisUtil.addP103Result();
        analysisUtil.addP104Result();
        analysisUtil.addP105Result();
        analysisUtil.addP106Result();
        analysisUtil.addP107Result();
        analysisUtil.addP108Result();
        analysisUtil.addP109Result();
        analysisUtil.addP201Result();
        analysisUtil.addP202Result();
        analysisUtil.addP204Result();
        analysisUtil.addP205Result();
        analysisUtil.addP301Result();
        analysisUtil.addP302Result();
        analysisUtil.addP303Result();
        analysisUtil.addP401Result();
        analysisUtil.addP501Result();
        analysisUtil.addP502Result();
        analysisUtil.addP601Result();
        analysisUtil.addP602Result();

        // 将非报告指标添加进来
        lifeStyleItems.addAll(analysisUtil.getLifeStyleItems());

        // 保存分析结果
        lifeStyleService.saveLifeStyleRecoedDetail(examId, lifeStyleDetails, lifeStyleItems);

        // 更新结论
        examResultRecordUtil.updateExamResultRecordForExam(examResultRecord,
                ExamItemConstant.EXAM_ITEM_TABLE.B00002);
    }

}

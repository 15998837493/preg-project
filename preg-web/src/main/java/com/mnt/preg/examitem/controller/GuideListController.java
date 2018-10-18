
package com.mnt.preg.examitem.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.utils.pdf.ReportForm;
import com.mnt.preg.customer.mapping.CustomerMapping;
import com.mnt.preg.customer.pojo.PregArchivesPojo;
import com.mnt.preg.examitem.constant.ExamItemConstant;
import com.mnt.preg.examitem.pojo.ExamItemPojo;
import com.mnt.preg.examitem.pojo.GuideListReportPojo;
import com.mnt.preg.examitem.pojo.NutritiousReportPojo;
import com.mnt.preg.examitem.service.GuideListService;
import com.mnt.preg.examitem.service.NutritiousSurveyService;
import com.mnt.preg.examitem.util.ExamItemUtil;
import com.mnt.preg.platform.entity.PregArchives;
import com.mnt.preg.platform.pojo.PregDiagnosisItemsVo;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;
import com.mnt.preg.platform.service.PregDiagnosisService;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pdf.GuideListPdf;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 营养评价报告单
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-4-11 zcq 初版
 */
@Controller
public class GuideListController extends BaseController {

    @Resource
    private PregDiagnosisService diagnosisService;

    @Resource
    public ExamItemUtil examItemUtil;

    @Resource
    public NutritiousSurveyService nutritiousSurveyService;

    @Resource
    public GuideListService guideListService;

    /**
     * 引导单
     * 
     * @author dhs
     * @param codes
     * @param diagnosisId
     * @param files
     * @param index
     */
    @RequestMapping(value = CustomerMapping.EXAM_GUIDE_LIST)
    public @ResponseBody
    WebResult<String> createGuideListPdf(String id) {
        WebResult<String> webResult = new WebResult<String>();
        String insId = this.getLoginUser().getUserPojo().getCreateInsId();
        ReportForm diagnosisForm = new ReportForm();
        diagnosisForm.setInsId(insId);
        diagnosisForm.setReportCode(id);
        diagnosisForm.setReportItem("P08001");

        GuideListPdf pdf = new GuideListPdf(){

            @Override
            public GuideListReportPojo beforeCreatePdf(ReportForm reportForm) {
                PregDiagnosisPojo pregDiagnosisPojo = diagnosisService.getDiagnosis(reportForm.getReportCode());
                PregArchivesPojo preArchive = pregArchivesService.getLastPregnancyArchive(pregDiagnosisPojo
                        .getDiagnosisCustomer());
                // 查询引导单记录表是否有记录
                Map<String, ExamItemPojo> map_guide = examItemUtil.queryExamItemMapByExamId(
                        ExamItemConstant.EXAM_ITEM_TABLE.guide, preArchive.getId());
                boolean createPdf = false;
                if (map_guide.size() > 0) {// 不是首次
                    createPdf = true;
                }
                PregArchives pregnancyArchives = new PregArchives();
                pregnancyArchives.setCreatePdf(createPdf);
                pregnancyArchives.setId(preArchive.getId());
                pregArchivesService.updatePregnancyArchives(pregnancyArchives);// 更改引导单pdf状态
                PregDiagnosisItemsVo item = diagnosisService.getDiagnosisItemsByDiagnosisIdAndInspectItem(
                        reportForm.getReportCode(), ExamItemConstant.EXAM_ITEM_CODE.B00003);// 人体成分
                NutritiousReportPojo nutritiousReportPojo = new NutritiousReportPojo();// 快速营养调查问卷数据
                PregDiagnosisItemsVo itemNutritious = diagnosisService
                        .getDiagnosisItemsByDiagnosisIdAndInspectItem(
                                reportForm.getReportCode(), ExamItemConstant.EXAM_ITEM_CODE.B00006);
                if (itemNutritious == null || StringUtils.isEmpty(itemNutritious.getResultCode())) {// 如果当前没有做快速营养调查问卷，就去取引导单记录表中快速营养调查问卷的数据
                    // 如果inspectCode为空，createPdf就一定会为true(map_guide不会为空)，否则也进入不到这个方法中，如果出问题，问题就在jsp打印置灰控制中
                    nutritiousReportPojo.setStaminaRup(map_guide.get("A00006").getItemString());// 体能消耗问题
                    nutritiousReportPojo.setFoodRisk(map_guide.get("A00007").getItemString());// 膳食结构风险
                    nutritiousReportPojo.setIllRisk(map_guide.get("A00008").getItemString());// 致病饮食风险
                    nutritiousReportPojo.setFoodQuestion(map_guide.get("A00011").getItemString());// 膳食问题
                    nutritiousReportPojo.setNutritionalRequirements(map_guide.get("A00009").getItemString());// 营养咨询需求
                } else {// 如果做了快速营养调查问卷，就现分析数据
                    nutritiousReportPojo = nutritiousSurveyService.analyseNutritious(itemNutritious
                            .getResultCode());
                    // 将体能消耗问题和运动消耗问题合并，都是必填项没有为空的情况
                    nutritiousReportPojo.setStaminaRup(nutritiousReportPojo.getStaminaRup() + ";"
                            + nutritiousReportPojo.getSportRup());
                }
                Map<String, ExamItemPojo> map = null;
                if (item != null && StringUtils.isNotBlank(item.getResultCode())) {
                    map = examItemUtil.queryExamItemMapByResultCode(ExamItemConstant.EXAM_ITEM_TABLE.B00003,
                            item.getResultCode());// 人体成分明细
                }
                GuideListReportPojo pojo = guideListService.analyseGuideList(reportForm.getReportCode(),
                        preArchive.getQuestionAllocId(), item,
                        map, nutritiousReportPojo);
                pojo.setDiagnosis(pregDiagnosisPojo);
                pojo.setCustomer(customerService.getCustomer(pregDiagnosisPojo.getDiagnosisCustomer()));
                pojo.setPregnancyArchives(preArchive);
                pojo.setInsId(getLoginUser().getUserPojo().getCreateInsId());// 机构号
                // 保存到结果表
                List<ExamItemPojo> detailList = new ArrayList<ExamItemPojo>();// 结果表明细
                if (StringUtils.isBlank(pojo.getInbody())) {
                    pojo.setInbody("无");
                }
                examItemUtil.deleteExamItem(ExamItemConstant.EXAM_ITEM_TABLE.guide, preArchive.getId());// 清空本次接诊下的结果表
                detailList.add(getExamDataDetail("A00001", "代谢异常风险因素_病史", null, pojo.getMetabolicBingshi(), null,
                        null, null));
                detailList.add(getExamDataDetail("A00002", "代谢异常风险因素_既往妊娠并发症及分娩史", null,
                        pojo.getMetabolicHistory(), null, null, null));
                detailList.add(getExamDataDetail("A00003", "营养不良风险因素_病史", null, pojo.getNutritiousBingshi(), null,
                        null, null));
                detailList.add(getExamDataDetail("A00004", "营养不良风险因素_既往妊娠并发症及分娩史", null,
                        pojo.getNutritiousHistory(), null, null, null));
                detailList.add(getExamDataDetail("A00005", "人体成分结论", null, pojo.getInbody(), null, null, null));
                detailList.add(getExamDataDetail("A00006", "体能消耗情况", null, pojo.getRup(), null, null, null));
                detailList.add(getExamDataDetail("A00007", "膳食结构风险", null, pojo.getFoodRisk(), null, null, null));
                detailList.add(getExamDataDetail("A00008", "致病饮食风险", null, pojo.getIllRisk(), null, null, null));
                detailList.add(getExamDataDetail("A00009", "营养咨询需求", null, pojo.getNutritionalRequirements(), null,
                        null, null));
                detailList.add(getExamDataDetail("A00010", "过敏情况", null, pojo.getAllergic(), null, null, null));
                detailList.add(getExamDataDetail("A00011", "妊娠期膳食摄入问题 ", null, pojo.getFoodQuestion(), null, null,
                        null));
                detailList.add(getExamDataDetail("A00012", "转诊建议", null, pojo.getSuggest(), null, null, null));
                examItemUtil.addExamItems(ExamItemConstant.EXAM_ITEM_TABLE.guide, preArchive.getId(), detailList);// 保存
                pojo.setInsId(getLoginUser().getUserPojo().getCreateInsId());// 机构号
                return pojo;
            }
        };
        webResult.setSuc(pdf.create(diagnosisForm));
        return webResult;
    }

    /**
     * 分配结果表明细
     * 
     * @author dhs
     * @param
     * @return
     */
    private ExamItemPojo getExamDataDetail(String itemCode, String itemName, String itemRefValue, String itemString,
            String itemResult, String itemCompare, String itemUnit) {
        ExamItemPojo detail = new ExamItemPojo();
        detail.setItemCode(itemCode);
        detail.setItemName(itemName);
        detail.setItemRefValue(itemRefValue);
        detail.setItemString(itemString);
        detail.setItemResult(itemResult);
        detail.setItemCompare(itemCompare);
        detail.setItemUnit(itemUnit);
        return detail;
    }

}

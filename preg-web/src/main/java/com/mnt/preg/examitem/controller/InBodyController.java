
package com.mnt.preg.examitem.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.health.utils.pdf.ReportForm;
import com.mnt.health.utils.times.JodaTimeTools;
import com.mnt.inbody.condition.InBodyCondition;
import com.mnt.inbody.pojo.InBodyCustVo;
import com.mnt.inbody.pojo.InBodyVo;
import com.mnt.preg.customer.pojo.CustomerPojo;
import com.mnt.preg.customer.pojo.PregArchivesPojo;
import com.mnt.preg.customer.service.CustomerService;
import com.mnt.preg.examitem.condition.InbodyCondition;
import com.mnt.preg.examitem.constant.ExamItemConstant;
import com.mnt.preg.examitem.entity.ExamResultRecord;
import com.mnt.preg.examitem.mapping.InBodyMapping;
import com.mnt.preg.examitem.mapping.InBodyPageName;
import com.mnt.preg.examitem.pojo.ExamItemPojo;
import com.mnt.preg.examitem.pojo.PregInBodyBcaPojo;
import com.mnt.preg.examitem.pojo.PregInBodyPojo;
import com.mnt.preg.examitem.service.PregInbodyService;
import com.mnt.preg.examitem.util.ExamItemUtil;
import com.mnt.preg.examitem.util.ExamResultRecordUtil;
import com.mnt.preg.platform.entity.PregDiagnosis;
import com.mnt.preg.platform.pojo.PregDiagnosisItemsVo;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;
import com.mnt.preg.platform.service.PregDiagnosisService;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pdf.InBodyPdf;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 人体成分Controller
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：v1.0 2014-12-31 zcq 初版
 */
@Controller
public class InBodyController extends BaseController {

    @Resource
    private CustomerService customerService;

    @Resource
    private PregDiagnosisService diagnosisService;

    @Resource
    private PregInbodyService pregInbodyService;

    @Resource
    private ExamItemUtil examItemUtil;

    @Resource
    private ExamResultRecordUtil examResultRecordUtil;

    /**
     * 跳转到人体成分客户选择页面
     * 
     * @author zcq
     * @return
     */
    @RequestMapping(value = InBodyMapping.INBODY_VIEW)
    public String toInBodyCustView(String custId, String id, String diagnosisId, Model model) {
        model.addAttribute("custInfo", customerService.getCustomer(custId));
        model.addAttribute("diagnosisId", diagnosisId);
        model.addAttribute("inspectId", id);
        return InBodyPageName.INBODY_VIEW;
    }

    /**
     * 检索人体成分客户列表
     * 
     * @author zcq
     * @param model
     * @return
     */
    @RequestMapping(value = InBodyMapping.INBODY_QUERY)
    public @ResponseBody
    WebResult<InBodyCustVo> queryInBodyCustomer(InBodyCondition condition) {
        WebResult<InBodyCustVo> webResult = new WebResult<InBodyCustVo>();
        if (StringUtils.isEmpty(condition.getUserExamDate())) {
            condition.setUserExamDate(JodaTimeTools.toString(new Date(), JodaTimeTools.FORMAT_6));
        }
        List<InBodyCustVo> list = inBodyService.queryInBodyCust(condition);
        if (list == null) {
            list = new ArrayList<InBodyCustVo>();
        }
        webResult.setData(list);

        return webResult;
    }

    /**
     * 同步数据查看人体成分报告
     * 
     * @author zcq
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = InBodyMapping.INBODY_ADD)
    public @ResponseBody
    WebResult<PregInBodyPojo> addInbody(InBodyCondition condition, String custId, String diagnosisId, Model model) {
        WebResult<PregInBodyPojo> webResult = new WebResult<PregInBodyPojo>();
        InBodyVo inbody = inBodyService.getInBody(condition);
        if (inbody.getTbwMax() == null || inbody.getTbwMin() == null) {
            inbody.setTbwMax(inbody.getEcwMax().add(inbody.getIcwMax()));
            inbody.setTbwMin(inbody.getEcwMin().add(inbody.getIcwMin()));
        }
        PregInBodyPojo groupBo = TransformerUtils.transformerProperties(PregInBodyPojo.class, inbody);
        groupBo.setDiagnosisId(diagnosisId);
        String userExamDate = JodaTimeTools.toString(groupBo.getExamDate(), JodaTimeTools.FORMAT_10);
        if (StringUtils.isNotEmpty(userExamDate)) {
            try {
                groupBo.setExamDate(JodaTimeTools.toDateTime(userExamDate, JodaTimeTools.FORMAT_10));
            } catch (ParseException e) {
                webResult.setError("日期类型转换异常");
            }
        }
        groupBo.setCustId(custId);
        groupBo.setIra1m(inbody.getIra1m());
        groupBo.setIla1m(inbody.getIla1m());
        groupBo.setIt1m(inbody.getIt1m());
        groupBo.setIrl1m(inbody.getIrl1m());
        groupBo.setIll1m(inbody.getIll1m());

        PregInBodyPojo result = new PregInBodyPojo();
        // 将人体成分的体重同步到接诊表，如果现体重还没有填就更新接诊时的现体重
        PregDiagnosisPojo diagPojo = diagnosisService.getDiagnosis(diagnosisId);
        if (diagPojo.getDiagnosisCustWeight() == null || diagPojo.getDiagnosisCustWeight().doubleValue() == 0) {
            diagPojo.setDiagnosisCustWeight(groupBo.getWt());
            PregDiagnosis diag = TransformerUtils.transformerProperties(PregDiagnosis.class, diagPojo);
            diagnosisService.updateDiagnosis(diag);
        }

        // 保存人体成分信息
        PregDiagnosisPojo updatePojo = diagnosisService.getDiagnosis(diagnosisId);
        result.setWt(updatePojo.getDiagnosisCustWeight());
        result.setBcaId(this.saveInbdoyRecord(groupBo));
        webResult.setSuc(result);
        return webResult;
    }

    /**
     * 同步数据查看人体成分报告
     * 
     * @author zcq
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = InBodyMapping.INBODY_REPORT)
    public @ResponseBody
    WebResult<String> getInbodyReport(String id) {
        WebResult<String> webResult = new WebResult<String>();
        webResult.setSuc(this.getInbodyReportBusiness(id));
        return webResult;
    }

    /************************************************************* 原business方法 ********************************************************/

    public String getInbodyReportBusiness(String inspectId) {
        // 第一步：实例化对象
        InBodyPdf pdf = new InBodyPdf(){

            @Override
            public PregInBodyBcaPojo beforeCreatePdf(ReportForm reportForm) {
                // 获取分析结果信息
                PregDiagnosisItemsVo diagnosisItem = diagnosisService.getDiagnosisItem(reportForm.getReportCode());
                String bcaId = diagnosisItem.getResultCode();
                PregDiagnosisPojo diagnosis = diagnosisService.getDiagnosis(diagnosisItem.getDiagnosisId());
                InbodyCondition condition = new InbodyCondition();
                condition.setBcaId(bcaId);
                PregInBodyBcaPojo inBodyVo = pregInbodyService.getInbodyByCondition(condition);

                inBodyVo.setExamMap(examItemUtil.queryExamItemMapByResultCode(ExamItemConstant.EXAM_ITEM_TABLE.B00003,
                        bcaId));// 获取分析指标结果
                InbodyCondition histroyCondition = new InbodyCondition();
                histroyCondition.setCustId(inBodyVo.getCustId());
                inBodyVo.setHistoryList(pregInbodyService.queryInbodyHistory(histroyCondition)); // 历史记录
                PregArchivesPojo pregVo = pregArchivesService.getLastPregnancyArchive(inBodyVo.getCustId());
                if (pregVo == null) {
                    pregVo = new PregArchivesPojo();
                }
                inBodyVo.setPregVo(pregVo); // 获取患者孕前信息
                CustomerPojo custVo = customerService.getCustomer(inBodyVo.getCustId());
                inBodyVo.setCustomerVo(custVo);// 患者基本信息
                inBodyVo.setInsId(getLoginUser().getUserPojo().getCreateInsId());// 机构号
                inBodyVo.setDiagnosis(diagnosis);
                inBodyVo.setDiagnosisItem(diagnosisItem);
                return inBodyVo;
            }
        };

        // 第二步：创建PDF
        String pdfpath = pdf.create(inspectId);

        return pdfpath;
    }

    // 第一步：记录数据
    public String saveInbdoyRecord(PregInBodyPojo groupBo) {
        PregDiagnosisPojo diagnosis = diagnosisService.getDiagnosis(groupBo.getDiagnosisId());
        if (diagnosis != null) {
            groupBo.setGestationalWeeks(diagnosis.getDiagnosisGestationalWeeks());
        }
        String bcaId = pregInbodyService.addInbdoy(groupBo);

        // 第三步：保存结果记录（检测项目记录表）
        ExamResultRecord examResultRecord = new ExamResultRecord();
        examResultRecord.setCustId(groupBo.getCustId());
        examResultRecord.setExamCode(bcaId);
        examResultRecord.setExamDate(new Date());
        examResultRecord.setExamCategory(ExamItemConstant.EXAM_ITEM_CODE.B00003);
        String examId = examResultRecordUtil.addExamResultRecordForExam(examResultRecord,
                ExamItemConstant.EXAM_ITEM_TABLE.B00003);

        // 第二步：数据分析
        List<ExamItemPojo> detailList = this.saveExamDataDetail(examId, groupBo);

        // 第四步：保存分析结果（指标结果表）
        examItemUtil.addExamItems(ExamItemConstant.EXAM_ITEM_TABLE.B00003, examId, detailList);

        examResultRecordUtil.updateExamResultRecordForExam(examResultRecord,
                ExamItemConstant.EXAM_ITEM_TABLE.B00003);
        return bcaId;
    }

    /**
     * 人体成分数据分析
     * 
     * @author zcq
     * @param examCode
     * @param inbodyVo
     */
    private List<ExamItemPojo> saveExamDataDetail(String examCode, PregInBodyPojo inbodyVo) {

        List<ExamItemPojo> detailList = new ArrayList<ExamItemPojo>();
        PregArchivesPojo archivesPojo = pregArchivesService.getLastPregnancyArchive(inbodyVo.getCustId());

        // 上限，下限，实际值，结果
        float result = 0;
        // 细胞内水分
        result = inbodyVo.getIcw().floatValue();
        String icwResultInfo = "";
        String icwResult = "0";
        if (result > inbodyVo.getIcwMax().floatValue()) {
            icwResult = "0";
            icwResultInfo = "细胞内水分增加";
        } else if (result < inbodyVo.getIcwMin().floatValue()) {
            icwResult = "0";
            icwResultInfo = "细胞内水分减少";
        }
        detailList.add(getExamDataDetail(examCode, "BODY00001", "细胞内水分",
                transferString(inbodyVo.getIcwMin().floatValue(), 1) + "~"
                        + transferString(inbodyVo.getIcwMax().floatValue(), 1), transferString(result, 1),
                icwResultInfo, icwResult, "L"));
        // 细胞外水分
        result = inbodyVo.getEcw().floatValue();
        String ecwResultInfo = "";
        String ecwResult = "0";
        if (result > inbodyVo.getEcwMax().floatValue()) {
            ecwResult = "0";
            ecwResultInfo = "细胞外水分增加";
        } else if (result < inbodyVo.getEcwMin().floatValue()) {
            ecwResult = "0";
            ecwResultInfo = "细胞外水分减少";
        }
        detailList.add(getExamDataDetail(examCode, "BODY00002", "细胞外水分",
                transferString(inbodyVo.getEcwMin().floatValue(), 1) + "~"
                        + transferString(inbodyVo.getEcwMax().floatValue(), 1), transferString(result, 1),
                ecwResultInfo, ecwResult, "L"));

        // 身体总水分
        result = inbodyVo.getTbw().floatValue();
        String tbwResultInfo = "";
        String tbwResult = "0";
        if (result > inbodyVo.getTbwMax().floatValue()) {
            tbwResult = "0";
            tbwResultInfo = "身体总水分增加";
        } else if (result < inbodyVo.getTbwMin().floatValue()) {
            tbwResult = "0";
            tbwResultInfo = "身体总水分减少";
        }
        detailList.add(getExamDataDetail(examCode, "BODY00003", "身体总水分",
                transferString(inbodyVo.getTbwMin().floatValue(), 1) + "~"
                        + transferString(inbodyVo.getTbwMax().floatValue(), 1), transferString(result, 1),
                tbwResultInfo,
                tbwResult, "L"));

        detailList.add(getExamDataDetail(examCode, "BODY00004", "肌肉量", transferString(inbodyVo.getSlmMin(), 1) + "~"
                + transferString(inbodyVo.getSlmMax(), 1), transferString(inbodyVo.getSlm(), 1), ecwResultInfo, "0",
                "kg"));
        detailList.add(getExamDataDetail(examCode, "BODY00005", "去脂体重", transferString(inbodyVo.getFfmMin(), 1) + "~"
                + transferString(inbodyVo.getFfmMax(), 1), transferString(inbodyVo.getFfm(), 1), "", "0", "kg"));
        detailList.add(getExamDataDetail(examCode, "BODY00006", "体重", transferString(inbodyVo.getWtMin(), 1) + "~"
                + transferString(inbodyVo.getWtMax(), 1), transferString(inbodyVo.getWt(), 1), "", "0", "kg"));

        detailList.add(getExamDataDetail(examCode, "BODY00007", "蛋白质", transferString(inbodyVo.getProteinMin(), 1)
                + "~" + transferString(inbodyVo.getProteinMax(), 1), transferString(inbodyVo.getProtein(), 1),
                "", "0", "kg"));
        detailList.add(getExamDataDetail(examCode, "BODY00008", "无机盐", transferString(inbodyVo.getMineralMin(), 2)
                + "~" + transferString(inbodyVo.getMineralMax(), 2), transferString(inbodyVo.getMineral(), 2), "", "0",
                "kg"));
        detailList.add(getExamDataDetail(examCode, "BODY00009", "体脂肪", transferString(inbodyVo.getBfmMin(), 1)
                + "~" + transferString(inbodyVo.getBfmMax(), 1), transferString(inbodyVo.getBfm(), 1), "", "0", "kg"));

        // 肌肉脂肪分析
        detailList.add(getExamDataDetail(examCode, "BODY00010", "体重", "", transferString(inbodyVo.getWt(), 1), "",
                "0", "kg"));
        detailList.add(getExamDataDetail(examCode, "BODY00011", "骨骼肌", "",
                transferString(inbodyVo.getSmm(), 1), "", "0", "kg"));
        detailList.add(getExamDataDetail(examCode, "BODY00012", "体脂肪", "",
                transferString(inbodyVo.getBfm(), 1), "", "0", "kg"));
        detailList.add(getExamDataDetail(examCode, "BODY00013", "体脂百分比", "",
                transferString(inbodyVo.getPbf(), 1), "", "0", "kg"));
        // 体重占比，骨骼肌占比，体脂肪占比
        detailList.add(getExamDataDetail(examCode, "BODY00014", "体重", "",
                transferString(inbodyVo.getPwt(), 1), "", "0", "kg"));
        detailList.add(getExamDataDetail(examCode, "BODY00015", "骨骼肌", "",
                transferString(inbodyVo.getPsmm(), 1), "", "0", "kg"));
        detailList.add(getExamDataDetail(examCode, "BODY00016", "体脂肪占比", "",
                transferString(inbodyVo.getPbfm(), 1), "", "0", "%"));

        // 细胞外水分比率
        detailList.add(getExamDataDetail(examCode, "BODY00017", "细胞外水分比率", "<0.39",
                transferString(inbodyVo.getWed(), 3), "", "0", "%"));

        // 人体成分测试历史变化记录
        InbodyCondition condition = new InbodyCondition();
        condition.setCustId(inbodyVo.getCustId());
        List<PregInBodyBcaPojo> bcaList = pregInbodyService.queryInbodyHistory(condition);
        // 根据人体成分的完成次数做数据处理
        int inbodyNum = bcaList.size();
        float defSmmF = 0;
        float defProteinF = 0;
        float defMineralF = 0;
        float defIcwF = 0;
        float defEcwF = 0;
        float defBfmF = 0;
        float defWedF = 0;
        if (inbodyNum <= 1) {
            detailList.add(getExamDataDetail(examCode, "BODY00018", "总体孕周", "", "——#——#——#——#——", "", "0", ""));
            detailList.add(getExamDataDetail(examCode, "BODY00019", "总体间隔", "", "——#——#——#——#——", "", "0", ""));
            detailList.add(getExamDataDetail(examCode, "BODY00020", "总体体重变化", "", "——#——#——#——#——", "", "0", "kg"));
            detailList.add(getExamDataDetail(examCode, "BODY00021", "总体蛋白质变化", "", "——#——#——#——#——", "", "0", "kg"));
            detailList.add(getExamDataDetail(examCode, "BODY00022", "总体脂肪变化", "", "——#——#——#——#——", "", "0", "kg"));
            detailList.add(getExamDataDetail(examCode, "BODY00023", "总体骨骼肌变化", "", "——#——#——#——#——", "", "0", "kg"));
            detailList.add(getExamDataDetail(examCode, "BODY00024", "总体细胞内水分变化", "", "——#——#——#——#——", "", "0", "L"));
            detailList.add(getExamDataDetail(examCode, "BODY00025", "总体细胞外比率变化", "", "——#——#——#——#——", "", "0", "L"));
            detailList.add(getExamDataDetail(examCode, "BODY00026", "总体无机盐", "", "——#——#——#——#——", "", "0", "L"));
            detailList.add(getExamDataDetail(examCode, "BODY00027", "总体细胞外变化", "", "——#——#——#——#——", "", "0", "L"));
        } else {
            List<String> cellResultList = new ArrayList<String>();
            /*
             * 计算规则:
             * 1. 首先计算历史总变化: 本次和首次的差
             * 2. 计算每次和上一次的差
             */
            // 时间
            cellResultList.clear();
            cellResultList.add(String.valueOf(bcaList.get(inbodyNum - 1).getGestationalWeeks() + "~"
                    + bcaList.get(0).getGestationalWeeks()));
            for (int i = 1; i < 5; i++) {
                if (i < inbodyNum) {
                    cellResultList.add(bcaList.get(i).getGestationalWeeks() + "~"
                            + bcaList.get(i - 1).getGestationalWeeks());
                } else {
                    cellResultList.add("——");
                }
            }
            detailList.add(getExamDataDetail(examCode, "BODY00018", "时间", "", StringUtils.join(cellResultList, "#"),
                    "", "0", ""));
            // 间隔时间
            cellResultList.clear();
            cellResultList.add(intervalWeekDay(bcaList.get(inbodyNum - 1).getGestationalWeeks(), bcaList.get(0)
                    .getGestationalWeeks()));
            for (int i = 1; i < 5; i++) {
                if (i < inbodyNum) {
                    cellResultList.add(intervalWeekDay(bcaList.get(i).getGestationalWeeks(), bcaList.get(i - 1)
                            .getGestationalWeeks()));
                } else {
                    cellResultList.add("——");
                }
            }
            detailList.add(getExamDataDetail(examCode, "BODY00019", "间隔时间（周）", "",
                    StringUtils.join(cellResultList, "#"),
                    "", "0", ""));
            // 体重
            cellResultList.clear();
            float defWtF = bcaList.get(0).getWt().subtract(bcaList.get(inbodyNum - 1).getWt()).floatValue();
            cellResultList.add(defWtF > 0 ? "+" + defWtF : "" + defWtF);
            for (int i = 1; i < 5; i++) {
                if (i < inbodyNum) {
                    float wtRef = bcaList.get(i - 1).getWt().subtract(bcaList.get(i).getWt()).floatValue();
                    cellResultList.add(wtRef > 0 ? "+" + wtRef : "" + wtRef);
                } else {
                    cellResultList.add("——");
                }
            }
            detailList.add(getExamDataDetail(examCode, "BODY00020", "总体体重变化", "",
                    StringUtils.join(cellResultList, "#"), "", "0", "kg"));
            // 蛋白质
            cellResultList.clear();
            defProteinF = bcaList.get(0).getProtein().subtract(bcaList.get(inbodyNum - 1).getProtein())
                    .floatValue();
            cellResultList.add(defProteinF > 0 ? "+" + defProteinF : "" + defProteinF);
            for (int i = 1; i < 5; i++) {
                if (i < inbodyNum) {
                    float proteinRef = bcaList.get(i - 1).getProtein().subtract(bcaList.get(i).getProtein())
                            .floatValue();
                    cellResultList.add(proteinRef > 0 ? "+" + proteinRef : "" + proteinRef);
                } else {
                    cellResultList.add("——");
                }
            }
            detailList.add(getExamDataDetail(examCode, "BODY00021", "总体蛋白质变化", "",
                    StringUtils.join(cellResultList, "#"), "", "0", "kg"));
            // 脂肪
            cellResultList.clear();
            defBfmF = bcaList.get(0).getBfm().subtract(bcaList.get(inbodyNum - 1).getBfm()).floatValue();
            cellResultList.add(defBfmF > 0 ? "+" + defBfmF : "" + defBfmF);
            for (int i = 1; i < 5; i++) {
                if (i < inbodyNum) {
                    float bfmRef = bcaList.get(i - 1).getBfm().subtract(bcaList.get(i).getBfm()).floatValue();
                    cellResultList.add(bfmRef > 0 ? "+" + bfmRef : "" + bfmRef);
                } else {
                    cellResultList.add("——");
                }
            }
            detailList.add(getExamDataDetail(examCode, "BODY00022", "总体脂肪变化", "",
                    StringUtils.join(cellResultList, "#"), "", "0", "kg"));
            // 骨骼肌
            cellResultList.clear();
            defSmmF = bcaList.get(0).getSmm().subtract(bcaList.get(inbodyNum - 1).getSmm()).floatValue();
            cellResultList.add(defSmmF > 0 ? "+" + defSmmF : "" + defSmmF);
            for (int i = 1; i < 5; i++) {
                if (i < inbodyNum) {
                    float smmRef = bcaList.get(i - 1).getSmm().subtract(bcaList.get(i).getSmm()).floatValue();
                    cellResultList.add(smmRef > 0 ? "+" + smmRef : "" + smmRef);
                } else {
                    cellResultList.add("——");
                }
            }
            detailList.add(getExamDataDetail(examCode, "BODY00023", "总体骨骼肌变化", "",
                    StringUtils.join(cellResultList, "#"), "", "0", "kg"));

            // 无机盐
            cellResultList.clear();
            defMineralF = bcaList.get(0).getMineral().subtract(bcaList.get(inbodyNum - 1).getMineral())
                    .floatValue();
            cellResultList.add(defMineralF > 0 ? "+" + defMineralF : "" + defMineralF);
            for (int i = 1; i < 5; i++) {
                if (i < inbodyNum) {
                    float mineralRef = bcaList.get(i - 1).getMineral().subtract(bcaList.get(i).getMineral())
                            .floatValue();
                    cellResultList.add(mineralRef > 0 ? "+" + mineralRef : "" + mineralRef);
                } else {
                    cellResultList.add("——");
                }
            }
            detailList.add(getExamDataDetail(examCode, "BODY00024", "总体无机盐变化", "",
                    StringUtils.join(cellResultList, "#"), "", "0", "kg"));

            // 细胞内水分
            cellResultList.clear();
            defIcwF = bcaList.get(0).getIcw().subtract(bcaList.get(inbodyNum - 1).getIcw()).floatValue();
            cellResultList.add(defIcwF > 0 ? "+" + defIcwF : "" + defIcwF);
            for (int i = 1; i < 5; i++) {
                if (i < inbodyNum) {
                    float icwRef = bcaList.get(i - 1).getIcw().subtract(bcaList.get(i).getIcw()).floatValue();
                    cellResultList.add(icwRef > 0 ? "+" + icwRef : "" + icwRef);
                } else {
                    cellResultList.add("——");
                }
            }
            detailList.add(getExamDataDetail(examCode, "BODY00025", "总体细胞内水分变化", "",
                    StringUtils.join(cellResultList, "#"), "", "0", "kg"));

            // 细胞外水分
            cellResultList.clear();
            defEcwF = bcaList.get(0).getEcw().subtract(bcaList.get(inbodyNum - 1).getEcw()).floatValue();
            cellResultList.add(defEcwF > 0 ? "+" + defEcwF : "" + defEcwF);
            for (int i = 1; i < 5; i++) {
                if (i < inbodyNum) {
                    float icwRef = bcaList.get(i - 1).getEcw().subtract(bcaList.get(i).getEcw()).floatValue();
                    cellResultList.add(icwRef > 0 ? "+" + icwRef : "" + icwRef);
                } else {
                    cellResultList.add("——");
                }
            }
            detailList.add(getExamDataDetail(examCode, "BODY00026", "总体细胞外水分变化", "",
                    StringUtils.join(cellResultList, "#"), "", "0", "kg"));

            // 细胞外水分比率
            cellResultList.clear();
            defWedF = bcaList.get(0).getWed().subtract(bcaList.get(inbodyNum - 1).getWed()).floatValue();
            cellResultList.add(defWedF > 0 ? "+" + defWedF : "" + defWedF);
            for (int i = 1; i < 5; i++) {
                if (i < inbodyNum) {
                    float wedRef = bcaList.get(i - 1).getWed().subtract(bcaList.get(i).getWed()).floatValue();
                    cellResultList.add(wedRef > 0 ? "+" + wedRef : "" + wedRef);
                } else {
                    cellResultList.add("——");
                }
            }
            detailList.add(getExamDataDetail(examCode, "BODY00027", "总体细胞外水分比率变化", "",
                    StringUtils.join(cellResultList, "#"), "", "0", "kg"));
        }

        // 孕期体重增长范围--和孕前体重对比
        List<String> weekList = new ArrayList<String>();
        List<String> weightList = new ArrayList<String>();
        for (int i = inbodyNum - 1; i >= 0; i--) {
            weekList.add(bcaList.get(i).getGestationalWeeks().split("\\+")[0]);
            weightList.add(bcaList.get(i).getWt().subtract(archivesPojo.getWeight()).toString());
        }
        detailList.add(getExamDataDetail(examCode, "BODY00028", "体重变化曲线", "", StringUtils.join(weekList, "#"),
                StringUtils.join(weightList, "#"), "0", ""));

        // 体重增长建议
        String weekUpWt = "";
        String allUpWt = "";
        float bmiF = archivesPojo.getBmi().floatValue();
        if (bmiF < 18.5) {
            weekUpWt = "0.51kg（0.44-0.58）";
            allUpWt = "12.5kg-18kg";
        } else if (bmiF >= 18.5 && bmiF < 25.0) {
            weekUpWt = "0.42kg（0.35-0.50）";
            allUpWt = "11.5kg-16kg";
        } else if (bmiF >= 25.0 && bmiF < 30.0) {
            weekUpWt = "0.28kg（0.23-0.33）";
            allUpWt = "7kg-11.5kg";
        } else {
            weekUpWt = "0.22kg（0.17-0.27）";
            allUpWt = "5kg-9kg";
        }
        detailList.add(getExamDataDetail(examCode, "BODY00029", "每周体重适宜增长范围", "", weekUpWt, "", "0", "kg"));
        detailList.add(getExamDataDetail(examCode, "BODY00030", "整体体重增长", "",
                String.valueOf(bcaList.get(0).getWt().subtract(archivesPojo.getWeight()).floatValue()), "", "0", "kg"));
        detailList.add(getExamDataDetail(examCode, "BODY00031", "孕期整体体重增长范围", "", allUpWt, "", "0", "kg"));

        // 节段脂肪分析
        detailList.add(getExamDataDetail(examCode, "BODY00032", "右上肢", "", transferString(inbodyVo.getFra(), 1),
                "", "0", "kg"));
        detailList.add(getExamDataDetail(examCode, "BODY00033", "右上肢百分比", "",
                transferString(inbodyVo.getPfra(), 1), "", "0", "%"));

        detailList.add(getExamDataDetail(examCode, "BODY00034", "左上肢", "", transferString(inbodyVo.getFla(), 1),
                "", "0", "kg"));
        detailList.add(getExamDataDetail(examCode, "BODY00035", "左上肢百分比", "",
                transferString(inbodyVo.getPfla(), 1), "", "0", "%"));
        detailList.add(getExamDataDetail(examCode, "BODY00036", "躯干", "", transferString(inbodyVo.getFt(), 1), "",
                "0", "kg"));
        detailList.add(getExamDataDetail(examCode, "BODY00037", "躯干百分比", "", transferString(inbodyVo.getPft(), 1),
                "", "0", "%"));
        detailList.add(getExamDataDetail(examCode, "BODY00038", "右下肢", "", transferString(inbodyVo.getFrl(), 1),
                "", "0", "kg"));
        detailList.add(getExamDataDetail(examCode, "BODY00039", "右下肢百分比", "", transferString(inbodyVo.getPfrl(), 1),
                "", "0", "%"));

        detailList.add(getExamDataDetail(examCode, "BODY00040", "左下肢", "", transferString(inbodyVo.getFll(), 1),
                "", "0", "kg"));
        detailList.add(getExamDataDetail(examCode, "BODY00041", "左下肢百分比", "", transferString(inbodyVo.getPfll(), 1),
                "", "0", "%"));

        // 研究项目
        detailList.add(getExamDataDetail(examCode, "BODY00042", "细胞内水分", transferString(inbodyVo.getIcwMin(), 1)
                + "~" + transferString(inbodyVo.getIcwMax(), 1), transferString(inbodyVo.getIcw(), 1), "", "0", "L"));

        detailList.add(getExamDataDetail(examCode, "BODY00043", "细胞外水分", transferString(inbodyVo.getEcwMin(), 1)
                + "~" + transferString(inbodyVo.getEcwMax(), 1), transferString(inbodyVo.getEcw(), 1), "", "0", "L"));

        detailList.add(getExamDataDetail(examCode, "BODY00044", "身体细胞量", transferString(inbodyVo.getBcmMin(), 1)
                + "~" + transferString(inbodyVo.getBcmMax(), 1), transferString(inbodyVo.getBcm(), 1), "", "0", "kg"));

        // 全身相位角
        detailList.add(getExamDataDetail(examCode, "BODY00045", "全身相位角", "", transferString(inbodyVo.getWbpa50(), 1),
                "", "0", "度"));

        float grow = inbodyVo.getWt().floatValue() - archivesPojo.getWeight().floatValue();
        float wtValueGrowUp = 0;
        float[] normalWT = wtNormal(bmiF, Integer.valueOf(inbodyVo.getGestationalWeeks().split("\\+")[0]));
        String infoWt = "";
        String compareWt = "";
        if (grow > normalWT[0]) {
            wtValueGrowUp = grow - normalWT[0];
            infoWt = "体重增长过快";
            compareWt = "4";
        } else if (grow < normalWT[1]) {
            wtValueGrowUp = grow - normalWT[1];
            infoWt = "体重增长过缓";
            compareWt = "4";
        } else {
            wtValueGrowUp = grow - normalWT[0];
            infoWt = "正常";
            compareWt = "0";
        }
        detailList.add(getExamDataDetail(examCode, "BODY00046", "整体体重增长", "", String.valueOf(wtValueGrowUp), infoWt,
                compareWt, ""));

        // 骨骼肌
        String smmInfo = "";
        String compareSmm = "";
        if (Float.valueOf(defSmmF) >= 0) {
            smmInfo = "骨骼肌增加";
            compareSmm = "4";
        } else {
            smmInfo = "骨骼肌减少";
            compareSmm = "4";
        }
        detailList.add(getExamDataDetail(examCode, "BODY00047", "整体骨骼肌增长", "", String.valueOf(defSmmF), smmInfo,
                compareSmm, ""));

        // 蛋白质
        String perInfo = "";
        String comparePer = "";
        if (Float.valueOf(defProteinF) >= 0) {
            perInfo = "蛋白质增加";
            comparePer = "4";
        } else {
            perInfo = "蛋白质减少";
            comparePer = "4";
        }
        detailList.add(getExamDataDetail(examCode, "BODY00048", "整体蛋白质增长", "", String.valueOf(defProteinF), perInfo,
                comparePer, ""));

        // 无机盐
        String mineralInfo = "";
        String compareMiner = "";
        if (Float.valueOf(defMineralF) >= 0) {
            mineralInfo = "无机盐增加";
            compareMiner = "4";
        } else {
            mineralInfo = "无机盐减少";
            compareMiner = "4";
        }
        detailList.add(getExamDataDetail(examCode, "BODY00049", "整体无机盐增长", "", String.valueOf(defMineralF),
                mineralInfo, compareMiner, ""));

        String icwInfo = "";
        String compareIcw = "";
        if (Float.valueOf(defIcwF) >= 0) {
            icwInfo = "细胞内水分增加";
            compareIcw = "4";
        } else {
            icwInfo = "细胞内水分减少";
            compareIcw = "4";
        }
        detailList.add(getExamDataDetail(examCode, "BODY00050", "整体细胞内水分增长", "", String.valueOf(defIcwF), icwInfo,
                compareIcw, ""));

        String ecwInfo = "";
        String compareEcw = "";
        if (Float.valueOf(defEcwF) >= 0) {
            ecwInfo = "细胞外水分增加";
            compareEcw = "4";
        } else {
            ecwInfo = "细胞外水分减少";
            compareEcw = "4";
        }
        detailList.add(getExamDataDetail(examCode, "BODY00051", "整体细胞外水分增长", "", String.valueOf(defIcwF), ecwInfo,
                compareEcw, ""));

        String bfmInfo = "";
        String compareBfm = "";
        if (Float.valueOf(defBfmF) >= 0) {
            bfmInfo = "体脂肪增加";
            compareBfm = "4";
        } else {
            bfmInfo = "体脂肪减少";
            compareBfm = "4";
        }
        detailList.add(getExamDataDetail(examCode, "BODY00052", "整体体脂肪增长", "", String.valueOf(defIcwF), bfmInfo,
                compareBfm, ""));

        String wedInfo = "";
        String compareWed = "0";
        if (inbodyVo.getWed().floatValue() >= 0.40f) {
            wedInfo = "浮肿";
            compareWed = "4";
        } else if (inbodyVo.getWed().floatValue() >= 0.39 && inbodyVo.getWed().floatValue() <= 0.40f) {
            wedInfo = "轻微";
            compareWed = "4";
        }
        detailList.add(getExamDataDetail(examCode, "BODY00053", "整体细胞外水分", "",
                transferString(inbodyVo.getWed(), 3), wedInfo, compareWed, ""));

        return detailList;
    }

    private ExamItemPojo getExamDataDetail(String examCode, String itemCode, String itemName, String itemRefValue,
            String itemString, String itemResult, String itemCompare, String itemUnit) {
        ExamItemPojo detail = new ExamItemPojo();
        detail.setExamId(examCode);
        detail.setItemCode(itemCode);
        detail.setItemName(itemName);
        detail.setItemRefValue(itemRefValue);
        detail.setItemType(ExamItemConstant.EXAM_ITEM_TABLE.B00003);
        detail.setItemString(itemString);
        detail.setItemResult(itemResult);
        detail.setItemCompare(itemCompare);
        detail.setItemUnit(itemUnit);
        return detail;
    }

    private String transferString(BigDecimal value, Integer scale) {
        return (value == null) ? "0" : value.setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
    }

    private String transferString(Float fValue, Integer scale) {
        BigDecimal value = new BigDecimal(fValue);
        return (value == null) ? "0" : value.setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 给四个点判断斜率
     * 
     * @author xdc
     * @param first
     * @param second
     * @return
     */
    private float[] slope(float[] first, float[] second) {
        // a表示上限， b表示下限 ，0表示12周， 1表示40周
        float[] slo = new float[4];
        float a1 = first[0];
        float a2 = first[1];
        float b1 = second[0];
        float b2 = second[1];

        slo[0] = a1 / 12;
        slo[1] = (a2 - a1) / (40 - 12);
        slo[2] = b1 / 12;
        slo[3] = (b2 - b1) / (40 - 12);
        return slo;
    }

    /**
     * 根据孕前bmi和当前孕周判断标准体重范围
     * 
     * @author xdc
     * @param bmiF
     * @param week
     * @return
     *         数组中0表示上限，1表示下限
     */
    private float[] wtNormal(float bmiF, int week) {
        float[] maxHs;
        float[] minHs;
        float[] normal = new float[2];// 0：是上限，1：是下限

        if (bmiF < 18.5) {
            maxHs = new float[] {2f, 18f};
            minHs = new float[] {1f, 12.5f};
        } else if (bmiF >= 18.5f && bmiF < 24.9f) {
            maxHs = new float[] {2f, 16f};
            minHs = new float[] {0.5f, 11.5f};
        } else if (bmiF >= 24.9f && bmiF < 29.9f) {
            maxHs = new float[] {1.5f, 11.5f};
            minHs = new float[] {0.5f, 7f};
        } else {
            maxHs = new float[] {1.5f, 9f};
            minHs = new float[] {0f, 5f};
        }

        float[] slo = slope(maxHs, minHs);
        if (week <= 12) {
            normal[0] = (float) (slo[0] * week);
            normal[1] = (float) (slo[2] * week);
        } else {
            normal[0] = (float) (slo[1] * (week - 12) + maxHs[0]);
            normal[1] = (float) (slo[3] * (week - 12) + minHs[0]);
        }

        return normal;
    }

    /**
     * 输入格式为a+b的孕期时间，计算时间间隔
     * 
     * @author xdc
     * @param week1
     * @param week2
     * @return
     */
    private String intervalWeekDay(String week1, String week2) {
        String[] days1 = week1.split("\\+");
        String[] days2 = week2.split("\\+");
        int dayInt1 = Integer.valueOf(days1[0]) * 7 + Integer.valueOf(days1[1]);
        int dayInt2 = Integer.valueOf(days2[0]) * 7 + Integer.valueOf(days2[1]);
        int defDay = Math.abs(dayInt1 - dayInt2);
        int week = defDay / 7;
        int days = defDay % 7;
        return week + "+" + days;
    }

}

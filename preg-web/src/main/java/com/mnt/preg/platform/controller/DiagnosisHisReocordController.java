
package com.mnt.preg.platform.controller;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.preg.examitem.condition.ExamResultRecordCondition;
import com.mnt.preg.examitem.pojo.ExamResultRecordPojo;
import com.mnt.preg.examitem.util.ExamResultRecordUtil;
import com.mnt.preg.platform.condition.DiagnosisCondition;
import com.mnt.preg.platform.mapping.PlatFormPageName;
import com.mnt.preg.platform.mapping.PlatformMapping;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;
import com.mnt.preg.platform.service.PregDiagnosisService;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 查询历史记录
 * 
 * @author xdc
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-12-19 xdc 初版
 */
@Controller
public class DiagnosisHisReocordController extends BaseController {

    @Resource
    private ExamResultRecordUtil examResultRecordUtil;

    @Resource
    private PregDiagnosisService diagnosisService;

    // ***************************************【查询历史记录的方法】**********************************************

    /**
     * JSP页面--guide_side.jsp 历史营养病历页面
     * 
     * @author zcq
     * @param custId
     * @param model
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_RECORD)
    public String getDiagnosisRecord(String custId, Model model) {
        model.addAttribute("custId", custId);
        String custName = customerService.getCustomer(custId).getCustName();
        model.addAttribute("custName", custName);
        return PlatFormPageName.DIAGNOSIS_RECORD;
    }

    /**
     * JSP页面--guide_side.jsp 历史营养病历列表
     * 
     * @author zcq
     * @param condition
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_HISTORY_QUERY)
    public @ResponseBody
    WebResult<PregDiagnosisPojo> queryDiagnosisHistory(DiagnosisCondition condition) {
        WebResult<PregDiagnosisPojo> webResult = new WebResult<PregDiagnosisPojo>();
        webResult.setData(diagnosisService.queryDiagnosis(condition));
        return webResult;
    }

    /**
     * JSP页面--guide_side.jsp 查询系统营养评价
     * 
     * @author zcq
     * @param custId
     * @param model
     * @return
     */
    @RequestMapping(value = PlatformMapping.QUERY_REPORT_VIEW)
    public String toDiagnosisReportView(String custId, Model model) {
        String custName = customerService.getCustomer(custId).getCustName();
        model.addAttribute("custName", custName);
        model.addAttribute("custId", custId);
        return PlatFormPageName.DIAGNOSIS_REPORT_VIEW;
    }

    /**
     * JSP页面--guide_side.jsp 查询检测报告列表
     * 
     * @author zcq
     * @param condition
     * @return
     */
    @RequestMapping(value = PlatformMapping.QUERY_REPORT_LIST)
    public @ResponseBody
    WebResult<List<ExamResultRecordPojo>> queryReportList(ExamResultRecordCondition condition) {
        WebResult<List<ExamResultRecordPojo>> webResult = new WebResult<List<ExamResultRecordPojo>>();
        webResult.setSuc(examResultRecordUtil.queryExamResultRecord(condition));
        return webResult;
    }

    /**
     * JSP页面--guide_side.jsp 查询检测报告是否存在
     * 
     * @author zcq
     * @param path
     * @return
     */
    @RequestMapping(value = PlatformMapping.CHECK_FILE_EXIST)
    public @ResponseBody
    WebResult<Boolean> checkFileExist(String path) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        Boolean flag = true;
        if (!new File(publicConfig.getResourceAbsolutePath() + path).exists()) {
            flag = false;
        }
        webResult.setSuc(flag);
        return webResult;
    }

}

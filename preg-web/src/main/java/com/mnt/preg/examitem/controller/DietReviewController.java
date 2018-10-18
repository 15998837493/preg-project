
package com.mnt.preg.examitem.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.preg.customer.pojo.CustomerPojo;
import com.mnt.preg.customer.service.CustomerService;
import com.mnt.preg.examitem.condition.ExamResultRecordCondition;
import com.mnt.preg.examitem.entity.ExamResultRecord;
import com.mnt.preg.examitem.form.DietReviewForm;
import com.mnt.preg.examitem.mapping.DietFoodMapping;
import com.mnt.preg.examitem.service.PregDietFoodAnalyseService;
import com.mnt.preg.examitem.service.PregDietFoodRecordService;
import com.mnt.preg.examitem.util.ExamItemUtil;
import com.mnt.preg.examitem.util.ExamResultRecordUtil;
import com.mnt.preg.master.service.basic.FoodService;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;
import com.mnt.preg.platform.service.PregDiagnosisService;
import com.mnt.preg.system.service.CodeService;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 膳食评估Controller
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-17 zcq 初版
 */
@Controller
public class DietReviewController extends BaseController {

    @Resource
    private FoodService foodService;

    @Resource
    public CodeService codeService;

    @Resource
    public PregDiagnosisService pregDiagnosisService;

    @Resource
    public ExamResultRecordUtil examResultRecordUtil;

    @Resource
    public ExamItemUtil examItemUtil;

    @Resource
    public PregDietFoodAnalyseService pregDietFoodAnalyseService;

    @Resource
    public CustomerService customerService;

    @Resource
    public PregDietFoodRecordService pregDietFoodRecordService;

    @Resource
    public PregDiagnosisService diagnosisService;

    /**
     * 膳食回顾表单提交
     * 
     * @param request
     * @param condition
     *            检索条件
     * @return
     */
    @RequestMapping(value = DietFoodMapping.DIET_REVIEW)
    public @ResponseBody
    WebResult<Boolean> addDietReview(DietReviewForm dietReviewForm) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        // 保存到结果表
        PregDiagnosisPojo diagnosisPojo = diagnosisService.getDiagnosis(dietReviewForm.getDiagnosisId());
        CustomerPojo cust = customerService.getCustomer(diagnosisPojo.getDiagnosisCustomer());
        ExamResultRecord examResultRecord = TransformerUtils.transformerProperties(ExamResultRecord.class, cust);
        examResultRecord.setExamCode(dietReviewForm.getDietReviewId());
        examResultRecord.setExamCategory(dietReviewForm.getDietReviewCode());
        examResultRecord.setDiagnosisResultNames(dietReviewForm.getDietReviewText());
        // 如果已经存在就更新
        ExamResultRecordCondition condition = new ExamResultRecordCondition();
        condition.setExamCategory(dietReviewForm.getDietReviewCode());
        condition.setCustId(cust.getCustId());
        condition.setExamCode(dietReviewForm.getDietReviewId());
        if (examResultRecordUtil.queryExamResultRecord(condition).size() > 0) {
            examResultRecord.setExamId(examResultRecordUtil.queryExamResultRecord(condition).get(0).getExamId());
            examResultRecordUtil.updateExamResultRecord(examResultRecord);
        } else {
            examResultRecordUtil.addExamResultRecord(examResultRecord);
        }

        webResult.setSuc(true);
        return webResult;
    }
}

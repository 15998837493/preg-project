
package com.mnt.preg.examitem.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.preg.customer.mapping.CustomerMapping;
import com.mnt.preg.customer.service.CustomerService;
import com.mnt.preg.examitem.util.ExamItemUtil;
import com.mnt.preg.examitem.util.ExamResultRecordUtil;
import com.mnt.preg.platform.condition.DiagnosisQuotaItemCondition;
import com.mnt.preg.platform.entity.DiagnosisQuotaItem;
import com.mnt.preg.platform.service.PregDiagnosisService;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 指标录入Controller
 * 
 * @author wsy
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-3-27 wsy 初版
 */
@Controller
public class QuotaController extends BaseController {

    @Resource
    private ExamItemUtil examItemUtil;

    @Resource
    private ExamResultRecordUtil examResultRecordUtil;

    @Resource
    private PregDiagnosisService diagnosisService;

    @Resource
    private CustomerService customerService;

    /**
     * 指标录入--保存辅助检测项目
     * 
     * @author wsy
     * @param
     * @return
     */
    @RequestMapping(value = CustomerMapping.QUOTA_ITEM_SAVE)
    public @ResponseBody
    WebResult<Boolean> saveQuotaItem(String itemIds, String diagnosisId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        if (StringUtils.isNotEmpty(itemIds) && StringUtils.isNotEmpty(diagnosisId)) {
            List<DiagnosisQuotaItem> list = new ArrayList<>();
            for (String itrmid : itemIds.split(",")) {
                if (StringUtils.isNotEmpty(itrmid)) {
                    DiagnosisQuotaItem bo = new DiagnosisQuotaItem();
                    bo.setInspectItemId(itrmid);
                    bo.setDiagnosisId(diagnosisId);
                    list.add(bo);
                }

            }
            diagnosisService.saveDiagnosisQuotaItem(list, diagnosisId);
            webResult.setSuc(true);
        }
        return webResult;
    }

    /**
     * 指标录入--查询辅助检测项目
     * 
     * @author wsy
     * @param
     * @return
     */
    @RequestMapping(value = CustomerMapping.QUOTA_ITEM_QUERY)
    public @ResponseBody
    WebResult<Boolean> queryQuotaItem(String diagnosisId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        DiagnosisQuotaItemCondition condition = new DiagnosisQuotaItemCondition();
        condition.setDiagnosisId(diagnosisId);
        webResult.setData(diagnosisService.queryDiagnosisQuotaItem(condition));
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 删除指标
     * 
     * @author xdc
     * @param id
     * @return
     */
    @RequestMapping(value = CustomerMapping.QUOTA_ITEM_DELETE)
    public @ResponseBody
    WebResult<Boolean> deleteQuotaItem(String id) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        diagnosisService.deleteDiagnosisQuotaItem(id);
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 获取指标历史记录
     * 
     * @author xdc
     * @param itemCode
     * @param custId
     * @return
     */
    @RequestMapping(value = CustomerMapping.GET_ITEM_HISTORY_CODE)
    public @ResponseBody
    WebResult<Boolean> getItemHistoryByCode(String itemCode, String custId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        // List<PregDiagnosisClinicalPojo> itemList = clinicalService.getItemHistoryByCode(itemCode, custId);
        // webResult.setData(itemList);
        webResult.setSuc(true);
        return webResult;
    }

    /******************************************** 原business方法 *****************************/

    // /**
    // * 保存指标
    // *
    // * @author xdc
    // * @param detailList
    // * @param diagnosisId
    // * @return
    // */
    // public String saveQuotaItems(List<PregDiagnosisClinical> detailList, String diagnosisId) {
    // // 保存表user_diagnosis_clinical，删除原有的指标
    // clinicalService.deleteDiagnosisClinicalByExamCode(diagnosisId);
    // if (CollectionUtils.isNotEmpty(detailList)) {
    // // 保存指标列表，addExamItems方法是公用方法
    // clinicalService.addPregDiagnosisClinical(detailList);
    // }
    // return "success";
    // }

}

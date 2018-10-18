
package com.mnt.preg.master.controller.preg;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.preg.master.condition.preg.SymptomsCondition;
import com.mnt.preg.master.entity.preg.Symptoms;
import com.mnt.preg.master.mapping.basic.MasterMapping;
import com.mnt.preg.master.pojo.preg.SymptomsPojo;
import com.mnt.preg.master.service.preg.SymptomsService;
import com.mnt.preg.web.constants.WebMsgConstant;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 功能症状Controller
 * 
 * @author dhs
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-11 gss 初版
 */
@Controller
public class SymtemsController {

    /**
     * 功能症状
     */
    @Resource
    private SymptomsService symptomsService;

    /**
     * 检索功能症状信息
     * 
     * @author zcq
     * @param condition
     * @return
     */
    @RequestMapping(value = MasterMapping.QUERY_SYMPTOMS_d)
    public @ResponseBody
    WebResult<SymptomsPojo> querySymptoms(SymptomsCondition condition) {
        WebResult<SymptomsPojo> webResult = new WebResult<SymptomsPojo>();
        webResult.setData(symptomsService.querySymptoms(condition));
        return webResult;
    }

    /**
     * 
     * 验证主键是否重复
     * 
     * @author dhs
     * @param name
     * @param id
     * @return
     */
    @RequestMapping(value = MasterMapping.SYMPTOMS_VALIDATE)
    public @ResponseBody
    boolean symptomsValidate(String symptomsId) {
        SymptomsPojo symptoms = symptomsService.getSymptomsBySymptomsId(symptomsId);
        if (symptoms == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 添加功能症状信息
     * 
     * @author dhs
     * @param Symptoms
     * @return
     */
    @RequestMapping(value = MasterMapping.ADD_SYMPTOMS)
    public @ResponseBody
    WebResult<SymptomsPojo> addSymptoms(Symptoms symptoms) {
        WebResult<SymptomsPojo> webResult = new WebResult<SymptomsPojo>();
        webResult.setSuc(symptomsService.saveSymptoms(symptoms), WebMsgConstant.success_message);
        return webResult;
    }

    /**
     * 
     * 删除功能症状信息
     * 
     * @author dhs
     * @param id
     * @return
     */
    @RequestMapping(value = MasterMapping.REMOVE_Symptoms)
    public @ResponseBody
    WebResult<Boolean> removeSymptoms(String id) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        symptomsService.removeSymptoms(id);
        webResult.setSuc(true, WebMsgConstant.success_message);
        return webResult;
    }

    /**
     * 修改功能症状信息
     * 
     * @author dhs
     * @param Symptoms
     * @return
     */
    @RequestMapping(value = MasterMapping.UPDATE_Symptoms)
    public @ResponseBody
    WebResult<SymptomsPojo> updateSymptoms(Symptoms symptoms) {
        WebResult<SymptomsPojo> webResult = new WebResult<SymptomsPojo>();
        webResult.setSuc(symptomsService.updateSymptoms(symptoms), WebMsgConstant.success_message);
        return webResult;
    }
}

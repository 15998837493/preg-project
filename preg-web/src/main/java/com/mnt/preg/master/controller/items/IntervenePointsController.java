
package com.mnt.preg.master.controller.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.preg.master.condition.items.InterveneDiseaseCondition;
import com.mnt.preg.master.condition.items.IntervenePointsCondition;
import com.mnt.preg.master.entity.preg.PregInterveneDiseasePoints;
import com.mnt.preg.master.entity.preg.PregIntervenePoints;
import com.mnt.preg.master.form.preg.IntervenePointsForm;
import com.mnt.preg.master.mapping.basic.MasterMapping;
import com.mnt.preg.master.mapping.basic.MasterPageName;
import com.mnt.preg.master.pojo.items.InterveneDiseaseGroupPojo;
import com.mnt.preg.master.pojo.items.InterveneDiseasePojo;
import com.mnt.preg.master.pojo.preg.PregIntervenePointsPojo;
import com.mnt.preg.master.service.items.InterveneDiseaseService;
import com.mnt.preg.master.service.preg.PregIntervenePointsService;
import com.mnt.preg.web.constants.WebMsgConstant;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 饮食原则/诊断课程
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-6-12 gss 初版
 */
@Controller
public class IntervenePointsController extends BaseController {

    @Resource
    private InterveneDiseaseService interveneDiseaseService;

    @Resource
    private PregIntervenePointsService intervenePointsService;

    /**
     * 
     * 初始化诊疗课程
     * 
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = MasterMapping.QUERY_INIT_INTERVENEPOINTS)
    public String initQueryIntervenePoints(Model model) {
        // 干预疾病信息
        List<InterveneDiseaseGroupPojo> diseaseList = new ArrayList<InterveneDiseaseGroupPojo>();

        List<InterveneDiseasePojo> interveneDiseaseList = interveneDiseaseService.queryInterveneDisease(null);
        List<InterveneDiseaseGroupPojo> resultList = new ArrayList<InterveneDiseaseGroupPojo>();
        Map<String, List<InterveneDiseasePojo>> diseaseMap = new TreeMap<String, List<InterveneDiseasePojo>>();
        if (CollectionUtils.isNotEmpty(interveneDiseaseList)) {
            for (InterveneDiseasePojo planDisease : interveneDiseaseList) {
                String type = planDisease.getDiseaseType();
                if (!diseaseMap.containsKey(type)) {
                    diseaseMap.put(type, new ArrayList<InterveneDiseasePojo>());
                }
                diseaseMap.get(type).add(planDisease);
            }
            for (String key : diseaseMap.keySet()) {
                InterveneDiseaseGroupPojo diseaseGroup = new InterveneDiseaseGroupPojo();
                diseaseGroup.setType(key.toString());
                diseaseGroup.setInterveneDiseaseList(diseaseMap.get(key));
                resultList.add(diseaseGroup);
            }
        }
        diseaseList = resultList;
        model.addAttribute("diseaseList", diseaseList);
        return MasterPageName.INTERVENEPOINTS_INIT_VIEW;
    }

    /**
     * 分页检索健康要点信息
     * 
     * @author gss
     * @param condition
     * @return
     */
    @RequestMapping(value = MasterMapping.INTERVENEPOINTS_QUERY)
    public @ResponseBody
    WebResult<PregIntervenePointsPojo> queryIntervenePoints(IntervenePointsCondition condition) {
        WebResult<PregIntervenePointsPojo> webResult = new WebResult<PregIntervenePointsPojo>();
        List<PregIntervenePointsPojo> intervenePointsDtos = intervenePointsService.queryIntervenePoints(condition);
        webResult.setData(intervenePointsDtos);
        return webResult;
    }

    /**
     * 添加健康要点信息
     * 
     * @author gss
     * @param form
     * @return
     */
    @RequestMapping(value = MasterMapping.INTERVENEPOINTS_ADD)
    public @ResponseBody
    WebResult<PregIntervenePointsPojo> addIntervenePoints(PregIntervenePointsPojo intervenePointsBo) {
        // 参数和返回值一样是根据孕期项目复制过来的
        WebResult<PregIntervenePointsPojo> webResult = new WebResult<PregIntervenePointsPojo>();
        intervenePointsBo.setPointType("30");// 诊断课程

        PregIntervenePoints intervenePoints = TransformerUtils.transformerProperties(PregIntervenePoints.class,
                intervenePointsBo);
        // 诊断项目集合
        List<PregInterveneDiseasePoints> interveneDiseasePointss = new ArrayList<PregInterveneDiseasePoints>();
        if (StringUtils.isNotEmpty(intervenePointsBo.getInterveneDiseaseIds())) {
            String[] interveneDiseaseIdStrings = intervenePointsBo.getInterveneDiseaseIds().split(",");
            PregInterveneDiseasePoints interveneDiseasePoints = null;
            for (String disease : interveneDiseaseIdStrings) {
                interveneDiseasePoints = new PregInterveneDiseasePoints();
                interveneDiseasePoints.setDiseaseId(disease);
                interveneDiseasePoints.setType(intervenePoints.getPointType());
                interveneDiseasePoints.setIdent("1");
                interveneDiseasePoints.setStatus(1);
                interveneDiseasePointss.add(interveneDiseasePoints);
            }
        }
        PregIntervenePointsPojo intervenePointsVo = intervenePointsService.addIntervenePoints(intervenePoints,
                interveneDiseasePointss);
        intervenePointsVo.setInterveneDiseaseIds(intervenePointsBo.getInterveneDiseaseIds());
        intervenePointsVo.setInterveneDiseaseNames(intervenePointsBo.getInterveneDiseaseNames());
        PregIntervenePointsPojo intervenePointsDto = intervenePointsVo;
        webResult.setSuc(intervenePointsDto, WebMsgConstant.success_message);
        return webResult;
    }

    /**
     * 修改健康要点信息
     * 
     * @author gss
     * @param form
     * @return
     */
    @RequestMapping(value = MasterMapping.INTERVENEPOINTS_UPDATE)
    public @ResponseBody
    WebResult<PregIntervenePointsPojo> updateIntervenePoints(PregIntervenePointsPojo intervenePointsBo) {
        WebResult<PregIntervenePointsPojo> webResult = new WebResult<PregIntervenePointsPojo>();
        PregIntervenePointsPojo intervenePointsDto = null;
        intervenePointsBo.setPointType("30");// 诊断课程

        PregIntervenePoints intervenePoints = TransformerUtils.transformerProperties(PregIntervenePoints.class,
                intervenePointsBo);
        List<PregInterveneDiseasePoints> interveneDiseasePointss = new ArrayList<PregInterveneDiseasePoints>();
        String diseaseCodes = intervenePointsBo.getInterveneDiseaseIds();
        if (StringUtils.isNotEmpty(diseaseCodes)) {
            String[] interveneDiseaseIdStrings = diseaseCodes.split(",");
            for (String disease : interveneDiseaseIdStrings) {
                PregInterveneDiseasePoints InterveneDiseasePoints = new PregInterveneDiseasePoints();
                InterveneDiseasePoints.setDiseaseId(disease);
                InterveneDiseasePoints.setPointId(intervenePoints.getPointId());
                InterveneDiseasePoints.setType(intervenePoints.getPointType());
                InterveneDiseasePoints.setIdent("1");
                InterveneDiseasePoints.setStatus(1);
                interveneDiseasePointss.add(InterveneDiseasePoints);
            }
        }
        PregIntervenePointsPojo intervenePointsVo = intervenePointsService.updateIntervenePoints(intervenePoints,
                interveneDiseasePointss);
        intervenePointsVo.setInterveneDiseaseIds(diseaseCodes);
        intervenePointsVo.setInterveneDiseaseNames(intervenePointsBo.getInterveneDiseaseNames());
        intervenePointsDto = intervenePointsVo;
        webResult.setSuc(intervenePointsDto, WebMsgConstant.success_message);
        return webResult;
    }

    /**
     * 
     * 删除健康要点信息
     * 
     * @author gss
     * @param id
     * @return
     */
    @RequestMapping(value = MasterMapping.REMOVE_INTERVENEPOINTS)
    public @ResponseBody
    WebResult<Boolean> removeIntervenePoints(String id) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        intervenePointsService.removeIntervenePoints(id);
        webResult.setSuc(true, WebMsgConstant.success_message);
        return webResult;
    }

    /**
     * 饮食原则一览页
     * 
     * @author xdc
     * @param condition
     * @return
     */
    @RequestMapping(value = MasterMapping.VIEW_DIET_TENET)
    public String queryIntervenePointsView(HttpServletRequest request, Model model) {
        // 获取诊断项目list
        InterveneDiseaseCondition condition = new InterveneDiseaseCondition();

        List<InterveneDiseasePojo> interveneDiseaseList = interveneDiseaseService.queryInterveneDisease(condition);
        List<InterveneDiseaseGroupPojo> resultList = new ArrayList<InterveneDiseaseGroupPojo>();
        Map<String, List<InterveneDiseasePojo>> diseaseMap = new TreeMap<String, List<InterveneDiseasePojo>>();
        if (CollectionUtils.isNotEmpty(interveneDiseaseList)) {
            for (InterveneDiseasePojo planDisease : interveneDiseaseList) {
                String type = planDisease.getDiseaseType();
                if (!diseaseMap.containsKey(type)) {
                    diseaseMap.put(type, new ArrayList<InterveneDiseasePojo>());
                }
                diseaseMap.get(type).add(planDisease);
            }
            for (String key : diseaseMap.keySet()) {
                InterveneDiseaseGroupPojo diseaseGroup = new InterveneDiseaseGroupPojo();
                diseaseGroup.setType(key.toString());
                diseaseGroup.setInterveneDiseaseList(diseaseMap.get(key));
                resultList.add(diseaseGroup);
            }
        }
        List<InterveneDiseaseGroupPojo> diseaseList = resultList;
        model.addAttribute("diseaseList", diseaseList);
        return MasterPageName.DIET_TENET_VIEW;
    }

    /**
     * 分页检索健康要点信息
     * 
     * @author xdc
     * @param condition
     * @return
     */
    @RequestMapping(value = MasterMapping.QUERY_DIET_TENET)
    public @ResponseBody
    WebResult<PregIntervenePointsPojo> queryDietTenet(IntervenePointsCondition condition) {
        condition.setPointType("10");
        WebResult<PregIntervenePointsPojo> webResult = new WebResult<PregIntervenePointsPojo>();
        webResult.setData(intervenePointsService.queryIntervenePoints(condition));
        return webResult;
    }

    /**
     * 添加健康要点信息
     * 
     * @author gss
     * @param form
     * @return
     */
    @RequestMapping(value = MasterMapping.ADD_DIET_TENET)
    public @ResponseBody
    WebResult<PregIntervenePointsPojo> addIntervenePoints(IntervenePointsForm form) {
        form.setPointType("10");
        WebResult<PregIntervenePointsPojo> webResult = new WebResult<PregIntervenePointsPojo>();
        PregIntervenePointsPojo intervenePointsBo = null;
        intervenePointsBo = TransformerUtils.transformerProperties(PregIntervenePointsPojo.class, form);

        PregIntervenePoints intervenePoints = TransformerUtils.transformerProperties(PregIntervenePoints.class,
                intervenePointsBo);
        // 诊断项目集合
        List<PregInterveneDiseasePoints> interveneDiseasePointss = new ArrayList<PregInterveneDiseasePoints>();
        if (StringUtils.isNotEmpty(intervenePointsBo.getInterveneDiseaseIds())) {
            String[] interveneDiseaseIdStrings = intervenePointsBo.getInterveneDiseaseIds().split(",");
            PregInterveneDiseasePoints interveneDiseasePoints = null;
            for (String disease : interveneDiseaseIdStrings) {
                interveneDiseasePoints = new PregInterveneDiseasePoints();
                interveneDiseasePoints.setDiseaseId(disease);
                interveneDiseasePoints.setType(intervenePoints.getPointType());
                interveneDiseasePoints.setIdent("1");
                interveneDiseasePoints.setStatus(1);
                interveneDiseasePointss.add(interveneDiseasePoints);
            }
        }
        PregIntervenePointsPojo intervenePointsVo = intervenePointsService.addIntervenePoints(intervenePoints,
                interveneDiseasePointss);
        intervenePointsVo.setInterveneDiseaseIds(intervenePointsBo.getInterveneDiseaseIds());
        intervenePointsVo.setInterveneDiseaseNames(intervenePointsBo.getInterveneDiseaseNames());
        webResult.setSuc(intervenePointsVo);
        return webResult;
    }

    /**
     * 修改健康要点信息
     * 
     * @author gss
     * @param form
     * @return
     */
    @RequestMapping(value = MasterMapping.UPDATE_DIET_TENET)
    public @ResponseBody
    WebResult<PregIntervenePointsPojo> updateIntervenePoints(IntervenePointsForm form) {
        form.setPointType("10");
        WebResult<PregIntervenePointsPojo> webResult = new WebResult<PregIntervenePointsPojo>();
        PregIntervenePointsPojo intervenePointsBo = null;
        intervenePointsBo = TransformerUtils.transformerProperties(PregIntervenePointsPojo.class, form);
        PregIntervenePoints intervenePoints = TransformerUtils.transformerProperties(PregIntervenePoints.class,
                intervenePointsBo);
        List<PregInterveneDiseasePoints> interveneDiseasePointss = new ArrayList<PregInterveneDiseasePoints>();
        String diseaseCodes = intervenePointsBo.getInterveneDiseaseIds();
        if (StringUtils.isNotEmpty(diseaseCodes)) {
            String[] interveneDiseaseIdStrings = diseaseCodes.split(",");
            for (String disease : interveneDiseaseIdStrings) {
                PregInterveneDiseasePoints InterveneDiseasePoints = new PregInterveneDiseasePoints();
                InterveneDiseasePoints.setDiseaseId(disease);
                InterveneDiseasePoints.setPointId(intervenePoints.getPointId());
                InterveneDiseasePoints.setType(intervenePoints.getPointType());
                InterveneDiseasePoints.setIdent("1");
                InterveneDiseasePoints.setStatus(1);
                interveneDiseasePointss.add(InterveneDiseasePoints);
            }
        }
        PregIntervenePointsPojo intervenePointsVo = intervenePointsService.updateIntervenePoints(intervenePoints,
                interveneDiseasePointss);
        intervenePointsVo.setInterveneDiseaseIds(diseaseCodes);
        intervenePointsVo.setInterveneDiseaseNames(intervenePointsBo.getInterveneDiseaseNames());
        webResult.setSuc(intervenePointsVo);
        return webResult;
    }

    /**
     * 
     * 删除健康要点信息
     * 
     * @author gss
     * @param id
     * @return
     */
    @RequestMapping(value = MasterMapping.REMOVE_DIET_TENET)
    public @ResponseBody
    WebResult<Boolean> removeDietTenet(String id) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        intervenePointsService.removeIntervenePoints(id);
        webResult.setSuc(true, "成功");
        return webResult;
    }

}

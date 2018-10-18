
package com.mnt.preg.system.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.preg.system.entity.Schedule;
import com.mnt.preg.system.mapping.SystemMapping;
import com.mnt.preg.system.mapping.SystemPageName;
import com.mnt.preg.system.pojo.SchedulePojo;
import com.mnt.preg.system.service.ScheduleService;
import com.mnt.preg.web.constants.WebMsgConstant;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 一周课程配置Controller
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：v1.0 2014-12-30 zcq 初版
 */
@Controller
public class ScheduleController extends BaseController {

    @Resource
    private ScheduleService scheduleService;

    /**
     * 初始化页面(不需要分页)
     * 
     * @return
     */
    @RequestMapping(value = SystemMapping.SCHEDULE_VIEW)
    public String querySymptoms(Model model) {
        model.addAttribute("schedule", scheduleService.queryScheduleByCondition(null));
        return SystemPageName.SCHEDULE_INIT_VIEW;
    }

    /**
     * 查询
     * 
     * @return
     */
    @RequestMapping(value = SystemMapping.SCHEDULE_QUERY)
    public @ResponseBody
    WebResult<SchedulePojo> querySymptoms(Schedule condition) {
        WebResult<SchedulePojo> webResult = new WebResult<SchedulePojo>();
        webResult.setData(scheduleService.queryScheduleByCondition(condition));
        return webResult;
    }

    /**
     * 添加信息
     * 
     * @author gss
     * @param nutrientBo
     * @return
     */
    @RequestMapping(value = SystemMapping.SCHEDULE_ADD)
    public String addNutrient(Schedule schedule, Model model) {
        scheduleService.saveSchedule(schedule);
        model.addAttribute("schedule", scheduleService.queryScheduleByCondition(null));
        model.addAttribute("successInfo", WebMsgConstant.success_message);
        return SystemPageName.SCHEDULE_INIT_VIEW;
    }

    /**
     * 修改信息
     * 
     * @author gss
     * @param nutrientBo
     * @return
     */
    @RequestMapping(value = SystemMapping.SCHEDULE_UPDATE)
    public String udpateSchedule(Schedule schedule, Model model) {
        WebResult<SchedulePojo> webResult = new WebResult<SchedulePojo>();
        webResult.setSuc(scheduleService.updateSchedule(schedule), WebMsgConstant.success_message);
        model.addAttribute("schedule", scheduleService.queryScheduleByCondition(null));
        model.addAttribute("successInfo", WebMsgConstant.success_message);
        return SystemPageName.SCHEDULE_INIT_VIEW;
    }

    /**
     * 
     * 删除信息
     * 
     * @author gss
     * @param id
     * @return
     */
    @RequestMapping(value = SystemMapping.SCHEDULE_DELETE)
    public String removeSchedule(String scheduleId, Model model) {
        scheduleService.removeSchedule(scheduleId);
        model.addAttribute("schedule", scheduleService.queryScheduleByCondition(null));
        return SystemPageName.SCHEDULE_INIT_VIEW;
    }
}

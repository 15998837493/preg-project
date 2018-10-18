
package com.mnt.preg.master.controller.preg;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.preg.master.condition.preg.PregnancyCourseCondition;
import com.mnt.preg.master.condition.preg.PregnancyCourseDetailCondition;
import com.mnt.preg.master.entity.preg.PregCourse;
import com.mnt.preg.master.entity.preg.PregCourseDetail;
import com.mnt.preg.master.form.basic.PregCourseDetailForm;
import com.mnt.preg.master.form.preg.PregCourseForm;
import com.mnt.preg.master.mapping.basic.MasterMapping;
import com.mnt.preg.master.mapping.basic.MasterPageName;
import com.mnt.preg.master.pojo.preg.PregCourseDetailPojo;
import com.mnt.preg.master.pojo.preg.PregCoursePojo;
import com.mnt.preg.master.service.preg.PregCourseService;
import com.mnt.preg.web.constants.WebMsgConstant;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 
 * 基础课程管理
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-9-12 gss 初版
 */
@Controller
public class CourseController extends BaseController {

    @Resource
    private PregCourseService pregCourseService;

    /**
     * 
     * 异步获取课程信息
     * 
     * @author gss
     * @param condition
     * @return
     */
    @RequestMapping(value = MasterMapping.COURSE_QUERY)
    public @ResponseBody
    WebResult<List<PregCoursePojo>> queryCourses(PregnancyCourseCondition condition) {
        WebResult<List<PregCoursePojo>> webResult = new WebResult<List<PregCoursePojo>>();
        webResult.setSuc(pregCourseService.queryPregnancyCourses(condition));
        return webResult;
    }

    /**
     * 
     * 删除课程信息
     * 
     * @author gss
     * @param id
     * @return
     */
    @RequestMapping(value = MasterMapping.COURSE_DELETE)
    public @ResponseBody
    WebResult<Boolean> deleteCourse(String id) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        pregCourseService.removePregnancyCourse(id);
        webResult.setSuc(true, WebMsgConstant.success_message);
        return webResult;
    }

    /**
     * 
     * 新增课程信息
     * 
     * @author gss
     * @return
     */
    @RequestMapping(value = MasterMapping.COURSE_SAVE)
    public @ResponseBody
    WebResult<Boolean> saveCourse(PregCourse courseBo) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        pregCourseService.addPregnancyCourse(courseBo);
        webResult.setSuc(true, WebMsgConstant.save_message);
        return webResult;
    }

    /**
     * 
     * 更新课程信息
     * 
     * @author gss
     * @return
     */
    @RequestMapping(value = MasterMapping.COURSE_UPDATE)
    public @ResponseBody
    WebResult<Boolean> updateCourse(PregCourse courseBo) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        pregCourseService.updatePregnancyCourse(courseBo);
        webResult.setSuc(true, WebMsgConstant.save_message);
        return webResult;
    }

    /**
     * 
     * 验证模板名称重复
     * 
     * @author gss
     * @param name
     * @param id
     * @return
     */
    @RequestMapping(value = MasterMapping.COURSE_NAME_VALIDATE)
    public @ResponseBody
    boolean queryCoursesByName(String name) {
        List<PregCoursePojo> courseDtos = pregCourseService.queryCoursesByName(name);
        if (CollectionUtils.isEmpty(courseDtos)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 
     * 异步获取体检项目字典信息
     * 
     * @author gss
     * @param condition
     * @return
     */
    @RequestMapping(value = MasterMapping.QUERY_PREGNANCYCOURSE)
    public @ResponseBody
    WebResult<PregCoursePojo> queryPregnancyCourse(PregnancyCourseCondition condition) {
        WebResult<PregCoursePojo> webResult = new WebResult<PregCoursePojo>();
        List<PregCoursePojo> PregnancyCourseListVo = pregCourseService.queryPregnancyCourses(condition);
        webResult.setData(PregnancyCourseListVo);
        return webResult;
    }

    /**
     * 孕期课程信息删除
     * 
     * @param id
     *            主键
     * @return
     * 
     */
    @RequestMapping(value = MasterMapping.PREGNANCYCOURSE_REMOVE)
    public @ResponseBody
    WebResult<Boolean> removePregnancyCourse(HttpServletRequest request, @RequestParam String id) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        if (StringUtils.isNotEmpty(id)) {
            pregCourseService.removePregnancyCourse(id);
            webResult.setSuc(true, WebMsgConstant.success_message);
        } else {
            webResult.setError("[孕期课程管理]---孕期课程信息删除异常", false);
        }

        return webResult;
    }

    /**
     * 
     * 孕期课程增加页面初始化
     * 
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = MasterMapping.ADD_INIT_PREGNANCYCOURSE)
    public String initAddPregnancyCourse(HttpServletRequest request, Model model) {
        return MasterPageName.PREGNANCYCOURSE_INIT_ADD;
    }

    /**
     * 孕期课程记录增加功能
     * 
     * @param PregCourseForm
     * @return
     */
    @RequestMapping(value = MasterMapping.ADD_PREGNANCYCOURSE)
    public @ResponseBody
    WebResult<PregCoursePojo> addPregnancyCourse(HttpServletRequest request, PregCourseForm form) {
        WebResult<PregCoursePojo> webResult = new WebResult<PregCoursePojo>();
        PregCourse pregnancyCourseBo = TransformerUtils.transformerProperties(PregCourse.class,
                form);
        webResult.setSuc(pregCourseService.addPregnancyCourse(pregnancyCourseBo),
                WebMsgConstant.success_message);
        return webResult;

    }

    /**
     * 
     * 项目修改初始化
     * 
     * @param request
     * @param familyId
     * @param model
     * @return
     */
    @RequestMapping(value = MasterMapping.UPDATE_INIT_PREGNANCYCOURSE)
    public @ResponseBody
    WebResult<PregCoursePojo> PregnancyCourseUpdateInit(HttpServletRequest request, @RequestParam String
            id,
            Model model) {
        WebResult<PregCoursePojo> webResult = new WebResult<PregCoursePojo>();
        PregCoursePojo pregnancyCourseVo;
        pregnancyCourseVo = pregCourseService.getPregnancyCourse(id);
        webResult.setSuc(pregnancyCourseVo, "成功");
        return webResult;
    }

    /**
     * 项目修改
     * 
     * @param itemForm
     *            修改表单数据
     * @return
     * 
     */
    @RequestMapping(value = MasterMapping.UPDATE_PREGNANCYCOURSE)
    public @ResponseBody
    WebResult<PregCoursePojo> updatePregnancyCourse(HttpServletRequest request, PregCourseForm form) {
        WebResult<PregCoursePojo> webResult = new WebResult<PregCoursePojo>();
        PregCourse pregnancyCourseBo = TransformerUtils.transformerProperties(PregCourse.class,
                form);
        pregCourseService.updatePregnancyCourse(pregnancyCourseBo);
        PregCoursePojo pregnancyCourseVo = pregCourseService.getPregnancyCourse(pregnancyCourseBo.getId());
        List<PregCourseDetailPojo> detailVos = pregCourseService.queryPregCourseDetailById(pregnancyCourseBo
                .getPregId());
        StringBuffer sb = new StringBuffer();
        for (PregCourseDetailPojo pregnancyCourseDetailVo : detailVos) {
            if (StringUtils.isEmpty(pregnancyCourseDetailVo.getPregDeCode())) {
                continue;
            }
            sb.append(pregnancyCourseDetailVo.getPregDeCode()).append("，");
        }
        if (sb.length() > 0) {
            pregnancyCourseVo.setPregDeString(sb.toString().substring(0, sb.toString().length() - 1));
        }
        webResult.setSuc(pregnancyCourseVo);
        return webResult;
    }

    /**
     * 
     * 异步获取明细记录
     * 
     * @author gss
     * @param condition
     * @return
     */
    @RequestMapping(value = MasterMapping.QUERY_DETAIL_COURSE)
    public @ResponseBody
    List<PregCourseDetailPojo> queryOption(PregnancyCourseDetailCondition condition) {
        List<PregCourseDetailPojo> pregnancyCourseDetailListDto = null;
        pregnancyCourseDetailListDto = pregCourseService.queryPregCourseDetails(condition);
        return pregnancyCourseDetailListDto == null ? null : pregnancyCourseDetailListDto;

    }

    /**
     * 
     * 向上移动体检分类信息
     * 
     * @author mnt_zhangjing
     */
    @RequestMapping(value = MasterMapping.MOVE_PREGNANCYCOURSE_DETAIL)
    public @ResponseBody
    WebResult<Boolean> upwardPregnancyCourseDetail(String flag, String id, HttpServletRequest request) {
        WebResult<Boolean> resultEntity = new WebResult<Boolean>();
        PregCourseDetailPojo nearPreCourseDetail = null;
        PregCourseDetailPojo pregnancyCourseDetailDto = pregCourseService.getPregnancyCourseDetail(id);
        PregCourseDetailForm form = TransformerUtils.transformerProperties(PregCourseDetailForm.class,
                pregnancyCourseDetailDto);
        form.setMoveFlag(flag);
        if (form.getPregDeOrder() <= 0 && StringUtils.equals(form.getMoveFlag(), "up")) {
            resultEntity.setSuc(false, "已是最上层");
            return resultEntity;
        }
        Integer maxOrderNo = pregCourseService.getMaxOrderCodeByPregId(form.getPregId());
        if (maxOrderNo == form.getPregDeOrder() && StringUtils.equals(form.getMoveFlag(), "down")) {
            resultEntity.setSuc(false, "已是最下层");
            return resultEntity;
        }
        // 如果移动标识是向上移动
        if (StringUtils.equals(form.getMoveFlag(), "up")) {

            // 查询移动对象的上一个的基本信息
            nearPreCourseDetail = pregCourseService.getPregnancyCourseDetailByPregId(form.getPregId(),
                    form.getPregDeOrder() - 1);

            Integer nearOrder = nearPreCourseDetail.getPregDeOrder();
            Integer movingOrder = form.getPregDeOrder();

            form.setPregDeOrder(nearOrder);// 设置排序号

            nearPreCourseDetail.setPregDeOrder(movingOrder);

        } else if (StringUtils.equals(form.getMoveFlag(), "down")) {
            // 查询移动对象的上一个问题的基本信息
            nearPreCourseDetail = pregCourseService.getPregnancyCourseDetailByPregId(form.getPregId(),
                    form.getPregDeOrder() + 1);// 上一个
            Integer orgOrder = form.getPregDeOrder();
            form.setPregDeOrder(nearPreCourseDetail.getPregDeOrder());
            nearPreCourseDetail.setPregDeOrder(orgOrder);
        }

        pregCourseService.updatePregnancyCourseDetail(
                TransformerUtils.transformerProperties(PregCourseDetail.class, nearPreCourseDetail));
        pregCourseService.updatePregnancyCourseDetail(
                TransformerUtils.transformerProperties(PregCourseDetail.class, form));
        resultEntity.setSuc(true, WebMsgConstant.success_message);
        return resultEntity;
    }

    /**
     * 
     * 初始化摄入模板明细页面
     * 
     * @author mnt_zhangjing
     * @param name
     * @param id
     * @return
     */
    @RequestMapping(value = MasterMapping.PREGNANCYCOURSE_DETAIL_VIEW)
    public String initIntakeDetailView(String name, String id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("name", name);
        return MasterPageName.PREGNANCYCOURSE_DETAIL_INIT;

    }

    /**
     * 
     * 根据课程主键获取课程明细
     * 
     * @author gss
     * @param condition
     * @return
     */
    @RequestMapping(value = MasterMapping.PREGNANCYCOURSE_DETAIL_QUERY)
    public @ResponseBody
    WebResult<List<PregCourseDetailPojo>> queryPregCourseDetailById(String pregId) {
        WebResult<List<PregCourseDetailPojo>> webResult = new
                WebResult<List<PregCourseDetailPojo>>();
        webResult.setSuc(pregCourseService.queryPregCourseDetailById(pregId));
        return webResult;
    }

    /**
     * 
     * 验证课程明细中代码是否重复
     * 
     * @author scd
     * @param condition
     * @return
     */
    @RequestMapping(value = MasterMapping.PREGDECODE_VALIDATE)
    public @ResponseBody
    boolean pregdeCodeValidate(String pregId, String pregDeCode, String type, String pregDeOldCode) {
        boolean flag = true;
        if (type.equals("add") && !StringUtils.isEmpty(type)) {
            List<PregCourseDetailPojo> detailList = pregCourseService.queryPregCourseDetailById(pregId);
            flag = pregCourseService.pregdeCodeValidate(pregDeCode, detailList);
        } else if (type.equals("update") && !StringUtils.isEmpty(type)) {
            if (pregDeOldCode.equals(pregDeCode)) {
                flag = true;
            } else {
                Integer codeCount = pregCourseService.pregdeCodeValidateUpdate(pregId, pregDeCode);
                if (codeCount > 0) {
                    flag = false;
                }
            }
        }
        return flag;
    }

    /**
     * 孕期课程记录增加功能
     * 
     * @param PregCourseForm
     * @return
     */
    @RequestMapping(value = MasterMapping.PREGNANCYCOURSE_DETAIL_ADD)
    public @ResponseBody
    WebResult<Boolean> addPregnancyCourseDetail(HttpServletRequest request, PregCourseDetailForm form) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        PregCourseDetail pregnancyCourseDetailBo = TransformerUtils.transformerProperties(
                PregCourseDetail.class,
                form);
        pregCourseService.addPregnancyCourseDetail(pregnancyCourseDetailBo);
        webResult.setSuc(true, WebMsgConstant.success_message);
        return webResult;

    }

    /**
     * 
     * 删除课程明细信息
     * 
     * @author gss
     * @param id
     * @return
     */
    @RequestMapping(value = MasterMapping.PREGNANCYCOURSE_DETAIL_REMOVE)
    public @ResponseBody
    WebResult<Boolean> deletePregnancyCourseDetail(String id) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        PregCourseDetailPojo pregnancyCourseDetailVo = pregCourseService.getPregnancyCourseDetail(id);
        PregCourseDetail pregnancyCourseDetail = TransformerUtils.transformerProperties(PregCourseDetail.class,
                pregnancyCourseDetailVo);
        pregnancyCourseDetail.setFlag(0);
        pregCourseService.updatePregnancyCourseDetail(pregnancyCourseDetail);
        webResult.setSuc(true, WebMsgConstant.success_message);
        return webResult;
    }

    /**
     * 
     * 修改课程明细
     * 
     * @author mnt_zhangjing
     * @param form
     * @return
     */
    @RequestMapping(value = MasterMapping.PREGNANCYCOURSE_DETAIL_UPDATE)
    public @ResponseBody
    WebResult<Boolean>
            updatePregnancyCourseDetail(PregCourseDetailForm form) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        if (StringUtils.isNotEmpty(form.getId())) {
            PregCourseDetail pregnancyCourseDetailBo = TransformerUtils.transformerProperties(
                    PregCourseDetail.class,
                    form);
            pregCourseService.updatePregnancyCourseDetail(pregnancyCourseDetailBo);
            webResult.setSuc(true, WebMsgConstant.success_message);
        } else {
            webResult.setError(WebMsgConstant.fail_message, false);
        }
        return webResult;
    }

    /**
     * 
     * 验证课程主键是否重复
     * 
     * @author gss
     * @param name
     * @param id
     * @return
     */
    @RequestMapping(value = MasterMapping.PREGNANCYCOURSE_VALIDATE)
    public @ResponseBody
    boolean queryPregnancyCourseValidate(String id) {
        PregnancyCourseCondition condition = new PregnancyCourseCondition();
        condition.setPregId(id);
        List<PregCoursePojo> pregnancyCourseDtos = pregCourseService.queryPregnancyCourses(condition);
        if (CollectionUtils.isEmpty(pregnancyCourseDtos)) {
            return true;
        }
        return false;
    }

}

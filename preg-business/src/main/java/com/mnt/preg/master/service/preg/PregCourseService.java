
package com.mnt.preg.master.service.preg;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import com.mnt.preg.master.condition.preg.PregnancyCourseCondition;
import com.mnt.preg.master.condition.preg.PregnancyCourseDetailCondition;
import com.mnt.preg.master.entity.preg.PregCourse;
import com.mnt.preg.master.entity.preg.PregCourseDetail;
import com.mnt.preg.master.pojo.preg.PregCourseDetailPojo;
import com.mnt.preg.master.pojo.preg.PregCoursePojo;

/**
 * 
 * 孕期课程接口
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-4-5 gss 初版
 */
@Validated
public interface PregCourseService {

    /**
     * 
     * 条件查询孕期课程信息
     * 
     * @author gss
     * @param condition
     * @return List<PregnancyCourseVo>
     */
    List<PregCoursePojo> queryPregnancyCourses(PregnancyCourseCondition condition);

    /**
     * 
     * 根据名称查询id不等于指定id的数据
     * 
     * @author gss
     * @param name
     * @param id
     * @return List<CourseVo>
     */
    List<PregCoursePojo> queryCoursesByName(String name);

    /**
     * 
     * 条件查询孕期课程信息
     * 
     * @author gss
     * @param condition
     * @return PregnancyCourseListVo
     */
    List<PregCoursePojo> queryPregnancyCourseList(PregnancyCourseCondition condition);

    /**
     * 
     * 保存孕期课程信息
     * 
     * @author gss
     * @param course
     */
    PregCoursePojo addPregnancyCourse(@Valid @NotNull PregCourse course);

    /**
     * 
     * 保存孕期明细课程信息
     * 
     * @author gss
     * @param pregnancyCourseDetail
     */
    void addPregnancyCourseDetail(@Valid @NotNull PregCourseDetail pregnancyCourseDetail);

    /**
     * 
     * 更新孕期课程信息
     * 
     * @author gss
     * @param course
     */
    PregCoursePojo updatePregnancyCourse(PregCourse course);

    /**
     * 
     * 根据主键查询基本信息
     * 
     * @author gss
     * @param id
     * @return PregnancyCourseVo
     */
    public PregCoursePojo getPregnancyCourse(String id);

    /**
     * 
     * 删除课程信息及配置信息
     * 
     * @author mnt_zhangjing
     * @param id
     */
    void removePregnancyCourse(@NotBlank String id);

    /**
     * 
     * 根据主键查询课程信息明细
     * 
     * @author gss
     * @param pregId
     * @return
     */
    List<PregCourseDetailPojo> queryPregCourseDetailById(@NotBlank String pregId);

    /**
     * 
     * 验证课程明细代码是否重复
     * 
     * @author scd
     * @param pregDeCode
     * @return
     */
    boolean pregdeCodeValidate(@NotBlank String pregDeCode, List<PregCourseDetailPojo> detailList);

    /**
     * 
     * 验证课程明细代码是否重复(修改操作)
     * 
     * @author scd
     * @param pregDeCode
     * @return
     */
    Integer pregdeCodeValidateUpdate(@NotBlank String pregId, @NotBlank String pregDeCode);

    /**
     * 
     * 条件查询孕期课程信息
     * 
     * @author gss
     * @param condition
     * @return PregnancyCourseDetailListVo
     */
    List<PregCourseDetailPojo> queryPregCourseDetails(PregnancyCourseDetailCondition condition);

    /**
     * 
     * 更新孕期课程详情
     * 
     * @author gss
     * @param pregnancyCourseDetail
     */
    public void updatePregnancyCourseDetail(PregCourseDetail pregnancyCourseDetail);

    /**
     * 
     * 查询孕期课程信息最大排序号
     * 
     * @author gss
     * @param pregId
     * @return Integer
     */
    Integer getMaxOrderCodeByPregId(@NotBlank String pregId);

    /**
     * 
     * 根据课程主键和排序号查询孕期课程信息
     * 
     * @author gss
     * @param pregId
     * @param orderNo
     * @return PregnancyCourseDetailVo
     */
    PregCourseDetailPojo getPregnancyCourseDetailByPregId(String pregId, Integer orderNo);

    /**
     * 
     * 根据主键查询孕期课程明细
     * 
     * @author gss
     * @param id
     * @return PregnancyCourseDetailVo
     */
    public PregCourseDetailPojo getPregnancyCourseDetail(@NotBlank String id);

}

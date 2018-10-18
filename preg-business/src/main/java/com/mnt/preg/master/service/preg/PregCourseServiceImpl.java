
package com.mnt.preg.master.service.preg;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.core.exception.ServiceException;
import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.core.utils.HQLSymbol;
import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.preg.main.results.ResultCode;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.master.condition.preg.PregnancyCourseCondition;
import com.mnt.preg.master.condition.preg.PregnancyCourseDetailCondition;
import com.mnt.preg.master.dao.preg.PregCourseDAO;
import com.mnt.preg.master.entity.preg.PregCourse;
import com.mnt.preg.master.entity.preg.PregCourseDetail;
import com.mnt.preg.master.pojo.preg.PregCourseDetailPojo;
import com.mnt.preg.master.pojo.preg.PregCoursePojo;

/**
 * 疾病表接口实现
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-4-5 gss 初版
 */
@Service
public class PregCourseServiceImpl extends BaseService implements PregCourseService {

    @Resource
    private PregCourseDAO pregCourseDAO;

    @Override
    @Transactional(readOnly = true)
    public List<PregCoursePojo> queryPregnancyCourses(PregnancyCourseCondition condition) {
        if (condition == null) {
            condition = new PregnancyCourseCondition();
        }

        List<PregCoursePojo> courseVos = pregCourseDAO.queryPregnancyCourse(condition);
        if (CollectionUtils.isNotEmpty(courseVos)) {
            for (PregCoursePojo pregnancyCourseVo : courseVos) {
                List<PregCourseDetailPojo> detailVos = pregCourseDAO
                        .queryPregCourseDetailById(pregnancyCourseVo.getPregId());
                if (CollectionUtils.isEmpty(detailVos)) {
                    continue;
                }
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
            }
        }
        return courseVos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PregCoursePojo> queryCoursesByName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        return pregCourseDAO.queryCourseByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PregCoursePojo> queryPregnancyCourseList(PregnancyCourseCondition condition) {
        return pregCourseDAO.queryPregnancyCourse(condition);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public PregCoursePojo addPregnancyCourse(PregCourse course) {
        String id = (String) pregCourseDAO.save(course);
        return pregCourseDAO.getTransformerBean(id, PregCourse.class, PregCoursePojo.class);

    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public PregCoursePojo updatePregnancyCourse(PregCourse course) {
        if (course == null || StringUtils.isEmpty(course.getId())) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        List<HQLConditionParam> updateParams = new ArrayList<HQLConditionParam>();
        updateParams.add(new HQLConditionParam("id", HQLSymbol.EQ.toString(), course.getId()));
        int count = pregCourseDAO.updateHQL(course, updateParams);
        if (count != 1) {
            throw new ServiceException(ResultCode.ERROR_99998);
        }
        return pregCourseDAO.getTransformerBean(course.getId(), PregCourse.class, PregCoursePojo.class);
    }

    @Override
    @Transactional(readOnly = true)
    public PregCoursePojo getPregnancyCourse(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        return pregCourseDAO.getTransformerBean(id, PregCourse.class, PregCoursePojo.class);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void removePregnancyCourse(String id) {
        PregCourse pregnancyCourse = pregCourseDAO.get(PregCourse.class, id);
        pregnancyCourse.setFlag(0);
        PregCoursePojo pregnancyCourseVo = updatePregnancyCourse(pregnancyCourse);
        if (pregnancyCourseVo != null && StringUtils.isNotEmpty(pregnancyCourseVo.getPregId())) {
            List<PregCourseDetailPojo> pregnancyCourseDetailVos = pregCourseDAO
                    .queryPregCourseDetailById(pregnancyCourseVo.getPregId());
            if (CollectionUtils.isNotEmpty(pregnancyCourseDetailVos)) {
                PregCourseDetail pregnancyCourseDetail = null;
                for (PregCourseDetailPojo pregnancyCourseDetailVo : pregnancyCourseDetailVos) {
                    pregnancyCourseDetail = TransformerUtils.transformerProperties(PregCourseDetail.class,
                            pregnancyCourseDetailVo);
                    pregnancyCourseDetail.setFlag(0);
                    updatePregnancyCourseDetail(pregnancyCourseDetail);
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void addPregnancyCourseDetail(PregCourseDetail pregnancyCourseDetail) {
        Integer maxOrder = pregCourseDAO.getMaxOrderCodeByPregId(pregnancyCourseDetail.getPregId());
        pregnancyCourseDetail.setPregDeOrder(maxOrder == null ? 0 : maxOrder + 1);
        pregCourseDAO.save(pregnancyCourseDetail);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PregCourseDetailPojo> queryPregCourseDetailById(String pregId) {
        return pregCourseDAO.queryPregCourseDetailById(pregId);
    }

    @Override
    public boolean pregdeCodeValidate(String pregDeCode, List<PregCourseDetailPojo> detailList) {
        boolean flag = true;
        for (PregCourseDetailPojo detail : detailList) {
            if (detail.getPregDeCode().equals(pregDeCode)) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    @Override
    @Transactional(readOnly = true)
    public Integer pregdeCodeValidateUpdate(String pregId, String pregDeCode) {
        return pregCourseDAO.pregdeCodeValidateUpdate(pregId, pregDeCode);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PregCourseDetailPojo> queryPregCourseDetails(PregnancyCourseDetailCondition condition) {
        if (condition == null) {
            condition = new PregnancyCourseDetailCondition();
        }
        List<PregCourseDetailPojo> pregnancyCourseDetailVos = pregCourseDAO.queryPregCourseDetails(condition);
        return pregnancyCourseDetailVos;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updatePregnancyCourseDetail(PregCourseDetail pregnancyCourseDetail) {
        if (pregnancyCourseDetail == null || StringUtils.isEmpty(pregnancyCourseDetail.getId())) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }

        List<HQLConditionParam> updateParams = new ArrayList<HQLConditionParam>();
        updateParams.add(new HQLConditionParam("id", HQLSymbol.EQ.toString(), pregnancyCourseDetail.getId()));
        int count = pregCourseDAO.updateHQL(pregnancyCourseDetail, updateParams);
        if (count != 1) {
            throw new ServiceException(ResultCode.ERROR_99998);
        }
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public Integer getMaxOrderCodeByPregId(String pregId) {
        return pregCourseDAO.getMaxOrderCodeByPregId(pregId);
    }

    @Override
    public PregCourseDetailPojo getPregnancyCourseDetailByPregId(String pregId, Integer orderNo) {
        if (StringUtils.isEmpty(pregId) || orderNo == null) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        return pregCourseDAO.getPregnancyCourseDetailByPregId(pregId, orderNo);
    }

    @Override
    @Transactional(readOnly = true)
    public PregCourseDetailPojo getPregnancyCourseDetail(String id) {
        return pregCourseDAO.getPregnancyCourseDetail(id);
    }

}

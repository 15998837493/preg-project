/**
 * 
 */

package com.mnt.preg.customer.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.core.utils.HQLSymbol;
import com.mnt.preg.customer.dao.PregCourseFeedbackDAO;
import com.mnt.preg.customer.entity.PregCourseFeedback;
import com.mnt.preg.customer.pojo.PregCourseFeedbackPojo;
import com.mnt.preg.main.service.BaseService;

/**
 * 客户管理
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-16 zcq 初版
 */
@Service
public class PregCourseFeedbackServiceImpl extends BaseService implements PregCourseFeebackService {

    @Resource
    private PregCourseFeedbackDAO courseFeedbackDAO;

    @Override
    @Transactional(readOnly = true)
    public List<PregCourseFeedbackPojo> queryCourseFeedback(PregCourseFeedback condition) {
        return courseFeedbackDAO.queryCourseFeedback(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public PregCourseFeedbackPojo getCourseFeedbackById(String id) {
        return courseFeedbackDAO.getPregCourseFeedbackById(id);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public PregCourseFeedbackPojo addCourseFeedback(PregCourseFeedback courseFeedback) {
        String id = (String) courseFeedbackDAO.save(courseFeedback);
        return courseFeedbackDAO.getPregCourseFeedbackById(id);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateCourseFeedback(PregCourseFeedback courseFeedback) {
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("feed_id", HQLSymbol.EQ.toString(), courseFeedback.getFeedId()));
        courseFeedbackDAO.updateHQL(courseFeedback, conditionParams);
    }
}

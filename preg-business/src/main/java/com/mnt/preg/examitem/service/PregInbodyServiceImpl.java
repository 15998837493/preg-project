
package com.mnt.preg.examitem.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.core.exception.ServiceException;
import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.core.utils.HQLSymbol;
import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.health.utils.times.JodaTimeTools;
import com.mnt.preg.customer.dao.CustomerDAO;
import com.mnt.preg.customer.pojo.PregArchivesPojo;
import com.mnt.preg.examitem.condition.InbodyCondition;
import com.mnt.preg.examitem.dao.ExamItemDAO;
import com.mnt.preg.examitem.dao.ExamResultRecordDAO;
import com.mnt.preg.examitem.dao.PregInbodyDAO;
import com.mnt.preg.examitem.entity.PregInbodyBca;
import com.mnt.preg.examitem.pojo.PregInBodyBcaPojo;
import com.mnt.preg.examitem.pojo.PregInBodyPojo;
import com.mnt.preg.main.results.ResultCode;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.platform.dao.PregArchivesDAO;
import com.mnt.preg.platform.dao.PregDiagnosisDAO;
import com.mnt.preg.platform.dao.PregPlanDAO;
import com.mnt.preg.statistic.pojo.DiagnosisInfoPojo;

/**
 * 诊疗服务
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-21 zcq 初版
 */
@Service
public class PregInbodyServiceImpl extends BaseService implements PregInbodyService {

    @Resource
    private PregInbodyDAO pregInbodyDAO;

    @Resource
    private ExamItemDAO examItemDAO;

    @Resource
    private ExamResultRecordDAO examResultRecordDAO;

    @Resource
    private PregPlanDAO pregPlanDAO;

    @Resource
    private PregDiagnosisDAO pregDiagnosisDAO;

    @Resource
    private PregArchivesDAO pregArchivesDAO;

    @Resource
    private CustomerDAO customerDAO;// 患者DAO

    @Override
    @Transactional(readOnly = true)
    public PregInBodyBcaPojo getInbodyByCondition(InbodyCondition condition) {
        if (condition.getEndDate() != null) {
            condition.setEndDate(JodaTimeTools.after_day(condition.getEndDate(), 1));
        }
        return pregInbodyDAO.getInbodyByCondition(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PregInBodyBcaPojo> queryInbodyHistory(InbodyCondition condition) {
        if (condition != null && condition.getEndDate() != null) {
            condition.setEndDate(JodaTimeTools.after_day(condition.getEndDate(), 1));
        }
        return pregInbodyDAO.queryInbodyHistory(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PregInBodyBcaPojo> getStatisticInbodyByCondition(InbodyCondition condition) {
        return pregInbodyDAO.getStatisticInbodyByCondition(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiagnosisInfoPojo> queryBaseInbodyByConsition(InbodyCondition condition) {
        return pregInbodyDAO.queryBaseInbodyByConsition(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> queryInbodyDatetimes(InbodyCondition condition) {
        return pregInbodyDAO.queryInbodyDatetimes(condition);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addInbdoy(PregInBodyPojo groupBo) {
        if (pregInbodyDAO.checkInbodyDatetimes(groupBo.getDatetimes()) > 0) {
            throw new ServiceException(ResultCode.ERROR_10012);
        }
        return this.addInbdoyInfo(groupBo);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addInbdoyInfo(PregInBodyPojo groupBo) {
        // BCA记录表
        PregInbodyBca bca = TransformerUtils.transformerProperties(PregInbodyBca.class, groupBo);

        PregArchivesPojo preArchive = pregArchivesDAO.getLastPregnancyArchive(groupBo.getCustId());
        String pregnancyWeeks = "";
        if (preArchive != null && preArchive.getLmp() != null) {
            int pregnantDays = JodaTimeTools.getDays(preArchive.getLmp(), new Date());
            int pregnantWeeks = pregnantDays / 7;
            int plusDays = pregnantDays % 7;
            pregnancyWeeks = pregnantWeeks + "+" + plusDays;
        }
        bca.setGestationalWeeks(pregnancyWeeks);
        return (String) pregInbodyDAO.save(bca);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateInbodyBca(PregInbodyBca bca) {
        String bcaId = bca.getBcaId();
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("bcaId", HQLSymbol.EQ.toString(), bcaId));
        pregInbodyDAO.updateHQL(bca, conditionParams);
    }

    @Override
    public void deleteInbodyBcaById(String bcaId) {
        pregInbodyDAO.deleteInbodyBcaById(bcaId);
    }

}

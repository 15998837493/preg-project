
package com.mnt.preg.master.service.preg;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.core.exception.ServiceException;
import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.core.utils.HQLSymbol;
import com.mnt.preg.main.results.ResultCode;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.master.condition.preg.DietTemplateCondition;
import com.mnt.preg.master.condition.preg.DietTemplateDetailCondition;
import com.mnt.preg.master.dao.preg.PregDietTemplateDAO;
import com.mnt.preg.master.entity.preg.PregDietTemplate;
import com.mnt.preg.master.entity.preg.PregDietTemplateDetail;
import com.mnt.preg.master.pojo.preg.PregDietTemplateDetailPojo;
import com.mnt.preg.master.pojo.preg.PregDietTemplatePojo;

@Service
public class PregDietTemplateServiceImpl extends BaseService implements PregDietTemplateService {

    @Resource
    private PregDietTemplateDAO pregDietTemplateDAO;

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public PregDietTemplatePojo addDietTemplate(PregDietTemplate dietTemplate) {
        String dietId = (String) pregDietTemplateDAO.save(dietTemplate);
        pregDietTemplateDAO.getCurrentSession().flush();
        PregDietTemplatePojo vo = pregDietTemplateDAO.getDietTemplateByDietId(dietId);
        return vo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PregDietTemplatePojo> queryDietTemplate(DietTemplateCondition condition) {
        List<PregDietTemplatePojo> list = pregDietTemplateDAO.queryDietTemplate(condition);
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public PregDietTemplatePojo getDietTemplate(String dietId) {
        return pregDietTemplateDAO.getTransformerBean(dietId, PregDietTemplate.class, PregDietTemplatePojo.class);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public PregDietTemplatePojo updateDietTemplate(PregDietTemplate dietTemplate) {
        String dietId = dietTemplate.getDietId();
        if (StringUtils.isEmpty(dietId)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        // 更新模版主表
        List<HQLConditionParam> updateParams = new ArrayList<HQLConditionParam>();
        updateParams.add(new HQLConditionParam("dietId", HQLSymbol.EQ.toString(), dietId));
        pregDietTemplateDAO.updateHQL(dietTemplate, updateParams);
        PregDietTemplatePojo vo = pregDietTemplateDAO.getDietTemplateByDietId(dietId);
        return vo;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void removeDietTemplate(String dietId) {
        PregDietTemplate dietTemplate = new PregDietTemplate();
        dietTemplate.setDietId(dietId);
        dietTemplate.setFlag(0);
        this.updateDietTemplate(dietTemplate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PregDietTemplatePojo> queryDietTemplateByCondition(DietTemplateCondition condition) {
        return pregDietTemplateDAO.queryDietTemplate(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PregDietTemplateDetailPojo> queryDietTemplateDetails(DietTemplateDetailCondition condition) {
        return pregDietTemplateDAO.queryDietTemplateDetail(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public PregDietTemplateDetailPojo getDietTemplateDetail(String detailId) {
        return pregDietTemplateDAO.getTransformerBean(detailId, PregDietTemplateDetail.class,
                PregDietTemplateDetailPojo.class);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addDietTemplateDetail(PregDietTemplateDetail dietTemplateDetail) {
        return (String) pregDietTemplateDAO.save(dietTemplateDetail);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateDietTemplateDetail(PregDietTemplateDetail dietTemplateDetail) {
        String id = dietTemplateDetail.getId();
        if (StringUtils.isEmpty(id)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        // 更新模版主表
        List<HQLConditionParam> updateParams = new ArrayList<HQLConditionParam>();
        updateParams.add(new HQLConditionParam("id", HQLSymbol.EQ.toString(), id));
        pregDietTemplateDAO.updateHQL(dietTemplateDetail, updateParams);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void removeDietTemplateDetail(String detailId) {
        PregDietTemplateDetail dietTemplateDetail = new PregDietTemplateDetail();
        dietTemplateDetail.setId(detailId);
        dietTemplateDetail.setFlag(0);
        this.updateDietTemplateDetail(dietTemplateDetail);
    }

    @Override
    public List<PregDietTemplateDetailPojo> queryDietTemplateDetailNamesByDietId(String dietId) {
        return pregDietTemplateDAO.queryDietTemplateDetailNamesbyTemplateId(dietId);
    }

    @Override
    public List<PregDietTemplateDetailPojo> queryDietTemplateDetailByCondition(DietTemplateDetailCondition condition) {
        return pregDietTemplateDAO.queryDietTemplateDetail(condition);
    }

    @Override
    public void removeFoodDayByCondition(String dietId, String foodDay) {
        pregDietTemplateDAO.removeFoodDayByCondition(dietId, foodDay);
    }

    @Override
    public Integer validCode(String filedValue) {
        return this.validCode("dietName", filedValue, PregDietTemplate.class);
    }
}

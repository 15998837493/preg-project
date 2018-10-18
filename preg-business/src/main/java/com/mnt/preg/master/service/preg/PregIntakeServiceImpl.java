
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
import com.mnt.preg.main.results.ResultCode;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.master.condition.preg.IntakeCondition;
import com.mnt.preg.master.dao.preg.PregIntakeDAO;
import com.mnt.preg.master.entity.preg.PregIntake;
import com.mnt.preg.master.entity.preg.PregIntakeDetail;
import com.mnt.preg.master.pojo.preg.PregIntakeDetailPojo;
import com.mnt.preg.master.pojo.preg.PregIntakePojo;

@Service
public class PregIntakeServiceImpl extends BaseService implements PregIntakeService {

    @Resource
    private PregIntakeDAO pregIntakeDAO;

    @Override
    @Transactional(readOnly = true)
    public List<PregIntakePojo> queryIntake(IntakeCondition condition) {
        return pregIntakeDAO.queryIntake(condition);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addIntake(PregIntake intake) {
        // 添加主表信息
        String intakeId = (String) pregIntakeDAO.save(intake);
        return intakeId;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateIntake(PregIntake intake) {
        String intakeId = intake.getIntakeId();
        if (StringUtils.isEmpty(intakeId)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        // 更新模版主表
        List<HQLConditionParam> updateParams = new ArrayList<HQLConditionParam>();
        updateParams.add(new HQLConditionParam("intakeId", HQLSymbol.EQ.toString(), intakeId));
        if (intake.getFlag() != null && intake.getFlag() == 0) {
            pregIntakeDAO.updateHQL(intake, updateParams);
            return;
        }
        pregIntakeDAO.updateHQL(intake, updateParams);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PregIntakeDetailPojo> queryIntakeDetailByIntakeId(String intakeId) {
        return pregIntakeDAO.queryIntakeDetailByIntakeId(intakeId);
    }

    @Override
    @Transactional(readOnly = true)
    public PregIntakeDetailPojo getIntakeDetail(String id) {
        return pregIntakeDAO.getTransformerBean(id, PregIntakeDetail.class, PregIntakeDetailPojo.class);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addIntakeDetail(PregIntakeDetail intakeDetail) {
        return (String) pregIntakeDAO.save(intakeDetail);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateIntakeDetail(PregIntakeDetail intakeDetail) {
        String id = intakeDetail.getId();
        if (StringUtils.isEmpty(id)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        // 更新模版主表
        List<HQLConditionParam> updateParams = new ArrayList<HQLConditionParam>();
        updateParams.add(new HQLConditionParam("id", HQLSymbol.EQ.toString(), id));
        pregIntakeDAO.updateHQL(intakeDetail, updateParams);
    }

    @Override
    @Transactional(readOnly = true)
    public PregIntakePojo queryIntakeAndDetailByCondition(IntakeCondition condition) {
        List<PregIntakePojo> intakeVos = pregIntakeDAO.queryIntake(condition);
        if (CollectionUtils.isEmpty(intakeVos)) {
            throw new ServiceException(ResultCode.ERROR_90001);
        }
        // 设置主表信息
        PregIntakePojo intakeVo = intakeVos.get(0);
        // 设置清单明细
        intakeVo.setIntakeDetailList(pregIntakeDAO.queryIntakeDetailByIntakeId(intakeVo.getIntakeId()));
        return intakeVo;
    }

    @Override
    public void removeIntakeDetailByIntakeId(String intakeId) {
        pregIntakeDAO.removeIntakeDetailByIntakeId(intakeId);
    }

}

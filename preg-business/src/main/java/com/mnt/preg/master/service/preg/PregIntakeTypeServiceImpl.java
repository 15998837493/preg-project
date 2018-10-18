
package com.mnt.preg.master.service.preg;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.core.utils.HQLSymbol;
import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.master.dao.preg.PregIntakeTypeDAO;
import com.mnt.preg.master.entity.preg.IntakeType;
import com.mnt.preg.master.pojo.preg.IntakeTypePojo;

@Service
public class PregIntakeTypeServiceImpl extends BaseService implements PregIntakeTypeService {

    @Resource
    private PregIntakeTypeDAO intakeTypeDAO;

    @Override
    @Transactional(readOnly = true)
    public List<IntakeTypePojo> queryIntakeTypeByCondition(IntakeType condition) {
        return intakeTypeDAO.queryIntakeType(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public IntakeTypePojo getIntakeType(String id) {
        return intakeTypeDAO.getTransformerBean(id, IntakeType.class, IntakeTypePojo.class);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public IntakeTypePojo addIntakeType(IntakeType intakeType) {
        String id = (String) intakeTypeDAO.save(intakeType);
        return intakeTypeDAO.getTransformerBean(id, IntakeType.class, IntakeTypePojo.class);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public IntakeTypePojo updateIntakeType(IntakeType intakeType) {
        // 更新模版主表
        List<HQLConditionParam> updateParams = new ArrayList<HQLConditionParam>();
        updateParams.add(new HQLConditionParam("id", HQLSymbol.EQ.toString(), intakeType.getId()));
        intakeTypeDAO.updateHQL(intakeType, updateParams);
        return intakeTypeDAO.getTransformerBean(intakeType.getId(), IntakeType.class, IntakeTypePojo.class);
    }

    @Override
    public void removeIntakeType(String id) {
        IntakeTypePojo intakeTypePojo = this.getIntakeType(id);
        IntakeType intakeType = TransformerUtils.transformerProperties(IntakeType.class, intakeTypePojo);
        intakeType.setFlag(0);
        this.updateIntakeType(intakeType);
    }
}

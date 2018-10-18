
package com.mnt.preg.master.service.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.core.utils.HQLSymbol;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.master.condition.items.HospitalCondition;
import com.mnt.preg.master.dao.items.HospitalDAO;
import com.mnt.preg.master.entity.items.Hospital;
import com.mnt.preg.master.pojo.items.HospitalPojo;

/**
 * 医院信息接口实现类
 * 
 * @author dhs
 * @version 1.7
 * 
 *          变更履历：
 *          v1.7 2018-08-07 dhs 初版
 */
@Service
public class HospitalServiceImpl extends BaseService implements HospitalService {

    @Resource
    private HospitalDAO hospitalDAO;

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public HospitalPojo saveHospital(Hospital hospital) {
        String id = (String) hospitalDAO.save(hospital);
        return hospitalDAO.getTransformerBean(id, Hospital.class, HospitalPojo.class);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public HospitalPojo updateHospital(Hospital hospital) {
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("hospitalId", HQLSymbol.EQ.toString(), hospital.getHospitalId()));
        hospitalDAO.updateHQL(hospital, conditionParams);
        return hospitalDAO.getTransformerBean(hospital.getHospitalId(), Hospital.class, HospitalPojo.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer checkHospitalName(String itemName, String itemType, String itemClassify) {
        Map<String, Object> conditionMap = new HashMap<String, Object>();
        conditionMap.put("hospitalType", itemType);
        conditionMap.put("hospitalClassify", itemClassify);
        conditionMap.put("hospitalName", itemName);
        return primaryKeyDao.validName(conditionMap, Hospital.class);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void removeHospital(String id) {
        hospitalDAO.deleteHospital(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HospitalPojo> queryHospital(HospitalCondition condition) {
        return hospitalDAO.queryHospitalByCondition(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public HospitalPojo getHospitalById(String hospitalId) {
        HospitalCondition condition = new HospitalCondition();
        condition.setHospitalId(hospitalId);
        List<HospitalPojo> list = hospitalDAO.queryHospitalByCondition(condition);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        } else {
            return new HospitalPojo();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<HospitalPojo> queryHospitalType() {
        return hospitalDAO.queryHospitalType();
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void removeHospitalByType(String type, String itemType, String itemClassify) {
        hospitalDAO.removeHospitalByType(type, itemType, itemClassify);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateHospitalType(String oldType, String newType, String type, String pType) {
        hospitalDAO.updateHospitalType(oldType, newType, type, pType);
    }

}

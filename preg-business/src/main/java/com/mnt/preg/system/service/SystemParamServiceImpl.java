/*
 * SystemParamServiceImpl.java	 1.0   2014-12-18
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.system.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.core.exception.ServiceException;
import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.core.utils.HQLSymbol;
import com.mnt.preg.system.dao.SystemParamDao;
import com.mnt.preg.system.entity.SystemParam;
import com.mnt.preg.system.pojo.SystemParamPojo;

/**
 * 系统参数事务
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：v1.0 2014-12-18 zcq 初版 变更履历：v1.0 2014-12-18 zy
 *          增加getSystemParam(String paramCode, String insId)
 */
@Service
public class SystemParamServiceImpl implements SystemParamService {

    @Resource
    private SystemParamDao systemParamDao;

    @Override
    @Transactional(readOnly = true)
    public List<SystemParamPojo> querySystemParam(SystemParam condition) {
        return systemParamDao.querySystemParam(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public SystemParamPojo getSystemParam(String paramId) {
        return systemParamDao.getSystemParam(paramId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateSystemParam(SystemParam systemParam) {
        String paramId = systemParam.getParamId();
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("paramId", HQLSymbol.EQ.toString(), paramId));
        SystemParamPojo paramVo = systemParamDao.getSystemParam(paramId);
        if (paramVo == null) {
            // 更新数据不存在
            throw new ServiceException("更新数据已经不存在!");
        }
        // 设置编码
        systemParam.setParamId(paramVo.getParamId());
        // 缓存处理
        systemParamDao.updateParam(systemParam, conditionParams);
    }
}

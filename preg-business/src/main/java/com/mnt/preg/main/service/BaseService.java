/*
 * BaseService.java	 1.0   2014-12-11
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.main.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.utils.thread.TokenManager;
import com.mnt.preg.main.utils.PublicConfig;
import com.mnt.preg.system.dao.PrimaryKeyDao;
import com.mnt.preg.system.dao.SystemParamDao;
import com.mnt.preg.system.dao.UserDao;
import com.mnt.preg.system.pojo.LoginUser;
import com.mnt.preg.system.service.LoginService;

/**
 * Service公共类
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-5-4 zcq 初版
 */
@Service
public abstract class BaseService {

    @Resource
    public PublicConfig publicConfig;

    @Resource
    public SystemParamDao systemParamDao;

    @Resource
    public PrimaryKeyDao primaryKeyDao;

    @Resource
    public UserDao userDao;

    @Resource
    protected LoginService loginService;

    public LoginUser getLoginUser() {
        String token = TokenManager.getCurrHashMap().get(Thread.currentThread());
        return loginService.getLogin(token);
    }

    public String getInsId() {
        return this.getLoginUser().getUserPojo().getCreateInsId();
    }

    /**
     * 获取系统参数
     * 
     * @param paramId
     *            系统参数编码
     * @return 参数值
     */
    public String getParamValue(String paramId) {
        return systemParamDao.getSystemParam(paramId).getParamValue();
    }

    /**
     * 校验编码是否存在
     * 
     * @author zcq
     * @return
     */
    public Integer validCode(String fieldName, String fieldValue, Class<?> clazz) {
        return primaryKeyDao.validCode(fieldName, fieldValue, clazz);
    }

    /**
     * 检验名称是否重复
     * 
     * @author zcq
     * @param conditionMap
     * @param clazz
     * @return
     */
    public Integer validName(Map<String, Object> conditionMap, Class<?> clazz) {
        return primaryKeyDao.validName(conditionMap, clazz);
    }

    /**
     * 根据表明和字段名查询去重的数据
     * 
     * @author xdc
     * @param tableName
     * @param fieldName
     * @return
     */
    @Transactional(readOnly = true)
    public List<String> queryDistinctByField(String tableName, String fieldName) {
        return systemParamDao.queryDistinctByField(tableName, fieldName);
    }

}

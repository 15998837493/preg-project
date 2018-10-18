
package com.mnt.preg.system.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mnt.health.core.exception.ServiceException;
import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.core.utils.HQLSymbol;
import com.mnt.preg.main.results.ResultCode;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.system.dao.DeptDAO;
import com.mnt.preg.system.entity.Dept;
import com.mnt.preg.system.pojo.DeptPojo;

/**
 * 功能菜单事务
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-12-18 zcq 初版
 */
@Service
public class DeptServiceImpl extends BaseService implements DeptService {

    @Resource
    private DeptDAO deptDAO;

    @Override
    @Transactional(readOnly = true)
    public List<DeptPojo> queryDept(Dept condition) {
        if (condition == null) {
            condition = new Dept();
        }
        return deptDAO.queryDept(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public DeptPojo getDept(String deptId) {
        return deptDAO.getTransformerBean(deptId, Dept.class, DeptPojo.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer checkDeptIdValid(String deptId) {
        return deptDAO.checkDeptIdValid(deptId);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer checkDeptNameValid(String deptName) {
        return deptDAO.checkDeptNameValid(deptName);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addDept(Dept dept) {
        // 主键：四位一级，父级编码+排序（补充到四位），例如 一级=0001，二级=00010001
        dept.setDeptId(createDeptId(dept.getDeptParent()));

        if (deptDAO.checkDeptIdValid(dept.getDeptId()) > 0) {
            throw new ServiceException(ResultCode.ERROR_90002);
        }

        return (String) deptDAO.save(dept);
    }

    /**
     * 生成功能菜单主键
     * 
     * @author zcq
     * @param deptParent
     * @return
     */
    private String createDeptId(String deptParent) {
        String maxDeptId = (String) deptDAO.getSonMaxDeptId(deptParent);
        String code = StringUtils.isEmpty(maxDeptId) ? "0001" : String.valueOf(Integer.valueOf(maxDeptId.substring(
                maxDeptId.length() - 4, maxDeptId.length())) + 1);
        int length = code.length();
        if (length < 4) {
            for (int i = 0; i < 4 - length; i++) {
                code = "0" + code;
            }
        }
        return "0000".equals(deptParent) ? code : (deptParent + code);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateDept(Dept dept) {
        String deptId = dept.getDeptId();

        if (StringUtils.isEmpty(deptId)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }

        // 设置检索条件
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("deptId", HQLSymbol.EQ.toString(), deptId));
        deptDAO.updateHQL(dept, conditionParams);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteDept(String deptId) {

        if (StringUtils.isEmpty(deptId)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }

        deptDAO.deleteDept(deptId);// 删除该部门以及子级部门
        deptDAO.deleteDeptUser(deptId);// 删除该部门以及子级部门的员工
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void editDeptOrder(List<String> deptIdList) {
        if (CollectionUtils.isNotEmpty(deptIdList)) {
            for (int i = 1; i <= deptIdList.size(); i++) {
                deptDAO.updateDeptOrder(deptIdList.get(i - 1), i);
            }
        }
    }

}

/*
 * RoleServiceImpl.java	 1.0   2014-12-15
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.system.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.HibernateException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.core.utils.HQLSymbol;
import com.mnt.preg.system.dao.PrimaryKeyDao;
import com.mnt.preg.system.dao.RightDao;
import com.mnt.preg.system.entity.Right;
import com.mnt.preg.system.entity.RightMenu;
import com.mnt.preg.system.pojo.MenuPojo;
import com.mnt.preg.system.pojo.RightPojo;

/**
 * 角色管理事务
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-12-18 zcq 初版
 */
@Service
public class RightServiceImpl implements RightService {

    @Resource
    private RightDao rightDao;

    @Resource
    private PrimaryKeyDao primaryKeyDao;

    @Override
    @Transactional(readOnly = true)
    public List<RightPojo> queryRight(Right right) {
        return rightDao.queryRight(right);
    }

    @Override
    @Transactional(readOnly = true)
    public RightPojo getRight(String rightId) {
        RightPojo rightVo = rightDao.getTransformerBean(rightId, Right.class, RightPojo.class);
        rightVo.setMenuList(rightDao.queryMenuByRightId(rightId));
        return rightVo;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addRight(Right right, List<String> menuIdList) {
        // 生成主键：顺序号
        String rightId = primaryKeyDao.getOrderNo("sys_right", "right_id");
        right.setRightId(rightId);
        // 保存权限组
        rightDao.save(right);

        if (CollectionUtils.isNotEmpty(menuIdList)) {
            for (String menuId : menuIdList) {
                RightMenu rightMenu = new RightMenu();
                rightMenu.setRightId(rightId);
                rightMenu.setMenuId(menuId);
                // 保存关联角色功能
                rightDao.save(rightMenu);
            }
        }
        return rightId;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateRight(Right right, List<String> menuIdList) {
        // 主键
        String rightId = right.getRightId();
        // 设置检索条件
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("rightId", HQLSymbol.EQ.toString(), rightId));
        rightDao.updateHQL(right, conditionParams);
        // 删除原权限组功能列表
        rightDao.deleteRightMenu(rightId);

        if (CollectionUtils.isNotEmpty(menuIdList)) {
            // 保存新角色功能列表
            for (String menuId : menuIdList) {
                RightMenu rightMenu = new RightMenu();
                rightMenu.setRightId(rightId);
                rightMenu.setMenuId(menuId);
                // 保存关联角色功能
                rightDao.save(rightMenu);
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Integer checkRightNameValid(String rightName) {
        return rightDao.checkRightNameValid(rightName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuPojo> queryMenuByRightId(String rightId) {
        return rightDao.queryMenuByRightId(rightId);
    }

    @Override
    public void removeRight(String rigthId) {
        RightPojo rightPojo = this.getRight(rigthId);
        Right right = new Right();
        BeanUtils.copyProperties(rightPojo, right);
        right.setFlag(0);
        this.updateRight(right, new ArrayList<String>());
    }
}

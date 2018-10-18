
package com.mnt.preg.platform.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.platform.dao.UserInspectItemDAO;
import com.mnt.preg.platform.entity.UserInspectItem;
import com.mnt.preg.platform.pojo.UserInspectItemPojo;

/**
 * 默认评价项目Service
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-3-28 zcq 初版
 */
@Service
public class UserInspectItemServiceImpl extends BaseService implements UserInspectItemService {

    @Resource
    private UserInspectItemDAO userInspectItemDAO;

    @Override
    @Transactional(readOnly = true)
    public List<UserInspectItemPojo> queryUserInspectByType(String inspectType) {
        String createUserId = this.getLoginUser().getUserPojo().getUserId();
        return userInspectItemDAO.queryUserInspectItemByCreateUserId(createUserId, inspectType);
    }

    /**
     * 根据创建人检索个人默认评价项目 mlq
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserInspectItemPojo> queryUserInspectByType(String createUserId, String inspectType) {
        return userInspectItemDAO.queryUserInspectItemByCreateUserId(createUserId, inspectType);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void saveUserInspectItems(List<UserInspectItemPojo> userInspectItemList, String inspectType) {
        String createUserId = this.getLoginUser().getUserPojo().getUserId();
        // 删除原有数据
        userInspectItemDAO.deleteUserInspectItemByCreateUserId(createUserId, inspectType);
        // 添加新数据
        if (CollectionUtils.isNotEmpty(userInspectItemList)) {
            for (UserInspectItemPojo itemPojo : userInspectItemList) {
                UserInspectItem userInspectItem = TransformerUtils.transformerProperties(UserInspectItem.class,
                        itemPojo);
                userInspectItemDAO.save(userInspectItem);
            }
        }
    }
}

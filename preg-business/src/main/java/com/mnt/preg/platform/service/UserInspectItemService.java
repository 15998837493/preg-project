
package com.mnt.preg.platform.service;

import java.util.List;

import org.springframework.validation.annotation.Validated;

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
@Validated
public interface UserInspectItemService {

    /**
     * 检索个人默认评价项目
     * 
     * @author zcq
     * @param inspectType
     * @return
     */
    List<UserInspectItemPojo> queryUserInspectByType(String inspectType);

    /**
     * 保存个人默认评价项目
     * 
     * @author zcq
     * @param userInspectItemList
     * @param inspectType
     */
    void saveUserInspectItems(List<UserInspectItemPojo> userInspectItemList, String inspectType);

    /**
     * 根据创建人检索个人默认评价项目
     * 
     * @author mlq
     * @param diagnosisUser
     * @param inspectType
     * @return
     */
    List<UserInspectItemPojo> queryUserInspectByType(String diagnosisUser, String inspectType);

}

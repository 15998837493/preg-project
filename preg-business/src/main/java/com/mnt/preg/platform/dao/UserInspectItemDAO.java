
package com.mnt.preg.platform.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.preg.platform.pojo.UserInspectItemPojo;

/**
 * 默认评价项目DAO
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-3-28 zcq 初版
 */
@Repository
public class UserInspectItemDAO extends HibernateTemplate {

    /**
     * 检索个人默认评价项目
     * 
     * @author zcq
     * @param createUserId
     * @param inspectType
     * @return
     */
    public List<UserInspectItemPojo> queryUserInspectItemByCreateUserId(String createUserId, String inspectType) {
        if (StringUtils.isBlank(createUserId)) {
            return null;
        }
        String sql = "SELECT " + DaoUtils.getSQLFields(UserInspectItemPojo.class, "UserInspectItemPojo")
                + "   FROM user_inspect_item AS UserInspectItemPojo"
                + "   WHERE UserInspectItemPojo.create_user_id = :createUserId"
                + "       AND UserInspectItemPojo.inspect_type = :inspectType";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("createUserId", createUserId);
        params.put("inspectType", inspectType);

        return this.SQLQueryAlias(sql, params, UserInspectItemPojo.class);
    }

    /**
     * 删除个人默认评价项目
     * 
     * @author zcq
     * @param createUserId
     * @param inspectType
     */
    public void deleteUserInspectItemByCreateUserId(String createUserId, String inspectType) {
        if (StringUtils.isNotBlank(createUserId) && StringUtils.isNotBlank(inspectType)) {
            Map<String, Object> paramsMap = new HashMap<String, Object>();
            paramsMap.put("createUserId", createUserId);
            paramsMap.put("inspectType", inspectType);
            String sql = "DELETE FROM user_inspect_item WHERE create_user_id=:createUserId AND inspect_type=:inspectType";
            this.executeSQL(sql, paramsMap);
        }
    }

}

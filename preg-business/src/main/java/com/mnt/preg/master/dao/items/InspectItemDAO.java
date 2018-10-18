
package com.mnt.preg.master.dao.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.core.utils.HQLSymbol;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.master.condition.items.InspectItemCondition;
import com.mnt.preg.master.entity.items.InspectItem;
import com.mnt.preg.master.pojo.items.InspectItemPojo;

/**
 * 检查项目表DAO
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历： v1.0 2016-4-7 gss 初版
 */
@Repository
public class InspectItemDAO extends HibernateTemplate {

    /**
     * 根据id查询检查项目
     * 
     * @param inspectId
     *            主键
     * @return InspectItemPojo
     */
    public InspectItemPojo getInspectItemByInspectId(String inspectId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(InspectItemPojo.class, "InspectItemPojo")
                + "   FROM mas_inspect_item AS InspectItemPojo "
                + "   WHERE InspectItemPojo.inspect_id=:inspectId ";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("inspectId", inspectId);
        return this.SQLQueryAliasFirst(sql, paramMap, InspectItemPojo.class);
    }

    /**
     * 根据Code查询检查项目
     * 
     * @param inspectCode
     *            主键
     * @return InspectItemPojo
     */
    public InspectItemPojo getInspectItemByInspectCode(String inspectCode) {
        String sql = "SELECT " + DaoUtils.getSQLFields(InspectItemPojo.class, "InspectItemPojo")
                + "   FROM mas_inspect_item AS InspectItemPojo "
                + "   WHERE InspectItemPojo.inspect_code=:inspectCode ";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("inspectCode", inspectCode);
        return this.SQLQueryAliasFirst(sql, paramMap, InspectItemPojo.class);
    }

    /**
     * 条件查询检测项目
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public List<InspectItemPojo> queryInspectItemByCondition(InspectItemCondition condition) {
        if (condition == null) {
            condition = new InspectItemCondition();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "InspectItemPojo");
        String sql = "SELECT " + DaoUtils.getSQLFields(InspectItemPojo.class, "InspectItemPojo")
                + "   FROM mas_inspect_item AS InspectItemPojo "
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), InspectItemPojo.class);
    }

    /**
     * 
     * 更新检查项目表记录
     * 
     * @author gss
     * @param inspectItem
     * @param id
     *            主键
     */
    public void updateInspectItem(InspectItem inspectItem, String id) {
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("inspect_id", HQLSymbol.EQ.toString(), id));
        this.updateHQL(inspectItem, conditionParams);
    }

}

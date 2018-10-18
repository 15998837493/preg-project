
package com.mnt.preg.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.system.condition.PrintCondition;
import com.mnt.preg.system.pojo.PrintPojo;

/**
 * 配置打印内容DAO
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：v1.0 2014-12-18 zcq 初版
 */
@Repository
public class PrintDAO extends HibernateTemplate {

    /**
     * 检索打印选项
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public List<PrintPojo> queryPrint(PrintCondition condition) {
        if (condition == null) {
            condition = new PrintCondition();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "PrintPojo");
        String sql = "SELECT " + DaoUtils.getSQLFields(PrintPojo.class, "PrintPojo")
                + "   FROM sys_print AS PrintPojo "
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();

        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), PrintPojo.class);
    }

}

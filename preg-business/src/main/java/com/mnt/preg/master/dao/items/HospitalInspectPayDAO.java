
package com.mnt.preg.master.dao.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.master.condition.items.HospitalInspectPayCondition;
import com.mnt.preg.master.pojo.items.HospitalInspectPayPojo;
import com.mnt.preg.master.pojo.items.ItemPojo;
import com.mnt.preg.platform.pojo.DiagnosisHospitalItemPojo;

/**
 * 收费项目配置DAO
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-6-21 zcq 初版
 */
@Repository
public class HospitalInspectPayDAO extends HibernateTemplate {

    /**
     * 根据主键获取收费项目信息
     * 
     * @author zcq
     * @param inspectId
     * @return
     */
    public HospitalInspectPayPojo getInspectByInspectId(String inspectId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(HospitalInspectPayPojo.class, "HospitalPojo")
                + "   FROM mas_hospital_inspect_pay AS HospitalPojo "
                + "   WHERE HospitalPojo.inspect_id=:inspectId ";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("inspectId", inspectId);
        return this.SQLQueryAliasFirst(sql, paramMap, HospitalInspectPayPojo.class);
    }

    /**
     * 条件查询检测项目
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public List<HospitalInspectPayPojo> queryInspectItemByCondition(HospitalInspectPayCondition condition) {
        if (condition == null) {
            condition = new HospitalInspectPayCondition();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "HospitalPojo");
        String sql = "SELECT " + DaoUtils.getSQLFields(HospitalInspectPayPojo.class, "HospitalPojo")
                + "   FROM mas_hospital_inspect_pay AS HospitalPojo "
                + queryCondition.getQueryString()
                + "   ORDER BY ISNULL(HospitalPojo.inspect_sort),HospitalPojo.inspect_sort ASC";
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), HospitalInspectPayPojo.class);
    }

    /**
     * 根据收费项目主键查询该收费项目下包含的检验项目
     * 
     * @author zcq
     * @param inspectId
     * @return
     */
    public List<ItemPojo> queryItemByInspectId(String inspectId) {
        if (StringUtils.isBlank(inspectId)) {
            return null;
        }
        String sql = "SELECT DISTINCT "
                + DaoUtils.getSQLFields(ItemPojo.class, "ItemPojo")
                + "   FROM mas_item AS ItemPojo "
                + "   JOIN mas_hospital_inspect_pay_config AS config ON ItemPojo.item_id = config.item_id"
                + "   JOIN mas_hospital_inspect_pay AS pay ON pay.inspect_id = config.inspect_id"
                + "   WHERE pay.inspect_id = :inspectId"
                + "   ORDER BY ItemPojo.item_order ASC";

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("inspectId", inspectId);

        return this.SQLQueryAlias(sql, paramMap, ItemPojo.class);
    }

    /**
     * 删除收费项目与检验项目的关联配置
     * 
     * @author zcq
     * @param inspectId
     */
    public void deleteInspectPayConfig(String inspectId) {
        String sql = "DELETE FROM mas_hospital_inspect_pay_config WHERE inspect_id=:inspectId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("inspectId", inspectId);
        this.executeSQL(sql, paramMap);
    }

    /**
     * 查询检验项目的历史检查数据
     * 
     * @author mlq
     * @param itemId
     */
    public List<DiagnosisHospitalItemPojo> queryHisItemsByInspectId(String itemId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("itemId", itemId);
        String sql = "SELECT "
                + DaoUtils.getSQLFields(DiagnosisHospitalItemPojo.class, "hisItem")
                + "     FROM user_diagnosis_hospital_item AS hisItem"
                + "     JOIN user_diagnosis_hospital_inspect_report AS hisReport ON hisReport.report_id = hisItem.report_id"
                + "     WHERE hisItem.item_Id =:itemId"
                + "     AND datediff(now(),hisReport.report_date)>0"
                + "     GROUP BY hisItem.item_Id";
        return this.SQLQueryAlias(sql, paramMap, DiagnosisHospitalItemPojo.class);
    }
}

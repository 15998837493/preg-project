
package com.mnt.preg.master.dao.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.main.enums.Flag;
import com.mnt.preg.master.condition.items.HospitalCondition;
import com.mnt.preg.master.pojo.items.HospitalPojo;

/**
 * 医院信息DAO
 * 
 * @author dhs
 * @version 1.7
 * 
 *          变更履历：
 *          v1.7 2018-08-07 dhs 初版
 */
@Repository
public class HospitalDAO extends HibernateTemplate {

    /**
     * 根据条件查询所有
     * 
     * @author dhs
     * @param condition
     * @return
     */
    public List<HospitalPojo> queryHospitalByCondition(HospitalCondition condition) {
        if (condition == null) {
            condition = new HospitalCondition();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "HospitalPojo");

        String sql = "SELECT " + DaoUtils.getSQLFields(HospitalPojo.class, "HospitalPojo")
                + "   FROM sys_hospital AS HospitalPojo"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), HospitalPojo.class);
    }

    /**
     * 删除医院信息(物理删除)
     * 
     * @author dhs
     * @param codeIdList
     * @return
     */
    public Integer deleteHospital(String id) {
        String sql = "DELETE FROM sys_hospital WHERE hospital_id = :hospitalId";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("hospitalId", id);
        return this.executeSQL(sql, params);
    }

    /**
     * 查询所有大分类
     * 
     * @author xdc
     * @param condition
     * @return
     */
    public List<HospitalPojo> queryHospitalType() {
        String sql = "SELECT DISTINCT HospitalPojo.hospital_type AS hospitalType,HospitalPojo.hospital_classify AS hospitalClassify "
                + "   FROM sys_hospital AS HospitalPojo"
                + "   WHERE HospitalPojo.hospital_type IS NOT NULL "
                + "     AND HospitalPojo.hospital_classify IS NOT NULL "
                + "     AND flag = 1";
        return this.SQLQueryAlias(sql, HospitalPojo.class);
    }

    /**
     * 根据类别移除指标
     * 
     * @author xdc
     * @param type
     * @param itemType
     * @param itemClassify
     */
    public void removeHospitalByType(String type, String itemType, String itemClassify) {
        Map<String, Object> queryParams = new HashMap<String, Object>();
        String executeSQL = "";
        if ("hospitalClassify".equals(type)) {
            executeSQL = "UPDATE sys_hospital SET flag=:flag WHERE hospital_classify=:itemClassify AND hospital_type=:itemType";
            queryParams.put("itemType", itemType);
            queryParams.put("itemClassify", itemClassify);
        } else {
            executeSQL = "UPDATE sys_hospital SET flag=:flag WHERE hospital_type=:itemType";
            queryParams.put("itemType", itemType);
        }
        queryParams.put("flag", Flag.deleted.getValue());
        this.executeSQL(executeSQL, queryParams);
    }

    /**
     * 更新医院类别
     * 
     * @author dhs
     * @param oldName
     * @param newName
     * @param type
     *            修改的类型
     * @param pName
     *            父类型（如果是修改itemType则为空）
     */
    public void updateHospitalType(String oldType, String newType, String type, String pType) {
        Map<String, Object> queryParams = new HashMap<String, Object>();
        String executeSQL = "";
        if ("hospitalClassify".equals(type)) {
            executeSQL = "UPDATE sys_hospital SET hospital_classify=:newType WHERE hospital_classify=:oldType AND hospital_type=:pType";
            queryParams.put("newType", newType);
            queryParams.put("oldType", oldType);
            queryParams.put("pType", pType);
        } else {
            executeSQL = "UPDATE sys_hospital SET hospital_type=:newType WHERE hospital_type=:oldType";
            queryParams.put("newType", newType);
            queryParams.put("oldType", oldType);
        }
        this.executeSQL(executeSQL, queryParams);
    }

}

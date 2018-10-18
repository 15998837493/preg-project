
package com.mnt.preg.platform.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.preg.customer.pojo.PregArchivesPojo;

/**
 * 孕期建档DAO
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-22 gss 初版
 */
@Repository
public class PregArchivesDAO extends HibernateTemplate {

    /**
     * 获取最近一次的孕期档案
     * 
     * @author zcq
     * @param custId
     * @return
     */
    public PregArchivesPojo getLastPregnancyArchive(String custId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(PregArchivesPojo.class, "PregnancyArchivesVo")
                + "       ,cust.cust_name AS custName"
                + "   FROM cus_pregnancy_archives AS PregnancyArchivesVo"
                + "       JOIN cus_customer AS cust ON cust.cust_id=PregnancyArchivesVo.cust_id"
                + "   WHERE PregnancyArchivesVo.cust_id=:custId"
                + "       AND PregnancyArchivesVo.flag=:flag"
                + "   ORDER BY PregnancyArchivesVo.lmp DESC"
                + "   LIMIT 1";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("custId", custId);
        queryParams.put("flag", 1);
        return this.SQLQueryAliasFirst(sql, queryParams, PregArchivesPojo.class);
    }

    /**
     * 根据建档ID查找记录
     * 
     * @author dhs
     * @param id
     * @return
     */
    public PregArchivesPojo getArchiveById(String id) {
        String sql = "SELECT " + DaoUtils.getSQLFields(PregArchivesPojo.class, "PregnancyArchivesVo")
                + "   FROM cus_pregnancy_archives AS PregnancyArchivesVo"
                + "   WHERE PregnancyArchivesVo.id=:id"
                + "       AND PregnancyArchivesVo.flag=:flag";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("id", id);
        queryParams.put("flag", 1);
        return this.SQLQueryAliasFirst(sql, queryParams, PregArchivesPojo.class);
    }

    /**
     * 物理删除孕期档案表信息
     * 
     * @author gss
     * @return
     */
    public void deletePregnancyArchives(String id) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("id", id);
        this.executeSQL("DELETE FROM cus_pregnancy_archives WHERE id=:id", paramsMap);
    }

    /**
     * 
     * 获取或者建档记录
     * 
     * @author scd
     * @param custId
     * @return
     */
    public List<PregArchivesPojo> queryCustomerPregRecprd(String custId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(PregArchivesPojo.class, "preg")
                + "   FROM cus_pregnancy_archives AS preg"
                + "   WHERE preg.cust_id=:custId AND preg.flag=:flag"
                + "   ORDER BY preg.create_datetime DESC ";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("custId", custId);
        queryParams.put("flag", 1);
        return this.SQLQueryAlias(sql, queryParams, PregArchivesPojo.class);
    }
}

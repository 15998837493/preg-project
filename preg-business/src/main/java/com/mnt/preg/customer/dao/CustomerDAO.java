
package com.mnt.preg.customer.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.customer.condition.CustomerCondition;
import com.mnt.preg.customer.condition.ImportResultCondition;
import com.mnt.preg.customer.pojo.CustomerPojo;
import com.mnt.preg.customer.pojo.ImportResultPojo;
import com.mnt.preg.main.enums.Flag;

/**
 * 客户DAO
 *
 * @author zcq
 * @version 1.0
 *
 *          变更履历：
 *          v1.0 2016-3-22 zcq 初版
 */
@Repository
public class CustomerDAO extends HibernateTemplate {

    /**
     * 批量导入查询
     *
     * @author xdc
     * @param condition
     * @return
     */
    public List<ImportResultPojo> queryImportResult(ImportResultCondition condition) {
        if (condition == null) {
            condition = new ImportResultCondition();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "ImportResultPojo");
        String sql = "SELECT " + DaoUtils.getSQLFields(ImportResultPojo.class, "ImportResultPojo")
                + "   FROM cus_import_result AS ImportResultPojo "
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), ImportResultPojo.class);
    }

    /**
     * 条件检索客户信息
     *
     * @author zcq
     * @param condition
     * @return
     */
    public List<CustomerPojo> queryCustomer(CustomerCondition condition) {
        if (condition == null) {
            condition = new CustomerCondition();
        }
        // 初始化变量-检索条件类
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "CustomerPojo");

        String querySQL = "SELECT" + DaoUtils.getSQLFields(CustomerPojo.class, "CustomerPojo") + ","
                + "             get_age(CustomerPojo.cust_birthday) AS custAge"
                + "        FROM cus_customer AS CustomerPojo"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();

        // String countSQL = "SELECT COUNT(CustomerPojo.cust_id)"
        // + " FROM cus_customer AS CustomerPojo"
        // + queryCondition.getQueryString();

        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), CustomerPojo.class);
    }

    /**
     * 通过患者Id取得对应会员信息
     *
     * @author gss
     * @param custId
     *            患者id
     * @return
     */
    public CustomerPojo getCustomer(String custId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(CustomerPojo.class, "CustomerPojo") + ","
                + "        get_age(CustomerPojo.cust_birthday) AS custAge"
                + "   FROM cus_customer AS CustomerPojo"
                + "   WHERE CustomerPojo.cust_id= :custId"
                + "        AND CustomerPojo.flag= :flag";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("custId", custId);
        queryParams.put("flag", 1);
        return this.SQLQueryAliasFirst(sql, queryParams, CustomerPojo.class);
    }

    /**
     * 检查客户帐号是否可用
     *
     * @param custCode
     *            用户名
     * @return Integer 与当前传入信息一致的会员数据数，新增时： 0-验证有效 否则无效 修改时: 1-验证有效 否则无效
     */
    public Integer checkCustCodeValid(String custCode) {
        String countSQL = "SELECT COUNT(cust_code) FROM cus_customer WHERE cust_code=:custCode AND flag=:flag";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("custCode", custCode);
        paramMap.put("flag", 1);
        return this.SQLCount(countSQL, paramMap);
    }

    /**
     * 分页检索患者分娩结局信息
     *
     * @author zcq
     * @param condition
     * @return
     */
    public CustomerCondition queryCustomerForBirthEnding(CustomerCondition condition) {
        if (condition == null) {
            condition = new CustomerCondition();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "CustomerPojo");

        String querySQL = "SELECT "
                + "    bb.birth_date AS birthDate,"
                + "    CustomerPojo.cust_id AS custId,"
                + "    CustomerPojo.cust_sike_id AS custSikeId,"
                + "    CustomerPojo.cust_patient_id AS custPatientId,"
                + "    CustomerPojo.cust_name as custName,"
                + "    get_age(CustomerPojo.cust_birthday) as custAge,"
                + "    CustomerPojo.cust_icard as custIcard,"
                + "    CustomerPojo.cust_phone as custPhone"
                + "    FROM cus_customer AS CustomerPojo "
                + "    left join "
//                + "    (select b.birth_date, b.cust_id from  cus_birthending b group by b.cust_id) as bb "
                + "    (select tt.birth_date, tt.cust_id from (SELECT cb.base_time AS birth_date,b.cust_id from cus_birthending AS b LEFT JOIN cus_birthending_baseinfo AS cb ON b.birth_id = cb.birth_id order BY cb.base_time DESC) AS tt group by tt.cust_id) as bb "
                + "    ON CustomerPojo.cust_id = bb.cust_id ";
        if (StringUtils.isNotBlank(condition.getContent())) {
            querySQL += " where CustomerPojo.cust_name like '%" + condition.getContent() + "%'"
                     + " or CustomerPojo.cust_icard like '%" + condition.getContent() + "%'"
                     + " or CustomerPojo.cust_sike_id like '%" + condition.getContent() + "%'"
                     + " or CustomerPojo.cust_patient_id like '%" + condition.getContent() + "%'";
        }
        if(StringUtils.isNotBlank(condition.getOrderColumn())) {
            querySQL +=  queryCondition.getOrderString();
        }else {
            querySQL += "    order by bb.birth_date desc, CustomerPojo.create_time ASC";
        }
        String countSQL = "SELECT COUNT(CustomerPojo.cust_id)"
                + "        FROM cus_customer AS CustomerPojo";
                if (StringUtils.isNotBlank(condition.getContent())) {
                    countSQL += " where CustomerPojo.cust_name like '%" + condition.getContent() + "%'"
                             + " or CustomerPojo.cust_icard = '" + condition.getContent() + "'"
                             + " or CustomerPojo.cust_sike_id = '" + condition.getContent() + "'"
                             + " or CustomerPojo.cust_patient_id = '" + condition.getContent() + "'";
                }

        return (CustomerCondition) this.queryForPage(condition, querySQL, countSQL, queryCondition.getQueryParams(),
                CustomerPojo.class);
    }

    /**
     * 分页检索患者信息
     *
     * @author zcq
     * @param condition
     * @return
     */
    public CustomerCondition queryCustomerForDiagnosis(CustomerCondition condition) {
        if (condition == null) {
            condition = new CustomerCondition();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "CustomerPojo");

        String querySQL = "SELECT " + DaoUtils.getSQLFields(CustomerPojo.class, "CustomerPojo")
                + "           ,get_age(CustomerPojo.cust_birthday) AS custAge"
                + "           ,(SELECT lmp "
                + "             FROM cus_pregnancy_archives "
                + "             WHERE cust_id=CustomerPojo.cust_id "
                + "             ORDER BY create_time DESC "
                + "             LIMIT 1) AS lmp"
                + "           ,(SELECT pregnancy_due_date "
                + "             FROM cus_pregnancy_archives "
                + "             WHERE cust_id=CustomerPojo.cust_id "
                + "             ORDER BY create_time DESC "
                + "             LIMIT 1) AS pregnancyDueDate"
                + "           ,(SELECT COUNT(diagnosis_customer) "
                + "             FROM user_diagnosis "
                + "             WHERE diagnosis_customer=CustomerPojo.cust_id "
                + "                 AND diagnosis_status='2'"
                + "             ) AS diagnosisCount"
                + "        FROM cus_customer AS CustomerPojo"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        String countSQL = "SELECT COUNT(CustomerPojo.cust_id)"
                + "        FROM cus_customer AS CustomerPojo"
                + queryCondition.getQueryString();

        return (CustomerCondition) this.queryForPage(condition, querySQL, countSQL, queryCondition.getQueryParams(),
                CustomerPojo.class);
    }

    /**
     *
     *
     * 分页检索患者信息（诊疗登记一览）
     *
     * @author scd
     * @param condition
     * @return
     */
    public CustomerCondition queryCustomerForDiagnosisPage(CustomerCondition condition) {
        String queryString = "  CustomerPojo.cust_sike_id LIKE:custSikeId OR "
                + "             CustomerPojo.cust_patient_id LIKE:custPatientId OR"
                + "             CustomerPojo.cust_name LIKE:custName OR"
                + "             CustomerPojo.cust_icard LIKE:custIcard  ";
        String querySQL = "SELECT " + DaoUtils.getSQLFields(CustomerPojo.class, "CustomerPojo")
                + "           ,get_age(CustomerPojo.cust_birthday) AS custAge"
                + "           ,(SELECT COUNT(diagnosis_customer) "
                + "             FROM user_diagnosis "
                + "             WHERE diagnosis_customer=CustomerPojo.cust_id "
                + "                 AND diagnosis_status='2'"
                + "             ) AS diagnosisCount"
                + "        FROM cus_customer AS CustomerPojo WHERE flag=:flag";
        String countSQL = "SELECT COUNT(CustomerPojo.cust_id)"
                + "        FROM cus_customer AS CustomerPojo";

        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (condition.getContent() != null && !condition.getContent().isEmpty()) {
            querySQL = querySQL + " AND" + queryString;
            countSQL = countSQL + " WHERE" + queryString;
            paramMap.put("custSikeId", "%" + condition.getContent() + "%");
            paramMap.put("custPatientId", "%" + condition.getContent() + "%");
            paramMap.put("custName", "%" + condition.getContent() + "%");
            paramMap.put("custIcard", "%" + condition.getContent() + "%");
        }

        // 设置排序
        if (!StringUtils.isEmpty(condition.getOrderColumn()) && !StringUtils.isEmpty(condition.getOrderDir())) {
            String orderString = " ORDER BY CONVERT(" + condition.getOrderColumn() + " USING GBK) "
                    + condition.getOrderDir();
            querySQL = querySQL + orderString;
        }

        paramMap.put("flag", 1);
        return (CustomerCondition) this.queryForPage(condition, querySQL, countSQL, paramMap,
                CustomerPojo.class);
    }

    /**
     *
     * 查询不为指定ID，属性名为指定值的患者信息
     *
     * @author mnt_zhangjing
     * @param propertyName
     *            属性名
     * @param propertyval
     *            属性值
     * @param custId
     *            患者Id
     * @return
     */
    public List<CustomerPojo> queryCustomerByProperty(String propertyName, String propertyval, String custId) {

        String sql = "SELECT " + DaoUtils.getSQLFields(CustomerPojo.class, "CustomerPojo")
                + " FROM cus_customer AS CustomerPojo "
                + " WHERE " + propertyName + "= :propertyval AND flag= :flag";

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("propertyval", propertyval);
        paramMap.put("flag", Flag.normal.getValue());

        if (StringUtils.isNotEmpty(custId)) {
            sql = sql + " AND cust_id!= :custId";
            paramMap.put("custId", custId);
        }

        return this.SQLQueryAlias(sql, paramMap, CustomerPojo.class);
    }

}

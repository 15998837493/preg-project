
package com.mnt.preg.platform.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.health.utils.times.JodaTimeTools;
import com.mnt.preg.main.enums.Flag;
import com.mnt.preg.platform.condition.DiagnosisBookingCondition;
import com.mnt.preg.platform.condition.DiagnosisCondition;
import com.mnt.preg.platform.condition.DiagnosisObstetricalCondition;
import com.mnt.preg.platform.entity.PregDiagnosisObstetrical;
import com.mnt.preg.platform.pojo.DiagnosisBookingPojo;
import com.mnt.preg.platform.pojo.PregDiagnosisObstetricalPojo;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;

/**
 * 诊疗DAO（主表：user_diagnosis）
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-2-18 zcq 初版
 */
@Repository
public class PregDiagnosisDAO extends HibernateTemplate {

    /**
     * 条件分页检索
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public List<PregDiagnosisPojo> queryDiagnosis(DiagnosisCondition condition) {
        if (condition == null) {
            condition = new DiagnosisCondition();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "DiagnosisVo");
        String querySQL = "SELECT "
                + DaoUtils.getSQLFields(PregDiagnosisPojo.class, "DiagnosisVo")
                + "       ,(SELECT GROUP_CONCAT(dis.disease_name ORDER BY dis.disease_code DESC SEPARATOR '、') "
                + "         FROM user_diagnosis_disease AS dis "
                + "         WHERE dis.diagnosis_id = DiagnosisVo.diagnosis_id "
                + "         ) AS diagnosisDiseases "
                + "       ,(SELECT GROUP_CONCAT(dis.disease_code ORDER BY dis.disease_code DESC SEPARATOR ',') "
                + "         FROM user_diagnosis_disease AS dis "
                + "         WHERE dis.diagnosis_id = DiagnosisVo.diagnosis_id AND LENGTH(dis.disease_code) > 0 "
                + "         ) AS diagnosisDiseaseCodes "
                + "        FROM user_diagnosis AS DiagnosisVo "
                + queryCondition.getQueryString()
                // + queryCondition.getOrderString();
                + " ORDER BY DiagnosisVo.diagnosis_date DESC";
        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), PregDiagnosisPojo.class);
    }

    /**
     * 条件分页检索(医生)
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public List<PregDiagnosisPojo> queryDiagnosisMore(DiagnosisCondition condition) {
        if (condition == null) {
            condition = new DiagnosisCondition();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "DiagnosisVo");
        String querySQL = "SELECT " + DaoUtils.getSQLFields(PregDiagnosisPojo.class, "DiagnosisVo")
                + "           ,(SELECT lmp "
                + "             FROM cus_pregnancy_archives "
                + "             WHERE cust_id=DiagnosisVo.diagnosis_customer"
                + "             ORDER BY create_date DESC "
                + "             LIMIT 1) AS diagnosisLmp"
                + "           ,(SELECT pregnancy_due_date "
                + "             FROM cus_pregnancy_archives "
                + "             WHERE cust_id=DiagnosisVo.diagnosis_customer"
                + "             ORDER BY create_date DESC "
                + "             LIMIT 1) AS diagnosisDueDate"
                + "           ,(SELECT COUNT(diagnosis_customer) "
                + "             FROM user_diagnosis "
                + "             WHERE diagnosis_customer=DiagnosisVo.diagnosis_customer "
                + "                 AND diagnosis_status='2'"
                + "             ) AS diagnosisCount"
                + "        FROM user_diagnosis AS DiagnosisVo"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();

        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), PregDiagnosisPojo.class);
    }

    /**
     * 条件分页检索(助理)
     * 
     * @author dhs
     * @param condition
     * @return
     */
    public List<PregDiagnosisPojo> queryDiagnosisMoreEvaluateByOrder(DiagnosisCondition condition) {
        if (condition == null) {
            condition = new DiagnosisCondition();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "DiagnosisVo");
        String querySQL = "SELECT " + DaoUtils.getSQLFields(PregDiagnosisPojo.class, "DiagnosisVo")
                + "           ,(SELECT lmp "
                + "             FROM cus_pregnancy_archives "
                + "             WHERE cust_id=DiagnosisVo.diagnosis_customer"
                + "             ORDER BY create_date DESC "
                + "             LIMIT 1) AS diagnosisLmp"
                + "           ,(SELECT pregnancy_due_date "
                + "             FROM cus_pregnancy_archives "
                + "             WHERE cust_id=DiagnosisVo.diagnosis_customer"
                + "             ORDER BY create_date DESC "
                + "             LIMIT 1) AS diagnosisDueDate"
                + "           ,(SELECT COUNT(diagnosis_customer) "
                + "             FROM user_diagnosis "
                + "             WHERE diagnosis_customer=DiagnosisVo.diagnosis_customer "
                + "                 AND diagnosis_status='2'"
                + "             ) AS diagnosisCount"
                + "        FROM user_diagnosis AS DiagnosisVo"
                + queryCondition.getQueryString()
                // + queryCondition.getOrderString();
                + " ORDER BY DiagnosisVo.diagnosis_assistant_status ASC,DiagnosisVo.create_time ASC";

        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), PregDiagnosisPojo.class);
    }

    /**
     * 根据实验室指标和检查项目状态修改接诊状态
     * 
     * @author xdc
     * @param condition
     * @return
     */
    public List<PregDiagnosisPojo> queryDiagnosisMoreEvaluate(DiagnosisCondition condition) {
        if (condition == null) {
            condition = new DiagnosisCondition();
        }

        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "DiagnosisVo");
        if (condition.getDiagnosisStatus() != null && condition.getDiagnosisStatus().equals(2)) {
            queryCondition.setOrderString(" ORDER BY DiagnosisVo.update_time DESC");
        }
        String querySQL = "SELECT "
                + DaoUtils.getSQLFields(PregDiagnosisPojo.class, "DiagnosisVo")
                + "           ,(SELECT lmp "
                + "             FROM cus_pregnancy_archives "
                + "             WHERE cust_id=DiagnosisVo.diagnosis_customer"
                + "             ORDER BY create_date DESC "
                + "             LIMIT 1) AS diagnosisLmp"
                + "           ,(SELECT pregnancy_due_date "
                + "             FROM cus_pregnancy_archives "
                + "             WHERE cust_id=DiagnosisVo.diagnosis_customer"
                + "             ORDER BY create_date DESC "
                + "             LIMIT 1) AS diagnosisDueDate"
                + "           ,(SELECT COUNT(diagnosis_customer) "
                + "             FROM user_diagnosis "
                + "             WHERE diagnosis_customer=DiagnosisVo.diagnosis_customer "
                + "                 AND diagnosis_status='2' "
                + "             ) AS diagnosisCount "
                + "           ,(SELECT CASE WHEN (SELECT COUNT(clinical.report_id) "
                + "                               FROM user_diagnosis_hospital_inspect_report AS clinical "
                +
                "                               WHERE DiagnosisVo.diagnosis_id = clinical.diagnosis_id AND clinical.report_status = 2) > 0 "
                + "                          OR (SELECT COUNT(items.id) "
                + "                              FROM user_diagnosis_inspect AS items "
                +
                "                              WHERE items.diagnosis_id = DiagnosisVo.diagnosis_id AND (items.inspect_status = 2 OR items.inspect_status = 4)) > 0 "
                + "                    THEN '1' ELSE '2' END) AS statusSync "
                + "        FROM user_diagnosis AS DiagnosisVo "
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();

        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), PregDiagnosisPojo.class);
    }

    /**
     * 根据接诊编码获取接诊信息
     * 
     * @author zcq
     * @param diagnosisId
     * @return
     */
    public PregDiagnosisPojo getDiagnosis(String diagnosisId) {
        List<PregDiagnosisPojo> diagnosisList = this.queryDiagnosis(new DiagnosisCondition(diagnosisId));
        return CollectionUtils.isNotEmpty(diagnosisList) ? diagnosisList.get(0) : null;
    }

    /**
     * 获取最近一次接诊信息
     * 
     * @author zcq
     * @param custId
     * @return
     */
    public PregDiagnosisPojo getLastDiagnosis(String custId) {
        if (StringUtils.isBlank(custId)) {
            return null;
        }
        String sql = "SELECT " + DaoUtils.getSQLFields(PregDiagnosisPojo.class, "PregDiagnosisPojo")
                + "   FROM user_diagnosis AS PregDiagnosisPojo"
                + "   WHERE PregDiagnosisPojo.diagnosis_customer = :custId"
                + "        AND PregDiagnosisPojo.diagnosis_date < :today"
                + "        AND PregDiagnosisPojo.diagnosis_status = :diagnosisStatus"
                + "        AND PregDiagnosisPojo.flag = :flag"
                + "   ORDER BY PregDiagnosisPojo.diagnosis_date DESC"
                + "   LIMIT 1";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("custId", custId);
        params.put("today", JodaTimeTools.getCurrentDate(JodaTimeTools.FORMAT_6));
        params.put("diagnosisStatus", "2");
        params.put("flag", Flag.normal.getValue());

        return this.SQLQueryAliasFirst(sql, params, PregDiagnosisPojo.class);
    }

    /**
     * 查询预约信息
     * 
     * @author dhs
     * @return
     */
    public List<DiagnosisBookingPojo> queryDiagnosisBookings(DiagnosisBookingCondition condition) {
        if (condition == null) {
            condition = new DiagnosisBookingCondition();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "DiagnosisVo");
        String querySQL = "SELECT " + DaoUtils.getSQLFields(DiagnosisBookingPojo.class, "DiagnosisVo")
                + "        FROM user_diagnosis_booking AS DiagnosisVo"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), DiagnosisBookingPojo.class);
    }

    /**
     * 根据Id查询预约信息
     * 
     * @author dhs
     * @return
     */
    public DiagnosisBookingPojo queryDiagnosisBookingById(String diagnosisBookingId) {
        List<DiagnosisBookingPojo> diagnosisBookingList = this.queryDiagnosisBookings(new DiagnosisBookingCondition(
                diagnosisBookingId));
        return CollectionUtils.isNotEmpty(diagnosisBookingList) ? diagnosisBookingList.get(0) : null;
    }

    /**
     * 查询符合条件的数量
     * 
     * @author zcq
     * @return
     */
    public Integer queryCountDiagnosis(String diagnosisCustomer, Date date) {
        if (date == null) {
            date = new Date();
        }
        String sql = "SELECT COUNT(diagnosis_id) "
                + "   FROM user_diagnosis "
                + "   WHERE diagnosis_customer=:diagnosisCustomer "
                + "       AND diagnosis_date LIKE:diagnosisDate"
                + "       AND flag=:flag";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("diagnosisCustomer", diagnosisCustomer);
        paramMap.put("diagnosisDate", "%" + JodaTimeTools.toString(date, JodaTimeTools.FORMAT_6) + "%");
        paramMap.put("flag", Flag.normal.getValue());
        return this.SQLCount(sql, paramMap);
    }

    /**
     * 取消预约
     * 
     * @author dhs
     * @param bookingId
     */
    public void deleteBooking(String bookingId) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("bookingId", bookingId);
        // 删除预约接诊（user_diagnosis_booking）
        this.executeSQL("DELETE FROM user_diagnosis_booking WHERE booking_id=:bookingId", paramsMap);
    }

    /**
     * 修改体重为空(因为体重是BigDecimal类型，只能另写一个方法设为null)
     * TODO:董宏生 跟下面方法一样，以后替换掉，暂时用着
     * 
     * @author dhs
     * @param diagnosisId
     */
    public void updateDiagnosisWeight(String diagnosisId) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("diagnosisId", diagnosisId);
        // 删除预约接诊（user_diagnosis）
        this.executeSQL(
                "UPDATE user_diagnosis SET diagnosis_cust_weight=NULL WHERE diagnosis_id=:diagnosisId",
                paramsMap);
    }

    /**
     * 修改接诊信息各项数值为空(因为是BigDecimal类型，只能另写一个方法设为null)
     * TODO:董宏生 因接诊信息多个字段类型为BigDecimal，用户不填后台封装的hibernate方法默认不修改，临时用此方法解决，因针对性太强，别的地方用会出问题，以后替换掉在hql中解决
     * 
     * @author dhs
     * @param diagnosisId
     */
    public void updateDiagnosisObstetricalNull(PregDiagnosisObstetrical pregDiagnosisObstetrical) {
        String fundalHeight = "";
        if (pregDiagnosisObstetrical.getObstetricalFundalHeight() == null) {// 宫高
            fundalHeight = "obstetrical_fundal_height = NULL,";
        }
        String perimeter = "";
        if (pregDiagnosisObstetrical.getObstetricalAbdominalPerimeter() == null) {// 腹围
            perimeter = "obstetrical_abdominal_perimeter = NULL,";
        }
        String babyWeight = "";
        if (pregDiagnosisObstetrical.getObstetricalBabyWeight() == null) {// 胎儿体重10百分位
            babyWeight = "obstetrical_baby_weight = NULL,";
        }
        String obstetricalBabyFemur = "";
        if (pregDiagnosisObstetrical.getObstetricalBabyFemur() == null) {// 胎儿股骨长
            obstetricalBabyFemur = "obstetrical_baby_femur = NULL,";
        }
        String obstetricalBabyBdp = "";
        if (pregDiagnosisObstetrical.getObstetricalBabyBdp() == null) {// 胎儿双顶径
            obstetricalBabyBdp = "obstetrical_baby_Bdp = NULL,";
        }
        String obstetricalBabyAbdominalPerimeter = "";
        if (pregDiagnosisObstetrical.getObstetricalBabyAbdominalPerimeter() == null) {// 胎儿腹围
            obstetricalBabyAbdominalPerimeter = "obstetrical_baby_abdominal_perimeter = NULL,";
        }
        String obstetricalAmnioticFluidOne = "";
        if (pregDiagnosisObstetrical.getObstetricalAmnioticFluidOne() == null) {// 羊水一象限
            obstetricalAmnioticFluidOne = "obstetrical_amniotic_fluid_one = NULL,";
        }
        String obstetricalAmnioticFluidTwo = "";
        if (pregDiagnosisObstetrical.getObstetricalAmnioticFluidTwo() == null) {// 羊水二象限
            obstetricalAmnioticFluidTwo = "obstetrical_amniotic_fluid_two = NULL,";
        }
        String obstetricalAmnioticFluidThree = "";
        if (pregDiagnosisObstetrical.getObstetricalAmnioticFluidThree() == null) {// 羊水三象限
            obstetricalAmnioticFluidThree = "obstetrical_amniotic_fluid_three = NULL,";
        }
        String obstetricalAmnioticFluidFour = "";
        if (pregDiagnosisObstetrical.getObstetricalAmnioticFluidFour() == null) {// 羊水四象限
            obstetricalAmnioticFluidFour = "obstetrical_amniotic_fluid_four = NULL,";
        }
        String obstetricalDiagnosisSystolic = "";
        if (pregDiagnosisObstetrical.getObstetricalDiagnosisSystolic() == null) {// 收缩压
            obstetricalDiagnosisSystolic = "obstetrical_diagnosis_systolic = NULL,";
        }
        String obstetricalDiagnosisDiastolic = "";
        if (pregDiagnosisObstetrical.getObstetricalDiagnosisDiastolic() == null) {// 舒张压
            obstetricalDiagnosisDiastolic = "obstetrical_diagnosis_diastolic = NULL,";
        }
        String sum = fundalHeight + perimeter + babyWeight + obstetricalBabyFemur + obstetricalBabyBdp
                + obstetricalBabyAbdominalPerimeter + obstetricalAmnioticFluidOne + obstetricalAmnioticFluidTwo
                + obstetricalAmnioticFluidThree + obstetricalAmnioticFluidFour + obstetricalDiagnosisSystolic
                + obstetricalDiagnosisDiastolic;
        if (sum.length() > 0) {
            sum = sum.substring(0, sum.length() - 1);
            Map<String, Object> paramsMap = new HashMap<String, Object>();
            paramsMap.put("diagnosisId", pregDiagnosisObstetrical.getDiagnosisId());
            this.executeSQL("UPDATE user_diagnosis_obstetrical SET " + sum + " WHERE diagnosis_id=:diagnosisId",
                    paramsMap);
        }
    }

    /**
     * 删除接诊相关的信息
     * 
     * @author zcq
     * @param diagnosisId
     */
    public void deleteDiagnosisRelation(String diagnosisId) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("diagnosisId", diagnosisId);
        // 删除接诊基本信息
        this.executeSQL("DELETE FROM user_diagnosis WHERE diagnosis_id=:diagnosisId", paramsMap);
        // 删除接诊实验室指标信息
        // this.executeSQL("DELETE FROM user_diagnosis_hospital_item WHERE exam_code=:diagnosisId", paramsMap);
        // 删除接诊评价项目信息
        this.executeSQL("DELETE FROM user_diagnosis_inspect WHERE diagnosis_id=:diagnosisId", paramsMap);
    }

    /**
     * 根据id查询产科信息
     * 
     * @param diagnosisId
     * @return PregDiagnosisObstetricalPojo
     */
    public PregDiagnosisObstetricalPojo getObstetricalByDiagnosisId(String diagnosisId) {
        String sql = "SELECT "
                + DaoUtils.getSQLFields(PregDiagnosisObstetricalPojo.class, "PregDiagnosisObstetricalPojo")
                + "   FROM user_diagnosis_obstetrical AS PregDiagnosisObstetricalPojo"
                + "   WHERE PregDiagnosisObstetricalPojo.diagnosis_id=:diagnosisId AND flag= :flag";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("diagnosisId", diagnosisId);
        paramMap.put("flag", Flag.normal.getValue());
        return this.SQLQueryAliasFirst(sql, paramMap, PregDiagnosisObstetricalPojo.class);
    }

    /**
     * 条件检索
     * 
     * @author dhs
     * @param condition
     * @return
     */
    public List<PregDiagnosisObstetricalPojo> queryDiagnosisObstetricals(DiagnosisObstetricalCondition condition) {
        if (condition == null) {
            condition = new DiagnosisObstetricalCondition();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "PregDiagnosisObstetricalPojo");
        String querySQL = "SELECT "
                + DaoUtils.getSQLFields(PregDiagnosisObstetricalPojo.class, "PregDiagnosisObstetricalPojo")
                + "   FROM user_diagnosis_obstetrical AS PregDiagnosisObstetricalPojo"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), PregDiagnosisObstetricalPojo.class);
    }

}


package com.mnt.preg.statistic.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.health.utils.times.JodaTimeTools;
import com.mnt.preg.statistic.condition.SearchCountCondition;
import com.mnt.preg.statistic.condition.StatisticForm;
import com.mnt.preg.statistic.pojo.BirthEndingInfoPojo;
import com.mnt.preg.statistic.pojo.CustomerInfoPojo;
import com.mnt.preg.statistic.pojo.DiagnosisInfoPojo;
import com.mnt.preg.statistic.pojo.StatiscBirthResultPojo;
import com.mnt.preg.statistic.pojo.StatisticCustomerPojo;
import com.mysql.jdbc.CallableStatement;

/**
 * 数据统计平台
 * 
 * @author mlq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-08-14 mlq 初版
 */
@Repository
public class StatisticDAO extends HibernateTemplate {

    /**
     * 分娩结局统计
     * 
     * @author dhs
     * @param condition
     * @return
     * @throws SQLException
     * @throws HibernateException
     */
    public StatiscBirthResultPojo queryConfinedAccount(String resultStartDate, String resultEndDate) {
        StatiscBirthResultPojo temp = new StatiscBirthResultPojo();
        try {
            temp = this.doInHibernate(resultStartDate, resultEndDate);
        } catch (HibernateException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    /**
     * 调用存储过程并封装返回值
     * 
     * @author dhs
     * @param Date
     * @return
     */
    private StatiscBirthResultPojo doInHibernate(String strartDate, String endDate) throws HibernateException,
            SQLException {
        @SuppressWarnings("all")
        ConnectionProvider cp = ((SessionFactoryImplementor) sessionFactory).getConnectionProvider();
        Connection c = cp.getConnection();
        CallableStatement cs = (CallableStatement) c
                .prepareCall(
                "{CALL get_confined_count(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
        cs.setString(1, strartDate);
        cs.setString(2, endDate);
        // 指定输出参数类型
        for (int x = 3; x <= 111; x++) {
            cs.registerOutParameter(x, java.sql.Types.INTEGER);
        }
        cs.execute();
        StatiscBirthResultPojo pojo = new StatiscBirthResultPojo();
        pojo.setNumRuYuan(String.valueOf(cs.getInt(3)));
        pojo.setNumBirthChuChan(String.valueOf(cs.getInt(4)));
        pojo.setNumBirthJingChan(String.valueOf(cs.getInt(5)));
        pojo.setNumDangerPreg(String.valueOf(cs.getInt(6)));
        pojo.setNumDeadPreg(String.valueOf(cs.getInt(7)));
        pojo.setNumPregWeek28(String.valueOf(cs.getInt(8)));
        pojo.setNumPregWeek2836(String.valueOf(cs.getInt(9)));
        pojo.setNumPregWeek3741(String.valueOf(cs.getInt(10)));
        pojo.setNumPregWeek42(String.valueOf(cs.getInt(11)));
        pojo.setNumPregFromMy(String.valueOf(cs.getInt(12)));
        pojo.setNumPregFromOther(String.valueOf(cs.getInt(13)));
        pojo.setNumPerinaeumFull(String.valueOf(cs.getInt(14)));
        pojo.setNumPerinaeumOne(String.valueOf(cs.getInt(15)));
        pojo.setNumPerinaeumTwo(String.valueOf(cs.getInt(16)));
        pojo.setNumPerinaeumThree(String.valueOf(cs.getInt(17)));
        pojo.setNumPerinaeumCut(String.valueOf(cs.getInt(18)));
        pojo.setNumBirthTypeAuto(String.valueOf(cs.getInt(19)));
        pojo.setNumBirthTypePull(String.valueOf(cs.getInt(20)));
        pojo.setNumBirthTypeForceps(String.valueOf(cs.getInt(21)));
        pojo.setNumBirthTypeHip(String.valueOf(cs.getInt(22)));
        pojo.setNumBirthTypeDissect(String.valueOf(cs.getInt(23)));
        pojo.setNumBirthTypeOther(String.valueOf(cs.getInt(24)));
        pojo.setNumPullTypeNone(String.valueOf(cs.getInt(25)));
        pojo.setNumPullTypeDrug(String.valueOf(cs.getInt(26)));
        pojo.setNumPullTypeMembrane(String.valueOf(cs.getInt(27)));
        pojo.setNumPullTypeBottle(String.valueOf(cs.getInt(28)));
        pojo.setNumPullTypeMembraneHarm(String.valueOf(cs.getInt(29)));
        pojo.setNumPullTypeOther(String.valueOf(cs.getInt(30)));
        pojo.setNumHelpOprationPostpartumCurettage(String.valueOf(cs.getInt(31)));
        pojo.setNumHelpOprationTurnHead(String.valueOf(cs.getInt(32)));
        pojo.setNumPlacentaHand(String.valueOf(cs.getInt(33)));
        pojo.setNumPlacentaWater(String.valueOf(cs.getInt(34)));
        pojo.setNumPlacentaAuto(String.valueOf(cs.getInt(35)));
        pojo.setNumPregHighLittle(String.valueOf(cs.getInt(36)));
        pojo.setNumPregHighMiddle(String.valueOf(cs.getInt(37)));
        pojo.setNumPregHighSerious(String.valueOf(cs.getInt(38)));
        pojo.setNumPregHighEclampsia(String.valueOf(cs.getInt(39)));
        pojo.setNumSugarAbnormal50g(String.valueOf(cs.getInt(40)));
        pojo.setNumSugarAbnormalPatience(String.valueOf(cs.getInt(41)));
        pojo.setNumSugarAbnormalPregDiabetes(String.valueOf(cs.getInt(42)));
        pojo.setNumSugarAbnormalDiabetesPreg(String.valueOf(cs.getInt(43)));
        pojo.setNumBloodABO(String.valueOf(cs.getInt(44)));
        pojo.setNumBloodRH(String.valueOf(cs.getInt(45)));
        pojo.setNumPelvicExit(String.valueOf(cs.getInt(46)));
        pojo.setNumPelvicEntrance(String.valueOf(cs.getInt(47)));
        pojo.setNumPregHistoryBad(String.valueOf(cs.getInt(48)));
        pojo.setNumBirthHistoryBad(String.valueOf(cs.getInt(49)));
        pojo.setNumCSHistory(String.valueOf(cs.getInt(50)));
        pojo.setNumFloatHead(String.valueOf(cs.getInt(51)));
        pojo.setNumBigAgePreg(String.valueOf(cs.getInt(52)));
        pojo.setNumCaulEarlyHarm(String.valueOf(cs.getInt(53)));
        pojo.setNumTwoManyBirth(String.valueOf(cs.getInt(54)));
        pojo.setNumEarlyBirth(String.valueOf(cs.getInt(55)));
        pojo.setNumTimeoutPreg(String.valueOf(cs.getInt(56)));
        pojo.setNumITP(String.valueOf(cs.getInt(57)));
        pojo.setNumFetalDistressHeart(String.valueOf(cs.getInt(58)));
        pojo.setNumFetalDistressWater(String.valueOf(cs.getInt(59)));
        pojo.setNumFetalDistressHeartWater(String.valueOf(cs.getInt(60)));
        pojo.setNumAbnormalPositionHip(String.valueOf(cs.getInt(61)));
        pojo.setNumAbnormalPositionOccipitotransverse(String.valueOf(cs.getInt(62)));
        pojo.setNumAbnormalPositionOccipitoposterior(String.valueOf(cs.getInt(63)));
        pojo.setNumCordEntanglement(String.valueOf(cs.getInt(64)));
        pojo.setNumCordLong(String.valueOf(cs.getInt(65)));
        pojo.setNumCordShort(String.valueOf(cs.getInt(66)));
        pojo.setNumCordProlapse(String.valueOf(cs.getInt(67)));
        pojo.setNumCordPresent(String.valueOf(cs.getInt(68)));
        pojo.setNumWaterMany(String.valueOf(cs.getInt(69)));
        pojo.setNumWaterLittle(String.valueOf(cs.getInt(70)));
        pojo.setNumPlacentaLow(String.valueOf(cs.getInt(71)));
        pojo.setNumPlacentaPreposition(String.valueOf(cs.getInt(72)));
        pojo.setNumPlacentaEarlyPeeling(String.valueOf(cs.getInt(73)));
        pojo.setNumPlacentaCaulRemain(String.valueOf(cs.getInt(74)));
        pojo.setNumPlacentaImplantation(String.valueOf(cs.getInt(75)));
        pojo.setNumHysterorrhexis(String.valueOf(cs.getInt(76)));
        pojo.setNumBirthFever(String.valueOf(cs.getInt(77)));
        pojo.setNumWaterEmbolism(String.valueOf(cs.getInt(78)));
        pojo.setNumBirthBradytoia(String.valueOf(cs.getInt(79)));
        pojo.setNumBirthSecond(String.valueOf(cs.getInt(80)));
        pojo.setNumBirthInfection(String.valueOf(cs.getInt(81)));
        pojo.setNumBirthLowHeat(String.valueOf(cs.getInt(82)));
        pojo.setNumUroschesis(String.valueOf(cs.getInt(83)));
        pojo.setNumWoundInfection(String.valueOf(cs.getInt(84)));
        pojo.setNumWoundFatLiquefaction(String.valueOf(cs.getInt(85)));
        pojo.setNumAnemia(String.valueOf(cs.getInt(86)));
        pojo.setNumAusAntiPositive(String.valueOf(cs.getInt(87)));
        pojo.setNumSLE(String.valueOf(cs.getInt(88)));
        pojo.setNumNewBirthSexMale(String.valueOf(cs.getInt(89)));
        pojo.setNumNewBirthSexFemale(String.valueOf(cs.getInt(90)));
        pojo.setNumUnknow(String.valueOf(cs.getInt(91)));
        pojo.setNumNewBirthWeight1000(String.valueOf(cs.getInt(92)));
        pojo.setNumNewBirthWeight10001499(String.valueOf(cs.getInt(93)));
        pojo.setNumNewBirthWeight15001999(String.valueOf(cs.getInt(94)));
        pojo.setNumNewBirthWeight20002499(String.valueOf(cs.getInt(95)));
        pojo.setNumNewBirthWeight25002999(String.valueOf(cs.getInt(96)));
        pojo.setNumNewBirthWeight30003499(String.valueOf(cs.getInt(97)));
        pojo.setNumNewBirthWeight35003999(String.valueOf(cs.getInt(98)));
        pojo.setNumNewBirthWeight40004499(String.valueOf(cs.getInt(99)));
        pojo.setNumNewBirthWeight4500(String.valueOf(cs.getInt(100)));
        pojo.setNumLowWeight2500(String.valueOf(cs.getInt(101)));
        pojo.setNumLowWeight1500(String.valueOf(cs.getInt(102)));
        pojo.setNumAsphyxiaLight(String.valueOf(cs.getInt(103)));
        pojo.setNumAsphyxiaSerious(String.valueOf(cs.getInt(104)));
        pojo.setNumSGA(String.valueOf(cs.getInt(105)));
        pojo.setNumRDS(String.valueOf(cs.getInt(106)));
        pojo.setNumPneumonia(String.valueOf(cs.getInt(107)));
        pojo.setNumBrainBlood(String.valueOf(cs.getInt(108)));
        pojo.setNumDeadFetus(String.valueOf(cs.getInt(109)));
        pojo.setNumDeadBirth(String.valueOf(cs.getInt(110)));
        pojo.setNumNewBirthDead(String.valueOf(cs.getInt(111)));
        pojo.setNumPregFromGaowei(String.valueOf(cs.getInt(112)));
        pojo.setNumPregFromNone(String.valueOf(cs.getInt(113)));
        cs.close();
        c.close();
        return pojo;
    }

    /**
     * 查询患者基本信息
     * 
     * @author mlq
     * @param custIds
     * @return
     */
    public List<CustomerInfoPojo> queryCustomerInfoByCustIds(SearchCountCondition condition) {
        if (condition == null) {
            condition = new SearchCountCondition();
        }
        // 设置查询条件
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "customerInfoPojo");
        // sql语法
        String sql = "SELECT "
                + DaoUtils.getSQLFields(CustomerInfoPojo.class, "customerInfoPojo")
                + "   ,get_age(conditionPojo.cust_birthday) AS custAge"
                + "   FROM cus_customer AS conditionPojo "
                + "   LEFT JOIN  cus_pregnancy_archives AS archivesPojo ON (archivesPojo.cust_id = conditionPojo.cust_id)"
                + queryCondition.getQueryString()
                + "   GROUP BY custId,id"
                + "   ORDER BY custId";
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), CustomerInfoPojo.class);
    }

    /**
     * 查询患者接诊信息
     * 
     * @author mlq
     * @param condition
     * @return
     */
    public List<DiagnosisInfoPojo> queryDiagnosisInfoByCustIds(SearchCountCondition condition) {
        if (condition == null) {
            condition = new SearchCountCondition();
        }
        // 分娩结局查询按照建档id进行统计
        String mysql = "";
        if (condition.getStage() == 2 && CollectionUtils.isNotEmpty(condition.getArchivesIds())) {
            mysql = " AND u1.archives_id = conditionPojo.archives_id";
        }

        // 设置查询条件
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "diagnosisInfoPojo");
        // sql语法
        String sql = "SELECT "
                + DaoUtils.getSQLFields(DiagnosisInfoPojo.class, "diagnosisInfoPojo")
                + "   ,(SELECT COUNT(u1.diagnosis_id) FROM user_diagnosis AS u1 "
                + "       WHERE u1.diagnosis_customer = conditionPojo.diagnosis_customer AND diagnosis_status='2'" + mysql
                + ") AS diagnosisCount "
                + "   FROM user_diagnosis AS conditionPojo "
                + queryCondition.getQueryString()
                + " AND conditionPojo.diagnosis_status = '2'"
                + " GROUP BY custId,diagnosisId"
                + " ORDER BY custId,diagnosisDate DESC";
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), DiagnosisInfoPojo.class);
    }

    /**
     * 查询患者分娩结局信息
     * 
     * @author mlq
     * @param condition
     * @return
     */
    public List<BirthEndingInfoPojo> queryBirthEndingInfoByCustIds(SearchCountCondition condition) {
        if (condition == null) {
            condition = new SearchCountCondition();
        }
        // 设置查询条件
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "birthEndingInfoPojo");
        // sql语法
        String sql = "SELECT "
                + DaoUtils.getSQLFields(BirthEndingInfoPojo.class, "birthEndingInfoPojo")
                + "   FROM cus_birthending AS conditionPojo "
                + "   LEFT JOIN cus_birthending_baseinfo AS baseinfoPojo ON (conditionPojo.birth_id = baseinfoPojo.birth_id)"
                + "   LEFT JOIN cus_birthending_discharged AS dischargedPojo ON (dischargedPojo.birth_id = baseinfoPojo.birth_id)"
                + queryCondition.getQueryString()
                + " GROUP BY custId,birthId"
                + " ORDER BY custId,baseinfoPojo.base_time DESC";
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), BirthEndingInfoPojo.class);
    }

    /**
     * 超级查询--查询患者列表
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public List<StatisticCustomerPojo> queryCustomerList(StatisticForm condition) {
        List<String> idList = queryIdList(condition);
        if (CollectionUtils.isEmpty(idList)) {
            return null;
        }
        String idListStr = StringUtils.join(idList, "','");
        String querySQL = "";
        if ("1".equals(condition.getPregPeriod())) {
            querySQL = "SELECT "
                    + "    z.diagnosis_customer AS custId,"
                    + "    z.diagnosis_cust_name AS custName,"
                    + "    z.diagnosis_cust_sike_id AS custSikeId,"
                    + "    z.diagnosis_cust_patient_id AS custPatientId,"
                    + "    arch.cust_age AS custAge,"
                    + "    z.diagnosis_org AS custOrg,"
                    + "    z.diagnosis_user_name AS doctorName,"
                    + "    z.diagnosis_count AS diagnosisCount,"
                    + "    arch.pregnancy_num AS pregnancyNum,"
                    + "    arch.birth_num AS birthNum,"
                    + "    arch.encyesis_situation AS encyesisSituation,"
                    + "    arch.pregnancy_due_date AS pregnancyDueDate "
                    + "FROM (SELECT diag1.*, diag2.diagnosis_count "
                    + "    FROM (SELECT  t.* "
                    + "        FROM (SELECT "
                    + "                diagnosis_customer,"
                    + "                diagnosis_cust_name,"
                    + "                diagnosis_cust_sike_id,"
                    + "                diagnosis_cust_patient_id,"
                    + "                diagnosis_org,"
                    + "                diagnosis_user_name,"
                    + "                archives_id"
                    + "            FROM user_diagnosis"
                    + "            WHERE diagnosis_id IN('" + idListStr + "')"
                    + "            ORDER BY diagnosis_date DESC) t"
                    + "        GROUP BY t.archives_id) diag1 "
                    + "    JOIN (SELECT diagnosis_customer, archives_id, COUNT(diagnosis_customer) AS diagnosis_count"
                    + "        FROM user_diagnosis"
                    + "        WHERE diagnosis_status = '2' AND archives_id IN("
                    + "            SELECT DISTINCT archives_id"
                    + "            FROM user_diagnosis"
                    + "            WHERE diagnosis_id IN('" + idListStr + "'))"
                    + "        GROUP BY archives_id) diag2 ON diag1.archives_id = diag2.archives_id) z "
                    + "JOIN cus_pregnancy_archives arch ON z.archives_id = arch.id;";
        } else {
            List<String> archIdList = this.SQLQuery("SELECT archives_id FROM cus_birthending WHERE birth_id IN('" + idListStr
                    + "') AND archives_id IS NOT NULL AND archives_id != ''");
            String archIdListStr = StringUtils.join(archIdList, "','");
            querySQL = "SELECT "
                    + "  zt2.cust_id AS custId,"
                    + "  zt2.cust_name AS custName,"
                    + "  zt2.cust_sike_id AS custSikeId,"
                    + "  zt2.cust_patient_id AS custPatientId,"
                    + "  zt1.birth_age AS custAge,"
                    + "  zt3.diagnosis_org AS custOrg,"
                    + "  zt3.diagnosis_user_name AS doctorName,"
                    + "  zt3.diagnosis_count AS diagnosisCount,"
                    + "  zt1.birth_preg_number AS pregnancyNum,"
                    + "  zt1.birth_born_number AS birthNum,"
                    + "  zt1.birth_tires_type AS birthTiresType,"
                    + "  zt1.birth_tires_type2 AS birthTiresType2,"
                    + "  zt1.base_time AS birthDate "
                    + "FROM (SELECT "
                    + "      t.* "
                    + "  FROM (SELECT "
                    + "          birth.cust_id,"
                    + "          baseInfo.birth_age,"
                    + "          birth.birth_preg_number,"
                    + "          birth.birth_born_number,"
                    + "          birth.birth_tires_type,"
                    + "          birth.birth_tires_type2,"
                    + "          baseInfo.base_time,"
                    + "          birth.archives_id"
                    + "      FROM cus_birthending AS birth"
                    + "      JOIN cus_birthending_baseinfo AS baseInfo"
                    + "      ON baseInfo.birth_id=birth.birth_id AND baseInfo.birth_id IN('" + idListStr + "')"
                    + "      ORDER BY baseInfo.base_time DESC) t "
                    + "  GROUP BY t.cust_id) zt1 "
                    + "JOIN (SELECT "
                    + "      cust_id,"
                    + "      cust_name,"
                    + "      cust_sike_id,"
                    + "      cust_patient_id"
                    + "  FROM cus_customer) zt2 "
                    + "ON zt1.cust_id = zt2.cust_id "
                    + "LEFT JOIN (SELECT "
                    + "      t.*, "
                    + "      COUNT(t.diagnosis_customer) AS diagnosis_count"
                    + "  FROM (SELECT "
                    + "          diagnosis_customer,"
                    + "          diagnosis_org,"
                    + "          diagnosis_user_name,"
                    + "          archives_id"
                    + "      FROM user_diagnosis"
                    + "      WHERE diagnosis_status = '2' AND archives_id IN('" + archIdListStr + "') "
                    + "      ORDER BY diagnosis_date DESC) t"
                    + "  GROUP BY t.archives_id) zt3 "
                    + "ON zt1.archives_id = zt3.archives_id;";
        }
        return this.SQLQueryAlias(querySQL, StatisticCustomerPojo.class);
    }

    /**
     * 查询符合条件的患者
     * 
     * @author zcq
     * @param condition
     * @return
     */
    private List<String> queryIdList(StatisticForm condition) {
        if (condition == null) {
            return null;
        }

        int ztCount = 0;
        String[] ztSqlArray = new String[3];
        String[] ztAlias = new String[3];
        String[] ztField = new String[3];

        /************************** 第一部分：建档相关的基本信息 *************************/

        int zt1Count = 0;
        String[] zt1SqlArray = new String[2];
        String[] zt1Field = new String[2];

        // 基本信息--建档表或分娩结局表：cus_pregnancy_archives
        if ("1".equals(condition.getPregPeriod())) {
            zt1Field[zt1Count] = "question_alloc_id";
            zt1SqlArray[zt1Count++] = getArchSQL(condition);
        }

        // 孕前病史及孕育史--建档问卷答案表：cus_question_answer
        String archAnswerSQL = getArchAnswerSQL(condition);
        if (StringUtils.isNotBlank(archAnswerSQL)) {
            if ("2".equals(condition.getPregPeriod())) {
                zt1Field[zt1Count] = "question_alloc_id";
                zt1SqlArray[zt1Count++] = "SELECT id, cust_id, question_alloc_id FROM cus_pregnancy_archives ";
            }
            zt1Field[zt1Count] = "question_alloc_id";
            zt1SqlArray[zt1Count++] = archAnswerSQL;
        }

        // 组合SQL
        if (zt1Count > 0) {
            String[] zt1Alias = {"zz1", "zz2"};
            String zt1SQL = getJoinSql("DISTINCT zz1.id AS archives_id, zz1.cust_id", zt1Count, zt1SqlArray, zt1Alias,
                    zt1Field);

            ztAlias[ztCount] = "archZT";
            ztField[ztCount] = "archives_id";
            ztSqlArray[ztCount++] = zt1SQL;
        }

        /************************** 第二部分：接诊相关信息 *************************/

        int zt2Count = 0;
        String[] zt2SqlArray = new String[3];
        String[] zt2Field = new String[3];

        // 孕期诊疗信息--接诊信息表：user_diagnosis
        boolean birthFlag = false;
        String diagSQL = getDiagSQL(condition);
        zt2Field[zt2Count] = "diagnosis_id";
        if (StringUtils.isNotBlank(diagSQL)) {
            zt2SqlArray[zt2Count++] = diagSQL;
            birthFlag = true;
        } else {
            zt2SqlArray[zt2Count++] = "SELECT diagnosis_id, diagnosis_customer, archives_id FROM user_diagnosis WHERE diagnosis_status = '2' ";
        }

        // 人体成分--人体成分表：cus_result_inbody
        String inbodySQL = getInbodySQL(condition);
        if (StringUtils.isNotBlank(inbodySQL)) {
            zt2Field[zt2Count] = "diagnosis_id";
            zt2SqlArray[zt2Count++] = inbodySQL;
        }

        // 孕期检验检测信息--检验项目表：user_diagnosis_hospital_item
        String itemSQL = getItemSQL(condition);
        if (StringUtils.isNotBlank(itemSQL)) {
            zt2Field[zt2Count] = "diagnosis_id";
            zt2SqlArray[zt2Count++] = itemSQL;
        }

        // 组合SQL
        if ("1".equals(condition.getPregPeriod())) {
            if (zt2Count > 0) {
                String[] zt2Alias = {"z1", "z2", "z3"};
                String zt2SQL = getJoinSql("DISTINCT z1.diagnosis_id, z1.archives_id", zt2Count, zt2SqlArray, zt2Alias,
                        zt2Field);

                ztAlias[ztCount] = "diagZT";
                ztField[ztCount] = "archives_id";
                ztSqlArray[ztCount++] = zt2SQL;
            }
        } else {
            if (zt2Count > 1 || birthFlag) {
                String[] zt2Alias = {"z1", "z2", "z3"};
                String zt2SQL = getJoinSql("DISTINCT z1.diagnosis_id, z1.archives_id", zt2Count, zt2SqlArray, zt2Alias,
                        zt2Field);

                ztAlias[ztCount] = "diagZT";
                ztField[ztCount] = "archives_id";
                ztSqlArray[ztCount++] = zt2SQL;
            }
        }

        /************************** 第三部分：分娩相关信息 *************************/
        // 分娩结局
        String birthSQL = "";
        if ("2".equals(condition.getPregPeriod())) {
            birthSQL = getBirthEndingSQL(condition);
        } else {
            birthSQL = getBirthEndingSQLForPreg(condition);
        }
        if (StringUtils.isNotBlank(birthSQL)) {
            ztAlias[ztCount] = "birthZT";
            ztField[ztCount] = "archives_id";
            ztSqlArray[ztCount++] = birthSQL;
        }

        /************************** 拼接整体SQL文 *************************/
        // 拼接整体SQL文
        String ztSQL = "";
        if ("1".equals(condition.getPregPeriod())) {
            ztSQL = getJoinSql("DISTINCT diagZT.diagnosis_id", ztCount, ztSqlArray, ztAlias, ztField);
        } else {
            ztSQL = getJoinSql("DISTINCT birthZT.birth_id", ztCount, ztSqlArray, ztAlias, ztField);
        }

        // 返回结果
        List<String> idList = this.SQLQuery(ztSQL);

        return idList;
    }

    /**
     * 基本信息--建档表：cus_pregnancy_archives
     * 
     * @author zcq
     * @param condition
     * @return
     */
    private String getArchSQL(StatisticForm condition) {
        StringBuffer archSQL = new StringBuffer("");
        archSQL.append("SELECT id, cust_id, question_alloc_id FROM cus_pregnancy_archives WHERE pregnancy_due_date BETWEEN '"
                + condition.getStartDate() + "' AND '" + condition.getEndDate() + "' ");
        // 身高
        if (StringUtils.isNotBlank(condition.getHeightFrom())) {
            archSQL.append("AND height >= " + condition.getHeightFrom() + " ");
        }
        if (StringUtils.isNotBlank(condition.getHeightTo())) {
            archSQL.append("AND height <= " + condition.getHeightTo() + " ");
        }
        // 年龄
        if (StringUtils.isNotBlank(condition.getAgeFrom())) {
            archSQL.append("AND cust_age >= " + condition.getAgeFrom() + " ");
        }
        if (StringUtils.isNotBlank(condition.getAgeTo())) {
            archSQL.append("AND cust_age <= " + condition.getAgeTo() + " ");
        }
        // bmi
        if (ArrayUtils.isNotEmpty(condition.getBmiScope())) {
            String[] bmiArray = condition.getBmiScope();
            StringBuffer bmiSQL = new StringBuffer("");
            for (int i = 0; i < bmiArray.length; i++) {
                bmiSQL.append("OR (bmi BETWEEN " + bmiArray[i].split("-")[0] + " AND " + bmiArray[i].split("-")[1] + ") ");
            }
            archSQL.append("AND (" + bmiSQL.toString().replaceFirst("OR", "") + ") ");
        }
        // 胎数
        if (ArrayUtils.isNotEmpty(condition.getBirthNum())) {
            archSQL.append("AND embryo_num IN('" + StringUtils.join(condition.getBirthNum(), "', '") + "') ");
        }
        return archSQL.toString();
    }

    /**
     * 孕前病史及孕育史--建档问卷答案表：cus_question_answer
     * 
     * @author zcq
     * @param condition
     * @return
     */
    private String getArchAnswerSQL(StatisticForm condition) {

        int archAnswerCount1 = 0;
        String[] archAnswerArray1 = new String[3];

        // 病史
        if (ArrayUtils.isNotEmpty(condition.getDiseaseHistory())) {
            archAnswerArray1[archAnswerCount1++] = "SELECT question_alloc_id FROM cus_question_answer WHERE (problem_id = '402880ef5a91a2b1015a91d2aa8b000e' AND problem_option_id IN('"
                    + StringUtils.join(condition.getDiseaseHistory(), "', '")
                    + "')) OR (problem_id = '402880ef5a91a2b1015a91e3ad350025' AND problem_option_id IN('"
                    + StringUtils.join(condition.getDiseaseHistory(), "', '") + "')) ";
        }
        // 家族史
        if (ArrayUtils.isNotEmpty(condition.getFamilyHistory())) {
            archAnswerArray1[archAnswerCount1++] = "SELECT question_alloc_id FROM cus_question_answer WHERE problem_id = '402880f9653c86f901653ca01df80000' AND problem_option_id IN('"
                    + StringUtils.join(condition.getFamilyHistory(), "', '") + "') ";
        }
        // 妊娠并发症
        if (ArrayUtils.isNotEmpty(condition.getPregComplications())) {
            archAnswerArray1[archAnswerCount1++] = "SELECT question_alloc_id FROM cus_question_answer WHERE problem_id = '402880ef5a91a2b1015a91eb0c02002d' AND problem_option_id IN('"
                    + StringUtils.join(condition.getPregComplications(), "', '") + "') ";
        }

        int archAnswerCount2 = 0;
        String[] archAnswerArray2 = new String[5];
        // 不良孕史
        if (ArrayUtils.isNotEmpty(condition.getBadPregHistory())) {
            archAnswerArray2[archAnswerCount2++] = "SELECT question_alloc_id FROM cus_question_answer WHERE problem_id IN('"
                    + StringUtils.join(condition.getBadPregHistory(), "', '") + "') AND answer_content >= 1 ";
        }
        // 不良产史
        if (ArrayUtils.isNotEmpty(condition.getBadBirthHistory())) {
            // 不良产史(早产)
            if (ArrayUtils.contains(condition.getBadBirthHistory(), "210100700000232")) {
                archAnswerArray2[archAnswerCount2++] = "SELECT question_alloc_id FROM cus_question_answer WHERE problem_id = '402880ef5a91a2b1015a91e73e730029' AND problem_option_id = '210100700000232' ";
            }
            // 不良产史(中期或者晚期引产)
            if (ArrayUtils.contains(condition.getBadBirthHistory(), "210100700000234")) {
                archAnswerArray2[archAnswerCount2++] = "SELECT question_alloc_id FROM cus_question_answer WHERE problem_id = '402880ef5a91a2b1015a91e812f1002a' AND problem_option_id = '210100700000234' ";
            }
            // 不良产史(巨大儿分娩)
            if (ArrayUtils.contains(condition.getBadBirthHistory(), "210100700000236")) {
                archAnswerArray2[archAnswerCount2++] = "SELECT question_alloc_id FROM cus_question_answer WHERE problem_id = '402880ef5a91a2b1015a91e8818e002b' AND problem_option_id = '210100700000236' ";
            }
            // 不良产史(畸形)
            if (ArrayUtils.contains(condition.getBadBirthHistory(), "210100700000238")) {
                archAnswerArray2[archAnswerCount2++] = "SELECT question_alloc_id FROM cus_question_answer WHERE problem_id = '402880ef5a91a2b1015a91e8ec52002c' AND problem_option_id = '210100700000238' ";
            }
        }

        int archAnswerCount3 = 0;
        String[] archAnswerArray3 = new String[2];
        // 孕几次
        if (ArrayUtils.isNotEmpty(condition.getPregTimes())) {
            String[] pregTimes = condition.getPregTimes();
            String pregTimesStr = "";
            if (ArrayUtils.contains(pregTimes, "1")) {
                pregTimesStr += "OR answer_content = 1 ";
            }
            if (ArrayUtils.contains(pregTimes, "2")) {
                pregTimesStr += "OR answer_content = 2 ";
            }
            if (ArrayUtils.contains(pregTimes, "3")) {
                pregTimesStr += "OR answer_content = 3 ";
            }
            if (ArrayUtils.contains(pregTimes, "4")) {
                pregTimesStr += "OR answer_content >= 4 ";
            }
            archAnswerArray3[archAnswerCount3++] = "SELECT question_alloc_id FROM cus_question_answer WHERE problem_id = '402880ef5a91a2b1015a91e542360026' AND problem_option_id = '210100700000228' AND ("
                    + pregTimesStr.replaceFirst("OR", "") + ") ";
        }
        // 产几次
        if (ArrayUtils.isNotEmpty(condition.getBirthTimes())) {
            String[] birthTimes = condition.getBirthTimes();
            String birthTimesStr = "";
            if (ArrayUtils.contains(birthTimes, "0")) {
                birthTimesStr += "OR answer_content = 0 ";
            }
            if (ArrayUtils.contains(birthTimes, "1")) {
                birthTimesStr += "OR answer_content = 1 ";
            }
            if (ArrayUtils.contains(birthTimes, "2")) {
                birthTimesStr += "OR answer_content = 2 ";
            }
            if (ArrayUtils.contains(birthTimes, "3")) {
                birthTimesStr += "OR answer_content >= 3 ";
            }
            archAnswerArray3[archAnswerCount3++] = "SELECT question_alloc_id FROM cus_question_answer WHERE problem_id = '402880ef5a91a2b1015a91e542360026' AND problem_option_id = '210100700000229' AND ("
                    + birthTimesStr.replaceFirst("OR", "") + ") ";
        }

        String[] archAliasT = {"t1", "t2", "t3", "t4", "t5", "t6", "t7", "t8"};
        String[] archAliasZ = {"z1", "z2", "z3", "z4", "z5", "z6", "z7", "z8"};
        String[] questionAllocIdField = {"question_alloc_id", "question_alloc_id", "question_alloc_id",
                "question_alloc_id", "question_alloc_id", "question_alloc_id"};

        int archAnswerCount4 = 0;
        String[] archAnswerArray4 = new String[3];
        if (archAnswerCount1 > 0) {
            archAnswerArray4[archAnswerCount4++] = getJoinSql("t1.question_alloc_id", archAnswerCount1,
                    archAnswerArray1, archAliasT, questionAllocIdField);
        }
        if (archAnswerCount2 > 0) {
            archAnswerArray4[archAnswerCount4++] = getJoinSql("t1.question_alloc_id", archAnswerCount2,
                    archAnswerArray2, archAliasT, questionAllocIdField);
        }
        if (archAnswerCount3 > 0) {
            archAnswerArray4[archAnswerCount4++] = getJoinSql("t1.question_alloc_id", archAnswerCount3,
                    archAnswerArray3, archAliasT, questionAllocIdField);
        }

        // 组合SQL
        String archAnswerSQL = "";
        if (archAnswerCount4 > 0) {
            archAnswerSQL = getJoinSql("z1.question_alloc_id", archAnswerCount4, archAnswerArray4, archAliasZ,
                    questionAllocIdField);
        }
        return archAnswerSQL;
    }

    /**
     * 孕期诊疗信息--接诊信息表：user_diagnosis
     * 
     * @author zcq
     * @param condition
     * @return
     */
    private String getDiagSQL(StatisticForm condition) {

        int archIdCount = 0;
        String[] archIdArray = new String[3];
        // 就诊次数
        if (StringUtils.isNotBlank(condition.getMenzhenNumFrom())
                || StringUtils.isNotBlank(condition.getMenzhenNumTo())) {
            StringBuffer diagCountSQL = new StringBuffer(
                    "SELECT archives_id FROM user_diagnosis WHERE diagnosis_status = '2' GROUP BY archives_id HAVING COUNT(archives_id) ");
            if (StringUtils.isNotBlank(condition.getMenzhenNumFrom())
                    && StringUtils.isNotBlank(condition.getMenzhenNumTo())) {
                diagCountSQL.append("BETWEEN " + condition.getMenzhenNumFrom() + " AND " + condition.getMenzhenNumTo() + " ");
            } else if (StringUtils.isNotBlank(condition.getMenzhenNumFrom())
                    && StringUtils.isBlank(condition.getMenzhenNumTo())) {
                diagCountSQL.append(">= " + condition.getMenzhenNumFrom() + " ");
            } else if (StringUtils.isBlank(condition.getMenzhenNumFrom())
                    && StringUtils.isNotBlank(condition.getMenzhenNumTo())) {
                diagCountSQL.append("<= " + condition.getMenzhenNumTo() + " ");
            }
            archIdArray[archIdCount++] = diagCountSQL.toString();
        }
        // 首诊孕周数&首次产检妊娠风险级别
        if (StringUtils.isNotBlank(condition.getMenzhenPregWeekFrom())
                || StringUtils.isNotBlank(condition.getMenzhenPregWeekTo())
                || ArrayUtils.isNotEmpty(condition.getFirstLevel())) {
            archIdArray[archIdCount] = "SELECT archives_id FROM user_diagnosis WHERE diagnosis_status = '2' AND diagnosis_type = '1' ";
            if (StringUtils.isNotBlank(condition.getMenzhenPregWeekFrom())) {
                archIdArray[archIdCount] += "AND diagnosis_gestational_weeks >= "
                        + (Integer.valueOf(condition.getMenzhenPregWeekFrom()) - 1) + " ";
            }
            if (StringUtils.isNotBlank(condition.getMenzhenPregWeekTo())) {
                archIdArray[archIdCount] += "AND diagnosis_gestational_weeks <= "
                        + (Integer.valueOf(condition.getMenzhenPregWeekTo()) - 1) + " ";
            }
            if (ArrayUtils.isNotEmpty(condition.getFirstLevel())) {
                archIdArray[archIdCount] += "AND gestation_level IN('" + StringUtils.join(condition.getFirstLevel(), "', '")
                        + "') ";
            }
            archIdCount++;
        }
        // 末次产检妊娠风险级别
        if (ArrayUtils.isNotEmpty(condition.getLastLevel())) {
            archIdArray[archIdCount++] = "SELECT diag.archives_id FROM "
                    + "(SELECT archives_id FROM user_diagnosis WHERE diagnosis_status = '2' AND gestation_level IN('"
                    + StringUtils.join(condition.getLastLevel(), "', '") + "') ORDER BY diagnosis_date DESC) diag "
                    + "GROUP BY diag.archives_id ";
        }

        int diagCount = 0;
        String[] diagArray = new String[5];

        diagArray[diagCount] = "SELECT diagnosis_id, diagnosis_customer, archives_id FROM user_diagnosis WHERE diagnosis_status = '2' ";
        // 接诊医生
        if (ArrayUtils.isNotEmpty(condition.getUserSelect())) {
            diagArray[diagCount] += "AND diagnosis_user IN('" + StringUtils.join(condition.getUserSelect(), "', '") + "') ";
        }
        // 满足组合条件（就诊次数||首诊孕周数||首次产检妊娠风险级别||末次产检妊娠风险级别）
        if (archIdCount > 0) {
            String[] archAliasT = {"t1", "t2", "t3"};
            String[] fieldNameT = {"archives_id", "archives_id", "archives_id"};
            String archIdSQL = getJoinSql("DISTINCT t1.archives_id", archIdCount, archIdArray, archAliasT, fieldNameT);

            diagArray[diagCount] += "AND archives_id IN(" + archIdSQL + ") ";
        }
        diagCount++;

        // 诊断
        if (StringUtils.isNotBlank(condition.getDeseaseIds())) {
            diagArray[diagCount++] = "SELECT diagnosis_id FROM user_diagnosis_disease WHERE disease_id IN('"
                    + condition.getDeseaseIds().replace(",", "', '") + "') ";
        }

        // 组合SQL
        String diagSQL = "";
        if (ArrayUtils.isNotEmpty(condition.getUserSelect()) || archIdCount > 0 || diagCount > 1) {
            String[] diagAlias = {"diagZ1", "diagZ2"};
            String[] diagField = {"diagnosis_id", "diagnosis_id"};
            diagSQL = getJoinSql("diagZ1.diagnosis_id, diagZ1.diagnosis_customer, diagZ1.archives_id", diagCount, diagArray,
                    diagAlias, diagField);
        }

        return diagSQL;
    }

    /**
     * 人体成分--人体成分表：cus_result_inbody
     * 
     * @author zcq
     * @param condition
     * @return
     */
    private String getInbodySQL(StatisticForm condition) {
        String[] archAliasT = {"t1", "t2", "t3", "t4", "t5", "t6", "t7", "t8"};
        String inbodySQL = "";
        int inbodyCount1 = 2;
        String[] inbodyArray1 = new String[6];
        // 体重增长情况
        if (ArrayUtils.isNotEmpty(condition.getWeightCondition())) {
            inbodyArray1[inbodyCount1++] = "SELECT exam_id FROM cus_result_inbody WHERE item_code = 'BODY00046' AND item_result IN('"
                    + StringUtils.join(condition.getWeightCondition(), "', '") + "') AND exam_id IS NOT NULL ";
        }
        // 骨骼肌减少
        if ("1".equals(condition.getMuscleReduce())) {
            inbodyArray1[inbodyCount1++] = "SELECT exam_id FROM cus_result_inbody WHERE item_code = 'BODY00047' AND item_result = '骨骼肌减少' AND exam_id IS NOT NULL ";
        }
        // 蛋白质减少
        if ("1".equals(condition.getProteinReduce())) {
            inbodyArray1[inbodyCount1++] = "SELECT exam_id FROM cus_result_inbody WHERE item_code = 'BODY00048' AND item_result = '蛋白质减少' AND exam_id IS NOT NULL ";
        }
        // 相位角
        if (StringUtils.isNotBlank(condition.getXiangweiFrom()) && StringUtils.isNotBlank(condition.getXiangweiTo())) {
            inbodyArray1[inbodyCount1++] = "SELECT exam_id FROM cus_result_inbody WHERE item_code = 'BODY00045' AND item_string BETWEEN "
                    + condition.getXiangweiFrom() + " AND " + condition.getXiangweiTo() + " ";
        } else if (StringUtils.isNotBlank(condition.getXiangweiFrom())
                && StringUtils.isBlank(condition.getXiangweiTo())) {
            inbodyArray1[inbodyCount1++] = "SELECT exam_id FROM cus_result_inbody WHERE item_code = 'BODY00045' AND item_string >= "
                    + condition.getXiangweiFrom() + " ";
        } else if (StringUtils.isBlank(condition.getXiangweiFrom())
                && StringUtils.isNotBlank(condition.getXiangweiTo())) {
            inbodyArray1[inbodyCount1++] = "SELECT exam_id FROM cus_result_inbody WHERE item_code = 'BODY00045' AND item_string <= "
                    + condition.getXiangweiTo() + " ";
        }
        // 浮肿情况
        if (ArrayUtils.isNotEmpty(condition.getFuzhongCondition())) {
            inbodyArray1[inbodyCount1++] = "SELECT exam_id FROM cus_result_inbody WHERE item_code = 'BODY00053' AND item_result IN('"
                    + StringUtils.join(condition.getFuzhongCondition(), "', '") + "') AND exam_id IS NOT NULL ";
        }
        // 人体成分评价项目
        if (inbodyCount1 > 2) {
            inbodyArray1[0] = "SELECT diagnosis_id, result_code AS exam_code FROM user_diagnosis_inspect WHERE inspect_code = 'B00003' AND inspect_status = '3' ";
            inbodyArray1[1] = "SELECT exam_id, exam_code FROM cus_result_record WHERE exam_category = 'B00003' ";
            String[] inbodyField = {"exam_code", "exam_code", "exam_id", "exam_id", "exam_id", "exam_id", "exam_id"};
            inbodySQL = getJoinSql("t1.diagnosis_id", inbodyCount1, inbodyArray1, archAliasT, inbodyField);
        }
        return inbodySQL;
    }

    /**
     * 孕期检验检测信息--检验项目表：user_diagnosis_hospital_item
     * 
     * @author zcq
     * @param condition
     * @return
     */
    private String getItemSQL(StatisticForm condition) {

        int itemCount1 = 0;
        String[] itemArray1 = new String[2];
        String[] custIdField = new String[2];

        // 检验项目
        if (StringUtils.isNotBlank(condition.getNormalInspectItemIds())) {
            String[] inspects = condition.getNormalInspectItemIds().split(",");
            StringBuffer items = new StringBuffer("");
            for (int i = 0; i < inspects.length; i++) {
                String[] idvalue = inspects[i].split("~", 3);
                items.append("OR (item_id = '" + idvalue[0] + "' ");
                if (StringUtils.isNotBlank(idvalue[1])) {
                    items.append("AND item_value >= " + idvalue[1] + " ");
                }
                if (StringUtils.isNotBlank(idvalue[2])) {
                    items.append("AND item_value <= " + idvalue[2] + " ");
                }
                items.append(") ");
            }
            custIdField[itemCount1] = "diagnosis_id";
            itemArray1[itemCount1++] = "SELECT diagnosis_id FROM user_diagnosis_hospital_item WHERE "
                    + items.toString().replaceFirst("OR", "");
        }

        String itemSQL = "";
        if (itemCount1 > 0) {
            String[] archAliasT = {"t1", "t2"};
            itemSQL = getJoinSql("t1.diagnosis_id", itemCount1, itemArray1, archAliasT, custIdField);
        }
        return itemSQL;
    }

    /**
     * 分娩信息--分娩信息表：cus_birth_ending
     * 
     * @author zcq
     * @param condition
     * @return
     */
    private String getBirthEndingSQL(StatisticForm condition) {
        StringBuffer birthSQL = new StringBuffer("");
        // 产检医院
        if (StringUtils.isNotBlank(condition.getBirthSeize())) {
            if ("1".equals(condition.getBirthSeize())) {
                birthSQL.append("AND birth_is_preg_hospital = '1' ");
            } else {
                birthSQL.append("AND birth_is_preg_hospital = '2' ");
            }
        }
        // 分娩医院
        if (StringUtils.isNotBlank(condition.getBirthChild())) {
            if ("1".equals(condition.getBirthChild())) {
                birthSQL.append("AND birth_is_this_hospital = '1' ");
            } else {
                birthSQL.append("AND birth_is_this_hospital = '2' ");
            }
        }
        // 身高
        if (StringUtils.isNotBlank(condition.getHeightFrom())) {
            birthSQL.append("AND birth_height >= " + condition.getHeightFrom() + " ");
        }
        if (StringUtils.isNotBlank(condition.getHeightTo())) {
            birthSQL.append("AND birth_height <= " + condition.getHeightTo() + " ");
        }
        // 年龄
        if (StringUtils.isNotBlank(condition.getAgeFrom())) {
            birthSQL.append("AND birth_age >= " + condition.getAgeFrom() + " ");
        }
        if (StringUtils.isNotBlank(condition.getAgeTo())) {
            birthSQL.append("AND birth_age <= " + condition.getAgeTo() + " ");
        }
        // bmi
        if (ArrayUtils.isNotEmpty(condition.getBmiScope())) {
            String[] bmiArray = condition.getBmiScope();
            StringBuffer bmiSQL = new StringBuffer("");
            for (int i = 0; i < bmiArray.length; i++) {
                bmiSQL.append("OR (cust_bmi BETWEEN " + bmiArray[i].split("-")[0] + " AND " + bmiArray[i].split("-")[1] + ") ");
            }
            birthSQL.append("AND (" + bmiSQL.toString().replaceFirst("OR", "") + ") ");
        }
        // 胎数
        if (ArrayUtils.isNotEmpty(condition.getBirthNum())) {
            String birthNumStr = "";
            if (ArrayUtils.contains(condition.getBirthNum(), "单胎")) {
                birthNumStr += "OR birth_tires_number = 1 ";
            }
            if (ArrayUtils.contains(condition.getBirthNum(), "双胎")) {
                birthNumStr += "OR birth_tires_number = 2 ";
            }
            if (ArrayUtils.contains(condition.getBirthNum(), "三胎及以上")) {
                birthNumStr += "OR birth_tires_number >= 3 ";
            }
            birthSQL.append("AND (" + birthNumStr.replaceFirst("OR", "") + ") ");
        }

        int birthEndingCount1 = 0;
        String[] birthEndingArray1 = new String[10];

        if (birthSQL.length() > 0) {
            birthEndingArray1[birthEndingCount1++] = "SELECT birth_id, cust_id, archives_id FROM cus_birthending WHERE "
                    + birthSQL.toString().replaceFirst("AND", "");
        } else {
            birthEndingArray1[birthEndingCount1++] = "SELECT birth_id, cust_id, archives_id FROM cus_birthending ";
        }

        // 分娩信息
        StringBuffer birthInfoSQL = new StringBuffer("");
        // 分娩时间
        birthInfoSQL.append("SELECT birth_id FROM cus_birthending_baseinfo WHERE base_time BETWEEN '"
                + condition.getStartDate()
                + "' AND '"
                + JodaTimeTools.toString(JodaTimeTools.after_day(JodaTimeTools.toDate(condition.getEndDate()), 1),
                        JodaTimeTools.FORMAT_6) + "' ");
        // 分娩时孕周
        if (StringUtils.isNotBlank(condition.getBirthPregWeekFrom())) {
            birthInfoSQL.append("AND base_weeks >= " + (Integer.valueOf(condition.getBirthPregWeekFrom()) - 1) + " ");
        }
        if (StringUtils.isNotBlank(condition.getBirthPregWeekTo())) {
            birthInfoSQL.append("AND base_weeks <= " + (Integer.valueOf(condition.getBirthPregWeekTo()) - 1) + " ");
        }
        // 分娩方式
        if (ArrayUtils.isNotEmpty(condition.getChildbirthType())) {
            birthInfoSQL.append("AND base_birth_type IN('" + StringUtils.join(condition.getChildbirthType(), "', '") + "') ");
        }
        // 分娩方位
        if (StringUtils.isNotBlank(condition.getBirthPlace())) {
            birthInfoSQL.append("AND base_birth_direction = '" + condition.getBirthPlace() + "' ");
        }
        // 麻醉类型
        if (ArrayUtils.isNotEmpty(condition.getMazuiType())) {
            birthInfoSQL.append("AND base_hocus_type IN('" + StringUtils.join(condition.getMazuiType(), "', '") + "') ");
        }
        // 危重产妇
        if (StringUtils.isNotBlank(condition.getIsDangerPregWoman())) {
            birthInfoSQL.append("AND base_iscritical = '" + condition.getIsDangerPregWoman() + "' ");
        }
        // 产前检查
        if (StringUtils.isNotBlank(condition.getIsInspectBeforeBirth())) {
            birthInfoSQL.append("AND base_complication_prenatal_cal = '" + condition.getIsInspectBeforeBirth() + "' ");
        }
        // 产妇并发症情况
        if (StringUtils.isNotBlank(condition.getComplicationIds())) {
            String[] ids = condition.getComplicationIds().split(",");
            // StringBuffer prenatalStr = new StringBuffer("");
            // StringBuffer birthingStr = new StringBuffer("");
            // StringBuffer afterBirthingStr = new StringBuffer("");
            // StringBuffer medicalStr = new StringBuffer("");
            StringBuffer compStr = new StringBuffer("");
            for (int i = 0; i < ids.length; i++) {
                // 产妇并发症情况--产前合并症
                compStr.append("OR LOCATE ('" + ids[i] + "', base_complication_prenatal) ");
                // 产妇并发症情况--产时并发症
                compStr.append("OR LOCATE ('" + ids[i] + "', base_complication_birthing) ");
                // 产妇并发症情况--产后并发症
                compStr.append("OR LOCATE ('" + ids[i] + "', base_complication_after_birthing) ");
                // 产妇并发症情况--内科并发症
                compStr.append("OR LOCATE ('" + ids[i] + "', base_complications_medical) ");
            }
            birthInfoSQL.append("AND (" + compStr.toString().replaceFirst("OR", "") + ") ");
        }
        // 产妇结局
        if (StringUtils.isNotBlank(condition.getIsLiveOrDead())) {
            birthInfoSQL.append("AND base_mater_ending_live_or_death = '" + condition.getIsLiveOrDead() + "' ");
        }
        // 产妇何时死亡
        if (StringUtils.isNotBlank(condition.getWhenDead())) {
            birthInfoSQL.append("AND base_birth_birthing_detail IN('" + condition.getWhenDead().replace(",", "', '")
                    + "') ");
        }

        if (birthInfoSQL.length() > 0) {
            birthEndingArray1[birthEndingCount1++] = birthInfoSQL.toString();
        }

        // 新生儿信息
        StringBuffer birthChildSQL = new StringBuffer("");
        // 新生儿性别
        if (ArrayUtils.isNotEmpty(condition.getNewBirthSex())) {
            birthChildSQL.append("AND baby_gender IN('" + StringUtils.join(condition.getNewBirthSex(), "', '") + "') ");
        }
        // 新生儿体重
        if (StringUtils.isNotBlank(condition.getWeightFrom())) {
            birthChildSQL.append("AND baby_weight >= " + condition.getWeightFrom() + " ");
        }
        if (StringUtils.isNotBlank(condition.getWeightTo())) {
            birthChildSQL.append("AND baby_weight <= " + condition.getWeightTo() + " ");
        }
        // 出生缺陷
        if ("1".equals(condition.getBirthDefect())) {
            birthChildSQL.append("AND !ISNULL(baby_defect) AND baby_defect != '' ");
        }
        // 抢救
        if (StringUtils.isNotBlank(condition.getIsRescue())) {
            birthChildSQL.append("AND baby_rescue = '" + condition.getIsRescue() + "' ");
        }
        // 新生儿并发症
        if (StringUtils.isNotBlank(condition.getChildComplicationIds())) {
            String[] ids = condition.getChildComplicationIds().split(",");
            StringBuffer childDiseaseStr = new StringBuffer("");
            for (int i = 0; i < ids.length; i++) {
                childDiseaseStr.append("OR LOCATE ('" + ids[i] + "', baby_complication) ");
            }
            birthChildSQL.append("AND (" + childDiseaseStr.toString().replaceFirst("OR", "") + ") ");
        }
        // 阿氏评分1分钟
        if (StringUtils.isNotBlank(condition.getAshiOneMinuteFrom())) {
            birthChildSQL.append("AND baby_Ashes_one_minute >= " + condition.getAshiOneMinuteFrom() + " ");
        }
        if (StringUtils.isNotBlank(condition.getAshiOneMinuteTo())) {
            birthChildSQL.append("AND baby_Ashes_one_minute <= " + condition.getAshiOneMinuteTo() + " ");
        }
        // 阿氏评分5分钟
        if (StringUtils.isNotBlank(condition.getAshiFiveMinuteFrom())) {
            birthChildSQL.append("AND baby_Ashes_five_minutes >= " + condition.getAshiFiveMinuteFrom() + " ");
        }
        if (StringUtils.isNotBlank(condition.getAshiFiveMinuteTo())) {
            birthChildSQL.append("AND baby_Ashes_five_minutes <= " + condition.getAshiFiveMinuteTo() + " ");
        }
        // 阿氏评分10分钟
        if (StringUtils.isNotBlank(condition.getAshiTenMinuteFrom())) {
            birthChildSQL.append("AND baby_Ashes_ten_minutes >= " + condition.getAshiTenMinuteFrom() + " ");
        }
        if (StringUtils.isNotBlank(condition.getAshiTenMinuteTo())) {
            birthChildSQL.append("AND baby_Ashes_ten_minutes <= " + condition.getAshiTenMinuteTo() + " ");
        }
        // 新生儿是否死亡
        if (StringUtils.isNotBlank(condition.getIsChildLiveOrDead())) {
            birthChildSQL.append("AND baby_is_death = '" + condition.getIsChildLiveOrDead() + "' ");
        }
        // 羊水量
        if (ArrayUtils.isNotEmpty(condition.getAfv())) {
            birthChildSQL.append("AND baby_amniotic_fluid_vol IN ('" + StringUtils.join(condition.getAfv(), "', '") + "') ");
        }
        // 羊水性状
        if (ArrayUtils.isNotEmpty(condition.getAfluid())) {
            birthChildSQL
                    .append("AND baby_amniotic_fluid_traits IN ('" + StringUtils.join(condition.getAfluid(), "', '") + "') ");
        }

        if (birthChildSQL.length() > 0) {
            birthEndingArray1[birthEndingCount1++] = "SELECT birth_id FROM cus_birthending_babyinfo WHERE "
                    + birthChildSQL.toString().replaceFirst("AND", "");
        }
        String[] archAliasZ = {"z1", "z2", "z3"};
        String[] custIdField = {"birth_id", "birth_id", "birth_id"};

        return getJoinSql("z1.birth_id, z1.cust_id, z1.archives_id", birthEndingCount1, birthEndingArray1, archAliasZ,
                custIdField);
    }

    /**
     * 妊娠期
     * 
     * @author zcq
     * @param condition
     * @return
     */
    private String getBirthEndingSQLForPreg(StatisticForm condition) {
        StringBuffer birthSQL = new StringBuffer("");
        // 产检医院
        if (StringUtils.isNotBlank(condition.getBirthSeize())) {
            birthSQL.append("SELECT birth_id, cust_id, archives_id FROM cus_birthending WHERE ");
            if ("1".equals(condition.getBirthSeize())) {
                birthSQL.append("birth_is_preg_hospital = '1' ");
            } else {
                birthSQL.append("birth_is_preg_hospital IS NULL ");
            }
        }
        return birthSQL.toString();
    }

    /**
     * 拼接joinSQL语句
     * 
     * @author zcq
     * @param fields
     * @param count
     * @param sqlArray
     * @param aliasName
     * @param fieldName
     * @return
     */
    private String getJoinSql(String fields, int count, String[] sqlArray, String[] aliasName, String[] fieldName) {
        StringBuffer result = new StringBuffer("");
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                if (i == 0) {
                    result.append("SELECT " + fields + " FROM (");
                    result.append(sqlArray[i]);
                    result.append(") " + aliasName[i] + " ");
                } else {
                    result.append("JOIN (");
                    result.append(sqlArray[i]);
                    result.append(") " + aliasName[i]
                            + " ON " + aliasName[i - 1] + "." + fieldName[i] + " = " + aliasName[i] + "."
                            + fieldName[i] + " ");
                }
            }
        }
        return result.toString();
    }

}

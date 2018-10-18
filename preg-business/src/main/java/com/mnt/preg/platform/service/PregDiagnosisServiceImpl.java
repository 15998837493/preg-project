
package com.mnt.preg.platform.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.core.utils.HQLSymbol;
import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.health.utils.times.JodaTimeTools;
import com.mnt.preg.customer.dao.CustomerDAO;
import com.mnt.preg.customer.pojo.CustomerPojo;
import com.mnt.preg.customer.pojo.PregArchivesPojo;
import com.mnt.preg.examitem.condition.ExamResultRecordCondition;
import com.mnt.preg.examitem.constant.ExamItemConstant;
import com.mnt.preg.examitem.dao.DiagnosisQuotaItemDAO;
import com.mnt.preg.examitem.dao.ExamItemDAO;
import com.mnt.preg.examitem.dao.ExamResultRecordDAO;
import com.mnt.preg.examitem.dao.PregInbodyDAO;
import com.mnt.preg.examitem.pojo.ExamResultRecordPojo;
import com.mnt.preg.examitem.util.ExamItemUtil;
import com.mnt.preg.examitem.util.ExamResultRecordUtil;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.master.condition.items.MasInterveneDiseaseInspectCondition;
import com.mnt.preg.master.dao.items.InspectItemDAO;
import com.mnt.preg.master.dao.items.InterveneDiseaseDAO;
import com.mnt.preg.master.dao.items.ItemDAO;
import com.mnt.preg.master.pojo.items.HospitalInspectPayPojo;
import com.mnt.preg.master.pojo.items.InspectItemPojo;
import com.mnt.preg.master.pojo.items.ItemPojo;
import com.mnt.preg.platform.condition.DiagnosisBookingCondition;
import com.mnt.preg.platform.condition.DiagnosisCondition;
import com.mnt.preg.platform.condition.DiagnosisItemsCondition;
import com.mnt.preg.platform.condition.DiagnosisObstetricalCondition;
import com.mnt.preg.platform.condition.DiagnosisPrescriptionCondition;
import com.mnt.preg.platform.condition.DiagnosisQuotaItemCondition;
import com.mnt.preg.platform.dao.PregArchivesDAO;
import com.mnt.preg.platform.dao.PregDiagnosisDAO;
import com.mnt.preg.platform.dao.PregDiagnosisExtenderDAO;
import com.mnt.preg.platform.dao.PregDiagnosisItemDAO;
import com.mnt.preg.platform.dao.PregPlanDAO;
import com.mnt.preg.platform.dao.UserInspectItemDAO;
import com.mnt.preg.platform.entity.DiagnosisBooking;
import com.mnt.preg.platform.entity.DiagnosisPrescription;
import com.mnt.preg.platform.entity.DiagnosisQuotaItem;
import com.mnt.preg.platform.entity.PregDiagnosis;
import com.mnt.preg.platform.entity.PregDiagnosisDisease;
import com.mnt.preg.platform.entity.PregDiagnosisExtender;
import com.mnt.preg.platform.entity.PregDiagnosisItems;
import com.mnt.preg.platform.entity.PregDiagnosisObstetrical;
import com.mnt.preg.platform.pojo.DiagnosisBookingPojo;
import com.mnt.preg.platform.pojo.DiagnosisHospitalInspectReportPojo;
import com.mnt.preg.platform.pojo.DiagnosisPrescriptionPojo;
import com.mnt.preg.platform.pojo.DiagnosisQuotaItemVo;
import com.mnt.preg.platform.pojo.PregDiagnosisAnalysisPojo;
import com.mnt.preg.platform.pojo.PregDiagnosisExtenderPojo;
import com.mnt.preg.platform.pojo.PregDiagnosisItemsVo;
import com.mnt.preg.platform.pojo.PregDiagnosisObstetricalPojo;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;
import com.mnt.preg.platform.pojo.UserInspectItemPojo;
import com.mnt.preg.system.dao.UserDao;

/**
 * 诊疗服务
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-21 zcq 初版
 */
@Service
public class PregDiagnosisServiceImpl extends BaseService implements PregDiagnosisService {

    @Resource
    private ExamResultRecordUtil examResultRecordUtil;

    @Resource
    private ExamItemUtil examItemUtil;

    @Resource
    private ExamItemDAO examItemDAO;

    @Resource
    private ExamResultRecordDAO examResultRecordDAO;

    @Resource
    private PregDiagnosisDAO pregDiagnosisDAO;

    @Resource
    private PregDiagnosisItemDAO pregDiagnosisItemDAO;

    @Resource
    private PregDiagnosisExtenderDAO pregDiagnosisExtenderDAO;

    @Resource
    private PregPlanDAO pregPlanDAO;

    @Resource
    private InspectItemDAO inspectItemDAO;

    @Resource
    private PregArchivesDAO pregArchivesDAO;

    @Resource
    private CustomerDAO customerDAO;// 患者DAO

    @Resource
    private DiagnosisQuotaItemDAO diagnosisQuotaItemDAO;

    @Resource
    private ItemDAO itemDAO;

    @Resource
    private PregInbodyDAO inbodyDAO;

    @Resource
    private InterveneDiseaseDAO interveneDiseaseDAO;

    @Resource
    private UserDao userDao;

    @Resource
    private UserInspectItemDAO userInspectItemDAO;

    @Resource
    private PregDiagnosisDiseaseService diagnosisDiseaseService;

    @Resource
    private DiagnosisHospitalItemService diagnosisHospitalitemService;

    // **********************************************诊疗登记开始*****************************************

    @Override
    @Transactional(readOnly = true)
    public PregDiagnosisPojo getLastDiagnosis(String custId) {
        return pregDiagnosisDAO.getLastDiagnosis(custId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PregDiagnosisPojo> queryDiagnosis(DiagnosisCondition condition) {
        return pregDiagnosisDAO.queryDiagnosis(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PregDiagnosisPojo> queryDiagnosisMore(DiagnosisCondition condition) {
        return pregDiagnosisDAO.queryDiagnosisMore(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PregDiagnosisPojo> queryDiagnosisMoreEvaluate(DiagnosisCondition condition) {
        return pregDiagnosisDAO.queryDiagnosisMoreEvaluate(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PregDiagnosisPojo> queryDiagnosisMoreEvaluateByOrder(DiagnosisCondition condition) {
        return pregDiagnosisDAO.queryDiagnosisMoreEvaluateByOrder(condition);
    }

    @Override
    public PregDiagnosisPojo getDiagnosis(String diagnosisId) {
        return pregDiagnosisDAO.getDiagnosis(diagnosisId);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer checkDiagnosisCustId(String custId, Date date) {
        return pregDiagnosisDAO.queryCountDiagnosis(custId, date);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public PregDiagnosisPojo completeDiagnosis(String diagnosisId) {
        PregDiagnosisPojo diagnosisVo = pregDiagnosisDAO.getDiagnosis(diagnosisId);
        PregDiagnosis userDiagnosis = TransformerUtils.transformerProperties(PregDiagnosis.class, diagnosisVo);
        userDiagnosis = this.completeDiagnosisMethod(userDiagnosis);
        this.updateDiagnosis(userDiagnosis);
        return pregDiagnosisDAO.getDiagnosis(diagnosisId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addDiagnosis(PregDiagnosis userDiagnosis) {
        // String custId = userDiagnosis.getDiagnosisCustomer();
        Date diagnosisDate = userDiagnosis.getDiagnosisDate();
        if (diagnosisDate == null) {
            diagnosisDate = new Date();
        }
        // if (pregDiagnosisDAO.queryCountDiagnosis(custId, diagnosisDate) > 0) {
        // throw new ServiceException(ResultCode.ERROR_10011);
        // }
        userDiagnosis = this.completeDiagnosisMethod(userDiagnosis);
        return (String) pregDiagnosisDAO.save(userDiagnosis);
    }

    @Override
    @Transactional(readOnly = true)
    public DiagnosisBookingPojo queryDiagnosisBooking(String bookingId) {
        return pregDiagnosisDAO.queryDiagnosisBookingById(bookingId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiagnosisBookingPojo> queryDiagnosisBookings(DiagnosisBookingCondition condition) {
        return pregDiagnosisDAO.queryDiagnosisBookings(condition);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void addDiagnosisTimer() {
        DiagnosisBookingCondition condition = new DiagnosisBookingCondition();
        condition.setBookingDate(getNowDate());
        List<DiagnosisBookingPojo> list = pregDiagnosisDAO.queryDiagnosisBookings(condition);// 查询所有时间为今天的预约信息
        if (CollectionUtils.isNotEmpty(list)) {// 如果有今天预约的信息
            for (DiagnosisBookingPojo bookingPojo : list) {// 循环预约信息
                // 将预约信息添加到接诊表
                PregDiagnosisPojo diagnosisPojo = pregDiagnosisDAO.getDiagnosis(bookingPojo.getDiagnosisId());// 根据接诊Id查接诊信息（只有一条）
                if (diagnosisPojo != null) {// 防止人为删除数据库造成报错
                    DiagnosisCondition diagnosisCondition = new DiagnosisCondition();// 以custId和预约日期来确定
                    diagnosisCondition.setDiagnosisCustomer(diagnosisPojo.getDiagnosisCustomer());
                    diagnosisCondition.setDiagnosisDate(bookingPojo.getBookingDate());
                    List<PregDiagnosisPojo> diagnosisList = pregDiagnosisDAO.queryDiagnosis(diagnosisCondition);
                    if (CollectionUtils.isEmpty(diagnosisList)) {// 接诊表里面没有，才可以添加预约信息
                        PregDiagnosis diagnosis = TransformerUtils.transformerProperties(PregDiagnosis.class,
                                diagnosisPojo);// 接诊信息
                        CustomerPojo customer = customerDAO.getCustomer(diagnosis.getDiagnosisCustomer());// 客户信息
                        PregArchivesPojo archive = pregArchivesDAO
                                .getArchiveById(diagnosis.getArchivesId() == null ? "" : diagnosis.getArchivesId());// 建档信息
                        diagnosis.setDiagnosisType(3);// 门诊类型：随诊
                        diagnosis.setDiagnosisDate(bookingPojo.getBookingDate());// 接诊日期
                        diagnosis.setDiagnosisCustPatientId(customer.getCustPatientId());// ID
                        diagnosis.setDiagnosisCustSikeId(customer.getCustSikeId());// 病案号
                        diagnosis.setDiagnosisCustName(customer.getCustName());// 姓名
                        diagnosis.setDiagnosisCustAge(customer.getCustAge());// 年龄
                        diagnosis.setDiagnosisCustHeight(customer.getCustHeight());// 身高
                        diagnosis.setDiagnosisCustWeight(null);// 体重
                        diagnosis.setDiagnosisCustWaistline(customer.getCustWaistline());// 腰围
                        diagnosis.setDiagnosisCustPlevel(customer.getCustPlevel());// 体力水平
                        diagnosis.setDiagnosisGestationalWeeks(reckonWeek(diagnosis.getDiagnosisCustomer(),
                                diagnosis.getDiagnosisDate(), diagnosis));// 孕周
                        diagnosis.setDiagnosisPlanPdf(null);// 干预方案pdf路径
                        diagnosis.setDiagnosisStatus(1);// 医生状态
                        diagnosis.setDiagnosisAssistantStatus(1);// 助理状态
                        diagnosis.setDiagnosisOrg("孕期营养门诊");// 来源
                        diagnosis.setDiagnosisReferralDoctor(null);// 转诊医生ID
                        diagnosis.setDiagnosisReferralDoctorName(null);// 转诊医生姓名
                        diagnosis.setDiagnosisInspectResult(null);// 检测项目结论
                        diagnosis.setDiagnosisExtenderDesc(null);// 营养处方备注
                        diagnosis.setDiagnosisDietRemark(null);// 膳食处方备注
                        diagnosis.setDiagnosisRemark(null);// 就诊备注
                        diagnosis.setDiagnosisRiseYunqi(null);
                        diagnosis.setDiagnosisRisePresent(null);
                        diagnosis.setDiagnosisRiseWeek(null);
                        diagnosis.setCreateTime(getNowDate());
                        diagnosis.setUpdateTime(getNowDate());
                        if (archive != null && archive.getPregnancyDueDate() != null
                                && compareToDate(archive.getPregnancyDueDate()) == -1) {// 预产期小于今天，建档ID为空
                            diagnosis.setArchivesId(null);
                        }
                        String diagnosisId = (String) pregDiagnosisDAO.save(diagnosis);

                        // 20180710 添加默认随诊评价项目
                        List<UserInspectItemPojo> userInspectList = userInspectItemDAO
                                .queryUserInspectItemByCreateUserId(diagnosis.getDiagnosisUser(), "vist");
                        if (CollectionUtils.isNotEmpty(userInspectList)) {
                            for (UserInspectItemPojo userInspect : userInspectList) {
                                PregDiagnosisItems diagnosisItem = new PregDiagnosisItems();
                                diagnosisItem.setInspectCode(userInspect.getInspectCode());
                                diagnosisItem.setInspectStatus(2);// 默认助理未评估
                                diagnosisItem.setDiagnosisId(diagnosisId);
                                addDiagnosisItem(diagnosisItem);
                            }
                        }
                        // 20180710 添加诊断项目
                        PregDiagnosisPojo lastDiagnosis = pregDiagnosisDAO.getLastDiagnosis(diagnosisPojo
                                .getDiagnosisCustomer());
                        if (lastDiagnosis != null) {
                            List<PregDiagnosisDisease> disList = diagnosisDiseaseService
                                    .queryDiagnosisDiseaseByDiagnosisId(lastDiagnosis.getDiagnosisId());
                            for (PregDiagnosisDisease disease : disList) {
                                disease.setDiagnosisId(diagnosisId);
                            }
                            diagnosisDiseaseService.saveDiagnosisDisease(disList);

                            // 同步最近一次的报告单到本次
                            List<DiagnosisHospitalInspectReportPojo> itemLately = diagnosisHospitalitemService
                                    .queryDiagnosisReports(lastDiagnosis.getDiagnosisId());
                            if (CollectionUtils.isNotEmpty(itemLately)) {
                                diagnosisHospitalitemService.addPregDiagnosisInspectReports(itemLately, diagnosisId);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addDiagnosisBookDate(DiagnosisBooking diagnosisBooking) {
        return (String) pregDiagnosisDAO.save(diagnosisBooking);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateDiagnosisBookDate(DiagnosisBooking diagnosisBooking) {
        String bookingId = diagnosisBooking.getBookingId();
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("bookingId", HQLSymbol.EQ.toString(), bookingId));
        pregDiagnosisDAO.updateHQL(diagnosisBooking, conditionParams);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateDiagnosis(PregDiagnosis userDiagnosis) {
        String diagnosisId = userDiagnosis.getDiagnosisId();
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("diagnosisId", HQLSymbol.EQ.toString(), diagnosisId));
        pregDiagnosisDAO.updateHQL(userDiagnosis, conditionParams);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateDiagnosisWeight(String diagnosisId) {
        pregDiagnosisDAO.updateDiagnosisWeight(diagnosisId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateDiagnosisObstetricalNull(PregDiagnosisObstetrical obstetrical) {
        pregDiagnosisDAO.updateDiagnosisObstetricalNull(obstetrical);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteBooking(String bookingId) {
        pregDiagnosisDAO.deleteBooking(bookingId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteDiagnosisRelation(String diagnosisId) {
        pregDiagnosisDAO.deleteDiagnosisRelation(diagnosisId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public PregDiagnosisPojo saveDiagnosisItemResult(String diagnosisId) {
        StringBuffer result = new StringBuffer();
        StringBuffer dietRemark = new StringBuffer();
        // 获取接诊信息
        PregDiagnosisPojo diagnosisPojo = pregDiagnosisDAO.getDiagnosis(diagnosisId);
        // 获取孕期建档结论
        PregArchivesPojo pregArchive = pregArchivesDAO.getLastPregnancyArchive(diagnosisPojo.getDiagnosisCustomer());
        if (pregArchive != null && StringUtils.isNotEmpty(pregArchive.getPregnancyResult())) {
            result.append("孕期建档：").append(pregArchive.getPregnancyResult()).append("；\n");
        }
        // 获取检测项目结论
        Map<String, ExamResultRecordPojo> map = this.getDiseaseInspectResult(diagnosisId);
        if (!CollectionUtils.sizeIsEmpty(map)) {
            for (String inspectItem : map.keySet()) {
                ExamResultRecordPojo inspectPojo = map.get(inspectItem);
                String inspectResult = inspectPojo.getDiagnosisResultNames();
                if (StringUtils.isNotEmpty(inspectResult)) {
                    // 膳食回顾
                    if ("A00001".equals(inspectItem)) {
                        result.append("膳食日记回顾：").append(inspectResult).append("\n");
                        dietRemark.append("膳食日记回顾：").append(inspectResult).append("\n");
                    }
                    // 膳食及生活方式分析结果
                    if ("B00002".equals(inspectItem)) {
                        String[] lifeArray = inspectResult.split("###");
                        if (StringUtils.isNotEmpty(lifeArray[0])) {
                            result.append("膳食营养状态分析：").append(lifeArray[0].replace("、", "")).append("\n");
                            dietRemark.append("膳食营养状态分析：").append(lifeArray[0].replace("、", "")).append("\n");
                        }
                        if (lifeArray.length >= 2 && StringUtils.isNotEmpty(lifeArray[1].trim())) {
                            result.append("生活方式分析：").append(lifeArray[1].replace("、", "")).append("\n");
                            dietRemark.append("生活方式分析：").append(lifeArray[1].replace("、", "")).append("\n");
                        }
                    }
                    // 人体成分分析结果
                    if ("B00003".equals(inspectItem)) {
                        result.append("人体成分：").append(inspectResult).append("\n");
                    }
                    // 膳食回顾
                    if ("B00005".equals(inspectItem)) {
                        result.append("24小时膳食回顾：").append(inspectResult).append("\n");
                    }
                    // 快速营养调查
                    if ("B00006".equals(inspectItem)) {
                        result.append("孕期快速营养调查：").append(inspectResult).append("\n");
                    }
                    // 生活方式
                    if ("B00007".equals(inspectItem)) {
                        result.append("孕期生活方式调查：").append(inspectResult).append("\n");
                    }
                    // 膳食频率调查
                    if ("B00008".equals(inspectItem)) {
                        result.append("孕期膳食频率调查：").append(inspectResult).append("\n");
                    }
                }
            }
        }
        // 营养病历--患者信息--本次营养评价结论
        // StringBuffer diagnosisCheck = new StringBuffer();
        // if (diagnosisPojo.getDiagnosisCustWeight() != null) {
        // if (StringUtils.isNotEmpty(String.valueOf(diagnosisPojo.getDiagnosisCustWeight()))) {// 数据库有可能存入空字符串，必须判断是否为空
        // diagnosisCheck.append("现体重：").append(diagnosisPojo.getDiagnosisCustWeight()).append(" kg ");
        // }
        // }
        // if (diagnosisPojo.getDiagnosisSystolic() != null) {
        // if (StringUtils.isNotEmpty(String.valueOf(diagnosisPojo.getDiagnosisSystolic()))) {// 数据库有可能存入空字符串，必须判断是否为空
        // diagnosisCheck.append("收缩压：").append(diagnosisPojo.getDiagnosisSystolic()).append(" mmHg ");
        // }
        // }
        // if (diagnosisPojo.getDiagnosisDiastolic() != null) {
        // if (StringUtils.isNotEmpty(String.valueOf(diagnosisPojo.getDiagnosisDiastolic()))) {// 数据库有可能存入空字符串，必须判断是否为空
        // diagnosisCheck.append("舒张压：").append(diagnosisPojo.getDiagnosisDiastolic()).append(" mmHg ");
        // }
        // }
        // if (diagnosisCheck.length() > 0) {
        // result.append("一般检查：").append(diagnosisCheck);
        // }

        // 修改接诊表--结论
        PregDiagnosis diagnosis = new PregDiagnosis();
        diagnosis.setDiagnosisId(diagnosisId);
        diagnosis.setDiagnosisInspectResult(result.toString());
        diagnosis.setDiagnosisDietRemark(dietRemark.toString());
        this.updateDiagnosis(diagnosis);

        return pregDiagnosisDAO.getDiagnosis(diagnosisId);
    }

    /**
     * 完善接诊信息
     * 
     * @author xdc
     * @param userDiagnosis
     * @return
     */
    private PregDiagnosis completeDiagnosisMethod(PregDiagnosis userDiagnosis) {
        String custId = userDiagnosis.getDiagnosisCustomer();
        CustomerPojo customerVo = customerDAO.getCustomer(custId);
        PregArchivesPojo preArchrive = pregArchivesDAO.getLastPregnancyArchive(custId);

        // mlq改为1,助理端接诊医生由页面传入（必填项）2,医师端正常取登陆人
        String userType = this.getLoginUser().getUserPojo().getUserType();
        if (!"assistant".equals(userType)) {
            userDiagnosis.setDiagnosisUser(this.getLoginUser().getUserPojo().getUserId());
            userDiagnosis.setDiagnosisUserName(this.getLoginUser().getUserPojo().getUserName());
        }

        // userDiagnosis.setDiagnosisUser(this.getLoginUser().getUserPojo().getUserId());
        // userDiagnosis.setDiagnosisUserName(this.getLoginUser().getUserPojo().getUserName());
        // String userType = this.getLoginUser().getUserPojo().getUserType();
        // if ("assistant".equals(userType)) {
        // List<UserPojo> assistantList = userDao
        // .queryDoctorOrAssistant(this.getLoginUser().getUserPojo().getUserId());
        // if (CollectionUtils.isNotEmpty(assistantList)) {
        // userDiagnosis.setDiagnosisUser(assistantList.get(0).getUserId());
        // userDiagnosis.setDiagnosisUserName(assistantList.get(0).getUserName());
        // } else {
        // throw new ServiceException(ResultCode.ERROR_80014);
        // }
        // }
        if (userDiagnosis.getDiagnosisDate() == null) {
            userDiagnosis.setDiagnosisDate(new Date());
        }
        userDiagnosis.setDiagnosisCustAge(customerVo.getCustAge());
        userDiagnosis.setDiagnosisCustHeight(customerVo.getCustHeight());
        userDiagnosis.setDiagnosisCustName(customerVo.getCustName());
        userDiagnosis.setDiagnosisCustPlevel(customerVo.getCustPlevel());
        userDiagnosis.setDiagnosisCustWaistline(customerVo.getCustWaistline());
        userDiagnosis.setDiagnosisCustPatientId(customerVo.getCustPatientId());
        userDiagnosis.setDiagnosisCustSikeId(customerVo.getCustSikeId());
        if (userDiagnosis.getDiagnosisStatus() == null) {
            userDiagnosis.setDiagnosisStatus(1);
        }
        if (userDiagnosis.getDiagnosisAssistantStatus() == null) {
            userDiagnosis.setDiagnosisAssistantStatus(1);
        }
        // 保存孕期信息
        if (preArchrive != null && preArchrive.getLmp() != null) {
            // 计算还有多少天到预产期--使用预产期计算孕周
            String dateString = JodaTimeTools.toString(userDiagnosis.getDiagnosisDate(), JodaTimeTools.FORMAT_6);
            Date date = JodaTimeTools.toDate(dateString);// 格式化的日期 ，不含时分秒
            int intervalDays = JodaTimeTools.getDays(date, preArchrive.getPregnancyDueDate());
            // 计算已经度过的天数--使用预产期计算孕周
            int pregnantDays = 280 - intervalDays;
            int pregnantWeeks = pregnantDays / 7;
            int plusDays = pregnantDays % 7;
            String pregnantStage = "";
            if (pregnantWeeks < 12) {
                pregnantStage = "pregnancy_pre";
            } else if (12 <= pregnantWeeks && pregnantWeeks < 27) {
                pregnantStage = "pregnancy_mid";
            } else if (pregnantWeeks >= 27) {
                pregnantStage = "pregnancy_late";
            }
            userDiagnosis.setDiagnosisPregnantStage(pregnantStage);
            userDiagnosis.setDiagnosisGestationalWeeks(pregnantWeeks + "+" + plusDays);
            userDiagnosis.setArchivesId(preArchrive.getId());
        }
        return userDiagnosis;
    }

    // *******************************************【产科信息】******************************************

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addDiagnosisObstetrical(PregDiagnosisObstetrical pregDiagnosisObstetrical) {
        return (String) pregDiagnosisDAO.save(pregDiagnosisObstetrical);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateDiagnosisObstetrical(PregDiagnosisObstetrical pregDiagnosisObstetrical) {
        String diagnosisId = pregDiagnosisObstetrical.getDiagnosisId();
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("diagnosisId", HQLSymbol.EQ.toString(), diagnosisId));
        pregDiagnosisDAO.updateHQL(pregDiagnosisObstetrical, conditionParams);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PregDiagnosisObstetricalPojo> queryDiagnosisObstetricals(DiagnosisObstetricalCondition condition) {
        return pregDiagnosisDAO.queryDiagnosisObstetricals(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public PregDiagnosisObstetricalPojo getObstetricalByDiagnosisId(String diagnosisId) {
        return pregDiagnosisDAO.getObstetricalByDiagnosisId(diagnosisId);
    }

    // *******************************************【医学营养评价项目】******************************************
    @Override
    @Transactional(readOnly = true)
    public List<PregDiagnosisItemsVo> queryDiagnosisItem(DiagnosisItemsCondition condition) {
        return pregDiagnosisItemDAO.queryDiagnosisItem(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public PregDiagnosisItemsVo getDiagnosisItemsByDiagnosisIdAndInspectItem(String diagnosisId, String inspectItem) {
        if (StringUtils.isEmpty(diagnosisId) || StringUtils.isEmpty(inspectItem)) {
            return null;
        }
        DiagnosisItemsCondition condition = new DiagnosisItemsCondition();
        condition.setDiagnosisId(diagnosisId);
        condition.setInspectCode(inspectItem);
        List<PregDiagnosisItemsVo> inspectItemsVos = pregDiagnosisItemDAO.queryDiagnosisItem(condition);
        if (CollectionUtils.isEmpty(inspectItemsVos)) {
            return null;
        }
        return inspectItemsVos.get(0);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addDiagnosisItem(PregDiagnosisItems diagnosisItem) {
        if (diagnosisItem == null) {
            return null;
        }
        return (String) pregDiagnosisItemDAO.save(diagnosisItem);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void saveDiagnosisItems(String diagnosisId, List<String> inspectCodeList) {
        List<String> currentItemList = pregDiagnosisItemDAO.queryCurrentDiagnosisItem(diagnosisId);
        // 添加新的检测项目
        if (CollectionUtils.isNotEmpty(inspectCodeList)) {
            for (String inspectCode : inspectCodeList) {
                if (!currentItemList.contains(inspectCode)) {
                    PregDiagnosisItems item = new PregDiagnosisItems();
                    item.setInspectCode(inspectCode);
                    item.setDiagnosisId(diagnosisId);
                    item.setInspectStatus(1);
                    pregDiagnosisItemDAO.save(item);
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public List<PregDiagnosisItemsVo> resetDiagnosisItem(String id) {
        // 查询所选检测项目信息
        PregDiagnosisItemsVo item = this.getDiagnosisItem(id);
        if (item == null) {
            return null;
        }
        String inspectCode = item.getInspectCode();
        String resultCode = item.getResultCode();
        if (StringUtils.isNotEmpty(resultCode)) {
            // 如果是“孕期人体成分评估”的话，还需要删除bca表中的数据
            if ("B00003".equals(inspectCode)) {
                inbodyDAO.deleteInbodyBcaById(resultCode);
            }
            // 删除计量评估记录表
            if ("B00004".equals(inspectCode)) {
                pregDiagnosisExtenderDAO.deleteDiagnosisExtender(item.getDiagnosisId());
            }
            // 删除指标结果
            ExamResultRecordPojo inspection = examResultRecordUtil.getExamResultRecordByExamCodeAndExamCategory(
                    resultCode, inspectCode);
            if (inspection != null) {
                examItemDAO.deleteExamItem(ExamItemConstant.inspectTableMap.get(inspectCode), inspection.getExamId());
            }
            // 删除结果记录
            examResultRecordUtil.deleteExamResultRecordByExamCodeAndExamCategory(resultCode, inspectCode);
        }
        // 修改检测项目状态
        PregDiagnosisItems diagnosisItem = new PregDiagnosisItems();
        diagnosisItem.setId(id);
        String userType = this.getLoginUser().getUserPojo().getUserType();
        diagnosisItem.setInspectStatus(1);
        if ("assistant".equals(userType)) {
            diagnosisItem.setInspectStatus(2);
        }
        diagnosisItem.setResultCode("");
        diagnosisItem.setInspectUser("");
        diagnosisItem.setInspectUserName("");
        pregDiagnosisItemDAO.updateHQL(diagnosisItem);
        // 查询检测项目
        DiagnosisItemsCondition condition = new DiagnosisItemsCondition();
        condition.setDiagnosisId(item.getDiagnosisId());
        return pregDiagnosisItemDAO.queryDiagnosisItem(condition);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateDiagnosisItem(PregDiagnosisItems diagnosisItem) {
        pregDiagnosisItemDAO.updateHQL(diagnosisItem);
    }

    @Override
    @Transactional(readOnly = true)
    public void updateDiagnosisItemInspectStatus(List<String> inspectCodeList, String inspectStatus, String diagnosisId) {
        pregDiagnosisItemDAO.updateDiagnosisItemInspectStatus(inspectCodeList, inspectStatus, diagnosisId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteDiagnosisInspect(List<String> inspectCodeList, String diagnosisId) {
        pregDiagnosisItemDAO.deleteDiagnosisInspectsByInspectCode(inspectCodeList, diagnosisId);
    }

    // --------------------------------------------【医学营养评价项目】-------------------------------------------

    // *******************************************【诊疗补充剂】******************************************

    @Override
    @Transactional(readOnly = true)
    public List<PregDiagnosisExtenderPojo> queryDiagnosisExtenderByDiagnosisId(String diagnosisId) {
        return pregDiagnosisExtenderDAO.queryDiagnosisExtenderByDiagnosisId(diagnosisId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void saveDiagnosisExtender(String diagnosisId, List<PregDiagnosisExtender> diagnosisExtenderList) {
        // 删除旧数据
        pregDiagnosisExtenderDAO.deleteDiagnosisExtender(diagnosisId);
        // 添加新数据
        if (CollectionUtils.isNotEmpty(diagnosisExtenderList)) {
            for (PregDiagnosisExtender extender : diagnosisExtenderList) {
                pregPlanDAO.save(extender);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteDiagnosisExtender(String diagnosisId) {
        // 删除旧数据
        pregDiagnosisExtenderDAO.deleteDiagnosisExtender(diagnosisId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiagnosisPrescriptionPojo> queryDiagnosisPrescriptionByDiagnosisId(String diagnosisId) {
        return pregDiagnosisExtenderDAO.queryDiagnosisPrescriptionByDiagnosisId(diagnosisId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiagnosisPrescriptionPojo> queryDiagnosisPrescriptionByCondition(
            DiagnosisPrescriptionCondition condition) {
        return pregDiagnosisExtenderDAO.queryDiagnosisPrescriptionByCondition(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public DiagnosisPrescriptionPojo getMaxProductListByCondition(
            DiagnosisPrescriptionCondition condition) {
        return pregDiagnosisExtenderDAO.getMaxProductListByCondition(condition);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void saveDiagnosisPrescription(String diagnosisId, List<DiagnosisPrescriptionPojo> diagnosisExtenderList) {
        // 删除旧数据
        pregDiagnosisExtenderDAO.deleteDiagnosisPrescription(diagnosisId);
        // 添加新数据
        if (CollectionUtils.isNotEmpty(diagnosisExtenderList)) {
            for (DiagnosisPrescriptionPojo extender : diagnosisExtenderList) {
                extender.setDiagnosisId(diagnosisId);
                pregPlanDAO.save(TransformerUtils.transformerProperties(DiagnosisPrescription.class, extender));
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiagnosisPrescriptionPojo> queryLastDiagnosisPrescription(String custId) {
        return pregDiagnosisExtenderDAO.queryLastDiagnosisPrescription(custId);
    }

    // --------------------------------------------【诊疗补充剂】-------------------------------------------

    // *******************************************【接诊检测--组合数据】***************************************

    @Override
    @Transactional(readOnly = true)
    public Map<String, ExamResultRecordPojo> getDiseaseInspectResult(String diagnosisId) {
        Map<String, ExamResultRecordPojo> map = new HashMap<String, ExamResultRecordPojo>();

        DiagnosisItemsCondition itemCondition = new DiagnosisItemsCondition();
        itemCondition.setDiagnosisId(diagnosisId);
        itemCondition.setInspectStatus(3);// 已完成
        List<PregDiagnosisItemsVo> diagnosisItemList = pregDiagnosisItemDAO.queryDiagnosisItem(itemCondition);
        String userType = this.getLoginUser().getUserPojo().getUserType();
        if ("assistant".equals(userType)) {
            itemCondition.setInspectStatus(4);// 助理已完成
            diagnosisItemList.addAll(pregDiagnosisItemDAO.queryDiagnosisItem(itemCondition));
        }

        List<String> examCodeList = new ArrayList<String>();
        if (CollectionUtils.isNotEmpty(diagnosisItemList)) {
            for (PregDiagnosisItemsVo item : diagnosisItemList) {
                if (StringUtils.isNotEmpty(item.getResultCode())) {
                    examCodeList.add(item.getResultCode());
                }
            }
        }
        ExamResultRecordCondition rCondition = new ExamResultRecordCondition();
        rCondition.setExamCodeList(examCodeList);
        List<ExamResultRecordPojo> resultList = examResultRecordUtil.queryExamResultRecord(rCondition);
        if (CollectionUtils.isNotEmpty(resultList)) {
            for (ExamResultRecordPojo result : resultList) {
                String inspectCode = result.getExamCategory();

                // 膳食回顾
                if ("A00001".equals(inspectCode)) {
                    map.put("A00001", result);
                }
                // 膳食及生活方式分析结果
                if ("B00002".equals(inspectCode)) {
                    map.put("B00002", result);
                }
                // 人体成分分析结果
                if ("B00003".equals(inspectCode)) {
                    map.put("B00003", result);
                }
                // 补充剂结论
                if ("B00004".equals(inspectCode)) {
                    map.put("B00004", result);
                }
                // 膳食回顾
                if ("B00005".equals(inspectCode)) {
                    map.put("B00005", result);
                }
                // 快速营养调查
                if ("B00006".equals(inspectCode)) {
                    map.put("B00006", result);
                }
                // 生活方式
                if ("B00007".equals(inspectCode)) {
                    map.put("B00007", result);
                }
                // 膳食频率调查
                if ("B00008".equals(inspectCode)) {
                    map.put("B00008", result);
                }
            }
        }
        return map;
    }

    @Override
    @Transactional(readOnly = true)
    public PregDiagnosisAnalysisPojo getDiagnosisAnalysis(String diagnosisId) {
        PregDiagnosisAnalysisPojo analysisVo = new PregDiagnosisAnalysisPojo();
        // 接诊信息
        PregDiagnosisPojo diagnosisVo = pregDiagnosisDAO.getDiagnosis(diagnosisId);
        // 诊断结果分析
        Map<String, ExamResultRecordPojo> resultMap = this.getDiseaseInspectResult(diagnosisId);
        diagnosisVo.setInbodyResult(resultMap.get("B00003"));
        diagnosisVo.setDietLifeResult(resultMap.get("B00002"));
        analysisVo.setDiagnosisVo(diagnosisVo);
        // 检测项目信息
        DiagnosisItemsCondition condition = new DiagnosisItemsCondition();
        condition.setDiagnosisId(diagnosisId);
        analysisVo.setDiagnosisItemList(pregDiagnosisItemDAO.queryDiagnosisItem(condition));
        // 客户信息
        analysisVo.setCustomerVo(customerDAO.getCustomer(diagnosisVo.getDiagnosisCustomer()));
        // 孕期建档信息
        analysisVo.setPregArchivesVo(pregArchivesDAO.getLastPregnancyArchive(diagnosisVo.getDiagnosisCustomer()));

        return analysisVo;
    }

    // -------------------------------------------【接诊检测--组合数据】---------------------------------------
    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String saveDiagnosisQuotaItem(List<DiagnosisQuotaItem> list, String diagnosisId) {
        /*
         * 判断患者是否一存在项目
         * 如果存在，继续执行下一条数据
         * 如果不存在，根据项目id查出项目名称，存入表user_diagnosis_quota_item
         */
        for (DiagnosisQuotaItem diagnosisQuotaItem : list) {
            DiagnosisQuotaItemCondition condition = TransformerUtils.transformerProperties(
                    DiagnosisQuotaItemCondition.class, diagnosisQuotaItem);
            List<DiagnosisQuotaItemVo> list2 = diagnosisQuotaItemDAO.queryDiagnosisQuotaItem(condition);
            if (list2.size() > 0)
                continue;
            InspectItemPojo inspectItemVo = inspectItemDAO.getInspectItemByInspectId(diagnosisQuotaItem
                    .getInspectItemId());
            diagnosisQuotaItem.setInspectItemName(inspectItemVo.getInspectName());
            diagnosisQuotaItemDAO.save(diagnosisQuotaItem);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteDiagnosisQuotaItem(String id) {
        diagnosisQuotaItemDAO.deleteDiagnosisQuotaItem(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiagnosisQuotaItemVo> queryDiagnosisQuotaItem(DiagnosisQuotaItemCondition condition) {
        return diagnosisQuotaItemDAO.queryDiagnosisQuotaItem(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public PregDiagnosisItemsVo getDiagnosisItem(String id) {
        DiagnosisItemsCondition condition = new DiagnosisItemsCondition();
        condition.setId(id);
        List<PregDiagnosisItemsVo> list = pregDiagnosisItemDAO.queryDiagnosisItem(condition);
        return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
    }

    @Override
    @Transactional(readOnly = true)
    public PregDiagnosisItemsVo getDiagnosisItemsByInspectCodeAndInspectItem(String inspectCode, String inspectItem) {
        if (StringUtils.isEmpty(inspectCode) || StringUtils.isEmpty(inspectItem)) {
            return null;
        }
        DiagnosisItemsCondition condition = new DiagnosisItemsCondition();
        condition.setResultCode(inspectCode);
        condition.setInspectCode(inspectItem);
        List<PregDiagnosisItemsVo> list = pregDiagnosisItemDAO.queryDiagnosisItem(condition);
        return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
    }

    /*********************************************** 辅助检查 ********************************************************/
    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addDiagnosisQuotaItem(DiagnosisQuotaItem diagnosisQuotaItem) {
        return (String) pregDiagnosisItemDAO.save(diagnosisQuotaItem);
    }

    @Override
    @Transactional(readOnly = true)
    public DiagnosisQuotaItem getDiagnosisQuotaItemById(String inspectItemId) {
        return pregDiagnosisItemDAO.get(DiagnosisQuotaItem.class, inspectItemId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void removeQuotaItem(String id) {
        DiagnosisQuotaItem diagnosisQuotaItem = pregDiagnosisItemDAO.get(DiagnosisQuotaItem.class, id);
        pregDiagnosisItemDAO.delete(diagnosisQuotaItem);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HospitalInspectPayPojo> queryInspectConfig(String diseaseId) {
        MasInterveneDiseaseInspectCondition condition = new MasInterveneDiseaseInspectCondition();
        condition.setDiseaseId(diseaseId);
        return interveneDiseaseDAO.queryInspectConfig(condition);
    }

    @Override
    public List<ItemPojo> queryQuotaItemByDiagnosisId(String diagnosisId) {
        return pregDiagnosisItemDAO.queryQuotaItemByDiagnosisId(diagnosisId);
    }

    /**
     * 获取当前时间 yyyy-MM-dd 00:00:00
     * 
     * @author dhs
     * @return
     */
    private static Date getNowDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String s = formatter.format(now);
        try {
            Date date = formatter.parse(s);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过预约日期计算孕周
     * 
     * @author dhs
     * @param custId
     * @param bookingDate
     * @return
     */
    private String reckonWeek(String custId, Date bookingDate, PregDiagnosis diagnosis) {
        PregArchivesPojo preArchrive = pregArchivesDAO.getLastPregnancyArchive(custId);
        if (preArchrive != null && preArchrive.getLmp() != null) {
            // 计算还有多少天到预产期--使用预产期计算孕周
            String dateString = JodaTimeTools.toString(bookingDate, JodaTimeTools.FORMAT_6);
            Date date = JodaTimeTools.toDate(dateString);// 格式化的日期 ，不含时分秒
            int intervalDays = JodaTimeTools.getDays(date, preArchrive.getPregnancyDueDate());
            // 计算已经度过的天数--使用预产期计算孕周
            int pregnantDays = 280 - intervalDays;
            int pregnantWeeks = pregnantDays / 7;
            int plusDays = pregnantDays % 7;
            String pregnantStage = "";
            if (pregnantWeeks < 12) {
                pregnantStage = "pregnancy_pre";
            } else if (12 <= pregnantWeeks && pregnantWeeks < 27) {
                pregnantStage = "pregnancy_mid";
            } else if (pregnantWeeks >= 27) {
                pregnantStage = "pregnancy_late";
            }
            diagnosis.setDiagnosisPregnantStage(pregnantStage);
            return pregnantWeeks + "+" + plusDays;
        } else {
            diagnosis.setDiagnosisPregnantStage(null);
            return null;
        }
    }

    /**
     * yyyy-MM-dd 格式时间与当前时间比较
     * 
     * @author dhs
     * @param Date
     * @return
     */
    private Integer compareToDate(Date dueDate) {
        String[] strDate = dueDate.toString().split("-");
        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal2.set(Integer.parseInt(strDate[0]), Integer.parseInt(strDate[1]) - 1, Integer.parseInt(strDate[2]));
        return cal2.getTime().compareTo(cal.getTime());
    }

}

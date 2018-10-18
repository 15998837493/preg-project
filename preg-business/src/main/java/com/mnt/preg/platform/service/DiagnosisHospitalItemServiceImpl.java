
package com.mnt.preg.platform.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.core.utils.HQLSymbol;
import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.health.utils.thread.TokenManager;
import com.mnt.health.utils.times.JodaTimeTools;
import com.mnt.preg.main.enums.Flag;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.platform.condition.DiagnosisHospitalInspectReportCondition;
import com.mnt.preg.platform.condition.DiagnosisHospitalItemCondition;
import com.mnt.preg.platform.dao.DiagnosisHospitalItemDAO;
import com.mnt.preg.platform.dao.PregDiagnosisDAO;
import com.mnt.preg.platform.entity.DiagnosisHospitalInspectPay;
import com.mnt.preg.platform.entity.DiagnosisHospitalInspectReport;
import com.mnt.preg.platform.entity.DiagnosisHospitalItem;
import com.mnt.preg.platform.pojo.DiagnosisHospitalInspectPayPojo;
import com.mnt.preg.platform.pojo.DiagnosisHospitalInspectReportPojo;
import com.mnt.preg.platform.pojo.DiagnosisHospitalItemPojo;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;

/**
 * 接诊实验室检验项目管理
 * 
 * @author mlq
 * 
 *         变更履历：
 *         2018-06-21 mlq
 */
@Service
public class DiagnosisHospitalItemServiceImpl extends BaseService implements DiagnosisHospitalItemService {

    @Resource
    private DiagnosisHospitalItemDAO diagnosisHospitalItemDAO;

    @Resource
    private PregDiagnosisDAO pregDiagnosisDAO;

    @Override
    @Transactional(readOnly = true)
    public List<DiagnosisHospitalInspectReportPojo> queryDiagnosisReports(String diagnosisId) {
        PregDiagnosisPojo diagnosisPojo = pregDiagnosisDAO.getDiagnosis(diagnosisId);
        // 报告单查询
        List<DiagnosisHospitalInspectReportPojo> reportList = diagnosisHospitalItemDAO
                .queryDiagnosisReports(diagnosisId);
        if (CollectionUtils.isNotEmpty(reportList)) {
            for (DiagnosisHospitalInspectReportPojo reportPojo : reportList) {
                // 收费项目拼接
                List<DiagnosisHospitalInspectPayPojo> inspectPayList = diagnosisHospitalItemDAO
                        .queryDiagnosisInspectPays(reportPojo
                                .getReportId());
                if (CollectionUtils.isNotEmpty(inspectPayList)) {
                    reportPojo.setInspectPayList(inspectPayList);
                }

                // 检验项目拼接
                List<DiagnosisHospitalItemPojo> itemList = diagnosisHospitalItemDAO.queryDiagnosisItems(reportPojo
                        .getReportId());
                if (CollectionUtils.isNotEmpty(itemList)) {
                    // 查询上次检验项目结果和孕周
                    for (DiagnosisHospitalItemPojo diagnosisHospitalItemPojo : itemList) {
                        DiagnosisHospitalItemCondition condition = new DiagnosisHospitalItemCondition();
                        condition.setCustId(diagnosisHospitalItemPojo.getCustId());
                        condition.setItemId(diagnosisHospitalItemPojo.getItemId());
                        condition.setDiagnosisDate(JodaTimeTools.toString(diagnosisPojo.getDiagnosisDate(),
                                JodaTimeTools.FORMAT_6));
                        List<DiagnosisHospitalItemPojo> lastItemList = diagnosisHospitalItemDAO
                                .queryDiagnosisHospitalItemByCondition(condition);

                        if (CollectionUtils.isNotEmpty(lastItemList)) {
                            diagnosisHospitalItemPojo.setGestationalWeeks(lastItemList.get(0).getGestationalWeeks());
                            diagnosisHospitalItemPojo.setLastItemValue(lastItemList.get(0).getItemValue());
                            diagnosisHospitalItemPojo.setLastItemResult(lastItemList.get(0).getItemResult());
                        }
                    }
                    reportPojo.setItemList(itemList);
                }
            }
        }
        return reportList;
    }

    @Override
    @Transactional(readOnly = true)
    public DiagnosisHospitalInspectReportPojo queryDiagnosisReportById(String reportId) {
        DiagnosisHospitalInspectReportPojo reportPojo = new DiagnosisHospitalInspectReportPojo();

        // 收费项目拼接
        List<DiagnosisHospitalInspectPayPojo> inspectPayList = diagnosisHospitalItemDAO
                .queryDiagnosisInspectPays(reportId);
        if (CollectionUtils.isNotEmpty(inspectPayList)) {
            reportPojo.setInspectPayList(inspectPayList);
        }

        // 检验项目拼接
        List<DiagnosisHospitalItemPojo> itemList = diagnosisHospitalItemDAO.queryDiagnosisItems(reportId);
        if (CollectionUtils.isNotEmpty(itemList)) {
            // 查询上次检验项目结果和孕周
            for (DiagnosisHospitalItemPojo diagnosisHospitalItemPojo : itemList) {
                DiagnosisHospitalItemCondition condition = new DiagnosisHospitalItemCondition();
                condition.setCustId(diagnosisHospitalItemPojo.getCustId());
                condition.setItemId(diagnosisHospitalItemPojo.getItemId());
                List<DiagnosisHospitalItemPojo> lastItemList = diagnosisHospitalItemDAO
                        .queryDiagnosisHospitalItemByCondition(condition);

                if (CollectionUtils.isNotEmpty(lastItemList)) {
                    diagnosisHospitalItemPojo.setGestationalWeeks(lastItemList.get(0).getGestationalWeeks());
                    diagnosisHospitalItemPojo.setLastItemValue(lastItemList.get(0).getItemValue());
                    diagnosisHospitalItemPojo.setLastItemResult(lastItemList.get(0).getItemResult());
                }
            }
            reportPojo.setItemList(itemList);
        }
        return reportPojo;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void addPregDiagnosisInspectReports(
            List<DiagnosisHospitalInspectReportPojo> inspectReportList, String diagnosisId) {

        // 确定报告状态
        int reportSatus = 2;
        String token = TokenManager.getCurrHashMap().get(Thread.currentThread());
        if (token != null && this.getLoginUser() != null) {
            String userType = this.getLoginUser().getUserPojo().getUserType();
            if (!"assistant".equals(userType)) {
                reportSatus = 1;
            }
        }
        if (CollectionUtils.isNotEmpty(inspectReportList)) {
            // 保存报告单
            DiagnosisHospitalInspectReport report = null;
            for (DiagnosisHospitalInspectReportPojo reportPojo : inspectReportList) {
                report = TransformerUtils.transformerProperties(DiagnosisHospitalInspectReport.class, reportPojo);
                report.setGestationalWeeks(null);
                report.setReportDate(null);
                report.setDiagnosisId(diagnosisId);
                report.setReportStatus(reportSatus);
                report.setFlag(Flag.normal.getValue());
                String reportId = (String) diagnosisHospitalItemDAO.save(report);
                // 保存收费项目
                if (CollectionUtils.isNotEmpty(reportPojo.getInspectPayList())) {
                    DiagnosisHospitalInspectPay inspectPay = null;
                    for (DiagnosisHospitalInspectPayPojo inspectPayPojo : reportPojo.getInspectPayList()) {
                        inspectPay = TransformerUtils.transformerProperties(DiagnosisHospitalInspectPay.class,
                                inspectPayPojo);
                        inspectPay.setReportId(reportId);
                        inspectPay.setFlag(Flag.normal.getValue());
                        diagnosisHospitalItemDAO.save(inspectPay);
                    }
                }
                // 保存检验项目
                if (CollectionUtils.isNotEmpty(reportPojo.getItemList())) {
                    DiagnosisHospitalItem item = null;
                    for (DiagnosisHospitalItemPojo itemPojo : reportPojo.getItemList()) {
                        item = TransformerUtils.transformerProperties(DiagnosisHospitalItem.class,
                                itemPojo);
                        item.setReportId(reportId);
                        item.setItemValue(null);
                        item.setItemResult(null);
                        item.setFlag(Flag.normal.getValue());
                        diagnosisHospitalItemDAO.save(item);
                    }
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateDiagnosisHospitalItemReport(DiagnosisHospitalInspectReport report) {
        String id = report.getReportId();
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("id", HQLSymbol.EQ.toString(), id));
        diagnosisHospitalItemDAO.updateHQL(report, conditionParams);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteDiagnosisHospitalInspectReport(String reportId) {
        diagnosisHospitalItemDAO.deleteDiagnosisHospitalInspectReport(reportId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public List<DiagnosisHospitalInspectPay> addPregDiagnosisInspectPays(
            List<DiagnosisHospitalInspectPay> inspectPayList, String reportId) {

        List<DiagnosisHospitalInspectPay> newPayList = new ArrayList<DiagnosisHospitalInspectPay>();
        if (CollectionUtils.isNotEmpty(inspectPayList)) {
            // 保存新数据
            for (DiagnosisHospitalInspectPay inspectPay : inspectPayList) {
                inspectPay.setReportId(reportId);
                inspectPay.setFlag(Flag.normal.getValue());
                String id = (String) diagnosisHospitalItemDAO.save(inspectPay);
                inspectPay.setId(id);
                newPayList.add(inspectPay);
            }
        }
        return newPayList;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteDiagnosisHospitalInspectPay(String reportId, String inspectId) {
        diagnosisHospitalItemDAO.deleteDiagnosisHospitalInspectPay(reportId, inspectId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public List<DiagnosisHospitalItem> addPregDiagnosisItems(List<DiagnosisHospitalItem> itemList, String reportId) {
        List<DiagnosisHospitalItem> newItemList = new ArrayList<DiagnosisHospitalItem>();
        if (CollectionUtils.isNotEmpty(itemList)) {
            // 保存新数据
            for (DiagnosisHospitalItem item : itemList) {
                item.setReportId(reportId);
                item.setFlag(Flag.normal.getValue());
                String id = (String) diagnosisHospitalItemDAO.save(item);
                item.setId(id);
                newItemList.add(item);
            }
        }
        return newItemList;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateDiagnosisHospitalItem(DiagnosisHospitalItem item) {
        String id = item.getId();
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("id", HQLSymbol.EQ.toString(), id));
        diagnosisHospitalItemDAO.updateHQL(item, conditionParams);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateDiagnosisHospitalItemReportByDiagnosisId(String diagnosisId, int status) {
        diagnosisHospitalItemDAO.updateDiagnosisHospitalItemReportByDiagnosisId(diagnosisId, status);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteDiagnosisHospitalItemById(String id) {
        diagnosisHospitalItemDAO.deleteDiagnosisHospitalItemById(id);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteDiagnosisHospitalItem(String reportId, String inspectId, String itemId) {
        diagnosisHospitalItemDAO.deleteDiagnosisHospitalItem(reportId, inspectId, itemId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiagnosisHospitalItemPojo> queryDiagnosisItemsByDiagnosisId(String diagnosisId) {
        return diagnosisHospitalItemDAO.queryDiagnosisItemsByDiagnosisId(diagnosisId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiagnosisHospitalItemPojo> queryDiagnosisItems(String reportId) {
        // 检验项目拼接
        List<DiagnosisHospitalItemPojo> itemList = diagnosisHospitalItemDAO.queryDiagnosisItems(reportId);
        if (CollectionUtils.isNotEmpty(itemList)) {
            // 查询上次检验项目结果和孕周
            for (DiagnosisHospitalItemPojo diagnosisHospitalItemPojo : itemList) {
                DiagnosisHospitalItemCondition condition = new DiagnosisHospitalItemCondition();
                condition.setCustId(diagnosisHospitalItemPojo.getCustId());
                condition.setItemId(diagnosisHospitalItemPojo.getItemId());
                List<DiagnosisHospitalItemPojo> lastItemList = diagnosisHospitalItemDAO
                        .queryDiagnosisHospitalItemByCondition(condition);

                if (CollectionUtils.isNotEmpty(lastItemList)) {
                    diagnosisHospitalItemPojo.setGestationalWeeks(lastItemList.get(0).getGestationalWeeks());
                    diagnosisHospitalItemPojo.setLastItemValue(lastItemList.get(0).getItemValue());
                    diagnosisHospitalItemPojo.setLastItemResult(lastItemList.get(0).getItemResult());
                }
            }
        }
        return itemList;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteDiagnosisHospitalItems(String custId, String diagnosisId, String ids) {
        diagnosisHospitalItemDAO.deleteDiagnosisHospitalItems(custId, diagnosisId, ids);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteDiagnosisHospitalEmptyReport(String diagnosisId) {
        diagnosisHospitalItemDAO.deleteDiagnosisHospitalEmptyReport(diagnosisId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteDiagnosisHospitalEmptyPay(String custId, String diagnosisId) {
        diagnosisHospitalItemDAO.deleteDiagnosisHospitalEmptyPay(custId, diagnosisId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public DiagnosisHospitalInspectReportPojo addReport(DiagnosisHospitalInspectReport report) {
        String id = (String) diagnosisHospitalItemDAO.save(report);
        return diagnosisHospitalItemDAO.getTransformerBean(id, DiagnosisHospitalInspectReport.class,
                DiagnosisHospitalInspectReportPojo.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiagnosisHospitalInspectPayPojo> queryLastDiagnosisInspects(String diagnosisId) {
        return diagnosisHospitalItemDAO.queryLastDiagnosisInspects(diagnosisId);
    }

    // ===================================================【历史检验数据查询】=======================================================

    @Override
    @Transactional(readOnly = true)
    public List<DiagnosisHospitalItemPojo> queryDiagnosisHospitalItemByCondition(
            DiagnosisHospitalItemCondition condition) {
        return diagnosisHospitalItemDAO.queryDiagnosisHospitalItemByCondition(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiagnosisHospitalInspectReportPojo> queryDiagnosisHospitalInspectReportByCondition(
            DiagnosisHospitalInspectReportCondition condition) {
        return diagnosisHospitalItemDAO.queryDiagnosisHospitalInspectReportByCondition(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiagnosisHospitalItemPojo> queryStatisticHospitalItemByCondition(
            DiagnosisHospitalItemCondition condition) {
        return diagnosisHospitalItemDAO.queryStatisticHospitalItemByCondition(condition);
    }

}

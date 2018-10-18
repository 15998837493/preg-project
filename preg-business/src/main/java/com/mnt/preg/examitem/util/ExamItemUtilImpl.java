
package com.mnt.preg.examitem.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.core.exception.ServiceException;
import com.mnt.preg.examitem.condition.ExamItemCondition;
import com.mnt.preg.examitem.dao.ExamItemDAO;
import com.mnt.preg.examitem.pojo.ExamItemPojo;
import com.mnt.preg.main.results.ResultCode;
import com.mnt.preg.main.service.BaseService;

/**
 * 检测项目指标管理
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-5-17 zcq 初版
 */
@Service
public class ExamItemUtilImpl extends BaseService implements ExamItemUtil {

    @Resource
    private ExamItemDAO examItemDAO;

    @Override
    @Transactional(readOnly = true)
    public List<ExamItemPojo> queryExamItem(ExamItemCondition condition) {
        return examItemDAO.queryExamItem(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, ExamItemPojo> queryExamItemMapByResultCode(String tableName, String resultCode) {
        if (StringUtils.isEmpty(tableName) || StringUtils.isEmpty(resultCode)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        ExamItemCondition examCondition = new ExamItemCondition();
        examCondition.setTableName(tableName);
        examCondition.setResultCode(resultCode);
        List<ExamItemPojo> examList = examItemDAO.queryExamItem(examCondition);

        Map<String, ExamItemPojo> resultMap = new HashMap<String, ExamItemPojo>();
        if (CollectionUtils.isNotEmpty(examList)) {
            for (ExamItemPojo detail : examList) {
                String itemCode = detail.getItemCode();
                if (!resultMap.containsKey(itemCode)) {
                    resultMap.put(itemCode, detail);
                }
            }
        }
        return resultMap;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, ExamItemPojo> queryExamItemMapByExamId(String tableName, String examId) {
        if (StringUtils.isEmpty(tableName) || StringUtils.isEmpty(examId)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        ExamItemCondition examCondition = new ExamItemCondition();
        examCondition.setTableName(tableName);
        examCondition.setExamId(examId);
        List<ExamItemPojo> examList = examItemDAO.queryExamItem(examCondition);

        Map<String, ExamItemPojo> resultMap = new HashMap<String, ExamItemPojo>();
        if (CollectionUtils.isNotEmpty(examList)) {
            for (ExamItemPojo detail : examList) {
                String itemCode = detail.getItemCode();
                if (!resultMap.containsKey(itemCode)) {
                    resultMap.put(itemCode, detail);
                }
            }
        }
        return resultMap;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamItemPojo> queryExamItemResult(ExamItemCondition condition) {
        if (StringUtils.isEmpty(condition.getResultCode()) || StringUtils.isEmpty(condition.getTableName())) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        return examItemDAO.queryExamItemResultByCondition(condition);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void addExamItems(String tableName, String examId, List<ExamItemPojo> detailList) {
        if (CollectionUtils.isNotEmpty(detailList)) {
            String insId = "";
            try {
                // 北京电力医院用
                insId = this.getInsId();
            } catch (Exception e) {
                insId = "C000000";
            }
            // 保存新数据
            for (ExamItemPojo detail : detailList) {
                detail.setExamId(examId);
                examItemDAO.addExamItem(tableName, detail, insId);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addExamItem(String tableName, ExamItemPojo examDataDetail) {
        return examItemDAO.addExamItem(tableName, examDataDetail, this.getInsId());
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteExamItem(String tableName, String examCode) {
        examItemDAO.deleteExamItem(tableName, examCode);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteExamItemByExamIdAndItemCode(String tableName, String examCode, String itemCode) {
        examItemDAO.deleteExamItemByExamIdAndItemCode(tableName, examCode, itemCode);
    }

}

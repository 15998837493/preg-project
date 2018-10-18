
package com.mnt.preg.examitem.dao;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.mnt.health.core.annotation.QueryFieldAnnotation;
import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.health.utils.times.JodaTimeTools;
import com.mnt.preg.examitem.condition.ExamItemCondition;
import com.mnt.preg.examitem.pojo.ExamItemPojo;

/**
 * 检测项目分析结果
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-10 zcq 初版
 */
@Repository
public class ExamItemDAO extends HibernateTemplate {

    /**
     * 条件检索检测项目指标
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public List<ExamItemPojo> queryExamItem(ExamItemCondition condition) {
        if (condition == null || StringUtils.isEmpty(condition.getTableName())) {
            return null;
        }
        String tableName = condition.getTableName();
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "ExamItemVo");

        if (StringUtils.isNotEmpty(condition.getResultCode())) {
            queryCondition.getQueryParams().put("examCode", condition.getResultCode());
            queryCondition.setQueryString(queryCondition.getQueryString() + " AND ResultRecord.exam_code=:examCode");
        }

        String sql = "SELECT " + DaoUtils.getSQLFields(ExamItemPojo.class, "ExamItemVo")
                + "   FROM " + tableName + " AS ExamItemVo"
                + (StringUtils.isNotEmpty(condition.getResultCode()) ?
                        " JOIN cus_result_record AS ResultRecord ON ResultRecord.exam_id=ExamItemVo.exam_id" : "")
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), ExamItemPojo.class);
    }

    /**
     * 条件查询指标异常
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public List<ExamItemPojo> queryExamItemResultByCondition(ExamItemCondition condition) {
        if (condition == null || StringUtils.isEmpty(condition.getTableName())) {
            return null;
        }
        String tableName = condition.getTableName();
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "ExamItemPojo");

        if (StringUtils.isNotEmpty(condition.getResultCode())) {
            queryCondition.getQueryParams().put("examCode", condition.getResultCode());
            queryCondition.setQueryString(queryCondition.getQueryString() + " AND ResultRecord.exam_code=:examCode");
        }
        queryCondition.setQueryString(queryCondition.getQueryString()
                + " AND !ISNULL(ExamItemPojo.item_result) AND ExamItemPojo.item_result!=''");

        String sql = "SELECT " + DaoUtils.getSQLFields(ExamItemPojo.class, "ExamItemPojo")
                + "   FROM " + tableName + " AS ExamItemPojo"
                + (StringUtils.isNotEmpty(condition.getResultCode()) ?
                        " JOIN cus_result_record AS ResultRecord ON ResultRecord.exam_id=ExamItemPojo.exam_id" : "")
                + queryCondition.getQueryString();

        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), ExamItemPojo.class);
    }

    /**
     * 保存检测项目指标
     * 
     * @author zcq
     * @param examItem
     * @return
     */
    public String addExamItem(String tableName, ExamItemPojo examItem, String insId) {
        String id = UUID.randomUUID().toString();
        examItem.setId(id);
        String sql = getInsertSQL(tableName, examItem, insId);
        this.executeSQL(sql);
        return id;
    }

    /**
     * 删除原来的指标数据
     * 
     * @author zcq
     * @param tableName
     * @param examId
     */
    public void deleteExamItem(String tableName, String examId) {
        if (StringUtils.isNotEmpty(tableName) && StringUtils.isNotEmpty(examId)) {
            String sql = "DELETE FROM " + tableName + " WHERE exam_id=:examId";
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("examId", examId);
            this.executeSQL(sql, paramMap);
        }
    }

    /**
     * 删除指定的指标
     * 
     * @author xdc
     * @param tableName
     * @param examId
     */
    public void deleteExamItemByExamIdAndItemCode(String tableName, String examId, String itemCode) {
        if (StringUtils.isNotEmpty(tableName) && StringUtils.isNotEmpty(examId)) {
            String sql = "DELETE FROM " + tableName + " WHERE exam_id=:examId AND item_code=:itemCode";
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("examId", examId);
            paramMap.put("itemCode", itemCode);
            this.executeSQL(sql, paramMap);
        }
    }

    // *****************************************自定义工具方法******************************************

    /**
     * 获取添加SQL语句
     * 
     * @author zcq
     * @param queryClass
     * @return
     */
    private String getInsertSQL(String tableName, ExamItemPojo examItem, String insId) {
        String insertSQL = "";
        String colums = "";
        String values = "";
        Field[] fields = ExamItemPojo.class.getDeclaredFields();
        Field.setAccessible(fields, true);
        for (Field field : fields) {
            if (field.isAnnotationPresent(QueryFieldAnnotation.class)) {
                try {
                    String fieldName = field.getName();
                    Object obj = field.get(examItem);
                    String value = null;
                    if (obj != null) {
                        // 如果是日期类型，格式化为数据库兼容的值
                        if (obj instanceof Date) {
                            value = "'" + JodaTimeTools.toString((Date) obj, JodaTimeTools.FORMAT_2) + "'";
                        } else {
                            value = "'" + obj.toString() + "'";
                        }
                    }
                    colums += DaoUtils.nameFieldToColumn(fieldName) + ",";
                    values += value + ",";
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        colums += "create_user_id,update_user_id,flag,create_ins_id";
        values += "'0','0','1','" + insId + "'";
        insertSQL = "INSERT INTO " + tableName + "(" + colums + ") VALUES (" + values + ")";
        return insertSQL;
    }
}


package com.mnt.preg.master.dao.preg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.master.condition.preg.PregnancyCourseCondition;
import com.mnt.preg.master.condition.preg.PregnancyCourseDetailCondition;
import com.mnt.preg.master.pojo.preg.PregCourseDetailPojo;
import com.mnt.preg.master.pojo.preg.PregCoursePojo;

/**
 * 
 * 孕期课程DAO
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-9-9 gss 初版
 */
@Repository
public class PregCourseDAO extends HibernateTemplate {

    /**
     * 
     * 条件查询孕期课程信息
     * 
     * @author gss
     * @param condition
     * @return List<PregnancyCourseVo>
     */
    public List<PregCoursePojo> queryPregnancyCourse(PregnancyCourseCondition condition) {
        if (condition == null) {
            condition = new PregnancyCourseCondition();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "PregnancyCourseVo");
        String querySQL = "SELECT " + DaoUtils.getSQLFields(PregCoursePojo.class, "PregnancyCourseVo")
                + "        FROM mas_pregnancy_course AS PregnancyCourseVo"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), PregCoursePojo.class);
    }

    /**
     * 
     * 根据名称查询基本信息
     * 
     * @author gss
     * @param name
     * @return
     */
    public List<PregCoursePojo> queryCourseByName(String name) {
        String sql = "SELECT " + DaoUtils.getSQLFields(PregCoursePojo.class, "PregCourseVo")
                + " FROM mas_course AS PregCourseVo"
                + " WHERE name= :name";

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name", name);
        return this.SQLQueryAlias(sql, paramMap, PregCoursePojo.class);
    }

    /**
     * 
     * 根据主键查询基本信息
     * 
     * @author gss
     * @param name
     * @return
     */
    public PregCoursePojo queryPregnancyCourseById(String pregId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(PregCoursePojo.class, "PregnancyCourseVo")
                + " FROM mas_pregnancy_course AS PregnancyCourseVo"
                + " WHERE preg_id= :pregId";

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("pregId", pregId);
        return this.SQLQueryAliasFirst(sql, paramMap, PregCoursePojo.class);
    }

    /**
     * 
     * 根据主键查询基本信息
     * 
     * @author gss
     * @param name
     * @return
     */
    public List<PregCourseDetailPojo> queryPregCourseDetailById(String pregId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(PregCourseDetailPojo.class, "PregnancyCourseDetailVo")
                + " FROM mas_pregnancy_course_detail AS PregnancyCourseDetailVo"
                + " WHERE preg_id= :pregId AND flag= :flag"
                + " ORDER BY preg_de_order ASC";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("pregId", pregId);
        paramMap.put("flag", 1);
        return this.SQLQueryAlias(sql, paramMap, PregCourseDetailPojo.class);
    }

    /**
     * 
     * 物理删除课程信息明细
     * 
     * @author gss
     * @param pregId
     */
    public void removePregCourseDetailById(String pregId) {
        String sql = "DELETE FROM mas_pregnancy_course_detail where preg_id = :pregId";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pregId", pregId);

        this.executeSQL(sql, params);
    }

    /**
     * 
     * 条件查询孕期课程明细信息
     * 
     * @author gss
     * @param condition
     * @return List<PregnancyCourseVo>
     */
    public List<PregCourseDetailPojo> queryPregCourseDetails(PregnancyCourseDetailCondition condition) {
        if (condition == null) {
            condition = new PregnancyCourseDetailCondition();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "PregnancyCourseDetailVo");
        String querySQL = "SELECT " + DaoUtils.getSQLFields(PregCoursePojo.class, "PregnancyCourseDetailVo")
                + "        FROM mas_pregnancy_course_detail AS PregnancyCourseDetailVo"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), PregCourseDetailPojo.class);
    }

    /**
     * 
     * 根据课程主键查询明细中最大排序号
     * 
     * @author gss
     * @param pregId
     * @return
     */
    public Integer getMaxOrderCodeByPregId(String pregId) {
        String sql = "SELECT MAX(preg_de_order) AS pregDeOrder"
                + "   FROM mas_pregnancy_course_detail AS PregnancyCourseDetailVo"
                + "   WHERE PregnancyCourseDetailVo.preg_id=:pregId AND flag= :flag";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("pregId", pregId);
        paramMap.put("flag", 1);
        return this.SQLCount(sql, paramMap);// java.lang.Integer cannot be cast to java.math.BigInteger
    }

    /**
     * 
     * 根据课程主键和排序号查询孕期课程信息
     * 
     * @author gss
     * @param pregId
     * @param orderNo
     * @return PregnancyCourseDetailVo
     */
    public PregCourseDetailPojo getPregnancyCourseDetailByPregId(String pregId, Integer orderNo) {

        String sql = "SELECT " + DaoUtils.getSQLFields(PregCourseDetailPojo.class, "PregnancyCourseDetailVo") +
                "   FROM mas_pregnancy_course_detail AS PregnancyCourseDetailVo" +
                "   WHERE preg_id = :pregId" +
                "   AND preg_de_order = :orderNo AND flag= :flag";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pregId", pregId);
        params.put("orderNo", orderNo);
        params.put("flag", 1);
        return this.SQLQueryAliasFirst(sql, params, PregCourseDetailPojo.class);
    }

    /**
     * 
     * 根据主键查询基本信息
     * 
     * @author gss
     * @param name
     * @return
     */
    public PregCourseDetailPojo getPregnancyCourseDetail(String id) {
        String sql = "SELECT " + DaoUtils.getSQLFields(PregCourseDetailPojo.class, "PregnancyCourseDetailVo")
                + " FROM mas_pregnancy_course_detail AS PregnancyCourseDetailVo"
                + " WHERE id= :id AND flag= :flag";

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        paramMap.put("flag", 1);
        return this.SQLQueryAliasFirst(sql, paramMap, PregCourseDetailPojo.class);
    }

    /**
     * 条件查询孕期课程信息
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public List<PregCourseDetailPojo> queryPregnancyCourseDetail(PregnancyCourseCondition condition) {
        if (condition == null) {
            condition = new PregnancyCourseCondition();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "course");
        String querySQL = "SELECT " + DaoUtils.getSQLFields(PregCourseDetailPojo.class, "PregnancyCourseDetailVo")
                + "        FROM mas_pregnancy_course_detail AS PregnancyCourseDetailVo"
                + "            JOIN mas_pregnancy_course AS course ON PregnancyCourseDetailVo.preg_id=course.preg_id"
                + "                AND PregnancyCourseDetailVo.flag='1'"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), PregCourseDetailPojo.class);
    }

    /**
     * 
     * 验证课程明细代码是否重复(修改操作)
     * 
     * @author scd
     * @param pregDeCode
     * @return
     */
    public Integer pregdeCodeValidateUpdate(String pregId, String pregDeCode) {
        String sql = "SELECT COUNT(preg_de_code)"
                + "   FROM mas_pregnancy_course_detail AS PregnancyCourseDetailVo"
                + "   WHERE PregnancyCourseDetailVo.preg_id=:pregId AND PregnancyCourseDetailVo.preg_de_code=:pregDeCode  AND flag= :flag";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("pregId", pregId);
        paramMap.put("pregDeCode", pregDeCode);
        paramMap.put("flag", 1);
        return this.SQLCount(sql, paramMap);
    }
}

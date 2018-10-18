/**
 * 
 */

package com.mnt.preg.master.pojo.question;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 
 * 问卷算法
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-7 gss 初版
 */
public class QuestionAlgPojo implements Serializable {

    private static final long serialVersionUID = -3639328480269825061L;

    /** 主键 */
    @QueryFieldAnnotation
    private String algId;

    /** 处理算法 */
    @QueryFieldAnnotation
    private String algDealMethod;

    /** 问卷编码 */
    @QueryFieldAnnotation
    private String questionId;

    /** 算法名称 */
    @QueryFieldAnnotation
    private String algName;

    /** 功能类型 */
    @QueryFieldAnnotation
    private Integer algType;

    public String getAlgId() {
        return algId;
    }

    public void setAlgId(String algId) {
        this.algId = algId;
    }

    public String getAlgDealMethod() {
        return algDealMethod;
    }

    public void setAlgDealMethod(String algDealMethod) {
        this.algDealMethod = algDealMethod;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getAlgName() {
        return algName;
    }

    public void setAlgName(String algName) {
        this.algName = algName;
    }

    public Integer getAlgType() {
        return algType;
    }

    public void setAlgType(Integer algType) {
        this.algType = algType;
    }

}

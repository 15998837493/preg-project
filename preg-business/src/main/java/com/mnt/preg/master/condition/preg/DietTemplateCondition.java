
package com.mnt.preg.master.condition.preg;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlTransient;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 食物检索条件
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2015-1-15 zy 初版
 */
public class DietTemplateCondition implements Serializable {

    private static final long serialVersionUID = 1876656860359626335L;

    /** 模版编码 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String dietId;

    /** 模版名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String dietName;

    /** 适合疾病 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String dietDiseaseIds;

    /** 模版状态 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String dietStatus;

    /** 创建时间排序 */
    @QueryConditionAnnotation(order = OrderConstants.DESC)
    private Date createTime;

    @XmlTransient
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

    /** 用户状态 1/0 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ, aliasName = "user")
    private String userStatus = "1";

    public String getDietId() {
        return dietId;
    }

    public void setDietId(String dietId) {
        this.dietId = dietId;
    }

    public String getDietName() {
        return dietName;
    }

    public void setDietName(String dietName) {
        this.dietName = dietName;
    }

    public String getDietDiseaseIds() {
        return dietDiseaseIds;
    }

    public void setDietDiseaseIds(String dietDiseaseIds) {
        this.dietDiseaseIds = dietDiseaseIds;
    }

    public String getDietStatus() {
        return dietStatus;
    }

    public void setDietStatus(String dietStatus) {
        this.dietStatus = dietStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

}

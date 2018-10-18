
package com.mnt.preg.master.condition.preg;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlTransient;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 摄入量模版信息检索条件
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-7-3 zcq 初版
 */
public class IntakeCondition implements Serializable {

    private static final long serialVersionUID = 1403586173137058987L;

    /** 编码 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ, order = OrderConstants.DESC)
    private String intakeId;

    /** 模板名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String intakeName;

    /** 膳食模式 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String intakeMode;

    /** 膳食结构 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String foodStructureId;

    /** 营养食品（天） */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String intakeProductDesc;

    /** 热量上限 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LE)
    private Integer intakeCaloricMax;

    /** 热量下限 */
    @QueryConditionAnnotation(symbol = SymbolConstants.GE)
    private Integer intakeCaloricMin;

    /** 孕期阶段 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String pregnantStage;

    /** 数据类型 */
    private String dataType;

    /** 创建人编码 */
    private String createUserId;

    /** 标识 */
    @XmlTransient
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

    /** 用户状态 1/0 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ, aliasName = "user")
    private String userStatus = "1";

    public IntakeCondition() {
        super();
    }

    public IntakeCondition(String intakeId) {
        this.intakeId = intakeId;
    }

    public String getIntakeId() {
        return intakeId;
    }

    public void setIntakeId(String intakeId) {
        this.intakeId = intakeId;
    }

    public String getIntakeName() {
        return intakeName;
    }

    public void setIntakeName(String intakeName) {
        this.intakeName = intakeName;
    }

    public String getIntakeMode() {
        return intakeMode;
    }

    public void setIntakeMode(String intakeMode) {
        this.intakeMode = intakeMode;
    }

    public String getFoodStructureId() {
        return foodStructureId;
    }

    public void setFoodStructureId(String foodStructureId) {
        this.foodStructureId = foodStructureId;
    }

    public String getIntakeProductDesc() {
        return intakeProductDesc;
    }

    public void setIntakeProductDesc(String intakeProductDesc) {
        this.intakeProductDesc = intakeProductDesc;
    }

    public Integer getIntakeCaloricMax() {
        return intakeCaloricMax;
    }

    public void setIntakeCaloricMax(Integer intakeCaloricMax) {
        this.intakeCaloricMax = intakeCaloricMax;
    }

    public Integer getIntakeCaloricMin() {
        return intakeCaloricMin;
    }

    public void setIntakeCaloricMin(Integer intakeCaloricMin) {
        this.intakeCaloricMin = intakeCaloricMin;
    }

    public String getPregnantStage() {
        return pregnantStage;
    }

    public void setPregnantStage(String pregnantStage) {
        this.pregnantStage = pregnantStage;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
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

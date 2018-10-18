
package com.mnt.preg.master.condition.items;

import java.io.Serializable;
import java.util.List;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 
 * 机构检查项目表
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历： v1.0 2016-6-8 gss 初版
 */
public class InspectItemCondition implements Serializable {

    private static final long serialVersionUID = 6907241399019407738L;

    /** 编码 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ, order = OrderConstants.ASC)
    private String inspectId;

    /** 编码 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ, order = OrderConstants.ASC)
    private String inspectCode;

    /** 检查项目 */
    @QueryConditionAnnotation(symbol = SymbolConstants.IN, name = "inspectId")
    private List<String> inspectIdList;

    /** 名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String inspectName;

    /** 适合性别 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String inspectSex;

    /** 价格（单位：分） */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private Integer inspectPrice;

    /** 项目类别 代码表：10=问卷，11=设备，12录入，99=其他 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ, order = OrderConstants.DESC)
    private String inspectCategory;

    /** 操作名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String inspectConfigName;

    /** 状态 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer inspectStatus;

    /** 删除标识 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

    /** 项目来源 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String inspectSource;

    /** 项目描述 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String inspectDesc;

    public String getInspectId() {
        return inspectId;
    }

    public void setInspectId(String inspectId) {
        this.inspectId = inspectId;
    }

    public String getInspectName() {
        return inspectName;
    }

    public void setInspectName(String inspectName) {
        this.inspectName = inspectName;
    }

    public String getInspectSex() {
        return inspectSex;
    }

    public void setInspectSex(String inspectSex) {
        this.inspectSex = inspectSex;
    }

    public Integer getInspectPrice() {
        return inspectPrice;
    }

    public void setInspectPrice(Integer inspectPrice) {
        this.inspectPrice = inspectPrice;
    }

    public String getInspectConfigName() {
        return inspectConfigName;
    }

    public void setInspectConfigName(String inspectConfigName) {
        this.inspectConfigName = inspectConfigName;
    }

    public Integer getInspectStatus() {
        return inspectStatus;
    }

    public void setInspectStatus(Integer inspectStatus) {
        this.inspectStatus = inspectStatus;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getInspectCategory() {
        return inspectCategory;
    }

    public void setInspectCategory(String inspectCategory) {
        this.inspectCategory = inspectCategory;
    }

    public String getInspectSource() {
        return inspectSource;
    }

    public void setInspectSource(String inspectSource) {
        this.inspectSource = inspectSource;
    }

    public String getInspectDesc() {
        return inspectDesc;
    }

    public void setInspectDesc(String inspectDesc) {
        this.inspectDesc = inspectDesc;
    }

    public List<String> getInspectIdList() {
        return inspectIdList;
    }

    public void setInspectIdList(List<String> inspectIdList) {
        this.inspectIdList = inspectIdList;
    }

    public String getInspectCode() {
        return inspectCode;
    }

    public void setInspectCode(String inspectCode) {
        this.inspectCode = inspectCode;
    }

}

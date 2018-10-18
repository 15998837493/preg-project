
package com.mnt.preg.platform.condition;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 登记检索条件
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-6-15 zcq 初版
 */
public class DiagnosisItemsCondition implements Serializable {

    private static final long serialVersionUID = -8713805225682464170L;

    /** 检查单号 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String id;

    /** 检查时间 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date inspectDatetime;

    /** 检查医生 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String inspectUser;

    /** 检查状态 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer inspectStatus;

    /** 所属门诊号 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diagnosisId;

    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

    @QueryConditionAnnotation(symbol = SymbolConstants.EQ, order = OrderConstants.ASC)
    private String inspectCode;

    /** 返回结果 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String resultCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getInspectDatetime() {
        return inspectDatetime;
    }

    public void setInspectDatetime(Date inspectDatetime) {
        this.inspectDatetime = inspectDatetime;
    }

    public String getInspectUser() {
        return inspectUser;
    }

    public void setInspectUser(String inspectUser) {
        this.inspectUser = inspectUser;
    }

    public Integer getInspectStatus() {
        return inspectStatus;
    }

    public void setInspectStatus(Integer inspectStatus) {
        this.inspectStatus = inspectStatus;
    }

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getInspectCode() {
        return inspectCode;
    }

    public void setInspectCode(String inspectCode) {
        this.inspectCode = inspectCode;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

}

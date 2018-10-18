
package com.mnt.preg.platform.pojo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 剂量评估分析结果信息
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-11-23 zcq 初版
 */
public class ExtenderAssessGroupPojo implements Serializable {

    private static final long serialVersionUID = -6446844509322954240L;

    /** 分析结果信息 */
    private Map<String, List<PregPlanExtenderAssessPojo>> resultMap;

    /** 返回码 */
    private String inspectCode;

    /** 接诊主键 */
    private String inspectId;

    public Map<String, List<PregPlanExtenderAssessPojo>> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<String, List<PregPlanExtenderAssessPojo>> resultMap) {
        this.resultMap = resultMap;
    }

    public String getInspectCode() {
        return inspectCode;
    }

    public void setInspectCode(String inspectCode) {
        this.inspectCode = inspectCode;
    }

    public String getInspectId() {
        return inspectId;
    }

    public void setInspectId(String inspectId) {
        this.inspectId = inspectId;
    }

}

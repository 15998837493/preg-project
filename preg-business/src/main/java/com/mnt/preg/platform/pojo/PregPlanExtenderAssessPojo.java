
package com.mnt.preg.platform.pojo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mnt.preg.customer.pojo.CustomerPojo;
import com.mnt.preg.customer.pojo.PregArchivesPojo;
import com.mnt.preg.examitem.pojo.ExamItemPojo;

/**
 * 干预方案补充剂计量评估
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-7-2 zcq 初版
 */
public class PregPlanExtenderAssessPojo implements Serializable, Cloneable {

    private static final long serialVersionUID = -6446844509322954240L;

    /** 补充剂元素量 */
    private Map<String, PregPlanExtenderAssessDetailPojo> elementMap = new HashMap<String, PregPlanExtenderAssessDetailPojo>();

    /** 检测结果 */
    private Map<String, ExamItemPojo> resultMap;

    /** 被评估的商品列表 */
    private List<PregDiagnosisExtenderPojo> extenderList;

    /** 接诊登记 */
    private PregDiagnosisPojo diagnosis;

    /** 补充剂名称 */
    private String extenderNames;

    /** 患者信息 */
    private CustomerPojo customer;

    /** 孕期建档信息 */
    private PregArchivesPojo archives;

    /** 服用周期 */
    private String takingCycle;

    public List<PregDiagnosisExtenderPojo> getExtenderList() {
        return extenderList;
    }

    public PregArchivesPojo getArchives() {
        return archives;
    }

    public void setArchives(PregArchivesPojo archives) {
        this.archives = archives;
    }

    public void setExtenderList(List<PregDiagnosisExtenderPojo> extenderList) {
        this.extenderList = extenderList;
    }

    public Map<String, ExamItemPojo> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<String, ExamItemPojo> resultMap) {
        this.resultMap = resultMap;
    }

    public String getExtenderNames() {
        return extenderNames;
    }

    public void setExtenderNames(String extenderNames) {
        this.extenderNames = extenderNames;
    }

    public Map<String, PregPlanExtenderAssessDetailPojo> getElementMap() {
        return elementMap;
    }

    public void setElementMap(Map<String, PregPlanExtenderAssessDetailPojo> elementMap) {
        this.elementMap = elementMap;
    }

    public CustomerPojo getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerPojo customer) {
        this.customer = customer;
    }

    public PregDiagnosisPojo getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(PregDiagnosisPojo diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTakingCycle() {
        return takingCycle;
    }

    public void setTakingCycle(String takingCycle) {
        this.takingCycle = takingCycle;
    }

    @Override
    public Object clone() {
        PregPlanExtenderAssessPojo stu = null;
        try {
            stu = (PregPlanExtenderAssessPojo) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return stu;
    }

}

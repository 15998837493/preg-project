
package com.mnt.preg.account.condition;

import java.io.Serializable;
import java.util.List;

/**
 * 统计工作量
 * 
 * @author xdc
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-2-5 xdc 初版
 */
public class WorkAccountCondition implements Serializable {

    private static final long serialVersionUID = -1841710661155525441L;

    /** 医师 */
    private String diagnosisUser;

    /** 医师姓名 */
    private String diagnosisUserName;

    /** 转诊医师ID */
    private String diagnosisZhuanUser;

    /** 系统营养评价项目Id */
    private String diagnosisMasItems;

    /** 系统营养评价项目操作者Id */
    private String diagnosisMasItemAuthors;

    /** 转诊医师名字 */
    private String diagnosisZhuanUserName;

    /** 系统营养评价项目名字 */
    private String diagnosisMasItemsName;

    /** 系统营养评价项目操作者名字 */
    private String diagnosisMasItemAuthorsName;

    /** 选中的医师姓名 */
    private List<String> chooseDiagnosisUserName;

    /** 医师列表 */
    private List<String> diagnosisUserList;

    /** 医师姓名列表 */
    private List<String> diagnosisUserNameList;

    /** 诊断项目 */
    private String diseaseId;

    /** 诊断项目 */
    private String diseaseName;

    /** 诊断项目列表 */
    private List<String> diseaseIdList;

    /** 诊断项目列表 */
    private List<String> diseaseNameList;

    /** 接诊时间 */
    private String diagnosisDateLike;

    /** 开始时间 */
    private String startDate;

    /** 结束时间 */
    private String endDate;

    /** 开始时间 */
    private String startDate2;

    /** 结束时间 */
    private String endDate2;

    /** 区分 就诊人数/初诊人数/复诊率 */
    private String type;

    /** excel图表标题 */
    private String title;

    /** excel图表内容 */
    private List<String> dataList;

    /** 判断导出的excel是个人还是对比 */
    private String createType;

    public String getDiagnosisUser() {
        return diagnosisUser;
    }

    public void setDiagnosisUser(String diagnosisUser) {
        this.diagnosisUser = diagnosisUser;
    }

    public List<String> getDiagnosisUserList() {
        return diagnosisUserList;
    }

    public void setDiagnosisUserList(List<String> diagnosisUserList) {
        this.diagnosisUserList = diagnosisUserList;
    }

    public List<String> getDiagnosisUserNameList() {
        return diagnosisUserNameList;
    }

    public void setDiagnosisUserNameList(List<String> diagnosisUserNameList) {
        this.diagnosisUserNameList = diagnosisUserNameList;
    }

    public String getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }

    public List<String> getDiseaseIdList() {
        return diseaseIdList;
    }

    public void setDiseaseIdList(List<String> diseaseIdList) {
        this.diseaseIdList = diseaseIdList;
    }

    public List<String> getDiseaseNameList() {
        return diseaseNameList;
    }

    public void setDiseaseNameList(List<String> diseaseNameList) {
        this.diseaseNameList = diseaseNameList;
    }

    public String getDiagnosisDateLike() {
        return diagnosisDateLike;
    }

    public void setDiagnosisDateLike(String diagnosisDateLike) {
        this.diagnosisDateLike = diagnosisDateLike;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate2() {
        return startDate2;
    }

    public void setStartDate2(String startDate2) {
        this.startDate2 = startDate2;
    }

    public String getEndDate2() {
        return endDate2;
    }

    public void setEndDate2(String endDate2) {
        this.endDate2 = endDate2;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getDataList() {
        return dataList;
    }

    public void setDataList(List<String> dataList) {
        this.dataList = dataList;
    }

    public List<String> getChooseDiagnosisUserName() {
        return chooseDiagnosisUserName;
    }

    public void setChooseDiagnosisUserName(List<String> chooseDiagnosisUserName) {
        this.chooseDiagnosisUserName = chooseDiagnosisUserName;
    }

    public String getCreateType() {
        return createType;
    }

    public void setCreateType(String createType) {
        this.createType = createType;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDiagnosisUserName() {
        return diagnosisUserName;
    }

    public void setDiagnosisUserName(String diagnosisUserName) {
        this.diagnosisUserName = diagnosisUserName;
    }

    public String getDiagnosisZhuanUser() {
        return diagnosisZhuanUser;
    }

    public void setDiagnosisZhuanUser(String diagnosisZhuanUser) {
        this.diagnosisZhuanUser = diagnosisZhuanUser;
    }

    public String getDiagnosisMasItems() {
        return diagnosisMasItems;
    }

    public void setDiagnosisMasItems(String diagnosisMasItems) {
        this.diagnosisMasItems = diagnosisMasItems;
    }

    public String getDiagnosisMasItemAuthors() {
        return diagnosisMasItemAuthors;
    }

    public void setDiagnosisMasItemAuthors(String diagnosisMasItemAuthors) {
        this.diagnosisMasItemAuthors = diagnosisMasItemAuthors;
    }

    public String getDiagnosisZhuanUserName() {
        return diagnosisZhuanUserName;
    }

    public void setDiagnosisZhuanUserName(String diagnosisZhuanUserName) {
        this.diagnosisZhuanUserName = diagnosisZhuanUserName;
    }

    public String getDiagnosisMasItemsName() {
        return diagnosisMasItemsName;
    }

    public void setDiagnosisMasItemsName(String diagnosisMasItemsName) {
        this.diagnosisMasItemsName = diagnosisMasItemsName;
    }

    public String getDiagnosisMasItemAuthorsName() {
        return diagnosisMasItemAuthorsName;
    }

    public void setDiagnosisMasItemAuthorsName(String diagnosisMasItemAuthorsName) {
        this.diagnosisMasItemAuthorsName = diagnosisMasItemAuthorsName;
    }

}

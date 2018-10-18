
package com.mnt.preg.master.pojo.items;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.mnt.health.core.annotation.QueryFieldAnnotation;
import com.mnt.preg.platform.pojo.DiagnosisHospitalItemPojo;

/**
 * 体检项目字典
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-4-5 gss 初版
 */
public class ItemPojo implements Serializable {

    private static final long serialVersionUID = -3639328480269825061L;

    /** 项目主键 */
    @QueryFieldAnnotation
    private String itemId;

    /** 项目编号 */
    @QueryFieldAnnotation
    private String itemCode;

    /** 项目名称 */
    @QueryFieldAnnotation
    private String itemName;

    /** 指标分类（大） */
    @QueryFieldAnnotation
    private String itemType;

    /** 数据归类 */
    @QueryFieldAnnotation
    private String itemClassify;

    /** 项目标准单位 */
    @QueryFieldAnnotation
    private String itemUnit;

    /** 是否对比 否;1=是 */
    @QueryFieldAnnotation
    private Integer isContrast;

    /** 是否重要 否;1=是 */
    @QueryFieldAnnotation
    private Integer isMain;

    /** 项目所属类型 */
    @QueryFieldAnnotation
    private String itemAnalyze;

    /** 结论算法 */
    @QueryFieldAnnotation
    private String resultJson;

    /** 检查项目名称 */
    private String inspectName;

    /** 指标参考范围 */
    @QueryFieldAnnotation
    private String itemRefValue;

    /** 指标参考范围 */
    @QueryFieldAnnotation
    private Integer itemOrder;

    /** 指标适宜性别 */
    @QueryFieldAnnotation
    private String itemSex;

    /** 检验时间 */
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date itemDatetime;

    /** 检测项目配置表id */
    private String id;

    /** 收费项目主键 */
    private String inspectPayId;

    /** 收费项目主键 */
    private String inspectPayName;

    /** 包含的收费项目 */
    private String insepctPayIds;

    /** 上次孕周 */
    private String lastGsetWeek;

    /** 上次结果 */
    private String lastItemString;

    /** 历史记录 */
    private List<DiagnosisHospitalItemPojo> hisItemList;

    // 逻辑删除标识
    private Integer flag;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemClassify() {
        return itemClassify;
    }

    public void setItemClassify(String itemClassify) {
        this.itemClassify = itemClassify;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    public Integer getIsContrast() {
        return isContrast;
    }

    public void setIsContrast(Integer isContrast) {
        this.isContrast = isContrast;
    }

    public Integer getIsMain() {
        return isMain;
    }

    public void setIsMain(Integer isMain) {
        this.isMain = isMain;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemAnalyze() {
        return itemAnalyze;
    }

    public void setItemAnalyze(String itemAnalyze) {
        this.itemAnalyze = itemAnalyze;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getInspectName() {
        return inspectName;
    }

    public void setInspectName(String inspectName) {
        this.inspectName = inspectName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getItemDatetime() {
        return itemDatetime;
    }

    public void setItemDatetime(Date itemDatetime) {
        this.itemDatetime = itemDatetime;
    }

    public String getItemRefValue() {
        return itemRefValue;
    }

    public void setItemRefValue(String itemRefValue) {
        this.itemRefValue = itemRefValue;
    }

    public String getResultJson() {
        return resultJson;
    }

    public void setResultJson(String resultJson) {
        this.resultJson = resultJson;
    }

    public String getInspectPayId() {
        return inspectPayId;
    }

    public void setInspectPayId(String inspectPayId) {
        this.inspectPayId = inspectPayId;
    }

    public String getInsepctPayIds() {
        return insepctPayIds;
    }

    public void setInsepctPayIds(String insepctPayIds) {
        this.insepctPayIds = insepctPayIds;
    }

    public String getLastGsetWeek() {
        return lastGsetWeek;
    }

    public void setLastGsetWeek(String lastGsetWeek) {
        this.lastGsetWeek = lastGsetWeek;
    }

    public String getLastItemString() {
        return lastItemString;
    }

    public void setLastItemString(String lastItemString) {
        this.lastItemString = lastItemString;
    }

    public String getInspectPayName() {
        return inspectPayName;
    }

    public void setInspectPayName(String inspectPayName) {
        this.inspectPayName = inspectPayName;
    }

    public Integer getItemOrder() {
        return itemOrder;
    }

    public void setItemOrder(Integer itemOrder) {
        this.itemOrder = itemOrder;
    }

    public String getItemSex() {
        return itemSex;
    }

    public void setItemSex(String itemSex) {
        this.itemSex = itemSex;
    }

    public List<DiagnosisHospitalItemPojo> getHisItemList() {
        return hisItemList;
    }

    public void setHisItemList(List<DiagnosisHospitalItemPojo> hisItemList) {
        this.hisItemList = hisItemList;
    }

}

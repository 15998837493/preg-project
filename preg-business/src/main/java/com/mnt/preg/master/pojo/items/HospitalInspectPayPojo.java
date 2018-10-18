
package com.mnt.preg.master.pojo.items;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 收费项目信息Pojo
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-6-21 zcq 初版
 */
public class HospitalInspectPayPojo implements Serializable {

    private static final long serialVersionUID = -3819990804621658472L;

    /** 主键 */
    @QueryFieldAnnotation
    private String inspectId;

    /** 编码 */
    @QueryFieldAnnotation
    private String inspectCode;

    /** 名称 */
    @QueryFieldAnnotation
    private String inspectName;

    /** 适合性别 */
    @QueryFieldAnnotation
    private String inspectSex;

    /** 出结果时限 */
    @QueryFieldAnnotation
    private String resultsSuggest;

    /** 复查提示 */
    @QueryFieldAnnotation
    private String reviewHints;

    /** 项目价格 */
    @QueryFieldAnnotation
    private BigDecimal inspectPrice;

    /** 项目描述 */
    @QueryFieldAnnotation
    private String inspectDesc;

    /** 项目排序 */
    @QueryFieldAnnotation
    private Integer inspectSort;

    /** 逻辑删除标识 */
    private Integer flag;

    /** 收费项目包含的检验项目 */
    private List<ItemPojo> itemList;

    /** 收费项目包含的检验项目主键 */
    private List<String> itemIdList;

    /** 诊断项目配置主键 */
    private String id;

    public String getInspectId() {
        return inspectId;
    }

    public void setInspectId(String inspectId) {
        this.inspectId = inspectId;
    }

    public String getInspectCode() {
        return inspectCode;
    }

    public void setInspectCode(String inspectCode) {
        this.inspectCode = inspectCode;
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

    public BigDecimal getInspectPrice() {
        return inspectPrice;
    }

    public void setInspectPrice(BigDecimal inspectPrice) {
        this.inspectPrice = inspectPrice;
    }

    public String getInspectDesc() {
        return inspectDesc;
    }

    public void setInspectDesc(String inspectDesc) {
        this.inspectDesc = inspectDesc;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getResultsSuggest() {
        return resultsSuggest;
    }

    public void setResultsSuggest(String resultsSuggest) {
        this.resultsSuggest = resultsSuggest;
    }

    public String getReviewHints() {
        return reviewHints;
    }

    public void setReviewHints(String reviewHints) {
        this.reviewHints = reviewHints;
    }

    public Integer getInspectSort() {
        return inspectSort;
    }

    public void setInspectSort(Integer inspectSort) {
        this.inspectSort = inspectSort;
    }

    public List<ItemPojo> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemPojo> itemList) {
        this.itemList = itemList;
    }

    public List<String> getItemIdList() {
        return itemIdList;
    }

    public void setItemIdList(List<String> itemIdList) {
        this.itemIdList = itemIdList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}

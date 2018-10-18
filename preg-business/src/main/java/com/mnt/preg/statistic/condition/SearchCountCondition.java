
package com.mnt.preg.statistic.condition;

import java.io.Serializable;
import java.util.List;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 查询条件统计
 * 
 * @author mlq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-08-15 mlq 初版
 */
public class SearchCountCondition implements Serializable {

    private static final long serialVersionUID = -1841710661155525441L;

    /** 患者id集合 */
    @QueryConditionAnnotation(name = "custId", aliasName = "conditionPojo", symbol = SymbolConstants.IN)
    private List<String> custIds;

    /** 患者id集合（接诊信息查询使用） */
    @QueryConditionAnnotation(name = "diagnosisCustomer", aliasName = "conditionPojo", symbol = SymbolConstants.IN)
    private List<String> customerIds;

    /** 阶段选项 1，妊娠 2，产后 */
    private Integer stage;

    /**
     * 导出选项集合
     * 1，总导出表
     * 2，体成分导出表
     * 3，孕期检验记录导出表
     * 4，营养制剂导出表
     * 5，分娩结局导出表
     */
    private String conditions;

    /** 检验项目集合 */
    private String items;

    /** 检验项目名字集合 */
    private String itemsName;

    /** 营养制剂数量最大值 */
    private Integer productNumMax;

    /** 新生儿数量最大值 */
    private Integer babyNumMax;

    /** 建档id集合（查询分娩阶段时条件） */
    @QueryConditionAnnotation(name = "archivesId", aliasName = "conditionPojo", symbol = SymbolConstants.IN)
    private List<String> archivesIds;

    /** 查询有效数据 */
    @QueryConditionAnnotation(aliasName = "conditionPojo", symbol = SymbolConstants.EQ)
    private Integer flag = 1;

    /** 查询条件 excel显示用 */
    private String queryCondition;

    public String getItemsName() {
        return itemsName;
    }

    public void setItemsName(String itemsName) {
        this.itemsName = itemsName;
    }

    public String getQueryCondition() {
        return queryCondition;
    }

    public void setQueryCondition(String queryCondition) {
        this.queryCondition = queryCondition;
    }

    public List<String> getCustIds() {
        return custIds;
    }

    public void setCustIds(List<String> custIds) {
        this.custIds = custIds;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public List<String> getArchivesIds() {
        return archivesIds;
    }

    public void setArchivesIds(List<String> archivesIds) {
        this.archivesIds = archivesIds;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public Integer getProductNumMax() {
        return productNumMax;
    }

    public void setProductNumMax(Integer productNumMax) {
        this.productNumMax = productNumMax;
    }

    public Integer getBabyNumMax() {
        return babyNumMax;
    }

    public void setBabyNumMax(Integer babyNumMax) {
        this.babyNumMax = babyNumMax;
    }

    public List<String> getCustomerIds() {
        return customerIds;
    }

    public void setCustomerIds(List<String> customerIds) {
        this.customerIds = customerIds;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

}

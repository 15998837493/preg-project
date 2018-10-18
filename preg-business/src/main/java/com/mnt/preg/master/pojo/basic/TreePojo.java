/*
 * Tree.java    1.0  2018-1-25
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.master.pojo.basic;

import java.io.Serializable;
import java.util.List;

import com.mnt.health.core.annotation.QueryFieldAnnotation;



/**
 * mas_树级表
 * 
 * @author xdc
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-25 xdc 初版
 */
public class TreePojo implements Serializable {

    private static final long serialVersionUID = -4045237132073921263L;

    /** 主键 */
    @QueryFieldAnnotation
    private String id;

    /** 类别类型 商品类别：product 食材类别：food_material 食物类别：food */
    @QueryFieldAnnotation
    private String treeKind;

    /** 类别编号 */
    @QueryFieldAnnotation
    private String treeId;

    /** 类别名称 */
    @QueryFieldAnnotation
    private String treeName;

    /** 类别父编号 */
    @QueryFieldAnnotation
    private String parentTreeId;

    /** 排序 */
    @QueryFieldAnnotation
    private Integer treeOrder;

    private List<TreePojo> childList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTreeKind() {
        return treeKind;
    }

    public void setTreeKind(String treeKind) {
        this.treeKind = treeKind;
    }

    public String getTreeId() {
        return treeId;
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }

    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }

    public String getParentTreeId() {
        return parentTreeId;
    }

    public void setParentTreeId(String parentTreeId) {
        this.parentTreeId = parentTreeId;
    }

    public Integer getTreeOrder() {
        return treeOrder;
    }

    public void setTreeOrder(Integer treeOrder) {
        this.treeOrder = treeOrder;
    }

    public List<TreePojo> getChildList() {
        return childList;
    }

    public void setChildList(List<TreePojo> childList) {
        this.childList = childList;
    }

}

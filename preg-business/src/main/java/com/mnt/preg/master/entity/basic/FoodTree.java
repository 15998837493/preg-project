/*
 * Tree.java    1.0  2018-1-25
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.master.entity.basic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * mas_树级表
 * 
 * @author xdc
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-25 xdc 初版
 */
@Entity
@Table(name = "mas_food_tree")
public class FoodTree extends MappedEntity {

    private static final long serialVersionUID = -4045237132073921263L;

    /** 主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String id;

    /** 类别类型 商品类别：product 食材类别：food_material 食物类别：food */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @UpdateAnnotation
    private String treeKind;

    /** 类别编号 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @UpdateAnnotation
    private String treeId;

    /** 类别名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @UpdateAnnotation
    private String treeName;

    /** 类别父编号 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ, order = OrderConstants.ASC, orderNumber = 1)
    @UpdateAnnotation
    private String parentTreeId;

    /** 排序 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ, order = OrderConstants.ASC, orderNumber = 2)
    @UpdateAnnotation
    private Integer treeOrder;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "tree_kind")
    public String getTreeKind() {
        return treeKind;
    }

    public void setTreeKind(String treeKind) {
        this.treeKind = treeKind;
    }

    @Column(name = "tree_id")
    public String getTreeId() {
        return treeId;
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }

    @Column(name = "tree_name")
    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }

    @Column(name = "parent_tree_id")
    public String getParentTreeId() {
        return parentTreeId;
    }

    public void setParentTreeId(String parentTreeId) {
        this.parentTreeId = parentTreeId;
    }

    @Column(name = "tree_order")
    public Integer getTreeOrder() {
        return treeOrder;
    }

    public void setTreeOrder(Integer treeOrder) {
        this.treeOrder = treeOrder;
    }

}

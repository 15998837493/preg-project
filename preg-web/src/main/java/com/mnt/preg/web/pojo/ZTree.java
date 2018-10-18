
package com.mnt.preg.web.pojo;

import java.util.List;

/**
 * zTree控件生成树实体类
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：v1.0 2014-12-30 zcq 初版
 */
public class ZTree<T> {

    // ID
    private String id;

    // 父ID
    private String pId;

    // 节点名称
    private String name;

    // 是不是父节点
    private Boolean isParent = false;

    // 自定义图标
    private String iconSkin;

    // 描述
    private String desc;

    // 拖拽--子节点移走
    private Boolean childOuter = false;

    // 拖拽
    private Boolean dropInner = true;

    // 可否被选中
    private Boolean chkDisabled = false;

    // 不显示radio
    private Boolean nocheck = false;

    // 返回结果
    private T value;

    // 子集
    private List<ZTree<T>> childList;

    public ZTree() {
        super();
    }

    public ZTree(String id, String name, Boolean isParent, String iconSkin) {
        super();
        this.id = id;
        this.name = name;
        this.isParent = isParent;
        this.iconSkin = iconSkin;
    }

    public ZTree(String id, String name, Boolean isParent, String iconSkin, Boolean nocheck) {
        super();
        this.id = id;
        this.name = name;
        this.isParent = isParent;
        this.iconSkin = iconSkin;
        this.nocheck = nocheck;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }

    public String getIconSkin() {
        return iconSkin;
    }

    public void setIconSkin(String iconSkin) {
        this.iconSkin = iconSkin;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Boolean getChildOuter() {
        return childOuter;
    }

    public void setChildOuter(Boolean childOuter) {
        this.childOuter = childOuter;
    }

    public Boolean getDropInner() {
        return dropInner;
    }

    public void setDropInner(Boolean dropInner) {
        this.dropInner = dropInner;
    }

    public Boolean getChkDisabled() {
        return chkDisabled;
    }

    public void setChkDisabled(Boolean chkDisabled) {
        this.chkDisabled = chkDisabled;
    }

    public Boolean getNocheck() {
        return nocheck;
    }

    public void setNocheck(Boolean nocheck) {
        this.nocheck = nocheck;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public List<ZTree<T>> getChildList() {
        return childList;
    }

    public void setChildList(List<ZTree<T>> childList) {
        this.childList = childList;
    }

}

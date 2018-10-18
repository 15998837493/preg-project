/*
 * Flag.java    1.0  2017-5-2
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.main.enums;

/**
 * 逻辑删除枚举
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-5-2 zy 初版
 */
public enum Flag {

    normal("正常", 1), deleted("删除", 0);

    private String name;

    private int value;

    private Flag(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}

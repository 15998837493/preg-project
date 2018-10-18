
package com.mnt.preg.web.utils;

/**
 * 常量编码
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-5-8 zcq 初版
 */
public class CodeValue {

    private String key;

    private String value;

    public CodeValue(String key, String value) {
        super();
        this.key = key;
        this.value = value;
    }

    public CodeValue() {
        super();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}

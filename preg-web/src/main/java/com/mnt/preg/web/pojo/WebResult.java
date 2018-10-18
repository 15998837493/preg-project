
package com.mnt.preg.web.pojo;

import java.util.List;

/**
 * 
 * 主要用于前台页面显示信息
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2014-12-15 zy 初版
 */
public class WebResult<T> {

    /** 操作状态 */
    private boolean result;

    /** 提示信息 */
    private String message;

    /** 操作结果 */
    private T value;

    /** 返回列表 */
    private List<?> data;

    public WebResult() {
        result = false;
        message = "";// 出于 layer插件 layer.alert(arg)的参数不能为空的考虑，对此进行初始化
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.result = true;
        this.data = data;
    }

    public WebResult<T> setSuc(T value) {
        this.result = true;
        this.value = value;
        return this;
    }

    public WebResult<T> setSuc(T value, String message) {
        this.result = true;
        this.value = value;
        this.message = message;
        return this;
    }

    public WebResult<T> setError(String message) {
        this.result = false;
        this.message = message;
        return this;
    }

    public WebResult<T> setError(String message, T value) {
        this.result = false;
        this.message = message;
        this.value = value;
        return this;
    }

}

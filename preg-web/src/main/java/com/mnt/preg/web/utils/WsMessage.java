
package com.mnt.preg.web.utils;

/**
 * WsMessage
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-4-4 zcq 初版
 */
public class WsMessage {

    /** 消息内容 */
    private String value;

    /** 消息类型 */
    private String type;

    public WsMessage() {
        super();
    }

    public WsMessage(String value, String type) {
        super();
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}

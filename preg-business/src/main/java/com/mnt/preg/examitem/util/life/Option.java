
package com.mnt.preg.examitem.util.life;

/**
 * 
 * 膳食及生活方式选项
 *
 * @author mnt_zhangjing
 * @version 1.0 
 *
 * 变更履历：
 *   v1.0 2017-3-6 mnt_zhangjing 初版
 */
public class Option {
    // 选项编号
    private String optionId;

    // 选项名称
    private String optionName;

    // 是否是异常选项
    private boolean exception;

    public Option(String optionId, String optionName, boolean exception) {
        super();
        this.optionId = optionId;
        this.optionName = optionName;
        this.exception = exception;
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public boolean isException() {
        return exception;
    }

    public void setException(boolean exception) {
        this.exception = exception;
    }
}

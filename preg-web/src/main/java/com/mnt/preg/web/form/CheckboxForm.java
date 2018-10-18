/**
 * 
 */

package com.mnt.preg.web.form;

import java.util.List;

/**
 * 
 * Check值
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2014-12-15 zy 初版
 */
public class CheckboxForm<Pojo> {

    private List<Pojo> checkValue;

    public List<Pojo> getCheckValue() {
        return checkValue;
    }

    public void setCheckValue(List<Pojo> checkValue) {
        this.checkValue = checkValue;
    }

}

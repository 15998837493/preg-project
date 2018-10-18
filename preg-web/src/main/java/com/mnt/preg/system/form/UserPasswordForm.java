/*
 * UserPasswordForm.java	 1.0   2015-1-8
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.system.form;

/**
 * 修改用户密码表单数据
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：v1.0 2015-1-8 zcq 初版
 */
public class UserPasswordForm {

    /** 帐户旧密码 */
    private String oldpsw;

    /** 帐户新密码 */
    private String newpsw;

    public String getOldpsw() {
        return oldpsw;
    }

    public void setOldpsw(String oldpsw) {
        this.oldpsw = oldpsw;
    }

    public String getNewpsw() {
        return newpsw;
    }

    public void setNewpsw(String newpsw) {
        this.newpsw = newpsw;
    }

}

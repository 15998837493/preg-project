
package com.mnt.preg.account.pojo;

import java.io.Serializable;

/**
 * 查询对比人数
 * 
 * @author dhs
 * @version 1.4
 * 
 *          变更履历：
 *          v1.4 2018-2-6 dhs 初版
 */
public class AccountContrastPojo implements Serializable {

    private static final long serialVersionUID = 4826784419965327557L;

    /** 医师姓名 */
    private String userName;

    /** 第一段时间范围的人数 */
    private String firstScopeCount;

    /** 第二段时间范围的人数 */
    private String lastScopeCount;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstScopeCount() {
        return firstScopeCount;
    }

    public void setFirstScopeCount(String firstScopeCount) {
        this.firstScopeCount = firstScopeCount;
    }

    public String getLastScopeCount() {
        return lastScopeCount;
    }

    public void setLastScopeCount(String lastScopeCount) {
        this.lastScopeCount = lastScopeCount;
    }
}

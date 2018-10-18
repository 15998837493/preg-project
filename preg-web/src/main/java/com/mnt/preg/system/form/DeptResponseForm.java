
package com.mnt.preg.system.form;

import java.util.List;

/**
 * 部门职务表单
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-4-16 zcq 初版
 */
public class DeptResponseForm {

    /** 部门ID */
    private String deptId;

    /** 职务列表 */
    private List<String> positionIdList;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public List<String> getPositionIdList() {
        return positionIdList;
    }

    public void setPositionIdList(List<String> positionIdList) {
        this.positionIdList = positionIdList;
    }

}

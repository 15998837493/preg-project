/*
 * ChinaVo.java	 1.0   2015-1-6
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.system.pojo;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 区域信息
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：v1.0 2015-1-6 zcq 初版
 */
public class ChinaPojo {

    @QueryFieldAnnotation
    private Integer id;

    @QueryFieldAnnotation
    private String name;

    @QueryFieldAnnotation
    private Integer pid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

}

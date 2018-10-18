/*
 * PrimaryKeyVo.java	 1.0   2015-10-27
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.system.pojo;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 
 * 主键生成规则VO
 * 
 * @author mnt_zhangjing
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-10-27 mnt_zhangjing 初版
 */
public class PrimaryKeyPojo {

    /** 主键 */
    @QueryFieldAnnotation
    private String keyId;

    /** 主键生成日期 */
    @QueryFieldAnnotation
    private String keyDate;

    /** 生成主键使用的表 */
    @QueryFieldAnnotation
    private String keyTableName;

    /** 主键顺序号 */
    @QueryFieldAnnotation
    private Integer keyOrder;

    /** 顺序号长度 */
    @QueryFieldAnnotation
    private Integer keyOrderLength;

    /** 主键类型 =1 生成的主键中包含日期 =2 生成的主键中不包含日期 */
    @QueryFieldAnnotation
    private String keyType;

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getKeyDate() {
        return keyDate;
    }

    public void setKeyDate(String keyDate) {
        this.keyDate = keyDate;
    }

    public String getKeyTableName() {
        return keyTableName;
    }

    public void setKeyTableName(String keyTableName) {
        this.keyTableName = keyTableName;
    }

    public Integer getKeyOrder() {
        return keyOrder;
    }

    public void setKeyOrder(Integer keyOrder) {
        this.keyOrder = keyOrder;
    }

    public Integer getKeyOrderLength() {
        return keyOrderLength;
    }

    public void setKeyOrderLength(Integer keyOrderLength) {
        this.keyOrderLength = keyOrderLength;
    }

    public String getKeyType() {
        return keyType;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    @Override
    public String toString() {
        return "PrimaryKeyVo [keyId=" + keyId + ", keyDate=" + keyDate + ", keyTableName=" + keyTableName
                + ", keyOrder=" + keyOrder + ", keyOrderLength=" + keyOrderLength + ", keyType=" + keyType + "]";
    }

}

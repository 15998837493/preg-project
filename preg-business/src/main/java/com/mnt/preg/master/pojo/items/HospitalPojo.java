
package com.mnt.preg.master.pojo.items;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 医院信息
 * 
 * @author dhs
 * @version 1.7
 * 
 *          变更履历：
 *          v1.0 2018-08-07 dhs 初版
 */
public class HospitalPojo implements Serializable {

    private static final long serialVersionUID = -7475307653280821437L;

    /** 项目主键 */
    @QueryFieldAnnotation
    private String hospitalId;

    /** 医院分类（大） */
    @QueryFieldAnnotation
    private String hospitalType;

    /** 医院分类（小） */
    @QueryFieldAnnotation
    private String hospitalClassify;

    /** 医院名称 */
    @QueryFieldAnnotation
    private String hospitalName;

    /** 省 */
    @QueryFieldAnnotation
    private String hospitalCapital;

    /** 市 */
    @QueryFieldAnnotation
    private String hospitalCity;

    /** 区 */
    @QueryFieldAnnotation
    private String hospitalArea;

    /** 是否合作 */
    @QueryFieldAnnotation
    private String hospitalWorkWith;

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalType() {
        return hospitalType;
    }

    public void setHospitalType(String hospitalType) {
        this.hospitalType = hospitalType;
    }

    public String getHospitalClassify() {
        return hospitalClassify;
    }

    public void setHospitalClassify(String hospitalClassify) {
        this.hospitalClassify = hospitalClassify;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalCapital() {
        return hospitalCapital;
    }

    public void setHospitalCapital(String hospitalCapital) {
        this.hospitalCapital = hospitalCapital;
    }

    public String getHospitalCity() {
        return hospitalCity;
    }

    public void setHospitalCity(String hospitalCity) {
        this.hospitalCity = hospitalCity;
    }

    public String getHospitalArea() {
        return hospitalArea;
    }

    public void setHospitalArea(String hospitalArea) {
        this.hospitalArea = hospitalArea;
    }

    public String getHospitalWorkWith() {
        return hospitalWorkWith;
    }

    public void setHospitalWorkWith(String hospitalWorkWith) {
        this.hospitalWorkWith = hospitalWorkWith;
    }

}

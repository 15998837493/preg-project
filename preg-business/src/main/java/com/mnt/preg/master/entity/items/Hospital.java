
package com.mnt.preg.master.entity.items;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 医院信息表
 * 
 * @author dhs
 * @version 1.7
 * 
 *          变更履历：
 *          v1.7 2018-08-07 dhs 初版
 */
@Entity
@Table(name = "sys_hospital")
public class Hospital extends MappedEntity {

    private static final long serialVersionUID = -18342559815115299L;

    /** 项目主键 */
    private String hospitalId;

    /** 医院分类（大） */
    @UpdateAnnotation
    private String hospitalType;

    /** 医院分类（小） */
    @UpdateAnnotation
    private String hospitalClassify;

    /** 医院名称 */
    @UpdateAnnotation
    private String hospitalName;

    /** 省 */
    @UpdateAnnotation
    private String hospitalCapital;

    /** 市 */
    @UpdateAnnotation
    private String hospitalCity;

    /** 区 */
    @UpdateAnnotation
    private String hospitalArea;

    /** 是否合作 */
    @UpdateAnnotation
    private String hospitalWorkWith;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "hospital_id")
    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    @Column(name = "hospital_type")
    public String getHospitalType() {
        return hospitalType;
    }

    public void setHospitalType(String hospitalType) {
        this.hospitalType = hospitalType;
    }

    @Column(name = "hospital_classify")
    public String getHospitalClassify() {
        return hospitalClassify;
    }

    public void setHospitalClassify(String hospitalClassify) {
        this.hospitalClassify = hospitalClassify;
    }

    @Column(name = "hospital_name")
    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    @Column(name = "hospital_capital")
    public String getHospitalCapital() {
        return hospitalCapital;
    }

    public void setHospitalCapital(String hospitalCapital) {
        this.hospitalCapital = hospitalCapital;
    }

    @Column(name = "hospital_city")
    public String getHospitalCity() {
        return hospitalCity;
    }

    public void setHospitalCity(String hospitalCity) {
        this.hospitalCity = hospitalCity;
    }

    @Column(name = "hospital_area")
    public String getHospitalArea() {
        return hospitalArea;
    }

    public void setHospitalArea(String hospitalArea) {
        this.hospitalArea = hospitalArea;
    }

    @Column(name = "hospital_work_with")
    public String getHospitalWorkWith() {
        return hospitalWorkWith;
    }

    public void setHospitalWorkWith(String hospitalWorkWith) {
        this.hospitalWorkWith = hospitalWorkWith;
    }

}

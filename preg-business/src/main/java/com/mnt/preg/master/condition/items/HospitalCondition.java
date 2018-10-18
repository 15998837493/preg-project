
package com.mnt.preg.master.condition.items;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 
 * 医院信息检索条件
 * 
 * @author dhs
 * @version 1.7
 * 
 *          变更履历：
 *          v1.0 2018-08-07 dhs 初版
 */
public class HospitalCondition implements Serializable {

    private static final long serialVersionUID = -8713805225682464170L;

    /** 项目主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String hospitalId;

    /** 医院分类（大） */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String hospitalType;

    /** 医院分类（小） */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String hospitalClassify;

    /** 医院名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String hospitalName;

    /** 省 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String hospitalCapital;

    /** 市 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String hospitalCity;

    /** 区 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String hospitalArea;

    /** 是否合作 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String hospitalWorkWith;

    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

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

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

}

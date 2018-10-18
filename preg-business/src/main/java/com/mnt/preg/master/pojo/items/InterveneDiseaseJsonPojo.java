
package com.mnt.preg.master.pojo.items;

import java.io.Serializable;

/**
 * 作为接诊诊断项目json转换的类
 * 
 * @author xdc
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-10-24 xdc 初版
 */
public class InterveneDiseaseJsonPojo implements Serializable {

    private static final long serialVersionUID = -1861802270896146914L;

    /** 主键 */
    private String id;

    /** 编码 */
    private String code;

    /** 名称 */
    private String name;

    /** ICD10编码 */
    private String icd10;

    /** 状态 */
    private String status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcd10() {
        return icd10;
    }

    public void setIcd10(String icd10) {
        this.icd10 = icd10;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        InterveneDiseaseJsonPojo other = (InterveneDiseaseJsonPojo) obj;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
            return false;
        if (icd10 == null) {
            if (other.icd10 != null)
                return false;
        } else if (!icd10.equals(other.icd10))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

}

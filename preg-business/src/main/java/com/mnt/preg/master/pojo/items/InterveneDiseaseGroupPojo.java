
package com.mnt.preg.master.pojo.items;

import java.io.Serializable;
import java.util.List;

/**
 * 干预疾病组合信息
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-9-9 zcq 初版
 */
public class InterveneDiseaseGroupPojo implements Serializable {

    private static final long serialVersionUID = -205607526597567742L;

    /** 干预疾病类型 */
    private String type;

    /** 干预疾病信息列表 */
    private List<InterveneDiseasePojo> interveneDiseaseList;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<InterveneDiseasePojo> getInterveneDiseaseList() {
        return interveneDiseaseList;
    }

    public void setInterveneDiseaseList(List<InterveneDiseasePojo> interveneDiseaseList) {
        this.interveneDiseaseList = interveneDiseaseList;
    }

}

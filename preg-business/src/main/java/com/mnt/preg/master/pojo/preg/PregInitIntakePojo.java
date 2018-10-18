
package com.mnt.preg.master.pojo.preg;

import java.io.Serializable;
import java.util.List;

import com.mnt.preg.master.pojo.items.InterveneDiseaseGroupPojo;

/**
 * 摄入量模版初始化信息
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-7-3 zcq 初版
 */
public class PregInitIntakePojo implements Serializable {

    private static final long serialVersionUID = 1403586173137058987L;

    /** 干预重点 */
    private List<InterveneDiseaseGroupPojo> diseaseList;

    /** 代量食谱模板 */
    private List<PregDietTemplatePojo> dietTemplateList;

    public List<InterveneDiseaseGroupPojo> getDiseaseList() {
        return diseaseList;
    }

    public void setDiseaseList(List<InterveneDiseaseGroupPojo> diseaseList) {
        this.diseaseList = diseaseList;
    }

    public List<PregDietTemplatePojo> getDietTemplateList() {
        return dietTemplateList;
    }

    public void setDietTemplateList(List<PregDietTemplatePojo> dietTemplateList) {
        this.dietTemplateList = dietTemplateList;
    }

}

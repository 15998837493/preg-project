
package com.mnt.preg.platform.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.mnt.preg.master.pojo.basic.ElementPojo;

/**
 * 干预方案补充剂计量评估明细
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-7-2 zcq 初版
 */
public class PregPlanExtenderAssessDetailPojo implements Serializable {

    private static final long serialVersionUID = -6446844509322954240L;

    /** 膳食元素 */
    private String dietElement;

    /** 补充剂元素 */
    private String extenderElement;

    /** 补充剂元素合计值 */
    private BigDecimal elementValue = BigDecimal.ZERO;

    /** 补充剂元素推荐值 */
    private String elementRNI;

    /** 补充剂元素适宜值 */
    private String elementAI;

    /** 补充剂元素最大值 */
    private String elementUL;

    /** 元素信息 */
    private ElementPojo element;

    /** 补充剂来源列表 */
    private List<String> srcList = new ArrayList<>();

    /** 补充剂成分备注列表 */
    private List<String> srcEleDescList = new ArrayList<>();

    /** 详细内容：①+维生素D3+：+45mg */
    private List<String> detailStrList = new ArrayList<>();

    /** 与rdis比对结果 */
    private String rdis;

    public String getRdis() {
        return rdis;
    }

    public void setRdis(String rdis) {
        this.rdis = rdis;
    }

    public List<String> getSrcEleDescList() {
        return srcEleDescList;
    }

    public BigDecimal getElementValue() {
        return elementValue;
    }

    public void setElementValue(BigDecimal elementValue) {
        this.elementValue = elementValue;
    }

    public List<String> getSrcList() {
        return srcList;
    }

    public String getDietElement() {
        return dietElement;
    }

    public void setDietElement(String dietElement) {
        this.dietElement = dietElement;
    }

    public String getExtenderElement() {
        return extenderElement;
    }

    public void setExtenderElement(String extenderElement) {
        this.extenderElement = extenderElement;
    }

    public String getElementRNI() {
        return elementRNI;
    }

    public void setElementRNI(String elementRNI) {
        this.elementRNI = elementRNI;
    }

    public String getElementAI() {
        return elementAI;
    }

    public void setElementAI(String elementAI) {
        this.elementAI = elementAI;
    }

    public String getElementUL() {
        return elementUL;
    }

    public void setElementUL(String elementUL) {
        this.elementUL = elementUL;
    }

    public ElementPojo getElement() {
        return element;
    }

    public void setElement(ElementPojo element) {
        this.element = element;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public void setDetailStrList(List<String> detailStrList) {
        this.detailStrList = detailStrList;
    }

    public List<String> getDetailStrList() {
        return detailStrList;
    }

}

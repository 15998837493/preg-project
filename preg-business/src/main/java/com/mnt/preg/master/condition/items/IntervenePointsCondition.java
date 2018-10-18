
package com.mnt.preg.master.condition.items;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlTransient;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;

public class IntervenePointsCondition implements Serializable {

    private static final long serialVersionUID = 6091020302197444285L;

    /** 大类 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String pointType;

    /** 异常返回类型 */
    @QueryConditionAnnotation(order = OrderConstants.DESC)
    private Integer pointOrder;

    /** 删除标识 */
    @XmlTransient
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

    /** 要点描述 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String pointDesc;

    /** 要点描述 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String pointSubclass;;

    public String getPointType() {
        return pointType;
    }

    public void setPointType(String pointType) {
        this.pointType = pointType;
    }

    public Integer getPointOrder() {
        return pointOrder;
    }

    public void setPointOrder(Integer pointOrder) {
        this.pointOrder = pointOrder;
    }

    public String getPointDesc() {
        return pointDesc;
    }

    public void setPointDesc(String pointDesc) {
        this.pointDesc = pointDesc;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getPointSubclass() {
        return pointSubclass;
    }

    public void setPointSubclass(String pointSubclass) {
        this.pointSubclass = pointSubclass;
    }

}

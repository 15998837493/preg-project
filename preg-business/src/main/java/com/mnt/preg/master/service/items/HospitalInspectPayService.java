
package com.mnt.preg.master.service.items;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import com.mnt.preg.master.condition.items.HospitalInspectPayCondition;
import com.mnt.preg.master.entity.items.HospitalInspectPay;
import com.mnt.preg.master.pojo.items.HospitalInspectPayPojo;

/**
 * 收费项目配置
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-6-21 zcq 初版
 */
@Validated
public interface HospitalInspectPayService {

    // =======================================【维护操作接口】==========================================

    /**
     * 根据主键获取收费项目信息
     * 
     * @author zcq
     * @param inspectId
     * @return
     */
    HospitalInspectPayPojo getInspectPayByInspectId(@NotBlank String inspectId);

    /**
     * 条件检索收费项目
     * 
     * @author zcq
     * @param condition
     * @return
     */
    List<HospitalInspectPayPojo> queryInspectPayByCondition(HospitalInspectPayCondition condition);

    /**
     * 更改收费项目信息
     * 
     * @author zcq
     * @param inspect
     */
    void updateInspectPay(HospitalInspectPay inspectPay);

    /**
     * 添加收费项目信息
     * 
     * @author zcq
     * @param inspect
     * @return
     */
    String saveInspectPay(@Valid @NotNull HospitalInspectPay inspectPay, List<String> itemIdList);

    /**
     * 删除收费项目
     * 
     * @author zcq
     * @param inspectId
     */
    void removeInspectPay(@NotBlank String inspectId);

    /**
     * 删除收费项目与检验项目的关联配置
     * 
     * @author zcq
     * @param inspectId
     */
    void deleteInspectPayConfig(String inspectId);

    // =======================================【数据查询接口】==========================================

    /**
     * 根据收费项目主键 查询 收费项目 以及 收费项目 包含的检验项目
     * 
     * @author zcq
     * @param inspectId
     * @return
     */
    HospitalInspectPayPojo queryInspectPayAndItemByInspectId(String inspectId);

    /**
     * 查询所有的 收费项目 以及 收费项目 包含的检验项目
     * 
     * @author zcq
     * @param condition
     * @return
     */
    List<HospitalInspectPayPojo> queryInspectPayAndItemByCondition(HospitalInspectPayCondition condition);

}

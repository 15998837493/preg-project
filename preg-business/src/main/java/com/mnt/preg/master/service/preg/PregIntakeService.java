
package com.mnt.preg.master.service.preg;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.mnt.preg.master.condition.preg.IntakeCondition;
import com.mnt.preg.master.entity.preg.PregIntake;
import com.mnt.preg.master.entity.preg.PregIntakeDetail;
import com.mnt.preg.master.pojo.preg.PregIntakeDetailPojo;
import com.mnt.preg.master.pojo.preg.PregIntakePojo;

/**
 * 膳食干预方案-膳食模板Service
 * 
 * @author wsy
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-3-22 wsy 初版
 */
@Validated
public interface PregIntakeService {

    /**
     * 查询摄入量模版信息
     * 
     * @author zcq
     * @param condition
     * @return
     */
    List<PregIntakePojo> queryIntake(IntakeCondition condition);

    /**
     * 添加摄入量模版
     * 
     * @author zcq
     * @param intake
     * @return
     */
    String addIntake(PregIntake intake);

    /**
     * 修改摄入量模版
     * 
     * @author zcq
     * @param intake
     */
    void updateIntake(PregIntake intake);

    /**
     * 查询摄入量模版明细信息
     * 
     * @author zcq
     * @param intakeId
     * @return
     */
    List<PregIntakeDetailPojo> queryIntakeDetailByIntakeId(String intakeId);

    /**
     * 条件检索出一条膳食清单信息包含明细信息
     * 
     * @author zcq
     * @param condition
     * @return
     */
    PregIntakePojo queryIntakeAndDetailByCondition(@Valid @NotNull IntakeCondition condition);

    /**
     * 获取摄入量模版明细信息
     * 
     * @author zcq
     * @param id
     * @return
     */
    PregIntakeDetailPojo getIntakeDetail(String id);

    /**
     * 添加摄入量模版明细
     * 
     * @author zcq
     * @param intakeDetail
     * @return
     */
    String addIntakeDetail(PregIntakeDetail intakeDetail);

    /**
     * 修改摄入量模版明细
     * 
     * @author zcq
     * @param intakeDetail
     */
    void updateIntakeDetail(PregIntakeDetail intakeDetail);

    /**
     * 删除摄入量模版明细
     * 
     * @author zcq
     * @param id
     */
    void removeIntakeDetailByIntakeId(String intakeId);
}

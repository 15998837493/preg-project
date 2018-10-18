
package com.mnt.preg.master.service.preg;

import java.util.List;

import com.mnt.preg.master.entity.preg.IntakeType;
import com.mnt.preg.master.pojo.preg.IntakeTypePojo;

/**
 * 膳食干预方案-摄入类型Service
 * 
 * @author wsy
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-3-22 wsy 初版
 */
public interface PregIntakeTypeService {

    /**
     * 查询摄入量类型信息
     * 
     * @author zcq
     * @param condition
     * @return
     */
    List<IntakeTypePojo> queryIntakeTypeByCondition(IntakeType condition);

    /**
     * 获取摄入量类型信息
     * 
     * @author zcq
     * @param id
     * @return
     */
    IntakeTypePojo getIntakeType(String id);

    /**
     * 添加摄入量类型
     * 
     * @author zcq
     * @param intakeType
     * @return IntakeTypeVo
     */
    IntakeTypePojo addIntakeType(IntakeType intakeType);

    /**
     * 修改摄入量类型
     * 
     * @author zcq
     * @param intakeType
     * @return IntakeTypeVo
     */
    IntakeTypePojo updateIntakeType(IntakeType intakeType);

    /**
     * 删除摄入量类型
     * 
     * @author zcq
     * @param id
     */
    void removeIntakeType(String id);
}

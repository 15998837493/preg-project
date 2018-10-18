
package com.mnt.preg.system.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import com.mnt.preg.system.entity.SystemParam;
import com.mnt.preg.system.pojo.SystemParamPojo;

/**
 * 系统参数事务
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：v1.0 2014-12-18 zcq 初版
 */
@Validated
public interface SystemParamService {

    /**
     * 查询系统参数--通用检索
     * 
     * <dl>
     * <dt>1 功能描述</dt>
     * <dd>查询系统参数</dd>
     * </dl>
     * 
     * @author zcq
     * @param condition
     *            条件
     * @return 系统参数
     */
    List<SystemParamPojo> querySystemParam(SystemParam condition);

    /**
     * 查询系统参数--根据【参数主键】
     * 
     * <dl>
     * <dt>1 功能描述</dt>
     * <dd>查询系统参数</dd>
     * </dl>
     * 
     * @author zcq
     * @param paramId
     *            参数主键
     * @return 系统参数
     */
    SystemParamPojo getSystemParam(@NotBlank String paramId);

    /**
     * 修改系统参数
     * 
     * <dl>
     * <dt>1 功能描述</dt>
     * <dd>修改系统参数</dd>
     * </dl>
     * 
     * @author zcq
     * @param systemParam
     *            系统参数
     */
    void updateSystemParam(@Valid @NotNull SystemParam systemParam);
}

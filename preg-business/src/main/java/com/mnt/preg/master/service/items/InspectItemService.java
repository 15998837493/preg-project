
package com.mnt.preg.master.service.items;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import com.mnt.preg.master.condition.items.InspectItemCondition;
import com.mnt.preg.master.pojo.items.InspectItemPojo;

/**
 * 
 * 系统检查项目接口
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-4-5 gss 初版
 */
@Validated
public interface InspectItemService {

    /**
     * 
     * 检查项目查询
     * <dl>
     * <dt>1 功能描述</dt>
     * <dd>检查项目查询</dd>
     * </dl>
     * 
     * @author gss
     * @param condition
     *            查询条件
     */
    List<InspectItemPojo> queryInspectItem(InspectItemCondition condition);

    /**
     * 
     * 增加检查项目记录
     * 
     * @author gss
     * @param itemVo
     * @return 主键
     */
    String saveInspectItem(@Valid @NotNull InspectItemPojo inspectItemVo);

    /**
     * 
     * 修改检查项目记录
     * 
     * @author gss
     * @param itemVo
     * @param id
     *            主键
     */
    void updateInspectItem(InspectItemPojo inspectItemVo, @NotBlank String id);

    /**
     * 
     * 根据主键删除检查项目记录
     * 
     * @author gss
     * @param id
     */
    void removeInspectItem(@NotBlank String id);

    /**
     * 
     * 根据主键查询检查项目表
     * <dl>
     * <dt>1 功能描述</dt>
     * <dd>根据主键查询检查项目表</dd>
     * </dl>
     * 
     * @author gss
     * @param id
     *            主键
     */
    InspectItemPojo getInspectItemById(@NotBlank String id);

    /**
     * 检查指标是否重复
     * 
     * @author xdc
     * @param inspectCode
     * @return
     */
    Integer checkInspctItemCodeValid(@NotBlank String inspectCode);

    /**
     * 编号获取检查项目
     * 
     * @author xdc
     * @param inspectCode
     * @return
     */
    InspectItemPojo getInspectItemByInspectCode(@NotBlank String inspectCode);
}

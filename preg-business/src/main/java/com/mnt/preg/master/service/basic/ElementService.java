
package com.mnt.preg.master.service.basic;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import com.mnt.preg.master.entity.basic.Element;
import com.mnt.preg.master.pojo.basic.ElementPojo;

@Validated
public interface ElementService {

    /**
     * 
     * 增加元素记录
     * 
     * @author gss
     * @param nutrient
     * @return NutrientVo
     */
    ElementPojo saveNutrient(@Valid @NotNull Element nutrient);

    /**
     * 
     * 修改元素记录
     * 
     * @author gss
     * @param nutrientVo
     * @param nutrientId
     *            主键
     */
    ElementPojo updateNutrient(@Valid @NotNull Element nutrient);

    /**
     * 
     * 根据主键删除元素记录
     * 
     * @author gss
     * @param id
     */
    void removeNutrient(@NotBlank String id);

    /**
     * 
     * 元素记录查询
     * <dl>
     * <dt>1 功能描述</dt>
     * <dd>元素记录查询</dd>
     * </dl>
     * 
     * @author gss
     * @param condition
     *            查询条件
     */
    List<ElementPojo> queryNutrient(Element condition);

    /**
     * 
     * 根据nutrientId查询问题基本信息
     * 
     * @author gss
     * @param nutrientId
     * @return
     */
    ElementPojo getNutrientByNutrientId(@NotBlank String nutrientId);

}

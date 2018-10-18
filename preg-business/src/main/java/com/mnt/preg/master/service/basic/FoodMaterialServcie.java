/*
 * FoodMaterialServcie.java    1.0  2018-1-25
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.master.service.basic;

import java.util.List;

import com.mnt.preg.master.condition.basic.FoodMaterialCondition;
import com.mnt.preg.master.entity.basic.FoodMaterial;
import com.mnt.preg.master.entity.basic.FoodMaterialCode;
import com.mnt.preg.master.pojo.basic.FoodMaterialPojo;
import com.mnt.preg.master.pojo.basic.NutrientAmountPojo;



/**
 * 食材Service
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-25 scd 初版
 */
public interface FoodMaterialServcie {

    /**
     * 条件检索食材信息(分页)
     * 
     * @param condition
     * @return FoodMaterialListVo
     * 
     */
    FoodMaterialCondition queryFoodMaterialByPage(FoodMaterialCondition condition);

    /**
     * 添加食材信息
     * 
     * @param foodMaterial
     * @param foodMaterialExt
     * @return fmId
     * 
     */
    String addFoodMaterial(FoodMaterial foodMaterial, List<NutrientAmountPojo> listExt, String insId);

    /**
     * 修改食材信息
     * 
     * @param fmId
     * @param foodMaterial
     * @param foodMaterialExt
     * 
     */
    void updateFoodMaterial(FoodMaterial foodMaterial, List<NutrientAmountPojo> listExt);

    /**
     * 删除食材信息
     * 
     * @author zcq
     * @param fmId
     */
    void removeFoodMaterial(String fmId);

    /**
     * 添加食材uuid对应编码关系
     * 
     * @param foodMaterialCode
     * @return id
     */
    String addFoodMaterialCode(FoodMaterialCode foodMaterialCode);

    /**
     * 条件检索食材信息
     * 
     * @param condition
     * @return FoodMaterialListVo
     * 
     */
    List<FoodMaterialPojo> queryFoodMaterial(FoodMaterial condition);

    /**
     * 通过foodId获取食材信息以及食材的元素信息
     * 
     * @param foodId
     * @return
     * 
     */
//    FoodMaterialPojo getFoodMaterialAndExtByFmId(String fmId);

    /**
     * 
     * 验证食材类型中是否有食材配置
     * 
     * @author scd
     * @param parentTreeId
     * @return
     */
    Integer checkFoodTreeAmount(String treeId);

    /**
     * 
     * 根据主键获取食材元素数据
     * 
     * @author scd
     * @param fmId
     * @return
     */
    List<NutrientAmountPojo> getFoodMaterialExt(String fmId);
    
    /**
     * 
     * 查询与当前食材类型相同的食材
     *
     * @author zhang_jing
     * @param fmId
     * @return
     */
    List<FoodMaterialPojo> queryTypeFoodMaterials(String fmId);
    
    /**
     * 获取食材基本信息
     * @param fmId
     * @return
     */
    FoodMaterial getFm(String fmId);
}

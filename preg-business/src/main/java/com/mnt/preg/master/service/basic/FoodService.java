/*
 * FoodService.java	 1.0   2015-1-15
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.master.service.basic;

import java.util.List;

import com.mnt.preg.examitem.condition.FoodCondition;
import com.mnt.preg.examitem.pojo.EvaluateFoodLibraryPojo;
import com.mnt.preg.master.entity.basic.FoodInfo;
import com.mnt.preg.master.entity.basic.FoodMaterialList;
import com.mnt.preg.master.pojo.basic.FoodMaterialListInfoPojo;
import com.mnt.preg.master.pojo.basic.FoodPojo;

/**
 * 食物相关数据
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2015-1-15 zy 初版
 */
public interface FoodService {

    /**
     * 食物-检索食物基本信息
     * 
     * @param condition
     *            检索条件
     * @return FoodListVo
     */
    List<FoodPojo> queryFood(FoodCondition condition);

    /**
     * 分页查询食物
     */
    FoodCondition queryFoodForPage(FoodCondition condition);

    /**
     * 食物-通过主键获取食物基本信息
     * 
     * @param foodId
     *            主键
     * @return FoodVo
     */
    List<FoodMaterialListInfoPojo> queryFoodMaterialListByFoodId(String foodId);

    /**
     * 食物-通过主键获取食物与食材基本信息
     * 
     * @param foodId
     *            主键
     * @return FoodVo
     */
    FoodPojo getFoodById(String foodId);

    /**
     * 通过食物ID获取食物信息和食物元素信息
     * 
     * @param foodId
     * @return FoodListVo
     * 
     */
    /**
     * 待删除
     */
    // FoodPojo getFoodAndExtByFoodId(String foodId);

    /**
     * 添加食物信息
     * 
     * @param food
     * @param foodExt
     * @return foodId
     * 
     */
    String addFood(FoodInfo food, List<FoodMaterialList> fmlList, String insId);

    /**
     * 修改食谱信息
     * 
     * @param foodId
     * @param food
     * @param foodExt
     * 
     */
    void updateFood(FoodInfo food, List<FoodMaterialList> fmlList);

    /**
     * 删除食谱信息
     * 
     * @author zcq
     * @param foodId
     */
    void removeFood(String foodId);

    /**
     * 食物-检索食物信息
     * 
     * @param condition
     *            检索条件
     * @return FoodBaseListVo
     */

     FoodCondition queryFoodBaseForPage(FoodCondition condition);


    /**
     * 
     * 查询评估食物库信息
     * 
     * @author mnt_zhangjing
     * @return
     */
    List<EvaluateFoodLibraryPojo> queryEvaluateFoodLibrary();

    /**
     * 
     * 验证食材类型中是否有食材配置
     * 
     * @author dhs
     * @return
     */
    Integer checkFoodTreeAmount(String treeId);

}

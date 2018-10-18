/*
 * FoodServiceImpl.java	 1.0   2015-1-15
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.master.service.basic;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.utils.HanyuToPinyin;
import com.mnt.preg.examitem.condition.FoodCondition;
import com.mnt.preg.examitem.pojo.EvaluateFoodLibraryPojo;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.main.utils.SQLSymbol;
import com.mnt.preg.master.dao.basic.FoodDAO;
import com.mnt.preg.master.dao.basic.FoodMaterialDAO;
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
@Service
public class FoodServiceImpl extends BaseService implements FoodService {

    /** 食物管理 */
    @Resource
    private FoodDAO foodDAO;

    /** 食材管理 */
    @Resource
    private FoodMaterialDAO foodMaterialDAO;

    @Override
    @Transactional(readOnly = true)
    public List<FoodPojo> queryFood(FoodCondition condition) {
        if (condition == null) {
            condition = new FoodCondition();
        }
        List<FoodPojo> foodVos = foodDAO.queryFood(condition);
        return foodVos;
    }

    @Override
    @Transactional(readOnly = true)
    public FoodCondition queryFoodForPage(FoodCondition condition) {
        if (condition == null) {
            condition = new FoodCondition();
        }
        return foodDAO.queryFoodForPage(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public FoodPojo getFoodById(String foodId) {
        FoodCondition condition = new FoodCondition();
        condition.setFoodId(foodId);
        List<FoodPojo> foodVos = foodDAO.queryFood(condition);

        return foodVos == null || foodVos.size() == 0 ? null : foodVos.get(0);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addFood(FoodInfo food, List<FoodMaterialList> fmlList, String insId) {
        // 保存食物主表信息
        food.setFoodSSpell(HanyuToPinyin.getInstance().getAbbrStringPinYin(food.getFoodName()));
        food.setFoodASpell(HanyuToPinyin.getInstance().getFullStringPinYin(food.getFoodName()));
        food.setFoodPic(food.getFoodASpell() + ".jpg");
        food.setFoodCounts(0);// 不能为null，随便加的
        String foodId = (String) foodDAO.save(food);
        // 保存食物元素信息
        // foodExt.setFoodId(foodId);
        // foodDAO.save(foodExt);
        // 保存食物的食材组成信息
        if (fmlList != null && fmlList.size() > 0) {
            for (FoodMaterialList fml : fmlList) {
                fml.setFoodId(foodId);
                foodDAO.save(fml);
            }
        }
        return foodId;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateFood(FoodInfo food, List<FoodMaterialList> fmlList) {
        String foodId = food.getFoodId();
        // 修改食材信息
        List<HQLConditionParam> materialParams = new ArrayList<HQLConditionParam>();
        materialParams.add(new HQLConditionParam("foodId", SQLSymbol.EQ.toString(), foodId));
        foodDAO.updateHQL(food, materialParams);

        // 修改食物的组成信息
        foodDAO.deleteFmlByFoodId(foodId);
        if (fmlList != null && fmlList.size() > 0) {
            for (FoodMaterialList fml : fmlList) {
                fml.setFoodId(foodId);
                foodDAO.save(fml);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void removeFood(String foodId) {
        FoodInfo food = new FoodInfo();
        food.setFoodId(foodId);
        food.setFlag(0);
        // 修改食材信息
        List<HQLConditionParam> materialParams = new ArrayList<HQLConditionParam>();
        materialParams.add(new HQLConditionParam("foodId", SQLSymbol.EQ.toString(), foodId));
        foodDAO.updateHQL(food, materialParams);
    }

    @Override
    @Transactional(readOnly = true)
    public FoodCondition queryFoodBaseForPage(FoodCondition condition) {
        return foodDAO.queryFoodBaseForPage(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EvaluateFoodLibraryPojo> queryEvaluateFoodLibrary() {
        return foodDAO.queryEvaluateFoodLibrary();
    }

    @Override
    @Transactional(readOnly = true)
    public Integer checkFoodTreeAmount(String treeId) {
        return foodDAO.countField("foodTreeType", treeId, FoodInfo.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FoodMaterialListInfoPojo> queryFoodMaterialListByFoodId(String foodId) {
        return foodMaterialDAO.queryFoodMaterialListByFoodId(foodId);
    }

}

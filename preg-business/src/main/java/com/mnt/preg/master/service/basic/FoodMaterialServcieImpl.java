/*
 * FoodMaterialServcieImpl.java    1.0  2018-1-25
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.master.service.basic;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.utils.HanyuToPinyin;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.main.utils.SQLSymbol;
import com.mnt.preg.master.condition.basic.FoodMaterialCondition;
import com.mnt.preg.master.dao.basic.FoodDAO;
import com.mnt.preg.master.dao.basic.FoodMaterialDAO;
import com.mnt.preg.master.dao.basic.ProductDAO;
import com.mnt.preg.master.entity.basic.FoodMaterial;
import com.mnt.preg.master.entity.basic.FoodMaterialCode;
import com.mnt.preg.master.entity.basic.NutrientAmount;
import com.mnt.preg.master.pojo.basic.FoodMaterialPojo;
import com.mnt.preg.master.pojo.basic.NutrientAmountPojo;

/**
 * 食材业务类
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-25 scd 初版
 */
@Service
public class FoodMaterialServcieImpl extends BaseService implements FoodMaterialServcie {
	
	private static final String corresType = "foodMaterial"; 

    /** 食材管理 */
    @Resource
    private FoodMaterialDAO foodMaterialDAO;

    /** 食物管理 */
    @Resource
    private FoodDAO foodDAO;
    
    @Resource
    private ProductDAO productDAO;

    @Override
    @Transactional(readOnly = true)
    public FoodMaterialCondition queryFoodMaterialByPage(FoodMaterialCondition condition) {
        if (condition == null) {
            condition = new FoodMaterialCondition();
        }
        FoodMaterialCondition fmVos = foodMaterialDAO.queryFoodMaterialByPage(condition);
        @SuppressWarnings("unchecked")
        List<FoodMaterialPojo> data = (List<FoodMaterialPojo>) fmVos.getData();
        for (FoodMaterialPojo foodMaterialPojo : data) {
            // 食材元素信息
        	String fmId = foodMaterialPojo.getFmId();
            List<NutrientAmountPojo> elementList = new ArrayList<NutrientAmountPojo>();
            elementList = foodMaterialDAO.queryElements(fmId);
            foodMaterialPojo.setProductElementList(elementList);
        }
        return fmVos;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addFoodMaterial(FoodMaterial foodMaterial, List<NutrientAmountPojo> listExt, String insId) {
        foodMaterial.setFmSSpell(HanyuToPinyin.getInstance().getAbbrStringPinYin(foodMaterial.getFmName()));
        foodMaterial.setFmASpell(HanyuToPinyin.getInstance().getFullStringPinYin(foodMaterial.getFmAlias()));
        String fmId = (String) foodDAO.save(foodMaterial);
        for (NutrientAmountPojo nutrientAmountPojo : listExt) {
        	if (nutrientAmountPojo.getNutrientDosage() != null) {
            	NutrientAmount nutrientAmount = new NutrientAmount();
            	BeanUtils.copyProperties(nutrientAmountPojo, nutrientAmount);
            	nutrientAmount.setCorresId(fmId);
            	nutrientAmount.setCorresType(corresType);
                foodDAO.save(nutrientAmount);
        	}
        }
        return fmId;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateFoodMaterial(FoodMaterial foodMaterial, List<NutrientAmountPojo> listExt) {
        String fmId = foodMaterial.getFmId();
        // 修改食材信息
        List<HQLConditionParam> materialParams = new ArrayList<HQLConditionParam>();
        materialParams.add(new HQLConditionParam("fmId", SQLSymbol.EQ.toString(), fmId));
        foodDAO.updateHQL(foodMaterial, materialParams);
        // 修改元素信息
        productDAO.deleteProductElementByProductId(fmId);
        if (!CollectionUtils.isEmpty(listExt)) {
            for (NutrientAmountPojo productElementPojo : listExt) {
            	NutrientAmount nutrientAmount = new NutrientAmount();
            	BeanUtils.copyProperties(productElementPojo, nutrientAmount);            	
                if (nutrientAmount.getNutrientDosage() != null) {
                	nutrientAmount.setCorresId(fmId);
                	nutrientAmount.setCorresType(corresType);                   
                    foodDAO.save(nutrientAmount);
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void removeFoodMaterial(String fmId) {
        FoodMaterial fm = new FoodMaterial();
        fm.setFmId(fmId);
        fm.setFlag(0);
        this.updateFoodMaterial(fm, null);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addFoodMaterialCode(FoodMaterialCode foodMaterialCode) {
        return (String) foodDAO.save(foodMaterialCode);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FoodMaterialPojo> queryFoodMaterial(FoodMaterial condition) {
        if (condition == null) {
            condition = new FoodMaterial();
        }
        List<FoodMaterialPojo> fmVos = foodMaterialDAO.queryFoodMaterial(condition);
        return fmVos;
    }

//    @Override
//    @Transactional(readOnly = true)
//    public FoodMaterialPojo getFoodMaterialAndExtByFmId(String fmId) {
//        FoodMaterialPojo foodMaterialVo = new FoodMaterialPojo();
//        // 食材信息
//        FoodMaterial condition = new FoodMaterial();
//        condition.setFmId(fmId);
//        List<FoodMaterialPojo> foodMaterialVos = foodMaterialDAO.queryFoodMaterial(condition);
//        foodMaterialVo = foodMaterialVos.get(0);
//        // 食材元素信息
//        FoodMaterialExt foodMaterialExtVo = foodMaterialDAO.get(FoodMaterialExt.class, fmId);
//        foodMaterialVo.setFoodMaterialExtVo(foodMaterialExtVo);
//        return foodMaterialVo;
//    }

    @Override
    @Transactional(readOnly = true)
    public Integer checkFoodTreeAmount(String treeId) {
        return foodMaterialDAO.countField("fmType", treeId, FoodMaterial.class);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<FoodMaterialPojo> queryTypeFoodMaterials(String fmId) {
        FoodMaterial foodMaterial = foodMaterialDAO.get(FoodMaterial.class, fmId);
        return foodMaterialDAO.queryFoodMaterialByType(StringUtils.substring(foodMaterial.getFmType(), 0, 3));
    }

	@Override
	@Transactional(readOnly = true)
	public List<NutrientAmountPojo> getFoodMaterialExt(String fmId) {
		return foodMaterialDAO.queryElements(fmId);
	}
	
    @Override
    @Transactional(readOnly = true)
    public FoodMaterial getFm(String fmId) {
    	return foodMaterialDAO.get(FoodMaterial.class, fmId);
    }
}

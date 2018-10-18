
package com.mnt.preg.master.service.basic;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.core.exception.ServiceException;
import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.core.utils.HQLSymbol;
import com.mnt.preg.main.results.ResultCode;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.master.condition.basic.ProductCondition;
import com.mnt.preg.master.dao.basic.ProductDAO;
import com.mnt.preg.master.entity.basic.NutrientAmount;
import com.mnt.preg.master.entity.basic.Product;
import com.mnt.preg.master.pojo.basic.ElementPojo;
import com.mnt.preg.master.pojo.basic.NutrientAmountPojo;
import com.mnt.preg.master.pojo.basic.ProductPojo;

/**
 * 商品表接口实现
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-4-5 gss 初版
 */
@Service
public class ProductServiceImpl extends BaseService implements ProductService {
	
	private static final String corresType = "product";

    @Resource
    private ProductDAO productDAO;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    public List<ProductPojo> queryProduct(ProductCondition condition) {
        return productDAO.queryProduct(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductPojo> queryProductByCondition(ProductCondition condition) {
        return productDAO.queryProduct(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductPojo> queryProductAndElementsByCondition(ProductCondition condition) {
        List<ProductPojo> productList = productDAO.queryProduct(condition);
        for (ProductPojo vo : productList) {
            List<NutrientAmountPojo> elementList = productDAO.queryProductElementByProductId(vo.getProductId());
            vo.setProductElementList(elementList);
            // 获取三大营养素及热量
            for (NutrientAmountPojo eleVo : elementList) {
                // 能量
                if ("E00001".equals(eleVo.getNutrientId())) {
                    vo.setUnitEnergy(eleVo.getNutrientDosage());
                }
                // 蛋白质
                if ("E00002".equals(eleVo.getNutrientId())) {
                    vo.setUnitProtein(eleVo.getNutrientDosage());
                }
                // 脂肪
                if ("E00003".equals(eleVo.getNutrientId())) {
                    vo.setUnitFat(eleVo.getNutrientDosage());
                }
                // 碳水化合物
                if ("E00004".equals(eleVo.getNutrientId())) {
                    vo.setUnitCbr(eleVo.getNutrientDosage());
                }
            }
        }
        return productList;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public ProductPojo addProduct(Product product) {
        LOGGER.info("【事物层】保存商品记录");
        product.setProductScore(0);
        String productId = (String) productDAO.save(product);
        return productDAO.getTransformerBean(productId, Product.class, ProductPojo.class);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public ProductPojo updateProduct(Product product) {
        if (product == null || StringUtils.isEmpty(product.getProductId())) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        LOGGER.info("【事物层】更新商品记录");

        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("product_id", HQLSymbol.EQ.toString(), product.getProductId()));
        int count = productDAO.updateHQL(product, conditionParams);
        if (count != 1) {
            throw new ServiceException(ResultCode.ERROR_99998);
        }

        return productDAO.getTransformerBean(product.getProductId(), Product.class, ProductPojo.class);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void removeProduct(String id) {
        Product product = (Product) productDAO.get(Product.class, id);
        product.setFlag(0);
        LOGGER.info("【事物层】删除商品记录:id" + id);
        this.updateProduct(product);
        productDAO.deleteProductElementByProductId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductPojo getProductById(String id) {
        LOGGER.info("【事物层】根据id查询商品记录");
        return productDAO.getTransformerBean(id, Product.class,
                ProductPojo.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NutrientAmountPojo> queryProductElementByProductId(String productId) {
        return productDAO.queryProductElementByProductId(productId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void saveProductElement(List<NutrientAmount> productElements, String productId) {
        if (CollectionUtils.isEmpty(productElements) || StringUtils.isEmpty(productId)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        productDAO.deleteProductElementByProductId(productId);
        for (NutrientAmount productElement : productElements) {
            if (productElement.getNutrientDosage() != null) {
                productElement.setCorresId(productId);
                productElement.setCorresType(corresType);
                
                productDAO.save(productElement);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteProductElementByProductId(String productId) {
        if (StringUtils.isEmpty(productId)) {
            return;
        }
        productDAO.deleteProductElementByProductId(productId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public List<ElementPojo> queryProductElement(List<String> productIdList) {
        return productDAO.queryProductElement(productIdList);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public Integer validProductName(String productName) {
        return this.validCode("productName", productName, Product.class);
    }
}

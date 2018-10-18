
package com.mnt.preg.master.service.basic;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.core.exception.ServiceException;
import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.core.utils.HQLSymbol;
import com.mnt.preg.main.results.ResultCode;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.master.dao.basic.ProductCatalogDAO;
import com.mnt.preg.master.entity.basic.ProductCatalog;
import com.mnt.preg.master.pojo.basic.ProductCatalogPojo;
import com.mnt.health.utils.HanyuToPinyin;
import com.mnt.health.utils.beans.TransformerUtils;

/**
 * 商品类别事务
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-12-18 gss 初版
 */
@Service
public class ProductCatalogServiceImpl extends BaseService implements ProductCatalogService {

    @Resource
    private ProductCatalogDAO productCatalogDAO;

    @Override
    @Transactional(readOnly = true)
    public List<ProductCatalogPojo> queryProductCatalog() {
        return productCatalogDAO.queryProductCatalog(null);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductCatalogPojo getProductCatalog(String catalogId) {
        return productCatalogDAO.getTransformerBean(catalogId, ProductCatalog.class, ProductCatalogPojo.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer checkCatalogIdValid(String catalogId) {
        return this.validCode("catalogId", catalogId, ProductCatalog.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer checkCatalogNameValid(String catalogName) {
        return this.validCode("catalogName", catalogName, ProductCatalog.class);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public ProductCatalogPojo addProductCatalog(ProductCatalog catalogBo) {
        ProductCatalog catalog = TransformerUtils.transformerProperties(ProductCatalog.class, catalogBo);
        String catalogId = addProductCatalog_help(catalog);
        return getProductCatalog(catalogId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addProductCatalog_help(ProductCatalog catalog) {
        // 主键：三位一级，父级编码+排序（补充到三位），例如 一级=001，二级=001001
        HanyuToPinyin hanyuToPinyin = new HanyuToPinyin();
        catalog.setCatalogPinyin(hanyuToPinyin.getAbbrStringPinYin(catalog.getCatalogName()));
        catalog.setCatalogId(createProductCatalogId(catalog.getCatalogParentId()));
        if (checkCatalogNameValid(catalog.getCatalogId()) > 0) {
            throw new ServiceException(ResultCode.ERROR_90002);
        }
        return (String) productCatalogDAO.save(catalog);
    }

    /**
     * 生成功能菜单主键
     * 
     * @author zcq
     * @param catalogParent
     * @return
     */
    private String createProductCatalogId(String catalogParent) {
        String maxProductCatalogId = (String) productCatalogDAO.getSonMaxProductCatalogId(catalogParent);
        String code = StringUtils.isEmpty(maxProductCatalogId) ? "001" : String.valueOf(Integer
                .valueOf(maxProductCatalogId.substring(
                        maxProductCatalogId.length() - 3, maxProductCatalogId.length())) + 1);
        int length = code.length();
        if (length < 3) {
            for (int i = 0; i < 3 - length; i++) {
                code = "0" + code;
            }
        }
        return "000".equals(catalogParent) ? code : (catalogParent + code);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateProductCatalog(ProductCatalog catalog) {
        String catalogId = catalog.getCatalogId();
        if (StringUtils.isEmpty(catalogId)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        // 设置检索条件
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("catalog_id", HQLSymbol.EQ.toString(), catalogId));
        productCatalogDAO.updateHQL(catalog, conditionParams);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteProductCatalog(String catalogId) {
        productCatalogDAO.deleteProductCatalog(catalogId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void editProductCatalogOrder(List<String> catalogIdList) {
        if (CollectionUtils.isNotEmpty(catalogIdList)) {
            for (int i = 1; i <= catalogIdList.size(); i++) {
                productCatalogDAO.updateProductCatalogOrder(catalogIdList.get(i - 1), i);
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductCatalogPojo> getProductCatalog_returnlist(ProductCatalog condition) {
        return getProductCatalogTreeList(productCatalogDAO.queryProductCatalog(condition));
    }

    /**
     * 把所有菜单按父子级分类
     * 
     * @author zcq
     * @return
     */
    private List<ProductCatalogPojo> getProductCatalogTreeList(List<ProductCatalogPojo> catalogList) {
        // 把所有菜单按父子级分类
        Map<String, List<ProductCatalogPojo>> catalogTreeMap = new LinkedHashMap<String, List<ProductCatalogPojo>>();
        if (CollectionUtils.isNotEmpty(catalogList)) {
            for (ProductCatalogPojo catalog : catalogList) {
                String parentCode = catalog.getCatalogParentId();
                if (!catalogTreeMap.containsKey(parentCode)) {
                    catalogTreeMap.put(parentCode, new ArrayList<ProductCatalogPojo>());
                }
                catalogTreeMap.get(parentCode).add(catalog);
            }
        }
        // 获取一级目录
        List<ProductCatalogPojo> oneProductCatalogList = catalogTreeMap.get("000");
        // 逐级填充完善菜单信息
        if (CollectionUtils.isNotEmpty(oneProductCatalogList)) {
            // 递归填充childList
            for (ProductCatalogPojo oneProductCatalog : oneProductCatalogList) {
                catalogRecursion(oneProductCatalog, catalogTreeMap);
            }
            // 去除没有子级的目录
            // checkProductCatalog(oneProductCatalogList);
        }
        return oneProductCatalogList;
    }

    /**
     * 递归填充childList
     * 
     * @author zcq
     * @param catalog
     * @param catalogMap
     */
    private void catalogRecursion(ProductCatalogPojo catalog, Map<String, List<ProductCatalogPojo>> catalogMap) {
        List<ProductCatalogPojo> catalogList = catalogMap.get(catalog.getCatalogId());
        if (CollectionUtils.isNotEmpty(catalogList)) {
            for (ProductCatalogPojo nextGradeProductCatalog : catalogList) {
                catalogRecursion(nextGradeProductCatalog, catalogMap);
            }
            catalog.setChildList(catalogList);
        }
    }

}

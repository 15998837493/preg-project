/*
 * MasCatalogServiceImpl.java    1.0  2017-10-20
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.system.service;

import java.util.ArrayList;
import java.util.List;

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
import com.mnt.preg.master.entity.basic.ProductCatalog;
import com.mnt.preg.system.dao.CatalogInfoDAO;
import com.mnt.preg.system.entity.CatalogInfo;
import com.mnt.preg.system.pojo.CatalogInfoPojo;

/**
 * 业务管理业务接口实现类
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-10-20 scd 初版
 */
@Service
public class CatalogInfoServiceImpl extends BaseService implements CatalogInfoService {

    @Resource
    private CatalogInfoDAO catalogInfoDAO;// 系统体检项目DAO

    @Override
    @Transactional(readOnly = true)
    public List<CatalogInfoPojo> queryCatalogByType(CatalogInfo condition) {
        return catalogInfoDAO.queryCatalogByType(condition);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public CatalogInfoPojo addCatalogInfo(CatalogInfo catalogInfo) {
        String catalogId = addCatalogInfoForRule(catalogInfo);
        return getProductCatalog(catalogId);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer checkCatalogIdValid(String catalogName) {
        return this.validCode("catalogName", catalogName, ProductCatalog.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer checkCatalogNameValid(String catalogName) {
        return this.validCode("catalogName", catalogName, CatalogInfo.class);
    }

    @Override
    @Transactional(readOnly = true)
    public CatalogInfoPojo getProductCatalog(String catalogId) {
        return catalogInfoDAO.getTransformerBean(catalogId, CatalogInfo.class, CatalogInfoPojo.class);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateCatalogInfo(CatalogInfo catalogInfo) {
        String catalogId = catalogInfo.getCatalogId();
        if (StringUtils.isEmpty(catalogId)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        // 设置检索条件
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("catalog_id", HQLSymbol.EQ.toString(), catalogId));
        catalogInfoDAO.updateHQL(catalogInfo, conditionParams);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteCatalogInfo(String catalogId) {
        catalogInfoDAO.deleteCatalogInfo(catalogId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void editCatalogInfoOrder(List<String> catalogIdList) {
        if (CollectionUtils.isNotEmpty(catalogIdList)) {
            for (int i = 1; i <= catalogIdList.size(); i++) {
                catalogInfoDAO.updateCatalogInfoOrder(catalogIdList.get(i - 1), i);
            }
        }
    }

    public String addCatalogInfoForRule(CatalogInfo catalogInfo) {
        // 主键：三位一级，父级编码+排序（补充到三位），例如 一级=001，二级=001001
        catalogInfo.setCatalogId(createProductCatalogId(catalogInfo.getCatalogParentId()));
        if (checkCatalogIdValid(catalogInfo.getCatalogId()) > 0) {
            throw new ServiceException(ResultCode.ERROR_90002);
        }
        return (String) catalogInfoDAO.save(catalogInfo);
    }

    /**
     * 生成功能菜单主键
     * 
     * @author zcq
     * @param catalogParent
     * @return
     */
    private String createProductCatalogId(String catalogParent) {
        String maxProductCatalogId = (String) catalogInfoDAO.getSonMaxProductCatalogId(catalogParent);
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
}

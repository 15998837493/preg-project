/*
 * MasCatalogService.java    1.0  2017-10-20
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.system.service;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import com.mnt.preg.system.entity.CatalogInfo;
import com.mnt.preg.system.pojo.CatalogInfoPojo;

/**
 * 类别管理业务接口
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-10-20 scd 初版
 */
@Validated
public interface CatalogInfoService {

    /**
     * 
     * 条件检索类别数据
     * 
     * @author scd
     * @param condtion
     * @return
     */
    List<CatalogInfoPojo> queryCatalogByType(CatalogInfo condition);

    /**
     * 
     * 添加类别
     * 
     * @author scd
     * @param catalogInfo
     * @return
     */
    CatalogInfoPojo addCatalogInfo(CatalogInfo catalogInfo);

    /**
     * 
     * 验证类别主键是否重复
     * 
     * @author scd
     * @param catalogName
     * @return
     */
    Integer checkCatalogIdValid(String catalogName);

    /**
     * 验证类别名称是否重复
     * 
     * @author scd
     * @param catalogName
     * 
     * @return Integer
     */
    Integer checkCatalogNameValid(String catalogName);

    /**
     * 
     * 根据主键获取类别
     * 
     * @author scd
     * @param catalogId
     * @return
     */
    CatalogInfoPojo getProductCatalog(String catalogId);

    /**
     * 修改类别
     * 
     * @author scd
     * @param catalogInfo
     */
    void updateCatalogInfo(CatalogInfo catalogInfo);

    /**
     * 删除类别
     * 
     * @author scd
     * @param catalogId
     */
    void deleteCatalogInfo(@NotBlank String catalogId);

    /**
     * 修改商品类别排序
     * 
     * @author scd
     * @param productCatalogIdList
     */
    void editCatalogInfoOrder(List<String> catalogIdList);
}

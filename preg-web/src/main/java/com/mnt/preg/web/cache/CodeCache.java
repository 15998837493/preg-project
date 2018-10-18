
package com.mnt.preg.web.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.mnt.preg.system.entity.CodeInfo;
import com.mnt.preg.system.pojo.CodePojo;
import com.mnt.preg.system.service.CodeService;

/**
 * 代码表缓存
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-15 zcq 初版
 */
@Component
public class CodeCache {

    @Resource
    private CodeService codeService;

    /**
     * 初始化代码表缓存
     * 
     * @author zcq
     */
    public void initCodeCache() {
        List<CodePojo> codeList = codeService.queryCode(null);
        if (CollectionUtils.isNotEmpty(codeList)) {
            Map<String, List<CodePojo>> mapParent = new HashMap<String, List<CodePojo>>();
            Map<String, CodePojo> mapAll = new HashMap<String, CodePojo>();
            for (CodePojo code : codeList) {
                String cacheKey = code.getParentCodeKind() + code.getParentCodeValue();
                if (!mapParent.containsKey(cacheKey)) {
                    mapParent.put(cacheKey, new ArrayList<CodePojo>());
                }
                mapParent.get(cacheKey).add(code);
                mapAll.put(code.getCodeKind() + code.getCodeValue(), code);
            }
            this.addCodeMapCache(mapAll);
            for (String key : mapParent.keySet()) {
                this.addCodeListCache(key + "_list", mapParent.get(key));
            }
        }
    }

    /**
     * 从缓存中获取代码表列表信息
     * 
     * @author zcq
     * @param codeKind
     * @param codeValue
     * @return
     */
    @Cacheable(value = "codeCache", key = "#codeKind.concat(#codeValue).concat('_list')")
    public List<CodePojo> getCodeListCache(String codeKind, String codeValue) {
        CodeInfo condition = new CodeInfo();
        condition.setParentCodeKind(codeKind);
        condition.setParentCodeValue(codeValue);
        return codeService.queryCode(condition);
    }

    /**
     * 从缓存中获取代码表信息
     * 
     * @author zcq
     * @return
     */
    @Cacheable(value = "codeCache", key = "'allCodeMap'")
    public Map<String, CodePojo> getCodeMapCache() {
        Map<String, CodePojo> mapAll = new HashMap<String, CodePojo>();
        List<CodePojo> codeList = codeService.queryCode(null);
        if (CollectionUtils.isNotEmpty(codeList)) {
            for (CodePojo code : codeList) {
                mapAll.put(code.getCodeKind() + code.getCodeValue(), code);
            }
            addCodeMapCache(mapAll);
        }
        return mapAll;
    }

    /**
     * 代码表列表信息添加到缓存
     * 
     * @author zcq
     * @param codeDto
     * @return
     */
    @CachePut(value = "codeCache", key = "#cacheKey")
    public List<CodePojo> addCodeListCache(String cacheKey, List<CodePojo> codeList) {
        return codeList;
    }

    /**
     * 代码表信息添加到缓存
     * 
     * @author zcq
     * @param codeDto
     * @return
     */
    @CachePut(value = "codeCache", key = "'allCodeMap'")
    public Map<String, CodePojo> addCodeMapCache(Map<String, CodePojo> map) {
        return map;
    }

    /**
     * 移除失效的缓存
     * 
     * @author zcq
     * @param codeKind
     * @param codeValue
     */
    @CacheEvict(value = "codeCache", key = "#cacheKey")
    public void removeCodeCache(String cacheKey) {
    }

}

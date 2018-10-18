
package com.mnt.preg.system.service;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.mnt.preg.system.dao.CodeDao;
import com.mnt.preg.system.entity.CodeInfo;
import com.mnt.preg.system.pojo.ChinaPojo;
import com.mnt.preg.system.pojo.CodePojo;

/**
 * 代码表事务
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2015-1-8 zy 初版
 */
@Service
public class CodeServiceImpl extends BaseService implements CodeService {

    @Resource
    private CodeDao codeDao;

    @Override
    @Transactional(readOnly = true)
    public List<CodePojo> queryCode(CodeInfo condition) {
        return codeDao.queryCode(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CodePojo> queryCodeView(CodeInfo condition) {
        return codeDao.queryCodeView(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public CodePojo getCode(String codeId) {
        return codeDao.getTransformerBean(codeId, CodeInfo.class, CodePojo.class);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public CodePojo addCode(CodeInfo code) {
        CodeInfo condition = new CodeInfo();
        condition.setCodeKind(code.getCodeKind());
        condition.setCodeValue(code.getCodeValue());
        if (codeDao.checkCodeValid(condition) > 0) {
            throw new ServiceException(ResultCode.ERROR_90014);
        }
        if (code.getCodeType() != 3) {
            code.setCodeKind(code.getCodeKind().toUpperCase());
            code.setCodeValue(code.getCodeValue().toUpperCase());
        }
        String codeId = (String) codeDao.save(code);
        return getCode(codeId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateCode(CodeInfo code) {
        String codeId = code.getCodeId();
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("codeId", HQLSymbol.EQ.toString(), codeId));
        codeDao.updateHQL(code, conditionParams);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteCode(List<String> codeIdList) {
        codeDao.deleteCodes(codeIdList);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer checkCodeValid(CodeInfo condition) {
        return codeDao.checkCodeValid(condition);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateCodeOrder(List<String> codeIdList) {
        if (CollectionUtils.isNotEmpty(codeIdList)) {
            for (int i = 1; i <= codeIdList.size(); i++) {
                codeDao.updateCodeOrder(codeIdList.get(i - 1), i);
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChinaPojo> queryChina(Integer pId, String type) {
        return codeDao.queryChina(pId, type);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getCodeMaxOrderByParentCodeKind(String parentCodeKind) {
        if (StringUtils.isEmpty(parentCodeKind)) {
            return 0;
        }
        return codeDao.getCodeMaxOrderByParentCodeKind(parentCodeKind);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void removeCode(String codeId) {// @NotBlank
        this.deleteCode(getRemoveCodeIdList(codeId));
    }

    /**
     * 获取要删除的代码表主键
     * 
     * @author zcq
     * @param codeId
     * @return
     */
    private List<String> getRemoveCodeIdList(String codeId) {
        List<String> codeIdList = new ArrayList<String>();
        codeIdList.add(codeId);
        CodePojo code = this.getCode(codeId);
        // 删除目录
        if (code.getCodeType() == 1) {
            CodeInfo condition = new CodeInfo();
            condition.setCodeKind(code.getCodeKind());
            List<CodePojo> muluList = queryCodeView(condition);
            if (CollectionUtils.isNotEmpty(muluList)) {
                Map<String, List<CodePojo>> map = new HashMap<String, List<CodePojo>>();
                for (CodePojo mulu : muluList) {
                    String id = mulu.getParentCodeKind() + "_" + mulu.getParentCodeValue();
                    if (!map.containsKey(id)) {
                        map.put(id, new ArrayList<CodePojo>());
                    }
                    map.get(id).add(mulu);
                }
                codeRecursion(code, map, codeIdList);
            }
        }
        // 删除类型
        if (code.getCodeType() == 2) {
            CodeInfo condition = new CodeInfo();
            condition.setParentCodeKind(code.getCodeKind());
            condition.setParentCodeValue(code.getCodeValue());
            List<CodePojo> typeList = queryCodeView(condition);
            if (CollectionUtils.isNotEmpty(typeList)) {
                for (CodePojo type : typeList) {
                    codeIdList.add(type.getCodeId());
                }
            }
        }

        return codeIdList;
    }

    /**
     * 递归获取要删除的所有代码表项
     * 
     * @author zcq
     * @param code
     * @param map
     * @param codeIdList
     */
    private void codeRecursion(CodePojo code, Map<String, List<CodePojo>> map, List<String> codeIdList) {
        List<CodePojo> codeList = map.get(code.getCodeKind() + "_" + code.getCodeValue());
        if (CollectionUtils.isNotEmpty(codeList)) {
            for (CodePojo nextCode : codeList) {
                codeRecursion(nextCode, map, codeIdList);
                codeIdList.add(nextCode.getCodeId());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void editCodeOrder(List<String> codeIdList) {
        if (CollectionUtils.isNotEmpty(codeIdList)) {
            for (int i = 1; i <= codeIdList.size(); i++) {
                codeDao.updateCodeOrder(codeIdList.get(i - 1), i);
            }
        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<CodePojo> queryCodeAll() {
        return codeDao.queryAll();
    }
}

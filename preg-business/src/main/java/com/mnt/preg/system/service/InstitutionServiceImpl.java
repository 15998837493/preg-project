
package com.mnt.preg.system.service;

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
import com.mnt.preg.system.condition.InstitutionCondition;
import com.mnt.preg.system.condition.PrintCondition;
import com.mnt.preg.system.dao.InstitutionDAO;
import com.mnt.preg.system.entity.InsMenu;
import com.mnt.preg.system.entity.InsPrint;
import com.mnt.preg.system.entity.Institution;
import com.mnt.preg.system.pojo.InstitutionPojo;
import com.mnt.preg.system.pojo.MenuPojo;
import com.mnt.preg.system.pojo.PrintPojo;

/**
 * 机构管理事务
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-10-27 zcq 初版
 */
@Service
public class InstitutionServiceImpl extends BaseService implements InstitutionService {

    @Resource
    private InstitutionDAO institutionDAO;

    @Override
    @Transactional(readOnly = true)
    public List<InstitutionPojo> queryIns(InstitutionCondition condition) {
        if (null == condition) {
            condition = new InstitutionCondition();
        }
        return institutionDAO.queryIns(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public InstitutionPojo getIns(String insId) {
        if (StringUtils.isEmpty(insId)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        return institutionDAO.getIns(insId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addIns(Institution insInfo) {
        String insId = insInfo.getInsId();
        if (StringUtils.isEmpty(insId)) {
            throw new ServiceException(ResultCode.ERROR_90003);
        }
        if (institutionDAO.checkInsIdValid(insId) > 0) {
            throw new ServiceException(ResultCode.ERROR_90002);
        }
        // 保存公司/机构信息
        institutionDAO.save(insInfo);
        return insId;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public InstitutionPojo editIns(Institution insInfo) {
        String insId = insInfo.getInsId();
        if (StringUtils.isEmpty(insId)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("insId", HQLSymbol.EQ.toString(), insId));
        institutionDAO.updateHQL(insInfo, conditionParams);
        return getIns(insId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuPojo> queryInsMenu(String insId) {
        return institutionDAO.queryMenuByInsId(insId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void saveInsMenu(String insId, List<String> menuIdList) {
        // 清除原有功能菜单
        institutionDAO.deleteInsMenu(insId);
        // 保存新功能菜单
        if (CollectionUtils.isNotEmpty(menuIdList)) {
            for (String menuId : menuIdList) {
                InsMenu newInsMenu = new InsMenu();
                newInsMenu.setInsId(insId);
                newInsMenu.setMenuId(menuId);
                // 保存
                institutionDAO.save(newInsMenu);
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Integer checkInsIdValid(String insId) {
        return institutionDAO.checkInsIdValid(insId);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer checkInsNameValid(String insName) {
        return institutionDAO.checkInsNameValid(insName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> queryInsPrintIds(String insId) {
        return institutionDAO.queryInsPrintIds(insId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrintPojo> queryInsPrint(PrintCondition condition) {
        return institutionDAO.queryInsPrint(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, List<PrintPojo>> getInsPrintListMap(String insId) {
        List<PrintPojo> list = institutionDAO.queryPrintByInsId(insId);
        Map<String, List<PrintPojo>> map = new LinkedHashMap<>();
        if (CollectionUtils.isNotEmpty(list)) {
            for (PrintPojo print : list) {
                String category = print.getPrintCategory();
                if (!map.containsKey(category)) {
                    map.put(category, new ArrayList<PrintPojo>());
                }
                map.get(category).add(print);
            }
        }
        return map;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void saveInsPrint(String insId, List<String> printIdList) {
        // 清除原有功能菜单
        institutionDAO.deleteInsPrint(insId);
        // 保存新功能菜单
        if (CollectionUtils.isNotEmpty(printIdList)) {
            for (String printId : printIdList) {
                InsPrint insPrint = new InsPrint();
                insPrint.setInsId(insId);
                insPrint.setPrintId(printId);
                // 保存
                institutionDAO.save(insPrint);
            }
        }
    }
}

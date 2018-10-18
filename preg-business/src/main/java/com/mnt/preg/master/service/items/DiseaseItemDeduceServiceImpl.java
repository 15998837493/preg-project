
package com.mnt.preg.master.service.items;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.core.exception.ServiceException;
import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.core.utils.HQLSymbol;
import com.mnt.preg.main.results.ResultCode;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.master.condition.items.DiseaseItemDeduceCondition;
import com.mnt.preg.master.condition.items.DiseaseItemDeduceGroupCondition;
import com.mnt.preg.master.dao.items.DiseaseItemDeduceDAO;
import com.mnt.preg.master.entity.items.DiseaseItemDeduce;
import com.mnt.preg.master.entity.items.DiseaseItemDeduceGroup;
import com.mnt.preg.master.pojo.items.DiseaseItemDeduceGroupPojo;
import com.mnt.preg.master.pojo.items.DiseaseItemDeducePojo;
import com.mnt.preg.master.pojo.items.ItemPojo;

/**
 * 干预疾病字典接口实现
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历： v1.0 2016-4-5 gss 初版
 */
@Service
public class DiseaseItemDeduceServiceImpl extends BaseService implements DiseaseItemDeduceService {

    @Resource
    private DiseaseItemDeduceDAO diseaseItemDeduceDAO;

    @Override
    @Transactional(readOnly = true)
    public List<DiseaseItemDeducePojo> queryDiseaseItemDeduce(DiseaseItemDeduceCondition condition) {
        return diseaseItemDeduceDAO.queryDiseaseItemDeduce(condition);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addDiseaseItemDeduce(DiseaseItemDeduce diseaseItemDeduce) {
        return (String) diseaseItemDeduceDAO.save(diseaseItemDeduce);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateDiseaseItemDeduce(DiseaseItemDeduce diseaseItemDeduce) {
        String id = diseaseItemDeduce.getId();
        if (StringUtils.isEmpty(id)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        // 更新
        List<HQLConditionParam> updateParams = new ArrayList<HQLConditionParam>();
        updateParams.add(new HQLConditionParam("id", HQLSymbol.EQ.toString(), id));
        diseaseItemDeduceDAO.updateHQL(diseaseItemDeduce, updateParams);
    }

    @Override
    @Transactional(readOnly = true)
    public DiseaseItemDeduce getDiseaseItemDeduce(String id) {
        return diseaseItemDeduceDAO.get(DiseaseItemDeduce.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public ItemPojo getDeduceItem(String id) {
        return diseaseItemDeduceDAO.getDeduceItem(id);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteDiseaseItemDeduceById(String id) {
        diseaseItemDeduceDAO.deleteDiseaseItemDeduceById(id);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteDiseaseItemDeduce(String diseaseId, String itemId) {
        diseaseItemDeduceDAO.deleteDiseaseItemDeduce(diseaseId, itemId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiseaseItemDeduceGroupPojo> queryDiseaseItemDeduceGroup(DiseaseItemDeduceGroupCondition condition) {
        return diseaseItemDeduceDAO.queryDiseaseItemDeduceGroup(condition);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addDiseaseItemDeduceGroup(DiseaseItemDeduceGroup diseaseItemDeduceGroup) {
        return (String) diseaseItemDeduceDAO.save(diseaseItemDeduceGroup);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteDiseaseItemDeduceGroupById(String id) {
        diseaseItemDeduceDAO.deleteDiseaseItemDeduceGroupById(id);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteDiseaseItemDeduceGroupByItemId(String itemId) {
        diseaseItemDeduceDAO.deleteDiseaseItemDeduceGroupByItemId(itemId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteDiseaseItemDeduceGroupByitemNames(String itemNames, String diseaseId) {
        diseaseItemDeduceDAO.deleteDiseaseItemDeduceGroupByitemNames(itemNames, diseaseId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiseaseItemDeduceGroupPojo> queryFitDiseaseItemDeduceGroup(List<String> itemIdList) {
        return diseaseItemDeduceDAO.queryFitDiseaseItemDeduceGroup(itemIdList);
    }

}

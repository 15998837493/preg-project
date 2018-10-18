
package com.mnt.preg.master.service.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.main.utils.AutomaticGenerationCoding;
import com.mnt.preg.master.condition.items.ItemCondition;
import com.mnt.preg.master.dao.items.ItemDAO;
import com.mnt.preg.master.entity.items.Item;
import com.mnt.preg.master.pojo.items.ItemPojo;

/**
 * 体检项目字典接口实现
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-4-5 gss 初版
 */
@Service
public class ItemServiceImpl extends BaseService implements ItemService {

    @Resource
    private ItemDAO itemDAO;// 系统体检项目DAO

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String saveItem(ItemPojo itemVo) {
        Item item = TransformerUtils.transformerProperties(Item.class, itemVo);
        return (String) itemDAO.save(item);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateItem(ItemPojo itemVo, String id) {
        Item item = TransformerUtils.transformerProperties(Item.class, itemVo);
        itemDAO.updateItem(item, id);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void removeItem(String id) {
        itemDAO.deleteItem(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemPojo> queryItem(ItemCondition condition) {
        if (condition == null) {
            condition = new ItemCondition();
        }
        List<ItemPojo> itemVos = itemDAO.queryItem(condition);
        return itemVos;
    }

    @Override
    @Transactional(readOnly = true)
    public ItemPojo getItemByItemId(String itemId) {
        return itemDAO.getItemByItemId(itemId);
    }

    @Override
    @Transactional(readOnly = true)
    public ItemPojo getItemByItemCode(String itemCode) {
        if (StringUtils.isEmpty(itemCode)) {
            return null;
        }
        return itemDAO.getItemByItemCode(itemCode);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemPojo> queryItemByCondition(ItemCondition condition) {
        List<ItemPojo> list = itemDAO.queryItemByCondition(condition);
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public Integer checkItemCode(String itemCode) {
        return this.validCode("itemCode", itemCode, Item.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer checkItemName(String itemName, String itemType, String itemClassify) {
        Map<String, Object> conditionMap = new HashMap<String, Object>();
        conditionMap.put("itemType", itemType);
        conditionMap.put("itemClassify", itemClassify);
        conditionMap.put("itemName", itemName);
        return primaryKeyDao.validName(conditionMap, Item.class);
    }

    @Override
    @Transactional(readOnly = true)
    public String getItemAbnomalsByItemCodes(List<String> itemCodeList) {
        List<String> list = itemDAO.queryItemAbnomalByItemCodes(itemCodeList);
        String abnomal = "";
        if (CollectionUtils.isNotEmpty(list)) {
            abnomal = StringUtils.join(list, "、");
        }
        return abnomal;
    }

    @Override
    @Transactional(readOnly = true)
    public String getMaxItemCode(String insId) {
        insId = insId == null ? "" : insId;
        String maxDiseaseCode = itemDAO.getMaxItemCode(insId);
        String newCode = AutomaticGenerationCoding.getInstance().getNextCashNumber(maxDiseaseCode, 6);
        return insId + newCode;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemPojo> queryItemType() {
        return itemDAO.queryItemType();
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void editItemOrder(String itemIds) {
        if (StringUtils.isNotBlank(itemIds)) {
            String[] itemIdArr = itemIds.split(",");
            for (int i = 0; i < itemIdArr.length; i++) {
                itemDAO.updateItemOrder(itemIdArr[i], i);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateItemType(String oldType, String newType, String type, String pType) {
        itemDAO.updateItemType(oldType, newType, type, pType);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void removeItemByType(String type, String itemType, String itemClassify) {
        itemDAO.removeItemByType(type, itemType, itemClassify);
    }
}

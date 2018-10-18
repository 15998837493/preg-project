
package com.mnt.preg.master.service.items;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.preg.main.enums.Flag;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.master.condition.items.InspectItemCondition;
import com.mnt.preg.master.dao.items.InspectItemDAO;
import com.mnt.preg.master.entity.items.InspectItem;
import com.mnt.preg.master.pojo.items.InspectItemPojo;

/**
 * 检查项目接口实现
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-4-5 gss 初版
 */
@Service
public class InspectItemServiceImpl extends BaseService implements InspectItemService {

    @Resource
    private InspectItemDAO inspectItemDAO;

    private static final Logger LOGGER = LoggerFactory.getLogger(InspectItemServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    public List<InspectItemPojo> queryInspectItem(InspectItemCondition condition) {
        if (condition == null) {
            condition = new InspectItemCondition();
        }
        List<InspectItemPojo> inspectItemVos = inspectItemDAO.queryInspectItemByCondition(condition);
        return inspectItemVos;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String saveInspectItem(InspectItemPojo inspectItemVo) {
        if (inspectItemVo == null) {
            return null;
        }
        LOGGER.info("【事物层】保存检查项目记录");
        InspectItem inspectItem = TransformerUtils.transformerProperties(InspectItem.class,
                inspectItemVo);
        inspectItem.setInspectId(inspectItemVo.getInspectId().trim());
        return (String) inspectItemDAO.save(inspectItem);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateInspectItem(InspectItemPojo inspectItemVo, String id) {
        if (inspectItemVo == null) {
            return;
        }
        LOGGER.info("【事物层】更新检查项目记录");
        InspectItem inspectItem = TransformerUtils.transformerProperties(InspectItem.class,
                inspectItemVo);
        inspectItemDAO.updateInspectItem(inspectItem, id);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void removeInspectItem(String id) {
        if (StringUtils.isEmpty(id)) {
            return;
        }
        InspectItem inspectItem = (InspectItem) inspectItemDAO.get(InspectItem.class, id);
        inspectItem.setFlag(Flag.deleted.getValue());
        LOGGER.info("【事物层】删除检查项目记录:id" + id);
        inspectItemDAO.updateInspectItem(inspectItem, id);
    }

    @Override
    @Transactional(readOnly = true)
    public InspectItemPojo getInspectItemById(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        LOGGER.info("【事物层】根据id查询检查项目记录");
        return (InspectItemPojo) inspectItemDAO.getTransformerBean(id, InspectItem.class,
                InspectItemPojo.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer checkInspctItemCodeValid(String inspectCode) {
        return this.validCode("inspectCode", inspectCode, InspectItem.class);
    }

    @Override
    @Transactional(readOnly = true)
    public InspectItemPojo getInspectItemByInspectCode(String inspectCode) {
        return inspectItemDAO.getInspectItemByInspectCode(inspectCode);
    }

}

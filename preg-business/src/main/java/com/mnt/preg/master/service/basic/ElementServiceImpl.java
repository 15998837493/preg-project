
package com.mnt.preg.master.service.basic;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.core.exception.ServiceException;
import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.core.utils.HQLSymbol;
import com.mnt.preg.main.results.ResultCode;
import com.mnt.preg.master.dao.basic.ElementDAO;
import com.mnt.preg.master.entity.basic.Element;
import com.mnt.preg.master.pojo.basic.ElementPojo;

@Service
public class ElementServiceImpl implements ElementService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElementServiceImpl.class);

    /** 元素库 */
    @Resource
    private ElementDAO elementDAO;

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public ElementPojo saveNutrient(Element nutrient) {
        String id = (String) elementDAO.save(nutrient);
        return elementDAO.getTransformerBean(id, Element.class, ElementPojo.class);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public ElementPojo updateNutrient(Element nutrient) {
        LOGGER.info("【事物层】更新元素记录");
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("id", HQLSymbol.EQ.toString(), nutrient.getId()));
        elementDAO.updateHQL(nutrient, conditionParams);
        return elementDAO.getTransformerBean(nutrient.getId(), Element.class, ElementPojo.class);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void removeNutrient(String id) {
        LOGGER.info("【事物层】删除元素记录:id=" + id);
        // 根据主键id查询
        Element nutrient = elementDAO.get(Element.class, id);
        if (nutrient == null) {
            throw new ServiceException(ResultCode.ERROR_99998);
        }
        nutrient.setFlag(0);
        this.updateNutrient(nutrient);
        elementDAO.removeProductElementByNutrientId(nutrient.getNutrientId());

    }

    @Override
    @Transactional(readOnly = true)
    public List<ElementPojo> queryNutrient(Element condition) {
        if (condition == null) {
            condition = new Element();
        }
        List<ElementPojo> elementpojo = elementDAO.queryNutrient(condition);
        return elementpojo;
    }

    @Override
    @Transactional(readOnly = true)
    public ElementPojo getNutrientByNutrientId(String nutrientId) {
        LOGGER.info("【事物层】根据nutrientId查询问卷基本信息:nutrientId：" + nutrientId);

        return elementDAO.getNutrientByNutrientId(nutrientId);
    }

}

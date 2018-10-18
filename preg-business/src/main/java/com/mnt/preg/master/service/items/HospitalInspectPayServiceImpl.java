
package com.mnt.preg.master.service.items;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.preg.main.enums.Flag;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.master.condition.items.HospitalInspectPayCondition;
import com.mnt.preg.master.dao.items.HospitalInspectPayDAO;
import com.mnt.preg.master.entity.items.HospitalInspectPay;
import com.mnt.preg.master.entity.items.HospitalInspectPayConfig;
import com.mnt.preg.master.pojo.items.HospitalInspectPayPojo;
import com.mnt.preg.master.pojo.items.ItemPojo;

/**
 * 收费项目配置
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-6-21 zcq 初版
 */
@Service
public class HospitalInspectPayServiceImpl extends BaseService implements HospitalInspectPayService {

    @Resource
    private HospitalInspectPayDAO hospitalInspectPayDAO;

    // =======================================【维护操作接口】==========================================

    @Override
    @Transactional(readOnly = true)
    public HospitalInspectPayPojo getInspectPayByInspectId(String inspectId) {
        return hospitalInspectPayDAO.getInspectByInspectId(inspectId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HospitalInspectPayPojo> queryInspectPayByCondition(HospitalInspectPayCondition condition) {
        return hospitalInspectPayDAO.queryInspectItemByCondition(condition);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateInspectPay(HospitalInspectPay inspectPay) {
        hospitalInspectPayDAO.updateHQL(inspectPay);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String saveInspectPay(HospitalInspectPay inspectPay, List<String> itemIdList) {
        // 第一步：保存收费项目信息
        String inspectId = inspectPay.getInspectId();
        if (StringUtils.isBlank(inspectId)) {
            inspectId = (String) hospitalInspectPayDAO.save(inspectPay);// 添加
        } else {
            hospitalInspectPayDAO.updateHQL(inspectPay);// 更新
        }
        // 第二步：保存关联配置
        // 删除旧数据
        hospitalInspectPayDAO.deleteInspectPayConfig(inspectId);
        // 添加新数据
        if (CollectionUtils.isNotEmpty(itemIdList)) {
            for (String itemId : itemIdList) {
                HospitalInspectPayConfig config = new HospitalInspectPayConfig();
                config.setInspectId(inspectId);
                config.setItemId(itemId);
                hospitalInspectPayDAO.save(config);
            }
        }
        return inspectId;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void removeInspectPay(String inspectId) {
        HospitalInspectPay inspectPay = new HospitalInspectPay();
        inspectPay.setInspectId(inspectId);
        inspectPay.setFlag(Flag.deleted.getValue());
        hospitalInspectPayDAO.updateHQL(inspectPay);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteInspectPayConfig(String inspectId) {
        hospitalInspectPayDAO.deleteInspectPayConfig(inspectId);
    }

    // =======================================【数据查询接口】==========================================

    @Override
    @Transactional(readOnly = true)
    public HospitalInspectPayPojo queryInspectPayAndItemByInspectId(String inspectId) {
        HospitalInspectPayPojo pay = hospitalInspectPayDAO.getInspectByInspectId(inspectId);
        if (pay != null) {
            List<ItemPojo> itemList = hospitalInspectPayDAO.queryItemByInspectId(pay.getInspectId());
            if (CollectionUtils.isNotEmpty(itemList)) {
                pay.setItemList(itemList);
            }
        }
        return pay;
    }

    @Override
    @Transactional(readOnly = true)
    public List<HospitalInspectPayPojo> queryInspectPayAndItemByCondition(HospitalInspectPayCondition condition) {
        List<HospitalInspectPayPojo> payList = hospitalInspectPayDAO.queryInspectItemByCondition(condition);
        if (CollectionUtils.isNotEmpty(payList)) {
            Iterator<HospitalInspectPayPojo> it = payList.iterator();
            while (it.hasNext()) {
                HospitalInspectPayPojo pay = it.next();
                if (pay != null && StringUtils.isNotBlank(pay.getInspectId())) {
                    List<ItemPojo> itemList = hospitalInspectPayDAO.queryItemByInspectId(pay.getInspectId());
                    if (CollectionUtils.isNotEmpty(itemList)) {
                        pay.setItemList(itemList);
                    }
                }
            }
        }
        return payList;
    }
}

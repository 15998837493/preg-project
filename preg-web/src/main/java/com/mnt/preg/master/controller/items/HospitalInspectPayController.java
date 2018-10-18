
package com.mnt.preg.master.controller.items;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.preg.master.condition.items.HospitalInspectPayCondition;
import com.mnt.preg.master.entity.items.HospitalInspectPay;
import com.mnt.preg.master.mapping.items.ItemsMapping;
import com.mnt.preg.master.pojo.items.HospitalInspectPayPojo;
import com.mnt.preg.master.pojo.items.ItemPojo;
import com.mnt.preg.master.service.items.HospitalInspectPayServiceImpl;
import com.mnt.preg.master.service.items.ItemService;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 收费项目配置Controller
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-6-21 zcq 初版
 */
@Controller
public class HospitalInspectPayController extends BaseController {

    @Resource
    private HospitalInspectPayServiceImpl hospitalInspectPayService;

    @Resource
    private ItemService itemService;

    /**
     * 获取收费项目配置页面
     * 
     * @author zcq
     * @param model
     * @return
     */
    @RequestMapping(value = ItemsMapping.INSPECT_PAY_VIEW)
    public String toInspectPayView(Model model) {
        List<ItemPojo> itemList = itemService.queryItem(null);
        model.addAttribute("itemList", NetJsonUtils.listToJsonArray(itemList));
        return "/master/items/hospital_inspect_pay";
    }

    /**
     * 检索收费项目
     * 
     * @author zcq
     * @param condition
     * @return
     */
    @RequestMapping(value = ItemsMapping.INSPECT_PAY_QUERY)
    public @ResponseBody
    WebResult<HospitalInspectPayPojo> queryInspectPay(HospitalInspectPayCondition condition) {
        WebResult<HospitalInspectPayPojo> webResult = new WebResult<HospitalInspectPayPojo>();
        webResult.setData(hospitalInspectPayService.queryInspectPayByCondition(condition));
        webResult.setResult(true);
        return webResult;
    }

    /**
     * 保存收费项目
     * 
     * @author zcq
     * @param inspectPay
     * @return
     */
    @RequestMapping(value = ItemsMapping.INSPECT_PAY_SAVE)
    public @ResponseBody
    WebResult<HospitalInspectPayPojo> saveInspectPay(HospitalInspectPayPojo inspectPayPojo) {
        WebResult<HospitalInspectPayPojo> webResult = new WebResult<HospitalInspectPayPojo>();
        HospitalInspectPay inspectPay = TransformerUtils.transformerProperties(HospitalInspectPay.class, inspectPayPojo);
        String inspectId = hospitalInspectPayService.saveInspectPay(inspectPay, inspectPayPojo.getItemIdList());
        return webResult.setSuc(hospitalInspectPayService.getInspectPayByInspectId(inspectId));
    }

    /**
     * 删除收费项目信息
     * 
     * @author zcq
     * @param inspectId
     * @return
     */
    @RequestMapping(value = ItemsMapping.INSPECT_PAY_REMOVE)
    public @ResponseBody
    WebResult<Boolean> removeInspectPay(String inspectId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        hospitalInspectPayService.removeInspectPay(inspectId);
        return webResult.setSuc(true);
    }

    /**
     * 查询收费项目的检验项目
     * 
     * @author zcq
     * @param inspectId
     * @return
     */
    @RequestMapping(value = ItemsMapping.INSPECT_PAY_ITEMS_GET)
    public @ResponseBody
    WebResult<List<ItemPojo>> getInspectPayItems(String inspectId) {
        WebResult<List<ItemPojo>> webResult = new WebResult<List<ItemPojo>>();
        HospitalInspectPayPojo inspectPay = hospitalInspectPayService.queryInspectPayAndItemByInspectId(inspectId);
        return webResult.setSuc(inspectPay.getItemList());
    }

    /**
     * 校验收费项目名称是否重复
     * 
     * @author zcq
     * @param inspectName
     * @param inspectOldName
     * @param type
     * @return
     */
    @RequestMapping(value = ItemsMapping.INSPECT_PAY_NAME_VALIDATE)
    public @ResponseBody
    boolean inspectItemNameValidate(String inspectName, String inspectOldName, String type) {
        if ("update".equals(type) && inspectName.equals(inspectOldName)) {
            return true;
        }
        int count = hospitalInspectPayService.validCode("inspectName", inspectName, HospitalInspectPay.class);
        if (count == 0) {
            return true;
        }
        return false;
    }

}

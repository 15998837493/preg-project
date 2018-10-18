
package com.mnt.preg.master.controller.items;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.preg.master.condition.items.HospitalCondition;
import com.mnt.preg.master.entity.items.Hospital;
import com.mnt.preg.master.mapping.items.ItemPageName;
import com.mnt.preg.master.mapping.items.ItemsMapping;
import com.mnt.preg.master.pojo.items.HospitalPojo;
import com.mnt.preg.master.service.items.HospitalService;
import com.mnt.preg.web.constants.WebMsgConstant;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;
import com.mnt.preg.web.pojo.ZTree;

/**
 * 医院信息Controller
 * 
 * @author dhs
 * @version 1.7
 * 
 *          变更履历：
 *          v1.7 2018-08-07 dhs 初版
 */
@Controller
public class HospitalController extends BaseController {

    @Resource
    private HospitalService hospitalService;

    /**
     * 
     * 跳转医院信息页面
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = ItemsMapping.HOSPITAL_VIEW)
    public String initHospitalPage(Model model) {
        List<HospitalPojo> hospitalList = hospitalService.queryHospitalType();
        model.addAttribute("treeNodes", NetJsonUtils.listToJsonArray(getItemTypeList(hospitalList)));
        return ItemPageName.HOSPITAL_VIEW;
    }

    /**
     * 
     * 检索医院信息
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = ItemsMapping.HOSPITAL_QUERY)
    public @ResponseBody
    WebResult<HospitalPojo> queryHospital(HospitalCondition condition) {
        WebResult<HospitalPojo> webResult = new WebResult<HospitalPojo>();
        webResult.setData(hospitalService.queryHospital(condition));
        return webResult;
    }

    /**
     * 
     * 添加医院信息
     * 
     * @param request
     * @param itemForm
     * @return
     */
    @RequestMapping(value = ItemsMapping.HOSPITAL_ADD)
    public @ResponseBody
    WebResult<HospitalPojo> addNutrient(Hospital hospital) {
        WebResult<HospitalPojo> webResult = new WebResult<HospitalPojo>();
        webResult.setSuc(hospitalService.saveHospital(hospital), WebMsgConstant.success_message);
        return webResult;
    }

    /**
     * 修改医院信息
     * 
     * @author dhs
     * @param hospital
     * @return
     */
    @RequestMapping(value = ItemsMapping.HOSPITAL_UPDATE)
    public @ResponseBody
    WebResult<HospitalPojo> updateNutrient(Hospital hospital) {
        WebResult<HospitalPojo> webResult = new WebResult<HospitalPojo>();
        webResult.setSuc(hospitalService.updateHospital(hospital), WebMsgConstant.success_message);
        return webResult;
    }

    /**
     * ztree节点名称修改
     * 
     * @author dhs
     * @param
     * @return
     */
    @RequestMapping(value = ItemsMapping.HOSPITAL_FATHER_NAME_UPDATE)
    public @ResponseBody
    WebResult<Boolean> itemOrder(String oldType, String newType, String type, String pType) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        hospitalService.updateHospitalType(oldType, newType, type, pType);
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 
     * 验证医院名称重复
     * 
     * @author dhs
     * @param itemCode
     * @param itemId
     * @param inspectId
     * @return
     */
    @RequestMapping(value = ItemsMapping.HOSPITAL_VALID)
    public @ResponseBody
    boolean checkItemName(String hospitalName, String hospitalId, String hospitalType, String hospitalClassify) {
        return this.check(hospitalName, hospitalId, hospitalType, hospitalClassify);
    }

    /**
     * 
     * 删除医院信息
     * 
     * @author dhs
     * @param id
     * @return
     */
    @RequestMapping(value = ItemsMapping.HOSPITAL_DELETE)
    public @ResponseBody
    WebResult<Boolean> removeNutrient(String hospitalId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        hospitalService.removeHospital(hospitalId);
        webResult.setSuc(true, WebMsgConstant.success_message);
        return webResult;
    }

    /**
     * 删除节点
     * 
     * @author dhs
     * @param
     * @return
     */
    @RequestMapping(value = ItemsMapping.HOSPITAL_REMOVE)
    public @ResponseBody
    WebResult<Boolean> removeHospitalByType(String type, String itemType, String itemClassify) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        hospitalService.removeHospitalByType(type, itemType, itemClassify);
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 获取医院分类
     * 
     * 
     * @author dhs
     * @return
     */
    private List<ZTree<String>> getItemTypeList(List<HospitalPojo> hospitalList) {
        List<ZTree<String>> treeList = new ArrayList<ZTree<String>>();
        treeList.add(new ZTree<String>("000", "医院类型", true, "home"));
        List<String> itemExistType = new ArrayList<String>();
        if (CollectionUtils.isNotEmpty(hospitalList)) {
            for (HospitalPojo hospitalPojo : hospitalList) {
                if (!itemExistType.contains(hospitalPojo.getHospitalType())) {
                    ZTree<String> treeP = new ZTree<String>();
                    treeP.setId(hospitalPojo.getHospitalType());
                    treeP.setpId("000");
                    treeP.setName(hospitalPojo.getHospitalType());
                    treeP.setValue(hospitalPojo.getHospitalType());
                    treeP.setIsParent(true);
                    treeP.setIconSkin("mulu");
                    treeList.add(treeP);
                }
                ZTree<String> tree = new ZTree<String>();
                tree.setId(hospitalPojo.getHospitalClassify());
                tree.setpId(hospitalPojo.getHospitalType());
                tree.setName(hospitalPojo.getHospitalClassify());
                tree.setValue(hospitalPojo.getHospitalClassify());
                tree.setIconSkin("menu");
                treeList.add(tree);
                itemExistType.add(hospitalPojo.getHospitalType());
            }
        }
        return treeList;
    }

    /**
     * 验证医院名称重复
     * 
     * @author dhs
     */
    private boolean check(String value, String id, String itemType, String itemClassify) {
        boolean flag;
        int index = 0;
        index = hospitalService.checkHospitalName(value.trim(), itemType, itemClassify);
        if (id.isEmpty()) {// 添加
            if (index == 0) {
                flag = true;
            } else {
                flag = false;
            }
        } else {// 修改
            HospitalCondition condition = new HospitalCondition();
            condition.setHospitalId(id);
            String oldValue = null;
            oldValue = hospitalService.queryHospital(condition).get(0).getHospitalName();
            if (oldValue.equals(value.trim())) {
                flag = true;
            } else {
                if (index == 0) {
                    flag = true;
                } else {
                    flag = false;
                }
            }
        }
        return flag;
    }
}

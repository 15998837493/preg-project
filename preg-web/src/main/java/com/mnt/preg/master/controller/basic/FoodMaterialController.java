/*
 * FoodMaterialController.java    1.0  2018-1-25
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.master.controller.basic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.utils.HanyuToPinyin;
import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.health.utils.maths.CreateRandomCode;
import com.mnt.preg.main.constant.MessageConstant;
import com.mnt.preg.main.enums.Flag;
import com.mnt.preg.master.condition.basic.FoodMaterialCondition;
import com.mnt.preg.master.entity.basic.FoodMaterial;
import com.mnt.preg.master.entity.basic.FoodTree;
import com.mnt.preg.master.form.basic.FoodMaterialForm;
import com.mnt.preg.master.mapping.basic.FoodMaterialMapping;
import com.mnt.preg.master.mapping.basic.FoodMaterialPage;
import com.mnt.preg.master.pojo.basic.FoodMaterialPojo;
import com.mnt.preg.master.pojo.basic.NutrientAmountPojo;
import com.mnt.preg.master.pojo.basic.TreePojo;
import com.mnt.preg.master.service.basic.FoodMaterialServcie;
import com.mnt.preg.master.service.basic.TreeService;
import com.mnt.preg.web.constants.PathConstant;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;
import com.mnt.preg.web.pojo.ZTree;


/**
 * 食材Controller
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-25 scd 初版
 */
@Controller
public class FoodMaterialController extends BaseController {

    @Resource
    private FoodMaterialServcie foodMaterialServcie;

    @Resource
    private TreeService treeService;

    private String treeKind = "food_material";

    /**
     * 
     * 跳转到食材一览页面
     * 
     * @author scd
     * @param model
     * @return
     */
    @RequestMapping(value = FoodMaterialMapping.FOOD_MATERIAL_QUERY)
    public String toFoodMaterial(Model model) {
        List<ZTree<TreePojo>> treeList = new ArrayList<ZTree<TreePojo>>();
        treeList.add(new ZTree<TreePojo>("000", "食材类别", true, "home"));
        FoodTree condition = new FoodTree();
        condition.setTreeKind(treeKind);
        List<TreePojo> menuList = treeService.getTree_returnlist(condition);
        if (CollectionUtils.isNotEmpty(menuList)) {
            treeList.addAll(this.getTrees(menuList));
        }
        model.addAttribute("treeNodes", NetJsonUtils.listToJsonArray(treeList));
        model.addAttribute("elementList", NetJsonUtils.listToJsonArray(elementService.queryNutrient(null)));
        return FoodMaterialPage.FOOD_MASTERIAL_VIEW;
    }

    /**
     * 
     * 异步加载食材信息
     * 
     * @author scd
     * @param condition
     * @return
     */
    @RequestMapping(value = FoodMaterialMapping.FOOD_MATERIAL_AJAX_QUERY)
    public @ResponseBody
    FoodMaterialCondition queryFoodMaterial(FoodMaterialCondition condition) {
        FoodMaterialCondition c = foodMaterialServcie.queryFoodMaterialByPage(condition);
        return c;
    }

    /**
     * 
     * 添加食材
     * 
     * @author scd
     * @param fmForm
     * @param fmeForm
     * @return
     */
    @RequestMapping(value = FoodMaterialMapping.FOOD_MATERIAL_ADD)
    public @ResponseBody
    WebResult<Boolean> addFoodMaterial(FoodMaterialForm fmForm) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        
        if (fmForm.getFmFatType() == null) {
            fmForm.setFmFatType("0");
        }
        if (fmForm.getFmProtidFlag() == null) {
            fmForm.setFmProtidFlag("no");
        }

        // 记录食材信息
        FoodMaterial foodMaterial = new FoodMaterial();
        BeanUtils.copyProperties(fmForm, foodMaterial);
        foodMaterial.setFmName(foodMaterial.getFmName().trim());
        
        String fmPic = fmForm.getFmPic();
        // 生成图片名称并移动图片到指定文件夹
        if (StringUtils.isNotEmpty(fmPic)) {
            String fromPath = publicConfig.getResourceAbsolutePath() + PathConstant.template_image_temp
                    + fmPic.substring(fmPic.lastIndexOf("/") + 1);
            String suffix = fmPic.substring(fmPic.lastIndexOf("."));
            String picName = HanyuToPinyin.getInstance().getFullStringPinYin(foodMaterial.getFmName());
            String destPath = getFoodPic(picName, suffix, "ext");
            try {
                FileUtils.moveFile(new File(fromPath), new File(destPath));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            foodMaterial.setFmPic(destPath.substring(destPath.lastIndexOf("/") + 1));// 重新设置图片路径
        }
        // 记录食材元素信息
        List<NutrientAmountPojo> eLeList = new ArrayList<NutrientAmountPojo>();
        eLeList = fmForm.getProductElementList();
        
        foodMaterialServcie.addFoodMaterial(foodMaterial, eLeList, "C000000");
        webResult.setSuc(true, MessageConstant.success_message);
        return webResult;
    }

    /**
     * 
     * 获取食谱图片路径信息
     * 
     * @author scd
     * @param picName
     * @param suffix
     * @param picType
     * @return
     */
    private String getFoodPic(String picName, String suffix, String picType) {
        // 目标文件路径
        String destPath = publicConfig.getResourceAbsolutePath() + PathConstant.food_ext_image + picName + suffix;
        if ("cuisine".equals(picType)) {
            destPath = publicConfig.getResourceAbsolutePath() + PathConstant.food_cuisine_image + picName + suffix;
        }
        File newFile = new File(destPath);
        if (newFile.exists()) {
            destPath = getFoodPic(picName + CreateRandomCode.getRandomCode(2), suffix, picType);
        }
        return destPath;
    }

    /**
     * 修改食材信息
     * 
     * @param request
     * @param fmForm
     * @param fmeForm
     * @return
     * 
     */
    @RequestMapping(value = FoodMaterialMapping.FOOD_MATERIAL_UPDATE)
    public @ResponseBody
    WebResult<Boolean> updateFoodMaterial(FoodMaterialForm fmForm) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        // 记录食材信息
        FoodMaterial foodMaterial = new FoodMaterial();
        BeanUtils.copyProperties(fmForm, foodMaterial);
        foodMaterial.setFmName(foodMaterial.getFmName().trim());
        String fmPic = fmForm.getFmPic();
        // 图片名称
        String picName = fmForm.getFmPicOld();
        // 目标文件路径
        String destPath = publicConfig.getResourceAbsolutePath() + PathConstant.food_ext_image + picName;
        // 若图片为空则新生成图片信息
//        if (StringUtils.isEmpty(picName.replaceAll("/", ""))) {
//            String suffix = fmPic.substring(fmPic.indexOf("."));
//            picName = HanyuToPinyin.getInstance().getFullStringPinYin(foodMaterial.getFmName());
//            destPath = getFoodPic(picName, suffix, "ext");
//        }
        // 覆盖图片
        if (StringUtils.isNotEmpty(fmPic) && fmPic.indexOf("/") > -1) {
            // 源文件路径
            String fromPath = publicConfig.getResourceAbsolutePath() + PathConstant.template_image_temp
                    + fmPic.substring(fmPic.lastIndexOf("/") + 1);
            // 删除原目录下的图片
            FileUtils.deleteQuietly(new File(destPath));
            // 移动新图片到目标文件夹下
            try {
                destPath = publicConfig.getResourceAbsolutePath() + PathConstant.food_ext_image + getFileNameWithSuffix(fmPic);
                FileUtils.moveFile(new File(fromPath), new File(destPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            foodMaterial.setFmPic(destPath.substring(destPath.lastIndexOf("/") + 1));
        }
        //TODO 记录食材元素信息
        List<NutrientAmountPojo> eLeList = new ArrayList<NutrientAmountPojo>();
        eLeList = fmForm.getProductElementList();

        foodMaterialServcie.updateFoodMaterial(foodMaterial, eLeList);
        webResult.setSuc(true, MessageConstant.success_message);
        return webResult;
    }

    /**
     * 
     * 删除食材
     * 
     * @author scd
     * @param fmId
     * @return
     */
    @RequestMapping(value = FoodMaterialMapping.FOOD_MATERIAL_REMOVE)
    public @ResponseBody
    WebResult<Boolean> deleteFoodMaterial(@RequestParam String fmId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        foodMaterialServcie.removeFoodMaterial(fmId);
        webResult.setSuc(true, MessageConstant.success_message);
        return webResult;
    }

    /******************************************************* 类别管理 *******************************************************************************/

    /**
     * 
     * 验证食材类别是否有食材配置
     * 
     * @author scd
     * @param parentTreeId
     * @return
     */
    @RequestMapping(value = FoodMaterialMapping.CATALOG_CHECK_HAVE_FOOD)
    public @ResponseBody
    WebResult<Boolean> checkFoodTreeAmount(String treeId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        if (foodMaterialServcie.checkFoodTreeAmount(treeId) > 0) {
            webResult.setSuc(false);
        } else {
            webResult.setSuc(true);
        }
        return webResult;
    }
    
    /**
     * 获取食材元素
     * @param fmId
     * @return
     */
    @RequestMapping(value = FoodMaterialMapping.GET_FOOD_ELEMENT)
    public @ResponseBody
    WebResult<NutrientAmountPojo> getFmElement(String fmId) {
        WebResult<NutrientAmountPojo> webResult = new WebResult<NutrientAmountPojo>();
        List<NutrientAmountPojo> list = foodMaterialServcie.getFoodMaterialExt(fmId);
        webResult.setData(list);
        return webResult;
    }
    
    /**
     * 
     * 判断结点下是否有子结点
     *
     * @author zj
     * @param treeId
     * @param type
     * @return
     */
    @RequestMapping(value = FoodMaterialMapping.CATALOG_CHECK_HAVE_SUB)
    public @ResponseBody
    WebResult<Boolean> checkHaveSub(String treeId, String type) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        int count = treeService.checkTreeHaveSub(treeId, type);
        if (count > 0) {
            webResult.setSuc(false);
        } else {
            webResult.setSuc(true);
        }
        return webResult;
    }

    /**
     * 添加功能菜单
     * 
     * @author gss
     * @param form
     * @return
     */
    @RequestMapping(value = FoodMaterialMapping.CATALOGINFO_ADD)
    public @ResponseBody
    WebResult<ZTree<TreePojo>> addFoodMaterial(FoodTree form) {
        WebResult<ZTree<TreePojo>> webResult = new WebResult<ZTree<TreePojo>>();
        String treeId = form.getParentTreeId();
        FoodMaterial condition = new FoodMaterial();
        condition.setFlag(Flag.normal.getValue());
        condition.setFmType(treeId);
        List<FoodMaterialPojo> foodMaterials = foodMaterialServcie.queryFoodMaterial(condition);
        // 类别下无食材才可以添加子节点
        if (CollectionUtils.isEmpty(foodMaterials)) {
            ZTree<TreePojo> zTree = new ZTree<TreePojo>();
            FoodTree tree = TransformerUtils.transformerProperties(FoodTree.class, form);
            tree.setTreeKind(treeKind);
            TreePojo treePojo = treeService.addTree(tree);
            zTree.setId(treePojo.getTreeId());
            zTree.setpId(treePojo.getParentTreeId());
            zTree.setName(treePojo.getTreeName());
            zTree.setValue(treePojo);
            zTree.setIconSkin("menu");
            webResult.setSuc(zTree);
        }
        return webResult;
    }

    /**
     * 验证商品名称不可以重复
     * 
     * @author scd
     * @param catalogName
     * @param catalogNameOld
     * @param opernerCatalog
     * @return
     */
    @RequestMapping(value = FoodMaterialMapping.CATALOGINFO_NAME_VALID)
    public @ResponseBody
    boolean checkCatalogInfoNameValid(String treeName, String treeNameOld, String opernerCatalog, String treeId) {
        if ("update".equals(opernerCatalog) && treeName.equals(treeNameOld)) {
            return true;
        } else {
            if (StringUtils.isEmpty(treeId)) {
                treeId = "000";
            }
            int count = treeService.checkTreeNameValidFromBrother(treeName, treeId);
            if (count == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 修改功能菜单
     * 
     * @author scd
     * @param form
     * @return
     */
    @RequestMapping(value = FoodMaterialMapping.CATALOGINFO_UPDATE)
    public @ResponseBody
    WebResult<Boolean> updateCatalogInfo(FoodTree tree) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        tree.setTreeKind(treeKind);
        treeService.updateTree(tree);
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 删除功能菜单
     * 
     * @author gss
     * @param menuId
     * @return
     */
    @RequestMapping(value = FoodMaterialMapping.CATALOGINFO_REMOVE)
    public @ResponseBody
    WebResult<Boolean> removeCatalogInfo(String treeId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        treeService.deleteTree(treeId, treeKind);
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 修改类别排序
     * 
     * @author scd
     * @param menuIdList
     * @return
     */
    @RequestMapping(value = FoodMaterialMapping.CATALOGINFO_UPDATE_ORDER)
    public @ResponseBody
    WebResult<Boolean> updateCatalogInfoOrder(String menuIds) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        treeService.editTreeOrder(Arrays.asList(menuIds.split(",")));
        webResult.setSuc(true);
        return webResult;
    }

    /*************************************************************** 分级菜单 ***************************************************************************/
    /**
     * 异步获取食物类别
     * 
     * @author scd
     * @return
     */
    @RequestMapping(value = FoodMaterialMapping.FOOD_TYPE_QUERY)
    @ResponseBody
    public WebResult<Boolean> getCatalogList() {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        FoodTree tree = new FoodTree();
        List<ZTree<TreePojo>> gradingList = new ArrayList<ZTree<TreePojo>>();
        tree.setTreeKind(treeKind);
        List<TreePojo> trees = treeService.queryTreeByCondition(tree);
        List<TreePojo> treePojos = getCatalogTreeList(trees);
        if (CollectionUtils.isNotEmpty(treePojos)) {
            gradingList.addAll(this.getDDLTrees(treePojos));
        }
        webResult.setData(gradingList);
        return webResult;
    }
    
    /**
     * 
     * 返回食材基本信息
     *
     * @author zj
     * @param fmId
     * @return
     */
    @RequestMapping(value = FoodMaterialMapping.GET_FM)
    @ResponseBody
    public WebResult<FoodMaterial> getFm(String fmId) {
        WebResult<FoodMaterial> webResult = new WebResult<FoodMaterial>();
        FoodMaterial fm = foodMaterialServcie.getFm(fmId);
        webResult.setSuc(fm);
        return webResult;
    }
    
    /**
     * 把所有菜单按父子级分类
     * 
     * @author scd
     * @return
     */
    private List<TreePojo> getCatalogTreeList(List<TreePojo> menuList) {
        // 把所有菜单按父子级分类
        Map<String, List<TreePojo>> menuTreeMap = new LinkedHashMap<String, List<TreePojo>>();
        if (CollectionUtils.isNotEmpty(menuList)) {
            for (TreePojo menu : menuList) {
                String parentCode = menu.getParentTreeId();
                if (!menuTreeMap.containsKey(parentCode)) {
                    menuTreeMap.put(parentCode, new ArrayList<TreePojo>());
                }
                menuTreeMap.get(parentCode).add(menu);
            }
        }
        // 获取一级目录
        List<TreePojo> oneMenuList = menuTreeMap.get("000");
        // 逐级填充完善菜单信息
        if (CollectionUtils.isNotEmpty(oneMenuList)) {
            // 递归填充childList
            for (TreePojo oneMenu : oneMenuList) {
                catalogRecursion(oneMenu, menuTreeMap);
            }
            // 去除没有子级的目录
            checkcCatalog(oneMenuList);
        }
        return oneMenuList;
    }

    /**
     * 递归填充childList
     * 
     * @author zcq
     * @param menu
     * @param menuMap
     */
    private void catalogRecursion(TreePojo menu, Map<String, List<TreePojo>> menuMap) {
        List<TreePojo> menuList = menuMap.get(menu.getTreeId());
        if (CollectionUtils.isNotEmpty(menuList)) {
            for (TreePojo nextGradeMenu : menuList) {
                catalogRecursion(nextGradeMenu, menuMap);
            }
            menu.setChildList(menuList);
        }
    }

    /**
     * 去除没有子级的目录
     * 
     * @author zcq
     * @param menuList
     */
    private void checkcCatalog(List<TreePojo> menuList) {
        if (CollectionUtils.isNotEmpty(menuList)) {
            List<TreePojo> tempList = new ArrayList<TreePojo>();
            tempList.addAll(menuList);
            for (TreePojo menu : menuList) {
                List<TreePojo> childList = menu.getChildList();
                checkcCatalog(childList);
            }
            menuList.removeAll(menuList);
            menuList.addAll(tempList);
        }
    }

    private List<ZTree<TreePojo>> getDDLTrees(List<TreePojo> menuList) {
        if (CollectionUtils.isEmpty(menuList)) {
            return null;
        }
        List<ZTree<TreePojo>> treeList = new ArrayList<ZTree<TreePojo>>();
        for (TreePojo productCatalog : menuList) {
            String catalogId = productCatalog.getTreeId();
            ZTree<TreePojo> tree = new ZTree<TreePojo>();
            tree.setId(catalogId);
            tree.setpId(productCatalog.getParentTreeId());
            tree.setName(productCatalog.getTreeName());
            tree.setValue(productCatalog);
            if (catalogId.length() <= 6) {
                tree.setIsParent(true);
                tree.setIconSkin("mulu");
            } else {
                tree.setIconSkin("menu");
            }
            treeList.add(tree);
            if (CollectionUtils.isNotEmpty(productCatalog.getChildList())) {
                List<ZTree<TreePojo>> childTreeList = this.getDDLTrees(productCatalog.getChildList());
                tree.setChildList(childTreeList);
            }
        }
        return treeList;
    }
    
    /** 
     * 保留文件名及后缀 
     */  
    public String getFileNameWithSuffix(String pathandname) {         
        int start = pathandname.lastIndexOf("/");  
        if (start != -1 ) {  
            return pathandname.substring(start + 1);  
        } else {  
            return null;  
        }         
    } 
    
    /**
     * 获取树
     * 
     * @param menuList
     * @return
     */
    protected List<ZTree<TreePojo>> getTrees(List<TreePojo> menuList) {
        if (CollectionUtils.isEmpty(menuList)) {
            return null;
        }
        List<ZTree<TreePojo>> treeList = new ArrayList<ZTree<TreePojo>>();
        for (TreePojo productCatalog : menuList) {
            String catalogId = productCatalog.getTreeId();
            ZTree<TreePojo> tree = new ZTree<TreePojo>();
            tree.setId(catalogId);
            tree.setpId(productCatalog.getParentTreeId());
            tree.setName(productCatalog.getTreeName());
            tree.setValue(productCatalog);
            if (CollectionUtils.isNotEmpty(productCatalog.getChildList())) {
                tree.setIsParent(true);
                tree.setIconSkin("mulu");
            } else {
                tree.setIconSkin("menu");
            }
            treeList.add(tree);
            if (CollectionUtils.isNotEmpty(productCatalog.getChildList())) {
                treeList.addAll(this.getTrees(productCatalog.getChildList()));
            }
        }
        return treeList;
    }

}

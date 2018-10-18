/*
 * FoodController.java	 1.0   2015-1-15
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.master.controller.basic;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.core.pojo.PageCondition;
import com.mnt.health.utils.HanyuToPinyin;
import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.health.utils.maths.CreateRandomCode;
import com.mnt.preg.examitem.condition.FoodCondition;
import com.mnt.preg.main.constant.MessageConstant;
import com.mnt.preg.main.enums.Flag;
import com.mnt.preg.master.entity.basic.FoodInfo;
import com.mnt.preg.master.entity.basic.FoodMaterialList;
import com.mnt.preg.master.entity.basic.FoodTree;
import com.mnt.preg.master.form.basic.FoodForm;
import com.mnt.preg.master.form.basic.FoodMaterialListForm;
import com.mnt.preg.master.mapping.basic.FoodMapping;
import com.mnt.preg.master.mapping.basic.FoodPage;
import com.mnt.preg.master.pojo.basic.FoodMaterialListInfoPojo;
import com.mnt.preg.master.pojo.basic.FoodPojo;
import com.mnt.preg.master.pojo.basic.TreePojo;
import com.mnt.preg.master.service.basic.FoodMaterialServcie;
import com.mnt.preg.master.service.basic.FoodService;
import com.mnt.preg.master.service.basic.TreeService;
import com.mnt.preg.web.constants.FoodConstant;
import com.mnt.preg.web.constants.PathConstant;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;
import com.mnt.preg.web.pojo.ZTree;



/**
 * 食物Controller
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2015-1-15 zy 初版
 */
@Controller
public class FoodController extends BaseController {

    @Resource
    private FoodMaterialServcie foodMaterialServcie;

    @Resource
    private FoodService foodService;

    @Resource
    private TreeService treeService;

    final String treeKind = "food";

    /**
     * 跳转到食谱一览页面
     * 
     * @return
     * 
     */
    @RequestMapping(value = FoodMapping.FOOD_CUISINE_QUERY)
    public String toFoodCuisine(Model model) {
        List<ZTree<TreePojo>> treeList = new ArrayList<ZTree<TreePojo>>();
        treeList.add(new ZTree<TreePojo>("000", "菜谱类型", true, "home"));
        FoodTree condition = new FoodTree();
        condition.setTreeKind(treeKind);
        List<TreePojo> menuList = treeService.queryTreeByCondition(condition);
        if (CollectionUtils.isNotEmpty(menuList)) {
            treeList.addAll(this.getTrees(menuList));
        }
        model.addAttribute("treeNodes_food", NetJsonUtils.listToJsonArray(treeList));// 食谱
        model.addAttribute("treeNodes", NetJsonUtils.listToJsonArray(getMaterialNodes()));// 食材
        return FoodPage.FOOD_VIEW;
    }

    private List<ZTree<TreePojo>> getMaterialNodes() {
        List<ZTree<TreePojo>> treeList = new ArrayList<ZTree<TreePojo>>();
        treeList.add(new ZTree<TreePojo>("000", "食物类别", true, "home"));
        FoodTree condition = new FoodTree();
        condition.setTreeKind("food_material");
        List<TreePojo> menuList = treeService.queryTreeByCondition(condition);
        if (CollectionUtils.isNotEmpty(menuList)) {
            treeList.addAll(this.getTrees(menuList));
        }
        return treeList;
    }

    /**
     * 异步加载食谱信息
     * 
     * @param request
     * @param condition
     * @return
     * 
     */
    @RequestMapping(value = FoodMapping.FOOD_CUISINE_AJAX_QUERY)
    public @ResponseBody
    PageCondition ajaxQueryFoodCuisine(FoodCondition condition) {
        PageCondition page = foodService.queryFoodForPage(condition);
        @SuppressWarnings("unchecked")
        List<FoodPojo> list = (List<FoodPojo>) page.getData();
        if (CollectionUtils.isNotEmpty(list)) {
            for (FoodPojo food : list) {
                FoodTree tree = new FoodTree();
                tree.setTreeKind(treeKind);
                tree.setTreeId(food.getFoodType());
                List<TreePojo> listTree = treeService.queryTreeByCondition(tree);
                if (listTree != null && listTree.size() == 1) {
                    food.setFoodTreeType(listTree.get(0).getTreeName());
                }
            }
        }
        return page;
    }

    /**
     * 添加食谱
     * 
     * @param request
     * @param foodForm
     * @param feForm
     * @return
     * 
     */
    @RequestMapping(value = FoodMapping.FOOD_CUISINE_ADD)
    public @ResponseBody
    WebResult<Boolean> addFoodCuisine(FoodForm foodForm, FoodMaterialListForm fmlForm) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        // 记录食谱信息
        FoodInfo food = new FoodInfo();
        BeanUtils.copyProperties(foodForm, food);
        food.setFoodTreeType(foodForm.getFoodType());
        String foodPic = foodForm.getFoodPic();
        // 生成图片名称并移动图片到指定文件夹
        if (StringUtils.isNotEmpty(foodPic)) {
            String fromPath = publicConfig.getResourceAbsolutePath() + PathConstant.template_image_temp
                    + foodPic.substring(foodPic.lastIndexOf("/") + 1);
            String suffix = foodPic.substring(foodPic.lastIndexOf("."));
            String picName = HanyuToPinyin.getInstance().getFullStringPinYin(foodForm.getFoodName());
            String destPath = getFoodPic(picName, suffix, "cuisine");
            try {
                FileUtils.moveFile(new File(fromPath), new File(destPath));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            food.setFoodPic(destPath.substring(destPath.lastIndexOf("/") + 1));// 重新设置图片路径
        }

        String[] benefitAndHarm = getBenefitAndHarm(foodForm);
        food.setFoodUnit("1");
        food.setFoodBenefit(benefitAndHarm[0]);
        food.setFoodHarm(benefitAndHarm[1]);
        // 记录食谱的组成信息
        List<FoodMaterialList> fmlList = getFmlList(fmlForm);
        foodService.addFood(food, fmlList, "C000000");;
        webResult.setSuc(true, MessageConstant.success_message);
        return webResult;
    }

    /**
     * 获取食谱图片路径信息
     * 
     * @author zcq
     * @param foodName
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
     * 把有益和有害成分List转成字符串
     * 
     * @param foodForm
     * @return BenefitAndHarm
     * 
     */
    private String[] getBenefitAndHarm(FoodForm foodForm) {
        String[] BenefitAndHarm = new String[2];
        // 有益成分
        String foodBenefit = "";
        if (foodForm.getFoodBenefitList() != null && foodForm.getFoodBenefitList().size() > 0) {
            for (int i = 0; i < foodForm.getFoodBenefitList().size(); i++) {
                if (i == foodForm.getFoodBenefitList().size() - 1) {
                    foodBenefit += foodForm.getFoodBenefitList().get(i);
                } else {
                    foodBenefit += foodForm.getFoodBenefitList().get(i) + ",";
                }
            }
        }
        BenefitAndHarm[0] = foodBenefit;
        // 有害成分
        String foodHarm = "";
        if (foodForm.getFoodHarmList() != null && foodForm.getFoodHarmList().size() > 0) {
            for (int i = 0; i < foodForm.getFoodHarmList().size(); i++) {
                if (i == foodForm.getFoodHarmList().size() - 1) {
                    foodHarm += foodForm.getFoodHarmList().get(i);
                } else {
                    foodHarm += foodForm.getFoodHarmList().get(i) + ",";
                }
            }
        }
        BenefitAndHarm[1] = foodHarm;

        return BenefitAndHarm;
    }

    /**
     * 分析食物的组成以及所占的比例
     * 
     * @param fmlForm
     * @return List<FoodMaterialListBo>
     * 
     */
    private List<FoodMaterialList> getFmlList(FoodMaterialListForm fmlForm) {
        List<FoodMaterialList> fmlList = new ArrayList<FoodMaterialList>();
        String[] extId = fmlForm.getFmId().split(",");
        String[] extCount = fmlForm.getFmlAmount().split(",");
        String[] extType = fmlForm.getFmlType().split(",");
        String[] extMaterial = fmlForm.getFmlMaterial().split(",");
        int sum = 0;
        for (int count = 0; count < extCount.length; count++) {
            sum = sum + Integer.parseInt(extCount[count]);
        }
        BigDecimal tempSum = BigDecimal.ZERO;
        for (int i = 0; i < extId.length; i++) {
            FoodMaterialList fmlBo = new FoodMaterialList();
            fmlBo.setFmlAmount(Integer.parseInt(extCount[i]));
            if (extMaterial.length != 0) {
                fmlBo.setFmlMaterial(extMaterial[i]);
            } else {
                fmlBo.setFmlMaterial("MAIN");
            }
            fmlBo.setFmId(extId[i]);
            if (extType[i].equals("primary")) {
                fmlBo.setFmlType("primary");
                fmlBo.setFmlIsIntakeType("yes");
            } else {
                fmlBo.setFmlType("secondary");
                fmlBo.setFmlIsIntakeType("no");
            }
            BigDecimal df = new BigDecimal(((double) Integer.parseInt(extCount[i]) / (double) sum) * 100);
            BigDecimal temp = df.setScale(2, BigDecimal.ROUND_HALF_UP);
            if (i == extId.length - 1) {
                BigDecimal ssum = BigDecimal.valueOf(100.00);
                fmlBo.setFmlPercent(ssum.subtract(tempSum));
            } else {
                tempSum = temp.add(tempSum);
                fmlBo.setFmlPercent(temp.setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            fmlList.add(fmlBo);
        }
        return fmlList;
    }

    /**
     * 异步查询
     * 
     * @param request
     * @param foodId
     * @param model
     * @return
     * 
     */
    @RequestMapping(value = FoodMapping.FOOD_QUERY_MATERIAL)
    public @ResponseBody
    WebResult<FoodMaterialListInfoPojo> queryFoodPojo(@RequestParam String foodId) {
        List<FoodMaterialListInfoPojo> list = foodService.queryFoodMaterialListByFoodId(foodId);
        WebResult<FoodMaterialListInfoPojo> webResult = new WebResult<FoodMaterialListInfoPojo>();
        webResult.setData(list);
        return webResult;
    }

    /**
     * 异步获取菜谱类别
     * 
     * @author dhs
     * @return
     */
    @RequestMapping(value = FoodMapping.FOOD_CATALOG_QUERY_ALL)
    @ResponseBody
    public WebResult<Boolean> getProductCatalogList() {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        FoodTree condition = new FoodTree();
        condition.setTreeKind(treeKind);
        List<TreePojo> menuList = treeService.getTree_returnlist(condition);
        List<TreePojo> cataList = getMenuTreeList(menuList);
        List<ZTree<TreePojo>> gradingList = new ArrayList<ZTree<TreePojo>>();
        if (CollectionUtils.isNotEmpty(cataList)) {
            gradingList.addAll(this.getDDLTrees(cataList));
        }
        webResult.setData(gradingList);
        return webResult;
    }

    private List<ZTree<TreePojo>> getDDLTrees(List<TreePojo> menuList) {
        if (CollectionUtils.isEmpty(menuList)) {
            return null;
        }
        List<ZTree<TreePojo>> treeList = new ArrayList<ZTree<TreePojo>>();
        for (TreePojo treePojo : menuList) {
            String catalogId = treePojo.getTreeId();
            ZTree<TreePojo> tree = new ZTree<TreePojo>();
            tree.setId(catalogId);
            tree.setpId(treePojo.getParentTreeId());
            tree.setName(treePojo.getTreeName());
            tree.setValue(treePojo);
            if (CollectionUtils.isNotEmpty(treePojo.getChildList())) {
                tree.setIsParent(true);
                tree.setIconSkin("mulu");
            } else {
                tree.setIconSkin("menu");
            }
            treeList.add(tree);
            if (CollectionUtils.isNotEmpty(treePojo.getChildList())) {
                List<ZTree<TreePojo>> childTreeList = this.getDDLTrees(treePojo.getChildList());
                tree.setChildList(childTreeList);
            }
        }
        return treeList;
    }

    /**
     * 把所有菜单按父子级分类
     * 
     * @author zcq
     * @return
     */
    private List<TreePojo> getMenuTreeList(List<TreePojo> menuList) {
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
                menuRecursion(oneMenu, menuTreeMap);
            }
            // 去除没有子级的目录
            checkMenu(oneMenuList);
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
    private void menuRecursion(TreePojo menu, Map<String, List<TreePojo>> menuMap) {
        List<TreePojo> menuList = menuMap.get(menu.getTreeId());
        if (CollectionUtils.isNotEmpty(menuList)) {
            for (TreePojo nextGradeMenu : menuList) {
                menuRecursion(nextGradeMenu, menuMap);
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
    private void checkMenu(List<TreePojo> menuList) {
        if (CollectionUtils.isNotEmpty(menuList)) {
            List<TreePojo> tempList = new ArrayList<TreePojo>();
            tempList.addAll(menuList);
            for (TreePojo menu : menuList) {
                List<TreePojo> childList = menu.getChildList();
                checkMenu(childList);
            }
            menuList.removeAll(menuList);
            menuList.addAll(tempList);
        }
    }

    /**
     * 修改食谱信息
     * 
     * @param request
     * @param foodForm
     * @param feForm
     * @return
     * 
     */
    @RequestMapping(value = FoodMapping.FOOD_CUISINE_UPDATE)
    public @ResponseBody
    WebResult<Boolean> updateFoodCuisine(FoodForm foodForm, FoodMaterialListForm fmlForm) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        // 记录食谱信息
        FoodInfo food = new FoodInfo();
        BeanUtils.copyProperties(foodForm, food);

        String foodPic = food.getFoodPic();
        // 图片名称
        String picName = foodForm.getFoodPicOld();
        // 目标文件路径
        String destPath = publicConfig.getResourceAbsolutePath() + PathConstant.food_cuisine_image + picName;
        // 若图片为空则新生成图片信息
        if (StringUtils.isEmpty(picName.replaceAll("/", ""))) {
            String suffix = null;
            if (!StringUtils.isEmpty(foodPic) && foodPic.indexOf(".") != -1) {
                suffix = foodPic.substring(foodPic.indexOf("."));
            } else {
                suffix = ".jpg";
            }
            picName = HanyuToPinyin.getInstance().getFullStringPinYin(food.getFoodName());
            destPath = getFoodPic(picName, suffix, "cuisine");
        }
        // 覆盖图片
        if (StringUtils.isNotEmpty(foodPic) && foodPic.indexOf("/") > -1) {
            // 源文件路径
            String fromPath = publicConfig.getResourceAbsolutePath() + PathConstant.template_image_temp
                    + foodPic.substring(foodPic.lastIndexOf("/") + 1);
            // 删除原目录下的图片
            FileUtils.deleteQuietly(new File(destPath));
            // 移动新图片到目标文件夹下
            try {
                // destPath = destPath.replace(destPath.substring(destPath.indexOf(".")),
                // fromPath.substring(fromPath.indexOf(".")));
                destPath = publicConfig.getResourceAbsolutePath() + PathConstant.food_cuisine_image
                        + getFileNameWithSuffix(foodPic);
                FileUtils.moveFile(new File(fromPath), new File(destPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            food.setFoodPic(destPath.substring(destPath.lastIndexOf("/") + 1));
        }

        String[] benefitAndHarm = getBenefitAndHarm(foodForm);
        food.setFoodBenefit(benefitAndHarm[0]);
        food.setFoodHarm(benefitAndHarm[1]);
        // 设置食物的食材组成信息
        List<FoodMaterialList> fmlList = new ArrayList<FoodMaterialList>();
        List<FoodMaterialList> fmlInfoList = getFmlList(fmlForm);
        if (fmlInfoList != null && fmlInfoList.size() > 0) {
            for (FoodMaterialList fmlInfo : fmlInfoList) {
                FoodMaterialList fml = new FoodMaterialList();
                BeanUtils.copyProperties(fmlInfo, fml);
                fmlList.add(fml);
            }
        }
        foodService.updateFood(food, fmlList);

        webResult.setSuc(true, MessageConstant.success_message);
        return webResult;
    }

    /**
     * 删除食谱
     * 
     * @param request
     * @param foodId
     * @return
     * 
     */
    @RequestMapping(value = FoodMapping.FOOD_CUISINE_REMOVE)
    public @ResponseBody
    WebResult<Boolean> deleteFoodCuisine(@RequestParam String foodId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        foodService.removeFood(foodId);
        webResult.setSuc(true, MessageConstant.success_message);
        return webResult;
    }

    /**
     * 导入Excel食材数据到数据库中
     * 
     * @param request
     * @param upForm
     * @return
     * 
     */
//    @RequestMapping(value = FoodMapping.IMPORT_FOOD_EXT)
//    public @ResponseBody
//    WebResult<Boolean> impFoodExt(UploadForm upForm) {
//
//        WebResult<Boolean> webResult = new WebResult<Boolean>();
//        MultipartFile multipartFile = upForm.getFiledata();
//
//        int one = 1;
//        // 文件类型
//        String fileType = "";
//        try {
//            // 文件名称
//            String fileName = multipartFile.getOriginalFilename();
//            fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.lastIndexOf(".") + 4);
//        } catch (Exception e) {
//            fileType = "";
//        }
//        if (!fileType.toLowerCase().equals("xls")) {
//            webResult.setError("第 导入的文件格式不正确，请导入后缀为.xls的文件！", false);
//            return webResult;
//        }
//
//        HSSFWorkbook wb = null;
//        InputStream is = null;
//        try {
//            is = multipartFile.getInputStream();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        // excel工作簿
//        try {
//            wb = new HSSFWorkbook(is);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        List<List<Object>> list = new ArrayList<List<Object>>();
//        // 获取sheet
//        try {
//            for (int sheets = 0; sheets < wb.getNumberOfSheets(); sheets++) {
//                HSSFSheet sheet = wb.getSheetAt(sheets);
//                // 最大行数
//                int lastRowNum = sheet.getLastRowNum();
//                if (sheet.getRow(0) == null) {
//                    webResult.setError("第 " + (sheets + one) + " 张表结构不正确，请重新选择", false);
//                    return webResult;
//                }
//                // 最大列数
//                int lastCellNum = sheet.getRow(0).getLastCellNum();
//                if (lastCellNum != 40) {
//                    webResult.setError("第 " + (sheets + one) + " 张表结构不正确，请重新选择", false);
//                    return webResult;
//                }
//                if (lastRowNum < 2) {
//                    webResult.setError("第 " + (sheets + one) + " 张表中没有数据，请重新选择", false);
//                    return webResult;
//                }
//                for (int i = 1; i <= lastRowNum; i++) {
//                    String[] params = new String[40];
//                    HSSFRow row = sheet.getRow(i);
//                    int physicalNum = row.getLastCellNum();
//                    try {
//                        if (row != null && physicalNum >= 40) {
//                            for (int j = 0; j < lastCellNum; j++) {
//                                String cell = "0";
//                                if (row.getCell(j) == null || row.getCell(j).equals("")) {
//                                    params[j] = cell;
//                                    continue;
//                                }
//                                if (row.getCell(j).getCellType() == HSSFCell.CELL_TYPE_STRING) {
//                                    cell = row.getCell(j).getStringCellValue();
//                                } else if (row.getCell(j).getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
//                                    DecimalFormat df = new DecimalFormat("#");
//                                    cell = String.valueOf(df.format(row.getCell(j).getNumericCellValue()));
//                                } else if (row.getCell(j).getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
//                                    cell = row.getCell(j).getCellFormula();
//                                }
//                                cell = cell.replace("?", "");
//                                params[j] = cell;
//                            }
//                        } else {
//                            continue;
//                            // throw new Exception("第" + (sheets+one) + "张表的第" +
//                            // (i+one) + "行，信息不正确或为空");
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        throw new Exception("遍历读取Excel表格数据时出错！" + " [" + sheet.getSheetName() + "] 表的第" + (i + one)
//                                + "行!<br>错误信息：" + e.getMessage());
//                    }
//                    // 设置食材信息
//                    try {
//                        setFoodMaterialParams(list, params, sheet, sheets, i);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        webResult.setError(
//                                "[" + sheet.getSheetName() + "] 表的第" + (i + one) + "行的数据有错误！<br> 错误信息："
//                                        + e.getMessage(), false);
//                        return webResult;
//                    }
//                }
//                // 保存操作
//                try {
//                    saveFoodMaterial(list, wb, sheets);
//                } catch (Exception e) {
//                    webResult.setError("导入Excel到数据库失败！<br>" + e.getMessage(), false);
//                    return webResult;
//                }
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            webResult.setError("导入Excel到数据库失败！<br>" + e.getMessage(), false);
//            return webResult;
//        }
//        webResult.setSuc(true, "导入成功");
//        return webResult;
//    }

//    private void setFoodMaterialParams(List<List<Object>> list, String[] params, HSSFSheet sheet, int sheets, int i)
//            throws Exception {
//        int one = 1;
//        FoodMaterialPojo fmEntity = new FoodMaterialPojo();
//        FoodMaterialExt fmlEntity = new FoodMaterialExt();
//        int count = 0;
//        // 设置食材类型
//        fmEntity.setFmId(params[count++]);
//        // 设置食材类型ID
//        String fmtId = getFeiId(params[count++]);
//        if (!fmtId.equals("-1")) {
//            fmEntity.setFmType(fmtId);
//        } else {
//            throw new Exception("数据库中没有该 [" + params[count - one] + "] 食材类型,在第 [" + (sheets + one) + "] 张工作表,第"
//                    + (i + one)
//                    + "行!");
//        }
//        // 食材名称
//        fmEntity.setFmName(params[count++]);
//        // 别名
//        if (params[count].equals("0") || params[count] == null || params[count].trim().equals("")) {
//            fmEntity.setFmAlias("");
//            count++;
//        } else {
//            fmEntity.setFmAlias(params[count++]);
//        }
//        // GI值
//        fmEntity.setFmGi(BigDecimal.valueOf(Double.valueOf(params[count++])));
//        // 食材级别
//        String foodLevelName = params[count];
//        if (foodLevelName.equals("黄灯")) {
//            fmlEntity.setFmLevel("yellow");
//        }
//        if (foodLevelName.equals("红灯")) {
//            fmlEntity.setFmLevel("red");
//        }
//        if (foodLevelName.equals("绿灯")) {
//            fmlEntity.setFmLevel("green");
//        }
//        count++;
//        // 是否属于优质蛋白质
//        fmEntity.setFmProtidFlag(getProtidFlag(params[count++]));
//        // 油脂来源
//        fmEntity.setFmFatType(getFatType(params[count++]));
//        // 可食部
//        fmlEntity.setFmEsculent(Integer.parseInt(params[count++]));
//        fmlEntity.setFmEnergy(BigDecimal.valueOf(Double.parseDouble(params[count++])));
//        fmlEntity.setFmProtid(BigDecimal.valueOf(Double.parseDouble(params[count++])));
//        fmlEntity.setFmFat(BigDecimal.valueOf(Double.parseDouble(params[count++])));
//        fmlEntity.setFmSfa(BigDecimal.valueOf(Double.parseDouble(params[count++])));
//        fmlEntity.setFmMfa(BigDecimal.valueOf(Double.parseDouble(params[count++])));
//        fmlEntity.setFmPfa(BigDecimal.valueOf(Double.parseDouble(params[count++])));
//        fmlEntity.setFmCbr(BigDecimal.valueOf(Double.parseDouble(params[count++])));
//        fmlEntity.setFmDf(BigDecimal.valueOf(Double.parseDouble(params[count++])));
//        fmlEntity.setFmCho(BigDecimal.valueOf(Double.parseDouble(params[count++])));
//        fmlEntity.setFmAshc(BigDecimal.valueOf(Double.parseDouble(params[count++])));
//        fmlEntity.setFmVa(BigDecimal.valueOf(Double.parseDouble(params[count++])));
//        fmlEntity.setFmVc(BigDecimal.valueOf(Double.parseDouble(params[count++])));
//        fmlEntity.setFmVe(BigDecimal.valueOf(Double.parseDouble(params[count++])));
//        fmlEntity.setFmCarotin(BigDecimal.valueOf(Double.parseDouble(params[count++])));
//        fmlEntity.setFmVb1(BigDecimal.valueOf(Double.parseDouble(params[count++])));
//        fmlEntity.setFmVb2(BigDecimal.valueOf(Double.parseDouble(params[count++])));
//        fmlEntity.setFmAf(BigDecimal.valueOf(Double.parseDouble(params[count++])));
//        fmlEntity.setFmEca(BigDecimal.valueOf(Double.parseDouble(params[count++])));
//        fmlEntity.setFmEp(BigDecimal.valueOf(Double.parseDouble(params[count++])));
//        fmlEntity.setFmEk(BigDecimal.valueOf(Double.parseDouble(params[count++])));
//        fmlEntity.setFmEna(BigDecimal.valueOf(Double.parseDouble(params[count++])));
//        fmlEntity.setFmEmg(BigDecimal.valueOf(Double.parseDouble(params[count++])));
//        fmlEntity.setFmEfe(BigDecimal.valueOf(Double.parseDouble(params[count++])));
//        fmlEntity.setFmEzn(BigDecimal.valueOf(Double.parseDouble(params[count++])));
//        fmlEntity.setFmEse(BigDecimal.valueOf(Double.parseDouble(params[count++])));
//        fmlEntity.setFmEcu(BigDecimal.valueOf(Double.parseDouble(params[count++])));
//        fmlEntity.setFmEmn(BigDecimal.valueOf(Double.parseDouble(params[count++])));
//        // 简介
//        fmEntity.setFmDesc(params[count++]);
//        // 功效
//        fmEntity.setFmEfficacy(params[count++]);
//        // 适用人群
//        fmEntity.setFmArea(params[count++]);
//        // 膳食结构ID
//        String fsId = getFsId(sheet.getSheetName());
//        if (!fsId.equals("-1")) {
//            fmEntity.setFsId(fsId);
//        } else {
//            throw new Exception("数据库中没有该 [" + sheet.getSheetName() + "] 膳食结构!");
//        }
//        // 图片
//        fmEntity.setFmPic("/resource/upload/food/ext/" + params[count++]);
//        // 记录校验成功的数据
//        List<Object> tempList = new ArrayList<Object>();
//        tempList.add(fmEntity);
//        tempList.add(fmlEntity);
//        list.add(tempList);
//    }

//    private void saveFoodMaterial(List<List<Object>> list, HSSFWorkbook wb, int sheets) throws Exception {
//        if (sheets == wb.getNumberOfSheets() - 1) {
//            for (int i_list = 0; i_list < list.size(); i_list++) {
//                // 记录食材信息
//                FoodMaterial fmBo = new FoodMaterial();
//                BeanUtils.copyProperties(fmBo, (FoodMaterialPojo) list.get(i_list).get(0));
//                // 记录食材元素信息
//                FoodMaterialExt fmeBo = new FoodMaterialExt();
//                BeanUtils.copyProperties(fmeBo, (FoodMaterialExt) list.get(i_list).get(1));
//                String fmId = "";
//
//                FoodMaterial foodMaterial = new FoodMaterial();
//                BeanUtils.copyProperties(foodMaterial, foodMaterial);
//                FoodMaterialExt foodMaterialExt = new FoodMaterialExt();
//                BeanUtils.copyProperties(foodMaterialExt, fmeBo);
//
//                fmId = foodMaterialServcie.addFoodMaterial(foodMaterial, foodMaterialExt, "C000000");
//                FoodMaterialCode fmcBo = new FoodMaterialCode();
//                fmcBo.setCode(fmBo.getFmId());
//                fmcBo.setUuid(fmId);
//
//                foodMaterialServcie.addFoodMaterialCode(fmcBo);
//            }
//        }
//    }

    /**
     * 名称获取膳食结构ID
     * 
     * @param fsName
     * @return
     * 
     */
    public String getFsId(String fsName) {
        // 谷类及杂豆
        if (fsName.equals(FoodConstant.FSI_GRAIN)) {
            return FoodConstant.FSI_GRAIN_ID;
        }
        // 淀粉类蔬菜
        if (fsName.equals(FoodConstant.FSI_STARCHY_V)) {
            return FoodConstant.FSI_STARCHY_V_ID;
        }
        // 大豆类
        if (fsName.equals(FoodConstant.FSI_PEAS)) {
            return FoodConstant.FSI_PEAS_ID;
        }
        // 非淀粉类蔬菜
        if (fsName.equals(FoodConstant.FSI_NOSTARCHY_V)) {
            return FoodConstant.FSI_NOSTARCHY_V_ID;
        }
        // 水果
        if (fsName.equals(FoodConstant.FSI_FRUITS)) {
            return FoodConstant.FSI_FRUITS_ID;
        }
        // 鱼肉蛋
        if (fsName.equals(FoodConstant.FSI_FME)) {
            return FoodConstant.FSI_FME_ID;
        }
        // 乳类
        if (fsName.equals(FoodConstant.FSI_MILK)) {
            return FoodConstant.FSI_MILK_ID;
        }
        // 坚果类
        if (fsName.equals(FoodConstant.FSI_NUTS)) {
            return FoodConstant.FSI_NUTS_ID;
        }
        // 油脂
        if (fsName.equals(FoodConstant.FSI_FAT)) {
            return FoodConstant.FSI_FAT_ID;
        }
        // 水
        if (fsName.equals(FoodConstant.FSI_WATER)) {
            return FoodConstant.FSI_WATER_ID;
        }
        // 盐
        if (fsName.equals(FoodConstant.FSI_SALT)) {
            return FoodConstant.FSI_SALT_ID;
        }
        if (fsName.equals("不加入结构计算")) {
            return "999";
        }
        return "-1";
    }

    /**
     * 食材类型名称获取ID
     * 
     * @param feiName
     * @return
     * 
     */
    public String getFeiId(String feiName) {
        // 谷类
        if (feiName.equals("谷类") || feiName.equals("谷类及制品")) {
            return FoodConstant.FEI_GRAIN_ID;
        }
        // 薯类淀粉
        if (feiName.equals("薯类淀粉") || feiName.equals("薯类淀粉及制品")) {
            return FoodConstant.FEI_STARCHY_ID;
        }
        // 蔬菜
        if (feiName.equals(FoodConstant.FEI_VEG)) {
            return FoodConstant.FEI_VEG_ID;
        }
        // 菌藻
        if (feiName.equals(FoodConstant.FEI_BA)) {
            return FoodConstant.FEI_BA_ID;
        }
        // 蛋类
        if (feiName.equals(FoodConstant.FEI_EGG)) {
            return FoodConstant.FEI_EGG_ID;
        }
        // 禽肉
        if (feiName.equals(FoodConstant.FEI_POULTRY)) {
            return FoodConstant.FEI_POULTRY_ID;
        }
        // 畜肉
        if (feiName.equals(FoodConstant.FEI_MEAT)) {
            return FoodConstant.FEI_MEAT_ID;
        }
        // 鱼虾蟹贝
        if (feiName.equals(FoodConstant.FEI_FISH)) {
            return FoodConstant.FEI_FISH_ID;
        }
        // 干豆
        if (feiName.equals(FoodConstant.FEI_BEANS) || feiName.equals("豆类及制品")) {
            return FoodConstant.FEI_BEANS_ID;
        }
        // 水果
        if (feiName.equals(FoodConstant.FEI_FRUITS) || feiName.equals("水果及其制品")) {
            return FoodConstant.FEI_FRUITS_ID;
        }
        // 糖蜜饯
        if (feiName.equals(FoodConstant.FEI_SUGAR)) {
            return FoodConstant.FEI_SUGAR_ID;
        }
        // 乳类
        if (feiName.equals(FoodConstant.FEI_MILK) || feiName.equals("乳类及制品")) {
            return FoodConstant.FEI_MILK_ID;
        }
        // 坚果种子
        if (feiName.equals(FoodConstant.FEI_NUTS) || feiName.equals("坚果类及制品")) {
            return FoodConstant.FEI_NUTS_ID;
        }
        // 油脂
        if (feiName.equals(FoodConstant.FEI_FAT)) {
            return FoodConstant.FEI_FAT_ID;
        }
        // 软饮料
        if (feiName.equals(FoodConstant.FEI_SD) || feiName.equals("饮料类")) {
            return FoodConstant.FEI_SD_ID;
        }
        // 酒精饮料
        if (feiName.equals(FoodConstant.FEI_WINE)) {
            return FoodConstant.FEI_WINE_ID;
        }
        // 小吃甜饼
        if (feiName.equals(FoodConstant.FEI_CAKE)) {
            return FoodConstant.FEI_CAKE_ID;
        }
        // 速食食品
        if (feiName.equals(FoodConstant.FEI_FF)) {
            return FoodConstant.FEI_FF_ID;
        }
        // 调味品
        if (feiName.equals(FoodConstant.FEI_SEA)) {
            return FoodConstant.FEI_SEA_ID;
        }
        // 婴幼儿食品
        if (feiName.equals(FoodConstant.FEI_INFANT)) {
            return FoodConstant.FEI_INFANT_ID;
        }
        // 药膳及其它
        if (feiName.equals("其它") || feiName.equals("其他")) {
            return FoodConstant.FEI_MO_ID;
        }
        // 盐
        if (feiName.equals(FoodConstant.FEI_SALT)) {
            return FoodConstant.FEI_SALT_ID;
        }
        if (feiName.equals("非淀粉类蔬菜及制品")) {
            return "23";
        }
        if (feiName.equals("特膳食品")) {
            return "24";
        }
        if (feiName.equals("肉类及制品")) {
            return "25";
        }
        return "-1";
    }

    /**
     * 判断是否属于优质蛋白质
     * 
     * @param name
     * @return
     * 
     */
    public String getProtidFlag(String name) {
        if (StringUtils.isEmpty(name) || "0".equals(name)) {
            return "yes";
        }
        if (name.equals(FoodConstant.FOOD_PROTID_Y)) {
            return "yes";
        }
        if (name.equals(FoodConstant.FOOD_PROTID_N)) {
            return "no";
        }
        return null;
    }

    /**
     * 判断油脂的来源
     * 
     * @param name
     * @return
     * 
     */
    public String getFatType(String name) {
        if (StringUtils.isEmpty(name) || "0".equals(name)) {
            return "no";
        }
        if (name.equals(FoodConstant.FAT_TYPE_ANIMAL)) {
            return "animal";
        }
        if (name.equals(FoodConstant.FAT_TYPE_PLANT)) {
            return "plant";
        }
        if (name.equals(FoodConstant.FAT_TYPE_NO)) {
            return "no";
        }
        return null;
    }

    /**
     * 导出食材库到Excel
     * 
     * @param response
     * @throws Exception
     */
//    @RequestMapping(value = "/page/food/export_food_material.action")
//    public void exportFoodMaterial(HttpServletResponse response) throws Exception {
//        String[] sheetName = new String[] {"谷类及杂豆", "淀粉类蔬菜", "非淀粉类蔬菜", "水果", "奶制品", "鱼肉蛋", "大豆类", "坚果", "油脂", "盐", "水",
//                "其它"};
//        String[] title = new String[] {"序列", "种类", "食材名称", "食材别名", "GI", "红绿灯", "优质蛋白质", "脂肪来源", "可食部(%)", "热量_kcal",
//                "蛋白质(g)", "脂肪(g)", "饱和脂肪(g)", "单不饱和脂肪酸(g)", "多不饱和脂肪酸(g)", "碳水化合物(g)", "膳食纤维(g)", "胆固醇(mg)", "灰分(g)",
//                "维生素A", "维生素C(mg)", "维生素E(mg)", "胡箩卜素(μg)", "硫胺素(mg)", "核黄素(mg)", "烟酸(mg)", "钙(mg)", "磷(mg)", "钾(mg)",
//                "钠(mg)", "镁(mg)", "铁(mg)", "锌(mg)", "硒", "铜(mg)", "锰(mg)", "食材简介", "营养价值", "使用人群"};
//
//        FoodMaterial condition = new FoodMaterial();
//        List<FoodMaterialPojo> list = foodMaterialServcie.queryFoodMaterial(condition);
//
//        List<List<Object[]>> materialList = new ArrayList<List<Object[]>>();
//        List<Object[]> sheet1 = new ArrayList<Object[]>();
//        List<Object[]> sheet2 = new ArrayList<Object[]>();
//        List<Object[]> sheet3 = new ArrayList<Object[]>();
//        List<Object[]> sheet4 = new ArrayList<Object[]>();
//        List<Object[]> sheet5 = new ArrayList<Object[]>();
//        List<Object[]> sheet6 = new ArrayList<Object[]>();
//        List<Object[]> sheet7 = new ArrayList<Object[]>();
//        List<Object[]> sheet8 = new ArrayList<Object[]>();
//        List<Object[]> sheet9 = new ArrayList<Object[]>();
//        List<Object[]> sheet10 = new ArrayList<Object[]>();
//        List<Object[]> sheet11 = new ArrayList<Object[]>();
//        List<Object[]> sheet12 = new ArrayList<Object[]>();
//        if (list != null && list.size() > 0) {
//            for (FoodMaterialPojo food : list) {
//                Object[] obj = new Object[40];
//                setFoodMaterial(obj, food);
//                FoodMaterialPojo foodDetail = foodMaterialServcie.getFoodMaterialAndExtByFmId(food.getFmId());
//                setFoodMaterialExt(obj, foodDetail.getFoodMaterialExtVo());
//                if ("001".equals(food.getFsId())) {// 谷类及杂豆
//                    sheet1.add(obj);
//                }
//                if ("002".equals(food.getFsId())) {// 淀粉类蔬菜
//                    sheet2.add(obj);
//                }
//                if ("003".equals(food.getFsId())) {// 非淀粉类蔬菜
//                    sheet3.add(obj);
//                }
//                if ("004".equals(food.getFsId())) {// 水果
//                    sheet4.add(obj);
//                }
//                if ("005".equals(food.getFsId())) {// 奶制品
//                    sheet5.add(obj);
//                }
//                if ("006".equals(food.getFsId())) {// 鱼肉蛋
//                    sheet6.add(obj);
//                }
//                if ("007".equals(food.getFsId())) {// 大豆类
//                    sheet7.add(obj);
//                }
//                if ("008".equals(food.getFsId())) {// 坚果
//                    sheet8.add(obj);
//                }
//                if ("009".equals(food.getFsId())) {// 油脂
//                    sheet9.add(obj);
//                }
//                if ("010".equals(food.getFsId())) {// 盐
//                    sheet10.add(obj);
//                }
//                if ("011".equals(food.getFsId())) {// 水
//                    sheet11.add(obj);
//                }
//                if ("999".equals(food.getFsId())) {// 其它
//                    sheet12.add(obj);
//                }
//            }
//            materialList.add(sheet1);
//            materialList.add(sheet2);
//            materialList.add(sheet3);
//            materialList.add(sheet4);
//            materialList.add(sheet5);
//            materialList.add(sheet6);
//            materialList.add(sheet7);
//            materialList.add(sheet8);
//            materialList.add(sheet9);
//            materialList.add(sheet10);
//            materialList.add(sheet11);
//            materialList.add(sheet12);
//        }
//        exportReportFoodMaterial(response, sheetName, title, materialList);
//    }

    /**
     * 设置生成Excel中食材的主表信息
     * 
     * @param obj
     * @param food
     */
//    private void setFoodMaterial(Object[] obj, FoodMaterialPojo food) {
//        obj[0] = food.getFmId();
//        obj[1] = getFeiIdE(food.getFmType());
//        obj[2] = food.getFmName();
//        obj[3] = food.getFmAlias();
//        obj[4] = food.getFmGi();
//        obj[6] = "yes".equals(food.getFmProtidFlag()) ? "是" : "否";
//        String fatType = food.getFmFatType();
//        obj[7] = "plant".equals(fatType) ? "植物" : "animal".equals(fatType) ? "动物" : "无";
////        obj[36] = food.getFmDesc();
////        obj[37] = food.getFmEfficacy();
////        obj[38] = food.getFmArea();
//        obj[39] = food.getFmPic();
//    }

    /**
     * 设置导出食材的元素值
     * 
     * @param obj
     * @param food
     */
//    private void setFoodMaterialExt(Object[] obj, FoodMaterialExt food) {
//        String levelName = food.getFmLevel();
//        obj[5] = "red".equals(levelName) ? "红灯" : "green".equals(levelName) ? "绿灯" : "黄灯";
//        int count = 8;
//        obj[count++] = food.getFmEsculent();
//        obj[count++] = food.getFmEnergy();
//        obj[count++] = food.getFmProtid();
//        obj[count++] = food.getFmFat();
//        obj[count++] = food.getFmSfa();
//        obj[count++] = food.getFmMfa();
//        obj[count++] = food.getFmPfa();
//        obj[count++] = food.getFmCbr();
//        obj[count++] = food.getFmDf();
//        obj[count++] = food.getFmCho();
//        obj[count++] = food.getFmAshc();
//        obj[count++] = food.getFmVa();
//        obj[count++] = food.getFmVc();
//        obj[count++] = food.getFmVe();
//        obj[count++] = food.getFmCarotin();
//        obj[count++] = food.getFmVb1();
//        obj[count++] = food.getFmVb2();
//        obj[count++] = food.getFmAf();
//        obj[count++] = food.getFmEca();
//        obj[count++] = food.getFmEp();
//        obj[count++] = food.getFmEk();
//        obj[count++] = food.getFmEna();
//        obj[count++] = food.getFmEmg();
//        obj[count++] = food.getFmEfe();
//        obj[count++] = food.getFmEzn();
//        obj[count++] = food.getFmEse();
//        obj[count++] = food.getFmEcu();
//        obj[count++] = food.getFmEmn();
//    }

    /**
     * 食材类型名称获取ID
     * 
     * @param feiName
     * @return
     * 
     */
    public String getFeiIdE(String feiName) {
        // 谷类
        if (FoodConstant.FEI_GRAIN_ID.equals(feiName)) {
            return "谷类";
        }
        // 薯类淀粉
        if (FoodConstant.FEI_STARCHY_ID.equals(feiName)) {
            return "薯类淀粉";
        }
        // 蔬菜
        if (FoodConstant.FEI_VEG_ID.equals(feiName)) {
            return FoodConstant.FEI_VEG;
        }
        // 菌藻
        if (FoodConstant.FEI_BA_ID.equals(feiName)) {
            return FoodConstant.FEI_BA;
        }
        // 蛋类
        if (FoodConstant.FEI_EGG_ID.equals(feiName)) {
            return FoodConstant.FEI_EGG;
        }
        // 禽肉
        if (FoodConstant.FEI_POULTRY_ID.equals(feiName)) {
            return FoodConstant.FEI_POULTRY;
        }
        // 畜肉
        if (FoodConstant.FEI_MEAT_ID.equals(feiName)) {
            return FoodConstant.FEI_MEAT;
        }
        // 鱼虾蟹贝
        if (FoodConstant.FEI_FISH_ID.equals(feiName)) {
            return FoodConstant.FEI_FISH;
        }
        // 干豆
        if (FoodConstant.FEI_BEANS_ID.equals(feiName)) {
            return FoodConstant.FEI_BEANS;
        }
        // 水果
        if (FoodConstant.FEI_FRUITS_ID.equals(feiName)) {
            return FoodConstant.FEI_FRUITS;
        }
        // 糖蜜饯
        if (FoodConstant.FEI_SUGAR_ID.equals(feiName)) {
            return FoodConstant.FEI_SUGAR;
        }
        // 乳类
        if (FoodConstant.FEI_MILK_ID.equals(feiName)) {
            return FoodConstant.FEI_MILK;
        }
        // 坚果种子
        if (FoodConstant.FEI_NUTS_ID.equals(feiName)) {
            return FoodConstant.FEI_NUTS;
        }
        // 油脂
        if (FoodConstant.FEI_FAT_ID.equals(feiName)) {
            return FoodConstant.FEI_FAT;
        }
        // 软饮料
        if (FoodConstant.FEI_SD_ID.equals(feiName)) {
            return FoodConstant.FEI_SD;
        }
        // 酒精饮料
        if (FoodConstant.FEI_WINE_ID.equals(feiName)) {
            return FoodConstant.FEI_WINE;
        }
        // 小吃甜饼
        if (FoodConstant.FEI_CAKE_ID.equals(feiName)) {
            return FoodConstant.FEI_CAKE;
        }
        // 速食食品
        if (FoodConstant.FEI_FF_ID.equals(feiName)) {
            return FoodConstant.FEI_FF;
        }
        // 调味品
        if (FoodConstant.FEI_SEA_ID.equals(feiName)) {
            return FoodConstant.FEI_SEA;
        }
        // 婴幼儿食品
        if (FoodConstant.FEI_INFANT_ID.equals(feiName)) {
            return FoodConstant.FEI_INFANT;
        }
        // 药膳及其它
        if (FoodConstant.FEI_MO_ID.equals(feiName)) {
            return "其它";
        }
        // 盐
        if (FoodConstant.FEI_SALT_ID.equals(feiName)) {
            return FoodConstant.FEI_SALT;
        }
        if ("23".equals(feiName)) {
            return "非淀粉类蔬菜及制品";
        }
        if ("24".equals(feiName)) {
            return "特膳食品";
        }
        if ("25".equals(feiName)) {
            return "肉类及制品";
        }
        return "其它";
    }

    /**
     * 导出食材Excel
     * 
     * @param response
     * @param sheetName
     * @param title
     * @param objectList
     * @return
     * @throws Exception
     */
    public String exportReportFoodMaterial(HttpServletResponse response, String[] sheetName, String[] title,
            List<List<Object[]>> objectList)
            throws Exception {
        // 先创建工作簿对象
        HSSFWorkbook workbook2003 = new HSSFWorkbook();
        // 设置标题单元格样式

        HSSFFont font = workbook2003.createFont();
        font.setFontName("仿宋_GB2312");
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
        font.setFontHeightInPoints((short) 12);
        HSSFCellStyle titlestyle = workbook2003.createCellStyle();
        titlestyle.setFont(font);// 选择需要用到的字体格式
        titlestyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titlestyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        titlestyle.setFillForegroundColor(HSSFColor.WHITE.index);
        titlestyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        titlestyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        titlestyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        titlestyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
        // 设置单元格样式
        HSSFCellStyle style = workbook2003.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        for (int l = 0; l < 12; l++) {
            List<Object[]> list = objectList.get(l);
            // 创建工作表对象并命名
            HSSFSheet sheet = workbook2003.createSheet(sheetName[l]);
            sheet.setDefaultColumnWidth(10);
            // 创建表格标题栏
            HSSFRow titlerow = sheet.createRow(0);
            for (int i = 0; i < title.length; i++) {
                HSSFCell titlenumCell = titlerow.createCell(i);
                titlenumCell.setCellValue(title[i]);
                titlenumCell.setCellStyle(titlestyle);
            }
            // 遍历集合对象创建行和单元格
            for (int i = 0; i < list.size(); i++) {
                // 取出对象
                Object[] obj = (Object[]) list.get(i);
                // 创建行
                HSSFRow row = sheet.createRow(i + 1);
                // 开始创建单元格并赋值
                for (int j = 0; j < obj.length; j++) {
                    HSSFCell jssfCell = row.createCell(j);
                    if (obj[j] == null) {
                        obj[j] = "";
                    }
                    jssfCell.setCellValue(String.valueOf(obj[j]));
                    jssfCell.setCellStyle(style);
                }
            }
        }

        response.setContentType("application/ms-excel;");
        String excelName = new String("食材库".getBytes("gb2312"), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;Filename=" + excelName + ".xls");
        OutputStream os = null;

        try {
            os = response.getOutputStream();
            workbook2003.write(os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                    return FoodPage.FOOD_VIEW;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return FoodPage.FOOD_VIEW;
    }

    /**
     * 根据名称获取烹饪方式ID
     * 
     * @param cmiName
     * @return
     * 
     */
    public String getCmiIdE(String cmiName) {
        if (cmiName.trim().equals("1")) {
            return "生食";
        }
        if (cmiName.trim().equals("2")) {
            return "拌";
        }
        if (cmiName.trim().equals("3")) {
            return "炒";
        }
        if (cmiName.trim().equals("4")) {
            return "炖";
        }
        if (cmiName.trim().equals("5")) {
            return "煮";
        }
        if (cmiName.trim().equals("6")) {
            return "煎";
        }
        if (cmiName.trim().equals("7")) {
            return "炸";
        }
        if (cmiName.trim().equals("8")) {
            return "蒸";
        }
        if (cmiName.trim().equals("9")) {
            return "爆";
        }
        if (cmiName.trim().equals("10")) {
            return "熘";
        }
        if (cmiName.trim().equals("11")) {
            return "烹";
        }
        if (cmiName.trim().equals("12")) {
            return "溻";
        }
        if (cmiName.trim().equals("13")) {
            return "贴";
        }
        if (cmiName.trim().equals("14")) {
            return "瓤";
        }
        if (cmiName.trim().equals("15")) {
            return "烧";
        }
        if (cmiName.trim().equals("16")) {
            return "焖";
        }
        if (cmiName.trim().equals("17")) {
            return "煨";
        }
        if (cmiName.trim().equals("18")) {
            return "焗";
        }
        if (cmiName.trim().equals("19")) {
            return "扒";
        }
        if (cmiName.trim().equals("20")) {
            return "烩";
        }
        if (cmiName.trim().equals("21")) {
            return "烤";
        }
        if (cmiName.trim().equals("22")) {
            return "盐焗";
        }
        if (cmiName.trim().equals("23")) {
            return "熏";
        }
        if (cmiName.trim().equals("24")) {
            return "泥烤";
        }
        if (cmiName.trim().equals("25")) {
            return "氽";
        }
        if (cmiName.trim().equals("26")) {
            return "熬";
        }
        if (cmiName.trim().equals("27")) {
            return "拔丝";
        }
        if (cmiName.trim().equals("28")) {
            return "蜜汁";
        }
        if (cmiName.trim().equals("29")) {
            return "糖水";
        }
        if (cmiName.trim().equals("30")) {
            return "涮";
        }
        if (cmiName.trim().equals("31")) {
            return "其他";
        }
        return "其他";
    }

    /**
     * 获取对应的适宜餐次
     * 
     * @param meals
     * @return
     * 
     */
    public String getMealTypeE(String meals) {
        String[] m = meals.split(",");
        String result = "";
        for (int i = 0; i < m.length; i++) {
            if (i == m.length - 1) {
                if (!StringUtils.isEmpty(m[i]) && FoodConstant.FITS_BREAKFAST.trim().equals(m[i])) {
                    result += "9";
                }
                if (!StringUtils.isEmpty(m[i]) && FoodConstant.FITS_LUNCH.trim().equals(m[i])) {
                    result += "10";
                }
                if (!StringUtils.isEmpty(m[i]) && FoodConstant.FITS_SUPPER.trim().equals(m[i])) {
                    result += "11";
                }
                if (!StringUtils.isEmpty(m[i]) && FoodConstant.FITS_MORNING_EXTRE.trim().equals(m[i])) {
                    result += "12";
                }
                if (!StringUtils.isEmpty(m[i]) && FoodConstant.FITS_AFTERNOON_EXTRE.trim().equals(m[i])) {
                    result += "13";
                }
            } else {
                if (!StringUtils.isEmpty(m[i]) && FoodConstant.FITS_BREAKFAST.trim().equals(m[i])) {
                    result += "9" + ",";
                }
                if (!StringUtils.isEmpty(m[i]) && FoodConstant.FITS_LUNCH.trim().equals(m[i])) {
                    result += "10" + ",";
                }
                if (!StringUtils.isEmpty(m[i]) && FoodConstant.FITS_SUPPER.trim().equals(m[i])) {
                    result += "11" + ",";
                }
                if (!StringUtils.isEmpty(m[i]) && FoodConstant.FITS_MORNING_EXTRE.trim().equals(m[i])) {
                    result += "12" + ",";
                }
                if (!StringUtils.isEmpty(m[i]) && FoodConstant.FITS_AFTERNOON_EXTRE.trim().equals(m[i])) {
                    result += "13" + ",";
                }
            }
        }
        return result;
    }

    /**
     * 获取有益成分
     * 
     * @param bf
     * @return
     * 
     */
    public String getBenefitE(String bf) {
        String[] b = bf.split(",");
        String result = "";
        for (int i = 0; i < b.length; i++) {
            if (i == b.length - 1) {
                if (!StringUtils.isEmpty(b[i]) && FoodConstant.RECOMMEND_OMG3.trim().equals(b[i])) {
                    result += "14";
                }
                if (!StringUtils.isEmpty(b[i]) && FoodConstant.RECOMMEND_I.trim().equals(b[i])) {
                    result += "15";
                }
                if (!StringUtils.isEmpty(b[i]) && FoodConstant.RECOMMEND_AC.trim().equals(b[i])) {
                    result += "16";
                }
            } else {
                if (!StringUtils.isEmpty(b[i]) && FoodConstant.RECOMMEND_OMG3.trim().equals(b[i])) {
                    result += "14" + ",";
                }
                if (!StringUtils.isEmpty(b[i]) && FoodConstant.RECOMMEND_I.trim().equals(b[i])) {
                    result += "15" + ",";
                }
                if (!StringUtils.isEmpty(b[i]) && FoodConstant.RECOMMEND_AC.trim().equals(b[i])) {
                    result += "16" + ",";
                }
            }
        }
        return result;
    }

    /**
     * 获取有害成分
     * 
     * @param harm
     * @return
     * 
     */
    public String getHarmE(String harm) {
        String[] h = harm.split(",");
        String result = "";
        for (int i = 0; i < h.length; i++) {
            if (i == h.length - 1) {
                if (!StringUtils.isEmpty(h[i]) && FoodConstant.AVOID_OX.trim().equals(h[i])) {
                    result += "17";
                }
                if (!StringUtils.isEmpty(h[i]) && FoodConstant.AVOID_GLU.trim().equals(h[i])) {
                    result += "18";
                }
                if (!StringUtils.isEmpty(h[i]) && FoodConstant.AVOID_PU.trim().equals(h[i])) {
                    result += "19";
                }
                if (!StringUtils.isEmpty(h[i]) && FoodConstant.AVOID_STIM.trim().equals(h[i])) {
                    result += "20";
                }
                if (!StringUtils.isEmpty(h[i]) && FoodConstant.AVOID_CRU.trim().equals(h[i])) {
                    result += "21";
                }
            } else {
                if (!StringUtils.isEmpty(h[i]) && FoodConstant.AVOID_OX.trim().equals(h[i])) {
                    result += "17" + ",";
                }
                if (!StringUtils.isEmpty(h[i]) && FoodConstant.AVOID_GLU.trim().equals(h[i])) {
                    result += "18" + ",";
                }
                if (!StringUtils.isEmpty(h[i]) && FoodConstant.AVOID_PU.trim().equals(h[i])) {
                    result += "19" + ",";
                }
                if (!StringUtils.isEmpty(h[i]) && FoodConstant.AVOID_STIM.trim().equals(h[i])) {
                    result += "20" + ",";
                }
                if (!StringUtils.isEmpty(h[i]) && FoodConstant.AVOID_CRU.trim().equals(h[i])) {
                    result += "21" + ",";
                }
            }
        }
        return result;
    }

    /**
     * 填充内容并生成Excel
     * 
     * @param response
     * @param excelName
     *            生成的Excel表名称
     * @param title
     *            Excel表格标题
     * @param list
     *            表格内容
     * @return
     * @throws Exception
     */
    public String exportReport(HttpServletResponse response, String excelName, String[] title, List<Object[]> list)
            throws Exception {
        // 先创建工作簿对象
        HSSFWorkbook workbook2003 = new HSSFWorkbook();
        // 创建工作表对象并命名
        HSSFSheet sheet = workbook2003.createSheet(excelName);
        // 设置标题单元格样式
        sheet.setDefaultColumnWidth(10);
        HSSFFont font = workbook2003.createFont();
        font.setFontName("仿宋_GB2312");
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
        font.setFontHeightInPoints((short) 12);
        HSSFCellStyle titlestyle = workbook2003.createCellStyle();
        titlestyle.setFont(font);// 选择需要用到的字体格式
        titlestyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titlestyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        titlestyle.setFillForegroundColor(HSSFColor.WHITE.index);
        titlestyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        titlestyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        titlestyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        titlestyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
        // 设置单元格样式
        HSSFCellStyle style = workbook2003.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 创建表格标题栏
        HSSFRow titlerow = sheet.createRow(0);
        for (int i = 0; i < title.length; i++) {
            HSSFCell titlenumCell = titlerow.createCell(i);
            titlenumCell.setCellValue(title[i]);
            titlenumCell.setCellStyle(titlestyle);
        }

        // 遍历集合对象创建行和单元格
        for (int i = 0; i < list.size(); i++) {
            // 取出对象
            Object[] obj = (Object[]) list.get(i);
            // 创建行
            HSSFRow row = sheet.createRow(i + 1);
            // 开始创建单元格并赋值
            for (int j = 0; j < obj.length; j++) {
                HSSFCell jssfCell = row.createCell(j);
                if (obj[j] == null) {
                    obj[j] = "";
                }
                jssfCell.setCellValue(String.valueOf(obj[j]));
                jssfCell.setCellStyle(style);
            }
        }
        response.setContentType("application/ms-excel;");
        excelName = new String(excelName.getBytes("gb2312"), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;Filename=" + excelName + ".xls");
        OutputStream os = null;

        try {
            os = response.getOutputStream();
            workbook2003.write(os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return FoodPage.FOOD_VIEW;
    }

    /******************************************************* 类别管理 *******************************************************************************/
    /**
     * 
     * 验证食材类别是否有食材配置
     * 
     * @author dhs
     * @param parentTreeId
     * @return
     */
    @RequestMapping(value = FoodMapping.FOOD_CATALOGINFO_AMOUNT_VALID)
    public @ResponseBody
    WebResult<Boolean> checkFoodTreeAmount(String treeId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        if (foodService.checkFoodTreeAmount(treeId) > 0) {
            webResult.setSuc(false);
        } else {
            webResult.setSuc(true);
        }
        return webResult;
    }

    /**
     * 添加功能菜单
     * 
     * @author dhs
     * @param form
     * @return
     */
    @RequestMapping(value = FoodMapping.FOOD_CATALOGINFO_ADD)
    public @ResponseBody
    WebResult<ZTree<TreePojo>> addFoodMaterial(FoodTree form) {
        WebResult<ZTree<TreePojo>> webResult = new WebResult<ZTree<TreePojo>>();
        String treeId = form.getParentTreeId();
        FoodCondition condition = new FoodCondition();
        condition.setFlag(Flag.normal.getValue());
        condition.setFoodCuisine(treeId);
        List<FoodPojo> foodPojo = foodService.queryFood(condition);
        // 类别下无食材才可以添加子节点
        if (CollectionUtils.isEmpty(foodPojo)) {
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
    @RequestMapping(value = FoodMapping.FOOD_CATALOGINFO_NAME_VALID)
    public @ResponseBody
    boolean checkCatalogInfoNameValid(String treeName, String treeNameOld, String opernerCatalog) {
        if ("update".equals(opernerCatalog) && treeName.equals(treeNameOld)) {
            return true;
        } else {
            int count = treeService.checkTreeNameValid(treeName, treeKind);
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
    @RequestMapping(value = FoodMapping.FOOD_CATALOGINFO_UPDATE)
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
    @RequestMapping(value = FoodMapping.FOOD_CATALOGINFO_DELETE)
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
    @RequestMapping(value = FoodMapping.FOOD_CATALOGINFO_UPDATE_ORDER)
    public @ResponseBody
    WebResult<Boolean> updateCatalogInfoOrder(String menuIds) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        treeService.editTreeOrder(Arrays.asList(menuIds.split(",")));
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 保留文件名及后缀
     */
    public String getFileNameWithSuffix(String pathandname) {
        int start = pathandname.lastIndexOf("/");
        if (start != -1) {
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

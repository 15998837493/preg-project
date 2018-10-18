/*
 * ImportFoodService.java    1.0  2018年9月6日
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.master.service.basic.importdata;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.itextpdf.text.log.SysoCounter;
import com.mnt.health.utils.HanyuToPinyin;
import com.mnt.preg.master.dao.basic.FoodDAO;
import com.mnt.preg.master.dao.basic.FoodMaterialDAO;
import com.mnt.preg.master.dao.basic.TreeDAO;
import com.mnt.preg.master.entity.basic.FoodInfo;
import com.mnt.preg.master.entity.basic.FoodMaterialList;
import com.mnt.preg.system.dao.CodeDao;
import com.mnt.preg.system.entity.CodeInfo;
import com.mnt.preg.system.pojo.CodePojo;

/**
 * 菜谱Excel导入程序
 *
 * @author zj
 * @version 1.0
 *
 *          变更履历：
 *          v1.0 2018年9月6日 zj 初版
 */
@Service
public class ImportFoodService {

    @Autowired
    private TreeDAO treeDAO;

    @Autowired
    private FoodDAO foodDAO;
    
    @Autowired
    private FoodMaterialDAO foodMaterialDAO;
    
    @Autowired
    private ImportDataService importDataService;
    
    @Autowired
    private CodeDao codeDao;

    @Transactional
    public void opratorDate() throws IOException {
        
        
        //TODO 删除菜谱表、菜谱食材关联表数据
        treeDAO.delFood();
        treeDAO.delFoodList();
        
        String xlsPath = "D:/健康管理项目文档/健康管理项目文档/开发管理/preg1.7.1/菜谱库-2.xlsx";
        
        FileInputStream fileIn = new FileInputStream(xlsPath);
        
        // 根据指定的文件输入流导入Excel从而产生Workbook对象
        Workbook wb = new XSSFWorkbook(fileIn);
        
        XSSFSheet sheet = ((XSSFWorkbook) wb).getSheetAt(0);
        
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            
            XSSFRow row = (XSSFRow) sheet.getRow(i);
            
            FoodInfo food = new FoodInfo();
                    
            // 菜谱类型
            String sortName = (StringUtils.isEmpty(row.getCell(2).toString())) ? "" : row.getCell(2).toString();           
            // 通过类别名称查类别id
            String foodType = treeDAO.queryTreeId(sortName.trim(), "000", "food");
            if (StringUtils.isEmpty(foodType)) {
                System.out.println("sortName:" + sortName);
                continue;
            }
            food.setFoodType(foodType);
            food.setFoodTreeType(foodType);
            
            // 菜系
            String caixi = (StringUtils.isEmpty(row.getCell(3).toString())) ? "" : row.getCell(3).toString();
            if (!StringUtils.isEmpty(caixi) && !StringUtils.isEmpty(caixi.trim())) {
                    food.setFoodStyleCook(caixi);
            }
            
            // 菜谱名称
            String foodName = (StringUtils.isEmpty(row.getCell(4).toString())) ? "" : row.getCell(4).toString();
            if (!StringUtils.isEmpty(foodName) && !StringUtils.isEmpty(foodName.trim())) {
                food.setFoodName(foodName);
            }
            
            //食物别名
            String alias = (StringUtils.isEmpty(row.getCell(5).toString())) ? "" : row.getCell(5).toString();
            if (!StringUtils.isEmpty(alias) && !StringUtils.isEmpty(alias.trim())) {
                    food.setFoodAlias(alias);
            }
            
            // 标签参考类型(肉类、水果类、菌类)
            String biaoqian = (StringUtils.isEmpty(row.getCell(7).toString())) ? "" : row.getCell(7).toString();
            if (!StringUtils.isEmpty(biaoqian) && !StringUtils.isEmpty(biaoqian.trim())) {
                    food.setFoodSort(biaoqian);
            }
            
            // 荤素菜
            String hunshu = (StringUtils.isEmpty(row.getCell(8).toString())) ? "" : row.getCell(8).toString();
            if (!StringUtils.isEmpty(hunshu) && !StringUtils.isEmpty(hunshu.trim())) {
                    food.setFoodMeatVegetable(hunshu);
            }
            
            // 过敏源
            String guominyuan = (StringUtils.isEmpty(row.getCell(9).toString())) ? "" : row.getCell(9).toString();
            if (!StringUtils.isEmpty(guominyuan) && !StringUtils.isEmpty(guominyuan.trim())) {
                    food.setFoodAllergen(guominyuan);
            }
            
            // 食物图片
            try {
                String pic = (StringUtils.isEmpty(row.getCell(74).toString())) ? "" : row.getCell(74).toString();
                if (!StringUtils.isEmpty(pic) && !StringUtils.isEmpty(pic.trim())) {
                        food.setFoodPic(pic);
                }
            } catch(Exception e) {
                System.out.println("i:" + i);
            }
            
            // 烹饪方式
            try {
                String pengRen = (StringUtils.isEmpty(row.getCell(16).toString())) ? "" : row.getCell(16).toString();
                if (!StringUtils.isEmpty(pengRen) && !StringUtils.isEmpty(pengRen.trim())) {
                        CodeInfo codeInfo = new CodeInfo();
                        codeInfo.setCodeKind("COKE_MODE");
                        codeInfo.setCodeName(pengRen);
                        List<CodePojo> list = codeDao.queryCode(codeInfo);
                        if (!CollectionUtils.isEmpty(list)) {
                        	if (list.get(0) != null) {
                        		food.setFoodCuisine(list.get(0).getCodeValue());
                        	}
                        }
                        
                }
            } catch(Exception e) {
                System.out.println("i:" + i);
            }
            
            // 功效
            try{
                String gongxiao = (StringUtils.isEmpty(row.getCell(75).toString())) ? "" : row.getCell(75).toString();
                if (!StringUtils.isEmpty(gongxiao) && !StringUtils.isEmpty(gongxiao.trim())) {
                        food.setFoodEfficacy(gongxiao);
                }
            } catch (Exception e) {
                System.out.println("i：" + i);
            }
            
            // 做法
            try{
                String zuofa = (StringUtils.isEmpty(row.getCell(76).toString())) ? "" : row.getCell(76).toString();
                if (!StringUtils.isEmpty(zuofa) && !StringUtils.isEmpty(zuofa.trim())) {
                        food.setFoodMake(zuofa);
                }
            } catch (Exception e) {
                System.out.println("i：" + i);
            }
            
            food.setFoodSSpell(HanyuToPinyin.getInstance().getAbbrStringPinYin(food.getFoodName()));
            food.setFoodASpell(HanyuToPinyin.getInstance().getFullStringPinYin(food.getFoodName()));
            food.setFoodCounts(1);
            food.setCreateInsId("sys");
            food.setCreateTime(new Date());
            food.setUpdateTime(new Date());
            food.setFlag(1);
            food.setUpdateUserId("sys");
            food.setCreateUserId("sys");
            String foodId = (String) foodDAO.save(food);
            
            // 保存菜谱食材对应关系表开始
            int StartX = 21;
            // 主料一
            StartX++;
            String zl1 = (StringUtils.isEmpty(row.getCell(StartX).toString())) ? "" : row.getCell(StartX).toString();
            StartX++;
            String zlCount1 = (StringUtils.isEmpty(row.getCell(StartX).toString())) ? "" : row.getCell(StartX).toString();
            if (!StringUtils.isEmpty(zlCount1) && !StringUtils.isEmpty(zlCount1.trim()) && !StringUtils.isEmpty(zl1) && !StringUtils.isEmpty(zl1.trim())) {
                try {
                    // 通过食材名称找到食材的id
                    FoodMaterialList fml1 = new FoodMaterialList();
                    String fmid = foodMaterialDAO.queryFmId(zl1);
                    if (!StringUtils.isEmpty(fmid)) {
                    	if (zlCount1.indexOf(".") != -1) {
                    		zlCount1 = zlCount1.substring(0, zlCount1.indexOf("."));
                    	}
                        int zlamount = Integer.parseInt(zlCount1);
                        fml1.setFmId(fmid);
                        fml1.setFmlAmount(zlamount);
                        fml1.setFoodId(foodId);
                        fml1.setFmlPercent(new BigDecimal(100));
                        fml1.setFmlMaterial("主料");
                        fml1.setFmlIsIntakeType("yes");                    
                        fml1.setCreateInsId("sys");
                        fml1.setCreateTime(new Date());
                        fml1.setUpdateTime(new Date());
                        fml1.setFlag(1);
                        fml1.setUpdateUserId("sys");
                        fml1.setCreateUserId("sys");
                        
                        foodDAO.save(fml1);
                    } else {
                    	System.out.println("找不到食材：" + zl1);
                    }

                } catch (Exception e) {
                	System.out.println("录入异常：菜谱名称-" + foodName);
                    importDataService.consoleLog(0, i, zl1, "zlCount1");
                }
            }
            
            // 主料二
            StartX = 26;
            String zl2 = null;
            try{
            	System.out.println(row.getCell(StartX).toString());
            	zl2 = (row.getCell(StartX) !=null && StringUtils.isEmpty(row.getCell(StartX).toString())) ? "" : row.getCell(StartX).toString();
            } catch (Exception e) {
//            	System.out.println("ss:" + row.getCell(StartX).toString());
            	System.out.println("i:" + i);
            	e.printStackTrace();
            }
            StartX++;
            String zlCount2 = (StringUtils.isEmpty(row.getCell(StartX).toString())) ? "" : row.getCell(StartX).toString();
            if (!StringUtils.isEmpty(zlCount2) && !StringUtils.isEmpty(zlCount2.trim()) && !StringUtils.isEmpty(zl2) && !StringUtils.isEmpty(zl2.trim())) {
                try {
                    // 通过食材名称找到食材的id
                    FoodMaterialList fml1 = new FoodMaterialList();
                    String fmid = foodMaterialDAO.queryFmId(zl2);
                    if (!StringUtils.isEmpty(fmid)) {
                    	if (zlCount2.indexOf(".") != -1) {
                    		zlCount2 = zlCount2.substring(0, zlCount2.indexOf("."));
                    	}
                        int zlamount = Integer.parseInt(zlCount2);
                        fml1.setFmId(fmid);
                        fml1.setFmlAmount(zlamount);
                        fml1.setFoodId(foodId);
                        fml1.setFmlPercent(new BigDecimal(100));
                        fml1.setFmlMaterial("主料");
                        fml1.setFmlIsIntakeType("yes");
                        
                        fml1.setCreateInsId("sys");
                        fml1.setCreateTime(new Date());
                        fml1.setUpdateTime(new Date());
                        fml1.setFlag(1);
                        fml1.setUpdateUserId("sys");
                        fml1.setCreateUserId("sys");
                        foodDAO.save(fml1);
                    } else {
                    	System.out.println("录入异常：" + foodName);
                    	System.out.println("找不到食材：" + zl2);
                    }

                    
                   
                } catch (Exception e) {
                	System.out.println("录入异常：菜谱名称-" + foodName);
                    importDataService.consoleLog(0, i, zl2, "zlCount2");
                }
            }
            
            // 主料三
            StartX = 30; 
            String zl3 = (StringUtils.isEmpty(row.getCell(StartX).toString())) ? "" : row.getCell(12).toString();
            StartX++;
            String zlCount3 = (StringUtils.isEmpty(row.getCell(13).toString())) ? "" : row.getCell(13).toString();
            if (!StringUtils.isEmpty(zlCount3) && !StringUtils.isEmpty(zlCount3.trim()) && !StringUtils.isEmpty(zl3) && !StringUtils.isEmpty(zl3.trim())) {
                try {
                    // 通过食材名称找到食材的id
                    FoodMaterialList fml1 = new FoodMaterialList();
                    String fmid = foodMaterialDAO.queryFmId(zl3);
                    if (!StringUtils.isEmpty(fmid)) { 
                    	if (zlCount3.indexOf(".") != -1) {
                    		zlCount3 = zlCount3.substring(0, zlCount3.indexOf("."));
                    	}
                        int zlamount = Integer.parseInt(zlCount3);
                        fml1.setFmId(fmid);
                        fml1.setFmlAmount(zlamount);
                        fml1.setFoodId(foodId);
                        fml1.setFmlPercent(new BigDecimal(100));
                        fml1.setFmlMaterial("主料");
                        fml1.setFmlIsIntakeType("yes");
                        
                        fml1.setCreateInsId("sys");
                        fml1.setCreateTime(new Date());
                        fml1.setUpdateTime(new Date());
                        fml1.setFlag(1);
                        fml1.setUpdateUserId("sys");
                        fml1.setCreateUserId("sys");
                        
                        foodDAO.save(fml1);
                    } else {
                    	
                    	System.out.println("找不到食材：" + zl3);
                    }

                } catch (Exception e) {
                	System.out.println("录入异常：菜谱名称-" + foodName);
                    importDataService.consoleLog(0, i, zl3, "zlCount3");
                }
            }
            
            // 主料四
            StartX=34;
            String zl4 = (StringUtils.isEmpty(row.getCell(StartX).toString())) ? "" : row.getCell(StartX).toString();
            StartX++;
            String zlCount4 = (StringUtils.isEmpty(row.getCell(StartX).toString())) ? "" : row.getCell(StartX).toString();
            if (!StringUtils.isEmpty(zlCount4) && !StringUtils.isEmpty(zlCount4.trim()) && !StringUtils.isEmpty(zl4) && !StringUtils.isEmpty(zl4.trim())) {
                try {
                    // 通过食材名称找到食材的id
                    FoodMaterialList fml1 = new FoodMaterialList();
                    String fmid = foodMaterialDAO.queryFmId(zl4);
                	if (zlCount4.indexOf(".") != -1) {
                		zlCount4 = zlCount4.substring(0, zlCount4.indexOf("."));
                	}
                    int zlamount = Integer.parseInt(zlCount4);
                    fml1.setFmId(fmid);
                    if (!StringUtils.isEmpty(fmid)) { 
                        fml1.setFmlAmount(zlamount);
                        fml1.setFoodId(foodId);
                        fml1.setFmlPercent(new BigDecimal(100));
                        fml1.setFmlMaterial("主料");
                        fml1.setFmlIsIntakeType("yes");
                        
                        fml1.setCreateInsId("sys");
                        fml1.setCreateTime(new Date());
                        fml1.setUpdateTime(new Date());
                        fml1.setFlag(1);
                        fml1.setUpdateUserId("sys");
                        fml1.setCreateUserId("sys");
                        
                        foodDAO.save(fml1);
                    } else {
                    	System.out.println("找不到食材：" + zl4);
                    }

                } catch (Exception e) {
                	System.out.println("录入异常：菜谱名称-" + foodName);
                    importDataService.consoleLog(0, i, zl4, "zlCount4");
                }
            }
        }
        
        
        
        
    }

}

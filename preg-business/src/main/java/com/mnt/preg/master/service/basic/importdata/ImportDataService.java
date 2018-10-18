/*
 * ImportDataService.java    1.0  2018年8月30日
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.master.service.basic.importdata;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mnt.health.utils.HanyuToPinyin;
import com.mnt.preg.master.dao.basic.FoodDAO;
import com.mnt.preg.master.dao.basic.TreeDAO;
import com.mnt.preg.master.entity.basic.FoodMaterial;
import com.mnt.preg.master.entity.basic.NutrientAmount;


/**
 * 导入数据
 *
 * @author zj
 * @version 1.0
 *
 *          变更履历：
 *          v1.0 2018年8月30日 zj 初版
 */
@Service
public class ImportDataService {
    
    private static final String corresType = "foodMaterial";

    @Autowired
    private TreeDAO treeDAO;
    
    @Autowired
    private FoodDAO foodDAO;

    @Transactional
    public void opratorDate() throws IOException {
        
        treeDAO.delFm();
        
        treeDAO.delNur();

        String xlsPath = "D:/健康管理项目文档/健康管理项目文档/开发管理/preg1.7.1/新版食材库（鱼肉蛋拆分）2018.9.18-2.xlsx";

        String tempSort = null;

        FileInputStream fileIn = new FileInputStream(xlsPath);
        // 根据指定的文件输入流导入Excel从而产生Workbook对象
        Workbook wb = new XSSFWorkbook(fileIn);

        for (int j = 0; j < 18; j++) {
            
            XSSFSheet sheet = ((XSSFWorkbook) wb).getSheetAt(j);
            // 遍历行
            for (int i = 3; i <= sheet.getLastRowNum(); i++) {
                XSSFRow row = (XSSFRow) sheet.getRow(i);

                //将行内容转换成对象
                FoodMaterial foodMaterial = new FoodMaterial();
                List<NutrientAmount> listExt = new ArrayList<NutrientAmount>();

                // 类别名称
                String sortName = null;
                try {
                    sortName = (StringUtils.isEmpty(row.getCell(0).toString())) ? tempSort : row.getCell(0).toString();
                } catch (Exception e) {
                    System.out.println(j);
                    continue;
                }
                
                tempSort = sortName;
                // 通过类别名称查类别id
                String fmType = treeDAO.queryTreeId(sortName.trim(), getBigSort(j), "food_material");
                if (StringUtils.isEmpty(fmType)) {
                    System.out.println("sortName:" + sortName);
                    System.out.println("bigsortid:" + getBigSort(j));
                    continue;
                }
                foodMaterial.setFmType(fmType);
                            
//                String fmId = (StringUtils.isEmpty(row.getCell(1).toString())) ? tempSort : row.getCell(1).toString();
//                foodMaterial.setFmId(fmId);

                String fmName = (StringUtils.isEmpty(row.getCell(2).toString())) ? "" : row.getCell(2).toString();
                foodMaterial.setFmName(fmName);
                
                //别名
                if (fmName.indexOf("[") != -1 && fmName.indexOf("]") != -1) {
                    int startX = fmName.trim().indexOf("[");
                    String strName = fmName.trim().substring(0, startX);
//                    System.out.println(strName);
                    foodMaterial.setFmName(strName);
                    String strAlias = fmName.substring(startX+1, fmName.trim().length()-1);
//                    System.out.println("别名：" + strAlias);
                    foodMaterial.setFmAlias(strAlias);
                }

                try {
                    String fmEsculent = (StringUtils.isEmpty(row.getCell(3).toString())) ? "100"
                            : row.getCell(3).toString();
                    if (fmEsculent.indexOf(".") != -1) {
                    	fmEsculent = fmEsculent.substring(0, fmEsculent.indexOf("."));
                    }
                    foodMaterial.setFmEsculent(Integer.parseInt(fmEsculent));
                } catch (Exception e) {
                    consoleLog(j, i, row.getCell(3).toString(), "fmEsculent");
                    foodMaterial.setFmEsculent(100);
                }   
                String fmWater_str = (StringUtils.isEmpty(row.getCell(4).toString())) ? "" : row.getCell(4).toString();
                if (!StringUtils.isEmpty(fmWater_str) && !StringUtils.isEmpty(fmWater_str.trim())) {
                    try {
                        BigDecimal fmWater = new BigDecimal(fmWater_str);
                        foodMaterial.setFmWater(fmWater);
                    } catch (Exception e) {
                        consoleLog(j, i, fmWater_str, "fmWater");
                    }

                }
                
                String ash_str = (StringUtils.isEmpty(row.getCell(15).toString())) ? "" : row.getCell(15).toString();
                if (!StringUtils.isEmpty(ash_str) && !StringUtils.isEmpty(ash_str.trim())) {
                    try {
                        BigDecimal ash = new BigDecimal(ash_str);
                        foodMaterial.setFmAsh(ash);
                    } catch (Exception e) {
                        consoleLog(j, i, ash_str, "ash");
                    }
                }
                
                foodMaterial.setFmSSpell(HanyuToPinyin.getInstance().getAbbrStringPinYin(foodMaterial.getFmName()));
                foodMaterial.setCreateInsId("sys");
                foodMaterial.setCreateTime(new Date());
                foodMaterial.setUpdateTime(new Date());
                foodMaterial.setFlag(1);
                foodMaterial.setUpdateUserId("sys");
                foodMaterial.setCreateUserId("sys");
                
                //保存食材
                String fmId = (String) foodDAO.save(foodMaterial);
                
                // 酒精度alcohol ml
                String alc_vol = (StringUtils.isEmpty(row.getCell(5).toString())) ? "" : row.getCell(5).toString();
                if (!StringUtils.isEmpty(alc_vol) && !StringUtils.isEmpty(alc_vol.trim())) {
                    try {
                        BigDecimal nutrientDosage = new BigDecimal(alc_vol);            
                        listExt.add(getNutrient("E00038", nutrientDosage, fmId));
                    } catch (Exception e) {
                        consoleLog(j, i, alc_vol, "alc_vol");
                    }
                }
                
                // 酒精度alcohol g
                String alc_weight = (StringUtils.isEmpty(row.getCell(6).toString())) ? "" : row.getCell(6).toString();
                if (!StringUtils.isEmpty(alc_weight) && !StringUtils.isEmpty(alc_weight.trim())) {
                    try {
                        BigDecimal nutrientDosage = new BigDecimal(alc_weight);            
                        listExt.add(getNutrient("E00039", nutrientDosage, fmId));
                    } catch (Exception e) {
                        consoleLog(j, i, alc_weight, "alc_weight");
                    }
                }
                                
                //能量
                String dosage_str = (StringUtils.isEmpty(row.getCell(7).toString())) ? "" : row.getCell(7).toString();
                if (!StringUtils.isEmpty(dosage_str) && !StringUtils.isEmpty(dosage_str.trim())) {
                    try {
                        BigDecimal nutrientDosage = new BigDecimal(dosage_str);            
                        listExt.add(getNutrient("E00001", nutrientDosage, fmId));
                    } catch (Exception e) {
                        consoleLog(j, i, dosage_str, "energy");
                    }
                }
                
                //蛋白质
                String pro_str = (StringUtils.isEmpty(row.getCell(8).toString())) ? "" : row.getCell(8).toString();
                if (!StringUtils.isEmpty(pro_str) && !StringUtils.isEmpty(pro_str.trim())) {
                    try{
                        BigDecimal proDosage = new BigDecimal(pro_str);            
                        listExt.add(getNutrient("E00002", proDosage, fmId));
                    } catch (Exception e) {
                        consoleLog(j, i, pro_str, "pro_str");
                    }

                }
                //脂肪
                String fat_str = (StringUtils.isEmpty(row.getCell(9).toString())) ? "" : row.getCell(9).toString();
                if (!StringUtils.isEmpty(fat_str) && !StringUtils.isEmpty(fat_str.trim())) {
                    try {
                        BigDecimal fatDosage = new BigDecimal(fat_str);            
                        listExt.add(getNutrient("E00003", fatDosage, fmId));
                    } catch (Exception e) {
                        consoleLog(j, i, fat_str, "fat_str");
                    }
                }
                //碳水化合物
                String cho_str = (StringUtils.isEmpty(row.getCell(10).toString())) ? "" : row.getCell(10).toString();
                if (!StringUtils.isEmpty(cho_str) && !StringUtils.isEmpty(cho_str.trim())) {
                    try {
                        BigDecimal choDosage = new BigDecimal(cho_str);            
                        listExt.add(getNutrient("E00004", choDosage, fmId));
                    } catch (Exception e) {
                        consoleLog(j, i, cho_str, "cho_str");
                    }
                }
                
                //TODO 膳食纤维总  Total
                String zong_sanshixianwei = (StringUtils.isEmpty(row.getCell(11).toString())) ? "" : row.getCell(11).toString();
                if (!StringUtils.isEmpty(zong_sanshixianwei) && !StringUtils.isEmpty(zong_sanshixianwei.trim())) {
                    try {
                        BigDecimal choDosage = new BigDecimal(zong_sanshixianwei);            
                        listExt.add(getNutrient("E00040", choDosage, fmId));
                    } catch (Exception e) {
                        consoleLog(j, i, zong_sanshixianwei, "zong_sanshixianwei");
                    }
                }
                
                //TODO 膳食纤维可溶性  
                String krx_sanshixianwei = (StringUtils.isEmpty(row.getCell(12).toString())) ? "" : row.getCell(12).toString();
                if (!StringUtils.isEmpty(krx_sanshixianwei) && !StringUtils.isEmpty(krx_sanshixianwei.trim())) {
                    try {
                        BigDecimal choDosage = new BigDecimal(krx_sanshixianwei);            
                        listExt.add(getNutrient("E00041", choDosage, fmId));
                    } catch (Exception e) {
                        consoleLog(j, i, krx_sanshixianwei, "krx_sanshixianwei");
                    }
                }
                
                //TODO 膳食纤维不溶性  
                String bukerong_sanshixianwei = (StringUtils.isEmpty(row.getCell(13).toString())) ? "" : row.getCell(13).toString();
                if (!StringUtils.isEmpty(bukerong_sanshixianwei) && !StringUtils.isEmpty(bukerong_sanshixianwei.trim())) {
                    try {
                        BigDecimal choDosage = new BigDecimal(bukerong_sanshixianwei);            
                        listExt.add(getNutrient("E00042", choDosage, fmId));
                    } catch (Exception e) {
                        consoleLog(j, i, bukerong_sanshixianwei, "krx_sanshixianwei");
                    }
                }
                //TODO 胆固醇
                String danguchun = (StringUtils.isEmpty(row.getCell(14).toString())) ? "" : row.getCell(14).toString();
                if (!StringUtils.isEmpty(danguchun) && !StringUtils.isEmpty(danguchun.trim())) {
                    try {
                        BigDecimal choDosage = new BigDecimal(danguchun);            
                        listExt.add(getNutrient("E00043", choDosage, fmId));
                    } catch (Exception e) {
                        consoleLog(j, i, danguchun, "danguchun");
                    }
                }
                
                
                //维生素A
                String v_a = (StringUtils.isEmpty(row.getCell(16).toString())) ? "" : row.getCell(16).toString();
                if (!StringUtils.isEmpty(v_a) && !StringUtils.isEmpty(v_a.trim())) {
                    try{
                        BigDecimal vaDosage = new BigDecimal(v_a);            
                        listExt.add(getNutrient("E00005", vaDosage, fmId));
                    } catch(Exception e){
                        consoleLog(j, i, v_a, "v_a");
                    }
                }
                
                //胡萝卜素
                String huluobo = (StringUtils.isEmpty(row.getCell(17).toString())) ? "" : row.getCell(17).toString();
                if (!StringUtils.isEmpty(huluobo) && !StringUtils.isEmpty(huluobo.trim())) {
                    try{
                        BigDecimal vaDosage = new BigDecimal(huluobo);            
                        listExt.add(getNutrient("E00044", vaDosage, fmId));
                    } catch(Exception e){
                        consoleLog(j, i, huluobo, "huluobo");
                    }
                }
                
                //视黄醇
                String shihuangchun = (StringUtils.isEmpty(row.getCell(18).toString())) ? "" : row.getCell(18).toString();
                if (!StringUtils.isEmpty(shihuangchun) && !StringUtils.isEmpty(shihuangchun.trim())) {
                    try{
                        BigDecimal vaDosage = new BigDecimal(shihuangchun);            
                        listExt.add(getNutrient("E00045", vaDosage, fmId));
                    } catch(Exception e){
                        consoleLog(j, i, shihuangchun, "shihuangchun");
                    }
                }
                
                //硫胺素
                String liuanshu = (StringUtils.isEmpty(row.getCell(19).toString())) ? "" : row.getCell(19).toString();
                if (!StringUtils.isEmpty(liuanshu) && !StringUtils.isEmpty(liuanshu.trim())) {
                    try{
                        BigDecimal vaDosage = new BigDecimal(liuanshu);            
                        listExt.add(getNutrient("E00046", vaDosage, fmId));
                    } catch(Exception e){
                        consoleLog(j, i, liuanshu, "liuanshu");
                    }
                }
                
                //核黄素
                String hehuangshu = (StringUtils.isEmpty(row.getCell(20).toString())) ? "" : row.getCell(20).toString();
                if (!StringUtils.isEmpty(liuanshu) && !StringUtils.isEmpty(liuanshu.trim())) {
                    try{
                        BigDecimal vaDosage = new BigDecimal(hehuangshu);            
                        listExt.add(getNutrient("E00047", vaDosage, fmId));
                    } catch(Exception e){
                        consoleLog(j, i, hehuangshu, "hehuangshu");
                    }
                }
               
                //维生素B6
                String v_6 = (StringUtils.isEmpty(row.getCell(21).toString())) ? "" : row.getCell(21).toString();
                if (!StringUtils.isEmpty(v_6) && !StringUtils.isEmpty(v_6.trim())) {
                    try{
                        BigDecimal v6Dosage = new BigDecimal(v_6);            
                        listExt.add(getNutrient("E00011", v6Dosage, fmId));
                    } catch (Exception e) {
                        consoleLog(j, i, v_6, "v_6");
                    }
                }
                //维生素B12
                String v_12 = (StringUtils.isEmpty(row.getCell(22).toString())) ? "" : row.getCell(22).toString();
                if (!StringUtils.isEmpty(v_12) && !StringUtils.isEmpty(v_12.trim())) {
                    try {
                        BigDecimal v12Dosage = new BigDecimal(v_12);            
                        listExt.add(getNutrient("E00012", v12Dosage, fmId));
                    } catch (Exception e) {
                        consoleLog(j, i, v_12, "v_12");
                    }

                }
                //叶酸
                String folic = (StringUtils.isEmpty(row.getCell(23).toString())) ? "" : row.getCell(23).toString();
                if (!StringUtils.isEmpty(folic) && !StringUtils.isEmpty(folic.trim())) {
                    try {
                        BigDecimal folicDosage = new BigDecimal(folic);            
                        listExt.add(getNutrient("E00014", folicDosage, fmId));
                    } catch (Exception e) {
                        consoleLog(j, i, folic, "folic");
                    }

                }
                //烟酸
                String acid = (StringUtils.isEmpty(row.getCell(24).toString())) ? "" : row.getCell(24).toString();
                if (!StringUtils.isEmpty(acid) && !StringUtils.isEmpty(acid.trim())) {
                    try {
                        BigDecimal acidDosage = new BigDecimal(acid);            
                        listExt.add(getNutrient("E00015", acidDosage, fmId));
                    } catch (Exception e) {
                        consoleLog(j, i, acid, "acid");
                    }

                }
                //VC
                String vc = (StringUtils.isEmpty(row.getCell(25).toString())) ? "" : row.getCell(25).toString();
                if (!StringUtils.isEmpty(vc) && !StringUtils.isEmpty(vc.trim())) {
                    try {
                        BigDecimal vcDosage = new BigDecimal(vc);            
                        listExt.add(getNutrient("E00018", vcDosage, fmId)); 
                    } catch (Exception e) {
                        consoleLog(j, i, vc, "vc");
                    }

                }
                //总维生素E
                String ve = (StringUtils.isEmpty(row.getCell(26).toString())) ? "" : row.getCell(26).toString();
                if (!StringUtils.isEmpty(ve) && !StringUtils.isEmpty(ve.trim())) {
                    try {
                        BigDecimal veDosage = new BigDecimal(ve);            
                        listExt.add(getNutrient("E00007", veDosage, fmId));
                    } catch (Exception e) {
                        consoleLog(j, i, ve, "ve");
                    }

                }
                
                //TODO 总维生素Ea-TE  
                String ve_a = (StringUtils.isEmpty(row.getCell(27).toString())) ? "" : row.getCell(27).toString();
                if (!StringUtils.isEmpty(ve_a) && !StringUtils.isEmpty(ve.trim())) {
                    try {
                        BigDecimal veDosage = new BigDecimal(ve_a);            
                        listExt.add(getNutrient("E00048", veDosage, fmId));
                    } catch (Exception e) {
                        consoleLog(j, i, ve_a, "ve_a");
                    }
                }
                
                //钙
                String ga = (StringUtils.isEmpty(row.getCell(28).toString())) ? "" : row.getCell(28).toString();
                if (!StringUtils.isEmpty(ga) && !StringUtils.isEmpty(ga.trim())) {
                    try {
                        BigDecimal gaDosage = new BigDecimal(ga);            
                        listExt.add(getNutrient("E00019", gaDosage, fmId));
                    } catch (Exception e) {
                        consoleLog(j, i, ga, "ga");
                    }
                }
                //磷
                String p_n = (StringUtils.isEmpty(row.getCell(29).toString())) ? "" : row.getCell(29).toString();
                if (!StringUtils.isEmpty(p_n) && !StringUtils.isEmpty(p_n.trim())) {
                    try {
                        BigDecimal pDosage = new BigDecimal(p_n);            
                        listExt.add(getNutrient("E00020", pDosage, fmId)); 
                    } catch (Exception e) {
                        consoleLog(j, i, p_n, "p_n");
                    }

                }
                //钾
                String k_n = (StringUtils.isEmpty(row.getCell(30).toString())) ? "" : row.getCell(30).toString();
                if (!StringUtils.isEmpty(k_n) && !StringUtils.isEmpty(k_n.trim())) {
                    try {
                        BigDecimal kDosage = new BigDecimal(k_n);            
                        listExt.add(getNutrient("E00021 ", kDosage, fmId)); 
                    } catch (Exception e) {
                        consoleLog(j, i, k_n, "k_n");
                    }

                }
                //钠
                String na = (StringUtils.isEmpty(row.getCell(31).toString())) ? "" : row.getCell(31).toString();
                if (!StringUtils.isEmpty(na) && !StringUtils.isEmpty(na.trim())) {
                    try {
                        BigDecimal naDosage = new BigDecimal(na);            
                        listExt.add(getNutrient("E00022", naDosage, fmId)); 
                    } catch (Exception e) {
                        consoleLog(j, i, na, "na");
                    }

                }
                //镁
                String mg = (StringUtils.isEmpty(row.getCell(32).toString())) ? "" : row.getCell(32).toString();
                if (!StringUtils.isEmpty(mg) && !StringUtils.isEmpty(mg.trim())) {
                    try {
                        BigDecimal mgDosage = new BigDecimal(mg);            
                        listExt.add(getNutrient("E00023", mgDosage, fmId)); 
                    } catch (Exception e) {
                        consoleLog(j, i, mg, "mg");
                    }

                }
                //铁
                String fe = (StringUtils.isEmpty(row.getCell(33).toString())) ? "" : row.getCell(33).toString();
                if (!StringUtils.isEmpty(fe) && !StringUtils.isEmpty(fe.trim())) {
                    try {
                        BigDecimal feDosage = new BigDecimal(fe);            
                        listExt.add(getNutrient("E00024", feDosage, fmId));
                    } catch (Exception e) {
                        consoleLog(j, i, fe, "fe");
                    }
;
                }            
                //锌
                String zn = (StringUtils.isEmpty(row.getCell(34).toString())) ? "" : row.getCell(34).toString();
                if (!StringUtils.isEmpty(zn) && !StringUtils.isEmpty(zn.trim())) {
                    try {
                        BigDecimal znDosage = new BigDecimal(zn);            
                        listExt.add(getNutrient("E00023", znDosage, fmId));
                    } catch (Exception e) {
                        consoleLog(j, i, zn, "zn");
                    }

                }
                //硒
                String se = (StringUtils.isEmpty(row.getCell(35).toString())) ? "" : row.getCell(35).toString();               
                if (!StringUtils.isEmpty(se) && !StringUtils.isEmpty(se.trim())) {
                    try {
                        BigDecimal seDosage = new BigDecimal(se);            
                        listExt.add(getNutrient("E00027", seDosage, fmId));
                    } catch (Exception e) {
                        consoleLog(j, i, se, "se");
                    }
                }
                //铜
                String cu = (StringUtils.isEmpty(row.getCell(36).toString())) ? "" : row.getCell(36).toString();
                if (!StringUtils.isEmpty(cu) && !StringUtils.isEmpty(cu.trim())) {
                    try {
                        BigDecimal cuDosage = new BigDecimal(cu);            
                        listExt.add(getNutrient("E00028", cuDosage, fmId));
                    } catch (Exception e) {
                        consoleLog(j, i, cu, "cu");
                    }
                }
                //锰
                String mn = (StringUtils.isEmpty(row.getCell(37).toString())) ? "" : row.getCell(37).toString();
                if (!StringUtils.isEmpty(mn)) {
                    try {
                        BigDecimal mnDosage = new BigDecimal(mn);            
                        listExt.add(getNutrient("E00030", mnDosage, fmId));
                    } catch (Exception e) {
                        consoleLog(j, i, mn, "mn");
                    }

                }
                //碘
                String i_n = (StringUtils.isEmpty(row.getCell(38).toString())) ? "" : row.getCell(38).toString();
                if (!StringUtils.isEmpty(i_n) && !StringUtils.isEmpty(i_n.trim())) {
                    try {
                        BigDecimal iDosage = new BigDecimal(i_n);            
                        listExt.add(getNutrient("E00025", iDosage, fmId));
                    } catch (Exception e) {
                        consoleLog(j, i, i_n, "i_n");
                    }
                }
                //保存元素集合
                for (NutrientAmount nutrientAmount : listExt) {
                    if (nutrientAmount.getNutrientDosage() != null) {
                        foodDAO.save(nutrientAmount);
                    }
                }                      
            }
        }


    }
    
    /**
     * 
     * 生成元素对象
     *
     * @author zj
     * @param nutrientId
     * @param nutrientDosage
     * @param fmId
     * @return
     */
    public NutrientAmount getNutrient(String nutrientId, BigDecimal nutrientDosage, String fmId) {
        NutrientAmount nutrientAmout = new NutrientAmount();
        nutrientAmout.setCorresId(fmId);
        nutrientAmout.setCorresType(corresType);
        nutrientAmout.setNutrientId(nutrientId);
        nutrientAmout.setNutrientDosage(nutrientDosage);
        nutrientAmout.setCreateInsId("sys");
        nutrientAmout.setCreateTime(new Date());
        nutrientAmout.setUpdateTime(new Date());
        nutrientAmout.setFlag(1);
        nutrientAmout.setUpdateUserId("sys");
        nutrientAmout.setCreateUserId("sys");
        return nutrientAmout;
    }
    
    public void consoleLog(int j, int i, String v, String ele) {
    	if (!v.equals("100.0") && !v.equals("")) {
            System.out.println("ele:" + ele);
            System.out.println("j:" + j);
            System.out.println("i:" + i);
            System.out.println("mn:" + v);
    	}
    }
    
    /**
     * 
     * 获取sheet对应大类
     *
     * @author zj
     * @param sheetNo
     * @return
     */
    public String getBigSort(int sheetNo) {
        String bigSort = null;
        if (sheetNo == 0) {//谷类
            bigSort = "004";
        } else if (sheetNo == 1) {//蔬菜类
            bigSort = "002";
        } else if (sheetNo == 2) {//薯类
            bigSort = "015";
        } else if (sheetNo == 3) {//水果
            bigSort = "007";
        } else if (sheetNo == 4) {//大豆类
            bigSort = "005";
        } else if (sheetNo == 5) {//坚果
            bigSort = "013";
        } else if (sheetNo == 6) {//全脂乳品
            bigSort = "010";
        } else if (sheetNo == 7) {//脱脂乳品
            bigSort = "011";
        } else if (sheetNo == 8) {//油脂
            bigSort = "008";
        } else if (sheetNo == 9) {//调味品
            bigSort = "001";
        } else if (sheetNo == 10) {//鱼
            bigSort = "022";
        } else if (sheetNo == 11) {//虾贝
            bigSort = "016";
        } else if (sheetNo == 12) {//肉（肥瘦）
            bigSort = "017";
        } else if (sheetNo == 13) {//肉 （肥）
            bigSort = "018";
        } else if (sheetNo == 14) {//肉 （瘦）
            bigSort = "019";
        } else if (sheetNo == 15) {//蛋
            bigSort = "020";
        } else if (sheetNo == 16) {//淀粉类
            bigSort = "012";
        } else if (sheetNo == 17) {//其他
            bigSort = "014";
        }
        return bigSort;
    }
    
    
    
    public static void main(String[] args) {
    	
    	String a = "64.0";
    	a = a.substring(0, a.indexOf("."));
    	int b = Integer.parseInt(a);
    	System.out.println(b);
        
//        String str = "链鱼[白鲢、胖子、连子鱼]";
//        
//        if (str.indexOf("[") != -1 && str.indexOf("]") != -1) {
//            int startX = str.indexOf("[");
//            String strName = str.substring(0, startX);
//            System.out.println(strName);
//            String strAlias = str.substring(startX+1, str.length()-1);
//            System.out.println(strAlias);
//        }
        
    }


}

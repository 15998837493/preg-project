
package com.mnt.preg.examitem.util.life;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.mnt.preg.examitem.entity.DietaryFrequencyItem;
import com.mnt.preg.examitem.pojo.EvaluateFoodLibraryPojo;
import com.mnt.preg.examitem.pojo.LifeStyleQuestionAnswerPojo;

/**
 * 
 * 膳食及生活方式分析工具
 * 
 * @author mnt_zhangjing
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-3-6 mnt_zhangjing 初版
 */
public class DietaryFrequencyAnalysisUtil extends DietaryFrequencyAnalysis {

    private double intakeFat = 0;// 脂肪摄入量

    private double intakeCbr = 0;// 碳水化合物摄入量

    private double intakeProtid = 0;// 蛋白质摄入量

    private String lackingElementstr = ""; // 摄入不足字符串 优质蛋白质处用

    public DietaryFrequencyAnalysisUtil(List<LifeStyleQuestionAnswerPojo> lifeStyleQuestionAnswerBos,
            List<EvaluateFoodLibraryPojo> evaluateFoodLibraryVos, String recordId) {
        super(lifeStyleQuestionAnswerBos, evaluateFoodLibraryVos, recordId);
    }

    /**
     * 
     * 水
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return DietaryFrequencyItem
     */
    public DietaryFrequencyItem getIntakeWater(String recordId, String pregnantStage) {
        double water = getWater();
        String itemString = String.valueOf((int) water);
        double[] recom = StageTransform.transformWater(pregnantStage);
        String refValue = (int) recom[0] + "~" + (int) recom[1];
        String result[] = getComparerRatio(water, recom[0], recom[1]);
        if ("0".equals(itemString)) {
            itemString = "——";
            result[0] = "";
        }
        return createDietaryItem(recordId, "饮水", dietaryFrequencyConstant.water, itemString, "ml", result[0],
                refValue, "0");
    }

    /**
     * 
     * 用油量
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return DietaryFrequencyItem
     */
    public DietaryFrequencyItem getIntakeOil(String recordId) {
        String[] result = getOilWeightString();
        return createDietaryItem(recordId, "用油量", dietaryFrequencyConstant.oils, result[0], "g", result[1], "25", "0");
    }

    /**
     * 
     * 谷类
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return DietaryFrequencyItem
     */
    public DietaryFrequencyItem getIntakeCereal(String recordId, String pregnantStage) {
        double cereal = getCereal();
        String itemString = Math.round(cereal) + "";
        double[] recom = StageTransform.transformCereal(pregnantStage);
        double min = recom[0];
        double max = recom[1];
        String result[] = getComparerRatio(cereal, min, max);
        if (cereal == 0) {
            itemString = "——";
            result[0] = "";
        }
        return createDietaryItem(recordId, "谷类", dietaryFrequencyConstant.cereal, itemString, "g", result[0],
                (int) min + "~" + (int) max, "0");
    }

    /**
     * 
     * 薯类
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return DietaryFrequencyItem
     */
    public DietaryFrequencyItem getIntakePotatoes(String recordId, String pregnantStage) {
        String itemString = potatoes;
        double[] recom = StageTransform.transformPotatoes(pregnantStage);
        double min = recom[0];
        double max = recom[1];
        String refValue = "";
        if (min >= max) {
            refValue = String.valueOf((int) min);
        } else {
            refValue = (int) min + "~" + (int) max;
        }
        double potatoesval = 0;
        if (potatoes.matches("\\d+(.\\d{1,2})?")) {
            potatoesval = Double.parseDouble(potatoes);
        }
        String result[] = getComparerRatio(potatoesval, min, max);
        if (StringUtils.isEmpty(itemString)) {
            itemString = "——";
            result[0] = "";
        }
        return createDietaryItem(recordId, "薯类", dietaryFrequencyConstant.potatoes, itemString, "g", result[0],
                refValue, "0");
    }

    /**
     * 
     * 蔬菜
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return DietaryFrequencyItem
     */
    public DietaryFrequencyItem getIntakeVeg(String recordId, String pregnantStage) {
        double vege = getVegetablesWeight();
        String itemString = Math.round(vege) + "";
        double[] recom = StageTransform.transformVegetables(pregnantStage);
        double min = recom[0];
        double max = recom[1];
        String refValue = "";
        if (min >= max) {
            refValue = String.valueOf((int) min);
        } else {
            refValue = (int) min + "~" + (int) max;
        }
        String result[] = getComparerRatio(vege, min, max);
        if ("0".equals(itemString)) {
            itemString = "——";
            result[0] = "";
        }
        return createDietaryItem(recordId, "蔬菜", dietaryFrequencyConstant.veg, itemString, "g", result[0], refValue,
                "0");
    }

    /**
     * 
     * 水果
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return DietaryFrequencyItem
     */
    public DietaryFrequencyItem getIntakeFruit(String recordId, String pregnantStage) {
        double fruit = getFruit();
        String itemString = Math.round(fruit) + "";
        double[] recom = StageTransform.transformFruit(pregnantStage);
        double min = recom[0];
        double max = recom[1];
        String result[] = getComparerRatio(fruit, min, max);
        if ("0".equals(itemString)) {
            itemString = "——";
            result[0] = "";
        }
        return createDietaryItem(recordId, "水果", dietaryFrequencyConstant.fruit, itemString, "g", result[0], (int) min
                + "~" + (int) max, "0");
    }

    /**
     * 
     * 鱼肉蛋
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return DietaryFrequencyItem
     */
    public DietaryFrequencyItem getIntakeFme(String recordId, String pregnantStage) {
        double fme = getMeatWeight();
        String itemString = Math.round(fme) + "";
        double[] recom = StageTransform.transformFME(pregnantStage);
        double min = recom[0];
        double max = recom[1];
        String result[] = getComparerRatio(fme, min, max);
        if ("0".equals(itemString)) {
            itemString = "——";
            result[0] = "";
        }
        return createDietaryItem(recordId, "鱼肉蛋", dietaryFrequencyConstant.fme, itemString, "g", result[0],
                (int) min + "~" + (int) max, "0");
    }

    /**
     * 
     * 乳类
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return DietaryFrequencyItem
     */
    public DietaryFrequencyItem getIntakeMilk(String recordId, String pregnantStage) {
        double milk = getAMilk() + getTMilk();
        String itemString = Math.round(milk) + "";
        double[] recom = StageTransform.transformMilk(pregnantStage);
        double min = recom[0];
        double max = recom[1];
        String refValue = "";
        if (min >= max) {
            refValue = String.valueOf((int) min);
        } else {
            refValue = (int) min + "~" + (int) max;
        }
        String result[] = getComparerRatio(milk, min, max);
        if ("0".equals(itemString)) {
            itemString = "——";
            result[0] = "";
        }
        return createDietaryItem(recordId, "奶及奶制品", dietaryFrequencyConstant.milk, itemString, "g", result[0],
                refValue, "0");
    }

    /**
     * 
     * 豆类
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return DietaryFrequencyItem
     */
    public DietaryFrequencyItem getIntakeBean(String recordId, String pregnantStage) {
        double bean = getBeans();
        String itemString = Math.round(bean) + "";
        double[] recom = StageTransform.transformBean(pregnantStage);
        double min = recom[0];
        double max = recom[1];
        String refValue = "";
        if (min >= max) {
            refValue = String.valueOf((int) min);
        } else {
            refValue = (int) min + "~" + (int) max;
        }
        String result[] = getComparerRatio(bean, min, max);
        if ("0".equals(itemString)) {
            itemString = "——";
            result[0] = "";
        }
        return createDietaryItem(recordId, "大豆类", dietaryFrequencyConstant.bean, itemString, "g", result[0],
                refValue, "0");
    }

    /**
     * 
     * 坚果类
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return DietaryFrequencyItem
     */
    public DietaryFrequencyItem getIntakeNut(String recordId, String pregnantStage) {
        double nut = getNut();
        String itemString = Math.round(nut) + "";
        double[] recom = StageTransform.transformNut(pregnantStage);
        double min = recom[0];
        double max = recom[1];
        String refValue = "";
        if (min >= max) {
            refValue = String.valueOf((int) min);
        } else {
            refValue = (int) min + "~" + (int) max;
        }
        String result[] = getComparerRatio(nut, min, max);
        if ("0".equals(itemString)) {
            itemString = "——";
            result[0] = "";
        }
        return createDietaryItem(recordId, "坚果", dietaryFrequencyConstant.nut, itemString, "g", result[0],
                refValue, "0");
    }

    /**
     * 
     * 动物内脏
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return DietaryFrequencyItem
     */
    public DietaryFrequencyItem getIntakeOrgans(String recordId) {
        String itemString = Math.round(getOrgans()) + "";
        if ("0".equals(itemString)) {
            itemString = "——";
        }
        return createDietaryItem(recordId, "动物内脏", dietaryFrequencyConstant.organs, itemString, "g", "", "——", "0");
    }

    /**
     * 菌类
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return DietaryFrequencyItem
     */
    public DietaryFrequencyItem getIntakeFungus(String recordId) {
        ResultEntity entity = getVegetablesClass();
        double ratio = 0;
        ratio = (double) (Double.parseDouble(fungus.substring(0, fungus.length() - 1)) / 100);
        String itemString = String.valueOf((int) (Math.rint(entity.getWeight() * ratio)));
        if ("0".equals(itemString)) {
            itemString = "——";
        }
        return createDietaryItem(recordId, "菌类", dietaryFrequencyConstant.fungus, itemString, "g", "", "——", "0");
    }

    /**
     * 
     * 藻类
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return DietaryFrequencyItem
     */
    public DietaryFrequencyItem getIntakeAlgae(String recordId) {
        ResultEntity entity = getVegetablesClass();
        double ratio = 0;
        ratio = (double) (Double.parseDouble(algae.substring(0, algae.length() - 1)) / 100);
        String itemString = String.valueOf((int) (Math.rint(entity.getWeight() * ratio)));
        if ("0".equals(itemString)) {
            itemString = "——";
        }
        return createDietaryItem(recordId, "藻类", dietaryFrequencyConstant.algae, itemString, "g", "", "——", "0");
    }

    /**
     * 
     * 粗杂粮
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return DietaryFrequencyItem
     */
    public DietaryFrequencyItem getIntakeCZL(String recordId) {
        String itemString = coarseGrains;
        double ratio = Double.parseDouble(coarseGrains.substring(0, coarseGrains.length() - 1)) / 100;
        String resultString = "";
        String result = "0";
        if (ratio <= 0.3) {
            resultString = down;
            result = "4";
        }
        return createDietaryItem(recordId, "粗杂粮占比", dietaryFrequencyConstant.czl, itemString, "",
                resultString, ">30%", result);
    }

    /**
     * 
     * 绿叶蔬菜
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return DietaryFrequencyItem
     */
    public DietaryFrequencyItem getIntakesVegetables(String recordId) {
        String itemString = glv;
        double ratio = 0;
        ratio = (double) (Double.parseDouble(glv.substring(0, glv.length() - 1)) / 100);
        String resultString = "";
        String result = "0";
        if (ratio <= 0.5) {
            resultString = down;
            result = "4";
        }
        return createDietaryItem(recordId, "深色蔬菜占比", dietaryFrequencyConstant.vegetables, itemString, "",
                resultString, ">50%", result);
    }

    /**
     * 
     * 优质蛋白质
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return DietaryFrequencyItem
     */
    public DietaryFrequencyItem getIntakesGoodProtid(String recordId) {
        double goodProtid = getGoodProtid();
        double protid = getIntakeProtid();
        String result = "";
        String resultExc = "0";
        if (goodProtid < protid / 2) {
            result = down;
            resultExc = "4";
        }
        DecimalFormat df = new DecimalFormat("0");
        return createDietaryItem(recordId, "优质蛋白质（g）", dietaryFrequencyConstant.goodProtid, df.format(goodProtid)
                + "±2", "g", result, ">总蛋白的50%", resultExc);
    }

    /**
     * 
     * 荤素比
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return DietaryFrequencyItem
     */
    public DietaryFrequencyItem getIntakeMeatRatio(String recordId) {
        String result = "";
        // 获取荤菜的重量
        double meat = getMeatWeight();
        // 获取素菜的重量
        double vegetables = getVegetablesWeight();
        // 计算荤素比
        String resultExc = "0";
        int ratio = (int) Math.round(meat / (meat + vegetables) * 100);
        if (ratio >= 50) {
            result = "↑";
            resultExc = "4";
        }
        return createDietaryItem(recordId, "荤菜质量占比", dietaryFrequencyConstant.meatRatio, ratio + "%", "", result,
                "<50%", resultExc);
    }

    /**
     * 实际一日总能量
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return DietaryFrequencyItem
     */
    public DietaryFrequencyItem getActualEnergy(String recordId, BigDecimal custHeight, String pregnantStage) {
        int[] itemString = getActualTotalEnergy();
        double height = 0;
        if (custHeight != null) {
            height = custHeight.doubleValue() / 100;
        }
        double recommond = getRecommendEnergy(height, pregnantStage);
        String result = "";
        String resultExc = "0";
        if (itemString[0] > recommond) {
            result = up;
            resultExc = "4";
        }
        if (itemString[1] < recommond) {
            result = down;
            resultExc = "4";
        }
        return createDietaryItem(recordId, "平均一日能量", dietaryFrequencyConstant.actual_totalEnergy, itemString[0] + "~"
                + itemString[1], "kacl", result, String.valueOf((int) recommond), resultExc);
    }

    /**
     * 普通食物能量占比
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return DietaryFrequencyItem
     */
    public DietaryFrequencyItem getEnergyPrec(String recordId, BigDecimal custHeight, String pregnantStage) {
        double indexY = getSnacksClassYingyang("foodEnergy");// 营养食品能量
        double totalEnergy = getTotalContent("foodEnergy", false);
        double percY = 0;
        if (totalEnergy != 0) {
            percY = indexY / totalEnergy * 100;
        }
        double indexL = getSnacksClassLingShi("foodEnergy");// 零食能量
        double percL = 0;
        if (totalEnergy != 0) {
            percL = indexL / totalEnergy * 100;
        }
        double perc = 100 - percY - percL;
        DecimalFormat df = new DecimalFormat("0.0");
        return createDietaryItem(recordId, "普通食物能量占比", dietaryFrequencyConstant.putongEnergy, df.format(perc) + "%",
                "", "", "", "0");
    }

    /**
     * 营养食品能量
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return DietaryFrequencyItem
     */
    public DietaryFrequencyItem getEnergyYingyang(String recordId, BigDecimal custHeight, String pregnantStage) {
        double index = getSnacksClassYingyang("foodEnergy");// 营养食品能量
        double totalEnergy = getTotalContent("foodEnergy", false);
        double perc = 0;
        if (totalEnergy != 0) {
            perc = index / totalEnergy * 100;
        }
        DecimalFormat df = new DecimalFormat("0.0");
        return createDietaryItem(recordId, "营养食品能量", dietaryFrequencyConstant.zibuEnergy, df.format(perc) + "%", "",
                "", "", "0");
    }

    /**
     * 零食能量占比
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return DietaryFrequencyItem
     */
    public DietaryFrequencyItem getEnergyLingshi(String recordId, BigDecimal custHeight, String pregnantStage) {
        double index = getSnacksClassLingShi("foodEnergy");// 零食能量
        double totalEnergy = getTotalContent("foodEnergy", false);
        double perc = 0;
        if (totalEnergy != 0) {
            perc = index / totalEnergy * 100;
        }
        DecimalFormat df = new DecimalFormat("0.0");
        return createDietaryItem(recordId, "零食能量占比", dietaryFrequencyConstant.lingshiEnergy, df.format(perc) + "%", "",
                "", "", "0");
    }

    /**
     * 
     * 碳水化合物摄入量
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return DietaryFrequencyItem
     */
    public DietaryFrequencyItem getIntakeCbr(String recordId, BigDecimal custHeight, String pregnantStage) {
        intakeCbr = getIntakeCbr();
        double height = 0;
        if (custHeight != null) {
            height = custHeight.doubleValue() / 100;
        }
        DecimalFormat df = new DecimalFormat("0.0");
        String recommond = getRecommendCbr(height, pregnantStage);
        String result[] = getComparerCbr(height, pregnantStage, intakeCbr);
        return createDietaryItem(recordId, "碳水化合物摄入量", dietaryFrequencyConstant.cbr, df.format(intakeCbr), "g",
                result[0], recommond, result[1]);
    }

    /**
     * 
     * 蛋白质摄入量
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return DietaryFrequencyItem
     */
    public DietaryFrequencyItem getIntakeProtid(String recordId, BigDecimal custWeight) {
        intakeProtid = getIntakeProtid();
        DecimalFormat df = new DecimalFormat("0.0");
        double weight = 0;
        if (custWeight != null) {
            weight = custWeight.doubleValue();
        }
        String recommond = getRecommendProtid(weight);
        String result[] = getComparerProtid(weight, intakeProtid);
        return createDietaryItem(recordId, "蛋白质摄入量", dietaryFrequencyConstant.protid, df.format(intakeProtid), "g",
                result[0], recommond, result[1]);
    }

    /**
     * 
     * 脂肪摄入量
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return DietaryFrequencyItem
     */
    public DietaryFrequencyItem getIntakeFat(String recordId, BigDecimal custHeight, String pregnantStage) {
        intakeFat = getIntakeFat();
        DecimalFormat df = new DecimalFormat("0.0");
        double height = 0;
        if (custHeight != null) {
            height = custHeight.doubleValue() / 100;
        }
        String recommond = getRecommendFat(height, pregnantStage);
        String result[] = getComparerFat(height, pregnantStage, intakeFat);
        return createDietaryItem(recordId, "脂肪", dietaryFrequencyConstant.fat, df.format(intakeFat), "g", result[0],
                recommond, result[1]);
    }

    /**
     * 
     * 碳水化合物供能比
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return DietaryFrequencyItem
     */
    public DietaryFrequencyItem getIntakeCbrRatio(String recordId) {
        double cbrRatio = 0;
        String itemString = "0%~2%";
        if (intakeCbr + intakeProtid + intakeFat != 0) {
            cbrRatio = intakeCbr * 4 / (intakeCbr * 4 + intakeProtid * 4 + intakeFat * 9) * 100;
            itemString = (int) ((Math.rint(cbrRatio) - 2) < 0 ? 0 : (Math.rint(cbrRatio) - 2)) + "%~"
                    + (int) ((Math.rint(cbrRatio) + 2) > 100 ? 100 : (Math.rint(cbrRatio) + 2)) + "%";
        }
        double min = 50;
        double max = 65;
        String result[] = getComparerRatio(cbrRatio, min, max);
        return createDietaryItem(recordId, "碳水化合物供能比", dietaryFrequencyConstant.cbrratio, itemString, "", result[0],
                (int) min + "%~" + (int) max + "%", result[1]);
    }

    /**
     * 
     * 蛋白质供能比
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return DietaryFrequencyItem
     */
    public DietaryFrequencyItem getIntakeProtidRatio(String recordId) {
        double protidRatio = 0;
        String itemString = "0%~2%";
        if (intakeCbr + intakeProtid + intakeFat != 0) {
            protidRatio = intakeProtid * 4 / (intakeCbr * 4 + intakeProtid * 4 + intakeFat * 9) * 100;
            itemString = ((Math.round(protidRatio) - 2) < 0 ? 0 : (Math.round(protidRatio) - 2)) + "%~"
                    + ((Math.round(protidRatio) + 2) > 100 ? 100 : (Math.round(protidRatio) + 2)) + "%";
        }
        double min = 12;
        double max = 20;
        String result[] = getComparerRatio(protidRatio, min, max);
        return createDietaryItem(recordId, "蛋白质供能比", dietaryFrequencyConstant.protidratio, itemString, "", result[0],
                (int) min + "%~" + (int) max + "%", result[1]);
    }

    /**
     * 
     * 脂肪供能比
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return DietaryFrequencyItem
     */
    public DietaryFrequencyItem getIntakeFatRatio(String recordId) {
        double fatRatio = 0;
        String itemString = "0%~2%";
        if (intakeCbr + intakeProtid + intakeFat != 0) {
            fatRatio = intakeFat * 9 / (intakeCbr * 4 + intakeProtid * 4 + intakeFat * 9) * 100;
            itemString = (int) ((Math.rint(fatRatio) - 2) < 0 ? 0 : (Math.rint(fatRatio) - 2)) + "%~"
                    + (int) ((Math.rint(fatRatio) + 2) > 100 ? 100 : (Math.rint(fatRatio) + 2)) + "%";
        }
        double min = 20;
        double max = 30;
        String result[] = getComparerRatio(fatRatio, min, max);
        return createDietaryItem(recordId, "脂肪供能比", dietaryFrequencyConstant.fatratio, itemString, "", result[0],
                (int) min + "%~" + (int) max + "%", result[1]);
    }

    /**
     * 
     * 摄入不足元素分析
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return DietaryFrequencyItem
     */
    public DietaryFrequencyItem analysisLackingElement(String recordId, String diagnosisPregnantStage, BigDecimal weight) {
        if (null == weight) {
            weight = new BigDecimal(0);
        }
        lackingElementstr = getLackingElement(diagnosisPregnantStage, weight);
        String result = StringUtils.isEmpty(lackingElementstr) ? "0" : "4";
        return createDietaryItem(recordId, "膳食中可能摄入不足的营养素有", dietaryFrequencyConstant.lacking, "", "",
                lackingElementstr, "", result);
    }

    /**
     * 摄入频率记录
     * 
     * @author xdc
     * @param recordId
     * @param diagnosisPregnantStage
     * @return
     */
    public DietaryFrequencyItem analysisFre1(String recordId, String diagnosisPregnantStage) {
        return createDietaryItem(recordId, "全脂奶及奶制品", dietaryFrequencyConstant.fre1, getFreqFormatStr(getFre1()), "",
                "", "", "0");
    }

    public DietaryFrequencyItem analysisFre2(String recordId, String diagnosisPregnantStage) {
        return createDietaryItem(recordId, "脱脂奶及奶制品", dietaryFrequencyConstant.fre2, getFreqFormatStr(getFre2()), "",
                "", "", "0");
    }

    public DietaryFrequencyItem analysisFre3(String recordId, String diagnosisPregnantStage) {
        return createDietaryItem(recordId, "大豆类", dietaryFrequencyConstant.fre3, getFreqFormatStr(getFre3()), "", "",
                "",
                "0");
    }

    public DietaryFrequencyItem analysisFre4(String recordId, String diagnosisPregnantStage) {
        return createDietaryItem(recordId, "鱼虾贝类", dietaryFrequencyConstant.fre4, getFreqFormatStr(getFre4()), "", "",
                "", "0");
    }

    public DietaryFrequencyItem analysisFre5(String recordId, String diagnosisPregnantStage) {
        return createDietaryItem(recordId, "坚果类", dietaryFrequencyConstant.fre5, getFreqFormatStr(getFre5()), "", "",
                "", "0");
    }

    public DietaryFrequencyItem analysisFre6(String recordId, String diagnosisPregnantStage) {
        return createDietaryItem(recordId, "动物内脏", dietaryFrequencyConstant.fre6, getFreqFormatStr(getFre6()), "", "",
                "", "0");
    }

    public DietaryFrequencyItem analysisFre7(String recordId, String diagnosisPregnantStage) {
        return createDietaryItem(recordId, "海参", dietaryFrequencyConstant.fre7, getFreqFormatStr(getFre7()), "", "",
                "", "0");
    }

    public DietaryFrequencyItem analysisFre8(String recordId, String diagnosisPregnantStage) {
        return createDietaryItem(recordId, "燕窝", dietaryFrequencyConstant.fre8, getFreqFormatStr(getFre8()), "", "",
                "", "0");
    }

    public DietaryFrequencyItem analysisFre9(String recordId, String diagnosisPregnantStage) {
        return createDietaryItem(recordId, "蜂蜜", dietaryFrequencyConstant.fre9, getFreqFormatStr(getFre9()), "", "",
                "", "0");
    }

    public DietaryFrequencyItem analysisFre10(String recordId, String diagnosisPregnantStage) {
        return createDietaryItem(recordId, "饼干", dietaryFrequencyConstant.fre10, getFreqFormatStr(getFre10()), "", "",
                "", "0");
    }

    public DietaryFrequencyItem analysisFre11(String recordId, String diagnosisPregnantStage) {
        return createDietaryItem(recordId, "蛋糕", dietaryFrequencyConstant.fre11, getFreqFormatStr(getFre11()), "", "",
                "", "0");
    }

    public DietaryFrequencyItem analysisFre12(String recordId, String diagnosisPregnantStage) {
        return createDietaryItem(recordId, "巧克力", dietaryFrequencyConstant.fre12, getFreqFormatStr(getFre12()), "", "",
                "", "0");
    }

    public DietaryFrequencyItem analysisFre13(String recordId, String diagnosisPregnantStage) {
        return createDietaryItem(recordId, "蜜饯", dietaryFrequencyConstant.fre13, getFreqFormatStr(getFre13()), "", "",
                "", "0");
    }

    public DietaryFrequencyItem analysisFre14(String recordId, String diagnosisPregnantStage) {
        return createDietaryItem(recordId, "糖果", dietaryFrequencyConstant.fre14, getFreqFormatStr(getFre14()), "", "",
                "", "0");
    }

    public DietaryFrequencyItem analysisFre15(String recordId, String diagnosisPregnantStage) {
        return createDietaryItem(recordId, "海苔", dietaryFrequencyConstant.fre15, getFreqFormatStr(getFre15()), "", "",
                "", "0");
    }

    public DietaryFrequencyItem analysisFre16(String recordId, String diagnosisPregnantStage) {
        return createDietaryItem(recordId, "膨化食品", dietaryFrequencyConstant.fre16, getFreqFormatStr(getFre16()), "",
                "",
                "", "0");
    }

    public DietaryFrequencyItem analysisFre17(String recordId, String diagnosisPregnantStage) {
        return createDietaryItem(recordId, "雪糕", dietaryFrequencyConstant.fre17, getFreqFormatStr(getFre17()), "", "",
                "", "0");
    }

    public DietaryFrequencyItem analysisFre18(String recordId, String diagnosisPregnantStage) {
        return createDietaryItem(recordId, "饮料", dietaryFrequencyConstant.fre18, getFreqFormatStr(getFre18()), "", "",
                "", "0");
    }

    /**
     * 营养食品/医学食品/特膳食品
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return DietaryFrequencyItem
     */
    public DietaryFrequencyItem analysisYingyangFood(String recordId, String diagnosisPregnantStage) {
        return createDietaryItem(recordId, "营养食品/医学食品/特膳食品", dietaryFrequencyConstant.yingYang, getYingyang(), "", "",
                "", "0");
    }

    /**
     * 返回摄入频率记录的固定格式的字符串
     * 
     * @author xdc
     * @param freq
     * @param value
     * @return
     */
    private String getFreqFormatStr(String[] result) {
        if (StringUtils.isEmpty(result[0]) || StringUtils.isEmpty(result[1])) {
            return "——";
        }
        return result[1] + "；每次" + result[0] + "g";
    }

    /**
     * 生成DietaryFrequencyItem对象
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @param item
     * @param itemString
     * @return
     */
    private DietaryFrequencyItem createDietaryItem(String recordId, String itemName, String itemCode,
            String itemString, String itemUnit, String itemResult, String itemRefValue, String itemCompare) {
        DietaryFrequencyItem item = new DietaryFrequencyItem();
        item.setExamId(recordId);
        item.setItemName(itemName);
        item.setItemCode(itemCode);
        item.setItemString(itemString);
        item.setItemUnit(itemUnit);
        item.setItemResult(itemResult);
        item.setItemRefValue(itemRefValue);
        item.setItemCompare(itemCompare);
        item.setItemType("cus_result_dietary_frequency");
        return item;
    }

}

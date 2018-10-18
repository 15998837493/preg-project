
package com.mnt.preg.examitem.util.life;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mnt.preg.examitem.entity.DietaryFrequencyItem;
import com.mnt.preg.examitem.pojo.EvaluateFoodLibraryPojo;
import com.mnt.preg.examitem.pojo.LifeStyleQuestionAnswerPojo;

/**
 * 
 * 膳食及生活方式分析
 * 
 * @author mnt_zhangjing
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-3-6 mnt_zhangjing 初版
 */
public class DietaryFrequencyAnalysis extends DietaryNutritionAnalysis {

    public static final Logger LOGGER = LoggerFactory.getLogger(DietaryFrequencyAnalysis.class);

    private DietaryFrequencyItem dietaryFrequencyItem;

    private String dietary_frequency_type = "dietary_frequency";

    private String meal = "meal";

    private String itemType = "cus_result_dietary_frequency";

    private String recordId;

    protected String lackingResult = "摄入不足";

    protected String excessResult = "摄入过量";

    protected String mgUnit = "mg";

    protected String down = "↓";

    protected String up = "↑";

    // 存放非报告分析结果
    private List<DietaryFrequencyItem> dietaryFrequencyItems = new ArrayList<DietaryFrequencyItem>();

    // 返回非报告分析结果
    public List<DietaryFrequencyItem> getLifeStyleItems() {
        return dietaryFrequencyItems;
    }

    /**
     * 
     * 生成LifeStyleItem 对象
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @param item
     * @param itemString
     * @return
     */
    protected DietaryFrequencyItem createLifeStyleItem(String recordId, String item, String itemString) {
        dietaryFrequencyItem = new DietaryFrequencyItem();
        dietaryFrequencyItem.setExamId(recordId);
        dietaryFrequencyItem.setItemCode(item);
        dietaryFrequencyItem.setItemName(lifeStyleConstant.getLifeStyleItem(item));
        dietaryFrequencyItem.setItemType(itemType);
        if (StringUtils.isEmpty(itemString) || "0".equals(itemString)) {
            itemString = "——";
        }
        System.err.println(lifeStyleConstant.getLifeStyleItem(item) + "===" + itemString);
        dietaryFrequencyItem.setItemString(itemString);
        dietaryFrequencyItem.setItemClassify(dietary_frequency_type);
        return dietaryFrequencyItem;
    }

    /**
     * 
     * 生成LifeStyleItem 对象
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @param item
     * @param itemString
     * @return
     */
    protected DietaryFrequencyItem createLifeStyleItem(String recordId, String item, String itemString,
            String itemException) {
        dietaryFrequencyItem = new DietaryFrequencyItem();
        dietaryFrequencyItem.setExamId(recordId);
        dietaryFrequencyItem.setItemCode(item);
        dietaryFrequencyItem.setItemName(lifeStyleConstant.getLifeStyleItem(item));
        dietaryFrequencyItem.setItemType(itemType);
        if (StringUtils.isEmpty(itemString) || "0".equals(itemString)) {
            itemString = "——";
        }
        System.err.println(lifeStyleConstant.getLifeStyleItem(item) + "===" + itemString);
        dietaryFrequencyItem.setItemString(itemString);
        dietaryFrequencyItem.setItemClassify(dietary_frequency_type);
        dietaryFrequencyItem.setItemResult(itemException);
        return dietaryFrequencyItem;
    }

    /**
     * 
     * 生成LifeStyleItem 对象
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @param item
     * @param itemString
     * @return
     */
    protected DietaryFrequencyItem createLifeStyleItem(String recordId, String item, String itemString, String unit,
            String itemCompare, String itemResult, String itemRefValue) {
        dietaryFrequencyItem = new DietaryFrequencyItem();
        dietaryFrequencyItem.setExamId(recordId);
        dietaryFrequencyItem.setItemCode(item);
        dietaryFrequencyItem.setItemName(dietaryFrequencyConstant.getLifeStyleItem(item));
        dietaryFrequencyItem.setItemType(itemType);
        if (StringUtils.isEmpty(itemString) || "0".equals(itemString)) {
            itemString = "——";
        } else {
            itemString = itemString + unit + itemResult;
        }
        dietaryFrequencyItem.setItemString(itemString);
        dietaryFrequencyItem.setItemUnit(unit);
        dietaryFrequencyItem.setItemCompare(itemCompare);
        dietaryFrequencyItem.setItemRefValue(itemRefValue);
        String itemException = "";
        if (StringUtils.isNotEmpty(itemCompare)) {
            if (up.equals(itemCompare)) {
                if (dietaryFrequencyConstant.cbrratio.equals(item) || dietaryFrequencyConstant.protidratio.equals(item)
                        || lifeStyleConstant.fatratio.equals(item)) {
                    itemException = dietaryFrequencyItem.getItemName() + "超标";
                } else {
                    itemException = dietaryFrequencyItem.getItemName() + excessResult;
                }
            }
            if (down.equals(itemCompare)) {
                if (dietaryFrequencyConstant.cbrratio.equals(item) || dietaryFrequencyConstant.protidratio.equals(item)
                        || lifeStyleConstant.fatratio.equals(item)) {
                    itemException = dietaryFrequencyItem.getItemName() + "不足";
                } else if (lifeStyleConstant.water.equals(item)) {
                    itemException = dietaryFrequencyItem.getItemName() + "小于1500ml";
                } else {
                    itemException = dietaryFrequencyItem.getItemName() + lackingResult;
                }
            }
        }
        System.err.println(lifeStyleConstant.getLifeStyleItem(item) + "===" + itemString + "  参考值=" + itemRefValue
                + "  比较结果=" + itemCompare);
        dietaryFrequencyItem.setItemResult(itemException);
        dietaryFrequencyItem.setItemClassify(meal);
        return dietaryFrequencyItem;
    }

    /**
     * 
     * 生成LifeStyleItem 对象
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @param item
     * @param itemString
     * @return
     */
    protected DietaryFrequencyItem createDietaryitem(String recordId, String itemName, String itemCode,
            String itemString, String unit, String itemCompare, String itemResult, String itemException,
            String itemRefValue) {
        dietaryFrequencyItem = new DietaryFrequencyItem();
        dietaryFrequencyItem.setExamId(recordId);
        dietaryFrequencyItem.setItemCode(itemCode);
        dietaryFrequencyItem.setItemName(itemName);
        dietaryFrequencyItem.setItemType(itemType);
        if (StringUtils.isEmpty(itemString) || "0".equals(itemString)) {
            itemString = "——";
        }
        dietaryFrequencyItem.setItemString(itemString + unit + itemResult);
        dietaryFrequencyItem.setItemUnit(unit);
        dietaryFrequencyItem.setItemCompare(itemCompare);
        dietaryFrequencyItem.setItemResult(itemException);
        dietaryFrequencyItem.setItemRefValue(itemRefValue);
        dietaryFrequencyItem.setItemClassify(meal);
        return dietaryFrequencyItem;
    }

    /**
     * 
     * 生成LifeStyleItem 对象
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @param item
     * @param itemString
     * @return
     */
    protected DietaryFrequencyItem createLifeStyleItem(String recordId, String item, String itemString, String unit,
            String itemCompare, String itemResult, String itemException, String itemRefValue, String classify) {
        dietaryFrequencyItem = new DietaryFrequencyItem();
        dietaryFrequencyItem.setExamId(recordId);
        dietaryFrequencyItem.setItemCode(item);
        dietaryFrequencyItem.setItemName(lifeStyleConstant.getLifeStyleItem(item));
        dietaryFrequencyItem.setItemType(itemType);
        if (StringUtils.isEmpty(itemString) || "0".equals(itemString)) {
            itemString = "——";
        }
        System.err.println(lifeStyleConstant.getLifeStyleItem(item) + "===" + itemString + "  参考值=" + itemRefValue
                + "  比较结果=" + itemCompare);
        dietaryFrequencyItem.setItemString(itemString + unit + itemResult);
        dietaryFrequencyItem.setItemUnit(unit);
        dietaryFrequencyItem.setItemCompare(itemCompare);
        dietaryFrequencyItem.setItemResult(itemException);
        dietaryFrequencyItem.setItemRefValue(itemRefValue);
        dietaryFrequencyItem.setItemClassify(classify);
        return dietaryFrequencyItem;
    }

    public DietaryFrequencyAnalysis(List<LifeStyleQuestionAnswerPojo> lifeStyleQuestionAnswerBos,
            List<EvaluateFoodLibraryPojo> evaluateFoodLibraryVos, String recordId) {
        super(lifeStyleQuestionAnswerBos, evaluateFoodLibraryVos);
        this.recordId = recordId;
    }

    /**
     * 
     * 获取指定项目的生活调查信息
     * 
     * @author mnt_zhangjing
     * @param item
     * @return
     */
    public String getLifeResult(String item, List<LifeStyleQuestionAnswerPojo> questionAnswerBos) {
        List<Problem> problems = lifeStyleConstant.getLifeStyleProblem(item);
        if (CollectionUtils.isEmpty(questionAnswerBos)) {
            return "";
        }
        StringBuffer result = new StringBuffer();

        for (Problem problem : problems) {
            boolean isP205 = false;
            // 问题是否被回答
            boolean flag = false;
            for (LifeStyleQuestionAnswerPojo lsqab : questionAnswerBos) {
                if (lsqab.getProblemId().equalsIgnoreCase(problem.getProblemId())) {
                    // 多选题
                    if (lifeStyleConstant.checkbox.equals(problem.getProblemType())) {
                        // 有选项的
                        if (StringUtils.isNotEmpty(lsqab.getOptionId())) {
                            Option option = lifeStyleConstant.getLifeStyleOption(lsqab.getOptionId());
                            if (option != null) {
                                if (result.indexOf(problem.getProblemName()) == -1) {
                                    result.append(problem.getProblemName());
                                }
                                if (option.isException()) {
                                    // 异常项需要加红色标记
                                    flag = true;
                                    result.append("%s").append(option.getOptionName()).append(",");
                                } else {
                                    flag = true;
                                    result.append(option.getOptionName()).append(",");
                                }
                            }
                        } else {
                            // 填空（如其他）
                            flag = true;
                            if (lifeStyleConstant.p302.equals(lsqab.getProblemId())) {
                                result.append("%s").append(lsqab.getAddvalue());
                            } else {
                                result.append(lsqab.getAddvalue());
                            }
                        }
                    }
                    // 单选题
                    if (lifeStyleConstant.radio.equals(problem.getProblemType())) {
                        Option option = lifeStyleConstant.getLifeStyleOption(lsqab.getOptionId());
                        if (option != null) {
                            if (option.isException()) {
                                // 异常项需要加红色标记
                                flag = true;
                                result.append("%s").append(option.getOptionName());
                            } else {
                                flag = true;
                                result.append(option.getOptionName());
                            }
                        }
                    }

                    if (lifeStyleConstant.p205.equals(problem.getProblemId())) {
                        isP205 = true;
                    }
                }
            }

            if (result.length() > 0 && flag) {
                if (",".equals(result.substring(result.length() - 1))) {
                    if (isP205) {
                        result.replace(result.length() - 1, result.length(), "运动;");
                    } else {
                        result.replace(result.length() - 1, result.length(), ";");
                    }
                } else {
                    result.append(";");
                }
            }

        }
        return result.toString();
    }

    /**
     * 
     * 计算荤素比
     * 
     * @author mnt_zhangjing
     * @return
     */
    public String getMeatRatio() {

        String result = "";

        // 获取荤菜的重量
        double meat = getMeatWeight();

        // 获取素菜的重量
        double vegetables = getVegetablesWeight();

        // 计算荤素比
        int ratio = (int) (Math.round(meat / (meat + vegetables) * 100));

        if (ratio >= 50) {
            result = "%s" + lifeStyleConstant.getLifeStyleItem(lifeStyleConstant.B00576) + ":" + ratio + "%;";
            dietaryFrequencyItems.add(createDietaryitem(recordId, "荤素比", lifeStyleConstant.B00576, ratio + "%", "", "",
                    up, lifeStyleConstant.getLifeStyleItem(lifeStyleConstant.B00576) + ":" + ratio + "%", "50%"));
        }
        if (0 < ratio && ratio < 50) {
            result = lifeStyleConstant.getLifeStyleItem(lifeStyleConstant.B00576) + ":" + ratio + "%;";
        }
        return result;
    }

    /**
     * 
     * 获取指定的餐次的用餐时间
     * 
     * @author mnt_zhangjing
     * @param meal
     *            餐次
     * @return
     */
    public String getEatingTime(String meal) {

        String result = "";
        if (StringUtils.isNotEmpty(meal)) {
            List<LifeStyleQuestionAnswerPojo> eatTimeQuestionAnswerBos = questionMap.get(lifeStyleConstant.p103);
            if (CollectionUtils.isNotEmpty(eatTimeQuestionAnswerBos)) {
                for (LifeStyleQuestionAnswerPojo lsqab : eatTimeQuestionAnswerBos) {
                    if (meal.equalsIgnoreCase(lsqab.getOptionId())) {
                        result = lsqab.getAddvalue();
                    }
                }
            }
        }
        return result;
    }

    /**
     * 
     * 获取摄入不足的元素
     * 
     * @author mnt_zhangjing
     * @return
     */
    public String getLackingElement(String diagnosisPregnantStage, BigDecimal weight) {
        StringBuffer result = new StringBuffer();
        double standard = 0.9;
        // 1 判断碘是否显示
        if (CollectionUtils.isNotEmpty(dietQuestionAnswerBos)) {
            StringBuffer iodine = new StringBuffer();
            for (LifeStyleQuestionAnswerPojo lsqab : dietQuestionAnswerBos) {
                if (lifeStyleConstant.p110.equalsIgnoreCase(lsqab.getProblemId())) {
                    iodine.append(lsqab.getOptionId());
                }
            }

            if (iodine.indexOf(lifeStyleConstant.O11002) == -1) {
                result.append(lifeStyleConstant.iodine).append(";");
                dietaryFrequencyItems.add(createLifeStyleItem(recordId, lifeStyleConstant.B00524, "", "",
                        lifeStyleConstant.exception, down, ""));
            }
        }

        // 处理dietQuestionAnswerBos 为空的情况
        List<LifeStyleQuestionAnswerPojo> p107List = questionMap.get(lifeStyleConstant.p107);
        double deepFish = getDeepFish();
        // 2判断ω-3脂肪酸是否显示
        // if (CollectionUtils.isEmpty(p107List)) {
        // if (deepFish < 50) {
        // result.append(lifeStyleConstant.oil).append(";");
        // dietaryFrequencyItems.add(createLifeStyleItem(recordId, lifeStyleConstant.B00525, "", "",
        // lifeStyleConstant.exception, down, ""));
        // }
        // }

        if (CollectionUtils.isNotEmpty(p107List)) {
            // 是否包含金龙鱼1:1:1调和油 标识或亚麻籽油、紫苏籽油。
            boolean flag = false;
            for (LifeStyleQuestionAnswerPojo lsqab : p107List) {
                if (!flag) {
                    if (lifeStyleConstant.O10709.equalsIgnoreCase(lsqab.getOptionId())
                            || lifeStyleConstant.O10711.equalsIgnoreCase(lsqab.getOptionId())) {
                        flag = true;
                    }
                }
            }

            if (!flag && deepFish < 50) {
                result.append(lifeStyleConstant.oil).append(";");
                dietaryFrequencyItems.add(createLifeStyleItem(recordId, lifeStyleConstant.B00525, "", "",
                        lifeStyleConstant.exception, down, ""));
            }
        }

        // 4判断优质蛋白质是否显示
        String foodProtid = "foodProtid";
        // 优质蛋白质总量
        double goofProtidweight = getGoodProtid(foodProtid);
        //
        if (goofProtidweight == 0 || goofProtidweight < weight.doubleValue() * 1.2 * 0.5) {
            result.append(lifeStyleConstant.foodProtid).append(";");
        }

        // 5 计算膳食纤维量
        String foodDf = "foodDf";
        double foodDfweight = getTotalContent(foodDf, false);
        if (foodDfweight < 24) {
            result.append(lifeStyleConstant.foodDf).append(";");
            dietaryFrequencyItems.add(createLifeStyleItem(recordId, lifeStyleConstant.B00509,
                    String.valueOf((int) foodDfweight), "g", lifeStyleConstant.exception, down, "24g"));
        }

        // 6 计算钙
        String foodEca = "foodEca";
        double foodEcaweight = getTotalContent(foodEca, false);
        double recomendfoodEca = StageTransform.transformEca(diagnosisPregnantStage) * standard;
        if (foodEcaweight < recomendfoodEca) {// mg
            result.append(lifeStyleConstant.foodEca).append(";");
            dietaryFrequencyItems.add(createLifeStyleItem(recordId, lifeStyleConstant.B00519,
                    String.valueOf((int) (foodEcaweight)), mgUnit, lifeStyleConstant.exception, down,
                    recomendfoodEca + mgUnit));
        }

        // 7 计算钾
        String foodEk = "foodEk";
        double foodEkweight = getTotalContent(foodEk, false);
        double recomendfoodEk = StageTransform.transformEk(diagnosisPregnantStage) * standard;
        if (foodEkweight < recomendfoodEk) {// mg
            result.append(lifeStyleConstant.foodEk).append(";");
            dietaryFrequencyItems.add(createLifeStyleItem(recordId, lifeStyleConstant.B00520,
                    String.valueOf((int) (foodEkweight)), mgUnit, lifeStyleConstant.exception, down,
                    recomendfoodEk + mgUnit));
        }

        // 8 计算镁
        String foodEmg = "foodEmg";
        double foodEmgweight = getTotalContent(foodEmg, false);
        double recomendfoodEmg = StageTransform.transformEmg(diagnosisPregnantStage) * standard;
        if (foodEmgweight < recomendfoodEmg) {// mg
            result.append(lifeStyleConstant.foodEmg).append(";");
            dietaryFrequencyItems.add(createLifeStyleItem(recordId, lifeStyleConstant.B00521,
                    String.valueOf((int) (foodEmgweight)), mgUnit, lifeStyleConstant.exception, down,
                    recomendfoodEmg + mgUnit));
        }

        // 8 计算铁
        String foodEfe = "foodEfe";
        double foodEfeweight = getTotalContent(foodEfe, false);
        double recomendfoodEfe = StageTransform.transformEfe(diagnosisPregnantStage) * standard;
        if (foodEfeweight < recomendfoodEfe) {// mg
            result.append(lifeStyleConstant.foodEfe).append(";");
            dietaryFrequencyItems.add(createLifeStyleItem(recordId, lifeStyleConstant.B00522,
                    String.valueOf((int) (foodEfeweight)), mgUnit, lifeStyleConstant.exception, down,
                    recomendfoodEfe + mgUnit));
        }

        // 9 计算锌
        String foodEzn = "foodEzn";
        double foodEznweight = getTotalContent(foodEzn, false);
        double recomendfoodEzn = StageTransform.transformEzn(diagnosisPregnantStage) * standard;
        if (foodEznweight < recomendfoodEzn) {// mg
            result.append(lifeStyleConstant.foodEzn).append(";");
            dietaryFrequencyItems.add(createLifeStyleItem(recordId, lifeStyleConstant.B00523,
                    String.valueOf((int) (foodEznweight)), mgUnit, lifeStyleConstant.exception, down,
                    recomendfoodEzn + mgUnit));
        }

        // 10 计算硒
        String foodEse = "foodEse";
        double foodEseweight = getTotalContent(foodEse, false);
        double recomendfoodEse = StageTransform.transformEse(diagnosisPregnantStage) * standard;
        if (foodEseweight < recomendfoodEse) {// mg
            result.append(lifeStyleConstant.foodEse).append(";");
        }

        // 11 计算维生素A
        String foodVa = "foodVa";
        double foodVaweight = getTotalContent(foodVa, false);
        double recomendfoodVa = StageTransform.transformVa(diagnosisPregnantStage) * standard;
        if (foodVaweight < recomendfoodVa) {// mg
            result.append(lifeStyleConstant.foodVa).append(";");
            dietaryFrequencyItems.add(createLifeStyleItem(recordId, lifeStyleConstant.B00510,
                    String.valueOf((int) (foodVaweight)), mgUnit, lifeStyleConstant.exception, down,
                    recomendfoodVa + mgUnit));
        }

        // 12 计算维生素C
        String foodVc = "foodVc";
        double foodVcweight = getTotalContent(foodVc, false);
        double recomendfoodVc = StageTransform.transformVc(diagnosisPregnantStage) * standard;
        if (foodVcweight < recomendfoodVc) {// mg
            result.append(lifeStyleConstant.foodVc).append(";");
            dietaryFrequencyItems.add(createLifeStyleItem(recordId, lifeStyleConstant.B00511,
                    String.valueOf((int) (foodVcweight)), mgUnit, lifeStyleConstant.exception, down,
                    recomendfoodVc + mgUnit));
        }

        // 13 维生素E
        String foodVe = "foodVe";
        double foodVeweight = getTotalContent(foodVe, false);
        double recomendfoodVe = StageTransform.transformVe(diagnosisPregnantStage) * standard;
        if (foodVeweight < recomendfoodVe) {// mg
            result.append(lifeStyleConstant.foodVe).append(";");
            dietaryFrequencyItems.add(createLifeStyleItem(recordId, lifeStyleConstant.B00512,
                    String.valueOf((int) (foodVeweight)), mgUnit, lifeStyleConstant.exception, down,
                    foodVeweight + mgUnit));
        }

        // 14 维生素B1
        String foodVb1 = "foodVb1";
        double foodVb1weight = getTotalContent(foodVb1, false);
        double recomendfoodVb1 = StageTransform.transformVb1(diagnosisPregnantStage) * standard;
        if (foodVb1weight < recomendfoodVb1) {// mg
            result.append(lifeStyleConstant.foodVb1).append(";");
        }

        // 15 维生素B2
        String foodVb2 = "foodVb2";
        double foodVb2weight = getTotalContent(foodVb2, false);
        double recomendfoodVb2 = StageTransform.transformVb2(diagnosisPregnantStage) * standard;
        if (foodVb2weight < recomendfoodVb2) {// mg
            result.append(lifeStyleConstant.foodVb2).append(";");
        }

        // 16 烟酸
        String foodAf = "foodAf";
        double foodAfweight = getTotalContent(foodAf, false);
        double recomendfoodAf = StageTransform.transformAf(diagnosisPregnantStage) * standard;
        if (foodAfweight < recomendfoodAf) {// mg
            result.append(lifeStyleConstant.foodAf).append(";");
            dietaryFrequencyItems.add(createLifeStyleItem(recordId, lifeStyleConstant.B00515,
                    String.valueOf((int) (foodAfweight)), mgUnit, lifeStyleConstant.exception, down,
                    recomendfoodAf + mgUnit));
        }

        // 17 维生素B6
        String foodVb6 = "foodVb6";
        double foodVb6weight = getTotalContent(foodVb6, false);
        double recomendfoodVb6 = StageTransform.transformVb6(diagnosisPregnantStage) * standard;
        if (foodVb6weight < recomendfoodVb6) {// mg
            result.append(lifeStyleConstant.foodVb6).append(";");
            dietaryFrequencyItems.add(createLifeStyleItem(recordId, lifeStyleConstant.B00517,
                    String.valueOf((int) (foodVb6weight)), mgUnit, lifeStyleConstant.exception, down,
                    Math.rint(recomendfoodVb6 * 100) / 100 + mgUnit));
        }

        // 18 维生素B12
        String foodVb12 = "foodVb12";
        double foodVb12weight = getTotalContent(foodVb12, false);
        double recomendfoodVb12 = StageTransform.transformVb12(diagnosisPregnantStage) * standard;
        if (foodVb12weight < recomendfoodVb12) {// mg
            result.append(lifeStyleConstant.foodVb12).append(";");
            dietaryFrequencyItems.add(createLifeStyleItem(recordId, lifeStyleConstant.B00518,
                    String.valueOf((int) (foodVb12weight)), mgUnit, lifeStyleConstant.exception, down,
                    recomendfoodVb12 + mgUnit));
        }

        // 19 叶酸(维生素B9)
        String foodVb9 = "foodVb9";
        double foodVb9weight = getTotalContent(foodVb9, false);
        double recomendfoodVb9 = StageTransform.transformVb9(diagnosisPregnantStage) * standard;
        if (foodVb9weight < recomendfoodVb9) {// mg
            result.append(lifeStyleConstant.foodVb9).append(";");
            dietaryFrequencyItems.add(createLifeStyleItem(recordId, lifeStyleConstant.B00516,
                    String.valueOf((int) (foodVb9weight)), mgUnit, lifeStyleConstant.exception, down,
                    recomendfoodVb9 + mgUnit));
        }

        return result.substring(0, result.length() - 1);
    }

    /**
     * 
     * 获取实际一日总能量
     * 
     * @author mnt_zhangjing
     * @return
     */
    public int[] getActualTotalEnergy() {
        int[] result;

        String foodEnergystr = "foodEnergy";

        double totalEnergy = getTotalContent(foodEnergystr, false);

        /**
         * p109
         * 【生活方式问题9】您在孕期常用的烹饪习惯有（可多选）中
         * 若患者选择（勾芡；高糖）任意一个则上面计算结果基础上+50kcal
         */
        List<LifeStyleQuestionAnswerPojo> habit = questionMap.get(lifeStyleConstant.p109);
        if (CollectionUtils.isNotEmpty(habit)) {
            boolean flag = false;// 是否选择（勾芡；高糖）任意一个标识
            for (LifeStyleQuestionAnswerPojo lsqab : habit) {
                if (lifeStyleConstant.O10902.equalsIgnoreCase(lsqab.getOptionId())
                        || lifeStyleConstant.O10906.equalsIgnoreCase(lsqab.getOptionId())) {
                    flag = true;
                }
            }
            if (flag) {
                totalEnergy = totalEnergy + 50;
            }
        }

        totalEnergy = Math.rint(totalEnergy / 10);
        double min = 0;
        if (totalEnergy > 10) {
            min = totalEnergy - 10;
        } else {
            min = 0;
        }
        min = min * 10;
        double max = (totalEnergy + 10) * 10;
        result = new int[] {(int) min, (int) max};
        return result;
    }

    /**
     * 
     * 推荐一日总能量
     * 
     * @author mnt_zhangjing
     * @return
     */
    public int getRecommendEnergy(double custHeight, String pregnantStage) {
        double recommend = StageTransform.transformEnergy(pregnantStage, custHeight);
        return (int) Math.rint(recommend);
    }

    /**
     * 
     * 碳水化合物摄入量
     * 
     * @author mnt_zhangjing
     * @return
     */
    public double getIntakeCbr() {

        String foodCbr = "foodCbr";

        return getTotalContent(foodCbr, false);
    }

    /**
     * 
     * 碳水化合物推荐摄入量
     * 
     * @author mnt_zhangjing
     * @return
     */
    public String getRecommendCbr(double custHeight, String pregnantStage) {

        String result = "";
        double recommendEnergy = StageTransform.transformEnergy(pregnantStage, custHeight);

        double recommendCbr = recommendEnergy * 0.65 / 4;
        DecimalFormat df = new DecimalFormat("0.0");
        if (recommendCbr <= 130) {
            result = "＞130.0g";
        } else {
            result = "130.0~" + df.format(recommendCbr);
        }
        return result;
    }

    /**
     * 
     * 碳水化合物比较结果
     * 
     * @author mnt_zhangjing
     * @return
     */
    public String[] getComparerCbr(double custHeight, String pregnantStage, double intake) {

        String result[] = new String[2];
        double recommendEnergy = StageTransform.transformEnergy(pregnantStage, custHeight);

        double recommendCbr = Math.rint(recommendEnergy * 0.65 / 4);

        result[0] = "";
        result[1] = lifeStyleConstant.normal;
        if (intake < 130) {
            result[0] = down;
            result[1] = lifeStyleConstant.exception;
        }
        if (recommendCbr < 130 && intake > 240) {
            result[0] = up;
            result[1] = lifeStyleConstant.exception;
        }
        if (intake > recommendCbr && recommendCbr > 130) {
            result[0] = up;
            result[1] = lifeStyleConstant.exception;
        }
        return result;
    }

    /**
     * 
     * 蛋白质摄入量
     * 
     * @author mnt_zhangjing
     * @return
     */
    public double getIntakeProtid() {

        String foodProtid = "foodProtid";

        return getTotalContent(foodProtid, false);
    }

    /**
     * 
     * 蛋白质推荐摄入量
     * 
     * @author mnt_zhangjing
     * @return
     */
    public String getRecommendProtid(double custWeight) {
        DecimalFormat df = new DecimalFormat("0.0");

        String result = df.format(custWeight) + "~" + df.format(custWeight * 2);

        return result;
    }

    /**
     * 
     * 蛋白质比较结果
     * 
     * @author mnt_zhangjing
     * @return
     */
    public String[] getComparerProtid(double custWeight, double intake) {

        String result[] = new String[2];

        result[0] = "";
        result[1] = lifeStyleConstant.normal;

        if (intake < custWeight) {
            result[0] = down;
            result[1] = lifeStyleConstant.exception;
        }
        if (intake > custWeight * 2) {
            result[0] = up;
            result[1] = lifeStyleConstant.exception;
        }
        return result;
    }

    /**
     * 
     * 脂肪摄入量
     * 
     * @author mnt_zhangjing
     * @return
     */
    public double getIntakeFat() {

        String foodFat = "foodFat";

        return getTotalContent(foodFat, false);
    }

    /**
     * 
     * 脂肪摄入量推荐摄入量
     * 
     * @author mnt_zhangjing
     * @return
     */
    public String getRecommendFat(double custWeight, String pregnantStage) {
        DecimalFormat df = new DecimalFormat("0.0");
        String result = "";
        double recommendEnergy = StageTransform.transformEnergy(pregnantStage, custWeight);

        result = df.format(recommendEnergy * 0.2 / 9) + "~" + df.format(recommendEnergy * 0.3 / 9);
        return result;
    }

    /**
     * 
     * 碳脂肪比较结果
     * 
     * @author mnt_zhangjing
     * @return
     */
    public String[] getComparerFat(double custWeight, String pregnantStage, double intake) {

        String result[] = new String[2];

        result[0] = "";
        result[1] = lifeStyleConstant.normal;

        double recommendEnergy = StageTransform.transformEnergy(pregnantStage, custWeight);

        if (intake < recommendEnergy * 0.2 / 9) {
            result[0] = down;
            result[1] = lifeStyleConstant.exception;
        }
        if (intake > recommendEnergy * 0.3 / 9) {
            result[0] = up;
            result[1] = lifeStyleConstant.exception;
        }
        return result;
    }

    /**
     * 
     * 获取比较结果
     * 
     * @author mnt_zhangjing
     * @return
     */
    public String[] getComparerRatio(double ratio, double min, double max) {

        String result[] = new String[2];

        result[0] = "";
        result[1] = lifeStyleConstant.normal;

        if (min >= max) {
            if (ratio < min) {
                result[0] = down;
                result[1] = lifeStyleConstant.exception;
            }
            if (ratio > min) {
                result[0] = up;
                result[1] = lifeStyleConstant.exception;
            }
        } else {
            if (ratio < min) {
                result[0] = down;
                result[1] = lifeStyleConstant.exception;
            }
            if (ratio > max) {
                result[0] = up;
                result[1] = lifeStyleConstant.exception;
            }
        }

        return result;
    }

    /**
     * 
     * 优质蛋白质
     * 
     * @author mnt_zhangjing
     * @return
     */
    public double getGoodProtid() {

        String foodFat = "foodProtid";

        return getGoodProtid(foodFat);
    }

    /**
     * 
     * 分析指定的生活方式问题
     * 
     * @author mnt_zhangjing
     * @param explain
     *            说明
     * @param problemId
     *            问题编号
     * @param type
     *            问题类型
     * @param itemCode
     *            项目编号
     */
    public void getLifeStyleItem(String explain, String problemId, String type, String itemCode) {

        List<LifeStyleQuestionAnswerPojo> questions = questionMap.get(problemId);

        StringBuffer result = new StringBuffer();
        if (CollectionUtils.isNotEmpty(questions)) {
            for (LifeStyleQuestionAnswerPojo lsqab : questions) {
                // 多选题
                if (lifeStyleConstant.checkbox.equals(type)) {
                    // 有选项的
                    if (StringUtils.isNotEmpty(lsqab.getOptionId())) {
                        Option option = lifeStyleConstant.getLifeStyleOption(lsqab.getOptionId());
                        if (option != null) {
                            if (option.isException()) {
                                result.append(option.getOptionName()).append(",");
                            }
                        }
                    } else {
                        // 填空（如其他）
                        result.append(lsqab.getAddvalue());
                    }
                }
                // 单选题
                if (lifeStyleConstant.radio.equals(type)) {
                    Option option = lifeStyleConstant.getLifeStyleOption(lsqab.getOptionId());
                    if (option != null) {
                        if (option.isException()) {
                            result.append(option.getOptionName());
                        }
                    }
                }
            }

            if (result.length() > 0) {
                if (",".equals(result.substring(result.length() - 1))) {
                    result.replace(result.length() - 1, result.length(), ";");
                } else {
                    result.append(";");
                }

                dietaryFrequencyItems.add(createLifeStyleItem(recordId, itemCode, explain + result.toString(), "",
                        lifeStyleConstant.exception, "", explain + result.toString(), "", dietary_frequency_type));
            }
        }

    }

    /**
     * 
     * 添加P101 分析结果
     * 
     * @author mnt_zhangjing
     */
    public void addP101Result() {
        getLifeStyleItem("", lifeStyleConstant.p101, lifeStyleConstant.radio, lifeStyleConstant.B00537);
    }

    /**
     * 
     * 添加P102 分析结果
     * 
     * @author mnt_zhangjing
     */
    public void addP102Result() {
        getLifeStyleItem("", lifeStyleConstant.p102, lifeStyleConstant.radio, lifeStyleConstant.B00538);
    }

    /**
     * 
     * 添加P103 分析结果
     * 
     * @author mnt_zhangjing
     */
    public void addP103Result() {
        List<LifeStyleQuestionAnswerPojo> questions = questionMap.get(lifeStyleConstant.p103);

        StringBuffer result = new StringBuffer();
        if (CollectionUtils.isNotEmpty(questions)) {
            for (LifeStyleQuestionAnswerPojo lsqab : questions) {
                // 有选项的
                if (StringUtils.isNotEmpty(lsqab.getOptionId())) {
                    if (lifeStyleConstant.O10301.equals(lsqab.getOptionId())
                            || lifeStyleConstant.O10303.equals(lsqab.getOptionId())
                            || lifeStyleConstant.O10305.equals(lsqab.getOptionId())) {
                        if (lsqab.getAddvalue().length() == 2) {
                            Option option = lifeStyleConstant.getLifeStyleOption(lsqab.getOptionId());
                            result.append(option.getOptionName()).append(":").append(lsqab.getAddvalue()).append(",");
                        }
                    }

                }
            }
        }

        if (result.length() > 0) {
            dietaryFrequencyItems.add(createLifeStyleItem(recordId, lifeStyleConstant.B00539,
                    result.substring(result.length() - 1).toString(), "", lifeStyleConstant.exception, "", result
                            .substring(result.length() - 1).toString(), "", dietary_frequency_type));
        }

    }

    /**
     * 
     * 添加P104 分析结果
     * 
     * @author mnt_zhangjing
     */
    public void addP104Result() {
        getLifeStyleItem("", lifeStyleConstant.p104, lifeStyleConstant.radio, lifeStyleConstant.B00540);
    }

    /**
     * 
     * 添加P105 分析结果
     * 
     * @author mnt_zhangjing
     */
    public void addP105Result() {
        getLifeStyleItem("", lifeStyleConstant.p105, lifeStyleConstant.radio, lifeStyleConstant.B00541);
    }

    /**
     * 
     * 添加P106 分析结果
     * 
     * @author mnt_zhangjing
     */
    public void addP106Result() {
        getLifeStyleItem("", lifeStyleConstant.p106, lifeStyleConstant.radio, lifeStyleConstant.B00542);
    }

    /**
     * 
     * 添加P107 分析结果
     * 
     * @author mnt_zhangjing
     */
    public void addP107Result() {
        getLifeStyleItem("", lifeStyleConstant.p107, lifeStyleConstant.checkbox, lifeStyleConstant.B00543);
    }

    /**
     * 
     * 添加P108 分析结果
     * 
     * @author mnt_zhangjing
     */
    public void addP108Result() {
        getLifeStyleItem("", lifeStyleConstant.p108, lifeStyleConstant.checkbox, lifeStyleConstant.B00544);
    }

    /**
     * 
     * 添加P109 分析结果
     * 
     * @author mnt_zhangjing
     */
    public void addP109Result() {
        getLifeStyleItem("", lifeStyleConstant.p109, lifeStyleConstant.checkbox, lifeStyleConstant.B00545);
    }

    /**
     * 
     * 添加P201 分析结果
     * 
     * @author mnt_zhangjing
     */
    public void addP201Result() {
        getLifeStyleItem("", lifeStyleConstant.p201, lifeStyleConstant.radio, lifeStyleConstant.B00546);
    }

    /**
     * 
     * 添加P202 分析结果
     * 
     * @author mnt_zhangjing
     */
    public void addP202Result() {
        getLifeStyleItem("", lifeStyleConstant.p202, lifeStyleConstant.checkbox, lifeStyleConstant.B00547);
    }

    /**
     * 
     * 添加P204 分析结果
     * 
     * @author mnt_zhangjing
     */
    public void addP204Result() {
        getLifeStyleItem("", lifeStyleConstant.p204, lifeStyleConstant.radio, lifeStyleConstant.B00549);
    }

    /**
     * 
     * 添加P205 分析结果
     * 
     * @author mnt_zhangjing
     */
    public void addP205Result() {
        getLifeStyleItem("", lifeStyleConstant.p205, lifeStyleConstant.checkbox, lifeStyleConstant.B00550);
    }

    /**
     * 
     * 添加P301 分析结果
     * 
     * @author mnt_zhangjing
     */
    public void addP301Result() {
        getLifeStyleItem("", lifeStyleConstant.p301, lifeStyleConstant.radio, lifeStyleConstant.B00551);
    }

    /**
     * 
     * 添加P302 分析结果
     * 
     * @author mnt_zhangjing
     */
    public void addP302Result() {
        getLifeStyleItem("", lifeStyleConstant.p302, lifeStyleConstant.checkbox, lifeStyleConstant.B00552);
    }

    /**
     * 
     * 添加P303 分析结果
     * 
     * @author mnt_zhangjing
     */
    public void addP303Result() {
        getLifeStyleItem("", lifeStyleConstant.p303, lifeStyleConstant.radio, lifeStyleConstant.B00553);
    }

    /**
     * 
     * 添加P401 分析结果
     * 
     * @author mnt_zhangjing
     */
    public void addP401Result() {
        getLifeStyleItem("", lifeStyleConstant.p401, lifeStyleConstant.checkbox, lifeStyleConstant.B00554);
    }

    /**
     * 
     * 添加P501 分析结果
     * 
     * @author mnt_zhangjing
     */
    public void addP501Result() {
        getLifeStyleItem("", lifeStyleConstant.p501, lifeStyleConstant.radio, lifeStyleConstant.B00555);
    }

    /**
     * 
     * 添加P502 分析结果
     * 
     * @author mnt_zhangjing
     */
    public void addP502Result() {
        getLifeStyleItem("", lifeStyleConstant.p502, lifeStyleConstant.radio, lifeStyleConstant.B00556);
    }

    /**
     * 
     * 添加P601 分析结果
     * 
     * @author mnt_zhangjing
     */
    public void addP601Result() {
        getLifeStyleItem("经常会接触到:", lifeStyleConstant.p601, lifeStyleConstant.checkbox, lifeStyleConstant.B00557);
    }

    /**
     * 
     * 添加P602 分析结果
     * 
     * @author mnt_zhangjing
     */
    public void addP602Result() {
        getLifeStyleItem("", lifeStyleConstant.p602, lifeStyleConstant.radio, lifeStyleConstant.B00558);
    }
}

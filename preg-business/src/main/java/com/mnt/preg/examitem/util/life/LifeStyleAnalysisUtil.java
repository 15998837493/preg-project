
package com.mnt.preg.examitem.util.life;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.mnt.preg.examitem.entity.LifeStyleItem;
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
public class LifeStyleAnalysisUtil extends LifeStyleAnalysis {

    private double intakeFat = 0;// 脂肪摄入量

    private double intakeCbr = 0;// 碳水化合物摄入量

    private double intakeProtid = 0;// 蛋白质摄入量

    private String lackingElementstr = ""; // 摄入不足字符串 优质蛋白质处用

    public LifeStyleAnalysisUtil(List<LifeStyleQuestionAnswerPojo> lifeStyleQuestionAnswerBos,
            List<EvaluateFoodLibraryPojo> evaluateFoodLibraryVos, String recordId) {
        super(lifeStyleQuestionAnswerBos, evaluateFoodLibraryVos, recordId);
    }

    /**
     * 
     * 生活方式调查-饮食分析
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem analysisDiet(String recordId) {
        String itemString = getLifeResult(lifeStyleConstant.diet, dietQuestionAnswerBos) + getMeatRatio();
        return createLifeStyleItem(recordId, lifeStyleConstant.diet, itemString);
    }

    /**
     * 
     * 生活方式调查-运动分析
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem analysisSport(String recordId) {
        String itemString = getLifeResult(lifeStyleConstant.sport, sportQuestionAnswerBos);
        return createLifeStyleItem(recordId, lifeStyleConstant.sport, itemString);
    }

    /**
     * 
     * 生活方式调查-睡眠分析
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem analysisSleep(String recordId) {
        String itemString = getLifeResult(lifeStyleConstant.sleep, sleepQuestionAnswerBos);
        return createLifeStyleItem(recordId, lifeStyleConstant.sleep, itemString);
    }

    /**
     * 
     * 生活方式调查-心理分析
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem analysisMental(String recordId) {
        String itemString = getLifeResult(lifeStyleConstant.mental, mentalQuestionAnswerBos);
        return createLifeStyleItem(recordId, lifeStyleConstant.mental, itemString);
    }

    /**
     * 
     * 生活方式调查-烟酒分析
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem analysisDrinking(String recordId) {
        String itemString = getLifeResult(lifeStyleConstant.drinking, drinkingQuestionAnswerBos);
        return createLifeStyleItem(recordId, lifeStyleConstant.drinking, itemString);
    }

    /**
     * 
     * 生活方式调查-环境分析
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem analysisEnvironment(String recordId) {
        String itemString = getLifeResult(lifeStyleConstant.environment, environmentQuestionAnswerBos);
        return createLifeStyleItem(recordId, lifeStyleConstant.environment, itemString);
    }

    /**
     * 
     * 摄入不足元素分析
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem analysisLackingElement(String recordId, String diagnosisPregnantStage) {
        lackingElementstr = getLackingElement(diagnosisPregnantStage);
        return createLifeStyleItem(recordId, lifeStyleConstant.lacking, lackingElementstr, "", "", "", "", "");
    }

    /**
     * 
     * 就餐时间-早餐
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getBreakfastTime(String recordId) {
        String itemString = getEatingTime(lifeStyleConstant.O10301);
        return createLifeStyleItem(recordId, lifeStyleConstant.breakfast, itemString);
    }

    /**
     * 
     * 就餐时间-上午加餐
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getMorningMealTime(String recordId) {
        String itemString = getEatingTime(lifeStyleConstant.O10302);
        return createLifeStyleItem(recordId, lifeStyleConstant.morningMeal, itemString);
    }

    /**
     * 
     * 就餐时间-午餐
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getLunchTime(String recordId) {
        String itemString = getEatingTime(lifeStyleConstant.O10303);
        return createLifeStyleItem(recordId, lifeStyleConstant.lunch, itemString);
    }

    /**
     * 
     * 就餐时间-下午加餐
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getAfternoonSnacksTime(String recordId) {
        String itemString = getEatingTime(lifeStyleConstant.O10304);
        return createLifeStyleItem(recordId, lifeStyleConstant.afternoonSnacks, itemString);
    }

    /**
     * 
     * 就餐时间-晚餐
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getDinnerTime(String recordId) {
        String itemString = getEatingTime(lifeStyleConstant.O10305);
        return createLifeStyleItem(recordId, lifeStyleConstant.dinner, itemString);
    }

    /**
     * 
     * 就餐时间-夜宵
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getSupperTime(String recordId) {
        String itemString = getEatingTime(lifeStyleConstant.O10306);
        return createLifeStyleItem(recordId, lifeStyleConstant.supper, itemString);
    }

    /**
     * 
     * 实际一日总能量
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getActualEnergy(String recordId, BigDecimal custHeight, String pregnantStage) {
        String itemString = getActualTotalEnergy();
        double height = 0;
        if (custHeight != null) {
            height = custHeight.doubleValue() / 100;
        }

        double recommond = getRecommendEnergy(height, pregnantStage);
        String exception = "";
        if (StringUtils.isNotEmpty(itemString)) {
            String[] itemsval = itemString.split("~");
            // 计算最大值未到推荐值：不足
            if (recommond > Double.parseDouble(itemsval[1])) {
                exception = lifeStyleConstant.getLifeStyleItem(lifeStyleConstant.actual_totalEnergy) + lackingResult;
            }
            // 计算最小值超过推荐值：过量
            if (recommond < Double.parseDouble(itemsval[0])) {
                exception = lifeStyleConstant.getLifeStyleItem(lifeStyleConstant.actual_totalEnergy) + excessResult;
            }
        }
        return createLifeStyleItem(recordId, lifeStyleConstant.actual_totalEnergy, itemString, "kacl", "", "",
                exception, String.valueOf((int) recommond));
    }

    /**
     * 
     * 推荐一日总能量
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getRecommendEnergy(String recordId, BigDecimal custHeight, String pregnantStage) {
        double height = 0;
        if (custHeight != null) {
            height = custHeight.doubleValue() / 100;
        }
        String itemString = String.valueOf(getRecommendEnergy(height, pregnantStage));
        return createLifeStyleItem(recordId, lifeStyleConstant.recommend_totalEnergy, itemString, "kacl", "", "", "",
                "");
    }

    /**
     * 
     * 碳水化合物摄入量
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getIntakeCbr(String recordId, BigDecimal custHeight, String pregnantStage) {
        intakeCbr = getIntakeCbr();
        String itemString = "";
        itemString = (int) Math.rint(intakeCbr) + "±5";
        double height = 0;
        if (custHeight != null) {
            height = custHeight.doubleValue() / 100;
        }
        String recommond = getRecommendCbr(height, pregnantStage);
        String result[] = getComparerCbr(height, pregnantStage, intakeCbr);
        return createLifeStyleItem(recordId, lifeStyleConstant.cbr, itemString, "g", result[1], result[0], recommond);
    }

    /**
     * 
     * 蛋白质摄入量
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getIntakeProtid(String recordId, BigDecimal custWeight) {
        intakeProtid = getIntakeProtid();
        String itemString = "";
        itemString = (int) Math.rint(intakeProtid) + "±2";
        double weight = 0;
        if (custWeight != null) {
            weight = custWeight.doubleValue();
        }
        String recommond = getRecommendProtid(weight);
        String result[] = getComparerProtid(weight, intakeProtid);
        return createLifeStyleItem(recordId, lifeStyleConstant.protid, itemString, "g", result[1], result[0], recommond);
    }

    /**
     * 
     * 脂肪摄入量
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getIntakeFat(String recordId, BigDecimal custHeight, String pregnantStage) {
        intakeFat = getIntakeFat();
        String itemString = "";
        itemString = (int) Math.rint(intakeFat) + "±2";
        double height = 0;
        if (custHeight != null) {
            height = custHeight.doubleValue() / 100;
        }
        String recommond = getRecommendFat(height, pregnantStage);
        String result[] = getComparerFat(height, pregnantStage, intakeFat);
        return createLifeStyleItem(recordId, lifeStyleConstant.fat, itemString, "g", result[1], result[0], recommond);
    }

    /**
     * 
     * 碳水化合物供能比
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getIntakeCbrRatio(String recordId) {
        double cbrRatio = 0;
        String itemString = "0%";
        if (intakeCbr + intakeProtid + intakeFat != 0) {
            cbrRatio = intakeCbr * 4 / (intakeCbr * 4 + intakeProtid * 4 + intakeFat * 9) * 100;
            itemString = (int) ((Math.rint(cbrRatio) - 2) < 0 ? 0 : (Math.rint(cbrRatio) - 2)) + "%~"
                    + (int) ((Math.rint(cbrRatio) + 2) > 100 ? 100 : (Math.rint(cbrRatio) + 2)) + "%";
        }

        double min = 50;
        double max = 65;
        String result[] = getComparerRatio(cbrRatio, min, max);
        return createLifeStyleItem(recordId, lifeStyleConstant.cbrratio, itemString, "", result[1], result[0],
                (int) min + "%~"
                        + (int) max + "%");
    }

    /**
     * 
     * 蛋白质供能比
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getIntakeProtidRatio(String recordId) {
        double protidRatio = 0;
        String itemString = "0%";
        if (intakeCbr + intakeProtid + intakeFat != 0) {
            protidRatio = intakeProtid * 4 / (intakeCbr * 4 + intakeProtid * 4 + intakeFat * 9) * 100;
            itemString = (int) ((Math.rint(protidRatio) - 2) < 0 ? 0 : (Math.rint(protidRatio) - 2)) + "%~"
                    + (int) ((Math.rint(protidRatio) + 2) > 100 ? 100 : (Math.rint(protidRatio) + 2)) + "%";
        }
        double min = 12;
        double max = 20;
        String result[] = getComparerRatio(protidRatio, min, max);
        return createLifeStyleItem(recordId, lifeStyleConstant.protidratio, itemString, "", result[1], result[0],
                (int) min
                        + "%~" + (int) max + "%");
    }

    /**
     * 
     * 脂肪供能比
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getIntakeFatRatio(String recordId) {
        double fatRatio = 0;
        String itemString = "0%";
        if (intakeCbr + intakeProtid + intakeFat != 0) {
            fatRatio = intakeFat * 9 / (intakeCbr * 4 + intakeProtid * 4 + intakeFat * 9) * 100;
            itemString = (int) ((Math.rint(fatRatio) - 2) < 0 ? 0 : (Math.rint(fatRatio) - 2)) + "%~"
                    + (int) ((Math.rint(fatRatio) + 2) > 100 ? 100 : (Math.rint(fatRatio) + 2)) + "%";
        }
        double min = 20;
        double max = 30;
        String result[] = getComparerRatio(fatRatio, min, max);
        return createLifeStyleItem(recordId, lifeStyleConstant.fatratio, itemString, "", result[1], result[0],
                (int) min + "%~"
                        + (int) max + "%");
    }

    /**
     * 
     * 谷类
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getIntakeCereal(String recordId, String pregnantStage) {
        double cereal = getCereal();
        String itemString = String.valueOf((int) cereal);
        double[] recom = StageTransform.transformCereal(pregnantStage);
        double min = recom[0];
        double max = recom[1];
        String result[] = getComparerRatio(cereal, min, max);
        return createLifeStyleItem(recordId, lifeStyleConstant.cereal, itemString, "g", result[1], result[0], (int) min
                + "~"
                + (int) max);
    }

    /**
     * 
     * 薯类
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getIntakePotatoes(String recordId, String pregnantStage) {
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
        return createLifeStyleItem(recordId, lifeStyleConstant.potatoes, itemString, "g", result[1], result[0],
                refValue);
    }

    /**
     * 
     * 蔬菜
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getIntakeVeg(String recordId, String pregnantStage) {
        double vege = getVegetablesWeight();
        String itemString = String.valueOf((int) vege);
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
        return createLifeStyleItem(recordId, lifeStyleConstant.veg, itemString, "g", result[1], result[0], refValue);
    }

    /**
     * 
     * 水果
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getIntakeFruit(String recordId, String pregnantStage) {
        double fruit = getFruit();
        String itemString = String.valueOf((int) fruit);
        double[] recom = StageTransform.transformFruit(pregnantStage);
        double min = recom[0];
        double max = recom[1];
        String result[] = getComparerRatio(fruit, min, max);
        return createLifeStyleItem(recordId, lifeStyleConstant.fruit, itemString, "g", result[1], result[0], (int) min
                + "~"
                + (int) max);
    }

    /**
     * 
     * 鱼肉蛋
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getIntakeFme(String recordId, String pregnantStage) {
        double fme = getMeatWeight();
        String itemString = String.valueOf((int) fme);
        double[] recom = StageTransform.transformFME(pregnantStage);
        double min = recom[0];
        double max = recom[1];
        String result[] = getComparerRatio(fme, min, max);
        return createLifeStyleItem(recordId, lifeStyleConstant.fme, itemString, "g", result[1], result[0], (int) min
                + "~"
                + (int) max);
    }

    /**
     * 
     * 乳类
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getIntakeMilk(String recordId, String pregnantStage) {
        double milk = getAMilk() + getTMilk();
        String itemString = String.valueOf((int) milk);
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
        return createLifeStyleItem(recordId, lifeStyleConstant.milk, itemString, "g", result[1], result[0], refValue);
    }

    /**
     * 
     * 豆类
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getIntakeBean(String recordId, String pregnantStage) {
        double bean = getBeans();
        String itemString = String.valueOf((int) bean);
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
        return createLifeStyleItem(recordId, lifeStyleConstant.bean, itemString, "g", result[1], result[0], refValue);
    }

    /**
     * 
     * 坚果类
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getIntakeNut(String recordId, String pregnantStage) {
        double nut = getNut();
        String itemString = String.valueOf((int) nut);
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
        return createLifeStyleItem(recordId, lifeStyleConstant.nut, itemString, "g", result[1], result[0], refValue);
    }

    /**
     * 
     * 水
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getIntakeWater(String recordId, String pregnantStage) {
        double water = getWater();
        String itemString = String.valueOf((int) water);
        double[] recom = StageTransform.transformWater(pregnantStage);
        double min = recom[0];
        double max = recom[1];
        String refValue = "";
        if (min >= max) {
            refValue = String.valueOf((int) min);
        } else {
            refValue = (int) min + "~" + (int) max;
        }
        String result[] = getComparerRatio(water, min, max);
        return createLifeStyleItem(recordId, lifeStyleConstant.water, itemString, "ml", result[1], result[0], refValue);
    }

    /**
     * 
     * 粗杂粮
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getIntakeCZL(String recordId) {
        String itemString = coarseGrains;
        double ratio = 0;
        ratio = Double.parseDouble(coarseGrains.substring(0, coarseGrains.length() - 1)) / 100;

        String resultString = ratio < 0.3 ? down : "";
        return createLifeStyleItem(recordId, lifeStyleConstant.czl, itemString, "", lifeStyleConstant.exception,
                resultString, lifeStyleConstant.getLifeStyleItem(lifeStyleConstant.czl) + ":" + itemString, "30%");
    }

    /**
     * 
     * 绿叶蔬菜
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getIntakesVegetables(String recordId) {
        String itemString = glv;
        double ratio = 0;
        ratio = (double) (Double.parseDouble(glv.substring(0, glv.length() - 1)) / 100);

        String resultString = ratio < 0.5 ? "↓" : "";
        return createLifeStyleItem(recordId, lifeStyleConstant.vegetables, itemString, "", lifeStyleConstant.exception,
                resultString, lifeStyleConstant.getLifeStyleItem(lifeStyleConstant.vegetables) + ":" + itemString,
                "50%");
    }

    /**
     * 
     * 优质蛋白质
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getIntakesGoodProtid(String recordId) {
        double protid = getGoodProtid();
        String itemString = "";
        itemString = (int) protid + "±2";
        String compare = "";
        String result = "";
        String exceptionName = "";
        if (StringUtils.isNotEmpty(lackingElementstr)) {
            if (lackingElementstr.contains(lifeStyleConstant.foodProtid)) {
                compare = down;
                result = lifeStyleConstant.exception;
                exceptionName = lackingResult;
            }
        }

        return createLifeStyleItem(recordId, lifeStyleConstant.goodProtid, itemString, "g", result, compare,
                exceptionName, "");
    }

    /**
     * 
     * 动物内脏
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getIntakeOrgans(String recordId) {
        String itemString = String.valueOf((int) getOrgans());
        return createLifeStyleItem(recordId, lifeStyleConstant.organs, itemString, "g", "", "", "");
    }

    /**
     * 
     * 用油量
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getIntakeOil(String recordId) {
        double oil = getOilWeight();
        String itemString = "";
        itemString = (int) oil + "";
        return createLifeStyleItem(recordId, lifeStyleConstant.oils, itemString, "g", "", "", "");
    }

    /**
     * 
     * 海参
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getIntakeHS(String recordId) {
        String itemString = hs;
        return createLifeStyleItem(recordId, lifeStyleConstant.hs, itemString, "g", "", "", "");
    }

    /**
     * 
     * 燕窝
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getIntakeYW(String recordId) {
        String itemString = yw;
        return createLifeStyleItem(recordId, lifeStyleConstant.yw, itemString, "g", "", "", "");
    }

    /**
     * 
     * 蜂蜜
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getIntakeFM(String recordId) {
        String itemString = fm;
        return createLifeStyleItem(recordId, lifeStyleConstant.fm, itemString, "g", "", "", "");
    }

    /**
     * 
     * 饼干点心
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getIntakeBGDX(String recordId) {
        String itemString = bgdx;
        return createLifeStyleItem(recordId, lifeStyleConstant.bgdx, itemString, "g", "", "", "");
    }

    /**
     * 
     * 蛋糕
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getIntakeDG(String recordId) {
        String itemString = dg;
        return createLifeStyleItem(recordId, lifeStyleConstant.dg, itemString, "g", "", "", "");
    }

    /**
     * 
     * 起酥面包
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getIntakeQSMB(String recordId) {
        String itemString = qsmb;
        return createLifeStyleItem(recordId, lifeStyleConstant.qsmb, itemString, "g", "", "", "");
    }

    /**
     * 
     * 海苔
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getIntakeHT(String recordId) {
        String itemString = ht;
        return createLifeStyleItem(recordId, lifeStyleConstant.ht, itemString, "g", "", "", "");
    }

    /**
     * 
     * 糖果
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getIntakeTG(String recordId) {
        String itemString = tg;
        return createLifeStyleItem(recordId, lifeStyleConstant.tg, itemString, "g", "", "", "");
    }

    /**
     * 
     * 蜜饯
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getIntakeMJ(String recordId) {
        String itemString = mj;
        return createLifeStyleItem(recordId, lifeStyleConstant.mj, itemString, "g", "", "", "");
    }

    /**
     * 
     * 巧克力
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getIntakeQKL(String recordId) {
        String itemString = qkl;
        return createLifeStyleItem(recordId, lifeStyleConstant.qkl, itemString, "g", "", "", "");
    }

    /**
     * 
     * 碳酸饮料
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getIntakeTSYL(String recordId) {
        String itemString = tsyl;
        return createLifeStyleItem(recordId, lifeStyleConstant.tsyl, itemString, "g", "", "", "");
    }

    /**
     * 
     * 冷饮雪糕
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getIntakeLYXG(String recordId) {
        String itemString = lyxg;
        return createLifeStyleItem(recordId, lifeStyleConstant.lyxg, itemString, "g", "", "", "");
    }

    /**
     * 
     * 膨化食品
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getIntakePHSP(String recordId) {
        String itemString = phsp;
        return createLifeStyleItem(recordId, lifeStyleConstant.phsp, itemString, "g", "", "", "");
    }

    /**
     * 
     * 菌类
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getIntakeFungus(String recordId) {
        ResultEntity entity = getVegetablesClass();
        double ratio = 0;
        ratio = (double) (Double.parseDouble(fungus.substring(0, fungus.length() - 1)) / 100);
        String itemString = String.valueOf((int) (Math.rint(entity.getWeight() * ratio)));
        return createLifeStyleItem(recordId, lifeStyleConstant.fungus, itemString, "g", "", "", "");
    }

    /**
     * 
     * 藻类
     * 
     * @author mnt_zhangjing
     * @param recordId
     * @return LifeStyleItem
     */
    public LifeStyleItem getIntakeAlgae(String recordId) {
        ResultEntity entity = getVegetablesClass();
        double ratio = 0;
        ratio = (double) (Double.parseDouble(algae.substring(0, algae.length() - 1)) / 100);
        String itemString = String.valueOf((int) (Math.rint(entity.getWeight() * ratio)));
        return createLifeStyleItem(recordId, lifeStyleConstant.algae, itemString, "g", "", "", "");
    }
}

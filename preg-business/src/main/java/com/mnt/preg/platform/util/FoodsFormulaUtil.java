/**
 * 
 */

package com.mnt.preg.platform.util;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.math.NumberRange;
import org.springframework.util.StringUtils;

import com.mnt.health.utils.maths.DecimalCalculate;
import com.mnt.preg.examitem.util.DietaryReviewPregnancyDRIs;
import com.mnt.preg.examitem.util.PregnancyDRIs;

/**
 * 
 * 计算公式单元类
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2014-11-18 zy 初版
 */
public class FoodsFormulaUtil {

    /**
     * 可食用部分参数范围
     */
    private static NumberRange foodEsculentRange = new NumberRange(0, 100);

    /**
     * 计算公式：单个食物中某营养素含量=食品重量*(可食部/100)*(每100g食品中该营养素含量/100)
     * 
     * @param foodAmount
     *            食品重量
     * @param foodEsculent
     *            可食部数据库存储为0-100，需要换算成百分比
     * @param yyshl
     *            每100g食品中该营养素含量
     * @return double
     */
    public static double jsFoodYYSHL(int foodAmount, int foodEsculent, double yyshl) {
        if (!foodEsculentRange.containsNumber(foodEsculent)) {
            throw new IllegalArgumentException("foodEsculent超出范围");
        }
        if (foodAmount < 0) {
            throw new IllegalArgumentException("foodAmount不能是负数");
        }
        if (foodAmount == 0) {
            return 0;
        }
        if (foodEsculent == 0) {
            return 0;
        }
        if (yyshl == 0) {
            return 0;
        }
        // 每克食品中营养素含量
        double v1 = DecimalCalculate.div(yyshl, 100, 2);
        // 可食部分百分比=可食部分/100
        double v2 = DecimalCalculate.div(foodEsculent, 100, 2);
        // 可食部分的重量=食物重量*可食部分百分比
        double v3 = DecimalCalculate.mul(foodAmount, v2);
        // 单个食物中某营养素含量=每克食品中营养素含量*可食部分的重量
        return DecimalCalculate.mul(v1, v3);
    }

    /**
     * BMI值 计算公式：BMI=体重（KG）/身高（M²）
     * 
     * @param weight
     *            体重(kg)
     * @param height
     *            身高(cm)
     * @return
     */
    public static double getBmi(double weight, double height) {
        // 身高m->换算成cm
        double heightM = DecimalCalculate.div(height, 100);
        double heightSQR = DecimalCalculate.mul(heightM, heightM);
        BigDecimal b1 = new BigDecimal(Double.toString(weight));
        BigDecimal b2 = new BigDecimal(Double.toString(heightSQR));
        // return DecimalCalculate.div(weight, heightSQR, 2);
        return b1.divide(b2, 1, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 日摄入标准值(旧版计算热量)
     * 
     * @param age
     *            年龄
     * @param weight
     *            体重
     * @param sexEnum
     *            性别
     * @param levelEnum
     *            体力等级
     * @return double
     */
    public static double getEdayStandardEnergy(int age, double height, double weight, String sexEnum, String levelEnum) {
        // 验证参数合法性
        if (sexEnum == null) {
            throw new NullPointerException("性别不能为空");
        }
        if (levelEnum == null) {
            throw new NullPointerException("体力水平不能为空");
        }
        double bmr = 0;
        // 计算BMI值
        double bmi = getBmi(weight, height);
        // 计算BMR值
        if (age >= 0 && age <= 17) {
            if (sexEnum.equals("female")) {// 女
                bmr = DecimalCalculate.mul(51, DecimalCalculate.sub(height, 107)) + 3118;
            }
            if (sexEnum.equals("male")) {// 男
                bmr = DecimalCalculate.mul(73, DecimalCalculate.sub(height, 105)) + 2721;
            }
        }
        if (age >= 18 && age <= 29) {
            if (sexEnum.equals("female")) {// 女
                bmr = DecimalCalculate.mul(62, DecimalCalculate.sub(height, 107)) + 2036;
            }
            if (sexEnum.equals("male")) {// 男
                bmr = DecimalCalculate.mul(63, DecimalCalculate.sub(height, 105)) + 2896;
            }
        }
        if (age >= 30 && age <= 59) {
            if (sexEnum.equals("female")) {// 女
                bmr = DecimalCalculate.mul(34, DecimalCalculate.sub(height, 107)) + 3538;
            }
            if (sexEnum.equals("male")) {// 男
                bmr = DecimalCalculate.mul(48, DecimalCalculate.sub(height, 105)) + 3653;
            }
        }
        if (age >= 60) {
            if (sexEnum.equals("female")) {// 女
                bmr = DecimalCalculate.mul(44, DecimalCalculate.sub(height, 107)) + 2491;
            }
            if (sexEnum.equals("male")) {// 男
                bmr = DecimalCalculate.mul(56, DecimalCalculate.sub(height, 105)) + 2035;
            }
        }

        // 计算日摄入量
        double v1 = DecimalCalculate.mul(bmr, 0.95);
        double v2 = 0;
        if (bmi < 25) {
            if (levelEnum.equals("st_light")) {
                if (sexEnum.equals("female")) {// 女
                    v2 = DecimalCalculate.mul(v1, 1.56);
                }
                if (sexEnum.equals("male")) {// 男
                    v2 = DecimalCalculate.mul(v1, 1.55);
                }
            }
            if (levelEnum.equals("st_medium")) {
                if (sexEnum.equals("female")) {// 女
                    v2 = DecimalCalculate.mul(v1, 1.64);
                }
                if (sexEnum.equals("male")) {// 男
                    v2 = DecimalCalculate.mul(v1, 1.78);
                }
            }
            if (levelEnum.equals("st_weight")) {
                if (sexEnum.equals("female")) {// 女
                    v2 = DecimalCalculate.mul(v1, 1.82);
                }
                if (sexEnum.equals("male")) {// 男
                    v2 = DecimalCalculate.mul(v1, 2.10);
                }
            }
        } else {
            if (levelEnum.equals("st_light")) {
                if (sexEnum.equals("female")) {// 女
                    v2 = DecimalCalculate.mul(v1, 1.3);
                }
                if (sexEnum.equals("male")) {// 男
                    v2 = DecimalCalculate.mul(v1, 1.3);
                }
            }
            if (levelEnum.equals("st_medium")) {
                if (sexEnum.equals("female")) {// 女
                    v2 = DecimalCalculate.mul(v1, 1.5);
                }
                if (sexEnum.equals("male")) {// 男
                    v2 = DecimalCalculate.mul(v1, 1.5);
                }
            }
            if (levelEnum.equals("st_weight")) {
                if (sexEnum.equals("female")) {// 女
                    v2 = DecimalCalculate.mul(v1, 1.8);
                }
                if (sexEnum.equals("male")) {// 男
                    v2 = DecimalCalculate.mul(v1, 1.8);
                }
            }
        }

        return DecimalCalculate.div(v2, 4.18, 2);
    }

    /**
     * 日摄入标准值
     * 
     * @param height
     *            身高
     * @param weight
     *            体重
     * @param sexEnum
     *            性别
     * @param levelEnum
     *            体力等级
     * @return double
     */
    public static double getEnergy(double height, double weight, String sexEnum, String levelEnum) {
        if (StringUtils.isEmpty(levelEnum)) {
            levelEnum = "st_light";
        }
        double result = 0;
        // 验证参数合法性
        if (sexEnum == null) {
            throw new NullPointerException("性别不能为空");
        }
        if (levelEnum == null) {
            throw new NullPointerException("体力水平不能为空");
        }
        // 计算BMI值
        double bmi = getBmi(weight, height);
        // 标准体重每公斤千卡数
        int value = getPerEnergy(bmi);
        // 计算千卡数
        double sumEnergy = 0;
        if ("male".equals(sexEnum)) {
            if (height >= 150) {
                sumEnergy = DecimalCalculate.mul(value, DecimalCalculate.sub(height, 105));
            } else {
                sumEnergy = DecimalCalculate.mul(value, DecimalCalculate.sub(height, 100));
            }
        }
        if ("female".equals(sexEnum)) {
            if (height >= 150) {
                sumEnergy = DecimalCalculate.mul(value, DecimalCalculate.mul(0.85, DecimalCalculate.sub(height, 100)));
            } else {
                sumEnergy = DecimalCalculate.mul(value, DecimalCalculate.sub(height, 100));
            }
        }
        if (levelEnum.equals("st_light")) {
            result = sumEnergy;
        }
        if (levelEnum.equals("st_medium")) {
            result = DecimalCalculate.add(sumEnergy, DecimalCalculate.mul(weight, 5));
        }
        if (levelEnum.equals("st_weight")) {
            result = DecimalCalculate.add(sumEnergy, DecimalCalculate.mul(weight, 8));
        }
        return result;
    }

    /**
     * 计算标准体重每公斤千卡数
     * 
     * @author zcq
     * @param bmi
     * @return value
     */
    public static int getPerEnergy(double bmi) {
        int value = 0;
        if (bmi < 16) {
            value = 40;
        }
        if (bmi >= 16 && bmi < 17) {
            value = 35;
        }
        if (bmi >= 17 && bmi < 18) {
            value = 34;
        }
        if (bmi >= 18 && bmi < 19) {
            value = 33;
        }
        if (bmi >= 19 && bmi < 20) {
            value = 32;
        }
        if (bmi >= 20 && bmi < 21) {
            value = 31;
        }
        if (bmi >= 21 && bmi < 22) {
            value = 30;
        }
        if (bmi >= 22 && bmi < 23) {
            value = 29;
        }
        if (bmi >= 23 && bmi < 24) {
            value = 28;
        }
        if (bmi >= 24 && bmi < 25) {
            value = 27;
        }
        if (bmi >= 25 && bmi < 26) {
            value = 26;
        }
        if (bmi >= 26 && bmi < 27) {
            value = 25;
        }
        if (bmi >= 27 && bmi < 28) {
            value = 24;
        }
        if (bmi >= 28 && bmi < 29) {
            value = 23;
        }
        if (bmi >= 29 && bmi < 30) {
            value = 22;
        }
        if (bmi >= 30) {
            value = 20;
        }
        return value;
    }

    /**
     * 通过标准能量获取11种膳食结构标准值
     * 
     * @param stenergy
     *            标准能量值
     * @return SpeciesStandardVal
     */
    public static SpeciesStandardVal getSpeciesVal(int stenergy) {
        return new SpeciesStandardVal(stenergy);
    }

    /**
     * 计算DRI值
     * 
     * @param age
     *            年龄
     * @param sex
     *            性别
     * @param sl
     *            身体水平
     * @param isGravida
     *            是否孕妇
     * @return DRIs
     */
    public static DRIs getDRIs(int age, String sex, String sl) {
        return new DRIs(age, sex, sl);
    }

    /**
     * 计算孕期DRI值
     * 
     * @author zcq
     * @param pregnancy
     * @return
     */
    public static PregnancyDRIs getPregnancyDRIs(String pregnancy) {
        return new PregnancyDRIs(pregnancy);
    }

    /**
     * 计算孕期DRI值(24小时膳食回顾用)
     * 
     * @author scd
     * @param pregnancy
     * @return
     */
    public static DietaryReviewPregnancyDRIs getDietaryReviewPregnancyDRIs(String pregnancy, BigDecimal height,
            BigDecimal weight) {
        return new DietaryReviewPregnancyDRIs(pregnancy, height, weight);
    }

    /**
     * 计算蛋白质推荐摄入量
     * 
     * @param diId
     *            所患有疾病
     * @param fsiId
     *            饮食结构比例类型
     * @param weiht
     *            会员体重
     * @return double 蛋白质推荐摄入量
     * 
     */
    public static Double getProtidRecom(String diId, String fsiId, double weight) {
        // 有肾病或痛风其一者
        if ("19".equals(diId) || "24".equals(diId)) {
            return DecimalCalculate.mul(weight, 0.8);
        }
        // 无肾病和痛风 而且饮食结构比例类型为145或343
        else if ("1".equals(fsiId) || "2".equals(fsiId)) {
            return DecimalCalculate.mul(weight, 2);
        }
        // 无肾病和痛风 而且饮食结构比例类型为433或523
        else if ("3".equals(fsiId) || "4".equals(fsiId)) {
            return DecimalCalculate.mul(weight, 1.5);
        }
        return null;
    }

    /**
     * 过滤筛选出一个符合条件的膳食结构
     * 
     * @author zcq
     * @param structList
     *            经过疾病筛选后的膳食结构列表
     * @param weight
     *            体重
     * @param height
     *            身高
     * @param sex
     *            性别
     * @param waistline
     *            腰围
     * @return struct
     *         筛选出的结构ID
     */
    public static String getFoodStruct(List<String> structList, double weight, double height, String sex,
            double waistline) {
        String struct = "0";// 返回最终选择的膳食结构ID，若无返回 0：代表经过过滤后没有符合条件的膳食结构！

        String mostLowGl = "2";// 极低GL膳食结构ID 343
        String lowGl = "3";// 低GL膳食结构ID 433
        String equGl = "4";// 平衡膳食结构ID 523
        // String fatGl = "1";// 脂肪控制膳食结构ID 622

        double bmi = getBmi(weight, height);
        if ("male".equals(sex)) {
            if ((bmi >= 28 || waistline >= 95) && structList.contains(mostLowGl)) {
                return mostLowGl;
            }
            if ((bmi >= 24 || waistline >= 85) && structList.contains(lowGl)) {
                return lowGl;
            }
            if ((waistline < 85) && structList.contains(equGl)) {
                return equGl;
            }
        }
        if ("female".equals(sex)) {
            if ((bmi >= 28 || waistline >= 90) && structList.contains(mostLowGl)) {
                return mostLowGl;
            }
            if ((bmi >= 24 || waistline >= 80) && structList.contains(lowGl)) {
                return lowGl;
            }
            if ((waistline < 80) && structList.contains(equGl)) {
                return equGl;
            }
        }
        return struct;
    }

    /**
     * 膳食频率
     * 
     * @author zcq
     * @param standardEnergy
     * @param foodType
     * @return
     */
    public static String getFrequencyResult(int standardEnergy, String foodType, double intake) {
        String result = "";
        if (standardEnergy <= 1100) {
            if ("water".equals(foodType)) {
                result = getFrequencyResult(new int[] {1200, 1500, 1700, 2000}, intake);
            } else if ("gushuzadou".equals(foodType)) {
                result = getFrequencyResult(new int[] {60, 75, 95, 125}, intake);
            } else if ("shucai".equals(foodType)) {
                result = getFrequencyResult(new int[] {125, 175, 225, 250}, intake);
            } else if ("shuiguo".equals(foodType)) {
                result = getFrequencyResult(new int[] {100, 125, 175, 250}, intake);
            } else if ("qinchu".equals(foodType)) {
                result = getFrequencyResult(new int[] {5, 10, 20, 25}, intake);
            } else if ("egg".equals(foodType)) {
                result = getFrequencyResult(new int[] {5, 15, 22, 25}, intake);
            } else if ("shuichanpin".equals(foodType)) {
                result = getFrequencyResult(new int[] {5, 10, 18, 20}, intake);
            } else if ("ruzhipin".equals(foodType)) {
                result = getFrequencyResult(new int[] {200, 400, 550, 600}, intake);
            } else if ("dadou".equals(foodType)) {
                result = getFrequencyResult(new int[] {5, 10, 15}, intake);
            } else if ("jianguo".equals(foodType)) {
                result = getFrequencyResult(new int[] {5, 10, 15}, intake);
            }
        } else if (standardEnergy > 1100 && standardEnergy <= 1300) {
            if ("water".equals(foodType)) {
                result = getFrequencyResult(new int[] {1200, 1500, 1700, 2000}, intake);
            } else if ("gushuzadou".equals(foodType)) {
                result = getFrequencyResult(new int[] {85, 90, 120, 150}, intake);
            } else if ("shucai".equals(foodType)) {
                result = getFrequencyResult(new int[] {200, 225, 275, 300}, intake);
            } else if ("shuiguo".equals(foodType)) {
                result = getFrequencyResult(new int[] {100, 125, 175, 250}, intake);
            } else if ("qinchu".equals(foodType)) {
                result = getFrequencyResult(new int[] {15, 20, 30, 40}, intake);
            } else if ("egg".equals(foodType)) {
                result = getFrequencyResult(new int[] {5, 15, 30, 40}, intake);
            } else if ("shuichanpin".equals(foodType)) {
                result = getFrequencyResult(new int[] {5, 15, 25, 35}, intake);
            } else if ("ruzhipin".equals(foodType)) {
                result = getFrequencyResult(new int[] {200, 400, 550, 600}, intake);
            } else if ("dadou".equals(foodType)) {
                result = getFrequencyResult(new int[] {5, 10, 15}, intake);
            } else if ("jianguo".equals(foodType)) {
                result = getFrequencyResult(new int[] {5, 10, 15}, intake);
            }
        } else if (standardEnergy > 1300 && standardEnergy <= 1500) {
            if ("water".equals(foodType)) {
                result = getFrequencyResult(new int[] {1200, 1500, 1700, 2000}, intake);
            } else if ("gushuzadou".equals(foodType)) {
                result = getFrequencyResult(new int[] {100, 125, 175, 200}, intake);
            } else if ("shucai".equals(foodType)) {
                result = getFrequencyResult(new int[] {250, 300, 500, 600}, intake);
            } else if ("shuiguo".equals(foodType)) {
                result = getFrequencyResult(new int[] {100, 125, 175, 250}, intake);
            } else if ("qinchu".equals(foodType)) {
                result = getFrequencyResult(new int[] {25, 40, 75, 100}, intake);
            } else if ("egg".equals(foodType)) {
                result = getFrequencyResult(new int[] {5, 15, 30, 40}, intake);
            } else if ("shuichanpin".equals(foodType)) {
                result = getFrequencyResult(new int[] {20, 40, 75, 100}, intake);
            } else if ("ruzhipin".equals(foodType)) {
                result = getFrequencyResult(new int[] {200, 325, 450, 500}, intake);
            } else if ("dadou".equals(foodType)) {
                result = getFrequencyResult(new int[] {5, 10, 15}, intake);
            } else if ("jianguo".equals(foodType)) {
                result = getFrequencyResult(new int[] {5, 10, 15}, intake);
            }
        } else if (standardEnergy > 1500 && standardEnergy <= 1700) {
            if ("water".equals(foodType)) {
                result = getFrequencyResult(new int[] {1200, 1500, 1700, 2000}, intake);
            } else if ("gushuzadou".equals(foodType)) {
                result = getFrequencyResult(new int[] {150, 175, 225, 250}, intake);
            } else if ("shucai".equals(foodType)) {
                result = getFrequencyResult(new int[] {250, 300, 500, 600}, intake);
            } else if ("shuiguo".equals(foodType)) {
                result = getFrequencyResult(new int[] {100, 200, 350, 400}, intake);
            } else if ("qinchu".equals(foodType)) {
                result = getFrequencyResult(new int[] {25, 40, 75, 100}, intake);
            } else if ("egg".equals(foodType)) {
                result = getFrequencyResult(new int[] {20, 40, 50, 100}, intake);
            } else if ("shuichanpin".equals(foodType)) {
                result = getFrequencyResult(new int[] {20, 40, 75, 100}, intake);
            } else if ("ruzhipin".equals(foodType)) {
                result = getFrequencyResult(new int[] {100, 200, 400, 500}, intake);
            } else if ("dadou".equals(foodType)) {
                result = getFrequencyResult(new int[] {5, 10, 15}, intake);
            } else if ("jianguo".equals(foodType)) {
                result = getFrequencyResult(new int[] {5, 10, 15}, intake);
            }
        } else if (standardEnergy > 1700 && standardEnergy <= 1900) {
            if ("water".equals(foodType)) {
                result = getFrequencyResult(new int[] {1200, 1500, 1700, 2000}, intake);
            } else if ("gushuzadou".equals(foodType)) {
                result = getFrequencyResult(new int[] {200, 210, 230, 250}, intake);
            } else if ("shucai".equals(foodType)) {
                result = getFrequencyResult(new int[] {250, 300, 500, 600}, intake);
            } else if ("shuiguo".equals(foodType)) {
                result = getFrequencyResult(new int[] {100, 200, 350, 400}, intake);
            } else if ("qinchu".equals(foodType)) {
                result = getFrequencyResult(new int[] {25, 40, 75, 100}, intake);
            } else if ("egg".equals(foodType)) {
                result = getFrequencyResult(new int[] {20, 40, 50, 100}, intake);
            } else if ("shuichanpin".equals(foodType)) {
                result = getFrequencyResult(new int[] {20, 40, 75, 100}, intake);
            } else if ("ruzhipin".equals(foodType)) {
                result = getFrequencyResult(new int[] {100, 200, 400, 500}, intake);
            } else if ("dadou".equals(foodType)) {
                result = getFrequencyResult(new int[] {5, 10, 15}, intake);
            } else if ("jianguo".equals(foodType)) {
                result = getFrequencyResult(new int[] {5, 10, 15}, intake);
            }
        } else if (standardEnergy > 1900 && standardEnergy <= 2100) {
            if ("water".equals(foodType)) {
                result = getFrequencyResult(new int[] {1200, 1500, 1700, 2000}, intake);
            } else if ("gushuzadou".equals(foodType)) {
                result = getFrequencyResult(new int[] {225, 230, 270, 275}, intake);
            } else if ("shucai".equals(foodType)) {
                result = getFrequencyResult(new int[] {250, 300, 500, 600}, intake);
            } else if ("shuiguo".equals(foodType)) {
                result = getFrequencyResult(new int[] {100, 200, 350, 400}, intake);
            } else if ("qinchu".equals(foodType)) {
                result = getFrequencyResult(new int[] {25, 40, 75, 100}, intake);
            } else if ("egg".equals(foodType)) {
                result = getFrequencyResult(new int[] {20, 40, 50, 100}, intake);
            } else if ("shuichanpin".equals(foodType)) {
                result = getFrequencyResult(new int[] {20, 40, 75, 100}, intake);
            } else if ("ruzhipin".equals(foodType)) {
                result = getFrequencyResult(new int[] {100, 200, 400, 500}, intake);
            } else if ("dadou".equals(foodType)) {
                result = getFrequencyResult(new int[] {5, 10, 15}, intake);
            } else if ("jianguo".equals(foodType)) {
                result = getFrequencyResult(new int[] {5, 10, 15}, intake);
            }
        } else if (standardEnergy > 2100 && standardEnergy <= 2300) {
            if ("water".equals(foodType)) {
                result = getFrequencyResult(new int[] {1200, 1500, 1700, 2000}, intake);
            } else if ("gushuzadou".equals(foodType)) {
                result = getFrequencyResult(new int[] {250, 260, 290, 300}, intake);
            } else if ("shucai".equals(foodType)) {
                result = getFrequencyResult(new int[] {250, 300, 500, 600}, intake);
            } else if ("shuiguo".equals(foodType)) {
                result = getFrequencyResult(new int[] {100, 200, 350, 400}, intake);
            } else if ("qinchu".equals(foodType)) {
                result = getFrequencyResult(new int[] {25, 40, 75, 100}, intake);
            } else if ("egg".equals(foodType)) {
                result = getFrequencyResult(new int[] {20, 40, 50, 100}, intake);
            } else if ("shuichanpin".equals(foodType)) {
                result = getFrequencyResult(new int[] {20, 40, 75, 100}, intake);
            } else if ("ruzhipin".equals(foodType)) {
                result = getFrequencyResult(new int[] {100, 200, 400, 500}, intake);
            } else if ("dadou".equals(foodType)) {
                result = getFrequencyResult(new int[] {20, 35, 50}, intake);
            } else if ("jianguo".equals(foodType)) {
                result = getFrequencyResult(new int[] {5, 10, 15}, intake);
            }
        } else if (standardEnergy > 2300 && standardEnergy <= 2500) {
            if ("water".equals(foodType)) {
                result = getFrequencyResult(new int[] {1200, 1500, 1700, 2000}, intake);
            } else if ("gushuzadou".equals(foodType)) {
                result = getFrequencyResult(new int[] {275, 285, 325, 350}, intake);
            } else if ("shucai".equals(foodType)) {
                result = getFrequencyResult(new int[] {250, 300, 500, 600}, intake);
            } else if ("shuiguo".equals(foodType)) {
                result = getFrequencyResult(new int[] {100, 200, 350, 400}, intake);
            } else if ("qinchu".equals(foodType)) {
                result = getFrequencyResult(new int[] {25, 40, 75, 100}, intake);
            } else if ("egg".equals(foodType)) {
                result = getFrequencyResult(new int[] {20, 40, 50, 100}, intake);
            } else if ("shuichanpin".equals(foodType)) {
                result = getFrequencyResult(new int[] {20, 40, 75, 100}, intake);
            } else if ("ruzhipin".equals(foodType)) {
                result = getFrequencyResult(new int[] {100, 200, 400, 500}, intake);
            } else if ("dadou".equals(foodType)) {
                result = getFrequencyResult(new int[] {20, 35, 50}, intake);
            } else if ("jianguo".equals(foodType)) {
                result = getFrequencyResult(new int[] {5, 10, 15}, intake);
            }
        } else if (standardEnergy > 2500 && standardEnergy <= 2700) {
            if ("water".equals(foodType)) {
                result = getFrequencyResult(new int[] {1200, 1500, 1700, 2000}, intake);
            } else if ("gushuzadou".equals(foodType)) {
                result = getFrequencyResult(new int[] {300, 325, 370, 400}, intake);
            } else if ("shucai".equals(foodType)) {
                result = getFrequencyResult(new int[] {250, 300, 500, 600}, intake);
            } else if ("shuiguo".equals(foodType)) {
                result = getFrequencyResult(new int[] {100, 200, 350, 400}, intake);
            } else if ("qinchu".equals(foodType)) {
                result = getFrequencyResult(new int[] {25, 40, 75, 100}, intake);
            } else if ("egg".equals(foodType)) {
                result = getFrequencyResult(new int[] {20, 40, 50, 100}, intake);
            } else if ("shuichanpin".equals(foodType)) {
                result = getFrequencyResult(new int[] {20, 40, 75, 100}, intake);
            } else if ("ruzhipin".equals(foodType)) {
                result = getFrequencyResult(new int[] {100, 200, 400, 500}, intake);
            } else if ("dadou".equals(foodType)) {
                result = getFrequencyResult(new int[] {20, 35, 50}, intake);
            } else if ("jianguo".equals(foodType)) {
                result = getFrequencyResult(new int[] {5, 10, 15}, intake);
            }
        } else if (standardEnergy > 2700 && standardEnergy <= 2900) {
            if ("water".equals(foodType)) {
                result = getFrequencyResult(new int[] {1200, 1500, 1700, 2000}, intake);
            } else if ("gushuzadou".equals(foodType)) {
                result = getFrequencyResult(new int[] {300, 325, 370, 400}, intake);
            } else if ("shucai".equals(foodType)) {
                result = getFrequencyResult(new int[] {250, 300, 500, 600}, intake);
            } else if ("shuiguo".equals(foodType)) {
                result = getFrequencyResult(new int[] {250, 350, 450, 500}, intake);
            } else if ("qinchu".equals(foodType)) {
                result = getFrequencyResult(new int[] {50, 75, 125, 150}, intake);
            } else if ("egg".equals(foodType)) {
                result = getFrequencyResult(new int[] {20, 40, 50, 100}, intake);
            } else if ("shuichanpin".equals(foodType)) {
                result = getFrequencyResult(new int[] {50, 75, 125, 150}, intake);
            } else if ("ruzhipin".equals(foodType)) {
                result = getFrequencyResult(new int[] {100, 200, 400, 500}, intake);
            } else if ("dadou".equals(foodType)) {
                result = getFrequencyResult(new int[] {20, 35, 50}, intake);
            } else if ("jianguo".equals(foodType)) {
                result = getFrequencyResult(new int[] {5, 10, 15}, intake);
            }
        } else {
            if ("water".equals(foodType)) {
                result = getFrequencyResult(new int[] {1200, 1500, 1700, 2000}, intake);
            } else if ("gushuzadou".equals(foodType)) {
                result = getFrequencyResult(new int[] {350, 375, 425, 500}, intake);
            } else if ("shucai".equals(foodType)) {
                result = getFrequencyResult(new int[] {250, 400, 600, 700}, intake);
            } else if ("shuiguo".equals(foodType)) {
                result = getFrequencyResult(new int[] {250, 350, 450, 500}, intake);
            } else if ("qinchu".equals(foodType)) {
                result = getFrequencyResult(new int[] {50, 75, 125, 150}, intake);
            } else if ("egg".equals(foodType)) {
                result = getFrequencyResult(new int[] {20, 40, 50, 100}, intake);
            } else if ("shuichanpin".equals(foodType)) {
                result = getFrequencyResult(new int[] {50, 75, 150, 175}, intake);
            } else if ("ruzhipin".equals(foodType)) {
                result = getFrequencyResult(new int[] {100, 200, 400, 500}, intake);
            } else if ("dadou".equals(foodType)) {
                result = getFrequencyResult(new int[] {20, 35, 50}, intake);
            } else if ("jianguo".equals(foodType)) {
                result = getFrequencyResult(new int[] {5, 10, 15}, intake);
            }
        }
        return result;
    }

    /**
     * 获取孕期膳食频率
     * 
     * @author zcq
     * @param pregnancy
     * @param foodType
     * @param intake
     * @return
     */
    public static String getPregnancyFrequencyResult(String pregnancy, String foodType, double intake) {
        String result = "";
        // 孕早期
        if ("pregnancy_pre".equals(pregnancy)) {
            if ("water".equals(foodType)) {
                result = getFrequencyResult(new int[] {800, 1200, 1800, 2500}, intake);
            } else if ("gushuzadou".equals(foodType)) {
                result = getFrequencyResult(new int[] {100, 200, 300, 400}, intake);
            } else if ("shucai".equals(foodType)) {
                result = getFrequencyResult(new int[] {100, 300, 500, 750}, intake);
            } else if ("shuiguo".equals(foodType)) {
                result = getFrequencyResult(new int[] {50, 100, 200, 300}, intake);
            } else if ("qinchu".equals(foodType)) {
                result = getFrequencyResult(new int[] {25, 50, 100, 150}, intake);
            } else if ("egg".equals(foodType)) {
                result = getFrequencyResult(new int[] {15, 25, 50, 75}, intake);
            } else if ("shuichanpin".equals(foodType)) {
                result = getFrequencyResult(new int[] {15, 25, 50, 75}, intake);
            } else if ("ruzhipin".equals(foodType)) {
                result = getFrequencyResult(new int[] {100, 200, 250, 400}, intake);
            } else if ("dadou".equals(foodType)) {
                result = getFrequencyResult(new int[] {5, 10, 20, 30}, intake);
            } else if ("jianguo".equals(foodType)) {
                result = getFrequencyResult(new int[] {5, 20, 30}, intake);
            }
        }
        // 孕中期
        else if ("pregnancy_mid".equals(pregnancy)) {
            if ("water".equals(foodType)) {
                result = getFrequencyResult(new int[] {800, 1200, 1800, 2500}, intake);
            } else if ("gushuzadou".equals(foodType)) {
                result = getFrequencyResult(new int[] {200, 350, 450, 600}, intake);
            } else if ("shucai".equals(foodType)) {
                result = getFrequencyResult(new int[] {100, 300, 500, 750}, intake);
            } else if ("shuiguo".equals(foodType)) {
                result = getFrequencyResult(new int[] {100, 200, 400, 600}, intake);
            } else if ("qinchu".equals(foodType)) {
                result = getFrequencyResult(new int[] {50, 100, 150, 200}, intake);
            } else if ("egg".equals(foodType)) {
                result = getFrequencyResult(new int[] {15, 25, 50, 75}, intake);
            } else if ("shuichanpin".equals(foodType)) {
                result = getFrequencyResult(new int[] {15, 25, 50, 75}, intake);
            } else if ("ruzhipin".equals(foodType)) {
                result = getFrequencyResult(new int[] {150, 300, 500, 750}, intake);
            } else if ("dadou".equals(foodType)) {
                result = getFrequencyResult(new int[] {5, 10, 20, 30}, intake);
            } else if ("jianguo".equals(foodType)) {
                result = getFrequencyResult(new int[] {5, 20, 30}, intake);
            }
        }
        // 孕晚期
        else if ("pregnancy_late".equals(pregnancy)) {
            if ("water".equals(foodType)) {
                result = getFrequencyResult(new int[] {800, 1200, 1800, 2500}, intake);
            } else if ("gushuzadou".equals(foodType)) {
                result = getFrequencyResult(new int[] {200, 350, 450, 600}, intake);
            } else if ("shucai".equals(foodType)) {
                result = getFrequencyResult(new int[] {100, 300, 500, 750}, intake);
            } else if ("shuiguo".equals(foodType)) {
                result = getFrequencyResult(new int[] {100, 200, 400, 600}, intake);
            } else if ("qinchu".equals(foodType)) {
                result = getFrequencyResult(new int[] {50, 100, 200, 300}, intake);
            } else if ("egg".equals(foodType)) {
                result = getFrequencyResult(new int[] {15, 25, 50, 75}, intake);
            } else if ("shuichanpin".equals(foodType)) {
                result = getFrequencyResult(new int[] {15, 25, 50, 75}, intake);
            } else if ("ruzhipin".equals(foodType)) {
                result = getFrequencyResult(new int[] {150, 300, 500, 750}, intake);
            } else if ("dadou".equals(foodType)) {
                result = getFrequencyResult(new int[] {5, 10, 20, 30}, intake);
            } else if ("jianguo".equals(foodType)) {
                result = getFrequencyResult(new int[] {5, 20, 30}, intake);
            }
        }
        return result;
    }

    private static String getFrequencyResult(int[] amount, double intake) {
        String result = "";
        if (amount.length == 4) {
            if (intake == 0) {
                result = "null";
            } else if (intake > 0 && intake <= amount[0]) {
                result = "least";
            } else if (intake > amount[0] && intake < amount[1]) {
                result = "less";
            } else if (intake >= amount[1] && intake <= amount[2]) {
                result = "normal";
            } else if (intake > amount[2] && intake < amount[3]) {
                result = "more";
            } else if (intake >= amount[3]) {
                result = "most";
            }
            result += ":" + amount[1] + "-" + amount[2];
        } else if (amount.length == 3) {
            if (intake == 0) {
                result = "null";
            } else if (intake > 0 && intake < amount[0]) {
                result = "less";
            } else if (intake >= amount[0] && intake <= amount[1]) {
                result = "normal";
            } else if (intake > amount[1] && intake < amount[2]) {
                result = "more";
            } else if (intake >= amount[2]) {
                result = "most";
            }
            result += ":" + amount[0] + "-" + amount[1];
        }
        return result;
    }

    /**
     * 计算孕期一日摄入能量
     * 
     * @author zcq
     * @param pregnancy
     * @param height
     * @return
     */
    public static Double getPregnancyEnergy(String pregnancy, double height) {
        Double result = 0.0;
        // 验证参数合法性
        if (StringUtils.isEmpty(pregnancy)) {
            throw new NullPointerException("孕期不能为空");
        }
        // 身高cm->换算成m
        double heightM = DecimalCalculate.div(height, 100);
        double heightSQR = DecimalCalculate.mul(heightM, heightM);
        double inittemp = DecimalCalculate.mul(heightSQR, 21.9 * 30);
        if ("pregnancy_pre".equals(pregnancy)) {
            result = inittemp;
        } else if ("pregnancy_mid".equals(pregnancy)) {
            result = DecimalCalculate.add(inittemp, 300);
        } else if ("pregnancy_late".equals(pregnancy)) {
            result = DecimalCalculate.add(inittemp, 450);
        }
        return result;
    }

    /**
     * 孕期早餐能量范围
     * 
     * @author zcq
     * @param energy
     * @return
     */
    public static String getPregnancyEnergyBreak(double energy) {
        return Math.round(DecimalCalculate.mul(energy, 0.25)) + "~" + Math.round(DecimalCalculate.mul(energy, 0.35));
    }

    /**
     * 孕期午餐能量范围
     * 
     * @author zcq
     * @param energy
     * @return
     */
    public static String getPregnancyEnergyLunch(double energy) {
        return Math.round(DecimalCalculate.mul(energy, 0.35)) + "~" + Math.round(DecimalCalculate.mul(energy, 0.45));
    }

    /**
     * 孕期晚餐能量范围
     * 
     * @author zcq
     * @param energy
     * @return
     */
    public static String getPregnancyEnergySupper(double energy) {
        return Math.round(DecimalCalculate.mul(energy, 0.25)) + "~" + Math.round(DecimalCalculate.mul(energy, 0.35));
    }
}

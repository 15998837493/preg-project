
package com.mnt.preg.examitem.util;

/**
 * 膳食评估常量
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-17 zcq 初版
 */
public class FoodScoreUtil {

    /******************** 餐类主键编码常量 **********************/
    public static final int MEALS_COUNT = 6;// 一天总餐次

    public static final String BREAKFAST_MEALS_CODE = "C00000CA000000000001";// 早餐编码

    public static final String MORNING_EXTRA_MEALS_CODE = "C00000CA000000000002";// 上午加餐编码

    public static final String LUNCH_MEALS_CODE = "C00000CA000000000003";// 午餐编码

    public static final String AFTERNOON_EXTRA_MEALS_CODE = "C00000CA000000000004";// 下午加餐编码

    public static final String SUPPER_MEALS_CODE = "C00000CA000000000005";// 晚餐编码

    public static final String NIGHT_EXTRE_MEALS_CODE = "C00000CA000000000006";// 晚上加餐编码

    /******************** 适宜餐次编码常量 **********************/
    public static final String FITS_BREAKFAST = "10";// 适宜早餐

    public static final String FITS_MORNING_EXTRE = "11";// 适宜上午加餐

    public static final String FITS_LUNCH = "12";// 适宜午餐

    public static final String FITS_AFTERNOON_EXTRE = "13";// 适宜下午加餐

    public static final String FITS_SUPPER = "14";// 适宜晚餐

    public static final String FITS_NIGHT_EXTRE = "15";// 适宜晚上加餐

    /******************** 推荐项编码常量 **********************/
    public static final String RECOMMEND_OMG3 = "30";// 含有ω-3

    public static final String RECOMMEND_I = "31";// 含有碘

    public static final String RECOMMEND_AC = "32";// 具有抗肿瘤效果

    /******************** 排除项编码常量 **********************/
    public static final String AVOID_OX = "50";// 含有草酸

    public static final String AVOID_GLU = "51";// 含有麸质

    public static final String AVOID_PU = "52";// 含有嘌呤

    public static final String AVOID_STIM = "53";// 属于刺激性菜品

    public static final String AVOID_CRU = "54";// 属于十字花科菜品

    /** 疾病对各摄入成分的要求 */
    public final static int DISEASE_REQUEST_AVOID = -2; // 尽量不吃

    public final static int DISEASE_REQUEST_LOW = -1; // 建议少吃

    public final static int DISEASE_REQUEST_UNLIMITED = 0; // 无限制

    public final static int DISEASE_REQUEST_HIGH = 1; // 建议多吃

    public final static int DISEASE_REQUEST_RECOMMEND = 2; // 尽量多吃

    /** 菜谱中各摄入成分是否考虑 */
    public final static int FOOD_IGNORE = 0; // 可以忽略的成分

    public final static int FOOD_EFFECT = 1; // 有效的成分

    /** 膳食结构类型 */
    public final static String INTAKE_TYPE_CORN = "001"; // 谷类及杂豆

    public final static String INTAKE_TYPE_STARCHVEGE = "002"; // 淀粉类蔬菜

    public final static String INTAKE_TYPE_NONSTARCHVEGE = "003"; // 非淀粉类蔬菜

    public final static String INTAKE_TYPE_FRUITS = "004"; // 水果

    public final static String INTAKE_TYPE_MILK = "005"; // 奶制品

    public final static String INTAKE_TYPE_ANIMAL = "006"; // 鱼肉蛋类

    public final static String INTAKE_TYPE_BEAN = "007"; // 大豆类

    public final static String INTAKE_TYPE_NUT = "008"; // 坚果

    public final static String INTAKE_TYPE_OIL = "009"; // 油脂

    public final static String INTAKE_TYPE_SALT = "010"; // 盐

    public final static String INTAKE_TYPE_WATER = "011"; // 水

    public final static String INTAKE_TYPE_MNT_STICK = "101"; // 营养棒

    public final static String INTAKE_TYPE_MNT_CAKE = "102"; // 营养糕

    public final static String INTAKE_TYPE_MNT_BREAD = "103"; // 餐包

    public final static String INTAKE_TYPE_MNT_MILK_SHAKE = "104"; // 奶昔

    public final static String INTAKE_TYPE_MNT_FRUITVEGE_POWDER = "105"; // 果蔬粉

    public final static String INTAKE_TYPE_OTHER = "999"; // 其他（只计算能量）

    /** 分隔符 */
    public final static String SEPARATOR = "、"; // 多个食材间分隔符

    /** 食谱生成预定义参数 */
    public final static int RULE_MAX_FOODS_NUM = 300; // 通过食谱第二次筛选(分餐次)的最大食谱数

    public final static int RULE_MAX_EXCHANGE_NUM = 40; // 食谱替换时供选择的最大食谱数

    public final static int RULE_MIN_SCORE = 0; // 通过食谱第二次筛选(分餐次)的最少得分

    public final static int RULE_MAX_USE_TIMES = 3; // 同一食谱在计划中最多出现的次数。主食不考虑

    public final static int RULE_MAX_RETRY_TIMES = 10; // 重新选菜的最大次数，避免死循环

}

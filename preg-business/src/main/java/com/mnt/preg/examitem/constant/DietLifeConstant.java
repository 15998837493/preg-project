
package com.mnt.preg.examitem.constant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.mnt.preg.examitem.pojo.EvaluateFoodLibraryPojo;

/**
 * 膳食评估常量类
 * 
 * @author xdc
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-5-31 xdc 初版
 */
public class DietLifeConstant {

    // 频率和对应的计算数值
    public static final Map<String, BigDecimal> FREQUENCY = new HashMap<String, BigDecimal>();
    static {
        FREQUENCY.put("0", new BigDecimal(0));
        FREQUENCY.put("1", new BigDecimal(0.03));
        FREQUENCY.put("2", new BigDecimal(0.05));
        FREQUENCY.put("3", new BigDecimal(0.08));
        FREQUENCY.put("4", new BigDecimal(0.2));
        FREQUENCY.put("5", new BigDecimal(0.5));
        FREQUENCY.put("6", new BigDecimal(0.8));
        FREQUENCY.put("7", new BigDecimal(1));
        FREQUENCY.put("8", new BigDecimal(2));
        FREQUENCY.put("9", new BigDecimal(3));
    }

    // 用油情况对应的数值
    public static final Map<String, BigDecimal> OILINFO = new HashMap<String, BigDecimal>();
    static {
        OILINFO.put("O10901", new BigDecimal(40));// 无特殊烹饪习惯O10901
        OILINFO.put("O10902", new BigDecimal(40));// 勾芡
        OILINFO.put("O10903", new BigDecimal(15));// 几乎不放油
        OILINFO.put("O10904", new BigDecimal(25));// 清淡
        OILINFO.put("O10905", new BigDecimal(60));// 高油
        OILINFO.put("O10906", new BigDecimal(40));// 高糖
        OILINFO.put("O10907", new BigDecimal(40));// 高盐
    }

    // 使用油的特殊情况
    public static final List<String> OILINFOTYPE = new ArrayList<String>();
    static {
        OILINFOTYPE.add("O10903");// 几乎不放油
        OILINFOTYPE.add("O10904");// 清淡
        OILINFOTYPE.add("O10905");// 高油

    }

    // 产能营养素推荐功能比
    public static final Map<String, String> RECOMENERYPERC = new HashMap<String, String>();
    static {
        RECOMENERYPERC.put("cbr", "50%-65%");
        RECOMENERYPERC.put("protid", "12%-20%");
        RECOMENERYPERC.put("fat", "20%-35%");
    }

    // 餐次与选项的对应关系
    public static final Map<String, String> MEALSOPTION = new HashMap<String, String>();
    static {
        MEALSOPTION.put("O10301", "早餐");
        MEALSOPTION.put("O10303", "午餐");
        MEALSOPTION.put("O10305", "晚餐");
    }

    // 一日推荐摄入量维护
    public static final Map<String, String> RECOMINTAKE = new HashMap<String, String>();
    static {
        RECOMINTAKE.put("饮水", "1500-1700ml");
        RECOMINTAKE.put("谷类", "200-300g");
        RECOMINTAKE.put("薯类", "50-100g");
        RECOMINTAKE.put("蔬菜", "300-500g");
        RECOMINTAKE.put("水果", "200-350g");
        RECOMINTAKE.put("鱼肉蛋", "120-200g");
        RECOMINTAKE.put("大豆", "15-20g");
        RECOMINTAKE.put("坚果", "10-15g");
        RECOMINTAKE.put("奶制品", "250-300g");
        RECOMINTAKE.put("油", "25-30g");
        RECOMINTAKE.put("盐", "0-6g");
    }

    // 混合食物成分对比 double[]中的三个数分别是谷类占比，鱼肉蛋占比，蔬菜占比
    public static final Map<String, double[]> HUNHESHIZB = new HashMap<String, double[]>();
    static {
        HUNHESHIZB.put("O80109", new double[] {0.6, 0.4, 0});
        HUNHESHIZB.put("O80110", new double[] {0.5, 0, 0.5});
        HUNHESHIZB.put("O80111", new double[] {0.35, 0.6, 0.05});
        HUNHESHIZB.put("O80112", new double[] {0.65, 0.3, 0.05});
    }

    // 食物分类_谷类
    public static final List<String> GULEI = new ArrayList<String>();
    static {
        GULEI.add("O80101");
        GULEI.add("O80102");
        GULEI.add("O80103");
        GULEI.add("O80104");
        GULEI.add("O80105");
        GULEI.add("O80106");
    }

    // 谷类计算质量换算比率
    public static final Map<String, Double> GULEIRATE = new HashMap<String, Double>();
    static {
        GULEIRATE.put("O80101", 0.4);
        GULEIRATE.put("O80102", 0.1);
        GULEIRATE.put("O80103", 0.7);
        GULEIRATE.put("O80104", 0.7);
        GULEIRATE.put("O80105", 0.7);
        GULEIRATE.put("O80106", 0.7);

        GULEIRATE.put("O80109", 0.7);
        GULEIRATE.put("O80110", 0.7);
        GULEIRATE.put("O80111", 0.7);
        GULEIRATE.put("O80112", 0.7);

    }

    // 混合
    public static final List<String> HUNHELEI = new ArrayList<String>();
    static {
        HUNHELEI.add("O80109");
        HUNHELEI.add("O80110");
        HUNHELEI.add("O80111");
        HUNHELEI.add("O80112");
    }

    // 食物分类_薯类
    public static final List<String> SHULEI = new ArrayList<String>();
    static {
        SHULEI.add("O80107");
    }

    // 蔬菜
    public static final List<String> SHUCAILEI = new ArrayList<String>();
    static {
        SHUCAILEI.add("O80311");
    }

    // 水果
    public static final List<String> SHUIGUOLEI = new ArrayList<String>();
    static {
        SHUIGUOLEI.add("O80401");
        SHUIGUOLEI.add("O80402");
        SHUIGUOLEI.add("O80403");
        SHUIGUOLEI.add("O80404");
        SHUIGUOLEI.add("O80405");
        SHUIGUOLEI.add("O80406");
        SHUIGUOLEI.add("O80407");
        SHUIGUOLEI.add("O80408");
    }

    // 水果_质量换算比率
    public static final Map<String, Double> SHUIGUOLEIRATE = new HashMap<String, Double>();
    static {
        SHUIGUOLEIRATE.put("O80401", 0.8);
        SHUIGUOLEIRATE.put("O80402", 0.6);
        SHUIGUOLEIRATE.put("O80403", 0.7);
        SHUIGUOLEIRATE.put("O80404", 0.8);
        SHUIGUOLEIRATE.put("O80405", 0.9);
        SHUIGUOLEIRATE.put("O80406", 0.9);
        SHUIGUOLEIRATE.put("O80407", 1.0);
        SHUIGUOLEIRATE.put("O80408", 0.9);
    }

    // 鱼肉蛋
    public static final List<String> YUROUDANLEI = new ArrayList<String>();
    static {
        YUROUDANLEI.add("O80209");
        YUROUDANLEI.add("O80210");
        YUROUDANLEI.add("O90108");
        YUROUDANLEI.add("O90101");
    }

    // 奶制品
    public static final List<String> NAIZHIPINLEI = new ArrayList<String>();
    static {
        NAIZHIPINLEI.add("O90102");
        NAIZHIPINLEI.add("O90103");
    }

    // 豆类
    public static final List<String> DOULEI = new ArrayList<String>();
    static {
        DOULEI.add("O90104");
    }

    // 坚果
    public static final List<String> JIANGUOLEI = new ArrayList<String>();
    static {
        JIANGUOLEI.add("O90106");
    }

    // 水
    public static final List<String> SHUILEI = new ArrayList<String>();
    static {
        SHUILEI.add("O70101");
    }

    // 零食分类——高盐
    public static final List<String> FOODTYPE2_GAOYAN = new ArrayList<String>();
    static {
        FOODTYPE2_GAOYAN.add("O90207");
    }

    // 零食分类——高油
    public static final List<String> FOODTYPE2_GAOYOU = new ArrayList<String>();
    static {
        FOODTYPE2_GAOYOU.add("O90206");
        FOODTYPE2_GAOYOU.add("O90208");
    }

    // 零食分类——高糖
    public static final List<String> FOODTYPE2_GAOTANG = new ArrayList<String>();
    static {
        FOODTYPE2_GAOTANG.add("O90203");
        FOODTYPE2_GAOTANG.add("O90204");
        FOODTYPE2_GAOTANG.add("O90205");
        FOODTYPE2_GAOTANG.add("O90209");
    }

    // 零食分类——高热量
    public static final List<String> FOODTYPE2_GAORELIANG = new ArrayList<String>();
    static {
        FOODTYPE2_GAORELIANG.add("O90201");
        FOODTYPE2_GAORELIANG.add("O90202");
        FOODTYPE2_GAORELIANG.add("O90210");
    }

    // 食物id与食物对应的关系
    public static Map<String, EvaluateFoodLibraryPojo> getFoodLibraryMap(
            List<EvaluateFoodLibraryPojo> evaluateFoodLibraryVos) {
        Map<String, EvaluateFoodLibraryPojo> foodLibraryMap = new HashMap<String, EvaluateFoodLibraryPojo>();
        for (EvaluateFoodLibraryPojo evaluateFoodLibraryVo : evaluateFoodLibraryVos) {
            foodLibraryMap.put(evaluateFoodLibraryVo.getFoodId(), evaluateFoodLibraryVo);
        }
        return foodLibraryMap;
    }

    // 补充剂元素（男）
    public static final Map<String, String> MALEELEMENTMAP = new LinkedHashMap<String, String>();
    static {
        MALEELEMENTMAP.put("foodVa", "800~——~3000");// 维生素A
        MALEELEMENTMAP.put("foodVe", "——~14~700");// 维生素E
        MALEELEMENTMAP.put("foodVb1", "1.4~——~——");// 维生素B1/硫胺素
        MALEELEMENTMAP.put("foodVb2", "1.4~——~——");// 维生素B2/核黄素
        MALEELEMENTMAP.put("foodVb6", "1.4~1.2~60");// 维生素B6
        MALEELEMENTMAP.put("foodVb12", "2.4~24~——");// 维生素B12
        MALEELEMENTMAP.put("foodAf", "15~——~35");// 烟酸/维生素B3
        MALEELEMENTMAP.put("foodVc", "100~——~2000");// 维生素C
        MALEELEMENTMAP.put("foodEca", "800~800~2000");// 钙
        MALEELEMENTMAP.put("foodEk", "——~2000~——");// 钾
        MALEELEMENTMAP.put("foodEmg", "330~350~——");// 镁
        MALEELEMENTMAP.put("foodEfe", "12~——~42");// 铁
        MALEELEMENTMAP.put("foodIodine", "120~——~600");// 碘
        MALEELEMENTMAP.put("foodEzn", "12.5~——~40");// 锌
        MALEELEMENTMAP.put("foodEse", "60~——~400");// 硒
    }

    // 补充剂元素（女）
    public static final Map<String, String> FEMALEELEMENTMAP = new LinkedHashMap<String, String>();
    static {
        FEMALEELEMENTMAP.put("foodVa", "700~——~3000");// 维生素A
        FEMALEELEMENTMAP.put("foodVe", "——~14~700");// 维生素E
        FEMALEELEMENTMAP.put("foodVb1", "1.3~——~——");// 维生素B1/硫胺素
        FEMALEELEMENTMAP.put("foodVb2", "1.2~——~——");// 维生素B2/核黄素
        FEMALEELEMENTMAP.put("foodVb6", "1.4~1.2~60");// 维生素B6
        FEMALEELEMENTMAP.put("foodVb12", "2.4~24~——");// 维生素B12
        FEMALEELEMENTMAP.put("foodAf", "12~——~35");// 烟酸/维生素B3
        FEMALEELEMENTMAP.put("foodVc", "100~——~2000");// 维生素C
        FEMALEELEMENTMAP.put("foodEca", "800~800~2000");// 钙
        FEMALEELEMENTMAP.put("foodEk", "——~2000~——");// 钾
        FEMALEELEMENTMAP.put("foodEmg", "330~350~——");// 镁
        FEMALEELEMENTMAP.put("foodEfe", "20~——~42");// 铁
        FEMALEELEMENTMAP.put("foodIodine", "120~——~600");// 碘
        FEMALEELEMENTMAP.put("foodEzn", "7.5~——~40");// 锌
        FEMALEELEMENTMAP.put("foodEse", "60~——~400");// 硒
    }

    public static final Map<String, String> ELEMENTNAME = new HashMap<String, String>();
    static {
        ELEMENTNAME.put("foodVa", "维生素A");// 维生素A
        ELEMENTNAME.put("foodVe", "维生素E");// 维生素E
        ELEMENTNAME.put("foodVb1", "维生素B1");// 维生素B1/硫胺素
        ELEMENTNAME.put("foodVb2", "维生素B2");// 维生素B2/核黄素
        ELEMENTNAME.put("foodVb6", "维生素B6");// 维生素B6
        ELEMENTNAME.put("foodVb12", "维生素B12");// 维生素B12
        ELEMENTNAME.put("foodAf", "烟酸");// 烟酸/维生素B3
        ELEMENTNAME.put("foodVc", "维生素C");// 维生素C
        ELEMENTNAME.put("foodEca", "钙");// 钙
        ELEMENTNAME.put("foodEk", "钾");// 钾
        ELEMENTNAME.put("foodEmg", "镁");// 镁
        ELEMENTNAME.put("foodEfe", "铁");// 铁
        ELEMENTNAME.put("foodIodine", "碘");// 碘
        ELEMENTNAME.put("foodEzn", "锌");// 锌
        ELEMENTNAME.put("foodEse", "硒");// 硒
        ELEMENTNAME.put("foodDf", "膳食纤维");// 膳食纤维
        ELEMENTNAME.put("foodProtid", "优质蛋白质");// 优质蛋白质
    }

    // 零食的建议
    public static final Map<String, String> SNACKSINFO = new HashMap<String, String>();
    static {
        SNACKSINFO.put("health", "零食摄入尚可，建议选择健康零食，参与医学营养零食管理，为自己的健康加分；");
        SNACKSINFO.put("excess", "零食摄入过量，建议您参与医学营养零食管理，轻松戒掉零食；");
    }

    // 份数与食物g数的对应关系
    public static final Map<String, Double> FENSHUCONECTION = new HashMap<String, Double>();
    static {
        FENSHUCONECTION.put("O90102", 200.0);
        FENSHUCONECTION.put("O90103", 200.0);
        FENSHUCONECTION.put("O90101", 50.0);
        FENSHUCONECTION.put("O90104", 25.0);
        FENSHUCONECTION.put("O90106", 10.0);
        FENSHUCONECTION.put("O90107", 50.0);
        FENSHUCONECTION.put("O90105", 50.0);
    }

    // 营养素作用及缺乏表现String[]中，0表示作用，1表示缺乏表现
    public static final Map<String, String[]> MISSELEMENTLOOK = new HashMap<String, String[]>();
    static {
        MISSELEMENTLOOK.put("foodProtid", new String[] {"优质蛋白质更容易被人体吸收利用，良好来源：动物蛋白质中的蛋、奶、肉、鱼以及大豆蛋白质。",
                "代谢率下降，对疾病抵抗力减退，易患病，远期效果是器官的损害等"});
        MISSELEMENTLOOK.put("foodDf", new String[] {"维持消化系统健康的必要物质，良好来源：燕麦、大麦、黑麦、水果、蔬菜和豆类。",
                "便秘等胃肠道疾病"});
        MISSELEMENTLOOK.put("foodEca", new String[] {"骨骼和牙齿的主要成分，维生素D吸收不良的人会有钙吸收不良，良好来源：干酪、牛奶、菠菜、沙丁鱼、芝麻酱等。",
                "手、脚针刺样发麻，骨质疏松。儿童缺钙有：烦躁、生长发育不良、肌肉痉挛抽搐等"});
        MISSELEMENTLOOK.put("foodEk", new String[] {"钾和钠、氯协同一起，控制体内水分，维持人体的酸碱平衡，良好食物来源：土豆、芦笋、鳄梨、菠菜、番茄、香蕉等。",
                "疲劳，肌乏力，便秘，痉挛及肾功能降低。严重缺钾可引起心脏问题如心律失常等。"});
        MISSELEMENTLOOK.put("foodEmg", new String[] {"构成骨和牙齿，与钙钠钾协同对神经信号传导好起着重要作用，良好来源：菠菜、豆类、坚果类、芝麻等。",
                "疲倦，虚弱，无食欲，语言迟钝，贫血等，缺镁严重时体征包括心率过快和抽搐等"});
        MISSELEMENTLOOK.put("foodEfe", new String[] {"血红蛋白的主要成分，对输送氧给全身起着主要作用。良好来源：菠菜、内脏、红色肉、蛋黄等。",
                "缺铁性贫血，如虚弱，肤色苍白，疲倦头晕，手指足趾冰冷麻木，指甲脆弱等"});
        MISSELEMENTLOOK.put("foodEzn", new String[] {"微量元素之一，维持机体的性发育、生殖细胞的生成，良好来源：牡蛎、蛋类、红色肉、龙虾等。",
                "食欲不振，味觉缺失，消化不良，脱发，伤口不易愈合，儿童生长不良，青春期发育及性成熟后滞等"});
        MISSELEMENTLOOK.put("foodEse", new String[] {"抗氧化剂之一，保护细胞免遭自由基的侵害，良好来源：鱼、甲壳类海产、禽类、巴西果等。",
                "心脏扩大，不能有效地泵出血液"});
        MISSELEMENTLOOK.put("foodIodine", new String[] {"人体所有细胞都含碘，但40%的碘都储存在甲状腺内，用它来制造甲状腺素，以供给正常生长和新陈代谢的需要。",
                "甲状腺肿大（颈部由于甲状腺长大而肿胀）和克丁病（呆小症，儿童矮小，愚笨）的最常见原因"});
        MISSELEMENTLOOK.put("foodVa", new String[] {"维持正常的视觉，尤其是暗视觉、生殖、皮肤和粘膜的健康，良好来源：红薯、胡萝卜、菠菜、肝、鸡蛋等。",
                "干眼病，并可发展成夜盲、角膜溃疡等；儿童生长不良、皮肤干燥、粗糙的毛囊角化病等"});
        MISSELEMENTLOOK.put("foodVc", new String[] {"抗氧化剂之一，参与血红蛋白的生成，协助铁在肠中的吸收，良好来源：西兰花、柿子椒、番茄、猕猴桃、柑橘等",
                "肌肉软弱，关节疼痛，伤口不易愈合，牙齿松动，牙龈肿胀出血，皮肤出现红点等"});
        MISSELEMENTLOOK.put("foodVe", new String[] {"抗氧化剂之一，参与制造红血球，防止凝血。良好来源：麦芽、杏仁、榛子、大豆等。",
                "有神经系统问题和贫血等"});
        MISSELEMENTLOOK.put("foodVb1", new String[] {"促进正常生长发育，维持心脏、神经及消化系统的正常生理机能，良好来源：豌豆、菠菜、肝、牛肉等。",
                "早期缺乏食欲不振、烦躁、疲劳乏力。严重时可出现虚弱、影响手、脚的神经损伤以及心率加快等"});
        MISSELEMENTLOOK.put("foodVb2", new String[] {"保护神经系统以及周身粘膜的健康中都起重要作用，良好来源：芦笋、牛奶、酸奶、肉类、蛋类等",
                "口唇发红干裂，口腔及舌的粘膜发炎，口腔溃疡，口角干裂，喉咙疼痛等"});
        MISSELEMENTLOOK.put("foodAf", new String[] {"参与葡萄糖、脂肪和氨基酸的产生和分解，良好来源：豌豆、肝、红色肉、禽类、三文鱼等。",
                "疲乏、虚弱、无食欲，轻度腹泻、焦虑，有时出现抑郁等"});
        MISSELEMENTLOOK.put("foodVb6", new String[] {"参与氨基酸的生产和消化，协助人体制造胰岛素，良好来源：红薯、香蕉、鸡、三文鱼、金枪鱼等。",
                "轻度缺乏的症状包括口唇干裂，皮脂溢出，恶心，腹泻，严重缺乏时出现食欲减退，抑郁等"});
        MISSELEMENTLOOK.put("foodVb12", new String[] {"可促进生长和发育，维持神经系统的正常功能，良好来源：奶制品、内脏、蛋、牛肉、海产食品等。",
                "巨幼细胞贫血，神经损伤（常见者为手足麻刺感），口及舌发炎等"});
    }

    // 选型和值得对应关系
    public static final Map<String, String> OPTIONGETVALUE = new HashMap<String, String>();
    static {
        // 第二题
        OPTIONGETVALUE.put("O10402", "5-10分钟");
        OPTIONGETVALUE.put("O10403", "10-15分钟");
        OPTIONGETVALUE.put("O10404", "15-20分钟");
        OPTIONGETVALUE.put("O10405", "20分钟以上");
        // 第三题
        OPTIONGETVALUE.put("O10501", "几乎不在外用餐");
        OPTIONGETVALUE.put("O10502", "一周1-3次");
        OPTIONGETVALUE.put("O10503", "一周3-5次");
        OPTIONGETVALUE.put("O10504", "一周5次以上");

    }

}

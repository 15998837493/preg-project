
package com.mnt.preg.examitem.util.life;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 生活方式选项信息
 * 
 * @author mnt_zhangjing
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-3-2 mnt_zhangjing 初版
 */
public class LifeStyleConstant {

    private static LifeStyleConstant lifeStyleConstant;

    // 选项
    private Map<String, Option> optionMap;

    // 问题
    private Map<String, List<Problem>> problemMap;

    // 分析项目
    private Map<String, String> itemMap;

    // 食物元素
    private Map<String, String> foodElementMap;

    // 食物有效重量
    private Map<String, Double> actualMap;

    private List<String> cereals;

    // 单选
    public String radio = "1";

    // 多选
    public String checkbox = "2";

    // 结果正常
    public String normal = "0";

    // 结果异常
    public String exception = "4";

    public String esmeat = "18";

    public String efmeat = "19";

    public String ehfish = "21";

    public String efish = "20";

    public String eegg = "22";

    public String fvege = "24";

    public String svege = "25";

    public String amilk = "34";

    public String tmilk = "35";

    public String ebean = "36";

    public String enut = "37";

    public String eorgants = "38";

    public String eoil = "56";

    public String iodine = "碘";

    public String oil = "ω-3脂肪酸";

    public String deepFish = "EPA;DHA";

    public String foodDf = "膳食纤维";

    public String foodProtid = "优质蛋白质";

    public String foodEca = "钙";

    public String foodEk = "钾";

    public String foodEmg = "镁";

    public String foodEfe = "铁";

    public String foodEzn = "锌";

    public String foodEse = "硒";

    public String foodVa = "维生素A";

    public String foodVc = "维生素C";

    public String foodVe = "维生素E";

    public String foodVb1 = "维生素B1";

    public String foodVb2 = "维生素B2";

    public String foodAf = "烟酸";

    public String foodVb6 = "维生素B6";

    public String foodVb12 = "维生素B12";

    public String foodVb9 = "叶酸";

    // =======================================报告部分使用===============================
    public String actual_totalEnergy = "B00500";// 实际一日总能量

    public String recommend_totalEnergy = "B00578";// 推荐一日总能量

    public String breakfast = "B00579";// 早餐

    public String morningMeal = "B00580";// 上午加餐

    public String lunch = "B00581";// 午餐

    public String afternoonSnacks = "B00582";// 下午加餐

    public String dinner = "B00583";// 晚餐

    public String supper = "B00584";// 夜宵

    public String cbr = "B00501";// 碳水化合物摄入量

    public String cbrratio = "B00502";// 碳水化合物供能比

    public String protid = "B00503";// 蛋白质摄入量

    public String protidratio = "B00504";// 蛋白质供能比

    public String fat = "B00505";// 脂肪摄入量

    public String fatratio = "B00506";// 脂肪供能比

    public String cereal = "B00529";// 谷类

    public String potatoes = "B00530";// 薯类

    public String veg = "B00531";// 蔬菜

    public String fruit = "B00532";// 水果

    public String fme = "B00533";// 鱼肉蛋

    public String bean = "B00534";// 大豆类

    public String nut = "B00535";// 坚果

    public String milk = "B00536";// 乳类

    public String water = "B00559";// 饮水

    public String czl = "B00575";// 粗杂粮

    public String vegetables = "B00577";// 绿叶蔬菜

    public String goodProtid = "B00508";// 优质蛋白质

    public String organs = "B00585";// 动物内脏

    public String oils = "B00586";// 用油量

    public String hs = "B00571";// 海参

    public String yw = "B00570";// 燕窝

    public String bgdx = "B00560";// 饼干点心

    public String dg = "B00561";// 蛋糕

    public String qsmb = "B00562";// 起酥面包

    public String ht = "B00587";// 海苔

    public String tg = "B00564";// 糖果

    public String mj = "B00565";// 蜜饯

    public String qkl = "B00566";// 巧克力

    public String tsyl = "B00567";// 碳酸饮料

    public String lyxg = "B00568";// 冷饮雪糕

    public String phsp = "B00569";// 膨化食品

    public String diet = "B00588"; // 饮食

    public String sport = "B00589";// 运动

    public String sleep = "B00590";// 睡眠

    public String mental = "B00591";// 心理

    public String drinking = "B00592";// 烟酒

    public String environment = "B00593";// 环境

    public String lacking = "B00594";// 膳食中可能不足的营养素

    public String fungus = "B00595";// 菌类

    public String algae = "B00596";// 藻类

    public String fm = "B00597";// 蜂蜜

    // =======================================报告部分使用===============================
    public String B00509 = "B00509";// 膳食纤维

    public String B00510 = "B00510";// 维生素A

    public String B00511 = "B00511";// 维生素C

    public String B00512 = "B00512";// 维生素E

    public String B00515 = "B00515";// 烟酸

    public String B00516 = "B00516";// 叶酸

    public String B00517 = "B00517";// 维生素B6

    public String B00518 = "B00518";// 维生素B12

    public String B00519 = "B00519";// 钙

    public String B00520 = "B00520";// 钾

    public String B00521 = "B00521";// 镁

    public String B00522 = "B00522";// 铁

    public String B00523 = "B00523";// 锌

    public String B00524 = "B00524";// 关于碘的摄入状态调查

    public String B00525 = "B00525";// ω-3脂肪酸

    /*
     * public String B00526 = "B00526";//亚油酸
     * public String B00527 = "B00527";//DHA+EPA
     * public String B00528 = "B00528";//DHA
     */

    public String B00537 = "B00537";// 您的膳食类型

    public String B00538 = "B00538";// 您在孕期的用餐频率为

    public String B00539 = "B00539";// 您在孕期的就餐时间为

    public String B00540 = "B00540";// 您在孕期的平均用餐时间为

    public String B00541 = "B00541";// 您在孕期的在外用餐频率为

    public String B00542 = "B00542";// 您在孕期的进餐感受通常为

    public String B00543 = "B00543";// 您在孕期的烹饪用油多为

    public String B00544 = "B00544";// 您在孕期常用的烹饪方式有

    public String B00545 = "B00545";// 您在孕期常用的烹饪习惯有

    public String B00546 = "B00546";// 您的运动频率为

    public String B00547 = "B00547";// 您常采用的运动锻炼方式

    public String B00549 = "B00549";// 您每次锻炼多长时间

    public String B00550 = "B00550";// 运动时间

    public String B00551 = "B00551";// 近期睡眠如何

    public String B00552 = "B00552";// 您睡眠不好的主要表现

    public String B00553 = "B00553";// 您每天平均睡眠时间

    public String B00554 = "B00554";// 您最近两周是否经常几种情绪

    public String B00555 = "B00555";// 关于饮酒

    public String B00556 = "B00556";// 关于主被动吸烟

    public String B00557 = "B00557";// 您的工作/生活场所经常会接触到哪些有害物质

    public String B00558 = "B00558";// 您在孕期平时晒太阳的时间

    public String B00576 = "B00576";// 荤素比

    public String p101 = "p101";// 您的膳食类型

    public String p102 = "p102";// 您在孕期的用餐频率为

    public String p103 = "p103";// 用餐时间

    public String p104 = "p104";// 您在孕期的平均用餐时间为

    public String p105 = "p105";// 您在孕期的在外用餐频率为

    public String p106 = "p106";// 您在孕期的进餐感受通常为

    public String p107 = "p107";// 烹饪用油

    public String p108 = "p108";// 您在孕期常用的烹饪方式有

    public String p109 = "p109";// 烹饪习惯

    public String p110 = "p110";// 碘摄入情况

    public String p201 = "p201";// 您参加运动锻炼吗

    public String p202 = "p202";// 您常采用的运动锻炼方式

    public String p204 = "p204";// 您每次锻炼多次时间

    public String p205 = "p205";// 运动时间

    public String p301 = "p301";// 近期睡眠如何

    public String p302 = "p302";// 您睡眠不好的主要表现

    public String p303 = "p303";// 您每天平均睡眠时间

    public String p401 = "p401";// 您最近两周是否经常几种情绪

    public String p501 = "p501";// 关于饮酒

    public String p502 = "p502";// 关于主被动吸烟

    public String p601 = "p601";// 您的工作/生活场所经常会接触到哪些有害物质

    public String p602 = "p602";// 您在孕期平时晒太阳的时间

    public String p801 = "p801";// 主食

    public String p802 = "p802";// 畜禽类肉

    public String p803 = "p803";// 蔬菜类

    public String p804 = "p804";// 水果

    public String p901 = "p901";// 混合食物

    public String p902 = "p902";// 零食

    public String p903 = "p903";// 营养品

    public String O10301 = "O10301";// 早餐

    public String O10302 = "O10302";// 上午加餐

    public String O10303 = "O10303";// 午餐

    public String O10304 = "O10304";// 下午加餐

    public String O10305 = "O10305";// 晚餐

    public String O10306 = "O10306";// 夜宵

    public String O11002 = "O11002";// 使用加碘盐并且每周都能够食用富含碘的食物

    public String O10702 = "O10702";// 葵花橄榄调和油

    public String O10705 = "O10705";// 橄榄油

    public String O10708 = "O10708";// 葡萄籽油

    public String O10709 = "O10709";// 亚麻籽油/紫苏籽油

    public String O10711 = "O10711";// 金龙鱼1:1:1调和油

    public String O10902 = "O10902";// 勾芡

    public String O10903 = "O10903";// 几乎不放油

    public String O10904 = "O10904";// 清淡

    public String O10905 = "O10905";// 高油

    public String O10906 = "O10906";// 高糖

    public String O10907 = "O10907";// 高盐

    public String O80113 = "O80113";// 粗粮肉比例

    public String O80209 = "O80209";// 例瘦肉比例

    public String O80311 = "O80311";// 深色蔬菜比例

    public String O80312 = "O80312";// 菌类比例

    public String O80313 = "O80313";// 藻类比例

    public String O90108 = "O90108";// 深海鱼比

    public String O90101 = "O90101";// 蛋

    public String O90102 = "O90102";// 全脂奶及奶制品

    public String O90103 = "O90103";// 脱脂奶及奶制品

    public String O90104 = "O90104";// 豆类

    public String O90105 = "O90105";// 鱼

    public String O90106 = "O90106";// 坚果

    public String O90107 = "O90107";// 动物内脏

    public String O80101 = "O80101";// 米饭

    public String O80102 = "O80102";// 粥

    public String O80103 = "O80103";// 面条

    public String O80104 = "O80104";// 馒头

    public String O80105 = "O80105";// 面包

    public String O80106 = "O80106";// 烙饼

    public String O80107 = "O80107";// 土豆

    public String O80109 = "O80109";// 包子

    public String O80110 = "O80110";// 饺子

    public String O80111 = "O80111";// 汉堡

    public String O80112 = "O80112";// 披萨

    public String O90301 = "O90301";// 燕窝

    public String O90302 = "O90302";// 海参

    public String O90303 = "O90303";// 蜂蜜

    public String O90201 = "O90201";// 饼干

    public String O90202 = "O90202";// 蛋糕

    public String O90203 = "O90203";// 巧克力

    public String O90204 = "O90204";// 蜜饯

    public String O90205 = "O90205";// 糖果

    public String O90206 = "O90206";// 高油高糖面包

    public String O90207 = "O90207";// 海苔

    public String O90208 = "O90208";// 膨化食品

    public String O90209 = "O90209";// 雪糕

    public String O90210 = "O90210";// 饮料

    private LifeStyleConstant() {
        optionMap = new HashMap<String, Option>();
        optionMap.put("O10101", new Option("O10101", "肉食型", true));
        optionMap.put("O10102", new Option("O10102", "偏肉食型", true));
        optionMap.put("O10103", new Option("O10103", "荤素搭配型", false));
        optionMap.put("O10104", new Option("O10104", "不吃鸡肉", false));
        optionMap.put("O10105", new Option("O10105", "不吃牛羊肉", false));
        optionMap.put("O10106", new Option("O10106", "不吃猪肉", false));
        optionMap.put("O10107", new Option("O10107", "不吃鱼虾等水产品", false));
        optionMap.put("O10108", new Option("O10108", "全素食型", true));
        optionMap.put("O10109", new Option("O10109", "偏素食", true));
        optionMap.put("O10110", new Option("O10110", "蛋奶素", true));
        optionMap.put("O10201", new Option("O10201", "一日三餐规律", false));
        optionMap.put("O10202", new Option("O10202", "三餐不规律", true));
        optionMap.put("O10203", new Option("O10203", "少食多餐", false));
        optionMap.put("O10301", new Option("O10301", "早餐", false));
        optionMap.put("O10302", new Option("O10302", "上午加餐", false));
        optionMap.put("O10303", new Option("O10303", "中餐", false));
        optionMap.put("O10304", new Option("O10304", "下午加餐", false));
        optionMap.put("O10305", new Option("O10305", "晚餐", false));
        optionMap.put("O10306", new Option("O10306", "夜宵", false));
        optionMap.put("O10401", new Option("O10401", "进餐时间5分钟之内", true));
        optionMap.put("O10402", new Option("O10402", "进餐时间5-10分钟", true));
        optionMap.put("O10403", new Option("O10403", "进餐时间10-20分钟", false));
        optionMap.put("O10404", new Option("O10404", "进餐时间20-30分钟", false));
        optionMap.put("O10405", new Option("O10405", "进餐时间30分钟以上", false));
        optionMap.put("O10501", new Option("O10501", "从不在外用餐", false));
        optionMap.put("O10502", new Option("O10502", "每周在外用餐1-3次", false));
        optionMap.put("O10503", new Option("O10503", "每周在外用餐4-6次", true));
        optionMap.put("O10504", new Option("O10504", "每周在外用餐7次以上", true));
        optionMap.put("O10601", new Option("O10601", "每餐大约五分饱", true));
        optionMap.put("O10602", new Option("O10602", "每餐大约六分饱", true));
        optionMap.put("O10603", new Option("O10603", "每餐大约七分饱", false));
        optionMap.put("O10604", new Option("O10604", "每餐大约八分饱", false));
        optionMap.put("O10605", new Option("O10605", "每餐大约九分饱", true));
        optionMap.put("O10606", new Option("O10606", "每餐大约十分饱", true));
        optionMap.put("O10701", new Option("O10701", "大豆调和油", false));
        optionMap.put("O10702", new Option("O10702", "葵花橄榄调和油", false));
        optionMap.put("O10703", new Option("O10703", "豆油", false));
        optionMap.put("O10704", new Option("O10704", "葵花籽油", false));
        optionMap.put("O10705", new Option("O10705", "橄榄油", false));
        optionMap.put("O10706", new Option("O10706", "玉米油", false));
        optionMap.put("O10707", new Option("O10707", "花生油 ", false));
        optionMap.put("O10708", new Option("O10708", "葡萄籽油  ", false));
        optionMap.put("O10709", new Option("O10709", "亚麻籽油/紫苏籽油  ", false));
        optionMap.put("O10710", new Option("O10710", "核桃油  ", false));
        optionMap.put("O10711", new Option("O10711", "金龙鱼1:1:1调和油  ", false));
        optionMap.put("O10801", new Option("O10801", "炒菜  ", false));
        optionMap.put("O10802", new Option("O10802", "蒸煮  ", false));
        optionMap.put("O10803", new Option("O10803", "煲炖  ", false));
        optionMap.put("O10804", new Option("O10804", "煎炸  ", true));
        optionMap.put("O10805", new Option("O10805", "烤  ", true));
        optionMap.put("O10806", new Option("O10806", "凉拌  ", false));
        optionMap.put("O10901", new Option("O10901", "无特殊烹饪习惯  ", true));
        optionMap.put("O10902", new Option("O10902", "勾芡  ", true));
        optionMap.put("O10903", new Option("O10903", "几乎不放油  ", true));
        optionMap.put("O10904", new Option("O10904", "清淡  ", false));
        optionMap.put("O10905", new Option("O10905", "高油", true));
        optionMap.put("O10906", new Option("O10906", "高糖", true));
        optionMap.put("O10907", new Option("O10907", "高盐", true));
        optionMap.put("O11001", new Option("O11001", "不使用加碘盐或不能保证每周都能食用富含碘的食物", false));
        optionMap.put("O11002", new Option("O11002", "使用加碘盐并且每周都能够食用富含碘的食物", false));
        optionMap.put("O20101", new Option("O20101", "几乎不运动", true));
        optionMap.put("O20102", new Option("O20102", "1～2次/周", true));
        optionMap.put("O20103", new Option("O20103", "3～5次/周", false));
        optionMap.put("O20104", new Option("O20104", ">5次/周", false));
        optionMap.put("O20201", new Option("O20201", "散步", false));
        optionMap.put("O20202", new Option("O20202", "慢跑", false));
        optionMap.put("O20203", new Option("O20203", "游泳", false));
        optionMap.put("O20204", new Option("O20204", "爬楼梯", false));
        optionMap.put("O20205", new Option("O20205", "舞蹈", false));
        optionMap.put("O20206", new Option("O20206", "瑜伽/普拉提", false));
        optionMap.put("O20207", new Option("O20207", "健身操", false));
        optionMap.put("O20208", new Option("O20208", "力量锻炼", false));
        optionMap.put("O20209", new Option("O20209", "太极拳", false));
        optionMap.put("O20401", new Option("O20401", "平均每次＜30分钟", false));
        optionMap.put("O20402", new Option("O20402", "平均每次30～60分钟", false));
        optionMap.put("O20403", new Option("O20403", "平均每次＞60分钟", false));
        optionMap.put("O20501", new Option("O20501", "晨起", false));
        optionMap.put("O20502", new Option("O20502", "早餐后", false));
        optionMap.put("O20503", new Option("O20503", "上午两餐之间", false));
        optionMap.put("O20504", new Option("O20504", "午餐后", false));
        optionMap.put("O20505", new Option("O20505", "下午两餐之间", false));
        optionMap.put("O20506", new Option("O20506", "晚餐后", false));
        optionMap.put("O30101", new Option("O30101", "近期睡眠好", false));
        optionMap.put("O30102", new Option("O30102", "近期睡眠一般", true));
        optionMap.put("O30103", new Option("O30103", "近期睡眠差", true));
        optionMap.put("O30201", new Option("O30201", "入睡困难", true));
        optionMap.put("O30202", new Option("O30202", "夜间易醒或早醒", true));
        optionMap.put("O30203", new Option("O30203", "夜起", true));
        optionMap.put("O30204", new Option("O30204", "多梦或噩梦中惊醒", true));
        optionMap.put("O30205", new Option("O30205", "熟睡时间短", true));
        optionMap.put("O30206", new Option("O30206", "夜间去厕所", true));
        optionMap.put("O30207", new Option("O30207", "呼吸不畅", true));
        optionMap.put("O30208", new Option("O30208", "感觉冷", true));
        optionMap.put("O30209", new Option("O30209", "感觉热", true));
        optionMap.put("O30210", new Option("O30210", "疼痛不适", true));
        optionMap.put("O30301", new Option("O30301", "平均睡眠时间＜5小时", true));
        optionMap.put("O30302", new Option("O30302", "平均睡眠时间5～7小时", true));
        optionMap.put("O30303", new Option("O30303", "平均睡眠时间7～9小时", false));
        optionMap.put("O30304", new Option("O30304", "平均睡眠时间＞9小时", true));
        optionMap.put("O40101", new Option("O40101", "情绪低落，压抑或沮丧", true));
        optionMap.put("O40102", new Option("O40102", "对人对事缺乏热情", true));
        optionMap.put("O40103", new Option("O40103", "注意力集中有困难", true));
        optionMap.put("O40104", new Option("O40104", "情绪激动或生气", true));
        optionMap.put("O40105", new Option("O40105", "精神紧张，很难放松", true));
        optionMap.put("O40106", new Option("O40106", "发脾气，没有耐性", true));
        optionMap.put("O40107", new Option("O40107", "焦虑不安、心烦意乱", true));
        optionMap.put("O40108", new Option("O40108", "心态很好，没有任何不良情绪", false));
        optionMap.put("O50101", new Option("O50101", "孕前经常饮酒，怀孕后不饮酒", false));
        optionMap.put("O50102", new Option("O50102", "孕前偶尔饮酒，怀孕后不饮酒", false));
        optionMap.put("O50103", new Option("O50103", "一直不饮酒", false));
        optionMap.put("O50104", new Option("O50104", "少量红酒", true));
        optionMap.put("O50105", new Option("O50105", "一直饮酒", true));
        optionMap.put("O50201", new Option("O50201", "不吸烟", false));
        optionMap.put("O50202", new Option("O50202", "吸烟", true));
        optionMap.put("O50203", new Option("O50203", "吸烟，已戒", true));
        optionMap.put("O50204", new Option("O50204", "被动吸烟（每天累计15分钟以上，且每周1天以上）", true));
        optionMap.put("O60101", new Option("O60101", "无或很少", false));
        optionMap.put("O60102", new Option("O60102", "噪音、震动", true));
        optionMap.put("O60103", new Option("O60103", "电磁辐射", true));
        optionMap.put("O60104", new Option("O60104", "二手烟", true));
        optionMap.put("O60105", new Option("O60105", "粉尘", true));
        optionMap.put("O60106", new Option("O60106", "杀虫剂", true));
        optionMap.put("O60107", new Option("O60107", "干洗衣物", true));
        optionMap.put("O60108", new Option("O60108", "化学污染（毒素环境、重金属）", true));
        optionMap.put("O60109", new Option("O60109", "空气污染", true));
        optionMap.put("O60110", new Option("O60110", "建筑装修污染", true));
        optionMap.put("O60111", new Option("O60111", "烹饪油烟", true));
        optionMap.put("O60112", new Option("O60112", "饮用非过滤水", true));
        optionMap.put("O60113", new Option("O60113", "通风不良", true));
        optionMap.put("O60114", new Option("O60114", "牙齿金属填充物（银汞合金、镍铬合金）", true));
        optionMap.put("O60201", new Option("O60201", "几乎不晒太阳", true));
        optionMap.put("O60202", new Option("O60202", "很少晒太阳", true));
        optionMap.put("O60203", new Option("O60203", "每天晒0.5-2小时太阳", false));

        itemMap = new HashMap<String, String>();
        itemMap.put(diet, "饮食");
        itemMap.put(sport, "运动");
        itemMap.put(sleep, "睡眠");
        itemMap.put(mental, "心理");
        itemMap.put(drinking, "烟酒");
        itemMap.put(environment, "环境");
        itemMap.put(breakfast, "早餐");
        itemMap.put(morningMeal, "上午加餐");
        itemMap.put(lunch, "午餐");
        itemMap.put(afternoonSnacks, "下午加餐");
        itemMap.put(dinner, "晚餐");
        itemMap.put(supper, "夜宵");
        itemMap.put(cbr, "碳水化合物摄入量");
        itemMap.put(protid, "蛋白质摄入量");
        itemMap.put(fat, "脂肪摄入量");
        itemMap.put(cbrratio, "碳水化合物供能比");
        itemMap.put(protidratio, "蛋白质供能比");
        itemMap.put(fatratio, "脂肪供能比");
        itemMap.put(cereal, "谷类");
        itemMap.put(potatoes, "薯类");
        itemMap.put(veg, "蔬菜");

        itemMap.put(fruit, "水果");
        itemMap.put(fme, "鱼肉蛋");
        itemMap.put(milk, "乳类");
        itemMap.put(bean, "大豆类");
        itemMap.put(nut, "坚果");
        itemMap.put(water, "饮水");
        itemMap.put(czl, "粗杂粮占比");
        itemMap.put(vegetables, "深色蔬菜占比");
        itemMap.put(goodProtid, "优质蛋白质");
        itemMap.put(organs, "动物内脏");
        itemMap.put(oils, "用油量");
        itemMap.put(hs, "海参");
        itemMap.put(yw, "燕窝");
        itemMap.put(bgdx, "饼干点心");
        itemMap.put(dg, "蛋糕");
        itemMap.put(qsmb, "起酥面包");
        itemMap.put(ht, "海苔");
        itemMap.put(tg, "糖果");
        itemMap.put(mj, "蜜饯");
        itemMap.put(qkl, "巧克力");
        itemMap.put(tsyl, "碳酸饮料");
        itemMap.put(lyxg, "冷饮雪糕");
        itemMap.put(phsp, "膨化食品");
        itemMap.put(actual_totalEnergy, "实际一日总能量");
        itemMap.put(recommend_totalEnergy, "推荐一日总能量");
        itemMap.put(lacking, "膳食中可能不足的营养素");
        itemMap.put(fungus, "菌类");
        itemMap.put(algae, "藻类");
        itemMap.put(fm, "蜂蜜");

        itemMap.put(B00509, "膳食纤维");
        itemMap.put(B00510, "维生素A");
        itemMap.put(B00511, "维生素C");
        itemMap.put(B00512, "维生素E");
        itemMap.put(B00515, "烟酸");
        itemMap.put(B00516, "叶酸");
        itemMap.put(B00517, "维生素B6");
        itemMap.put(B00518, "维生素B12");
        itemMap.put(B00519, "钙");
        itemMap.put(B00520, "钾");
        itemMap.put(B00521, "镁");
        itemMap.put(B00522, "铁");
        itemMap.put(B00523, "锌");
        itemMap.put(B00524, "关于碘的摄入状态调查");
        itemMap.put(B00525, "ω-3脂肪酸");

        itemMap.put(B00537, "您的膳食类型");
        itemMap.put(B00538, "您在孕期的用餐频率为");
        itemMap.put(B00539, "您在孕期的就餐时间为");
        itemMap.put(B00540, "您在孕期的平均用餐时间为");
        itemMap.put(B00541, "您在孕期的在外用餐频率为");
        itemMap.put(B00542, "您在孕期的进餐感受通常为");
        itemMap.put(B00543, "您在孕期的烹饪用油多为");
        itemMap.put(B00544, "您在孕期常用的烹饪方式有");
        itemMap.put(B00545, "您在孕期常用的烹饪习惯有");
        itemMap.put(B00546, "您的运动频率为");
        itemMap.put(B00547, "您常采用的运动锻炼方式");
        itemMap.put(B00549, "您每次锻炼多长时间");
        itemMap.put(B00550, "运动时间");
        itemMap.put(B00551, "近期睡眠如何");
        itemMap.put(B00552, "您睡眠不好的主要表现");
        itemMap.put(B00553, "您每天平均睡眠时间");
        itemMap.put(B00554, "您最近两周是否经常几种情绪");
        itemMap.put(B00555, "关于饮酒");
        itemMap.put(B00556, "关于主被动吸烟");
        itemMap.put(B00557, "您的工作/生活场所经常会接触到哪些有害物质");
        itemMap.put(B00558, "您在孕期平时晒太阳的时间");
        itemMap.put(B00576, "荤菜占比");

        problemMap = new HashMap<String, List<Problem>>();
        List<Problem> diets = new ArrayList<>();
        diets.add(new Problem("p101", "", radio));
        diets.add(new Problem("p102", "", radio));
        diets.add(new Problem("p104", "", radio));
        diets.add(new Problem("p105", "", radio));
        diets.add(new Problem("p106", "", radio));
        diets.add(new Problem("p107", "烹饪用油多为:", checkbox));
        diets.add(new Problem("p108", "烹饪方式多为:", checkbox));
        diets.add(new Problem("p109", "烹饪习惯:", checkbox));
        problemMap.put(diet, diets);

        List<Problem> sports = new ArrayList<>();
        sports.add(new Problem("p201", "", radio));
        sports.add(new Problem("p202", "常用运动方式为:", checkbox));
        sports.add(new Problem("p204", "", radio));
        sports.add(new Problem("p205", "", checkbox));
        problemMap.put(sport, sports);

        List<Problem> sleeps = new ArrayList<>();
        sleeps.add(new Problem("p301", "", radio));
        sleeps.add(new Problem("p302", "睡眠时:", checkbox));
        sleeps.add(new Problem("p303", "", radio));
        problemMap.put(sleep, sleeps);

        List<Problem> mentals = new ArrayList<>();
        mentals.add(new Problem("p401", "", checkbox));
        problemMap.put(mental, mentals);

        List<Problem> drinkings = new ArrayList<>();
        drinkings.add(new Problem("p501", "", radio));
        drinkings.add(new Problem("p502", "", radio));
        problemMap.put(drinking, drinkings);

        List<Problem> environments = new ArrayList<>();
        environments.add(new Problem("p601", "经常会接触到:", checkbox));
        environments.add(new Problem("p602", "", radio));
        problemMap.put(environment, environments);

        // 绑定问卷食物与食物库食物的关系
        foodElementMap = new HashMap<String, String>();
        foodElementMap.put("O80101", "5");
        foodElementMap.put("O80102", "6");
        foodElementMap.put("O80103", "9");
        foodElementMap.put("O80104", "8");
        foodElementMap.put("O80105", "10");
        foodElementMap.put("O80106", "11");
        foodElementMap.put("O80107", "12");
        foodElementMap.put("O80108", "7");
        foodElementMap.put("O80109", "13");
        foodElementMap.put("O80110", "14");
        foodElementMap.put("O80111", "15");
        foodElementMap.put("O80112", "16");

        foodElementMap.put("O80401", "28");
        foodElementMap.put("O80402", "29");
        foodElementMap.put("O80403", "30");
        foodElementMap.put("O80404", "31");
        foodElementMap.put("O80405", "32");
        foodElementMap.put("O80406", "33");
        foodElementMap.put("O80407", "62");
        foodElementMap.put("O80408", "27");

        foodElementMap.put("O90101", "22");
        foodElementMap.put("O90102", "34");
        foodElementMap.put("O90103", "35");
        foodElementMap.put("O90104", "36");
        foodElementMap.put("O90106", "37");
        foodElementMap.put("O90107", "38");

        foodElementMap.put("O90201", "39");
        foodElementMap.put("O90202", "40");
        foodElementMap.put("O90203", "41");
        foodElementMap.put("O90204", "42");
        foodElementMap.put("O90205", "43");
        foodElementMap.put("O90206", "44");
        foodElementMap.put("O90207", "45");
        foodElementMap.put("O90208", "46");
        foodElementMap.put("O90209", "47");
        foodElementMap.put("O90210", "48");

        foodElementMap.put("O90301", "50");
        foodElementMap.put("O90302", "63");
        foodElementMap.put("O90303", "49");

        actualMap = new HashMap<String, Double>();
        actualMap.put("O80101", 0.4);
        actualMap.put("O80102", 0.1);
        actualMap.put("O80103", 0.7);
        actualMap.put("O80104", 0.7);
        actualMap.put("O80105", 0.7);
        actualMap.put("O80106", 0.7);
        actualMap.put("O80108", 0.7);
        actualMap.put("O80109", 0.7);
        actualMap.put("O80110", 0.7);
        actualMap.put("O80111", 0.7);
        actualMap.put("O80112", 0.7);

        actualMap.put("O80401", 0.8);
        actualMap.put("O80402", 0.6);
        actualMap.put("O80403", 0.7);
        actualMap.put("O80404", 0.8);
        actualMap.put("O80405", 0.9);
        actualMap.put("O80406", 0.9);
        actualMap.put("O80407", 1.0);
        actualMap.put("O80408", 0.9);

        cereals = new ArrayList<String>();
        cereals.add(O80101);
        cereals.add(O80102);
        cereals.add(O80103);
        cereals.add(O80104);
        cereals.add(O80105);
        cereals.add(O80106);
        cereals.add(O80109);
        cereals.add(O80110);
        cereals.add(O80111);
        cereals.add(O80112);
    };

    public static LifeStyleConstant getInstance() {
        if (lifeStyleConstant == null) {
            lifeStyleConstant = new LifeStyleConstant();
        }
        return lifeStyleConstant;
    }

    public Option getLifeStyleOption(String key) {
        return optionMap.get(key);
    }

    public String getLifeStyleItem(String key) {
        return itemMap.get(key);
    }

    public List<Problem> getLifeStyleProblem(String key) {
        return problemMap.get(key);
    }

    public String getFoodElement(String key) {
        return foodElementMap.get(key);
    }

    public Double getActualWeight(String key) {
        return actualMap.get(key);
    }

    public List<String> getCerealFood() {
        return cereals;
    }
}

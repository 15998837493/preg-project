
package com.mnt.preg.examitem.util.life;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

import com.mnt.health.core.exception.ServiceException;
import com.mnt.preg.examitem.pojo.EvaluateFoodLibraryPojo;
import com.mnt.preg.examitem.pojo.LifeStyleQuestionAnswerPojo;
import com.mnt.preg.main.results.ResultCode;

/**
 * 
 * 营养分析
 * 
 * @author mnt_zhangjing
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-3-7 mnt_zhangjing 初版
 */
public class NutritionAnalysis {

    public static final Logger LOGGER = LoggerFactory.getLogger(NutritionAnalysis.class);

    // 将问卷记录按问题分类
    protected Map<String, List<LifeStyleQuestionAnswerPojo>> questionMap;

    // 将问卷记录按问题分类
    protected Map<String, EvaluateFoodLibraryPojo> foodLibraryMap;

    // 饮食相关问题记录
    protected List<LifeStyleQuestionAnswerPojo> dietQuestionAnswerBos;

    // 运动相关问题记录
    protected List<LifeStyleQuestionAnswerPojo> sportQuestionAnswerBos;

    // 睡眠相关问题记录
    protected List<LifeStyleQuestionAnswerPojo> sleepQuestionAnswerBos;

    // 心理相关问题记录
    protected List<LifeStyleQuestionAnswerPojo> mentalQuestionAnswerBos;

    // 烟酒相关问题记录
    protected List<LifeStyleQuestionAnswerPojo> drinkingQuestionAnswerBos;

    // 环境相关问题记录
    protected List<LifeStyleQuestionAnswerPojo> environmentQuestionAnswerBos;

    // 水相关问题记录
    protected List<LifeStyleQuestionAnswerPojo> waterQuestionAnswerBos;

    // 不按比例和频率计算的
    protected List<LifeStyleQuestionAnswerPojo> classes1;

    // 按比例计算的
    protected List<LifeStyleQuestionAnswerPojo> classes2;

    // 按频率计算的
    protected List<LifeStyleQuestionAnswerPojo> classes3;

    protected LifeStyleConstant lifeStyleConstant;

    protected String coarseGrains = "";

    protected String glv = "";

    protected String hs = "";// 海参

    protected String fm = "";// 蜂蜜

    protected String yw = "";// 燕窝

    protected String bgdx = "";// 饼干点心

    protected String dg = "";// 蛋糕

    protected String qsmb = "";// 起酥面包

    protected String ht = "";// 海苔

    protected String tg = "";// 糖果

    protected String mj = "";// 蜜饯

    protected String qkl = "";// 巧克力

    protected String tsyl = "";// 碳酸饮料

    protected String lyxg = "";// 冷饮雪糕

    protected String phsp = "";// 膨化食品

    protected String potatoes = "";// 薯类

    protected String fungus = "";// 菌类

    protected String algae = "";// 藻类

    public NutritionAnalysis(List<LifeStyleQuestionAnswerPojo> lifeStyleQuestionAnswerBos,
            List<EvaluateFoodLibraryPojo> evaluateFoodLibraryVos) {
        lifeStyleConstant = LifeStyleConstant.getInstance();
        dietQuestionAnswerBos = new ArrayList<LifeStyleQuestionAnswerPojo>();
        sportQuestionAnswerBos = new ArrayList<LifeStyleQuestionAnswerPojo>();
        sleepQuestionAnswerBos = new ArrayList<LifeStyleQuestionAnswerPojo>();
        mentalQuestionAnswerBos = new ArrayList<LifeStyleQuestionAnswerPojo>();
        drinkingQuestionAnswerBos = new ArrayList<LifeStyleQuestionAnswerPojo>();
        environmentQuestionAnswerBos = new ArrayList<LifeStyleQuestionAnswerPojo>();
        waterQuestionAnswerBos = new ArrayList<LifeStyleQuestionAnswerPojo>();
        questionMap = new HashMap<String, List<LifeStyleQuestionAnswerPojo>>();
        classes1 = new ArrayList<LifeStyleQuestionAnswerPojo>();
        classes2 = new ArrayList<LifeStyleQuestionAnswerPojo>();
        classes3 = new ArrayList<LifeStyleQuestionAnswerPojo>();
        for (LifeStyleQuestionAnswerPojo lifeStyleQuestionAnswerBo : lifeStyleQuestionAnswerBos) {
            if (lifeStyleQuestionAnswerBo.getProblemId().contains("p1")) {
                dietQuestionAnswerBos.add(lifeStyleQuestionAnswerBo);
            }
            if (lifeStyleQuestionAnswerBo.getProblemId().contains("p2")) {
                sportQuestionAnswerBos.add(lifeStyleQuestionAnswerBo);
            }
            if (lifeStyleQuestionAnswerBo.getProblemId().contains("p3")) {
                sleepQuestionAnswerBos.add(lifeStyleQuestionAnswerBo);
            }
            if (lifeStyleQuestionAnswerBo.getProblemId().contains("p4")) {
                mentalQuestionAnswerBos.add(lifeStyleQuestionAnswerBo);
            }
            if (lifeStyleQuestionAnswerBo.getProblemId().contains("p5")) {
                drinkingQuestionAnswerBos.add(lifeStyleQuestionAnswerBo);
            }
            if (lifeStyleQuestionAnswerBo.getProblemId().contains("p6")) {
                environmentQuestionAnswerBos.add(lifeStyleQuestionAnswerBo);
            }
            if (lifeStyleQuestionAnswerBo.getProblemId().contains("p7")) {
                waterQuestionAnswerBos.add(lifeStyleQuestionAnswerBo);
            }
            if (lifeStyleQuestionAnswerBo.getProblemId().contains("p801")
                    || lifeStyleQuestionAnswerBo.getProblemId().contains("p804")) {
                classes1.add(lifeStyleQuestionAnswerBo);
            }
            if (lifeStyleQuestionAnswerBo.getProblemId().contains("p802")
                    || lifeStyleQuestionAnswerBo.getProblemId().contains("p803")) {
                classes2.add(lifeStyleQuestionAnswerBo);
            }
            if (lifeStyleQuestionAnswerBo.getProblemId().contains("p9")) {
                classes3.add(lifeStyleQuestionAnswerBo);
            }

            if (lifeStyleConstant.O80113.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                coarseGrains = lifeStyleQuestionAnswerBo.getAddvalue();
            }
            if (lifeStyleConstant.O80311.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                glv = lifeStyleQuestionAnswerBo.getAddvalue();
            }
            if (lifeStyleConstant.O80312.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                fungus = lifeStyleQuestionAnswerBo.getAddvalue();
            }
            if (lifeStyleConstant.O80313.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                algae = lifeStyleQuestionAnswerBo.getAddvalue();
            }
            if (lifeStyleConstant.O90301.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                yw = String.valueOf((int) Math.rint(Double.parseDouble(lifeStyleQuestionAnswerBo.getAddvalue())
                        * FrequencyUtil
                                .transform(lifeStyleQuestionAnswerBo.getFrequency())));
            }
            if (lifeStyleConstant.O90302.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                hs = String.valueOf((int) Math.rint(Double.parseDouble(lifeStyleQuestionAnswerBo.getAddvalue())
                        * FrequencyUtil
                                .transform(lifeStyleQuestionAnswerBo.getFrequency())));
            }
            if (lifeStyleConstant.O90303.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                fm = String.valueOf((int) Math.rint(Double.parseDouble(lifeStyleQuestionAnswerBo.getAddvalue())
                        * FrequencyUtil
                                .transform(lifeStyleQuestionAnswerBo.getFrequency())));
            }
            if (lifeStyleConstant.O90201.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                bgdx = String.valueOf((int) Math.rint(Double.parseDouble(lifeStyleQuestionAnswerBo.getAddvalue())
                        * FrequencyUtil
                                .transform(lifeStyleQuestionAnswerBo.getFrequency())));
            }
            if (lifeStyleConstant.O90202.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                dg = String.valueOf((int) Math.rint(Double.parseDouble(lifeStyleQuestionAnswerBo.getAddvalue())
                        * FrequencyUtil
                                .transform(lifeStyleQuestionAnswerBo.getFrequency())));
            }
            if (lifeStyleConstant.O90203.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                qkl = String.valueOf((int) Math.rint(Double.parseDouble(lifeStyleQuestionAnswerBo.getAddvalue())
                        * FrequencyUtil
                                .transform(lifeStyleQuestionAnswerBo.getFrequency())));
            }
            if (lifeStyleConstant.O90204.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                mj = String.valueOf((int) Math.rint(Double.parseDouble(lifeStyleQuestionAnswerBo.getAddvalue())
                        * FrequencyUtil
                                .transform(lifeStyleQuestionAnswerBo.getFrequency())));
            }
            if (lifeStyleConstant.O90205.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                tg = String.valueOf((int) Math.rint(Double.parseDouble(lifeStyleQuestionAnswerBo.getAddvalue())
                        * FrequencyUtil
                                .transform(lifeStyleQuestionAnswerBo.getFrequency())));
            }
            if (lifeStyleConstant.O90206.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                qsmb = String.valueOf((int) Math.rint(Double.parseDouble(lifeStyleQuestionAnswerBo.getAddvalue())
                        * FrequencyUtil
                                .transform(lifeStyleQuestionAnswerBo.getFrequency())));
            }
            if (lifeStyleConstant.O90207.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                ht = String.valueOf((int) Math.rint(Double.parseDouble(lifeStyleQuestionAnswerBo.getAddvalue())
                        * FrequencyUtil
                                .transform(lifeStyleQuestionAnswerBo.getFrequency())));
            }
            if (lifeStyleConstant.O90208.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                phsp = String.valueOf((int) Math.rint(Double.parseDouble(lifeStyleQuestionAnswerBo.getAddvalue())
                        * FrequencyUtil
                                .transform(lifeStyleQuestionAnswerBo.getFrequency())));
            }
            if (lifeStyleConstant.O90209.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                lyxg = String.valueOf((int) Math.rint(Double.parseDouble(lifeStyleQuestionAnswerBo.getAddvalue())
                        * FrequencyUtil
                                .transform(lifeStyleQuestionAnswerBo.getFrequency())));
            }
            if (lifeStyleConstant.O90210.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                tsyl = String.valueOf((int) Math.rint(Double.parseDouble(lifeStyleQuestionAnswerBo.getAddvalue())
                        * FrequencyUtil
                                .transform(lifeStyleQuestionAnswerBo.getFrequency())));
            }
            if (CollectionUtils.isEmpty(questionMap.get(lifeStyleQuestionAnswerBo.getProblemId()))) {
                List<LifeStyleQuestionAnswerPojo> tempAnswerBos = new ArrayList<LifeStyleQuestionAnswerPojo>();
                tempAnswerBos.add(lifeStyleQuestionAnswerBo);
                questionMap.put(lifeStyleQuestionAnswerBo.getProblemId(), tempAnswerBos);
            } else {
                questionMap.get(lifeStyleQuestionAnswerBo.getProblemId()).add(lifeStyleQuestionAnswerBo);
            }
        }

        if (CollectionUtils.isNotEmpty(evaluateFoodLibraryVos)) {
            foodLibraryMap = new HashMap<String, EvaluateFoodLibraryPojo>();
            for (EvaluateFoodLibraryPojo evaluateFoodLibraryVo : evaluateFoodLibraryVos) {
                foodLibraryMap.put(evaluateFoodLibraryVo.getFoodId(), evaluateFoodLibraryVo);
            }
        }
    }

    /**
     * 
     * 获取指定食物指定营养素的值
     * 
     * @author mnt_zhangjing
     * @param foodId
     * @param method
     * @return
     */
    public double getContentByFood(String foodId, Method method) {
        try {
            BigDecimal bd = null;
            EvaluateFoodLibraryPojo evaluate = foodLibraryMap.get(foodId);
            if (evaluate == null) {
                return 0;
            }
            bd = (BigDecimal) method.invoke(evaluate);
            if (bd == null) {
                return 0;
            }
            return bd.doubleValue();
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            LOGGER.error("[反射异常]" + e.getMessage());
            throw new ServiceException(ResultCode.ERROR_99999);
        }
    }

    /**
     * 
     * 计算问卷中鱼肉蛋的重量
     * 
     * @author mnt_zhangjing
     * @return
     */
    public double getMeatWeight() {
        double result = 0;
        /**
         * 荤菜平均每天摄入量=【膳食营养状态评问题3】填写克数（g）
         * +【膳食营养状态评问题6中鱼类（不包括深海鱼类）；深海鱼类；蛋类】填写克数（g）×填写频率，
         * 加上【包子饺子；汉堡热狗；披萨馅饼】填写克数（g）×含有鱼肉蛋类的每单位计算百分比（%）的加和
         */
        // 1计算膳食营养状态评问题3填写克数==>p802
        List<LifeStyleQuestionAnswerPojo> p802List = questionMap.get(lifeStyleConstant.p802);
        if (CollectionUtils.isNotEmpty(p802List)) {
            for (LifeStyleQuestionAnswerPojo lifeStyleQuestionAnswerBo : p802List) {
                if (StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getAddvalue())) {
                    if (lifeStyleQuestionAnswerBo.getAddvalue().matches("\\d+(.\\d{1,2})?")) {
                        result = result + Double.parseDouble(lifeStyleQuestionAnswerBo.getAddvalue());
                    }
                }
            }
        }

        // 膳食营养状态评问题6中鱼类；蛋类】填写克数（g）×填写频率==>p901
        result = result + getEgg();
        ResultEntity resultEntity = getFish();
        if (resultEntity != null) {
            result = result + resultEntity.getWeight();
        }

        List<LifeStyleQuestionAnswerPojo> p901List = questionMap.get(lifeStyleConstant.p901);
        if (CollectionUtils.isNotEmpty(p901List)) {
            for (LifeStyleQuestionAnswerPojo lifeStyleQuestionAnswerBo : p901List) {
                if (StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getOptionId())) {
                    if (StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getAddvalue())
                            && StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getFrequency())) {
                        if (lifeStyleConstant.O90101.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                            // 蛋类
                            result = result
                                    + (UnitTransform.transformEgg(lifeStyleQuestionAnswerBo.getAddvalue()) * FrequencyUtil
                                            .transform(lifeStyleQuestionAnswerBo.getFrequency()));
                        }
                        if (lifeStyleConstant.O90105.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                            // 鱼类
                            result = result
                                    + (UnitTransform.transformFinsh(lifeStyleQuestionAnswerBo.getAddvalue()) * FrequencyUtil
                                            .transform(lifeStyleQuestionAnswerBo.getFrequency()));
                        }
                    }

                }
            }
        }

        // 【包子饺子；汉堡热狗；披萨馅饼】填写克数（g）×含有鱼肉蛋类的每单位计算百分比（%）==>p801
        result = result + getMeatInP801();

        return result;
    }

    /**
     * 
     * 计算问卷中蔬菜的重量
     * 
     * @author mnt_zhangjing
     * @return
     */
    public double getVegetablesWeight() {
        double result = 0;
        /**
         * 素菜平均每天摄入量=【膳食营养状态评问题4】填写克数（g）的加和（或份数×100g的加和），
         * 加上【包子饺子；汉堡热狗；披萨馅饼】填写克数（g）×含有蔬菜类的每单位计算百分比（%）的加和
         */
        // 1【膳食营养状态评问题4】填写克数（g）==>p803
        List<LifeStyleQuestionAnswerPojo> p803List = questionMap.get(lifeStyleConstant.p803);
        if (CollectionUtils.isNotEmpty(p803List)) {
            for (LifeStyleQuestionAnswerPojo lifeStyleQuestionAnswerBo : p803List) {
                if (StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getAddvalue())) {
                    if (lifeStyleQuestionAnswerBo.getAddvalue().matches("\\d+(.\\d{1,2})?")) {
                        result = result + Double.parseDouble(lifeStyleQuestionAnswerBo.getAddvalue());
                    }
                }
            }
        }

        // 【包子饺子；汉堡热狗；披萨馅饼】填写克数（g）×含有鱼肉蛋类的每单位计算百分比（%）==>p801
        List<LifeStyleQuestionAnswerPojo> p801List = questionMap.get(lifeStyleConstant.p801);
        if (CollectionUtils.isNotEmpty(p801List)) {
            for (LifeStyleQuestionAnswerPojo lifeStyleQuestionAnswerBo : p801List) {
                if (StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getOptionId())) {
                    if (StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getAddvalue())) {
                        if (lifeStyleConstant.O80109.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                            // 包子
                            ElementRatio ratio = new BugRatio();
                            if (lifeStyleQuestionAnswerBo.getAddvalue().matches("\\d+(.\\d{1,2})?")) {
                                result = result
                                        + (Double.parseDouble(lifeStyleQuestionAnswerBo.getAddvalue()) * ratio
                                                .getVegetablesRatio());
                            }
                        }
                        if (lifeStyleConstant.O80110.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                            // 饺子
                            ElementRatio ratio = new DumplingsRatio();
                            if (lifeStyleQuestionAnswerBo.getAddvalue().matches("\\d+(.\\d{1,2})?")) {
                                result = result
                                        + (Double.parseDouble(lifeStyleQuestionAnswerBo.getAddvalue()) * ratio
                                                .getVegetablesRatio());
                            }
                        }
                        if (lifeStyleConstant.O80111.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                            // 汉堡
                            ElementRatio ratio = new HamburgersRatio();
                            if (lifeStyleQuestionAnswerBo.getAddvalue().matches("\\d+(.\\d{1,2})?")) {
                                result = result
                                        + (Double.parseDouble(lifeStyleQuestionAnswerBo.getAddvalue()) * ratio
                                                .getVegetablesRatio());
                            }
                        }
                        if (lifeStyleConstant.O80112.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                            // 披萨
                            ElementRatio ratio = new PizzaRatio();
                            if (lifeStyleQuestionAnswerBo.getAddvalue().matches("\\d+(.\\d{1,2})?")) {
                                result = result
                                        + (Double.parseDouble(lifeStyleQuestionAnswerBo.getAddvalue()) * ratio
                                                .getVegetablesRatio());
                            }
                        }
                    }

                }
            }
        }
        return result;
    }

    /**
     * 
     * 获取鸡蛋的重量
     * 
     * @author mnt_zhangjing
     * @return
     */
    public double getEgg() {
        double result = 0;
        List<LifeStyleQuestionAnswerPojo> p901List = questionMap.get(lifeStyleConstant.p901);
        if (CollectionUtils.isNotEmpty(p901List)) {
            for (LifeStyleQuestionAnswerPojo lifeStyleQuestionAnswerBo : p901List) {
                if (StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getOptionId())) {
                    if (StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getAddvalue())
                            && StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getFrequency())) {
                        if (lifeStyleConstant.O90101.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                            // 蛋类
                            result = result
                                    + (UnitTransform.transformEgg(lifeStyleQuestionAnswerBo.getAddvalue()) * FrequencyUtil
                                            .transform(lifeStyleQuestionAnswerBo.getFrequency()));
                        }
                    }

                }
            }
        }
        return result;
    }

    /**
     * 
     * 获取全脂奶及奶制品的重量
     * 
     * @author mnt_zhangjing
     * @return
     */
    public double getAMilk() {
        double result = 0;
        List<LifeStyleQuestionAnswerPojo> p901List = questionMap.get(lifeStyleConstant.p901);
        if (CollectionUtils.isNotEmpty(p901List)) {
            for (LifeStyleQuestionAnswerPojo lifeStyleQuestionAnswerBo : p901List) {
                if (StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getOptionId())) {
                    if (StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getAddvalue())
                            && StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getFrequency())) {
                        if (lifeStyleConstant.O90102.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                            // 全脂奶及奶制品
                            result = result
                                    + (UnitTransform.transformAmilk(lifeStyleQuestionAnswerBo.getAddvalue()) * FrequencyUtil
                                            .transform(lifeStyleQuestionAnswerBo.getFrequency()));
                        }
                    }

                }
            }
        }
        return result;
    }

    /**
     * 
     * 获取脱脂奶及奶制品的重量
     * 
     * @author mnt_zhangjing
     * @return
     */
    public double getTMilk() {
        double result = 0;
        List<LifeStyleQuestionAnswerPojo> p901List = questionMap.get(lifeStyleConstant.p901);
        if (CollectionUtils.isNotEmpty(p901List)) {
            for (LifeStyleQuestionAnswerPojo lifeStyleQuestionAnswerBo : p901List) {
                if (StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getOptionId())) {
                    if (StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getAddvalue())
                            && StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getFrequency())) {
                        if (lifeStyleConstant.O90103.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                            // 脱脂奶及奶制品
                            result = result
                                    + (UnitTransform.transformMilk(lifeStyleQuestionAnswerBo.getAddvalue()) * FrequencyUtil
                                            .transform(lifeStyleQuestionAnswerBo.getFrequency()));
                        }
                    }

                }
            }
        }
        return result;
    }

    /**
     * 
     * 获取豆类的重量
     * 
     * @author mnt_zhangjing
     * @return
     */
    public double getBeans() {
        double result = 0;
        List<LifeStyleQuestionAnswerPojo> p901List = questionMap.get(lifeStyleConstant.p901);
        if (CollectionUtils.isNotEmpty(p901List)) {
            for (LifeStyleQuestionAnswerPojo lifeStyleQuestionAnswerBo : p901List) {
                if (StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getOptionId())) {
                    if (StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getAddvalue())
                            && StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getFrequency())) {
                        if (lifeStyleConstant.O90104.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                            result = result
                                    + (UnitTransform.transformBeans(lifeStyleQuestionAnswerBo.getAddvalue()) * FrequencyUtil
                                            .transform(lifeStyleQuestionAnswerBo.getFrequency()));
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 
     * 获取坚果类的重量
     * 
     * @author mnt_zhangjing
     * @return
     */
    public double getNut() {
        double result = 0;
        List<LifeStyleQuestionAnswerPojo> p901List = questionMap.get(lifeStyleConstant.p901);
        if (CollectionUtils.isNotEmpty(p901List)) {
            for (LifeStyleQuestionAnswerPojo lifeStyleQuestionAnswerBo : p901List) {
                if (StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getOptionId())) {
                    if (StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getAddvalue())
                            && StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getFrequency())) {
                        if (lifeStyleConstant.O90106.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                            result = result
                                    + (UnitTransform.transformNut(lifeStyleQuestionAnswerBo.getAddvalue()) * FrequencyUtil
                                            .transform(lifeStyleQuestionAnswerBo.getFrequency()));
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 
     * 获取内脏类的重量
     * 
     * @author mnt_zhangjing
     * @return
     */
    public double getOrgans() {
        double result = 0;
        List<LifeStyleQuestionAnswerPojo> p901List = questionMap.get(lifeStyleConstant.p901);
        if (CollectionUtils.isNotEmpty(p901List)) {
            for (LifeStyleQuestionAnswerPojo lifeStyleQuestionAnswerBo : p901List) {
                if (StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getOptionId())) {
                    if (StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getAddvalue())
                            && StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getFrequency())) {
                        if (lifeStyleConstant.O90107.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                            // 全脂奶及奶制品
                            result = result
                                    + (UnitTransform.transformOrgans(lifeStyleQuestionAnswerBo.getAddvalue()) * FrequencyUtil
                                            .transform(lifeStyleQuestionAnswerBo.getFrequency()));
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 
     * 获取深海鱼的总量
     * 
     * @author mnt_zhangjing
     * @return
     */
    public double getDeepFish() {
        double result = 0;
        ResultEntity resultEntity = getFish();
        if (resultEntity != null) {
            result = (double) (Math.round(resultEntity.getWeight() * resultEntity.getRatio()));
        }
        return result;
    }

    /**
     * 
     * 获得鱼的重量信息
     * 
     * @author mnt_zhangjing
     * @return
     */
    public ResultEntity getFish() {
        ResultEntity resultEntity = null;
        // 膳食营养状态评问题6中鱼类；蛋类】填写克数（g）×填写频率==>p901
        List<LifeStyleQuestionAnswerPojo> p901List = questionMap.get(lifeStyleConstant.p901);
        if (CollectionUtils.isNotEmpty(p901List)) {
            double ratio = 0;
            String addvalue = "";
            String frequency = "";
            for (LifeStyleQuestionAnswerPojo lifeStyleQuestionAnswerBo : p901List) {
                if (StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getOptionId())) {
                    if (StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getAddvalue())) {
                        // 获取深海鱼的比例
                        if (lifeStyleConstant.O90108.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                            ratio = (double) (Double.parseDouble(lifeStyleQuestionAnswerBo.getAddvalue()
                                    .substring(0, lifeStyleQuestionAnswerBo.getAddvalue().length() - 1)) / 100);
                        }
                        if (lifeStyleConstant.O90105.equals(lifeStyleQuestionAnswerBo.getOptionId())
                                && StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getFrequency())) {
                            // 鱼类
                            addvalue = lifeStyleQuestionAnswerBo.getAddvalue();
                            frequency = lifeStyleQuestionAnswerBo.getFrequency();
                        }
                    }

                }
            }
            resultEntity = new ResultEntity(ratio, UnitTransform.transformFinsh(addvalue)
                    * FrequencyUtil.transform(frequency));
        }

        return resultEntity;
    }

    public double getElementWeight(String element) {
        double result = 0;

        return result;
    }

    /**
     * 
     * 获取类别1 问题中的元素含量
     * 
     * @author mnt_zhangjing
     * @param element
     * @param modulus
     *            是否乘以系数
     * @return
     */
    public double getClasses1Element(String element, boolean modulus) {
        double result = 0;
        if (foodLibraryMap == null) {
            throw new ServiceException(ResultCode.ERROR_90001);
        }
        if (StringUtils.isEmpty(element)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        Class<EvaluateFoodLibraryPojo> classes = EvaluateFoodLibraryPojo.class;
        String methodName = "get" + element.substring(0, 1).toUpperCase() + element.substring(1);
        Method method = ClassUtils.getMethod(classes, methodName);
        if (method == null) {
            throw new ServiceException(ResultCode.ERROR_99999);
        }
        if (CollectionUtils.isNotEmpty(classes1)) {
            for (LifeStyleQuestionAnswerPojo lifeStyleQuestionAnswerBo : classes1) {

                if (StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getAddvalue())
                        && !lifeStyleConstant.O80113.equalsIgnoreCase(lifeStyleQuestionAnswerBo.getOptionId())) {
                    if (lifeStyleQuestionAnswerBo.getAddvalue().matches("\\d+(.\\d{1,2})?")) {
                        double eleval = getContentByFood(lifeStyleConstant
                                .getFoodElement(lifeStyleQuestionAnswerBo.getOptionId()), method);
                        if (eleval != 0) {
                            Double actual = null;
                            if (modulus) {
                                actual = lifeStyleConstant.getActualWeight(lifeStyleQuestionAnswerBo.getOptionId());
                            }
                            if (actual == null) {
                                actual = 1.0;
                            }
                            result = result
                                    + (Double.parseDouble(lifeStyleQuestionAnswerBo.getAddvalue()) * eleval * 10 / 1000 * actual);
                        }

                    }
                }
            }
        }

        return result;
    }

    /**
     * 
     * 获取类别2 问题中的元素含量
     * 
     * @author mnt_zhangjing
     * @param element
     * @return
     */
    public double getClasses2Element(String element) {
        double result = 0;
        if (foodLibraryMap == null) {
            throw new ServiceException(ResultCode.ERROR_90001);
        }

        if (StringUtils.isEmpty(element)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        Class<EvaluateFoodLibraryPojo> classes = EvaluateFoodLibraryPojo.class;
        String methodName = "get" + element.substring(0, 1).toUpperCase() + element.substring(1);
        Method method = ClassUtils.getMethod(classes, methodName);
        if (method == null) {
            throw new ServiceException(ResultCode.ERROR_99999);
        }

        // 获取问卷中瘦肉的食物库信息
        double smeat = getContentByFood(lifeStyleConstant.esmeat, method);
        // 获取肉的重量信息
        ResultEntity resultEntity = getMeatClass();
        // 瘦肉的
        result = result
                + (resultEntity.getWeight() * resultEntity.getRatio() * smeat) / 100;
        // 获取问卷中肥肉的食物库信息
        double fmeat = getContentByFood(lifeStyleConstant.efmeat, method);
        // 肥肉的
        result = result + (resultEntity.getWeight() * (1 - resultEntity.getRatio()) * fmeat) / 100;

        // 获取问卷中深色蔬菜的食物库信息
        double svege = getContentByFood(lifeStyleConstant.svege, method);
        // 获取蔬菜重量信息
        ResultEntity resultEntityv = getVegetablesClass();
        // 深色
        result = result
                + (resultEntityv.getWeight() * resultEntityv.getRatio() * svege) / 100;
        // 获取问卷中蔬菜（非深色）食物库信息
        double fvege = getContentByFood(lifeStyleConstant.fvege, method);
        result = result
                + (resultEntityv.getWeight() * (1 - resultEntityv.getRatio()) * fvege) / 100;

        return result;
    }

    /**
     * 
     * 获取类别3 问题中的元素含量
     * 
     * @author mnt_zhangjing
     * @param element
     * @return
     */
    public double getClasses3Element(String element) {
        double result = 0;
        if (foodLibraryMap == null) {
            throw new ServiceException(ResultCode.ERROR_90001);
        }

        if (StringUtils.isEmpty(element)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        // result = result + getSnacksClass(element);

        Class<EvaluateFoodLibraryPojo> classes = EvaluateFoodLibraryPojo.class;
        String methodName = "get" + element.substring(0, 1).toUpperCase() + element.substring(1);
        Method method = ClassUtils.getMethod(classes, methodName);
        if (method == null) {
            throw new ServiceException(ResultCode.ERROR_99999);
        }

        // 蛋类
        double eggweight = getEgg();
        double egg = getContentByFood(lifeStyleConstant.eegg, method);
        result = result + eggweight * egg / 100;

        // 全脂
        double amilkweight = getAMilk();
        double amilk = getContentByFood(lifeStyleConstant.amilk, method);
        result = result + amilkweight * amilk / 100;

        // 脱脂
        double tmilkweight = getTMilk();
        double tmilk = getContentByFood(lifeStyleConstant.tmilk, method);
        result = result + tmilkweight * tmilk / 100;

        // 豆类
        double beanweight = getBeans();
        double bean = getContentByFood(lifeStyleConstant.ebean, method);
        result = result + beanweight * bean / 100;

        // 鱼虾类
        ResultEntity resultEntity = getFish();
        if (resultEntity != null) {
            // 不包括深海鱼
            double ffish = getContentByFood(lifeStyleConstant.efish, method);
            result = result + resultEntity.getWeight() * ffish * (1 - resultEntity.getRatio()) / 100;

            // 包括深海鱼
            double hfish = getContentByFood(lifeStyleConstant.ehfish, method);
            result = result + resultEntity.getWeight() * hfish * resultEntity.getRatio() / 100;
        }

        // 坚果类
        double nutweight = getNut();
        double nut = getContentByFood(lifeStyleConstant.enut, method);
        result = result + nutweight * nut / 100;

        // 内脏类
        double organtsweight = getOrgans();
        double organts = getContentByFood(lifeStyleConstant.eorgants, method);
        result = result + organtsweight * organts / 100;
        return result;
    }

    /**
     * 
     * 获取油的重量
     * 
     * @author mnt_zhangjing
     * @return 重量
     */
    public double getOilWeight() {
        double result = 0;
        List<LifeStyleQuestionAnswerPojo> classes4 = questionMap.get(lifeStyleConstant.p109);
        if (CollectionUtils.isNotEmpty(classes4)) {
            for (LifeStyleQuestionAnswerPojo lifeStyleQuestionAnswerBo : classes4) {
                if (lifeStyleConstant.O10903.equals(lifeStyleQuestionAnswerBo.getOptionId())
                        || lifeStyleConstant.O10904.equals(lifeStyleQuestionAnswerBo.getOptionId())
                        || lifeStyleConstant.O10905.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                    result = result > UnitTransform.transformOil(lifeStyleQuestionAnswerBo.getOptionId()) ? result
                            : UnitTransform.transformOil(lifeStyleQuestionAnswerBo.getOptionId());
                }
            }
        }
        if (result == 0) {
            result = 30;
        }
        return result;
    }

    /**
     * 
     * 获取油的营养素含量
     * 
     * @author mnt_zhangjing
     * @param element
     * @return
     */
    public double getOil(String element) {
        double result = 0;

        if (foodLibraryMap == null) {
            throw new ServiceException(ResultCode.ERROR_90001);
        }

        if (StringUtils.isEmpty(element)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }

        Class<EvaluateFoodLibraryPojo> classes = EvaluateFoodLibraryPojo.class;
        String methodName = "get" + element.substring(0, 1).toUpperCase() + element.substring(1);
        Method method = ClassUtils.getMethod(classes, methodName);
        if (method == null) {
            throw new ServiceException(ResultCode.ERROR_99999);
        }

        double oilWeight = getOilWeight();
        double oil = getContentByFood(lifeStyleConstant.eoil, method);
        result = oilWeight * oil / 100;

        return result;
    }

    /**
     * 
     * 获取肉类的重量
     * 
     * @author mnt_zhangjing
     * @return
     */
    public ResultEntity getMeatClass() {
        double weight = 0;
        double ratio = 0;
        if (CollectionUtils.isNotEmpty(classes2)) {
            for (LifeStyleQuestionAnswerPojo lifeStyleQuestionAnswerBo : classes2) {
                if (lifeStyleConstant.p802.equals(lifeStyleQuestionAnswerBo.getProblemId())) {
                    if (StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getAddvalue())) {
                        // 获取瘦肉的比例
                        if (lifeStyleConstant.O80209.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                            ratio = (double) (Double.parseDouble(lifeStyleQuestionAnswerBo.getAddvalue()
                                    .substring(0, lifeStyleQuestionAnswerBo.getAddvalue().length() - 1)) / 100);
                        }
                        // 肉类
                        if (lifeStyleQuestionAnswerBo.getAddvalue().matches("\\d+(.\\d{1,2})?")) {
                            weight = weight + Double.parseDouble(lifeStyleQuestionAnswerBo.getAddvalue());
                        }
                    }
                }
            }
        }
        ResultEntity resultEntity = new ResultEntity(ratio, weight);
        return resultEntity;
    }

    /**
     * 
     * 获取蔬菜类的重量
     * 
     * @author mnt_zhangjing
     * @return
     */
    public ResultEntity getVegetablesClass() {
        double weight = 0;
        double ratio = 0;
        List<LifeStyleQuestionAnswerPojo> p803List = questionMap.get(lifeStyleConstant.p803);
        if (CollectionUtils.isNotEmpty(p803List)) {
            for (LifeStyleQuestionAnswerPojo lifeStyleQuestionAnswerBo : p803List) {
                if (StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getAddvalue())) {
                    // 获取深色蔬菜的比例
                    if (lifeStyleConstant.O80311.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                        ratio = (double) (Double.parseDouble(lifeStyleQuestionAnswerBo.getAddvalue()
                                .substring(0, lifeStyleQuestionAnswerBo.getAddvalue().length() - 1)) / 100);
                    }
                    // 蔬菜
                    if (lifeStyleQuestionAnswerBo.getAddvalue().matches("\\d+(.\\d{1,2})?")) {
                        weight = weight + Double.parseDouble(lifeStyleQuestionAnswerBo.getAddvalue());
                    }
                }
            }
        }
        ResultEntity resultEntity = new ResultEntity(ratio, weight);
        return resultEntity;
    }

    /**
     * 
     * 获取零食及营养品类的营养素含量
     * 
     * @author mnt_zhangjing
     * @return
     */
    public double getSnacksClass(String element) {
        double result = 0;
        if (foodLibraryMap == null) {
            throw new ServiceException(ResultCode.ERROR_90001);
        }
        if (StringUtils.isEmpty(element)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        Class<EvaluateFoodLibraryPojo> classes = EvaluateFoodLibraryPojo.class;
        String methodName = "get" + element.substring(0, 1).toUpperCase() + element.substring(1);
        Method method = ClassUtils.getMethod(classes, methodName);
        if (method == null) {
            throw new ServiceException(ResultCode.ERROR_99999);
        }
        if (CollectionUtils.isNotEmpty(classes3)) {
            for (LifeStyleQuestionAnswerPojo lifeStyleQuestionAnswerBo : classes3) {
                if (StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getAddvalue())
                        && StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getFrequency())) {
                    // 获取问卷中选项在食物空中的对象信息
                    double eleval = getContentByFood(
                            lifeStyleConstant.getFoodElement(lifeStyleQuestionAnswerBo.getOptionId()), method);
                    if (eleval != 0) {
                        if (lifeStyleConstant.p902.equals(lifeStyleQuestionAnswerBo.getProblemId())) {
                            if (lifeStyleQuestionAnswerBo.getAddvalue().matches("\\d+(.\\d{1,2})?")) {
                                result = result
                                        + (Double.parseDouble(lifeStyleQuestionAnswerBo.getAddvalue()) * eleval * 10 / 1000)
                                        * FrequencyUtil.transform(lifeStyleQuestionAnswerBo.getFrequency());
                            }
                        }
                        if (lifeStyleConstant.p903.equals(lifeStyleQuestionAnswerBo.getProblemId())) {
                            if (lifeStyleQuestionAnswerBo.getAddvalue().matches("\\d+(.\\d{1,2})?")) {
                                // 处理营养糕
                                if (lifeStyleConstant.O90203.equalsIgnoreCase(lifeStyleQuestionAnswerBo.getOptionId())) {
                                    result = result
                                            + UnitTransform.transformCake(lifeStyleQuestionAnswerBo.getAddvalue())
                                            * eleval * 10 / 1000
                                            * FrequencyUtil.transform(lifeStyleQuestionAnswerBo.getFrequency());
                                    // 营养奶昔
                                } else if (lifeStyleConstant.O90204.equalsIgnoreCase(lifeStyleQuestionAnswerBo
                                        .getOptionId())) {
                                    result = result
                                            + UnitTransform.transformMilkshake(lifeStyleQuestionAnswerBo.getAddvalue())
                                            * eleval * 10 / 1000
                                            * FrequencyUtil.transform(lifeStyleQuestionAnswerBo.getFrequency());
                                    // 营养餐包
                                } else if (lifeStyleConstant.O90205.equalsIgnoreCase(lifeStyleQuestionAnswerBo
                                        .getOptionId())) {
                                    result = result
                                            + UnitTransform.transformPacks(lifeStyleQuestionAnswerBo.getAddvalue())
                                            * eleval * 10 / 1000
                                            * FrequencyUtil.transform(lifeStyleQuestionAnswerBo.getFrequency());
                                } else {
                                    result = result + Double.parseDouble(lifeStyleQuestionAnswerBo.getAddvalue())
                                            * eleval
                                            * 10 / 1000
                                            * FrequencyUtil.transform(lifeStyleQuestionAnswerBo.getFrequency());
                                }
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * 
     * 获取指定元素的总含量
     * 
     * @author mnt_zhangjing
     * @param element
     *            元素
     * @param modulus
     *            是否乘以系数
     * @return
     */
    public double getTotalContent(String element, boolean modulus) {
        return getClasses1Element(element, modulus) + getClasses2Element(element) + getClasses3Element(element)
                + getSnacksClass(element) + getOil(element);
    }

    /**
     * 
     * 获取水果的重量
     * 
     * @author mnt_zhangjing
     * @return
     */
    public double getFruit() {
        double result = 0;
        if (CollectionUtils.isNotEmpty(classes1)) {
            for (LifeStyleQuestionAnswerPojo lifeStyleQuestionAnswerBo : classes1) {
                if (lifeStyleConstant.p804.equalsIgnoreCase(lifeStyleQuestionAnswerBo.getProblemId())) {
                    if (StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getAddvalue())) {
                        if (lifeStyleQuestionAnswerBo.getAddvalue().matches("\\d+(.\\d{1,2})?")) {
                            Double actual = lifeStyleConstant.getActualWeight(lifeStyleQuestionAnswerBo.getOptionId());
                            if (actual == null) {
                                actual = 1.0;
                            }
                            result = result + (Double.parseDouble(lifeStyleQuestionAnswerBo.getAddvalue()) * actual);

                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 
     * 获取水的输入量
     * 
     * @author mnt_zhangjing
     * @return
     */
    public double getWater() {
        double result = 0;
        if (CollectionUtils.isNotEmpty(waterQuestionAnswerBos)) {
            LifeStyleQuestionAnswerPojo waterQuestionAnswerBo = waterQuestionAnswerBos.get(0);
            if (StringUtils.isNotEmpty(waterQuestionAnswerBo.getAddvalue())) {
                if (waterQuestionAnswerBo.getAddvalue().matches("\\d+(.\\d{1,2})?")) {
                    result = Double.parseDouble(waterQuestionAnswerBo.getAddvalue());
                }
            }
        }
        return result;
    }

    /**
     * 
     * 获取获取谷类的重量
     * 
     * @author mnt_zhangjing
     * @return
     */

    public double getCereal() {
        double result = 0;
        double food = 0;
        List<LifeStyleQuestionAnswerPojo> cerealQuestionAnswerBos = questionMap.get(lifeStyleConstant.p801);
        if (CollectionUtils.isNotEmpty(cerealQuestionAnswerBos)) {
            for (LifeStyleQuestionAnswerPojo lifeStyleQuestionAnswerBo : cerealQuestionAnswerBos) {
                for (String optionId : lifeStyleConstant.getCerealFood()) {
                    if (optionId.equalsIgnoreCase(lifeStyleQuestionAnswerBo.getOptionId())) {
                        if (StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getAddvalue())) {
                            if (lifeStyleQuestionAnswerBo.getAddvalue().matches("\\d+(.\\d{1,2})?")) {
                                Double actual = lifeStyleConstant.getActualWeight(lifeStyleQuestionAnswerBo
                                        .getOptionId());
                                if (actual == null) {
                                    actual = 1.0;
                                }
                                food = Double.parseDouble(lifeStyleQuestionAnswerBo.getAddvalue()) * actual;
                                if (lifeStyleConstant.O80109.equalsIgnoreCase(lifeStyleQuestionAnswerBo.getOptionId())) {
                                    ElementRatio ratio = new BugRatio();
                                    food = food * ratio.getCerealRatio();
                                }
                                if (lifeStyleConstant.O80110.equalsIgnoreCase(lifeStyleQuestionAnswerBo.getOptionId())) {
                                    ElementRatio ratio = new DumplingsRatio();
                                    food = food * ratio.getCerealRatio();
                                }
                                if (lifeStyleConstant.O80111.equalsIgnoreCase(lifeStyleQuestionAnswerBo.getOptionId())) {
                                    ElementRatio ratio = new HamburgersRatio();
                                    food = food * ratio.getCerealRatio();
                                }
                                if (lifeStyleConstant.O80112.equalsIgnoreCase(lifeStyleQuestionAnswerBo.getOptionId())) {
                                    ElementRatio ratio = new PizzaRatio();
                                    food = food * ratio.getCerealRatio();
                                }

                                result = result + food;
                            }
                        }
                    }
                }
                if (lifeStyleConstant.O80107.equalsIgnoreCase(lifeStyleQuestionAnswerBo.getOptionId())) {
                    potatoes = lifeStyleQuestionAnswerBo.getAddvalue();
                }
            }
        }
        return result;
    }

    /**
     * 
     * 包子饺子；汉堡热狗；披萨馅饼】填写克数（g）×含有鱼肉蛋类的每单位计算百分比（%）
     * 
     * @author mnt_zhangjing
     * @return
     */

    public double getMeatInP801() {
        double result = 0;
        // 【包子饺子；汉堡热狗；披萨馅饼】填写克数（g）×含有鱼肉蛋类的每单位计算百分比（%）==>p801
        List<LifeStyleQuestionAnswerPojo> p801List = questionMap.get(lifeStyleConstant.p801);
        if (CollectionUtils.isNotEmpty(p801List)) {
            for (LifeStyleQuestionAnswerPojo lifeStyleQuestionAnswerBo : p801List) {
                if (StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getOptionId())) {
                    if (StringUtils.isNotEmpty(lifeStyleQuestionAnswerBo.getAddvalue())) {
                        if (lifeStyleConstant.O80109.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                            // 包子
                            ElementRatio ratio = new BugRatio();
                            if (lifeStyleQuestionAnswerBo.getAddvalue().matches("\\d+(.\\d{1,2})?")) {
                                result = result
                                        + (Double.parseDouble(lifeStyleQuestionAnswerBo.getAddvalue()) * ratio
                                                .getMeatRatio());
                            }
                        }
                        if (lifeStyleConstant.O80110.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                            // 饺子
                            ElementRatio ratio = new DumplingsRatio();
                            if (lifeStyleQuestionAnswerBo.getAddvalue().matches("\\d+(.\\d{1,2})?")) {
                                result = result
                                        + (Double.parseDouble(lifeStyleQuestionAnswerBo.getAddvalue()) * ratio
                                                .getMeatRatio());
                            }
                        }
                        if (lifeStyleConstant.O80111.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                            // 汉堡
                            ElementRatio ratio = new HamburgersRatio();
                            if (lifeStyleQuestionAnswerBo.getAddvalue().matches("\\d+(.\\d{1,2})?")) {
                                result = result
                                        + (Double.parseDouble(lifeStyleQuestionAnswerBo.getAddvalue()) * ratio
                                                .getMeatRatio());
                            }
                        }
                        if (lifeStyleConstant.O80112.equals(lifeStyleQuestionAnswerBo.getOptionId())) {
                            // 披萨
                            ElementRatio ratio = new PizzaRatio();
                            if (lifeStyleQuestionAnswerBo.getAddvalue().matches("\\d+(.\\d{1,2})?")) {
                                result = result
                                        + (Double.parseDouble(lifeStyleQuestionAnswerBo.getAddvalue()) * ratio
                                                .getMeatRatio());
                            }
                        }
                    }

                }
            }
        }
        return result;
    }

    /**
     * 
     * 获取优质蛋白质的重量
     * 
     * @author mnt_zhangjing
     * @return
     */
    public double getGoodProtid(String element) {
        double result = 0;

        if (foodLibraryMap == null) {
            throw new ServiceException(ResultCode.ERROR_90001);
        }
        if (StringUtils.isEmpty(element)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        Class<EvaluateFoodLibraryPojo> classes = EvaluateFoodLibraryPojo.class;
        String methodName = "get" + element.substring(0, 1).toUpperCase() + element.substring(1);
        Method method = ClassUtils.getMethod(classes, methodName);
        if (method == null) {
            throw new ServiceException(ResultCode.ERROR_99999);
        }
        // 获取问卷中选项在食物空中的对象信息
        double elevalm = getContentByFood(lifeStyleConstant.esmeat, method);

        // 获取肉的重量信息
        ResultEntity resultEntity = getMeatClass();

        // 包子饺子；汉堡热狗；披萨馅饼 中肉的重量
        double p801 = getMeatInP801();

        if (resultEntity != null) {
            result = result + (resultEntity.getWeight() + p801) * resultEntity.getRatio() * elevalm / 100;
        }

        // 获取鱼的重量信息
        resultEntity = getFish();
        // 获取问卷中选项在食物空中的对象信息

        double elevalhf = getContentByFood(lifeStyleConstant.ehfish, method);
        double elevalf = getContentByFood(lifeStyleConstant.efish, method);

        if (resultEntity != null) {
            result = result + resultEntity.getWeight() * resultEntity.getRatio() * elevalhf / 100;
            result = result + resultEntity.getWeight() * (1 - resultEntity.getRatio()) * elevalf / 100;
        }

        // 获取蛋类的重量
        double egg = getEgg();
        double elevalegg = getContentByFood(lifeStyleConstant.eegg, method);
        result = result + egg * elevalegg / 100;

        // 获取乳类的重量
        double amilk = getAMilk();
        double tmilk = getTMilk();
        double elevalamilk = getContentByFood(lifeStyleConstant.amilk, method);
        double elevaltmilk = getContentByFood(lifeStyleConstant.tmilk, method);
        result = result + amilk * elevalamilk / 100 + tmilk * elevaltmilk / 100;

        // 获取豆类的重量
        double bean = getBeans();
        double elevalbean = getContentByFood(lifeStyleConstant.ebean, method);
        result = result + bean * elevalbean / 100;

        return result;
    }
}

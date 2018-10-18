
package com.mnt.preg.examitem.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.DocumentException;
import com.mnt.preg.customer.dao.QuestionAnswerDAO;
import com.mnt.preg.customer.pojo.QuestionAnswerPojo;
import com.mnt.preg.examitem.pojo.NutritiousReportPojo;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.master.dao.question.QuestionDAO;

/**
 * 营养快速调查
 * 
 * @author dhs
 * @version 1.3.3
 * 
 *          变更履历：
 *          v1.3.3 2018-01-09 dhs 初版
 */
@Service
public class NutritiousSurveyServiceImpl extends BaseService implements NutritiousSurveyService {

    @Resource
    private QuestionAnswerDAO questionAnswerDAO;

    @Resource
    private QuestionDAO questionDAO;

    @Override
    @Transactional(readOnly = true)
    public NutritiousReportPojo analyseNutritious(String questionAllocId) {
        NutritiousReportPojo nutritiousSurvey = new NutritiousReportPojo();
        List<QuestionAnswerPojo> list = questionAnswerDAO.queryQuestionAnswer(questionAllocId);
        if (list.size() > 0) {
            String foodIntake = "";// 膳食摄入问题
            String badDietary = "";// 不良饮食喜好
            String badHabits = "";// 不良用餐习惯
            String foodRisk = "";// 膳食结构风险
            String illRisk = "";// 致病饮食风险
            String nutritionalRequirements = "";// 营养咨询需求
            String foodQuestion = "";// 膳食结构问题
            boolean flag_roushi = false;// 肉食
            boolean flag_pianroushi = false;// 偏肉食
            boolean flag_quansu = false;// 全素食
            boolean flag_jiagong = false;// 加工类肉食品（肉干、肉松、香肠等）
            boolean flag_fangbian = false;// 方便类食品（主要指方便面和膨化食品）
            boolean flag_guantou = false;// 罐头类食品（包括鱼肉类和水果类）
            boolean flag_guofu = false;// 话梅蜜饯类食品（果脯）
            boolean flag_tian = false;// 冷冻甜品类食品（冰淇淋、冰棒和各种雪糕）
            boolean flag_youzha = false;// 油炸、腌制、烧烤类食品
            boolean flag_yuintake = false;// 平均每天鱼肉蛋摄入量大于250g
            boolean flag_shuiguointake = false;// 平均每天水果摄入量大于500g
            boolean flag_zhushiintake = false;// 平均每天主食摄入量大于300g（熟重约等于750g米饭）
            boolean flag_shucaiintake = false;// 平均每天蔬菜量少于250g
            boolean flag_gaodian = false;// 高糖高油的中西糕点（不含低温烘烤和全麦饼干）
            boolean flag_yinliao = false;// 高糖饮料（尤其是碳酸类）
            boolean flag_zhushis = false;// 很少摄入主食
            boolean flag_shuiguos = false;// 很少摄入水果
            boolean flag_yurous = false;// 很少摄入鱼肉蛋
            boolean flag_dous = false;// 很少摄入豆制品
            boolean flag_culiangs = false;// 很少摄入粗粮
            boolean flag_zaiwai = false;// 每周至少3天，每天至少一餐上在饭店或者外卖点餐
            boolean flag_dannai = false;// 蛋奶素（单纯不吃肉）
            boolean flag_wuteshu = false;// 无特殊膳食偏好
            boolean flag_wusheru = false;// 无此类不良日常膳食摄入问题
            boolean flag_wubuliang = false;// 无
            boolean flag_wuxiguan = false;// 无此类不良用餐习惯
            int count_intake = 0;
            int count_dijia = 0;
            for (QuestionAnswerPojo pojo : list) {
                if ("402880f460d8bac30160d8c0d6290000".equals(pojo.getProblemId())) {// 您最近是否因食欲导致饮食摄入量明显改变?（单选）
                    nutritiousSurvey.setEats(this.getOptionName(pojo.getProblemOptionId()));
                    if ("11000022018010900006".equals(pojo.getProblemOptionId())) {// 食量明显增加
                        foodQuestion += "食欲旺盛、食量增加;";
                    } else if ("11000022018010900007".equals(pojo.getProblemOptionId())) {// 食量明显减少
                        foodQuestion += "食欲不良、食量减少;";
                    }
                }
                if ("402880f460d8bac30160d8c356900001".equals(pojo.getProblemId())) {// 您存在哪种膳食偏好？(单选)
                    nutritiousSurvey.setFoodPreference(this.getOptionName(pojo.getProblemOptionId()));
                }
                if ("402880f460d8bac30160d8c6e1150002".equals(pojo.getProblemId())) {// 您存在哪些不良日常膳食摄入问题？(多选)
                    foodIntake += this.getOptionName(pojo.getProblemOptionId()) + ";";
                }
                if ("402880f460d8bac30160d8d42b940003".equals(pojo.getProblemId())) {// 您存在哪些不健康的食品每周摄入超过3次？(多选)
                    badDietary += this.getOptionName(pojo.getProblemOptionId()) + ";";
                }
                if ("402880f460d8bac30160d8d59f130004".equals(pojo.getProblemId())) {// 您存在哪些不良用餐习惯？(多选)
                    badHabits += this.getOptionName(pojo.getProblemOptionId()) + ";";
                }
                if ("402880f460d8bac30160d8d6c3cf0005".equals(pojo.getProblemId())) {// 您的每天体能消耗情况？(单选)
                    nutritiousSurvey.setStaminaRup(this.getOptionName(pojo.getProblemOptionId()));
                }
                if ("402880f460d8bac30160d8d77e920006".equals(pojo.getProblemId())) {// 您的每周运动情况为？(单选)
                    nutritiousSurvey.setSportRup(this.getOptionName(pojo.getProblemOptionId()));
                }
                if ("402880f460d8bac30160d8d859d30007".equals(pojo.getProblemId())) {// 您存在哪些明确并紧急的营养需求？(多选)
                    nutritionalRequirements += this.getOptionName(pojo.getProblemOptionId()) + ";";
                }
                if ("11000022018010900009".equals(pojo.getProblemOptionId())) {// 肉食
                    flag_roushi = true;
                }
                if ("11000022018010900010".equals(pojo.getProblemOptionId())) {// 偏肉食
                    flag_pianroushi = true;
                }
                if ("11000022018010900011".equals(pojo.getProblemOptionId())) {// 蛋奶素（单纯不吃肉）
                    flag_dannai = true;
                }
                if ("11000022018010900012".equals(pojo.getProblemOptionId())) {// 全素食
                    flag_quansu = true;
                }
                if ("11000022018010900013".equals(pojo.getProblemOptionId())) {// 无特殊膳食偏好
                    flag_wuteshu = true;
                }
                if ("11000022018010900025".equals(pojo.getProblemOptionId())) {// 无此类不良日常膳食摄入问题
                    flag_wusheru = true;
                }
                if ("11000022018010900034".equals(pojo.getProblemOptionId())) {// 无
                    flag_wubuliang = true;
                }
                if ("11000022018010900040".equals(pojo.getProblemOptionId())) {// 无此类不良用餐习惯
                    flag_wuxiguan = true;
                }
                if ("11000022018010900027".equals(pojo.getProblemOptionId())) {// 加工类肉食品（肉干、肉松、香肠等）
                    flag_jiagong = true;
                }
                if ("11000022018010900030".equals(pojo.getProblemOptionId())) {// 方便类食品（主要指方便面和膨化食品）
                    flag_fangbian = true;
                }
                if ("11000022018010900031".equals(pojo.getProblemOptionId())) {// 罐头类食品（包括鱼肉类和水果类）
                    flag_guantou = true;
                }
                if ("11000022018010900032".equals(pojo.getProblemOptionId())) {// 话梅蜜饯类食品（果脯）
                    flag_guofu = true;
                }
                if ("11000022018010900033".equals(pojo.getProblemOptionId())) {// 冷冻甜品类食品（冰淇淋、冰棒和各种雪糕）
                    flag_tian = true;
                }
                if ("11000022018010900026".equals(pojo.getProblemOptionId())) {// 油炸、腌制、烧烤类食品
                    flag_youzha = true;
                }
                if ("11000022018010900022".equals(pojo.getProblemOptionId())) {// 平均每天鱼肉蛋摄入量大于250g
                    flag_yuintake = true;
                }
                if ("11000022018010900023".equals(pojo.getProblemOptionId())) {// 平均每天水果摄入量大于500g
                    flag_shuiguointake = true;
                }
                if ("11000022018010900024".equals(pojo.getProblemOptionId())) {// 平均每天主食摄入量大于300g（熟重约等于750g米饭）
                    flag_zhushiintake = true;
                }
                if ("11000022018010900020".equals(pojo.getProblemOptionId())) {// 平均每天蔬菜量少于250g
                    flag_shucaiintake = true;
                }
                if ("11000022018010900028".equals(pojo.getProblemOptionId())) {// 高糖高油的中西糕点（不含低温烘烤和全麦饼干）
                    flag_gaodian = true;
                }
                if ("11000022018010900029".equals(pojo.getProblemOptionId())) {// 高糖饮料（尤其是碳酸类）
                    flag_yinliao = true;
                }
                if ("11000022018010900014".equals(pojo.getProblemOptionId())) {// 很少摄入主食
                    flag_zhushis = true;
                }
                if ("11000022018010900016".equals(pojo.getProblemOptionId())) {// 很少摄入水果
                    flag_shuiguos = true;
                }
                if ("11000022018010900019".equals(pojo.getProblemOptionId())) {// 很少摄入鱼肉蛋
                    flag_yurous = true;
                }
                if ("11000022018010900017".equals(pojo.getProblemOptionId())) {// 很少摄入豆制品
                    flag_dous = true;
                }
                if ("11000022018010900015".equals(pojo.getProblemOptionId())) {// 很少摄入粗粮
                    flag_culiangs = true;
                }
                if ("11000022018010900037".equals(pojo.getProblemOptionId())) {// 每周至少3天，每天至少一餐上在饭店或者外卖点餐
                    flag_zaiwai = true;
                }
            }
            if ((flag_roushi || flag_pianroushi || flag_yuintake) && (flag_youzha || flag_jiagong)) {
                foodRisk += "高蛋白高脂;";
            }
            if (flag_shuiguointake == false && flag_zhushis && (flag_roushi || flag_pianroushi || flag_yuintake)) {
                foodRisk += "低碳水化合物;";
            }
            if (flag_zhushis == false && flag_shuiguos == false
                    && (flag_shuiguointake || flag_zhushiintake || flag_gaodian || flag_yinliao || flag_tian)
                    && (flag_roushi || flag_pianroushi || flag_yuintake || flag_youzha || flag_jiagong)) {
                foodRisk += "高碳水化合物高脂;";
            }
            if (flag_quansu) {
                count_intake += 1;
            }
            if (flag_yurous) {
                count_intake += 1;
            }
            if (flag_dous) {
                count_intake += 1;
                count_dijia += 1;
            }
            if (count_intake >= 2) {
                foodRisk += "蛋白质摄入不足;";
            }
            if (flag_roushi || flag_pianroushi || flag_youzha || flag_jiagong || flag_fangbian || flag_guantou
                    || flag_guofu || flag_zaiwai) {
                illRisk += "高钠饮食;";
            }
            if (flag_zhushis) {
                count_dijia += 1;
            }
            if (flag_shuiguos) {
                count_dijia += 1;
            }
            if (flag_shucaiintake) {
                count_dijia += 1;
            }
            if (count_dijia >= 3 && flag_shuiguointake == false) {
                illRisk += "低钾饮食;";
            }
            if ((flag_shuiguointake || flag_zhushiintake) && flag_culiangs && flag_zhushis == false) {
                illRisk += "高血糖负荷饮食;";
            }
            if (flag_roushi || flag_pianroushi || flag_youzha || flag_jiagong || flag_gaodian || flag_yinliao
                    || flag_fangbian || flag_guantou || flag_guofu || flag_tian) {
                illRisk += "致炎性饮食;";
            }
            if (flag_wuteshu == false) {
                if ((flag_roushi)) {// 肉食
                    foodQuestion += this.getOptionName("11000022018010900009") + ";";
                }
                if (flag_pianroushi) {// 偏肉食
                    foodQuestion += this.getOptionName("11000022018010900010") + ";";
                }
                if (flag_dannai) {// 蛋奶素（单纯不吃肉）
                    foodQuestion += this.getOptionName("11000022018010900011") + ";";
                }
                if (flag_quansu) {// 全素食
                    foodQuestion += this.getOptionName("11000022018010900012") + ";";
                }
            }
            if (flag_wusheru == false && StringUtils.isNotBlank(foodIntake)) {
                foodQuestion += foodIntake;
            }
            if (flag_wubuliang == false && StringUtils.isNotBlank(badDietary)) {
                foodQuestion += badDietary;
            }
            if (flag_wuxiguan == false && StringUtils.isNotBlank(badHabits)) {
                foodQuestion += badHabits;
            }
            nutritiousSurvey.setFoodIntake(foodIntake.substring(0, foodIntake.length() - 1));
            nutritiousSurvey.setBadDietary(badDietary.substring(0, badDietary.length() - 1));
            nutritiousSurvey.setBadHabits(badHabits.substring(0, badHabits.length() - 1));
            if (foodRisk.length() > 0) {
                nutritiousSurvey.setFoodRisk(foodRisk.substring(0, foodRisk.length() - 1));
            } else {
                nutritiousSurvey.setFoodRisk("无");
            }
            if (illRisk.length() > 0) {
                nutritiousSurvey.setIllRisk(illRisk.substring(0, illRisk.length() - 1));
            } else {
                nutritiousSurvey.setIllRisk("无");
            }
            if (nutritionalRequirements.length() > 0) {
                nutritiousSurvey.setNutritionalRequirements(nutritionalRequirements.substring(0,
                        nutritionalRequirements.length() - 1));
            } else {
                nutritiousSurvey.setNutritionalRequirements("无");
            }
            if (foodQuestion.length() > 0) {
                nutritiousSurvey.setFoodQuestion(foodQuestion.substring(0, foodQuestion.length() - 1));
            } else {
                nutritiousSurvey.setFoodQuestion("无");
            }
        }
        return nutritiousSurvey;
    }

    /**
     * 返回选项名称
     * 
     * @author dhs
     * @param problemId
     * @param optionId
     * @throws DocumentException
     */
    private String getOptionName(String optionId) {
        return questionDAO.getOptionById(optionId).getOptionContent();
    }
}

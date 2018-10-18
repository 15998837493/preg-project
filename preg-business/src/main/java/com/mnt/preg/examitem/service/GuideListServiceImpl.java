
package com.mnt.preg.examitem.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.mnt.preg.customer.dao.QuestionAnswerDAO;
import com.mnt.preg.customer.pojo.QuestionAnswerPojo;
import com.mnt.preg.examitem.condition.InbodyCondition;
import com.mnt.preg.examitem.dao.PregInbodyDAO;
import com.mnt.preg.examitem.pojo.ExamItemPojo;
import com.mnt.preg.examitem.pojo.GuideListReportPojo;
import com.mnt.preg.examitem.pojo.NutritiousReportPojo;
import com.mnt.preg.examitem.pojo.PregInBodyBcaPojo;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.master.dao.question.QuestionDAO;
import com.mnt.preg.platform.condition.DiagnosisItemsCondition;
import com.mnt.preg.platform.dao.PregDiagnosisItemDAO;
import com.mnt.preg.platform.pojo.PregDiagnosisItemsVo;

/**
 * 引导单
 * 
 * @author dhs
 * @version 1.3.3
 * 
 *          变更履历：
 *          v1.3.3 2018-01-09 dhs 初版
 */
@Service
public class GuideListServiceImpl extends BaseService implements GuideListService {

    @Resource
    private QuestionAnswerDAO questionAnswerDAO;

    @Resource
    private QuestionDAO questionDAO;

    @Resource
    private PregInbodyDAO inbodyDAO;

    @Resource
    private PregDiagnosisItemDAO pregDiagnosisItemDAO;

    @Override
    public GuideListReportPojo analyseGuideList(String diagnosisId, String allocId, PregDiagnosisItemsVo item,
            Map<String, ExamItemPojo> map, NutritiousReportPojo nutritiousReportPojo) {
        GuideListReportPojo guideListReportPojo = new GuideListReportPojo();
        List<QuestionAnswerPojo> list = questionAnswerDAO.queryQuestionAnswer(allocId);
        String allergic = "";// 过敏情况
        boolean allergic_flag = false;// 过敏情况，为true才会接着判断
        String suggest = "请持此引导单及相关营养评价报告单，前往 _______________________________ 就诊";// 转诊建议
        if (list.size() > 0) {
            String metabolicBingshi = "";// 代谢异常风险因素_病史
            String nutritiousBingshi = "";// 营养不良风险因素_病史
            float weight = 0;// 体重
            float height = 0;// 身高
            String metabolicHistory = "";// 代谢异常风险因素_既往妊娠并发症及分娩史
            String nutritiousHistory = "";// 营养不良风险因素_既往妊娠并发症及分娩史
            for (QuestionAnswerPojo pojo : list) {
                if ("402880ef5a91a2b1015a91d2aa8b000e".equals(pojo.getProblemId())) {// 您是否有以下疾病
                    if ("210100700000181".equals(pojo.getProblemOptionId())) {// 高血压
                        metabolicBingshi += this.getOptionName(pojo.getProblemOptionId()) + ";";
                    }
                    if ("210100700000182".equals(pojo.getProblemOptionId())) {// 高血脂
                        metabolicBingshi += this.getOptionName(pojo.getProblemOptionId()) + ";";
                    }
                    if ("210100700000183".equals(pojo.getProblemOptionId())) {// 甲状腺疾病
                        metabolicBingshi += this.getOptionName(pojo.getProblemOptionId()) + ";";
                    }
                    if ("210100700000185".equals(pojo.getProblemOptionId())) {// 贫血
                        nutritiousBingshi += this.getOptionName(pojo.getProblemOptionId()) + ";";
                    }
                    if ("210100700000193".equals(pojo.getProblemOptionId())) {// 便秘（功能性）
                        nutritiousBingshi += this.getOptionName(pojo.getProblemOptionId()) + ";";
                    }
                    if ("210100700000187".equals(pojo.getProblemOptionId())) {// 多囊卵巢综合征（pcos）
                        metabolicBingshi += this.getOptionName(pojo.getProblemOptionId()) + ";";
                    }
                    if ("11000022018011200237".equals(pojo.getProblemOptionId())) {// 脂肪肝
                        metabolicBingshi += this.getOptionName(pojo.getProblemOptionId()) + ";";
                    }
                    if ("11000022018011200238".equals(pojo.getProblemOptionId())) {// 腹型肥胖（腰围大于等于90）
                        metabolicBingshi += this.getOptionName(pojo.getProblemOptionId()) + ";";
                    }
                    if ("11000022018011200239".equals(pojo.getProblemOptionId())) {// 高尿酸血症/痛风
                        metabolicBingshi += this.getOptionName(pojo.getProblemOptionId()) + ";";
                    }
                    if ("210100700000194".equals(pojo.getProblemOptionId())) {// 胃炎
                        nutritiousBingshi += this.getOptionName(pojo.getProblemOptionId()) + ";";
                    }
                    if ("210100700000195".equals(pojo.getProblemOptionId())) {// 消化道溃疡
                        nutritiousBingshi += this.getOptionName(pojo.getProblemOptionId()) + ";";
                    }
                    if ("11000022018011200240".equals(pojo.getProblemOptionId())) {// 慢性腹泻
                        nutritiousBingshi += this.getOptionName(pojo.getProblemOptionId()) + ";";
                    }
                }
                if ("402880ef5a91a2b1015a91e3ad350025".equals(pojo.getProblemId())) {// 您现在是否有糖尿病病史或存在糖尿病前期状态
                    if (!"11000022017092500035".equals(pojo.getProblemOptionId())) {// 选项不是"无"
                        metabolicBingshi += this.getOptionName(pojo.getProblemOptionId()) + ";";
                    }
                }
                if ("402880f45b2212c8015b230c05930015".equals(pojo.getProblemId())) {// 您的孕前体重是#kg
                    weight = Float.parseFloat(pojo.getAnswerContent());
                }
                if ("402880f45b2212c8015b230848550010".equals(pojo.getProblemId())) {// 您的身高是#cm
                    height = Float.parseFloat(pojo.getAnswerContent());
                }
                if ("402880ef5a91a2b1015a91eb0c02002d".equals(pojo.getProblemId())) {// 既往妊娠并发症
                    if ("210100700000240".equals(pojo.getProblemOptionId())) {// 妊娠糖尿病
                        metabolicHistory += this.getOptionName(pojo.getProblemOptionId()) + ";";
                    }
                    if ("210100700000241".equals(pojo.getProblemOptionId())) {// 妊娠高血压
                        metabolicHistory += this.getOptionName(pojo.getProblemOptionId()) + ";";
                    }
                    if ("210100700000244".equals(pojo.getProblemOptionId())) {// 甲状腺疾病
                        metabolicHistory += this.getOptionName(pojo.getProblemOptionId()) + ";";
                    }
                    if ("210100700000249".equals(pojo.getProblemOptionId())) {// 重度贫血
                        nutritiousHistory += "重度贫血;";
                    }
                }
                if ("402880e860e955590160e9642b870000".equals(pojo.getProblemId())) {// 高碘或造影剂接触史？
                    if ("11000022018011200250".equals(pojo.getProblemOptionId())) {// 是
                        nutritiousBingshi += "有高碘或造影剂接触史;";
                    }
                }
                if ("402880ef5a91a2b1015a91e8818e002b".equals(pojo.getProblemId())) {// 是否有巨大儿分娩？
                    if ("210100700000236".equals(pojo.getProblemOptionId())) {// 是
                        nutritiousHistory += "巨大儿分娩史;";
                    }
                }
                if ("402880ef5a91a2b1015a91c5d566000a".equals(pojo.getProblemId())) {// 您是否对某种物质有过过敏史？
                    if ("210100700000174".equals(pojo.getProblemOptionId())) {// 是
                        allergic_flag = true;
                    } else if ("210100700000175".equals(pojo.getProblemOptionId())) {// 否
                        allergic += "无过敏情况;";
                    }
                }
                if (allergic_flag) {
                    if ("402880ef5a91a2b1015a91c6905d000b".equals(pojo.getProblemId())// 您对哪种药物有过敏史：#
                            || "402880ef5a91a2b1015a91c7b794000c".equals(pojo.getProblemId())// 您对哪种食物有过敏史：#
                            || "402880ef5a91a2b1015a91ccd1ea000d".equals(pojo.getProblemId())) { // 其它过敏的物质：#
                        allergic += pojo.getAnswerContent() + ";";
                    }
                }
            }
            height = (height / 100) * (height / 100);
            float num = weight / height;// bmi = 孕前体重 / 身高
            if (num >= 24) {
                metabolicBingshi += "BMI:" + this.keepADecimal(num) + ";";// 代谢异常风险因素_孕前BMI
            } else if (num < 18.5) {
                nutritiousBingshi += "BMI:" + this.keepADecimal(num) + ";";// 营养不良风险因素_孕前BMI
            }
            guideListReportPojo.setMetabolicBingshi(this.formatString(metabolicBingshi));// 代谢异常风险因素_病史
            guideListReportPojo.setMetabolicHistory(this.formatString(metabolicHistory));// 代谢异常风险因素_既往史
            guideListReportPojo.setNutritiousBingshi(this.formatString(nutritiousBingshi));// 营养不良风险因素_孕前疾病
            guideListReportPojo.setNutritiousHistory(this.formatString(nutritiousHistory));// 营养不良风险因素_既往史
        }
        DiagnosisItemsCondition personCondition = new DiagnosisItemsCondition();
        personCondition.setDiagnosisId(diagnosisId);
        personCondition.setInspectStatus(3);// 3为已做完的pdf状态
        personCondition.setInspectCode("B00003");
        List<PregDiagnosisItemsVo> personList = pregDiagnosisItemDAO.queryDiagnosisItem(personCondition);
        if (CollectionUtils.isNotEmpty(personList)) {// 做了人体成分报告
            if (item != null && StringUtils.isNotBlank(item.getResultCode())) {// InBody
                InbodyCondition inbodyCondition = new InbodyCondition();
                inbodyCondition.setBcaId(item.getResultCode());
                String inbody = "";// 人体成分结论
                PregInBodyBcaPojo inbodyBcaPojo = inbodyDAO.getInbodyByCondition(inbodyCondition);// 查询inbody主表
                if (map.size() > 0) {
                    if (map.get("BODY00017") != null) {
                        // 细胞外水分比例，小于0.39为正常，大于0.39有疾病
                        float web = Float.parseFloat(map.get("BODY00017").getItemString());
                        if (web >= 0.40f) {
                            inbody += "浮肿;";
                        } else if (web >= 0.39f) {
                            inbody += "轻度浮肿;";
                        }
                    }
                    if (map.get("BODY00046") != null) {// 体重（1.3.3新加的需求，去除体脂肪新增体重）
                        String infoWt = map.get("BODY00046").getItemResult();
                        if ("体重增长过快".equals(infoWt)) {
                            inbody += "体重增长过快;";
                        } else if ("体重增长过缓".equals(infoWt)) {
                            inbody += "体重增长过缓;";
                        }
                    }
                }
                if (inbodyBcaPojo != null) {
                    BigDecimal biaozhun = new BigDecimal("90");// 标准为百分之90
                    if (inbodyBcaPojo.getPilt() != null && inbodyBcaPojo.getPilt().compareTo(biaozhun) == -1) {// 躯干肌肉百分比不足
                        inbody += "躯干力量不足;";
                    }
                    if ((inbodyBcaPojo.getPilrl() != null && inbodyBcaPojo.getPilll() != null)
                            && (inbodyBcaPojo.getPilrl().compareTo(biaozhun) == -1
                            || inbodyBcaPojo.getPilll().compareTo(biaozhun) == -1)) {// 左下肢或右下肢不足
                        inbody += "下肢力量不足;";
                    }
                    if (inbodyBcaPojo.getProtein() != null && inbodyBcaPojo.getProteinMax() != null
                            && inbodyBcaPojo.getProteinMin() != null) {// 蛋白质
                        if (inbodyBcaPojo.getProtein().compareTo(inbodyBcaPojo.getProteinMin()) == -1) {
                            inbody += "蛋白质不足;";
                        }
                    }
                    // if (inbodyBcaPojo.getBfm() != null && inbodyBcaPojo.getBfmMax() != null
                    // && inbodyBcaPojo.getBfmMin() != null) {// 体脂肪
                    // if (inbodyBcaPojo.getBfm().compareTo(inbodyBcaPojo.getBfmMin()) == -1) {
                    // inbody += "体脂肪不足;";
                    // } else if (inbodyBcaPojo.getBfm().compareTo(inbodyBcaPojo.getBfmMax()) == 1) {
                    // inbody += "体脂肪过量;";
                    // }
                    // }
                    if (inbodyBcaPojo.getPsmm() != null && inbodyBcaPojo.getPsmm().compareTo(biaozhun) == -1) {// 骨骼肌正常值范围
                                                                                                               // 90-110
                        inbody += "肌肉量不足;";
                    }
                }
                guideListReportPojo.setInbody(this.formatString(inbody) == "无" ? "正常" : this.formatString(inbody));// 如果没有显示正常
            }
        } else {// 没做人体成分报告
            guideListReportPojo.setInbody("--");// 如果没有显示--
        }
        guideListReportPojo.setRup(nutritiousReportPojo.getStaminaRup());// 体能消耗情况
        guideListReportPojo.setFoodRisk(nutritiousReportPojo.getFoodRisk());// 膳食结构风险
        guideListReportPojo.setIllRisk(nutritiousReportPojo.getIllRisk());// 致病饮食风险
        guideListReportPojo.setFoodQuestion(nutritiousReportPojo.getFoodQuestion());// 膳食问题
        guideListReportPojo.setNutritionalRequirements(nutritiousReportPojo.getNutritionalRequirements());// 营养咨询需求
        guideListReportPojo.setAllergic(this.formatString(allergic));// 过敏情况
        guideListReportPojo.setSuggest(suggest);
        return guideListReportPojo;
    }

    /**
     * 返回选项名称
     * 
     * @author dhs
     * @param optionId
     */
    private String getOptionName(String optionId) {
        return questionDAO.getOptionById(optionId).getOptionContent();
    }

    /**
     * 去除最后一个;
     * 
     * @author dhs
     * @param String
     */
    private String formatString(String str) {
        String butter = "";
        if (StringUtils.isNotBlank(str)) {
            butter = str.substring(0, str.length() - 1);
        } else {
            butter = "无";
        }
        return butter;
    }

    /**
     * 保留一位小数
     * 
     * @author dhs
     * @param String
     */
    private String keepADecimal(float num) {
        if (String.valueOf(num).contains(".")) {
            DecimalFormat decimalFormat = new DecimalFormat("0.0");// 四舍五入保留一位小数
            return decimalFormat.format(num);
        } else {
            return String.valueOf(num);
        }
    }

}

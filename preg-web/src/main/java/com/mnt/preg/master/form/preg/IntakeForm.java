
package com.mnt.preg.master.form.preg;

/**
 * 膳食执行清单模板
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历： v1.0 2015-7-3 gss 初版
 */
public class IntakeForm {

    /** 编码 */
    private String intakeId;

    /** 模板名称 */
    private String intakeName;

    /** 膳食模式 */
    private String intakeMode;

    /** 食谱模板 */
    private String dietId;

    /** 食谱模板 */
    private String dietName;

    /** 膳食结构 */
    private String foodStructureId;

    /** 营养食品（天） */
    private String intakeProductDesc;

    /** 套餐提示（2周） */
    private String intakePrompt;

    /** 热量上限 */
    private String intakeCaloricMax;

    /** 热量下限 */
    private String intakeCaloricMin;

    /** 实际热量 */
    private String intakeActualEnergy;

    /** 碳水化合物 */
    private String intakeCbr;

    /** 碳水化合物占比 */
    private String intakeCbrPercent;

    /** 蛋白质 */
    private String intakeProtein;

    /** 蛋白质占比 */
    private String intakeProteinPercent;

    /** 脂肪 */
    private String intakeFat;

    /** 脂肪占比 */
    private String intakeFatPercent;

    /** 摄入量描述 */
    private String intakeDesc;

    /** 孕期阶段 */
    private String pregnantStage;

    /** 诊断 */
    private String intakeMark;

    /** 备注 */
    private String intakeRemark;

    /** 膳食明细 */
    private String detailsJson;

    /** 数据归属类型,code:DATA_BELONG_TYPE */
    private String dataType;

    // 早餐
    // 十月糕
    private String c00000CA000000000001_F02001;

    // 十月奶昔
    private String c00000CA000000000001_F02002;

    // 餐包
    private String c00000CA000000000001_F02003;

    // 谷薯
    private String c00000CA000000000001_F01001;

    // 蔬菜
    private String c00000CA000000000001_F01002;

    // 鱼肉蛋
    private String c00000CA000000000001_F01004;

    // 大豆类
    private String c00000CA000000000001_F01005;

    // 油
    private String c00000CA000000000001_F01008;

    // 调味品
    private String c00000CA000000000001_F01009;

    // //上午加餐
    // 十月糕
    private String c00000CA000000000002_F02001;

    // 十月奶昔
    private String c00000CA000000000002_F02002;

    // 餐包
    private String c00000CA000000000002_F02003;

    // 水果
    private String c00000CA000000000002_F01003;

    // 坚果
    private String c00000CA000000000002_F01006;

    // 奶及奶制品
    private String c00000CA000000000002_F01007;

    // 其它
    private String swjc_qt;

    // //午餐
    // 十月糕
    private String c00000CA000000000003_F02001;

    // 十月奶昔
    private String c00000CA000000000003_F02002;

    // 餐包
    private String c00000CA000000000003_F02003;

    // 谷薯
    private String c00000CA000000000003_F01001;

    // 蔬菜
    private String c00000CA000000000003_F01002;

    // 鱼肉蛋
    private String c00000CA000000000003_F01004;

    // 大豆类
    private String c00000CA000000000003_F01005;

    // 油
    private String c00000CA000000000003_F01008;

    // 调味品
    private String c00000CA000000000003_F01009;

    // //下午加餐
    // 十月糕
    private String c00000CA000000000004_F02001;

    // 十月奶昔
    private String c00000CA000000000004_F02002;

    // 餐包
    private String c00000CA000000000004_F02003;

    // 水果
    private String c00000CA000000000004_F01003;

    // 坚果
    private String c00000CA000000000004_F01006;

    // 奶及奶制品
    private String c00000CA000000000004_F01007;

    // 其它
    private String xwjc_qt;

    // //晚餐
    // 月糕
    private String c00000CA000000000005_F02001;

    // 月奶昔
    private String c00000CA000000000005_F02002;

    // 包
    private String c00000CA000000000005_F02003;

    // 薯
    private String c00000CA000000000005_F01001;

    // 菜
    private String c00000CA000000000005_F01002;

    // 肉蛋
    private String c00000CA000000000005_F01004;

    // 豆类
    private String c00000CA000000000005_F01005;

    //
    private String c00000CA000000000005_F01008;

    // 味品
    private String c00000CA000000000005_F01009;

    // //睡前加餐
    // 十月糕
    private String c00000CA000000000006_F02001;

    // 十月奶昔
    private String c00000CA000000000006_F02002;

    // 餐包
    private String c00000CA000000000006_F02003;

    // 水果
    private String c00000CA000000000006_F01003;

    // 坚果
    private String c00000CA000000000006_F01006;

    // 奶及奶制品
    private String c00000CA000000000006_F01007;

    // 其它
    private String sqjc_qt;

    public String getDetailsJson() {
        return detailsJson;
    }

    public void setDetailsJson(String detailsJson) {
        this.detailsJson = detailsJson;
    }

    public String getIntakeId() {
        return intakeId;
    }

    public void setIntakeId(String intakeId) {
        this.intakeId = intakeId;
    }

    public String getIntakeName() {
        return intakeName;
    }

    public void setIntakeName(String intakeName) {
        this.intakeName = intakeName;
    }

    public String getIntakeMode() {
        return intakeMode;
    }

    public void setIntakeMode(String intakeMode) {
        this.intakeMode = intakeMode;
    }

    public String getDietId() {
        return dietId;
    }

    public void setDietId(String dietId) {
        this.dietId = dietId;
    }

    public String getFoodStructureId() {
        return foodStructureId;
    }

    public void setFoodStructureId(String foodStructureId) {
        this.foodStructureId = foodStructureId;
    }

    public String getIntakeProductDesc() {
        return intakeProductDesc;
    }

    public void setIntakeProductDesc(String intakeProductDesc) {
        this.intakeProductDesc = intakeProductDesc;
    }

    public String getIntakePrompt() {
        return intakePrompt;
    }

    public void setIntakePrompt(String intakePrompt) {
        this.intakePrompt = intakePrompt;
    }

    public String getIntakeCaloricMax() {
        return intakeCaloricMax;
    }

    public void setIntakeCaloricMax(String intakeCaloricMax) {
        this.intakeCaloricMax = intakeCaloricMax;
    }

    public String getIntakeCaloricMin() {
        return intakeCaloricMin;
    }

    public void setIntakeCaloricMin(String intakeCaloricMin) {
        this.intakeCaloricMin = intakeCaloricMin;
    }

    public String getIntakeActualEnergy() {
        return intakeActualEnergy;
    }

    public void setIntakeActualEnergy(String intakeActualEnergy) {
        this.intakeActualEnergy = intakeActualEnergy;
    }

    public String getIntakeCbr() {
        return intakeCbr;
    }

    public void setIntakeCbr(String intakeCbr) {
        this.intakeCbr = intakeCbr;
    }

    public String getIntakeCbrPercent() {
        return intakeCbrPercent;
    }

    public void setIntakeCbrPercent(String intakeCbrPercent) {
        this.intakeCbrPercent = intakeCbrPercent;
    }

    public String getIntakeProtein() {
        return intakeProtein;
    }

    public void setIntakeProtein(String intakeProtein) {
        this.intakeProtein = intakeProtein;
    }

    public String getIntakeProteinPercent() {
        return intakeProteinPercent;
    }

    public void setIntakeProteinPercent(String intakeProteinPercent) {
        this.intakeProteinPercent = intakeProteinPercent;
    }

    public String getIntakeFat() {
        return intakeFat;
    }

    public void setIntakeFat(String intakeFat) {
        this.intakeFat = intakeFat;
    }

    public String getIntakeFatPercent() {
        return intakeFatPercent;
    }

    public void setIntakeFatPercent(String intakeFatPercent) {
        this.intakeFatPercent = intakeFatPercent;
    }

    public String getIntakeDesc() {
        return intakeDesc;
    }

    public void setIntakeDesc(String intakeDesc) {
        this.intakeDesc = intakeDesc;
    }

    public String getC00000CA000000000001_F02001() {
        return c00000CA000000000001_F02001;
    }

    public void setC00000CA000000000001_F02001(String c00000ca000000000001_F02001) {
        c00000CA000000000001_F02001 = c00000ca000000000001_F02001;
    }

    public String getC00000CA000000000001_F02002() {
        return c00000CA000000000001_F02002;
    }

    public void setC00000CA000000000001_F02002(String c00000ca000000000001_F02002) {
        c00000CA000000000001_F02002 = c00000ca000000000001_F02002;
    }

    public String getC00000CA000000000001_F02003() {
        return c00000CA000000000001_F02003;
    }

    public void setC00000CA000000000001_F02003(String c00000ca000000000001_F02003) {
        c00000CA000000000001_F02003 = c00000ca000000000001_F02003;
    }

    public String getC00000CA000000000001_F01001() {
        return c00000CA000000000001_F01001;
    }

    public void setC00000CA000000000001_F01001(String c00000ca000000000001_F01001) {
        c00000CA000000000001_F01001 = c00000ca000000000001_F01001;
    }

    public String getC00000CA000000000001_F01002() {
        return c00000CA000000000001_F01002;
    }

    public void setC00000CA000000000001_F01002(String c00000ca000000000001_F01002) {
        c00000CA000000000001_F01002 = c00000ca000000000001_F01002;
    }

    public String getC00000CA000000000001_F01004() {
        return c00000CA000000000001_F01004;
    }

    public void setC00000CA000000000001_F01004(String c00000ca000000000001_F01004) {
        c00000CA000000000001_F01004 = c00000ca000000000001_F01004;
    }

    public String getC00000CA000000000001_F01005() {
        return c00000CA000000000001_F01005;
    }

    public void setC00000CA000000000001_F01005(String c00000ca000000000001_F01005) {
        c00000CA000000000001_F01005 = c00000ca000000000001_F01005;
    }

    public String getC00000CA000000000001_F01008() {
        return c00000CA000000000001_F01008;
    }

    public void setC00000CA000000000001_F01008(String c00000ca000000000001_F01008) {
        c00000CA000000000001_F01008 = c00000ca000000000001_F01008;
    }

    public String getC00000CA000000000001_F01009() {
        return c00000CA000000000001_F01009;
    }

    public void setC00000CA000000000001_F01009(String c00000ca000000000001_F01009) {
        c00000CA000000000001_F01009 = c00000ca000000000001_F01009;
    }

    public String getC00000CA000000000002_F02001() {
        return c00000CA000000000002_F02001;
    }

    public void setC00000CA000000000002_F02001(String c00000ca000000000002_F02001) {
        c00000CA000000000002_F02001 = c00000ca000000000002_F02001;
    }

    public String getC00000CA000000000002_F02002() {
        return c00000CA000000000002_F02002;
    }

    public void setC00000CA000000000002_F02002(String c00000ca000000000002_F02002) {
        c00000CA000000000002_F02002 = c00000ca000000000002_F02002;
    }

    public String getC00000CA000000000002_F02003() {
        return c00000CA000000000002_F02003;
    }

    public void setC00000CA000000000002_F02003(String c00000ca000000000002_F02003) {
        c00000CA000000000002_F02003 = c00000ca000000000002_F02003;
    }

    public String getC00000CA000000000002_F01003() {
        return c00000CA000000000002_F01003;
    }

    public void setC00000CA000000000002_F01003(String c00000ca000000000002_F01003) {
        c00000CA000000000002_F01003 = c00000ca000000000002_F01003;
    }

    public String getC00000CA000000000002_F01006() {
        return c00000CA000000000002_F01006;
    }

    public void setC00000CA000000000002_F01006(String c00000ca000000000002_F01006) {
        c00000CA000000000002_F01006 = c00000ca000000000002_F01006;
    }

    public String getC00000CA000000000002_F01007() {
        return c00000CA000000000002_F01007;
    }

    public void setC00000CA000000000002_F01007(String c00000ca000000000002_F01007) {
        c00000CA000000000002_F01007 = c00000ca000000000002_F01007;
    }

    public String getC00000CA000000000003_F02001() {
        return c00000CA000000000003_F02001;
    }

    public void setC00000CA000000000003_F02001(String c00000ca000000000003_F02001) {
        c00000CA000000000003_F02001 = c00000ca000000000003_F02001;
    }

    public String getC00000CA000000000003_F02002() {
        return c00000CA000000000003_F02002;
    }

    public void setC00000CA000000000003_F02002(String c00000ca000000000003_F02002) {
        c00000CA000000000003_F02002 = c00000ca000000000003_F02002;
    }

    public String getC00000CA000000000003_F02003() {
        return c00000CA000000000003_F02003;
    }

    public void setC00000CA000000000003_F02003(String c00000ca000000000003_F02003) {
        c00000CA000000000003_F02003 = c00000ca000000000003_F02003;
    }

    public String getC00000CA000000000003_F01001() {
        return c00000CA000000000003_F01001;
    }

    public void setC00000CA000000000003_F01001(String c00000ca000000000003_F01001) {
        c00000CA000000000003_F01001 = c00000ca000000000003_F01001;
    }

    public String getC00000CA000000000003_F01002() {
        return c00000CA000000000003_F01002;
    }

    public void setC00000CA000000000003_F01002(String c00000ca000000000003_F01002) {
        c00000CA000000000003_F01002 = c00000ca000000000003_F01002;
    }

    public String getC00000CA000000000003_F01004() {
        return c00000CA000000000003_F01004;
    }

    public void setC00000CA000000000003_F01004(String c00000ca000000000003_F01004) {
        c00000CA000000000003_F01004 = c00000ca000000000003_F01004;
    }

    public String getC00000CA000000000003_F01005() {
        return c00000CA000000000003_F01005;
    }

    public void setC00000CA000000000003_F01005(String c00000ca000000000003_F01005) {
        c00000CA000000000003_F01005 = c00000ca000000000003_F01005;
    }

    public String getC00000CA000000000003_F01008() {
        return c00000CA000000000003_F01008;
    }

    public void setC00000CA000000000003_F01008(String c00000ca000000000003_F01008) {
        c00000CA000000000003_F01008 = c00000ca000000000003_F01008;
    }

    public String getC00000CA000000000003_F01009() {
        return c00000CA000000000003_F01009;
    }

    public void setC00000CA000000000003_F01009(String c00000ca000000000003_F01009) {
        c00000CA000000000003_F01009 = c00000ca000000000003_F01009;
    }

    public String getC00000CA000000000004_F02001() {
        return c00000CA000000000004_F02001;
    }

    public void setC00000CA000000000004_F02001(String c00000ca000000000004_F02001) {
        c00000CA000000000004_F02001 = c00000ca000000000004_F02001;
    }

    public String getC00000CA000000000004_F02002() {
        return c00000CA000000000004_F02002;
    }

    public void setC00000CA000000000004_F02002(String c00000ca000000000004_F02002) {
        c00000CA000000000004_F02002 = c00000ca000000000004_F02002;
    }

    public String getC00000CA000000000004_F02003() {
        return c00000CA000000000004_F02003;
    }

    public void setC00000CA000000000004_F02003(String c00000ca000000000004_F02003) {
        c00000CA000000000004_F02003 = c00000ca000000000004_F02003;
    }

    public String getC00000CA000000000004_F01003() {
        return c00000CA000000000004_F01003;
    }

    public void setC00000CA000000000004_F01003(String c00000ca000000000004_F01003) {
        c00000CA000000000004_F01003 = c00000ca000000000004_F01003;
    }

    public String getC00000CA000000000004_F01006() {
        return c00000CA000000000004_F01006;
    }

    public void setC00000CA000000000004_F01006(String c00000ca000000000004_F01006) {
        c00000CA000000000004_F01006 = c00000ca000000000004_F01006;
    }

    public String getC00000CA000000000004_F01007() {
        return c00000CA000000000004_F01007;
    }

    public void setC00000CA000000000004_F01007(String c00000ca000000000004_F01007) {
        c00000CA000000000004_F01007 = c00000ca000000000004_F01007;
    }

    public String getC00000CA000000000005_F02001() {
        return c00000CA000000000005_F02001;
    }

    public void setC00000CA000000000005_F02001(String c00000ca000000000005_F02001) {
        c00000CA000000000005_F02001 = c00000ca000000000005_F02001;
    }

    public String getC00000CA000000000005_F02002() {
        return c00000CA000000000005_F02002;
    }

    public void setC00000CA000000000005_F02002(String c00000ca000000000005_F02002) {
        c00000CA000000000005_F02002 = c00000ca000000000005_F02002;
    }

    public String getC00000CA000000000005_F02003() {
        return c00000CA000000000005_F02003;
    }

    public void setC00000CA000000000005_F02003(String c00000ca000000000005_F02003) {
        c00000CA000000000005_F02003 = c00000ca000000000005_F02003;
    }

    public String getC00000CA000000000005_F01001() {
        return c00000CA000000000005_F01001;
    }

    public void setC00000CA000000000005_F01001(String c00000ca000000000005_F01001) {
        c00000CA000000000005_F01001 = c00000ca000000000005_F01001;
    }

    public String getC00000CA000000000005_F01002() {
        return c00000CA000000000005_F01002;
    }

    public void setC00000CA000000000005_F01002(String c00000ca000000000005_F01002) {
        c00000CA000000000005_F01002 = c00000ca000000000005_F01002;
    }

    public String getC00000CA000000000005_F01004() {
        return c00000CA000000000005_F01004;
    }

    public void setC00000CA000000000005_F01004(String c00000ca000000000005_F01004) {
        c00000CA000000000005_F01004 = c00000ca000000000005_F01004;
    }

    public String getC00000CA000000000005_F01005() {
        return c00000CA000000000005_F01005;
    }

    public void setC00000CA000000000005_F01005(String c00000ca000000000005_F01005) {
        c00000CA000000000005_F01005 = c00000ca000000000005_F01005;
    }

    public String getC00000CA000000000005_F01008() {
        return c00000CA000000000005_F01008;
    }

    public void setC00000CA000000000005_F01008(String c00000ca000000000005_F01008) {
        c00000CA000000000005_F01008 = c00000ca000000000005_F01008;
    }

    public String getC00000CA000000000005_F01009() {
        return c00000CA000000000005_F01009;
    }

    public void setC00000CA000000000005_F01009(String c00000ca000000000005_F01009) {
        c00000CA000000000005_F01009 = c00000ca000000000005_F01009;
    }

    public String getC00000CA000000000006_F02001() {
        return c00000CA000000000006_F02001;
    }

    public void setC00000CA000000000006_F02001(String c00000ca000000000006_F02001) {
        c00000CA000000000006_F02001 = c00000ca000000000006_F02001;
    }

    public String getC00000CA000000000006_F02002() {
        return c00000CA000000000006_F02002;
    }

    public void setC00000CA000000000006_F02002(String c00000ca000000000006_F02002) {
        c00000CA000000000006_F02002 = c00000ca000000000006_F02002;
    }

    public String getC00000CA000000000006_F02003() {
        return c00000CA000000000006_F02003;
    }

    public void setC00000CA000000000006_F02003(String c00000ca000000000006_F02003) {
        c00000CA000000000006_F02003 = c00000ca000000000006_F02003;
    }

    public String getC00000CA000000000006_F01003() {
        return c00000CA000000000006_F01003;
    }

    public void setC00000CA000000000006_F01003(String c00000ca000000000006_F01003) {
        c00000CA000000000006_F01003 = c00000ca000000000006_F01003;
    }

    public String getC00000CA000000000006_F01006() {
        return c00000CA000000000006_F01006;
    }

    public void setC00000CA000000000006_F01006(String c00000ca000000000006_F01006) {
        c00000CA000000000006_F01006 = c00000ca000000000006_F01006;
    }

    public String getC00000CA000000000006_F01007() {
        return c00000CA000000000006_F01007;
    }

    public void setC00000CA000000000006_F01007(String c00000ca000000000006_F01007) {
        c00000CA000000000006_F01007 = c00000ca000000000006_F01007;
    }

    public String getSwjc_qt() {
        return swjc_qt;
    }

    public void setSwjc_qt(String swjc_qt) {
        this.swjc_qt = swjc_qt;
    }

    public String getPregnantStage() {
        return pregnantStage;
    }

    public void setPregnantStage(String pregnantStage) {
        this.pregnantStage = pregnantStage;
    }

    public String getXwjc_qt() {
        return xwjc_qt;
    }

    public void setXwjc_qt(String xwjc_qt) {
        this.xwjc_qt = xwjc_qt;
    }

    public String getSqjc_qt() {
        return sqjc_qt;
    }

    public void setSqjc_qt(String sqjc_qt) {
        this.sqjc_qt = sqjc_qt;
    }

    public String getIntakeMark() {
        return intakeMark;
    }

    public void setIntakeMark(String intakeMark) {
        this.intakeMark = intakeMark;
    }

    public String getIntakeRemark() {
        return intakeRemark;
    }

    public void setIntakeRemark(String intakeRemark) {
        this.intakeRemark = intakeRemark;
    }

    public String getDietName() {
        return dietName;
    }

    public void setDietName(String dietName) {
        this.dietName = dietName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}

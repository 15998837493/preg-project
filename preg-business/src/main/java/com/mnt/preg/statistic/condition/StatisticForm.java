/*
 * StatisticForm.java    1.0  2018年8月17日
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.statistic.condition;

/**
 * 统计超级查询条件form
 * 
 * @author lipeng
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018年8月17日 lipeng 初版
 */
public class StatisticForm {

    /** 阶段1-妊娠期，2-产后 */
    private String pregPeriod;

    /** 时间类型 */
    private String timeType;

    /** 开始日期（妊娠期：预产期，产后：分娩时间） */
    private String startDate;

    /** 结束日期（妊娠期：预产期，产后：分娩时间） */
    private String endDate;

    /** 营养医师 user_id */
    private String[] userSelect;

    /** 产检医院（本院：1，其他医院：2） */
    private String birthSeize;

    /** 分娩医院（本院：1，其他医院：2） */
    private String birthChild;

    /** 身高from */
    private String heightFrom;

    /** 身高to */
    private String heightTo;

    /** 年龄from */
    private String ageFrom;

    /** 年龄to */
    private String ageTo;

    /** bmi范围 （0-18.5） */
    private String[] bmiScope;

    /** 胎数（单胎，双胎，三胎及以上） */
    private String[] birthNum;

    /** 病史（problem_option_id） */
    private String[] diseaseHistory;

    /** 家族史 （problem_option_id） */
    private String[] familyHistory;

    /** 妊娠并发症（problem_option_id） */
    private String[] pregComplications;

    /** 孕次（1次：1，2次：2，3次：3，>3次：4） */
    private String[] pregTimes;

    /** 产次 （0次：0，1次：1，2次：2，>2次：3） */
    private String[] birthTimes;

    /** 不良孕史 （problem_id） */
    private String[] badPregHistory;

    /** 不良产史 （problem_option_id） */
    private String[] badBirthHistory;

    /** 正常检验项目 （item_id） */
    private String normalInspectItemIds;

    /** 体重增长情况 （正常：正常，过快：体重增长过快，过缓：体重增长过缓） */
    private String[] weightCondition;

    /** 蛋白质降低（选中：1） */
    private String proteinReduce;

    /** 骨骼肌降低（选中：1） */
    private String muscleReduce;

    /** 浮肿情况 （轻微：轻微，浮肿：浮肿） */
    private String[] fuzhongCondition;

    /** 相位角from */
    private String xiangweiFrom;

    /** 相位角to */
    private String xiangweiTo;

    /** 诊断（disease_id） */
    private String deseaseIds;

    /** 营养门诊就诊次数from */
    private String menzhenNumFrom;

    /** 营养门诊就诊次数to */
    private String menzhenNumTo;

    /** 首次孕期营养门诊就诊孕周数from */
    private String menzhenPregWeekFrom;

    /** 首次孕期营养门诊就诊孕周数to */
    private String menzhenPregWeekTo;

    /** 首次产检妊娠分险级别（绿色,黄色,橙色,红色,紫色） */
    private String[] firstLevel;

    /** 末次产检妊娠分险级别 （绿色,黄色,橙色,红色,紫色） */
    private String[] lastLevel;

    /** 是否一日门诊 （暂时不用） */
    private String isOneday;

    /** 是否MDT门诊（暂时不用） */
    private String isMDT;

    /** 分娩时孕周from */
    private String birthPregWeekFrom;

    /** 分娩时孕周to */
    private String birthPregWeekTo;

    /** 分娩方式 */
    private String[] childbirthType;

    /** 分娩方位 */
    private String birthPlace;

    /** 麻醉类型 */
    private String[] mazuiType;

    /** 是否危重产妇 */
    private String isDangerPregWoman;

    /** 是否产前检查 */
    private String isInspectBeforeBirth;

    /** 产妇并发症情况 */
    private String complicationIds;

    /** 产妇结局 */
    private String isLiveOrDead;

    /** 产妇何时死亡 */
    private String whenDead;

    /** 新生儿性别 */
    private String[] newBirthSex;

    /** 新生儿体重from */
    private String weightFrom;

    /** 新生儿体重to */
    private String weightTo;

    /** 出生缺陷（选中：1） */
    private String birthDefect;

    /** 是否抢救 */
    private String isRescue;

    /** 新生儿并发症 */
    private String childComplicationIds;

    /** 阿氏评分1分钟from */
    private String ashiOneMinuteFrom;

    /** 阿氏评分1分钟 to */
    private String ashiOneMinuteTo;

    /** 阿氏评分5分钟from */
    private String ashiFiveMinuteFrom;

    /** 阿氏评分5分钟to */
    private String ashiFiveMinuteTo;

    /** 阿氏评分10分钟from */
    private String ashiTenMinuteFrom;

    /** 阿氏评分10分钟to */
    private String ashiTenMinuteTo;

    /** 新生儿是否死亡 （0：存活；1：死亡） */
    private String isChildLiveOrDead;

    /** 羊水量 */
    private String[] afv;

    /** 羊水性状 */
    private String[] afluid;

    public String getPregPeriod() {
        return pregPeriod;
    }

    public void setPregPeriod(String pregPeriod) {
        this.pregPeriod = pregPeriod;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String[] getUserSelect() {
        return userSelect;
    }

    public void setUserSelect(String[] userSelect) {
        this.userSelect = userSelect;
    }

    public String getBirthSeize() {
        return birthSeize;
    }

    public void setBirthSeize(String birthSeize) {
        this.birthSeize = birthSeize;
    }

    public String getBirthChild() {
        return birthChild;
    }

    public void setBirthChild(String birthChild) {
        this.birthChild = birthChild;
    }

    public String getHeightFrom() {
        return heightFrom;
    }

    public void setHeightFrom(String heightFrom) {
        this.heightFrom = heightFrom;
    }

    public String getHeightTo() {
        return heightTo;
    }

    public void setHeightTo(String heightTo) {
        this.heightTo = heightTo;
    }

    public String getAgeFrom() {
        return ageFrom;
    }

    public void setAgeFrom(String ageFrom) {
        this.ageFrom = ageFrom;
    }

    public String getAgeTo() {
        return ageTo;
    }

    public void setAgeTo(String ageTo) {
        this.ageTo = ageTo;
    }

    public String[] getBmiScope() {
        return bmiScope;
    }

    public void setBmiScope(String[] bmiScope) {
        this.bmiScope = bmiScope;
    }

    public String[] getBirthNum() {
        return birthNum;
    }

    public void setBirthNum(String[] birthNum) {
        this.birthNum = birthNum;
    }

    public String[] getDiseaseHistory() {
        return diseaseHistory;
    }

    public void setDiseaseHistory(String[] diseaseHistory) {
        this.diseaseHistory = diseaseHistory;
    }

    public String[] getFamilyHistory() {
        return familyHistory;
    }

    public void setFamilyHistory(String[] familyHistory) {
        this.familyHistory = familyHistory;
    }

    public String[] getPregComplications() {
        return pregComplications;
    }

    public void setPregComplications(String[] pregComplications) {
        this.pregComplications = pregComplications;
    }

    public String[] getPregTimes() {
        return pregTimes;
    }

    public void setPregTimes(String[] pregTimes) {
        this.pregTimes = pregTimes;
    }

    public String[] getBirthTimes() {
        return birthTimes;
    }

    public void setBirthTimes(String[] birthTimes) {
        this.birthTimes = birthTimes;
    }

    public String[] getBadPregHistory() {
        return badPregHistory;
    }

    public void setBadPregHistory(String[] badPregHistory) {
        this.badPregHistory = badPregHistory;
    }

    public String[] getBadBirthHistory() {
        return badBirthHistory;
    }

    public void setBadBirthHistory(String[] badBirthHistory) {
        this.badBirthHistory = badBirthHistory;
    }

    public String getNormalInspectItemIds() {
        return normalInspectItemIds;
    }

    public void setNormalInspectItemIds(String normalInspectItemIds) {
        this.normalInspectItemIds = normalInspectItemIds;
    }

    public String[] getWeightCondition() {
        return weightCondition;
    }

    public void setWeightCondition(String[] weightCondition) {
        this.weightCondition = weightCondition;
    }

    public String getProteinReduce() {
        return proteinReduce;
    }

    public void setProteinReduce(String proteinReduce) {
        this.proteinReduce = proteinReduce;
    }

    public String getMuscleReduce() {
        return muscleReduce;
    }

    public void setMuscleReduce(String muscleReduce) {
        this.muscleReduce = muscleReduce;
    }

    public String[] getFuzhongCondition() {
        return fuzhongCondition;
    }

    public void setFuzhongCondition(String[] fuzhongCondition) {
        this.fuzhongCondition = fuzhongCondition;
    }

    public String getXiangweiFrom() {
        return xiangweiFrom;
    }

    public void setXiangweiFrom(String xiangweiFrom) {
        this.xiangweiFrom = xiangweiFrom;
    }

    public String getXiangweiTo() {
        return xiangweiTo;
    }

    public void setXiangweiTo(String xiangweiTo) {
        this.xiangweiTo = xiangweiTo;
    }

    public String getDeseaseIds() {
        return deseaseIds;
    }

    public void setDeseaseIds(String deseaseIds) {
        this.deseaseIds = deseaseIds;
    }

    public String getMenzhenNumFrom() {
        return menzhenNumFrom;
    }

    public void setMenzhenNumFrom(String menzhenNumFrom) {
        this.menzhenNumFrom = menzhenNumFrom;
    }

    public String getMenzhenNumTo() {
        return menzhenNumTo;
    }

    public void setMenzhenNumTo(String menzhenNumTo) {
        this.menzhenNumTo = menzhenNumTo;
    }

    public String getMenzhenPregWeekFrom() {
        return menzhenPregWeekFrom;
    }

    public void setMenzhenPregWeekFrom(String menzhenPregWeekFrom) {
        this.menzhenPregWeekFrom = menzhenPregWeekFrom;
    }

    public String getMenzhenPregWeekTo() {
        return menzhenPregWeekTo;
    }

    public void setMenzhenPregWeekTo(String menzhenPregWeekTo) {
        this.menzhenPregWeekTo = menzhenPregWeekTo;
    }

    public String[] getFirstLevel() {
        return firstLevel;
    }

    public void setFirstLevel(String[] firstLevel) {
        this.firstLevel = firstLevel;
    }

    public String[] getLastLevel() {
        return lastLevel;
    }

    public void setLastLevel(String[] lastLevel) {
        this.lastLevel = lastLevel;
    }

    public String getIsOneday() {
        return isOneday;
    }

    public void setIsOneday(String isOneday) {
        this.isOneday = isOneday;
    }

    public String getIsMDT() {
        return isMDT;
    }

    public void setIsMDT(String isMDT) {
        this.isMDT = isMDT;
    }

    public String getBirthPregWeekFrom() {
        return birthPregWeekFrom;
    }

    public void setBirthPregWeekFrom(String birthPregWeekFrom) {
        this.birthPregWeekFrom = birthPregWeekFrom;
    }

    public String getBirthPregWeekTo() {
        return birthPregWeekTo;
    }

    public void setBirthPregWeekTo(String birthPregWeekTo) {
        this.birthPregWeekTo = birthPregWeekTo;
    }

    public String[] getChildbirthType() {
        return childbirthType;
    }

    public void setChildbirthType(String[] childbirthType) {
        this.childbirthType = childbirthType;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String[] getMazuiType() {
        return mazuiType;
    }

    public void setMazuiType(String[] mazuiType) {
        this.mazuiType = mazuiType;
    }

    public String getIsDangerPregWoman() {
        return isDangerPregWoman;
    }

    public void setIsDangerPregWoman(String isDangerPregWoman) {
        this.isDangerPregWoman = isDangerPregWoman;
    }

    public String getIsInspectBeforeBirth() {
        return isInspectBeforeBirth;
    }

    public void setIsInspectBeforeBirth(String isInspectBeforeBirth) {
        this.isInspectBeforeBirth = isInspectBeforeBirth;
    }

    public String getComplicationIds() {
        return complicationIds;
    }

    public void setComplicationIds(String complicationIds) {
        this.complicationIds = complicationIds;
    }

    public String getIsLiveOrDead() {
        return isLiveOrDead;
    }

    public void setIsLiveOrDead(String isLiveOrDead) {
        this.isLiveOrDead = isLiveOrDead;
    }

    public String getWhenDead() {
        return whenDead;
    }

    public void setWhenDead(String whenDead) {
        this.whenDead = whenDead;
    }

    public String[] getNewBirthSex() {
        return newBirthSex;
    }

    public void setNewBirthSex(String[] newBirthSex) {
        this.newBirthSex = newBirthSex;
    }

    public String getWeightFrom() {
        return weightFrom;
    }

    public void setWeightFrom(String weightFrom) {
        this.weightFrom = weightFrom;
    }

    public String getWeightTo() {
        return weightTo;
    }

    public void setWeightTo(String weightTo) {
        this.weightTo = weightTo;
    }

    public String getBirthDefect() {
        return birthDefect;
    }

    public void setBirthDefect(String birthDefect) {
        this.birthDefect = birthDefect;
    }

    public String getIsRescue() {
        return isRescue;
    }

    public void setIsRescue(String isRescue) {
        this.isRescue = isRescue;
    }

    public String getChildComplicationIds() {
        return childComplicationIds;
    }

    public void setChildComplicationIds(String childComplicationIds) {
        this.childComplicationIds = childComplicationIds;
    }

    public String getAshiOneMinuteFrom() {
        return ashiOneMinuteFrom;
    }

    public void setAshiOneMinuteFrom(String ashiOneMinuteFrom) {
        this.ashiOneMinuteFrom = ashiOneMinuteFrom;
    }

    public String getAshiOneMinuteTo() {
        return ashiOneMinuteTo;
    }

    public void setAshiOneMinuteTo(String ashiOneMinuteTo) {
        this.ashiOneMinuteTo = ashiOneMinuteTo;
    }

    public String getAshiFiveMinuteFrom() {
        return ashiFiveMinuteFrom;
    }

    public void setAshiFiveMinuteFrom(String ashiFiveMinuteFrom) {
        this.ashiFiveMinuteFrom = ashiFiveMinuteFrom;
    }

    public String getAshiFiveMinuteTo() {
        return ashiFiveMinuteTo;
    }

    public void setAshiFiveMinuteTo(String ashiFiveMinuteTo) {
        this.ashiFiveMinuteTo = ashiFiveMinuteTo;
    }

    public String getAshiTenMinuteFrom() {
        return ashiTenMinuteFrom;
    }

    public void setAshiTenMinuteFrom(String ashiTenMinuteFrom) {
        this.ashiTenMinuteFrom = ashiTenMinuteFrom;
    }

    public String getAshiTenMinuteTo() {
        return ashiTenMinuteTo;
    }

    public void setAshiTenMinuteTo(String ashiTenMinuteTo) {
        this.ashiTenMinuteTo = ashiTenMinuteTo;
    }

    public String getIsChildLiveOrDead() {
        return isChildLiveOrDead;
    }

    public void setIsChildLiveOrDead(String isChildLiveOrDead) {
        this.isChildLiveOrDead = isChildLiveOrDead;
    }

    public String[] getAfv() {
        return afv;
    }

    public void setAfv(String[] afv) {
        this.afv = afv;
    }

    public String[] getAfluid() {
        return afluid;
    }

    public void setAfluid(String[] afluid) {
        this.afluid = afluid;
    }

}

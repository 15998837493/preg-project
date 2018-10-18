
package com.mnt.preg.examitem.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 人体成分分析主表BAC
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-7-18 zcq 初版
 */
public class PregInBodyPojo implements Serializable {

    private static final long serialVersionUID = 7715614317930272952L;

    /** 编号 */
    private String bcaId;

    /** 检测数据编号 */
    private String datetimes;

    /** 编号 */
    private String custId;

    /** 报告路径 */
    private String inbodyPdfPath;

    /** 姓名 */
    private String name;

    /** 性别 */
    private String gender;

    /** 年龄 */
    private Integer age;

    /** 身高 */
    private BigDecimal height;

    /** 生日 */
    private String birthday;

    /** 检测日期 */
    private Date examDate;

    /** 当前体重 */
    private BigDecimal wt;

    /** 体重下限 */
    private BigDecimal wtMin;

    /** 体重上限 */
    private BigDecimal wtMax;

    /** 身体总水分 */
    private BigDecimal tbw;

    /** 身体总水分 --下限 */
    private BigDecimal tbwMin;

    /** 身体总水分 --上限 */
    private BigDecimal tbwMax;

    /** 蛋白质 */
    private BigDecimal protein;

    /** 蛋白质下限 */
    private BigDecimal proteinMin;

    /** 蛋白质上限 */
    private BigDecimal proteinMax;

    /** 无机盐 */
    private BigDecimal mineral;

    /** 无机盐下限 */
    private BigDecimal mineralMin;

    /** 无机盐上限 */
    private BigDecimal mineralMax;

    /** 体脂肪 */
    private BigDecimal bfm;

    /** 体脂肪下限 */
    private BigDecimal bfmMin;

    /** 体脂肪上限 */
    private BigDecimal bfmMax;

    /** 肌肉量 */
    private BigDecimal slm;

    /** 肌肉量 -- 下限 */
    private BigDecimal slmMin;

    /** 肌肉量 -- 上限 */
    private BigDecimal slmMax;

    /** 去脂体重 */
    private BigDecimal ffm;

    /** 去脂体重 -- 下限 */
    private BigDecimal ffmMin;

    /** 去脂体重 -- 上限 */
    private BigDecimal ffmMax;

    /** 细胞内水分 */
    private BigDecimal icw;

    /** 细胞内水分下限 */
    private BigDecimal icwMin;

    /** 细胞内水分上限 */
    private BigDecimal icwMax;

    /** 细胞外水分 */
    private BigDecimal ecw;

    /** 细胞外水分下限 */
    private BigDecimal ecwMin;

    /** 细胞外水分上限 */
    private BigDecimal ecwMax;

    /** 骨骼肌--下限 */
    private BigDecimal smmMin;

    /** 骨骼肌--上限 */
    private BigDecimal smmMax;

    /** 骨骼肌 */
    private BigDecimal smm;

    /** 身体质量参数 */
    private BigDecimal bmi;

    /** 身体质量参数--下限 */
    private BigDecimal bmiMin;

    /** 身体质量参数--上限 */
    private BigDecimal bmiMax;

    /** 体脂百分数 */
    private BigDecimal pbf;

    /** 体脂百分数--下限 */
    private BigDecimal pbfMin;

    /** 体脂百分数 --上限 */
    private BigDecimal pbfMax;

    /** 体脂肪百分数--下限 */
    private BigDecimal pbfmMin;

    /** 体脂肪百分数 --上限 */
    private BigDecimal pbfmMax;

    /** 腰臀比 */
    private BigDecimal whr;

    /** 腰臀比 下限 */
    private BigDecimal whrMin;

    /** 腰臀比上限 */
    private BigDecimal whrMax;

    /** 体重百分比 */
    private BigDecimal pwt;

    /** 骨骼肌百分比 */
    private BigDecimal psmm;

    /** 体脂肪百分比 */
    private BigDecimal pbfm;

    /** 右上肢肌肉（标准） */
    private BigDecimal plra;

    /** 右上肢肌肉（kg） */
    private BigDecimal lra;

    /** 右上肢肌肉（%） */
    private BigDecimal pilra;

    /** 左上肢肌肉（标准） */
    private BigDecimal plla;

    /** 左上肢肌肉（kg） */
    private BigDecimal lla;

    /** 左上肢肌肉（%） */
    private BigDecimal pilla;

    /** 躯干肌肉（标准） */
    private BigDecimal plt;

    /** 躯干肌肉（kg） */
    private BigDecimal lt;

    /** 躯干肌肉（%） */
    private BigDecimal pilt;

    /** 右下肢肌肉（标准） */
    private BigDecimal plrl;

    /** 右下肢肌肉（kg） */
    private BigDecimal lrl;

    /** 右下肢肌肉（%） */
    private BigDecimal pilrl;

    /** 左下肢肌肉（标准） */
    private BigDecimal plll;

    /** 左下肢肌肉（kg） */
    private BigDecimal lll;

    /** 左下肢肌肉（%） */
    private BigDecimal pilll;

    /** 右上肢（kg） */
    private BigDecimal fra;

    /** 右上肢（%） */
    private BigDecimal pfra;

    /** 左上肢（kg） */
    private BigDecimal fla;

    /** 左上肢（%） */
    private BigDecimal pfla;

    /** 躯干（kg） */
    private BigDecimal ft;

    /** 躯干（%） */
    private BigDecimal pft;

    /** 右下肢（kg） */
    private BigDecimal frl;

    /** 右下肢（%） */
    private BigDecimal pfrl;

    /** 左下肢（kg） */
    private BigDecimal fll;

    /** 左下肢（%） */
    private BigDecimal pfll;

    /** 节段浮肿（4） */
    private BigDecimal fedra;

    /** 节段浮肿（5） */
    private BigDecimal fedla;

    /** 节段浮肿 */
    private BigDecimal fedt;

    /** 节段浮肿（6） */
    private BigDecimal fedrl;

    /** 节段浮肿（7） */
    private BigDecimal fedll;

    /** 节段浮肿（12） */
    private BigDecimal fed;

    /** 右上肢 */
    private BigDecimal wedra;

    /** 左上肢（浮肿） */
    private BigDecimal wedla;

    /** 躯干（浮肿） */
    private BigDecimal wedt;

    /** 右下肢（浮肿） */
    private BigDecimal wedrl;

    /** 左下肢（浮肿） */
    private BigDecimal wedll;

    /** 细胞外水分比率 */
    private BigDecimal wed;

    /** ACR */
    private BigDecimal acr;

    /** ACL */
    private BigDecimal acl;

    /** AMC */
    private BigDecimal amc;

    /** 身高 */
    private BigDecimal ht;

    /** 目标体重 */
    private BigDecimal tw;

    /** 质量控制 */
    private BigDecimal wc;

    /** 脂肪控制 */
    private BigDecimal fc;

    /** 肌肉控制 */
    private BigDecimal mc;

    /** Obesity */
    private BigDecimal obesity;

    /** Obesity--下限 */
    private BigDecimal odMin;

    /** Obesity--上限 */
    private BigDecimal odMax;

    /** BMC */
    private BigDecimal bmc;

    /** BMC--下限 */
    private BigDecimal bmcMin;

    /** BMC--上限 */
    private BigDecimal bmcMax;

    /** 基础代谢率 */
    private BigDecimal bmr;

    /** 基础代谢率--下限 */
    private BigDecimal bmrMin;

    /** 基础代谢率--上限 */
    private BigDecimal bmrMax;

    /** 身体细胞量 */
    private BigDecimal bcm;

    /** 身体细胞量下限 */
    private BigDecimal bcmMin;

    /** 身体细胞量上限 */
    private BigDecimal bcmMax;

    /** 健康评估得分 */
    private BigDecimal totScore;

    /** 健康评估得分 */
    private BigDecimal fs;

    /** 内脏脂肪面积 */
    private BigDecimal vfa;

    /** 右上肢1k */
    private BigDecimal ira1;

    /** 右上肢5k */
    private BigDecimal ira5;

    /** 右上肢50k */
    private BigDecimal ira50;

    /** 右上肢250k */
    private BigDecimal ira250;

    /** 右上肢500k */
    private BigDecimal ira500;

    /** 右上肢1M */
    private BigDecimal ira1m;

    /** 左上肢1k */
    private BigDecimal ila1;

    /** 左上肢5k */
    private BigDecimal ila5;

    /** 左上肢50k */
    private BigDecimal ila50;

    /** 左上肢250k */
    private BigDecimal ila250;

    /** 左上肢500k */
    private BigDecimal ila500;

    /** 左上肢1M */
    private BigDecimal ila1m;

    /** 躯干1k */
    private BigDecimal it1;

    /** 躯干5k */
    private BigDecimal it5;

    /** 躯干50k */
    private BigDecimal it50;

    /** 躯干250k */
    private BigDecimal it250;

    /** 躯干500k */
    private BigDecimal it500;

    /** 躯干1M */
    private BigDecimal it1m;

    /** 右下肢1k */
    private BigDecimal irl1;

    /** 右下肢5k */
    private BigDecimal irl5;

    /** 右下肢50k */
    private BigDecimal irl50;

    /** 右下肢250k */
    private BigDecimal irl250;

    /** 右下肢500k */
    private BigDecimal irl500;

    /** 右下肢1M */
    private BigDecimal irl1m;

    /** 左下肢1k */
    private BigDecimal ill1;

    /** 左下肢5k */
    private BigDecimal ill5;

    /** 左下肢50k */
    private BigDecimal ill50;

    /** 左下肢250k */
    private BigDecimal ill250;

    /** 左下肢500k */
    private BigDecimal ill500;

    /** 左下肢1M */
    private BigDecimal ill1m;

    /** 全身相位角 */
    private BigDecimal wbpa50;

    private String diagnosisId;

    private String gestationalWeeks;

    public String getBcaId() {
        return bcaId;
    }

    public void setBcaId(String bcaId) {
        this.bcaId = bcaId;
    }

    public BigDecimal getSlmMin() {
        return slmMin;
    }

    public void setSlmMin(BigDecimal slmMin) {
        this.slmMin = slmMin;
    }

    public BigDecimal getSlmMax() {
        return slmMax;
    }

    public void setSlmMax(BigDecimal slmMax) {
        this.slmMax = slmMax;
    }

    public BigDecimal getFfmMin() {
        return ffmMin;
    }

    public void setFfmMin(BigDecimal ffmMin) {
        this.ffmMin = ffmMin;
    }

    public BigDecimal getFfmMax() {
        return ffmMax;
    }

    public void setFfmMax(BigDecimal ffmMax) {
        this.ffmMax = ffmMax;
    }

    public String getDatetimes() {
        return datetimes;
    }

    public void setDatetimes(String datetimes) {
        this.datetimes = datetimes;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getInbodyPdfPath() {
        return inbodyPdfPath;
    }

    public void setInbodyPdfPath(String inbodyPdfPath) {
        this.inbodyPdfPath = inbodyPdfPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public BigDecimal getWt() {
        return wt;
    }

    public void setWt(BigDecimal wt) {
        this.wt = wt;
    }

    public BigDecimal getTbw() {
        return tbw;
    }

    public void setTbw(BigDecimal tbw) {
        this.tbw = tbw;
    }

    public BigDecimal getTbwMin() {
        return tbwMin;
    }

    public void setTbwMin(BigDecimal tbwMin) {
        this.tbwMin = tbwMin;
    }

    public BigDecimal getTbwMax() {
        return tbwMax;
    }

    public void setTbwMax(BigDecimal tbwMax) {
        this.tbwMax = tbwMax;
    }

    public BigDecimal getProtein() {
        return protein;
    }

    public void setProtein(BigDecimal protein) {
        this.protein = protein;
    }

    public BigDecimal getProteinMin() {
        return proteinMin;
    }

    public void setProteinMin(BigDecimal proteinMin) {
        this.proteinMin = proteinMin;
    }

    public BigDecimal getProteinMax() {
        return proteinMax;
    }

    public void setProteinMax(BigDecimal proteinMax) {
        this.proteinMax = proteinMax;
    }

    public BigDecimal getMineral() {
        return mineral;
    }

    public void setMineral(BigDecimal mineral) {
        this.mineral = mineral;
    }

    public BigDecimal getMineralMin() {
        return mineralMin;
    }

    public void setMineralMin(BigDecimal mineralMin) {
        this.mineralMin = mineralMin;
    }

    public BigDecimal getMineralMax() {
        return mineralMax;
    }

    public void setMineralMax(BigDecimal mineralMax) {
        this.mineralMax = mineralMax;
    }

    public BigDecimal getBfm() {
        return bfm;
    }

    public void setBfm(BigDecimal bfm) {
        this.bfm = bfm;
    }

    public BigDecimal getBfmMin() {
        return bfmMin;
    }

    public void setBfmMin(BigDecimal bfmMin) {
        this.bfmMin = bfmMin;
    }

    public BigDecimal getBfmMax() {
        return bfmMax;
    }

    public void setBfmMax(BigDecimal bfmMax) {
        this.bfmMax = bfmMax;
    }

    public BigDecimal getSlm() {
        return slm;
    }

    public void setSlm(BigDecimal slm) {
        this.slm = slm;
    }

    public BigDecimal getFfm() {
        return ffm;
    }

    public void setFfm(BigDecimal ffm) {
        this.ffm = ffm;
    }

    public BigDecimal getIcw() {
        return icw;
    }

    public void setIcw(BigDecimal icw) {
        this.icw = icw;
    }

    public BigDecimal getIcwMin() {
        return icwMin;
    }

    public void setIcwMin(BigDecimal icwMin) {
        this.icwMin = icwMin;
    }

    public BigDecimal getIcwMax() {
        return icwMax;
    }

    public void setIcwMax(BigDecimal icwMax) {
        this.icwMax = icwMax;
    }

    public BigDecimal getEcw() {
        return ecw;
    }

    public void setEcw(BigDecimal ecw) {
        this.ecw = ecw;
    }

    public BigDecimal getEcwMin() {
        return ecwMin;
    }

    public void setEcwMin(BigDecimal ecwMin) {
        this.ecwMin = ecwMin;
    }

    public BigDecimal getEcwMax() {
        return ecwMax;
    }

    public void setEcwMax(BigDecimal ecwMax) {
        this.ecwMax = ecwMax;
    }

    public BigDecimal getSmmMin() {
        return smmMin;
    }

    public void setSmmMin(BigDecimal smmMin) {
        this.smmMin = smmMin;
    }

    public BigDecimal getSmmMax() {
        return smmMax;
    }

    public void setSmmMax(BigDecimal smmMax) {
        this.smmMax = smmMax;
    }

    public BigDecimal getSmm() {
        return smm;
    }

    public void setSmm(BigDecimal smm) {
        this.smm = smm;
    }

    public BigDecimal getBmi() {
        return bmi;
    }

    public void setBmi(BigDecimal bmi) {
        this.bmi = bmi;
    }

    public BigDecimal getBmiMin() {
        return bmiMin;
    }

    public void setBmiMin(BigDecimal bmiMin) {
        this.bmiMin = bmiMin;
    }

    public BigDecimal getBmiMax() {
        return bmiMax;
    }

    public void setBmiMax(BigDecimal bmiMax) {
        this.bmiMax = bmiMax;
    }

    public BigDecimal getPbf() {
        return pbf;
    }

    public void setPbf(BigDecimal pbf) {
        this.pbf = pbf;
    }

    public BigDecimal getPbfMin() {
        return pbfMin;
    }

    public void setPbfMin(BigDecimal pbfMin) {
        this.pbfMin = pbfMin;
    }

    public BigDecimal getPbfMax() {
        return pbfMax;
    }

    public void setPbfMax(BigDecimal pbfMax) {
        this.pbfMax = pbfMax;
    }

    public BigDecimal getPbfmMin() {
        return pbfmMin;
    }

    public void setPbfmMin(BigDecimal pbfmMin) {
        this.pbfmMin = pbfmMin;
    }

    public BigDecimal getPbfmMax() {
        return pbfmMax;
    }

    public void setPbfmMax(BigDecimal pbfmMax) {
        this.pbfmMax = pbfmMax;
    }

    public BigDecimal getWhr() {
        return whr;
    }

    public void setWhr(BigDecimal whr) {
        this.whr = whr;
    }

    public BigDecimal getWhrMin() {
        return whrMin;
    }

    public void setWhrMin(BigDecimal whrMin) {
        this.whrMin = whrMin;
    }

    public BigDecimal getWhrMax() {
        return whrMax;
    }

    public void setWhrMax(BigDecimal whrMax) {
        this.whrMax = whrMax;
    }

    public BigDecimal getWtMin() {
        return wtMin;
    }

    public void setWtMin(BigDecimal wtMin) {
        this.wtMin = wtMin;
    }

    public BigDecimal getWtMax() {
        return wtMax;
    }

    public void setWtMax(BigDecimal wtMax) {
        this.wtMax = wtMax;
    }

    public BigDecimal getPwt() {
        return pwt;
    }

    public void setPwt(BigDecimal pwt) {
        this.pwt = pwt;
    }

    public BigDecimal getPsmm() {
        return psmm;
    }

    public void setPsmm(BigDecimal psmm) {
        this.psmm = psmm;
    }

    public BigDecimal getPbfm() {
        return pbfm;
    }

    public void setPbfm(BigDecimal pbfm) {
        this.pbfm = pbfm;
    }

    public BigDecimal getPlra() {
        return plra;
    }

    public void setPlra(BigDecimal plra) {
        this.plra = plra;
    }

    public BigDecimal getLra() {
        return lra;
    }

    public void setLra(BigDecimal lra) {
        this.lra = lra;
    }

    public BigDecimal getPilra() {
        return pilra;
    }

    public void setPilra(BigDecimal pilra) {
        this.pilra = pilra;
    }

    public BigDecimal getPlla() {
        return plla;
    }

    public void setPlla(BigDecimal plla) {
        this.plla = plla;
    }

    public BigDecimal getLla() {
        return lla;
    }

    public void setLla(BigDecimal lla) {
        this.lla = lla;
    }

    public BigDecimal getPilla() {
        return pilla;
    }

    public void setPilla(BigDecimal pilla) {
        this.pilla = pilla;
    }

    public BigDecimal getPlt() {
        return plt;
    }

    public void setPlt(BigDecimal plt) {
        this.plt = plt;
    }

    public BigDecimal getLt() {
        return lt;
    }

    public void setLt(BigDecimal lt) {
        this.lt = lt;
    }

    public BigDecimal getPilt() {
        return pilt;
    }

    public void setPilt(BigDecimal pilt) {
        this.pilt = pilt;
    }

    public BigDecimal getPlrl() {
        return plrl;
    }

    public void setPlrl(BigDecimal plrl) {
        this.plrl = plrl;
    }

    public BigDecimal getLrl() {
        return lrl;
    }

    public void setLrl(BigDecimal lrl) {
        this.lrl = lrl;
    }

    public BigDecimal getPilrl() {
        return pilrl;
    }

    public void setPilrl(BigDecimal pilrl) {
        this.pilrl = pilrl;
    }

    public BigDecimal getPlll() {
        return plll;
    }

    public void setPlll(BigDecimal plll) {
        this.plll = plll;
    }

    public BigDecimal getLll() {
        return lll;
    }

    public void setLll(BigDecimal lll) {
        this.lll = lll;
    }

    public BigDecimal getPilll() {
        return pilll;
    }

    public void setPilll(BigDecimal pilll) {
        this.pilll = pilll;
    }

    public BigDecimal getFra() {
        return fra;
    }

    public void setFra(BigDecimal fra) {
        this.fra = fra;
    }

    public BigDecimal getPfra() {
        return pfra;
    }

    public void setPfra(BigDecimal pfra) {
        this.pfra = pfra;
    }

    public BigDecimal getFla() {
        return fla;
    }

    public void setFla(BigDecimal fla) {
        this.fla = fla;
    }

    public BigDecimal getPfla() {
        return pfla;
    }

    public void setPfla(BigDecimal pfla) {
        this.pfla = pfla;
    }

    public BigDecimal getFt() {
        return ft;
    }

    public void setFt(BigDecimal ft) {
        this.ft = ft;
    }

    public BigDecimal getPft() {
        return pft;
    }

    public void setPft(BigDecimal pft) {
        this.pft = pft;
    }

    public BigDecimal getFrl() {
        return frl;
    }

    public void setFrl(BigDecimal frl) {
        this.frl = frl;
    }

    public BigDecimal getPfrl() {
        return pfrl;
    }

    public void setPfrl(BigDecimal pfrl) {
        this.pfrl = pfrl;
    }

    public BigDecimal getFll() {
        return fll;
    }

    public void setFll(BigDecimal fll) {
        this.fll = fll;
    }

    public BigDecimal getPfll() {
        return pfll;
    }

    public void setPfll(BigDecimal pfll) {
        this.pfll = pfll;
    }

    public BigDecimal getFedra() {
        return fedra;
    }

    public void setFedra(BigDecimal fedra) {
        this.fedra = fedra;
    }

    public BigDecimal getFedla() {
        return fedla;
    }

    public void setFedla(BigDecimal fedla) {
        this.fedla = fedla;
    }

    public BigDecimal getFedt() {
        return fedt;
    }

    public void setFedt(BigDecimal fedt) {
        this.fedt = fedt;
    }

    public BigDecimal getFedrl() {
        return fedrl;
    }

    public void setFedrl(BigDecimal fedrl) {
        this.fedrl = fedrl;
    }

    public BigDecimal getFedll() {
        return fedll;
    }

    public void setFedll(BigDecimal fedll) {
        this.fedll = fedll;
    }

    public BigDecimal getFed() {
        return fed;
    }

    public void setFed(BigDecimal fed) {
        this.fed = fed;
    }

    public BigDecimal getWedra() {
        return wedra;
    }

    public void setWedra(BigDecimal wedra) {
        this.wedra = wedra;
    }

    public BigDecimal getWedla() {
        return wedla;
    }

    public void setWedla(BigDecimal wedla) {
        this.wedla = wedla;
    }

    public BigDecimal getWedt() {
        return wedt;
    }

    public void setWedt(BigDecimal wedt) {
        this.wedt = wedt;
    }

    public BigDecimal getWedrl() {
        return wedrl;
    }

    public void setWedrl(BigDecimal wedrl) {
        this.wedrl = wedrl;
    }

    public BigDecimal getWedll() {
        return wedll;
    }

    public void setWedll(BigDecimal wedll) {
        this.wedll = wedll;
    }

    public BigDecimal getWed() {
        return wed;
    }

    public void setWed(BigDecimal wed) {
        this.wed = wed;
    }

    public BigDecimal getHt() {
        return ht;
    }

    public void setHt(BigDecimal ht) {
        this.ht = ht;
    }

    public BigDecimal getTw() {
        return tw;
    }

    public void setTw(BigDecimal tw) {
        this.tw = tw;
    }

    public BigDecimal getWc() {
        return wc;
    }

    public void setWc(BigDecimal wc) {
        this.wc = wc;
    }

    public BigDecimal getFc() {
        return fc;
    }

    public void setFc(BigDecimal fc) {
        this.fc = fc;
    }

    public BigDecimal getMc() {
        return mc;
    }

    public void setMc(BigDecimal mc) {
        this.mc = mc;
    }

    public BigDecimal getBmr() {
        return bmr;
    }

    public void setBmr(BigDecimal bmr) {
        this.bmr = bmr;
    }

    public BigDecimal getBcm() {
        return bcm;
    }

    public void setBcm(BigDecimal bcm) {
        this.bcm = bcm;
    }

    public BigDecimal getBcmMin() {
        return bcmMin;
    }

    public void setBcmMin(BigDecimal bcmMin) {
        this.bcmMin = bcmMin;
    }

    public BigDecimal getBcmMax() {
        return bcmMax;
    }

    public void setBcmMax(BigDecimal bcmMax) {
        this.bcmMax = bcmMax;
    }

    public BigDecimal getTotScore() {
        return totScore;
    }

    public void setTotScore(BigDecimal totScore) {
        this.totScore = totScore;
    }

    public BigDecimal getFs() {
        return fs;
    }

    public void setFs(BigDecimal fs) {
        this.fs = fs;
    }

    public BigDecimal getVfa() {
        return vfa;
    }

    public void setVfa(BigDecimal vfa) {
        this.vfa = vfa;
    }

    public BigDecimal getIra1() {
        return ira1;
    }

    public void setIra1(BigDecimal ira1) {
        this.ira1 = ira1;
    }

    public BigDecimal getIra5() {
        return ira5;
    }

    public void setIra5(BigDecimal ira5) {
        this.ira5 = ira5;
    }

    public BigDecimal getIra50() {
        return ira50;
    }

    public void setIra50(BigDecimal ira50) {
        this.ira50 = ira50;
    }

    public BigDecimal getIra250() {
        return ira250;
    }

    public void setIra250(BigDecimal ira250) {
        this.ira250 = ira250;
    }

    public BigDecimal getIra500() {
        return ira500;
    }

    public void setIra500(BigDecimal ira500) {
        this.ira500 = ira500;
    }

    public BigDecimal getIra1m() {
        return ira1m;
    }

    public void setIra1m(BigDecimal ira1m) {
        this.ira1m = ira1m;
    }

    public BigDecimal getIla1() {
        return ila1;
    }

    public void setIla1(BigDecimal ila1) {
        this.ila1 = ila1;
    }

    public BigDecimal getIla5() {
        return ila5;
    }

    public void setIla5(BigDecimal ila5) {
        this.ila5 = ila5;
    }

    public BigDecimal getIla50() {
        return ila50;
    }

    public void setIla50(BigDecimal ila50) {
        this.ila50 = ila50;
    }

    public BigDecimal getIla250() {
        return ila250;
    }

    public void setIla250(BigDecimal ila250) {
        this.ila250 = ila250;
    }

    public BigDecimal getIla500() {
        return ila500;
    }

    public void setIla500(BigDecimal ila500) {
        this.ila500 = ila500;
    }

    public BigDecimal getIla1m() {
        return ila1m;
    }

    public void setIla1m(BigDecimal ila1m) {
        this.ila1m = ila1m;
    }

    public BigDecimal getIt1() {
        return it1;
    }

    public void setIt1(BigDecimal it1) {
        this.it1 = it1;
    }

    public BigDecimal getIt5() {
        return it5;
    }

    public void setIt5(BigDecimal it5) {
        this.it5 = it5;
    }

    public BigDecimal getIt50() {
        return it50;
    }

    public void setIt50(BigDecimal it50) {
        this.it50 = it50;
    }

    public BigDecimal getIt250() {
        return it250;
    }

    public void setIt250(BigDecimal it250) {
        this.it250 = it250;
    }

    public BigDecimal getIt500() {
        return it500;
    }

    public void setIt500(BigDecimal it500) {
        this.it500 = it500;
    }

    public BigDecimal getIt1m() {
        return it1m;
    }

    public void setIt1m(BigDecimal it1m) {
        this.it1m = it1m;
    }

    public BigDecimal getIrl1() {
        return irl1;
    }

    public void setIrl1(BigDecimal irl1) {
        this.irl1 = irl1;
    }

    public BigDecimal getIrl5() {
        return irl5;
    }

    public void setIrl5(BigDecimal irl5) {
        this.irl5 = irl5;
    }

    public BigDecimal getIrl50() {
        return irl50;
    }

    public void setIrl50(BigDecimal irl50) {
        this.irl50 = irl50;
    }

    public BigDecimal getIrl250() {
        return irl250;
    }

    public void setIrl250(BigDecimal irl250) {
        this.irl250 = irl250;
    }

    public BigDecimal getIrl500() {
        return irl500;
    }

    public void setIrl500(BigDecimal irl500) {
        this.irl500 = irl500;
    }

    public BigDecimal getIrl1m() {
        return irl1m;
    }

    public void setIrl1m(BigDecimal irl1m) {
        this.irl1m = irl1m;
    }

    public BigDecimal getIll1() {
        return ill1;
    }

    public void setIll1(BigDecimal ill1) {
        this.ill1 = ill1;
    }

    public BigDecimal getIll5() {
        return ill5;
    }

    public void setIll5(BigDecimal ill5) {
        this.ill5 = ill5;
    }

    public BigDecimal getIll50() {
        return ill50;
    }

    public void setIll50(BigDecimal ill50) {
        this.ill50 = ill50;
    }

    public BigDecimal getIll250() {
        return ill250;
    }

    public void setIll250(BigDecimal ill250) {
        this.ill250 = ill250;
    }

    public BigDecimal getIll500() {
        return ill500;
    }

    public void setIll500(BigDecimal ill500) {
        this.ill500 = ill500;
    }

    public BigDecimal getIll1m() {
        return ill1m;
    }

    public void setIll1m(BigDecimal ill1m) {
        this.ill1m = ill1m;
    }

    public BigDecimal getAcr() {
        return acr;
    }

    public void setAcr(BigDecimal acr) {
        this.acr = acr;
    }

    public BigDecimal getAcl() {
        return acl;
    }

    public void setAcl(BigDecimal acl) {
        this.acl = acl;
    }

    public BigDecimal getAmc() {
        return amc;
    }

    public void setAmc(BigDecimal amc) {
        this.amc = amc;
    }

    public BigDecimal getObesity() {
        return obesity;
    }

    public void setObesity(BigDecimal obesity) {
        this.obesity = obesity;
    }

    public BigDecimal getOdMin() {
        return odMin;
    }

    public void setOdMin(BigDecimal odMin) {
        this.odMin = odMin;
    }

    public BigDecimal getOdMax() {
        return odMax;
    }

    public void setOdMax(BigDecimal odMax) {
        this.odMax = odMax;
    }

    public BigDecimal getBmc() {
        return bmc;
    }

    public void setBmc(BigDecimal bmc) {
        this.bmc = bmc;
    }

    public BigDecimal getBmcMin() {
        return bmcMin;
    }

    public void setBmcMin(BigDecimal bmcMin) {
        this.bmcMin = bmcMin;
    }

    public BigDecimal getBmcMax() {
        return bmcMax;
    }

    public void setBmcMax(BigDecimal bmcMax) {
        this.bmcMax = bmcMax;
    }

    public BigDecimal getBmrMin() {
        return bmrMin;
    }

    public void setBmrMin(BigDecimal bmrMin) {
        this.bmrMin = bmrMin;
    }

    public BigDecimal getBmrMax() {
        return bmrMax;
    }

    public void setBmrMax(BigDecimal bmrMax) {
        this.bmrMax = bmrMax;
    }

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public String getGestationalWeeks() {
        return gestationalWeeks;
    }

    public void setGestationalWeeks(String gestationalWeeks) {
        this.gestationalWeeks = gestationalWeeks;
    }

    public BigDecimal getWbpa50() {
        return wbpa50;
    }

    public void setWbpa50(BigDecimal wbpa50) {
        this.wbpa50 = wbpa50;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

}

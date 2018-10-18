/**
 * 
 */

package com.mnt.preg.platform.util;

/**
 * 11大类膳食结构标准值
 * 
 * @author zy
 * @verser 1.0.0
 * @create_date 2014-10-10
 * @modify_author
 * @modify_date
 * @modify_info
 */
public class SpeciesStandardVal {

    public int grainVal;// 谷类

    public int starchyVal;// 淀粉蔬菜类

    public int nostarchyVal;// 非淀粉蔬菜类

    public int fruitsVal;// 水果

    public int milkVal;// 奶制品

    public int femVal;// 鱼肉蛋

    public int peasVal;// 豆类

    public int nutsVal;// 坚果类

    public int fatVal;// 油脂

    public int saltVal;// 盐类

    public int waterMinVal = 1500;// 酒水类

    public int waterMaxVal = 1700;// 酒水类

    public SpeciesStandardVal(int stenergy) {
        if (stenergy <= 1700) {// 1600千卡
            this.grainVal = 200;// 谷类
            this.starchyVal = 25;// 淀粉蔬菜类
            this.nostarchyVal = 300;// 非淀粉蔬菜类
            this.fruitsVal = 200;// 水果
            this.milkVal = 300;// 奶制品
            this.femVal = 125;// 鱼肉蛋
            this.peasVal = 20;// 豆类
            this.nutsVal = 10;// 坚果类
            this.fatVal = 20;// 油脂
            this.saltVal = 6;// 盐类
        }
        if (stenergy > 1700 && stenergy <= 1900) {// 1800千卡
            this.grainVal = 225;// 谷类
            this.starchyVal = 25;// 淀粉蔬菜类
            this.nostarchyVal = 300;// 非淀粉蔬菜类
            this.fruitsVal = 200;// 水果
            this.milkVal = 300;// 奶制品
            this.femVal = 125;// 鱼肉蛋
            this.peasVal = 20;// 豆类
            this.nutsVal = 10;// 坚果类
            this.fatVal = 25;// 油脂
            this.saltVal = 6;// 盐类
        }
        if (stenergy > 1900 && stenergy <= 2100) {// 2000千卡
            this.grainVal = 275;// 谷类
            this.starchyVal = 25;// 淀粉蔬菜类
            this.nostarchyVal = 350;// 非淀粉蔬菜类
            this.fruitsVal = 300;// 水果
            this.milkVal = 300;// 奶制品
            this.femVal = 150;// 鱼肉蛋
            this.peasVal = 25;// 豆类
            this.nutsVal = 15;// 坚果类
            this.fatVal = 25;// 油脂
            this.saltVal = 6;// 盐类
        }
        if (stenergy > 2100 && stenergy <= 2300) {// 2200千卡
            this.grainVal = 275;// 谷类
            this.starchyVal = 25;// 淀粉蔬菜类
            this.nostarchyVal = 400;// 非淀粉蔬菜类
            this.fruitsVal = 300;// 水果
            this.milkVal = 300;// 奶制品
            this.femVal = 200;// 鱼肉蛋
            this.peasVal = 25;// 豆类
            this.nutsVal = 15;// 坚果类
            this.fatVal = 25;// 油脂
            this.saltVal = 6;// 盐类
        }
        if (stenergy > 2300 && stenergy <= 2500) {// 2400千卡
            this.grainVal = 325;// 谷类
            this.starchyVal = 25;// 淀粉蔬菜类
            this.nostarchyVal = 450;// 非淀粉蔬菜类
            this.fruitsVal = 400;// 水果
            this.milkVal = 300;// 奶制品
            this.femVal = 200;// 鱼肉蛋
            this.peasVal = 25;// 豆类
            this.nutsVal = 15;// 坚果类
            this.fatVal = 30;// 油脂
            this.saltVal = 6;// 盐类
        }
        if (stenergy > 2500 && stenergy <= 2700) {// 2600千卡
            this.grainVal = 375;// 谷类
            this.starchyVal = 25;// 淀粉蔬菜类
            this.nostarchyVal = 500;// 非淀粉蔬菜类
            this.fruitsVal = 400;// 水果
            this.milkVal = 300;// 奶制品
            this.femVal = 225;// 鱼肉蛋
            this.peasVal = 30;// 豆类
            this.nutsVal = 20;// 坚果类
            this.fatVal = 30;// 油脂
            this.saltVal = 6;// 盐类
        }
        if (stenergy > 2700) {// 2800千卡
            this.grainVal = 425;// 谷类
            this.starchyVal = 25;// 淀粉蔬菜类
            this.nostarchyVal = 500;// 非淀粉蔬菜类
            this.fruitsVal = 500;// 水果
            this.milkVal = 300;// 奶制品
            this.femVal = 225;// 鱼肉蛋
            this.peasVal = 30;// 豆类
            this.nutsVal = 20;// 坚果类
            this.fatVal = 30;// 油脂
            this.saltVal = 6;// 盐类
        }
    }

    public int getGrainVal() {
        return grainVal;
    }

    public void setGrainVal(int grainVal) {
        this.grainVal = grainVal;
    }

    public int getStarchyVal() {
        return starchyVal;
    }

    public void setStarchyVal(int starchyVal) {
        this.starchyVal = starchyVal;
    }

    public int getNostarchyVal() {
        return nostarchyVal;
    }

    public void setNostarchyVal(int nostarchyVal) {
        this.nostarchyVal = nostarchyVal;
    }

    public int getFemVal() {
        return femVal;
    }

    public void setFemVal(int femVal) {
        this.femVal = femVal;
    }

    public int getPeasVal() {
        return peasVal;
    }

    public void setPeasVal(int peasVal) {
        this.peasVal = peasVal;
    }

    public int getFruitsVal() {
        return fruitsVal;
    }

    public void setFruitsVal(int fruitsVal) {
        this.fruitsVal = fruitsVal;
    }

    public int getMilkVal() {
        return milkVal;
    }

    public void setMilkVal(int milkVal) {
        this.milkVal = milkVal;
    }

    public int getNutsVal() {
        return nutsVal;
    }

    public void setNutsVal(int nutsVal) {
        this.nutsVal = nutsVal;
    }

    public int getFatVal() {
        return fatVal;
    }

    public void setFatVal(int fatVal) {
        this.fatVal = fatVal;
    }

    public int getSaltVal() {
        return saltVal;
    }

    public void setSaltVal(int saltVal) {
        this.saltVal = saltVal;
    }

    public int getWaterMinVal() {
        return waterMinVal;
    }

    public void setWaterMinVal(int waterMinVal) {
        this.waterMinVal = waterMinVal;
    }

    public int getWaterMaxVal() {
        return waterMaxVal;
    }

    public void setWaterMaxVal(int waterMaxVal) {
        this.waterMaxVal = waterMaxVal;
    }

}

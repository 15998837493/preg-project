
package com.mnt.preg.platform.util;

/**
 * 
 * DRIs算法类
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2014-10-10 zy 初版
 */
public class DRIs {

    // 能量_kcal
    public double foodEnergy;

    // 蛋白质g
    public double foodProtid;

    // 脂肪g
    public String foodFat;

    // 饱和脂肪酸
    public String foodSfa;

    // 单不包含脂肪酸
    public double foodScnfa;

    // 多不饱和脂肪酸
    public double foodPfa;

    // 碳水化合物(g)
    public String foodCbr;

    // 膳食纤维(g)
    public double foodDf;

    // 胆固醇(mg)
    public double foodCholesterin;

    // 灰分(g)
    public double foodAshc;

    // 维生素A(μg)
    private double foodVa;

    // 维生素C
    public double foodVc;

    // 维生素E
    public double foodVe;

    // 胡萝卜素(μg)
    public double foodCarotin;

    // 硫胺素（mg）
    public double foodVb1;

    // 核黄素
    public double foodVb2;

    // 烟酸(mg)
    public double foodAf;

    // 钙(mg)
    public double foodEca;

    // 磷(mg)
    public double foodEp;

    // 钾(mg)
    public double foodEk;

    // 钠(mg)
    public double foodEna;

    // 镁(mg)
    public double foodEmg;

    // 铁(mg)
    public double foodEfe;

    // 锌(mg)
    public double foodEzn;

    // 硒(μg)
    public double foodEse;

    // 铜
    public double foodEcu;

    // 锰(mg)
    public double foodEmn;

    public DRIs(int age, String sex, String sl) {
        // 男
        if (sex.equals("male")) {
            if (age == 1) {// 1岁-男
                this.setVals(900, 25, "35", "0", 0, 0, "50~65", 0, 0, 0, 310, 40, 6, 0, 0.6, 0.6, 6, 600, 300, 900,
                        700, 140, 9, 4, 25, 0.3, 1.5);
            } else if (age == 2) {// 2岁-男
                this.setVals(1100, 25, "35", "0", 0, 0, "50~65", 5, 0, 0, 310, 40, 6, 0, 0.6, 0.6, 6, 600, 300, 900,
                        700, 140, 9, 4, 25, 0.3, 1.5);
            } else if (age == 3) {// 3岁-男
                this.setVals(1250, 30, "35", "0", 0, 0, "50~65", 6, 0, 0, 310, 40, 6, 0, 0.6, 0.6, 6, 600, 300, 900,
                        700, 140, 9, 4, 25, 0.3, 1.5);
            } else if (age == 4) {// 4岁-男
                this.setVals(1300, 30, "20~30", "0~8", 0, 0, "50~65", 7, 0, 0, 360, 50, 7, 0, 0.8, 0.7, 8, 800, 350,
                        1200, 900, 160, 10, 5.5, 30, 0.4, 2);
            } else if (age == 5) {// 5岁-男
                this.setVals(1400, 30, "20~30", "0~8", 0, 0, "50~65", 8, 0, 0, 360, 50, 7, 0, 0.8, 0.7, 8, 800, 350,
                        1200, 900, 160, 10, 5.5, 30, 0.4, 2);
            } else if (age == 6) {// 6岁-男
                this.setVals(1400, 35, "20~30", "0~8", 0, 0, "50~65", 9, 0, 0, 360, 50, 7, 0, 0.8, 0.7, 8, 800, 350,
                        1200, 900, 160, 10, 5.5, 30, 0.4, 2);
            } else if (age == 7) {// 7岁-男
                this.setVals(1500, 40, "20~30", "0~8", 0, 0, "50~65", 10, 0, 0, 500, 65, 9, 0, 1, 1, 11, 1000, 470,
                        1500, 1200, 220, 13, 7, 40, 0.5, 3);
            } else if (age == 8) {// 8岁-男
                this.setVals(1650, 40, "20~30", "0~8", 0, 0, "50~65", 11, 0, 0, 500, 65, 9, 0, 1, 1, 11, 1000, 470,
                        1500, 1200, 220, 13, 7, 40, 0.5, 3);
            } else if (age == 9) {// 9岁-男
                this.setVals(1750, 45, "20~30", "0~8", 0, 0, "50~65", 12, 0, 0, 500, 65, 9, 0, 1, 1, 11, 1000, 470,
                        1500, 1200, 220, 13, 7, 40, 0.5, 3);
            } else if (age == 10) {// 10岁-男
                this.setVals(1800, 50, "20~30", "0~8", 0, 0, "50~65", 13, 0, 0, 500, 65, 9, 0, 1, 1, 11, 1000, 470,
                        1500, 1200, 220, 13, 7, 40, 0.5, 3);
            } else if (age >= 11 && age <= 13) {// 11-13岁-男
                this.setVals(2050, 60, "20~30", "0~8", 0, 0, "50~65", 14, 0, 0, 670, 90, 13, 0, 1.3, 1.3, 14, 1200,
                        640, 1900, 1400, 300, 15, 10, 55, 0.7, 4);
            } else if (age >= 14 && age <= 17) {// 14-17岁-男
                this.setVals(2500, 75, "20~30", "0~8", 0, 0, "50~65", 15, 0, 0, 820, 100, 14, 0, 1.6, 1.5, 16, 1000,
                        710, 2200, 1600, 320, 16, 11.5, 60, 0.8, 4.5);
            } else if (age >= 18 && age <= 49) {// 18-49岁-男
                this.setVals(2250, 65, "20~30", "0~8", 0, 0, "50~65", 24, 0, 0, 800, 100, 14, 0, 1.4, 1.4, 15, 800,
                        720, 2000, 1500, 330, 12, 12.5, 60, 0.8, 4.5);
            } else if (age >= 50 && age <= 64) {// 50-64岁-男
                this.setVals(2100, 65, "20~30", "0~8", 0, 0, "50~65", 24, 0, 0, 800, 100, 14, 0, 1.4, 1.4, 14, 1000,
                        720, 2000, 1400, 330, 12, 12.5, 60, 0.8, 4.5);
            } else if (age >= 65 && age <= 79) {// 65-79岁-男
                this.setVals(2050, 65, "20~30", "0~8", 0, 0, "50~65", 24, 0, 0, 800, 100, 14, 0, 1.4, 1.4, 14, 1000,
                        700, 2000, 1400, 320, 12, 12.5, 60, 0.8, 4.5);
            } else if (age >= 80) {// >80岁-男
                this.setVals(1900, 65, "20~30", "0~8", 0, 0, "50~65", 24, 0, 0, 800, 100, 14, 0, 1.4, 1.4, 13, 1000,
                        670, 2000, 1300, 310, 12, 12.5, 60, 0.8, 4.5);
            }
        }

        // 女
        if (sex.equals("female")) {
            if (age == 1) {// 1岁-女
                this.setVals(800, 25, "35", "0", 0, 0, "50~65", 0, 0, 0, 310, 40, 6, 0, 0.6, 0.6, 6, 600, 300, 900,
                        700, 140, 9, 4, 25, 0.3, 1.5);
            } else if (age == 2) {// 2岁-女
                this.setVals(1000, 25, "35", "0", 0, 0, "50~65", 5, 0, 0, 310, 40, 6, 0, 0.6, 0.6, 6, 600, 300, 900,
                        700, 140, 9, 4, 25, 0.3, 1.5);
            } else if (age == 3) {// 3岁-女
                this.setVals(1200, 30, "35", "0", 0, 0, "50~65", 6, 0, 0, 310, 40, 6, 0, 0.6, 0.6, 6, 600, 300, 900,
                        700, 140, 9, 4, 25, 0.3, 1.5);
            } else if (age == 4) {// 4岁-女
                this.setVals(1250, 30, "20~30", "0~8", 0, 0, "50~65", 7, 0, 0, 360, 50, 7, 0, 0.8, 0.7, 8, 800, 350,
                        1200, 900, 160, 10, 5.5, 30, 0.4, 2);
            } else if (age == 5) {// 5岁-女
                this.setVals(1300, 30, "20~30", "0~8", 0, 0, "50~65", 8, 0, 0, 360, 50, 7, 0, 0.8, 0.7, 8, 800, 350,
                        1200, 900, 160, 10, 5.5, 30, 0.4, 2);
            } else if (age == 6) {// 6岁-女
                this.setVals(1250, 35, "20~30", "0~8", 0, 0, "50~65", 9, 0, 0, 360, 50, 7, 0, 0.8, 0.7, 8, 800, 350,
                        1200, 900, 160, 10, 5.5, 30, 0.4, 2);
            } else if (age == 7) {// 7岁-女
                this.setVals(1350, 40, "20~30", "0~8", 0, 0, "50~65", 10, 0, 0, 500, 65, 9, 0, 1, 1, 10, 1000, 470,
                        1500, 1200, 220, 13, 7, 40, 0.5, 3);
            } else if (age == 8) {// 8岁-女
                this.setVals(1450, 40, "20~30", "0~8", 0, 0, "50~65", 11, 0, 0, 500, 65, 9, 0, 1, 1, 10, 1000, 470,
                        1500, 1200, 220, 13, 7, 40, 0.5, 3);
            } else if (age == 9) {// 9岁-女
                this.setVals(1550, 45, "20~30", "0~8", 0, 0, "50~65", 12, 0, 0, 500, 65, 9, 0, 1, 1, 10, 1000, 470,
                        1500, 1200, 220, 13, 7, 40, 0.5, 3);
            } else if (age == 10) {// 10岁-女
                this.setVals(1650, 50, "20~30", "0~8", 0, 0, "50~65", 13, 0, 0, 500, 65, 9, 0, 1, 1, 10, 1000, 470,
                        1500, 1200, 220, 13, 7, 40, 0.5, 3);
            } else if (age >= 11 && age <= 13) {// 11-13岁-女
                this.setVals(1800, 55, "20~30", "0~8", 0, 0, "50~65", 14, 0, 0, 630, 90, 13, 0, 1.1, 1.1, 12, 1200,
                        640, 1900, 1400, 300, 18, 9.0, 55, 0.7, 4);
            } else if (age >= 14 && age <= 17) {// 14-17岁-女
                this.setVals(2000, 60, "20~30", "0~8", 0, 0, "50~65", 15, 0, 0, 630, 100, 14, 0, 1.3, 1.2, 13, 1000,
                        710, 2200, 1600, 320, 18, 8.5, 60, 0.8, 4.5);
            } else if (age >= 18 && age <= 49) {// 18-49岁-女
                this.setVals(1800, 55, "20~30", "0~8", 0, 0, "50~65", 24, 0, 0, 700, 100, 14, 0, 1.2, 1.2, 12, 800,
                        720, 2000, 1500, 330, 20, 7.5, 60, 0.8, 4.5);
            } else if (age >= 50 && age <= 64) {// 50-64岁-女
                this.setVals(1750, 55, "20~30", "0~8", 0, 0, "50~65", 24, 0, 0, 700, 100, 14, 0, 1.2, 1.2, 12, 1000,
                        720, 2000, 1400, 330, 12, 7.5, 60, 0.8, 4.5);
            } else if (age >= 65 && age <= 79) {// 65-79岁-女
                this.setVals(1700, 55, "20~30", "0~8", 0, 0, "50~65", 24, 0, 0, 700, 100, 14, 0, 1.2, 1.2, 11, 1000,
                        700, 2000, 1400, 320, 12, 7.5, 60, 0.8, 4.5);
            } else if (age >= 80) {// >80岁-女
                this.setVals(1500, 55, "20~30", "0~8", 0, 0, "50~65", 24, 0, 0, 700, 100, 14, 0, 1.2, 1.2, 10, 1000,
                        670, 2000, 1300, 310, 12, 7.5, 60, 0.8, 4.5);
            }
        }
    }

    private void setVals(double foodEnergy, double foodProtid, String foodFat, String foodSfa, double foodScnfa,
            double foodPfa, String foodCbr, double foodDf, double foodCholesterin, double foodAshc, double foodVa,
            double foodVc, double foodVe, double foodCarotin, double foodVb1, double foodVb2, double foodAf,
            double foodEca, double foodEp, double foodEk, double foodEna, double foodEmg, double foodEfe,
            double foodEzn, double foodEse, double foodEcu, double foodEmn) {

        this.foodEnergy = foodEnergy;
        this.foodProtid = foodProtid;
        this.foodFat = foodFat;
        this.foodSfa = foodSfa;
        this.foodScnfa = foodScnfa;
        this.foodPfa = foodPfa;
        this.foodCbr = foodCbr;
        this.foodDf = foodDf;
        this.foodCholesterin = foodCholesterin;
        this.foodAshc = foodAshc;
        this.foodVa = foodVa;
        this.foodVc = foodVc;
        this.foodVe = foodVe;
        this.foodCarotin = foodCarotin;
        this.foodVb1 = foodVb1;
        this.foodVb2 = foodVb2;
        this.foodAf = foodAf;
        this.foodEca = foodEca;
        this.foodEp = foodEp;
        this.foodEk = foodEk;
        this.foodEna = foodEna;
        this.foodEmg = foodEmg;
        this.foodEfe = foodEfe;
        this.foodEzn = foodEzn;
        this.foodEse = foodEse;
        this.foodEcu = foodEcu;
        this.foodEmn = foodEmn;

    }

    public double getFoodEnergy() {
        return foodEnergy;
    }

    public void setFoodEnergy(double foodEnergy) {
        this.foodEnergy = foodEnergy;
    }

    public String getFoodFat() {
        return foodFat;
    }

    public void setFoodFat(String foodFat) {
        this.foodFat = foodFat;
    }

    public double getFoodProtid() {
        return foodProtid;
    }

    public void setFoodProtid(double foodProtid) {
        this.foodProtid = foodProtid;
    }

    public String getFoodSfa() {
        return foodSfa;
    }

    public void setFoodSfa(String foodSfa) {
        this.foodSfa = foodSfa;
    }

    public double getFoodScnfa() {
        return foodScnfa;
    }

    public void setFoodScnfa(double foodScnfa) {
        this.foodScnfa = foodScnfa;
    }

    public String getFoodCbr() {
        return foodCbr;
    }

    public void setFoodCbr(String foodCbr) {
        this.foodCbr = foodCbr;
    }

    public double getFoodDf() {
        return foodDf;
    }

    public void setFoodDf(double foodDf) {
        this.foodDf = foodDf;
    }

    public double getFoodCholesterin() {
        return foodCholesterin;
    }

    public void setFoodCholesterin(double foodCholesterin) {
        this.foodCholesterin = foodCholesterin;
    }

    public double getFoodVa() {
        return foodVa;
    }

    public void setFoodVa(double foodVa) {
        this.foodVa = foodVa;
    }

    public double getFoodAshc() {
        return foodAshc;
    }

    public void setFoodAshc(double foodAshc) {
        this.foodAshc = foodAshc;
    }

    public double getFoodCarotin() {
        return foodCarotin;
    }

    public void setFoodCarotin(double foodCarotin) {
        this.foodCarotin = foodCarotin;
    }

    public double getFoodAf() {
        return foodAf;
    }

    public void setFoodAf(double foodAf) {
        this.foodAf = foodAf;
    }

    public double getFoodVb1() {
        return foodVb1;
    }

    public void setFoodVb1(double foodVb1) {
        this.foodVb1 = foodVb1;
    }

    public double getFoodVb2() {
        return foodVb2;
    }

    public void setFoodVb2(double foodVb2) {
        this.foodVb2 = foodVb2;
    }

    public double getFoodVc() {
        return foodVc;
    }

    public void setFoodVc(double foodVc) {
        this.foodVc = foodVc;
    }

    public double getFoodVe() {
        return foodVe;
    }

    public void setFoodVe(double foodVe) {
        this.foodVe = foodVe;
    }

    public double getFoodEca() {
        return foodEca;
    }

    public void setFoodEca(double foodEca) {
        this.foodEca = foodEca;
    }

    public double getFoodEp() {
        return foodEp;
    }

    public void setFoodEp(double foodEp) {
        this.foodEp = foodEp;
    }

    public double getFoodEk() {
        return foodEk;
    }

    public void setFoodEk(double foodEk) {
        this.foodEk = foodEk;
    }

    public double getFoodEna() {
        return foodEna;
    }

    public void setFoodEna(double foodEna) {
        this.foodEna = foodEna;
    }

    public double getFoodEmg() {
        return foodEmg;
    }

    public void setFoodEmg(double foodEmg) {
        this.foodEmg = foodEmg;
    }

    public double getFoodEfe() {
        return foodEfe;
    }

    public void setFoodEfe(double foodEfe) {
        this.foodEfe = foodEfe;
    }

    public double getFoodEzn() {
        return foodEzn;
    }

    public void setFoodEzn(double foodEzn) {
        this.foodEzn = foodEzn;
    }

    public double getFoodEse() {
        return foodEse;
    }

    public void setFoodEse(double foodEse) {
        this.foodEse = foodEse;
    }

    public double getFoodEcu() {
        return foodEcu;
    }

    public void setFoodEcu(double foodEcu) {
        this.foodEcu = foodEcu;
    }

    public double getFoodEmn() {
        return foodEmn;
    }

    public void setFoodEmn(double foodEmn) {
        this.foodEmn = foodEmn;
    }

    public double getFoodPfa() {
        return foodPfa;
    }

    public void setFoodPfa(double foodPfa) {
        this.foodPfa = foodPfa;
    }

}

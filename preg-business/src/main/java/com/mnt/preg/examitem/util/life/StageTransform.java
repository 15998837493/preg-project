
package com.mnt.preg.examitem.util.life;

import org.apache.commons.lang.StringUtils;

public class StageTransform {

    private static String pregnancy_pre = "pregnancy_pre"; // 孕早

    private static String pregnancy_mid = "pregnancy_mid";// 孕中

    private static String pregnancy_late = "pregnancy_late";// 孕晚

    /**
     * 
     * 钙
     * 
     * @author mnt_zhangjing
     * @param diagnosisPregnantStage
     *            孕期阶段
     * @return
     */
    public static double transformEca(String diagnosisPregnantStage) {
        double result = 0;
        if (StringUtils.isEmpty(diagnosisPregnantStage)) {
            return result;
        }

        if (pregnancy_pre.equals(diagnosisPregnantStage)) {
            result = 800;
        }
        if (pregnancy_mid.equals(diagnosisPregnantStage)) {
            result = 1000;
        }
        if (pregnancy_late.equals(diagnosisPregnantStage)) {
            result = 1000;
        }
        return result;
    }

    /**
     * 
     * 钾
     * 
     * @author mnt_zhangjing
     * @param diagnosisPregnantStage
     *            孕期阶段
     * @return
     */
    public static double transformEk(String diagnosisPregnantStage) {
        double result = 0;
        if (StringUtils.isEmpty(diagnosisPregnantStage)) {
            return result;
        }

        if (pregnancy_pre.equals(diagnosisPregnantStage)) {
            result = 2000;
        }
        if (pregnancy_mid.equals(diagnosisPregnantStage)) {
            result = 2000;
        }
        if (pregnancy_late.equals(diagnosisPregnantStage)) {
            result = 2000;
        }
        return result;
    }

    /**
     * 
     * 镁
     * 
     * @author mnt_zhangjing
     * @param diagnosisPregnantStage
     *            孕期阶段
     * @return
     */
    public static double transformEmg(String diagnosisPregnantStage) {
        double result = 0;
        if (StringUtils.isEmpty(diagnosisPregnantStage)) {
            return result;
        }

        if (pregnancy_pre.equals(diagnosisPregnantStage)) {
            result = 370;
        }
        if (pregnancy_mid.equals(diagnosisPregnantStage)) {
            result = 370;
        }
        if (pregnancy_late.equals(diagnosisPregnantStage)) {
            result = 370;
        }
        return result;
    }

    /**
     * 
     * 铁
     * 
     * @author mnt_zhangjing
     * @param diagnosisPregnantStage
     *            孕期阶段
     * @return
     */
    public static double transformEfe(String diagnosisPregnantStage) {
        double result = 0;
        if (StringUtils.isEmpty(diagnosisPregnantStage)) {
            return result;
        }

        if (pregnancy_pre.equals(diagnosisPregnantStage)) {
            result = 20;
        }
        if (pregnancy_mid.equals(diagnosisPregnantStage)) {
            result = 24;
        }
        if (pregnancy_late.equals(diagnosisPregnantStage)) {
            result = 29;
        }
        return result;
    }

    /**
     * 
     * 锌
     * 
     * @author mnt_zhangjing
     * @param diagnosisPregnantStage
     *            孕期阶段
     * @return
     */
    public static double transformEzn(String diagnosisPregnantStage) {
        double result = 0;
        if (StringUtils.isEmpty(diagnosisPregnantStage)) {
            return result;
        }

        if (pregnancy_pre.equals(diagnosisPregnantStage)) {
            result = 9.5;
        }
        if (pregnancy_mid.equals(diagnosisPregnantStage)) {
            result = 9.5;
        }
        if (pregnancy_late.equals(diagnosisPregnantStage)) {
            result = 9.5;
        }
        return result;
    }

    /**
     * 
     * 硒
     * 
     * @author mnt_zhangjing
     * @param diagnosisPregnantStage
     *            孕期阶段
     * @return
     */
    public static double transformEse(String diagnosisPregnantStage) {
        double result = 0;
        if (StringUtils.isEmpty(diagnosisPregnantStage)) {
            return result;
        }

        if (pregnancy_pre.equals(diagnosisPregnantStage)) {
            result = 65;
        }
        if (pregnancy_mid.equals(diagnosisPregnantStage)) {
            result = 65;
        }
        if (pregnancy_late.equals(diagnosisPregnantStage)) {
            result = 65;
        }
        return result;
    }

    /**
     * 
     * 维生素A
     * 
     * @author mnt_zhangjing
     * @param diagnosisPregnantStage
     *            孕期阶段
     * @return
     */
    public static double transformVa(String diagnosisPregnantStage) {
        double result = 0;
        if (StringUtils.isEmpty(diagnosisPregnantStage)) {
            return result;
        }

        if (pregnancy_pre.equals(diagnosisPregnantStage)) {
            result = 700;
        }
        if (pregnancy_mid.equals(diagnosisPregnantStage)) {
            result = 770;
        }
        if (pregnancy_late.equals(diagnosisPregnantStage)) {
            result = 770;
        }
        return result;
    }

    /**
     * 
     * 维生素C
     * 
     * @author mnt_zhangjing
     * @param diagnosisPregnantStage
     *            孕期阶段
     * @return
     */
    public static double transformVc(String diagnosisPregnantStage) {
        double result = 0;
        if (StringUtils.isEmpty(diagnosisPregnantStage)) {
            return result;
        }

        if (pregnancy_pre.equals(diagnosisPregnantStage)) {
            result = 100;
        }
        if (pregnancy_mid.equals(diagnosisPregnantStage)) {
            result = 115;
        }
        if (pregnancy_late.equals(diagnosisPregnantStage)) {
            result = 115;
        }
        return result;
    }

    /**
     * 
     * 维生素E
     * 
     * @author mnt_zhangjing
     * @param diagnosisPregnantStage
     *            孕期阶段
     * @return
     */
    public static double transformVe(String diagnosisPregnantStage) {
        double result = 0;
        if (StringUtils.isEmpty(diagnosisPregnantStage)) {
            return result;
        }

        if (pregnancy_pre.equals(diagnosisPregnantStage)) {
            result = 14;
        }
        if (pregnancy_mid.equals(diagnosisPregnantStage)) {
            result = 14;
        }
        if (pregnancy_late.equals(diagnosisPregnantStage)) {
            result = 14;
        }
        return result;
    }

    /**
     * 
     * 维生素B1
     * 
     * @author mnt_zhangjing
     * @param diagnosisPregnantStage
     *            孕期阶段
     * @return
     */
    public static double transformVb1(String diagnosisPregnantStage) {
        double result = 0;
        if (StringUtils.isEmpty(diagnosisPregnantStage)) {
            return result;
        }

        if (pregnancy_pre.equals(diagnosisPregnantStage)) {
            result = 1.2;
        }
        if (pregnancy_mid.equals(diagnosisPregnantStage)) {
            result = 1.4;
        }
        if (pregnancy_late.equals(diagnosisPregnantStage)) {
            result = 1.5;
        }
        return result;
    }

    /**
     * 
     * 维生素B2
     * 
     * @author mnt_zhangjing
     * @param diagnosisPregnantStage
     *            孕期阶段
     * @return
     */
    public static double transformVb2(String diagnosisPregnantStage) {
        double result = 0;
        if (StringUtils.isEmpty(diagnosisPregnantStage)) {
            return result;
        }

        if (pregnancy_pre.equals(diagnosisPregnantStage)) {
            result = 1.2;
        }
        if (pregnancy_mid.equals(diagnosisPregnantStage)) {
            result = 1.4;
        }
        if (pregnancy_late.equals(diagnosisPregnantStage)) {
            result = 1.5;
        }
        return result;
    }

    /**
     * 
     * 烟酸
     * 
     * @author mnt_zhangjing
     * @param diagnosisPregnantStage
     *            孕期阶段
     * @return
     */
    public static double transformAf(String diagnosisPregnantStage) {
        double result = 0;
        if (StringUtils.isEmpty(diagnosisPregnantStage)) {
            return result;
        }

        if (pregnancy_pre.equals(diagnosisPregnantStage)) {
            result = 12;
        }
        if (pregnancy_mid.equals(diagnosisPregnantStage)) {
            result = 12;
        }
        if (pregnancy_late.equals(diagnosisPregnantStage)) {
            result = 12;
        }
        return result;
    }

    /**
     * 
     * 维生素B6
     * 
     * @author mnt_zhangjing
     * @param diagnosisPregnantStage
     *            孕期阶段
     * @return
     */
    public static double transformVb6(String diagnosisPregnantStage) {
        double result = 0;
        if (StringUtils.isEmpty(diagnosisPregnantStage)) {
            return result;
        }

        if (pregnancy_pre.equals(diagnosisPregnantStage)) {
            result = 2.2;
        }
        if (pregnancy_mid.equals(diagnosisPregnantStage)) {
            result = 2.2;
        }
        if (pregnancy_late.equals(diagnosisPregnantStage)) {
            result = 2.2;
        }
        return result;
    }

    /**
     * 
     * 维生素B12
     * 
     * @author mnt_zhangjing
     * @param diagnosisPregnantStage
     *            孕期阶段
     * @return
     */
    public static double transformVb12(String diagnosisPregnantStage) {
        double result = 0;
        if (StringUtils.isEmpty(diagnosisPregnantStage)) {
            return result;
        }

        if (pregnancy_pre.equals(diagnosisPregnantStage)) {
            result = 2.9;
        }
        if (pregnancy_mid.equals(diagnosisPregnantStage)) {
            result = 2.9;
        }
        if (pregnancy_late.equals(diagnosisPregnantStage)) {
            result = 2.9;
        }
        return result;
    }

    /**
     * 
     * 叶酸(维生素B9)
     * 
     * @author mnt_zhangjing
     * @param diagnosisPregnantStage
     *            孕期阶段
     * @return
     */
    public static double transformVb9(String diagnosisPregnantStage) {
        double result = 0;
        if (StringUtils.isEmpty(diagnosisPregnantStage)) {
            return result;
        }

        if (pregnancy_pre.equals(diagnosisPregnantStage)) {
            result = 600;
        }
        if (pregnancy_mid.equals(diagnosisPregnantStage)) {
            result = 600;
        }
        if (pregnancy_late.equals(diagnosisPregnantStage)) {
            result = 600;
        }
        return result;
    }

    /**
     * 
     * 能量推荐值
     * 
     * @author mnt_zhangjing
     * @param diagnosisPregnantStage
     *            孕期阶段
     * @return
     */
    public static double transformEnergy(String diagnosisPregnantStage, double custHeight) {
        double result = 0;
        if (StringUtils.isEmpty(diagnosisPregnantStage)) {
            return result;
        }

        if (pregnancy_pre.equals(diagnosisPregnantStage)) {
            result = custHeight * custHeight * 21.9 * 30;
        }
        if (pregnancy_mid.equals(diagnosisPregnantStage)) {
            result = custHeight * custHeight * 21.9 * 30 + 300;
        }
        if (pregnancy_late.equals(diagnosisPregnantStage)) {
            result = custHeight * custHeight * 21.9 * 30 + 450;
        }
        return result;
    }

    /**
     * 
     * 谷类推荐值
     * 
     * @author mnt_zhangjing
     * @param diagnosisPregnantStage
     *            孕期阶段
     * @return
     */
    public static double[] transformCereal(String diagnosisPregnantStage) {
        double result[] = new double[2];
        if (StringUtils.isEmpty(diagnosisPregnantStage)) {
            return result;
        }

        if (pregnancy_pre.equals(diagnosisPregnantStage)) {
            result[0] = 175;
            result[1] = 250;
        }
        if (pregnancy_mid.equals(diagnosisPregnantStage)) {
            result[0] = 175;
            result[1] = 250;
        }
        if (pregnancy_late.equals(diagnosisPregnantStage)) {
            result[0] = 200;
            result[1] = 275;
        }
        return result;
    }

    /**
     * 
     * 薯类类推荐值
     * 
     * @author mnt_zhangjing
     * @param diagnosisPregnantStage
     *            孕期阶段
     * @return
     */
    public static double[] transformPotatoes(String diagnosisPregnantStage) {
        double result[] = new double[2];
        if (StringUtils.isEmpty(diagnosisPregnantStage)) {
            return result;
        }

        if (pregnancy_pre.equals(diagnosisPregnantStage)) {
            result[0] = 75;
            result[1] = 100;
        }
        if (pregnancy_mid.equals(diagnosisPregnantStage)) {
            result[0] = 75;
            result[1] = 100;
        }
        if (pregnancy_late.equals(diagnosisPregnantStage)) {
            result[0] = 75;
            result[1] = 100;
        }
        return result;
    }

    /**
     * 
     * 蔬菜类推荐值
     * 
     * @author mnt_zhangjing
     * @param diagnosisPregnantStage
     *            孕期阶段
     * @return
     */
    public static double[] transformVegetables(String diagnosisPregnantStage) {
        double result[] = new double[2];
        if (StringUtils.isEmpty(diagnosisPregnantStage)) {
            return result;
        }

        if (pregnancy_pre.equals(diagnosisPregnantStage)) {
            result[0] = 300;
            result[1] = 500;
        }
        if (pregnancy_mid.equals(diagnosisPregnantStage)) {
            result[0] = 300;
            result[1] = 500;
        }
        if (pregnancy_late.equals(diagnosisPregnantStage)) {
            result[0] = 300;
            result[1] = 500;
        }
        return result;
    }

    /**
     * 
     * 水果推荐值
     * 
     * @author mnt_zhangjing
     * @param diagnosisPregnantStage
     *            孕期阶段
     * @return
     */
    public static double[] transformFruit(String diagnosisPregnantStage) {
        double result[] = new double[2];
        if (StringUtils.isEmpty(diagnosisPregnantStage)) {
            result[0] = 0;
            result[1] = 0;
            return result;
        }

        if (pregnancy_pre.equals(diagnosisPregnantStage)) {
            result[0] = 200;
            result[1] = 400;
        }
        if (pregnancy_mid.equals(diagnosisPregnantStage)) {
            result[0] = 200;
            result[1] = 400;
        }
        if (pregnancy_late.equals(diagnosisPregnantStage)) {
            result[0] = 200;
            result[1] = 400;
        }
        return result;
    }

    /**
     * 
     * 鱼肉蛋推荐值
     * 
     * @author mnt_zhangjing
     * @param diagnosisPregnantStage
     *            孕期阶段
     * @return
     */
    public static double[] transformFME(String diagnosisPregnantStage) {
        double result[] = new double[2];
        if (StringUtils.isEmpty(diagnosisPregnantStage)) {
            result[0] = 0;
            result[1] = 0;
            return result;
        }

        if (pregnancy_pre.equals(diagnosisPregnantStage)) {
            result[0] = 120;
            result[1] = 200;
        }
        if (pregnancy_mid.equals(diagnosisPregnantStage)) {
            result[0] = 150;
            result[1] = 200;
        }
        if (pregnancy_late.equals(diagnosisPregnantStage)) {
            result[0] = 200;
            result[1] = 250;
        }
        return result;
    }

    /**
     * 
     * 乳制品推荐值
     * 
     * @author mnt_zhangjing
     * @param diagnosisPregnantStage
     *            孕期阶段
     * @return
     */
    public static double[] transformMilk(String diagnosisPregnantStage) {
        double result[] = new double[2];
        if (StringUtils.isEmpty(diagnosisPregnantStage)) {
            result[0] = 0;
            result[1] = 0;
            return result;
        }

        if (pregnancy_pre.equals(diagnosisPregnantStage)) {
            result[0] = 300;
        }
        if (pregnancy_mid.equals(diagnosisPregnantStage)) {
            result[0] = 300;
            result[1] = 500;
        }
        if (pregnancy_late.equals(diagnosisPregnantStage)) {
            result[0] = 300;
            result[1] = 500;
        }
        return result;
    }

    /**
     * 
     * 豆类推荐值
     * 
     * @author mnt_zhangjing
     * @param diagnosisPregnantStage
     *            孕期阶段
     * @return
     */
    public static double[] transformBean(String diagnosisPregnantStage) {
        double result[] = new double[2];
        if (StringUtils.isEmpty(diagnosisPregnantStage)) {
            result[0] = 0;
            result[1] = 0;
            return result;
        }

        if (pregnancy_pre.equals(diagnosisPregnantStage)) {
            result[0] = 15;
        }
        if (pregnancy_mid.equals(diagnosisPregnantStage)) {
            result[0] = 20;
        }
        if (pregnancy_late.equals(diagnosisPregnantStage)) {
            result[0] = 20;
        }
        return result;
    }

    /**
     * 
     * 坚果类推荐值
     * 
     * @author mnt_zhangjing
     * @param diagnosisPregnantStage
     *            孕期阶段
     * @return
     */
    public static double[] transformNut(String diagnosisPregnantStage) {
        double result[] = new double[2];
        if (StringUtils.isEmpty(diagnosisPregnantStage)) {
            result[0] = 0;
            result[1] = 0;
            return result;
        }

        if (pregnancy_pre.equals(diagnosisPregnantStage)) {
            result[0] = 10;
        }
        if (pregnancy_mid.equals(diagnosisPregnantStage)) {
            result[0] = 10;
        }
        if (pregnancy_late.equals(diagnosisPregnantStage)) {
            result[0] = 10;
        }
        return result;
    }

    /**
     * 
     * 饮水推荐值
     * 
     * @author mnt_zhangjing
     * @param diagnosisPregnantStage
     *            孕期阶段
     * @return
     */
    public static double[] transformWater(String diagnosisPregnantStage) {
        double result[] = new double[2];
        if (StringUtils.isEmpty(diagnosisPregnantStage)) {
            result[0] = 0;
            result[1] = 0;
            return result;
        }

        if (pregnancy_pre.equals(diagnosisPregnantStage)) {
            result[0] = 1700;
            result[1] = 1900;
        }
        if (pregnancy_mid.equals(diagnosisPregnantStage)) {
            result[0] = 1700;
            result[1] = 1900;
        }
        if (pregnancy_late.equals(diagnosisPregnantStage)) {
            result[0] = 1700;
            result[1] = 1900;
        }
        return result;
    }
}

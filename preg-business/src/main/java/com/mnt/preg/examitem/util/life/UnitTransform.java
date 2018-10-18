
package com.mnt.preg.examitem.util.life;

import org.apache.commons.lang.StringUtils;

public class UnitTransform {

    public static double transformEgg(String num) {
        double result = 0;
        if (StringUtils.isEmpty(num)) {
            return result;
        }

        // 一份蛋约为50g
        if (num.matches("\\d+(.\\d{1,2})?")) {
            result = Double.parseDouble(num) * 50;
        }
        return result;
    }

    public static double transformFinsh(String num) {
        double result = 0;
        if (StringUtils.isEmpty(num)) {
            return result;
        }

        // 一份鱼为50g
        if (num.matches("\\d+(.\\d{1,2})?")) {
            result = Double.parseDouble(num) * 50;
        }
        return result;
    }

    // 营养糕
    public static double transformCake(String num) {
        double result = 0;
        if (StringUtils.isEmpty(num)) {
            return result;
        }

        // 一袋营养糕为45g
        if (num.matches("\\d+(.\\d{1,2})?")) {
            result = Double.parseDouble(num) * 45;
        }
        return result;
    }

    // 营养奶昔
    public static double transformMilkshake(String num) {
        double result = 0;
        if (StringUtils.isEmpty(num)) {
            return result;
        }

        // 一袋营养奶昔为40g
        if (num.matches("\\d+(.\\d{1,2})?")) {
            result = Double.parseDouble(num) * 40;
        }
        return result;
    }

    // 营养餐包
    public static double transformPacks(String num) {
        double result = 0;
        if (StringUtils.isEmpty(num)) {
            return result;
        }

        // 一袋营养餐包为40g
        if (num.matches("\\d+(.\\d{1,2})?")) {
            result = Double.parseDouble(num) * 40;
        }
        return result;
    }

    // 内脏
    public static double transformOrgans(String num) {
        double result = 0;
        if (StringUtils.isEmpty(num)) {
            return result;
        }

        // 一袋营养餐包为40g
        if (num.matches("\\d+(.\\d{1,2})?")) {
            result = Double.parseDouble(num) * 50;
        }
        return result;
    }

    // 全脂乳制品
    public static double transformAmilk(String num) {
        double result = 0;
        if (StringUtils.isEmpty(num)) {
            return result;
        }

        // 一分全脂乳制品为200g
        if (num.matches("\\d+(.\\d{1,2})?")) {
            result = Double.parseDouble(num) * 200;
        }
        return result;
    }

    // 脱脂乳制品
    public static double transformMilk(String num) {
        double result = 0;
        if (StringUtils.isEmpty(num)) {
            return result;
        }

        // 一分脱脂乳制品为200g
        if (num.matches("\\d+(.\\d{1,2})?")) {
            result = Double.parseDouble(num) * 200;
        }
        return result;
    }

    // 豆类
    public static double transformBeans(String num) {
        double result = 0;
        if (StringUtils.isEmpty(num)) {
            return result;
        }

        // 一分豆类为25g
        if (num.matches("\\d+(.\\d{1,2})?")) {
            result = Double.parseDouble(num) * 25;
        }
        return result;
    }

    // 坚果类
    public static double transformNut(String num) {
        double result = 0;
        if (StringUtils.isEmpty(num)) {
            return result;
        }

        // 一分坚果类为10g
        if (num.matches("\\d+(.\\d{1,2})?")) {
            result = Double.parseDouble(num) * 10;
        }
        return result;
    }

    // 油类
    public static double transformOil(String optionId) {
        double result = 0;
        if (StringUtils.isEmpty(optionId)) {
            return result;
        }

        switch (optionId) {
            case "O10903":
                return 10;
            case "O10904":
                return 15;
            case "O10905":
                return 40;
            default:
                return 30;
        }
    }

}

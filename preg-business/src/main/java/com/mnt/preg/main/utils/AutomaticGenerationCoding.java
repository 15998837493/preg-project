
package com.mnt.preg.main.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/*
 * AutomaticGenerationCoding.java    1.0  2017-10-18
 *
 * 沈阳成林健康科技有限公司
 * 
 */

/**
 * 按规则（A000001-B000001）生成编码
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-10-18 scd 初版
 */
public class AutomaticGenerationCoding {

    // 单例
    private AutomaticGenerationCoding() {
    }

    private static class SingletonHolder {

        private static AutomaticGenerationCoding instance = new AutomaticGenerationCoding();
    }

    public static AutomaticGenerationCoding getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 获取Asc码
     * 
     * @param st
     * @return
     */
    public int getAscNum(String st) {
        byte[] gc = st.getBytes();
        int ascNum = (int) gc[0];
        return ascNum;
    }

    /**
     * ASC转字符
     * 
     * @param backnum
     * @return
     */
    public String getNumAsc(int num) {
        /*
         * String str="";
         * if(num<65||num>90)
         * {
         * return str;
         * }
         */
        char a = (char) num;
        return a + "";
    }

    /**
     * 
     * 主方法
     * 
     * @param number
     *            传入一个String类型编码（A000001）
     * @param size
     *            传入字符串的长度
     * @return
     */
    public String getNextCashNumber(String number, int size) {
        if (StringUtils.isEmpty(number)) {
            StringBuffer num = new StringBuffer();
            for (int i = 1; i <= size; i++) {
                if (i == 1) {
                    num.append("A");
                } else if (i == size) {
                    num.append("1");
                } else {
                    num.append("0");
                }
            }
            return num.toString();
        }
        String pre = number.replaceAll("[^a-zA-Z]", "");// 获取字母部分
        String counterStr = number.replaceAll("[^0-9]", "");
        // 字符+数字
        if (!StringUtils.isEmpty(counterStr)) {
            int counter = Integer.parseInt(counterStr);// 获取数字部分
            counter++;
            Map<String, Object> checkMap = CheckNumGreaterMax(pre, counter, size);
            if (!(boolean) checkMap.get("success")) {
                counterStr = checkMap.get("counterStr").toString();
                return pre + counterStr;
            }
            // A999->AB01 字母自增
            pre = letterCounter(pre);
            if (pre.length() > size) {
                return "";
            }
            if (pre.length() < size) {
                pre += String.format("%0" + (size - pre.length()) + "d", 1);
            }
            return pre;
        }
        String str = letterCounter(pre);
        return str;
    }

    /**
     * 字母自增
     * 
     * @param str
     * @return
     */
    public String letterCounter(String str) {
        int len = str.length();
        int startI = len - 1;
        String tempA = "";
        String tempB = "";
        while (true) {
            tempA = "";
            if (startI > 0) {
                tempA = str.substring(0, startI);
            }
            if (startI < 0 && len == 0) {
                tempA = str;
                tempB = "A";
                break;
            } else {
                tempB = str.substring(startI, len);
                int lastCode = getAscNum(tempB);
                lastCode++;
                if (lastCode <= 90) {
                    tempB = getNumAsc(lastCode);
                    break;
                }
                startI--;
                len--;
            }
        }

        String newStr = tempA + tempB;
        while (newStr.length() < str.length()) {
            newStr += "A";
        }
        return newStr;
    }

    // 检查数字计数器是否超额
    public Map<String, Object> CheckNumGreaterMax(String pre, int counter, int size) {
        int preL = pre.length();
        int maxConter = 0;
        String str = "";

        int n = size - preL;
        StringBuffer max = new StringBuffer();
        for (int i = 0; i < n; i++) {
            max.append(9);
        }
        maxConter = Integer.parseInt(max.toString());
        str = String.format("%0" + (size - preL) + "d", counter);
        // switch (preL) {
        // case 1:
        // maxConter = 99999;
        // str = String.format("%05d", counter);
        // break;
        // case 2:
        // maxConter = 9999;
        // str = String.format("%04d", counter);
        // break;
        // case 3:
        // maxConter = 999;
        // str = String.format("%03d", counter);
        // break;
        // case 4:
        // maxConter = 99;
        // str = String.format("%02d", counter);
        // break;
        // case 5:
        // maxConter = 9;
        // str = String.format("%01d", counter);
        // break;
        // }
        Map<String, Object> map = new HashMap<String, Object>();
        if (counter > maxConter) {
            map.put("success", true);
        } else {
            map.put("success", false);
            map.put("counterStr", str);
        }
        return map;
    }

    // demo
    // public static void main(String[] args) {
    // AutomaticGenerationCoding test = new AutomaticGenerationCoding();
    // String num = test.getNextCashNumber(null, 7);
    // System.out.println(num);
    // }
}

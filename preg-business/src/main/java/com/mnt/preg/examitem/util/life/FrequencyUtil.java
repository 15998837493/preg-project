
package com.mnt.preg.examitem.util.life;

import org.apache.commons.lang.StringUtils;


public class FrequencyUtil {

    public static double transform(String frequency){
        if (StringUtils.isEmpty(frequency)) {
            return 0;
        }
        switch (frequency) {
            case "0":
                return 0;
            case "1":
                return 0.03;
            case "2":
                return 0.05;
            case "3":
                return 0.08;
            case "4":
                return 0.2;
            case "5":
                return 0.5;
            case "6":
                return 0.8;
            case "7":
                return 1;
            case "8":
                return 2;
            case "9":
                return 3;

            default:
                return 0;
        }
    }
}

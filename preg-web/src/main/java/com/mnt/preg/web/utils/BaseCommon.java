
package com.mnt.preg.web.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 系统参数配置
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-4-9 zcq 初版
 */
public class BaseCommon {

    /** 项目路径 */
    private String basepath;

    /** websocket服务端口号 */
    private int websocketPort;

    /** 系统版本号 */
    private String systemVersion;

    public String getBasepath() {
        return basepath;
    }

    public void setBasepath(String basepath) {
        this.basepath = basepath;
    }

    public int getWebsocketPort() {
        return websocketPort;
    }

    public void setWebsocketPort(int websocketPort) {
        this.websocketPort = websocketPort;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public static void main(String[] args) {
        Date startDate = new Date();
        Date lmp = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");// 格式化为年月
        try {
            startDate = sdf.parse("2016/12/20");
            lmp = sdf.parse("2017/4/15");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(startDate);
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(lmp);
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        // 判断是否为最小日期
        String minMonth = "";
        if (StringUtils.isEmpty(minMonth)) {
            minMonth = sdf.format(new Date());
        }
        Calendar minDate = Calendar.getInstance();
        try {
            minDate.setTime(sdf.parse(minMonth));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (min.before(minDate)) {
            minMonth = sdf.format(min.getTime());
        }
        System.out.println("minMonth:" + minMonth);

        Calendar curr = min;
        while (curr.before(max)) {
            System.out.println(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }

        String s = "P001,P002,P003,P004,P005,P006";
        List<String> codeList = Arrays.asList(s.split(","));
        System.out.println(codeList.contains("P001"));
    }

}

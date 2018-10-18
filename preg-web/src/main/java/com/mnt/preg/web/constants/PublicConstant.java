
package com.mnt.preg.web.constants;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 机构参数值
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 上午8:53:27 zy 初版
 */
public class PublicConstant {

    /** 机构logo1 */
    public static final String logo1 = "/report_logo.jpg";

    /** 机构logo2 */
    public static final String logo2 = "/report_logo.png";

    /** 补充剂频次 */
    public static final Map<String, Integer> fregMap = new HashMap<String, Integer>();
    static {
        fregMap.put("", 0);
        fregMap.put("qd", 1);
        fregMap.put("bid", 2);
        fregMap.put("tid", 3);
        fregMap.put("qid", 4);
        fregMap.put("qod", 1);
        fregMap.put("qw", 1);
        fregMap.put("biw", 1);
        fregMap.put("tiw", 1);
        fregMap.put("qow", 1);
        fregMap.put("2w", 1);
        fregMap.put("3w", 1);
        fregMap.put("4w", 1);
        fregMap.put("q1/2h", 48);
        fregMap.put("qh", 24);
        fregMap.put("q2h", 12);
        fregMap.put("q3h", 8);
        fregMap.put("q4h", 6);
        fregMap.put("q6h", 4);
        fregMap.put("q8h", 3);
        fregMap.put("q12h", 2);
        fregMap.put("st", 100);
    }

    // ****************************************************孕期补充剂元素分析***********************************************
    /** 补充剂元素不同孕期 */
    public static final Map<String, Map<String, String>> pregExtenderElementMap() {
        Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
        map.put("pregnancy_pre", pregElementPreMap);
        map.put("pregnancy_mid", pregElementMidMap);
        map.put("pregnancy_late", pregElementLateMap);
        return map;
    }

    /** 孕早期元素 */
    public static final Map<String, String> pregElementPreMap = new LinkedHashMap<String, String>();
    static {
        pregElementPreMap.put("E00005", "700~——~3000");// 维生素A
        pregElementPreMap.put("E00006", "10~——~50");// 维生素D
        pregElementPreMap.put("E00007", "——~14~700");// 维生素E
        pregElementPreMap.put("E00008", "——~80~——");// 维生素K
        pregElementPreMap.put("E00009", "1.2~——~——");// 维生素B1
        pregElementPreMap.put("E00010", "1.2~——~——");// 维生素B2
        pregElementPreMap.put("E00011", "——~2.2~60");// 维生素B6
        pregElementPreMap.put("E00012", "2.9~——~——");// 维生素B12
        pregElementPreMap.put("E00013", "——~6~——");// 泛酸
        pregElementPreMap.put("E00014", "600~——~1000");// 叶酸
        pregElementPreMap.put("E00015", "12~——~35");// 烟酸
        pregElementPreMap.put("E00016", "——~420~3000");// 胆碱
        pregElementPreMap.put("E00017", "——~40~——");// 生物素
        pregElementPreMap.put("E00018", "100~——~2000");// 维生素C

        pregElementPreMap.put("E00019", "800~——~2000");// 钙
        pregElementPreMap.put("E00020", "720~——~——");// 磷
        pregElementPreMap.put("E00021", "——~2000~——");// 钾
        pregElementPreMap.put("E00022", "——~1500~——");// 钠
        pregElementPreMap.put("E00023", "370~——~——");// 镁
        pregElementPreMap.put("E00024", "——~20~42");// 铁
        pregElementPreMap.put("E00025", "230~——~600");// 碘
        pregElementPreMap.put("E00026", "9.5~——~40");// 锌
        pregElementPreMap.put("E00027", "65~——~400");// 硒
        pregElementPreMap.put("E00028", "0.9~——~8");// 铜
        pregElementPreMap.put("E00029", "——~31~——");// 铬
        pregElementPreMap.put("E00030", "——~4.9~11");// 锰
        pregElementPreMap.put("E00031", "110~——~900");// 钼

        pregElementPreMap.put("E00032", "——~——~——");// a-亚麻酸
        pregElementPreMap.put("E00033", "——~200~——");// DHA
    }

    /** 孕中期元素 */
    public static final Map<String, String> pregElementMidMap = new LinkedHashMap<String, String>();
    static {
        pregElementMidMap.put("E00005", "770~——~3000");// 维生素A 原："770~1000~——"
        pregElementMidMap.put("E00006", "10~——~50");// 维生素D
        pregElementMidMap.put("E00007", "——~14~700");// 维生素E
        pregElementMidMap.put("E00008", "——~80~——");// 维生素K
        pregElementMidMap.put("E00009", "1.4~——~——");// 维生素B1
        pregElementMidMap.put("E00010", "1.4~——~——");// 维生素B2
        pregElementMidMap.put("E00011", "——~2.2~60");// 维生素B6
        pregElementMidMap.put("E00012", "2.9~——~——");// 维生素B12
        pregElementMidMap.put("E00013", "——~6~——");// 泛酸
        pregElementMidMap.put("E00014", "600~——~1000");// 叶酸
        pregElementMidMap.put("E00015", "12~——~35");// 烟酸
        pregElementMidMap.put("E00016", "——~420~3000");// 胆碱
        pregElementMidMap.put("E00017", "——~40~——");// 生物素
        pregElementMidMap.put("E00018", "115~——~2000");// 维生素C

        pregElementMidMap.put("E00019", "1000~——~2000");// 钙
        pregElementMidMap.put("E00020", "720~——~3500");// 磷
        pregElementMidMap.put("E00021", "——~2000~——");// 钾
        pregElementMidMap.put("E00022", "——~1500~——");// 钠
        pregElementMidMap.put("E00023", "370~——~——");// 镁
        pregElementMidMap.put("E00024", "——~24~42");// 铁
        pregElementMidMap.put("E00025", "230~——~600");// 碘
        pregElementMidMap.put("E00026", "9.5~——~40");// 锌
        pregElementMidMap.put("E00027", "65~——~400");// 硒
        pregElementMidMap.put("E00028", "0.9~——~8");// 铜
        pregElementMidMap.put("E00029", "——~34~——");// 铬
        pregElementMidMap.put("E00030", "——~4.9~11");// 锰
        pregElementMidMap.put("E00031", "110~——~900");// 钼

        pregElementMidMap.put("E00032", "——~——~——");// a-亚麻酸
        pregElementMidMap.put("E00033", "——~200~——");// DHA
    }

    /** 孕晚期元素 */
    public static final Map<String, String> pregElementLateMap = new LinkedHashMap<String, String>();
    static {
        pregElementLateMap.put("E00005", "770~——~3000");// 维生素A
        pregElementLateMap.put("E00006", "10~——~50");// 维生素D
        pregElementLateMap.put("E00007", "——~14~700");// 维生素E
        pregElementLateMap.put("E00008", "——~80~——");// 维生素K
        pregElementLateMap.put("E00009", "1.5~——~——");// 维生素B1
        pregElementLateMap.put("E00010", "1.5~——~——");// 维生素B2
        pregElementLateMap.put("E00011", "——~2.2~60");// 维生素B6
        pregElementLateMap.put("E00012", "2.9~——~——");// 维生素B12
        pregElementLateMap.put("E00013", "——~6~——");// 泛酸
        pregElementLateMap.put("E00014", "600~——~1000");// 叶酸
        pregElementLateMap.put("E00015", "12~——~35");// 烟酸
        pregElementLateMap.put("E00016", "——~420~3000");// 胆碱
        pregElementLateMap.put("E00017", "——~40~——");// 生物素
        pregElementLateMap.put("E00018", "115~——~2000");// 维生素C

        pregElementLateMap.put("E00019", "1000~——~2000");// 钙
        pregElementLateMap.put("E00020", "720~——~3500");// 磷
        pregElementLateMap.put("E00021", "——~2000~——");// 钾
        pregElementLateMap.put("E00022", "——~1500~——");// 钠
        pregElementLateMap.put("E00023", "370~——~——");// 镁
        pregElementLateMap.put("E00024", "——~29~42");// 铁
        pregElementLateMap.put("E00025", "230~——~600");// 碘
        pregElementLateMap.put("E00026", "9.5~——~40");// 锌
        pregElementLateMap.put("E00027", "65~——~400");// 硒
        pregElementLateMap.put("E00028", "0.9~——~8");// 铜
        pregElementLateMap.put("E00029", "——~36~——");// 铬
        pregElementLateMap.put("E00030", "——~4.9~11");// 锰
        pregElementLateMap.put("E00031", "110~——~900");// 钼

        pregElementLateMap.put("E00032", "——~——~——");// a-亚麻酸
        pregElementLateMap.put("E00033", "——~200~——");// DHA
    }

    // ----------------------------------------------------孕期补充剂元素分析-------------------------------------------

    // ⑪⑫⑬⑭⑮⑯⑰⑱⑲⑳
    // ①②③④⑤⑥⑦⑧⑨⑩⑪⑫⑬⑭⑮⑯⑰⑱⑲⑳㉑㉒㉓㉔㉕㉖㉗㉘㉙㉚㉛㉜㉝㉞㉟㊱㊲㊳㊴㊵㊶㊷㊸㊹㊺㊻㊼㊽㊾㊿
    // ①②③④⑤⑥⑦⑧⑨⑩⑪⑫⑬⑭⑮⑯⑰⑱⑲⑳
    public static final String SEQUENCE_CONTRAST_MAP = "①②③④⑤⑥⑦⑧⑨⑩⑪⑫⑬⑭⑮⑯⑰⑱⑲⑳";
}

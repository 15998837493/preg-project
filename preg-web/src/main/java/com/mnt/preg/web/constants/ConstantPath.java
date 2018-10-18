
package com.mnt.preg.web.constants;

import java.io.File;

/**
 * 路径常量
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-12-9 zcq 初版
 */
public class ConstantPath {

    /** 机构logo图片路径 :resource/template/image/cover/logo/ */
    public static final String LOGO_PATH = "resource/template/image/cover/logo/";

    /** 报告图片路径 :resource/template/image/cover/logo/report/ */
    public static final String REPORT_PATH = "resource/template/image/cover/logo/report/";

    /** 报告PDF路径 :resource/report/pdf/ */
    public static final String PDF_PATH = "resource/report/pdf/";

    /** 报告PDF路径 :resource/report/pdf/ */
    public static final String EXCEL_PATH = "resource/report/excel/";

    /** 报告HTML路径 :resource/report/html/ */
    public static final String HTML_PATH = "resource/report/html/";

    /** 教育中心路径 :resource/edu/ */
    public static final String EDU_PATH = "resource/edu/";

    /** PDF压缩包存放路径 */
    public static final String REPORT_ZIP_PATH = "resource" + File.separator + "upload" + File.separator + "temp"
            + File.separator + "report_zip" + File.separator;
}

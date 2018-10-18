/*
 * PathConstants.java    1.0  2015-12-9
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.web.constants;

import java.io.File;

import com.mnt.preg.web.anoton.FrontCache;

/**
 * 路径常量
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-12-9 zy 初版
 */
@FrontCache(space = "path.constants")
public class PathConstant {

    /** 默认机构logo路径 */
    public static final String template_logo = "resource/template/image/cover/logo/";

    /** 图片临时保存路径 */
    public static final String template_image_temp = "resource/template/image/temp/";

    /** 机构LOGO路径 */
    public static final String ins_logo_image = "resource/upload/insLogo/";

    /** 用户头像路径 */
    public static final String user_head_image = "resource/upload/headImage/";

    /** 教育中心路径 */
    public static final String edu_cover_image = "resource/upload/eduImage/";

    /** PDF图片下载路径 */
    public static final String down_pdf_image = "resource/upload/temp/pdfimg";

    /** 商品图片路径 */
    public static final String product_image = "resource/upload/product/ext/";

    /** 食材图片路径 */
    public static final String food_ext_image = "resource/upload/food/ext/";

    /** 食物图片路径 */
    public static final String food_cuisine_image = "resource/upload/food/cuisine/";

    /** 报告图片路径 :resource/template/image/cover/logo/report/ */
    public static final String REPORT_PATH = "resource/template/image/cover/logo/report/";

    /** 报告PDF路径 :resource/report/pdf/ */
    public static final String PDF_PATH = "resource/report/pdf/";

    /** 报告HTML路径 :resource/report/html/ */
    public static final String HTML_PATH = "resource/report/html/";

    /** 教育中心路径 :resource/edu/ */
    public static final String EDU_PATH = "resource/edu/";

    /** PDF压缩包存放路径 */
    public static final String REPORT_ZIP_PATH = "resource" + File.separator + "upload" + File.separator + "temp"
            + File.separator + "report_zip" + File.separator;

}

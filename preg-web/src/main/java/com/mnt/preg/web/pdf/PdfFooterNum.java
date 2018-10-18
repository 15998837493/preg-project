
package com.mnt.preg.web.pdf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * 设置pdf页码
 * 
 * @author gss
 * 
 */
public class PdfFooterNum extends PdfPageEventHelper {

    /**
     * 文档字体大小，页脚页眉最好和文本大小一致
     */
    public int presentFontSize = 8;

    /**
     * 文档页面大小，最好前面传入，否则默认为A4纸张
     */
    public Rectangle pageSize = PageSize.A4;

    // 模板
    public PdfTemplate total;

    // 基础字体对象
    public BaseFont bf = null;

    // 利用基础字体生成的字体对象，一般用于生成中文文字
    public Font fontDetail = null;

    /**
     * 无参构造方法
     */
    public PdfFooterNum() {

    }

    /**
     * 
     * 构造方法.
     * 
     * @param presentFontSize
     *            数据体字体大小
     * @param pageSize
     *            页面文档大小，A4，A5，A6横转翻转等Rectangle对象
     */
    public PdfFooterNum(int presentFontSize,
            Rectangle pageSize) {
        this.presentFontSize = presentFontSize;
        this.pageSize = pageSize;
    }

    public void setPresentFontSize(int presentFontSize) {
        this.presentFontSize = presentFontSize;
    }

    /**
     * 文档打开时创建模板
     */
    public void onOpenDocument(PdfWriter writer, Document document) {
        total = writer.getDirectContent().createTemplate(50, 50);// 共 页 的矩形的长宽高
    }

    /**
     * 关闭每页的时候，写入页眉，写入'第几页共'这几个字。
     */
    public void onEndPage(PdfWriter writer, Document document) {
        try {
            if (bf == null) {
                bf = BaseFont.createFont(this.readProperties().getProperty("resource.absolute.path")
                        + "resource/template/image/cover/font/msyh.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            }
            if (fontDetail == null) {
                fontDetail = new Font(bf, presentFontSize, Font.NORMAL);// 数据体字体
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 2.写入前半部分的 第 X页/共
        int pageS = writer.getPageNumber();
        String foot1 = "第 " + pageS + " 页 /共";
        Phrase footer = new Phrase(foot1, fontDetail);

        // 3.计算前半部分的foot1的长度，后面好定位最后一部分的'Y页'这俩字的x轴坐标，字体长度也要计算进去 = len
        float len = bf.getWidthPoint(foot1, presentFontSize);

        // 4.拿到当前的PdfContentByte
        PdfContentByte cb = writer.getDirectContent();

        // 5.写入页脚1，x轴就是(右margin+左margin + right() -left()- len)/2.0F
        // 再给偏移20F适合人类视觉感受，否则肉眼看上去就太偏左了
        // ,y轴就是底边界-20,否则就贴边重叠到数据体里了就不是页脚了；注意Y轴是从下往上累加的，最上方的Top值是大于Bottom好几百开外的。
        ColumnText
                .showTextAligned(
                        cb,
                        Element.ALIGN_CENTER,
                        footer,
                        (document.rightMargin() + document.right()
                                + document.leftMargin() - document.left() - len) / 2.0F + 20F,
                        document.bottom() - 30, 0);

        // 6.写入页脚2的模板（就是页脚的Y页这俩字）添加到文档中，计算模板的和Y轴,X=(右边界-左边界 - 前半部分的len值)/2.0F +
        // len ， y 轴和之前的保持一致，底边界-20
        cb.addTemplate(total, (document.rightMargin() + document.right()
                + document.leftMargin() - document.left()) / 2.0F + 20F,
                document.bottom() - 30); // 调节模版显示的位置

    }

    /**
     * 关闭文档时，替换模板，完成整个页眉页脚组件
     */
    public void onCloseDocument(PdfWriter writer, Document document) {
        // 7.最后一步了，就是关闭文档的时候，将模板替换成实际的 Y 值,至此，page x of y 制作完毕，完美兼容各种文档size。
        total.beginText();
        total.setFontAndSize(bf, presentFontSize);// 生成的模版的字体、颜色
        String foot2 = " " + (writer.getPageNumber() - 1) + " 页";
        total.showText(foot2);// 模版显示的内容
        total.endText();
        total.closePath();
    }

    /**
     * 读取配置文件
     * 
     * @author gss
     */
    public Properties readProperties() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("config/config.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return p;
    }
}

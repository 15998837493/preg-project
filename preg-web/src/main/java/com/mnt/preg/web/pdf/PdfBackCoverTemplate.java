
package com.mnt.preg.web.pdf;

import java.util.Map;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.mnt.health.utils.pdf.FrontCoverForm;

/**
 * 报告封底
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-9-7 zcq 初版
 */
public class PdfBackCoverTemplate extends AbstractPdf<FrontCoverForm> {

    @Override
    public void handler(FrontCoverForm coverForm) throws DocumentException {
        document.newPage();
        document.add(utils.createParagraph(" ", 20f, utils.darkGrayColor, 250f, 60f, 7f));
        document.add(utils.createParagraph("技术支持：北京麦芽健康管理有限公司", 9f, utils.darkGrayColor, 5f, 0, 18f));
        document.add(utils.createParagraph("服务热线：400-024-1167", 9f, utils.darkGrayColor, 5f, 0, 175f));

        utils.drawLine(writer, 0, 780f, 600f, 780f, 5f, utils.deepGreenColor);

        Map<String, String> map = coverForm.getMapForm();

        Paragraph insName = utils.createParagraph(map.get("insName"), 20f, utils.darkGrayColor, 0f, 20f, 20f);
        insName.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(insName);

        Paragraph content = utils.createParagraph("健康生活      从这里开始", 18f, utils.darkGrayColor, 0f, 20f, 20f);
        content.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(content);

        String absolutePath = this.readProperties().getProperty("resource.absolute.path");
        utils.addImage(document, absolutePath + "resource/template/image/cover/logo/" + map.get("insId")
                + "/back-cover-logo.png", 140f, 80f, 50);
    }
}

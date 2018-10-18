
package com.mnt.preg.web.pdf;

import java.util.Map;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.mnt.health.utils.pdf.FrontCoverForm;

/**
 * 报告封页
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-9-7 zcq 初版
 */
public class PdfFrontCoverTemplate extends AbstractPdf<FrontCoverForm> {

    @Override
    public void handler(FrontCoverForm coverForm) throws DocumentException {
        document.newPage();
        document.add(utils.createParagraph(" ", 20f, utils.darkGrayColor, 250f, 60f, 200f));

        utils.drawLine(writer, 0, 780f, 600f, 780f, 5f, utils.deepGreenColor);

        Paragraph title = utils.createParagraph("营养病历", 26f, utils.darkGrayColor, 0, 20f, 20f);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);
        Paragraph enTitle = utils.createParagraph("NUTRITION HISTORY", 10f, utils.darkGrayColor, 0, 0, 90f);
        enTitle.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(enTitle);

        String line = "_____________________________";

        Map<String, String> map = coverForm.getMapForm();

        document.add(utils.createParagraph("编       号：", 18f, utils.darkGrayColor, 160f, 20f, 0));
        document.add(utils.createParagraph(map.get("code"), 18f, utils.darkGrayColor, 270f, 0, 2f));
        document.add(utils.createParagraph(line, 12f, utils.darkGrayColor, 250f, 0, 40f));

        document.add(utils.createParagraph("姓       名：", 18f, utils.darkGrayColor, 160f, 0, 0));
        document.add(utils.createParagraph(map.get("name"), 18f, utils.darkGrayColor, 270f, 0, 2f));
        document.add(utils.createParagraph(line, 12f, utils.darkGrayColor, 250f, 0, 40f));

        document.add(utils.createParagraph("性       别：", 18f, utils.darkGrayColor, 160f, 0, 0));
        document.add(utils.createParagraph(map.get("sex"), 18f, utils.darkGrayColor, 270f, 0, 2f));
        document.add(utils.createParagraph(line, 12f, utils.darkGrayColor, 250f, 0, 40f));

        document.add(utils.createParagraph("年       龄：", 18f, utils.darkGrayColor, 160f, 0, 0));
        document.add(utils.createParagraph(map.get("age"), 18f, utils.darkGrayColor, 270f, 0, 2f));
        document.add(utils.createParagraph(line, 12f, utils.darkGrayColor, 250f, 0, 40f));

        document.add(utils.createParagraph("报告日期：", 18f, utils.darkGrayColor, 160f, 0, 0));
        document.add(utils.createParagraph(map.get("date"), 18f, utils.darkGrayColor, 270f, 0, 2f));
        document.add(utils.createParagraph(line, 12f, utils.darkGrayColor, 250f, 0, 40f));

        Paragraph worn = utils.createParagraph("本档案非医疗诊断报告，任何医疗行为请遵从医嘱", 12f, utils.darkGrayColor, 0, 180f, 20f);
        worn.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(worn);

        String absolutePath = this.readProperties().getProperty("resource.absolute.path");
        utils.addImage(document, absolutePath + "resource/template/image/cover/logo/" + map.get("insId")
                + "/front-cover-logo.png", 170f, 120f, 50);
    }
}

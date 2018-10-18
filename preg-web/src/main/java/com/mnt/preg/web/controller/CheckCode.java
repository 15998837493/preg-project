
package com.mnt.preg.web.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 验证码
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-6-26 zcq 初版
 */
public class CheckCode extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * 获取校验码
     * 
     * @author zcq
     * @param req
     * @param res
     * @throws IOException
     * @throws ServletException
     */
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        // 设置页面不缓存
        res.setContentType("images/jpeg");
        res.setHeader("Pragma", "No-cache");
        res.setHeader("Cache-Control", "no-cache");
        res.setDateHeader("Expires", 0);

        HttpSession session = req.getSession(true);

        // 在内存中创建图象
        int width = 160;

        // 在内存中创建图象
        int height = 80;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // 获取图形上下文
        Graphics g = image.getGraphics();

        // 生成随机类
        Random random = new Random();

        // 设定背景色 -白色
        g.setColor(new Color(255, 255, 255));
        g.fillRect(0, 0, width, height);

        // 设定字体
        g.setFont(new Font("Times New Roman", Font.PLAIN, 60));

        // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
        g.setColor(getRandColor(160, 200));

        for (int i = 0; i < 4; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(30);
            int yl = random.nextInt(30);
            g.drawLine(x, y, x + xl, y + yl);
        }

        // 取随机产生的认证码(4位数字)
        String[] code = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "m", "n", "p", "q", "r", "s", "t", "u",
                "v",
                "w", "x", "y", "z", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
                "J", "K",
                "L", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

        // 数组长度
        int length = code.length;

        String sRand = "";

        for (int i = 0; i < 4; i++) {
            // String rand=String.valueOf(random.nextInt(10)); //原来方法这个是产生数字的0-9
            // sRand+=rand;
            int start = random.nextInt(length);
            String rand = code[start];
            sRand += rand;

            // 将认证码显示到图象中 - 红色
            g.setColor(new Color(220, 20, 60)); // 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            g.drawString(rand, (30 * i) + 15, 60);
        }

        // 将认证码存入SESSION
        session.setAttribute("rand", sRand);

        // 图象生效
        g.dispose();

        // 输出图象到页面
        ImageIO.write(image, "JPEG", res.getOutputStream());
    }

    private Color getRandColor(int fc, int bc) {
        // 给定范围获得随机颜色
        Random random = new Random();

        if (fc > 255) {
            fc = 255;
        }

        if (bc > 255) {
            bc = 255;
        }

        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);

        return new Color(r, g, b);
    }

}

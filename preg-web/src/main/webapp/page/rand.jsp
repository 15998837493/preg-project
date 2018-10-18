<%@page pageEncoding="UTF-8"  language="java" contentType="image/jpeg" import="java.awt.*,java.awt.image.*,java.util.*,javax.imageio.*" %>
<%@ page import="com.mnt.preg.web.constants.SessionConstant"%>
<%!
		Color getRandColor(int fc,int bc){//给定范围获得随机颜色
        Random random = new Random();
        if(fc>255) fc=255;
        if(bc>255) bc=255;
        int r=fc+random.nextInt(bc-fc);
        int g=fc+random.nextInt(bc-fc);
        int b=fc+random.nextInt(bc-fc);
        return new Color(r,g,b);
        }
%>
<%
       // 设置页面不缓存
        response.setContentType("images/jpeg");

        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // 在内存中创建图象
        int width = 105;

        // 在内存中创建图象
        int height = 46;
        BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);

        // 获取图形上下文
        Graphics2D  g = image.createGraphics();

        // 生成随机类
        Random random = new Random();

        // 设定背景色 -白色
        g.setColor(new Color(255,255,255));
        g.fillRect(0, 0, width, height);

        // 设定字体
        g.setFont(new Font("Times New Roman", Font.PLAIN, 38));

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
        String[] code = {
                "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "m",
                "n", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C",
                "D", "E", "F", "G", "H", "J", "K", "L", "N",  "P",
                "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
            };

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
            g.setColor(new Color(220,20,60)); // 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            g.drawString(rand, (23 * i) + 6, 32);
        }

        // 将认证码存入SESSION
        session.setAttribute(SessionConstant.LOGIN_USER_RAND_CODE, sRand);

        // 图象生效
        g.dispose();

        // 输出图象到页面
        ImageIO.write(image, "JPEG", response.getOutputStream());
		//下边两行是防止抛出getOutputStream() has already been called for this response异常的
		out.clear();
		out = pageContext.pushBody();
%>
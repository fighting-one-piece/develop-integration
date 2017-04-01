package org.cisiondata.modules.login.filter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cisiondata.utils.redis.RedisUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class VerificationCodeFilter extends OncePerRequestFilter {
	
	private int width = 90;//验证码宽度
	private int height = 40;//验证码高度
	private int codeCount = 4;//验证码个数
	private int lineCount = 19;//混淆线个数
	
	//验证码内容
    char[] codeSequence = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
    		'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 
    		'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.addHeader("Cache-Control", "no-store, no-cache, must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0L);
        response.setContentType("image/jpeg");
        
        StringBuilder verificationCodeBuilder = new StringBuilder();
        //定义画布
        BufferedImage bufferedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //得到画笔
        Graphics graphics = bufferedImg.getGraphics();
        //1.设置颜色,画边框
        graphics.setColor(Color.black);
        graphics.drawRect(0,0,width,height);
        //2.设置颜色,填充内部
        graphics.setColor(Color.white);
        graphics.fillRect(1,1,width-2,height-2);
        //3.设置干扰线
        graphics.setColor(Color.gray);
        Random random = new Random();
        for (int i = 0; i < lineCount; i++) {
            graphics.drawLine(random.nextInt(width), random.nextInt(width), random.nextInt(width), random.nextInt(width));
        }
        //4.设置验证码
        graphics.setColor(Color.blue);
        //4.1设置验证码字体
        graphics.setFont(new Font("宋体", Font.BOLD|Font.ITALIC, 30));
        for (int i = 0; i < codeCount; i++) {
            char c = codeSequence[random.nextInt(codeSequence.length)];
            verificationCodeBuilder.append(c);
            graphics.drawString(c+"",15*(i+1),30);
        }
        String uuid = UUID.randomUUID().toString();
        response.setHeader("Data", uuid);
        RedisUtils.getInstance().set(uuid, verificationCodeBuilder.toString(), 60);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bufferedImg, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }

}

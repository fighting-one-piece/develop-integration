package org.cisiondata.modules.login.service.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.login.service.IVerificationCodeService;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.redis.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("verificationCodeService")
public class VerificationCodeServiceImpl implements IVerificationCodeService{
	
	private Logger LOG = LoggerFactory.getLogger(VerificationCodeServiceImpl.class);
	
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
	
    //获取验证码
	public void readVerificationCode(String time, HttpServletRequest request, HttpServletResponse response) 
			throws IOException {
		String code = UUID.randomUUID().toString();
		System.out.println("UUID:"+code);
		//定义随机数类
        Random r = new Random();
        //定义存储验证码的类
        StringBuilder builderCode = new StringBuilder();
        //定义画布
        BufferedImage buffImg = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        //得到画笔
        Graphics g = buffImg.getGraphics();
        //1.设置颜色,画边框
        g.setColor(Color.black);
        g.drawRect(0,0,width,height);
        //2.设置颜色,填充内部
        g.setColor(Color.white);
        g.fillRect(1,1,width-2,height-2);
        //3.设置干扰线
        g.setColor(Color.gray);
        for (int i = 0; i < lineCount; i++) {
            g.drawLine(r.nextInt(width),r.nextInt(width),r.nextInt(width),r.nextInt(width));
        }
        //4.设置验证码
        g.setColor(Color.blue);
        //4.1设置验证码字体
        g.setFont(new Font("宋体",Font.BOLD|Font.ITALIC,30));
        for (int i = 0; i < codeCount; i++) {
            char c = codeSequence[r.nextInt(codeSequence.length)];
            builderCode.append(c);
            g.drawString(c+"",15*(i+1),30);
        }
        //5.保存到session中
        HttpSession session = request.getSession();
        session.setAttribute("codeValidate",builderCode.toString());
        RedisUtils.getInstance().set(code, builderCode.toString());
        System.out.println("打印验证码："+builderCode.toString());
        //6.输出到屏幕
        ServletOutputStream sos = null;
        try {
        	sos = response.getOutputStream();
        	ImageIO.write(buffImg,"png",sos);
		} catch (IOException e) {
			e.printStackTrace();
		}
        //7.禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Data", code);
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/JPEG");
        //8.关闭sos
        sos.close();
	}
	
	//判断验证码
	public Boolean validateVerificationCode(String uuid, String verificationCode) throws BusinessException {
		LOG.info("uuid: {} verificationCode: {}", uuid, verificationCode);
		try {
			if (StringUtils.isBlank(verificationCode)) {
				throw new BusinessException(ResultCode.VERIFICATION_CODE_FAILURE);
			}
			Object verificationCodeObj = RedisUtils.getInstance().get(uuid);
			if (null == verificationCodeObj) {
				throw new BusinessException(ResultCode.VERIFICATION_CODE_FAILURE);
			}
			verificationCode = verificationCode.toLowerCase();
			LOG.info("v : {} rv:  {}", verificationCode, String.valueOf(verificationCodeObj).toLowerCase());
			if (!verificationCode.equalsIgnoreCase(String.valueOf(verificationCodeObj).toLowerCase())){
				throw new BusinessException(ResultCode.VERIFICATION_CODE_FAILURE);
			}
			RedisUtils.getInstance().delete(uuid);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
			throw new BusinessException(ResultCode.VERIFICATION_CODE_FAILURE);
		}
		return true;
	}

	
}

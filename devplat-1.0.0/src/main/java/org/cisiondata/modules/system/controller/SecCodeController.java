package org.cisiondata.modules.system.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cisiondata.modules.system.service.ISecCodeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 验证码Contoller
 * @author fb
 *
 */
@Controller
public class SecCodeController {
	
	@Resource(name="secCodeService")
	private ISecCodeService secCodeService = null;
	
    /**
     * 具体获取验证码的方法
     * @param time  time为时戳,这样的话可以避免浏览器缓存验证码
     * @throws IOException
     */
    @RequestMapping(value = "/verificationCode.jpg",method = RequestMethod.GET)
    public void getCode(String time, HttpServletRequest request,HttpServletResponse response) throws IOException{
    	try {
    		secCodeService.getCodeVali(time, request, response);
		} catch (Exception e) {
			e.getMessage();
		}
    }
    
}

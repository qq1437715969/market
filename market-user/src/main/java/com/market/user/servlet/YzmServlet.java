package com.market.user.servlet;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.market.constant.UserConstant;
import com.market.core.annotion.RealIP;
import com.market.core.config.CacheClient;
import com.market.exception.UserException;
import com.market.utils.CheckUtil;
import com.market.utils.IPUtils;
import com.market.utils.VerificationCodeTool;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet(urlPatterns="/user/imgYzm2.yzm")
public class YzmServlet extends HttpServlet {

	private static final long serialVersionUID = -7279575363699088431L;

	@Autowired
	private CacheClient client;
	
	@Override
	public void init(ServletConfig config)  throws  ServletException{
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req,resp);
	}

	@RealIP
	private void processRequest(HttpServletRequest req, HttpServletResponse resp) {
		try {
			String ipAddr = IPUtils.getIpAddr(req);
			String imgYzmPur = req.getParameter(UserConstant.IMG_YZM_PUR_STR);
			if(CheckUtil.isBlank(imgYzmPur)) {
				imgYzmPur = req.getHeader(UserConstant.IMG_YZM_PUR_STR);
			}
			if(CheckUtil.isBlank(imgYzmPur)) {
				throw new UserException("获取验证码参数缺失");
			}
			resp.setContentType("image/jpeg");
			resp.setHeader("Pragma", "No-cache");
			resp.setHeader("Cache-Control", "no-cache");
			resp.setDateHeader("Expires", 0L);
		    HttpSession localHttpSession = req.getSession(true);
		    VerificationCodeTool vct = new VerificationCodeTool();
		    BufferedImage image = vct.drawVerificationCodeImage();
		    ServletOutputStream localServletOutputStream = resp.getOutputStream();
		   	if(ipAddr.indexOf(":")!=-1) {
		   		ipAddr = ipAddr.replaceAll(":","").trim();
		   	}
		    client.set(UserConstant.IMG_YZM_PRE+ipAddr,vct.getXyresult()+"");
		    //localHttpSession.setAttribute("rand", vct.getXyresult()+"");
			//localHttpSession.setAttribute("randCount", 0);
			ImageIO.write(image, "JPEG", localServletOutputStream);
			localServletOutputStream.flush();
		    localServletOutputStream.close();
		    log.info("验证码生成成功,内容:"+vct.getRandomString()+" 结果:"+vct.getXyresult());
		} catch (IOException e) {
			log.error("创建验证码失败",e);
		}
	}
}

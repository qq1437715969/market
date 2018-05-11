package com.market.user.servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.market.constant.UserConstant;
import com.market.core.annotion.RealIP;
import com.market.core.config.CacheClient;
import com.market.core.constant.TimeConstant;
import com.market.exception.UserException;
import com.market.utils.CheckUtil;
import com.market.utils.IPUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@WebServlet(urlPatterns="/user/imgYzm1.yzm")
public class RandCodeServlet extends HttpServlet {

	@Autowired
	private CacheClient client;
	
	private static final long serialVersionUID = -6316706844836398723L;

	@Override
	public void init(ServletConfig config)  throws  ServletException{
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}
	
	@RealIP
	private void processRequest(HttpServletRequest req,
			HttpServletResponse resp)
			throws ServletException, IOException {
		
		String ipAddr = IPUtils.getIpAddr(req);
		String imgYzmPur = req.getParameter(UserConstant.IMG_YZM_PUR_STR);
		if(CheckUtil.isBlank(imgYzmPur)) {
			imgYzmPur = req.getHeader(UserConstant.IMG_YZM_PUR_STR);
		}
		if(CheckUtil.isBlank(imgYzmPur)) {
			throw new UserException("获取验证码参数缺失");
		}
		resp.setContentType("image/jpeg");
		resp.setHeader("Pragma", "no-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);
		int i = 60;
		int j = 20;
		BufferedImage localBufferedImage = new BufferedImage(i, j, 1);
		Graphics localGraphics = localBufferedImage.getGraphics();
		Random localRandom = new Random();
		String str1 = req.getParameter("sid");
		if (str1 == null)
			str1 = "rand";
		localGraphics.setColor(_$1(200, 250));
		localGraphics.fillRect(0, 0, i, j);
		localGraphics.setFont(new Font("Times New Roman", 0, 18));
		localGraphics.setColor(_$1(160, 200));
		for (int k = 0; k < 155; ++k) {
			int l = localRandom.nextInt(i);
			int i1 = localRandom.nextInt(j);
			int i2 = localRandom.nextInt(12);
			int i3 = localRandom.nextInt(12);
			localGraphics.drawLine(l, i1, l + i2, i1 + i3);
		}
		String str2 = "";
		for (int l = 0; l < 4; ++l) {
			String str3 = String.valueOf(localRandom.nextInt(10));
			str2 = str2 + str3;
			localGraphics.setColor(new Color(20 + localRandom.nextInt(110),
					20 + localRandom.nextInt(110), 20 + localRandom
							.nextInt(110)));
			localGraphics.drawString(str3, 13 * l + 6, 16);
		}
		if(ipAddr.indexOf(":")!=-1) {
	   		ipAddr = ipAddr.replaceAll(":","").trim();
	   	}
		Map<String, String> map = new HashMap<String, String>();
	   	map.put(UserConstant.IMG_YZM_PUR_STR,imgYzmPur);
	   	map.put(UserConstant.IMG_YZM_STR,str2.toLowerCase());
		client.set(UserConstant.IMG_YZM_PRE+ipAddr,map,TimeConstant.MINUTE_5);
//		HttpSession session = req.getSession(true);
//		if (session.isNew()) {
//			session.setMaxInactiveInterval(300);
//		}
//		session.setAttribute(str1, str2.toLowerCase());
//		session.setAttribute("randErrCount", 0);
		localGraphics.dispose();
		localBufferedImage.flush();
		ServletOutputStream localServletOutputStream = resp
				.getOutputStream();
		ImageIO.write(localBufferedImage, "JPEG", localServletOutputStream);
		localServletOutputStream.flush();
		localServletOutputStream.close();
	}

	private Color _$1(int paramInt1, int paramInt2) {
		Random localRandom = new Random();
		if (paramInt1 > 255)
			paramInt1 = 255;
		if (paramInt2 > 255)
			paramInt2 = 255;
		int i = paramInt1 + localRandom.nextInt(paramInt2 - paramInt1);
		int j = paramInt1 + localRandom.nextInt(paramInt2 - paramInt1);
		int k = paramInt1 + localRandom.nextInt(paramInt2 - paramInt1);
		return new Color(i, j, k);
	}

	protected void doGet(HttpServletRequest req,
			HttpServletResponse resp)
			throws ServletException, IOException {
		processRequest(req, resp);
	}

	protected void doPost(HttpServletRequest req,
			HttpServletResponse resp)
			throws ServletException, IOException {
		processRequest(req, resp);
	}

	public String getServletInfo() {
		return "Short description";
	}
}

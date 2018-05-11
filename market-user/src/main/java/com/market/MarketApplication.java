package com.market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import com.market.user.servlet.RandCodeServlet;
import com.market.user.servlet.YzmServlet;

@SpringBootApplication
@EnableRedisHttpSession
@ServletComponentScan("com.market.user.servlet")
public class MarketApplication {
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(MarketApplication.class, args);
	}
	
	
	
//	@Bean
//	public ServletRegistrationBean randCodeServletRegistration() {
//		ServletRegistrationBean registration = new ServletRegistrationBean(new RandCodeServlet());
//		registration.addUrlMappings("/user/imgYzm1.yzm");
//		return registration;
//	}

	/**
	 * 图形计算验证码servlet
	 * @return
	 */
//	@Bean
//	public ServletRegistrationBean yzmServletRegistration() {
//		ServletRegistrationBean registration = new ServletRegistrationBean(new YzmServlet());
//		registration.addUrlMappings("/user/imgYzm2.yzm");
//		return registration;
//	}

}

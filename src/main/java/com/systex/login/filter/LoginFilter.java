package com.systex.login.filter;

//將登入驗證帳號密碼從 controller 移至filter

//import java.io.BufferedReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.systex.login.model.UserAcc;
import com.systex.login.service.LoginService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
@Order(1)
public class LoginFilter extends OncePerRequestFilter {

	@Autowired
	LoginService loginService;

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return !request.getServletPath().startsWith("/login");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserAcc user;
		String contentType = request.getHeader("Content-Type");
		if (contentType.compareToIgnoreCase("application/x-www-form-urlencoded") == 0) {
			user = userInfoBind(request);
		} else {
			try {//目前前端皆使用AjaxLogin
				user = new Gson().fromJson(request.getReader(), UserAcc.class);// 轉置成json
			} catch (JsonSyntaxException e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
		}

		user = loginService.verify(user);
		if (session.getAttribute("userInfo") == null && user == null) {
//			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED");
//			filterChain.doFilter(request, response);
		} else {
			session.setAttribute("userInfo", user); // 將登入資訊放入session
//			filterChain.doFilter(request, response);
		}
		filterChain.doFilter(request, response);
	}

	private UserAcc userInfoBind(HttpServletRequest request) throws IOException {//表單資料與model綁定
		UserAcc user = new UserAcc();
		new ServletRequestDataBinder(user).bind(request);

		return user;
	}

}

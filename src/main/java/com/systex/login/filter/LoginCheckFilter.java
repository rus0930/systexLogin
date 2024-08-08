package com.systex.login.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.systex.login.service.LoginService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
@Order(2)
public class LoginCheckFilter extends OncePerRequestFilter{
	
	@Autowired
	LoginService loginService;

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		final String path = request.getServletPath();
		return path.equals("/") || path.startsWith("/static") || path.equals("/api/login") || path.equals("/api/signUp");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("userInfo") == null && !request.getServletPath().equals("/login")) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED");
		} else {
			filterChain.doFilter(request, response);
		}
		
	}

}

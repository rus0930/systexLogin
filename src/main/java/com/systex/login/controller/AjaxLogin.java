package com.systex.login.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.systex.login.model.UserAcc;
import com.systex.service.LoginService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class AjaxLogin {
	@Autowired
	LoginService loginService;

	@PostMapping(path = "/login", produces = "application/json")
	public UserAcc login(@RequestBody UserAcc loginInfo,HttpServletResponse response, HttpSession session) throws IOException {
		UserAcc user = loginService.verify(loginInfo);
		if(user==null) {
			response.setStatus(401);
		}
		session.setAttribute("userInfo", user); // 將登入資訊放入session
		return user;
	}
}

package com.systex.login.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.systex.login.model.ResponseMessage;
import com.systex.login.model.UserAcc;
import com.systex.login.service.LoginService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class AjaxLogin {
	@Autowired
	LoginService accService;
	
	@PostMapping(path = "/login", produces = "application/json")
	public UserAcc login(@RequestBody UserAcc loginInfo,HttpServletResponse response, HttpSession session) throws IOException {
		UserAcc user = accService.verify(loginInfo);
		if(user==null) {
			response.setStatus(401);
		}
		session.setAttribute("userInfo", user); // 將登入資訊放入session
		return user;
	}
	
	@PostMapping(path = "/signUp", produces = "application/json")
	public ResponseEntity<ResponseMessage> signUp(@RequestBody UserAcc signUpInfo,HttpServletResponse response, HttpSession session) throws IOException {
		try {
			accService.signUp(signUpInfo);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("新增帳號成功"));
		}catch (Exception e){
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage(e.getMessage()));
		}
		
	}	
	
}

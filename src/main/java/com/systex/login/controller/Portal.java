package com.systex.login.controller;


//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.systex.login.model.UserAcc;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class Portal {

//	@Autowired
//	LoginService loginService;

	@GetMapping("/")
	public String sign(HttpSession session) {//根目錄檢測session自動登入
		UserAcc login = (UserAcc) session.getAttribute("userInfo");
		if (login != null) {// login by session
			return "redirect:/home";
		}
		return "login";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {//登出
		session.invalidate();// Invalidates session
		return "redirect:/";
	}

//	@PostMapping("/")
//	public ModelAndView verify(@ModelAttribute UserAcc user, Model model, HttpSession session) {
//		ModelAndView mv = new ModelAndView("login");
//		UserAcc acc = loginService.verify(user);
//		
//		if (acc != null) {//登入成功
//			mv = new ModelAndView("redirect:/home");
////			mv.addObject("userInfo", acc); // dispatcher info
//			session.setAttribute("userInfo", acc); // 將登入資訊放入session
//		} else {//登入失敗
//			mv.addObject("loginInfo", user);
//		}
//		return mv;
//	}
	
	@GetMapping("/home")
	public String getHome() {//filter已驗證
		return "home";
	}
	
	@PostMapping("/login")
	public ModelAndView login(@ModelAttribute UserAcc user, Model model, HttpSession session) {//表單登入
		UserAcc login = (UserAcc) session.getAttribute("userInfo");
		ModelAndView mv = new ModelAndView("login");
		if (login != null) {
			mv = new ModelAndView("redirect:/home");
		}else {
			mv.addObject("loginInfo", user);
		}
		
		return mv;
	}
}

package com.systex.login.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.systex.login.model.UserAcc;
import com.systex.login.model.UserRepo;

import jakarta.servlet.http.HttpSession;

@Controller
public class Portal {

	@Autowired
	UserRepo userRepo;

	@GetMapping("/")
	public String sign(HttpSession session) {
		UserAcc login=(UserAcc)session.getAttribute("userInfo");
		if(login!=null) {//login by session
			return "home";
		}
		return "login";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();//delete session
		return "redirect:/";
	}

	@PostMapping("/sign")
	public ModelAndView verify(@ModelAttribute UserAcc user, Model model,HttpSession session) {
		ModelAndView mv = new ModelAndView("login");
		Optional<UserAcc> userO = userRepo.findById(user.getAcc());
//		System.out.println(userInfo.isEmpty());
//		System.out.println(userInfo.isPresent());//是否有資料
		if (userO.isPresent()) {
			UserAcc acc = userO.get();
			if (user.getPas().equals(acc.getPas())) {
				mv = new ModelAndView("home");
				mv.addObject("userInfo", userO.get()); //以參數dispatcher
				session.setAttribute("userInfo", acc); //將登入資訊放入session
			} else {
				mv.addObject("loginInfo", user);
			}
		} else {
			mv.addObject("loginInfo", user);
		}
		return mv;
	}

}

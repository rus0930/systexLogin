package com.systex.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systex.login.model.UserAcc;
import com.systex.login.model.UserRepo;

@Service
public class LoginService {
	
	@Autowired
	private UserRepo userRepo;
	
	
	public UserAcc verify(UserAcc user) {
		Optional<UserAcc> userO = userRepo.findById(user.getAcc());
		UserAcc userInfo = null;
		if (userO.isPresent()) {
			UserAcc acc = userO.get();
			if (user.getPas().equals(acc.getPas())) {
				userInfo = UserAcc.info(acc.getName(), acc.getAcc());
			}
		}
		return userInfo;
	}	
}

package com.systex.login.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systex.login.model.UserAcc;
import com.systex.login.model.UserRepo;

@Service
public class LoginService {

	@Autowired
	private UserRepo userRepo;
	
	private UserAcc splitPas(UserAcc userInput){
			final UserAcc userNew =new UserAcc();
			userNew.setAcc(userInput.getAcc());
			userNew.setName(userInput.getName());
			return userNew;
	}

	public UserAcc verify(UserAcc userInput) {
		Optional<UserAcc> userO = userRepo.findById(userInput.getAcc());
		UserAcc userInputInfo = null;
		if (userO.isPresent()) {
			UserAcc acc = userO.get();
			if (userInput.getPas().equals(acc.getPas())) {
				userInputInfo = splitPas(acc);
			}
		}
		return userInputInfo;
	}

	public void signUp(UserAcc userInput) throws Exception {
		if (userInput.getAcc().length() < 2 || userInput.getPas().length() < 2) {
			throw new Exception("帳號或密碼必須有兩位以上");
		}
		if (userInput.getName() == null || userInput.getName().length() == 0) {
			throw new Exception("暱稱必填");
		}
		Optional<UserAcc> userO = userRepo.findById(userInput.getAcc());
		if (userO.isPresent()) {
			throw new Exception("此帳號已經存在");
		}
		userRepo.save(userInput);
	}
}

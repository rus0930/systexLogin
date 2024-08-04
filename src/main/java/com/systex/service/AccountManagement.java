package com.systex.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systex.login.model.UserAcc;
import com.systex.login.model.UserRepo;

//尚未完成
@Service
public class AccountManagement {

	@Autowired
	UserRepo userRepo;

	public List<UserAcc> findAll() {
		System.out.println(userRepo.findAll().getClass().getName());
		return userRepo.findAll();
	}

	public UserAcc findById(String id) {
		if (id == null) {
			return null;
		}
		Optional<UserAcc> userO = userRepo.findById(id);
		if (userO.isPresent()) {
			UserAcc user = userO.get();
			return UserAcc.info(user.getAcc(), user.getName());
		}
		return null;
	}
	
	public boolean deleteById(String id) {
		if (findById(id) == null) {
			return false;
		}
		userRepo.deleteById(id);
		return true;
	}

}

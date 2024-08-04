package com.systex.login.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserAcc {
	
	@Id
	private String acc;
	private String pas;
	private String name;
	
	@Override
	public String toString() {
		return "User [acc=" + acc + ", pas=" + pas + "]";
	}
	public String getAcc() {
		return acc;
	}
	public void setAcc(String acc) {
		this.acc = acc;
	}
	public String getPas() {
		return pas;
	}
	public void setPas(String pas) {
		this.pas = pas;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public static UserAcc info(String name,String acc) {
		final UserAcc user =new UserAcc();
		user.setAcc(acc);
		user.setName(name);
		return user;
	}
	
}

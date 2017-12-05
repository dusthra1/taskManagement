package com.mindtree.task.form;

public class LoginForm {

	private String userID;
	private String password;
	private String locale;
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	@Override
	public String toString() {
		return "LoginForm [userID=" + userID + ", password=" + password + "]";
	}
	
	
	
}

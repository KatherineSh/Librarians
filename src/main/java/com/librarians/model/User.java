package com.librarians.model;

public class User {
	
	private String login;
	private String pass;
	private Enum<UserRole> role;
	
	public User(String login, String pass, Enum<UserRole> role) {
		this.login = login;
		this.pass = pass;
		this.role = role;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public Enum<UserRole> getRole() {
		return role;
	}
	public void setRole(Enum<UserRole> role) {
		this.role = role;
	}

	

}

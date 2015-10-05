package com.librarians.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="USER")
public class User implements Serializable {

	private static final long serialVersionUID = 6806558306202297628L;

	@Id
	@GeneratedValue
	@Column(name="USER_ID", columnDefinition="unsigned int(11)")
	private Integer id;
	
	@Column(name="LOGIN", length=255, nullable=false)
	private String login;
	
	@Column(name="PASSWORD", length=255, nullable=false)
	private String pass;
	
	@Column(name="NAME", length=120)
	private String name;
	
	@Column(name="EMAIL", length=255)
	private String email;
	

	@Column(name="ROLE")
	private UserRole role;
	
	public User() {
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Enumerated(EnumType.STRING)
	public UserRole getRole() {
		return role;
	}
	public void setRole(UserRole role) {
		this.role = role;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}

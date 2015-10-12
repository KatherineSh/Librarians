package com.librarians.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="USER")
//uniqueConstraints=@UniqueConstraint(columnNames={"email","user_id"})
public class User implements Serializable {

	private static final long serialVersionUID = 6806558306202297628L;

	@Id
	@GeneratedValue
	@Column(name="USER_ID", columnDefinition="unsigned int(11)", unique=true)
	private Integer id;
	
	@Size(min=5)
	@Column(name="PASSWORD", length=255)
	private String pass;
	
	@Size(max=120)
	@Column(name="NAME", length=120)
	private String name;
	
	@Size(min=10)
	@Column(name="EMAIL", length=255)
	private String email;
	
	@Enumerated(EnumType.STRING)
	@Column(name="ROLE", columnDefinition="not null default 'USER'")
	private UserRole role;
	
	@Column(name="AGE")
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date age;
	
	public User() {
	}
	
	public User(String name, String password, String email, Date age, UserRole role) {
		this.name = name;
		this.pass = password;
		this.email = email;
		this.age = age;
		this.role = role;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Date getAge() {
		return age;
	}
	public void setAge(Date age) {
		this.age = age;
	}
}

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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="USER", uniqueConstraints=@UniqueConstraint(columnNames={"email"}) )
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
	@NotBlank
	@Column(name="NAME", length=120)
	private String name;
	
	@Email
	@NotBlank
	@Column(name="EMAIL", length=255)
	private String email;
	
	@Enumerated(EnumType.STRING)
	@Column(name="ROLE", columnDefinition="not null default 'USER'")
	private UserRole role;
	
	@Column(name="BIRTHDAY")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Past
	private Date birthday;
	
	public User() {
	}
	
	public User(String name, String password, String email, Date birthday, UserRole role) {
		this.name = name;
		this.pass = password;
		this.email = email;
		this.birthday = birthday;
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
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}

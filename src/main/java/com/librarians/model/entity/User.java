package com.librarians.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Index;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.librarians.model.UserRole;


@Entity
@Table(name="USER")
public class User implements Serializable {

	private static final long serialVersionUID = 6806558306202297628L;

	@Id	@GeneratedValue
	@Column(name="id", columnDefinition="int(11) unsigned", unique=true)
	private Integer id;
	
	@NotBlank @Size(min=5) 
	@Column(name="password", length=255)
	private String pass;
	
	@NotBlank @Size(max=120)
	@Index(name="name_index")
	@Column(name="name", length=120, unique=true)
	private String name;
	
	@Email @NotBlank
	@Index(name="email_index")
	@Column(name="email", length=255, unique=true)
	private String email;
		
	@Enumerated(EnumType.STRING)
	@Column(name="role", columnDefinition="enum('ADMIN','USER','LIBRARIAN') default 'USER'")
	private UserRole role = UserRole.USER;
	
	@Column(name="birthday")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Past @Temporal(TemporalType.DATE) 
	private Date birthday;
	
	@Column(name="enabled")
	private boolean enabled = false;
	
	@OneToMany(mappedBy="user")
	@JsonBackReference
	private List<BookInstance> bookInstances;
	
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
	
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public List<BookInstance> getBookInstances() {
		return bookInstances;
	}
}

package com.librarians.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class VerificationToken implements Serializable {

	private static final long serialVersionUID = -9068867775872999638L;

	//24 hours
	private static final int EXPIRATION =  24;

	@Id
	@GeneratedValue
	@Column(name="id", unique=true)
	private Long id;

	@JoinColumn(name="user_id",nullable=false)
	@OneToOne(targetEntity=User.class,fetch=FetchType.EAGER)
	private User user;

	@NotNull
	private String token;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiryDate;

	public VerificationToken() { 
		
	}
	
	public VerificationToken(User user, String token) {
		this.user = user;
		this.token = token;
		this.expiryDate = calculateExpiryDate();
	}

	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public Date calculateExpiryDate(){
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(Calendar.HOUR, EXPIRATION);
		return currentDate.getTime();
	}
}

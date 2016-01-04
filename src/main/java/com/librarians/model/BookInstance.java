package com.librarians.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="book_instance")
public class BookInstance implements Serializable {

	private static final long serialVersionUID = 2060777674591049749L;

	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="book_id")
	@JsonManagedReference
	private Book book;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;

	@NotNull
	private boolean status = true;
	
	public BookInstance(){	
	}
	
	public BookInstance(Book book){	
		this.book = book;
	}
	
	public Integer getId() {
		return id;
	}

	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}

	public Book getBook() {
		return book;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}	

}

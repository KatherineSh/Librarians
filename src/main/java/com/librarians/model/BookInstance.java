package com.librarians.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="book_instance")
public class BookInstance implements Serializable {

	private static final long serialVersionUID = 2060777674591049749L;

	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="book_id")
	private Book book;
	
	@ManyToOne
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

	public User getUser() {
		return user;
	}	

}

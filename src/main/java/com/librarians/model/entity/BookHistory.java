package com.librarians.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="book_history")
public class BookHistory implements Serializable {

	private static final long serialVersionUID = 6040705003329038713L;

	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	
	@NotNull
	@ManyToOne( fetch=FetchType.LAZY)
	@JoinColumn(name="book_instance_id",nullable=false)
	@Cascade(value={CascadeType.SAVE_UPDATE})
	private BookInstance bookInstance;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	@Cascade(value={CascadeType.SAVE_UPDATE})
	private User reader;

	@NotBlank
	@Column(name="state",length=10)
	@Enumerated(EnumType.STRING)
	private BookState state;
	
	@NotNull
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Temporal(TemporalType.DATE)
	@Column(name="date_taken")
	private Date dateWhenTaken;
	
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Temporal(TemporalType.DATE)
	@Column(name="date_returned")
	private Date dateWhenReturned;
	
	@NotNull
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Temporal(TemporalType.DATE)
	@Column(name="expiration_date")
	private Date expirationDate;
	
	public BookHistory(BookInstance bookInstance, User reader, BookState state, Date dateWhenTaken, Date expirationDate) {
		this.bookInstance = bookInstance;
		this.reader = reader;
		this.state = state;
		this.dateWhenTaken = dateWhenTaken;
		this.expirationDate = expirationDate;
	}

	public Integer getId() {
		return this.id;
	}

	public BookInstance getBookInstance() {
		return this.bookInstance;
	}
	
	public User getReader() {
		return this.reader;
	}

	public BookState getState() {
		return this.state;
	}

	public void setState(BookState state) {
		this.state = state;
	}

	public Date getDateWhenTaken() {
		return dateWhenTaken;
	}
	
	public Date getDateWhenReturned() {
		return dateWhenReturned;
	}

	public void setDateWhenReturned(Date dateWhenReturned) {
		this.dateWhenReturned = dateWhenReturned;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}
}

package com.librarians.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

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
	
	@OneToMany(mappedBy="bookInstance", fetch=FetchType.LAZY)
	@Cascade(value={CascadeType.SAVE_UPDATE})
	private List<BookHistory> instanceHistory;

	@NotNull
	@Column(name="active", length=1)
	private boolean active = true;
	
	public BookInstance(){	
	}
	
	public BookInstance(Book book){	
		this.book = book;
	}
	
	public Integer getId() {
		return id;
	}

	public boolean isActive() {
		return this.active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
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

	public List<BookHistory> getBookHistory() {
		return this.instanceHistory;
	}

	public void setBookHistory(List<BookHistory> instanceHistory) {
		this.instanceHistory = instanceHistory;
	}
}

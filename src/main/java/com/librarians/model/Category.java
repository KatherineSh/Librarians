package com.librarians.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;

@Entity
@Table(name="book_category")
public class Category implements Serializable {

	private static final long serialVersionUID = -3254397116065632966L;

	@Id @GeneratedValue
	@Column(name="id", columnDefinition="int(11) unsigned", unique=true)
	private Integer id;
	
	@NotNull
	@Index(name="category_index")
	@Column(name="category_name", length=255, unique=true)
	private String categoryName;
	
	//inverse side of association,
	//i name mappedBy attribute to say which field in Book have association to this one
	@OneToMany(mappedBy="category", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<Book> books = new HashSet<Book>();

	public Category() {
	}

//	public Category(String categoryName) {
//		this.categoryName = categoryName;
//	}

	public Category(String id) {
		this.id = Integer.getInteger(id);
	}
		
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

	public void addBook(Book book) {
		this.books.add(book);
	}
}

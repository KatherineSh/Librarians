package com.librarians.model;


import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.hibernate.validator.constraints.NotBlank;

import com.librarians.validation.NumberLength;
import com.librarians.validation.Year;

@Entity
@Table(name="BOOK")
public class Book  implements Serializable {

	private static final long serialVersionUID = 6050908002150364357L;

	@Id @GeneratedValue
	@Column(name="id",columnDefinition="int(11) unsigned", unique=true)
	private Integer id;
	
	@NotBlank
	@Column(name="title", length=255)
	private String title;
	
	@Column(name="description", length=255)
	private String description;
	
	@NotBlank
	@Column(name="author", length=255)
	private String author;
	
	@Year(min=1900)
	private Short year;
	
	@NotNull @NumberLength(min=10, max=13)
	@Index(name="isbn_index")
	@Column(name="isbn", unique=true)
	private Long isbn;
	
	@OneToMany(mappedBy="book")
	private Set<BookInstance> instances;

	public Book(){
	}
	
	public Book(String title, String description, String author, Short year, Long isbn) {
		this.title = title;
		this.description = description;
		this.author = author;
		this.year = year;
		this.isbn = isbn;
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}

	public Short getYear() {
		return year;
	}
	public void setYear(Short year) {
		this.year = year;
	}

	public Long getIsbn() {
		return isbn;
	}
	public void setIsbn(Long isbn) {
		this.isbn = isbn;
	}	
	
	public Set<BookInstance> getInstances() {
		return instances;
	}

}

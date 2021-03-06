package com.librarians.model.entity;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Index;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.librarians.validation.NumberLength;
import com.librarians.validation.Year;

@Entity
@Table(name="book")
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
	
	@OneToMany(mappedBy="book", fetch=FetchType.LAZY)
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	@JsonBackReference
	private Set<BookInstance> instances = new HashSet<BookInstance>();
	
	//Association's owner, because it's many side
	@Valid
	@NotNull
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="category_id",nullable=false)
	private Category category;
	
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
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public Set<BookInstance> getInstances() {
		return instances;
	}
	public void setInstances(Set<BookInstance> instances) {
		this.instances = instances;
	}

}

package com.librarians.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Index;

@Entity
@Table(name="book_category")
public class BookCategory implements Serializable {

	private static final long serialVersionUID = -3254397116065632966L;

	@Id @GeneratedValue
	@Column(name="id", columnDefinition="int(11) unsigned", unique=true)
	private Integer id;
	
	@NotNull
	@Index(name="category_index")
	@Column(name="category_name", length=255, unique=true)
	private String categoryName;

	public BookCategory() {
	}
	
	public BookCategory(String categoryName) {
		this.categoryName = categoryName;
	}
		
	public Integer getId() {
		return id;
	}
	
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}

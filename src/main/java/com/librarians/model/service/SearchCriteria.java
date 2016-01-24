package com.librarians.model.service;

import java.util.StringTokenizer;

import org.hibernate.criterion.Order;

public class SearchCriteria {
	
	private final String ASC_ORDER = "asc";
	
	private Integer offset;
	
	private Integer limit;
	
	private String order;
	
	private String sortField;
	
	private String searchKey;

	//added
	private String assosiatedTableName;
	
	private String assosiatedColumnName;
	
	public SearchCriteria(Integer offset, Integer limit, String order, String sortField, String searchKey) {
		this.limit = limit;
		this.offset = offset;
		this.sortField = sortField;
		this.searchKey = searchKey;
		this.order = order;
	}
	
	public Integer getOffset() {
		return offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public String getSearchKey() {
		return searchKey;
	}
	
	public String getAssosiatedTableName() {
		return assosiatedTableName;
	}

	public String getAssosiatedColumnName() {
		return assosiatedColumnName;
	}

	//methods with logic
	
	public boolean hasSearchKey() {
		return (this.searchKey != null && !this.searchKey.isEmpty()) ? true : false;		
	}
	
	public boolean hasSortField() {
		return (this.sortField != null && !this.sortField.isEmpty()) ? true : false;		
	}
	
	public boolean hasOrderAndSortField() {
		return (order != null && !order.isEmpty() && sortField != null && !sortField.isEmpty()) ? true : false;		
	}
	
	public boolean isOrderByAssosiatedTable(){
		 boolean hasAssosiatedTable = (hasSortField() && this.sortField.contains("."));
		 
		 if(hasAssosiatedTable) {
			StringTokenizer tokenizer = new StringTokenizer(this.sortField,".");
			this.assosiatedTableName = tokenizer.nextToken();
			this.assosiatedColumnName = tokenizer.nextToken();
		 }
		 return hasAssosiatedTable;
	}
	
	public Order getOrder(String alias) {
		Order order = null;
		
		if(this.order.equals(ASC_ORDER) ){
			order = Order.asc(alias + "." + this.assosiatedColumnName);
		} else {
			order = Order.desc(alias + "." + this.assosiatedColumnName);
		}
		return order;
	}
	
	public Order getOrder() {
		Order order = null;
		
		if(this.order.equals(ASC_ORDER) ){
			order = Order.asc(this.sortField);
		} else {
			order = Order.desc(this.sortField);
		}
		return order;
	}
}

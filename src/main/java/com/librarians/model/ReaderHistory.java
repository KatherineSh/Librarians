package com.librarians.model;

import java.util.List;

import com.librarians.model.entity.BookHistory;

public interface ReaderHistory {

	public List<BookHistory> getBookHistoryForUSer(Integer id);
	
}

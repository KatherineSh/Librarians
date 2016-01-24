package com.librarians.dao.book;

import java.util.List;

import com.librarians.model.entity.BookHistory;

public interface BookHistoryDao {

	public List<BookHistory> getBookoHistoryForUser(Integer readedrId);

}

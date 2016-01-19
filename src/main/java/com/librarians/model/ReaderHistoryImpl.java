package com.librarians.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.librarians.dao.BookHistoryDao;
import com.librarians.model.entity.BookHistory;

@Service("readerHistory")
public class ReaderHistoryImpl implements ReaderHistory {
	
	@Autowired
	private BookHistoryDao bookHistoryDao;

	@Override
	public List<BookHistory> getBookHistoryForUSer(Integer readedrId) {
		List<BookHistory> history = bookHistoryDao.getBookoHistoryForUser(readedrId);
		return history;
	}

}

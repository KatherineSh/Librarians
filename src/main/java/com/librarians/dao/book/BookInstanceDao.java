package com.librarians.dao.book;

import java.util.List;

import com.librarians.model.entity.BookHistory;
import com.librarians.model.entity.BookInstance;

public interface BookInstanceDao {
	
	public Integer getInstanceCountById(Integer id);

	public Integer getFreeInstanceCountById(Integer id);

	public void addBookInstanceToUser(Integer bookId, String userName) throws Exception;

	public boolean isBookAssignedToUser(Integer bookId, String userName);

	public void removeUserAssignmentFromBookInstance(Integer bookId, String userName) throws Exception;
	
	public List<BookHistory> getBookInstanceHistory(Integer bookInstanceId);

	public List<BookInstance> getBookInstances(Integer bookId);

	public boolean getTakenInstancesCount(Integer bookId, String currentUserName);

}

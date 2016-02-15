package com.librarians.service.book;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyInt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.librarians.dao.book.BookDao;
import com.librarians.dao.book.BookInstanceDao;
import com.librarians.model.entity.Book;

public class BookServiceImplTest {
	
	@InjectMocks
	private BookServiceImpl underTest;

	@Mock
	private BookDao bookDao;
	
	@Mock
	private BookInstanceDao bookInstanceDao;
	
	@Before 
	public void setUp() {
		underTest = new BookServiceImpl();
		MockitoAnnotations.initMocks(this);
	}
		
	
	@Test
	public void testAddBook_shouldSaveOneNewBookAndTwoBookInstances() {
		Book book = new Book("title", "author", "description", (short) 1988, 1234569871L);
		Integer instanceCount = 2;
		
//		Set<BookInstance> instances = new HashSet<BookInstance>();
//		instances.add(new BookInstance(book));
//		instances.add(new BookInstance(book));

		underTest.addBook(book, instanceCount);

//		verify(instances, times(2)).add(new BookInstance(book));
//		verify(book).setInstances(instances);

		int createdSetSize = book.getInstances().size();
		assertEquals(createdSetSize, 2);
		verify(bookDao, times(1)).addBook(book);		
	}
	
	@Test
	public void testAddBook_shouldSaveOneNewBookAndNoBookInstances() {
		Book book = new Book("title", "author", "description", (short) 1988, 1234569871L);
		Integer instanceCount = 0;
		
		underTest.addBook(book, instanceCount);
		
		int createdSetSize = book.getInstances().size();
		assertEquals(createdSetSize, 0);
		verify(bookDao, times(1)).addBook(book);		
	}
	
	@Test
	public void testGetBookInstances_shouldTakeBookIdListAndReturnBookInstancesCountMap() {
	
		List<String> bookIds = Arrays.asList("12","3","8"); //look data provider
		
		when(bookInstanceDao.getFreeInstanceCountById(12)).thenReturn(1);
		when(bookInstanceDao.getFreeInstanceCountById(3)).thenReturn(3);
		when(bookInstanceDao.getFreeInstanceCountById(8)).thenReturn(0);
	
		Map<Integer, Integer> expectedResult = new HashMap<Integer, Integer>();
		expectedResult.put(12, 1);
		expectedResult.put(3, 3);
		expectedResult.put(8, 0);
		
		Map<Integer, Integer> result = underTest.getBookInstancesCount(bookIds);		
		assertEquals(result, expectedResult);
	}
	
	@Test
	public void testGetBookInstances_shouldTakeEmptyBookIdListAndReturnNull() {
		List<String> bookIds = new ArrayList<String>();

		Map<Integer, Integer> result = underTest.getBookInstancesCount(bookIds);
		assertEquals(result, new HashMap<>());
	}
	
	@Test
	public void testReturnBook_shouldTakeBookIdAndUserNameAndReturnTrue() throws Exception{
		doNothing().when(bookInstanceDao).removeUserFromBookInstance(eq(12), eq("John"));
		assertTrue(underTest.returnBook(12, "John"));
	}

	@Test(expected = Exception.class) 
	public void testReturnBook_shouldTakeBookIdAndUserNameAndReturnFalseCauseOfRollback() throws Exception{
		doThrow().when(bookInstanceDao).removeUserFromBookInstance(anyInt(), anyString());
		assertFalse(underTest.returnBook(anyInt(), anyString()));
	}
	
}

package com.librarians.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("authorService")
public class AuthorService {
	
	@Autowired
	private AuthorsProviderRestClient authorsRestClient;
	
	public List<Author> getAuthorsList(){
		return authorsRestClient.getAuthors();
	}

	public void addAuthor(String authorName) {
		Author author = new Author(authorName);
		authorsRestClient.addAuthor(author);
	}

}

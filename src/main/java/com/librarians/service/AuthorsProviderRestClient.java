package com.librarians.service;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service("authorsRestClient")
public class AuthorsProviderRestClient {
	
	public List<Author> getAuthors() {
		RestTemplate template = new RestTemplate();
		
		@SuppressWarnings("unchecked")	
		List<Author> result = template.getForObject("http://localhost:8081/ContentProvider/service/authors", List.class);
		return result;
	}

	//@ResponseStatus(HttpStatus.OK)
	public void addAuthor(Author author) {
		
		RestTemplate template = new RestTemplate();
		
		try{
			//URI uri = template.postForLocation("http://localhost:8081/ContentProvider/service/authors", author, Author.class);
			
			URI uri = template.postForLocation("http://localhost:8081/ContentProvider/service/authors", author.getName());
			
			//System.out.println("Location : " + uri.toASCIIString());
			System.out.println("---------------Done: URI = "+ uri);
		} catch( HttpClientErrorException e ) {
			if (e.getStatusCode().equals(HttpStatus.CONFLICT)) {
				//throw new AuthorDuplicationException();
				System.out.println("--------------Client:  HttpClientErrorException");
			}
		} catch (RestClientException ex){
			System.out.println("--------------Client:  exception");
		}
	}
	
/*	@ExceptionHandler(HttpClientErrorException.class)
	@ResponseStatus(value=HttpStatus.CONFLICT, reason="This author is already exist")
	public void handleCustomException(HttpClientErrorException ex) {
		System.out.println("CONFLICT");
		//ModelAndView model = new ModelAndView("error/generic_error");
		//model.addObject("errCode", ex.getErrCode());
		//model.addObject("errMsg", ex.getErrMsg());

	}*/
}

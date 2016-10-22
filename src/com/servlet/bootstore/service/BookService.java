package com.servlet.bootstore.service;

import com.servlet.bootstore.dao.BookDAO;
import com.servlet.bootstore.dao.impl.BookDAOImpl;
import com.servlet.bootstore.domain.Book;
import com.servlet.bootstore.domain.ShoppingCart;
import com.servlet.bootstore.web.CriteriaBook;
import com.servlet.bootstore.web.Page;

public class BookService {
	
	private BookDAO bookDAO = new BookDAOImpl();
	
	public Page<Book> getPage(CriteriaBook cb){
		return bookDAO.getPage(cb);
		
	}

	public Book getBook(int id) {
		return bookDAO.getBook(id);
	}

	public boolean addToCart(int id, ShoppingCart sc) {
		
		Book book = bookDAO.getBook(id);
		if(book != null){
			sc.addBook(book);
			return true;
		}
		return false;
	}

}

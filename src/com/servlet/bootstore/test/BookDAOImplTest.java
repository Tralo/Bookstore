package com.servlet.bootstore.test;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import com.servlet.bootstore.dao.impl.BookDAOImpl;
import com.servlet.bootstore.domain.Book;
import com.servlet.bootstore.domain.ShoppingCartItem;
import com.servlet.bootstore.web.CriteriaBook;
import com.servlet.bootstore.web.Page;

public class BookDAOImplTest {
	
	private BookDAOImpl bookDao = new BookDAOImpl();

	@Test
	public void testGetBook() {
		Book book = bookDao.getBook(5);
		System.out.println(book);
	}

	@Test
	public void testGetPage() {
		CriteriaBook cb = new CriteriaBook(0, 1000, 3000);
		Page<Book> page = bookDao.getPage(cb);
		System.out.println("pageNo:  " + page);
		System.out.println("totalPageNumber:  " + page.getTotalPageNumber());
		System.out.println("list:  " + page.getList());
		System.out.println("prev:  " + page.getPrevPage());
		System.out.println("next:  " + page.getNextPage());
	}


	@Test
	public void testGetStoreNumber() {
		int storeNumber = bookDao.getStoreNumber(5);
	
		System.out.println(storeNumber);
	}

	@Test
	public void testBatchUpdateStoreNumberAndSalesAmount() {
		Collection<ShoppingCartItem> items  = new ArrayList<ShoppingCartItem>();
		Book book = bookDao.getBook(1);
		ShoppingCartItem sci1 = new ShoppingCartItem(book);
		sci1.setQuantity(10);
		items.add(sci1);
		
		Book book2 = bookDao.getBook(2);
		ShoppingCartItem sci2 = new ShoppingCartItem(book2);
		sci2.setQuantity(5);
		items.add(sci2);
		

		Book book3 = bookDao.getBook(3);
		ShoppingCartItem sci3 = new ShoppingCartItem(book3);
		sci3.setQuantity(4);
		items.add(sci3);
		

		Book book4 = bookDao.getBook(4);
		ShoppingCartItem sci4 = new ShoppingCartItem(book4);
		sci4.setQuantity(12);
		items.add(sci4);
		
		bookDao.batchUpdateStoreNumberAndSalesAmount(items);
		
	}

}

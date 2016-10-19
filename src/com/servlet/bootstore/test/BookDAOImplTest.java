package com.servlet.bootstore.test;

import org.junit.Test;

import com.servlet.bootstore.dao.impl.BookDAOImpl;
import com.servlet.bootstore.domain.Book;
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
	}

}

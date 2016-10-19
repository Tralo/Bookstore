package com.servlet.bootstore.test;

import static org.junit.Assert.fail;

import java.sql.Date;
import java.util.List;

import org.junit.Test;

import com.servlet.bootstore.dao.impl.BookDAOImpl;
import com.servlet.bootstore.domain.Book;

public class BaseDAOTest {

	private BookDAOImpl baseDAO = new BookDAOImpl();
	
	@Test
	public void testInsert() {
		String sql = "INSERT INTO trade (userid,tradetime) VALUES (?,?)";
		long id = baseDAO.insert(sql,1,new Date(new java.util.Date().getTime()));
		System.out.println(id);
	}

	@Test
	public void testUpdate() {
		String sql = "UPDATE mybooks SET Salesamount = ? WHERE Id = ?";
		baseDAO.update(sql, 44,10);
	}

	@Test
	public void testQuery() {
		String sql = "SELECT id, author,title,price,publishingDate,salesAmount,storeNumber,remark FROM mybooks WHERE id = ?";
		Book book = baseDAO.query(sql, 10);
		System.out.println(book);
	}

	@Test
	public void testQueryForList() {
		String sql = "SELECT id, author,title,price,publishingDate,salesAmount,storeNumber,remark FROM mybooks WHERE id < ?";
		List<Book> books = baseDAO.queryForList(sql, 10);
		System.out.println(books);
	}

	@Test
	public void testGetSingleVal() {
		String sql = "SELECT COUNT(id) FROM mybooks";
		long count = baseDAO.getSingleVal(sql);
		System.out.println(count);
		
	}

	@Test
	public void testBatch() {
		String sql = "UPDATE mybooks SET salesAmount = ?, storeNumber = ? " +
				"WHERE id = ?";
		baseDAO.batch(sql, new Object[]{1,1,1},new Object[]{2,2,2},new Object[]{3,3,3});
	}

}

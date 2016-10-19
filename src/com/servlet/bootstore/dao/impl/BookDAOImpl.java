package com.servlet.bootstore.dao.impl;

import java.util.Collection;
import java.util.List;

import com.servlet.bootstore.dao.BookDAO;
import com.servlet.bootstore.domain.Book;
import com.servlet.bootstore.domain.ShoppingCartItem;
import com.servlet.bootstore.web.CriteriaBook;
import com.servlet.bootstore.web.Page;

public class BookDAOImpl extends BaseDAO<Book> implements BookDAO{

	@Override
	public Book getBook(int id) {
		String sql = "SELECT id, author,title,price,publishingDate,salesAmount,storeNumber,remark FROM mybooks WHERE id = ?";
		return query(sql, id);
	}

	@Override
	public Page<Book> getPage(CriteriaBook cb) {
		Page<Book> page = new Page<>(cb.getPageNo());
		page.setTotalItemNumber(getTotalBookNumber(cb));
		//检验pageNo的合法性
		cb.setPageNo(page.getPageNo());
		page.setList(getPageList(cb, 3));
		return page;
	}

	@Override
	public long getTotalBookNumber(CriteriaBook cb) {
		String sql =  "SELECT count(id) FROM mybooks WHERE price >= ? AND price <= ?";
		System.out.println(cb);
		return getSingleVal(sql, cb.getMinPrice(),cb.getMaxPrice());
	}

	@Override
	public List<Book> getPageList(CriteriaBook cb, int pageSize) {
		String sql =  "SELECT id, author,title,price,publishingDate,salesAmount,"
				+ "storeNumber,remark FROM mybooks "
				+ "WHERE price >= ? AND price <= ? "
				+ "LIMIT ?,?";
		
		
		return queryForList(sql, cb.getMinPrice(),cb.getMaxPrice(),(cb.getPageNo() - 1) * pageSize, pageSize);
	}

	@Override
	public int getStoreNumber(Integer id) { 
		String sql = "SELECT storeNumber FROM mybooks WHERE id = ?";
		return getSingleVal(sql, id);
	}

	@Override
	public void batchUpdateStoreNumberAndSalesAmount(
			Collection<ShoppingCartItem> items) {
		
	}


}

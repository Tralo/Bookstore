package com.servlet.bootstore.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.servlet.bootstore.dao.AccountDAO;
import com.servlet.bootstore.dao.BookDAO;
import com.servlet.bootstore.dao.TradeDAO;
import com.servlet.bootstore.dao.TradeItemDAO;
import com.servlet.bootstore.dao.UserDAO;
import com.servlet.bootstore.dao.impl.AccountDAOImpl;
import com.servlet.bootstore.dao.impl.BookDAOImpl;
import com.servlet.bootstore.dao.impl.TradeDAOImpl;
import com.servlet.bootstore.dao.impl.TradeItemDAOImpl;
import com.servlet.bootstore.dao.impl.UserDAOImpl;
import com.servlet.bootstore.db.JDBCUtils;
import com.servlet.bootstore.domain.Book;
import com.servlet.bootstore.domain.ShoppingCart;
import com.servlet.bootstore.domain.ShoppingCartItem;
import com.servlet.bootstore.domain.Trade;
import com.servlet.bootstore.domain.TradeItem;
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

	public void removeItemFromShoppingCart(ShoppingCart sc, int id) {
		sc.removeItem(id);
	}

	public void clearShoppingCart(ShoppingCart sc) {
		sc.clear();
	}

	public void updateItemQuantity(ShoppingCart sc, int id, int quantity) {
		sc.updateItemQuantity(id, quantity);
	}
	
	private AccountDAO accountDAO = new AccountDAOImpl();
	private TradeDAO tradeDAO = new TradeDAOImpl();
	private UserDAO userDAO = new UserDAOImpl();
	private TradeItemDAO tradeItemDAO = new TradeItemDAOImpl();

	public void cash(ShoppingCart sc, String username,
			String accountId) {
		
		Connection connection = null;
		try {
			connection = JDBCUtils.getConnection();
			connection.setAutoCommit(false);
			//DAO操作，需传入 connection 对象
			connection.commit();
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally{
		}
		//1. 更新 mybooks 数据相关记录的 salesamount 和 storenumber
		bookDAO.batchUpdateStoreNumberAndSalesAmount(sc.getItems());
		
		//2. 更新余额 account 数据表的 balance
		accountDAO.updateBalance(Integer.parseInt(accountId), sc.getTotalMoney());
		
		//3. 向 trade 数据表插入一条记录
		Trade trade = new Trade();
		trade.setTradeTime(new Date(new java.util.Date().getTime()));
		trade.setUserId(userDAO.getUser(username).getUserId());
		tradeDAO.insert(trade);
		
		//4. 向 tradeItem 数据表插入 n 条记录
		Collection<TradeItem> items = new ArrayList<TradeItem>();
		for(ShoppingCartItem sci : sc.getItems()){
			TradeItem tradeItem = new TradeItem();
			tradeItem.setBookId(sci.getBook().getId());
			tradeItem.setTradeId(trade.getTradeId());
			tradeItem.setQuantity(sci.getQuantity());
			items.add(tradeItem);
		}
		
		tradeItemDAO.batchSave(items);
		//5. 清空购物车
		sc.clear(); 
		
	}

}

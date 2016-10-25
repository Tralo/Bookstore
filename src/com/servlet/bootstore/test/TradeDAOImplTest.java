package com.servlet.bootstore.test;

import java.sql.Connection;
import java.sql.Date;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.servlet.bootstore.dao.TradeDAO;
import com.servlet.bootstore.dao.impl.TradeDAOImpl;
import com.servlet.bootstore.db.JDBCUtils;
import com.servlet.bootstore.domain.Trade;
import com.servlet.bootstore.web.ConnectionContext;

public class TradeDAOImplTest {

	private TradeDAO tradeDAO = new TradeDAOImpl();
	

	@Before
	public void prepare(){
		Connection connection = JDBCUtils.getConnection();
		ConnectionContext.getInstance().bind(connection);
	}
	
	@Test
	public void testInsertTrade() {
		Trade trade = new Trade();
		trade.setUserId(4);
		trade.setTradeTime(new Date(new java.util.Date().getTime()));
		tradeDAO.insert(trade);
	}

	@Test
	public void testGetTradesWithUserId() {
		Set<Trade> trades = tradeDAO.getTradesWithUserId(1);
		System.out.println(trades);
	}

}

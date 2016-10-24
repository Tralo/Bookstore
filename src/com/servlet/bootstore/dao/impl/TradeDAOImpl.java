package com.servlet.bootstore.dao.impl;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import com.servlet.bootstore.dao.TradeDAO;
import com.servlet.bootstore.domain.Trade;

public class TradeDAOImpl extends BaseDAO<Trade> implements TradeDAO{

	@Override
	public void insert(Trade trade) {
		String sql = "INSERT INTO trade (userid,tradetime) VALUES " +
				"(?,?)";
		update(sql, trade.getUserId(),trade.getTradeTime());
		
	}

	@Override
	public Set<Trade> getTradesWithUserId(Integer userId) {
		String sql = "SELECT tradeId, userId, tradeTime FROM trade "
				+ "WHERE userId = ?";
		
		
		return new HashSet<>(queryForList(sql, userId));
	}

}

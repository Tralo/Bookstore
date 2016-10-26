package com.servlet.bootstore.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.servlet.bootstore.dao.TradeDAO;
import com.servlet.bootstore.dao.TradeItemDAO;
import com.servlet.bootstore.dao.UserDAO;
import com.servlet.bootstore.dao.impl.TradeDAOImpl;
import com.servlet.bootstore.dao.impl.TradeItemDAOImpl;
import com.servlet.bootstore.dao.impl.UserDAOImpl;
import com.servlet.bootstore.domain.Trade;
import com.servlet.bootstore.domain.TradeItem;
import com.servlet.bootstore.domain.User;

public class UserService {
	private UserDAO userDAO = new UserDAOImpl();
	private TradeDAO tradeDAO = new TradeDAOImpl();
	private TradeItemDAO tradeItemDAO = new TradeItemDAOImpl();
	
	public User getUserByUserName(String username){
		return userDAO.getUser(username);
	}
	
	public User getUserWithTrades(String username){
		User user = userDAO.getUser(username);
		if(user == null){
			return null;
		}
		int userId = user.getUserId();
		Set<Trade> trades = tradeDAO.getTradesWithUserId(userId);
		if(trades != null){
			for(Trade t : trades){
				int tradeId = t.getTradeId();
				
				Set<TradeItem> tradeItems = tradeItemDAO.getTradeItemsWithTradeId(tradeId);
				t.setItems(tradeItems);
				
			}
		}
		user.setTrades(trades);
		return user;
	}
	
}

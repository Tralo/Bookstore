package com.servlet.bootstore.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.servlet.bootstore.dao.TradeItemDAO;
import com.servlet.bootstore.domain.TradeItem;

public class TradeItemDAOImpl extends BaseDAO<TradeItem> implements TradeItemDAO{

	
	@Override
	public void batchSave(Collection<TradeItem> items) {
		String sql = "INSERT INTO tradeitem(bookid, quantity, tradeid) "
				+ "VALUES(?,?,?)";
		Object[][] params = new Object[items.size()][3];
		
		List<TradeItem> tis = new ArrayList<TradeItem>(items);
		for(int i = 0; i < tis.size(); i++){
			TradeItem ti = tis.get(i);
			params[i][0] = ti.getBookId();
			params[i][1] = ti.getQuantity();
			params[i][2] = ti.getTradeId();
		}
		
		batch(sql, params);
		
		
	}

	@Override
	public Set<TradeItem> getTradeItemsWithTradeId(Integer tradeId) {
		String sql = "SELECT itemId,bookId,quantity,tradeId FROM tradeitem WHERE tradeId = ?";
		return new HashSet<>(queryForList(sql, tradeId));
	}

}

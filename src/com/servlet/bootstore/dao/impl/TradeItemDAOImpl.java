package com.servlet.bootstore.dao.impl;

import java.util.Collection;
import java.util.Set;

import com.servlet.bootstore.dao.TradeItemDAO;
import com.servlet.bootstore.domain.TradeItem;

public class TradeItemDAOImpl extends BaseDAO<TradeItem> implements TradeItemDAO{

	@Override
	public void batchSave(Collection<TradeItem> items) {
		
	}

	@Override
	public Set<TradeItem> getTradeItemsWithTradeId(Integer tradeId) {
		return null;
	}

}

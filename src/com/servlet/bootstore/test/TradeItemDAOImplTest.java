package com.servlet.bootstore.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.junit.Test;

import com.servlet.bootstore.dao.TradeItemDAO;
import com.servlet.bootstore.dao.impl.TradeItemDAOImpl;
import com.servlet.bootstore.domain.TradeItem;

public class TradeItemDAOImplTest {
	
	private TradeItemDAO tradeItemDAO = new TradeItemDAOImpl();

	@Test
	public void testBatchSave() {
		Collection<TradeItem> items = new ArrayList<TradeItem>();
		
		items.add(new TradeItem(null, 1, 10, 12));
		items.add(new TradeItem(null, 2, 20, 13));
		items.add(new TradeItem(null, 3, 30, 14));
		items.add(new TradeItem(null, 4, 40, 15));
		items.add(new TradeItem(null, 5, 50, 16));
		
		tradeItemDAO.batchSave(items);
	
		
	}

	@Test
	public void testGetTradeItemsWithTradeId() {
		Set<TradeItem> items = tradeItemDAO.getTradeItemsWithTradeId(12);
		System.out.println(items.toString());
		
	}

}

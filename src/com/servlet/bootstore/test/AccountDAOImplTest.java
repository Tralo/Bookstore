package com.servlet.bootstore.test;

import static org.junit.Assert.fail;

import java.sql.Connection;

import org.junit.Before;
import org.junit.Test;

import com.servlet.bootstore.dao.AccountDAO;
import com.servlet.bootstore.dao.impl.AccountDAOImpl;
import com.servlet.bootstore.db.JDBCUtils;
import com.servlet.bootstore.domain.Account;
import com.servlet.bootstore.web.ConnectionContext;

public class AccountDAOImplTest {
	
	AccountDAO accountDAO = new AccountDAOImpl();
	
	
	
	@Before
	public void prepare(){
		Connection connection = JDBCUtils.getConnection();
		ConnectionContext.getInstance().bind(connection);
	}

	@Test
	public void testGet() {
		Account a = accountDAO.get(1);
		System.out.println(a);
		
	}
	
	@Test
	public void testUpdateBalance() {
		accountDAO.updateBalance(1, 50);
	}

}

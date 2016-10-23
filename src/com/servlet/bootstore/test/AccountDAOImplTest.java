package com.servlet.bootstore.test;

import static org.junit.Assert.fail;

import org.junit.Test;

import com.servlet.bootstore.dao.AccountDAO;
import com.servlet.bootstore.dao.impl.AccountDAOImpl;
import com.servlet.bootstore.domain.Account;

public class AccountDAOImplTest {
	
	AccountDAO accountDAO = new AccountDAOImpl();

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

package com.servlet.bootstore.service;

import com.servlet.bootstore.dao.AccountDAO;
import com.servlet.bootstore.dao.impl.AccountDAOImpl;
import com.servlet.bootstore.domain.Account;

public class AccountService {
	private AccountDAO accountDAO = new AccountDAOImpl();
	public Account getAccount(int accountId){
		return accountDAO.get(accountId);
				
	}
	
}

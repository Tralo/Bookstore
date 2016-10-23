package com.servlet.bootstore.dao.impl;

import com.servlet.bootstore.dao.AccountDAO;
import com.servlet.bootstore.domain.Account;

public class AccountDAOImpl extends BaseDAO<Account> implements AccountDAO {

	@Override
	public Account get(Integer accountId) {
		String sql = "SELECT accountId,balance FROM account WHERE accountId = ?";

		return query(sql, accountId);
	}

	@Override
	public void updateBalance(Integer accountId, float amount) {
		String sql = "UPDATE account SET balance = balance - ? WHERE accountId = ?";
		update(sql, amount, accountId);

	}

}

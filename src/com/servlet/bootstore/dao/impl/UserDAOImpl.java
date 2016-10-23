package com.servlet.bootstore.dao.impl;

import com.servlet.bootstore.dao.UserDAO;
import com.servlet.bootstore.domain.User;

public class UserDAOImpl extends BaseDAO<User> implements UserDAO{

	@Override
	public User getUser(String username) {
		String sql = "SELECT userId,username,accountId " +
				"FROM userinfo WHERE username = ?";
		
		return query(sql, username);
	}

}

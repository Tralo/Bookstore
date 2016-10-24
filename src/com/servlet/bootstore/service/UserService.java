package com.servlet.bootstore.service;

import com.servlet.bootstore.dao.UserDAO;
import com.servlet.bootstore.dao.impl.UserDAOImpl;
import com.servlet.bootstore.domain.User;

public class UserService {
	private UserDAO userDAO = new UserDAOImpl();
	
	
	public User getUserByUserName(String username){
		return userDAO.getUser(username);
	}
	
}

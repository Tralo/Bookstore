package com.servlet.bootstore.dao;

import com.servlet.bootstore.domain.User;

public interface UserDAO {

	
	public abstract User getUser(String username);
	
}

package com.servlet.bootstore.test;

import org.junit.Test;

import com.servlet.bootstore.dao.UserDAO;
import com.servlet.bootstore.dao.impl.UserDAOImpl;
import com.servlet.bootstore.domain.User;

public class UserDAOImplTest {

	UserDAO userDao = new UserDAOImpl();
	@Test
	public void testGetUser() {
		User user = userDao.getUser("Tom");
		System.out.println(user);
	}

}

package com.servlet.bootstore.test;

import java.sql.Connection;

import org.junit.Before;
import org.junit.Test;

import com.servlet.bootstore.dao.UserDAO;
import com.servlet.bootstore.dao.impl.UserDAOImpl;
import com.servlet.bootstore.db.JDBCUtils;
import com.servlet.bootstore.domain.User;
import com.servlet.bootstore.web.ConnectionContext;

public class UserDAOImplTest {

	UserDAO userDao = new UserDAOImpl();
	

	@Before
	public void prepare(){
		Connection connection = JDBCUtils.getConnection();
		ConnectionContext.getInstance().bind(connection);
	}
	
	@Test
	public void testGetUser() {
		User user = userDao.getUser("Tom");
		System.out.println(user);
	}

}

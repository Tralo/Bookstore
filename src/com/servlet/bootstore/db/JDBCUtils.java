package com.servlet.bootstore.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.servlet.bootstore.exception.DBException;

/**
 * 
 * JDBC �Ĺ�����
 *
 */
public class JDBCUtils {

	private static DataSource dataSource = null;
	
	static{
		dataSource = new ComboPooledDataSource("javawebapp");
	}
	
	//��ȡ���ݿ�����
	public static Connection getConnection(){  
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("Connection获取异常");
		}
	}
 
	//�ر����ݿ�����
	public static void release(Connection connection) {
		try {
			if(connection != null){
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("release异常");
		}
	}
	
	//�ر����ݿ�����
	public static void release(ResultSet rs, Statement statement) {
		try {
			if(rs != null){
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("release异常");
		}
		
		try {
			if(statement != null){
				statement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("release异常");
		}
	}
	
}

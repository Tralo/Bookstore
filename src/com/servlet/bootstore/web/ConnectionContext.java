package com.servlet.bootstore.web;

import java.sql.Connection;

public class ConnectionContext {
	
	private ConnectionContext(){
		
	}
	
	private static ConnectionContext instance = new ConnectionContext();
	
	public static ConnectionContext getInstance(){
		return instance;
	}
	
	
	private ThreadLocal<Connection> connLocalThreadLocal = new ThreadLocal<>();
	
	
	public void bind(Connection conn){
		connLocalThreadLocal.set(conn);
	}
	
	public Connection get(){
		return connLocalThreadLocal.get();
	}
	
	public void remove(){
		connLocalThreadLocal.remove();
	}
	
	
}

package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	//leverage a design pattern called the singleton pattern
	//the singleton pattern involves making a private constructor
	//so no other class can instance this object
	//store a static reference to the single copy of the object made
	//we provide a public method to access that single instance
	
	private static ConnectionUtil singleton = new ConnectionUtil();
	
	private Connection conn;
	
	private ConnectionUtil() {
		super();
		String password = System.getenv("DB_PASSWORD");
		String username = System.getenv("DB_USERNAME");
		String url = System.getenv("DB_URL");
		try {
			this.conn = DriverManager.getConnection(url, username, password);
		}catch(SQLException e) {
			System.out.println("Failed to connect to DB");
			//System.out.println("Password: " + password);
			System.out.println("Username: " + username);
			System.out.println("URL: " + url);
		}
		
	}
	
	public static ConnectionUtil getConnectionUtil() {
		return singleton;
	}
	
	
	public Connection getConnection() {
		return conn;
	}
	
	//technically we don't need a release connection method
	// maybe in the future when we consider multi threading

}

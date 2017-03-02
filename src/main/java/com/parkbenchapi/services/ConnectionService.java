package com.parkbenchapi.services;

import java.sql.*;

public abstract class ConnectionService {
	
	/**
	 * Connection object to be used by services
	 */
	protected Connection conn = null;
	
	/**
	 * Constructor for initializing connection to the database
	 */
	public ConnectionService() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			//Comment this line out in development
			conn = DriverManager.getConnection("jdbc:mysql://localhost/ParkBench", "ParkBenchAdmin", "Yensid1901!");
			
			// Comment this line out before deploying
			//conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/ParkBenchDB", "root", "root");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

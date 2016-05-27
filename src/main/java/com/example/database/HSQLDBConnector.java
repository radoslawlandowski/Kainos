package com.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class HSQLDBConnector implements DatabaseConnector {
	
	private static final Logger logger = LogManager.getLogger(HSQLDBConnector.class);
	
	private String driver;
	private String path;
	private String login;
	private String password;  
	
	public HSQLDBConnector(String driver, String path, String login, String password) {
		this.driver = driver;
		this.path = path;
		this.login = login;
		this.password = password;
	}
	
	public HSQLDBConnector() {
		this("org.hsqldb.jdbc.JDBCDriver",
		     "jdbc:hsqldb:mem:testdb",
			 "sa",
			 "");
	}
	
	@Override
	public Connection connect() throws SQLException {
		Connection c = null;
	      try {
	         Class.forName(this.driver);
	         c = DriverManager.getConnection(this.path, this.login, this.password);
	      } catch (Exception e) {
	    	  logger.error("Cannot connect to the database", e);
	    	  throw new SQLException();
	      }
	      logger.info("Connected to the database!");
	      return c;
	}
}

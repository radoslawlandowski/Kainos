package com.example.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseConnector {
	public Connection connect() throws SQLException;
}
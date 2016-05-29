package com.example.infrastructure;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.database.DatabaseConnector;

public class SQLRepositoryTest {

	@Autowired
	DatabaseConnector connector;
	
	@InjectMocks
	SQLRepository repo;
	
	@Mock
	Connection mockConn;
	
	@Mock
	Statement mockStmt;
	
	@Mock
	PreparedStatement mockPstmt;
	
	@Mock
	ResultSet mockRs;
	
	@Before
	public void setup() {
		repo = new SQLRepository(mockConn);
	}
	
	@Test
	public void firstTest() {
		
	}
	
	
}

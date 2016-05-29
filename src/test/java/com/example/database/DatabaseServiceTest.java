package com.example.database;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.example.infrastructure.SQLRepository;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseServiceTest {
	
	private static final Logger logger = LogManager.getLogger(DatabaseServiceTest.class);
	private static final String fileName = "data.csv";

	@InjectMocks
	private DatabaseService service = new DatabaseServiceImpl();
	
	@Mock 
	private DatabaseConnector mockConnector;
	
	@Mock
	private Connection mockConn;
	
	@Mock
	private PreparedStatement mockPstmt;
	
	@Mock
	private ResultSet mockRs;
	
	@Mock
	private SQLRepository mockRepo;
 
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void initializeDatabaseTest() {
		try {
			Mockito.when(mockConnector.connect()).thenReturn(mockConn);
			
			service.initializeDatabase();
		} catch (SQLException e) {
			logger.warn("Cant connect!", e);
		}
				
		verify(mockRepo, times(1)).createTableInsideDatabaseRevisited();
		verify(service, times(1)).insertDataFromFile(fileName);
	}
	
 
}

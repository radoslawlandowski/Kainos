package com.example.infrastructure;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

public class SQLRepositoryTest {

	private static final Logger logger = LogManager.getLogger(SQLRepositoryTest.class);

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
	
	String start = "2011-02-04";
	String end = "2011-04-04";
	
	Date sDate;
	Date eDate;
	
	BigDecimal val;
	BigDecimal val1;
	BigDecimal val2;
	
	
	@Before
	public void setup() {
		mockConn = Mockito.mock(Connection.class);
		mockPstmt = Mockito.mock(PreparedStatement.class);
		mockRs = Mockito.mock(ResultSet.class);
		mockStmt = Mockito.mock(Statement.class);

		repo = new SQLRepository(mockConn);
		
		sDate = prepareDate(start);
		eDate = prepareDate(end);

 		val1 = new BigDecimal(100);
		val2 = new BigDecimal(102);
	
	}

	private Date prepareDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date trueDate = null;
		java.sql.Date resultDate = null;
		try {
			trueDate = sdf.parse(date);
			resultDate = new Date(trueDate.getTime());
		} catch (ParseException e) {
			logger.error("The date cannot be parsed. Check your string format. [Should be: yyyy-MM-dd]", e);
		}
		return resultDate;
	}

	@Test
	public void createTable() {
		String createTableString = "CREATE TABLE kainos (mydate DATE, val CHAR(6));";
		try {
			Mockito.when(mockConn.createStatement()).thenReturn(mockStmt);
			Mockito.when(mockStmt.executeUpdate(createTableString)).thenReturn(0);

			repo.createTableInsideDatabaseRevisited();

			verify(mockConn, times(1)).createStatement();
			verify(mockStmt, times(1)).executeUpdate(createTableString);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void insertRowWithCorrectData() {
		String insertString = "INSERT INTO kainos VALUES (?, ?);";



		try {
			Mockito.when(mockConn.prepareStatement(insertString)).thenReturn(mockPstmt);
			Mockito.when(mockPstmt.executeUpdate()).thenReturn(1);

			repo.insertRowRevisited(sDate, val1);

			verify(mockConn, times(1)).setAutoCommit(false);
			verify(mockConn, times(1)).prepareStatement(insertString);
			verify(mockPstmt, times(1)).setDate(1, sDate);
			verify(mockPstmt, times(1)).setBigDecimal(2, val1);
			verify(mockConn, times(1)).commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void selectWhereDateMatchesWithCorrectData() {
		String query = "SELECT * FROM kainos WHERE mydate BETWEEN ? AND ?;";

		try {
			Mockito.when(mockConn.prepareStatement(query)).thenReturn(mockPstmt);
			Mockito.when(mockPstmt.executeQuery()).thenReturn(mockRs);
			
			repo.selectWhereDateMatchesRevisited(sDate, eDate);
			
			verify(mockConn, times(1)).prepareStatement(query);
			verify(mockPstmt, times(1)).setDate(1, sDate);
			verify(mockPstmt, times(1)).setDate(2, eDate);
			verify(mockPstmt, times(1)).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

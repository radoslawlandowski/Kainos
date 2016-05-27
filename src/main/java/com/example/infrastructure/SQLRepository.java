package com.example.infrastructure;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.example.model.Exchange;

public class SQLRepository {

	private static final Logger logger = LogManager.getLogger(SQLRepository.class);

	private Connection c;

	public SQLRepository(Connection c) {
		this.c = c;
	}

	private void rollTransactionBack(Connection con) {
		if (con != null) {
			try {
				logger.error("Transaction is being rolled back!");
				con.rollback();
			} catch (SQLException e) {
				logger.error("Could not roll transaction back", e);
			}
		}
	}

	private void close(PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				logger.warn("Could not close PreparedStatement pstmt!", e);
			}
		}
	}

	private void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				logger.warn("Could not close ResultSet rs!", e);
			}
		}
	}
	
	private void close(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				logger.warn("Could not close Statement st!", e);
			}
		}
	}

	public void createTableInsideDatabaseRevisited() {
		String createTableString = "CREATE TABLE kainos (mydate DATE, val CHAR(6));";
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			stmt.executeUpdate(createTableString);
			
		} catch (SQLException e) {
			logger.error("Cannot create the database", e);
		} finally {
			close(stmt);
		}
	}

	public void insertRowRevisited(Object[] data) throws IllegalArgumentException {

		String insertString = "INSERT INTO kainos VALUES (?, ?);";

		PreparedStatement pstmt = null;
		try {
			c.setAutoCommit(false);
			pstmt = c.prepareStatement(insertString);
			pstmt.setDate(1, (Date) data[0]);
			pstmt.setBigDecimal(2, (BigDecimal) data[1]);
			pstmt.executeUpdate();
			c.commit();
		} catch (SQLException e) {
			logger.error("SQL insert failed", e);
			rollTransactionBack(c);
		} finally {
			close(pstmt);
		}

	}

	private List<Exchange> receiveDataRevisited(ResultSet rs) {

		List<Exchange> rows = new ArrayList<Exchange>();

		try {
			while (rs.next()) {
				Date date = rs.getDate("mydate");
				BigDecimal value = rs.getBigDecimal("val").setScale(2, RoundingMode.HALF_EVEN);
				Object[] container = { date, value };
				Exchange row = new Exchange(container);
				rows.add(row);
			}
		} catch (SQLException e) {
			logger.error("Data receive failed", e);
		} finally {
			close(rs);
		}
		return rows;
	}

	public List<Exchange> selectRevisited() throws IllegalArgumentException {
		
		List<Exchange> data = new ArrayList<Exchange>();

		String selectString = "SELECT * FROM kainos;";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = c.prepareStatement(selectString);
			rs = pstmt.executeQuery();
			data = receiveDataRevisited(rs);
			logger.info("Select all query performed successfully!");
		} catch (SQLException e) {
			logger.error("SQL Select failed", e);
			rollTransactionBack(c);
		} finally {
			close(pstmt);
			close(rs);
		}
		return data;

	}

	public List<Exchange> selectWhereDateMatchesRevisited(Date startDate, Date endDate)
			throws IllegalArgumentException {
		List<Exchange> data = new ArrayList<Exchange>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String query = "SELECT * FROM kainos WHERE mydate BETWEEN ? AND ?;";
		try {
			pstmt = c.prepareStatement(query);
			pstmt.setDate(1, startDate);
			pstmt.setDate(2, endDate);
			rs = pstmt.executeQuery();
			data = receiveDataRevisited(rs);
			logger.info("Select query performed successfully!");
		} catch (SQLException e) {
			logger.info("Select query failed!", e);
			rollTransactionBack(c);
		} finally {
			close(pstmt);
			close(rs);
		}
		return data;
	}
}

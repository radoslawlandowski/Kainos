package com.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.example.model.Exchange;

public class SQLRepository {
	
		private static final Logger logger = LogManager.getLogger(SQLRepository.class);
	
		private Connection c; 

		private SQLTable table;
			
		public SQLRepository(Connection c, SQLTable table) {
			this.c = c;
			this.table = table;
		}
		
		public SQLTable getTable() {
			return table;
		}

		public void setTable(SQLTable table) {
			this.table = table;
		}

		private void rollTransactionBack(Connection con) {
		        if (con != null) {
		            try {
		                logger.error("Transaction is being rolled back!");
		                con.rollback();
		            } catch(SQLException e) {
		                e.printStackTrace();
		            }
		        }
		}
		
		private void close(PreparedStatement pstmt) {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					logger.warn("Could not close PreparedStatement pstmt!");
					e.printStackTrace();
				}
			}
		}
		
		private void close(ResultSet rs) {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.warn("Could not close ResultSet rs!");
					e.printStackTrace();
				}
			}
		}
		
		public boolean exists(String tableName) {			
			DatabaseMetaData dbm;
			boolean result = false;
			try {
				dbm = c.getMetaData();
				ResultSet tables = dbm.getTables(null, null, tableName, null);
				if (tables.next()) {
				  result = true;
				}
				else {
				  result = false;
				}	
			} catch (SQLException e) {
				e.printStackTrace();
			}
		   return result;
		}
		
		public int countRows() {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String query = "SELECT COUNT(*) AS total FROM " + table.getTableName();
			int rows = 0;
			try {
				c.setAutoCommit(false);
				pstmt = c.prepareStatement(query);
		        rs = pstmt.executeQuery();
		        if (rs.last()) {
		            rows = rs.getRow();
		            // Move to beginning
		            rs.beforeFirst();
		        }
		    } catch (SQLException e ) {
		        e.printStackTrace();
		        rollTransactionBack(c);
		    } finally {
		    	close(pstmt);
		    	close(rs);
		    }
			return rows;

		}
		
		public void createTableInsideDatabase() { 
			
			String tableName = table.getTableName();
			int numberOfColumns = table.getNumberOfColumns();
			String columnNames[] = table.getColumnNames();
			DataTypes types[] = table.getColumnTypes();
			int sizes[] = table.getSizes();
			
			StringBuilder builder = new StringBuilder();
			builder.append("CREATE TABLE ")
				   .append(tableName)
				   .append(" (");
			for (int i = 0 ; i < numberOfColumns ; i++) {
				 builder.append(columnNames[i])
				 		.append(" ")
				 		.append(types[i].toString());
				 if(sizes[i] != 0) {
					 builder.append("(")
					 		.append(sizes[i])
					 		.append(")");
				 }
				 		builder.append(", ");
				}  
			builder.delete(builder.length()-2, builder.length());
			builder.append(");");
			String createString = builder.toString();
						
			PreparedStatement pstmt = null;
			try {
				c.setAutoCommit(false);
		        pstmt = c.prepareStatement(createString);
		        pstmt.executeUpdate();
		        c.commit();
				logger.info("Table \"" + tableName + "\" created!");
		    } catch (SQLException e ) {
		    	logger.error("Table \"" + this.table.getTableName() + "\" already exists!");
		        e.printStackTrace();
		        rollTransactionBack(c);
		    } finally {
		    	close(pstmt);
		    }
			
			
		}
		
		public void insertRow(String... data) throws IllegalArgumentException {
			
			int numberOfColumns = table.getNumberOfColumns();
			String tableName = table.getTableName();
			
			if(data.length != numberOfColumns) {
				logger.error("Wrong amount of arguments for table: \"" + table.getTableName() + "\"!");
				throw new IllegalArgumentException("Wrong amount of arguments for table: \"" + table.getTableName() + "\"!");
			}
			
			String insertIntro = "INSERT INTO " + tableName + " VALUES (";
			StringBuilder b = new StringBuilder(insertIntro);
			for(@SuppressWarnings("unused") String item : data) {
				b.append("?, ");
			}
			b.delete(b.length()-2, b.length());
			b.append(");");
			
			String insertString = b.toString();
			
			PreparedStatement pstmt = null;
			try {
				c.setAutoCommit(false);
				pstmt = c.prepareStatement(insertString);
				for(int i = 0 ; i < numberOfColumns ; i++) {
					pstmt.setString(i+1, data[i]);
				}
				pstmt.executeUpdate();
				c.commit();
				logger.info("Row inserted into \"" + tableName + "\"!");
			} catch (SQLException e ) {
		        e.printStackTrace();
		        rollTransactionBack(c);
		    } finally {
		    	close(pstmt);
		    }
			
		}
		
		public void insertRowRevisited(Object[] data) throws IllegalArgumentException {
			
			int numberOfColumns = table.getNumberOfColumns();
			String tableName = table.getTableName();
			
			if(data.length != numberOfColumns) {
				logger.error("Wrong amount of arguments for table: \"" + table.getTableName() + "\"!");
				throw new IllegalArgumentException("Wrong amount of arguments for table: \"" + table.getTableName() + "\"!");
			}
			
			String insertIntro = "INSERT INTO " + tableName + " VALUES (";
			StringBuilder b = new StringBuilder(insertIntro);
			for(@SuppressWarnings("unused") Object item : data) {
				b.append("?, ");
			}
			b.delete(b.length()-2, b.length());
			b.append(");");
			
			String insertString = b.toString();
			
			PreparedStatement pstmt = null;
			try {
				c.setAutoCommit(false);
				pstmt = c.prepareStatement(insertString);
				pstmt.setDate(1, (Date)data[0]);
				pstmt.setBigDecimal(2, (BigDecimal)data[1]);
				pstmt.executeUpdate();
				c.commit();
			} catch (SQLException e ) {
		        e.printStackTrace();
		        rollTransactionBack(c);
		    } finally {
		    	close(pstmt);
		    }
			
		}
		
		private List<ArrayList<String>> receiveData(ResultSet rs, String... columnName) {
			
			List<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
			
			int numberOfColumns = columnName.length;
			try {
				@SuppressWarnings("unused") int i = 0;
				while (rs.next()) {
					ArrayList<String> row = new ArrayList<String>();
					for(int j = 0 ; j < numberOfColumns ; j++) {
						row.add(rs.getString(columnName[j]));
					}
					rows.add(row);
					i++;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(rs);
			}
			
			return rows;
		}
		
		private List<Exchange> receiveDataRevisited(ResultSet rs, String... columnName) {
			
			List<Exchange> rows = new ArrayList<Exchange>();
			
			byte datePos = 0;
			byte valPos = 1;
			
				try {
				while (rs.next()) {
					Date date = rs.getDate(columnName[datePos]);
					BigDecimal value = rs.getBigDecimal(columnName[valPos]).setScale(2, RoundingMode.HALF_EVEN);
					Object[] container = {date, value};
					Exchange row = new Exchange(container);
					rows.add(row);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(rs);
			}
			return rows;
		}
		
		private String selectString(String... columnNames) {
			String tableName = table.getTableName();
			
			StringBuilder sb = new StringBuilder("SELECT ");
			for(String item : columnNames) {
				sb.append(item).append(", ");
			}
			sb.delete(sb.length()-2, sb.length());
			sb.append(" FROM ").append(tableName).append(";");
			
			String selectString = sb.toString();
			return selectString;
		}
		
		private String selectWhereString(String select, String columnName) {
			StringBuilder sb = new StringBuilder(select);
			sb.deleteCharAt(sb.length()-1);
			
			sb.append(" WHERE ").append(columnName).append("= ?;");
			String selectString = sb.toString();
			return selectString;
			
		}
		
		private String selectAndOrderByString(String select, String columnName) {
			StringBuilder sb = new StringBuilder(select);
			sb.deleteCharAt(sb.length()-1);

			sb.append(" ORDER BY ").append(columnName).append(";");
			String selectString = sb.toString();
			return selectString;
		}
		
		public List<ArrayList<String>> select(String... columnName) throws IllegalArgumentException {
			
			List<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
			int numberOfColumns = table.getNumberOfColumns();
			
			if(columnName.length > numberOfColumns) {
				logger.error("Wrong amount of arguments for table: \"" + table.getTableName() + "\"!");
				throw new IllegalArgumentException("Wrong amount of arguments for table: \"" + table.getTableName() + "\"!");
			} else {
				
				String selectString = selectString(columnName);
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				
				try {
					pstmt = c.prepareStatement(selectString);
					rs = pstmt.executeQuery();
					data = receiveData(rs, columnName);
					logger.info("Select query on table: \"" + table.getTableName() + "\" performed successfully!");
				} catch (SQLException e) {
					e.printStackTrace();
					rollTransactionBack(c);
				} finally {
					close(pstmt);
					close(rs);
				}
			}
			return data;
		}
		
public List<Exchange> selectRevisited(String... columnName) throws IllegalArgumentException {
			
			List<Exchange> data = new ArrayList<Exchange>();
			int numberOfColumns = table.getNumberOfColumns();
			
			if(columnName.length > numberOfColumns) {
				logger.error("Wrong amount of arguments for table: \"" + table.getTableName() + "\"!");
				throw new IllegalArgumentException("Wrong amount of arguments for table: \"" + table.getTableName() + "\"!");
			} else {
				
				String selectString = selectString(columnName);
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				
				try {
					pstmt = c.prepareStatement(selectString);
					rs = pstmt.executeQuery();
					data = receiveDataRevisited(rs, columnName);
					logger.info("Select query on table: \"" + table.getTableName() + "\" performed successfully!");
				} catch (SQLException e) {
					e.printStackTrace();
					rollTransactionBack(c);
				} finally {
					close(pstmt);
					close(rs);
				}
			}
			return data;
		}

		
		public List<ArrayList<String>> selectWhereDateMatches(String startDate, String endDate) throws IllegalArgumentException {
			List<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String dateColumnName = table.getColumnNames()[0];
			
			String query = "SELECT * FROM " + table.getTableName() + " WHERE " + dateColumnName +" BETWEEN ? AND ?;";
			try {
				pstmt = c.prepareStatement(query);
				pstmt.setString(1, startDate);
				pstmt.setString(2, endDate);

				rs = pstmt.executeQuery();
				data = receiveData(rs, table.getColumnNames());
				logger.info("Select query on table: \"" + table.getTableName() + "\" performed successfully!");
			} catch (SQLException e) {
				e.printStackTrace();
				rollTransactionBack(c);
			} finally {
				close(pstmt);
				close(rs);
			}
		
		return data;
		}
		
		public List<Exchange> selectWhereDateMatchesRevisited(Date startDate, Date endDate) throws IllegalArgumentException {
			List<Exchange> data = new ArrayList<Exchange>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String dateColumnName = table.getColumnNames()[0];
			
			String query = "SELECT * FROM " + table.getTableName() + " WHERE " + dateColumnName +" BETWEEN ? AND ?;";
			try {
				pstmt = c.prepareStatement(query);
				pstmt.setDate(1, startDate);
				pstmt.setDate(2, endDate);

				rs = pstmt.executeQuery();
				data = receiveDataRevisited(rs, table.getColumnNames());
				logger.info("Select query on table: \"" + table.getTableName() + "\" performed successfully!");
			} catch (SQLException e) {
				e.printStackTrace();
				rollTransactionBack(c);
			} finally {
				close(pstmt);
				close(rs);
			}
		
		return data;
		}

		

		
}

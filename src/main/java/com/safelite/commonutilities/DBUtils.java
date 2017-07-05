package com.safelite.commonutilities;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Properties;

public class DBUtils {
	private static final Logger LOG = Logger.getLogger(DBUtils.class);
	private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";

	public static void executeSqlStmt(DataBaseConnections db, String sqlStmt, Object... params) throws SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		try {
			dbConnection = getConnection(db);
			dbConnection.setAutoCommit(false);
			preparedStatement = dbConnection.prepareStatement(sqlStmt);
			int cnt = 0;
			for (Object param : params) {
				preparedStatement.setObject(++cnt, param);
			}
			LOG.debug("[executeSqlStmt] = \n" + sqlStmt + "\n");
			LOG.debug("[executeSqlStmt params] = \n" + Arrays.toString(params) + "\n");

			/**
			 * uncomment below only if need debug sql is needed in ExtentReport
			 */
//			Markup m = MarkupHelper.createCodeBlock(sqlStmt);
//			String testMethod = Arrays.toString(Thread.currentThread().getStackTrace());
//			if (testMethod.contains("Class") || testMethod.contains("tearDown") || testMethod.contains("setup")) {
//				ExtentReportThread.getTest().debug(m);
//			} else {
//				ExtentReportThread.getChildTest().debug(m);
//			}
			/**
			 * uncomment above only if need debug sql is needed in ExtentReport
			 */

			preparedStatement.executeUpdate();
			dbConnection.commit();
			LOG.debug("[------DB Query is successfully executed.------]");
		} catch (SQLException e) {
			LOG.error(e.getMessage());
			dbConnection.rollback();
			LOG.debug("[------DB Connection is rolled back.------]");
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
				LOG.debug("[------SQL Statement is executed.------]");
			}
			if (dbConnection != null) {
				dbConnection.close();
				LOG.debug("[------DB Connection is closed.------]");
			}
		}
	}

	public static void select(DataBaseConnections db, String sqlStmt, DBResultSetProcessor processor, Object... params) {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		try {
			dbConnection = getConnection(db);
			preparedStatement = dbConnection.prepareStatement(sqlStmt);
			int cnt = 0;
			for (Object param : params) {
				preparedStatement.setObject(++cnt, param);
			}
			try {
				LOG.debug("selectSQL = \n" + sqlStmt + "\n");
				LOG.debug("[selectSQL params] = \n" + Arrays.toString(params) + "\n");

				/**
				 * uncomment below only if need debug sql is needed in ExtentReport
				 */
//				Markup m = MarkupHelper.createCodeBlock(sqlStmt);
//				String testMethod = Arrays.toString(Thread.currentThread().getStackTrace());
//				if (testMethod.contains("Class") || testMethod.contains("tearDown") || testMethod.contains("setup")) {
//					ExtentReportThread.getTest().debug(m);
//				} else {
//					ExtentReportThread.getChildTest().debug(m);
//				}
				/**
				 * uncomment above only if need debug sql is needed in ExtentReport
				 */

				ResultSet rs = preparedStatement.executeQuery();
				long rowCnt = 0;
				while (rs.next()) {
					processor.process(rs, rowCnt++);
				}
			} catch (SQLException e) {
				LOG.error(e.getMessage());
			}
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
					LOG.debug("[------SQL Statement is executed.------]");
				} catch (SQLException ex) {
					LOG.error(ex.getMessage());
				}
			}
			if (dbConnection != null) {
				try {
					dbConnection.close();
					LOG.debug("[------DB Connection is closed.------]");
				} catch (SQLException ex) {
					LOG.error(ex.getMessage());
				}
			}
		}
	}

	private static Connection getConnection(DataBaseConnections db) {
		String fileName = "db.properties";
		Properties properties = new Properties();
		Connection connection = null;
		InputStream is = null;
		String dbConnection;
		try {
			is = DBUtils.class.getClassLoader().getResourceAsStream(fileName);
			properties.load(is);
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
		String user = properties.getProperty(db.user());
		String password = properties.getProperty(db.password());
		String host = properties.getProperty(db.host());
		String port = properties.getProperty(db.port());
		String servicename = properties.getProperty(db.servicename());
		String sid = properties.getProperty(db.sid());
		if (sid == null || sid.isEmpty() || sid.equals(" ")) {
			dbConnection = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=" + host +
					")(PORT=" + port + "))(CONNECT_DATA=(SERVICE_NAME=" + servicename +
					")(SERVER=DEDICATED)(FAILOVER_MODE=(TYPE=SELECT)(METHOD=BASIC)(RETRIES=180)(DELAY=5))))";
		} else {
			dbConnection = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=" + host +
					")(PORT=" + port + "))(CONNECT_DATA=(SID=" + sid +
					")(SERVER=DEDICATED)(FAILOVER_MODE=(TYPE=SELECT)(METHOD=BASIC)(RETRIES=180)(DELAY=5))))";
		}
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			LOG.error(e.getMessage());
		}
		try {
			connection = DriverManager.getConnection(dbConnection, user, password);
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					LOG.error(e.getMessage());
				}
			}
		}
		return connection;
	}
	
	public static ArrayList<LinkedHashMap<String, Object>> selectValuesFromDB(DataBaseConnections db, String sqlStmt, Object... params) {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		 ArrayList<LinkedHashMap<String, Object>> queryResult = new ArrayList<LinkedHashMap<String, Object>>();
		try {
			dbConnection = getConnection(db);
			preparedStatement = dbConnection.prepareStatement(sqlStmt);
			int cnt = 0;
			for (Object param : params) {
				preparedStatement.setObject(++cnt, param);
			}
			try {
				LOG.debug("selectSQL = \n" + sqlStmt + "\n");
				LOG.debug("[selectSQL params] = \n" + Arrays.toString(params) + "\n");

				ResultSet rs = preparedStatement.executeQuery();
				
				ResultSetMetaData rsmd = rs.getMetaData();
				  int columncount = rsmd.getColumnCount();
				  LinkedHashMap<String, Object> row;
				  while (rs.next()) {
					  row = new LinkedHashMap<String, Object>();
				   for (int i = 1; i <= columncount; i++) {
					   row.put(rsmd.getColumnName(i), rs.getObject(i));
					   LOG.info("Column name: "+rsmd.getColumnName(i));
					   LOG.info("Row value: "+rs.getObject(i));
				   }
				   queryResult.add(row);
				  }
			} catch (SQLException e) {
				LOG.error(e.getMessage());
			}
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
					LOG.debug("[------SQL Statement is executed.------]");
				} catch (SQLException ex) {
					LOG.error(ex.getMessage());
				}
			}
			if (dbConnection != null) {
				try {
					dbConnection.close();
					LOG.debug("[------DB Connection is closed.------]");
				} catch (SQLException ex) {
					LOG.error(ex.getMessage());
				}
			}
		}
		 return queryResult;
	}
	
	public static ArrayList<String> select(DataBaseConnections db, String sqlStmt,String columnName, int rowsSizeFromApplication, Object... params) {
		ArrayList<String> queryData = new ArrayList<>();
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		try {
			dbConnection = getConnection(db);
			preparedStatement = dbConnection.prepareStatement(sqlStmt);
			int cnt = 0;
			for (Object param : params) {
				preparedStatement.setObject(++cnt, param);
			}
			try {
				LOG.debug("selectSQL = \n" + sqlStmt + "\n");
				LOG.debug("[selectSQL params] = \n" + Arrays.toString(params) + "\n");

				/**
				 * uncomment below only if need debug sql is needed in ExtentReport
				 */
//				Markup m = MarkupHelper.createCodeBlock(sqlStmt);
//				String testMethod = Arrays.toString(Thread.currentThread().getStackTrace());
//				if (testMethod.contains("Class") || testMethod.contains("tearDown") || testMethod.contains("setup")) {
//					ExtentReportThread.getTest().debug(m);
//				} else {
//					ExtentReportThread.getChildTest().debug(m);
//				}
				/**
				 * uncomment above only if need debug sql is needed in ExtentReport
				 */

				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					// Retrieve by column name
					String data = rs.getString(columnName);
					queryData.add(data);
					if (queryData.size() == rowsSizeFromApplication) {
						break;
					}
				}
			} catch (SQLException e) {
				LOG.error(e.getMessage());
			}
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
					LOG.debug("[------SQL Statement is executed.------]");
				} catch (SQLException ex) {
					LOG.error(ex.getMessage());
				}
			}
			if (dbConnection != null) {
				try {
					dbConnection.close();
					LOG.debug("[------DB Connection is closed.------]");
				} catch (SQLException ex) {
					LOG.error(ex.getMessage());
				}
			}
		}
		return queryData;
	}
}
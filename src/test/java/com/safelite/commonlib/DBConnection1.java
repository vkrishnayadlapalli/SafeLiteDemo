package com.safelite.commonlib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.ibatis.jdbc.ScriptRunner;

public class DBConnection1 {
	private static String driverName = "oracle.jdbc.driver.OracleDriver";
	private static String dbURL = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(Host=192.168.5.159)(Port=1521)))(CONNECT_DATA=(SERVICE_NAME=margqa_2_2)))";
	/*private static String strUserID = "im_owner";
	private static String strPassword = "margqa_2_2";*/
	private static String strUserID = "web_user";
	private static String strPassword = "margqa_2_2";
	private static Connection connection;

	/**
	 * Description: Run the query for getting date for location datesQuery
	 *
	 * @throws SQLException
	 * @throws IOException
	 */
	public static Connection getConnection(String Value) throws SQLException, IOException {
		
			try {

				Class.forName(driverName);
				try {
					String sqlPath = System.getProperty("user.dir") + "/resources/sqlFiles/" + Value;
					connection = DriverManager.getConnection(dbURL,
							strUserID, strPassword);
					// Initialize object for ScripRunner
					ScriptRunner sr = new ScriptRunner(connection);

					// Give the input file to Reader
					Reader reader = new BufferedReader(new FileReader(sqlPath));

					sr.setDelimiter("/"); // Delimiter the file with '/' char
					sr.runScript(reader); // run the script
					connection.close(); // close the connection
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		
		return connection;
	}
	
	public static void querforWeekendDates() throws SQLException, IOException{
		for (int i = 0; i <= 2; i++) {
			String Value = " ";
			if (i == 0) {
				Value = "deleteTwxLaborScheduleDataRESTAURANT 00010.sql";
			}
			if (i == 1) {
				Value = "deleteTwxLaborScheduleDataTWX001.sql";
			}
			if (i == 2) {
				Value = "deleteTwxLaborScheduleDataTWX002.sql";
			}
			getConnection(Value);
	}
	}

	public static void querforSavedraftMarkasReadyButtons() throws SQLException, IOException{
		for (int i = 0; i <= 2; i++) {
			String Value = " ";
			if (i == 0) {
				Value = "deleteOpenShiftsRESTAURANT 00010.sql";
			}
			if (i == 1) {
				Value = "deleteOpenShiftsTWX001.sql";
			}
			if (i == 2) {
				Value = "deleteOpenShiftsTWX002.sql";
			}
			getConnection(Value);
	}
	}
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		querforWeekendDates();
		querforSavedraftMarkasReadyButtons();
	}
	
	public static Connection getJDBCConncetion() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection(dbURL, "im_owner", "margqa_2_2");
		return connection;
	}
	
	public static ArrayList<String> getQueryResult(String SQLScriptFilePath, String columnName, int rowsSizeFromApplication) throws IOException, ClassNotFoundException, SQLException {
		Statement stmt = null;
		ArrayList<String> queryData = new ArrayList<String>();
		try {
			String s;
			StringBuffer sb = new StringBuffer();
			FileReader fr = new FileReader(new File(SQLScriptFilePath));
			BufferedReader br = new BufferedReader(fr);
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
			br.close();
			
			String[] inst = sb.toString().split(";");
			System.out.println("Query is: " + inst[0]);
			connection = getJDBCConncetion();
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(inst[0]);
			while (rs.next()) {
				// Retrieve by column name
				String data = rs.getString(columnName);
				queryData.add(data);
				if(queryData.size()==rowsSizeFromApplication){
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return queryData;
	}
}

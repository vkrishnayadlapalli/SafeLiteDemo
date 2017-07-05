package com.safelite.commonutilities;

import org.apache.log4j.Logger;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DataBaseAccessMethods {
	private static final Logger LOG = Logger.getLogger(DataBaseAccessMethods.class);
	private static final String ROOT = System.getProperty("user.dir");
	private static final String SEPARATOR = File.separator;
	private static final String CES_SQL_ROOT = ROOT + SEPARATOR + "resources" + SEPARATOR + "sqlFiles" + SEPARATOR + "ces";
	private static final String TWX_SQL_ROOT = ROOT + SEPARATOR + "resources" + SEPARATOR + "sqlFiles" + SEPARATOR + "twx";

	/**
	 * fileToString
	 * @Description This method is used to read content from a file and store them to a String
	 * @author Cigniti Technologies 
	 * @return str
	 * @throws Throwable the throwable
	 * @LastModifiedDate 02 Aug 2016
	 */
	private static String fileToString(String filePath) {
		String str = null;
		try {
			//str variable stores the values retrived from the data base.
			str = new String(Files.readAllBytes(Paths.get(filePath)), Charset.forName("UTF-8"));
		} catch (Exception ex) {
			LOG.debug(ex.getMessage());
		}
		return str;
	}
	
	/**
	 * utility method to decode site_name
	 * @Description This method is used to get DB based on the site name
	 * @author Cigniti Technologies 
	 * @return the DB based on site_name
	 * @throws Throwable the throwable
	 * @LastModifiedDate 02 Aug 2016
	 */
	private static DataBaseConnections getDb(String siteName) {
		DataBaseConnections db;
		//set at job and get system property to find db if not null
		switch (siteName.toLowerCase()) {
			case "margqa-2-5":
				db = DataBaseConnections.MARGQA_2_5;
				break;
			case "margqa-2-2":
				db = DataBaseConnections.MARGQA_2_2;
				break;
			case "fran001-margqa-2-2":
				db = DataBaseConnections.FRAN001MARGQA_2_2;
				break;
			case "fran002-margqa-2-2":
				db = DataBaseConnections.FRAN002MARGQA_2_2;
				break;
			case "aqvd00301-em":
				db = DataBaseConnections.MARGQA_2_5;
				break;
			case "aqvd00301-nc":
				db = DataBaseConnections.MARGQA_2_5;
				break;
			default:
				db = DataBaseConnections.MARGQA_2_2;
				break;
		}
		return db;
	}

	/**
	 * CES common method to update user group control access for CES app
	 *
	 * @param siteName      is the site_name of the DB
	 * @param accessAllowed Y/N for access_allowed col
	 * @param screen        is the screen name
	 * @param control       is the control name
	 * @param userGroup     is the user group name
	 */
	public static void updateCesUsrGrpAccess(String siteName, Object accessAllowed, Object screen, Object control, Object userGroup) {
		String sqlFilePath = CES_SQL_ROOT + SEPARATOR + "common" + SEPARATOR + "update_user_group_access.sql";
		String sql = fileToString(sqlFilePath);
		try {
			DBUtils.executeSqlStmt(getDb(siteName), sql, accessAllowed, screen, control, userGroup);
		} catch (Exception ex) {
			LOG.debug(ex.getMessage());
		}
	}

	/**
	 * CES app select checkbox status (Y/N) on EMW >> Labor >> TeamworX Corporate Controls screen
	 *
	 * @param siteName    is the site_name of the DB
	 * @param controlName is the checkbox control label
	 * @return the checkbox status (Y/N)
	 */
	public static String selectValueFromCepApplicationPreferenceByName(String siteName, String controlName) {
		String sqlFilePath = CES_SQL_ROOT + SEPARATOR + "emw" + SEPARATOR + "labor" + SEPARATOR + "select_twx_corp_ctrl_value_by_name.sql";
		String sql = fileToString(sqlFilePath);
		final String[] value = new String[1];
		DBUtils.select(getDb(siteName), sql, (ResultSet rs, long cnt) -> {
			value[0] = rs.getString("default_value");
		}, controlName);
		return value[0];
	}

	/**
	 * CES >> Showroom app select row record column value by product_name
	 *
	 * @param siteName is the site_name of the DB
	 * @param colName  is the column_name of T_SHOWROOM_HDR table
	 * @param prodName is the product_name of the testing row record in T_SHOWROOM_HDR table
	 * @return the value of given column_name
	 */
	public static String selectShowroomRowColValueByProductName(String siteName, String colName, String prodName) {
		String sqlFilePath = CES_SQL_ROOT + SEPARATOR + "showroom" + SEPARATOR + "common" + SEPARATOR + "select_showroom_row_by_product_name.sql";
		String sql = fileToString(sqlFilePath);
		final String[] value = new String[1];
		DBUtils.select(getDb(siteName), sql, (ResultSet rs, long cnt) -> {
			//it's a generic getString to treat all DB data type as a string for testing purpose
			value[0] = rs.getString(colName);
		}, prodName);
		return value[0];
	}

	/**
	 * CES app to use insert stmt to stage data for NC >> Labor >> Reports >> Completed Shift Awaiting Acknowledgement
	 *
	 * @param siteName is the site_name of the DB
	 */
	public static void insertStageEmpAckLaborRecordsInCes(String siteName) {
		String sqlFilePath = CES_SQL_ROOT + SEPARATOR + "nc" + SEPARATOR + "labor" + SEPARATOR + "completedShiftsAwaitingAcknowledgement" + SEPARATOR + "staging.sql";
		String sql = fileToString(sqlFilePath);
		try {
			DBUtils.executeSqlStmt(getDb(siteName), sql);
		} catch (SQLException e) {
			LOG.debug(e.getMessage());
		}
	}

	/**
	 * CES app to use insert stmt to stage data for NC >> Labor >> Reports >> Completed Shift Awaiting Acknowledgement
	 *
	 * @param siteName is the site_name of the DB
	 */
	public static void shiftAcknowledgementTruncateSetupAndTearDown(String siteName) {
		String sqlFilePath = CES_SQL_ROOT + SEPARATOR + "nc" + SEPARATOR + "labor" + SEPARATOR + "completedShiftsAwaitingAcknowledgement" + SEPARATOR + "setup_and_teardown.sql";
		String sql = fileToString(sqlFilePath);
		try {
			DBUtils.executeSqlStmt(getDb(siteName), sql);
		} catch (SQLException e) {
			LOG.debug(e.getMessage());
		}
	}

	/**
	 * CES app to use insert stmt to stage data for NC >> Labor >> Reports >> Completed Shift Awaiting Acknowledgement
	 *
	 * @param siteName is the site_name of the DB
	 */
	public static void insertFranchisorEmpAckLaborRecordsInCes(String siteName) {
		String sqlFilePath = CES_SQL_ROOT + SEPARATOR + "nc" + SEPARATOR + "labor" + SEPARATOR + "completedShiftsAwaitingAcknowledgement" + SEPARATOR + "franchisor_emp_ack_staging.sql";
		String sql = fileToString(sqlFilePath);
		try {
			DBUtils.executeSqlStmt(getDb(siteName), sql);
		} catch (SQLException e) {
			LOG.debug(e.getMessage());
		}
	}

	/**
	 * CES app to use insert stmt to stage data for NC >> Labor >> Reports >> Completed Shift Awaiting Acknowledgement
	 *
	 * @param siteName is the site_name of the DB
	 */
	public static void insertFranchiseeEmpAckLaborRecordsInCes(String siteName) {
		String sqlFilePath = CES_SQL_ROOT + SEPARATOR + "nc" + SEPARATOR + "labor" + SEPARATOR + "completedShiftsAwaitingAcknowledgement" + SEPARATOR + "franchisee_emp_ack_staging.sql";
		String sql = fileToString(sqlFilePath);
		try {
			DBUtils.executeSqlStmt(getDb(siteName), sql);
		} catch (SQLException e) {
			LOG.debug(e.getMessage());
		}
	}

	/**
	 * CES >> Showroom app to use delete product in grid with add edit row
	 *
	 * @param siteName is the site_name of the DB
	 */
	public static void deleteCesShowroomRowByProductName(String siteName, String productName) {
		String sqlFilePath = CES_SQL_ROOT + SEPARATOR + "showroom" + SEPARATOR + "common" + SEPARATOR + "delete_showroom_row_by_product_name.sql";
		String sql = fileToString(sqlFilePath);
		try {
			DBUtils.executeSqlStmt(getDb(siteName), sql, productName);
		} catch (SQLException e) {
			LOG.debug(e.getMessage());
		}
	}

	/**
	 * CES app to use delete product in grid with add edit row
	 *
	 * @param siteName is the site_name of the DB
	 */
	public static void setupCorporateDateNumberFormat(String siteName, String decimalCharacter, String dateFormat) {
		String sqlFilePath = CES_SQL_ROOT + SEPARATOR + "showroom" + SEPARATOR + "common" + SEPARATOR + "setup_corporate_date_number_format.sql";
		String sql = fileToString(sqlFilePath);
		try {
			DBUtils.executeSqlStmt(getDb(siteName), sql, decimalCharacter, dateFormat);
		} catch (SQLException e) {
			LOG.debug(e.getMessage());
		}
	}

	/**
	 * CES app to use get data from DB
	 *
	 * @param siteName is the site_name of the DB
	 */
	public static ArrayList<LinkedHashMap<String, Object>> getDataFromDBForEMWMonthlyLocation(String siteName, String month, String userId, String logicalName) {
		String sqlFilePath = CES_SQL_ROOT + SEPARATOR + "emw" + SEPARATOR + "productSuite" + SEPARATOR + "monthly_location.sql";
		String sql = fileToString(sqlFilePath);
		ArrayList<LinkedHashMap<String, Object>> queryResult = new ArrayList<LinkedHashMap<String, Object>>();
		queryResult = DBUtils.selectValuesFromDB(getDb(siteName), sql, month, userId, logicalName);
		return queryResult;
	}

	/**
	 * CES app to use get data from DB
	 *
	 * @param siteName is the site_name of the DB
	 */
	public static ArrayList<LinkedHashMap<String, Object>> getDataFromDBForEMWMonthlyApplication(String siteName, String month, String userId, String logicalName) {
		String sqlFilePath = CES_SQL_ROOT + SEPARATOR + "emw" + SEPARATOR + "productSuite" + SEPARATOR + "monthly_application.sql";
		String sql = fileToString(sqlFilePath);
		ArrayList<LinkedHashMap<String, Object>> queryResult = new ArrayList<LinkedHashMap<String, Object>>();
		queryResult = DBUtils.selectValuesFromDB(getDb(siteName), sql, month, userId, logicalName);
		return queryResult;
	}

	/**
	 * CES app to use get data from DB
	 *
	 * @param siteName is the site_name of the DB
	 */
	public static ArrayList<LinkedHashMap<String, Object>> getDataFromDBForEMWSummaryLocation(String siteName, String monthFrom, String monthTo, String userId, String logicalName) {
		String sqlFilePath = CES_SQL_ROOT + SEPARATOR + "emw" + SEPARATOR + "productSuite" + SEPARATOR + "summary_location.sql";
		String sql = fileToString(sqlFilePath);
		ArrayList<LinkedHashMap<String, Object>> queryResult = new ArrayList<LinkedHashMap<String, Object>>();
		queryResult = DBUtils.selectValuesFromDB(getDb(siteName), sql, monthFrom, monthTo, userId, logicalName);
		return queryResult;
	}

	/**
	 * CES app to use get data from DB
	 *
	 * @param siteName is the site_name of the DB
	 */
	public static ArrayList<LinkedHashMap<String, Object>> getDataFromDBForEMWSummaryApplication(String siteName, String monthFrom, String monthTo, String userId, String logicalName) {
		String sqlFilePath = CES_SQL_ROOT + SEPARATOR + "emw" + SEPARATOR + "productSuite" + SEPARATOR + "summary_application.sql";
		String sql = fileToString(sqlFilePath);
		ArrayList<LinkedHashMap<String, Object>> queryResult = new ArrayList<LinkedHashMap<String, Object>>();
		queryResult = DBUtils.selectValuesFromDB(getDb(siteName), sql, monthFrom, monthTo, userId, logicalName);
		return queryResult;
	}

	/**
	 * CES >> Showroom app to use select values from a column
	 *
	 * @param siteName is the site_name of the DB
	 * @param colSizeByApp is size of rows from application
	 */
//	public static ArrayList<String> selectCesShowroomByAscendingPriceColumnName(String siteName, String columnName, int rowsSizeFromApplication) {
//		ArrayList<String> queryData = new ArrayList<>();
//		String sqlFilePath = CES_SQL_ROOT + SEPARATOR + "showroom" + SEPARATOR + "samples" + SEPARATOR + "bigDataSimpleGrid" + SEPARATOR + "gridSortByNumAscGrpSortByMkt.sql";
//		String sql = fileToString(sqlFilePath);
//		try {
//			queryData = DBUtils.select(getDb(siteName), sql, columnName, rowsSizeFromApplication);
//		} catch (Exception e) {
//			LOG.debug(e.getMessage());
//		}
//		return queryData;
//	}
	public static ArrayList<String> selectCesShowroomByAscendingPriceColumnName(String siteName, long colSizeByApp) {
		String sqlFilePath = CES_SQL_ROOT + SEPARATOR + "showroom" + SEPARATOR + "samples" + SEPARATOR + "bigDataSimpleGrid" + SEPARATOR + "gridSortByNumAscGrpSortByMkt.sql";
		String sql = fileToString(sqlFilePath);
		ArrayList<String> list = new ArrayList<>();
		DBUtils.select(getDb(siteName), sql, (ResultSet rs, long cnt) -> {
			if (cnt < colSizeByApp) {
				list.add(rs.getString("price"));

			}
		});
		return list;
	}

	/**
	 * CES >> Showroom app to use select values from a column
	 *
	 * @param siteName                is the site_name of the DB
	 * @param columnName              is name of the column in DB
	 * @param rowsSizeFromApplication is size of rows from application
	 */
	public static ArrayList<String> selectCesShowroomByDescendingPriceColumnName(String siteName, String columnName, int rowsSizeFromApplication) {
		ArrayList<String> queryData = new ArrayList<>();
		String sqlFilePath = CES_SQL_ROOT + SEPARATOR + "showroom" + SEPARATOR + "samples" + SEPARATOR + "bigDataSimpleGrid" + SEPARATOR + "gridSortByNumDescGrpSortByMkt.sql";
		String sql = fileToString(sqlFilePath);
		try {
			queryData = DBUtils.select(getDb(siteName), sql, columnName, rowsSizeFromApplication);
		} catch (Exception e) {
			LOG.debug(e.getMessage());
		}
		return queryData;
	}

	/**
	 * CES >> Showroom app to use select values from a column
	 *
	 * @param siteName                is the site_name of the DB
	 * @param columnName              is name of the column in DB
	 * @param rowsSizeFromApplication is size of rows from application
	 */
	public static ArrayList<String> selectCesShowroomByAscendingDateColumnName(String siteName, String columnName, int rowsSizeFromApplication) {
		ArrayList<String> queryData = new ArrayList<>();
		String sqlFilePath = CES_SQL_ROOT + SEPARATOR + "showroom" + SEPARATOR + "samples" + SEPARATOR + "bigDataSimpleGrid" + SEPARATOR + "gridSortByDateAscGrpSortByMkt.sql";
		String sql = fileToString(sqlFilePath);
		try {
			queryData = DBUtils.select(getDb(siteName), sql, columnName, rowsSizeFromApplication);
		} catch (Exception e) {
			LOG.debug(e.getMessage());
		}
		return queryData;
	}

	/**
	 * CES >> Showroom app to use select values from a column
	 *
	 * @param siteName                is the site_name of the DB
	 * @param columnName              is name of the column in DB
	 * @param rowsSizeFromApplication is size of rows from application
	 */
	public static ArrayList<String> selectCesShowroomByDescendingDateColumnName(String siteName, String columnName, int rowsSizeFromApplication) {
		ArrayList<String> queryData = new ArrayList<>();
		String sqlFilePath = CES_SQL_ROOT + SEPARATOR + "showroom" + SEPARATOR + "samples" + SEPARATOR + "bigDataSimpleGrid" + SEPARATOR + "gridSortByDateDescGrpSortByMkt.sql";
		String sql = fileToString(sqlFilePath);
		try {
			queryData = DBUtils.select(getDb(siteName), sql, columnName, rowsSizeFromApplication);
		} catch (Exception e) {
			LOG.debug(e.getMessage());
		}
		return queryData;
	}

	/**
	 * CES >> Showroom app to use select values from a column
	 *
	 * @param siteName                is the site_name of the DB
	 * @param columnName              is name of the column in DB
	 * @param rowsSizeFromApplication is size of rows from application
	 */
	public static ArrayList<String> selectCesShowroomByDescendingDescriptionColumnName(String siteName, String columnName, int rowsSizeFromApplication) {
		ArrayList<String> queryData = new ArrayList<>();
		String sqlFilePath = CES_SQL_ROOT + SEPARATOR + "showroom" + SEPARATOR + "samples" + SEPARATOR + "bigDataSimpleGrid" + SEPARATOR + "gridSortByDescGrpSortByMkt.sql";
		String sql = fileToString(sqlFilePath);
		try {
			queryData = DBUtils.select(getDb(siteName), sql, columnName, rowsSizeFromApplication);
		} catch (Exception e) {
			LOG.debug(e.getMessage());
		}
		return queryData;
	}

	/**
	 * CES >> Showroom app to use select values from a column
	 *
	 * @param siteName                is the site_name of the DB
	 * @param columnName              is name of the column in DB
	 * @param rowsSizeFromApplication is size of rows from application
	 */
	public static ArrayList<String> selectCesShowroomByAscendingDescriptionColumnName(String siteName, String columnName, int rowsSizeFromApplication) {
		ArrayList<String> queryData = new ArrayList<>();
		String sqlFilePath = CES_SQL_ROOT + SEPARATOR + "showroom" + SEPARATOR + "samples" + SEPARATOR + "bigDataSimpleGrid" + SEPARATOR + "gridSortByDescAscGrpSortByMkt.sql";
		String sql = fileToString(sqlFilePath);
		try {
			queryData = DBUtils.select(getDb(siteName), sql, columnName, rowsSizeFromApplication);
		} catch (Exception e) {
			LOG.debug(e.getMessage());
		}
		return queryData;
	}

	/**
	 * CES >> Showroom app to use select values from a column
	 *
	 * @param siteName                is the site_name of the DB
	 * @param columnName              is name of the column in DB
	 * @param rowsSizeFromApplication is size of rows from application
	 */
	public static ArrayList<String> selectCesShowroomFilterByDescription(String siteName, String columnName, int rowsSizeFromApplication) {
		ArrayList<String> queryData = new ArrayList<>();
		String sqlFilePath = CES_SQL_ROOT + SEPARATOR + "showroom" + SEPARATOR + "samples" + SEPARATOR + "bigDataSimpleGrid" + SEPARATOR + "filterByDescription.sql";
		String sql = fileToString(sqlFilePath);
		try {
			queryData = DBUtils.select(getDb(siteName), sql, columnName, rowsSizeFromApplication);
		} catch (Exception e) {
			LOG.debug(e.getMessage());
		}
		return queryData;
	}

	/**
	 * CES >> Showroom app to use select values from a column
	 *
	 * @param siteName                is the site_name of the DB
	 * @param columnName              is name of the column in DB
	 * @param rowsSizeFromApplication is size of rows from application
	 */
	public static ArrayList<String> selectCesShowroomFilterByNumber(String siteName, String columnName, int rowsSizeFromApplication) {
		ArrayList<String> queryData = new ArrayList<>();
		String sqlFilePath = CES_SQL_ROOT + SEPARATOR + "showroom" + SEPARATOR + "samples" + SEPARATOR + "bigDataSimpleGrid" + SEPARATOR + "filterByNumber.sql";
		String sql = fileToString(sqlFilePath);
		try {
			queryData = DBUtils.select(getDb(siteName), sql, columnName, rowsSizeFromApplication);
		} catch (Exception e) {
			LOG.debug(e.getMessage());
		}
		return queryData;
	}

	/**
	 * CES >> Showroom app to use select values from a column
	 *
	 * @param siteName                is the site_name of the DB
	 * @param columnName              is name of the column in DB
	 * @param rowsSizeFromApplication is size of rows from application
	 */
	public static ArrayList<String> selectCesShowroomFilterByDateGrtEqu(String siteName, String columnName, int rowsSizeFromApplication) {
		ArrayList<String> queryData = new ArrayList<>();
		String sqlFilePath = CES_SQL_ROOT + SEPARATOR + "showroom" + SEPARATOR + "samples" + SEPARATOR + "bigDataSimpleGrid" + SEPARATOR + "filterByDateGrtEquThan.sql";
		String sql = fileToString(sqlFilePath);
		try {
			queryData = DBUtils.select(getDb(siteName), sql, columnName, rowsSizeFromApplication);
		} catch (Exception e) {
			LOG.debug(e.getMessage());
		}
		return queryData;
	}

	/**
	 * CES >> Showroom app to use select values from a column
	 *
	 * @param siteName                is the site_name of the DB
	 * @param columnName              is name of the column in DB
	 * @param rowsSizeFromApplication is size of rows from application
	 */
	public static ArrayList<String> selectCesShowroomFilterByDateLssEqu(String siteName, String columnName, int rowsSizeFromApplication) {
		ArrayList<String> queryData = new ArrayList<>();
		String sqlFilePath = CES_SQL_ROOT + SEPARATOR + "showroom" + SEPARATOR + "samples" + SEPARATOR + "bigDataSimpleGrid" + SEPARATOR + "filterByDateLssEquThan.sql";
		String sql = fileToString(sqlFilePath);
		try {
			queryData = DBUtils.select(getDb(siteName), sql, columnName, rowsSizeFromApplication);
		} catch (Exception e) {
			LOG.debug(e.getMessage());
		}
		return queryData;
	}

	/**
	 * CES >> Showroom app to use select values from a column
	 *
	 * @param siteName                is the site_name of the DB
	 * @param columnName              is name of the column in DB
	 * @param rowsSizeFromApplication is size of rows from application
	 */
	public static ArrayList<String> selectCesShowroomFilterByDateBtw(String siteName, String columnName, int rowsSizeFromApplication) {
		ArrayList<String> queryData = new ArrayList<>();
		String sqlFilePath = CES_SQL_ROOT + SEPARATOR + "showroom" + SEPARATOR + "samples" + SEPARATOR + "bigDataSimpleGrid" + SEPARATOR + "filterByDateBtw.sql";
		String sql = fileToString(sqlFilePath);
		try {
			queryData = DBUtils.select(getDb(siteName), sql, columnName, rowsSizeFromApplication);
		} catch (Exception e) {
			LOG.debug(e.getMessage());
		}
		return queryData;
	}

	/**
	 * CES >> Showroom app to use select values from a column
	 *
	 * @param siteName                is the site_name of the DB
	 * @param columnName              is name of the column in DB
	 * @param rowsSizeFromApplication is size of rows from application
	 */
	public static ArrayList<String> selectCesShowroomFilterByDateFormatBtw(String siteName, String columnName, int rowsSizeFromApplication) {
		ArrayList<String> queryData = new ArrayList<>();
		String sqlFilePath = CES_SQL_ROOT + SEPARATOR + "showroom" + SEPARATOR + "samples" + SEPARATOR + "bigDataSimpleGrid" + SEPARATOR + "filterByDateFormatBtw.sql";
		String sql = fileToString(sqlFilePath);
		try {
			queryData = DBUtils.select(getDb(siteName), sql, columnName, rowsSizeFromApplication);
		} catch (Exception e) {
			LOG.debug(e.getMessage());
		}
		return queryData;
	}

	/**
	 * CES >> Showroom app to use select values from a column
	 *
	 * @param siteName                is the site_name of the DB
	 * @param columnName              is name of the column in DB
	 * @param rowsSizeFromApplication is size of rows from application
	 */
	public static ArrayList<String> selectCesShowroomFilterByPriceFormatBtw(String siteName, String columnName, int rowsSizeFromApplication) {
		ArrayList<String> queryData = new ArrayList<>();
		String sqlFilePath = CES_SQL_ROOT + SEPARATOR + "showroom" + SEPARATOR + "samples" + SEPARATOR + "bigDataSimpleGrid" + SEPARATOR + "filterByPriceFormatBtw.sql";
		String sql = fileToString(sqlFilePath);
		try {
			queryData = DBUtils.select(getDb(siteName), sql, columnName, rowsSizeFromApplication);
		} catch (Exception e) {
			LOG.debug(e.getMessage());
		}
		return queryData;
	}
	
	/**
	 * CES >> Showroom app to use select values from a column
	 *
	 * @param siteName                is the site_name of the DB
	 * @param columnName              is name of the column in DB
	 * @param rowsSizeFromApplication is size of rows from application
	 */
	public static ArrayList<String> selectCesShowroomRetrieveBeforeStaging(String siteName, String columnName, int rowsSizeFromApplication) {
		ArrayList<String> queryData = new ArrayList<>();
		String sqlFilePath = CES_SQL_ROOT + SEPARATOR + "nc" + SEPARATOR + "labor" + SEPARATOR + "completedShiftsAwaitingAcknowledgement" + SEPARATOR + "retrieve_before_staging.sql";
		String sql = fileToString(sqlFilePath);	
		try {
			queryData = DBUtils.select(getDb(siteName), sql, columnName, rowsSizeFromApplication);
		} catch (Exception e) {
			LOG.debug(e.getMessage());
		}
		return queryData;
	}
	
	/**
	 * CES >> Showroom app to use select values from a column
	 *
	 * @param siteName                is the site_name of the DB
	 * param columnName              is name of the column in DB
	 * param rowsSizeFromApplication is size of rows from application
	 */
	public static ArrayList<String> selectCesShowroomTruncCepNotficationOutbox(String siteName) {
		ArrayList<String> queryData = new ArrayList<>();
		String sqlFilePath = CES_SQL_ROOT + SEPARATOR + "nc" + SEPARATOR + "labor" + SEPARATOR + "completedShiftsAwaitingAcknowledgement" + SEPARATOR + "trunc_cep_notfication_outbox.sql";
		String sql = fileToString(sqlFilePath);	
		try {
			DBUtils.executeSqlStmt(getDb(siteName), sql);
		} catch (Exception e) {
			LOG.debug(e.getMessage());
		}
		return queryData;
	}
	
	/**
	 * CES >> Showroom app to use select values from a column
	 *
	 * @param siteName                is the site_name of the DB
	 * @param columnName              is name of the column in DB
	 * @param rowsSizeFromApplication is size of rows from application
	 */
	public static ArrayList<String> selectCesShowroomSelectCepNotficationOutbox(String siteName, String columnName, int rowsSizeFromApplication, String RECIPIENT_ACCOUNT_ID) {
		ArrayList<String> queryData = new ArrayList<>();
		String sqlFilePath = CES_SQL_ROOT + SEPARATOR + "nc" + SEPARATOR + "labor" + SEPARATOR + "completedShiftsAwaitingAcknowledgement" + SEPARATOR + "select_cep_notfication_outbox.sql";
		String sql = fileToString(sqlFilePath);	
		try {
			queryData = DBUtils.select(getDb(siteName), sql, columnName, rowsSizeFromApplication, RECIPIENT_ACCOUNT_ID);
		} catch (Exception e) {
			LOG.debug(e.getMessage());
		}
		return queryData;
	}
	
	/**
	 * CES >> Showroom app to use select values from a column
	 *
	 * @param siteName                is the site_name of the DB
	 * @param columnName              is name of the column in DB
	 * @param rowsSizeFromApplication is size of rows from application
	 */
	public static ArrayList<String> selectCesShowroomRetrieveAfterStaging(String siteName, String columnName, int rowsSizeFromApplication) {
		ArrayList<String> queryData = new ArrayList<>();
		String sqlFilePath = CES_SQL_ROOT + SEPARATOR + "nc" + SEPARATOR + "labor" + SEPARATOR + "completedShiftsAwaitingAcknowledgement" + SEPARATOR + "retrieve_after_staging.sql";
		String sql = fileToString(sqlFilePath);	
		try {
			queryData = DBUtils.select(getDb(siteName), sql, columnName, rowsSizeFromApplication);
		} catch (Exception e) {
			LOG.debug(e.getMessage());
		}
		return queryData;
	}
	
	/**
	 * CES >> Showroom app to use select values from a column
	 *
	 * @param siteName                is the site_name of the DB
	 * param columnName              is name of the column in DB
	 * param rowsSizeFromApplication is size of rows from application
	 */
	public static ArrayList<String> selectCesShowroomStagingTeardown(String siteName) {
		ArrayList<String> queryData = new ArrayList<>();
		String sqlFilePath = CES_SQL_ROOT + SEPARATOR + "nc" + SEPARATOR + "labor" + SEPARATOR + "completedShiftsAwaitingAcknowledgement" + SEPARATOR + "staging_teardown.sql";
		String sql = fileToString(sqlFilePath);	
		try {
			DBUtils.executeSqlStmt(getDb(siteName), sql);
		} catch (Exception e) {
			LOG.debug(e.getMessage());
		}
		return queryData;
	}
}
package com.authority.common.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mysql {
	private static Logger log = LoggerFactory.getLogger(Mysql.class);
	private static Connection con = null;
	private static String mysqlUrl,user,password;
	static {
		try{
			Properties prop = new Properties();
			prop.load(Mysql.class.getResourceAsStream("/config/ibatis/jdbc.properties"));
			Class.forName(prop.getProperty("mysql.jdbc.driverClassName"));
			mysqlUrl = prop.getProperty("mysql.jdbc.url");
			user = prop.getProperty("mysql.jdbc.username");
			password = prop.getProperty("mysql.jdbc.password");
		}catch(Exception e){
			System.exit(1);
		}
	}


	/**
	 * Shut down a specified database. But it doesn't matter that later we could
	 * also connect to another database. Because the Derby engine is not closed.
	 * 
	 * @param databaseName
	 */
	public static void shutdownDatabase(String databaseName) {
		boolean gotSQLExc = false;
		try {
			DriverManager.getConnection(mysqlUrl + ";shutdown=true");
		} catch (SQLException se) {
			if (se.getSQLState().equals("08006")) {
				gotSQLExc = true;
			}
		}
		if (!gotSQLExc) {
			log.debug("Database did not shut down normally");
		} else {
			log.debug("Database: " + databaseName + " shut down normally");
		}
	}

	/**
	 * shut down all opened databases and close the Derby engine. The effect is
	 * that after the execution of this method, we will not permitted to use
	 * Derby again in the rest of our program. Or else, an exception of
	 * "can't find a suitable driver for [a database URL]" will be thrown.
	 * However, you can still use another approach to resolve this problem:
	 * newInstance() For example,
	 * 
	 * <pre>
	 * Class.forName(&quot;org.apache.derby.jdbc.EmbeddedDriver&quot;).newInstance();
	 * </pre>
	 */
	public static void shutdownAll() {
		boolean gotSQLExc = false;
		try {
			DriverManager.getConnection("jdbc:mysql:;shutdown=true");
		} catch (SQLException se) {
			if (se.getSQLState().equals("XJ015")) {
				gotSQLExc = true;
			}
		}
		if (!gotSQLExc) {
			log.debug("Database did not shut down normally");
		} else {
			log.debug("All databases shut down normally and Derby completely closed");
		}
	}

	/**
	 * Just connect to a database desired by providing the appropriate
	 * parameters.
	 * 
	 * @param databaseName
	 * @param user
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public synchronized static Connection getConnection() throws SQLException {
		con = DriverManager.getConnection(mysqlUrl, user, password);
		log.debug("Connection is sucessfully established,  " + mysqlUrl);
		return con;
	}

	public static HashSet<String> listAllTables(Connection con)
			throws SQLException {
		DatabaseMetaData meta = con.getMetaData();
		ResultSet res = meta.getTables(null, null, null,
				new String[] { "TABLE" });
		HashSet<String> set = new HashSet<String>();
		while (res.next()) {
			set.add(res.getString("TABLE_NAME").toUpperCase());
		}
		log.debug("All the tables associated to current connection are :");
		return set;
	}

	public static boolean isTableExists(String table, Connection con)
			throws SQLException {
		if (listAllTables(con).contains(table.toUpperCase())) {
			return true;
		} else {
			return false;
		}
	}

	public static HashSet<String> listAllSchemas(Connection con)
			throws SQLException {
		DatabaseMetaData meta = con.getMetaData();
		ResultSet res = meta.getSchemas(null, null);
		HashSet<String> set = new HashSet<String>();
		while (res.next()) {
			set.add(res.getString("TABLE_SCHEM"));
		}
		log.debug("All the schemas associated to current connection are :");
		return set;
	}

	public static HashMap<String, String> listAllSchemasAndTables(Connection con)
			throws SQLException {
		DatabaseMetaData meta = con.getMetaData();
		ResultSet res = meta.getTables(null, null, null,
				new String[] { "TABLE" });
		HashMap<String, String> map = new HashMap<String, String>();
		while (res.next()) {
			map.put(res.getString("TABLE_SCHEM"), res.getString("TABLE_NAME"));
		}
		log.debug("All the tables and their corresponding schemas associated to current connection are :");
		return map;
	}
}
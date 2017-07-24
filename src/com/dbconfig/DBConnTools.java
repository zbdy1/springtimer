package com.dbconfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DBConnTools {

	public static Connection myConn = null;
	public static Map<String, Object> dbIntconfig = new HashMap<String, Object>();
	static {
		dbIntconfig = DBProperties.dbIntconfig;
		connMysqlDB(dbIntconfig);
	}
	/**
	 * 
	 * 
	 * @Title:
	 * @param:
	 * @return:void
	 * @author: nani
	 * @createtime:2014-9-26����8:39:43
	 */
	public static void connMysqlDB(Map<String, Object> dbIntconfig) {
		String jdbcUrl = (String) dbIntconfig.get("mysql.jdbc.url");
		String driverClassName = (String) dbIntconfig
				.get("mysql.jdbc.driverClassName");
		String userName = (String) dbIntconfig.get("mysql.jdbc.username");
		String passwd = (String) dbIntconfig.get("mysql.jdbc.password");

		try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			System.out.println(jdbcUrl);
			System.out.println(userName);
			System.out.println(passwd);
			myConn = DriverManager.getConnection(jdbcUrl, userName, passwd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("==DBConnTools=105==连接失败");
			//e.printStackTrace();
		}
	}
	
}

package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Dbcontext {
	static final String URL_DB = "jdbc:mysql://localhost:3306/qltv";
	static final String USER = "root";
	static final String PASS = "";

	public static Connection getConnection() {
		Connection cnt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			cnt = DriverManager.getConnection(URL_DB, USER, PASS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cnt;
	}
	
}

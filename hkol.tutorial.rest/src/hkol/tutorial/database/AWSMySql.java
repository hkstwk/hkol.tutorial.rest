package hkol.tutorial.database;

import java.sql.Connection;

import javax.naming.*;
import javax.sql.*;

public class AWSMySql {
	private static DataSource MySqlRest = null;
	private static Context context = null;
    private static Context envContext = null;
	
	public static DataSource MySqlRestConn() throws Exception {
	
		context = new InitialContext();
		envContext = (Context) context.lookup("java:comp/env");
		
		if (MySqlRest != null){
			return MySqlRest;
		}
		
		try {				
				MySqlRest = (DataSource) envContext.lookup("jdbc/db");	
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return MySqlRest;
	}
	
	protected static Connection mysqlPersonsConnection(){
		Connection conn= null;
		try{
			conn = AWSMySql.MySqlRestConn().getConnection();
			return conn;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}

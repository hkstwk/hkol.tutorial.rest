package hkol.tutorial.rest.status;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.io.PrintWriter;
import java.sql.*;

import hkol.tutorial.database.*;

@Path("/v1/status/")
public class V1_status {
	
	private static final String api_version = "00.02.00";

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnTitle(){
		return "<p>Java REST Web Service</p>";
	}
	
	@Path("/version")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnVersion(){
		return "<p>Java REST Web Service version: " + api_version + "<p>";
	}
	
	@Path("/database")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnDatabaseStatus() throws Exception {
		PreparedStatement query = null;
		String myString = null;
		String returnString = "<p>database status, lege string</p>";
		Connection conn = null;
		
		try {
			conn = AWSMySql.MySqlRestConn().getConnection();
			query = conn.prepareStatement("select * from Persons;");
			ResultSet rs = query.executeQuery();
			
			int count = 1;
            while (rs.next()) {
               myString = (String.format("User #%d: %-15s %s", count++,
                        rs.getString("FirstName"), rs.getString("LastName")));
                 
            }
			query.close();
			returnString = "<p>Database Status -> Date/Time return: " + myString + "</p>";
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally {
			if (conn != null) conn.close();
		}
		
		return returnString;
	}
	
}

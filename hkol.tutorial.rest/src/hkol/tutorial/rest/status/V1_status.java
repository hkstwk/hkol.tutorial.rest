package hkol.tutorial.rest.status;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.sql.*;

import hkol.tutorial.database.*;

@Path("/v1/status/")
public class V1_status {
	
	private static final String api_version = "00.02.00";
	private static Integer count = 0;

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
		String myString = "";
		String returnString = "<p>database status, lege string</p>";
		Connection conn = null;
		
		try {
			conn = AWSMySql.MySqlRestConn().getConnection();
			query = conn.prepareStatement("select * from Persons;");
			ResultSet rs = query.executeQuery();
			
            while (rs.next()) {
               myString += rs.getString("FirstName") + " " + rs.getString("LastName") + "<br>";
            }
			query.close();
			returnString = "<p>" + count++ + "</p><p>Contents of table Persons:</p><p>" + myString + "</p>";
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

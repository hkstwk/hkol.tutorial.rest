package hkol.tutorial.rest.persons;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;

import hkol.tutorial.database.AWSMySql;
import hkol.tutorial.util.ToJSON;

@Path("/v1/persons")
public class V1_persons {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnAllPersons() throws Exception {
		
		PreparedStatement query = null;
		String returnString = null;
		Connection conn = null;
		Response response = null;
		
		try {
			conn = AWSMySql.MySqlRestConn().getConnection();
			query = conn.prepareStatement("select * from Persons");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();
			
			returnString = json.toString();
			response = Response.ok(returnString).build();
            
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally {
			if (conn != null) conn.close();
		}
		
		return response;
	}
}

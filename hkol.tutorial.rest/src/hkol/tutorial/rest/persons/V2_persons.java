package hkol.tutorial.rest.persons;

import javax.ws.rs.Path;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;

import hkol.tutorial.database.AWSMySql;
import hkol.tutorial.util.ToJSON;

@Path("/v2/persons")
public class V2_persons {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnPersons(
					@QueryParam("City") String city) 
					throws Exception {
		
		
		String returnString = null;
		JSONArray json = new JSONArray();
		
		Connection conn = null;
		PreparedStatement query = null;
		
		try {
			conn = AWSMySql.MySqlRestConn().getConnection();
			query = conn.prepareStatement("select * from Persons");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			
			
			json = converter.toJSONArray(rs);
			query.close();
			
			returnString = json.toString();
	
            
		}
		catch (Exception e){
			e.printStackTrace();
			return Response.status(500).entity("Server not able to proces request").build();
		}
		finally {
			if (conn != null) conn.close();
		}
		
		return Response.ok(returnString).build();
	}
}

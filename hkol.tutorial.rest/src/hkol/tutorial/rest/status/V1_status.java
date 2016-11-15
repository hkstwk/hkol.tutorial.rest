package hkol.tutorial.rest.status;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import hkol.tutorial.database.DBConnector;

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
	public Response returnDatabaseStatus() throws Exception {
		MongoClient mongoClient = null;
		MongoDatabase db = null;
		Response response = Response.ok("Hi there").build();
		String result = null;
		
		try {
				mongoClient = DBConnector.getMongoClient("localhost", 27017);
				db = DBConnector.getMongoDatabase(mongoClient, "test");
				
				result = db.getName();
				response = Response.ok(result.toString()).build();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally {
			if (mongoClient != null) mongoClient.close();
		}
		
		return response;
	}
	
}

package hkol.tutorial.rest.persons;

import static com.mongodb.client.model.Filters.eq;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

import hkol.tutorial.database.DBConnector;

@Path("/v1/persons")
public class V1_persons {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnAllPersons() throws Exception {
		
		MongoClient mongoClient = null;
		MongoDatabase db = null;
		
		FindIterable<Document> result = null;
		Response response = Response.ok("Hi there").build();
		
		try {
			mongoClient = DBConnector.getMongoClient("localhost", 27017);
			db = DBConnector.getMongoDatabase(mongoClient, "test");
			
			result = db.getCollection("restaurants").find(eq("borough", "Manhattan"));
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

package hkol.tutorial.rest.persons;

import static com.mongodb.client.model.Filters.eq;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
		
		Response response = Response.ok("Hi there this is GET").build();
		FindIterable<Document> iterable = null;
		String result = "";
		
		try {
			mongoClient = DBConnector.getMongoClient("localhost", 27017);
			db = DBConnector.getMongoDatabase(mongoClient, "test");
			
			iterable = db.getCollection("restaurants").find(eq("borough", "Manhattan")).limit(100);
			
			for (Document document : iterable) {
			    result += document.toJson();
			}

			
			response = Response.ok(result).build();
            
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally {
			if (mongoClient != null) mongoClient.close();
		}
		
		return response;
	}
	
	@POST
	@Path("/new")
	@Consumes("application/x-www-form-urlencoded")
	public Response addPerson(@FormParam("email")String email, @FormParam("pwd")String pwd) throws Exception {
		MongoClient mongoClient = null;
		MongoDatabase db = null;
		
		System.out.println(email);
		
		Response response = Response.ok("Hi there this is post").build();
		String result = "POST: ik heb een record toegevoegd";
		
		try {
			mongoClient = DBConnector.getMongoClient("localhost", 27017);
			db = DBConnector.getMongoDatabase(mongoClient, "test");
			
			db.getCollection("persons").insertOne(new Document("fname", email).append("lname", pwd));
						
			return Response.ok(result).build();
            
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

package hkol.tutorial.tracker;

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

@Path("/v1/tickets")
public class V1_tickets {
	
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
			
			iterable = db.getCollection("tickets").find().limit(100);
			
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
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response addPerson(@FormParam("date")String date, @FormParam("hours")String hours) throws Exception {
		MongoClient mongoClient = null;
		MongoDatabase db = null;
		
		System.out.println("hallo daar! De parameters zijn: " + date + " : " + hours);
		
		Response response = Response.ok("Hi there this is post").build();
		String result = "POST: ik heb een record toegevoegd";
		
		try {
			mongoClient = DBConnector.getMongoClient("localhost", 27017);
			db = DBConnector.getMongoDatabase(mongoClient, "test");
			
			db.getCollection("tickets").insertOne(new Document("date", date).append("hours", hours));
						
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

package hkol.tutorial.rest.tickets;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import hkol.tutorial.database.DBConnector;

@Path("/v1/tickets")
public class V1_tickets {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnAllTickets() throws Exception {
		
		MongoClient mongoClient = null;
		MongoDatabase db = null;
		
		FindIterable<Document> iterable = null;
		String result = "";
		
		try {
			mongoClient = DBConnector.getMongoClient("localhost", 27017);
			db = DBConnector.getMongoDatabase(mongoClient, "test");
			
			iterable = db.getCollection("tickets").find().limit(100);
			MongoCursor<Document> cursor = iterable.iterator();
			
			result += "[";
			while (cursor.hasNext()){
				result += cursor.next().toJson();
				if (cursor.hasNext()){
					result += ",";
				}
			}
			result += "]";

			return Response.ok(result).build();
		}
		catch (Exception e){
			return Response.serverError().build();
		}
		finally {
			if (mongoClient != null) mongoClient.close();
		}
	}
	
	@POST
	@Path("/new")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addTicket(@FormParam("date")String date, @FormParam("hours")String hours) throws Exception {
		MongoClient mongoClient = null;
		MongoDatabase db = null;
	    MongoCollection<Document> coll = null;
		
		try {
			mongoClient = DBConnector.getMongoClient("localhost", 27017);
			db = mongoClient.getDatabase("test");
			coll = db.getCollection("tickets");
			
			Document doc = new Document("date", date).append("hours", hours);
			
			coll.insertOne(doc);
						
			return Response.ok(doc.toJson()).build();
		}
		catch (Exception e){
			return Response.serverError().build();
		}
		finally {
			if (mongoClient != null) mongoClient.close();
		}
	}
}
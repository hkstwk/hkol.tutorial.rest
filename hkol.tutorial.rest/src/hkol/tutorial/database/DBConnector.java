package hkol.tutorial.database;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DBConnector {
	public static MongoClient getMongoClient(String host, int port){
		return new MongoClient(host, port);
	}
	
	public static MongoDatabase getMongoDatabase(MongoClient mongoClient, String database){
		return mongoClient.getDatabase(database);
	}
	
	public static MongoCollection<Document> getCollection(MongoClient mongoClient, MongoDatabase database, String collection){
		return database.getCollection(collection);
	}
	
	public static void closeMongoClient(MongoClient mongoClient){
		mongoClient.close();
	}
}

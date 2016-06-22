package api;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import configuration.ThoughtsSharingConfiguration;

public class ThoughtsSharingApp extends Application<ThoughtsSharingConfiguration> {
	
	public static void main(String[] args) throws Exception {
		new ThoughtsSharingApp().run(args);
	}

	@Override
	public void run(ThoughtsSharingConfiguration conf, Environment env) throws Exception {
		// Create a client for the remote mongodb
		MongoClient mongoClient = new MongoClient(conf.getDbURL(),conf.getDbPort());
		// Get the database managing thoughts
		MongoDatabase thoughtsDb  = mongoClient.getDatabase(conf.getDbName());
		// Create and get the collection of thoughts
		// seems to be not needed thoughtsDb.createCollection(conf.getCollectionName());
		MongoCollection<Document> thoughtsCollection = thoughtsDb.getCollection(conf.getCollectionName());
		
		final ThoughtsSharingAPI api = new ThoughtsSharingAPI(thoughtsCollection);
		env.jersey().register(api);;

	}

}

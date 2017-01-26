package org.cisiondata.utils.mongodb;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class MongoDBUtils {
	
	private MongoClient mongoClient = null;
    
    private MongoDBUtils() {  
        initMongoClient();  
    }  
      
    private static class MongoDBUtilsHolder {  
        private static final MongoDBUtils INSTANCE = new MongoDBUtils();  
    }  
      
    public static final MongoDBUtils getInstance() {  
        return MongoDBUtilsHolder.INSTANCE;  
    } 
    
    private void initMongoClient() {  
    	List<ServerAddress> serverAddressList = new ArrayList<ServerAddress>();  
        ServerAddress serverAddress01 = new ServerAddress("192.168.0.20", 27018);  
        ServerAddress serverAddress02 = new ServerAddress("192.168.0.115", 27018);  
        serverAddressList.add(serverAddress01);  
        serverAddressList.add(serverAddress02);  
        List<MongoCredential> credentials = new ArrayList<MongoCredential>();  
        MongoCredential credential = MongoCredential.createCredential(  
                "root", "admin", "123456".toCharArray());  
        credentials.add(credential);  
        mongoClient = new MongoClient(serverAddressList, credentials);  
    }  
      
    public MongoClient getClient() {  
        return mongoClient;  
    }  
    
}

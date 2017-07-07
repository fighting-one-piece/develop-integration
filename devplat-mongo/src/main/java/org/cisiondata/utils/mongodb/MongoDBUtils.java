package org.cisiondata.utils.mongodb;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class MongoDBUtils {
	
	private Logger LOG = LoggerFactory.getLogger(MongoDBUtils.class);
	
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
		Properties properties = new Properties();
		InputStream in = null;
		try {
			in = MongoDBUtils.class.getClassLoader().getResourceAsStream("mongo/env.local.properties");
			properties.load(in);
			String clusterNodesTxt = properties.getProperty("mongo.cluster.nodes");
			if (StringUtils.isBlank(clusterNodesTxt)) throw new RuntimeException("server addresses is not null");
			String[] clusterNodes = clusterNodesTxt.indexOf(",") == -1 ? 
					new String[]{clusterNodesTxt} : clusterNodesTxt.split(",");
			List<ServerAddress> serverAddressList = new ArrayList<ServerAddress>(); 
			for (int i = 0, len = clusterNodes.length; i < len; i++) {
				String clusterNode = clusterNodes[i];
				if (clusterNode.indexOf(":") == -1) continue;
				String[] ipAndPort = clusterNode.split(":");
				serverAddressList.add(new ServerAddress(ipAndPort[0], Integer.parseInt(ipAndPort[1]))); 
				LOG.info("Mongo Cluster Host: {} : {}", ipAndPort[0], Integer.parseInt(ipAndPort[1]));
			}
			String username = properties.getProperty("mongo.cluster.username");
			String password = properties.getProperty("mongo.cluster.password");
			String database = properties.getProperty("mongo.cluster.database");
			List<MongoCredential> credentials = new ArrayList<MongoCredential>();  
	        MongoCredential credential = MongoCredential.createCredential(  
	                username, database, password.toCharArray());  
	        credentials.add(credential);  
	        mongoClient = new MongoClient(serverAddressList, credentials);  
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			try {
				if (null != in) in.close();
			} catch (IOException e) {
				LOG.error(e.getMessage(), e);
			}
		}
	}
    
    public MongoClient getClient() {  
        return mongoClient;  
    }  
    
}

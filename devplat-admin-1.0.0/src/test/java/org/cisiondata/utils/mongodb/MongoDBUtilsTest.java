package org.cisiondata.utils.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.cisiondata.utils.mongodb.MongoDBUtils;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.DeleteOneModel;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.WriteModel;
import com.mongodb.client.result.UpdateResult;

public class MongoDBUtilsTest {

	@Test  
    public void insertCollection() {  
        MongoClient mongoClient = MongoDBUtils.getInstance().getClient();  
        MongoDatabase database = mongoClient.getDatabase("admin");  
        database.createCollection("test");  
        mongoClient.close();  
    }  
	
    @Test  
    public void insertOne() {  
        MongoClient mongoClient = MongoDBUtils.getInstance().getClient();  
        MongoDatabase database = mongoClient.getDatabase("test");  
        MongoCollection<Document> collection = database.getCollection("test");  
        Document document1 = new Document();  
        document1.append("id", 1).append("username", "zhangsan").append("password", "zhangsan");  
        collection.insertOne(document1);  
        Document document2 = new Document();  
        document2.put("id", 2);  
        document2.put("username", "lisi");  
        document2.put("password", "lisi");  
        collection.insertOne(document2);  
        mongoClient.close();  
    }  
    
    @Test
	public void insertOneWithSpring() {
//    	MongoTemplate mongoTemplate = (MongoTemplate) SpringApplicationContext.getInstance().get("mongoTemplate");
    	Document document = new Document();  
        document.put("id", 3);  
        document.put("username", "wangwu");  
        document.put("password", "wangwu");  
//    	mongoTemplate.insert(document);
//    	mongoTemplate.insert(document, "test");
    }
      
    @Test  
    public void insertMany() {  
        MongoClient mongoClient = MongoDBUtils.getInstance().getClient();  
        MongoDatabase database = mongoClient.getDatabase("admin");  
        MongoCollection<Document> collection = database.getCollection("test");  
        List<Document> documents = new ArrayList<Document>();  
        Document document = null;  
        for (int i = 10; i < 20; i++) {  
            document = new Document();  
            document.append("id", i).append("username", "lisi" + i).append("password", "lisi" + i);  
            documents.add(document);  
        }  
        collection.insertMany(documents);  
        mongoClient.close();  
    }  
      
    @Test  
    public void updateOne() {  
        MongoClient mongoClient = MongoDBUtils.getInstance().getClient();  
        MongoDatabase database = mongoClient.getDatabase("admin");  
        MongoCollection<Document> collection = database.getCollection("test");  
        Document filter = new Document();  
        filter.put("id", 4);  
        filter.put("username", "lisi");  
        filter.put("password", "lisi");  
        Document updateContent = new Document();  
        updateContent.put("username", "lisinew");  
        updateContent.put("password", "lisinew");  
        BasicDBObject update = new BasicDBObject("$set", updateContent);   
        UpdateResult result = collection.updateOne(filter, update);  
        System.out.println(result.wasAcknowledged());  
        System.out.println(result.getMatchedCount());  
        System.out.println(result.getModifiedCount());  
    }  
      
    @Test  
    public void updateMany() {  
        MongoClient mongoClient = MongoDBUtils.getInstance().getClient();  
        MongoDatabase database = mongoClient.getDatabase("admin");  
        MongoCollection<Document> collection = database.getCollection("test");  
        Document filter = new Document();  
        filter.put("username", "zhangsan");  
        Document updateContent = new Document();  
        updateContent.put("username", "zhangsanupdate");  
        updateContent.put("password", "zhangsanupdate");  
        Document update = new Document("$set", updateContent);   
        UpdateResult result = collection.updateMany(filter, update);  
        System.out.println(result.wasAcknowledged());  
        System.out.println(result.getMatchedCount());  
        System.out.println(result.getModifiedCount());  
    }  
      
    @Test  
    public void delete() {  
        MongoClient mongoClient = MongoDBUtils.getInstance().getClient();  
        MongoDatabase database = mongoClient.getDatabase("admin");  
        MongoCollection<Document> collection = database.getCollection("test");  
        Document filter = new Document();  
        filter.put("id", 2);  
        Document deleteDocuemnt = collection.findOneAndDelete(filter);  
        System.out.println(deleteDocuemnt);  
    }  
      
    @Test  
    public void findByFilter() {  
        MongoClient mongoClient = MongoDBUtils.getInstance().getClient();  
        MongoDatabase database = mongoClient.getDatabase("admin");  
        MongoCollection<Document> collection = database.getCollection("test");  
        Document filter = new Document();  
        filter.put("id", 1);  
        FindIterable<Document> docs = collection.find(filter);  
        MongoCursor<Document> iterator = docs.iterator();  
        while (iterator.hasNext()) {  
            Document doc = iterator.next();  
            System.out.println(doc);  
        }  
        mongoClient.close();  
    }  
      
      
    @Test  
    public void findAll() {  
        MongoClient mongoClient = MongoDBUtils.getInstance().getClient();  
        MongoDatabase database = mongoClient.getDatabase("test");  
        MongoCollection<Document> collection = database.getCollection("test");  
        FindIterable<Document> docs = collection.find();  
        MongoCursor<Document> iterator = docs.iterator();  
        while (iterator.hasNext()) {  
            Document doc = iterator.next();  
            System.out.println(doc);  
        }  
        mongoClient.close();  
    }  
      
    @Test  
    public void findAllWithPagination() {  
        MongoClient mongoClient = MongoDBUtils.getInstance().getClient();  
        MongoDatabase database = mongoClient.getDatabase("admin");  
        MongoCollection<Document> collection = database.getCollection("test");  
        FindIterable<Document> docs = collection.find().skip(5).limit(5);  
        MongoCursor<Document> iterator = docs.iterator();  
        while (iterator.hasNext()) {  
            Document doc = iterator.next();  
            System.out.println(doc);  
        }  
        mongoClient.close();  
    }  
      
    @Test  
    public void bulk() {  
        MongoClient mongoClient = MongoDBUtils.getInstance().getClient();  
        MongoDatabase database = mongoClient.getDatabase("admin");  
        MongoCollection<Document> collection = database.getCollection("test");  
        List<WriteModel<Document>> requests = new ArrayList<WriteModel<Document>>();  
          
        Document insertOneDoc = new Document();  
        insertOneDoc.append("id", 6).append("username", "zhangsan6").append("password", "zhangsan6");  
        InsertOneModel<Document> insertOne = new InsertOneModel<Document>(insertOneDoc);  
        requests.add(insertOne);  
          
        Document filter = new Document();  
        filter.put("id", 4);  
        Document updateContent = new Document();  
        updateContent.put("username", "lisibulkupdate");  
        updateContent.put("password", "lisibulkupdate");  
        Document update = new Document("$set", updateContent);   
        UpdateOneModel<Document> updateOne = new UpdateOneModel<Document>(filter, update);  
        requests.add(updateOne);  
          
        Document deleteFilter = new Document();  
        deleteFilter.put("id", 4);  
        DeleteOneModel<Document> deleteOne = new DeleteOneModel<Document>(deleteFilter);  
        requests.add(deleteOne);  
          
        BulkWriteResult bwr = collection.bulkWrite(requests);  
        System.out.println(bwr.getMatchedCount());  
        System.out.println(bwr.getInsertedCount());  
        System.out.println(bwr.getModifiedCount());  
        System.out.println(bwr.getDeletedCount());  
          
        mongoClient.close();  
    }  
	
}

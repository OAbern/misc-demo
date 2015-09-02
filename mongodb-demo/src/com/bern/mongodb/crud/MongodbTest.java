package com.bern.mongodb.crud;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

/**
 * 简单的示例操作
 * @author sola
 *
 */
public class MongodbTest {
	public static void main(String[] args) {
		//配置mongodb的连接
		MongoClient client = new MongoClient("localhost", 27017);
		MongoDatabase db = client.getDatabase("mytest");
		
		getCollectionNames(db);
		getDocumentNames(db);
		
		client.close();
	}
	
	/**
	 * 获取数据库中集合的名字
	 * @param db
	 */
	public static void getCollectionNames(MongoDatabase db) {
		MongoIterable<String> names =  db.listCollectionNames();
		MongoCursor<String> namesCursor = names.iterator();
		while(namesCursor.hasNext()) {
			String name = namesCursor.next();
			System.out.println(name);
		}
	}
	
	/**
	 * 获取集合中文档的名字
	 * @param db
	 */
	public static void getDocumentNames(MongoDatabase db) {
		MongoCollection<Document> collection = db.getCollection("hello");
		Document document = new Document();
		document.append("_id", 3);
		document.append("name", "fd2");
		document.append("age", 22);
		
		collection.insertOne(document);
		FindIterable<Document> iterable = collection.find();
		MongoCursor<Document> cursor = iterable.iterator();
		while(cursor.hasNext()) {
			Document temp = cursor.next();
			System.out.println(temp.toJson());
		}
	}
	
	
}

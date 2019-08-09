package com.gmg.demo.arangodb.basic;

import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.ArangoDatabase;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.BaseEdgeDocument;
import com.arangodb.entity.CollectionEntity;
import com.arangodb.entity.MultiDocumentEntity;
import com.arangodb.model.AqlQueryOptions;
import com.arangodb.util.MapBuilder;
import com.arangodb.velocypack.VPackSlice;
import com.arangodb.velocypack.exception.VPackException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gmg
 * @title: Example
 * @projectName graphLearning
 * @description: TODO
 * @date 2019/8/8 17:08
 */
public class Example {
    public static void main(String[] args) {
        ArangoDB arangoDB = new ArangoDB.Builder()
                .host("192.168.254.134", 8529)
                .user("root")
                .password("root")
                .build();

        //创建数据库
        String dbName = "mydb";
       /* try {
            arangoDB.createDatabase(dbName);
            System.out.println("Database created: " + dbName);
        } catch (ArangoDBException e) {
            System.err.println("Failed to create database: " + dbName + "; " + e.getMessage());
        }*/

        //创建 Collection 相当于表
        String collectionName = "firstCollection";
        /*try {
            CollectionEntity myArangoCollection = arangoDB.db(dbName).createCollection(collectionName);
            System.out.println("Collection created: " + myArangoCollection.getName());
        } catch (ArangoDBException e) {
            System.err.println("Failed to create collection: " + collectionName + "; " + e.getMessage());
        }*/

        // 创建Document 相当于每行数据
       /* BaseDocument myObject = new BaseDocument();
        myObject.setKey("myKey");
        myObject.addAttribute("a", "gmg");
        myObject.addAttribute("b", 45);
        try {
            arangoDB.db(dbName).collection(collectionName).insertDocument(myObject);
            System.out.println("Document created");
        } catch (ArangoDBException e) {
            System.err.println("Failed to create document. " + e.getMessage());
        }*/

        //Read a document
       /* try {
            BaseDocument myDocument = arangoDB.db(dbName).collection(collectionName).getDocument("myKey",
                    BaseDocument.class);
            System.out.println("Key: " + myDocument.getKey());
            System.out.println("Attribute a: " + myDocument.getAttribute("a"));
            System.out.println("Attribute b: " + myDocument.getAttribute("b"));
        } catch (ArangoDBException e) {
            System.err.println("Failed to get document: myKey; " + e.getMessage());
        }*/

        // Read a document as VelocyPack
/*
        try {
            VPackSlice myDocument = arangoDB.db(dbName).collection(collectionName).getDocument("myKey",
                    VPackSlice.class);
            System.out.println("Key: " + myDocument.get("_key").getAsString());
            System.out.println("Attribute a: " + myDocument.get("a").getAsString());
            System.out.println("Attribute b: " + myDocument.get("b").getAsInt());
        } catch (ArangoDBException | VPackException e) {
            System.err.println("Failed to get document: myKey; " + e.getMessage());
        }*/

        // 更新文档
        /*myObject.addAttribute("c", "Bar");
        try {
            arangoDB.db(dbName).collection(collectionName).updateDocument("myKey", myObject);
        } catch (ArangoDBException e) {
            System.err.println("Failed to update document. " + e.getMessage());
        }*/

        // 删除文档
        /*try {
            arangoDB.db(dbName).collection(collectionName).deleteDocument("myKey");
        } catch (ArangoDBException e) {
            System.err.println("Failed to delete document. " + e.getMessage());
        }*/

        //Execute AQL queries
        /*try {
            String query = "FOR t IN firstCollection FILTER t.name == @name RETURN t";
            Map<String, Object> bindVars = new MapBuilder().put("name", "Homer").get();
            ArangoCursor<BaseDocument> cursor = arangoDB.db(dbName).query(query, bindVars, null,
                    BaseDocument.class);
            cursor.forEachRemaining(aDocument -> {
                System.out.println("Key: " + aDocument.getKey());
            });
        } catch (ArangoDBException e) {
            System.err.println("Failed to execute query. " + e.getMessage());
        }*/

        // Delete a document with AQL
        /*try {
            String query = "FOR t IN firstCollection FILTER t.name == @name "
                    + "REMOVE t IN firstCollection LET removed = OLD RETURN removed";
            Map<String, Object> bindVars = new MapBuilder().put("name", "Homer").get();
            ArangoCursor<BaseDocument> cursor = arangoDB.db(dbName).query(query, bindVars, null,
                    BaseDocument.class);
            cursor.forEachRemaining(aDocument -> {
                System.out.println("Removed document " + aDocument.getKey());
            });
        } catch (ArangoDBException e) {
            System.err.println("Failed to execute query. " + e.getMessage());
        }*/


        try {
            ArangoDatabase db = arangoDB.db("mydb");

            String queryCmmd = "for doc in @@collection return  doc";

            AqlQueryOptions options = new AqlQueryOptions();

            Map map =new HashMap();
            map.put("@collection","firstCollection");

            ArangoCursor<BaseDocument> cursor =db.query(queryCmmd, map, options, BaseDocument.class);
            List<BaseDocument> list= cursor.asListRemaining();

            while (cursor.hasNext()) {
                BaseDocument object = cursor.next();
                String a=object.getAttribute("a").toString();
                System.out.println(a);
            }
        } catch (ArangoDBException e) {
            System.err.println("Failed to get document: myKey; " + e.getMessage());
        }

    }
}

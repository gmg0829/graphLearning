package com.gmg.demo.arangodb.advanced;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.BaseEdgeDocument;
import com.arangodb.model.AqlQueryOptions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author gmg
 * @title: Example
 * @projectName graphLearning
 * @description: TODO
 * @date 2019/8/9 9:35
 */
public class Example {
    public static void main(String[] args) {
        insertEdge();
    }

    public static void insertGraph() {
        ArangoDB arangoDB = new ArangoDB.Builder()
                .host("192.168.254.134", 8529)
                .user("root")
                .password("root")
                .build();
        //数据库
        ArangoDatabase db = arangoDB.db("mydb");
        //集合
        ArangoCollection coll =db.collection("Ewriters");

        BaseDocument document = new BaseDocument();
        document.addAttribute("name","滚开");
        Set<String> books = new HashSet<>();
        books.add("巫师世界");
        books.add("神秘之旅");
        books.add("永恒剑主");
        books.add("剑道真解");
        books.add("极道天魔");
        document.addAttribute("books",books);
        coll.insertDocument(document);


        ArangoCollection collbooks = db.collection("Ebooks");
        BaseDocument document1 = new BaseDocument();
        document1.addAttribute("name","巫师世界");
        document1.addAttribute("writer","滚开");
        collbooks.insertDocument(document1);

        BaseDocument document2 = new BaseDocument();
        document2.addAttribute("name","神秘之旅");
        document2.addAttribute("writer","滚开");
        collbooks.insertDocument(document2);

        BaseDocument document3 = new BaseDocument();
        document3.addAttribute("name","永恒剑主");
        document3.addAttribute("writer","滚开");
        collbooks.insertDocument(document3);

        BaseDocument document4 = new BaseDocument();
        document4.addAttribute("name","剑道真解");
        document4.addAttribute("writer","滚开");
        collbooks.insertDocument(document4);

        BaseDocument document5 = new BaseDocument();
        document5.addAttribute("name","极道天魔");
        document5.addAttribute("writer","滚开");
        collbooks.insertDocument(document5);
    }

    public static void insertEdge() {
        long time = System.currentTimeMillis()/1000;
        ArangoDB arangoDB = new ArangoDB.Builder()
                .host("192.168.254.134", 8529)
                .user("root")
                .password("root")
                .build();
        //数据库
        ArangoDatabase db = arangoDB.db("mydb");
        //集合
        ArangoCollection coll =db.collection("QidianBooks");

        String queryCmmd = "for doc in @@collection return  doc";
        String queryCmmd2 = "for doc in @@collection return  doc";

        AqlQueryOptions options = new AqlQueryOptions();
        options.ttl(1000000);//持续时间
        Map map =new HashMap();
        map.put("@collection","Ebooks");
        Map map2 =new HashMap();
        map2.put("@collection","Ewriters");
        ArangoCursor<BaseEdgeDocument> cursor = db.query(queryCmmd, map, options, BaseEdgeDocument.class);
        int ii = 0;
        while (cursor.hasNext()) {
            ii++;
            BaseEdgeDocument object = cursor.next();
            String writes = object.getAttribute("writer").toString();
            String book = object.getAttribute("name").toString();
            String _to = object.getId();
            String _from = "";
            ArangoCursor<BaseEdgeDocument> cursorebooks = db.query(queryCmmd2, map2, options, BaseEdgeDocument.class);

            while (cursorebooks.hasNext()) {
                //Document
                BaseEdgeDocument object2 = cursorebooks.next();
                //BaseEdgeDocument object3 = new BaseEdgeDocument();object3.se
                if(object2.getAttribute("name").equals(writes)) {
                    _from = object2.getId();
                    BaseEdgeDocument baseDocument = new BaseEdgeDocument();
                    baseDocument.setFrom(_from);
                    baseDocument.setTo(_to);
                    baseDocument.addAttribute("relation","write");
                    baseDocument.addAttribute("status","active");
                    baseDocument.addAttribute("updataAt",time);
                    System.out.println(_to);
                    coll.insertDocument(baseDocument);
                    break;
                }
                //coll.importDocuments();多数据

            }

        }
    }


}

package com.gmg.demo.arangodb.graph;

import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDB;
import com.arangodb.entity.CollectionType;
import com.arangodb.entity.EdgeDefinition;
import com.arangodb.model.CollectionCreateOptions;
import com.arangodb.model.GraphCreateOptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * @author gmg
 * @title: Example
 * @projectName graphLearning
 * @description: TODO
 * @date 2019/8/9 10:54
 */
public class Example {
    public static void main(String[] args) {
        ArangoDB arangoDB = new ArangoDB.Builder()
                .host("192.168.254.134", 8529)
                .user("root")
                .password("root")
                .build();
        String dbName = "mydb";

        // 创建顶点
       // arangoDB.db(dbName).createCollection("v_user");

        //创建边
        CollectionCreateOptions coptions = new CollectionCreateOptions();
        coptions.type(CollectionType.EDGES);
        arangoDB.db(dbName).createCollection("e_user_relation", coptions);

        //批量插入顶点数据
        Collection<String> dataVertix = new ArrayList<String>();
        dataVertix.add("{" +
                "  \"_key\": \"u3\"," +
                "  \"name\":\"zhangsan\"" +
                "}");
        dataVertix.add("{" +
                "  \"_key\": \"u4\"," +
                "  \"name\":\"lisi\"" +
                "}");
        dataVertix.add("{" +
                "  \"_key\": \"u5\"," +
                "  \"name\":\"wangwu\"" +
                "}");
        dataVertix.add("{" +
                "  \"_key\": \"u6\"," +
                "  \"name\":\"zhaoliu\"" +
                "}");

        arangoDB.db(dbName).collection("v_user").insertDocuments(dataVertix);


        //批量插入边数据
        Collection<String> dataEdge = new ArrayList<String>();
        dataEdge.add("{" +
                "  \"_key\": \"u3_u4\"," +
                "  \"_from\": \"v_user/u3\"," +
                "  \"_to\": \"v_user/u4\"," +
                "  \"relation\": \"is_father\"" +
                "}");
        dataEdge.add("{" +
                "  \"_key\": \"u4_u5\"," +
                "  \"_from\": \"v_user/u4\"," +
                "  \"_to\": \"v_user/u5\"," +
                "  \"relation\": \"is_father\"" +
                "}");

        arangoDB.db(dbName).collection("e_user_relation").insertDocuments(dataEdge);


        //创建图
        Collection<EdgeDefinition> edgeDefinitions = new ArrayList<EdgeDefinition>();
        EdgeDefinition edgeDefinition = new EdgeDefinition();

        edgeDefinition.collection("e_user_relation");

        edgeDefinition.from("v_user");

        edgeDefinition.to("v_user");

        edgeDefinitions.add(edgeDefinition);

        GraphCreateOptions goptions = new GraphCreateOptions();
        goptions.orphanCollections("myGraph");

        arangoDB.db(dbName).createGraph("myGraph", edgeDefinitions, goptions);

        //使用AQL查询, 结果 (u3)-[is_father]->(u4)-[is_father]->(u5)
        String query = "FOR v,e,p IN 2 OUTBOUND 'v_user/u3' GRAPH 'myGraph' " +
                "return p";

        ArangoCursor<Map> result = arangoDB.db(dbName).query(query, null, null, Map.class);

        while (result.hasNext()) {
            Map path = result.next();
            System.out.println(path);
        }



    }
}

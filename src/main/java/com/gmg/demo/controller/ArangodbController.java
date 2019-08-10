package com.gmg.demo.controller;

import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDB;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author gmg
 * @title: ArangodbController
 * @projectName graphLearning
 * @description: TODO
 * @date 2019/8/9 16:18
 */
@RestController
public class ArangodbController {

    @RequestMapping("getArangodbMap")
    public Map getArangodbMap(){
        ArangoDB arangoDB = new ArangoDB.Builder()
                .host("192.168.254.134", 8529)
                .user("root")
                .password("root")
                .build();
        String dbName = "mydb";
        String query = "FOR v,e,p IN 2 OUTBOUND 'v_user/u3' GRAPH 'myGraph' " +
                "return p";

        ArangoCursor<Map> result = arangoDB.db(dbName).query(query, null, null, Map.class);
        Map path=null;
        while (result.hasNext()) {
             path = result.next();
        }
        return path;
    }
}

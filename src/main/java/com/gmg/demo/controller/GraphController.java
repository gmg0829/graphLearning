package com.gmg.demo.controller;

import com.gmg.demo.util.Neo4jFormat;
import com.gmg.demo.util.Neo4jDriverUtil;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.types.Node;
import org.neo4j.driver.v1.types.Path;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author gmg
 * @title: GraphController
 * @projectName neo4j_demo
 * @description: TODO
 * @date 2019/8/7 16:18
 */
@RestController
public class GraphController {

    @GetMapping("getPath")
    public Map<String, HashSet<Map<String, Object>>> getPath(){
        StatementResult statementResult= Neo4jDriverUtil
                .runStatementResult("MATCH p=(a)-[r]-(b) where a.CUSTOMER_ID='001611094560' and type(r)<>'PAY'  RETURN p LIMIT 300");
        return Neo4jFormat.formatPath(statementResult);
    }


    @GetMapping("getNode")
    public  List<Map<String,Object>> getNode(String customerId){
        Map<String,Object> parameters = Collections.singletonMap( "customerId", customerId);

        List<Record> list= Neo4jDriverUtil
                .runWithParameter("MATCH (a:CCustomer) where a.CUSTOMER_ID=$customerId return a",parameters);
        return Neo4jFormat.formatSignalNode(list);
    }


    @GetMapping("getPathNode")
        public  List<Map<String,Object>> getPathNode(){
        List<Record> list= Neo4jDriverUtil
                .run("MATCH (a)-[r]-(b) where a.CUSTOMER_ID='001611094560' and type(r)<>'PAY'  RETURN a,b LIMIT 300");
        return Neo4jFormat.formatSignalNode(list);
    }


}

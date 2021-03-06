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
import java.util.concurrent.CompletionStage;

/**
 * @author gmg
 * @title: GraphController
 * @projectName neo4j_demo
 * @description: TODO
 * @date 2019/8/7 16:18
 */
@RestController
public class GraphController {

    /**
     * 获取路径全部（节点和关系）
     * @return
     */
    @GetMapping("getPath")
    public Map<String, HashSet<Map<String, Object>>> getPath(){
        StatementResult statementResult= Neo4jDriverUtil
                .runStatementResult("MATCH p=(a)-[r]-(b) where a.CUSTOMER_ID='001611094560' and type(r)<>'PAY'  RETURN p LIMIT 300");
        return Neo4jFormat.formatPath(statementResult);
    }

    /**
     * 获取单个节点
     * @param customerId
     * @return
     */
    @GetMapping("getSingleNode")
    public  List<Map<String,Object>> getSingleNode(String customerId){
        Map<String,Object> parameters = Collections.singletonMap( "customerId", customerId);

        List<Record> list= Neo4jDriverUtil
                .runWithParameter("MATCH (a:CCustomer) where a.CUSTOMER_ID=$customerId return a",parameters);
        return Neo4jFormat.formatNode(list);
    }

    /**
     * 异步获取单个节点
     * @param customerId
     * @return
     */
    @GetMapping("getAsyncSingleNode")
    public  CompletionStage<List<Map<String,Object>>> getAsyncSingleNode(String customerId){
        Map<String,Object> parameters = Collections.singletonMap( "customerId", customerId);

        CompletionStage<List<Map<String,Object>>> list= Neo4jDriverUtil
                .runAsync("MATCH (a:CCustomer) where a.CUSTOMER_ID=$customerId return a",parameters);
        return list;
    }

    /**
     * 获取路径中的所有节点
     * @return
     */
    @GetMapping("getPathNode")
        public  List<Map<String,Object>> getPathNode(){
        List<Record> list= Neo4jDriverUtil
                .run("MATCH (a)-[r]-(b) where a.CUSTOMER_ID='001611094560' and type(r)<>'PAY'  RETURN a,b LIMIT 300");
        return Neo4jFormat.formatNode(list);
    }

    /**
     * 获取路径中的关系
     * @return
     */
    @GetMapping("getPathRelation")
    public Map<String, HashSet<Map<String, Object>>> getPathRelation(){
        StatementResult statementResult= Neo4jDriverUtil
                .runStatementResult("MATCH (a)-[r]-(b) where a.CUSTOMER_ID='001611094560' and type(r)<>'PAY'  RETURN r LIMIT 300");
        return Neo4jFormat.formatPathRelation(statementResult);
    }



}

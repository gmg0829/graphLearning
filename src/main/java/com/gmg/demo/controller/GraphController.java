package com.gmg.demo.controller;

import com.gmg.demo.util.FormatJson;
import com.gmg.demo.util.Neo4jDriverUtil;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("object")
    public Map<String, HashSet<Map<String, Object>>> getObject(){
        StatementResult statementResult= Neo4jDriverUtil
                .runStatementResult("MATCH p=(a)-[r]-(b) where a.CUSTOMER_ID='001611094560' and type(r)<>'PAY'  RETURN p LIMIT 300");
        return FormatJson.formatJson(statementResult);
    }
}

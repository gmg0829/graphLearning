package com.gmg.demo.util;

import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.types.Node;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;

import static org.neo4j.driver.v1.Values.parameters;

/**
 * @author gmg
 * @title: Neo4jDriverUtil
 * @projectName DataVisualDiscrimination
 * @description: TODO
 * @date 2019/5/29 13:44
 */
public class Neo4jDriverUtil {

    public static Driver getDriver() {
        String uri = ProfileUtil.loadConfig("neo4j.uri");
        String username = ProfileUtil.loadConfig("neo4j.username");
        String password = ProfileUtil.loadConfig("neo4j.password");
        return GraphDatabase.driver(uri, AuthTokens.basic(username, password));
    }

    public static StatementResult runStatementResult(String sql) {
        Driver driver = Neo4jDriverUtil.getDriver();
        StatementResult statementResult = null;
        try {
            Session session = driver.session();
            statementResult = session.run(sql);
            session.close();
            driver.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return statementResult;
    }

    public static List<Record> run(String sql) {
        Driver driver = Neo4jDriverUtil.getDriver();
        List<Record> list = null;
        try {
            Session session = driver.session();
            list = session.run(sql).list();
            session.close();
            driver.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Record> runWithParameter(String sql, Map<String,Object> parameter) {
        Driver driver = Neo4jDriverUtil.getDriver();
        List<Record> list = null;
        try {
            Session session = driver.session();
            list = session.run(sql,parameter).list();
            session.close();
            driver.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static CompletionStage<List<Map<String,Object>>> runAsync(String sql, Map<String,Object> parameter) {
        Driver driver = Neo4jDriverUtil.getDriver();
        try (Session session = driver.session()) {
            return session.runAsync( sql, parameter)
                    .thenCompose( cursor -> cursor.listAsync( record -> record.get( 0 ).asMap()) )
                    .exceptionally( error ->
                    {
                        // query execution failed, print error and fallback to empty list of titles
                        error.printStackTrace();
                        return Collections.emptyList();
                    } )
                    .thenCompose( titles -> session.closeAsync().thenApply( ignore -> titles ) );
        }
    }



    public static List<Record> transactionRead(String sql) {
        Driver driver = Neo4jDriverUtil.getDriver();
        try (Session session = driver.session()) {
            session.readTransaction(new TransactionWork<List<Record>>() {
                @Override
                public List<Record> execute(Transaction tx) {
                    return tx.run(sql).list();
                }
            });
        }
        return Collections.emptyList();
    }

    public static int transactionWrite(String sql) {
        Driver driver = Neo4jDriverUtil.getDriver();
        try (Session session = driver.session()) {
            session.writeTransaction(new TransactionWork<Integer>() {
                @Override
                public Integer execute(Transaction tx) {
                    tx.run(sql);
                    return 1;
                }
            });
        }
        return 0;
    }




}

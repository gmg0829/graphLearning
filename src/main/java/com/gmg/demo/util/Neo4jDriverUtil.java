package com.gmg.demo.util;

import org.neo4j.driver.v1.*;

import java.util.Collections;
import java.util.List;

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

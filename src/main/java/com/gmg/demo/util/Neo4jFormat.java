package com.gmg.demo.util;

import org.neo4j.driver.internal.InternalPath;
import org.neo4j.driver.internal.InternalRelationship;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.types.Node;
import org.neo4j.driver.v1.types.Relationship;

import java.util.*;

/**
 * @author gmg
 * @title: FormatJson
 * @projectName neo4j_demo
 * @description: TODO
 * @date 2019/8/7 16:09
 */
public class Neo4jFormat {

    /**
     * 格式化路径
     * @param result
     * @return
     */
    public static Map<String, HashSet<Map<String, Object>>> formatPath( StatementResult result){
        Map<String, HashSet<Map<String, Object>>> retuMap = new HashMap<String, HashSet<Map<String, Object>>>();
        try  {
            // 存放所有的节点数据
            HashSet<Map<String, Object>> nodedatas = new HashSet<Map<String, Object>>();
            // 存放所有的关系数据
            HashSet<Map<String, Object>> allrelationships = new HashSet<Map<String, Object>>();

            while (result.hasNext()) {
                Record record = result.next();
                // 这里面存的是这个关系的键值对，其实就是起始节点，关系，结束节点
                Map<String, Object> date = record.asMap();
                for (String key : date.keySet()) {
                    Object object = date.get(key);
                    // 强制转换
                    InternalPath data = (InternalPath) object;
                    Iterable<Node> allnodes = data.nodes();

                    for (Node node : allnodes) {
                        long nodeid = node.id();
                        Map<String, Object> nodedatamap = new HashMap<String, Object>();
                        // 添加节点的属性
                        Map<String, Object> data1 = node.asMap();
                        for (String key1 : data1.keySet()) {
                            nodedatamap.put(key1, data1.get(key1));
                        }
                        nodedatamap.put("name", nodeid);
                        nodedatas.add(nodedatamap);
                    }

                    Iterable<Relationship> relationships = data.relationships();

                    Map<String, Object> shipdata = new HashMap<String, Object>();
                    for (Relationship relationship : relationships) {
                        // 添加关系的属性
                        Map<String, Object> data1 = relationship.asMap();
                        for (String key1 : data1.keySet()) {
                            shipdata.put(key1, data1.get(key1));
                        }
                        // 起始节点id
                        long source = relationship.startNodeId();
                        // 结束节点Id
                        long target = relationship.endNodeId();
                        // 添加起始节点id
                        shipdata.put("source", source);
                        shipdata.put("target", target);
                    }
                    allrelationships.add(shipdata);
                }
            }
            retuMap.put("nodes", nodedatas);
            retuMap.put("relation", allrelationships);

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        } finally {
        }
        return retuMap;
    }

    /**
     * 格式化节点
     * @param list
     * @return
     */
    public static  List<Map<String,Object>>  formatNode( List<Record> list) {
        List<Map<String,Object>> mapList=new ArrayList<>();
        if (!list.isEmpty()){
            if (list.size() <= 1){
               Map<String,Object> map=list.get(0).values().get(0).asNode().asMap();
               mapList.add(map);
            }else{
                for (Record record : list) {
                    Value value=record.values().get(0);
                    Node node= value.asNode();
                    Map<String,Object> map=node.asMap();
                    mapList.add(map);
                }
            }

        }
        return mapList;
    }

    /**
     * 格式化路径
     * @param result
     * @return
     */
    public static Map<String, HashSet<Map<String, Object>>> formatPathRelation( StatementResult result){
        Map<String, HashSet<Map<String, Object>>> retuMap = new HashMap<String, HashSet<Map<String, Object>>>();
        try  {
            // 存放所有的关系数据
            HashSet<Map<String, Object>> allrelationships = new HashSet<Map<String, Object>>();

            while (result.hasNext()) {
                Record record = result.next();
                // 这里面存的是这个关系的键值对，其实就是起始节点，关系，结束节点
                Map<String, Object> date = record.asMap();
                for (String key : date.keySet()) {
                    Object object = date.get(key);
                    // 强制转换
                    InternalRelationship relationship= (InternalRelationship) object;

                    Map<String, Object> shipdata = new HashMap<String, Object>();
                    long source = relationship.startNodeId();
                    // 结束节点Id
                    long target = relationship.endNodeId();
                    // 添加起始节点id
                    shipdata.put("source", source);
                    shipdata.put("target", target);
                    // 添加关系的属性
                    Map<String, Object> relationProperties = relationship.asMap();
                    for (String key1 : relationProperties.keySet()) {
                        shipdata.put(key1, relationProperties.get(key1));
                    }
                    allrelationships.add(shipdata);
                }
            }
            retuMap.put("relation", allrelationships);

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        } finally {
        }
        return retuMap;
    }


    }

package com.gmg.demo.janusgraph;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.tinkerpop.gremlin.process.traversal.Bindings;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.util.empty.EmptyGraph;
import org.apache.commons.configuration.Configuration;
import org.janusgraph.core.JanusGraphFactory;

/**
 * @author gmg
 * @title: Test
 * @projectName graphLearning
 * @description: TODO
 * @date 2020/1/8 13:18
 */
public class Test {

    // used for bindings
    private static final String NAME = "name";
    private static final String AGE = "age";
    private static final String TIME = "time";
    private static final String REASON = "reason";
    private static final String PLACE = "place";
    private static final String LABEL = "label";
    private static final String OUT_V = "outV";
    private static final String IN_V = "inV";

    public static void main(String[] args) throws Exception {

        Configuration conf=new PropertiesConfiguration("conf/remote-graph.properties");
        Graph graph = EmptyGraph.instance();
        GraphTraversalSource g = graph.traversal().withRemote(conf);

//        final Bindings b = Bindings.instance();
//
//        Vertex saturn = g.addV(b.of(LABEL, "titan")).property(NAME, b.of(NAME, "saturn"))
//                .property(AGE, b.of(AGE, 10000)).next();

//        Object herculesAge = g.V().hasLabel("titan").has(NAME, "saturn").values(AGE).next();
//        System.out.println("Hercules is " + herculesAge + " years old.");

//         g.addV("person").property("name", "John").next();
//         g.addV("person").property("name", "Snow").next();
//         g.addV("person").property("name", "Erya").next();
//
//        System.out.println("Vertex count = " + g.V().count().next());
//        System.out.println("Edges count = " + g.E().count().next());



    }
}

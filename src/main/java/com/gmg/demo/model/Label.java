package com.gmg.demo.model;

import org.neo4j.ogm.annotation.NodeEntity;

/**
 * @author gmg
 * @title: P
 * @projectName sdn5-movies
 * @description: TODO
 * @date 2019/4/9 17:07
 */
@NodeEntity
public class Label {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Label(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Label() {
    }
}

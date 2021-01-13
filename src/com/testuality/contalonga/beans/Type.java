package com.testuality.contalonga.beans;

import java.util.List;

public class Type {

    private String id;
    private String name;
    private List<Subtype> subtypeList;

    public Type(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Subtype> getSubtypeList() {
        return subtypeList;
    }
}

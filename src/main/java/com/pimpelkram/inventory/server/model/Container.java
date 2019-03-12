package com.pimpelkram.inventory.server.model;

public class Container {

    private String name;
    private String parent;

    // getter setters:

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    // constructor:
    public Container(String name, String parent) {
        this.name = name;
        this.parent = parent;
    }

}

package com.pimpelkram.inventory.server.model;

import java.util.List;

public class Item {

    private String       name;
    private List<String> tagList;
    private String       imagePath;
    private String       description;
    private String       container;

    // getter setter

    public String getContainer() {
        return this.container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTagList() {
        return this.tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // constructor
    public Item(String name, List<String> tags, String imagePath, String containerName, String description) {
        this.name = name;
        this.tagList = tags;
        this.imagePath = imagePath;
        this.container = containerName;
        this.description = description;
    }

}

package com.pimpelkram.inventory.server.model;

import java.util.List;
import java.util.UUID;

public class Item {

    private UUID id;

    private String       name;
    private List<String> tagList;
    private String       imagePath;
    private String       description;
    private UUID       containerID;

    // getter setter


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getContainerID() {
        return this.containerID;
    }

    public void setContainerID(UUID containerID) {
        this.containerID = containerID;
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
    public Item(String name, List<String> tags, String imagePath, UUID containerID, String description) {
        this.name = name;
        this.tagList = tags;
        this.imagePath = imagePath;
        this.containerID = containerID;
        this.description = description;
        this.id = UUID.randomUUID();
    }

}

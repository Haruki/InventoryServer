package com.pimpelkram.inventory.server.model;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {

    private UUID         id;

    private String       name;
    private List<String> tagList;
    private List<String> imagePathList;
    private String       description;
    private UUID         containerID;

    // getter setter

    public UUID getId() {
        return this.id;
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

    public List<String> getImagePath() {
        return this.imagePathList;
    }

    public void setImagePath(List<String> imagePathList) {
        this.imagePathList = imagePathList;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // constructor
    @JsonCreator
    public Item(@JsonProperty("name") String name, @JsonProperty("tags") List<String> tags, @JsonProperty("imagePath") List<String> imagePathList,
            @JsonProperty("containerID") UUID containerID, @JsonProperty("description") String description) {
        this.name = name;
        this.tagList = tags;
        this.imagePathList = imagePathList;
        this.containerID = containerID;
        this.description = description;
        this.id = UUID.randomUUID();
    }

}

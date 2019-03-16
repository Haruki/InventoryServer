package com.pimpelkram.inventory.server.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Container {

    private String name;
    private Map<String, Container> subContainers = new HashMap<>();
    private UUID uuid;

    public Container(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID();
    }

    //----------------standard getter setter:-------------------


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Container> getSubContainers() {
        return subContainers;
    }

    public void setSubContainers(Map<String, Container> subContainers) {
        this.subContainers = subContainers;
    }

    //--------------------container handling---------------------

    public boolean addContainer(Container container, UUID parent) {
        if (this.getUuid().equals(parent)) {
            this.subContainers.put(container.getName(),container);
        } else {
            //test if one returns true;???
        }
        return false;
    }
}

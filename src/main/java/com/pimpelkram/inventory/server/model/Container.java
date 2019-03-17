package com.pimpelkram.inventory.server.model;

import java.util.*;
import java.util.stream.Stream;

public class Container {

    private String name;
    private List<Container> subContainers = new ArrayList<>();
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

    public List<Container> getSubContainers() {
        return subContainers;
    }

    public void setSubContainers(List<Container> subContainers) {
        this.subContainers = subContainers;
    }

    //--------------------container handling---------------------

    public boolean addContainer(Container container, UUID parent) {
        if (this.getUuid().equals(parent)) {
            this.subContainers.add(container);
            return true;
        } else {
            return this.subContainers.stream().anyMatch(con -> con.addContainer(container, parent));
        }
    }

    public Stream<Container> getAll() {
        Stream<Container> result = Stream.of(this);
        for (Container c : this.subContainers){
           result = Stream.concat(result, c.getAll());

        }
        return result;
    }
}

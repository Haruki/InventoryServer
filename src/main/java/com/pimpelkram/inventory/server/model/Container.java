package com.pimpelkram.inventory.server.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Container {

    private String          name;
    private List<Container> subContainers = new ArrayList<>();
    private UUID            uuid;

    public Container(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID();
    }

    public Container(String name, UUID id) {
        this.name = name;
        this.uuid = id;
    }

    @JsonCreator
    public Container(@JsonProperty("name") String name, @JsonProperty("id") UUID id, @JsonProperty("subContainers") List<Container> subContainers) {
        this.name = name;
        this.uuid = id;
        this.subContainers = subContainers;
    }

    // ----------------standard getter setter:-------------------

    public UUID getUuid() {
        return this.uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Container> getSubContainers() {
        return this.subContainers;
    }

    public void setSubContainers(List<Container> subContainers) {
        this.subContainers = subContainers;
    }

    // --------------------container handling---------------------

    public boolean addContainer(Container container, UUID parent) {
        if (this.getUuid().equals(parent)) {
            this.subContainers.add(container);
            return true;
        } else {
            return this.subContainers.stream().anyMatch(con -> con.addContainer(container, parent));
        }
    }

    public Stream<Container> all() {
        Stream<Container> result = Stream.of(this);
        for (final Container c : this.subContainers) {
            result = Stream.concat(result, c.all());

        }
        return result;
    }
}

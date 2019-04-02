
package com.pimpelkram.inventory.server;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pimpelkram.inventory.server.model.Container;
import com.pimpelkram.inventory.server.model.Inventory;
import com.pimpelkram.inventory.server.model.Item;

import spark.Request;
import spark.Response;

public class InventoryWebApi {

    private final Inventory    inventory;
    private final ObjectMapper mapper;

    public InventoryWebApi(Inventory inventory, ObjectMapper mapper) {
        this.inventory = inventory;
        this.mapper = mapper;
    }

    // ------------------ CONTAINERS --------------------------

    String getContainers(Request request, Response response) {
        final List<Container> allContainers = this.inventory.getContainers();
        try {
            return this.mapper.writeValueAsString(allContainers);
        } catch (final JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "ERROR";
        }
    }

    String getContainer(Request request, Response response) {
        final Optional<Container> item = this.inventory.getContainer(UUID.fromString(request.params(":id")));
        if (item.isPresent()) {
            try {
                return this.mapper.writeValueAsString(item.get());
            } catch (final JsonProcessingException e) {
                e.printStackTrace();
                return "ERROR";
            }
        } else {
            response.status(404);
            return null;
        }
    }

    // -------------------- ITEMS------------------------

    String getItem(Request request, Response response) {
        final Optional<Item> item = this.inventory.getItem(UUID.fromString(request.params(":id")));
        if (item.isPresent()) {
            try {
                return this.mapper.writeValueAsString(item.get());
            } catch (final JsonProcessingException e) {
                e.printStackTrace();
                return "ERROR";
            }
        } else {
            response.status(404);
            return null;
        }

    }

    String getItems(Request request, Response response) {
        final List<Item> allItems = this.inventory.getItems();
        try {
            return this.mapper.writeValueAsString(allItems);
        } catch (final JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "ERROR";
        }
    }

    String addItem(Request request, Response response) {
        final String requestString = request.body();
        Item item;
        try {
            item = this.mapper.readValue(requestString, Item.class);
            final UUID newID = this.inventory.addItem(item.getName(), item.getTagList(), item.getImagePath(), item.getContainerID(),
                    item.getDescription());
            final Optional<Item> newItem = this.inventory.getItem(newID);
            if (newItem.isPresent()) {
                return this.mapper.writeValueAsString(newItem);
            } else {
                response.status(404);
                return "Fehler bei der Erstellung eines neuen Items";
            }
        } catch (final IOException e) {
            e.printStackTrace();
            return "ERROR";

        }
    }
}

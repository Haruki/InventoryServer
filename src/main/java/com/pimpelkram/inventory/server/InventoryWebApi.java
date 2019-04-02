
package com.pimpelkram.inventory.server;

import java.util.List;
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
        try {
            final Container item = this.inventory.getContainer(UUID.fromString(request.params(":id"))).get();
            return this.mapper.writeValueAsString(item);
        } catch (final JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "ERROR";
        }
    }

    // -------------------- ITEMS------------------------

    String getItem(Request request, Response response) {
        final Item item = this.inventory.getItem(UUID.fromString(request.params(":id"))).get();
        try {
            return this.mapper.writeValueAsString(item);
        } catch (final JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "ERROR";
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
}

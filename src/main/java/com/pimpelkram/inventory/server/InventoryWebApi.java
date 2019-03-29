
package com.pimpelkram.inventory.server;

import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    String getItems(Request request, Response response) {
        return "hallooo moep lalala ALLE";
    }

    String getItem(Request request, Response response) {
        try {
            final Item item = this.inventory.getItem(UUID.fromString(request.params(":id"))).get();
            return this.mapper.writeValueAsString(item);
        } catch (final JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "ERROR";
        }
    }
}

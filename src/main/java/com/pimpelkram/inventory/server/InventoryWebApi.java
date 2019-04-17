
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

    String addContainer(Request request, Response response) {
        final String requestString = request.body();
        try {
            final Container container = this.mapper.readValue(requestString, Container.class);
            final UUID newContainerUUID = this.inventory.addContainer(container.getName(), container.getParentUuid());
            final Optional<Container> newContainer = this.inventory.getContainer(newContainerUUID);
            if (newContainer.isPresent()) {
                return this.mapper.writeValueAsString(newContainer.get());
            } else {
                return "Fehler bei der Erstellung eines neuen Containers";
            }
        } catch (final IOException e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    // -------------------- ITEMS------------------------

    String updateItem(Request req, Response resp) {
        final String requestString = req.body();
        try {
            final Item updateItem = this.mapper.readValue(requestString, Item.class);
            if (updateItem == null) {
                return "invalid request data";
            }
            final Optional<Item> item = this.inventory.getItem(updateItem.getId());
            if (item.isPresent()) {
                if (updateItem.getContainerID() != null && !updateItem.getContainerID().equals(item.get().getContainerID())) {
                    item.get().setContainerID(updateItem.getContainerID());
                }
                if (updateItem.getDescription() != null && !updateItem.getDescription().equals(item.get().getDescription())) {
                    item.get().setDescription(updateItem.getDescription());
                }
                if (updateItem.getImagePath() != null && !updateItem.getImagePath().equals(item.get().getImagePath())) {
                    item.get().setImagePath(updateItem.getImagePath());
                }

            } else {
                return "Item not found";
            }
            this.inventory.updateItem(item.get());
            return "OK";
        } catch (final IOException e) {
            e.printStackTrace();
            return "ERROR " + e.getMessage();
        }

    }

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
                return this.mapper.writeValueAsString(newItem.get());
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

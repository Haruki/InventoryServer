package com.pimpelkram.inventory.server;

import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.pimpelkram.inventory.server.config.Settings;
import com.pimpelkram.inventory.server.config.SettingsFileLoader;
import com.pimpelkram.inventory.server.model.Inventory;

public class Main {

    static Logger       logger   = LoggerFactory.getLogger(Main.class);

    static Settings     settings = new SettingsFileLoader().getSettings();
    static ObjectMapper mapper   = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public static void main(String[] args) {
        try {
            final Inventory inventory = mapper.readValue(new File(settings.getServerFolder() + "inventory.json"), Inventory.class);
            final InventoryWebApi api = new InventoryWebApi(inventory, mapper);
            logger.debug("Anzahl items: " + inventory.getItems().size());
            staticFiles.externalLocation(settings.getServerFolder());
            path("/items", () -> {
                get("/", api::getItems);
                get("/:id", api::getItem);
                post("/", api::addItem);
            });
            path("/containers", () -> {
                get("/", api::getContainers);
                get("/:id", api::getContainer);
            });
        } catch (final IOException e) {

            e.printStackTrace();
        }

    }
}

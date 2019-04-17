package com.pimpelkram.inventory.server;

import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.staticFiles;

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
        final PersistenceHandler persistence = new PersistenceHandler(mapper, settings.getServerFolder());
        final Inventory inventory = persistence.loadInventory();
        final InventoryWebApi api = new InventoryWebApi(inventory, mapper);
        logger.debug("Anzahl items: " + inventory.getItems().size());
        staticFiles.externalLocation(settings.getServerFolder());
        final ImageUpload imageUpload = new ImageUpload(settings.getServerFolder());
        path("/items", () -> {
            get("/", api::getItems);
            get("/:id", api::getItem);
            post("/", api::addItem);
            put("/", api::updateItem);
        });
        path("/containers", () -> {
            get("/", api::getContainers);
            get("/:id", api::getContainer);
        });
        post("/imageUpload", imageUpload::post);
        get("/save", persistence::save);

    }
}

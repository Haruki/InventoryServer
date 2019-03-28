package com.pimpelkram.inventory.server;

import static spark.Spark.get;
import static spark.Spark.path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.pimpelkram.inventory.server.config.Settings;
import com.pimpelkram.inventory.server.config.SettingsFileLoader;

public class Main {

    static Logger       logger   = LoggerFactory.getLogger(Main.class);

    static Settings     settings = new SettingsFileLoader().getSettings();
    static ObjectMapper mapper   = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public static void main(String[] args) {
        // mapper.re
        path("/items", () -> {
            get("/", InventoryWebApi::getItems);
            get("/:id", InventoryWebApi::getItem);
        });

    }
}

package com.pimpelkram.inventory.server;

import static spark.Spark.get;
import static spark.Spark.path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        path("/items", () -> {
            get("/", InventoryWebApi::getItems);
            get("/:id", InventoryWebApi::getItem);
        });

    }
}

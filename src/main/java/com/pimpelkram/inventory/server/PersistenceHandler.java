package com.pimpelkram.inventory.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pimpelkram.inventory.server.model.Inventory;
import spark.Request;
import spark.Response;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PersistenceHandler {

    private final ObjectMapper mapper;
    private final String inventoryPath;
    private Inventory inv;

    public PersistenceHandler(ObjectMapper mapper, Inventory inv, String inventoryPath) {
        this.mapper = mapper;
        this.inv = inv;
        this.inventoryPath = inventoryPath;
    }

    public String save(Request req, Response resp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss_SSS");
        LocalDateTime now = LocalDateTime.now();
        String newFileName = inventoryPath + "inventory_" + now.format(formatter) + ".json";
        File f = new File(newFileName);
        try {
            mapper.writeValue(f, inv);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "OK";
    }
}

package com.pimpelkram.inventory.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pimpelkram.inventory.server.model.Inventory;

import java.io.File;
import java.io.IOException;

public class PersistenceHandler {

    private final ObjectMapper mapper;
    private final String inventoryPath;
    private Inventory inv;

    public PersistenceHandler(ObjectMapper mapper, Inventory inv, String inventoryPath) {
        this.mapper = mapper;
        this.inv = inv;
        this.inventoryPath = inventoryPath;
    }

    public void save() {
        File f = new File(inventoryPath);
        try {
            mapper.writeValue(f, inv);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.pimpelkram.inventory.server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pimpelkram.inventory.server.model.Inventory;

import spark.Request;
import spark.Response;

public class PersistenceHandler {

    private final ObjectMapper mapper;
    private final String       inventoryPathString;
    private Inventory          inv;
    private Path               newestInventoryPath;
    Logger                     logger = LoggerFactory.getLogger(PersistenceHandler.class);

    public PersistenceHandler(ObjectMapper mapper, String inventoryPathParam) {
        this.mapper = mapper;
        this.inventoryPathString = inventoryPathParam;
        final Path inventoryPath = Paths.get(inventoryPathParam);
        Optional<Path> first = null;
        try {
            // Find the newest inventory file:
            first = Files.list(inventoryPath)
                    .filter(p -> p.getFileName().toString().startsWith("inventory"))
                    .sorted((p1, p2) -> p1.toString().compareTo(p2.toString()))
                    .reduce((one, two) -> two);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        if (first.isPresent()) {
            this.newestInventoryPath = first.get();
        } else {
            this.logger.error("Cannot load inventory file");
        }
    }

    public String getNewestInventory() {
        return this.newestInventoryPath.toString();
    }

    public String save(Request req, Response resp) {
        // allow save operation from localhost only
        this.logger.info("Client IP: " + req.ip());
        if (req.ip().equals("127.0.0.1") || req.ip().equals("0:0:0:0:0:0:0:1")) {
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss_SSS");
            final LocalDateTime now = LocalDateTime.now();
            final String newFileName = this.inventoryPathString + "inventory_" + now.format(formatter) + ".json";
            final File f = new File(newFileName);
            try {
                this.mapper.writeValue(f, this.inv);
            } catch (final IOException e) {
                e.printStackTrace();
            }
            this.newestInventoryPath = f.toPath();
            return "OK. Saved as " + this.getNewestInventory();
        } else {
            return "Saving allowed from localhost only!";
        }
    }

    /** Loads the latest saved inventory by newest name from filesystem.
     * @return @see(Inventory.class)
     * @since 15.04.2019
     * @author borsutzha */
    public Inventory loadInventory() {
        try {
            this.logger.info("loading inventory file: " + this.getNewestInventory());
            this.inv = this.mapper.readValue(new File(this.getNewestInventory()), Inventory.class);
            return this.inv;
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

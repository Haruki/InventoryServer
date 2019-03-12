package com.pimpelkram.inventory.server;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.pimpelkram.inventory.server.model.Inventory;

public class InventoryTest {

    Inventory inventory = new Inventory();

    @Test
    void addTest() {
        this.inventory.addContainer("MS", null);
        this.inventory.addContainer("Lagerraum", "MS");
        assertEquals("Lagerraum", this.inventory.getContainer("Lagerraum").getName());
    }

}

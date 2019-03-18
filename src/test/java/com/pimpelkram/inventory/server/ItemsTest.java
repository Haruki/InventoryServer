package com.pimpelkram.inventory.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.pimpelkram.inventory.server.model.Inventory;
import com.pimpelkram.inventory.server.model.Item;

public class ItemsTest {

    Inventory inv = new Inventory();
    UUID      msContainerId;
    UUID      rhContainerId;
    UUID      lagerraum1Id;
    UUID      box1Id;

    @BeforeEach
    void setup() {
        this.msContainerId = this.inv.addContainer("MS", null);
        this.rhContainerId = this.inv.addContainer("RH", null);
        this.lagerraum1Id = this.inv.addContainer("Lagerraum1", this.msContainerId);
        this.box1Id = this.inv.addContainer("Box1", this.lagerraum1Id);
    }

    @Test
    void addItemTest() {
        final List<String> tags = new ArrayList<>();
        tags.add("watercooling");
        final UUID nexxosID = this.inv.addItem("Nexxos 240mm", tags, "000001.jpg", this.box1Id, "Alphacool Radiator");
        final Optional<Item> result = this.inv.getItem(nexxosID);
        assertTrue(result.isPresent());
        assertEquals("Nexxos 240mm", result.get().getName());
    }
}

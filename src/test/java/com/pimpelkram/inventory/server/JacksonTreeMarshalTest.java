package com.pimpelkram.inventory.server;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.pimpelkram.inventory.server.model.Inventory;

public class JacksonTreeMarshalTest {

    ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    Inventory    inv    = new Inventory();

    @BeforeEach
    void setup() {
        final UUID msContainerID = this.inv.addContainer("MS", null);
        final UUID rhContainerID = this.inv.addContainer("RH", null);
        final UUID msLagerraum1ID = this.inv.addContainer("Lagerraum1", msContainerID);
        final UUID msLagerraum1Box1ID = this.inv.addContainer("Box1", msLagerraum1ID);
        final List<String> tags = new ArrayList<>();
        tags.add("watercooling");
        tags.add("pc parts");
        final List<String> imagePaths = new ArrayList<>();
        imagePaths.add("000001.jpg");
        imagePaths.add("000002.jpg");
        final UUID radiatorID = this.inv.addItem("Nexxos 240mm", tags, imagePaths, msLagerraum1Box1ID, "Alphacool Radiator");
        final UUID mouseId = this.inv.addItem("Roccat Tyon", null, imagePaths, msLagerraum1Box1ID, "Maus");

    }

    @Test
    void toJsonTest() throws Exception {
        final String resultJson = this.mapper.writeValueAsString(this.inv);
        System.out.println(resultJson);
    }

}

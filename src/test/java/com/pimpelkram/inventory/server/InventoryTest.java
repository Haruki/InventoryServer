package com.pimpelkram.inventory.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.pimpelkram.inventory.server.model.Container;
import org.junit.jupiter.api.Test;

import com.pimpelkram.inventory.server.model.Inventory;

import java.util.Optional;
import java.util.UUID;

public class InventoryTest {

    Inventory inventory = new Inventory();

    @Test
    void addTest() {
        this.inventory.addContainer("MS", null);
        UUID nextParent = null;
        for (Container c : this.inventory.getContainers()) {
            if (c.getName().equals("MS")) {
                nextParent = c.getUuid();
            }
        }
        UUID lagerraumID = this.inventory.addContainer("Lagerraum", nextParent);
        assertNotNull(lagerraumID);
        Optional<Container> result = this.inventory.getContainer(lagerraumID);
        assert result.isPresent();
        assertEquals("Lagerraum", result.get().getName());
        assertEquals(lagerraumID, result.get().getUuid());
        assertEquals(1, this.inventory.getContainers().get(0).getSubContainers().size());

        UUID kuecheID = this.inventory.addContainer("kueche", nextParent);
        UUID kueche_regal1 = this.inventory.addContainer("regal1", kuecheID);

        assertEquals(2, this.inventory.getContainers().get(0).getSubContainers().size());
        assertEquals("regal1", this.inventory.getContainer(kueche_regal1).get().getName());
    }

}

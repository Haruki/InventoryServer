package com.pimpelkram.inventory.server.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Inventory {

    // @JsonDeserialize(as = ArrayList.class, contentAs = Container.class)
    private List<Container> containers;
    // @JsonDeserialize(as = ArrayList.class, contentAs = Item.class)
    private List<Item>      items;
    private boolean         dirty;

    // getters setters:

    public List<Container> getContainers() {
        return this.containers;
    }

    public boolean isDirty() {
        return this.dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public void setContainers(List<Container> containers) {
        this.containers = containers;
        this.dirty = true;
    }

    public List<Item> getItems() {
        return this.items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        this.dirty = true;
    }

    // other methods:

    // ------------------------Container Methods:-------------------------------

    /** Fügt einen Container zur Liste der bekannten Container hinzu.
     * Der Name muss eindeutig sein!
     * @param name Name des Containers (z.B. MS oder RH oder LagerraumMS oder Box25)
     * @param parent Name des Parent-Containers falls vorhanden, darf null sein
     * @return true= Hinzufügen war erfolgreich, false=Fehler, wurde nicht hinzugefügt (z.B. Name nicht eindeutig/schon vorhanden)
     * @since 12.03.2019
     * @author borsutzha */
    public UUID addContainer(String name, UUID parent) {
        final Container newContainer = new Container(name);
        if (this.containers == null) {
            this.containers = new ArrayList<>();
        }
        // name darf nicht null sein!
        if (name == null) {
            return null;
        }
        if (parent == null) {
            this.containers.add(newContainer);
        } else {
            final boolean isSuccessfullAdded = this.containers.stream().anyMatch(container -> container.addContainer(newContainer, parent));
            if (!isSuccessfullAdded) {
                return null;
            }
        }
        this.dirty = true;
        return newContainer.getUuid();
    }

    public Optional<Container> getContainer(UUID id) {
        Stream<Container> all = Stream.empty();
        for (final Container c : this.containers) {
            all = Stream.concat(all, c.all());
        }
        return all.filter(c -> c.getUuid().equals(id)).findFirst();
    }

    // ----------------------------Item Methods-----------------------------

    public boolean updateImages(UUID id, List<String> imageList) {
        return false;
    }

    public boolean existsItem(UUID id) {
        return this.items.stream().anyMatch(item -> item.getId().equals(id));
    }

    public UUID addItem(String name, List<String> tags, List<String> imagePathList, UUID containerID, String description) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        final Item newItem = new Item(name, tags, imagePathList, containerID, description);
        this.items.add(newItem);
        this.dirty = true;
        return newItem.getId();
    }

    public boolean deleteItem(UUID itemId) {
        this.dirty = true;
        return this.items.removeIf(item -> item.getId().equals(itemId));
    }

    public List<Item> getItemsOfContainer(UUID containerID) {
        return this.items.stream().filter(item -> item.getContainerID().equals(containerID)).collect(Collectors.toList());
    }

    public Optional<Item> getItem(UUID itemId) {
        return this.getItems().stream().filter(item -> item.getId().equals(itemId)).findFirst();
    }

}

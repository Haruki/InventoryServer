package com.pimpelkram.inventory.server.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Inventory {

    private List<Container> containers;
    private List<Item>      items;

    // getters setters:

    public List<Container> getContainers() {
        return this.containers;
    }

    public void setContainers(List<Container> containers) {
        this.containers = containers;
    }

    public List<Item> getItems() {
        return this.items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    // other methods:
    /** Fügt einen Container zur Liste der bekannten Container hinzu.
     * Der Name muss eindeutig sein!
     * @param name Name des Containers (z.B. MS oder RH oder LagerraumMS oder Box25)
     * @param parent Name des Parent-Containers falls vorhanden, darf null sein
     * @return true= Hinzufügen war erfolgreich, false=Fehler, wurde nicht hinzugefügt (z.B. Name nicht eindeutig/schon vorhanden)
     * @since 12.03.2019
     * @author borsutzha */
    public boolean addContainer(String name, String parent) {
        if (this.containers == null) {
            this.containers = new ArrayList<>();
        }
        // name darf nicht null sein!
        if (name == null) {
            return false;
        }
        // name muss eindeutig sein!
        if (this.containers.stream().anyMatch(container -> container.getName().equals(name))) {
            return false;
        }
        // parent muss existieren falls nicht null
        if (parent != null && !this.containers.stream().anyMatch(container -> parent.equals(container.getName()))) {
            return false;
        }
        this.containers.add(new Container(name, parent));
        return true;
    }

    public Container getContainer(String name) {
        final List<Container> sublist = this.containers.stream().filter(container -> container.getName().equals(name)).collect(Collectors.toList());
        if (sublist != null && sublist.size() > 0) {
            return sublist.get(0);
        }
        return null;
    }

    public void deleteContainer(String name) {
        this.containers.removeIf(container -> container.getName().equals(name));
    }

    public boolean addItem(String name, List<String> tags, String imagePath, String containerName, String description) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        if (name == null || this.items.stream().anyMatch(item -> item.getName().equals(name))) {
            return false;
        }
        this.items.add(new Item(name, tags, imagePath, containerName, description));
        return true;
    }

    public void deleteItem(String name) {
        this.items.removeIf(item -> item.getName() == name);
    }

}

package com.pimpelkram.inventory.server.config;

/** Bean Class for storing all (persistent) settings.
 * @author borsutzha */
public class Settings {

    private String serverFolder;

    public String getServerFolder() {
        return this.serverFolder;
    }

    public void setServerFolder(String serverFolder) {
        this.serverFolder = serverFolder;
    }

}

package com.pimpelkram.inventory.server.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.json.Json;
import javax.json.JsonReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Lädt JSON Properties als Einstellungen für das Programm. Es wird
 * standardmäßig im Home Verzeichnis des users erwartet.
 * @author borsutzha */
public class SettingsFileLoader {

    Logger               logger           = LoggerFactory.getLogger(SettingsFileLoader.class);

    private final String homePath         = System.getProperty("user.home");

    private final String expectedFileName = "inventoryServer.json";

    private final Path   settingsPath     = Paths.get(this.homePath + "\\" + this.expectedFileName);

    private boolean existsConfigFile() {
        return Files.exists(this.settingsPath);
    }

    /* Lädt Settings aus einer Datei im Home-Verzeichnis. Namen sind fest
     * definiert. (Convention over Configuration)
     * @see com.pimpelkram.deliverytool.config.SettingsLoader#getSettings() */
    public Settings getSettings() {
        if (this.existsConfigFile()) {

            Settings settings = new Settings();
            final Charset charset = Charset.forName("UTF-8");
            BufferedReader fileReader;
            try {
                fileReader = Files.newBufferedReader(this.settingsPath, charset);
                final JsonReader reader = Json.createReader(fileReader);
                settings = new JsonSettingsBuilder().build(settings, reader);
            } catch (final IOException e) {
                this.logger.error("Konfigurationsdatei kann nicht geladen werden", e);
            }

            return settings;
        } else {
            this.logger.error("Konfigurationsdatei nicht gefunden");
            return null;
        }
    }

}

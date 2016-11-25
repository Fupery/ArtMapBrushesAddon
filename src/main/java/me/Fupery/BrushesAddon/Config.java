package me.Fupery.BrushesAddon;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

class Config {
    private String resourcePackURL;

    private Config(String resourcePackURL) {
        this.resourcePackURL = resourcePackURL;
    }

    static Config loadConfigurations(Main plugin) throws InvalidResourcePackURLException {
        if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdir();
        File config = new File(plugin.getDataFolder(), "config.yml");
        if (!config.exists()) plugin.saveDefaultConfig();

        String url = plugin.getConfig().getString("resourcePackURL");

        try {
            new URL(url);
        } catch (MalformedURLException e) {
            throw new InvalidResourcePackURLException(url, e);
        }
        return new Config(url);
    }

    String getResourcePackURL() {
        return resourcePackURL;
    }

    static class InvalidResourcePackURLException extends Exception {
        InvalidResourcePackURLException(String URL, MalformedURLException e) {
            super(String.format("'%s' is not a valid resource pack URL! Plugin will be disabled.", URL));
            e.printStackTrace();
        }
    }
}

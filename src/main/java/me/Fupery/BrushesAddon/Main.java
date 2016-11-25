package me.Fupery.BrushesAddon;

import me.Fupery.ArtMap.ArtMap;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main plugin;
    private PlayerMountEaselListener listener = new PlayerMountEaselListener(this);
    private Config config;

    static Main instance() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        PluginManager manager = Bukkit.getPluginManager();
        Plugin plugin = manager.getPlugin("ArtMap");
        plugin.getDescription().getVersion();


        try {
            config = Config.loadConfigurations(this);
        } catch (Config.InvalidResourcePackURLException e) {
            e.printStackTrace();
            getPluginLoader().disablePlugin(this);
            return;
        }
        ArtMap.instance().setColourPalette(new CustomPalette());
        ArtMap.getRecipeLoader().unloadRecipes();
        ArtMap.getRecipeLoader().loadRecipes();

        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    Config getConfigurations() {
        return config;
    }

    @Override
    public void onDisable() {
        listener.unregister();
        plugin = null;
    }
}

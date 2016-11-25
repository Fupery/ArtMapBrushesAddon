package me.Fupery.BrushesAddon;

import me.Fupery.ArtMap.ArtMap;
import me.Fupery.ArtMap.Utils.Version;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main plugin;
    private PlayerArtMapListener listener = new PlayerArtMapListener(this);
    private Config config;

    static Main instance() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        if (getArtMapVersion().isLessThan(2, 4, 9)) {
            disablePlugin("Invalid ArtMap version! 2.4.9 or higher is required. This add-on will be disabled.");
            return;
        }
        if (Version.getBukkitVersion().isLessThan(1, 9)) {
            disablePlugin("Invalid minecraft version! This add-on does not work with minecraft 1.8 or below.");
            return;
        }
        if (!ArtMap.getConfiguration().FORCE_ART_KIT) {
            disablePlugin("Invalid Configuration! You must set 'forceArtKit' to 'true' in your ArtMap config.yml.");
            return;
        }
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

    private void disablePlugin(String message) {
        getLogger().warning(message);
        getPluginLoader().disablePlugin(this);
    }

    private static Version getArtMapVersion() {
        PluginManager manager = Bukkit.getPluginManager();
        String[] version = manager.getPlugin("ArtMap").getDescription().getVersion().split("\\.");
        int[] versionNumbers = new int[version.length];
        for (int i = 0; i < version.length; i++) {
            versionNumbers[i] = Integer.parseInt(version[i]);
        }
        return new Version(versionNumbers);
    }

    @Override
    public void onDisable() {
        try {
            listener.unregister();
        } catch (Exception e) {
            //Fail silently
        }
        plugin = null;
    }
}

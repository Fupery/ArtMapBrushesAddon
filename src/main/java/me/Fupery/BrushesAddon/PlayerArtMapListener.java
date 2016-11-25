package me.Fupery.BrushesAddon;

import me.Fupery.ArtMap.Event.PlayerMountEaselEvent;
import me.Fupery.ArtMap.Event.PlayerOpenMenuEvent;
import me.Fupery.ArtMap.Listeners.RegisteredListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class PlayerArtMapListener implements RegisteredListener {

    private Main plugin;
    private MetadataTag resourcePackSent = new MetadataTag("artmap-resource-pack");
    private List<UUID> resourcePackSending = new ArrayList<>();

    PlayerArtMapListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMountEasel(PlayerMountEaselEvent event) {
        Player player = event.getPlayer();
        if (resourcePackSent.isTagged(player)) return;
        player.setResourcePack(plugin.getConfigurations().getResourcePackURL());
        resourcePackSending.add(player.getUniqueId());
    }

    @EventHandler
    public void onPlayerOpenMenu(PlayerOpenMenuEvent event) {
        Player player = event.getPlayer();
        if (resourcePackSent.isTagged(player)) return;
        player.setResourcePack(plugin.getConfigurations().getResourcePackURL());
        resourcePackSending.add(player.getUniqueId());
    }

    @EventHandler
    public void onPlayerResourcePackStatus(PlayerResourcePackStatusEvent event) {
        Player player = event.getPlayer();
        if (!resourcePackSending.contains(player.getUniqueId())) return;
        switch (event.getStatus()) {
            case SUCCESSFULLY_LOADED:
                resourcePackSending.remove(player.getUniqueId());
                break;
            case DECLINED:
                resourcePackSending.remove(player.getUniqueId());
                resourcePackSent.tag(player, false);
                break;
            case FAILED_DOWNLOAD:
                resourcePackSending.remove(player.getUniqueId());
                break;
            case ACCEPTED:
                resourcePackSent.tag(player, true);
                break;
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (resourcePackSent.isTagged(player)) resourcePackSent.removeTag(player);
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        if (resourcePackSent.isTagged(player)) resourcePackSent.removeTag(player);
    }

    @Override
    public void unregister() {
        PlayerMountEaselEvent.getHandlerList().unregister(this);
        PlayerOpenMenuEvent.getHandlerList().unregister(this);
        PlayerResourcePackStatusEvent.getHandlerList().unregister(this);
        PlayerQuitEvent.getHandlerList().unregister(this);
        PlayerChangedWorldEvent.getHandlerList().unregister(this);
    }
}

package me.Fupery.BrushesAddon;

import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

final class MetadataTag {

    private final String tag;

    MetadataTag(String tag) {
        this.tag = tag;
    }

    void tag(Player player, boolean value) {
        if (isTagged(player)) removeTag(player);
        player.setMetadata(tag, new FixedMetadataValue(Main.instance(), value));
    }

    void removeTag(Player player) {
        player.removeMetadata(tag, Main.instance());
    }

    boolean isTagged(Player player) {
        return player.hasMetadata(tag);
    }

    boolean readTag(Player player) {
        return player.getMetadata(tag).get(0).asBoolean();
    }
}

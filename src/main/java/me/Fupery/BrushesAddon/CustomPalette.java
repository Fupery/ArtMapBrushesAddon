package me.Fupery.BrushesAddon;

import me.Fupery.ArtMap.Colour.ArtDye;
import me.Fupery.ArtMap.Colour.Palette;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomPalette implements Palette {
    public final ArtDye
            BLACK = new CustomBrush("Black", 119, ChatColor.DARK_GRAY, 1),
            RED = new CustomBrush("Red", 17, ChatColor.RED, 2),
            GREEN = new CustomBrush("Green", 109, ChatColor.DARK_GREEN, 3),
            BROWN = new CustomBrush("Brown", 105, ChatColor.DARK_RED, 4),
            BLUE = new CustomBrush("Blue", 101, ChatColor.DARK_BLUE, 5),
            PURPLE = new CustomBrush("Purple", 97, ChatColor.DARK_PURPLE, 6),
            CYAN = new CustomBrush("Cyan", 93, ChatColor.DARK_AQUA, 7),
            SILVER = new CustomBrush("Silver", 32, ChatColor.GRAY, 8),
            GRAY = new CustomBrush("Gray", 85, ChatColor.DARK_GRAY, 9),
            PINK = new CustomBrush("Pink", 81, ChatColor.LIGHT_PURPLE, 10),
            LIME = new CustomBrush("Lime", 77, ChatColor.GREEN, 11),
            YELLOW = new CustomBrush("Yellow", 74, ChatColor.YELLOW, 12),
            LIGHT_BLUE = new CustomBrush("Light Blue", 69, ChatColor.BLUE, 13),
            MAGENTA = new CustomBrush("Magenta", 64, ChatColor.LIGHT_PURPLE, 14),
            ORANGE = new CustomBrush("Orange", 61, ChatColor.GOLD, 15),
            WHITE = new CustomBrush("White", 58, ChatColor.WHITE, 16),
            CREAM = new CustomBrush("Cream", 10, ChatColor.GOLD, 17),
            COFFEE = new CustomBrush("Coffee", 41, ChatColor.DARK_RED, 18),
            GRAPHITE = new CustomBrush("Graphite", 87, ChatColor.DARK_GRAY, 19),
            GUNPOWDER = new CustomBrush("Gunpowder", 89, ChatColor.GRAY, 20),
            MAROON = new CustomBrush("Maroon", 142, ChatColor.DARK_RED, 21),
            AQUA = new CustomBrush("Aqua", 125, ChatColor.AQUA, 22),
            GRASS = new CustomBrush("Grass", 5, ChatColor.DARK_GREEN, 23),
            GOLD = new CustomBrush("Gold", 121, ChatColor.GOLD, 24),
            VOID = new CustomBrush("Void", 0, ChatColor.DARK_GREEN, 25);

    private final ArtDye[] dyes = new ArtDye[]{BLACK, RED, GREEN, BROWN, BLUE, PURPLE, CYAN,
            SILVER, GRAY, PINK, LIME, YELLOW, LIGHT_BLUE, MAGENTA, ORANGE, WHITE,
            CREAM, COFFEE, GRAPHITE, GUNPOWDER, MAROON, AQUA, GRASS, GOLD, VOID};

    @Override
    public ArtDye getDye(ItemStack item) {
        if (item.getType() == Material.DIAMOND_HOE && item.hasItemMeta()
                && item.getItemMeta().spigot().isUnbreakable()
                && item.getItemMeta().hasItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                && item.getItemMeta().hasItemFlag(ItemFlag.HIDE_UNBREAKABLE)) {

            for (ArtDye dye : dyes) {
                if (item.getDurability() == dye.getDurability()) return dye;
            }
        }
        return null;
    }

    @Override
    public ArtDye[] getDyes() {
        return dyes;
    }

    @Override
    public ArtDye getDefaultColour() {
        return WHITE;
    }

    private class CustomBrush extends ArtDye {
        private CustomBrush(String name, int colour, ChatColor chatColor, int durability) {
            super(name, colour, chatColor, Material.DIAMOND_HOE, durability);
        }

        @Override
        public ItemStack toItem() {
            ItemStack item = super.toItem();
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(name() + " Brush");
            meta.spigot().setUnbreakable(true);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
            item.setItemMeta(meta);
            return item;
        }
    }

}

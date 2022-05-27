package org.saberdev.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.saberdev.Pouches;
import org.saberdev.gui.struct.GUI;
import org.saberdev.gui.struct.InventoryClick;
import org.saberdev.gui.struct.InventoryClose;
import org.saberdev.utils.ItemBuilder;

public class PouchGUI extends GUI {

    public PouchGUI(Pouches main) {
        super(main);
        this.setTitle("Pouches");
        this.setSize(54 / 2);
    }
    // 12 14

    @Override
    protected void buildInventory(Player player, Object... objects) {
        this.setItem(12, new ItemBuilder(Material.BOWL, 1).name("&b&lMoney Pouches").lore("&7Click to view all money pouches").build(), event -> new MoneyGUI(main).open(player));
        this.setItem(14, new ItemBuilder(Material.EXPERIENCE_BOTTLE, 1).name("&b&lExperience Pouches").lore("&7Click to view all experience pouches").build(), event -> new ExperienceGUI(main).open(player));
    }

    @Override
    protected InventoryClick mainInventoryClick() {
        return inventoryClick -> inventoryClick.setCancelled(true);
    }

    @Override
    protected InventoryClose inventoryClose() {
        return null;
    }
}

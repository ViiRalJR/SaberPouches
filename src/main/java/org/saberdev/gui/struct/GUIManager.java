package org.saberdev.gui.struct;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter @Setter
public class GUIManager {
    // -------------------------------------------- //
    // LOCAL FIELDS
    // -------------------------------------------- //

    private final Map<UUID, GUI> playerGuis = new HashMap<>();

    private ItemStack fillerItem, previousPageItem, nextPageItem;

    // -------------------------------------------- //
    // CONSTRUCT
    // -------------------------------------------- //

    public GUIManager() {
        setFillerItem(new ItemStack(Material.AIR));
        setPreviousPageItem(new ItemStack(Material.AIR));
        setNextPageItem(new ItemStack(Material.AIR));
    }

    // -------------------------------------------- //
    // FUNCTIONS
    // -------------------------------------------- //


    public GUI getPlayerGui(UUID uuid) {
        return this.playerGuis.get(uuid);
    }

    public GUI addPlayerGui(UUID uuid, GUI gui) {
        return this.playerGuis.put(uuid, gui);
    }

    public GUI removePlayerGui(UUID uuid) {
        return this.playerGuis.remove(uuid);
    }

}

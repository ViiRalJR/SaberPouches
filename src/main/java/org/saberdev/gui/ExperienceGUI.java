package org.saberdev.gui;

import org.bukkit.entity.Player;
import org.saberdev.Pouches;
import org.saberdev.gui.struct.GUI;
import org.saberdev.gui.struct.InventoryClick;
import org.saberdev.gui.struct.InventoryClose;

import java.util.concurrent.atomic.AtomicInteger;

public class ExperienceGUI extends GUI {

    public ExperienceGUI(Pouches main) {
        super(main);
        this.setTitle("Experience Pouches");
        this.setSize(54);
    }

    @Override
    protected void buildInventory(Player player, Object... objects) {
        AtomicInteger amount = new AtomicInteger(-1);
        main.getPouchHandler().getExperiencePouches().forEach((s, experiencePouch) -> {
            amount.getAndIncrement();
            this.setItem(amount.get(), experiencePouch.build(), event -> {
                if (player.hasPermission("saber.admin")) player.getInventory().addItem(experiencePouch.build());
            });
        });
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

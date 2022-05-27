package org.saberdev.gui;

import org.bukkit.entity.Player;
import org.saberdev.Pouches;
import org.saberdev.gui.struct.GUI;
import org.saberdev.gui.struct.InventoryClick;
import org.saberdev.gui.struct.InventoryClose;

import java.util.concurrent.atomic.AtomicInteger;

public class MoneyGUI extends GUI {

    public MoneyGUI(Pouches main) {
        super(main);
        this.setTitle("Money Pouches");
        this.setSize(54);
    }

    @Override
    protected void buildInventory(Player player, Object... objects) {
        AtomicInteger amount = new AtomicInteger(-1);
        main.getPouchHandler().getMoneyPouches().forEach((s, moneyPouch) -> {
            amount.getAndIncrement();
            this.setItem(amount.get(), moneyPouch.build(), event -> {
                if (player.hasPermission("saber.admin")) player.getInventory().addItem(moneyPouch.build());
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
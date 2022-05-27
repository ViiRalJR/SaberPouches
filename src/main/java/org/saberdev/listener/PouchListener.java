package org.saberdev.listener;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.saberdev.Pouches;
import org.saberdev.utils.StringUtils;
import org.saberdev.utils.Vault;

import java.util.Random;

public class PouchListener implements Listener {

    private Pouches plugin;

    public PouchListener(Pouches plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        ItemStack item = event.getItem();
        if (item == null || item.getType() == Material.AIR) return;
        NBTItem nbtItem = new NBTItem(item).hasCustomNbtData() ? new NBTItem(item) : null;
        if (nbtItem == null) return;
        if (!nbtItem.hasKey("SaberPouchType")) return;
        String type = nbtItem.getString("SaberPouchType");
        int min = nbtItem.getInteger("SaberMinAmount");
        int max = nbtItem.getInteger("SaberMaxAmount");
        int value = randomInt(min, max);
        if (type.equalsIgnoreCase("money")) {
            event.setCancelled(true);
            Vault.INSTANCE.getEconomy().depositPlayer(player, value);
            player.sendMessage(StringUtils.color(plugin.getConfig().getString("language.reward message money").replace("{AMOUNT}", Integer.toString(value))));
        }
        if (type.equalsIgnoreCase("experience")) {
            event.setCancelled(true);
            player.giveExp(value);
            player.sendMessage(StringUtils.color(plugin.getConfig().getString("language.reward message experience").replace("{AMOUNT}", Integer.toString(value))));
        }
        if(event.getItem().getAmount() > 1) {
            event.getItem().setAmount(event.getItem().getAmount() - 1);
        } else {
            event.getItem().setAmount(0);
        }
        player.updateInventory();
    }

    public int randomInt(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }


}

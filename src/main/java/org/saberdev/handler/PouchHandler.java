package org.saberdev.handler;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.saberdev.Pouches;
import org.saberdev.objects.ExperiencePouch;
import org.saberdev.objects.MoneyPouch;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class PouchHandler {

    private final Pouches plugin;

    private final HashMap<String, MoneyPouch> moneyPouches = new HashMap<>();
    private final HashMap<String, ExperiencePouch> experiencePouches = new HashMap<>();

    public PouchHandler(Pouches plugin) {
        this.plugin = plugin;
        reloadPouches();
    }

    public void reloadPouches() {
        moneyPouches.clear();
        experiencePouches.clear();
        loadMoneyPouches();
        loadExperiencePouches();
    }

    public void loadMoneyPouches() {
        long started = System.currentTimeMillis();
        AtomicInteger amount = new AtomicInteger();
        plugin.getConfig().getConfigurationSection("pouches.money").getKeys(false).forEach(pouch -> {
            String path = "pouches.money." + pouch + ".";
            String name = plugin.getConfig().getString(path + "name");
            List<String> lore = plugin.getConfig().getStringList(path + "lore");
            Material material = Material.valueOf(plugin.getConfig().getString(path + "material"));
            int min = plugin.getConfig().getInt(path + "minimum");
            int max = plugin.getConfig().getInt(path + "maximum");
            moneyPouches.put(pouch, new MoneyPouch(name, material, lore, max, min));
            amount.getAndIncrement();
        });
        long completed = started - System.currentTimeMillis();
        Bukkit.getConsoleSender().sendMessage("(SaberPouches) Loaded " + amount + " money pouches in " + completed + "ms");
    }

    public void loadExperiencePouches() {
        long started = System.currentTimeMillis();
        AtomicInteger amount = new AtomicInteger();
        plugin.getConfig().getConfigurationSection("pouches.experience").getKeys(false).forEach(pouch -> {
            String path = "pouches.experience." + pouch + ".";
            String name = plugin.getConfig().getString(path + "name");
            List<String> lore = plugin.getConfig().getStringList(path + "lore");
            Material material = Material.valueOf(plugin.getConfig().getString(path + "material"));
            int min = plugin.getConfig().getInt(path + "minimum");
            int max = plugin.getConfig().getInt(path + "maximum");
            experiencePouches.put(pouch, new ExperiencePouch(name, material, lore, max, min));
            amount.getAndIncrement();
        });
        long completed = started - System.currentTimeMillis();
        Bukkit.getConsoleSender().sendMessage("(SaberPouches) Loaded " + amount + " experience pouches in " + completed + "ms");
    }

}

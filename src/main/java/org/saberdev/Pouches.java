package org.saberdev;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.saberdev.commands.PouchCommand;
import org.saberdev.gui.struct.GUIListener;
import org.saberdev.gui.struct.GUIManager;
import org.saberdev.handler.PouchHandler;
import org.saberdev.listener.PouchListener;
import org.saberdev.utils.Vault;

@Getter
public final class Pouches extends JavaPlugin {

    public static Pouches instance;
    public static Pouches getInstance() {
        return instance;
    }

    private PouchHandler pouchHandler;
    private GUIManager guiManager;

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        this.reloadConfig();
        pouchHandler = new PouchHandler(this);
        guiManager = new GUIManager();
        getServer().getPluginManager().registerEvents(new GUIListener(this), this);
        getServer().getPluginManager().registerEvents(new PouchListener(this), this);
        getCommand("pouch").setExecutor(new PouchCommand(this));
        Vault.INSTANCE.setupEconomy();
    }

    @Override
    public void onDisable() {
    }
}

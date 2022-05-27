package org.saberdev.utils;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public enum Vault {
    INSTANCE;

    private boolean didSetupEco = false;
    private Economy economy;

    public void setupEconomy() {
        didSetupEco = true;
        final RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) economy = economyProvider.getProvider();
    }

    public Economy getEconomy() {
        if (!didSetupEco) {
            setupEconomy();
        }
        return economy;
    }
}

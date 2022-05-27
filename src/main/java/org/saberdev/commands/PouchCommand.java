package org.saberdev.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.saberdev.Pouches;
import org.saberdev.gui.PouchGUI;
import org.saberdev.utils.StringUtils;

public class PouchCommand implements CommandExecutor {

    private Pouches plugin;

    public PouchCommand(Pouches plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        final Player player = sender != null ? (Player) sender : null;
        if (player == null) return true;
        if (!player.hasPermission("saber.admin")) {
            player.sendMessage(StringUtils.color(plugin.getConfig().getString("language.no permission")));
            return true;
        }
        if (args.length == 0) {
            player.sendMessage(StringUtils.color(""));
            player.sendMessage(StringUtils.color("&b&lSaberPouches &7- Help Page"));
            player.sendMessage(StringUtils.color(""));
            player.sendMessage(StringUtils.color("&b&l * &f/pouch give <player> <xp/money> <pouch name> [amount]"));
            player.sendMessage(StringUtils.color("&b&l * &f/pouch open &7- Open pouch GUI"));
            player.sendMessage(StringUtils.color(""));
            player.sendMessage(StringUtils.color("&b<> &f= Required Arguments"));
            player.sendMessage(StringUtils.color("&b[] &f= Optional Arguments"));
            return true;
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("open")) {
                new PouchGUI(plugin).open(player);
            }
            else {
                player.sendMessage(StringUtils.color(""));
                player.sendMessage(StringUtils.color("&b&lSaberPouches &7- Help Page"));
                player.sendMessage(StringUtils.color(""));
                player.sendMessage(StringUtils.color("&b&l * &f/pouch give <player> <xp/money> <pouch name> [amount]"));
                player.sendMessage(StringUtils.color("&b&l * &f/pouch open &7- Open pouch GUI"));
                player.sendMessage(StringUtils.color(""));
                player.sendMessage(StringUtils.color("&b<> &f= Required Arguments"));
                player.sendMessage(StringUtils.color("&b[] &f= Optional Arguments"));
            }
            return true;
        }
        if (args.length >= 4 && args[0].equalsIgnoreCase("give")) {
            Player target = Bukkit.getPlayer(args[1]) != null ? Bukkit.getPlayer(args[1]) : null;
            if (target == null) {
                player.sendMessage(StringUtils.color("&c&l(!)&c Invalid Player!"));
                return true;
            }
            String type = args[2].equalsIgnoreCase("xp") || args[2].equalsIgnoreCase("money") ? args[2] : null;
            if (type == null) {
                player.sendMessage(StringUtils.color("&c&l(!)&c Invalid Pouch Type!"));
                return true;
            }
            String name = args[3];
            ItemStack item = null;
            if (type.equalsIgnoreCase("xp")) {
                if (plugin.getPouchHandler().getExperiencePouches().containsKey(name)) {
                    item = plugin.getPouchHandler().getExperiencePouches().get(name).build();
                } else {
                    player.sendMessage(StringUtils.color("&c&l(!)&c Invalid Pouch Name!"));
                    return true;
                }
            }
            if (type.equalsIgnoreCase("money")) {
                if (plugin.getPouchHandler().getMoneyPouches().containsKey(name)) {
                    item = plugin.getPouchHandler().getMoneyPouches().get(name).build();
                } else {
                    player.sendMessage(StringUtils.color("&c&l(!)&c Invalid Pouch Name!"));
                    return true;
                }
            }
            int amount = 1;
            if (args.length == 5) {
                amount = Integer.parseInt(args[4]);
            }
            for (int i = 0; i < amount; i++) {
                target.getInventory().addItem(item);
            }
            target.sendMessage(StringUtils.color(plugin.getConfig().getString("language.received pouch").replace("{AMOUNT}", Integer.toString(amount)).replace("{NAME}", name).replace("{TYPE}", type)));
            player.sendMessage(StringUtils.color(plugin.getConfig().getString("language.given pouch").replace("{AMOUNT}", Integer.toString(amount)).replace("{NAME}", name).replace("{TYPE}", type).replace("{PLAYER}", target.getName())));
        } else {
            player.sendMessage(StringUtils.color(""));
            player.sendMessage(StringUtils.color("&b&lSaberPouches &7- Help Page"));
            player.sendMessage(StringUtils.color(""));
            player.sendMessage(StringUtils.color("&b&l * &f/pouch give <player> <xp/money> <pouch name>"));
            player.sendMessage(StringUtils.color("&b&l * &f/pouch open &7- Open pouch GUI"));
            player.sendMessage(StringUtils.color(""));
        }

        return true;
    }
}

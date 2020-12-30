package me.aaron.timer.commands;

import me.aaron.timer.Main;
import me.aaron.timer.utils.Config;
import me.aaron.timer.utils.Permissions;
import me.aaron.timer.utils.SettingsItems;
import me.aaron.timer.utils.SettingsModes;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.IOException;

public class BackpackCommand implements CommandExecutor {
    private Inventory inventory = Bukkit.createInventory(null, 27, "Backpack");

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cKein Konsolenbefehl");
            return false;
        }
        Player p = (Player) sender;
        if (Permissions.hasPermission(p, Permissions.Rank.USER)) {
            if (SettingsModes.settings.get(SettingsItems.ItemType.BACKPACK) == SettingsItems.ItemState.ENABLED) {
                p.openInventory(inventory);
            } else {
                p.sendMessage(Main.getPrefix("Backpack", "Das §9Backpack §7wurde §cdeaktiviert §7du kannst es in §9/settings §7aktivieren."));
            }
        } else {
            p.sendMessage(Main.getPrefix("Backpack", "Du hast §ckeine Berechtigung §7um das Backpack zu benutzen."));
        }

        return false;
    }
}

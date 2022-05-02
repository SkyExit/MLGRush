package de.laurinhummel.mlgrush.commands;

import de.laurinhummel.mlgrush.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

public class SetTeleportY implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        PluginManager pluginManager = Bukkit.getPluginManager();
        FileConfiguration config = Main.getPlugin().getConfig();
        Player player = (Player) sender;

        config.set("Warp.TeleportY.Int", player.getLocation().getY());
        config.set("Warp.TeleportY.World", player.getWorld().getName());
        Main.getPlugin().saveConfig();
        player.sendMessage("TeleportY was set to " + player.getLocation().getY());


        return false;
    }
}

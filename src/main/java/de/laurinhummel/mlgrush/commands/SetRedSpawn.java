package de.laurinhummel.mlgrush.commands;

import de.laurinhummel.mlgrush.main.Main;
import de.laurinhummel.mlgrush.shortcuts.McColors;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

public class SetRedSpawn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        PluginManager pluginManager = Bukkit.getPluginManager();
        FileConfiguration config = Main.getPlugin().getConfig();
        Player player = (Player) sender;
        if(player.isOp()) {
            config.set("Warp.Red.X", player.getLocation().getX());
            config.set("Warp.Red.Y", player.getLocation().getY());
            config.set("Warp.Red.Z", player.getLocation().getZ());
            config.set("Warp.Red.Pitch", player.getLocation().getPitch());
            config.set("Warp.Red.Yaw", player.getLocation().getYaw());
            config.set("Warp.Red.World", player.getWorld().getName());
            Main.getPlugin().saveConfig();
            player.sendRawMessage(McColors.AQUA + "Red spawn location was set!");
        }
        return false;
    }
}
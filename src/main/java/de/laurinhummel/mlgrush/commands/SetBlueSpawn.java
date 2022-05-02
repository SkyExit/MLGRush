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

public class SetBlueSpawn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        PluginManager pluginManager = Bukkit.getPluginManager();
        FileConfiguration config = Main.getPlugin().getConfig();
        Player player = (Player) sender;
        if(player.isOp()) {
            config.set("Warp.Blue.X", player.getLocation().getX());
            config.set("Warp.Blue.Y", player.getLocation().getY());
            config.set("Warp.Blue.Z", player.getLocation().getZ());
            config.set("Warp.Blue.Pitch", player.getLocation().getPitch());
            config.set("Warp.Blue.Yaw", player.getLocation().getYaw());
            config.set("Warp.Blue.World", player.getWorld().getName());
            Main.getPlugin().saveConfig();
            player.sendRawMessage(McColors.AQUA + "Blue spawn location was set!");
        }
        return false;
    }
}

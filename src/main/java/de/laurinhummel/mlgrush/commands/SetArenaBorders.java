package de.laurinhummel.mlgrush.commands;

import de.laurinhummel.mlgrush.main.Main;
import de.laurinhummel.mlgrush.shortcuts.McColors;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SetArenaBorders implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        FileConfiguration config = Main.getPlugin().getConfig();
        switch (args[0]) {
            case "1": {
                if(player.isOp()) {
                    config.set("Arena.1.X", player.getLocation().getX());
                    config.set("Arena.1.Y", player.getLocation().getY());
                    config.set("Arena.1.Z", player.getLocation().getZ());
                    config.set("Arena.1.World", player.getWorld().getName());
                    Main.getPlugin().saveConfig();
                    player.sendRawMessage(McColors.AQUA + "First Arena Border was set!");
                } else {
                    player.sendMessage(McColors.RED + "You must be an operator!");
                }
            }
            break;
            case "2": {
                if(player.isOp()) {
                    config.set("Arena.2.X", player.getLocation().getX());
                    config.set("Arena.2.Y", player.getLocation().getY());
                    config.set("Arena.2.Z", player.getLocation().getZ());
                    config.set("Arena.2.World", player.getWorld().getName());
                    Main.getPlugin().saveConfig();
                    player.sendRawMessage(McColors.AQUA + "Second Arena Border was set!");
                } else {
                    player.sendMessage(McColors.RED + "You must be an operator!");
                }
            }
            break;
            default: player.sendMessage(McColors.AQUA + "Select two opposite corners!");
        }
        return true;
    }
}

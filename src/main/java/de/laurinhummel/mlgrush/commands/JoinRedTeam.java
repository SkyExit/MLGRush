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

public class JoinRedTeam implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        PluginManager pluginManager = Bukkit.getPluginManager();
        FileConfiguration config = Main.getPlugin().getConfig();
        Player player = (Player) sender;

        config.set("Team.Red." + player.getName(), true);
        config.set("Team.Blue." + player.getName(), false);
        Main.getPlugin().saveConfig();

        player.setDisplayName(McColors.RED + "RED >> " + McColors.GOLD + player.getName() + McColors.WHITE);
        player.setPlayerListName(McColors.RED + "RED >> " + McColors.GOLD + player.getName() + McColors.WHITE);

        player.sendMessage(McColors.AQUA + "You joined the " + McColors.RED + "RED" + McColors.AQUA + " Team!");

        return false;
    }
}

package de.laurinhummel.mlgrush.commands;

import de.laurinhummel.mlgrush.main.Main;
import de.laurinhummel.mlgrush.shortcuts.McColors;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetStuff implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        Main.getPlugin().refillInv(player);

        return false;
    }
}

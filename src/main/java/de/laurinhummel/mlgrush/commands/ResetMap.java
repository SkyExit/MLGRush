package de.laurinhummel.mlgrush.commands;

import com.sk89q.worldedit.MaxChangedBlocksException;
import de.laurinhummel.mlgrush.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ResetMap implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            Main.getPlugin().resetMap();
        } catch (MaxChangedBlocksException e) {
            e.printStackTrace();
        }
        return false;
    }
}

package de.laurinhummel.mlgrush.events;

import de.laurinhummel.mlgrush.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.PluginManager;

public class CrossVoidEdge implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        PluginManager pluginManager = Bukkit.getPluginManager();
        FileConfiguration config = Main.getPlugin().getConfig();

        if(!player.getWorld().getName().equals(config.getString("Warp.TeleportY.World"))) return;

        if(player.getLocation().getY() < Float.parseFloat(String.valueOf(config.getDouble("Warp.TeleportY.Int")))) {
            Main.getPlugin().teleportBack(player);
            Main.getPlugin().refillInv(player);
        }
    }
}

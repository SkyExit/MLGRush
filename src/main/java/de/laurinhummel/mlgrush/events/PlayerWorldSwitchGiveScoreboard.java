package de.laurinhummel.mlgrush.events;

import de.laurinhummel.mlgrush.main.Main;
import de.laurinhummel.mlgrush.scoreboard.ScoreboardBuilder;
import de.laurinhummel.mlgrush.scoreboard.ScoreboardFiller;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class PlayerWorldSwitchGiveScoreboard implements Listener {
    @EventHandler
    public void onPlayerWorldSwitch(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        FileConfiguration config = Main.getPlugin().getConfig();

        if(player.getWorld().getName().equals(config.getString("Warp.TeleportY.World"))) {
            player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
            new ScoreboardFiller(player);
        } else {
            player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        FileConfiguration config = Main.getPlugin().getConfig();

        if(player.getWorld().getName().equals(config.getString("Warp.TeleportY.World"))) {
            player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
            new ScoreboardFiller(player);
        }
    }
}


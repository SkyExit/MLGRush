package de.laurinhummel.mlgrush.scoreboard;

import de.laurinhummel.mlgrush.main.Main;
import de.laurinhummel.mlgrush.shortcuts.McColors;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardFiller extends ScoreboardBuilder {
    FileConfiguration config = Main.getPlugin().getConfig();

    public ScoreboardFiller(Player player) {
        super(player, "   " + McColors.AQUA + "MLG" + McColors.GOLD + "Rush   ");
        run();
    }

    @Override
    public void update() {

    }

    private void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                setScore(String.valueOf(config.getInt("Score.Blue")), 3);
                setScore(String.valueOf(config.getInt("Score.Red")), 6);
            }
        }.runTaskTimer(Main.getPlugin(), 20, 20);
    }

    @Override
    public void createScoreboard() {
        setScore(McColors.BLUE + "BLUE TEAM:", 4);
        setScore("blue score", 3);

        setScore("", 5);

        setScore(McColors.RED + "RED TEAM:", 7);
        setScore("red score", 6);
    }
}

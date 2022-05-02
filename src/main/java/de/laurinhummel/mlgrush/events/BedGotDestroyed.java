package de.laurinhummel.mlgrush.events;

import com.sk89q.worldedit.MaxChangedBlocksException;
import de.laurinhummel.mlgrush.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.material.Wool;
import org.bukkit.plugin.PluginManager;

public class BedGotDestroyed implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) throws MaxChangedBlocksException {
        Player player = event.getPlayer();
        PluginManager pluginManager = Bukkit.getPluginManager();
        FileConfiguration config = Main.getPlugin().getConfig();

        if(!player.getWorld().getName().equals(config.getString("Warp.TeleportY.World"))) return;

        if(event.getBlock().getType().equals(Material.RED_BED)) {
            event.setCancelled(true);
            if(event.getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.RED_WOOL) || event.getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.BLUE_WOOL)) {
                Block woolBlock = event.getBlock().getRelative(BlockFace.DOWN);
                BlockState woolState = woolBlock.getState();
                Wool wool = (Wool) woolState.getData();

                if(config.get("Team.Blue." + player.getName()) == null) {
                    player.sendMessage("You need to be in a team!");
                    return;
                }

                if(wool.getColor().equals(DyeColor.RED)) {
                    if(config.get("Team.Blue." + player.getName()).equals(true)) {

                        for(Player p : Bukkit.getOnlinePlayers()) {
                            p.sendMessage(player.getName() + " scored! (blue)");
                        }

                        int blueScore = config.getInt("Score.Blue");
                        if(config.get("Score.Blue") == null) {
                            config.set("Score.Blue", 0);
                        }
                        blueScore++;
                        config.set("Score.Blue", blueScore);
                        Main.getPlugin().saveConfig();

                        for(Player p : Bukkit.getOnlinePlayers()) {
                            if(p.getWorld().getName().equals(config.getString("Warp.TeleportY.World"))) {
                                Main.getPlugin().teleportBack(p);
                                Main.getPlugin().refillInv(p);
                            }
                        }
                        Main.getPlugin().resetMap();

                    } else {
                        player.sendMessage("You cant break your own bed!");
                    }

                } else if(wool.getColor().equals(DyeColor.BLUE)) {
                    if(config.get("Team.Red." + player.getName()).equals(true)) {

                        for(Player p : Bukkit.getOnlinePlayers()) {
                            p.sendMessage(player.getName() + " scored! (red)");
                        }

                        int redeScore = config.getInt("Score.Red");
                        if(config.get("Score.Red") == null) {
                            config.set("Score.Red", 0);
                        }
                        redeScore++;
                        config.set("Score.Red", redeScore);
                        Main.getPlugin().saveConfig();

                        for(Player p : Bukkit.getOnlinePlayers()) {
                            if(p.getWorld().getName().equals(config.getString("Warp.TeleportY.World"))) {
                                Main.getPlugin().teleportBack(p);
                                Main.getPlugin().refillInv(p);
                            }
                        }
                        Main.getPlugin().resetMap();
                    } else {
                        player.sendMessage("You cant break your own bed!");
                    }
                }
            } else {
                player.sendMessage("There must be colored Wool under this bed!");
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        FileConfiguration config = Main.getPlugin().getConfig();
        if(!player.getWorld().getName().equals(config.getString("Warp.TeleportY.World"))) return;

        if(event.getBlock().getType().equals(Main.getPlugin().getSandstone().getType())) {
            return;
        } else {
            if(!player.isOp()) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreakSand(BlockBreakEvent event) {
        Player player = event.getPlayer();
        FileConfiguration config = Main.getPlugin().getConfig();
        if(!player.getWorld().getName().equals(config.getString("Warp.TeleportY.World"))) return;

        if(event.getBlock().getType().equals(Main.getPlugin().getSandstone().getType())) {
            return;
        } else {
            if(!player.isOp()) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBuildOnWool(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        FileConfiguration config = Main.getPlugin().getConfig();
        if(!player.getWorld().getName().equals(config.getString("Warp.TeleportY.World"))) return;

        if (event.getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.LEGACY_WOOL)) {
            if(!player.isOp()) {
                event.setCancelled(true);
            }
        }
    }

}

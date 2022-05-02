package de.laurinhummel.mlgrush.main;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.selector.CuboidRegionSelector;
import com.sk89q.worldedit.world.block.BaseBlock;
import com.sk89q.worldedit.world.block.BlockType;
import com.sk89q.worldedit.world.block.BlockTypes;
import de.laurinhummel.mlgrush.commands.*;
import de.laurinhummel.mlgrush.events.BedGotDestroyed;
import de.laurinhummel.mlgrush.events.CrossVoidEdge;
import de.laurinhummel.mlgrush.events.DoNoDamage;
import de.laurinhummel.mlgrush.events.PlayerWorldSwitchGiveScoreboard;
import de.laurinhummel.mlgrush.scoreboard.ScoreboardBuilder;
import de.laurinhummel.mlgrush.shortcuts.McColors;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashSet;
import java.util.Set;

public final class Main extends JavaPlugin {
    private static Main plugin;

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {

        //Bukkit.getWorld("world").setDifficulty(Difficulty.PEACEFUL);
        //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule keepInventory true");

        PluginManager pluginManager = Bukkit.getPluginManager();
        FileConfiguration config = getPlugin().getConfig();
        getConfig();
        config.addDefault("Team", null);
        config.addDefault("Score", null);
        config.addDefault("Warp", null);
        config.addDefault("Arena", null);
        config.set("Team", null);
        config.set("Score", null);
        config.options().copyDefaults(true);
        saveConfig();

        getCommand("joinblue").setExecutor((CommandExecutor) new JoinBlueTeam());
        getCommand("joinred").setExecutor((CommandExecutor) new JoinRedTeam());
        getCommand("setlobbyspawn").setExecutor((CommandExecutor) new SetLobbySpawn());
        getCommand("setredspawn").setExecutor((CommandExecutor) new SetRedSpawn());
        getCommand("setbluespawn").setExecutor((CommandExecutor) new SetBlueSpawn());
        getCommand("resetmap").setExecutor((CommandExecutor) new ResetMap());
        getCommand("setteleporty").setExecutor((CommandExecutor) new SetTeleportY());
        getCommand("setteleporty").setExecutor((CommandExecutor) new SetTeleportY());
        getCommand("getstuff").setExecutor((CommandExecutor) new GetStuff());
        getCommand("setarenaborders").setExecutor((CommandExecutor) new SetArenaBorders());

        pluginManager.registerEvents((Listener) new BedGotDestroyed(), (Plugin) this);
        pluginManager.registerEvents((Listener) new CrossVoidEdge(), (Plugin) this);
        pluginManager.registerEvents((Listener) new DoNoDamage(), (Plugin) this);
        pluginManager.registerEvents((Listener) new PlayerWorldSwitchGiveScoreboard(), (Plugin) this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Main getPlugin() {
        return plugin;
    }

    public WorldEditPlugin getWorldEditPlugin() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        if(plugin instanceof WorldEditPlugin) return (WorldEditPlugin) plugin;
        else return null;
    }

    public void legacyResetMap() {
        //Replace Sandstone with Air
        FileConfiguration config = Main.getPlugin().getConfig();
        for (Chunk c : Bukkit.getWorld(config.getString("Warp.TeleportY.World")).getLoadedChunks()) {
            int X = c.getX() * 16;
            int Z = c.getZ() * 16;

            for (int x = 0; x < 16; x++) // whole chunk
            {
                for (int z = 0; z < 16; z++) // whole chunk
                {
                    for (int y = 0; y < 40; y++) // any level between 1 - 40
                    {
                        if (c.getWorld().getBlockAt(X + x, y, Z + z).getBlockData().matches(Material.SANDSTONE.createBlockData())) {
                            c.getWorld().getBlockAt(X + x, y, Z + z).setType(Material.AIR);
                        }
                    }
                }
            }
        }
    }

    public void resetMap() throws MaxChangedBlocksException {
        FileConfiguration config = Main.getPlugin().getConfig();
        double X1 = config.getDouble("Arena.1.X");
        double Y1 = config.getDouble("Arena.1.Y");
        double Z1 = config.getDouble("Arena.1.Z");
        double X2 = config.getDouble("Arena.2.X");
        double Y2 = config.getDouble("Arena.2.Y");
        double Z2 = config.getDouble("Arena.2.Z");
        com.sk89q.worldedit.world.World world = BukkitAdapter.adapt(Bukkit.getWorld(config.getString("Arena.1.World")));

        BlockVector3 vec1 = BlockVector3.at(X1, Y1, Z1);
        BlockVector3 vec2 = BlockVector3.at(X2, Y2, Z2);

        CuboidRegion region = new CuboidRegion(world, vec1, vec2);
        Set<BaseBlock> s1 = new HashSet<BaseBlock>();
            s1.add(BlockTypes.SANDSTONE.getDefaultState().toBaseBlock());

        EditSession editSession = WorldEdit.getInstance().newEditSession(world);
            editSession.replaceBlocks(region, s1, BlockTypes.AIR.getDefaultState());
            editSession.commit();
            editSession.close();
    }

    public void teleportBack(Player p) {
        //Teleport every

        //for (Player p : Bukkit.getOnlinePlayers()) {
            if(getConfig().get("Team.Blue." + p.getName()) == null) {
                double x = getConfig().getDouble("Warp.Lobby.X");
                double y = getConfig().getDouble("Warp.Lobby.Y");
                double z = getConfig().getDouble("Warp.Lobby.Z");
                Location loc = new Location(p.getWorld(), x, y, z);
                p.teleport(loc);
                return;
            }

            if(getConfig().get("Team.Blue." + p.getName()).equals(true)) {
                double x = getConfig().getDouble("Warp.Blue.X");
                double y = getConfig().getDouble("Warp.Blue.Y");
                double z = getConfig().getDouble("Warp.Blue.Z");
                Location loc = new Location(p.getWorld(), x, y, z);
                loc.setPitch(Float.parseFloat(String.valueOf(getConfig().getDouble("Warp.Blue.Pitch"))));
                loc.setYaw(Float.parseFloat(String.valueOf(getConfig().getDouble("Warp.Blue.Yaw"))));
                p.teleport(loc);
            } else {
                double x = getConfig().getDouble("Warp.Red.X");
                double y = getConfig().getDouble("Warp.Red.Y");
                double z = getConfig().getDouble("Warp.Red.Z");
                Location loc = new Location(p.getWorld(), x, y, z);
                loc.setPitch(Float.parseFloat(String.valueOf(getConfig().getDouble("Warp.Red.Pitch"))));
                loc.setYaw(Float.parseFloat(String.valueOf(getConfig().getDouble("Warp.Red.Yaw"))));
                p.teleport(loc);
            }

        //}
    }

    public void refillInv(Player player) {
        player.getInventory().clear();

        ItemStack stick = new ItemStack(Material.STICK);
        ItemMeta stickMeta = stick.getItemMeta();
        stickMeta.setDisplayName(McColors.AQUA + "STICK");
        stickMeta.addEnchant(Enchantment.KNOCKBACK, 2, true);
        stick.setItemMeta(stickMeta);

        ItemStack pickaxe = new ItemStack(Material.STONE_PICKAXE);
        ItemMeta pickaxeMeta = pickaxe.getItemMeta();
        pickaxeMeta.setDisplayName(McColors.AQUA + "PICKAXE");
        pickaxeMeta.addEnchant(Enchantment.DIG_SPEED, 1, true);
        pickaxe.setItemMeta(pickaxeMeta);

        //ItemStack sandstone = new ItemStack(Material.SANDSTONE, 32);
        //sandstone.setDurability((short) 2);

        player.getInventory().addItem(stick);
        player.getInventory().addItem(getSandstone());
        player.getInventory().addItem(pickaxe);
    }

    public ItemStack getSandstone() {
        ItemStack sandstone = new ItemStack(Material.SANDSTONE, 32);
        sandstone.setDurability((short) 2);

        return sandstone;
    }
}

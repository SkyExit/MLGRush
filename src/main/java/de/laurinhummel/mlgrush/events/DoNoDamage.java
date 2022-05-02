package de.laurinhummel.mlgrush.events;

import de.laurinhummel.mlgrush.main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DoNoDamage implements Listener {
    @EventHandler
    public void onDamageDone(EntityDamageByEntityEvent event) {
        FileConfiguration config = Main.getPlugin().getConfig();

        Player player = (Player) event.getEntity();
        Player damager = (Player) event.getDamager();

        if(!player.getWorld().getName().equals(config.getString("Warp.TeleportY.World"))) return;

        if(config.get("Team.Red." + player.getName()) == null) {
            return;
        }

        if(config.get("Team.Red." + player.getName()).equals(config.get("Team.Red." + damager.getName()))) {
            event.setCancelled(true);
        } else {
            event.setDamage(0);
        }
    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent e) {
        FileConfiguration config = Main.getPlugin().getConfig();
        Player player = (Player) e.getEntity();
        if(!player.getWorld().getName().equals(config.getString("Warp.TeleportY.World"))) return;

        if(e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
            e.setCancelled(true);
        }
    }
}

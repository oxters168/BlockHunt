package nl.Steffion.BlockHunt.Listeners;

import nl.Steffion.BlockHunt.Arena;
import nl.Steffion.BlockHunt.Arena.ArenaState;
import nl.Steffion.BlockHunt.W;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class OnEntityDamageByEntityEvent implements Listener {

	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		Entity ent = event.getEntity();
		Player player = null;
		if (ent instanceof Player) {
			player = (Player) ent;
		}

		if (player != null) {
			for (Arena arena : W.arenaList) {
				if (arena.playersInArena.contains(player)) {
					if (arena.gameState == ArenaState.WAITING
							|| arena.gameState == ArenaState.STARTING) {
						event.setCancelled(true);
					} else {
						if (arena.seekers.contains(player)
								&& arena.seekers.contains(event.getDamager())) {
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}
}

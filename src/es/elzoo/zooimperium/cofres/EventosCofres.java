package es.elzoo.zooimperium.cofres;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;


public class EventosCofres implements Listener {
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK || e.getClickedBlock().getType() != Material.CHEST) {
			return;
		}
		e.setCancelled(true);
		Cofre.abrirCofres(e.getPlayer(), e.getClickedBlock().getLocation());
	}
}

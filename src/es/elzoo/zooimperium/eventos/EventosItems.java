package es.elzoo.zooimperium.eventos;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import es.elzoo.zooimperium.utiles.UtilesPlayers;

public class EventosItems implements Listener {
	String name = "Bendaje";
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
		Player player = event.getPlayer();
		ItemStack hand = player.getInventory().getItemInMainHand();
//		ItemMeta meta = hand.getItemMeta();
//		if(meta.hasDisplayName() && meta.getDisplayName().equalsIgnoreCase(name)){
//			UtilesPlayers.curar(player, 4);
//		}
		if (hand.getType().equals(Material.PAPER)) {
			hand.add(-1);
			UtilesPlayers.curar(player, 1);
		} else if (hand.getType().equals(Material.PINK_DYE)) {
			hand.add(-1);
			UtilesPlayers.curar(player, 2);
		} else if (hand.getType().equals(Material.RED_DYE)) {
			hand.add(-1);
			UtilesPlayers.curar(player, 3);
		}
	}
}

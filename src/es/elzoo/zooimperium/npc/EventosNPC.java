package es.elzoo.zooimperium.npc;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

import es.elzoo.zooimperium.npc.npc.subasta.NPCSubasta;
import es.elzoo.zooimperium.npc.npc.tiendas.NPCTiendaMateriales;

public class EventosNPC implements Listener{
	@EventHandler(ignoreCancelled=true)
	public void onPlayerInteractAtNpc(PlayerInteractAtEntityEvent event) {
		if(!event.getRightClicked().hasMetadata("NPC")) {
			return;
		}
		
		if(!event.getHand().equals(EquipmentSlot.HAND)) {
			return;
		}
		
		String nombre = event.getRightClicked().getName();	
		
		if(nombre.contains("Gestor")) {
			NPCSubasta.abrirInterfazMain(event.getPlayer());
		} else if(nombre.contains("materiales")) {
			NPCTiendaMateriales.abrirInterfaz(event.getPlayer());
		} else if(nombre.contains("Vendedor de comida")) {
			
		} else if(nombre.contains("Vendedor de cultivos")) {
		} else if(nombre.contains("Vendedor de maderas")) {
		}
	}
}

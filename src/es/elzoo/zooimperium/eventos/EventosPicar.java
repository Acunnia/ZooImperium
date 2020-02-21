package es.elzoo.zooimperium.eventos;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class EventosPicar implements Listener {
	public void BlockBreakEvent(Block block, Player player) {
		Bukkit.getLogger().info("El plugin ha sido cargado");	
		if (block.getBlockData().getMaterial().equals(Material.STONE)){
			block.setType(Material.BEDROCK);
		}
		return;		
	}
}

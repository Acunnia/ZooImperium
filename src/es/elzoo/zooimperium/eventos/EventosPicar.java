package es.elzoo.zooimperium.eventos;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class EventosPicar implements Listener {
	public static Plugin plugin = Bukkit.getPluginManager().getPlugin("ZooImperium");
    @EventHandler
    public static void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer(); 
        if(player.getGameMode().equals(GameMode.CREATIVE)) {
        	return;
        }
        
        Block block = event.getBlock();											// GUARDAMOS EL BLOQUE Y EL JUGADOR DEL EVENTO
        
        ItemStack drop;															// INSTANCIAMOS UN ITEM DE INVENTARIO VACIO
        
        if (block.getBlockData().getMaterial().equals(Material.STONE)){			// COMPROBAMOS SI ESTÁ PICANDO PIEDRA
        	drop = new ItemStack(Material.COBBLESTONE);							// COMO ESTÁ PICANDO PIEDRA, CREAMOS EL ITEM DROP
			player.getWorld().dropItemNaturally(block.getLocation(), drop);		// DROPEA EL COSO EN LA POSICION DEL BLOQUE
        	event.setCancelled(true);											// CANCELA EL EVENTO DE PICAR
            block.getLocation().getBlock().setType(Material.COBBLESTONE);			// COLOCA BEDROCK EN EL BLOQUE PICADO
            Bukkit.getScheduler().runTaskLater(plugin, () -> {				// PREPARA UNA TAREA FUTURA, NECESITA COMO PARAMETRO EL PLUGIN
            	if (block.getBlockData().getMaterial().equals(Material.COBBLESTONE)) {
					block.getLocation().getBlock().setType(Material.STONE);
				}																// ACCION DE LA TAREA FUTURA (PONER EL BLOQUE ORIGINAL)
            }, 100L);															// TIEMPO QUE VA A TRANSCURRIR HASTA REALIZAR LA TAREA
        } else if (block.getBlockData().getMaterial().equals(Material.COBBLESTONE)){			
        	drop = new ItemStack(Material.COBBLESTONE);							
			player.getWorld().dropItemNaturally(block.getLocation(), drop);		
        	event.setCancelled(true);										
            block.getLocation().getBlock().setType(Material.BEDROCK);	
            Bukkit.getScheduler().runTaskLater(plugin, () -> {		
                block.getLocation().getBlock().setType(Material.STONE);	
            }, 100L);	
        }
        return;
    }
}
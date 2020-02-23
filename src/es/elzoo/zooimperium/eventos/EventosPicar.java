package es.elzoo.zooimperium.eventos;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import es.elzoo.zooimperium.IPlayer;

public class EventosPicar implements Listener {
	private static Set<Location> stone = new HashSet<Location>();
	
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
        	IPlayer.getPlayer(player.getName()).get().subirExp(0.05);
        	addStone(block.getLocation());
        	drop = new ItemStack(Material.COBBLESTONE);							// COMO ESTÁ PICANDO PIEDRA, CREAMOS EL ITEM DROP
			player.getWorld().dropItemNaturally(block.getLocation(), drop);		// DROPEA EL COSO EN LA POSICION DEL BLOQUE
        	event.setCancelled(true);											// CANCELA EL EVENTO DE PICAR
            block.setType(Material.COBBLESTONE);			// COLOCA BEDROCK EN EL BLOQUE PICADO
            Bukkit.getScheduler().runTaskLater(plugin, () -> {				// PREPARA UNA TAREA FUTURA, NECESITA COMO PARAMETRO EL PLUGIN
            	if (block.getBlockData().getMaterial().equals(Material.COBBLESTONE)) {
					block.setType(Material.STONE);
				}																// ACCION DE LA TAREA FUTURA (PONER EL BLOQUE ORIGINAL)
            }, 100L);															// TIEMPO QUE VA A TRANSCURRIR HASTA REALIZAR LA TAREA
        } else if (block.getBlockData().getMaterial().equals(Material.COBBLESTONE)){			
        	drop = new ItemStack(Material.COBBLESTONE);	
        	IPlayer.getPlayer(player.getName()).get().subirExp(0.05);
			player.getWorld().dropItemNaturally(block.getLocation(), drop);		
        	event.setCancelled(true);										
            block.setType(Material.BEDROCK);	
            Bukkit.getScheduler().runTaskLater(plugin, () -> {		
            	if (isStone(block.getLocation())) {
            		block.setType(Material.STONE);	
				}	else {
					block.setType(Material.COBBLESTONE);
				}
            	
            }, 100L);	
        }
        return;
    }
    
    public static void addStone(Location loc) {
    	EventosPicar.stone.add(loc);
    }
    
    public static boolean isStone(Location loc) {
    	return EventosPicar.stone.contains(loc);
    }
}
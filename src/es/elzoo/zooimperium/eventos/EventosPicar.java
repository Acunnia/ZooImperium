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
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import es.elzoo.zooimperium.IPlayer;

public class EventosPicar implements Listener {
	private static Set<Location> stone = new HashSet<Location>();
	
	public static Plugin plugin = Bukkit.getPluginManager().getPlugin("ZooImperium");
	
	@EventHandler
	public static void onBlockPlace(BlockPlaceEvent event) {
		if(!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
        	event.setCancelled(true);
        }
	}
	
    @EventHandler
    public static void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer(); 
        if(player.getGameMode().equals(GameMode.CREATIVE)) {
        	return;
        }
        Material hand = event.getPlayer().getInventory().getItemInMainHand().getType();
        Bukkit.getLogger().info(hand.toString());
        
        Block block = event.getBlock();											// GUARDAMOS EL BLOQUE Y EL JUGADOR DEL EVENTO
        
        ItemStack drop;
        
        Material last = block.getType();
        
        switch (hand) {
		case DIAMOND_PICKAXE:
		case GOLDEN_PICKAXE:
		case IRON_PICKAXE:
		case STONE_PICKAXE:
		case WOODEN_PICKAXE:
			switch (last) {
			case STONE:
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
	            }, 100L);							
				break;
			case COBBLESTONE:
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
				break;
			case COAL_ORE:
				drop = new ItemStack(Material.COAL);	
	        	IPlayer.getPlayer(player.getName()).get().subirExp(0.2);
				player.getWorld().dropItemNaturally(block.getLocation(), drop);		
	        	event.setCancelled(true);										
	            block.setType(Material.STONE);
				break;
			case DIAMOND_ORE:
				drop = new ItemStack(Material.DIAMOND);	
	        	IPlayer.getPlayer(player.getName()).get().subirExp(10);
				player.getWorld().dropItemNaturally(block.getLocation(), drop);		
	        	event.setCancelled(true);										
	            block.setType(Material.STONE);
				break;
			case EMERALD_ORE:
				drop = new ItemStack(Material.EMERALD);	
	        	IPlayer.getPlayer(player.getName()).get().subirExp(100);
				player.getWorld().dropItemNaturally(block.getLocation(), drop);		
	        	event.setCancelled(true);										
	            block.setType(Material.STONE);	
				break;
			case GOLD_ORE:
				drop = new ItemStack(Material.GOLD_ORE);	
	        	IPlayer.getPlayer(player.getName()).get().subirExp(3);
				player.getWorld().dropItemNaturally(block.getLocation(), drop);		
	        	event.setCancelled(true);										
	            block.setType(Material.STONE);	
				break;
			case IRON_ORE:
				drop = new ItemStack(Material.IRON_ORE);	
	        	IPlayer.getPlayer(player.getName()).get().subirExp(0.5);
				player.getWorld().dropItemNaturally(block.getLocation(), drop);		
	        	event.setCancelled(true);										
	            block.setType(Material.STONE);	
				break;
			case LAPIS_ORE:
				drop = new ItemStack(Material.LAPIS_LAZULI);	
	        	IPlayer.getPlayer(player.getName()).get().subirExp(0.2);
				player.getWorld().dropItemNaturally(block.getLocation(), drop);		
	        	event.setCancelled(true);										
	            block.setType(Material.STONE);	
				break;
			case REDSTONE_ORE:
				drop = new ItemStack(Material.REDSTONE);	
	        	IPlayer.getPlayer(player.getName()).get().subirExp(0.2);
				player.getWorld().dropItemNaturally(block.getLocation(), drop);		
	        	event.setCancelled(true);										
	            block.setType(Material.STONE);	
				break;
			case NETHER_QUARTZ_ORE:
				drop = new ItemStack(Material.QUARTZ);	
	        	IPlayer.getPlayer(player.getName()).get().subirExp(0.2);
				player.getWorld().dropItemNaturally(block.getLocation(), drop);		
	        	event.setCancelled(true);										
	            block.setType(Material.STONE);	
				break;
			default:
				event.setCancelled(true);
				break;
			}

		default:
			switch (last) {
			case ACACIA_WOOD:
			case ACACIA_LOG:
			case BIRCH_WOOD:	
			case BIRCH_LOG:
			case DARK_OAK_WOOD:	
			case DARK_OAK_LOG:
			case JUNGLE_WOOD:
			case JUNGLE_LOG:
			case OAK_LOG:
			case OAK_WOOD:
			case SPRUCE_WOOD:
			case SPRUCE_LOG:
			case PUMPKIN:
			case MELON:
			case HAY_BLOCK:
				drop = new ItemStack(block.getType());	
	        	IPlayer.getPlayer(player.getName()).get().subirExp(0.1);
				player.getWorld().dropItemNaturally(block.getLocation(), drop);		
	        	event.setCancelled(true);			
	            block.setType(Material.AIR);
	            if (last != Material.PUMPKIN && last != Material.MELON && last != Material.HAY_BLOCK) {
	            	Bukkit.getScheduler().runTaskLater(plugin, () -> {	
	            		block.setType(last);
	            	}, 100L);
				}	
	            break;
			case SAND:
			case GRAVEL:
			case RED_SAND:
				drop = new ItemStack(block.getType());	
	        	IPlayer.getPlayer(player.getName()).get().subirExp(0.01);
				player.getWorld().dropItemNaturally(block.getLocation(), drop);		
	        	event.setCancelled(true);
	            block.setType(Material.AIR);
	            Bukkit.getScheduler().runTaskLater(plugin, () -> {	
	            	block.setType(last);
	            }, 100L);
	            break;
			default:
				event.setCancelled(true);
				break;
			}
			break;
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
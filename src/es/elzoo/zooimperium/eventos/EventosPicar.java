package es.elzoo.zooimperium.eventos;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class EventosPicar implements Listener {
    @EventHandler
    public static void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getBlockData().getMaterial().equals(Material.STONE)){
            event.setCancelled(true);
//            block.getDrops();
            block.getLocation().getBlock().setType(Material.BEDROCK);
        }
        return;
    }
}
package es.elzoo.zooimperium.npc.npc.tiendas;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import es.elzoo.zooimperium.npc.inventarios.tiendas.InvTiendaMateriales;

public class NPCTiendaMateriales {
	public static void abrirInterfaz(Player player) {	
		InvTiendaMateriales inv = new InvTiendaMateriales(new ItemStack(Material.AIR), player);
		inv.abrir(player);
	}
}

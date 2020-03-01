package es.elzoo.zooimperium.npc.inventarios.subasta;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import es.elzoo.zooimperium.IPlayer;
import es.elzoo.zooimperium.utiles.gui.GUI;
import net.md_5.bungee.api.ChatColor;

public class InvSubastaMain extends GUI	{
//	private IPlayer iPlayer;
	
	public InvSubastaMain(IPlayer IP) {
		super(36, "Casa de subastas");
//		this.iPlayer = IP;
		ponerItems();
	}
	
	private void ponerItems() {
		for (int i = 0; i < 36; i++) {
			ItemStack item = null;
			if (i == 11) {
				item = GUI.crearItem(Material.GOLD_BLOCK, ChatColor.GOLD+"Explorador de subastas");
				ItemMeta meta = item.getItemMeta();
				meta.setLore(Arrays.asList(new String[] {
						ChatColor.GRAY +"Encuentra items que otros", 
						ChatColor.GRAY +"jugadores pongan a la venta",
						"",
						ChatColor.GRAY +"Los items ofertados aquí",
						ChatColor.GRAY +"son bajo " +ChatColor.GOLD + "subasta" + ChatColor.GRAY + ", lo que",
						ChatColor.GRAY +"quiere decir que tienes que",
						ChatColor.GRAY +"ser el máximo postor para",
						ChatColor.GRAY +"llevartelo."
				}));
				item.setItemMeta(meta);
			} else if (i == 13) {
				item = GUI.crearItem(Material.GOLDEN_CARROT, ChatColor.GOLD+"Tus pujas");
			} else if (i == 15) {
				item = GUI.crearItem(Material.GOLD_BLOCK, ChatColor.GREEN+"Crear subasta");
				ItemMeta meta = item.getItemMeta();
				meta.setLore(Arrays.asList(new String[] {
						ChatColor.GRAY +"Pon tus items a la venta para", 
						ChatColor.GRAY +"que los compren otros jugadores",
						ChatColor.GRAY +"",
						ChatColor.GOLD+"¡¡CLICK PARA HACERTE RICO!!"
				}));
				item.setItemMeta(meta);
			} else {
				item = GUI.crearItem(Material.BLACK_STAINED_GLASS_PANE, "");
			}
			ponerItem(i, item);
		}
	}
	
}

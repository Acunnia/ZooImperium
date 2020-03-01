package es.elzoo.zooimperium.npc.inventarios.tiendas;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import es.elzoo.zooimperium.IPlayer;
import es.elzoo.zooimperium.utiles.gui.GUI;

public class InvTiendaMateriales extends GUI {
	private IPlayer ip;
	private ItemStack itemVendido;

	public InvTiendaMateriales(ItemStack vender, Player pl) {
		super(54, "Tienda de materiales");
		this.ip = IPlayer.getPlayer(pl.getName()).get();
		this.itemVendido = vender;
		ponerItems();
	}
	
	public void ponerItems() {
		for (int i = 0; i < 54; i++) {
			ItemStack item = new ItemStack(Material.AIR);
			if (i < 9 || i > 44 || i%9 == 0 || i%9 == 8) {
				item = crearItem(Material.ORANGE_STAINED_GLASS_PANE, "");
			}
			if (i == 49) {
				item = this.itemVendido;
			}
			ponerItem(i, item);
		}
	}
	
	@Override
	public void onClick(InventoryClickEvent e) {
		super.onClick(e);
		if (e.getRawSlot() > 54) {										 	// Inv usuario
			this.itemVendido = e.getCurrentItem();
			ponerItems();
			e.getCurrentItem().setAmount(0);
			ip.darDinero(100);
			e.setCancelled(true);
		} else {															// Inv tienda
			int i = e.getRawSlot();
			if (i < 9 || (i > 44 && i != 49) || i%9 == 0 || i%9 == 8) {
				e.setCancelled(true);
			} else if (i == 49) {											// Devolver
				
			}
		}
	}

}

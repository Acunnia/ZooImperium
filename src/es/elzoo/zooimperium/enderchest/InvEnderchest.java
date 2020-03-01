package es.elzoo.zooimperium.enderchest;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import es.elzoo.zooimperium.utiles.gui.GUI;
import net.md_5.bungee.api.ChatColor;

public class InvEnderchest extends GUI {
	private Enderchest enderchest;
	
	public InvEnderchest(Enderchest ec) {
		super(27, ChatColor.BLUE + "Enderchest +");
		this.enderchest = ec;
		
		int tam = this.enderchest.getListItems().size();
		
		for (int i = 0; i < 27; i++) {
			ItemStack item = new ItemStack(Material.AIR);
			if (i == 12) {
				if (tam > 0) item = this.enderchest.getListItems().get(0);			
			} else if (i == 13) {
				if (tam > 1) item = this.enderchest.getListItems().get(1);
			} else if (i == 14) {
				if (tam > 2) item = this.enderchest.getListItems().get(2);
			} else if (i == 4 && enderchest.getNivel() > 0) {
				if (tam > 3) item = this.enderchest.getListItems().get(3);
			} else if (i == 22 && enderchest.getNivel() > 1) {
				if (tam > 4) item = this.enderchest.getListItems().get(4);
			} else if (i == 3 && enderchest.getNivel() > 2) {
				if (tam > 5) item = this.enderchest.getListItems().get(5);
			} else if (i == 23 && enderchest.getNivel() > 3) {
				if (tam > 6) item = this.enderchest.getListItems().get(6);
			} else if (i == 5 && enderchest.getNivel() > 4) {
				if (tam > 7) item = this.enderchest.getListItems().get(7);
			} else if (i == 21 && enderchest.getNivel() > 5) {
				if (tam > 8)item = this.enderchest.getListItems().get(8);
			} else {
				item = crearItem(Material.BLACK_STAINED_GLASS_PANE, "");
			}
			ponerItem(i, item);
		}
	}
	
	@Override
	public void onClose(InventoryCloseEvent e) {		
		super.onClose(e);
		
		if(e.getInventory().getViewers().size() <= 1) {
			
			List<ItemStack> paraGuardar = new ArrayList<ItemStack>();
			paraGuardar.add(this.getInventario().getItem(12));
			paraGuardar.add(this.getInventario().getItem(13));
			paraGuardar.add(this.getInventario().getItem(14));
			int nivel = enderchest.getNivel();
			if (nivel > 0) {
				paraGuardar.add(this.getInventario().getItem(4));
			}
			if (nivel > 1) {
				paraGuardar.add(this.getInventario().getItem(22));
			}
			if (nivel > 2) {
				paraGuardar.add(this.getInventario().getItem(3));

			}
			if (nivel > 3) {
				paraGuardar.add(this.getInventario().getItem(23));
			}
			if (nivel > 4) {
				paraGuardar.add(this.getInventario().getItem(5));
			}
			if (nivel > 5) {
				paraGuardar.add(this.getInventario().getItem(21));
			}
			
			enderchest.setListItems(paraGuardar);
			enderchest.guardarInventario();
		}		
	}
	
	@Override
	public void onClick(InventoryClickEvent e) {
		super.onClick(e);
		int nivel = this.enderchest.getNivel();
		if (e.getRawSlot() > 26) {
			e.setCancelled(false);
		} else if (e.getSlot()%9 < 3 ) {
			e.setCancelled(true);
		} else if (e.getSlot()%9 > 5) {
			e.setCancelled(true);
		} else if (e.getSlot() == 4 && nivel < 1) {
			e.setCancelled(true);
		} else if (e.getSlot() == 22 && nivel < 2) {
			e.setCancelled(true);
		} else if (e.getSlot() == 3 && nivel < 3) {
			e.setCancelled(true);
		} else if (e.getSlot() == 23 && nivel < 4) {
			e.setCancelled(true);
		} else if (e.getSlot() == 5 && nivel < 5) {
			e.setCancelled(true);
		} else if (e.getSlot() == 21 && nivel < 6) {
			e.setCancelled(true);
		} else {
			e.setCancelled(false);
		}
	}
	
}

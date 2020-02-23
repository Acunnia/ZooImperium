package es.elzoo.zooimperium.cofres;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import es.elzoo.zooimperium.utiles.gui.GUI;

public class InvCofres extends GUI {
	private static List<InvCofres> invsAbiertos = new ArrayList<InvCofres>();
	
	private int estado;
	private List<ItemStack> items;
	private String tipo;
	
	public void updateProp(int estado, List<ItemStack> items, String tipo) {
		this.estado = estado;
		this.items = items;
		this.tipo = tipo;
	}
	
	public InvCofres(int estado, List<ItemStack> items, String tipo) {
		super(54, "Cofre loot");
		this.estado = estado;
		this.items = items;
		this.tipo = tipo;
		ponerItems();
	}
	
	public static void updateCofre() {
		invsAbiertos.parallelStream().forEach(InvCofres::ponerItems);
	}
	
	@Override
	public void onClose(InventoryCloseEvent e) {
		super.onClose(e);
		
		invsAbiertos.remove(this);
	}
	
	@Override
	public void abrir(Player player) {
		super.abrir(player);
		
		invsAbiertos.add(this);
	}
	
	private void ponerItems() {
		for (int i = 0; i < 54; i++) {
			ItemStack item = null;
			if (i==10) {
				if (estado < 1) {
					item = GUI.crearItem(Material.GLASS_PANE, "Registrando...");
				} else {
					item = items.get(0);
				}
			} else if (i == 19) {
				if (estado < 2) {
					item = GUI.crearItem(Material.GLASS_PANE, "Registrando...");
				} else {
					item = items.get(1);
				}
			} else if (i == 28) {
				if (estado < 3) {
					item = GUI.crearItem(Material.GLASS_PANE, "Registrando...");
				} else {
					item = items.get(2);
				}
			} else if (i == 37) {
				if (estado < 4) {
					item = GUI.crearItem(Material.GLASS_PANE, "Registrando...");
				} else {
					item = items.get(3);
				}
			} else if (i == 12) {
				if (estado < 5) {
					item = GUI.crearItem(Material.GLASS_PANE, "Registrando...");
				} else {
					item = items.get(4);
				}
			} else if (i == 13) {
				if (estado < 6) {
					item = GUI.crearItem(Material.GLASS_PANE, "Registrando...");
				} else {
					item = items.get(5);
				}
			} else if (i == 14) {
				if (estado < 7) {
					item = GUI.crearItem(Material.GLASS_PANE, "Registrando...");
				} else {
					item = items.get(6);
				}
			} else if (i == 21) {
				if (estado < 8) {
					item = GUI.crearItem(Material.GLASS_PANE, "Registrando...");
				} else {
					item = items.get(7);
				}
			} else if (i == 22) {
				if (estado < 9) {
					item = GUI.crearItem(Material.GLASS_PANE, "Registrando...");
				} else {
					item = items.get(8);
				}
			} else if (i == 23) {
				if (estado < 10) {
					item = GUI.crearItem(Material.GLASS_PANE, "Registrando...");
				} else {
					item = items.get(9);
				}
			} else if (i == 30) {
				if (estado < 11) {
					item = GUI.crearItem(Material.GLASS_PANE, "Registrando...");
				} else {
					item = items.get(10);
				}
			} else if (i == 31) {
				if (estado < 12) {
					item = GUI.crearItem(Material.GLASS_PANE, "Registrando...");
				} else {
					item = items.get(11);
				}
			} else if (i == 32) {
				if (estado < 13) {
					item = GUI.crearItem(Material.GLASS_PANE, "Registrando...");
				} else {
					item = items.get(12);
				}
			} else if (i == 39) {
				if (estado < 14) {
					item = GUI.crearItem(Material.GLASS_PANE, "Registrando...");
				} else {
					item = items.get(13);
				}
			} else if (i == 40) {
				if (estado < 15) {
					item = GUI.crearItem(Material.GLASS_PANE, "Registrando...");
				} else {
					item = items.get(14);
				}
			} else if (i == 41) {
				if (estado < 16) {
					item = GUI.crearItem(Material.GLASS_PANE, "Registrando...");
				} else {
					item = items.get(15);
				}
			} else if (i == 16) {
				if (estado < 17) {
					item = GUI.crearItem(Material.GLASS_PANE, "Registrando...");
				} else {
					item = items.get(16);
				}
			} else if (i == 25) {
				if (estado < 18) {
					item = GUI.crearItem(Material.GLASS_PANE, "Registrando...");
				} else {
					item = items.get(17);
				}
			} else if (i == 34) {
				if (estado < 19) {
					item = GUI.crearItem(Material.GLASS_PANE, "Registrando...");
				} else {
					item = items.get(18);
				}
			} else if (i == 43) {
				if (estado < 20) {
					item = GUI.crearItem(Material.GLASS_PANE, "Registrando...");
				} else {
					item = items.get(19);
				}
			} else if (i == 11 || i == 20 || i == 29 || i == 38 || i == 15 || i == 24 || i == 33 || i == 42) {
				switch (tipo) {
				case "comida":
					item = GUI.crearItem(Material.GREEN_STAINED_GLASS_PANE, "");
					break;
				default:
					item = GUI.crearItem(Material.BLUE_STAINED_GLASS_PANE, "");
					break;
				}
			} else {
				item = GUI.crearItem(Material.BLACK_STAINED_GLASS_PANE, "");	
			}
			ponerItem(i, item);
			
		}
	}

}

package es.elzoo.zooimperium.cofres;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import es.elzoo.zooimperium.utiles.gui.GUI;

public class InvCofres extends GUI {
	private static List<InvCofres> invsAbiertos = new ArrayList<InvCofres>();
	
	private int estado;
	private List<ItemStack> items;
	private CofreType tipo;
	private Cofre cofre;
	
	public void updateProp(int estado) {
		this.estado = estado;
	}
	
	public void updateItems(Location loc, int pos, ItemStack item) {
		Cofre.getCofreFromLoc(loc).setItems(pos, item);
	}
	
	public InvCofres(int estado, List<ItemStack> items, CofreType tipo, Cofre cofre) {
		super(54, "Cofre loot");
		this.cofre = cofre;
		this.estado = estado;
		this.items = items;
		this.tipo = tipo;
		ponerItems();
	}
	
	public Cofre getCofre() {
		return this.cofre;
	}
	
	public static void updateCofre() {
		invsAbiertos.parallelStream().forEach(InvCofres::ponerItems);
	}
	
	@Override
	public void onDrag(InventoryDragEvent e) {
		if(e.getInventory() instanceof PlayerInventory) {
			e.setCancelled(false);
		} else {
			boolean ok = true;
			Set<Integer> slots = e.getInventorySlots();
			for(Integer slot : slots) {
				if(slot < estadoToSlot(estado)+1 && slot > 8 && (slot%9==1 || slot%9==3 || slot%9==4 || slot%9==5 || slot%9==7)) {
					ok = false;
				}
			}
			if(!ok) {
				e.setCancelled(true);
			}
		}
	}
	
	@Override
	public void onClick(InventoryClickEvent e) {
		int slot = e.getSlot();
		int rslot = e.getRawSlot();
		if(rslot > 8 && rslot < estadoToSlot(this.estado)+1) {
			if(rslot%9==1 || rslot%9==3 || rslot%9==4 || rslot%9==5 || rslot%9==7) {
				e.setCancelled(false);
				Bukkit.getLogger().info(e.getAction().name());
				switch (e.getAction()) {
				case PICKUP_ALL:
					items.set(deSlotAEstado(slot), null);
					break;
				case MOVE_TO_OTHER_INVENTORY:
					items.set(deSlotAEstado(slot), null);
					break;
					
				case PLACE_ALL:
					updateItems(cofre.getLocation(), slot, e.getCursor());
					break;
					
				default:
					e.setCancelled(true);
					break;
				}
				
//				if (!(this.cofre.getItems().get(deSlotAEstado(slot)) == null)) {
//					Bukkit.getLogger().info("CONDICIONAL 1 ");
//					this.cofre.getItems().set(deSlotAEstado(slot), null);
//				} else if(e.getCurrentItem() != null) {
//					Bukkit.getLogger().info("CONDICIONAL 2 ");
//					this.cofre.getItems().set(deSlotAEstado(slot), e.getCurrentItem());
//				} else {
//					Bukkit.getLogger().info("CONDICIONAL 3 ");
//					Bukkit.getLogger().info(e.getCursor().toString());
//					e.setCancelled(true);
//				}
			}		
		} else if (rslot >= 54) {
			e.setCancelled(false);
		} else {
			e.setCancelled(true);
		}
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
				case COMIDA:
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
	
	private int estadoToSlot(int estado) {
		int res = 0;
		switch (estado) {
		case 1:
			res = 10;
			break;
		case 2:
			res = 19;
			break;
		case 3:
			res = 28;
			break;
		case 4:
			res = 37;
			break;
		case 5:
			res = 12;
			break;
		case 6:
			res = 13;
			break;
		case 7:
			res = 14;
			break;
		case 8:
			res = 21;
			break;
		case 9:
			res = 22;
			break;
		case 10:
			res = 23;
			break;
		case 11:
			res = 30;
			break;
		case 12:
			res = 31;
			break;
		case 13:
			res = 32;
			break;
		case 14:
			res = 39;
			break;
		case 15:
			res = 40;
			break;
		case 16:
			res = 41;
			break;
		case 17:
			res = 16;
			break;
		case 18:
			res = 25;
			break;
		case 19:
			res = 34;
			break;
		case 20:
			res = 43;
			break;
		default:
			res = 43;
		}
		return res;
	}

	private int deSlotAEstado(int slot) {
		int res = 0;
		switch (slot) {
		case 10:
			res = 0;
			break;
		case 19:
			res = 1;
			break;
		case 28:
			res = 2;
			break;
		case 37:
			res = 3;
			break;
		case 12:
			res = 4;
			break;
		case 13:
			res = 5;
			break;
		case 14:
			res = 6;
			break;
		case 21:
			res = 7;
			break;
		case 22:
			res = 8;
			break;
		case 23:
			res = 9;
			break;
		case 30:
			res = 10;
			break;
		case 31:
			res = 11;
			break;
		case 32:
			res = 12;
			break;
		case 39:
			res = 13;
			break;
		case 40:
			res = 14;
			break;
		case 41:
			res = 15;
			break;
		case 16:
			res = 16;
			break;
		case 25:
			res = 17;
			break;
		case 34:
			res = 18;
			break;
		case 43:
			res = 19;
			break;
		default:
			res = 19;
		}
		return res;
	}
}

package es.elzoo.zooimperium.cofres;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class Cofre {
	private static Plugin plugin = Bukkit.getPluginManager().getPlugin("ZooImperium");
	public static List<Cofre> listaCofres = new ArrayList<Cofre>();
	
	private Location location;
	private List<ItemStack> items;
	private int estado;
	private String tipo;
	private boolean abierto;
	
	//TODO: Cargar datos;
	
	private static Cofre getCofre(Location loc) {
		if (!cofreYaCreado(loc)) {
			addCofre(loc);
		}
		return getCofreFromLoc(loc);
	}
	
	public static boolean cofreYaCreado(Location loc) {
		return listaCofres.parallelStream().anyMatch(x -> x.getLocation().equals(loc));
	}
	
	public static Cofre getCofreFromLoc(Location loc) {
		return listaCofres.parallelStream().filter(x -> x.getLocation().equals(loc)).findFirst().get();
	}
	
	private static void addCofre(Location loc) {
		Cofre nuevo = new Cofre(loc, Arrays.asList(new ItemStack(Material.OAK_WOOD, 2),new ItemStack(Material.OAK_WOOD, 2),new ItemStack(Material.OAK_WOOD, 2),new ItemStack(Material.OAK_WOOD, 2),new ItemStack(Material.OAK_WOOD, 2),new ItemStack(Material.OAK_WOOD, 2),new ItemStack(Material.OAK_WOOD, 2),new ItemStack(Material.OAK_WOOD, 2),new ItemStack(Material.OAK_WOOD, 2),new ItemStack(Material.OAK_WOOD, 2),new ItemStack(Material.OAK_WOOD, 2),new ItemStack(Material.OAK_WOOD, 2),new ItemStack(Material.OAK_WOOD, 2),new ItemStack(Material.OAK_WOOD, 2),new ItemStack(Material.OAK_WOOD, 2),new ItemStack(Material.OAK_WOOD, 2),new ItemStack(Material.OAK_WOOD, 2),new ItemStack(Material.OAK_WOOD, 2),new ItemStack(Material.OAK_WOOD, 2),new ItemStack(Material.OAK_WOOD, 2)));
		listaCofres.add(nuevo);
	}
	
	private Cofre(Location loc, List<ItemStack> items) {
		this.location = loc;
		this.items = items;
		this.estado = 0;
		this.tipo = "comida";
		this.abierto = false;
	}
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public List<ItemStack> getItems() {
		return items;
	}

	public void setItems(List<ItemStack> items) {
		this.items = items;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public static void abrirCofres(Player player, Location loc) {
		Cofre cofre = getCofre(loc);
		InvCofres inv = new InvCofres(cofre.estado, cofre.items, cofre.tipo);
		inv.abrir(player);
		tareaCofres(cofre, inv);
	
	}
	

	
	private static void tareaCofres(Cofre cofre, InvCofres inv) {
		if (!cofre.abierto) {
			cofre.abierto = true;
			Bukkit.getScheduler().runTaskLater(plugin, () -> {				// PREPARA UNA TAREA FUTURA, NECESITA COMO PARAMETRO EL PLUGIN
				if (!InvCofres.inventariosAbiertos.isEmpty()) {
					cofre.estado ++;
					inv.updateProp(cofre.estado, cofre.items, cofre.tipo);
					InvCofres.updateCofre();
					cofre.abierto = false;
					tareaCofres(cofre, inv);
				} else {
					cofre.abierto = false;
				}
			}, 20L);
		} else {
			Bukkit.getScheduler().runTaskLater(plugin, () -> {				// PREPARA UNA TAREA FUTURA, NECESITA COMO PARAMETRO EL PLUGIN
				inv.updateProp(cofre.estado, cofre.items, cofre.tipo);	
				InvCofres.updateCofre();
				tareaCofres(cofre, inv);
			}, 20L);
		}
	}
}

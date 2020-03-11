package es.elzoo.zooimperium.salidas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Salidas {
	public static List<Salidas> listaSalidas = new ArrayList<Salidas>();
	
	private String nombre;
	private Location loc;
	private boolean open;
	private Map<Player, Integer> estado;
	
	public Location getLocation() {
		return loc;
	}
	
	public static void addSalida(String name, Location loc) {
		Salidas nueva = new Salidas(name, loc);
		listaSalidas.add(nueva);
	}
	
	public Salidas(String name, Location loc) {
		this.nombre = name;
		this.loc = loc;
		this.open = false;
		this.estado = new HashMap<Player, Integer>();
		
	}
	
	public void tickSalida() {
		World mundo = Bukkit.getWorlds().get(0);
		
		List<Player> jugadores = mundo.getPlayers().parallelStream().filter(p -> {
					if(!p.getGameMode().equals(GameMode.SURVIVAL)) {
						return false;
					}
					
					Location locP = p.getLocation();
					Location locS = this.getLocation();
					locS.setY(locP.getBlockY());
					
					return locP.distanceSquared(locS) <= 576;
				}).collect(Collectors.toList());
		if (this.estado.keySet() == null) {
			if (jugadores == null) {
				return;
			} else {
				for (Player player : jugadores) {
					player.sendMessage("Entrando en punto de extraccion");
					this.estado.put(player, 0);
				}
			}
		}
		for (Player player : this.estado.keySet()) {
			if (jugadores.contains(player)) {
				Integer tiempo = this.estado.get(player);
				player.sendMessage("Tiempo restante de salida " + (30-tiempo));
				if (tiempo < 30) {
					this.estado.put(player, tiempo+1);
					jugadores.remove(player);
				} else {
					player.sendMessage("Has salido con exito");
					player.teleport(new Location(mundo, 200, 100, 200));
				}
			} else {
				player.sendMessage("Te has salido del punto de extraccion");
				this.estado.remove(player);
			}
		}
		for (Player player : jugadores) {
			player.sendMessage("Entrando en punto de extraccion");
			this.estado.put(player, 0);
		}
	}
	
}


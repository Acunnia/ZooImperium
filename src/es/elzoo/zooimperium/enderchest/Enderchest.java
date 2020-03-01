package es.elzoo.zooimperium.enderchest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import es.elzoo.zooimperium.ZooImperium;

public class Enderchest {
	public static Plugin plugin = Bukkit.getPluginManager().getPlugin("ZooImperium");
	
	private static List<Enderchest> listaEC = new ArrayList<Enderchest>();
	
	private String nick;
	private List<ItemStack> listItems;
	private int nivel;
	
	
	
	public Enderchest(String nick2, List<ItemStack> itemsList, int nivel2) {
		this.nick = nick2;
		this.listItems = itemsList;
		this.nivel = nivel2;
	}
	
	public static boolean existEnderchestFromNick(String nick) {
		return listaEC.parallelStream().anyMatch(x -> x.getNick().equals(nick));
	}
	
	public static Optional<Enderchest> getEnderchestOfPlayer(String nick) {
		return listaEC.parallelStream().filter(x -> x.getNick().equals(nick)).findFirst();
	}

	public int getNivel() {
		return nivel;
	}
	
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	
	public String getNick() {
		return nick;
	}
	
	public List<ItemStack> getListItems() {
		return listItems;
	}
	
	public void setListItems(List<ItemStack> listItems) {
		this.listItems = listItems;
	}
	
	public List<Enderchest> getListaEC() {
		return listaEC;
	}
	
	public static void cargarDatos() {
		Connection conexion = ZooImperium.getConexion();
		PreparedStatement stmt = null;
		Bukkit.getLogger().info("Cargando enderchests...");
		try {
			stmt = conexion.prepareStatement("SELECT nick, items, nivel FROM zooImperium_enderchest");
			ResultSet res = stmt.executeQuery();
			while(res.next()) {
				String nick = res.getString(1);
				String datosItems = res.getString(2);
				int nivel = res.getInt(3);
				
				List<ItemStack> itemsList = new ArrayList<ItemStack>();
				
				JsonArray itemsArray = new JsonParser().parse(datosItems).getAsJsonArray();
				itemsArray.forEach(jsonItem -> {
					String itemstackRaw = jsonItem.getAsString();
					YamlConfiguration config = new YamlConfiguration();
					try {
						config.loadFromString(itemstackRaw);
					} catch (InvalidConfigurationException e) {
						e.printStackTrace();
					}
					Map<String, Object> itemRaw = config.getValues(true);
					itemsList.add(ItemStack.deserialize(itemRaw));
				});
				addEnderchest(nick, itemsList, nivel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
	}

	private static void addEnderchest(String nick, List<ItemStack> itemsList, int nivel) {
		listaEC.add(new Enderchest(nick, itemsList, nivel));
		
	}

	public void guardarInventario() {
		Bukkit.getScheduler().runTaskAsynchronously(plugin, task -> {
			JsonArray items = new JsonArray();			
			this.listItems.stream().forEach(item -> {
				if(item == null) {
					return;
				}
				
				YamlConfiguration config = new YamlConfiguration();
				item.serialize().forEach((s,o) -> {config.set(s, o);});
				String itemRaw = config.saveToString();	
				items.add(itemRaw);
			});			
			String itemsJson = (new Gson()).toJson(items);
			
			Connection conexion = ZooImperium.getConexion();
			PreparedStatement stmt = null;
			try {
				stmt = conexion.prepareStatement("INSERT INTO zooImperium_enderchest (nick, items, nivel) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE items=?");
				stmt.setString(1, nick);
				stmt.setString(2, itemsJson);
				stmt.setInt(3, nivel);
				stmt.setString(4, itemsJson);
				stmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (stmt != null) {
						stmt.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void nuevoEnderchest(String name) {
		addEnderchest(name, Arrays.asList(new ItemStack(Material.AIR, 1), new ItemStack(Material.AIR, 1), new ItemStack(Material.AIR, 1)), 0);
	}	
}

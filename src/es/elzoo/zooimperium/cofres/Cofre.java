package es.elzoo.zooimperium.cofres;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Axis;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Orientable;
import org.bukkit.block.data.Rotatable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.google.common.collect.Streams;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import es.elzoo.zooimperium.ZooImperium;

public class Cofre {
	private static Plugin plugin = Bukkit.getPluginManager().getPlugin("ZooImperium");
	public static List<Cofre> listaCofres = new ArrayList<Cofre>();
	
	private Location location;
	private List<ItemStack> items;
	private int estado;
	private boolean abierto;
	private CofreType tipo;
	private int tier;

	public CofreType getTipo() {
		return tipo;
	}

	public void setTipo(CofreType tipo) {
		this.tipo = tipo;
	}

	public int getTier() {
		return tier;
	}

	public void setTier(int tier) {
		this.tier = tier;
	}
	
	private static Cofre getCofre(Location loc) {
		if (isLooteable(loc)) {
			return getCofreFromLoc(loc);
		} 
		return null;
	}
	
	public static boolean isLooteable(Location loc) {
		return listaCofres.parallelStream().anyMatch(x -> x.getLocation().equals(loc));
	}
	
	public static Cofre getCofreFromLoc(Location loc) {
		return listaCofres.parallelStream().filter(x -> x.getLocation().equals(loc)).findFirst().get();
	}
	
	private static void addCofre(Location loc, CofreType tipo, int tier) {
		Cofre nuevo = new Cofre(loc, tipo, tier);
		listaCofres.add(nuevo);
	}
	
	private Cofre(Location loc, CofreType tipo, int tier) {
		this.location = loc;
		this.items = aleatorizarItems(tipo, tier);
		this.estado = 0;
		this.tipo = tipo;
		this.tier = tier;
		this.abierto = false;
	}
	
	private static List<ItemStack> aleatorizarItems(CofreType tipo, int tier) {
		List<ItemStack> lista = Arrays.asList(null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
		int aciertos = 0;
		int slots =(int) (Math.random() * 11);
		while(aciertos < slots) {
			int pos =(int) (Math.random() * 20);
			if(lista.get(pos) == null && pos != 20) {
				ItemStack nuevo = ObjetosCofre.getItemAleatorio(tipo, tier);
				lista.set(pos, nuevo);
				aciertos++;
			}	
		}
		return lista;
	}
	
	public static void generarTodos() {
		listaCofres.stream().forEach(x -> Cofre.colocarCofre(x));
	}
	
	public static void colocarCofre(Cofre cofre) {
		setBlock(cofre.location.getBlock(), Material.CHEST, BlockFace.NORTH);
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

	public static void abrirCofres(Player player, Location loc) {
		Cofre cofre = getCofre(loc);
		InvCofres inv = new InvCofres(cofre.estado, cofre.items, cofre.tipo, cofre);
		inv.abrir(player);
		tareaCofres(cofre, inv);
	
	}
	
	private static void tareaCofres(Cofre cofre, InvCofres inv) {
		if (!cofre.abierto) {
			cofre.abierto = true;
			Bukkit.getScheduler().runTaskLater(plugin, () -> {				// PREPARA UNA TAREA FUTURA, NECESITA COMO PARAMETRO EL PLUGIN
				if (!InvCofres.inventariosAbiertos.isEmpty()) {
					cofre.estado ++;
					inv.updateProp(cofre.estado);
					InvCofres.updateCofre();
					cofre.abierto = false;
					tareaCofres(cofre, inv);
				} else {
					cofre.abierto = false;
				}
			}, 20L);
		} else {
			Bukkit.getScheduler().runTaskLater(plugin, () -> {				// PREPARA UNA TAREA FUTURA, NECESITA COMO PARAMETRO EL PLUGIN
				inv.updateProp(cofre.estado);	
				InvCofres.updateCofre();
				tareaCofres(cofre, inv);
			}, 20L);
		}
	}
	
	public static void cargarDatos() throws Exception {
		byte[] encoded = Files.readAllBytes(Paths.get(ZooImperium.RUTA+"/cofres.json"));
		Gson g = new Gson();
		
		JsonObject jCofres = g.fromJson(new String(encoded), JsonObject.class);
		Streams.stream(jCofres.entrySet().iterator()).forEach(entrada -> {
			JsonObject jCofre = entrada.getValue().getAsJsonObject();
			
			String tipo = jCofre.get("tipo").getAsString();
			String[] rawLoc = jCofre.get("location").getAsString().split(",");
			Location loc = new Location(Bukkit.getWorlds().get(0), Integer.valueOf(rawLoc[0].trim()), Integer.valueOf(rawLoc[1].trim()), Integer.valueOf(rawLoc[2].trim()));
			CofreType cofreTipo = CofreType.valueOf(tipo);
			int tier = jCofre.get("tier").getAsInt();
			
			addCofre(loc, cofreTipo, tier);
		});
	}
	
	public static void setBlock(Block block, Material material, BlockFace blockFace) {
	    block.setType(material);
	    BlockData blockData = block.getBlockData();
	    if (blockData instanceof Directional) {
	        ((Directional) blockData).setFacing(blockFace);
	        block.setBlockData(blockData);
	    }
	    if (blockData instanceof Orientable) {
	        ((Orientable) blockData).setAxis(convertBlockFaceToAxis(blockFace));
	        block.setBlockData(blockData);
	    }
	    if (blockData instanceof Rotatable) {
	        ((Rotatable) blockData).setRotation(blockFace);
	        block.setBlockData(blockData);
	    }
	}
	
	private static Axis convertBlockFaceToAxis(BlockFace face) {
	    switch (face) {
	        case NORTH:
	        case SOUTH:
	            return Axis.Z;
	        case EAST:
	        case WEST:
	            return Axis.X;
	        case UP:
	        case DOWN:
	            return Axis.Y;
	            default:
	                return Axis.X;
	    }
	}

	public void setItems(int pos, ItemStack item) {
		this.items.set(pos, item);
		
	}
}

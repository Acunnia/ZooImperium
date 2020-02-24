package es.elzoo.zooimperium.cofres;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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

import com.google.common.collect.Streams;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import es.elzoo.zooimperium.ZooImperium;

public class RegistroCofres {
	private static List<RegistroCofres> lista = new ArrayList<RegistroCofres>();
	
	public static void generarTodos() {
		lista.stream().forEach(x -> RegistroCofres.colocarCofre(x));
	}
	
	public static void colocarCofre(RegistroCofres cofre) {
		setBlock(cofre.loc.getBlock(), Material.CHEST, BlockFace.NORTH);
	}
	
	private Location loc;
	private CofreType tipo;
	private int tier;
	
	public static List<RegistroCofres> getLista() {
		return lista;
	}

	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}

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

	public static void addCofre(Location loc, CofreType tipo, int tier) {
		lista.add(new RegistroCofres(loc, tipo, tier));
	}
	
	private RegistroCofres(Location loc, CofreType tipo, int tier) {
		this.loc = loc;
		this.tipo = tipo;
		this.tier = tier;
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
}

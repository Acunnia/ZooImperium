package es.elzoo.zooimperium.cofres;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.google.common.collect.Streams;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import es.elzoo.zooimperium.ZooImperium;

public class RegistroCofres {
	private static List<RegistroCofres> lista = new ArrayList<RegistroCofres>();
	
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
			Location loc = new Location(Bukkit.getWorlds().get(0), Integer.valueOf(rawLoc[0]), Integer.valueOf(rawLoc[1]), Integer.valueOf(rawLoc[2]));
			CofreType cofreTipo = CofreType.valueOf(tipo);
			int tier = jCofre.get("tier").getAsInt();
			
			addCofre(loc, cofreTipo, tier);
		});
	}
	
}

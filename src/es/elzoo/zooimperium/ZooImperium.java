package es.elzoo.zooimperium;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import es.elzoo.zooimperium.eventos.EventosPicar;

public class ZooImperium extends JavaPlugin{
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new EventosPicar(), this);
		Bukkit.getLogger().info("El plugin ha sido cargado");	
	}
}

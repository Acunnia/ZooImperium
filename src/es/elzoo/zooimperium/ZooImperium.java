package es.elzoo.zooimperium;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import es.elzoo.zooimperium.eventos.EventosPicar;

public class ZooImperium extends JavaPlugin{
	public static Plugin pl = null; // DONDE VAMOS A ALMACENAR EL ESTADO DEL SERVIDOR
	
	@Override
	public void onEnable() {
		pl = this; // Guardamos el estado del plugin, así tenemos un unico objeto "plugin"
		
		getServer().getPluginManager().registerEvents(new EventosPicar(), this); // PREPARA AL SERVIDOR PARA LEER LOS EVENTOS DE EventosPicar
		Bukkit.getLogger().info("El plugin ha sido cargado");	// MUESTRA POR CONSOLA QUE EL PLUGIN SE HA CARGADO CON EXITO
	}
}

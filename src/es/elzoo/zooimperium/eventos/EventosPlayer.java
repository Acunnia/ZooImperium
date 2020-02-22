package es.elzoo.zooimperium.eventos;

import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import es.elzoo.zooimperium.IPlayer;

public class EventosPlayer implements Listener {
	public static Plugin plugin = Bukkit.getPluginManager().getPlugin("ZooImperium");
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Optional<IPlayer> optIP = IPlayer.getPlayer(event.getPlayer().getName());		
		if(!optIP.isPresent()) {
			IPlayer.crearPlayer(event.getPlayer());
		}
	}
	
	@EventHandler(ignoreCancelled=true)
	public void onBedEnter(PlayerBedEnterEvent event) {
		event.getPlayer().setStatistic(Statistic.TIME_SINCE_REST,0);
	}
		
	//Guardar los stats del jugador al desconectarse
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Optional<IPlayer> optIP = IPlayer.getPlayer(event.getPlayer().getName());
		if(!optIP.isPresent()) {
			return;
		}
		optIP.get().guardarSQLAsync();
	}
}

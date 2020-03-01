package es.elzoo.zooimperium.eventos;

import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import es.elzoo.zooimperium.IPlayer;
import es.elzoo.zooimperium.ImperiumScoreboard;

public class EventosPlayer implements Listener {
	public static Plugin plugin = Bukkit.getPluginManager().getPlugin("ZooImperium");
	
	@EventHandler
	public void chatFormat(AsyncPlayerChatEvent event){
	Player p = event.getPlayer();	
	IPlayer ip = IPlayer.getPlayer(p.getName()).get();
	event.setFormat(ChatColor.GRAY+ "[" + ChatColor.DARK_GRAY + String.format("%03d",ip.getNivel()) + ChatColor.GRAY+ "][" + ip.getRango() + ChatColor.GRAY+ "] " + ChatColor.WHITE + p.getDisplayName() + ": " + ChatColor.GRAY + event.getMessage());
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		event.setJoinMessage("");
		Optional<IPlayer> optIP = IPlayer.getPlayer(event.getPlayer().getName());		
		if(!optIP.isPresent()) {
			IPlayer.crearPlayer(event.getPlayer());
		}
		ImperiumScoreboard.setScoreBoard(event.getPlayer());
	}
		
	//Guardar los stats del jugador al desconectarse
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		event.setQuitMessage("");
		Optional<IPlayer> optIP = IPlayer.getPlayer(event.getPlayer().getName());
		if(!optIP.isPresent()) {
			return;
		}
		optIP.get().guardarSQLAsync();
	}
	
	
}

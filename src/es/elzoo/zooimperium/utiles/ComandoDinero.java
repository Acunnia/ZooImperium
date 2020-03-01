package es.elzoo.zooimperium.utiles;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import es.elzoo.zooimperium.IPlayer;
import net.md_5.bungee.api.ChatColor;

public class ComandoDinero implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("Comando unico para jugadores");
			return true;
		}
		
		Player player = (Player) sender;
		
		IPlayer ip = IPlayer.getPlayer(sender.getName()).get();
		
		player.sendMessage(ChatColor.YELLOW + "Tienes en la cuenta: " + ChatColor.GOLD + ip.getDinero() + " $");
		
		return true;
	}

}

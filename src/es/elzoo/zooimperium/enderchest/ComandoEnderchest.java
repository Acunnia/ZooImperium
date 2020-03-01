package es.elzoo.zooimperium.enderchest;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ComandoEnderchest implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("Comando unico para jugadores");
			return true;
		}
		
		Player player = (Player) sender;
		
		if(!Enderchest.existEnderchestFromNick(player.getName())) {
			Enderchest.nuevoEnderchest(player.getName());
		}
		Enderchest ec = Enderchest.getEnderchestOfPlayer(player.getName()).get();
		
		InvEnderchest inv = new InvEnderchest(ec);
		inv.abrir(player);
		
		return true;
	}

}

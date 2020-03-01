package es.elzoo.zooimperium.npc.npc.subasta;

import org.bukkit.entity.Player;

import es.elzoo.zooimperium.IPlayer;
import es.elzoo.zooimperium.npc.inventarios.subasta.InvSubastaMain;

public class NPCSubasta {
	public static void abrirInterfazMain(Player player) {	
		InvSubastaMain inv = new InvSubastaMain(IPlayer.getPlayer(player.getName()).get());
		inv.abrir(player);
	}
}

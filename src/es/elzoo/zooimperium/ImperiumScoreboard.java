package es.elzoo.zooimperium;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import net.md_5.bungee.api.ChatColor;

public class ImperiumScoreboard {
	public static List<String> carteles = Arrays.asList(ChatColor.YELLOW + "" + ChatColor.BOLD + "ZooImperium",
			ChatColor.YELLOW + "" + ChatColor.BOLD + "ZooImperium",
			ChatColor.YELLOW + "" + ChatColor.BOLD + "ZooImperium",
			ChatColor.YELLOW + "" + ChatColor.BOLD + "ZooImperium",
			ChatColor.YELLOW + "" + ChatColor.BOLD + "ZooImperium",
			ChatColor.YELLOW + "" + ChatColor.BOLD + "ZooImperium",
			ChatColor.YELLOW + "" + ChatColor.BOLD + "ZooImperium",
			ChatColor.YELLOW + "" + ChatColor.BOLD + "ZooImperium",
			ChatColor.YELLOW + "" + ChatColor.BOLD + "ZooImperium",
			ChatColor.GOLD + "" + ChatColor.BOLD + "Z" + ChatColor.YELLOW + "" + ChatColor.BOLD + "ooImperium",
			ChatColor.WHITE + "" + ChatColor.BOLD + "Z" + ChatColor.GOLD + "" + ChatColor.BOLD + "o" + ChatColor.YELLOW
					+ "" + ChatColor.BOLD + "oImperium",
			ChatColor.YELLOW + "" + ChatColor.BOLD + "Z" + ChatColor.WHITE + "" + ChatColor.BOLD + "o" + ChatColor.GOLD
					+ "" + ChatColor.BOLD + "o" + ChatColor.YELLOW + "" + ChatColor.BOLD + "Imperium",
			ChatColor.YELLOW + "" + ChatColor.BOLD + "Zo" + ChatColor.WHITE + "" + ChatColor.BOLD + "o" + ChatColor.GOLD
					+ "" + ChatColor.BOLD + "I" + ChatColor.YELLOW + "" + ChatColor.BOLD + "mperium",
			ChatColor.YELLOW + "" + ChatColor.BOLD + "Zoo" + ChatColor.WHITE + "" + ChatColor.BOLD + "I"
					+ ChatColor.GOLD + "" + ChatColor.BOLD + "m" + ChatColor.YELLOW + "" + ChatColor.BOLD + "perium",
			ChatColor.YELLOW + "" + ChatColor.BOLD + "ZooI" + ChatColor.WHITE + "" + ChatColor.BOLD + "m"
					+ ChatColor.GOLD + "" + ChatColor.BOLD + "p" + ChatColor.YELLOW + "" + ChatColor.BOLD + "erium",
			ChatColor.YELLOW + "" + ChatColor.BOLD + "ZooIm" + ChatColor.WHITE + "" + ChatColor.BOLD + "p"
					+ ChatColor.GOLD + "" + ChatColor.BOLD + "e" + ChatColor.YELLOW + "" + ChatColor.BOLD + "rium",
			ChatColor.YELLOW + "" + ChatColor.BOLD + "ZooImp" + ChatColor.WHITE + "" + ChatColor.BOLD + "e"
					+ ChatColor.GOLD + "" + ChatColor.BOLD + "r" + ChatColor.YELLOW + "" + ChatColor.BOLD + "ium",
			ChatColor.YELLOW + "" + ChatColor.BOLD + "ZooImpe" + ChatColor.WHITE + "" + ChatColor.BOLD + "r"
					+ ChatColor.GOLD + "" + ChatColor.BOLD + "i" + ChatColor.YELLOW + "" + ChatColor.BOLD + "um",
			ChatColor.YELLOW + "" + ChatColor.BOLD + "ZooImper" + ChatColor.WHITE + "" + ChatColor.BOLD + "i"
					+ ChatColor.GOLD + "" + ChatColor.BOLD + "u" + ChatColor.YELLOW + "" + ChatColor.BOLD + "m",
			ChatColor.YELLOW + "" + ChatColor.BOLD + "ZooImperi" + ChatColor.WHITE + "" + ChatColor.BOLD + "u"
					+ ChatColor.GOLD + "" + ChatColor.BOLD + "m");

	public static int estado = 0;

	public static void setScoreBoard(Player pl) {

		IPlayer ip = IPlayer.getPlayer(pl.getName()).get();

		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		Objective objective = board.registerNewObjective("main", "dummy", carteles.get(estado));
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		Score sIP = objective.getScore(ChatColor.WHITE + "elzoo.es");
		Score blank = objective.getScore("");
		Score sCoins = objective.getScore(ChatColor.WHITE + "Monedas: " + ChatColor.GOLD + ip.getDinero() + " $");
		Score blank2 = objective.getScore(" ");
		Score rank = objective.getScore(ip.getRango());
		Score blank3 = objective.getScore("  ");

		blank3.setScore(6);
		rank.setScore(5);
		blank2.setScore(4);
		sCoins.setScore(3);
		blank.setScore(2);
		sIP.setScore(1);

		pl.setScoreboard(board);
	}

}

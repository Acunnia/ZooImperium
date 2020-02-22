package es.elzoo.zooimperium.utiles;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class UtilesPlayers {
	public static void curar(Player pl, int lvl) {
		// TODO: Barra inferior de carga
		switch (lvl) {
		case 1:
			pl.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,70,2));
			break;
		case 2:
			pl.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,105,2));
			break;
		default:
			pl.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,85,3));
			break;
		}
	}
}

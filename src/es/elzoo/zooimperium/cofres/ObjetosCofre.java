package es.elzoo.zooimperium.cofres;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import es.elzoo.zooimperium.utiles.DistributedRandomNumberGenerator;

public class ObjetosCofre {
	private static List<Material> comida = Arrays.asList(
			Material.BREAD, Material.WHEAT, Material.MELON_SLICE, Material.POTATO, Material.SWEET_BERRIES,Material.BEETROOT,
			Material.APPLE,Material.GOLDEN_APPLE,Material.CARROT,Material.PORKCHOP, Material.BEEF,Material.HONEY_BOTTLE,
			Material.PUMPKIN_PIE, Material.GOLDEN_CARROT);
	private static int[] comidaMinsT0 = new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
	private static int[] comidaMaxsT0 = new int[] {2, 6, 11, 2, 1, 3, 1, 1, 7, 1, 1, 1, 1, 1};
	private static double[] comidaTier0 = new double[] {0.07, 0.22, 0.19, 0.03, 0.05, 0.07, 0.05, 0.01, 0.12, 0.05, 0.05, 0.03, 0.03, 0.03};
	
	public static ItemStack getItemAleatorio(CofreType tipo, int tier) {
		ItemStack res = null;
		switch (tipo) {
		case COMIDA:
			switch (tier) {
			case 0:
				DistributedRandomNumberGenerator random = new DistributedRandomNumberGenerator();
				for (int i = 0; i < comidaTier0.length; i++) {
					random.addNumber(i, comidaTier0[i]);
				}
				int index = random.getDistributedRandomNumber();
				Material mat = comida.get(index);
				int cant = 0;
				if (comidaMaxsT0[index]-comidaMinsT0[index] == 0) {
					cant = comidaMaxsT0[index];
				} else {
					cant = new Random().nextInt(comidaMaxsT0[index]-comidaMinsT0[index]) + comidaMinsT0[index];
				}
				res = new ItemStack(mat, cant);
				break;
			default:
				break;
			}			
			break;
		default:
			break;
		}
		return res;
	}
		
//	private static List<ItemStack> comidaTier2 = Arrays.asList(new ItemStack(Material.BREAD, 1),new ItemStack(Material.BREAD, 2),
//			new ItemStack(Material.BREAD, 3),new ItemStack(Material.WHEAT, 1),new ItemStack(Material.WHEAT, 2),
//			new ItemStack(Material.WHEAT, 3),new ItemStack(Material.MELON_SLICE, 2),new ItemStack(Material.MELON_SLICE, 5),
//			new ItemStack(Material.MELON_SLICE, 4));
//	private static List<ItemStack> armasTier0 = Arrays.asList(new ItemStack(Material.BREAD, 1),new ItemStack(Material.BREAD, 2),
//			new ItemStack(Material.BREAD, 3),new ItemStack(Material.WHEAT, 1),new ItemStack(Material.WHEAT, 2),
//			new ItemStack(Material.WHEAT, 3),new ItemStack(Material.MELON_SLICE, 2),new ItemStack(Material.MELON_SLICE, 5),
//			new ItemStack(Material.MELON_SLICE, 4));
}

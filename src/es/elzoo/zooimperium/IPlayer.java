package es.elzoo.zooimperium;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import net.md_5.bungee.api.ChatColor;

public class IPlayer {
	public static Plugin plugin = Bukkit.getPluginManager().getPlugin("ZooImperium");
	private static List<IPlayer> players = new ArrayList<IPlayer>();
	
	String nombre;
	int nivel;
	double exp;
	String rango;
	int rangoNum;
	
	int dinero;
	
	LocalTime cura1;
	LocalTime cura2;
	LocalTime cura3;
	
	public int getNivel() {
		return this.nivel;
	}
	public double getExp() {
		return this.exp;
	}
	public int getRangoNum() {
		return this.rangoNum;
	}
	public int getDinero() {
		return this.dinero;
	}
	
	private void setRango(int rangoNum) {
		String rango;
		switch (rangoNum) {
		case 0:
			rango = ChatColor.DARK_GRAY+"Carbón IV";
			break;
		case 1:
			rango = ChatColor.DARK_GRAY+"Carbón III";
			break;
		case 2:
			rango = ChatColor.DARK_GRAY+"Carbón II";
			break;
		case 3:
			rango = ChatColor.DARK_GRAY+"Carbón I";
			break;
		case 4:
			rango = ChatColor.DARK_GRAY+"Carbón IV";
			break;
		case 5:
			rango = ChatColor.DARK_GRAY+"Carbón III";
			break;
		case 6:
			rango = ChatColor.DARK_GRAY+"Carbón II";
			break;
		case 7:
			rango = ChatColor.DARK_GRAY+"Carbón I";
			break;
		case 8:
			rango = ChatColor.DARK_GRAY+"Carbón IV";
			break;
		case 9:
			rango = ChatColor.DARK_GRAY+"Carbón III";
			break;
		case 10:
			rango = ChatColor.DARK_GRAY+"Carbón II";
			break;
		case 11:
			rango = ChatColor.DARK_GRAY+"Carbón I";
			break;
		case 12:
			rango = ChatColor.DARK_GRAY+"Carbón IV";
			break;
		case 13:
			rango = ChatColor.DARK_GRAY+"Carbón III";
			break;
		case 14:
			rango = ChatColor.DARK_GRAY+"Carbón II";
			break;
		case 15:
			rango = ChatColor.DARK_GRAY+"Carbón I";
			break;
		case 16:
			rango = ChatColor.DARK_GRAY+"Carbón IV";
			break;
			
		default:
			rango = ChatColor.DARK_GRAY+"Carbón IV";
			break;
		}
		this.rango = rango;
	}
	
	private IPlayer(String nick, int nivel, double experiencia, int rangoNum, int dinero) {
		setRango(rangoNum);
		this.nombre = nick;
		this.exp = experiencia;
		this.nivel = nivel;
		this.dinero = dinero;
		this.rangoNum = rangoNum;
	}
	
	public static Optional<IPlayer> getPlayer(String nick) {
		return players.parallelStream().filter(ip -> ip.nombre.equalsIgnoreCase(nick)).findFirst();
	}
	
	public void subirExp(double cant) {
		this.exp += cant;
		if(this.exp>=100) {
			this.exp -= 100;
			subirNivel(1);
		}
	}
	
	public void subirNivel(int cant) {
		this.nivel += cant;
	}
	
	public void guardarSQLAsync() {
		Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> guardarSQL());
	}
	
	private void guardarSQL() {
		PreparedStatement stmt = null;
		try {
			stmt = ZooImperium.getConexion().prepareStatement("UPDATE zooImperium_player SET nivel=?, experiencia=?, rango=?, dinero=? WHERE LOWER(nick)=LOWER(?);");
			stmt.setInt(1, nivel);
			stmt.setDouble(2, exp);
			stmt.setInt(3, rangoNum);
			stmt.setInt(4, dinero);
			
			stmt.setString(5, nombre);
			
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void guardarDatos() {
		players.stream().forEach(zp -> zp.guardarSQL());
	}
	
	public static void crearPlayer(Player player) {
		String nick = player.getName();
		
		Optional<IPlayer> ipViejo = players.parallelStream().filter(ip -> ip.nombre.equalsIgnoreCase(player.getName())).findFirst();
		if(ipViejo.isPresent()) {
			players.remove(ipViejo.get());
		}
		
		player.getActivePotionEffects().stream().forEach(pt -> player.removePotionEffect(pt.getType()));
		IPlayer ip = new IPlayer(nick, 0, 0, 0, 0);
		players.add(ip);

		
		Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
			PreparedStatement stmtBorrar = null;
			PreparedStatement stmtCrear = null;
			try {
				stmtBorrar = ZooImperium.getConexion().prepareStatement("DELETE FROM zooImperium_player WHERE LOWER(nick)=LOWER(?);");
				stmtBorrar.setString(1, ip.nombre);
				stmtBorrar.executeUpdate();
				
				stmtCrear = ZooImperium.getConexion().prepareStatement("INSERT INTO zooImperium_player (nick, nivel, rango, experiencia, dinero) VALUES (?,?,?,?,?);");
				
				stmtCrear.setString(1, ip.nombre);
				stmtCrear.setInt(2, ip.getNivel());
				stmtCrear.setInt(3, ip.getRangoNum());
				stmtCrear.setDouble(4, ip.getExp());
				stmtCrear.setInt(5, ip.getDinero());
								
				stmtCrear.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if(stmtBorrar != null) {
						stmtBorrar.close();
					}
					if (stmtCrear != null) {
						stmtCrear.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void cargarDatos() {
		PreparedStatement stmt = null;
		try {
			stmt = ZooImperium.getConexion().prepareStatement("SELECT nick, nivel, rango, experiencia, dinero FROM zooImperium_player;");
			ResultSet res = stmt.executeQuery();
			while(res.next()) {
				String nick = res.getString(1);
				
				int nivel = res.getInt(2);
				int rangoNum = res.getInt(3);
				double experiencia = res.getDouble(4);
				int dinero = res.getInt(5);
				
				IPlayer ip = new IPlayer(nick, nivel, experiencia, rangoNum, dinero);
				IPlayer.players.add(ip);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

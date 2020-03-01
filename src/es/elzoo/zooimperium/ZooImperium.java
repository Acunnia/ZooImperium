package es.elzoo.zooimperium;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import es.elzoo.zooimperium.cofres.Cofre;
import es.elzoo.zooimperium.cofres.EventosCofres;
import es.elzoo.zooimperium.enderchest.ComandoEnderchest;
import es.elzoo.zooimperium.enderchest.Enderchest;
import es.elzoo.zooimperium.eventos.EventosItems;
import es.elzoo.zooimperium.eventos.EventosPicar;
import es.elzoo.zooimperium.eventos.EventosPlayer;
import es.elzoo.zooimperium.npc.EventosNPC;
import es.elzoo.zooimperium.utiles.ComandoDinero;
import es.elzoo.zooimperium.utiles.gui.GUIEventos;

public class ZooImperium extends JavaPlugin {
	static String url;
	static String user;
	static String pass;
	private static Connection conexion;
	
	public static String RUTA = "";
	
	@Override
	public void onEnable() {
		RUTA = this.getDataFolder().getAbsolutePath();
		
		getConfig().addDefault("database", "database");
		getConfig().addDefault("user", "username");
		getConfig().addDefault("pass", "password");
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		// Conectar a la BBDD		
		try {
			Bukkit.getLogger().info("Conectando a la BBDD.");
			
			url = "jdbc:mysql://localhost:3306/"+getConfig().getString("database")+"?autoReconnect=true";
			user = getConfig().getString("user");
			pass = getConfig().getString("pass");
			conexion = DriverManager.getConnection(url, user, pass);	
			crearTablas();
			
			Bukkit.getLogger().info("Conexión establecida.");
			
			IPlayer.cargarDatos();
			
			Cofre.cargarDatos();
			
			Enderchest.cargarDatos();
			
		} catch(Exception e) {
			e.printStackTrace();
			Bukkit.getServer().shutdown();
		}
		
		getServer().getPluginManager().registerEvents(new EventosPicar(), this); // PREPARA AL SERVIDOR PARA LEER LOS EVENTOS DE EventosPicar
		getServer().getPluginManager().registerEvents(new EventosItems(), this);
		getServer().getPluginManager().registerEvents(new EventosPlayer(), this);
		getServer().getPluginManager().registerEvents(new GUIEventos(), this);
		getServer().getPluginManager().registerEvents(new EventosCofres(), this);
		getServer().getPluginManager().registerEvents(new EventosNPC(), this);
		
		getCommand("zooec").setExecutor(new ComandoEnderchest());
		getCommand("dinero").setExecutor(new ComandoDinero());
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
			ImperiumScoreboard.estado++;
			if (estado == ) {
				
			}
			for (Player pl : Bukkit.getOnlinePlayers()) {
				ImperiumScoreboard.setScoreBoard(pl);
			}
		}, 20, 20);
		
		Cofre.generarTodos();
		
		Bukkit.getLogger().info("El plugin ha sido cargado");	// MUESTRA POR CONSOLA QUE EL PLUGIN SE HA CARGADO CON EXITO
	}
	
	private void crearTablas() throws Exception {
		PreparedStatement t1 = conexion.prepareStatement("CREATE TABLE IF NOT EXISTS zooImperium_player(nick varchar(64) UNIQUE, nivel int, experiencia double, rango int, dinero int);");
		PreparedStatement t2 = conexion.prepareStatement("CREATE TABLE IF NOT EXISTS zooImperium_subasta(id int PRIMARY KEY auto_increment, nick varchar(64), item text, fecha timestamp DEFAULT current_timestamp, pujas text);");
		PreparedStatement t3 = conexion.prepareStatement("CREATE TABLE IF NOT EXISTS zooImperium_enderchest(nick varchar(64) UNIQUE, items text, nivel int);");
		
		PreparedStatement[] stmts = new PreparedStatement[] {t1, t2, t3};
		
		for(PreparedStatement stmt : stmts) {
			stmt.executeUpdate();
			stmt.close();
		}
	}	
	
	public static Connection getConexion() {
		checkConexion();
		return conexion;
	}
	
	public static void checkConexion() {
		try {
			if(conexion == null || conexion.isClosed() || !conexion.isValid(0)) {
				conexion = DriverManager.getConnection(url, user, pass);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onDisable() {		
		IPlayer.guardarDatos();
		
		
		try {
			Bukkit.getLogger().info("Cerrando conexión con BBDD.");
			
			if(conexion != null && !conexion.isClosed()) {
				conexion.close();
			}
			
			Bukkit.getLogger().info("Conexión cerrada con éxito.");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

package net.moltendorf.Bukkit.AutoShutdown;

import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

/**
 * Created by moltendorf on 15/05/21.
 *
 * @author moltendorf
 */
public class AutoShutdown extends JavaPlugin {

	// Main instance.
	private static AutoShutdown instance = null;

	protected static AutoShutdown getInstance() {
		return instance;
	}

	// Variable data.
	protected Settings   settings     = null;
	private   BukkitTask shutdownTask = null;

	@Override
	public void onEnable() {
		instance = this;

		saveDefaultConfig();

		// Construct new settings.
		settings = new Settings();

		// Are we enabled?
		if (!settings.enabled) {
			return;
		}

		final Server server = getServer();

		server.getPluginManager().registerEvents(new Listeners(), this);
		server.getScheduler().runTaskTimer(this, this::checkPlayers, settings.ticks, settings.ticks);
	}

	@Override
	public void onDisable() {
		instance = null;
	}

	public void checkPlayers() {
		final Server server = getServer();

		if (server.getOnlinePlayers().size() == 0) {
			if (shutdownTask == null) {
				shutdownTask = server.getScheduler().runTaskLater(this, this::save, settings.ticks - settings.save);

				getLogger().info("Scheduled server shutdown in " + (settings.ticks/20) + " seconds.");
			}
		} else {
			if (shutdownTask != null) {
				shutdownTask.cancel();
				shutdownTask = null;

				getLogger().info("Canceled server shutdown.");
			}
		}
	}

	private void save() {
		final Server server = getServer();

		getLogger().info("Saving...");
		server.getWorlds().forEach(World::save);
		getLogger().info("Saved the world.");

		shutdownTask = server.getScheduler().runTaskLater(this, server::shutdown, settings.save);
	}
}

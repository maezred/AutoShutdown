package net.moltendorf.Bukkit.AutoShutdown;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * Created by moltendorf on 15/05/21.
 *
 * @author moltendorf
 */
public class Settings {
	protected static Settings getInstance() {
		return AutoShutdown.getInstance().settings;
	}

	// Variable data.
	protected boolean enabled = true; // Whether or not the plugin is enabled at all.

	protected long save = 200; // Save the world this many ticks before shutdown.
	protected long ticks = 1200; // Delay before server begins shutting down from inactivity.

	public Settings() {
		final FileConfiguration config = AutoShutdown.getInstance().getConfig();

		enabled = config.getBoolean("enabled", enabled);
		save = config.getLong("save", save);
		ticks = config.getLong("ticks", ticks);
	}
}

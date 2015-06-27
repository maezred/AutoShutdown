package net.moltendorf.Bukkit.AutoShutdown;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by moltendorf on 15/05/21.
 *
 * @author moltendorf
 */
public class Listeners implements Listener {
	@EventHandler(ignoreCancelled = true)
	public void PlayerQuitEventHandler(final PlayerQuitEvent event) {
		final AutoShutdown instance = AutoShutdown.getInstance();

		instance.getServer().getScheduler().runTask(instance, instance::checkPlayers);
	}

	@EventHandler(ignoreCancelled = true)
	public void PlayerJoinEventHandler(final PlayerJoinEvent event) {
		AutoShutdown.getInstance().checkPlayers();
	}
}

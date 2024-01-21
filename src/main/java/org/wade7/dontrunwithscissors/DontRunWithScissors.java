package org.wade7.dontrunwithscissors;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class DontRunWithScissors extends JavaPlugin implements Listener {

	String version = "1.0.0";

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("DontRunWithScissors " + version + " initialized");
	}

	@Override
	public void onDisable() {
		Bukkit.getLogger().info("DontRunWithScissors " + version + " destroyed");
	}

	private boolean isPlayerHoldingShears(Player player) {
		return player.getInventory().getItemInMainHand().getType() == Material.SHEARS;
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();

		if (player.isSprinting() && isPlayerHoldingShears(player)) {
			player.damage(2.0);
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();

		// kinda hacky because the generic death is currently the only one to end in "died"
		if (Objects.requireNonNull(event.getDeathMessage()).endsWith("died") && isPlayerHoldingShears(player)) {
			event.setDeathMessage(player.getName() + " tried to run with scissors");
		}
	}
}

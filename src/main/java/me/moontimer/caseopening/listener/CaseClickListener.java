package me.moontimer.caseopening.listener;

import me.moontimer.caseopening.manager.CaseManager;
import me.moontimer.caseopening.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class CaseClickListener implements Listener {

    @EventHandler
    public void onCaseClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        try {
            if (event.getClickedBlock().getType().equals(new ItemBuilder(Material.CHEST, "§cCaseOpening").getMaterial()))
                new CaseManager(player).openSelectionChest();
        } catch (NullPointerException ignored) {}
    }
}

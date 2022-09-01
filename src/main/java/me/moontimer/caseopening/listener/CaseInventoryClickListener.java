package me.moontimer.caseopening.listener;

import me.moontimer.caseopening.manager.Case;
import me.moontimer.caseopening.utils.CaseOpeningAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class CaseInventoryClickListener implements Listener {

    @EventHandler
    public void onSelectionClickInventory(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals("§cCaseOpening")) {
            event.setCancelled(true);
            switch (event.getCurrentItem().getType()) {
                case CHEST:
                    CaseOpeningAPI.removeCase(player, Case.VoteChest, 1);
                    break;
                case ENDER_PORTAL_FRAME:
                    CaseOpeningAPI.removeCase(player, Case.SupremeChest, 1);
                    break;
                case ENCHANTMENT_TABLE:
                    CaseOpeningAPI.removeCase(player, Case.QuestChest, 1);
                    break;
                default:
                    event.setCancelled(true);
                    break;
            }
        }
    }
}

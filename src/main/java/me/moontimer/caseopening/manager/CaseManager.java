package me.moontimer.caseopening.manager;

import me.moontimer.caseopening.utils.ItemBuilder;
import me.moontimer.caseopening.utils.CaseOpeningAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CaseManager {

    private final Player player;

    public CaseManager(Player player) {
        this.player = player;
    }

    public void openSelectionChest() {
        Inventory inventory = Bukkit.createInventory(null, 9*3, "§cCaseOpening");
        for (int i = 0; inventory.getSize() > i; i++) {
            inventory.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, "").build());
        }
        inventory.setItem(11, new ItemBuilder(Material.CHEST, "§fVote Kiste").lore("§6Du hast §e" + CaseOpeningAPI.getCaseAmount(player, Case.VoteChest) + " Kisten").build());
        inventory.setItem(13, new ItemBuilder(Material.ENDER_PORTAL_FRAME, "§pSupreme Kiste").lore("§6Du hast §e" + CaseOpeningAPI.getCaseAmount(player, Case.SupremeChest) + " Kisten").build());
        inventory.setItem(15, new ItemBuilder(Material.ENCHANTMENT_TABLE, "§eQuest Kiste").lore("§6Du hast §e" + CaseOpeningAPI.getCaseAmount(player, Case.QuestChest) + " Kisten").build());
    }
}

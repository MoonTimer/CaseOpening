package me.moontimer.caseopening.commands;

import me.moontimer.caseopening.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetCaseCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (!player.hasPermission("case.set")) {
            player.sendMessage("§cDu hast keine Rechte um die Case Chest zu setzen!");
            return true;
        }
        player.getLocation().getBlock().setType(new ItemBuilder(Material.CHEST, "§cCaseOpening").getMaterial());
        player.sendMessage("§aDie Case Chest wurde erfolgreich gesetzt");
        return false;
    }
}

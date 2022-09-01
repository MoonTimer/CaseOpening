package me.moontimer.caseopening.utils;

import me.moontimer.caseopening.CaseOpening;
import me.moontimer.caseopening.manager.Case;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CaseOpeningAPI {

    private static MySQL mySQL = CaseOpening.getInstance().getMySQL();
    private static HashMap<ItemStack, Double> caseContent = new HashMap<>();

    public static void setCaseAmount(Player player, Case chest, int amount) {
        if (isRegistered(player)) {
            mySQL.execute("UPDATE CaseOpening SET '" + chest.getSqlName() + "' = '" + amount + "' WHERE UUID = '" + player.getUniqueId().toString() + "'");
        } else {
            mySQL.execute("INSERT INTO CaseOpening (UUID, VoteChest, SupremeChest, QuestChest) VALUES ('" + player.getUniqueId().toString() + "', '" + 0 + "', '" + 0 + "', '" + 0 + "')");
        }
    }

    public static int getCaseAmount(Player player, Case chest) {
        try {
            ResultSet rs = mySQL.executeQuery("SELECT * FROM CaseOpening WHERE UUID = '" + player.getUniqueId().toString() + "'");
            if (rs.next())
                return rs.getInt(chest.getSqlName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void addCase(Player player, Case chest, int amount) {
        setCaseAmount(player, chest, (getCaseAmount(player, chest) + amount));
    }

    public static void removeCase(Player player, Case chest, int amount) {
        int allCases = getCaseAmount(player, chest);
        if (allCases < amount) {
            return;
        }
        player.sendMessage("§cDu hast zu wenig Kisten dieser Art");
        setCaseAmount(player, chest, allCases - amount);
    }

    private static boolean isRegistered(Player player) {
        try {
            ResultSet rs = mySQL.executeQuery("SELECT * FROM CaseOpening WHERE UUID = '" + player.getUniqueId().toString() + "'");
            if (rs.next())
                return (rs.getInt("Job") != 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<ItemStack> getCaseRepeatingTemplate(){
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();

        for(int i = 0; i<27; i++){
            items.add(generateItem());
        }

        ((ArrayList<ItemStack>)items.clone()).forEach(items::add);

        return items;
    }

    public static ItemStack generateItem(){
        List<Double> percents = new ArrayList<>(caseContent.values());
        List<Double> percentsList = new ArrayList<>(caseContent.values());

        double[] arrayPercents = new double[percents.size()];

        for (int i = 0; i < arrayPercents.length; i++) {
            arrayPercents[i] = percents.get(i);
        }

        Arrays.sort(arrayPercents);
        percents = new ArrayList<Double>();

        for(double d : arrayPercents){
            if(percents.contains(d)) continue;
            percents.add(d);
        }

        double chance = 100;

        for(double d : percents){
            int rand = new Random().nextInt(100);

            if(rand < d){
                chance = d;
                break;
            }
        }

        ArrayList<ItemStack> selected = new ArrayList<ItemStack>();

        double finalChance = chance;
        caseContent.entrySet().stream().filter(entry -> {
            double percent = entry.getValue();
            return percent== finalChance;
        }).forEach(entry -> selected.add(entry.getKey()));

        return selected.get(new Random().nextInt(selected.size()));
    }

    public static void openCaseInventory(Player p) {
        List<ItemStack> items= getCaseRepeatingTemplate();

        Inventory inv = Bukkit.createInventory(null, 27, "§e§f§6CaseOpening");

        inv.setItem(4, new ItemBuilder(Material.HOPPER, "§aGewinn:").build());

        Iterator<ItemStack> iterator= items.iterator();

        inv.setItem(9, iterator.next());

        p.openInventory(inv);

        CallableChanger<Integer> callableChanger = new CallableChanger<Integer>() {
            private int i = 0;

            @Override
            public Integer call() {
                return i;
            }

            @Override
            public void change(Integer integer) {
                this.i=integer;
            }
        };

        Runnable runnable = new Runnable() {

            @Override
            public void run() {

                inv.setItem(17, inv.getItem(16));
                inv.setItem(16, inv.getItem(15));
                inv.setItem(15, inv.getItem(14));
                inv.setItem(14, inv.getItem(13));
                inv.setItem(13, inv.getItem(12));
                inv.setItem(12, inv.getItem(11));
                inv.setItem(11, inv.getItem(10));
                inv.setItem(10, inv.getItem(9));

                inv.setItem(9, iterator.next());

                p.updateInventory();

                if(!iterator.hasNext()){
                    Bukkit.getScheduler().cancelTask(callableChanger.call());

                    ItemStack item = inv.getItem(13);
                    p.getInventory().addItem(item);
                    p.sendMessage("Du hast gewonnen" + item.getType().name().toLowerCase() + "§e\"");
                }
            }

        };

        callableChanger.change(Bukkit.getScheduler().scheduleSyncRepeatingTask(CaseOpening.getInstance(), runnable, 2, 2));
    }

}

package me.moontimer.caseopening.manager;

import org.bukkit.Material;

public enum Case {

    VoteChest("§fVote Kiste", "VoteChest", Material.CHEST),
    SupremeChest("§pSupreme Kiste", "SupremeChest", Material.ENDER_PORTAL_FRAME),
    QuestChest("§eQuest Kiste", "QuestChest", Material.ENCHANTMENT_TABLE);


    private final String name, sqlName;
    private final Material material;
    Case(String name, String sqlName, Material material) {
        this.name = name;
        this.sqlName = sqlName;
        this.material = material;
    }

    public String getName() {
        return name;
    }

    public String getSqlName() {
        return sqlName;
    }

    public Material getMaterial() {
        return material;
    }
}

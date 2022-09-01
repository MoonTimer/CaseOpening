package me.moontimer.caseopening;

import me.moontimer.caseopening.utils.MySQL;
import org.bukkit.plugin.java.JavaPlugin;

public final class CaseOpening extends JavaPlugin {

    private static CaseOpening instance;
    private MySQL mySQL;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        mySQL = new MySQL();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static CaseOpening getInstance() {
        return instance;
    }

    public MySQL getMySQL() {
        return mySQL;
    }
}

package land.euko.enchantrework;

import com.tcoded.folialib.FoliaLib;
import land.euko.enchantrework.config.ConfigManager;
import land.euko.enchantrework.domain.service.DamageCalculationService;
import land.euko.enchantrework.domain.service.MobCategoryService;
import land.euko.enchantrework.infrastructure.command.ReloadCommand;
import land.euko.enchantrework.infrastructure.listener.DamageListener;
import lombok.Getter;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Getter
    private FoliaLib foliaLib;

    @Getter
    private ConfigManager configManager;

    private DamageCalculationService damageService;
    private MobCategoryService mobCategoryService;

    @Override
    public void onEnable() {
        foliaLib = new FoliaLib(this);

        saveDefaultConfig();
        configManager = new ConfigManager(this);

        mobCategoryService = new MobCategoryService();
        damageService = new DamageCalculationService(configManager);

        registerListeners();
        registerCommands();

        getLogger().info("EnchantmentRework successfully enabled!");
        getLogger().info("Folia server: " + foliaLib.isFolia());
    }

    @Override
    public void onDisable() {
        if (foliaLib != null) {
            foliaLib.getScheduler().cancelAllTasks();
        }

        getLogger().info("EnchantmentRework disabled!");
    }

    public void reloadPluginConfig() {
        reloadConfig();

        configManager = new ConfigManager(this);
        damageService = new DamageCalculationService(configManager);

        HandlerList.unregisterAll(this);
        registerListeners();

        getLogger().info("Configuration reloaded!");
    }


    private void registerListeners() {
        DamageListener damageListener = new DamageListener(
                configManager,
                mobCategoryService,
                damageService
        );

        getServer().getPluginManager().registerEvents(damageListener, this);
    }

    private void registerCommands() {
        ReloadCommand reloadCommand = new ReloadCommand(this);
        getCommand("enchantrework").setExecutor(reloadCommand);
    }
}
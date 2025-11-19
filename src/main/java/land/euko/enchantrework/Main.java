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

/**
 * Переработка механики зачарований Бич членистоногих и Небесная кара
 */
public final class Main extends JavaPlugin {

    @Getter
    private FoliaLib foliaLib;

    @Getter
    private ConfigManager configManager;

    private DamageCalculationService damageService;
    private MobCategoryService mobCategoryService;

    @Override
    public void onEnable() {
        // Инициализация FoliaLib для совместимости с Folia
        foliaLib = new FoliaLib(this);

        // Загрузка конфигурации
        saveDefaultConfig();
        configManager = new ConfigManager(this);

        // Инициализация сервисов (Manual DI)
        mobCategoryService = new MobCategoryService();
        damageService = new DamageCalculationService(configManager);

        // Регистрация слушателей
        registerListeners();

        // Регистрация команд
        registerCommands();

        getLogger().info("EnchantmentRework успешно запущен!");
        getLogger().info("Поддержка Folia: " + foliaLib.isFolia());
    }

    @Override
    public void onDisable() {
        // Отмена всех задач FoliaLib
        if (foliaLib != null) {
            foliaLib.getScheduler().cancelAllTasks();
        }

        getLogger().info("EnchantmentRework выключен!");
    }

    /**
     * Перезагрузка конфигурации плагина
     */

    public void reloadPluginConfig() {
        reloadConfig();

        configManager = new ConfigManager(this);
        damageService = new DamageCalculationService(configManager);

        HandlerList.unregisterAll(this);
        registerListeners();

        getLogger().info("Конфигурация перезагружена!");
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
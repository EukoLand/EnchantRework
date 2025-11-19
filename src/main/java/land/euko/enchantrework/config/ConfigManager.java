package land.euko.enchantrework.config;

import land.euko.enchantrework.Main;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private final Main plugin;

    @Getter
    private final PluginConfig config;

    public ConfigManager(Main plugin) {
        this.plugin = plugin;
        this.config = loadConfig();
    }

    private PluginConfig loadConfig() {
        FileConfiguration cfg = plugin.getConfig();

        return PluginConfig.builder()
                .enabled(cfg.getBoolean("enabled", true))
                .playerDamageOnly(cfg.getBoolean("player-damage-only", true))
                .roundingMode(parseRoundingMode(cfg.getString("rounding-mode", "STANDARD")))
                .minimumDamageMode(parseMinimumDamageMode(cfg.getString("minimum-damage-mode", "MINIMUM")))
                .noEnchantMultiplier(cfg.getDouble("multiplier.no-enchant", 0.70))
                .withEnchantMultiplier(cfg.getDouble("multiplier.with-enchant", 1.30))
                .build();
    }

    private PluginConfig.RoundingMode parseRoundingMode(String value) {
        try {
            return PluginConfig.RoundingMode.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning(
                    "Invalid rounding mode: " + value + ". Using STANDARD."
            );
            return PluginConfig.RoundingMode.STANDARD;
        }
    }

    private PluginConfig.MinimumDamageMode parseMinimumDamageMode(String value) {
        try {
            return PluginConfig.MinimumDamageMode.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning(
                    "Invalid minimum damage mode: " + value + ". Using MINIMUM."
            );
            return PluginConfig.MinimumDamageMode.MINIMUM;
        }
    }
}
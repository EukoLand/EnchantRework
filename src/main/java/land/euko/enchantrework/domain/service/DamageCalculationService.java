package land.euko.enchantrework.domain.service;

import land.euko.enchantrework.config.ConfigManager;
import land.euko.enchantrework.config.PluginConfig;
import land.euko.enchantrework.domain.model.DamageModifier;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DamageCalculationService {

    private final ConfigManager configManager;

    /**
     * Вычисляет финальный урон с учётом модификаторов
     *
     * @param modifier модификатор урона
     * @return финальный урон после всех модификаций
     */
    public double calculate(DamageModifier modifier) {
        // Если модификатор не применим, возвращаем базовый урон
        if (!modifier.shouldApplyModifier()) {
            return modifier.getBaseDamage();
        }

        // Применяем множитель
        double rawDamage = modifier.getBaseDamage() * modifier.getMultiplier();

        // Применяем округление
        double roundedDamage = applyRounding(rawDamage);

        // Применяем правило минимального урона
        double finalDamage = applyMinimumDamage(roundedDamage);

        return finalDamage;
    }

    /**
     * Применяет округление согласно настройкам конфига
     */
    private double applyRounding(double damage) {
        PluginConfig.RoundingMode mode = configManager.getConfig().getRoundingMode();

        return switch (mode) {
            case STANDARD -> Math.round(damage);
            case ALWAYS_DOWN -> Math.floor(damage);
            case ALWAYS_UP -> Math.ceil(damage);
        };
    }

    /**
     * Применяет правило минимального урона согласно настройкам конфига
     */
    private double applyMinimumDamage(double damage) {
        if (damage >= 1.0) {
            return damage;
        }

        PluginConfig.MinimumDamageMode mode = configManager.getConfig().getMinimumDamageMode();

        return switch (mode) {
            case ZERO -> 0.0;
            case MINIMUM -> 1.0;
            case ALLOW_DECIMAL -> damage;
        };
    }
}
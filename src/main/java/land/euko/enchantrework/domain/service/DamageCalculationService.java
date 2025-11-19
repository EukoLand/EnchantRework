package land.euko.enchantrework.domain.service;

import land.euko.enchantrework.config.ConfigManager;
import land.euko.enchantrework.config.PluginConfig;
import land.euko.enchantrework.domain.model.DamageModifier;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DamageCalculationService {

    private final ConfigManager configManager;

    /**
     * Calculates final damage after applying all modifiers
     *
     * @param modifier modifier to be applied
     * @return final damage
     */
    public double calculate(DamageModifier modifier) {
        if (!modifier.shouldApplyModifier()) {
            return modifier.getBaseDamage();
        }

        double rawDamage = modifier.getBaseDamage() * modifier.getMultiplier();
        double roundedDamage = applyRounding(rawDamage);
        double finalDamage = applyMinimumDamage(roundedDamage);

        return finalDamage;
    }

    private double applyRounding(double damage) {
        PluginConfig.RoundingMode mode = configManager.getConfig().getRoundingMode();

        return switch (mode) {
            case STANDARD -> Math.round(damage);
            case ALWAYS_DOWN -> Math.floor(damage);
            case ALWAYS_UP -> Math.ceil(damage);
        };
    }

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
package land.euko.enchantrework.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DamageModifier {

    /**
     * Base damage before modification
     */
    private final double baseDamage;

    /**
     * Category of the mob-victim
     */
    private final MobCategory mobCategory;

    /**
     * Does the weapon have an enchantment?
     */
    private final boolean hasEnchantment;

    /**
     * Damage multiplier without enchantment
     */
    private final double noEnchantMultiplier;

    /**
     * Damage multiplier with enchantment
     */
    private final double withEnchantMultiplier;

    /**
     * Получает применимый множитель урона
     */
    public double getMultiplier() {
        return hasEnchantment ? withEnchantMultiplier : noEnchantMultiplier;
    }

    /**
     * Проверяет, нужно ли применять модификатор к данному мобу
     *
     * @return true если моб относится к специальной категории
     */
    public boolean shouldApplyModifier() {
        return mobCategory != MobCategory.NORMAL;
    }
}
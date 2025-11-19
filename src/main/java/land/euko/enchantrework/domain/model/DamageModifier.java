package land.euko.enchantrework.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DamageModifier {

    /**
     * Базовый урон до модификации
     */
    private final double baseDamage;

    /**
     * Категория моба-жертвы
     */
    private final MobCategory mobCategory;

    /**
     * Есть ли соответствующее зачарование на оружии
     */
    private final boolean hasEnchantment;

    /**
     * Множитель урона без зачарования
     */
    private final double noEnchantMultiplier;

    /**
     * Множитель урона с зачарованием
     */
    private final double withEnchantMultiplier;

    /**
     * Получает применимый множитель урона
     *
     * @return множитель урона (0.70 или 1.30)
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
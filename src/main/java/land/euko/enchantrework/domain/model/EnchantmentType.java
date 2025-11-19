package land.euko.enchantrework.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.enchantments.Enchantment;

@Getter
@RequiredArgsConstructor
public enum EnchantmentType {

    /**
     * Бич членистоногих - эффективен против пауков, пчёл и т.д.
     */
    BANE_OF_ARTHROPODS(Enchantment.BANE_OF_ARTHROPODS, MobCategory.ARTHROPOD),

    /**
     * Небесная кара - эффективна против нежити
     */
    SMITE(Enchantment.SMITE, MobCategory.UNDEAD);

    private final Enchantment bukkitEnchantment;
    private final MobCategory targetCategory;

    /**
     * Находит тип зачарования по категории моба
     *
     * @param category категория моба
     * @return тип зачарования или null, если не найден
     */
    public static EnchantmentType findByCategory(MobCategory category) {
        for (EnchantmentType type : values()) {
            if (type.targetCategory == category) {
                return type;
            }
        }
        return null;
    }
}
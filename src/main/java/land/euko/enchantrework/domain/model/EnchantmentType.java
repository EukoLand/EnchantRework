package land.euko.enchantrework.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.enchantments.Enchantment;

@Getter
@RequiredArgsConstructor
public enum EnchantmentType {

    BANE_OF_ARTHROPODS(Enchantment.BANE_OF_ARTHROPODS, MobCategory.ARTHROPOD),
    SMITE(Enchantment.SMITE, MobCategory.UNDEAD);

    private final Enchantment bukkitEnchantment;
    private final MobCategory targetCategory;

    /**
     * Founding enchantment type by mob category
     *
     * @param category mob category
     * @return type of enchantment or null
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
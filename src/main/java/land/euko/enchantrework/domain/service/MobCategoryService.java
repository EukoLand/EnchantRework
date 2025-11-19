package land.euko.enchantrework.domain.service;

import land.euko.enchantrework.domain.model.MobCategory;
import org.bukkit.entity.EntityType;

public class MobCategoryService {

    /**
     * Определяет категорию моба по его типу
     *
     * @param entityType тип сущности Bukkit
     * @return категория моба
     */
    public MobCategory determineCategory(EntityType entityType) {
        if (MobCategory.ARTHROPOD.contains(entityType)) {
            return MobCategory.ARTHROPOD;
        }

        if (MobCategory.UNDEAD.contains(entityType)) {
            return MobCategory.UNDEAD;
        }

        return MobCategory.NORMAL;
    }
}
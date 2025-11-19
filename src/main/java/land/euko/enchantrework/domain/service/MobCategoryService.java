package land.euko.enchantrework.domain.service;

import land.euko.enchantrework.domain.model.MobCategory;
import org.bukkit.entity.EntityType;

public class MobCategoryService {

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
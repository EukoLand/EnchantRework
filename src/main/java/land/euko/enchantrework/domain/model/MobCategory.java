package land.euko.enchantrework.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.EntityType;

import java.util.EnumSet;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public enum MobCategory {

    ARTHROPOD(EnumSet.of(
            EntityType.SPIDER,
            EntityType.CAVE_SPIDER,
            EntityType.BEE,
            EntityType.SILVERFISH,
            EntityType.ENDERMITE
    )),

    UNDEAD(EnumSet.of(
            EntityType.ZOMBIE,
            EntityType.ZOMBIE_VILLAGER,
            EntityType.HUSK,
            EntityType.DROWNED,
            EntityType.SKELETON,
            EntityType.STRAY,
            EntityType.WITHER_SKELETON,
            EntityType.PHANTOM,
            EntityType.WITHER,
            EntityType.ZOGLIN,
            EntityType.ZOMBIFIED_PIGLIN,
            EntityType.ZOMBIE_HORSE,
            EntityType.SKELETON_HORSE,
            EntityType.BOGGED
    )),

    /**
     * Mobs with no special category
     */
    NORMAL(EnumSet.noneOf(EntityType.class));

    private final Set<EntityType> entities;

    /**
     * Checks if the mob category contains the given entity type
     */
    public boolean contains(EntityType type) {
        return entities.contains(type);
    }
}
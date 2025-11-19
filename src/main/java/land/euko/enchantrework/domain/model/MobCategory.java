package land.euko.enchantrework.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.EntityType;

import java.util.EnumSet;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public enum MobCategory {

    /**
     * Членистоногие мобы - уязвимы к Бичу членистоногих
     */
    ARTHROPOD(EnumSet.of(
            EntityType.SPIDER,
            EntityType.CAVE_SPIDER,
            EntityType.BEE,
            EntityType.SILVERFISH,
            EntityType.ENDERMITE
    )),

    /**
     * Нежить - уязвима к Небесной каре
     */
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
            EntityType.ZOMBIFIED_PIGLIN
    )),

    /**
     * Обычные мобы - без специальных модификаторов
     */
    NORMAL(EnumSet.noneOf(EntityType.class));

    private final Set<EntityType> entities;

    /**
     * Проверяет, принадлежит ли тип сущности к данной категории
     */
    public boolean contains(EntityType type) {
        return entities.contains(type);
    }
}
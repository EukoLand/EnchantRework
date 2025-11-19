package land.euko.enchantrework.infrastructure.listener;

import land.euko.enchantrework.config.ConfigManager;
import land.euko.enchantrework.domain.model.DamageModifier;
import land.euko.enchantrework.domain.model.EnchantmentType;
import land.euko.enchantrework.domain.model.MobCategory;
import land.euko.enchantrework.domain.service.DamageCalculationService;
import land.euko.enchantrework.domain.service.MobCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

@Slf4j
@RequiredArgsConstructor
public class DamageListener implements Listener {

    private final ConfigManager configManager;
    private final MobCategoryService mobCategoryService;
    private final DamageCalculationService damageService;

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onDamageByEntity(EntityDamageByEntityEvent event) {
        handleDamage(event, event.getDamager());
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onAnyDamage(EntityDamageEvent event) {
        if (configManager.getConfig().isPlayerDamageOnly()) {
            return;
        }

        handleDamage(event, null);
    }


    private void handleDamage(EntityDamageEvent event, Entity damager) {
        if (!configManager.getConfig().isEnabled()) {
            return;
        }

        if (!(event.getEntity() instanceof LivingEntity victim)) {
            return;
        }

        MobCategory category = mobCategoryService.determineCategory(victim.getType());
        if (category == MobCategory.NORMAL) {
            return;
        }

        boolean hasEnchant = false;

        if (damager instanceof Player player) {
            hasEnchant = checkEnchantment(player, category);
        }

        double base = event.getDamage();

        DamageModifier modifier = DamageModifier.builder()
                .baseDamage(base)
                .mobCategory(category)
                .hasEnchantment(hasEnchant)
                .noEnchantMultiplier(configManager.getConfig().getNoEnchantMultiplier())
                .withEnchantMultiplier(configManager.getConfig().getWithEnchantMultiplier())
                .build();

        double newDamage = damageService.calculate(modifier);
        event.setDamage(newDamage);
    }


    private boolean checkEnchantment(Player player, MobCategory category) {
        ItemStack weapon = player.getInventory().getItemInMainHand();
        if (weapon == null || weapon.getType() == Material.AIR) {
            return false;
        }
        EnchantmentType enchantType = EnchantmentType.findByCategory(category);
        if (enchantType == null) {
            return false;
        }
        return weapon.getEnchantmentLevel(enchantType.getBukkitEnchantment()) > 0;
    }
}

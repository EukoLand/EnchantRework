package land.euko.enchantrework.config;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PluginConfig {

    private final boolean enabled;
    private final boolean playerDamageOnly;
    private final RoundingMode roundingMode;
    private final MinimumDamageMode minimumDamageMode;

    private final double noEnchantMultiplier;
    private final double withEnchantMultiplier;

    /**
     * Режимы округления урона
     */
    public enum RoundingMode {
        /** Стандартное округление (0.5 и выше -> вверх) */
        STANDARD,

        /** Всегда округлять вниз */
        ALWAYS_DOWN,

        /** Всегда округлять вверх */
        ALWAYS_UP
    }

    /**
     * Режимы обработки минимального урона
     */
    public enum MinimumDamageMode {
        /** Наносить 0 урона при уроне < 1.0 */
        ZERO,

        /** Наносить минимум 1.0 урона */
        MINIMUM,

        /** Разрешить дробный урон < 1.0 */
        ALLOW_DECIMAL
    }
}
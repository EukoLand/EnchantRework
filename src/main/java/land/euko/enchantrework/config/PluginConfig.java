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

    public enum RoundingMode {
        /** Standard rounding (0.5 and higher -> 1) */
        STANDARD,

        /** Always round down */
        ALWAYS_DOWN,

        /** Always round up */
        ALWAYS_UP
    }

    public enum MinimumDamageMode {
        /** 0 Damage when < 1.0 */
        ZERO,

        /** Minimum damage is 1.0 */
        MINIMUM,

        /** Allow decimal damage */
        ALLOW_DECIMAL
    }
}
package ipsis.woot.items;

import ipsis.woot.util.FactoryTier;
import net.minecraft.item.ItemStack;

public interface IBuilderItem {

    FactoryTier getTier(ItemStack itemStack);
    BuilderModes getBuilderMode(ItemStack itemStack);

    enum BuilderModes {
        BUILD_1(FactoryTier.TIER_1),
        BUILD_2(FactoryTier.TIER_2),
        BUILD_3(FactoryTier.TIER_3),
        BUILD_4(FactoryTier.TIER_4),
        VALIDATE_1(FactoryTier.TIER_1),
        VALIDATE_2(FactoryTier.TIER_2),
        VALIDATE_3(FactoryTier.TIER_3),
        VALIDATE_4(FactoryTier.TIER_4);

        public static BuilderModes DEFAULT_MODE = BUILD_1;

        private static BuilderModes[] VALUES = values();
        public BuilderModes getNext() {
            return VALUES[(this.ordinal() + 1 ) % VALUES.length];
        }

        private FactoryTier tier;
        BuilderModes(FactoryTier tier ) {
            this.tier = tier;
        }

        public FactoryTier getFactoryTier() {
            return this.tier;
        }

        public boolean isBuildMode() {
            return this == BUILD_1 || this == BUILD_2 || this == BUILD_3 || this == BUILD_4;
        }

        public boolean isValidateMode() {
            return this == VALIDATE_1 || this == VALIDATE_2 || this == VALIDATE_3 || this == VALIDATE_4;
        }
    }
}

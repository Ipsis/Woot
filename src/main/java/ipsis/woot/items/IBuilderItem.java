package ipsis.woot.items;

import ipsis.woot.util.FactoryTier;
import net.minecraft.item.ItemStack;

import java.util.EnumSet;

public interface IBuilderItem {

    FactoryTier getTier(ItemStack itemStack);
    BuilderModes getBuilderMode(ItemStack itemStack);

    enum BuilderModes {
        BUILD_1(FactoryTier.TIER_1),
        BUILD_2(FactoryTier.TIER_2),
        BUILD_3(FactoryTier.TIER_3),
        BUILD_4(FactoryTier.TIER_4),
        BUILD_5(FactoryTier.TIER_5),
        VALIDATE_1(FactoryTier.TIER_1),
        VALIDATE_2(FactoryTier.TIER_2),
        VALIDATE_3(FactoryTier.TIER_3),
        VALIDATE_4(FactoryTier.TIER_4),
        VALIDATE_5(FactoryTier.TIER_5);

        private static EnumSet<BuilderModes> BUILD_MODES = EnumSet.of(BUILD_1, BUILD_2, BUILD_3, BUILD_4, BUILD_5);
        private static EnumSet<BuilderModes> VALIDATE_MODES = EnumSet.of(VALIDATE_1, VALIDATE_2, VALIDATE_3, VALIDATE_4, VALIDATE_5);


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
            return BUILD_MODES.contains(this);
        }

        public boolean isValidateMode() {
            return VALIDATE_MODES.contains(this);
        }
    }
}

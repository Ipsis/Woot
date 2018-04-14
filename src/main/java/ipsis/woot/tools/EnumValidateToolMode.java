package ipsis.woot.tools;

import ipsis.woot.multiblock.EnumMobFactoryTier;
import ipsis.woot.reference.Reference;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nullable;

public enum EnumValidateToolMode {

    VALIDATE_T1,
    VALIDATE_T2,
    VALIDATE_T3,
    VALIDATE_T4,
    VALIDATE_IMPORT,
    VALIDATE_EXPORT;

    public static EnumValidateToolMode getValidateToolMode(int id) {

        return values()[MathHelper.clamp(id, 0, values().length - 1)];
    }

    public EnumValidateToolMode getNext() {

        int next = (this.ordinal() + 1) % values().length;
        return values()[next];
    }

    @Nullable
    public EnumMobFactoryTier getTierFromMode() {

        EnumMobFactoryTier tier = null;
        if (this == VALIDATE_T1)
            tier = EnumMobFactoryTier.TIER_ONE;
        else if (this == VALIDATE_T2)
            tier = EnumMobFactoryTier.TIER_TWO;
        else if (this == VALIDATE_T3)
            tier = EnumMobFactoryTier.TIER_THREE;
        else if (this == VALIDATE_T4)
            tier = EnumMobFactoryTier.TIER_FOUR;


        return tier;
    }

    public boolean isValidateTierMode() {

        return this == VALIDATE_T1 || this == VALIDATE_T2 || this == VALIDATE_T3 || this == VALIDATE_T4;
    }

    public String getUnlocalisedName() {

        return "info." + Reference.MOD_ID + ".hammer.mode." + this.name().toLowerCase();
    }
}

package ipsis.woot.plugins.bloodmagic;

import WayofTime.bloodmagic.api.impl.BloodMagicAPI;
import WayofTime.bloodmagic.ritual.IMasterRitualStone;
import ipsis.Woot;
import ipsis.woot.util.DebugSetup;
import ipsis.woot.util.WootMobName;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;

public class BloodMagicHelper {

    public static @Nonnull
    ItemStack createWill() {

        return ItemStack.EMPTY;
    }

    public static int getLifeEssenceRatio(WootMobName wootMobName) {

        int lifeEssenceRatio = 0;

        if (!BloodMagicAPI.INSTANCE.getBlacklist().getSacrifice().contains(wootMobName.getResourceLocation()))
            lifeEssenceRatio = BloodMagicAPI.INSTANCE.getValueManager().getSacrificial().getOrDefault(wootMobName.getResourceLocation(), BloodMagic.SACRIFICE_AMOUNT);
        else
            Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_BM_LE, "createBloodEssence", wootMobName + " is blacklisted by BM");

        return lifeEssenceRatio;
    }

    public static @Nonnull
    FluidStack createBloodEssence(WootMobName wootMobName, int numMobs, int sacrificeRuneCount) {

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_BM_LE, "createBloodEssence", wootMobName + ":" + numMobs + " sacrificeRuneCount:" + sacrificeRuneCount);

        FluidStack out = new FluidStack(BloodMagic.lifeEssence, 0);

        /**
         * This is based directly off the Well Of Suffering calculation.
         * The idea is to offer the same output as the BloodMagic ritual, not to exceed it.
         */
        int lifeEssenceRatio = getLifeEssenceRatio(wootMobName);
        if (lifeEssenceRatio > 0) {
            float sacrificeEfficiencyMultiplier = (float)(0.10 * sacrificeRuneCount);
            out.amount = (int)(numMobs * ((1 + sacrificeEfficiencyMultiplier) * lifeEssenceRatio));
            Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_BM_LE, "createBloodEssence", "lifeEssenceRate:" + lifeEssenceRatio + " sacrificeEfficiencyMultiplier:" + sacrificeEfficiencyMultiplier);
        }

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_BM_LE, "createBloodEssence", out);
        return out;
    }

    public static boolean canPerformRitual(IMasterRitualStone masterRitualStone, int refreshCost) {

        int currentEssence = masterRitualStone.getOwnerNetwork().getCurrentEssence();
        if (currentEssence < refreshCost) {
            masterRitualStone.getOwnerNetwork().causeNausea();
            return false;
        }

        return true;
    }
}

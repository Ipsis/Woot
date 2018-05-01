package ipsis.woot.loot.generators;

import ipsis.Woot;
import ipsis.woot.loot.LootGenerationFarmInfo;
import ipsis.woot.plugins.bloodmagic.BloodMagic;
import ipsis.woot.plugins.bloodmagic.BloodMagicHelper;
import ipsis.woot.util.ConfigKeyHelper;
import ipsis.woot.util.DebugSetup;
import ipsis.woot.util.EnumFarmUpgrade;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class BloodMagicLifeEssenceGenerator implements ILootGenerator {

    @Override
    public void generate(World world, LootGenerationFarmInfo farmInfo) {

        if (BloodMagic.lifeEssence == null)
            return;

        if (!farmInfo.farmSetup.hasUpgrade(EnumFarmUpgrade.BM_LE_TANK))
            return;

        // Is there anywhere to put it
        if (farmInfo.fluidHandlerList.size() == 0)
            return;

        if (!farmInfo.keepAliveTankRitual)
            return;


        int runeCount = Woot.wootConfiguration.getInteger(farmInfo.farmSetup.getWootMobName(), ConfigKeyHelper.getBmLeTankParam(farmInfo.farmSetup.getUpgradeLevel(EnumFarmUpgrade.BM_LE_TANK)));
        FluidStack fluidStack = BloodMagicHelper.createBloodEssence(farmInfo.farmSetup.getWootMobName(), farmInfo.farmSetup.getNumMobs(), runeCount);
        Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_BM_LE, "generateBloodEssence", "generate: " + fluidStack);
        if (fluidStack.amount > 0) {

            int left = fluidStack.amount;
            for (IFluidHandler hdlr : farmInfo.fluidHandlerList) {
                if (left == 0)
                    break;

                FluidStack out = new FluidStack(fluidStack.getFluid(), left);
                int filled = hdlr.fill(out, true);
                Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_BM_LE, "generateBloodEssence", "filled:" + filled);
                left -= filled;
                if (left < 0)
                    left = 0;
            }
        }



    }
}

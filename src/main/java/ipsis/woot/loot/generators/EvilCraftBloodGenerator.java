package ipsis.woot.loot.generators;

import ipsis.Woot;
import ipsis.woot.loot.LootGenerationFarmInfo;
import ipsis.woot.plugins.evilcraft.EvilCraft;
import ipsis.woot.plugins.evilcraft.EvilCraftHelper;
import ipsis.woot.util.ConfigKeyHelper;
import ipsis.woot.util.DebugSetup;
import ipsis.woot.util.EnumFarmUpgrade;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class EvilCraftBloodGenerator implements ILootGenerator {

    @Override
    public void generate(World world, LootGenerationFarmInfo farmInfo) {

        if (EvilCraft.blood == null)
            return;

        if (!farmInfo.farmSetup.hasUpgrade(EnumFarmUpgrade.EC_BLOOD))
            return;

        // Is there anywhere to put it
        if (farmInfo.fluidHandlerList.size() == 0)
            return;

        int mbPerHp = Woot.wootConfiguration.getInteger(farmInfo.farmSetup.getWootMobName(), ConfigKeyHelper.getEcBloodParam(farmInfo.farmSetup.getUpgradeLevel(EnumFarmUpgrade.EC_BLOOD)));
        FluidStack fluidStack = EvilCraftHelper.createBlood(world, farmInfo.farmSetup.getWootMobName(), farmInfo.farmSetup.getNumMobs(), mbPerHp);

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_EC, "generateBlood", "generate: " + fluidStack);
        if (fluidStack.amount > 0) {

            int left = fluidStack.amount;
            for (IFluidHandler hdlr : farmInfo.fluidHandlerList) {
                if (left == 0)
                    break;

                FluidStack out = new FluidStack(fluidStack.getFluid(), left);
                int filled = hdlr.fill(out, true);
                Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_EC, "generateBlood", "filled:" + filled);
                left -= filled;
                if (left < 0)
                    left = 0;
            }
        }
    }
}

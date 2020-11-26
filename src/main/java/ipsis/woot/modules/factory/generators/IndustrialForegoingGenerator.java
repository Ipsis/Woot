package ipsis.woot.modules.factory.generators;

import ipsis.woot.compat.industforegoing.IndustrialForegoingPlugin;
import ipsis.woot.modules.factory.FormedSetup;
import ipsis.woot.modules.factory.PerkType;
import ipsis.woot.util.FakeMob;
import net.minecraft.fluid.Fluids;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

public class IndustrialForegoingGenerator {

    public static class GeneratedFluids {
        public FluidStack meat = FluidStack.EMPTY;
        public FluidStack pink = FluidStack.EMPTY;
        public FluidStack essence = FluidStack.EMPTY;
    }

    public static GeneratedFluids getFluids(FormedSetup formedSetup, World world) {
        GeneratedFluids generatedFluids = new GeneratedFluids();
        for (FakeMob fakeMob : formedSetup.getAllMobs()) {
            int mobCount = formedSetup.getAllMobParams().get(fakeMob).getMobCount(formedSetup.getAllPerks().containsKey(PerkType.MASS), formedSetup.hasMassExotic());
            FluidStack fluidStack = IndustrialForegoingPlugin.getLiquidMeatAmount(fakeMob, world);
            if (!fluidStack.isEmpty()) {
                fluidStack.setAmount(fluidStack.getAmount() * mobCount);
                if (generatedFluids.meat.isEmpty())
                    generatedFluids.meat = fluidStack.copy();
                else
                    generatedFluids.meat.setAmount(generatedFluids.meat.getAmount() + fluidStack.getAmount());
            }

            fluidStack = IndustrialForegoingPlugin.getPinkSlimeAmount(fakeMob, world);
            if (!fluidStack.isEmpty()) {
                fluidStack.setAmount(fluidStack.getAmount() * mobCount);
                if (generatedFluids.pink.isEmpty())
                    generatedFluids.pink = fluidStack.copy();
                else
                    generatedFluids.pink.setAmount(generatedFluids.pink.getAmount() + fluidStack.getAmount());
            }

            fluidStack = IndustrialForegoingPlugin.getEssenceAmount(fakeMob, world);
            if (!fluidStack.isEmpty()) {
                fluidStack.setAmount(fluidStack.getAmount() * mobCount);
                if (generatedFluids.essence.isEmpty())
                    generatedFluids.essence = fluidStack.copy();
                else
                    generatedFluids.essence.setAmount(generatedFluids.essence.getAmount() + fluidStack.getAmount());
            }
        }

        return generatedFluids;
    }
}

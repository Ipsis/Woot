package ipsis.woot.modules.factory.generators;

import ipsis.woot.compat.industforegoing.IndustrialForegoingPlugin;
import ipsis.woot.modules.factory.FormedSetup;
import ipsis.woot.util.FakeMob;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class IndustrialForegoingGenerator {

    public class GeneratedFluids {
        public FluidStack meat = FluidStack.EMPTY;
        public FluidStack pink = FluidStack.EMPTY;
        public FluidStack essence = FluidStack.EMPTY;
    }

    public GeneratedFluids getFluids(FormedSetup formedSetup, World world) {
        GeneratedFluids generatedFluids = new GeneratedFluids();
        for (FakeMob fakeMob : formedSetup.getAllMobs()) {
            FluidStack fluidStack = IndustrialForegoingPlugin.getLiquidMeatAmount(fakeMob, world);
            if (!fluidStack.isEmpty()) {
                if (generatedFluids.meat.isEmpty())
                    generatedFluids.meat = fluidStack.copy();
                else
                    generatedFluids.meat.setAmount(generatedFluids.meat.getAmount() + fluidStack.getAmount());
            }

            fluidStack = IndustrialForegoingPlugin.getPinkSlimeAmount(fakeMob, world);
            if (!fluidStack.isEmpty()) {
                if (generatedFluids.pink.isEmpty())
                    generatedFluids.pink = fluidStack.copy();
                else
                    generatedFluids.pink.setAmount(generatedFluids.pink.getAmount() + fluidStack.getAmount());
            }

            fluidStack = IndustrialForegoingPlugin.getEssenceAmount(fakeMob, world);
            if (!fluidStack.isEmpty()) {
                if (generatedFluids.essence.isEmpty())
                    generatedFluids.essence = fluidStack.copy();
                else
                    generatedFluids.essence.setAmount(generatedFluids.essence.getAmount() + fluidStack.getAmount());
            }
        }
        return generatedFluids;
    }
}

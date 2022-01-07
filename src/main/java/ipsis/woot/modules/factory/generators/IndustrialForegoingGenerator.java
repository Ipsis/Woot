package ipsis.woot.modules.factory.generators;

import ipsis.woot.compat.industforegoing.IndustrialForegoingPlugin;
import ipsis.woot.modules.factory.FactoryConfiguration;
import ipsis.woot.modules.factory.FormedSetup;
import ipsis.woot.modules.factory.perks.Perk;
import ipsis.woot.util.FakeMob;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

public class IndustrialForegoingGenerator {

    public static class GeneratedFluids {
        public FluidStack meat = FluidStack.EMPTY;
        public FluidStack pink = FluidStack.EMPTY;
        public FluidStack essence = FluidStack.EMPTY;
        public FluidStack ether = FluidStack.EMPTY;
    }

    public static GeneratedFluids getFluids(FormedSetup formedSetup, World world) {
        GeneratedFluids generatedFluids = new GeneratedFluids();
        for (FakeMob fakeMob : formedSetup.getAllMobs()) {
            int mobCount = formedSetup.getAllMobParams().get(fakeMob).getMobCount(formedSetup.getAllPerks().containsKey(Perk.Group.MASS), formedSetup.hasMassExotic());
            FluidStack fluidStack = IndustrialForegoingPlugin.getLiquidMeatAmount(fakeMob, world);
            if (!fluidStack.isEmpty()) {
                int perk_tier = formedSetup.getAllPerks().getOrDefault(Perk.Group.SLAUGHTER, 0);
                if (perk_tier > 0) {
                    int configVal = FactoryConfiguration.getPerkIntValue(Perk.Group.SLAUGHTER, perk_tier).get();
                    fluidStack.setAmount((fluidStack.getAmount() * mobCount * configVal) / 100);
                    if (generatedFluids.meat.isEmpty())
                        generatedFluids.meat = fluidStack.copy();
                    else
                        generatedFluids.meat.setAmount(generatedFluids.meat.getAmount() + fluidStack.getAmount());
                }
            }

            fluidStack = IndustrialForegoingPlugin.getPinkSlimeAmount(fakeMob, world);
            if (!fluidStack.isEmpty()) {
                int perk_tier = formedSetup.getAllPerks().getOrDefault(Perk.Group.SLAUGHTER, 0);
                if (perk_tier > 0) {
                    int configVal = FactoryConfiguration.getPerkIntValue(Perk.Group.SLAUGHTER, perk_tier).get();
                    fluidStack.setAmount((fluidStack.getAmount() * mobCount * configVal) / 100);
                    if (generatedFluids.pink.isEmpty())
                        generatedFluids.pink = fluidStack.copy();
                    else
                        generatedFluids.pink.setAmount(generatedFluids.pink.getAmount() + fluidStack.getAmount());
                }
            }

            fluidStack = IndustrialForegoingPlugin.getEssenceAmount(fakeMob, world);
            if (!fluidStack.isEmpty()) {
                int perk_tier = formedSetup.getAllPerks().getOrDefault(Perk.Group.CRUSHER, 0);
                if (perk_tier > 0) {
                    int configVal = FactoryConfiguration.getPerkIntValue(Perk.Group.CRUSHER, perk_tier).get();
                    fluidStack.setAmount((fluidStack.getAmount() * mobCount * configVal) / 100);
                    if (generatedFluids.essence.isEmpty())
                        generatedFluids.essence = fluidStack.copy();
                    else
                        generatedFluids.essence.setAmount(generatedFluids.essence.getAmount() + fluidStack.getAmount());
                }
            }

            fluidStack = IndustrialForegoingPlugin.getEtherAmount(fakeMob, world);
            if (!fluidStack.isEmpty()) {
                int perk_tier = formedSetup.getAllPerks().getOrDefault(Perk.Group.LASER, 0);
                if (perk_tier > 0) {
                    int configVal = FactoryConfiguration.getPerkIntValue(Perk.Group.LASER, perk_tier).get();
                    fluidStack.setAmount((fluidStack.getAmount() * mobCount * configVal) / 100);
                    if (generatedFluids.ether.isEmpty())
                        generatedFluids.ether = fluidStack.copy();
                    else
                        generatedFluids.ether.setAmount(generatedFluids.ether.getAmount() + fluidStack.getAmount());
                }
            }
        }

        return generatedFluids;
    }
}

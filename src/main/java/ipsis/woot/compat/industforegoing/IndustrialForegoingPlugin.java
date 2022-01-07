package ipsis.woot.compat.industforegoing;

import ipsis.woot.simulator.spawning.SpawnController;
import ipsis.woot.util.FakeMob;
import net.minecraft.fluid.Fluid;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ObjectHolder;

public class IndustrialForegoingPlugin {

    @ObjectHolder("industrialforegoing:meat")
    private static Fluid LIQUID_MEAT_FLUID = null;

    @ObjectHolder("industrialforegoing:pink_slime")
    private static Fluid PINK_SLIME_FLUID = null;

    @ObjectHolder("industrialforegoing:essence")
    private static Fluid ESSENCE_FLUID = null;

    @ObjectHolder("industrialforegoing:ether_gas")
    private static Fluid ETHER_FLUID = null;

    public static FluidStack getLiquidMeatAmount(FakeMob fakeMob, World world) {
        if (LIQUID_MEAT_FLUID == null || world == null || fakeMob == null || !fakeMob.isValid())
            return FluidStack.EMPTY;

        int health = SpawnController.get().getMobHealth(fakeMob, world);
        if (!SpawnController.get().isAnimal(fakeMob, world))
            health *= 20;

        return new FluidStack(LIQUID_MEAT_FLUID, health);
    }

    public static FluidStack getPinkSlimeAmount(FakeMob fakeMob, World world) {
        if (PINK_SLIME_FLUID == null || world == null || fakeMob == null || !fakeMob.isValid())
            return FluidStack.EMPTY;

        int health = SpawnController.get().getMobHealth(fakeMob, world);
        if (!SpawnController.get().isAnimal(fakeMob, world))
            health *= 20;

        return new FluidStack(PINK_SLIME_FLUID, health);
    }

    public static FluidStack getEssenceAmount(FakeMob fakeMob, World world) {
        if (ESSENCE_FLUID == null || world == null || fakeMob == null || !fakeMob.isValid())
            return FluidStack.EMPTY;

        int xp = SpawnController.get().getMobExperience(fakeMob, world);
        return new FluidStack(ESSENCE_FLUID, xp * 20);
    }

    public static FluidStack getEtherAmount(FakeMob fakeMob, World world) {
        if (ETHER_FLUID == null || world == null || fakeMob == null || !fakeMob.isValid() || !fakeMob.isWither())
            return FluidStack.EMPTY;

        return new FluidStack(ETHER_FLUID, 600);
    }
}

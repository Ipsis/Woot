package ipsis.woot.plugins.thermal;

import cofh.core.init.CoreProps;
import cofh.thermalexpansion.util.managers.machine.CrucibleManager;
import ipsis.woot.init.ModItems;
import ipsis.woot.item.ItemXpShard;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Optional;

public class Thermal {

    public static final String THERMAL_EXPANSION_MODID = "thermalexpansion";
    private static final String EXPERIENCE_NAME = "experience";

    @Optional.Method(modid = THERMAL_EXPANSION_MODID)
    public static void init() {

        // TE registers their own liquid experience
        if (FluidRegistry.isFluidRegistered(EXPERIENCE_NAME)) {

            Fluid experience = FluidRegistry.getFluid(EXPERIENCE_NAME);
            CrucibleManager.addRecipe(CrucibleManager.DEFAULT_ENERGY * 2, new ItemStack(ModItems.itemXpShard), new FluidStack(experience, ItemXpShard.XP_VALUE * CoreProps.MB_PER_XP));
        }
    }
}

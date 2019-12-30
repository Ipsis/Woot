package ipsis.woot.modules.infuser;

import ipsis.woot.crafting.InfuserRecipe;
import ipsis.woot.fluilds.FluidSetup;
import ipsis.woot.modules.squeezer.DyeMakeup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class InfuserRecipes {

    public static void load() {

        InfuserRecipe.addRecipe(
                new ItemStack(InfuserSetup.WHITE_DYE_SHARD_ITEM.get()),
                new ItemStack(InfuserSetup.WHITE_DYE_CASING_ITEM.get()),
                new FluidStack(FluidSetup.PUREDYE_FLUID.get(), DyeMakeup.LCM));
        InfuserRecipe.addRecipe(
                new ItemStack(InfuserSetup.ORANGE_DYE_SHARD_ITEM.get()),
                new ItemStack(InfuserSetup.ORANGE_DYE_CASING_ITEM.get()),
                new FluidStack(FluidSetup.PUREDYE_FLUID.get(), DyeMakeup.LCM));
    }
}

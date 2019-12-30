package ipsis.woot.modules.infuser;

import ipsis.woot.crafting.InfuserRecipe;
import ipsis.woot.fluilds.FluidSetup;
import ipsis.woot.modules.squeezer.DyeMakeup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class InfuserRegistry {

    public static final Logger LOGGER = LogManager.getLogger();

    static InfuserRegistry INSTANCE = new InfuserRegistry();
    public static InfuserRegistry get() { return INSTANCE; }

    private List<InfuserRecipe> recipes = new ArrayList<>();

    public void loadRecipes() {
        recipes.clear();

        addRecipe(new ItemStack(InfuserSetup.WHITE_DYE_CASING_ITEM.get(), 1),
                new FluidStack(FluidSetup.PUREDYE_FLUID.get(), DyeMakeup.LCM),
               new ItemStack(InfuserSetup.WHITE_DYE_SHARD_ITEM.get()));
        addRecipe(new ItemStack(InfuserSetup.ORANGE_DYE_CASING_ITEM.get(), 1),
                new FluidStack(FluidSetup.PUREDYE_FLUID.get(), DyeMakeup.LCM),
                new ItemStack(InfuserSetup.ORANGE_DYE_SHARD_ITEM.get()));
    }

    private void addRecipe(ItemStack itemStack, FluidStack fluidStack, ItemStack output) {
        LOGGER.info("addRecipe: {} + {}/{} -> {}",
                itemStack.getTranslationKey(),
                fluidStack.getTranslationKey(), fluidStack.getAmount(),
                output.getTranslationKey());
        recipes.add(new InfuserRecipe(itemStack, fluidStack, output));
    }

    public @Nullable
    InfuserRecipe getRecipe(ItemStack itemStack, FluidStack fluidStack) {
        if (itemStack != null && !itemStack.isEmpty() && fluidStack != null && !fluidStack.isEmpty()) {
            for (InfuserRecipe r : recipes) {
                if (r.isInput(itemStack) && r.isInputFluid(fluidStack))
                    return r;
            }
        }

        return null;
    }

    public boolean isInput(ItemStack itemStack) {
        if (itemStack != null && !itemStack.isEmpty())
        for (InfuserRecipe r : recipes) {
            if (r.isInput(itemStack))
                return true;
        }
        return false;
    }
}

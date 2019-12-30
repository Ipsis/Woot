package ipsis.woot.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class InfuserRecipe {

    public final WootIngredient input;
    public final FluidStack fluid;
    public final ItemStack output;

    public InfuserRecipe(ItemStack output, ItemStack input, FluidStack fluid) {
        this(output, new WootIngredient(input), fluid);
    }

    public InfuserRecipe(ItemStack output, ResourceLocation tag, FluidStack fluid) {
        this(output, new WootIngredient(tag), fluid);
    }

    private InfuserRecipe(ItemStack output, WootIngredient input, FluidStack fluid) {
        this.output = output;
        this.input = input;
        this.fluid = fluid;
    }

    public static ArrayList<InfuserRecipe> recipeList = new ArrayList<>();
    public static void addRecipe(ItemStack output, ItemStack input, FluidStack fluid) {
        InfuserRecipe recipe = new InfuserRecipe(output, input, fluid);
        recipeList.add(recipe);
    }

    public static @Nullable
    InfuserRecipe findRecipe(ItemStack input, FluidStack fluidStack) {
        if (input == null || fluidStack == null || input.isEmpty() || fluidStack.isEmpty())
            return null;

        for (InfuserRecipe recipe : recipeList) {
            if (recipe.input.isSameIngredient(input) && fluidStack.containsFluid(recipe.fluid))
                return recipe;
        }

        return null;
    }


}

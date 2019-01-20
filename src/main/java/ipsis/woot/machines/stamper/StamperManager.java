package ipsis.woot.machines.stamper;

import ipsis.woot.ModFluids;
import ipsis.woot.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StamperManager {

    public static final StamperManager INSTANCE = new StamperManager();

    public void init() {

        // Dye Plate Recipes
        addRecipe(Stamp.BLACK, new FluidStack(ModFluids.pureDye, 1000), new ItemStack(ModItems.dyeBlank, 1), new ItemStack(ModItems.blackDyePlate, 1));
        addRecipe(Stamp.RED, new FluidStack(ModFluids.pureDye, 1000), new ItemStack(ModItems.dyeBlank, 1), new ItemStack(ModItems.redDyePlate, 1));

        // Prism recipe
        addRecipe(Stamp.PRISM, new FluidStack(ModFluids.pureDye, 1000), new ItemStack(Blocks.GLASS), new ItemStack(ModItems.prism, 1));

        // Enchanted Plate Recipes
        addRecipe(Stamp.ENCHANTED_PLATE_BASIC, new FluidStack(ModFluids.pureEnchant, 1000), new ItemStack(ModItems.stygianPlate), new ItemStack(ModItems.basicEnchantedPlate));
        addRecipe(Stamp.ENCHANTED_PLATE, new FluidStack(ModFluids.pureEnchant, 2000), new ItemStack(ModItems.stygianPlate), new ItemStack(ModItems.enchantedPlate));
        addRecipe(Stamp.ENCHANTED_PLATE_ADVANCED, new FluidStack(ModFluids.pureEnchant, 3000), new ItemStack(ModItems.stygianPlate), new ItemStack(ModItems.advancedEnchantedPlate));

    }

    private static List<Recipe> recipes = new ArrayList<>();

    public static @Nullable Recipe getRecipe(Stamp stamp, Fluid fluid, ItemStack input) {

        Recipe matchingRecipe = null;
        Iterator iter = recipes.iterator();
        while (iter.hasNext()) {
            Recipe recipe = (Recipe)iter.next();
            if (recipe.stamp == stamp && recipe.fluidStack.getFluid() == fluid && recipe.input.isItemEqual(input)) {
                matchingRecipe = recipe;
                break;
            }
        }

        return matchingRecipe;
    }

    private void addRecipe(Stamp stamp, FluidStack fluidStack, ItemStack input, ItemStack output) {
        recipes.add(new Recipe(stamp, fluidStack, input, output));
    }

    public class Recipe {

        Stamp stamp;
        FluidStack fluidStack;
        ItemStack input;
        ItemStack output;

        public Recipe(Stamp stamp, FluidStack fluidStack, ItemStack input, ItemStack output) {
            this.stamp = stamp;
            this.fluidStack = fluidStack;
            this.input = input;
            this.output = output;
        }
    }


    enum Stamp {
        BLACK, RED, GREEN, BROWN,
        BLUE, PURPLE, CYAN, LIGHTGRAY,
        GRAY, PINK, LIME, YELLOW,
        LIGHTBLUE, MAGENTA, ORANGE, WHITE,
        PRISM,
        ENCHANTED_PLATE_BASIC, ENCHANTED_PLATE, ENCHANTED_PLATE_ADVANCED;

        public static final Stamp[] VALUES = Stamp.values();

        public Stamp getNext() {
            return VALUES[(this.ordinal() + 1) % VALUES.length];
        }
    }
}

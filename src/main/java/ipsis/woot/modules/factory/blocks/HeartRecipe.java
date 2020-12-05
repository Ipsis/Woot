package ipsis.woot.modules.factory.blocks;

import ipsis.woot.util.FakeMob;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Currently running recipe
 */
public class HeartRecipe {

    int numTicks;
    int numUnits;
    public List<ItemStack> recipeItems = new ArrayList<>();
    public List<FluidStack> recipeFluids = new ArrayList<>();

    public int getNumTicks() {
        return numTicks;
    }

    public int getNumUnits() {
        return numUnits;
    }

    public HeartRecipe() {
        numTicks = 1;
        numUnits = 1;
    }

    public HeartRecipe(int numTicks, int numUnits) {
        this.numTicks = MathHelper.clamp(numTicks, 1, Integer.MAX_VALUE);
        this.numUnits = MathHelper.clamp(numUnits, 1, Integer.MAX_VALUE);
    }

    public void addItem(ItemStack itemStack) {
        boolean added = false;
        for (ItemStack currStack : recipeItems) {
            if (currStack.isItemEqual(itemStack)) {
                currStack.setCount(currStack.getCount() + itemStack.getCount());
                added = true;
            }
        }

        if (!added) {
            ItemStack addStack = itemStack.copy();
            recipeItems.add(addStack);
        }
    }

    public void addFluid(FluidStack fluidStack) {
        boolean added = false;
        for (FluidStack currStack : recipeFluids) {
            if (currStack.isFluidEqual(fluidStack)) {
                currStack.setAmount(currStack.getAmount() + fluidStack.getAmount());
                added = true;
            }

        }

        if (!added) {
            FluidStack addStack = fluidStack.copy();
            recipeFluids.add(addStack);
        }
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "numTicks=" + numTicks +
                ", numUnits=" + numUnits +
                ", items=" + recipeItems.size() +
                ", fluids=" + recipeFluids.size() +
                '}';
    }
}

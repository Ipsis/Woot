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
    public HashMap<FakeMob, List<ItemStack>> items = new HashMap<>();
    public HashMap<FakeMob, List<FluidStack>> fluids = new HashMap<>();

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

    public void addItem(FakeMob fakeMob, ItemStack itemStack) {
        if (!items.containsKey(fakeMob))
            items.put(fakeMob, new ArrayList<>());

        if (!itemStack.isEmpty())
            items.get(fakeMob).add(itemStack.copy());
    }

    public void addFluid(FakeMob fakeMob, FluidStack fluidStack) {
        if (!fluids.containsKey(fakeMob))
            fluids.put(fakeMob, new ArrayList<>());

        if (!fluidStack.isEmpty())
            fluids.get(fakeMob).add(fluidStack.copy());
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "numTicks=" + numTicks +
                ", numUnits=" + numUnits +
                ", items=" + items.size() +
                ", fluids=" + fluids.size() +
                '}';
    }
}

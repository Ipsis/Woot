package ipsis.woot.farming;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class SpawnRecipe implements ISpawnRecipe {

    private List<FluidStack> fluids = new ArrayList<>();
    private List<ItemStack> items = new ArrayList<>();
    private boolean efficiency;

    public void setEfficiency(boolean efficiency) {
        this.efficiency = efficiency;
    }

    public boolean getEfficiency() {
        return this.efficiency;
    }

    @Nonnull
    public List<FluidStack> getFluids() {

        return fluids;
    }

    @Nonnull
    public List<ItemStack> getItems() {

        return items;
    }

    @Override
    public void addIngredient(ItemStack itemStack) {

        items.add(itemStack.copy());
    }

    @Override
    public void addIngredient(FluidStack fluidStack) {

        fluids.add(fluidStack.copy());
    }
}

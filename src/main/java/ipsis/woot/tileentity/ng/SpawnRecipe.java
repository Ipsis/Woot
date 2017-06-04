package ipsis.woot.tileentity.ng;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class SpawnRecipe implements ISpawnRecipe {

    @Nonnull
    public List<FluidStack> getFluids() {

        return new ArrayList<>();
    }

    @Nonnull
    public List<ItemStack> getItems() {

        return new ArrayList<>();
    }
}

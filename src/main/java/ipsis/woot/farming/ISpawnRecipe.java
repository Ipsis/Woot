package ipsis.woot.farming;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.List;

public interface ISpawnRecipe {

    @Nonnull List<FluidStack> getFluids();
    @Nonnull List<ItemStack> getItems();
}

package ipsis.woot.farming;

import ipsis.woot.util.WootMobName;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.Set;

public interface ISpawnRecipeRepository {

    @Nullable SpawnRecipe get(WootMobName wootMobName);
    void add(WootMobName wootMobName, SpawnRecipe recipe);
    Set<WootMobName> getAllMobs();

    void addDefaultItem(ItemStack itemStack);
    void addDefaultFluid(FluidStack fluidStack);
    void setDefaultEfficiency(boolean efficiency);
}

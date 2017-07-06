package ipsis.woot.crafting;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface IAnvilManager {

    void addRecipe(@Nonnull AnvilRecipe recipe);
    @Nullable
    List<ItemStack> craft(ItemStack baseItem, @Nonnull List<ItemStack> items);
}

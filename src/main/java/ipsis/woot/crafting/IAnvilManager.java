package ipsis.woot.crafting;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface IAnvilManager {

    /**
     *
     * @param output - what will the craft product
     * @param base - what item should be on the anvil
     * @param preserveBase - leave item on anvil after craft
     * @param ingredients - items needed to craft
     */
    void addRecipe(ItemStack output, ItemStack base, boolean preserveBase, Object ... ingredients);

    /**
     * @param baseItem
     * @param ingredients
     * @return returns first matching recipe or null
     */
    @Nullable IAnvilRecipe getRecipe(ItemStack baseItem, @Nonnull List<ItemStack> ingredients);

    boolean isValidBaseItem(ItemStack itemStack);

    List<IAnvilRecipe> getRecipes();

    IAnvilRecipe getRecipe(ItemStack output);
}

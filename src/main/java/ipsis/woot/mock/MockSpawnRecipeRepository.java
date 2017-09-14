package ipsis.woot.mock;

import ipsis.woot.farming.ISpawnRecipeRepository;
import ipsis.woot.farming.SpawnRecipe;
import ipsis.woot.util.WootMobName;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class MockSpawnRecipeRepository implements ISpawnRecipeRepository {


    @Nullable
    @Override
    public SpawnRecipe get(WootMobName wootMobName) {

        SpawnRecipe mockRecipe = new SpawnRecipe();
        mockRecipe.addIngredient(new ItemStack(Items.REDSTONE, 4));
        mockRecipe.addIngredient(new ItemStack(Items.COAL, 1));

        return mockRecipe;
    }

    @Override
    public void add(WootMobName wootMobName, SpawnRecipe recipe) {

    }
}

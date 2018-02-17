package ipsis.woot.farming;

import ipsis.woot.oss.LogHelper;
import ipsis.woot.util.WootMobName;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class SpawnRecipeRepository implements ISpawnRecipeRepository {

    HashMap<WootMobName, SpawnRecipe> recipes = new HashMap<>();

    private SpawnRecipe defaultSpawnRecipe = new SpawnRecipe();

    public SpawnRecipeRepository() {

        defaultSpawnRecipe.setEfficiency(true);
    }

    @Nullable
    @Override
    public SpawnRecipe get(WootMobName wootMobName) {

        if (recipes.containsKey(wootMobName))
            return recipes.get(wootMobName);

        return defaultSpawnRecipe;
    }

    @Override
    public void add(WootMobName wootMobName, SpawnRecipe recipe) {

        if (recipe.getItems().size() > 6) {
            LogHelper.error("Too many spawn recipe item ingredients max of 6 " + wootMobName.toString());
            return;
        }

        if (recipe.getFluids().size() > 6) {
            LogHelper.error("Too many spawn recipe fluid ingredients max of 6 " + wootMobName.toString());
            return;
        }

        LogHelper.info("Add spawn recipe ingredients for " + wootMobName + "->" + recipe);

        recipes.put(wootMobName, recipe);
    }

    @Override
    public Set<WootMobName> getAllMobs() {

        return recipes.keySet();
    }

    @Override
    public void addDefaultItem(ItemStack itemStack) {

        defaultSpawnRecipe.addIngredient(itemStack);
        LogHelper.info("Add default spawn recipe ingredient " + itemStack.getDisplayName() + "x" + itemStack.getCount());
    }

    @Override
    public void addDefaultFluid(FluidStack fluidStack) {

        defaultSpawnRecipe.addIngredient(fluidStack);
        LogHelper.info("Add default spawn recipe fluid " + fluidStack.getLocalizedName() + "x" + fluidStack.amount + "mb");
    }

    @Override
    public void setDefaultEfficiency(boolean efficiency) {

        defaultSpawnRecipe.setEfficiency(efficiency);
        LogHelper.info("Setting default spawn recipe efficiency support " + efficiency);

    }
}

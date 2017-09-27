package ipsis.woot.farming;

import ipsis.woot.oss.LogHelper;
import ipsis.woot.util.WootMobName;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class SpawnRecipeRepository implements ISpawnRecipeRepository {

    HashMap<WootMobName, SpawnRecipe> recipes = new HashMap<>();

    @Nullable
    @Override
    public SpawnRecipe get(WootMobName wootMobName) {

        return recipes.get(wootMobName);
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

        recipes.put(wootMobName, recipe);
    }

    @Override
    public Set<WootMobName> getAllMobs() {

        return recipes.keySet();
    }
}

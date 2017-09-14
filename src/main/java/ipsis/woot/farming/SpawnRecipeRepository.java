package ipsis.woot.farming;

import ipsis.woot.util.WootMobName;

import javax.annotation.Nullable;
import java.util.HashMap;

public class SpawnRecipeRepository implements ISpawnRecipeRepository {

    HashMap<WootMobName, SpawnRecipe> recipes = new HashMap<>();

    @Nullable
    @Override
    public SpawnRecipe get(WootMobName wootMobName) {

        return recipes.get(wootMobName);
    }

    @Override
    public void add(WootMobName wootMobName, SpawnRecipe recipe) {

        recipes.put(wootMobName, recipe);

    }
}

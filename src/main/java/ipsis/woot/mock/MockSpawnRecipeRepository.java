package ipsis.woot.mock;

import ipsis.woot.farming.ISpawnRecipeRepository;
import ipsis.woot.farming.SpawnRecipe;
import ipsis.woot.util.WootMobName;

import javax.annotation.Nullable;

public class MockSpawnRecipeRepository implements ISpawnRecipeRepository {


    @Nullable
    @Override
    public SpawnRecipe get(WootMobName wootMobName) {

        return null;
    }
}

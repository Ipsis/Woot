package ipsis.woot.tileentity.ng.mock;

import ipsis.woot.tileentity.ng.ISpawnRecipeRepository;
import ipsis.woot.tileentity.ng.SpawnRecipe;
import ipsis.woot.tileentity.ng.WootMobName;

import javax.annotation.Nullable;

public class MockSpawnRecipeRepository implements ISpawnRecipeRepository {


    @Nullable
    @Override
    public SpawnRecipe get(WootMobName wootMobName) {

        return null;
    }
}

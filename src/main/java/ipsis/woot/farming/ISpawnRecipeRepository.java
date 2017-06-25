package ipsis.woot.farming;

import ipsis.woot.util.WootMobName;

import javax.annotation.Nullable;

public interface ISpawnRecipeRepository {

    @Nullable SpawnRecipe get(WootMobName wootMobName);
}

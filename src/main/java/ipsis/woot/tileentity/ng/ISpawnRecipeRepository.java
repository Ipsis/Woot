package ipsis.woot.tileentity.ng;

import javax.annotation.Nullable;

public interface ISpawnRecipeRepository {

    @Nullable SpawnRecipe get(WootMobName wootMobName);
}

package ipsis.woot.tileentity.ng;

import javax.annotation.Nullable;

public interface ISpawnRecipeConsumer {

    boolean consume(@Nullable SpawnRecipe spawnRecipe, int mobCount);
}

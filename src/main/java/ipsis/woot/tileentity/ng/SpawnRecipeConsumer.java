package ipsis.woot.tileentity.ng;

import javax.annotation.Nullable;

public class SpawnRecipeConsumer implements ISpawnRecipeConsumer {

    @Override
    public boolean consume(@Nullable SpawnRecipe spawnRecipe, int mobCount) {

        if (spawnRecipe == null)
            return true;

        return true;
    }
}

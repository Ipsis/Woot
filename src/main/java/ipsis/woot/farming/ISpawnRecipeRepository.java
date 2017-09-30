package ipsis.woot.farming;

import ipsis.woot.util.WootMobName;

import javax.annotation.Nullable;
import java.util.Set;

public interface ISpawnRecipeRepository {

    @Nullable SpawnRecipe get(WootMobName wootMobName);
    void add(WootMobName wootMobName, SpawnRecipe recipe);
    Set<WootMobName> getAllMobs();
}

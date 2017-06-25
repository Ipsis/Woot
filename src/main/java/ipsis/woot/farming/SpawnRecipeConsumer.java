package ipsis.woot.farming;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class SpawnRecipeConsumer implements ISpawnRecipeConsumer {

    public boolean consume(World world, BlockPos pos, @Nullable ISpawnRecipe spawnRecipe, int mobCount) {

        if (spawnRecipe == null)
            return true;

        return true;
    }
}

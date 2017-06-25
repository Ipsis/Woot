package ipsis.woot.farming;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public interface ISpawnRecipeConsumer {

    boolean consume(World world, BlockPos pos, @Nullable ISpawnRecipe spawnRecipe, int mobCount);
}

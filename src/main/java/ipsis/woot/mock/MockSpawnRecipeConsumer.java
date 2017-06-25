package ipsis.woot.mock;

import ipsis.woot.farming.ISpawnRecipe;
import ipsis.woot.farming.ISpawnRecipeConsumer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MockSpawnRecipeConsumer implements ISpawnRecipeConsumer {

    @Override
    public boolean consume(World world, BlockPos pos, @Nullable ISpawnRecipe spawnRecipe, int mobCount) {
        return true;
    }
}

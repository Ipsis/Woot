package ipsis.woot.mock;

import ipsis.woot.farming.ISpawnRecipe;
import ipsis.woot.farming.ISpawnRecipeConsumer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.List;

public class MockSpawnRecipeConsumer implements ISpawnRecipeConsumer {

    @Override
    public boolean consume(World world, BlockPos pos, List<IFluidHandler> fluidHandlerList, List<IItemHandler> itemHandlerList, @Nullable ISpawnRecipe spawnRecipe, int mobCount) {
        return true;
    }
}

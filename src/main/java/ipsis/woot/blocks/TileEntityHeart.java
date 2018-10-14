package ipsis.woot.blocks;

import ipsis.woot.factory.*;
import ipsis.woot.factory.structure.FactoryConfig;
import ipsis.woot.factory.structure.FactoryLayout;
import ipsis.woot.factory.structure.locator.IMultiBlockMaster;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityHeart extends TileEntity implements ITickable, IMultiBlockMaster {

    private SimpleTickTracker tickTracker;
    private FactoryLayout factoryLayout;
    private FactoryConfig factoryConfig;
    private RFRecipeProgressTracker recipeProgressTracker;
    private SpawnRecipeConsumer spawnRecipeConsumer;

    public TileEntityHeart() {

        // NOTE: this is called without a world on startup
        tickTracker = new SimpleTickTracker();
        tickTracker.setStructureTickCount(20);
        recipeProgressTracker = new RFRecipeProgressTracker();
    }

    @Override
    public void update() {

        // Only the server ticks
        if (world.isRemote)
            return;

        // Cannot set this on create as the world may not be set
        if (factoryLayout == null) {
            factoryLayout = new FactoryLayout();
            factoryLayout.setWorldPos(getWorld(), getPos());
            factoryLayout.setDirtyLayout();
        }

        // Watch for fake, accelerated ticks
        if (!tickTracker.tick(getWorld()))
            return;

        factoryLayout.tick(tickTracker);

        if (factoryLayout.isFormed()) {

            if (factoryLayout.hasChanged()) {
                // TODO reset the factory and handle the changed configuration
                factoryLayout.clearChanged();
            }

            // Redstone signal STOPS the machine
            if (world.isBlockPowered(getPos())) {
                recipeProgressTracker.tick(tickTracker);
                if (recipeProgressTracker.isComplete()) {
                    if (spawnRecipeConsumer.consume()) {
                    }

                    recipeProgressTracker.reset();
                }
            }
        }
    }

    /**
     * IMultiBlockMaster
     */
    public void interrupt() {

    }
}

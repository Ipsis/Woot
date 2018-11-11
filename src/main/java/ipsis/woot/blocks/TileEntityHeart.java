package ipsis.woot.blocks;

import ipsis.Woot;
import ipsis.woot.drops.generation.LootGenerator;
import ipsis.woot.factory.*;
import ipsis.woot.factory.progress.*;
import ipsis.woot.factory.structure.FactoryConfig;
import ipsis.woot.factory.structure.FactoryConfigBuilder;
import ipsis.woot.factory.structure.FactoryLayout;
import ipsis.woot.factory.structure.locator.IMultiBlockMaster;
import ipsis.woot.util.IDebug;
import ipsis.woot.util.helpers.ConnectedCapHelper;
import ipsis.woot.util.helpers.WorldHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

import java.util.List;

public class TileEntityHeart extends TileEntity implements ITickable, IMultiBlockMaster, IDebug {

    private SimpleTickTracker tickTracker;
    private FactoryLayout factoryLayout; // The factory blocks and where they are
    private FactoryConfig factoryConfig; // The configuration of the factory
    private SpawnRecipeConsumer spawnRecipeConsumer;
    private TrackingState trackingState = new TrackingState();
    private RFRecipeProgressTracker recipeProgressTracker;

    public TileEntityHeart() {

        // NOTE: this is called without a world on startup
        tickTracker = new SimpleTickTracker();
        tickTracker.setStructureTickCount(20);
        spawnRecipeConsumer = new SpawnRecipeConsumer();
    }

    private boolean isRunning() {
        return getWorld() != null && !getWorld().isBlockPowered(getPos());
    }

    @Override
    public void update() {

        // Only the server ticks
        if (WorldHelper.isClientWorld(world))
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
                factoryConfig = FactoryConfigBuilder.create(factoryLayout);

                RecipeManager.FactoryRecipe factoryRecipe = Woot.RECIPE_MANAGER.getFactoryRecipe(factoryConfig.getFakeMobKey(), factoryConfig.getLooting());
                IProgessRecipe iProgessRecipe = new PowerRecipe();
                iProgessRecipe.setRecipe(120, 120);
                recipeProgressTracker = new RFRecipeProgressTracker(iProgessRecipe, new MockRecipeUnitProvider());
                factoryLayout.clearChanged();
            }

            // Redstone signal STOPS the machine
            if (isRunning()) {
                recipeProgressTracker.tick(tickTracker);
                if (recipeProgressTracker.isComplete()) {
                    if (spawnRecipeConsumer.consume()) {

                        LootGenerator.Setup setup = Woot.LOOT_GENERATOR.getNewSetup(
                                factoryConfig.getFakeMobKey(), factoryConfig.getLooting(),
                                1, getWorld().getDifficultyForLocation(getPos()));
                        setup.itemHandlers.addAll(ConnectedCapHelper.getConnectedItemHandlers(getWorld(), factoryConfig.getExportPos()));
                        setup.fuildHandlers.addAll(ConnectedCapHelper.getConnectedFillableFluidHandlers(getWorld(), factoryConfig.getExportPos()));
                        Woot.LOOT_GENERATOR.generate(getWorld(), setup);
                    }
                    recipeProgressTracker.reset();
                }
            }
        }
    }

    /**
     * This is for misc state for things like BloodMagic, how much xp we have unused etc
     */
    private class TrackingState {
        private int storedXp = 0;
        public void setStoredXp(int v) { this.storedXp = v; }
    }

    /**
     * IMultiBlockMaster
     */
    public void interrupt() {
        if (factoryLayout != null)
            factoryLayout.setDirtyLayout();
    }

    /**
     * IDebug
     */
    @Override
    public void getDebugText(List<String> debug) {

        if (factoryLayout != null && factoryLayout.isFormed()) {
            debug.add("Formed:" + factoryLayout.isFormed());
            debug.add(String.format("Config: %s looting %d", factoryConfig.getFakeMobKey(), factoryConfig.getLooting()));
            debug.add(String.format("Exporter:%s Importer:%s Power:%s", factoryConfig.getExportPos(), factoryConfig.getImportPos(), factoryConfig.getPowerCellPos()));
            debug.add("Tracking xp:" + trackingState.storedXp);
        } else {
            debug.add("Unformed");
        }
    }
}

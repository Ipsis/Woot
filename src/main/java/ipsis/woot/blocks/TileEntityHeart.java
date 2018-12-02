package ipsis.woot.blocks;

import ipsis.Woot;
import ipsis.woot.costing.FactoryRecipeManager;
import ipsis.woot.drops.generation.LootGenerator;
import ipsis.woot.factory.*;
import ipsis.woot.factory.recipes.FactoryRecipe;
import ipsis.woot.factory.recipes.IWootUnitProvider;
import ipsis.woot.factory.structure.FactoryConfig;
import ipsis.woot.factory.structure.FactoryConfigBuilder;
import ipsis.woot.factory.structure.FactoryLayout;
import ipsis.woot.factory.structure.locator.IMultiBlockMaster;
import ipsis.woot.school.SchoolManager;
import ipsis.woot.util.IDebug;
import ipsis.woot.util.helpers.ConnectedCapHelper;
import ipsis.woot.util.helpers.LogHelper;
import ipsis.woot.util.helpers.WorldHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public class TileEntityHeart extends TileEntity implements ITickable, IMultiBlockMaster, IDebug {

    private SimpleTickTracker tickTracker;
    private FactoryLayout factoryLayout; // The factory blocks and where they are
    private FactoryConfig factoryConfig; // The configuration of the factory
    private SpawnRecipeConsumer spawnRecipeConsumer;
    private TrackingState trackingState = new TrackingState();
    private int consumedWootUnits = 0;
    private FactoryRecipe factoryRecipe = new FactoryRecipe(1, 1);

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
                factoryRecipe = FactoryRecipeManager.INSTANCE.createFactoryRecipe(factoryConfig, getWorld());
                SchoolManager.teachMob(factoryConfig.getFakeMobKey(), factoryConfig.getLooting());
                factoryLayout.clearChanged();
                LogHelper.info(factoryRecipe);
            }

            // Redstone signal STOPS the machine
            if (isRunning()) {
                tickRecipe();
                if (isRecipeComplete()) {
                    LogHelper.info("Generate ze loots");
                    if (spawnRecipeConsumer.consume()) {
                        LootGenerator.Setup setup = Woot.LOOT_GENERATOR.getNewSetup(
                                factoryConfig.getFakeMobKey(), factoryConfig.getLooting(),
                                1, getWorld().getDifficultyForLocation(getPos()));
                        setup.itemHandlers.addAll(ConnectedCapHelper.getConnectedItemHandlers(getWorld(), factoryConfig.getExportPos()));
                        setup.fuildHandlers.addAll(ConnectedCapHelper.getConnectedFillableFluidHandlers(getWorld(), factoryConfig.getExportPos()));
                        Woot.LOOT_GENERATOR.generate(getWorld(), setup);
                    }

                    resetRecipe();
                }
            }
        }
    }

    /**
     * Progress
     */
    private boolean isRecipeComplete() {
        return consumedWootUnits >= factoryRecipe.getNumUnits();
    }

    private void tickRecipe() {

        if (factoryLayout.isFormed()) {

            TileEntity generatorTE = getWorld().getTileEntity(factoryConfig.getGeneratorPos());
            if (generatorTE instanceof IWootUnitProvider) {
                IWootUnitProvider iWootUnitProvider = (IWootUnitProvider)generatorTE;
                consumedWootUnits += iWootUnitProvider.consume(getRecipeWootUnitsPerTick());
            }
        }
    }

    private void resetRecipe() {
        consumedWootUnits = 0;
    }

    private int getRecipeWootUnitsPerTick() {
        return MathHelper.clamp(1, factoryRecipe.getNumUnits() /  factoryRecipe.getNumTicks(), Integer.MAX_VALUE);
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
            debug.add(String.format("Exporter:%s Importer:%s Generator:%s", factoryConfig.getExportPos(), factoryConfig.getImportPos(), factoryConfig.getGeneratorPos()));
            debug.add("Tracking xp:" + trackingState.storedXp);
            debug.add("Cost " + factoryRecipe);
        } else {
            debug.add("Unformed");
        }
    }
}

package ipsis.woot.factory.heart;

import ipsis.woot.Woot;
import ipsis.woot.debug.IWootDebug;
import ipsis.woot.factory.multiblock.FactoryConfig;
import ipsis.woot.factory.multiblock.FactoryLayout;
import ipsis.woot.factory.multiblock.IMultiBlockMaster;
import ipsis.woot.mod.ModTileEntities;
import ipsis.woot.util.helper.WorldHelper;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

import java.util.List;

public class TileEntityHeart extends TileEntity implements IWootDebug, IMultiBlockMaster, ITickable {

    /**
     * The factoryLayout will not exist until after the first update call
     * The factoryConfig will only exist if factoryLayout.isFormed
     * The factoryRecipe will always exist but only be valid is factoryLayout.isFormed
     */
    private FactoryLayout factoryLayout; // The factory blocks and where they are
    private FactoryConfig factoryConfig; // The configuration of the factory

    private TickTracker tickTracker = new TickTracker();

    public TileEntityHeart() {
        super(ModTileEntities.heartTileEntityType);
        tickTracker.setStructureTickCount(20);
    }

    private boolean isRunning() {
        return world != null && !world.isBlockPowered(pos);
    }

    /**
     * ITickable
     */
    @Override
    public void tick() {
        if (WorldHelper.isClientWorld(world))
            return;

        if (factoryLayout == null) {
            factoryLayout = new FactoryLayout();
            factoryLayout.setWorldPos(world, pos);
            factoryLayout.setDirty();
        }

        if (!tickTracker.tick(world))
            return;

        factoryLayout.tick(tickTracker);

        if (factoryLayout.isFormed()) {
            if (factoryLayout.hasChanged()) {
                factoryConfig = FactoryConfig.createFromLayout(world, factoryLayout);
                factoryLayout.clearChanged();
                Woot.LOGGER.info("Created factory for " + factoryConfig);
            }

            if (isRunning()) {

            }
        }

    }

    /**
     * IMultiBlockMaster
     */
    @Override
    public void interrupt() {
        Woot.LOGGER.info("TileEntityHeart: interrupt");
        if (factoryLayout != null)
            factoryLayout.setDirty();
    }

    /**
     * IWootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("=====> TileEntityHeart");
        return debug;
    }
}

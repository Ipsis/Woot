package ipsis.woot.modules.factory.generators;

import ipsis.woot.config.Config;
import ipsis.woot.config.ConfigOverride;
import ipsis.woot.modules.factory.Setup;
import ipsis.woot.modules.factory.blocks.HeartTileEntity;
import ipsis.woot.simulator.MobSimulator;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.FakeMobKey;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LootGeneration {

    static final Logger LOGGER = LogManager.getLogger();

    public static LootGeneration get() { return INSTANCE; }
    static LootGeneration INSTANCE;
    static { INSTANCE = new LootGeneration(); }

    public void generate(HeartTileEntity heartTileEntity, Setup setup) {

        /**
         * Get the output options
         */
        List<LazyOptional<IItemHandler>> itemHandlers = new ArrayList<>();
        for (Direction facing : Direction.values()) {
            if (!heartTileEntity.getWorld().isBlockLoaded(setup.getExportPos().offset(facing)))
                continue;

            TileEntity te = heartTileEntity.getWorld().getTileEntity(setup.getExportPos().offset(facing));
            if (!(te instanceof TileEntity))
                continue;

            itemHandlers.add(te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite()));
        }

        int looting = setup.getLooting();
        for (FakeMob mob : setup.getMobs()) {
            int mobCount = Config.OVERRIDE.getInteger(mob, ConfigOverride.OverrideKey.MASS_COUNT);
            LOGGER.debug("generate: {} * {}", mob, mobCount);
            for (int i = 0; i < mobCount; i++) {
                List<ItemStack> rolledDrops = MobSimulator.getInstance().getRolledDrops(new FakeMobKey(mob, looting));
                Iterator<LazyOptional<IItemHandler>> iter = itemHandlers.iterator();
                while (iter.hasNext()) {
                    LazyOptional<IItemHandler> hdlr = iter.next();
                    hdlr.ifPresent(h -> {
                        LOGGER.debug("generate: try drop into {} ", h);
                        for (ItemStack itemStack : rolledDrops) {
                            if (itemStack.isEmpty())
                                continue;

                            ItemStack result = ItemHandlerHelper.insertItem(h, itemStack.copy(), false);
                            if (result.isEmpty())
                                itemStack.setCount(0);
                            else
                                itemStack.shrink(itemStack.getCount() - result.getCount());
                        }
                    });
                }
            }
        }



    }
}

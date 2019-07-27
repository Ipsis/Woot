package ipsis.woot.factory.generators;

import ipsis.woot.Woot;
import ipsis.woot.common.WootConfig;
import ipsis.woot.factory.FactoryUpgrade;
import ipsis.woot.factory.FactoryUpgradeType;
import ipsis.woot.factory.Setup;
import ipsis.woot.factory.blocks.HeartTileEntity;
import ipsis.woot.loot.DropRegistry;
import ipsis.woot.loot.MobDrop;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.helper.MobDropHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LootGeneration {

    static final Logger LOGGER = LogManager.getLogger();
    private static final Marker LOOTGEN = MarkerManager.getMarker("WOOT_LOOTGEN");

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

            TileEntity te = heartTileEntity.getWorld().getTileEntity(heartTileEntity.getPos().offset(facing));
            if (!(te instanceof TileEntity))
                continue;

            itemHandlers.add(te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite()));
        }

        for (FakeMob mob : setup.getMobs()) {
            int mobCount = WootConfig.get().getIntValue(mob, WootConfig.Key.MASS);
            if (setup.getUpgrades().containsKey(FactoryUpgradeType.MASS)) {
                int level = setup.getUpgrades().get(FactoryUpgradeType.MASS);
                mobCount = WootConfig.get().getIntValue(mob, FactoryUpgradeType.MASS, level);
            }

            LOGGER.info(LOOTGEN, "generate: {} * {}", mob, mobCount);
            List<MobDrop> mobDrops = DropRegistry.get().getMobDrops(new FakeMobKey(mob, 0));
            for (int i = 0; i < mobCount; i++) {
                List<ItemStack> drops = MobDropHelper.getDrops(mobDrops);
                Iterator<LazyOptional<IItemHandler>> iter = itemHandlers.iterator();
                while (iter.hasNext()) {
                    LazyOptional<IItemHandler> hdlr = iter.next();
                    hdlr.ifPresent(h -> {
                        LOGGER.info(LOOTGEN, "generate: try drop into {} ", h);
                        for (ItemStack itemStack : drops) {
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

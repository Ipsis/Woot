package ipsis.woot.mod;

import ipsis.woot.Woot;
import ipsis.woot.debug.ItemDebug;
import ipsis.woot.layout.BlockLayout;
import ipsis.woot.layout.TileEntityLayout;
import ipsis.woot.tools.ItemHammer;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class Registration {

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        Woot.LOGGER.info("Register blocks");

        event.getRegistry().registerAll(
                new BlockLayout()
        );
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        Woot.LOGGER.info("Register items");

        event.getRegistry().register(new ItemDebug());
        event.getRegistry().register(new ItemHammer());
        event.getRegistry().register(new ItemBlock(ModBlocks.layoutBlock, new Item.Properties().group(Woot.TAB_WOOT)).setRegistryName(ModBlocks.layoutBlock.getRegistryName()));
    }

    @SubscribeEvent
    public static void registerTileEntities(RegistryEvent.Register<TileEntityType<?>> event) {
        Woot.LOGGER.info("Register tile entities");

        event.getRegistry().register(TileEntityType.Builder.create(TileEntityLayout::new).build(null).setRegistryName(Woot.MODID, BlockLayout.BASENAME));
    }

}

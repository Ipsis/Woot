package ipsis.woot.event;

import ipsis.woot.init.ModBlocks;
import ipsis.woot.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerRegistryEvent {

    @SubscribeEvent
    public void onRegisterBlocks(RegistryEvent.Register<Block> event) {

        event.getRegistry().registerAll(
                ModBlocks.blockLayout,
                ModBlocks.blockFactory,
                ModBlocks.blockController,
                ModBlocks.blockExtender,
                ModBlocks.blockProxy,
                ModBlocks.blockStructure,
                ModBlocks.blockUpgrade,
                ModBlocks.blockUpgradeB);
    }

    @SubscribeEvent
    public void onRegisterItems(RegistryEvent.Register<Item> event) {

        event.getRegistry().registerAll(
                ModItems.itemPrism2,
                ModItems.itemXpShard,
                ModItems.itemSkull,
                ModItems.itemShard,
                ModItems.itemDye,
                ModItems.itemFactoryFrame,
                ModItems.itemFactoryUpgrade,
                ModItems.itemFerrocrete,
                ModItems.itemFerrocretePlate,
                ModItems.itemFactoryCasing,
                ModItems.itemPrismFrame,
                ModItems.itemYahHammer,
                ModItems.itemPulverisedFerrocrete,
                ModItems.itemFactoryCap,
                ModItems.itemFactoryConnector,
                ModItems.itemManual
        );
    }
}

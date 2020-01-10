package ipsis.woot.setup;

import ipsis.woot.Woot;
import ipsis.woot.crafting.*;
import ipsis.woot.modules.factory.blocks.TickConverterBlock;
import ipsis.woot.modules.factory.blocks.TickConverterTileEntity;
import ipsis.woot.mod.ModBlocks;
import ipsis.woot.modules.simulation.SimulationSetup;
import ipsis.woot.modules.factory.items.MobShardItem;
import ipsis.woot.modules.simulation.dimension.TartarusModDimension;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class Registration {

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        Woot.LOGGER.info("registerBlocks");
        event.getRegistry().register(new TickConverterBlock());
    }

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        Woot.LOGGER.info("registerItems");

        Item.Properties properties = new Item.Properties().group(Woot.itemGroup);

        event.getRegistry().register(new MobShardItem());
        event.getRegistry().register(new BlockItem(ModBlocks.TICK_CONVERTER_BLOCK, properties).setRegistryName(Woot.MODID, TickConverterBlock.REGNAME));


    }

    @SubscribeEvent
    public static void registerTileEntities(final RegistryEvent.Register<TileEntityType<?>> event) {
        Woot.LOGGER.info("registerTileEntities");
        event.getRegistry().register(TileEntityType.Builder.create(TickConverterTileEntity::new, ModBlocks.TICK_CONVERTER_BLOCK).build(null).setRegistryName(Woot.MODID, TickConverterBlock.REGNAME));
    }

    @SubscribeEvent
    public static void registerEnchantments(final RegistryEvent.Register<Enchantment> event) {
        Woot.LOGGER.info("registerEnchantments");
    }

    @SubscribeEvent
    public static void registerDimensions(final RegistryEvent.Register<ModDimension> event) {
        Woot.LOGGER.info("registerDimensions");
        event.getRegistry().register(new TartarusModDimension().setRegistryName(SimulationSetup.TARTARUS_DIMENSION_ID));
    }

    @SubscribeEvent
    public static void registerRecipeSerializer(final RegistryEvent.Register<IRecipeSerializer<?>> event) {
        Woot.LOGGER.info("registerRecipeSerializer");

        event.getRegistry().register(new DyeSqueezerRecipeSerializer<>(DyeSqueezerRecipe::new).setRegistryName(Woot.MODID, "dyesqueezer"));
        event.getRegistry().register(new AnvilRecipeSerializer<>(AnvilRecipe::new).setRegistryName(Woot.MODID, "anvil"));
        event.getRegistry().register(new InfuserRecipeSerializer<>(InfuserRecipe::new).setRegistryName(Woot.MODID, "infuser"));

    }
}

package ipsis.woot.mod;

import ipsis.woot.Woot;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class Registration {

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        Woot.LOGGER.info("registerBlocks");
    }

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        Woot.LOGGER.info("registerItems");
    }

    @SubscribeEvent
    public static void registerTileEntities(final RegistryEvent.Register<TileEntityType<?>> event) {
        Woot.LOGGER.info("registerTileEntities");
    }

    @SubscribeEvent
    public static void registerEnchantments(final RegistryEvent.Register<Enchantment> event) {
        Woot.LOGGER.info("registerEnchantments");
    }

    @SubscribeEvent
    public static void registerModDimensions(final RegistryEvent.Register<ModDimension> event) {
        Woot.LOGGER.info("registerModDimensions");
        event.getRegistry().register(ModDimensions.tartarusModDimension.setRegistryName(Woot.MODID, "tartarus_dim"));
        ModDimensions.tartarusDimensionType = DimensionManager.registerDimension(
                new ResourceLocation(Woot.MODID, "tartarus_simulator"), ModDimensions.tartarusModDimension, null, true);
        DimensionManager.keepLoaded(ModDimensions.tartarusDimensionType, true);
    }

    @SubscribeEvent
    public static void registerDimensions(RegisterDimensionsEvent registerDimensionsEvent) {
        Woot.LOGGER.info("registerDimensions");
    }

    @SubscribeEvent
    public static void registerChunkGeneratorTypes(final RegistryEvent.Register<ChunkGeneratorType<?, ?>> event) {
        Woot.LOGGER.info("registerChunkGeneratorTypes");
        event.getRegistry().register(ModDimensions.tartarusChunkGeneratorType.setRegistryName(Woot.MODID, "tartarus_gen"));
    }
}

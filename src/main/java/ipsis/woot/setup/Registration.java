package ipsis.woot.setup;

import ipsis.woot.Woot;
import ipsis.woot.crafting.*;
import ipsis.woot.loot.ExoticDropsLootModifier;
import ipsis.woot.simulator.MobSimulatorSetup;
import ipsis.woot.simulator.tartarus.TartarusModDimension;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class Registration {

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        Woot.setup.getLogger().info("registerBlocks");
    }

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        Woot.setup.getLogger().info("registerItems");
        Item.Properties properties = new Item.Properties().group(Woot.setup.getCreativeTab());
    }

    @SubscribeEvent
    public static void registerTileEntities(final RegistryEvent.Register<TileEntityType<?>> event) {
        Woot.setup.getLogger().info("registerTileEntities");
    }

    @SubscribeEvent
    public static void registerEnchantments(final RegistryEvent.Register<Enchantment> event) {
        Woot.setup.getLogger().info("registerEnchantments");
    }

    @SubscribeEvent
    public static void registerDimensions(final RegistryEvent.Register<ModDimension> event) {
        Woot.setup.getLogger().info("registerDimensions");
        event.getRegistry().register(new TartarusModDimension().setRegistryName(MobSimulatorSetup.TARTARUS_DIMENSION_ID));
    }

    @SubscribeEvent
    public static void registerRecipeSerializer(final RegistryEvent.Register<IRecipeSerializer<?>> event) {
        Woot.setup.getLogger().info("registerRecipeSerializer");

        event.getRegistry().register(new DyeSqueezerRecipeSerializer<>(DyeSqueezerRecipe::new).setRegistryName(Woot.MODID, "dyesqueezer"));
        event.getRegistry().register(new AnvilRecipeSerializer<>(AnvilRecipe::new).setRegistryName(Woot.MODID, "anvil"));
        event.getRegistry().register(new InfuserRecipeSerializer<>(InfuserRecipe::new).setRegistryName(Woot.MODID, "infuser"));
        event.getRegistry().register(new FluidConvertorRecipeSerializer<>(FluidConvertorRecipe::new).setRegistryName(Woot.MODID, "fluidconvertor"));
        event.getRegistry().register(new FactoryRecipeSerializer<>(FactoryRecipe::new).setRegistryName(Woot.MODID, "factory"));
    }

    @SubscribeEvent
    public static void registerLootModifierSerializer(final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
        Woot.setup.getLogger().info("registerLootModifierSerializer");

        event.getRegistry().register(new ExoticDropsLootModifier.Serializer().setRegistryName(Woot.MODID, "exotic_drops"));
    }
}

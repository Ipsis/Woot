package ipsis.woot.mod;

import ipsis.woot.Woot;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModFluids {

    public static final ResourceLocation CONATUS_STILL = new ResourceLocation("woot:block/conatus_still");
    public static final ResourceLocation CONATUS_FLOWING = new ResourceLocation("woot:block/conatus_flow");

    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, Woot.MODID);
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, Woot.MODID);
    public static final DeferredRegister<Fluid> FLUIDS = new DeferredRegister<>(ForgeRegistries.FLUIDS, Woot.MODID);

    public static RegistryObject<FlowingFluid> CONATUS_FLUID =
            FLUIDS.register("conatus_fluid",
                    () -> new ForgeFlowingFluid.Source(ModFluids.CONATUS_FLUID_PROPERTIES));

    public static RegistryObject<FlowingFluid> CONATUS_FLUID_FLOWING =
            FLUIDS.register("conatus_fluid_flowing",
                    () -> new ForgeFlowingFluid.Flowing(ModFluids.CONATUS_FLUID_PROPERTIES));

    public static RegistryObject<FlowingFluidBlock> CONATUS_FLUID_BLOCK =
            BLOCKS.register("conatus_fluid_block",
                    () -> new FlowingFluidBlock(CONATUS_FLUID,
                            Block.Properties.create(Material.WATER)
                                    .doesNotBlockMovement()
                                    .hardnessAndResistance(100.0F)
                                    .noDrops()));

    public static RegistryObject<Item> CONATUS_FLUID_BUCKET =
            ITEMS.register("conatus_fluid_bucket",
                    () -> new BucketItem(CONATUS_FLUID,
                            new Item.Properties()
                                    .containerItem(Items.BUCKET)
                                    .maxStackSize(1)
                                    .group(Woot.itemGroup)));

    public static final ForgeFlowingFluid.Properties CONATUS_FLUID_PROPERTIES =
            new ForgeFlowingFluid.Properties(
                    CONATUS_FLUID,
                    CONATUS_FLUID_FLOWING,
                    FluidAttributes.builder(CONATUS_STILL, CONATUS_FLOWING))
                    .bucket(CONATUS_FLUID_BUCKET)
                    .block(CONATUS_FLUID_BLOCK);

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        FLUIDS.register(modEventBus);
    }
}

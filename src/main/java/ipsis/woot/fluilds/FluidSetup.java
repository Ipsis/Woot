package ipsis.woot.fluilds;

import ipsis.woot.Woot;
import ipsis.woot.setup.ModSetup;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidSetup {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Woot.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Woot.MODID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, Woot.MODID);

    /**
     * Conatus
     */
    public static final ResourceLocation CONATUS_STILL = new ResourceLocation("woot:block/conatus_still");
    public static final ResourceLocation CONATUS_FLOWING = new ResourceLocation("woot:block/conatus_flow");

    public static RegistryObject<FlowingFluid> CONATUS_FLUID = FLUIDS.register("conatus_fluid", () -> new ForgeFlowingFluid.Source(FluidSetup.CONATUS_FLUID_PROPERTIES));
    public static RegistryObject<FlowingFluid> CONATUS_FLUID_FLOWING = FLUIDS.register("conatus_fluid_flowing", () -> new ForgeFlowingFluid.Flowing(FluidSetup.CONATUS_FLUID_PROPERTIES));
    public static RegistryObject<FlowingFluidBlock> CONATUS_FLUID_BLOCK = BLOCKS.register("conatus_fluid_block",
            () -> new FlowingFluidBlock(CONATUS_FLUID,
                    Block.Properties.create(Material.WATER)
                            .doesNotBlockMovement()
                            .hardnessAndResistance(100.0F)
                            .noDrops()));
    public static RegistryObject<Item> CONATUS_FLUID_BUCKET = ITEMS.register("conatus_fluid_bucket",
            () -> new BucketItem(CONATUS_FLUID,
                    new Item.Properties()
                            .containerItem(Items.BUCKET)
                            .maxStackSize(1)
                            .group(Woot.setup.getCreativeTab())));
    public static final ForgeFlowingFluid.Properties CONATUS_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            CONATUS_FLUID,
            CONATUS_FLUID_FLOWING,
            FluidAttributes.builder(CONATUS_STILL, CONATUS_FLOWING))
            .bucket(CONATUS_FLUID_BUCKET)
            .block(CONATUS_FLUID_BLOCK);

    /**
     * Pure Dye
     */
    public static final ResourceLocation PUREDYE_STILL = new ResourceLocation("woot:block/puredye_still");
    public static final ResourceLocation PUREDYE_FLOWING = new ResourceLocation("woot:block/puredye_flow");

    public static RegistryObject<FlowingFluid> PUREDYE_FLUID = FLUIDS.register("puredye_fluid", () -> new ForgeFlowingFluid.Source(FluidSetup.PUREDYE_FLUID_PROPERTIES));
    public static RegistryObject<FlowingFluid> PUREDYE_FLUID_FLOWING = FLUIDS.register("puredye_fluid_flowing", () -> new ForgeFlowingFluid.Flowing(FluidSetup.PUREDYE_FLUID_PROPERTIES));
    public static RegistryObject<FlowingFluidBlock> PUREDYE_FLUID_BLOCK = BLOCKS.register("puredye_fluid_block",
            () -> new FlowingFluidBlock(PUREDYE_FLUID,
                    Block.Properties.create(Material.WATER)
                            .doesNotBlockMovement()
                            .hardnessAndResistance(100.0F)
                            .noDrops()));
    public static RegistryObject<Item> PUREDYE_FLUID_BUCKET = ITEMS.register("puredye_fluid_bucket",
            () -> new BucketItem(PUREDYE_FLUID,
                    new Item.Properties()
                            .containerItem(Items.BUCKET)
                            .maxStackSize(1)
                            .group(Woot.setup.getCreativeTab())));
    public static final ForgeFlowingFluid.Properties PUREDYE_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            PUREDYE_FLUID,
            PUREDYE_FLUID_FLOWING,
            FluidAttributes.builder(PUREDYE_STILL, PUREDYE_FLOWING))
            .bucket(PUREDYE_FLUID_BUCKET)
            .block(PUREDYE_FLUID_BLOCK);

    /**
     * Liquid Enchantment
     */
    public static final ResourceLocation ENCHANT_STILL = new ResourceLocation("woot:block/enchant_still");
    public static final ResourceLocation ENCHANT_FLOWING = new ResourceLocation("woot:block/enchant_flow");

    public static RegistryObject<FlowingFluid> ENCHANT_FLUID = FLUIDS.register("enchant_fluid", () -> new ForgeFlowingFluid.Source(FluidSetup.ENCHANT_FLUID_PROPERTIES));
    public static RegistryObject<FlowingFluid> ENCHANT_FLUID_FLOWING = FLUIDS.register("enchant_fluid_flowing", () -> new ForgeFlowingFluid.Flowing(FluidSetup.ENCHANT_FLUID_PROPERTIES));
    public static RegistryObject<FlowingFluidBlock> ENCHANT_FLUID_BLOCK = BLOCKS.register("enchant_fluid_block",
            () -> new FlowingFluidBlock(ENCHANT_FLUID,
                    Block.Properties.create(Material.WATER)
                            .doesNotBlockMovement()
                            .hardnessAndResistance(100.0F)
                            .noDrops()));
    public static RegistryObject<Item> ENCHANT_FLUID_BUCKET = ITEMS.register("enchant_fluid_bucket",
            () -> new BucketItem(ENCHANT_FLUID,
                    new Item.Properties()
                            .containerItem(Items.BUCKET)
                            .maxStackSize(1)
                            .group(Woot.setup.getCreativeTab())));
    public static final ForgeFlowingFluid.Properties ENCHANT_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            ENCHANT_FLUID,
            ENCHANT_FLUID_FLOWING,
            FluidAttributes.builder(ENCHANT_STILL, ENCHANT_FLOWING))
            .bucket(ENCHANT_FLUID_BUCKET)
            .block(ENCHANT_FLUID_BLOCK);

    /**
     * Mob Essence
     */
    public static final ResourceLocation MOB_ESSENCE_STILL = new ResourceLocation("woot:block/mob_essence_still");
    public static final ResourceLocation MOB_ESSENCE_FLOWING = new ResourceLocation("woot:block/mob_essence_flow");

    public static RegistryObject<FlowingFluid> MOB_ESSENCE_FLUID = FLUIDS.register("mob_essence_fluid", () -> new ForgeFlowingFluid.Source(FluidSetup.MOB_ESSENCE_FLUID_PROPERTIES));
    public static RegistryObject<FlowingFluid> MOB_ESSENCE_FLUID_FLOWING = FLUIDS.register("mob_essence_fluid_flowing", () -> new ForgeFlowingFluid.Flowing(FluidSetup.MOB_ESSENCE_FLUID_PROPERTIES));
    public static RegistryObject<FlowingFluidBlock> MOB_ESSENCE_FLUID_BLOCK = BLOCKS.register("mob_essence_fluid_block",
            () -> new FlowingFluidBlock(MOB_ESSENCE_FLUID,
                    Block.Properties.create(Material.WATER)
                            .doesNotBlockMovement()
                            .hardnessAndResistance(100.0F)
                            .noDrops()));
    public static RegistryObject<Item> MOB_ESSENCE_FLUID_BUCKET = ITEMS.register("mob_essence_fluid_bucket",
            () -> new BucketItem(MOB_ESSENCE_FLUID,
                    new Item.Properties()
                            .containerItem(Items.BUCKET)
                            .maxStackSize(1)
                            .group(Woot.setup.getCreativeTab())));
    public static final ForgeFlowingFluid.Properties MOB_ESSENCE_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            MOB_ESSENCE_FLUID,
            MOB_ESSENCE_FLUID_FLOWING,
            FluidAttributes.builder(MOB_ESSENCE_STILL, MOB_ESSENCE_FLOWING).viscosity(6000))
            .bucket(MOB_ESSENCE_FLUID_BUCKET)
            .block(MOB_ESSENCE_FLUID_BLOCK);

    public static void register() {
        Woot.setup.getLogger().info("FluidSetup: register");
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        FLUIDS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

}

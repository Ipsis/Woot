package ipsis.woot.datagen.modules;

import ipsis.woot.Woot;
import ipsis.woot.crafting.InfuserRecipe;
import ipsis.woot.crafting.InfuserRecipeBuilder;
import ipsis.woot.fluilds.FluidSetup;
import ipsis.woot.modules.generic.GenericSetup;
import ipsis.woot.modules.infuser.InfuserSetup;
import ipsis.woot.modules.infuser.items.DyeCasingItem;
import ipsis.woot.modules.infuser.items.DyePlateItem;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Consumer;

public class Infuser {

    /**
     * For easy updating of json recipe energy and fluid costs
     */
    private static final ResourceLocation PRISM_RL = new ResourceLocation(Woot.MODID,"infuser/prism");
    private static final int PRISM_ENERGY_COST = 1000;
    private static final int PRISM_FLUID_COST = 1000;

    private static final ResourceLocation MAGMA_BLOCK_RL = new ResourceLocation(Woot.MODID,"infuser/magmablock1");
    private static final ResourceLocation MAGMA_BLOCK_RL2 = new ResourceLocation(Woot.MODID,"infuser/magmablock2");
    private static final int MAGMA_BLOCK_ENERGY_COST = 1000;
    private static final int MAGMA_BLOCK_FLUID_COST = 1000;

    private static final ResourceLocation ENCH_BOOK_1_RL = new ResourceLocation(Woot.MODID,"infuser/ench_book_1");
    private static final ResourceLocation ENCH_BOOK_2_RL = new ResourceLocation(Woot.MODID,"infuser/ench_book_2");
    private static final ResourceLocation ENCH_BOOK_3_RL = new ResourceLocation(Woot.MODID,"infuser/ench_book_3");
    private static final ResourceLocation ENCH_BOOK_4_RL = new ResourceLocation(Woot.MODID,"infuser/ench_book_4");
    private static final ResourceLocation ENCH_BOOK_5_RL = new ResourceLocation(Woot.MODID,"infuser/ench_book_5");

    private static final int ENCH_BOOK_1_ENERGY_COST = 1000;
    private static final int ENCH_BOOK_2_ENERGY_COST = 2000;
    private static final int ENCH_BOOK_3_ENERGY_COST = 4000;
    private static final int ENCH_BOOK_4_ENERGY_COST = 8000;
    private static final int ENCH_BOOK_5_ENERGY_COST = 16000;
    private static final int ENCH_BOOK_1_FLUID_COST = 1000;
    private static final int ENCH_BOOK_2_FLUID_COST = 2000;
    private static final int ENCH_BOOK_3_FLUID_COST = 3000;
    private static final int ENCH_BOOK_4_FLUID_COST = 4000;
    private static final int ENCH_BOOK_5_FLUID_COST = 5000;

    private static final ResourceLocation ENCH_PLATE_1_RL = new ResourceLocation(Woot.MODID,"infuser/ench_plate_1");
    private static final ResourceLocation ENCH_PLATE_2_RL = new ResourceLocation(Woot.MODID,"infuser/ench_plate_2");
    private static final ResourceLocation ENCH_PLATE_3_RL = new ResourceLocation(Woot.MODID,"infuser/ench_plate_3");

    private static final int ENCH_PLATE_1_ENERGY_COST = 10000;
    private static final int ENCH_PLATE_2_ENERGY_COST = 20000;
    private static final int ENCH_PLATE_3_ENERGY_COST = 30000;
    private static final int ENCH_PLATE_1_FLUID_COST = 1000;
    private static final int ENCH_PLATE_2_FLUID_COST = 2000;
    private static final int ENCH_PLATE_3_FLUID_COST = 3000;

    private static final int DYE_ENERGY_COST = 400;
    private static final int DYE_FLUID_COST = 72;

    public static void registerRecipes(Consumer<IFinishedRecipe> consumer) {

        ShapedRecipeBuilder.shapedRecipe(InfuserSetup.INFUSER_BLOCK.get())
                .patternLine(" d ")
                .patternLine("pcp")
                .patternLine(" b ")
                .key('d', Blocks.DROPPER)
                .key('c', GenericSetup.MACHINE_CASING_ITEM.get())
                .key('b', Items.BUCKET)
                .key('p', Blocks.PISTON)
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(InfuserSetup.INFUSER_BLOCK.get())
                .addIngredient(InfuserSetup.INFUSER_BLOCK.get())
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer, "infuser_1");


        InfuserRecipeBuilder.infuserRecipe(
                GenericSetup.PRISM_ITEM.get(), 1,
                Ingredient.fromTag(Tags.Items.GLASS),
                Ingredient.EMPTY, 0,
                new FluidStack(FluidSetup.PUREDYE_FLUID.get(), PRISM_FLUID_COST),
                PRISM_ENERGY_COST).build(consumer, "prism");

        InfuserRecipeBuilder.infuserRecipe(
                Blocks.MAGMA_BLOCK.asItem(), 1,
                Ingredient.fromTag(Tags.Items.STONE),
                Ingredient.EMPTY, 0,
                new FluidStack(Fluids.LAVA, MAGMA_BLOCK_FLUID_COST),
                MAGMA_BLOCK_ENERGY_COST).build(consumer, "magmablock1");

        InfuserRecipeBuilder.infuserRecipe(
                Blocks.MAGMA_BLOCK.asItem(), 2,
                Ingredient.fromTag(Tags.Items.OBSIDIAN),
                Ingredient.EMPTY, 0,
                new FluidStack(Fluids.LAVA, MAGMA_BLOCK_FLUID_COST),
                MAGMA_BLOCK_ENERGY_COST).build(consumer, "magmablock2");

        ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
        InfuserRecipeBuilder.infuserRecipe(
                book.getItem(),  1,
                Ingredient.fromItems(Items.BOOK),
                Ingredient.fromItems(Items.REDSTONE), 1,
                new FluidStack(FluidSetup.ENCHANT_FLUID.get(), ENCH_BOOK_1_FLUID_COST),
                ENCH_BOOK_1_ENERGY_COST).build(consumer, "ench_book_1");
        InfuserRecipeBuilder.infuserRecipe(
                book.getItem(),  2,
                Ingredient.fromItems(Items.BOOK),
                Ingredient.fromItems(Items.QUARTZ), 1,
                new FluidStack(FluidSetup.ENCHANT_FLUID.get(), ENCH_BOOK_2_FLUID_COST),
                ENCH_BOOK_2_ENERGY_COST).build(consumer, "ench_book_2");
        InfuserRecipeBuilder.infuserRecipe(
                book.getItem(),  3,
                Ingredient.fromItems(Items.BOOK),
                Ingredient.fromItems(Items.REDSTONE_BLOCK), 1,
                new FluidStack(FluidSetup.ENCHANT_FLUID.get(), ENCH_BOOK_3_FLUID_COST),
                ENCH_BOOK_3_ENERGY_COST).build(consumer, "ench_book_3");
        InfuserRecipeBuilder.infuserRecipe(
                book.getItem(),  4,
                Ingredient.fromItems(Items.BOOK),
                Ingredient.fromItems(Items.QUARTZ_BLOCK), 1,
                new FluidStack(FluidSetup.ENCHANT_FLUID.get(), ENCH_BOOK_4_FLUID_COST),
                ENCH_BOOK_4_ENERGY_COST).build(consumer, "ench_book_4");
        InfuserRecipeBuilder.infuserRecipe(
                book.getItem(),  5,
                Ingredient.fromItems(Items.BOOK),
                Ingredient.fromItems(Blocks.LAPIS_BLOCK), 1,
                new FluidStack(FluidSetup.ENCHANT_FLUID.get(), ENCH_BOOK_5_FLUID_COST),
                ENCH_BOOK_5_ENERGY_COST).build(consumer, "ench_book_5");

        InfuserRecipeBuilder.infuserRecipe(
                GenericSetup.ENCH_PLATE_1.get(), 1,
                Ingredient.fromItems(GenericSetup.SI_PLATE_ITEM.get()),
                Ingredient.fromTag(Tags.Items.INGOTS_IRON), 1,
                new FluidStack(FluidSetup.ENCHANT_FLUID.get(), ENCH_PLATE_1_FLUID_COST),
                ENCH_PLATE_1_ENERGY_COST).build(consumer, "ench_plate_1");
        InfuserRecipeBuilder.infuserRecipe(
                GenericSetup.ENCH_PLATE_2.get(), 1,
                Ingredient.fromItems(GenericSetup.SI_PLATE_ITEM.get()),
                Ingredient.fromTag(Tags.Items.INGOTS_GOLD), 1,
                new FluidStack(FluidSetup.ENCHANT_FLUID.get(), ENCH_PLATE_2_FLUID_COST),
                ENCH_PLATE_2_ENERGY_COST).build(consumer, "ench_plate_2");
        InfuserRecipeBuilder.infuserRecipe(
                GenericSetup.ENCH_PLATE_3.get(), 1,
                Ingredient.fromItems(GenericSetup.SI_PLATE_ITEM.get()),
                Ingredient.fromTag(Tags.Items.GEMS_DIAMOND), 1,
                new FluidStack(FluidSetup.ENCHANT_FLUID.get(), ENCH_PLATE_3_FLUID_COST),
                ENCH_PLATE_3_ENERGY_COST).build(consumer, "ench_plate_3");


        /**
         * Plates
         */
        class Plate {
            RegistryObject<DyeCasingItem> casing;
            RegistryObject<DyePlateItem> plate;
            String name;
            public Plate(RegistryObject<DyeCasingItem> casing, RegistryObject<DyePlateItem> plate, String name) {
                this.casing = casing;
                this.plate = plate;
                this.name = name + "_plate";
            }
        }
        Plate[] plates = {
                new Plate(InfuserSetup.WHITE_DYE_CASING_ITEM, InfuserSetup.WHITE_DYE_PLATE_ITEM, "white"),
                new Plate(InfuserSetup.ORANGE_DYE_CASING_ITEM, InfuserSetup.ORANGE_DYE_PLATE_ITEM, "orange"),
                new Plate(InfuserSetup.MAGENTA_DYE_CASING_ITEM, InfuserSetup.MAGENTA_DYE_PLATE_ITEM, "magenta"),
                new Plate(InfuserSetup.LIGHT_BLUE_DYE_CASING_ITEM, InfuserSetup.LIGHT_BLUE_DYE_PLATE_ITEM, "light_blue"),
                new Plate(InfuserSetup.YELLOW_DYE_CASING_ITEM, InfuserSetup.YELLOW_DYE_PLATE_ITEM, "yellow"),
                new Plate(InfuserSetup.LIME_DYE_CASING_ITEM, InfuserSetup.LIME_DYE_PLATE_ITEM, "lime"),
                new Plate(InfuserSetup.PINK_DYE_CASING_ITEM, InfuserSetup.PINK_DYE_PLATE_ITEM, "pink"),
                new Plate(InfuserSetup.GRAY_DYE_CASING_ITEM, InfuserSetup.GRAY_DYE_PLATE_ITEM, "gray"),
                new Plate(InfuserSetup.LIGHT_GRAY_DYE_CASING_ITEM, InfuserSetup.LIGHT_GRAY_DYE_PLATE_ITEM, "light_gray"),
                new Plate(InfuserSetup.CYAN_DYE_CASING_ITEM, InfuserSetup.CYAN_DYE_PLATE_ITEM, "cyan"),
                new Plate(InfuserSetup.PURPLE_DYE_CASING_ITEM, InfuserSetup.PURPLE_DYE_PLATE_ITEM, "purple"),
                new Plate(InfuserSetup.BLUE_DYE_CASING_ITEM, InfuserSetup.BLUE_DYE_PLATE_ITEM, "blue"),
                new Plate(InfuserSetup.BROWN_DYE_CASING_ITEM, InfuserSetup.BROWN_DYE_PLATE_ITEM, "brown"),
                new Plate(InfuserSetup.GREEN_DYE_CASING_ITEM, InfuserSetup.GREEN_DYE_PLATE_ITEM, "green"),
                new Plate(InfuserSetup.RED_DYE_CASING_ITEM, InfuserSetup.RED_DYE_PLATE_ITEM, "red"),
                new Plate(InfuserSetup.BLACK_DYE_CASING_ITEM, InfuserSetup.BLACK_DYE_PLATE_ITEM, "black")
        };

        for (Plate p : plates) {
            Woot.setup.getLogger().info("Generating Infuser recipe for {} plate", p.name);
            InfuserRecipeBuilder.infuserRecipe(
                    p.plate.get(), 1,
                    Ingredient.fromItems(p.casing.get()),
                    Ingredient.EMPTY, 0,
                    new FluidStack(FluidSetup.PUREDYE_FLUID.get(), DYE_FLUID_COST),
                    DYE_ENERGY_COST).build(consumer, p.name);
        }

    }
}

package ipsis.woot.datagen.modules;

import ipsis.woot.Woot;
import ipsis.woot.crafting.InfuserRecipe;
import ipsis.woot.fluilds.FluidSetup;
import ipsis.woot.modules.generic.GenericSetup;
import ipsis.woot.modules.infuser.InfuserSetup;
import ipsis.woot.modules.infuser.items.DyeCasingItem;
import ipsis.woot.modules.infuser.items.DyePlateItem;
import ipsis.woot.modules.squeezer.DyeMakeup;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Consumer;

public class Infuser {
    public static void registerRecipes(Consumer<IFinishedRecipe> consumer) {

        ShapedRecipeBuilder.shapedRecipe(InfuserSetup.INFUSER_BLOCK.get())
                .patternLine("idi")
                .patternLine("gbg")
                .patternLine("ioi")
                .key('i', GenericSetup.SI_PLATE_ITEM.get())
                .key('d', Blocks.DROPPER)
                .key('o', Tags.Items.OBSIDIAN)
                .key('b', Items.BUCKET)
                .key('g', Tags.Items.GLASS)
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        InfuserRecipe.infuserRecipe(new ResourceLocation(Woot.MODID, "infuser/prism"),
                Ingredient.fromTag(Tags.Items.GLASS),
                Ingredient.EMPTY, 0,
                new FluidStack(FluidSetup.PUREDYE_FLUID.get(), 1000),
                GenericSetup.PRISM_ITEM.get(), 1000)
                .build(consumer, new ResourceLocation(Woot.MODID, "infuser/prism"));

        ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
        InfuserRecipe.infuserRecipe(new ResourceLocation(Woot.MODID, "infuser/ench_book_1"),
                Ingredient.fromItems(Items.BOOK),
                Ingredient.fromItems(Items.LAPIS_LAZULI), 1,
                new FluidStack(FluidSetup.ENCHANT_FLUID.get(), 1000),
                book.getItem(),  1000)
                .build(consumer, new ResourceLocation(Woot.MODID, "infuser/ench_book_1"));
        InfuserRecipe.infuserRecipe(new ResourceLocation(Woot.MODID, "infuser/ench_book_2"),
                Ingredient.fromItems(Items.BOOK),
                Ingredient.fromItems(Items.LAPIS_LAZULI), 2,
                new FluidStack(FluidSetup.ENCHANT_FLUID.get(), 2000),
                book.getItem(), 1000)
                .build(consumer, new ResourceLocation(Woot.MODID, "infuser/ench_book_2"));
        InfuserRecipe.infuserRecipe(new ResourceLocation(Woot.MODID, "infuser/ench_book_3"),
                Ingredient.fromItems(Items.BOOK),
                Ingredient.fromItems(Items.LAPIS_LAZULI), 3,
                new FluidStack(FluidSetup.ENCHANT_FLUID.get(), 3000),
                book.getItem(), 1000)
                .build(consumer, new ResourceLocation(Woot.MODID, "infuser/ench_book_3"));
        InfuserRecipe.infuserRecipe(new ResourceLocation(Woot.MODID, "infuser/ench_book_4"),
                Ingredient.fromItems(Items.BOOK),
                Ingredient.fromItems(Items.LAPIS_LAZULI), 4,
                new FluidStack(FluidSetup.ENCHANT_FLUID.get(), 4000),
                book.getItem(), 1000)
                .build(consumer, new ResourceLocation(Woot.MODID, "infuser/ench_book_4"));
        InfuserRecipe.infuserRecipe(new ResourceLocation(Woot.MODID, "infuser/ench_book_5"),
                Ingredient.fromItems(Items.BOOK),
                Ingredient.fromItems(Items.LAPIS_LAZULI), 5,
                new FluidStack(FluidSetup.ENCHANT_FLUID.get(), 5000),
                book.getItem(), 1000)
                .build(consumer, new ResourceLocation(Woot.MODID, "infuser/ench_book_5"));

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
                this.name = name;
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
            Woot.LOGGER.info("Generating Infuser recipe for {} plate", p.name);
            ResourceLocation rl = new ResourceLocation(Woot.MODID, "infuser/" + p.name + "_plate");
            InfuserRecipe.infuserRecipe(rl,
                    Ingredient.fromItems(p.casing.get()),
                    Ingredient.EMPTY, 0,
                    new FluidStack(FluidSetup.PUREDYE_FLUID.get(), DyeMakeup.LCM),
                    p.plate.get(), 1000)
                    .build(consumer, rl);
        }
    }
}

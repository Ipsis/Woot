package ipsis.woot.datagen.modules;

import ipsis.woot.Woot;
import ipsis.woot.modules.anvil.AnvilSetup;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.modules.generic.GenericSetup;
import ipsis.woot.modules.infuser.InfuserSetup;
import ipsis.woot.modules.infuser.items.DyeCasingItem;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Consumer;

public class Generic {

    public static void registerRecipes(Consumer<IFinishedRecipe> consumer) {

        ShapedRecipeBuilder.shaped(GenericSetup.SI_DUST_ITEM.get(), 1)
                .pattern(" i ")
                .pattern("n n")
                .pattern(" s ")
                .define('i', Tags.Items.INGOTS_IRON)
                .define('n', Tags.Items.NETHERRACK)
                .define('s', Blocks.SOUL_SAND)
                .group(Woot.MODID)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer, new ResourceLocation(Woot.MODID, "si_dust_1"));

        ShapedRecipeBuilder.shaped(GenericSetup.SI_DUST_ITEM.get(), 3)
                .pattern(" i ")
                .pattern("nhn")
                .pattern(" s ")
                .define('i', Tags.Items.INGOTS_IRON)
                .define('n', Tags.Items.NETHERRACK)
                .define('s', Blocks.SOUL_SAND)
                .define('h', AnvilSetup.HAMMER_ITEM.get())
                .group(Woot.MODID)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer, new ResourceLocation(Woot.MODID, "si_dust_2"));

        CookingRecipeBuilder.smelting(
                Ingredient.of(GenericSetup.SI_DUST_ITEM.get()),
                GenericSetup.SI_INGOT_ITEM.get(),
                1.0F, 200)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(GenericSetup.SI_PLATE_ITEM.get())
                .requires(Ingredient.of(GenericSetup.SI_INGOT_ITEM.get()))
                .requires(AnvilSetup.PLATE_DIE_ITEM.get())
                .requires(Ingredient.of(AnvilSetup.HAMMER_ITEM.get()))
                .group(Woot.MODID)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(FactorySetup.MOB_SHARD_ITEM.get())
                .requires(Ingredient.of(Items.ENDER_PEARL))
                .requires(AnvilSetup.SHARD_DIE_ITEM.get())
                .requires(Ingredient.of(AnvilSetup.HAMMER_ITEM.get()))
                .group(Woot.MODID)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(FactorySetup.MOB_SHARD_ITEM.get())
                .requires(FactorySetup.MOB_SHARD_ITEM.get())
                .group(Woot.MODID)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer, new ResourceLocation(Woot.MODID, "mobshard2"));

        ShapedRecipeBuilder.shaped(GenericSetup.MACHINE_CASING_ITEM.get())
                .pattern(" p ")
                .pattern("pcp")
                .pattern(" p ")
                .define('p', GenericSetup.SI_PLATE_ITEM.get())
                .define('c', Tags.Items.CHESTS)
                .group(Woot.MODID)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer);

        /**
         * Casings
         */
        class Casing {
            RegistryObject<DyeCasingItem> casing;
            ITag.INamedTag<Item> tag;

            public Casing(RegistryObject<DyeCasingItem> casing, ITag.INamedTag<Item> tag) {
                this.casing = casing;
                this.tag = tag;
            }
        }

        Casing[] casings = {
                new Casing(InfuserSetup.WHITE_DYE_CASING_ITEM, Tags.Items.DYES_WHITE),
                new Casing(InfuserSetup.ORANGE_DYE_CASING_ITEM, Tags.Items.DYES_ORANGE),
                new Casing(InfuserSetup.MAGENTA_DYE_CASING_ITEM, Tags.Items.DYES_MAGENTA),
                new Casing(InfuserSetup.LIGHT_BLUE_DYE_CASING_ITEM, Tags.Items.DYES_LIGHT_BLUE),
                new Casing(InfuserSetup.YELLOW_DYE_CASING_ITEM, Tags.Items.DYES_YELLOW),
                new Casing(InfuserSetup.LIME_DYE_CASING_ITEM, Tags.Items.DYES_LIME),
                new Casing(InfuserSetup.PINK_DYE_CASING_ITEM, Tags.Items.DYES_PINK),
                new Casing(InfuserSetup.GRAY_DYE_CASING_ITEM, Tags.Items.DYES_GRAY),
                new Casing(InfuserSetup.LIGHT_GRAY_DYE_CASING_ITEM, Tags.Items.DYES_LIGHT_GRAY),
                new Casing(InfuserSetup.CYAN_DYE_CASING_ITEM, Tags.Items.DYES_CYAN),
                new Casing(InfuserSetup.PURPLE_DYE_CASING_ITEM, Tags.Items.DYES_PURPLE),
                new Casing(InfuserSetup.BLUE_DYE_CASING_ITEM, Tags.Items.DYES_BLUE),
                new Casing(InfuserSetup.BROWN_DYE_CASING_ITEM, Tags.Items.DYES_BROWN),
                new Casing(InfuserSetup.GREEN_DYE_CASING_ITEM, Tags.Items.DYES_GREEN),
                new Casing(InfuserSetup.RED_DYE_CASING_ITEM, Tags.Items.DYES_RED),
                new Casing(InfuserSetup.BLACK_DYE_CASING_ITEM, Tags.Items.DYES_BLACK)
        };

        for (Casing c : casings) {
            ShapelessRecipeBuilder.shapeless(c.casing.get(), 16)
                    .requires(Ingredient.of(c.tag))
                    .requires(AnvilSetup.DYE_DIE_ITEM.get())
                    .requires(Ingredient.of(AnvilSetup.HAMMER_ITEM.get()))
                    .group(Woot.MODID)
                    .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                    .save(consumer);
        }
    }
}

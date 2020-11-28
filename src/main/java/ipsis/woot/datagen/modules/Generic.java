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

        ShapedRecipeBuilder.shapedRecipe(GenericSetup.SI_DUST_ITEM.get(), 1)
                .patternLine(" i ")
                .patternLine("n n")
                .patternLine(" s ")
                .key('i', Tags.Items.INGOTS_IRON)
                .key('n', Tags.Items.NETHERRACK)
                .key('s', Blocks.SOUL_SAND)
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer, new ResourceLocation(Woot.MODID, "si_dust_1"));

        ShapedRecipeBuilder.shapedRecipe(GenericSetup.SI_DUST_ITEM.get(), 3)
                .patternLine(" i ")
                .patternLine("nhn")
                .patternLine(" s ")
                .key('i', Tags.Items.INGOTS_IRON)
                .key('n', Tags.Items.NETHERRACK)
                .key('s', Blocks.SOUL_SAND)
                .key('h', AnvilSetup.HAMMER_ITEM.get())
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer, new ResourceLocation(Woot.MODID, "si_dust_2"));

        CookingRecipeBuilder.smeltingRecipe(
                Ingredient.fromItems(GenericSetup.SI_DUST_ITEM.get()),
                GenericSetup.SI_INGOT_ITEM.get(),
                1.0F, 200)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(GenericSetup.SI_PLATE_ITEM.get())
                .addIngredient(Ingredient.fromItems(GenericSetup.SI_INGOT_ITEM.get()))
                .addIngredient(AnvilSetup.PLATE_DIE_ITEM.get())
                .addIngredient(Ingredient.fromItems(AnvilSetup.HAMMER_ITEM.get()))
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(FactorySetup.MOB_SHARD_ITEM.get())
                .addIngredient(Ingredient.fromItems(Items.ENDER_PEARL))
                .addIngredient(AnvilSetup.SHARD_DIE_ITEM.get())
                .addIngredient(Ingredient.fromItems(AnvilSetup.HAMMER_ITEM.get()))
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(FactorySetup.MOB_SHARD_ITEM.get())
                .addIngredient(FactorySetup.MOB_SHARD_ITEM.get())
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer, new ResourceLocation(Woot.MODID, "mobshard2"));

        ShapedRecipeBuilder.shapedRecipe(GenericSetup.MACHINE_CASING_ITEM.get())
                .patternLine(" p ")
                .patternLine("pcp")
                .patternLine(" p ")
                .key('p', GenericSetup.SI_PLATE_ITEM.get())
                .key('c', Tags.Items.CHESTS)
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

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
            ShapelessRecipeBuilder.shapelessRecipe(c.casing.get(), 16)
                    .addIngredient(Ingredient.fromTag(c.tag))
                    .addIngredient(AnvilSetup.DYE_DIE_ITEM.get())
                    .addIngredient(Ingredient.fromItems(AnvilSetup.HAMMER_ITEM.get()))
                    .setGroup(Woot.MODID)
                    .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                    .build(consumer);
        }
    }
}

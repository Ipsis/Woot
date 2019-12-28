package ipsis.woot.misc.squeezer;

import ipsis.woot.simulation.spawning.AbstractMobSpawner;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SqueezerRegistry {

    public static final Logger LOGGER = LogManager.getLogger();

    static SqueezerRegistry INSTANCE = new SqueezerRegistry();
    public static SqueezerRegistry get() { return INSTANCE; }

    private List<Recipe> recipes = new ArrayList<>();
    public void loadRecipes(@Nonnull RecipeManager recipeManager) {

        recipes.clear();

        addRecipe(new ItemStack(Items.INK_SAC), DyeMakeup.BLACK);
        addRecipe(new ItemStack(Items.RED_DYE), DyeMakeup.RED);
        addRecipe(new ItemStack(Items.GREEN_DYE), DyeMakeup.GREEN);
        addRecipe(new ItemStack(Items.COCOA_BEANS), DyeMakeup.BROWN);
        addRecipe(new ItemStack(Items.LAPIS_LAZULI), DyeMakeup.BLUE);
        addRecipe(new ItemStack(Items.PURPLE_DYE), DyeMakeup.PURPLE);
        addRecipe(new ItemStack(Items.CYAN_DYE), DyeMakeup.CYAN);
        addRecipe(new ItemStack(Items.LIGHT_GRAY_DYE), DyeMakeup.LIGHTGRAY);
        addRecipe(new ItemStack(Items.GRAY_DYE), DyeMakeup.GRAY);
        addRecipe(new ItemStack(Items.PINK_DYE), DyeMakeup.PINK);
        addRecipe(new ItemStack(Items.LIME_DYE), DyeMakeup.LIME);
        addRecipe(new ItemStack(Items.YELLOW_DYE), DyeMakeup.YELLOW);
        addRecipe(new ItemStack(Items.LIGHT_BLUE_DYE), DyeMakeup.LIGHTBLUE);
        addRecipe(new ItemStack(Items.MAGENTA_DYE), DyeMakeup.MAGENTA);
        addRecipe(new ItemStack(Items.ORANGE_DYE), DyeMakeup.ORANGE);
        addRecipe(new ItemStack(Items.BONE_MEAL), DyeMakeup.WHITE);

        ResourceLocation rs = new ResourceLocation("forge", "dyes");
        Tag<Item> tag = ItemTags.getCollection().get(rs);
        if (tag == null)
            return;

        for (IRecipe recipe : recipeManager.getRecipes()) {
            if (recipe instanceof ShapelessRecipe || recipe instanceof AbstractCookingRecipe) {
                if (recipe.getIngredients().size() == 1) {
                    if (tag.contains(recipe.getRecipeOutput().getItem())) {
                        // Output item is a dye so add it to the squeezer
                        DyeMakeup dyeMakeup = null;
                        for (DyeMakeup d : DyeMakeup.VALUES) {
                            if (recipe.getRecipeOutput().getItem().getTags().contains(d.getForgeTag())) {
                                dyeMakeup = d;
                                break;
                            }
                        }

                        if (dyeMakeup != null) {
                            if (recipe instanceof ShapelessRecipe) {
                                for (ItemStack itemStack : ((ShapelessRecipe)recipe).getIngredients().get(0).getMatchingStacks()) {
                                    LOGGER.info("loadRecipes: shapeless");
                                    addRecipe(itemStack.copy(), dyeMakeup);
                                }
                            } else if (recipe instanceof AbstractCookingRecipe) {
                                for (ItemStack itemStack : ((AbstractCookingRecipe)recipe).getIngredients().get(0).getMatchingStacks()) {
                                    LOGGER.info("loadRecipes: cooking");
                                    addRecipe(itemStack.copy(), dyeMakeup);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void addRecipe(ItemStack itemStack, DyeMakeup dyeMakeup) {
        LOGGER.info("addRecipe: {} -> {}", itemStack.getTranslationKey(), dyeMakeup);
        recipes.add(new Recipe(itemStack.copy(), dyeMakeup));
    }

    public class Recipe {
        private ItemStack input;
        private DyeMakeup dyeMakeup;

        public Recipe(ItemStack itemStack, DyeMakeup dyeMakeup) {
            this.input = itemStack;
            this.dyeMakeup = dyeMakeup;
        }

        public ItemStack getInput() { return this.input; }
        public DyeMakeup getDyeMakeup() { return this.dyeMakeup; }
        public boolean isInput(ItemStack itemStack) {
            if (itemStack == null || itemStack.isEmpty())
                return false;

            // This should never be true
            if (input.isEmpty())
                return false;

            if (input.isItemEqual(itemStack))
                return true;

            return false;
        }
    }

    public @Nullable Recipe getRecipe(ItemStack itemStack) {
        if (itemStack != null && !itemStack.isEmpty()) {
            for (Recipe r : recipes) {
                if (r.isInput(itemStack))
                    return r;
            }
        }

        return null;
    }

    /**
     * Red/Yellow/Blue/White breakdown for each of the standard 16 Minecraft colors
     * Dye tags are provided by Forge
     */
    private static final int LCM = 72;
    enum DyeMakeup {

        BLACK("black", LCM / 3, LCM / 3, LCM / 3, 0),
        RED("red", LCM, 0, 0, 0),
        GREEN("green", 0, LCM / 2, LCM / 2, 0),
        BROWN("brown", 3 * (LCM / 4), LCM / 8, LCM / 8, 0),
        BLUE("blue", 0, 0, LCM, 0),
        PURPLE("purple", LCM / 2, 0, LCM / 2, 0),
        CYAN("cyan", 0, 0, LCM / 4, 3 * (LCM / 4)),
        LIGHTGRAY("light_gray", LCM / 9, LCM / 9, LCM / 9, 2 * (LCM / 3)),
        GRAY("gray", LCM / 6, LCM / 6, LCM / 6, LCM / 2),
        PINK("pink", LCM / 2, 0, 0, LCM / 2),
        LIME("lime", 0, LCM / 4, LCM / 4, LCM / 2),
        YELLOW("yellow", 0, LCM, 0, 0),
        LIGHTBLUE("light_blue", 0, 0, LCM / 2, LCM / 2),
        MAGENTA("magenta", LCM / 2, 0, LCM / 4, LCM / 4),
        ORANGE("orange", LCM / 2, LCM / 2, 0, 0),
        WHITE("white", 0, 0, 0, LCM);

        private int red;
        private int yellow;
        private int blue;
        private int white;
        private String tag;

        DyeMakeup(String tag, int red, int yellow, int blue, int white) {
            this.tag = tag;
            this.red = red;
            this.yellow = yellow;
            this.blue = blue;
            this.white = white;
        }

        public int getRed() { return this.red; }
        public int getYellow() { return this.yellow; }
        public int getBlue() { return this.blue; }
        public int getWhite() { return this.white; }
        public ResourceLocation getForgeTag() { return new ResourceLocation("forge", "dyes/" + this.tag); }

        public static final DyeMakeup[] VALUES = DyeMakeup.values();
    }
}

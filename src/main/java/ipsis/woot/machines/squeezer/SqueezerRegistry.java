package ipsis.woot.machines.squeezer;

import ipsis.woot.Woot;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SqueezerRegistry {

    public static final SqueezerRegistry INSTANCE = new SqueezerRegistry();

    private List<RecipeSqueezer> recipes = new ArrayList<>();

    public void loadRecipes(RecipeManager recipeManager) {
        loadVanilla(recipeManager);

    }

    private void loadVanilla(RecipeManager recipeManager) {

        // @todo IItemProvider ????
        addRecipe(new ItemStack(Items.INK_SAC), DyeMakeup.BLACK);
        addRecipe(new ItemStack(Items.ROSE_RED), DyeMakeup.RED);
        addRecipe(new ItemStack(Items.CACTUS_GREEN), DyeMakeup.GREEN);
        addRecipe(new ItemStack(Items.COCOA_BEANS), DyeMakeup.BROWN);
        addRecipe(new ItemStack(Items.LAPIS_LAZULI), DyeMakeup.BLUE);
        addRecipe(new ItemStack(Items.PURPLE_DYE), DyeMakeup.PURPLE);
        addRecipe(new ItemStack(Items.CYAN_DYE), DyeMakeup.CYAN);
        addRecipe(new ItemStack(Items.LIGHT_GRAY_DYE), DyeMakeup.LIGHTGRAY);
        addRecipe(new ItemStack(Items.GRAY_DYE), DyeMakeup.GRAY);
        addRecipe(new ItemStack(Items.PINK_DYE), DyeMakeup.PINK);
        addRecipe(new ItemStack(Items.LIME_DYE), DyeMakeup.LIME);
        addRecipe(new ItemStack(Items.DANDELION_YELLOW), DyeMakeup.YELLOW);
        addRecipe(new ItemStack(Items.LIGHT_BLUE_DYE), DyeMakeup.LIGHTBLUE);
        addRecipe(new ItemStack(Items.MAGENTA_DYE), DyeMakeup.MAGENTA);
        addRecipe(new ItemStack(Items.ORANGE_DYE), DyeMakeup.ORANGE);
        addRecipe(new ItemStack(Items.BONE_MEAL), DyeMakeup.WHITE);

        Collection<IRecipe> recipes = recipeManager.getRecipes();
        for (IRecipe recipe : recipes) {
            if (recipe instanceof ShapelessRecipe) {
                ShapelessRecipe r = (ShapelessRecipe)recipe;
                if (r.getIngredients().size() == 1) {
                    ResourceLocation rs = new ResourceLocation("forge", "dyes");
                    boolean isDye = ItemTags.getCollection().get(rs).contains(r.getRecipeOutput().getItem());
                    if (isDye)
                        Woot.LOGGER.info("loadVanilla: crafting output is a dye for " + r.getIngredients().get(0));


                }
            }
        }


    }

    public void addRecipe(ItemStack itemStack, DyeMakeup dyeMakeup) {
        Woot.LOGGER.info("addRecipe: {} -> {}", itemStack.getTranslationKey(), dyeMakeup);
        recipes.add(new RecipeSqueezer(itemStack.copy(), dyeMakeup));
    }


    public @Nullable RecipeSqueezer getRecipe(ItemStack itemStack) {
        if (itemStack.isEmpty())
            return null;

        // @todo recipe lookup is very slow
        for (RecipeSqueezer recipeSqueezer : recipes) {
            if (recipeSqueezer.isInput(itemStack))
                return recipeSqueezer;
        }

        return null;
    }


    public class RecipeSqueezer {

        private ItemStack input;
        private DyeMakeup dyeMakeup;

        public RecipeSqueezer(ItemStack itemStack, DyeMakeup dyeMakeup) {
            this.input = itemStack;
            this.dyeMakeup = dyeMakeup;
        }

        public ItemStack getInput() { return this.input; }
        public DyeMakeup getDyeMakeup() { return this.dyeMakeup; }
        public boolean isInput(ItemStack itemStack) {
            if (itemStack.isEmpty())
                return false;

            // This should never be true
            if (input.isEmpty())
                return false;

            if (input.isItemEqual(itemStack))
                return true;

            return false;
        }

    }

    /**
     * Red/Yellow/Blue/White breakdown for each of the standard 16 Minecraft colors
     * Dye tags are provided by Forge
     */
    private static final int LCM = 72;
    public enum DyeMakeup {

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

        public int getRed() {
            return this.red;
        }

        public int getYellow() {
            return this.yellow;
        }

        public int getBlue() {
            return this.blue;
        }

        public int getWhite() {
            return this.white;
        }

        public String getTag() {
            return this.tag;
        }

        public static final DyeMakeup[] VALUES = DyeMakeup.values();

        @Override
        public String toString() {
            return name() + "-> red:" + red + "/yellow:" + yellow + "/blue:" + blue + "/white:" + white;
        }
    }
}

package ipsis.woot.machines.squeezer;

import ipsis.woot.util.helpers.LogHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class SqueezerManager {

    public static final SqueezerManager INSTANCE = new SqueezerManager();

    public void init() {

        LogHelper.info("init vanilla dyes");
        addSqueezerRecipe(new ItemStack(Items.DYE, 1, 0), DyeMakeup.BLACK);
        addSqueezerRecipe(new ItemStack(Items.DYE, 1, 1), DyeMakeup.RED);
        addSqueezerRecipe(new ItemStack(Items.DYE, 1, 2), DyeMakeup.GREEN);
        addSqueezerRecipe(new ItemStack(Items.DYE, 1, 3), DyeMakeup.BROWN);
        addSqueezerRecipe(new ItemStack(Items.DYE, 1, 4), DyeMakeup.BLUE);
        addSqueezerRecipe(new ItemStack(Items.DYE, 1, 5), DyeMakeup.PURPLE);
        addSqueezerRecipe(new ItemStack(Items.DYE, 1, 6), DyeMakeup.CYAN);
        addSqueezerRecipe(new ItemStack(Items.DYE, 1, 7), DyeMakeup.LIGHTGRAY);
        addSqueezerRecipe(new ItemStack(Items.DYE, 1, 8), DyeMakeup.GRAY);
        addSqueezerRecipe(new ItemStack(Items.DYE, 1, 9), DyeMakeup.PINK);
        addSqueezerRecipe(new ItemStack(Items.DYE, 1, 10), DyeMakeup.LIME);
        addSqueezerRecipe(new ItemStack(Items.DYE, 1, 11), DyeMakeup.YELLOW);
        addSqueezerRecipe(new ItemStack(Items.DYE, 1, 12), DyeMakeup.LIGHTBLUE);
        addSqueezerRecipe(new ItemStack(Items.DYE, 1, 13), DyeMakeup.MAGENTA);
        addSqueezerRecipe(new ItemStack(Items.DYE, 1, 14), DyeMakeup.ORANGE);
        addSqueezerRecipe(new ItemStack(Items.DYE, 1, 15), DyeMakeup.WHITE);

        LogHelper.info("init shapeless");
        Iterator<IRecipe> iter = CraftingManager.REGISTRY.iterator();
        while (iter.hasNext()) {
            IRecipe iRecipe = iter.next();
            if (iRecipe instanceof ShapelessRecipes) {
                ShapelessRecipes r = (ShapelessRecipes)iRecipe;
                if (r.getIngredients().size() == 1) {
                    DyeMakeup dyeMakeup = getDyeMakeup(r.getRecipeOutput());
                    if (dyeMakeup != null) {
                        addSqueezerRecipe(r.getIngredients().get(0), dyeMakeup);
                    }
                }
            }
        }

        LogHelper.info("init smelting");
        Iterator smeltingIter = FurnaceRecipes.instance().getSmeltingList().entrySet().iterator();
        while (smeltingIter.hasNext()) {
            Map.Entry pairs = (Map.Entry)smeltingIter.next();
            ItemStack out = ((ItemStack)pairs.getValue());
            ItemStack in = ((ItemStack)pairs.getKey());
            DyeMakeup dyeMakeup = getDyeMakeup(out);
            if (dyeMakeup != null)
                addSqueezerRecipe(in, dyeMakeup);
        }
    }

    private @Nullable DyeMakeup getDyeMakeup(ItemStack recipeOutput) {

        // TODO DyeUtils.isDye!

        // TODO handle output being an oredict dye
        if (recipeOutput.getItem() == Items.DYE)
            return DyeMakeup.getFromItem(recipeOutput);

        int[] oreIds = OreDictionary.getOreIDs(recipeOutput);
        List oreIdList = Arrays.asList(oreIds);

        for (DyeMakeup d : DyeMakeup.VALUES) {
            if (oreIdList.contains(OreDictionary.getOreID(d.oreDict)))
                return d;

        }
        return null;
    }

    private void addSqueezerRecipe(ItemStack in, DyeMakeup dyeMakeup) {
        LogHelper.info("addSqueezerRecipe: " + in.getDisplayName() + " -> " + dyeMakeup);
        recipeList.add(new SqueezerRecipe(in.copy(), dyeMakeup));
    }

    private void addSqueezerRecipe(Ingredient ingredient, DyeMakeup dyeMakeup) {

        ItemStack[] stacks = ingredient.getMatchingStacks();
        for (ItemStack itemStack : stacks) {
            LogHelper.info("addSqueezerRecipe: " + itemStack.getDisplayName() + " -> " + dyeMakeup);
            recipeList.add(new SqueezerRecipe(itemStack.copy(), dyeMakeup));
        }
    }

    // TODO Squeezer recipe list needs to be something better than walk the list
    private List<SqueezerRecipe> recipeList = new ArrayList<>();
    public @Nullable SqueezerRecipe getRecipe(@Nonnull ItemStack itemStack) {

        if (itemStack.isEmpty())
            return null;

        Iterator recipeIter = recipeList.iterator();
        while (recipeIter.hasNext()) {
            SqueezerRecipe recipe = (SqueezerRecipe)recipeIter.next();
            if (ItemStack.areItemsEqual(recipe.getInput(), itemStack)) {
                return recipe;
            }
        }

        return null;
    }

    public class SqueezerRecipe {

        private ItemStack input;
        private DyeMakeup dyeMakeup;

        public SqueezerRecipe(ItemStack input, DyeMakeup dyeMakeup) {
            this.input = input;
            this.dyeMakeup = dyeMakeup;
        }

        public ItemStack getInput() { return this.input; }
        public DyeMakeup getDyeMakeup() { return this.dyeMakeup; }
    }

    private static final int LCM = 72;
    public enum DyeMakeup {

        BLACK("dyeBlack", LCM/3, LCM/3, LCM/3, 0),
        RED("dyeRed", LCM, 0, 0, 0),
        GREEN("dyeGreen", 0, LCM/2, LCM/2, 0),
        BROWN("dyeBrown", 3*(LCM/4), LCM/8, LCM/8, 0),
        BLUE("dyeBlue", 0, 0, LCM, 0),
        PURPLE("dyePurple", LCM/2, 0, LCM/2, 0),
        CYAN("dyeCyan", 0, 0, LCM/4, 3*(LCM/4)),
        LIGHTGRAY("dyeLightGray", LCM/9, LCM/9, LCM/9, 2*(LCM/3)),
        GRAY("dyeGray", LCM/6, LCM/6, LCM/6, LCM/2),
        PINK("dyePink", LCM/2, 0, 0, LCM/2),
        LIME("dyeLime", 0, LCM/4, LCM/4, LCM/2),
        YELLOW("dyeYellow", 0, LCM, 0, 0),
        LIGHTBLUE("dyeLightBlue",0, 0, LCM/2, LCM/2),
        MAGENTA("dyeMagenta", LCM/2, 0, LCM/4, LCM/4),
        ORANGE("dyeOrange", LCM/2, LCM/2, 0, 0),
        WHITE("dyeWhite", 0, 0, 0, LCM);

        private int red;
        private int yellow;
        private int blue;
        private int white;
        private String oreDict;
        DyeMakeup(String oreDict, int red, int yellow, int blue, int white) {
            this.oreDict = oreDict;
            this.red = red;
            this.yellow = yellow;
            this.blue = blue;
            this.white = white;
        }

        public int getRed() { return this.red; }
        public int getYellow() { return this.yellow; }
        public int getBlue() { return this.blue; }
        public int getWhite() { return this.white; }
        public String getOreDict() { return this.oreDict; }
        public static final DyeMakeup[] VALUES = DyeMakeup.values();

        @Override
        public String toString() {
            return name() + "-> red:" + red + "/yellow:" + yellow + "/blue:" + blue + "/white:" + white;
        }

        public static @Nullable DyeMakeup getFromItem(ItemStack itemStack) {

            if (itemStack.getItem() != Items.DYE)
                return null;

            // TODO dyes will not be metadata based in 1.13
            int meta = itemStack.getMetadata();
            switch (meta) {
                case 0:
                    return DyeMakeup.BLACK;
                case 1:
                    return DyeMakeup.RED;
                case 2:
                    return DyeMakeup.GREEN;
                case 3:
                    return DyeMakeup.BROWN;
                case 4:
                    return DyeMakeup.BLUE;
                case 5:
                    return DyeMakeup.PURPLE;
                case 6:
                    return DyeMakeup.CYAN;
                case 7:
                    return DyeMakeup.LIGHTGRAY;
                case 8:
                    return DyeMakeup.GRAY;
                case 9:
                    return DyeMakeup.PINK;
                case 10:
                    return DyeMakeup.LIME;
                case 11:
                    return DyeMakeup.YELLOW;
                case 12:
                    return DyeMakeup.LIGHTBLUE;
                case 13:
                    return DyeMakeup.MAGENTA;
                case 14:
                    return DyeMakeup.ORANGE;
                case 15:
                    return DyeMakeup.WHITE;
            }

            return null;
        }
    }

}

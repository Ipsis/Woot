package ipsis.woot.crafting;

import ipsis.woot.fluilds.FluidSetup;
import ipsis.woot.modules.infuser.InfuserSetup;
import ipsis.woot.modules.squeezer.DyeMakeup;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SqueezerRecipe {

    public final DyeMakeup output;
    public final WootIngredient input;

    public SqueezerRecipe(DyeMakeup output, ItemStack input) {
        this(output, new WootIngredient(input));
    }

    public SqueezerRecipe(DyeMakeup output, ResourceLocation tag) {
        this(output, new WootIngredient(tag));
    }

    private SqueezerRecipe(DyeMakeup output, WootIngredient ingredient) {
        this.output = output;
        this.input = ingredient;
        initJei();
    }

    public static ArrayList<SqueezerRecipe> recipeList = new ArrayList<>();
    public static void addRecipe(DyeMakeup output, ItemStack input) {
        SqueezerRecipe recipe = new SqueezerRecipe(output, input);
        recipeList.add(recipe);
    }

    public static void addRecipe(DyeMakeup output, ResourceLocation tag) {
        SqueezerRecipe recipe = new SqueezerRecipe(output, tag);
        recipeList.add(recipe);
    }

    public FluidStack getOutput() {
        return new FluidStack(FluidSetup.PUREDYE_FLUID.get(), DyeMakeup.LCM);
    }

    public static void clearRecipes() {
        recipeList = new ArrayList<>();
    }

    public static @Nullable
    SqueezerRecipe findRecipe(ItemStack input) {
        if (input != null && !input.isEmpty()) {
            for (SqueezerRecipe recipe : recipeList) {
                if (recipe.input.isSameIngredient(input))
                    return recipe;
            }
        }

        return null;
    }

    public List<List<ItemStack>> jeiInputs;
    private void initJei() {
        jeiInputs = new ArrayList<>();
        List<ItemStack> in = new ArrayList<>();
        if (input.isItemStackIngredient()) {
            in.add(input.itemStack.copy());
        } else if (input.isTagIngredient()) {
            Tag<Item> itemTag = ItemTags.getCollection().get(input.tag);
            if (itemTag != null) {
                for (Item item : itemTag.getAllElements())
                    in.add(new ItemStack(item));
            }
            Tag<Block> blockTag = BlockTags.getCollection().get(input.tag);
            if (blockTag != null) {
                for (Block block : blockTag.getAllElements())
                    in.add(new ItemStack(block.asItem()));
            }
        }

        jeiInputs.add(in);
    }

    public List<List<ItemStack>> getJeiInputs() { return jeiInputs; }
}

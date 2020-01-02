package ipsis.woot.crafting;

import ipsis.woot.fluilds.FluidSetup;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
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

public class InfuserRecipe {

    public final WootIngredient input;
    public final WootIngredient augment;
    public final FluidStack fluid;
    public final ItemStack output;
    public final int energy;

    public InfuserRecipe(ItemStack output, Object input, FluidStack fluidStack, Object augment, int energy) {
        this.output = output;
        this.energy = energy;
        if (input instanceof ItemStack)
            this.input = new WootIngredient((ItemStack)input);
        else if (input instanceof ResourceLocation)
            this.input = new WootIngredient((ResourceLocation) input);
        else
            throw new IllegalStateException("Invalid input to InfuserRecipe");

        this.fluid = fluidStack;
        if (augment != null)
            if (augment instanceof ItemStack)
                this.augment = new WootIngredient((ItemStack) augment);
            else if (augment instanceof ResourceLocation)
                this.augment = new WootIngredient((ResourceLocation) augment);
            else
                throw new IllegalStateException("Invalid augment to InfuserRecipe");
        else
            this.augment = null;

        initJei();
    }

    public ItemStack getOutput() {
        return output.copy();
    }
    public FluidStack getFluidInput() { return this.fluid.copy(); }
    public boolean hasAugment() { return this.augment != null; }
    public int getEnergy() { return this.energy; }

    public static ArrayList<InfuserRecipe> recipeList = new ArrayList<>();
    public static void addRecipe(ItemStack output, Object input, FluidStack fluidStack, Object augment, int energy) {
        InfuserRecipe recipe = new InfuserRecipe(output, input, fluidStack, augment, energy);
        recipeList.add(recipe);
    }

    public static boolean isValidInput(ItemStack itemStack) {
        return true;
    }

    public static boolean isValidAugment(ItemStack itemStack) {
        return true;
    }

    public static boolean isValidFluid(FluidStack fluidStack) {
        return true;
    }

    public static List<Fluid> getValidFluids() {
        List<Fluid> fluids = new ArrayList<>();
        fluids.add(FluidSetup.ENCHANT_FLUID.get().getFluid());
        fluids.add(FluidSetup.PUREDYE_FLUID.get().getFluid());
        return fluids;
    }

    public static @Nullable
    InfuserRecipe findRecipe(ItemStack input, FluidStack fluidStack, ItemStack augment) {
        if (input == null || fluidStack == null || input.isEmpty() || fluidStack.isEmpty())
            return null;

        for (InfuserRecipe recipe : recipeList) {
            if (recipe.input.isSameIngredient(input) && fluidStack.containsFluid(recipe.fluid))
                return recipe;
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
                    in.add(new ItemStack(item, input.size));
            }
            Tag<Block> blockTag = BlockTags.getCollection().get(input.tag);
            if (blockTag != null) {
                for (Block block : blockTag.getAllElements())
                    in.add(new ItemStack(block.asItem(), input.getSize()));
            }
        }
        jeiInputs.add(in);

        List<ItemStack> augments = new ArrayList<>();
        if (hasAugment()) {
            if (augment.isItemStackIngredient())
                augments.add(augment.itemStack.copy());
            // TODO tags
        }
        jeiInputs.add(augments);
    }

    public List<List<ItemStack>> getJeiInputs() { return jeiInputs; }
}

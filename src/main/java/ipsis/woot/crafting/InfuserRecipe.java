package ipsis.woot.crafting;

import ipsis.woot.Woot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ipsis.woot.crafting.InfuserRecipeBuilder.SERIALIZER;

public class InfuserRecipe implements IRecipe<IInventory> {

    private final Ingredient ingredient;
    private final Ingredient augment;
    private final int augmentCount;
    private final FluidStack fluid;
    private final Item result;
    private final int count;
    private final int energy;
    private final ResourceLocation id;
    private final IRecipeType<?> type;

    public InfuserRecipe(ResourceLocation id, Ingredient ingredient, Ingredient augment, int augmentCount, FluidStack fluidStack, IItemProvider result, int count, int energy) {
        this.id = id;
        this.ingredient = ingredient;
        this.augment = augment;
        this.augmentCount = augmentCount;
        this.fluid = fluidStack;
        this.result = result.asItem();
        this.count = count;
        this.type = INFUSER_TYPE;
        this.energy = energy;

        inputs.add(Arrays.asList(ingredient.getMatchingStacks()));
        if (hasAugment()) {
            List<ItemStack> stacks = Arrays.asList(augment.getMatchingStacks());
            for (ItemStack stack : stacks)
                stack.setCount(augmentCount);
            inputs.add(stacks);
        }
    }

    public Ingredient getIngredient() { return this.ingredient; }
    public boolean hasAugment() { return this.augment != Ingredient.EMPTY; }
    public Ingredient getAugment() { return this.augment; }
    public int getAugmentCount() { return this.augmentCount; }
    public ItemStack getOutput() { return new ItemStack(this.result, this.count); }
    public FluidStack getFluidInput() { return this.fluid; }
    public int getEnergy() { return this.energy; }

    public static final IRecipeType<InfuserRecipe> INFUSER_TYPE = IRecipeType.register(Woot.MODID + ":infuser");

    /**
     * Jei
     */
    private List<List<ItemStack>> inputs = new ArrayList<>();
    public List<List<ItemStack>> getInputs() { return inputs; }

    /**
     * Valid inputs
     */

    private static List<ItemStack> validInputs = new ArrayList<>();
    public static void clearValidInputs() { validInputs.clear(); }
    public static void addValidInput(ItemStack itemStack) { validInputs.add(itemStack); }
    public static boolean isValidInput(ItemStack itemStack) {
        for (ItemStack i : validInputs) {
            if (i.isItemEqual(itemStack))
                return true;
        }
        return false;
    }

    private static List<ItemStack> validAugments = new ArrayList<>();
    public static void clearValidAugments() { validAugments.clear(); }
    public static void addValidAugment(ItemStack itemStack) { validAugments.add(itemStack); }
    public static boolean isValidAugment(ItemStack itemStack) {
        for (ItemStack i : validAugments) {
            if (i.isItemEqual(itemStack))
                return true;
        }
        return false;
    }

    private static List<FluidStack> validFluids = new ArrayList<>();
    public static void clearValidFluids() { validFluids.clear(); }
    public static void addValidFluid(FluidStack fluidStack) { validFluids.add(fluidStack); }
    public static boolean isValidFluid(FluidStack fluidStack) {
        for (FluidStack f : validFluids) {
            if (f.isFluidEqual(fluidStack))
                return true;
        }

        return false;
    }

    /**
     * IRecipe
     * Matches ingredient and optional augment
     * Any fluid lookup will have to be done externally from all matching recipes
     */
    @Override
    public boolean matches(IInventory inv, World worldIn) {

        if (!ingredient.test(inv.getStackInSlot(0)))
            return false;

        if (augment != Ingredient.EMPTY) {
            ItemStack invStack = inv.getStackInSlot(1);
            // augment count must be exact
            if (!augment.test(invStack) || augmentCount > invStack.getCount())
                return false;
        }

        return true;
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return null;
    }

    @Override
    public boolean canFit(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public IRecipeType<?> getType() {
        return type;
    }

}

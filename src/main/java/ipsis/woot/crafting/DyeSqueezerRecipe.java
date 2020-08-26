package ipsis.woot.crafting;

import ipsis.woot.Woot;
import ipsis.woot.fluilds.FluidSetup;
import ipsis.woot.modules.squeezer.DyeMakeup;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ipsis.woot.crafting.DyeSqueezerRecipeBuilder.SERIALIZER;

public class DyeSqueezerRecipe implements IRecipe<IInventory>  {

    private final Ingredient ingredient;
    private final int energy;
    private final int red;
    private final int yellow;
    private final int blue;
    private final int white;
    private final ResourceLocation id;
    private final IRecipeType<?> type;

    public DyeSqueezerRecipe(ResourceLocation id, Ingredient ingredient,
                             int energy, int red, int yellow, int blue, int white) {

        this.id = id;
        this.ingredient = ingredient;
        this.energy = energy;
        this.red = red;
        this.yellow = yellow;
        this.blue = blue;
        this.white = white;
        this.type = DYE_SQUEEZER_TYPE;

        inputs.add(Arrays.asList(ingredient.getMatchingStacks()));
    }

    public int getEnergy() { return this.energy; }
    public int getRed() { return this.red; }
    public int getYellow() { return this.yellow; }
    public int getBlue() { return this.blue; }
    public int getWhite() { return this.white; }
    public FluidStack getOutput() { return new FluidStack(FluidSetup.PUREDYE_FLUID.get(), DyeMakeup.LCM); }
    public Ingredient getIngredient() { return this.ingredient; }

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

    /**
     * Jei
     */
    private List<List<ItemStack>> inputs = new ArrayList<>();
    public List<List<ItemStack>> getInputs() { return inputs; }

    public static final IRecipeType<DyeSqueezerRecipe> DYE_SQUEEZER_TYPE = IRecipeType.register(Woot.MODID + ":dyesqueezer");

    /**
     * IRecipe
     */
    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return this.ingredient.test(inv.getStackInSlot(0));
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return ItemStack.EMPTY;
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

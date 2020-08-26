package ipsis.woot.crafting;

import com.google.gson.JsonObject;
import ipsis.woot.Woot;
import ipsis.woot.util.FluidStackHelper;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static ipsis.woot.crafting.FluidConvertorRecipeBuilder.SERIALIZER;

public class FluidConvertorRecipe implements IRecipe<IInventory> {

    private final Ingredient catalyst;
    private final int catalystCount;
    private final FluidStack inputFluid;
    private final FluidStack outputFluid;
    private final int energy;
    private final ResourceLocation id;
    private final IRecipeType<?> type;

    public FluidConvertorRecipe(ResourceLocation id, Ingredient catalyst, int catalystCount, FluidStack fluidStack, FluidStack outputFluid, int energy) {
        this.id = id;
        this.catalyst = catalyst;
        this.catalystCount = catalystCount;
        this.inputFluid = fluidStack;
        this.outputFluid = outputFluid;
        this.type = FLUID_CONV_TYPE;
        this.energy = energy;

        inputs.add(Arrays.asList(catalyst.getMatchingStacks()));
    }

    public Ingredient getCatalyst() { return this.catalyst; }
    public int getCatalystCount() { return this.catalystCount; }
    public FluidStack getOutput() { return outputFluid.copy(); }
    public FluidStack getInputFluid() { return this.inputFluid; }
    public int getEnergy() { return this.energy; }

    @Override
    public String toString() {
        return "FluidConvertorRecipe{" +
                "catalyst=" + catalyst +
                ", catalystCount=" + catalystCount +
                ", inputFluid=" + inputFluid +
                ", outputFluid=" + outputFluid +
                ", energy=" + energy +
                '}';
    }

    public static final IRecipeType<FluidConvertorRecipe> FLUID_CONV_TYPE = IRecipeType.register(Woot.MODID + ":fluidconvertor");


    /**
     * Valid inputs
     */
    private static List<ItemStack> validCatalysts = new ArrayList<>();
    public static void clearValidCatalysts() { validCatalysts.clear(); }
    public static void addValidCatalyst(ItemStack itemStack) { validCatalysts.add(itemStack); }
    public static boolean isValidCatalyst(ItemStack itemStack) {
        if (itemStack.isEmpty())
            return false;

        for (ItemStack i : validCatalysts) {
            if (i.isItemEqual(itemStack))
                return true;
        }
        return false;
    }

    private static List<FluidStack> validInputs = new ArrayList<>();
    public static void clearValidInputs() { validInputs.clear(); }
    public static void addValidInput(FluidStack fluidStack) { validInputs.add(fluidStack); }
    public static boolean isValidInput(FluidStack fluidStack) {
        if (fluidStack.isEmpty())
            return false;

        for (FluidStack f : validInputs) {
            if (f.isFluidEqual(fluidStack))
                return true;
        }
        return false;
    }

    /**
     * Jei
     */
    private List<List<ItemStack>> inputs = new ArrayList<>();
    public List<List<ItemStack>> getInputs() { return inputs; }

    /**
     * IRecipe
     * Matches catalyst
     * Any fluid lookup will have to be done externally from all matching recipes
     */
    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return catalyst.test(inv.getStackInSlot(0));
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

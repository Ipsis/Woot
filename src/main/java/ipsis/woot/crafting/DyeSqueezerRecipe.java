package ipsis.woot.crafting;

import com.google.gson.JsonObject;
import ipsis.woot.fluilds.FluidSetup;
import ipsis.woot.modules.squeezer.DyeMakeup;
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

    public static DyeSqueezerRecipe dyeSqueezerRecipe(ResourceLocation id, Ingredient ingredient, int energy, DyeMakeup dyeMakeup) {
        return new DyeSqueezerRecipe(id, ingredient, energy, dyeMakeup.getRed(), dyeMakeup.getYellow(), dyeMakeup.getBlue(), dyeMakeup.getWhite());
    }


    public void build(Consumer<IFinishedRecipe> consumer, ResourceLocation id) {
        // ? validate
        if (this.ingredient == null)
            throw new IllegalStateException("No valid ingredient for recipe " + id);
        consumer.accept(new Finished(id, this.ingredient, this.energy, this.red, this.yellow, this.blue, this.white ));
    }

    public static final IRecipeType<DyeSqueezerRecipe> DYE_SQUEEZER_TYPE = IRecipeType.register("dyesqueezer");

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

    /**
     * IFinishedRecipe
     */
    public static class Finished implements IFinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient ingredient;
        private final int energy;
        private final int red;
        private final int yellow;
        private final int blue;
        private final int white;

        private Finished(ResourceLocation id, Ingredient ingredient, int energy, int red, int yellow, int blue, int white) {
            this.id = id;
            this.ingredient = ingredient;
            this.energy = energy;
            this.red = red;
            this.yellow = yellow;
            this.blue = blue;
            this.white = white;
        }
        @Override
        public void serialize(JsonObject json) {

            json.add("ingredient", this.ingredient.serialize());
            json.addProperty("energy", this.energy);
            json.addProperty("red", this.red);
            json.addProperty("yellow", this.yellow);
            json.addProperty("blue", this.blue);
            json.addProperty("white", this.white);

        }

        @Override
        public ResourceLocation getID() {
            return id;
        }

        @Override
        public IRecipeSerializer<?> getSerializer() {
            return SERIALIZER;
        }

        @Nullable
        @Override
        public JsonObject getAdvancementJson() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementID() {
            return null;
        }
    }

    @ObjectHolder("woot:dyesqueezer")
    public static final IRecipeSerializer<IRecipe<?>> SERIALIZER = null;

}

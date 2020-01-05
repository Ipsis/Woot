package ipsis.woot.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ipsis.woot.Woot;
import ipsis.woot.fluilds.FluidSetup;
import ipsis.woot.modules.squeezer.DyeMakeup;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class DyeSqueezerRecipe {

    private final Ingredient ingredient;
    private final int energy;
    private final int red;
    private final int yellow;
    private final int blue;
    private final int white;

    public DyeSqueezerRecipe(Ingredient ingredient,
                             int energy, int red, int yellow, int blue, int white) {

        this.ingredient = ingredient;
        this.energy = energy;
        this.red = red;
        this.yellow = yellow;
        this.blue = blue;
        this.white = white;

        inputs.add(Arrays.asList(ingredient.getMatchingStacks()));
    }

    public int getEnergy() { return this.energy; }
    public int getRed() { return this.red; }
    public int getYellow() { return this.yellow; }
    public int getBlue() { return this.blue; }
    public int getWhite() { return this.white; }
    public FluidStack getOutput() { return new FluidStack(FluidSetup.PUREDYE_FLUID.get(), DyeMakeup.LCM); }

    /**
     * Jei
     */
    private List<List<ItemStack>> inputs = new ArrayList<>();
    public List<List<ItemStack>> getInputs() { return inputs; }

    /**
     *
     */
    public static ArrayList<DyeSqueezerRecipe> recipeList = new ArrayList<>();
    public static void clearRecipes() { recipeList .clear(); }
    public static void addRecipe(Ingredient ingredient, int energy, int red, int yellow, int blue, int white) {
        DyeSqueezerRecipe recipe2 = new DyeSqueezerRecipe(ingredient, energy, red, yellow, blue, white);
        recipeList.add(recipe2);
    }

    public static @Nullable
    DyeSqueezerRecipe findRecipe(ItemStack itemStack) {
        if (itemStack == null || itemStack.isEmpty())
            return null;

        for (DyeSqueezerRecipe recipe : recipeList) {
            if (recipe.ingredient.test(itemStack))
                return recipe;
        }

        return null;
    }

    public static DyeSqueezerRecipe dyeSqueezerRecipe(Ingredient ingredient, int energy, int red, int yellow, int blue, int white) {
        return new DyeSqueezerRecipe(ingredient, energy, red, yellow, blue, white);
    }

    public static DyeSqueezerRecipe dyeSqueezerRecipe(Ingredient ingredient, int energy, DyeMakeup dyeMakeup) {
        return new DyeSqueezerRecipe(ingredient, energy, dyeMakeup.getRed(), dyeMakeup.getYellow(), dyeMakeup.getBlue(), dyeMakeup.getWhite());
    }


    public void build(Consumer<IFinishedRecipe> consumer, ResourceLocation id) {
        // ? validate
        if (this.ingredient == null)
            throw new IllegalStateException("No valid ingredient for recipe " + id);
        consumer.accept(new Finished(id, this.ingredient, this.energy, this.red, this.yellow, this.blue, this.white ));
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

    public static class Serializer<T extends IRecipe<?>> implements IRecipeSerializer<T> {

        private ResourceLocation name;

        @Override
        public IRecipeSerializer<?> setRegistryName(ResourceLocation name) {
            this.name = name;
            return this;
        }

        @Nullable
        @Override
        public ResourceLocation getRegistryName() {
            return name;
        }

        @Override
        public Class<IRecipeSerializer<?>> getRegistryType() {
            return Serializer.<IRecipeSerializer<?>>castClass(IRecipeSerializer.class);
        }

        @SuppressWarnings("unchecked") // Need this wrapper, because generics
        private static <G> Class<G> castClass(Class<?> cls) {
            return (Class<G>)cls;
        }

        @SuppressWarnings("unchecked") // We return a nested one, so we can't know what type it is.
        @Override
        public T read(ResourceLocation recipeId, JsonObject json) {

            JsonElement jsonelement = (JsonElement)(JSONUtils.isJsonArray(json, "ingredient") ? JSONUtils.getJsonArray(json, "ingredient") : JSONUtils.getJsonObject(json, "ingredient"));
            Ingredient ingredient = Ingredient.deserialize(jsonelement);

            int energy = JSONUtils.getInt(json, "energy", 100);
            int red = JSONUtils.getInt(json, "red", 0);
            int yellow = JSONUtils.getInt(json, "yellow", 0);
            int blue = JSONUtils.getInt(json, "blue", 0);
            int white = JSONUtils.getInt(json, "white", 0);

            DyeSqueezerRecipe.addRecipe(ingredient, energy, red, yellow, blue, white);

            // If we return null then the recipe wont be used but we can still use the load process
            return null;
        }

        @Nullable
        @Override
        public T read(ResourceLocation recipeId, PacketBuffer buffer) {
            String s = buffer.readString(32767);
            Ingredient ingredient = Ingredient.read(buffer);
            Ingredient augment = Ingredient.read(buffer);
            ItemStack itemStack = buffer.readItemStack();
            int energy = buffer.readInt();
            int red = buffer.readInt();
            int yellow = buffer.readInt();
            int blue = buffer.readInt();
            int white = buffer.readInt();
            Woot.LOGGER.info("Recipe read not supported");
            return null;
            //return new DyeSqueezerRecipe(ingredient,energy, red, yellow, blue, white);
        }

        @Override
        public void write(PacketBuffer buffer, T recipe) {
            Woot.LOGGER.info("Recipe write not supported");
        }
    }
}

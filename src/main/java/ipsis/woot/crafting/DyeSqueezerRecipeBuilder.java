package ipsis.woot.crafting;

import com.google.gson.JsonObject;
import ipsis.woot.Woot;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class DyeSqueezerRecipeBuilder {

    private final Ingredient ingredient;
    private final int energy;
    private final int red;
    private final int yellow;
    private final int blue;
    private final int white;

    public DyeSqueezerRecipeBuilder(Ingredient ingredient, int energy, int red, int yellow, int blue, int white) {
        this.ingredient = ingredient;
        this.energy = energy;
        this.red = red;
        this.yellow = yellow;
        this.blue = blue;
        this.white = white;
    }

    public static DyeSqueezerRecipeBuilder dyeSqueezerRecipe(
            Ingredient ingredient, int energy,
            int red, int yellow, int blue, int white) {

        return new DyeSqueezerRecipeBuilder(ingredient, energy, red, yellow, blue, white);
    }

    public void build(Consumer<IFinishedRecipe> recipe, String name) {
        recipe.accept(new DyeSqueezerRecipeBuilder.Result(
                new ResourceLocation(Woot.MODID, "dyesqueezer/" + name),
                ingredient, energy, red, yellow, blue, white
        ));
    }

    public static class Result implements IFinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient ingredient;
        private final int energy;
        private final int red;
        private final int yellow;
        private final int blue;
        private final int white;

        private Result(ResourceLocation id, Ingredient ingredient, int energy, int red, int yellow, int blue, int white) {
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

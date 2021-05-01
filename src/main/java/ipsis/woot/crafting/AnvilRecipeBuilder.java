package ipsis.woot.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ipsis.woot.Woot;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class AnvilRecipeBuilder {

    private final Ingredient baseIngredient;
    private final NonNullList<Ingredient> ingredients;
    private final Item result;
    private int count;

    public AnvilRecipeBuilder( IItemProvider result, int count, Ingredient baseIngredient) {
        this.baseIngredient = baseIngredient;
        this.result = result.asItem();
        this.count = count;
        this.ingredients = NonNullList.create();
    }

    public static AnvilRecipeBuilder anvilRecipe(IItemProvider result, int count, Ingredient baseIngredient) {
        return new AnvilRecipeBuilder(result, count, baseIngredient);
    }

    public AnvilRecipeBuilder addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        return this;
    }

    public void build(Consumer<IFinishedRecipe> recipe, String name) {
        recipe.accept(new AnvilRecipeBuilder.Result(
                new ResourceLocation(Woot.MODID, "anvil/" + name),
                this.baseIngredient,
                this.result,
                this.count,
                this.ingredients
        ));
    }

    public static class Result implements IFinishedRecipe {

        private final NonNullList<Ingredient> ingredients;
        private final Ingredient baseIngredient;
        private final Item result;
        private final int count;
        private final ResourceLocation id;

        public Result(ResourceLocation id, Ingredient baseIngredient, Item result, int count, NonNullList<Ingredient> ingredients) {
            this.id = id;
            this.baseIngredient = baseIngredient;
            this.result = result;
            this.count = count;
            this.ingredients = ingredients;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("base", this.baseIngredient.toJson());
            JsonArray array = new JsonArray();
            for (Ingredient i : this.ingredients)
                array.add(i.toJson());
            json.add("ingredients", array);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("item", ForgeRegistries.ITEMS.getKey(this.result).toString());
            if (this.count > 1)
                jsonObject.addProperty("count", this.count);
            json.add("result", jsonObject);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public IRecipeSerializer<?> getType() {
            return SERIALIZER;
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }

    @ObjectHolder("woot:anvil")
    public static final IRecipeSerializer<IRecipe<?>> SERIALIZER = null;
}

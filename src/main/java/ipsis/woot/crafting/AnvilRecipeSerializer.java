package ipsis.woot.crafting;

import com.google.gson.JsonArray;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class AnvilRecipeSerializer<T extends AnvilRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {

    private final AnvilRecipeSerializer.IFactory<T> factory;

    public AnvilRecipeSerializer(AnvilRecipeSerializer.IFactory<T> factory) {
        this.factory = factory;
    }

    @Nullable
    @Override
    public T read(ResourceLocation recipeId, PacketBuffer buffer) {
        String s = buffer.readString(32767);
        Ingredient baseIngredient = Ingredient.read(buffer);

        NonNullList<Ingredient> ingredients = NonNullList.create();
        int ingCount = buffer.readShort();
        if (ingCount != 0) {
            for (int i = 0; i < ingCount; i++)
                ingredients.add(Ingredient.read(buffer));
        }

        ItemStack result = buffer.readItemStack();
        return this.factory.create(recipeId, baseIngredient, result.getItem(), result.getCount(), ingredients);
    }

    @Override
    public void write(PacketBuffer buffer, T recipe) {
        recipe.getBaseIngredient().write(buffer);
        buffer.writeShort(recipe.getIngredients().size());
        for (Ingredient ingredient : recipe.getIngredients())
            ingredient.write(buffer);
        buffer.writeItemStack(recipe.getOutput());
    }

    @Override
    public T read(ResourceLocation recipeId, JsonObject json) {

        JsonElement jsonelement = (JsonElement) (JSONUtils.isJsonArray(json, "base") ? JSONUtils.getJsonArray(json, "base") : JSONUtils.getJsonObject(json, "base"));
        Ingredient baseIngredient = Ingredient.deserialize(jsonelement);

        NonNullList<Ingredient> nonnulllist = readIngredients(JSONUtils.getJsonArray(json, "ingredients"));
        if (nonnulllist.isEmpty()) {
            throw new JsonParseException("No ingredients for anvil recipe");
        } else if (nonnulllist.size() > 4) {
            throw new JsonParseException("Too many ingredients for anvil recipe the max is 4");
        } else {
            ItemStack result = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
            return this.factory.create(recipeId, baseIngredient, result.getItem(), result.getCount(), nonnulllist);
        }
    }

    private static NonNullList<Ingredient> readIngredients(JsonArray p_199568_0_) {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        for (int i = 0; i < p_199568_0_.size(); ++i) {
            Ingredient ingredient = Ingredient.deserialize(p_199568_0_.get(i));
            if (!ingredient.hasNoMatchingItems())
                nonnulllist.add(ingredient);
        }
        return nonnulllist;
    }

    public interface IFactory<T extends AnvilRecipe> {
        T create(ResourceLocation resourceLocation, Ingredient base, IItemProvider result, int count, NonNullList<Ingredient> ingredients);
    }
}
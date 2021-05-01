package ipsis.woot.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ipsis.woot.Woot;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class DyeSqueezerRecipeSerializer<T extends DyeSqueezerRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {

    private final DyeSqueezerRecipeSerializer.IFactory<T> factory;

    public DyeSqueezerRecipeSerializer(DyeSqueezerRecipeSerializer.IFactory<T> factory) {
        this.factory = factory;
    }

    @Nullable
    @Override
    public T fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
        try {
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            int energy = buffer.readInt();
            int red = buffer.readInt();
            int yellow = buffer.readInt();
            int blue = buffer.readInt();
            int white = buffer.readInt();
            return this.factory.create(recipeId, ingredient, energy, red, yellow, blue, white);
        } catch (Exception e) {
            Woot.setup.getLogger().error("DyeSqueezerRecipeSerializer:read", e);
            throw e;
        }
    }

    @Override
    public void toNetwork(PacketBuffer buffer, T recipe) {
        //Woot.setup.getLogger().debug("DyeSqueezerRecipeSerializer:write");
        try {
            recipe.getIngredient().toNetwork(buffer);
            buffer.writeInt(recipe.getEnergy());
            buffer.writeInt(recipe.getRed());
            buffer.writeInt(recipe.getYellow());
            buffer.writeInt(recipe.getBlue());
            buffer.writeInt(recipe.getWhite());
        } catch (Exception e) {
            Woot.setup.getLogger().error("DyeSqueezerRecipeSerializer:write", e);
            throw e;
        }
    }

    @Override
    public T fromJson(ResourceLocation recipeId, JsonObject json) {
        JsonElement jsonelement = (JsonElement)(JSONUtils.isArrayNode(json, "ingredient") ? JSONUtils.getAsJsonArray(json, "ingredient") : JSONUtils.getAsJsonObject(json, "ingredient"));
        Ingredient ingredient = Ingredient.fromJson(jsonelement);

        int energy = JSONUtils.getAsInt(json, "energy", 100);
        int red = JSONUtils.getAsInt(json, "red", 0);
        int yellow = JSONUtils.getAsInt(json, "yellow", 0);
        int blue = JSONUtils.getAsInt(json, "blue", 0);
        int white = JSONUtils.getAsInt(json, "white", 0);

        return this.factory.create(recipeId, ingredient, energy, red, yellow, blue, white);
    }

    public interface IFactory<T extends DyeSqueezerRecipe> {
        T create(ResourceLocation resourceLocation, Ingredient ingredient, int energy, int red, int yellow, int blue, int white);
    }
}

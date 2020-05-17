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
    public T read(ResourceLocation recipeId, PacketBuffer buffer) {
        try {
            Ingredient ingredient = Ingredient.read(buffer);
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
    public void write(PacketBuffer buffer, T recipe) {
        //Woot.setup.getLogger().debug("DyeSqueezerRecipeSerializer:write");
        try {
            recipe.getIngredient().write(buffer);
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
    public T read(ResourceLocation recipeId, JsonObject json) {
        JsonElement jsonelement = (JsonElement)(JSONUtils.isJsonArray(json, "ingredient") ? JSONUtils.getJsonArray(json, "ingredient") : JSONUtils.getJsonObject(json, "ingredient"));
        Ingredient ingredient = Ingredient.deserialize(jsonelement);

        int energy = JSONUtils.getInt(json, "energy", 100);
        int red = JSONUtils.getInt(json, "red", 0);
        int yellow = JSONUtils.getInt(json, "yellow", 0);
        int blue = JSONUtils.getInt(json, "blue", 0);
        int white = JSONUtils.getInt(json, "white", 0);

        return this.factory.create(recipeId, ingredient, energy, red, yellow, blue, white);
    }

    public interface IFactory<T extends DyeSqueezerRecipe> {
        T create(ResourceLocation resourceLocation, Ingredient ingredient, int energy, int red, int yellow, int blue, int white);
    }
}

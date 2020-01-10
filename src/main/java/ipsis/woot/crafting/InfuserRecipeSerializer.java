package ipsis.woot.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ipsis.woot.util.FluidStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class InfuserRecipeSerializer<T extends InfuserRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {

    private final InfuserRecipeSerializer.IFactory<T> factory;

    public InfuserRecipeSerializer(InfuserRecipeSerializer.IFactory<T> factory) {
        this.factory = factory;
    }


    @Override
    public T read(ResourceLocation recipeId, JsonObject json) {

        JsonElement jsonelement = (JsonElement)(JSONUtils.isJsonArray(json, "ingredient") ? JSONUtils.getJsonArray(json, "ingredient") : JSONUtils.getJsonObject(json, "ingredient"));
        Ingredient ingredient = Ingredient.deserialize(jsonelement);
        Ingredient augment = Ingredient.EMPTY;
        if (json.has("augment")) {
            jsonelement = (JsonElement) (JSONUtils.isJsonArray(json, "augment") ? JSONUtils.getJsonArray(json, "augment") : JSONUtils.getJsonObject(json, "augment"));
            augment = Ingredient.deserialize(jsonelement);
        }
        int augmentCount = 1;
        if (json.has("augment_count"))
            augmentCount = JSONUtils.getInt(json, "augment_count", 1);
        FluidStack fluidStack = FluidStackHelper.parse(JSONUtils.getJsonObject(json, "infuse"));
        ItemStack result = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
        int energy = JSONUtils.getInt(json, "energy", 1000);

        return this.factory.create(recipeId, ingredient, augment, augmentCount, fluidStack, result.getItem(), result.getCount(), energy);
    }

    @Nullable
    @Override
    public T read(ResourceLocation recipeId, PacketBuffer buffer) {
        return null;
    }

    @Override
    public void write(PacketBuffer buffer, T recipe) {

    }

    public interface IFactory<T extends InfuserRecipe> {
        T create(ResourceLocation resourceLocation, Ingredient ingredient, Ingredient augment, int augmentCount, FluidStack fluidStack, IItemProvider item, int count, int energy);
    }
}

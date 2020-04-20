package ipsis.woot.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ipsis.woot.util.FluidStackHelper;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class ConatusGeneratorRecipeSerializer<T extends ConatusGeneratorRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements  IRecipeSerializer<T> {

    private final ConatusGeneratorRecipeSerializer.IFactory<T> factory;

    public ConatusGeneratorRecipeSerializer(ConatusGeneratorRecipeSerializer.IFactory<T> factory) {
        this.factory = factory;
    }

    @Override
    public T read(ResourceLocation recipeId, JsonObject json) {

        JsonElement jsonelement = (JsonElement)(JSONUtils.isJsonArray(json, "ingredient") ? JSONUtils.getJsonArray(json, "catalyst") : JSONUtils.getJsonObject(json, "catalyst"));
        Ingredient catalyst = Ingredient.deserialize(jsonelement);

        int catalystCount = 1;
        if (json.has("catalyst_count"))
            catalystCount = JSONUtils.getInt(json, "catalyst_count", 1);

        FluidStack inputFluid = FluidStackHelper.parse(JSONUtils.getJsonObject(json, "input"));
        FluidStack outputFluid = FluidStackHelper.parse(JSONUtils.getJsonObject(json, "result"));
        int energy = JSONUtils.getInt(json, "energy", 1000);

        return this.factory.create(recipeId,
                catalyst, catalystCount, inputFluid, outputFluid, energy);
    }

    @Nullable
    @Override
    public T read(ResourceLocation recipeId, PacketBuffer buffer) {
        return null;
    }

    @Override
    public void write(PacketBuffer buffer, T recipe) {

    }

    public interface IFactory<T extends ConatusGeneratorRecipe> {
        T create(ResourceLocation id, Ingredient catalyst, int catalystCount, FluidStack fluidStack, FluidStack outputFluid, int energy);
    }
}

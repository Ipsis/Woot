package ipsis.woot.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ipsis.woot.Woot;
import ipsis.woot.util.FluidStackHelper;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class FluidConvertorRecipeSerializer<T extends FluidConvertorRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements  IRecipeSerializer<T> {

    private final FluidConvertorRecipeSerializer.IFactory<T> factory;

    public FluidConvertorRecipeSerializer(FluidConvertorRecipeSerializer.IFactory<T> factory) {
        this.factory = factory;
    }

    @Override
    public T read(ResourceLocation recipeId, JsonObject json) {

            JsonElement jsonelement = (JsonElement) (JSONUtils.isJsonArray(json, "ingredient") ? JSONUtils.getJsonArray(json, "catalyst") : JSONUtils.getJsonObject(json, "catalyst"));
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
        try {
            Ingredient catalyst = Ingredient.read(buffer);
            int catalystCount = buffer.readInt();
            FluidStack inputFluid = buffer.readFluidStack();
            FluidStack outputFluid = buffer.readFluidStack();
            int energy = buffer.readInt();
            return this.factory.create(recipeId,
                    catalyst, catalystCount, inputFluid, outputFluid, energy);
        } catch (Exception e) {
            Woot.setup.getLogger().error("FluidConvertorRecipeSerializer:read", e);
            throw e;
        }
    }

    @Override
    public void write(PacketBuffer buffer, T recipe) {
        //Woot.setup.getLogger().debug("FluidConvertorRecipeSerializer:write");
        try {
            recipe.getCatalyst().write(buffer);
            buffer.writeInt(recipe.getCatalystCount());
            buffer.writeFluidStack(recipe.getInputFluid());
            buffer.writeFluidStack(recipe.getOutput());
            buffer.writeInt(recipe.getEnergy());
        } catch (Exception e) {
            Woot.setup.getLogger().error("FluidConvertorRecipeSerializer:write", e);
            throw e;
        }
    }

    public interface IFactory<T extends FluidConvertorRecipe> {
        T create(ResourceLocation id, Ingredient catalyst, int catalystCount, FluidStack fluidStack, FluidStack outputFluid, int energy);
    }
}

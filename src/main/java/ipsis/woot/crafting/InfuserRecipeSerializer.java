package ipsis.woot.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ipsis.woot.Woot;
import ipsis.woot.util.FluidStackHelper;
import ipsis.woot.util.FluidStackPacketHandler;
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
        try {
            Ingredient augment = Ingredient.EMPTY;
            Ingredient ingredient = Ingredient.read(buffer);
            FluidStack fluidStack = buffer.readFluidStack();
            int augmentCount = buffer.readInt();
            if (augmentCount > 0)
                augment = Ingredient.read(buffer);

            int energy = buffer.readInt();
            ItemStack result = buffer.readItemStack();
            return this.factory.create(recipeId, ingredient, augment, augmentCount, fluidStack, result.getItem(), result.getCount(), energy);
        } catch (Exception e) {
            Woot.setup.getLogger().error("InfuserRecipeSerializer:read", e);
            throw e;
        }
    }


    @Override
    public void write(PacketBuffer buffer, T recipe) {
        //Woot.setup.getLogger().debug("InfuserRecipeSerializer:write");
        try {
            recipe.getIngredient().write(buffer);
            buffer.writeFluidStack(recipe.getFluidInput());
            if (recipe.hasAugment()) {
                buffer.writeInt(recipe.getAugmentCount());
                recipe.getAugment().write(buffer);
            } else {
                buffer.writeInt(0);
            }
            buffer.writeInt(recipe.getEnergy());
            buffer.writeItemStack(recipe.getOutput());
        } catch (Exception e) {
            Woot.setup.getLogger().error("InfuserRecipeSerializer:write", e);
            throw e;
        }
    }

    public interface IFactory<T extends InfuserRecipe> {
        T create(ResourceLocation resourceLocation, Ingredient ingredient, Ingredient augment, int augmentCount, FluidStack fluidStack, IItemProvider item, int count, int energy);
    }
}

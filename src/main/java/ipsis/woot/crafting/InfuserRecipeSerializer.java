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
    public T fromJson(ResourceLocation recipeId, JsonObject json) {

        JsonElement jsonelement = (JsonElement)(JSONUtils.isArrayNode(json, "ingredient") ? JSONUtils.getAsJsonArray(json, "ingredient") : JSONUtils.getAsJsonObject(json, "ingredient"));
        Ingredient ingredient = Ingredient.fromJson(jsonelement);
        Ingredient augment = Ingredient.EMPTY;
        if (json.has("augment")) {
            jsonelement = (JsonElement) (JSONUtils.isArrayNode(json, "augment") ? JSONUtils.getAsJsonArray(json, "augment") : JSONUtils.getAsJsonObject(json, "augment"));
            augment = Ingredient.fromJson(jsonelement);
        }
        int augmentCount = 1;
        if (json.has("augment_count"))
            augmentCount = JSONUtils.getAsInt(json, "augment_count", 1);
        FluidStack fluidStack = FluidStackHelper.parse(JSONUtils.getAsJsonObject(json, "infuse"));
        ItemStack result = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "result"));
        int energy = JSONUtils.getAsInt(json, "energy", 1000);

        return this.factory.create(recipeId, ingredient, augment, augmentCount, fluidStack, result.getItem(), result.getCount(), energy);
    }

    @Nullable
    @Override
    public T fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
        try {
            Ingredient augment = Ingredient.EMPTY;
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            FluidStack fluidStack = buffer.readFluidStack();
            int augmentCount = buffer.readInt();
            if (augmentCount > 0)
                augment = Ingredient.fromNetwork(buffer);

            int energy = buffer.readInt();
            ItemStack result = buffer.readItem();
            return this.factory.create(recipeId, ingredient, augment, augmentCount, fluidStack, result.getItem(), result.getCount(), energy);
        } catch (Exception e) {
            Woot.setup.getLogger().error("InfuserRecipeSerializer:read", e);
            throw e;
        }
    }


    @Override
    public void toNetwork(PacketBuffer buffer, T recipe) {
        //Woot.setup.getLogger().debug("InfuserRecipeSerializer:write");
        try {
            recipe.getIngredient().toNetwork(buffer);
            buffer.writeFluidStack(recipe.getFluidInput());
            if (recipe.hasAugment()) {
                buffer.writeInt(recipe.getAugmentCount());
                recipe.getAugment().toNetwork(buffer);
            } else {
                buffer.writeInt(0);
            }
            buffer.writeInt(recipe.getEnergy());
            buffer.writeItem(recipe.getOutput());
        } catch (Exception e) {
            Woot.setup.getLogger().error("InfuserRecipeSerializer:write", e);
            throw e;
        }
    }

    public interface IFactory<T extends InfuserRecipe> {
        T create(ResourceLocation resourceLocation, Ingredient ingredient, Ingredient augment, int augmentCount, FluidStack fluidStack, IItemProvider item, int count, int energy);
    }
}

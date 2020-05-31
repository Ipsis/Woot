package ipsis.woot.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ipsis.woot.Woot;
import ipsis.woot.util.FakeMob;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class FactoryRecipeSerializer<T extends FactoryRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {

    private final FactoryRecipeSerializer.IFactory<T> factory;

    public FactoryRecipeSerializer(FactoryRecipeSerializer.IFactory<T> factory) {
        this.factory = factory;
    }

    @Override
    public T read(ResourceLocation recipeId, JsonObject json) {

       FakeMob fakeMob = new FakeMob(JSONUtils.getString(json, "mob", ""));

       NonNullList<ItemStack> items = NonNullList.create();
       NonNullList<FluidStack> fluids = NonNullList.create();

       JsonArray itemArray = JSONUtils.getJsonArray(json, "items");
       for (int i = 0; i < itemArray.size(); i++) {
           if (itemArray.get(i) instanceof JsonObject) {
               ItemStack itemStack = ShapedRecipe.deserializeItem((JsonObject) itemArray.get(i));
               if (!itemStack.isEmpty())
                   items.add(itemStack);
           }
       }

        JsonArray fluidArray = JSONUtils.getJsonArray(json, "fluids");
        for (int i = 0; i < fluidArray.size(); i++) {

        }

       return this.factory.create(recipeId, fakeMob, items, fluids);
    }

    @Nullable
    @Override
    public T read(ResourceLocation recipeId, PacketBuffer buffer) {

        try {
            FakeMob fakeMob = new FakeMob(buffer.readString());
            NonNullList<ItemStack> items = NonNullList.create();
            NonNullList<FluidStack> fluids = NonNullList.create();

            int itemCount = buffer.readShort();
            for (int i = 0; i < itemCount; i++)
                items.add(buffer.readItemStack());

            int fluidCount = buffer.readShort();
            for (int i = 0; i < fluidCount; i++)
                fluids.add(buffer.readFluidStack());

            return this.factory.create(recipeId, fakeMob, items, fluids);
        } catch (Exception e) {
            Woot.setup.getLogger().error("FactoryRecipeSerializer:read", e);
            throw e;
        }
    }

    @Override
    public void write(PacketBuffer buffer, T recipe) {

        try {
            buffer.writeString(recipe.getFakeMob().toString());
            buffer.writeShort(recipe.getItems().size());
            for (ItemStack itemStack : recipe.getItems())
                buffer.writeItemStack(itemStack);
            buffer.writeShort(recipe.getFluids().size());
            for (FluidStack fluidStack : recipe.getFluids())
                buffer.writeFluidStack(fluidStack);

        } catch (Exception e) {
            Woot.setup.getLogger().error("FactoryRecipeSerializer:write", e);
            throw e;
        }
    }

    public interface IFactory<T extends FactoryRecipe> {
        T create(ResourceLocation id, FakeMob fakeMob, NonNullList<ItemStack> items, NonNullList<FluidStack> fluids);
    }

}

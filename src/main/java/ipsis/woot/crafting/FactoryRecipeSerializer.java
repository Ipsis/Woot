package ipsis.woot.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import ipsis.woot.Woot;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.FluidStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.crafting.CraftingHelper;
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
        NonNullList<FactoryRecipe.Drop> drops = NonNullList.create();

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
            if (fluidArray.get(i) instanceof JsonObject) {
                FluidStack fluidStack = FluidStackHelper.parse((JsonObject)fluidArray.get(i));
                if (!fluidStack.isEmpty())
                    fluids.add(fluidStack);
            }
        }

        JsonArray dropArray = JSONUtils.getJsonArray(json, "drops");
        for (int i =0 ; i < dropArray.size(); i++) {
            if (dropArray.get(i) instanceof JsonObject) {
                JsonObject jsonObject = (JsonObject)dropArray.get(i);
                ItemStack itemStack = CraftingHelper.getItemStack(jsonObject, true);
                if (!itemStack.isEmpty()) {

                    FactoryRecipe.Drop drop = new FactoryRecipe.Drop(itemStack);

                    JsonArray sizeArray = JSONUtils.getJsonArray(jsonObject, "sizes");
                    JsonArray chanceArray = JSONUtils.getJsonArray(jsonObject, "chances");

                    for (int j = 0; j < sizeArray.size(); j++) {
                        if (sizeArray.get(j).isJsonPrimitive()) {
                            JsonPrimitive primitive = sizeArray.get(j).getAsJsonPrimitive();
                            if (primitive.isNumber()) {
                                Integer stackSize = sizeArray.get(j).getAsInt();
                                if (j < 4)
                                    drop.stackSizes[j] = stackSize;
                            }
                        }
                    }

                    for (int j = 0; j < chanceArray.size(); j++) {
                        if (chanceArray.get(j).isJsonPrimitive()) {
                            JsonPrimitive primitive = sizeArray.get(j).getAsJsonPrimitive();
                            if (primitive.isNumber()) {
                                Float chance = chanceArray.get(j).getAsFloat();
                                if (j < 4)
                                    drop.dropChance[j] = MathHelper.clamp(chance, 0.0F, 100.0F);
                            }
                        }
                    }
                    drops.add(drop);
                }
            }
        }

       return this.factory.create(recipeId, fakeMob, items, fluids, drops);
    }

    @Nullable
    @Override
    public T read(ResourceLocation recipeId, PacketBuffer buffer) {

        try {
            FakeMob fakeMob = new FakeMob(buffer.readString());
            NonNullList<ItemStack> items = NonNullList.create();
            NonNullList<FluidStack> fluids = NonNullList.create();
            NonNullList<FactoryRecipe.Drop> drops = NonNullList.create();

            int itemCount = buffer.readShort();
            for (int i = 0; i < itemCount; i++)
                items.add(buffer.readItemStack());

            int fluidCount = buffer.readShort();
            for (int i = 0; i < fluidCount; i++)
                fluids.add(buffer.readFluidStack());

            return this.factory.create(recipeId, fakeMob, items, fluids, drops);
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
        T create(ResourceLocation id, FakeMob fakeMob, NonNullList<ItemStack> items, NonNullList<FluidStack> fluids, NonNullList<FactoryRecipe.Drop> drops);
    }

}

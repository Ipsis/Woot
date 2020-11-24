package ipsis.woot.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ipsis.woot.Woot;
import ipsis.woot.util.FakeMob;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

import java.util.function.Consumer;

public class FactoryRecipeBuilder {

    private final FakeMob result;
    private final NonNullList<ItemStack> items;
    private final NonNullList<FluidStack> fluids;
    private final NonNullList<FactoryRecipe.Drop> drops;

    public FactoryRecipeBuilder(FakeMob result) {
        this.result = result;
        this.items = NonNullList.create();
        this.fluids = NonNullList.create();
        this.drops = NonNullList.create();
    }

    public static FactoryRecipeBuilder factoryRecipe(FakeMob result) {
        return new FactoryRecipeBuilder(result);
    }

    public FactoryRecipeBuilder addIngredient(ItemStack itemStack) {
        this.items.add(itemStack);
        return this;
    }

    public FactoryRecipeBuilder addIngredient(FluidStack fluidStack) {
        this.fluids.add(fluidStack);
        return this;
    }

    public FactoryRecipeBuilder addDrop(FactoryRecipe.Drop drop) {
        this.drops.add(drop);
        return this;
    }

    public void build(Consumer<IFinishedRecipe> recipe, String name) {
        recipe.accept(new Result(
                new ResourceLocation(Woot.MODID, "factory/" + name),
                this.result,
                this.items,
                this.fluids,
                this.drops
        ));
    }

    public static class Result implements IFinishedRecipe {

        private final FakeMob result;
        private final NonNullList<ItemStack> items;
        private final NonNullList<FluidStack> fluids;
        private final NonNullList<FactoryRecipe.Drop> drops;
        private final ResourceLocation id;

        public Result(ResourceLocation id, FakeMob result, NonNullList<ItemStack> items, NonNullList<FluidStack> fluids, NonNullList<FactoryRecipe.Drop> drops) {
           this.id = id;
           this.result = result;
           this.items = items;
           this.fluids = fluids;
           this.drops = drops;
        }

        @Override
        public void serialize(JsonObject json) {

            json.addProperty("mob", result.getResourceLocation().toString());

            // Items
            JsonArray array = new JsonArray();
            for (ItemStack itemStack : items) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("item", ForgeRegistries.ITEMS.getKey(itemStack.getItem()).toString());
                jsonObject.addProperty("count", itemStack.getCount());
                array.add(jsonObject);
            }
            json.add("items", array);

            // fluids
            array = new JsonArray();
            for (FluidStack fluidStack : fluids) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("fluid", ForgeRegistries.FLUIDS.getKey(fluidStack.getFluid()).toString());
                jsonObject.addProperty("amount", fluidStack.getAmount());
                array.add(jsonObject);
            }
            json.add("fluids", array);


            // Drops
            array = new JsonArray();
            for (FactoryRecipe.Drop d : drops) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("item", ForgeRegistries.ITEMS.getKey(d.itemStack.getItem()).toString());
                JsonArray sizes = new JsonArray();
                for (int i = 0; i < d.stackSizes.length; i++)
                    sizes.add(d.stackSizes[i]);
                jsonObject.add("sizes", sizes);

                JsonArray chances = new JsonArray();
                for (int i = 0; i < d.dropChance.length; i++)
                    chances.add(d.dropChance[i]);
                jsonObject.add("chances", chances);
                array.add(jsonObject);

            }
            json.add("drops", array);

        }

        @Override
        public ResourceLocation getID() {
            return id;
        }

        @Override
        public IRecipeSerializer<?> getSerializer() {
            return SERIALIZER;
        }

        @Override
        public JsonObject getAdvancementJson() {
            return null;
        }

        @Override
        public ResourceLocation getAdvancementID() {
            return null;
        }
    }

    @ObjectHolder("woot:factory")
    public static final IRecipeSerializer<IRecipe<?>> SERIALIZER = null;


}

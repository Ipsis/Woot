package ipsis.woot.crafting;

import com.google.gson.JsonObject;
import ipsis.woot.Woot;
import ipsis.woot.util.FluidStackHelper;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class InfuserRecipeBuilder {

    private final Ingredient ingredient;
    private final Ingredient augment;
    private final int augmentCount;
    private final FluidStack fluid;
    private final Item result;
    private final int count;
    private final int energy;

    public InfuserRecipeBuilder(IItemProvider result, int count, Ingredient ingredient, Ingredient augment, int augmentCount, FluidStack fluidStack, int energy) {
        this.ingredient = ingredient;
        this.augment = augment;
        this.augmentCount = augmentCount;
        this.fluid = fluidStack;
        this.result = result.asItem();
        this.count = count;
        this.energy = energy;
    }

    public static InfuserRecipeBuilder infuserRecipe(IItemProvider result, int count, Ingredient ingredient, Ingredient augment, int augmentCount, FluidStack fluidStack, int energy) {
        return new InfuserRecipeBuilder(result, count, ingredient, augment, augmentCount, fluidStack, energy);
    }


    public void build(Consumer<IFinishedRecipe> recipe, String name) {
        recipe.accept(new InfuserRecipeBuilder.Result(
                new ResourceLocation(Woot.MODID, "infuser/" + name),
                 this.ingredient,
                this.augment,
                this.augmentCount,
                this.fluid,
                this.result,
                this.count,
                this.energy
        ));
    }

    public static class Result implements IFinishedRecipe {

        private final ResourceLocation id;
        private final Ingredient ingredient;
        private final Ingredient augment;
        private final int augmentCount;
        private final FluidStack fluid;
        private final Item result;
        private final int count;
        private final int energy;

        private Result(ResourceLocation id, Ingredient ingredient, Ingredient augment, int augmentCount, FluidStack fluidStack, Item result, int count, int energy) {
            this.id = id;
            this.ingredient = ingredient;
            this.augment = augment;
            this.augmentCount = augmentCount;
            this.fluid = fluidStack;
            this.result = result;
            this.count = count;
            this.energy = energy;
        }

        @Override
        public void serialize(JsonObject json) {
            json.add("ingredient", this.ingredient.serialize());
            if (augment != Ingredient.EMPTY) {
                json.add("augment", this.augment.serialize());
                if (this.augmentCount > 1)
                    json.addProperty("augment_count", augmentCount);
            }

            json.add("infuse", FluidStackHelper.create(this.fluid));

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("item", ForgeRegistries.ITEMS.getKey(this.result).toString());
            if (this.count > 1)
                jsonObject.addProperty("count", this.count);
            json.add("result", jsonObject);
            json.addProperty("energy", this.energy);
        }

        @Override
        public ResourceLocation getID() {
            return id;
        }

        @Override
        public IRecipeSerializer<?> getSerializer() {
            return SERIALIZER;
        }

        @Nullable
        @Override
        public JsonObject getAdvancementJson() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementID() {
            return null;
        }
    }

    @ObjectHolder("woot:infuser")
    public static final IRecipeSerializer<IRecipe<?>> SERIALIZER = null;
}

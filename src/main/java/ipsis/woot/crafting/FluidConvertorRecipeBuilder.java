package ipsis.woot.crafting;

import com.google.gson.JsonObject;
import ipsis.woot.Woot;
import ipsis.woot.util.FluidStackHelper;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class FluidConvertorRecipeBuilder {

    private final Ingredient catalyst;
    private final int catalystCount;
    private final FluidStack inFluid;
    private final FluidStack result;
    private final int energy;

    public FluidConvertorRecipeBuilder(FluidStack result, int energy, Ingredient catalyst, int catalystCount, FluidStack inFluid) {
        this.result = result;
        this.catalyst = catalyst;
        this.catalystCount = catalystCount;
        this.energy = energy;
        this.inFluid = inFluid;
    }

    public static FluidConvertorRecipeBuilder fluidConvertorRecipe(
            FluidStack result, int energy,
            Ingredient catalyst, int catalystCount,
            FluidStack inFluid) {

        return new FluidConvertorRecipeBuilder(result, energy, catalyst, catalystCount, inFluid);
    }

    public void build(Consumer<IFinishedRecipe> recipe, String name) {
        recipe.accept(new FluidConvertorRecipeBuilder.Result(
                new ResourceLocation(Woot.MODID, "fluidconvertor/" + name),
                result, energy,
                catalyst, catalystCount,
                inFluid
        ));
    }

    public static class Result implements IFinishedRecipe {

        private final ResourceLocation id;
        private final Ingredient catalyst;
        private final int catalystcount;
        private final FluidStack inputFluid;
        private final FluidStack result;
        private final int energy;

        public Result(ResourceLocation id, FluidStack result, int energy, Ingredient catalyst, int catalystCount, FluidStack inputFluid) {
            this.id = id;
            this.catalyst = catalyst;
            this.catalystcount = catalystCount;
            this.inputFluid = inputFluid;
            this.result = result;
            this.energy = energy;
        }

        @Override
        public void serialize(JsonObject json) {
            json.add("catalyst", this.catalyst.serialize());
            json.addProperty("catalyst_count", this.catalystcount);
            json.add("input", FluidStackHelper.create(this.inputFluid));
            json.add("result", FluidStackHelper.create(this.result));
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

    @ObjectHolder("woot:fluidconvertor")
    public static final IRecipeSerializer<IRecipe<?>> SERIALIZER = null;
}

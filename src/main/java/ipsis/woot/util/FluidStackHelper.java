package ipsis.woot.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

public class FluidStackHelper {

    public static FluidStack parse(@Nonnull JsonObject jsonObject) {
        ResourceLocation id = new ResourceLocation(JSONUtils.getString(jsonObject, "fluid"));
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(id);
        if (fluid == null)
            throw new JsonSyntaxException("Unknown fluid");

        return new FluidStack(fluid, JSONUtils.getInt(jsonObject, "amount", 1000));
    }

    public static JsonObject create(@Nonnull FluidStack fluidStack) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("fluid", fluidStack.getFluid().getFluid().getRegistryName().toString());
        jsonObject.addProperty("amount", fluidStack.getAmount());
        return jsonObject;
    }
}

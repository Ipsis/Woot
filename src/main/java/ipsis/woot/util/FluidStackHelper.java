package ipsis.woot.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import ipsis.woot.Woot;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
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

    public static FluidStack reduceByPercentage(FluidStack fluidStack, double p) {

        p = MathHelper.clamp(p, 0.0F, 100.0F);

        Woot.setup.getLogger().debug("reduceByPercentage: {} @ {}%%",
                fluidStack.getAmount(), p);

        if (p == 0.0F || fluidStack.isEmpty())
            return fluidStack;

        if (p == 100.0F)
            return FluidStack.EMPTY;

        int reduction = (int)((fluidStack.getAmount() / 100.0F) * p);
        int left = fluidStack.getAmount() - reduction;
        fluidStack.setAmount(left);

        return fluidStack;
    }
}

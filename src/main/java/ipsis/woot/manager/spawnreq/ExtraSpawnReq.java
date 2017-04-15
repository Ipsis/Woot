package ipsis.woot.manager.spawnreq;

import com.google.gson.*;
import ipsis.woot.manager.EnumEnchantKey;
import ipsis.woot.manager.MobRegistry;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.util.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * For now items have precedence over fluid
 * Fluid can only be one fluid
 */
public class ExtraSpawnReq {

    private String wootName;
    private List<ItemStack> items;
    private List<FluidStack> fluids;
    private boolean allowEfficiency;

    public ExtraSpawnReq() {

        wootName = MobRegistry.INVALID_MOB_NAME;
        items = new ArrayList<ItemStack>();
        fluids = new ArrayList<FluidStack>();
        allowEfficiency = true;
    }


    public boolean hasItems() { return !items.isEmpty(); }
    public boolean hasFluids() { return !hasItems() && !fluids.isEmpty(); }

    public void setWootName(String wootName) {
        this.wootName = wootName;
    }

    public String getWootName() { return this.wootName; }
    public boolean getAllowEfficiency() { return this.allowEfficiency; }
    public void setAllowEfficiency(boolean allowEfficiency) { this.allowEfficiency = allowEfficiency; }

    public void addItemStack(String name, int stackSize) {

        ItemStack itemStack = ItemStackHelper.getItemStackFromName(name);
        if (itemStack != null) {
            itemStack.stackSize = stackSize;
            if (itemStack.stackSize > itemStack.getMaxStackSize())
                itemStack.stackSize = itemStack.getMaxStackSize();
            items.add(itemStack);
        } else {
            LogHelper.info("ExtraSpawnReq: Failed to convert " + name);
        }
    }

    public void addFluidStack(String name, int amount) {

        FluidStack fluidStack = FluidRegistry.getFluidStack(name, amount);
        if (fluidStack != null) {
            fluids.add(fluidStack);
        } else {
            LogHelper.info("ExtraSpawnReq: Failed to convert " + name);
        }
    }

    public List<ItemStack> getItems() {
        return items;
    }

    private List<FluidStack> getFluids() {

        if (!hasItems())
            return fluids;
        else
            return new ArrayList<>();
    }

    public FluidStack getFluid() {

        if (hasItems() || !hasFluids())
            return null;

        FluidStack f = fluids.get(0);
        if (f != null)
            return f;

        return null;
    }

    public static class ItemStackSerializer implements JsonSerializer<ItemStack> {

        @Override
        public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context) {

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("item", ItemStackHelper.getItemStackName(src));
            jsonObject.addProperty("amount", src.stackSize);
            return jsonObject;
        }
    }

    public static class FluidStackSerializer implements JsonSerializer<FluidStack> {

        @Override
        public JsonElement serialize(FluidStack src, Type typeOfSrc, JsonSerializationContext context) {

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("fluid", FluidRegistry.getFluidName(src.getFluid()));
            jsonObject.addProperty("amount", src.amount);
            return jsonObject;
        }
    }

    public static class Serializer implements JsonDeserializer<ExtraSpawnReq>, JsonSerializer<ExtraSpawnReq> {

        @Override
        public ExtraSpawnReq deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

            JsonObject jsonObject = json.getAsJsonObject();
            String name = JsonUtils.getString(jsonObject, "mob");
            boolean eff = JsonUtils.getBoolean(jsonObject, "efficiency", true);
            JsonArray jsonArray = JsonUtils.getJsonArray(jsonObject, "items");

            ExtraSpawnReq req = new ExtraSpawnReq();
            req.setWootName(name);
            req.setAllowEfficiency(eff);

            for (int i = 0; i < jsonArray.size(); i++ ) {
                JsonElement jsonElement = jsonArray.get(i);
                String itemName = JsonUtils.getString(jsonElement.getAsJsonObject(), "item");
                int amount = JsonUtils.getInt(jsonElement.getAsJsonObject(), "amount");
                req.addItemStack(itemName, amount);
            }

            jsonArray = JsonUtils.getJsonArray(jsonObject, "fluids");
            for (int i = 0; i < jsonArray.size(); i++ ) {
                JsonElement jsonElement = jsonArray.get(i);
                String fluidName = JsonUtils.getString(jsonElement.getAsJsonObject(), "fluid");
                int amount = JsonUtils.getInt(jsonElement.getAsJsonObject(), "amount");
                req.addFluidStack(fluidName, amount);
            }

            return req;
        }

        @Override
        public JsonElement serialize(ExtraSpawnReq src, Type typeOfSrc, JsonSerializationContext context) {

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("mob", src.getWootName());
            jsonObject.addProperty("efficiency", src.getAllowEfficiency());
            jsonObject.add("items", context.serialize(src.items));
            jsonObject.add("fluids", context.serialize(src.fluids));

            return jsonObject;
        }
    }

}


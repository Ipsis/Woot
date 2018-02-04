package ipsis.woot.util;

import com.google.gson.*;
import ipsis.woot.oss.LogHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nullable;

public class JsonHelper {

    private static Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public static WootMobName getWootMobName(JsonObject json) {

        String mobName = JsonUtils.getString(json, "mobName", "");
        if (mobName.equals(""))
            throw new JsonSyntaxException("Mob name cannot be empty");

        WootMobName wootMobName = WootMobNameBuilder.createFromConfigString(mobName);
        if (!wootMobName.isValid())
            LogHelper.error("Unknown mob " + mobName);

        return wootMobName;
    }

    public static JsonObject toJsonObject(ItemStack itemStack) {

        Item item = itemStack.getItem();
        ResourceLocation resourceLocation = ItemStackHelper.getItemName(item);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("item", resourceLocation.toString());

        if (item.getHasSubtypes())
            jsonObject.addProperty("data", itemStack.getMetadata());

        if (itemStack.hasTagCompound() && itemStack.getTagCompound().getSize() > 0) {
            NBTTagCompound nbt = itemStack.getTagCompound().copy();
            if (nbt.hasKey("ForgeCaps"))
                nbt.removeTag("ForgeCaps");

            jsonObject.addProperty("nbt", nbt.toString());
        }
        return jsonObject;
    }

    public static @Nullable FluidStack getFluidStack(JsonObject json) {

        String fluidName = JsonUtils.getString(json, "fluid");

        if (!FluidRegistry.isFluidRegistered(fluidName))
            return null;

        if (!json.has("mb")) {
            LogHelper.error("getFluidStack: missing mb for fluid " + fluidName);
            return null;
        }

        int mb = JsonUtils.getInt(json, "mb", 1000);
        return FluidRegistry.getFluidStack(fluidName, JsonUtils.getInt(json, "mb", 1000));
    }

    /**
     * Straight from Forge CraftingHandler but I want it to always return an ItemStack
     */
    public static ItemStack getItemStack(JsonObject json)
    {
        String itemName = JsonUtils.getString(json, "item");

        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName));

        if (item == null) {
            LogHelper.error("getItemStack: unknown item " + itemName);
            return ItemStack.EMPTY;
        }

        if (item.getHasSubtypes() && !json.has("data")) {
            LogHelper.error("getItemStack: missing data for item " + itemName);
            return ItemStack.EMPTY;
        }

        if (json.has("nbt"))
        {
            // Lets hope this works? Needs test
            try
            {
                JsonElement element = json.get("nbt");
                NBTTagCompound nbt;
                if(element.isJsonObject())
                    nbt = JsonToNBT.getTagFromJson(GSON.toJson(element));
                else
                    nbt = JsonToNBT.getTagFromJson(element.getAsString());

                NBTTagCompound tmp = new NBTTagCompound();
                if (nbt.hasKey("ForgeCaps"))
                {
                    tmp.setTag("ForgeCaps", nbt.getTag("ForgeCaps"));
                    nbt.removeTag("ForgeCaps");
                }

                tmp.setTag("tag", nbt);
                tmp.setString("id", itemName);
                tmp.setInteger("Count", JsonUtils.getInt(json, "count", 1));
                tmp.setInteger("Damage", JsonUtils.getInt(json, "data", 0));

                return new ItemStack(tmp);
            }
            catch (NBTException e)
            {
                LogHelper.error("getItemStack: invalid NBT Entry: " + e.toString());
                return ItemStack.EMPTY;
            }
        }

        return new ItemStack(item, JsonUtils.getInt(json, "count", 1), JsonUtils.getInt(json, "data", 0));
    }
}

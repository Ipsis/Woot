package ipsis.woot.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class JsonHelper {

    private static Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public static @Nonnull
    JsonObject toJsonObject(@Nonnull ItemStack itemStack) {

        JsonObject jsonObject = new JsonObject();
        Item item = itemStack.getItem();
        ResourceLocation resourceLocation = ItemStackHelper.getItemResourceLocation(item);
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
}

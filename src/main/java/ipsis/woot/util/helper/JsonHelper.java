package ipsis.woot.util.helper;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;

public class JsonHelper {

    /**
     * This is the reverse of the Forge CraftingHelper.getItemStack
     */
    public static JsonObject toJsonObject(ItemStack itemStack) {

        JsonObject json = new JsonObject();
        json.addProperty("item", itemStack.getItem().getRegistryName().toString());
        return json;
    }
}

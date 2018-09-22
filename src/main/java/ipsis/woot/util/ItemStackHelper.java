package ipsis.woot.util;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nonnull;

public class ItemStackHelper {

    public static ResourceLocation getItemResourceLocation(Item item) {
        return item == null ? null : ForgeRegistries.ITEMS.getKey(item);
    }

    public static @Nonnull String getItemMod(Item item) {

        ResourceLocation resourceLocation = getItemResourceLocation(item);
        if (resourceLocation == null)
            return "";

        return resourceLocation.getResourceDomain();
    }
}

package ipsis.woot.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CompareUtils {

    public static boolean isFromMod(WootMobName wootMobName, String mod) {

        return wootMobName.getResourceLocation().getResourceDomain().equalsIgnoreCase(mod);
    }

    public static boolean isSameMob(WootMobName wootMobName, String mob) {

        return wootMobName.getName().equalsIgnoreCase(mob) || wootMobName.getEntityKey().equalsIgnoreCase(mob);
    }

    public static boolean isFromMod(ItemStack itemStack, String mod) {

        ResourceLocation resourceLocation = itemStack.getItem().getRegistryName();
        return resourceLocation != null &&  resourceLocation.getResourceDomain().equalsIgnoreCase(mod);
    }

    public static boolean isSameItem(ItemStack itemStackA, ItemStack itemStackB) {

        return ItemStack.areItemsEqualIgnoreDurability(itemStackA, itemStackB);
    }
}

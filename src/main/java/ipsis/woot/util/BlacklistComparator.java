package ipsis.woot.util;

import net.minecraft.item.ItemStack;

public class BlacklistComparator {

    public static boolean isSameMod(WootMobName wootMobName, String mod) {

        return wootMobName.getResourceLocation().getResourceDomain().equalsIgnoreCase(mod);
    }

    public static boolean isSameMob(WootMobName wootMobName, String mob) {

        return wootMobName.getName().equalsIgnoreCase(mob) || wootMobName.getEntityKey().equalsIgnoreCase(mob);
    }

    public static boolean isSameMod(ItemStack itemStack, String mod) {

        return itemStack.getItem().getRegistryName().getResourceDomain().equalsIgnoreCase(mod);
    }

    public static boolean isSameItem(ItemStack itemStackA, ItemStack itemStackB) {

        return ItemStack.areItemsEqualIgnoreDurability(itemStackA, itemStackB);
    }
}

package ipsis.woot.util.helpers;

import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.FakeMobKeyFactory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nonnull;

public class ProgrammedMobHelper {

    private static final String NBT_DEATH_COUNT = "deathCount";

    private static final int FULLY_PROGRAMMED_COUNT = 1;

    /**
     * return true if the entity is programmed AND is valid
     */
    public static boolean isEntityProgrammed(ItemStack itemStack) {

        if (itemStack == null || itemStack.isEmpty() || !itemStack.hasTagCompound())
            return false;

        FakeMobKey fakeMobKey = getProgrammedEntity(itemStack);
        return fakeMobKey.isValid();
    }

    public static boolean isFullyProgrammed(ItemStack itemStack) {

        if (itemStack == null || itemStack.isEmpty())
            return false;

        return getDeathCount(itemStack) >= FULLY_PROGRAMMED_COUNT;
    }

    public static int getDeathCount(ItemStack itemStack) {

        if (itemStack == null || itemStack.isEmpty())
            return 0;

        if (!itemStack.hasTagCompound() || !itemStack.getTagCompound().hasKey(NBT_DEATH_COUNT))
            return 0;

        return itemStack.getTagCompound().getInteger(NBT_DEATH_COUNT);
    }

    public static @Nonnull FakeMobKey getProgrammedEntity(ItemStack itemStack) {

        if (itemStack == null || itemStack.isEmpty() || !itemStack.hasTagCompound())
            return new FakeMobKey();

        return FakeMobKeyFactory.createFromNBT(itemStack.getTagCompound());
    }

    public static void programEntity(ItemStack itemStack, FakeMobKey fakeMobKey) {

        if (itemStack == null || itemStack.isEmpty() || fakeMobKey == null || !fakeMobKey.isValid() || isEntityProgrammed(itemStack))
            return;

        if (!itemStack.hasTagCompound())
            itemStack.setTagCompound(new NBTTagCompound());

        FakeMobKey.writeToNBT(fakeMobKey, itemStack.getTagCompound());
    }

    public static void incrementDeathCount(ItemStack itemStack, int delta) {

        if (itemStack == null || itemStack.isEmpty())
            return;

        if (!itemStack.hasTagCompound())
            itemStack.setTagCompound(new NBTTagCompound());

        NBTTagCompound nbtTagCompound = itemStack.getTagCompound();
        int curr = 0;
        if (nbtTagCompound.hasKey(NBT_DEATH_COUNT))
            curr = nbtTagCompound.getInteger(NBT_DEATH_COUNT);

        curr += delta;
        nbtTagCompound.setInteger(NBT_DEATH_COUNT, MathHelper.clamp(curr, 0, FULLY_PROGRAMMED_COUNT));
    }

    public static @Nonnull String getItemStackDisplayName(ItemStack itemStack) {

        String displayName = "";
        if (isEntityProgrammed(itemStack)) {
            FakeMobKey fakeMobKey = getProgrammedEntity(itemStack);
            EntityEntry entityEntry = ForgeRegistries.ENTITIES.getValue(fakeMobKey.getResourceLocation());
            if (entityEntry != null)
                displayName =  "entity." + entityEntry.getName() + ".name";
        }

        return displayName;

    }
}

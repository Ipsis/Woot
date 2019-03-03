package ipsis.woot.util.helper;

import ipsis.woot.util.FakeMobKey;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

public class ProgrammedMobHelper {

    private static final String NBT_DEATH_COUNT = "deathCount";

    private static final int FULLY_PROGRAMMED_COUNT = 1;

    /**
     * return true if the entity is programmed AND is valid
     */
    public static boolean isEntityProgrammed(ItemStack itemStack) {

        if (itemStack == null || itemStack.isEmpty() || !itemStack.hasTag())
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

        if (!itemStack.hasTag() || !itemStack.getTag().hasKey(NBT_DEATH_COUNT))
            return 0;

        return itemStack.getTag().getInt(NBT_DEATH_COUNT);
    }

    public static @Nonnull
    FakeMobKey getProgrammedEntity(ItemStack itemStack) {

        if (itemStack == null || itemStack.isEmpty() || !itemStack.hasTag())
            return new FakeMobKey();

        return FakeMobKeyHelper.createFromNBT(itemStack.getTag());
    }

    public static void programEntity(ItemStack itemStack, FakeMobKey fakeMobKey) {

        if (itemStack == null || itemStack.isEmpty() || fakeMobKey == null || !fakeMobKey.isValid() || isEntityProgrammed(itemStack))
            return;

        if (!itemStack.hasTag())
            itemStack.setTag(new NBTTagCompound());

        FakeMobKey.writeToNBT(fakeMobKey, itemStack.getTag());
    }

    public static void incrementDeathCount(ItemStack itemStack, int delta) {

        if (itemStack == null || itemStack.isEmpty())
            return;

        if (!itemStack.hasTag())
            itemStack.setTag(new NBTTagCompound());

        NBTTagCompound nbtTagCompound = itemStack.getTag();
        int curr = 0;
        if (nbtTagCompound.hasKey(NBT_DEATH_COUNT))
            curr = nbtTagCompound.getInt(NBT_DEATH_COUNT);

        curr += delta;
        nbtTagCompound.setInt(NBT_DEATH_COUNT, MathHelper.clamp(curr, 0, FULLY_PROGRAMMED_COUNT));
    }

    public static @Nonnull String getItemStackDisplayName(ItemStack itemStack) {

        String displayName = "";
        if (isEntityProgrammed(itemStack)) {
            FakeMobKey fakeMobKey = getProgrammedEntity(itemStack);
            EntityType entityType = ForgeRegistries.ENTITIES.getValue(fakeMobKey.getResourceLocation());
            if (entityType != null)
                displayName =  entityType.getTranslationKey();
        }

        return displayName;

    }
}

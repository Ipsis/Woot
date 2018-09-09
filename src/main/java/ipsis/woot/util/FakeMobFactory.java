package ipsis.woot.util;

import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;

import static ipsis.woot.util.FakeMob.NBT_FAKE_MOB_NAME;

public class FakeMobFactory {

    public static @Nonnull
    FakeMob create(String entityName, String displayName) {

        return new FakeMob(FakeMobKeyFactory.createFromString(entityName), displayName);
    }

    public static @Nonnull
    FakeMob create(@Nonnull EntityLiving entityLiving) {

        return new FakeMob(FakeMobKeyFactory.createFromEntity(entityLiving));
    }

    public static @Nonnull FakeMob createFromNBT(NBTTagCompound tagCompound) {

        if (tagCompound == null || !tagCompound.hasKey(NBT_FAKE_MOB_NAME))
            return new FakeMob();

        FakeMobKey fakeMobKey = FakeMobKeyFactory.createFromNBT(tagCompound);
        return new FakeMob(fakeMobKey, tagCompound.getString(NBT_FAKE_MOB_NAME));
    }
}

package ipsis.woot.util;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.regex.Pattern;

import static ipsis.woot.util.FakeMobKey.NBT_FAKE_MOB_KEY_ENTITY;
import static ipsis.woot.util.FakeMobKey.NBT_FAKE_MOB_KEY_TAG;

public class FakeMobKeyFactory {

    /**
     * Create a new FakeMobKey from an existing entity
     * @param entityLiving
     * @return
     */
    public static @Nonnull FakeMobKey createFromEntity(@Nonnull EntityLiving entityLiving) {

        FakeMobKey fakeMobKey;
        ResourceLocation rl = EntityList.getKey(entityLiving);
        if (rl != null)
            fakeMobKey = new FakeMobKey(rl.toString());
        else
            fakeMobKey = new FakeMobKey();

        return fakeMobKey;
    }

    /**
     * Create a new FakeMobKey from a token string
     * The token string must be valid, but the entity does not have to be present
     * @param pattern "entity" or "entity,key"
     */
    public static @Nonnull FakeMobKey createFromPattern(@Nonnull String pattern) {

       FakeMobKey fakeMobKey;
       String[] parts = pattern.split(Pattern.quote(","));
       if (parts.length == 1)
           fakeMobKey = new FakeMobKey(parts[0]);
       else if (parts.length == 2)
           fakeMobKey = new FakeMobKey(parts[0], parts[1]);
       else
           fakeMobKey = new FakeMobKey();

       return fakeMobKey;
    }

    public static @Nonnull FakeMobKey createFromString(@Nonnull String entityName) {

        return new FakeMobKey(entityName);
    }

    public static @Nonnull FakeMobKey createFromNBT(NBTTagCompound tagCompound) {

        if (tagCompound == null || !tagCompound.hasKey(NBT_FAKE_MOB_KEY_ENTITY) || !tagCompound.hasKey(NBT_FAKE_MOB_KEY_TAG))
            return new FakeMobKey();

        return new FakeMobKey(tagCompound.getString(NBT_FAKE_MOB_KEY_ENTITY), tagCompound.getString(NBT_FAKE_MOB_KEY_TAG));
    }
}

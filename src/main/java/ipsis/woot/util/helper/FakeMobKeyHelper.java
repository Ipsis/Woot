package ipsis.woot.util.helper;

import ipsis.woot.util.FakeMobKey;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.regex.Pattern;

import static ipsis.woot.util.FakeMobKey.NBT_FAKE_MOB_KEY_ENTITY;
import static ipsis.woot.util.FakeMobKey.NBT_FAKE_MOB_KEY_TAG;

public class FakeMobKeyHelper {

    /**
     * Create a new FakeMobKey from an existing EntityLiving
     * @return valid FakeMobKey or invalid FakeMobKey if EntityLiving not present in the EntityList
     */
    public static @Nonnull
    FakeMobKey createFromEntity(@Nonnull EntityLiving entityLiving) {

        FakeMobKey fakeMobKey;
        ResourceLocation rl = ForgeRegistries.ENTITIES.getKey(entityLiving.getType());
        if (rl != null)
            fakeMobKey = new FakeMobKey(rl.toString());
        else
            fakeMobKey = new FakeMobKey();

        return fakeMobKey;
    }

    /**
     * Create a new FakeMobKey from a token string
     * No check is made against the EntityList
     * @param entityName "entity" or "entity,key"
     * @return valid FakeMobKey or invalid FakeMobKey if pattern not valid
     */
    public static @Nonnull FakeMobKey createFromString(@Nonnull String entityName) {

        FakeMobKey fakeMobKey;
        String[] parts = entityName.split(Pattern.quote(","));
        if (parts.length == 1)
            fakeMobKey = new FakeMobKey(parts[0]);
        else if (parts.length == 2)
            fakeMobKey = new FakeMobKey(parts[0], parts[1]);
        else
            fakeMobKey = new FakeMobKey();

        return fakeMobKey;
    }

    /**
     * Create a new FakeMobKey from NBT data
     * @return valid FakeMobKey or invalid FakeMobKey if NBT not present
     */
    public static @Nonnull FakeMobKey createFromNBT(NBTTagCompound tagCompound) {

        if (tagCompound == null || !tagCompound.hasKey(NBT_FAKE_MOB_KEY_ENTITY) || !tagCompound.hasKey(NBT_FAKE_MOB_KEY_TAG))
            return new FakeMobKey();

        return new FakeMobKey(tagCompound.getString(NBT_FAKE_MOB_KEY_ENTITY), tagCompound.getString(NBT_FAKE_MOB_KEY_TAG));
    }
}

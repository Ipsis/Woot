package ipsis.woot.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

/**
 * This class represents a Minecraft mob that can be spawned in a Woot factory.
 * Each mob is stored as its "mod:mob[,tag]" name eg. "minecraft:creeper"
 * A tag is also allowed to be used to add extra information. This used to be used for the Wither skeleton
 * which wasn't a unique entity so became "minecraft:skeleton,wither".
 *
 * There is no guarantee that the FakeMobKey is a valid entity, as that depends on the main EntityList.
 */
public class FakeMobKey {

    private static final String INVALID_ENTITY_KEY = "INVALID";
    private static final String EMPTY_TAG = "";

    private String entityKey;
    private String tag;
    private String name;

    public FakeMobKey() {

        this(INVALID_ENTITY_KEY, EMPTY_TAG);
    }

    public FakeMobKey(String entityKey) {

        this(entityKey, EMPTY_TAG);
    }

    public FakeMobKey(String entityKey, String tag) {

        setInfo(entityKey, tag);
    }

    public FakeMobKey(NBTTagCompound tagCompound) {

        this();
        if (tagCompound != null && tagCompound.hasKey(NBT_FAKE_MOB_KEY_ENTITY) && tagCompound.hasKey(NBT_FAKE_MOB_KEY_TAG))
            setInfo(tagCompound.getString(NBT_FAKE_MOB_KEY_ENTITY), tagCompound.getString(NBT_FAKE_MOB_KEY_TAG));
    }

    private void setInfo(String entityKey, String tag) {
        this.entityKey = entityKey;
        this.tag = tag;
        this.name = entityKey;
        if (!this.tag.equalsIgnoreCase(EMPTY_TAG))
            this.name += "," + this.tag;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getEntityKey() { return entityKey; }
    public String getTag() { return tag; }
    public String getName() { return name; }

    /**
     * This does not guarantee that an entity can be created from this FakeMobKey
     */
    public boolean isValid() { return !this.entityKey.equals(INVALID_ENTITY_KEY); }

    public @Nonnull
    ResourceLocation getResourceLocation() { return new ResourceLocation(entityKey); }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        FakeMobKey that = (FakeMobKey)obj;

        if (!entityKey.equalsIgnoreCase(that.entityKey) && !tag.equalsIgnoreCase(that.tag))
            return false;

        return name.equalsIgnoreCase(that.name);
    }

    @Override
    public int hashCode() {

        int result = entityKey.hashCode();
        result = 31 * result + tag.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    public static final String NBT_FAKE_MOB_KEY_ENTITY = "fakeMobKeyEntity";
    public static final String NBT_FAKE_MOB_KEY_TAG = "fakeMobKeyTag";
    public static void writeToNBT(@Nonnull FakeMobKey fakeMobKey, NBTTagCompound tagCompound) {

        if (fakeMobKey.isValid()) {

            if (tagCompound == null)
                tagCompound = new NBTTagCompound();

            tagCompound.setString(NBT_FAKE_MOB_KEY_ENTITY, fakeMobKey.getEntityKey());
            tagCompound.setString(NBT_FAKE_MOB_KEY_TAG, fakeMobKey.getTag());
        }
    }

    /**
     * Checks to see if the key represents a valid entity in the entity list.
     * NB the entity list contains mobs and other world entities such as lightning bolts
     * @return true if the key is in the EntityList
     */
    public static boolean isInEntityList(FakeMobKey fakeMobKey) {
        return ForgeRegistries.ENTITIES.containsKey(fakeMobKey.getResourceLocation());
    }

}

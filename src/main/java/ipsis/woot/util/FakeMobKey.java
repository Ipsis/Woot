package ipsis.woot.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

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
    public boolean isValid() { return !this.entityKey.equals(INVALID_ENTITY_KEY); }

    public ResourceLocation getResourceLocation() { return new ResourceLocation(entityKey); }

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

}

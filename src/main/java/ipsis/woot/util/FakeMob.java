package ipsis.woot.util;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.Objects;

public class FakeMob {

    private static final String INVALID_ENTITY_KEY = "INVALID";
    private static final String EMPTY_TAG = "";

    private String entityKey;
    private String tag;
    private String name;

    public FakeMob() {
        this(INVALID_ENTITY_KEY, EMPTY_TAG);
    }

    public FakeMob(String entityKey) {
        this(entityKey, EMPTY_TAG);
    }

    public FakeMob(String entityKey, String tag) {
        setInfo(entityKey, tag);
    }

    public FakeMob(FakeMob fakeMob) {
        this(fakeMob.getEntityKey(), fakeMob.getTag());
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

    public @Nonnull
    ResourceLocation getResourceLocation() { return new ResourceLocation(entityKey); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FakeMob fakeMob = (FakeMob) o;
        return entityKey.equalsIgnoreCase(fakeMob.entityKey) &&
                tag.equalsIgnoreCase(fakeMob.tag) &&
                name.equalsIgnoreCase(fakeMob.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityKey, tag, name);
    }

    /**
     * Does not guarantee that an entity can be created from this entry
     */
    public boolean isValid() { return !this.entityKey.equalsIgnoreCase(INVALID_ENTITY_KEY); }

    /**
     * Checks to see if the key represents a valid entity in the entity list.
     * NB the entity list contains mobs and other world entities such as lightning bolts
     * @return true if the key is in the EntityList
     */
    public static boolean isInEntityList(FakeMob fakeMob) {
        return ForgeRegistries.ENTITIES.containsKey(fakeMob.getResourceLocation());
    }

    private static final String KEY_ENTITY = "keyEntity";
    private static final String KEY_TAG = "keyTag";
    public static void writeToNBT(@Nonnull FakeMob fakeMob, CompoundNBT nbtTagCompound) {
        if (fakeMob.isValid()) {
            if (nbtTagCompound == null)
                nbtTagCompound = new CompoundNBT();

            nbtTagCompound.putString(KEY_ENTITY, fakeMob.entityKey);
            nbtTagCompound.putString(KEY_TAG, fakeMob.tag);
        }
    }

    public FakeMob(CompoundNBT nbtTagCompound) {
        this();
        if (nbtTagCompound != null && nbtTagCompound.contains(KEY_ENTITY) && nbtTagCompound.contains(KEY_TAG))
            setInfo(nbtTagCompound.getString(KEY_ENTITY), nbtTagCompound.getString(KEY_TAG));
    }
}

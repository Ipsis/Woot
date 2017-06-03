package ipsis.woot.tileentity.ng;

import ipsis.woot.reference.Reference;
import net.minecraft.util.ResourceLocation;

public class WootMobName {

    public String getEntityKey() {
        return entityKey;
    }

    private String entityKey = "INVALID";

    public String getTag() {
        return tag;
    }

    private String tag = "none";

    public String getName() {
        return name;
    }

    private String name;

    public WootMobName() {

        this.name = Reference.MOD_ID + ":" + this.tag + ":" + this.entityKey;
    }

    public WootMobName(String entityKey, String tag) {

        this.entityKey = entityKey;
        this.tag = tag;
        this.name = Reference.MOD_ID + ":" + this.tag + ":" + this.entityKey;
    }

    public boolean isValid() {

        return !this.entityKey.equals("INVALID");
    }

    public ResourceLocation getResourceLocation() {

        return new ResourceLocation(entityKey);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        WootMobName that = (WootMobName)o;

        if (!entityKey.equalsIgnoreCase(that.entityKey))
            return false;

        if (!tag.equalsIgnoreCase(that.tag))
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
}

package ipsis.woot.util;

import ipsis.woot.plugins.thauncraft.Thaumcraft;
import net.minecraft.util.ResourceLocation;

public class WootMobName {

    public String getEntityKey() {
        return entityKey;
    }

    private String entityKey = "INVALID";

    public String getTag() {
        return tag;
    }

    private String tag = "";

    public String getName() {
        return name;
    }

    private String name;

    private void createName() {

        if (tag.equals(""))
            this.name = this.entityKey;
        else
            this.name = this.entityKey + "," + this.tag;

    }

    public WootMobName() {

        createName();
    }

    @Override
    public String toString() {

        return this.name;
    }

    public WootMobName(String entityKey, String tag) {

        this.entityKey = entityKey;
        this.tag = tag;
        createName();
    }

    public WootMobName(String entityKey) {

        this.entityKey = entityKey;
        this.tag = "";
        createName();
    }

    public boolean isValid() {

        return !this.entityKey.equals("INVALID");
    }

    public boolean isEnderDragon() {

        return this.entityKey.equalsIgnoreCase("minecraft:ender_dragon");
    }

    public boolean isChaosGuardian() {

        return this.entityKey.equalsIgnoreCase("draconicevolution:chaosguardian");
    }

    public boolean isThaumcraftWisp() {

        return this.entityKey.equalsIgnoreCase(Thaumcraft.getWispName());
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

        if (!tag.equals("") && !tag.equalsIgnoreCase(that.tag))
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

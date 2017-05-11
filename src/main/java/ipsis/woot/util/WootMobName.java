package ipsis.woot.util;

import ipsis.woot.reference.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is the key that most of the mod uses.
 * woot:extra:mcname
 */
public class WootMobName {

    private String mcName;
    private String tag;

    private WootMobName() { tag = "none"; mcName = "minecraft:pig"; };

    public static WootMobName createFromNbt(NBTTagCompound nbtTagCompound) {

        WootMobName c = new WootMobName();

        if (nbtTagCompound.hasKey("mcName") && nbtTagCompound.hasKey("tag")) {
            c.mcName = nbtTagCompound.getString("mcName");
            c.tag = nbtTagCompound.getString("tag");

            if (EntityList.isRegistered(c.getResourceLocation()))
                return c;
        }

        return null;
    }

    public void writeToNbt(NBTTagCompound nbtTagCompound) {

        nbtTagCompound.setString("mcName", mcName);
        nbtTagCompound.setString("tag", tag);
    }

    public static WootMobName createFromEntity(Entity entity) {

        WootMobName c = new WootMobName();

        ResourceLocation resourceLocation = EntityList.getKey(entity);
        if (resourceLocation == null)
            c = null;
        else
            c.mcName = resourceLocation.toString();

        return c;
    }

    @Nullable
    public static WootMobName createFromString(String name) {

        WootMobName c = new WootMobName();

        Pattern pattern = Pattern.compile("(\\w*):(\\w*):(.*)");
        Matcher matcher = pattern.matcher(name);

        if (!matcher.find())
            return null;

        if (matcher.groupCount() != 3)
            return null;

        if (!matcher.group(1).equalsIgnoreCase(Reference.MOD_ID))
            return null;

        c.tag = matcher.group(2);
        c.mcName = matcher.group(3);
        return c;
    }

    public ResourceLocation getResourceLocation() {

        return new ResourceLocation(mcName);
    }

    @Override
    public String toString() {

        return Reference.MOD_ID + ":" + tag + ":" + mcName;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this)
            return true;

        if (!(obj instanceof WootMobName))
            return false;

        WootMobName wootMobName = (WootMobName)obj;

        return wootMobName.tag.equalsIgnoreCase(this.tag) && wootMobName.mcName.equalsIgnoreCase(this.mcName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(tag, mcName);
    }
}

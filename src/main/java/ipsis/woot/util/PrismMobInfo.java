package ipsis.woot.util;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class PrismMobInfo {

    private static final String NBT_WOOT = "wootName";
    private static final String NBT_NAME = "name";

    private WootMobName wootMobName;
    private String entityName;

    private PrismMobInfo() { };


    public void writeToMbt(NBTTagCompound nbt) {

        nbt.setString(NBT_NAME, entityName);
    }

    public static PrismMobInfo createFromEntity(EntityLiving entityLiving) {

        PrismMobInfo prismMobInfo = new PrismMobInfo();
        prismMobInfo.entityKey = EntityList.getKey(entityLiving).toString();
        prismMobInfo.entityName = entityLiving.getName();
        return prismMobInfo;
    }

    public static PrismMobInfo createFromNbt(NBTTagCompound nbt) {

        if (nbt == null)
            return null;

        PrismMobInfo prismMobInfo = new PrismMobInfo();
        if (nbt.hasKey(NBT_KEY))
            prismMobInfo.entityKey = nbt.getString(NBT_KEY);
        else
            prismMobInfo.entityKey = "minecraft:pig";

        if (nbt.hasKey(NBT_NAME))
            prismMobInfo.entityName = nbt.getString(NBT_NAME);
        else
            prismMobInfo.entityName = prismMobInfo.entityKey;

        if (EntityList.isRegistered(prismMobInfo.getResourceLocation()))
            return prismMobInfo;

        return null;
    }

    public static PrismMobInfo createFromText(String key, String name) {

        PrismMobInfo prismMobInfo = new PrismMobInfo();
        prismMobInfo.entityName = name;
        prismMobInfo.entityKey = key;

        if (EntityList.isRegistered(prismMobInfo.getResourceLocation()))
            return prismMobInfo;

        return null;
    }

    public String getEntityKey() { return entityKey; }
    public String getEntityName() { return  entityName; }
    public ResourceLocation getResourceLocation() { return new ResourceLocation(entityKey); }
}

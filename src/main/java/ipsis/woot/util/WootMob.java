package ipsis.woot.util;

import ipsis.woot.manager.ConfigManager;
import ipsis.woot.reference.Reference;
import ipsis.woot.tileentity.ng.configuration.EnumConfigKey;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.List;

public class WootMob {

    private WootMobName wootMobName;
    private String displayName;
    private int deathCount;

    private static List<String> internalMobBlacklist = new ArrayList<>();
    private static List<String> internalModBlacklist = new ArrayList<>();

    private static List<String> modList = new ArrayList<>();
    private static List<String> mobList = new ArrayList<>();

    public static final String NBT_NAME= "wootKey";
    private static final String NBT_TAG= "wootTag";
    private static final String NBT_DISPLAY= "displayName";
    private static final String NBT_DEATHS = "deathCount";

    private WootMob() {
        wootMobName = null;
        displayName = "Unknown Mob";
        deathCount = 0;
    }

    public static void addToInternalMobBlacklist(String mobName) {

        internalMobBlacklist.add(mobName.toLowerCase());
    }

    public static void addToInternalModBlacklist(String modName) {

        internalModBlacklist.add(modName.toLowerCase());
    }

    public static void addToMobList(String mobName) {

        mobList.add(mobName.toLowerCase());
    }

    public static void addToModList(String modName) {

        modList.add(modName.toLowerCase());
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {

        if (nbt == null)
            nbt = new NBTTagCompound();

        nbt.setString(NBT_NAME, wootMobName.getKey());
        nbt.setString(NBT_TAG, wootMobName.getTag());
        nbt.setString(NBT_DISPLAY, displayName);
        nbt.setInteger(NBT_DEATHS, deathCount);

        return nbt;
    }

    public static WootMob createFromNBT(NBTTagCompound nbt) {

        if (nbt == null)
            return null;

        if (!nbt.hasKey(NBT_NAME) || !nbt.hasKey(NBT_TAG)) {
            return null;
        }

        WootMob wootMob = new WootMob();
        String name = Reference.MOD_ID + ":" + nbt.getString(NBT_TAG) + ":" + nbt.getString(NBT_NAME);
        wootMob.wootMobName = WootMobName.createFromString(name);
        if (wootMob.wootMobName == null)
                return null;

        if (nbt.hasKey(NBT_DISPLAY))
            wootMob.displayName = nbt.getString(NBT_DISPLAY);

        if (nbt.hasKey(NBT_DEATHS))
            wootMob.deathCount = nbt.getInteger(NBT_DEATHS);

        return wootMob;
    }

    public static WootMob createFromEntity(EntityLivingBase entityLivingBase) {

        if (entityLivingBase == null)
            return null;

        WootMob wootMob = new WootMob();
        wootMob.wootMobName = WootMobName.createFromEntity(entityLivingBase);
        if (wootMob.wootMobName == null)
            return null;

        wootMob.displayName = entityLivingBase.getName();

        return  wootMob;
    }

    public static boolean canCapture(EntityLivingBase entityLivingBase) {

        WootMobName wootMobName = WootMobName.createFromEntity(entityLivingBase);
        return canCapture(wootMobName);
    }

    public static boolean canCapture(WootMobName wootMobName) {

        if (wootMobName == null)
            return false;

        for (String s : internalModBlacklist)
            if (wootMobName.toString().toLowerCase().contains(s.toLowerCase()))
                return false;

        for (String s : internalMobBlacklist)
            if (wootMobName.toString().toLowerCase().equals(s))
                return false;

        if (ConfigManager.instance().getBoolean(EnumConfigKey.MOB_WHITELIST)) {

            // Is it on the mob whitelist
            for (String s : mobList) {
                if (wootMobName.toString().toLowerCase().equals(s))
                    return true;
            }
        } else {

            // Is it on the mob blacklist
            for (String s : mobList) {
                if (wootMobName.toString().toLowerCase().equals(s))
                    return false;
            }
        }

        return true;
    }

    public String getDisplayName() { return displayName; }
    public int getDeathCount() { return deathCount; }
    public WootMobName getWootMobName() { return wootMobName; }

    public void incrementDeathCount(int count) {

        // TODO dont use 1
        deathCount += count;
        deathCount = MathHelper.clamp(deathCount, 0, 1);
    }
}

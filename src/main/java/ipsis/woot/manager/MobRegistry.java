package ipsis.woot.manager;

import ipsis.Woot;
import ipsis.oss.LogHelper;
import ipsis.woot.reference.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MobRegistry {

    public static final String INVALID_MOB_NAME = "InvalidMob";
    HashMap<String, MobInfo> mobInfoHashMap = new HashMap<String, MobInfo>();
    List<String> mobBlacklist = new ArrayList<String>();

    public static String getMcName(String wootName) {

        // Woot:tag:mcname
        return wootName.substring(wootName.lastIndexOf(':') + 1);
    }

    public boolean isValidMobName(String mobName) {

        return mobName != null && !mobName.equals(INVALID_MOB_NAME) && !mobName.equals("");
    }

    public String createWootName(EntityLiving entityLiving) {

        String name = EntityList.getEntityString(entityLiving);
        if (entityLiving instanceof EntitySkeleton) {
            if (((EntitySkeleton) entityLiving).getSkeletonType() == 1)
                name = "wither:" + name;
            else
                name = "none:" + name;
        } else {
            name = "none:" + name;
        }

        // Name is of the form "Woot:<tag>:mcname"
        return  Reference.MOD_ID + ":" + name;
    }

    public String onEntityLiving(EntityLiving entityLiving) {

        // blacklist is based off Minecraft names
        if (mobBlacklist.contains(EntityList.getEntityString(entityLiving)))
            return INVALID_MOB_NAME;

        String wootName = createWootName(entityLiving);
        if (!mobInfoHashMap.containsKey(wootName)) {
            MobInfo info = new MobInfo(wootName, entityLiving.getName());
            mobInfoHashMap.put(wootName, info);
        } else {
            MobInfo mobInfo = mobInfoHashMap.get(wootName);
            if (mobInfo.displayName.equals(INVALID_MOB_NAME))
                mobInfo.displayName = entityLiving.getName();
        }

        return wootName;
    }

    public void addMapping(String wootName, int xp) {

        if (!mobInfoHashMap.containsKey(wootName)) {
            MobInfo info = new MobInfo(wootName, xp);
            mobInfoHashMap.put(wootName, info);
        } else {
            mobInfoHashMap.get(wootName).setXp(xp);
        }
    }

    public boolean isKnown(String wootName) {

        return mobInfoHashMap.containsKey(wootName);
    }

    void extraEntitySetup(MobInfo mobInfo, Entity entity) {

        if (isWitherSkeleton(mobInfo.wootMobName, entity)) {
            ((EntitySkeleton) entity).setSkeletonType(1);
        } else if (isSlime(mobInfo.wootMobName, entity)) {
            if (((EntitySlime)entity).getSlimeSize() != 1)
                ((EntitySlime)entity).setSlimeSize(1);
        }
    }

    boolean isWitherSkeleton(String wootName, Entity entity) {

        if (entity instanceof EntitySkeleton)
            return wootName.equals(Reference.MOD_ID + ":" + "wither:Skeleton");
        return false;
    }

    boolean isSlime(String wootName, Entity entity) {

        if (entity instanceof EntitySlime)
            return wootName.equals(Reference.MOD_ID + ":" + "none:Slime");
        return false;
    }

    public Entity createEntity(String wootName, World world) {

        if (!isKnown(wootName))
            addMapping(wootName, -1);

        Entity entity = EntityList.createEntityByName(mobInfoHashMap.get(wootName).getMcMobName(), world);
        if (entity != null)
            extraEntitySetup(mobInfoHashMap.get(wootName), entity);

        return entity;
    }

    public boolean hasXp(String wootName) {

        if (mobInfoHashMap.containsKey(wootName))
            return mobInfoHashMap.get(wootName).hasXp();

        return false;
    }

    public int getSpawnXp(String wootName) {

        if (mobInfoHashMap.containsKey(wootName))
            return mobInfoHashMap.get(wootName).getSpawnXp();

        return 1;
    }

    public int getDeathXp(String wootName) {

        if (mobInfoHashMap.containsKey(wootName))
            return mobInfoHashMap.get(wootName).getDeathXp();

        return 1;
    }

    public String getDisplayName(String wootName) {

        if (mobInfoHashMap.containsKey(wootName))
            return mobInfoHashMap.get(wootName).getDisplayName();

        return INVALID_MOB_NAME;
    }

    public class MobInfo {

        String wootMobName;
        String mcMobName;
        String displayName;
        int spawnXp;
        int deathXp;

        private MobInfo() { }

        public MobInfo(String wootMobName, int mobXp) {

           this(wootMobName, INVALID_MOB_NAME, mobXp);
        }

        public MobInfo(String wootMobName, String displayName) {

            this(wootMobName, displayName, -1);
        }

        public MobInfo(String wootMobName, String displayName, int mobXp) {

            this.wootMobName = wootMobName;
            this.mcMobName = MobRegistry.getMcName(wootMobName);
            this.displayName = displayName;
            setXp(mobXp);
        }

        public String getMcMobName() { return mcMobName; }
        public String getDisplayName() { return displayName; }
        public int getSpawnXp() { return spawnXp; }
        public int getDeathXp() { return deathXp; }
        public boolean hasXp() { return spawnXp != -1 && deathXp != -1; }

        public void setXp(int mobXp) {

            if (mobXp == 0) {
                this.spawnXp = 1;
                this.deathXp = 0;
            } else {
                this.spawnXp = mobXp;
                this.deathXp = mobXp;
            }
        }
    }
}

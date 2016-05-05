package ipsis.woot.manager;

import ipsis.woot.oss.LogHelper;
import ipsis.woot.reference.Reference;
import ipsis.woot.reference.Settings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Method;
import java.util.HashMap;


public class MobRegistry {

    public static final String INVALID_MOB_NAME = "InvalidMob";
    HashMap<String, MobInfo> mobInfoHashMap = new HashMap<String, MobInfo>();

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

    public String createWootName(String name) {

        return Reference.MOD_ID + ":" + name;
    }

    private String createDisplayName(EntityLiving entityLiving) {

        if (entityLiving instanceof  EntitySkeleton) {
            if (((EntitySkeleton) entityLiving).getSkeletonType() == 1)
                return I18n.translateToLocal("entity.Woot:witherskelly.name");
            else
                return entityLiving.getName();
        } else {
            return entityLiving.getName();
        }
    }

    public String onEntityLiving(EntityLiving entityLiving) {

        // blacklist is based off Minecraft names
        String name = EntityList.getEntityString(entityLiving);

        // Remove this always
        if (name.equals("EnderDragon"))
            return INVALID_MOB_NAME;

        for (int i = 0; i < Settings.prismBlacklist.length; i++) {
            if (Settings.prismBlacklist[i].equals(name))
                return INVALID_MOB_NAME;
        }

        String wootName = createWootName(entityLiving);
        String displayName = createDisplayName(entityLiving);
        if (!mobInfoHashMap.containsKey(wootName)) {
            MobInfo info = new MobInfo(wootName, displayName);
            mobInfoHashMap.put(wootName, info);
        } else {
            MobInfo mobInfo = mobInfoHashMap.get(wootName);
            if (mobInfo.displayName.equals(INVALID_MOB_NAME))
                mobInfo.displayName = displayName;
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
                setSlimeSize((EntitySlime)entity, 1);
        } else if (isMagmaCube(mobInfo.wootMobName, entity)) {
            if (((EntitySlime)entity).getSlimeSize() == 1)
                setSlimeSize((EntitySlime)entity, 2);
        }
    }

    private void setSlimeSize(EntitySlime entitySlime, int size) {

        String[] methodNames = new String[]{ "func_70799_a", "setSlimeSize" };

        try {
            Method m = ReflectionHelper.findMethod(EntitySlime.class, null, methodNames, int.class);
            m.invoke(entitySlime, size);
        } catch (Throwable e){
            LogHelper.warn("Reflection EntitySlime.setSlimeSize failed");
        }
    }

    boolean isWitherSkeleton(String wootName, Entity entity) {

        return entity instanceof EntitySkeleton && wootName.equals(Reference.MOD_ID + ":" + "wither:Skeleton");
    }

    boolean isSlime(String wootName, Entity entity) {

        return entity instanceof EntitySlime && wootName.equals(Reference.MOD_ID + ":" + "none:Slime");
    }

    boolean isMagmaCube(String wootName, Entity entity) {

        return entity instanceof EntityMagmaCube && wootName.equals(Reference.MOD_ID + ":" + "none:LavaSlime");
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

        return mobInfoHashMap.containsKey(wootName) && mobInfoHashMap.get(wootName).hasXp();

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

        public final static int MIN_XP_VALUE = 1;

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
                this.spawnXp = MIN_XP_VALUE;
                this.deathXp = 0;
            } else {
                this.spawnXp = mobXp;
                this.deathXp = mobXp;
            }
        }
    }
}

/**
 * Reference amounts:
 *
 * EntityMob: 5
 *
 * EntityWither: 50
 * EntityBlaze: 10
 * EntityEndermite: 3
 * EntityGhast: 5
 * EntityGuardian: 10
 */

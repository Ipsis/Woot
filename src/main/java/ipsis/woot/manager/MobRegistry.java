package ipsis.woot.manager;

import ipsis.Woot;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.reference.Reference;
import ipsis.woot.reference.Settings;
import ipsis.woot.util.StringHelper;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.*;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


public class MobRegistry {

    public static final String INVALID_MOB_NAME = "InvalidMob";
    public static final String ENDER_DRAGON = "Woot:none:EnderDragon";
    HashMap<String, MobInfo> mobInfoHashMap = new HashMap<String, MobInfo>();

    Map<String, Integer> mobCostMap = new TreeMap<String, Integer>(String.CASE_INSENSITIVE_ORDER);

    public static String getMcName(String wootName) {

        // Woot:tag:mcname
        return wootName.substring(wootName.lastIndexOf(':') + 1);
    }

    public boolean isValidMobName(String mobName) {

        return mobName != null && !mobName.equals(INVALID_MOB_NAME) && !mobName.equals("");
    }

    public void addCosting(String mobName, int cost) {

        if (mobName != null && cost > 0)
            mobCostMap.put(mobName, cost);
    }

    public String createWootName(EntityLiving entityLiving) {

        String name = EntityList.getEntityString(entityLiving);
        if (entityLiving instanceof EntitySkeleton) {
            if (((EntitySkeleton) entityLiving).getSkeletonType() == SkeletonType.WITHER)
                name = "wither:" + name;
            else
                name = "none:" + name;
        } else if (entityLiving instanceof EntityCreeper) {
            if (((EntityCreeper) entityLiving).getPowered() == true)
                name = "charged:" + name;
            else
                name = "none:" + name;
        } else if (entityLiving instanceof EntityGuardian) {
            if (((EntityGuardian)entityLiving).isElder() == true)
                name = "elder:" + name;
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
            if (((EntitySkeleton) entityLiving).getSkeletonType() == SkeletonType.WITHER)
                return StringHelper.localize("entity.Woot:witherskelly.name");
            else
                return entityLiving.getName();
        } else if (entityLiving instanceof EntityCreeper) {
            if (((EntityCreeper) entityLiving).getPowered() == true)
                return StringHelper.localize("entity.Woot:chargedcreeper.name");
            else
                return entityLiving.getName();
        } else if (entityLiving instanceof EntityGuardian) {
            if (((EntityGuardian)entityLiving).isElder() == true)
                return StringHelper.localize("entity.Woot:elderguardian.name");
            else
                return entityLiving.getName();
        } else {
            return entityLiving.getName();
        }
    }

    public boolean isPrismValid(String wootName) {

        if (isEnderDragon(wootName) && !Settings.allowEnderDragon)
            return false;

        if (MobBlacklist.isMobBlacklisted(wootName))
            return false;

        String[] mobList;
        if (Settings.usePrismWhitelist)
            mobList = Settings.prismWhitelist;
        else
            mobList = Settings.prismBlacklist;

        for (int i = 0; i < mobList.length; i++) {
            if (mobList[i].equalsIgnoreCase(wootName)) {
                return mobList == Settings.prismWhitelist;
            }
        }

        // not on blacklist: valid
        // not on whitelist: invalid
        return mobList == Settings.prismBlacklist;
    }

    public void cmdDumpPrism(ICommandSender sender) {

        //sender.addChatMessage(new TextComponentTranslation("commands.Woot:woot.dump.blacklist.summary", ENDER_DRAGON));
        if (Settings.usePrismWhitelist) {
            for (int i = 0; i < Settings.prismWhitelist.length; i++)
                sender.addChatMessage(new TextComponentTranslation("commands.Woot:woot.dump.prism.whitelist.summary", Settings.prismWhitelist[i]));
        } else {
            for (int i = 0; i < Settings.prismBlacklist.length; i++)
                sender.addChatMessage(new TextComponentTranslation("commands.Woot:woot.dump.prism.blacklist.summary", Settings.prismBlacklist[i]));
        }
    }

    public void cmdDumpCosts(ICommandSender sender) {

        StringBuilder sb = new StringBuilder();
        for (String name : mobCostMap.keySet())
            sender.addChatMessage(new TextComponentTranslation("commands.Woot:woot.cost.summary",
                    name, mobCostMap.get(name)));

    }

    public String onEntityLiving(EntityLiving entityLiving) {

        String name = EntityList.getEntityString(entityLiving);
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

    void extraEntitySetup(MobInfo mobInfo, Entity entity, World world) {

        if (isWitherSkeleton(mobInfo.wootMobName, entity)) {
            ((EntitySkeleton) entity).setSkeletonType(SkeletonType.WITHER);
        } else if (isSlime(mobInfo.wootMobName, entity)) {
            if (((EntitySlime)entity).getSlimeSize() != 1)
                setSlimeSize((EntitySlime)entity, 1);
        } else if (isMagmaCube(mobInfo.wootMobName, entity)) {
            if (((EntitySlime)entity).getSlimeSize() == 1)
                setSlimeSize((EntitySlime)entity, 2);
        } else if (isChargedCreeper(mobInfo.wootMobName, entity)) {
            entity.onStruckByLightning(new EntityLightningBolt(world,
                    entity.getPosition().getX(),
                    entity.getPosition().getY(),
                    entity.getPosition().getZ(), true));
        } else if (isElderGuardian(mobInfo.wootMobName, entity)) {
            ((EntityGuardian)entity).setElder(true);
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

    public static boolean isEnderDragon(String wootName) {

        return ENDER_DRAGON.equals(wootName);
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

    boolean isChargedCreeper(String wootName, Entity entity) {

        return entity instanceof EntityCreeper && wootName.equals(Reference.MOD_ID + ":" + "charged:Creeper");
    }

    boolean isElderGuardian(String wootName, Entity entity) {

        return entity instanceof EntityGuardian && wootName.equals(Reference.MOD_ID + ":" + "elder:Guardian");
    }

    public Entity createEntity(String wootName, World world) {

        if (!isKnown(wootName))
            addMapping(wootName, -1);

        Entity entity = EntityList.createEntityByName(mobInfoHashMap.get(wootName).getMcMobName(), world);
        if (entity != null)
            extraEntitySetup(mobInfoHashMap.get(wootName), entity, world);

        return entity;
    }

    public boolean hasXp(String wootName) {

        return mobInfoHashMap.containsKey(wootName) && mobInfoHashMap.get(wootName).hasXp();

    }

    public int getSpawnXp(String wootName) {

        Integer cost = Woot.mobRegistry.mobCostMap.get(wootName);
        if (cost != null)
            return cost;

        if (mobInfoHashMap.containsKey(wootName))
            return mobInfoHashMap.get(wootName).getDeathXp();

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
        public int getDeathXp() { return deathXp; }
        public boolean hasXp() { return deathXp != -1; }

        public void setXp(int mobXp) {

            if (mobXp == 0)
                this.deathXp = MIN_XP_VALUE;
            else
                this.deathXp = mobXp;
        }
    }
}

/**
 * Vanilla Mobs And Default Remapping
 *
 *
 * Tier I [Bone/Flesh]
 * ------
 *
 * Bat - 1
 * Chicken - 1
 * Cow - 1
 * Horse - 1
 * Mooshroom - 1
 * Ocelot - 1
 * Pig - 1
 * Rabbit - 1
 * Sheep - 1
 * Squid - 1
 * Villager - 1
 * Wolf - 1
 * Shulker - 5
 * Silverfish  - 5
 * Skeleton - 5
 * Slime - 4
 * Spider - 5
 * Zombie - 5
 * Cave Spider - 5
 * Creeper - 5
 * Endermite - 3
 * Giant Zombie - 5
 *
 * Tier II [Blaze]
 * -------
 *
 * Magma Cube - 2
 * Witch - 5
 * Blaze - 10
 * Ghast - 5
 * Zombie Pigman - 5
 *
 * Tier III [Enderpearl]
 * --------
 *
 * Iron Golem - 1
 * Enderman - 5
 * Guardian - 10
 * Wither Skeleton - 5
 *
 * Tier IV [Wither Star]
 * -------
 *
 * Wither - 50
 * Dragon - 12000(?)
 *
 *
 */

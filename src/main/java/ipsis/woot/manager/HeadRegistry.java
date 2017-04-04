package ipsis.woot.manager;

import ipsis.Woot;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.SkeletonType;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class HeadRegistry {

    HashMap<String, HeadConfig> headConfigHashMap = new HashMap<String, HeadConfig>();

    public void init() {

        initVanilla();
        initModded();
    }

    void initVanilla() {

        /**
         * Vanilla
         * Skull meta is in ItemSkull.java
         */
        String key;

        // EntitySkeleton.java
        key = Woot.mobRegistry.createWootName("none:Skeleton");
        headConfigHashMap.put(key, new HeadConfig(key, new ItemStack(Items.SKULL, 1, 0)));
        key = Woot.mobRegistry.createWootName("wither:Skeleton");
        headConfigHashMap.put(key, new HeadConfig(key, new ItemStack(Items.SKULL, 1, 1)));
        // EntityZombie.java
        key = Woot.mobRegistry.createWootName("none:Zombie");
        headConfigHashMap.put(key, new HeadConfig(key, new ItemStack(Items.SKULL, 1, 2)));
        // EntityCreeper.java
        key = Woot.mobRegistry.createWootName("none:Creeper");
        headConfigHashMap.put(key, new HeadConfig(key, new ItemStack(Items.SKULL, 1, 4)));
    }

    void initModded() {

        String key;

        // EnderIO Enderman SkullsA
        Item i = Item.getByNameOrId("EnderIO:blockEndermanSkull");
        if (i != null) {
            key = Woot.mobRegistry.createWootName("none:Enderman");
            headConfigHashMap.put(key, new HeadConfig(key, new ItemStack(i)));
        }

    }

    public ItemStack handleDecap(String wootMobName, EnumSpawnerUpgrade upgrade) {

        SpawnerUpgrade u = UpgradeManager.getSpawnerUpgrade(upgrade);
        if (!u.isDecapitate())
            return null;

        HeadConfig headConfig = headConfigHashMap.get(wootMobName);
        if (headConfig == null)
            return null;

        float chance = (float)u.getDecapitateChance() / 100.0F;
        if (Woot.RANDOM.nextFloat() <= chance)
            return ItemStack.copyItemStack(headConfig.headStack);

        return null;
    }

    public ItemStack getVanillaHead(EntityLiving entityLiving) {

        ItemStack itemStack = null;
        if (entityLiving instanceof EntityCreeper) {
            itemStack = new ItemStack(Items.SKULL, 1, 4);
        } else if (entityLiving instanceof EntityZombie) {
            itemStack = new ItemStack(Items.SKULL, 1, 2);
        } else if (entityLiving instanceof EntitySkeleton) {
            EntitySkeleton entitySkeleton = (EntitySkeleton)entityLiving;
            if (entitySkeleton.getSkeletonType() == SkeletonType.NORMAL) {
                itemStack = new ItemStack(Items.SKULL, 1, 0);
            } else if (entitySkeleton.getSkeletonType() == SkeletonType.WITHER) {
                itemStack = new ItemStack(Items.SKULL, 1, 1);
            }
        }

        return itemStack;
    }

    class HeadConfig {

        String wootMobName;
        ItemStack headStack;

        public HeadConfig(String wootMobName, ItemStack headStack) {

            this.wootMobName = wootMobName;
            this.headStack = headStack.copy();
        }
    }
}

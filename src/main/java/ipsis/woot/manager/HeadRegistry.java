package ipsis.woot.manager;

import ipsis.Woot;
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

        if (Woot.random.nextInt(100) + 1 > u.getDecapitateChance())
            return ItemStack.copyItemStack(headConfig.headStack);

        return null;
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

package ipsis.woot.loot.repository;

import ipsis.woot.util.EnumEnchantKey;
import ipsis.woot.util.WootMobName;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LootDrop {

    private ItemStack itemStack;
    private List<LootMob> mobs = new ArrayList<>();

    public LootDrop(ItemStack itemStack) {

        this.itemStack = itemStack;
        if (this.itemStack.isItemStackDamageable())
            this.itemStack.setItemDamage(0);
    }

    public ItemStack getItemStack() {

        return this.itemStack;
    }

    public List<LootMob> getMobs() {

        return this.mobs;
    }

    public static class LootMob {

        private WootMobName wootMobName;
        private HashMap<Integer, Integer> looting0 = new HashMap<>();
        private HashMap<Integer, Integer> looting1 = new HashMap<>();
        private HashMap<Integer, Integer> looting2 = new HashMap<>();
        private HashMap<Integer, Integer> looting3 = new HashMap<>();

        public LootMob(WootMobName wootMobName) {

            this.wootMobName = wootMobName;
        }

        public WootMobName getWootMobName() {

            return wootMobName;
        }

        public HashMap<Integer, Integer> getLooting(EnumEnchantKey key) {

            if (key == EnumEnchantKey.NO_ENCHANT)
                return looting0;
            else if (key == EnumEnchantKey.LOOTING_I)
                return looting1;
            else if (key == EnumEnchantKey.LOOTING_II)
                return looting2;
            else
                return looting3;
        }
    }
}

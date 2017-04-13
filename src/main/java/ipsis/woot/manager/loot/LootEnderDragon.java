package ipsis.woot.manager.loot;

import ipsis.Woot;
import ipsis.woot.manager.EnumEnchantKey;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.reference.Settings;
import ipsis.woot.util.ItemStackHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LootEnderDragon {

    private HashMap<EnumEnchantKey, List<DragonDrop>> dropMap;

    public LootEnderDragon() {

        dropMap = new HashMap<EnumEnchantKey, List<DragonDrop>>();

        dropMap.put(EnumEnchantKey.NO_ENCHANT, new ArrayList<DragonDrop>());
        dropMap.put(EnumEnchantKey.LOOTING_I, new ArrayList<DragonDrop>());
        dropMap.put(EnumEnchantKey.LOOTING_II, new ArrayList<DragonDrop>());
        dropMap.put(EnumEnchantKey.LOOTING_III, new ArrayList<DragonDrop>());
    }

    public void addDrops() {

        for (int i = 0; i < Settings.dragonDropList.length; i++)
            addDrop(Settings.dragonDropList[i]);
    }

    private void addDrop(String drop) {

        /**
         * enchant:item:meta:stack:chance
         */

        EnumEnchantKey key;
        String item;
        int meta;
        int stackSize;
        float chance;
        String[] parts = drop.split(",");
        if (parts.length == 4) {
            try {
                key = EnumEnchantKey.getFromString(parts[0]);
                item = parts[1];
                stackSize = Integer.parseInt(parts[2]);
                chance = Float.parseFloat(parts[3]);

                ItemStack itemStack = ItemStackHelper.getItemStackFromName(item);
                if (itemStack != null && stackSize > 0 && chance > 0.0F && chance <= 100.0F) {
                    itemStack.stackSize = stackSize;
                    LogHelper.info("Added Ender Dragon drop - " + item + "/" + stackSize + "@" + chance + "%%");
                    dropMap.get(key).add(new DragonDrop(itemStack, chance));
                }

            } catch (NumberFormatException e) {
                LogHelper.error("Invalid dragon drop - key,item,meta,stacksize,chance: " + drop);
            }

        } else {
            LogHelper.error("Invalid dragon drop - key,item,meta,stacksize,chance: " + drop);
        }

    }

    public List<ItemStack> getDrops(EnumEnchantKey key) {

        List<ItemStack> drops = new ArrayList<ItemStack>();
        for (DragonDrop i : dropMap.get(key)) {
            float chance = Woot.RANDOM.nextFloat();
            if (chance <= (i.chance / 100.0F))
                drops.add(i.itemStack.copy());
        }

        return drops;
    }

    public List<FullDropInfo> getFullDropInfo(EnumEnchantKey key) {

       List<FullDropInfo> drops = new ArrayList<FullDropInfo>();
       for (DragonDrop i : dropMap.get(key))
           drops.add(new FullDropInfo(i.itemStack.copy(), i.chance));

       return drops;
    }

    static class DragonDrop {

        float chance;
        ItemStack itemStack;

        public DragonDrop(ItemStack itemStack, float chance) {
            this.chance = chance;
            this.itemStack = itemStack.copy();
        }
    }
}

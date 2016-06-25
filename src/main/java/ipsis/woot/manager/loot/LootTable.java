package ipsis.woot.manager.loot;

import ipsis.Woot;
import ipsis.woot.manager.EnumEnchantKey;
import ipsis.woot.reference.Settings;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;

import java.util.ArrayList;
import java.util.List;

public class LootTable {

    String wootName;

    List<Drop> mobDroplist;
    List<Drop> mobDroplistLootingI;
    List<Drop> mobDroplistLootingII;
    List<Drop> mobDroplistLootingIII;

    Integer sampleSize;
    Integer sampleSizeLootingI;
    Integer sampleSizeLootingII;
    Integer sampleSizeLootingIII;

    private LootTable() { }

    public LootTable(String wootName) {

        this.wootName = wootName;

        mobDroplist = new ArrayList<Drop>();
        mobDroplistLootingI = new ArrayList<Drop>();
        mobDroplistLootingII = new ArrayList<Drop>();
        mobDroplistLootingIII = new ArrayList<Drop>();

        sampleSize = 0;
        sampleSizeLootingI = 0;
        sampleSizeLootingII = 0;
        sampleSizeLootingIII = 0;
    }

    public boolean isFull(EnumEnchantKey key) {

        int size = getSize(key);
        return size == Settings.sampleSize;
    }

    public boolean isEmpty(EnumEnchantKey key) {

        int size = getSize(key);
        return size == 0;
    }

    public void update(EnumEnchantKey key, List<EntityItem> drops) {

        List<Drop> dropList = getList(key);

        // This should NEVER happen
        if (dropList == null)
            return;

        incSize(key);

        for (EntityItem entityItem : drops) {
            ItemStack itemStack = entityItem.getEntityItem();

            boolean found = false;
            for (Drop d : dropList) {
                if (d.itemStack.isItemEqualIgnoreDurability(itemStack)) {
                    d.update(itemStack.stackSize);
                    found = true;
                }
            }

            if (!found) {
                Drop d = new Drop(itemStack);
                d.update(itemStack.stackSize);
                dropList.add(d);
            }
        }
    }

    public void dump(ICommandSender sender, EnumEnchantKey key) {

        int size = getSize(key);
        List<Drop> dropList = getList(key);

        if (dropList == null || size == 0) {
            sender.addChatMessage(new TextComponentString(String.format("Mob: %s/%s is empty", wootName, key)));
        } else {
            for (Drop d : dropList) {
               d.dump(sender, wootName, key, d.count, size);
            }
        }
    }

    private List<Drop> getList(EnumEnchantKey key) {

        List<Drop> dropList = null;
        if (key == EnumEnchantKey.NO_ENCHANT) {
            dropList = mobDroplist;
        } else if (key == EnumEnchantKey.LOOTING_I) {
            dropList = mobDroplistLootingI;
        } else if (key == EnumEnchantKey.LOOTING_II) {
            dropList = mobDroplistLootingII;
        } else if (key == EnumEnchantKey.LOOTING_III) {
            dropList = mobDroplistLootingIII;
        }

        return dropList;
    }

    private void incSize(EnumEnchantKey key) {

        if (key == EnumEnchantKey.NO_ENCHANT)
            sampleSize++;
        else if (key == EnumEnchantKey.LOOTING_I)
            sampleSizeLootingI++;
        else if (key == EnumEnchantKey.LOOTING_II)
            sampleSizeLootingII++;
        else if (key == EnumEnchantKey.LOOTING_III)
            sampleSizeLootingIII++;
    }

    private Integer getSize(EnumEnchantKey key) {

        Integer size = 0;
        if (key == EnumEnchantKey.NO_ENCHANT)
            size = sampleSize;
        else if (key == EnumEnchantKey.LOOTING_I)
            size = sampleSizeLootingI;
        else if (key == EnumEnchantKey.LOOTING_II)
            size = sampleSizeLootingII;
        else if (key == EnumEnchantKey.LOOTING_III)
            size = sampleSizeLootingIII;

        return size;
    }

    public void getLoot(EnumEnchantKey key, List<ItemStack> loot) {

        List<Drop> dropList = getList(key);
        int size = getSize(key);

        if (dropList == null || size == 0)
            return;

        for (Drop drop : dropList) {
            float chance = Woot.lootPool.RAND.nextFloat();
            if (chance <= drop.getChance(size)) {
                ItemStack dropStack = ItemStack.copyItemStack(drop.itemStack);
                dropStack.stackSize = drop.getWeightedSize();
                loot.add(dropStack);
            }
        }
    }
}

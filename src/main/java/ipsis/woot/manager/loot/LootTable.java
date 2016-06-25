package ipsis.woot.manager.loot;

import ipsis.woot.manager.EnumEnchantKey;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;

import java.util.ArrayList;
import java.util.List;

public class LootTable {

    String wootName;

    List<MobDrop> mobDroplist;
    List<MobDrop> mobDroplistLootingI;
    List<MobDrop> mobDroplistLootingII;
    List<MobDrop> mobDroplistLootingIII;

    Integer sampleSize;
    Integer sampleSizeLootingI;
    Integer sampleSizeLootingII;
    Integer sampleSizeLootingIII;

    private LootTable() { }

    public LootTable(String wootName) {

        this.wootName = wootName;

        mobDroplist = new ArrayList<MobDrop>();
        mobDroplistLootingI = new ArrayList<MobDrop>();
        mobDroplistLootingII = new ArrayList<MobDrop>();
        mobDroplistLootingIII = new ArrayList<MobDrop>();

        sampleSize = 0;
        sampleSizeLootingI = 0;
        sampleSizeLootingII = 0;
        sampleSizeLootingIII = 0;
    }

    public void update(EnumEnchantKey key, List<EntityItem> drops) {

        List<MobDrop> dropList = null;

        if (key == EnumEnchantKey.NO_ENCHANT) {
            sampleSize++;
            dropList = mobDroplist;
        } else if (key == EnumEnchantKey.LOOTING_I) {
            sampleSizeLootingI++;
            dropList = mobDroplistLootingI;
        } else if (key == EnumEnchantKey.LOOTING_II) {
            sampleSizeLootingII++;
            dropList = mobDroplistLootingII;
        } else if (key == EnumEnchantKey.LOOTING_III) {
            sampleSizeLootingIII++;
            dropList = mobDroplistLootingIII;
        }

        // This should NEVER happen
        if (dropList == null)
            return;

        for (EntityItem entityItem : drops) {
            ItemStack itemStack = entityItem.getEntityItem();

            boolean found = false;
            for (MobDrop d : dropList) {
                if (d.itemStack.isItemEqualIgnoreDurability(itemStack)) {
                    d.update(itemStack.stackSize);
                    found = true;
                }
            }

            if (!found) {
                MobDrop d = new MobDrop(itemStack);
                d.update(itemStack.stackSize);
                dropList.add(d);
            }
        }
    }

    public void dump(ICommandSender sender, EnumEnchantKey key) {

        int size = 0;
        List<MobDrop> dropList = null;
        if (key == EnumEnchantKey.NO_ENCHANT) {
            dropList = mobDroplist;
            size = sampleSize;
        } else if (key == EnumEnchantKey.LOOTING_I) {
            dropList = mobDroplistLootingI;
            size = sampleSizeLootingI;
        } else if (key == EnumEnchantKey.LOOTING_II) {
            dropList = mobDroplistLootingII;
            size = sampleSizeLootingII;
        } else if (key == EnumEnchantKey.LOOTING_III) {
            dropList = mobDroplistLootingIII;
            size = sampleSizeLootingIII;
        }

        if (dropList == null) {
            sender.addChatMessage(new TextComponentString(String.format("Mob: %s/%s is empty", wootName, key)));
        } else {
            for (MobDrop d : dropList) {
               d.dump(sender, wootName, key, size);
            }
        }
    }

    private List<MobDrop> getList(EnumEnchantKey key) {

        List<MobDrop> dropList = null;
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

    private int getSize(EnumEnchantKey key) {

        int size = 0;
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

    public void getLoot(EnumEnchantKey key, int numMobs, List<ItemStack> loot) {

        List<MobDrop> dropList = getList(key);
        int size = getSize(key);

        if (dropList == null || size == 0)
            return;

        for (int mob = 0; mob < numMobs; mob++) {

            for (MobDrop drop : dropList) {
                ItemStack itemStack = drop.getLoot(size);
                if (itemStack != null)
                    loot.add(itemStack);
            }
        }
    }
}

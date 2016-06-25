package ipsis.woot.manager.loot;

import ipsis.Woot;
import ipsis.woot.manager.EnumEnchantKey;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.text.TextComponentString;

import java.util.ArrayList;
import java.util.List;

public class Drop {

    ItemStack itemStack;
    int count;
    List<DropData> dropList;

    public float getChance(int sampleSize) {

        if (sampleSize == 0)
            return 0.0F;

        return ((float)count / (float)sampleSize);
    }

    public Drop(ItemStack itemStack) {

        this.itemStack = ItemStack.copyItemStack(itemStack);
        count = 0;
        dropList = new ArrayList<DropData>();
    }

    public void update(int stackSize) {

        count++;
        boolean found = false;
        for (DropData dropData: dropList) {
            if (dropData.stackSize == stackSize) {
                dropData.incItemWeight();
                found = true;
            }
        }

        if (!found)
            dropList.add(new DropData(stackSize, 1));
    }

    public int getWeightedSize() {

        DropData dropData = WeightedRandom.getRandomItem(Woot.lootPool.RAND, dropList);
        if (dropData != null)
            return dropData.stackSize;

        return 1;
    }

    public void dump(ICommandSender sender, String wootName, EnumEnchantKey key, int count, int sampleSize) {

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Mob: %s/%s %s@%.2f%% [", wootName, key, itemStack.getDisplayName(), getChance(sampleSize) * 100.0F));
        for (DropData d : dropList)
            sb.append(String.format(" s:%d/w:%d ", d.stackSize, d.itemWeight));
        sb.append("]");

        sender.addChatMessage(new TextComponentString(sb.toString()));
    }

    public static class DropData extends WeightedRandom.Item {

        public final int stackSize;

        public DropData(int stackSize, int itemWeight) {

            super(itemWeight);
            this.stackSize = stackSize;
        }

        public void incItemWeight() {
            itemWeight++;
        }
    }
}



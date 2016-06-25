package ipsis.woot.manager.loot;

import ipsis.Woot;
import ipsis.woot.manager.EnumEnchantKey;
import ipsis.woot.oss.LogHelper;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;

import java.util.NavigableSet;
import java.util.TreeSet;

public class MobDrop {

    TreeSet<SizedDrop> dropTreeSet;
    SizedDrop drop;
    ItemStack itemStack;

    public MobDrop(ItemStack itemStack) {

        dropTreeSet = null;
        drop = null;
        this.itemStack = ItemStack.copyItemStack(itemStack);
    }

    void updateDrop() {
        drop.incCount();
    }

    void migrate() {

        dropTreeSet = new TreeSet<SizedDrop>();
        dropTreeSet.add(new SizedDrop(drop.count, drop.stackSize));
        drop = null;
    }

    void update(int stackSize) {

        if (dropTreeSet == null && drop == null) {
           drop = new SizedDrop(1, stackSize);
        } else if (dropTreeSet == null && drop != null && drop.stackSize == stackSize) {
            updateDrop();
        } else {
            if (dropTreeSet == null) {
                migrate();
            }

            boolean found = false;
            for (SizedDrop s : dropTreeSet) {
                if (s.getStackSize() == stackSize) {
                    s.incCount();
                    found = true;
                }
            }

            if (found == false)
                dropTreeSet.add(new SizedDrop(1, stackSize));
        }
    }

    public void dump(ICommandSender sender, String wootName, EnumEnchantKey key, int sampleSize) {

        if (drop == null && dropTreeSet == null) {
            sender.addChatMessage(new TextComponentString(String.format("Mob: %s/%s %s is empty", wootName, key, itemStack.getDisplayName())));
        } else if (drop != null) {
            sender.addChatMessage(new TextComponentString(String.format("Mob: %s/%s %s %s %.2f", wootName, key, itemStack.getDisplayName(), drop, drop.getProbability(sampleSize))));
        } else {
            for (SizedDrop s : dropTreeSet)
                sender.addChatMessage(new TextComponentString(String.format("Mob: %s/%s %s %s %.2f", wootName, key, itemStack.getDisplayName(), s, s.getProbability(sampleSize))));
        }
    }

    ItemStack getLoot(int sampleSize) {

        ItemStack retStack = null;
        float chance = Woot.lootManager.RAND.nextFloat();

        if (drop != null) {
            if (drop.isHit(sampleSize, chance)) {
                retStack = ItemStack.copyItemStack(itemStack);
                retStack.stackSize = drop.getStackSize();
            }

        } else {
            NavigableSet<SizedDrop> sortedSet = dropTreeSet.descendingSet();
            for (SizedDrop s : sortedSet) {
                LogHelper.info("Check: " + itemStack + " " + s.getStackSize() + " " + chance + "<=" + s.getProbability(sampleSize)/100.F);
                if (s.isHit(sampleSize, chance)) {
                    retStack = ItemStack.copyItemStack(itemStack);
                    retStack.stackSize = s.getStackSize();
                    break;
                }
            }
        }

        return retStack;
    }
}

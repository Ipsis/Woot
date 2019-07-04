package ipsis.woot.loot;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MobDrop {
    private ItemStack droppedItem = ItemStack.EMPTY;

    private float dropChance = 0.0F;
    private Map<Integer, Float> stackSizeChances = new HashMap<>();

    public MobDrop(@Nonnull ItemStack itemStack, float dropChance) {
        droppedItem = itemStack.copy();
        this.dropChance = dropChance;
    }

    private MobDrop() { }

    public void addSizeChance(int stackSize, float dropChance) {
        stackSizeChances.put(stackSize, dropChance);
    }
    public @Nonnull ItemStack getDroppedItem() { return droppedItem; }
    public float getDropChance() { return dropChance; }

    @Override
    public String toString() {
        String s = droppedItem.getTranslationKey() + "@" + dropChance;
        for (Integer i : stackSizeChances.keySet())
            s += " " + i + ":" + stackSizeChances.get(i);
        return s;
    }
}

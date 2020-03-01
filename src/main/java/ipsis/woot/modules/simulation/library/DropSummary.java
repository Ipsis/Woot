package ipsis.woot.modules.simulation.library;

import net.minecraft.item.ItemStack;

import java.util.Arrays;

public class DropSummary {


    // The item that drops
    private ItemStack itemStack;
    // Absolute chance to drop at a looting level
    private float[] chanceToDrop;

    public DropSummary(ItemStack itemStack, float loot0, float loot1, float loot2, float loot3) {
        this.itemStack = itemStack.copy();
        this.itemStack.setCount(1);
        chanceToDrop = new float[]{loot0, loot1, loot2, loot3};
    }

    @Override
    public String toString() {
        return "DropDetails{" +
                "itemStack=" + itemStack.getTranslationKey() +
                ", chanceToDrop=" + Arrays.toString(chanceToDrop) +
                '}';
    }

    public static DropSummary createFromMobDropData(MobDropData mobDropData) {
        DropSummary dropSummary = new DropSummary(mobDropData.getItemStack().copy(),
                mobDropData.getDropChance(0),
                mobDropData.getDropChance(1),
                mobDropData.getDropChance(2),
                mobDropData.getDropChance(3));
        return dropSummary;
    }


}

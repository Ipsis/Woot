package ipsis.woot.drops;

import ipsis.woot.util.FakeMobKey;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MobDropData {

    private FakeMobKey fakeMobKey;
    private int looting;
    private List<DropData> drops = new ArrayList<>();

    public MobDropData(FakeMobKey fakeMobKey, int looting) {
        this.fakeMobKey = fakeMobKey;
        this.looting = looting;
    }

    public List<DropData> getDrops() { return Collections.unmodifiableList(drops); }
    public FakeMobKey getFakeMobKey() { return fakeMobKey; }
    public int getLooting() { return looting; }
    public void add(DropData dropData) { drops.add(dropData); }

    public static class DropData {

        private ItemStack itemStack;
        private float chance;
        private HashMap<Integer, Float> sizeChances = new HashMap<>();

        public DropData(ItemStack itemStack) {
            this.itemStack = itemStack.copy();
            this.chance = 0.0F;
        }

        public void setChance(float chance) { this.chance = chance; }
        public void addSize(int stackSize, float chance) { sizeChances.put(stackSize, chance); }

        public ItemStack getItemStack() { return itemStack; }
        public float getChance() { return chance; }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(itemStack.getTranslationKey());
            sb.append("/" + chance + "% ");

            for (Integer stackSize : sizeChances.keySet())
                sb.append(stackSize + "-" + sizeChances.get(stackSize) + " ");

            return sb.toString();
        }
    }
}

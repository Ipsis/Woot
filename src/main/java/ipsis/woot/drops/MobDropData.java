package ipsis.woot.drops;

import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.MiscUtils;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MobDropData {

    private FakeMobKey fakeMobKey;
    private int looting;
    private List<DropData> drops = new ArrayList<>();

    private MobDropData() { }
    public MobDropData(FakeMobKey fakeMobKey, int looting) {

        this.looting = MiscUtils.clampLooting(looting);
        this.fakeMobKey = fakeMobKey;
    }

    public List<DropData> getDrops() {

        return Collections.unmodifiableList(drops);
    }

    public int getLooting() { return this.looting; }
    public FakeMobKey getFakeMobKey() { return this.fakeMobKey; }

    public void addDropData(DropData dropData) { drops.add(dropData); }

    public static class DropData {

        private ItemStack itemStack;
        private float dropChance;
        private HashMap<Integer, Float> sizeChances = new HashMap<>();

        private DropData() { }
        public DropData(ItemStack itemStack) {
            this.itemStack = itemStack.copy();
            this.dropChance = 0.0F;
        }

        public void setDropChance(float dropChance) { this.dropChance = dropChance; }
        public void addSizeDropChance(int stackSize, float dropChance) {
            sizeChances.put(stackSize, dropChance);
        }

        @Override
        public String toString() {
            return itemStack.getUnlocalizedName() + "/" + dropChance + "%";
        }
    }
}

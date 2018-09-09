package ipsis.woot.loot;

import ipsis.woot.util.FakeMobKey;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * What the mob can drop at a specific looting level
 */
public class MobDropData {

    public FakeMobKey fakeMobKey;
    public int looting;
    public List<DropEntry> drops = new ArrayList<>();


    private MobDropData() {}
    public MobDropData(FakeMobKey fakeMobKey, int looting) {

        this.fakeMobKey = fakeMobKey;
        this.looting = looting;
    }

    public void addDropEntry(DropEntry dropEntry) {

        drops.add(dropEntry);
    }

    @Override
    public String toString() {

        return fakeMobKey.toString() + "#" + looting;
    }

    public static class DropEntry {

        private ItemStack itemStack;
        private float chance;
        private Map<Integer, Float> chanceMap = new HashMap<>();

        private DropEntry() {}
        public DropEntry(ItemStack itemStack) {

            this.itemStack = itemStack.copy();
        }

        public void addStackChance(int stackSize, float chance) {

            chanceMap.put(stackSize, chance);
        }

        public void setDropChance(float chance) {

            this.chance = chance;
        }

        @Override
        public String toString() {

            StringBuilder sb = new StringBuilder(itemStack.getDisplayName() + " " + chance + "% ");
            for (Integer i : chanceMap.keySet())
                sb.append(i + "/" + chanceMap.get(i) + "% ");
            return sb.toString();
        }
    }
}

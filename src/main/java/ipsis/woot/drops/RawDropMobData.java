package ipsis.woot.drops;

import ipsis.woot.util.FakeMobKey;
import net.minecraft.util.math.MathHelper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class RawDropMobData {

    protected FakeMobKey fakeMobKey;

    /**
     * [looting] -> number of times this mob has dropped the item
     */
    protected int[] dropCounts = new int[4]; // one per looting possibility

    /**
     * [looting] -> [stacksize][count of times that this stacksize dropped]
     */
    protected HashMap<Integer, HashMap<Integer, Integer>> lootingData = new HashMap<>();

    public RawDropMobData(FakeMobKey fakeMobKey) {
        this.fakeMobKey = fakeMobKey;
        lootingData.put(0, new HashMap<>());
        lootingData.put(1, new HashMap<>());
        lootingData.put(2, new HashMap<>());
        lootingData.put(3, new HashMap<>());
    }

    public void updateDropCount(int looting) {
        dropCounts[looting]++;
    }

    public void update(int looting, int stackSize) {
        HashMap<Integer, Integer> data = lootingData.get(looting);
        int count = 0;
        if (data.keySet().contains(stackSize))
            count = data.get(stackSize);

        count++;
        data.put(stackSize, count);
    }

    public Set<Integer> getValidStackSizes(int looting) {
        return Collections.unmodifiableSet(lootingData.get(looting).keySet());
    }

    /**
     * Chances of dropping the item, regardless of stacksize
     */
    public float getDropChance(int looting, int sampleSize) {
        return (100.0F / (float)sampleSize) * (float)dropCounts[looting];
    }

    /**
     * Changes of dropping the item with a specific stacksize
     */
    public float getDropChance(int looting, int stackSize, int sampleSize) {
        float chance = 0.0F;
        HashMap<Integer, Integer> data = lootingData.get(looting);
        int count = 0;
        if (data.keySet().contains(stackSize)) {
            count = data.get(stackSize);
            count = MathHelper.clamp(count, 0, sampleSize);
            chance = (100.0F / (float)sampleSize) * (float)count;
        }
        return chance;
    }

}

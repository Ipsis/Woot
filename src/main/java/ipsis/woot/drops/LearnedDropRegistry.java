package ipsis.woot.drops;

import ipsis.woot.config.FactoryConfig;
import ipsis.woot.config.PolicyConfig;
import ipsis.woot.util.FakeMobKey;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Learned Drop Registry Structure
 *
 * List of RawDropData
 *     RawDropData represents a unique item
 *         Contains a list of RawDropMobData
 *             RawDropMobData represents a unique mob type that has dropped the item
 *                 Contains how many times the item has been dropped
 *                 Contains how many times the item of a specific stack size has been dropped
 *
 */

public class LearnedDropRegistry {

    // Map of the number of learning events for each mob
    private HashMap<FakeMobKey, Integer[]> sampleCounts = new HashMap<>();
    private void updateSampleCount(FakeMobKey fakeMobKey, int looting) {
        Integer[] s = sampleCounts.computeIfAbsent(fakeMobKey, k -> new Integer[]{0, 0, 0, 0});
        s[looting]++;
    }
    private int getSampleCount(FakeMobKey fakeMobKey, int looting) {
        Integer[] s = sampleCounts.computeIfAbsent(fakeMobKey, k -> new Integer[]{0, 0, 0, 0});
        return s[looting];
    }

    private List<RawDropData> drops = new ArrayList<>();
    private @Nonnull RawDropData getRawDropForLearning(@Nonnull ItemStack itemStack) {
        RawDropData drop = null;
        for (RawDropData d : drops) {
            if (DropRegistry.isEqualForLearning(itemStack, d.itemStack)) {
                drop = d;
                break;
            }
        }

        if (drop == null) {
            drop = new RawDropData(itemStack);
            drops.add(drop);
        }

        return drop;
    }

    public void init() {
        // @todo load from file
    }

    /**
     * Learning
     */
    public void learnSilent(@Nonnull FakeMobKey fakeMobKey, int looting, @Nonnull List<ItemStack> drops) {
        updateSampleCount(fakeMobKey, looting);
        learnSilent(fakeMobKey, looting, drops);
    }

    public void learn(@Nonnull FakeMobKey fakeMobKey, int looting, @Nonnull List<ItemStack> drops) {

        for (ItemStack itemStack : drops) {
            if (itemStack == null || itemStack.isEmpty())
                continue;

            if (!PolicyConfig.canLearn(itemStack))
                continue;

            RawDropData rawDropData = getRawDropForLearning(itemStack);
            rawDropData.update(fakeMobKey, looting, itemStack.getCount());
        }
    }

    public boolean isLearningComplete(@Nonnull FakeMobKey fakeMobKey, int looting) {
        return getSampleCount(fakeMobKey, looting) >= FactoryConfig.LEARN_MOB_COUNT.get();
    }

    /**
     * Get the drops for a specific mob
     */
    @Nonnull public MobDropData getMobDropData(@Nonnull FakeMobKey fakeMobKey, int looting) {
        int sampleSize = getSampleCount(fakeMobKey, looting);
        MobDropData mobDropData = new MobDropData(fakeMobKey, looting);

        for (RawDropData drop : drops) {
            for (RawDropMobData rawDropMobData : drop.mobData) {
                if (rawDropMobData.fakeMobKey.equals(fakeMobKey)) {
                    MobDropData.DropData dropData = new MobDropData.DropData(drop.itemStack);
                    dropData.setChance(rawDropMobData.getDropChance(looting, sampleSize));
                    for (Integer size : rawDropMobData.getValidStackSizes(looting))
                        dropData.addSize(size, rawDropMobData.getDropChance(looting, size, sampleSize));
                    mobDropData.add(dropData);
                }
            }
        }

        return mobDropData;
    }
}

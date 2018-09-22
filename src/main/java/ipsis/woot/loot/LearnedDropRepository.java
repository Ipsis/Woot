package ipsis.woot.loot;

import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.MiscUtils;
import ipsis.woot.util.StackUtils;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.*;

import static ipsis.woot.util.MiscUtils.clampLooting;

public class LearnedDropRepository implements ILootDropProvider, ILootLearning {

    private static final int SAMPLE_COUNT = 25;

    // Map of mob -> [no looting][looting 1][looting 2][looting 3]
    private static final HashMap<FakeMobKey, Integer[]> samples = new HashMap<>();

    // List of learned drops
    private List<RawDropData> learnedDrops = new ArrayList<>();

    private void updateSampleCount(FakeMobKey fakeMobKey, int looting) {

        Integer[] s = samples.computeIfAbsent(fakeMobKey, k -> new Integer[]{0, 0, 0, 0});
        s[looting]++;
    }

    private int getSampleCount(FakeMobKey fakeMobKey, int looting) {

        Integer[] s = samples.computeIfAbsent(fakeMobKey, k -> new Integer[]{0, 0, 0, 0});
        return s[looting];
    }

    /**
     * Get an existing entry or create a new one if it doesn't exist.
     */
    private @Nonnull
    RawDropData getRawDropForLearning(ItemStack itemStack) {

        RawDropData drop = null;
        for (RawDropData d : learnedDrops) {
            if (StackUtils.isEqualForLearning(d.itemStack, itemStack)) {
                drop = d;
                break;
            }
        }

        if (drop == null) {
            drop = new RawDropData(itemStack);
            learnedDrops.add(drop);
        }

        return drop;
    }


    // ILootDropProvider
    @Override
    public void init() {

        new LearnedDropJson().load(this);
    }

    @Override
    public void shutdown() {

        new LearnedDropJson().write(this);
    }

    // ILootLearning
    @Override
    public void learn(@Nonnull FakeMobKey fakeMobKey, int looting, @Nonnull List<ItemStack> drops) {

        looting = clampLooting(looting);
        updateSampleCount(fakeMobKey, looting);
        learnSilent(fakeMobKey, looting, drops);
    }

    @Override
    public void learnSilent(@Nonnull FakeMobKey fakeMobKey, int looting, @Nonnull List<ItemStack> drops) {

        looting = clampLooting(looting);

        for (ItemStack itemStack : drops) {

            // TODO check if we dont want to learn
            RawDropData rawDropData = getRawDropForLearning(itemStack);
            rawDropData.update(fakeMobKey, looting, itemStack.getCount());
        }
    }

    public boolean isFull(@Nonnull FakeMobKey fakeMobKey, int looting) {

        looting = clampLooting(looting);
        return getSampleCount(fakeMobKey, looting) >= SAMPLE_COUNT;
    }


    // save/load access
    public Map<FakeMobKey, Integer[]> getSampleCounts() {
        return Collections.unmodifiableMap(samples);
    }

    public List<RawDropData> getRawDropData() {
        return Collections.unmodifiableList(learnedDrops);
    }

    public void getStatus(@Nonnull List<String> status, String[] args) {

        status.add("LearnedDropRepository");
        status.add("Max Samples: " + SAMPLE_COUNT);

        for (FakeMobKey fakeMobKey : samples.keySet()) {
            Integer[] s = samples.get(fakeMobKey);
            status.add(fakeMobKey + " " + s[0] + "/" + s[1] + "/" + s[2] + "/" + s[3]);
        }

        for (RawDropData rawDropData : learnedDrops)
            status.add(rawDropData.toString());
    }

    @Nonnull
    @Override
    public MobDropData getDrops(@Nonnull FakeMobKey fakeMobKey, int looting) {

        looting = MiscUtils.clampLooting(looting);
        return getDropData(fakeMobKey, looting, getSampleCount(fakeMobKey, looting));
    }

    private MobDropData getDropData(FakeMobKey fakeMobKey, int looting, int sampleCount) {

        looting = MiscUtils.clampLooting(looting);

        MobDropData mobDropData = new MobDropData(fakeMobKey, looting);

        for (RawDropData rawDropData : learnedDrops) {
            for (RawDropMobData rawDropMobData : rawDropData.mobData) {
                if (rawDropMobData.fakeMobKey.equals(fakeMobKey)) {
                    MobDropData.DropData dropData = new MobDropData.DropData(rawDropData.itemStack);

                    dropData.setDropChance(rawDropMobData.getDropChance(looting, sampleCount));
                    for (Integer stackSize : rawDropMobData.getValidSizes(looting)) {
                        dropData.addSizeDropChance(stackSize, rawDropMobData.getSizeDropChance(looting, stackSize, sampleCount));
                    }
                }
            }
        }

        return mobDropData;
    }

    private class RawDropData {

        ItemStack itemStack;
        List<RawDropMobData> mobData = new ArrayList<>();

        private RawDropData() { }
        public RawDropData(ItemStack itemStack) { this.itemStack = itemStack.copy(); }

        private void update(FakeMobKey fakeMobKey, int looting, int stackSize) {

            RawDropMobData rawDropMobData = null;
            for (RawDropMobData curr: mobData) {
                if (curr.fakeMobKey.equals(fakeMobKey)) {
                    rawDropMobData = curr;
                    break;
                }
            }

            if (rawDropMobData == null) {
                rawDropMobData = new RawDropMobData(fakeMobKey);
                mobData.add(rawDropMobData);
            }


            rawDropMobData.updateDropCount(looting);
            rawDropMobData.update(looting, stackSize);
        }

    }

    private class RawDropMobData {

        private FakeMobKey fakeMobKey;
        private int[] dropCounts = new int[4];
        private HashMap<Integer, HashMap<Integer, Integer>> lootingData = new HashMap<>();

        private RawDropMobData() { }
        public RawDropMobData(FakeMobKey fakeMobKey) {
            this.fakeMobKey = fakeMobKey;
            lootingData.put(0, new HashMap<>());
            lootingData.put(1, new HashMap<>());
            lootingData.put(2, new HashMap<>());
            lootingData.put(3, new HashMap<>());
        }

        public void updateDropCount(int looting) {
            looting = MiscUtils.clampLooting(looting);
            dropCounts[looting]++;
        }

        public void update(int looting, int stackSize) {

            looting = MiscUtils.clampLooting(looting);
            HashMap<Integer, Integer> data = lootingData.get(looting);
            int count = 0;
            if (data.keySet().contains(stackSize))
                count = data.get(stackSize);

            count++;
            data.put(stackSize, count);
        }

        public Set<Integer> getValidSizes(int looting) {

            looting = MiscUtils.clampLooting(looting);
            return Collections.unmodifiableSet(lootingData.get(looting).keySet());
        }

        public float getDropChance(int looting, int sampleCount) {

            looting = MiscUtils.clampLooting(looting);
            return (100.0F / (float)sampleCount) * (float)dropCounts[looting];
        }

        public boolean hasDropped(int looting) {

            // TODO do the roll here ???
            return true;
        }

        public float getSizeDropChance(int looting, int stackSize, int sampleCount) {

            return 0.0F;
        }

    }
}
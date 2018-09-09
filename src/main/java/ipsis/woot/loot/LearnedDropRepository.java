package ipsis.woot.loot;

import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.LootUtils;
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

    private void incrementSampleCount(FakeMobKey fakeMobKey, int looting) {

        Integer[] s = samples.computeIfAbsent(fakeMobKey, k -> new Integer[]{0, 0, 0, 0});
        s[looting]++;
    }

    private int getSampleCount(FakeMobKey fakeMobKey, int looting) {

        Integer[] s = samples.computeIfAbsent(fakeMobKey, k -> new Integer[]{0, 0, 0, 0});
        return s[looting];
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
        incrementSampleCount(fakeMobKey, looting);
        learnSilent(fakeMobKey, looting, drops);
    }

    @Override
    public void learnSilent(@Nonnull FakeMobKey fakeMobKey, int looting, @Nonnull List<ItemStack> drops) {

        looting = clampLooting(looting);

        for (ItemStack itemStack : drops) {

            // TODO check if we dont want to learn

           RawDropData drop = null;
           for (RawDropData d : learnedDrops) {
               if (LootUtils.isIdenticalForLearning(d.getItemStack(), itemStack)) {
                   drop = d;
                   break;
               }
           }

           if (drop == null) {
               drop = new RawDropData(itemStack);
               learnedDrops.add(drop);
           }

           drop.incStackSizeForLooting(fakeMobKey, looting, itemStack.getCount());
        }
    }

    public boolean isFull(@Nonnull FakeMobKey fakeMobKey, int looting) {

        looting = clampLooting(looting);
        return getSampleCount(fakeMobKey, looting) >= SAMPLE_COUNT;
    }

    public @Nonnull MobDropData getDrops(@Nonnull FakeMobKey fakeMobKey, int looting) {

        MobDropData drops = new MobDropData(fakeMobKey, looting);

        looting = clampLooting(looting);

        for (RawDropData rawDropData : learnedDrops) {
            for (RawDropData.DropMob dropMob : rawDropData.getDropMobs()) {
                if (dropMob.getFakeMobKey().equals(fakeMobKey)) {

                    int sampleCount = getSampleCount(fakeMobKey, looting);
                    Map<Integer, Integer> lootingMap = dropMob.getLooting(looting);
                    if (lootingMap.keySet().size() > 0) {
                        MobDropData.DropEntry dropEntry = new MobDropData.DropEntry(rawDropData.getItemStack());
                        for (Integer size : lootingMap.keySet()) {

                            int dropCount = lootingMap.get(size);
                            float chance = ((100.0F / (float)sampleCount) * (float)dropCount);
                            dropEntry.addStackChance(size, chance);
                        }

                        float dropChance = ((100.0F / (float)sampleCount) * (float)dropMob.getDropCount(looting));
                        dropEntry.setDropChance(dropChance);
                        drops.addDropEntry(dropEntry);
                    }
                }
            }
        }

        return drops;
    }

    // save/load access
    public Map<FakeMobKey, Integer[]> getSampleCounts() {
        return Collections.unmodifiableMap(samples);
    }

    public List<RawDropData> getRawDropData() {
        return Collections.unmodifiableList(learnedDrops);
    }

    public void getStatus(@Nonnull List<String> status) {

        status.add("LearnedDropRepository");
        status.add("Max Samples: " + SAMPLE_COUNT);

        for (FakeMobKey fakeMobKey : samples.keySet()) {
            Integer[] s = samples.get(fakeMobKey);
            status.add(fakeMobKey + " " + s[0] + "/" + s[1] + "/" + s[2] + "/" + s[3]);
        }

        for (RawDropData rawDropData : learnedDrops)
            status.add(rawDropData.toString());
    }
}
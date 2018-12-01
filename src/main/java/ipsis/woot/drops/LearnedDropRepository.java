package ipsis.woot.drops;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ipsis.Woot;
import ipsis.woot.configuration.vanilla.GeneralConfig;
import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.helpers.JsonHelper;
import ipsis.woot.util.MiscUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;
import java.util.*;

public class LearnedDropRepository implements IDropProvider {

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
            if (DropManager.isEqualForLearning(d.itemStack, itemStack)) {
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


    public void init() {
        LearnedDropJson.load(this);
    }

    public void shutdown() {
        LearnedDropJson.save(this);
    }

    public void learn(@Nonnull FakeMobKey fakeMobKey, int looting, @Nonnull List<ItemStack> drops) {
        looting = MiscUtils.clampLooting(looting);
        updateSampleCount(fakeMobKey, looting);
        learnSilent(fakeMobKey, looting, drops);
    }

    public void learnSilent(@Nonnull FakeMobKey fakeMobKey, int looting, @Nonnull List<ItemStack> drops) {
        looting = MiscUtils.clampLooting(looting);

        for (ItemStack itemStack : drops) {
            if (Woot.POLICY_MANAGER.canLearnItem(itemStack)) {
                RawDropData rawDropData = getRawDropForLearning(itemStack);
                rawDropData.update(fakeMobKey, looting, itemStack.getCount());
            }
        }
    }

    public boolean isLearningComplete(@Nonnull FakeMobKey fakeMobKey, int looting) {
        looting = MiscUtils.clampLooting(looting);
        return getSampleCount(fakeMobKey, looting) >= GeneralConfig.LEARN_MOB_COUNT;
    }

    /**
     * IDropProvider
     */
    @Nonnull
    @Override
    public MobDropData getMobDropData(@Nonnull FakeMobKey fakeMobKey, int looting) {
        looting = MiscUtils.clampLooting(looting);

        int sampleCount = getSampleCount(fakeMobKey, looting);
        MobDropData mobDropData = new MobDropData(fakeMobKey, looting);
        for (RawDropData rawDropData : learnedDrops) {
            for (RawDropMobData rawDropMobData : rawDropData.mobData) {
                if (rawDropMobData.fakeMobKey.equals(fakeMobKey)) {
                    MobDropData.DropData dropData = new MobDropData.DropData(rawDropData.itemStack);

                    dropData.setDropChance(rawDropMobData.getDropChance(looting, sampleCount));
                    for (Integer stackSize : rawDropMobData.getValidSizes(looting)) {
                        dropData.addSizeDropChance(stackSize, rawDropMobData.getSizeDropChance(looting, stackSize, sampleCount));
                    }

                    mobDropData.addDropData(dropData);
                }
            }
        }

        return mobDropData;
    }

    public void getStatus(@Nonnull List<String> status, String[] args) {
        status.add(">>> LearnedDropRepository");
        status.add("Max Samples: " + GeneralConfig.LEARN_MOB_COUNT);

        for (FakeMobKey fakeMobKey : samples.keySet()) {
            Integer[] s = samples.get(fakeMobKey);
            status.add(fakeMobKey + " " + s[0] + "/" + s[1] + "/" + s[2] + "/" + s[3] + "\n");
        }

        for (RawDropData raw : learnedDrops)
            status.add(raw.toString());
        status.add("<<< LearnedDropRepository");
    }

    /**
     * Sava/load access only
     */
    public Map<FakeMobKey, Integer[]> getSampleCounts() {
        return Collections.unmodifiableMap(samples);
    }

    public List<RawDropData> getRawDropData() {
        return Collections.unmodifiableList(learnedDrops);
    }

    protected class RawDropData {

        private ItemStack itemStack;
        private List<RawDropMobData> mobData = new ArrayList<>();

        private RawDropData() { }
        public RawDropData(ItemStack itemStack) { this.itemStack = itemStack.copy(); this.itemStack.setCount(1);}

        public boolean hasModData() { return !mobData.isEmpty(); }

        @Override
        public String toString() {

            StringBuilder sb = new StringBuilder(itemStack.getDisplayName() + "\n");
            for (RawDropMobData dropMob : mobData)
                sb.append(dropMob.toString());

            return sb.toString();
        }

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

        public JsonObject toJsonObject() {

            JsonObject jsonObject = new JsonObject();
            {
                JsonObject itemStackObject = JsonHelper.toJsonObject(itemStack);
                jsonObject.add("drop", itemStackObject);

                JsonArray mobsArray = new JsonArray();
                for (RawDropMobData rawDropMobData : mobData)
                    mobsArray.add(rawDropMobData.toJsonObject());

                jsonObject.add("mobs", mobsArray);
            }

            return jsonObject;
        }
    }

    protected class RawDropMobData {

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

        public JsonObject toJsonObject() {

            JsonObject jsonObject = new JsonObject();
            {
                jsonObject.addProperty("mob", "bob");
                JsonArray array = new JsonArray();

                for (int i = 0; i < 4; i++) {

                    HashMap<Integer, Integer> map = lootingData.get(i);
                    if (map.isEmpty())
                        continue;

                    for (Integer s : map.keySet()) {
                        JsonObject jsonObject1 = new JsonObject();
                        jsonObject1.addProperty("looting", i);
                        jsonObject1.addProperty("stackSize", s);
                        jsonObject1.addProperty("dropCount", map.get(s));
                        array.add(jsonObject1);
                    }
                }
                jsonObject.add("sizes", array);
            }
            return jsonObject;
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

            looting = MiscUtils.clampLooting(looting);
            HashMap<Integer, Integer> data = lootingData.get(looting);
            int count = 0;
            if (data.keySet().contains(stackSize))
                count = data.get(stackSize);

            // Should never happen but ...
            count = MathHelper.clamp(count, 0, sampleCount);

            return (100.0F / (float)sampleCount) * (float)count;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(fakeMobKey.toString() + "\n");
            for (Integer looting : lootingData.keySet()) {
                sb.append("Looting " + looting);
                HashMap<Integer, Integer> data = lootingData.get(looting);
                for (Integer size : data.keySet())
                    sb.append(" " + size + "/" + data.get(size));
                sb.append("\n");
            }
            return sb.toString();
        }
    }
}

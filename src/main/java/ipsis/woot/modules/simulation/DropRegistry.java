package ipsis.woot.modules.simulation;

import com.google.gson.*;
import ipsis.woot.mod.ModFiles;
import ipsis.woot.policy.PolicyRegistry;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.helper.JsonHelper;
import ipsis.woot.util.helper.MathHelper;
import ipsis.woot.util.helper.SerializationHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JSONUtils;
import net.minecraftforge.common.crafting.CraftingHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class DropRegistry {

    public static final Logger LOGGER = LogManager.getLogger();

    static DropRegistry INSTANCE = new DropRegistry();
    public static DropRegistry get() { return INSTANCE; }

    HashMap<FakeMob, Mob> mobs = new HashMap<>();

    public Set<FakeMob> getKnownMobs() {
        return mobs.keySet();
    }

    public void primeAllMobLearning() {
        for (FakeMob fakeMob : mobs.keySet()) {
            LOGGER.debug("Try learning on " + fakeMob);
            tryLearning(fakeMob);
        }
    }

    public void tryLearning(@Nonnull FakeMob fakeMob) {
        LOGGER.debug("Trying to learn {}", fakeMob);
        for (int looting = 0; looting < 4; looting++) {
            FakeMobKey fakeMobKey = new FakeMobKey(fakeMob, looting);
            if (!isLearningFinished(fakeMobKey, SimulationConfiguration.SIMULATION_MOB_COUNT.get()))
                MobSimulator.get().learn(new FakeMobKey(fakeMob, looting));
        }
    }

    public void learnSilent(@Nonnull FakeMobKey fakeMobKey, @Nonnull List<ItemStack> drops) {
        LOGGER.debug("learnSilent {}", fakeMobKey);
        Mob mob = getOrCreateMob(fakeMobKey.getMob());
        drops.forEach((drop)-> mob.addSimulatedDrop(fakeMobKey.getLooting(), drop));
    }

    public void learn(@Nonnull FakeMobKey fakeMobKey, @Nonnull List<ItemStack> drops) {
        LOGGER.debug("learn {}", fakeMobKey);
        Mob mob = getOrCreateMob(fakeMobKey.getMob());
        mob.incrementSimulatedCount(fakeMobKey.getLooting());
        learnSilent(fakeMobKey, drops);
    }

    public void learnCustomDrop(@Nonnull FakeMobKey fakeMobKey, @Nonnull ItemStack droppedItem, float dropChance) {
        LOGGER.debug("learnCustomDrop {} {}", fakeMobKey, droppedItem.getItem());
        Mob mob = getOrCreateMob(fakeMobKey.getMob());
        mob.addCustomDrop(fakeMobKey.getLooting(), droppedItem, dropChance);
    }

    public void learnCustomDrop(@Nonnull FakeMobKey fakeMobKey, @Nonnull ItemStack droppedItem, int count, float dropChance) {
        LOGGER.debug("learnCustomDrop {} {} {}", fakeMobKey, droppedItem.getItem(), count);
        droppedItem.setCount(count);
        learnCustomDrop(fakeMobKey, droppedItem, dropChance);
    }

    public void learnCustomDropStackSize(@Nonnull FakeMobKey fakeMobKey, @Nonnull ItemStack droppedItem, int[] stackSize, float[] dropChance) {
        LOGGER.debug("learnCustomDropStackSize {} {} {} {}", fakeMobKey, droppedItem, stackSize, dropChance);
        Mob mob = getOrCreateMob(fakeMobKey.getMob());

        float full = 0.0F;
        for (float f : dropChance)
            full += f;
        if (full != 100.0F)
            return;

        for (int i = 0; i < stackSize.length; i++) {
            ItemStack itemStack = droppedItem.copy();
            itemStack.setCount(stackSize[i]);
            mob.addCustomDropStackSize(fakeMobKey.getLooting(), itemStack, dropChance[i]);
        }
    }

    public void learnCustomDropStackSize(@Nonnull FakeMobKey fakeMobKey, @Nonnull ItemStack droppedItem, int stackSize, float dropChance) {
        LOGGER.debug("learnCustomDropStackSize {} {} {} {}", fakeMobKey, droppedItem, stackSize, dropChance);
        Mob mob = getOrCreateMob(fakeMobKey.getMob());
        ItemStack itemStack = droppedItem.copy();
        itemStack.setCount(stackSize);
        mob.addCustomDropStackSize(fakeMobKey.getLooting(), itemStack, dropChance);
    }

    public List<Integer> getLearningStatus(@Nonnull FakeMob fakeMob) {
        List<Integer> status = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) status.add(i, 0);

        Mob mob = mobs.get(fakeMob);
        if (mob != null) {
            for (int i = 0; i < 4; i++)
                status.set(i, mob.getSimulatedDropCount(i));
        }

        return status;
    }

    public boolean isLearningFinished(@Nonnull FakeMobKey fakeMobKey, int learningCap) {
        Mob mob = mobs.get(fakeMobKey.getMob());
        if (mob == null)
            return false;

        if (mob.getSimulatedDropCount(fakeMobKey.getLooting()) < learningCap)
            return false;

        return true;
    }

    public @Nonnull List<MobDrop> getMobDrops(@Nonnull FakeMobKey fakeMobKey) {
        List<MobDrop> drops = new ArrayList<>();
        Mob mob = mobs.get(fakeMobKey.getMob());
        if (mob != null)
            mob.drops.forEach((drop) -> drops.add(drop.getMobDrop(fakeMobKey.getLooting(), mob.getSimulatedDropCount(fakeMobKey.getLooting()))));
        return drops;
    }

    private @Nonnull Mob getOrCreateMob(@Nonnull FakeMob fakeMob) {
        Mob mob = null;
        if (!mobs.containsKey(fakeMob)) {
            mob = new Mob(fakeMob);
            mobs.put(fakeMob, mob);
        }
        return mobs.get(fakeMob);
    }

    public boolean isEqualForLearning(ItemStack itemStackA, ItemStack itemStackB) {

        boolean isEqual = ItemStack.areItemsEqualIgnoreDurability(itemStackA, itemStackB);
        return isEqual;
    }

    /**
     * Collection of mob drop information covering both custom and learned information
     */
    class Mob {

        FakeMob fakeMob;
        static final int MAX_LOOTING = 4;

        // number of learn events for this mob, dropping nothing is still an event
        int[] simulatedDropEventCount = new int[MAX_LOOTING];
        List<Drop> drops = new ArrayList<>();

        private Mob() {}
        public Mob(FakeMob fakeMob) {
            this.fakeMob = fakeMob;
            for (int i = 0; i < MAX_LOOTING; i++)
                simulatedDropEventCount[i] = 0;
        }

        public void incrementSimulatedCount(int looting) {
            simulatedDropEventCount[looting]++;
        }

        public int getSimulatedDropCount(int looting) { return simulatedDropEventCount[looting]; }

        private @Nullable Drop getOrCreateDrop(@Nonnull ItemStack itemStack) {
            if (itemStack.isEmpty())
                return null;

            Drop drop = null;
            Iterator<Drop> iter = drops.iterator();
            while (iter.hasNext() && drop == null) {
                Drop curr = iter.next();
                if (isEqualForLearning(itemStack, curr.droppedItem))
                    drop = curr;
            }

            if (drop == null)
                drop = new Drop(itemStack);

            return drop;
        }

        private @Nullable Drop getDrop(@Nonnull ItemStack itemStack, boolean createOnFail) {
            if (itemStack.isEmpty())
                return null;

            Drop drop = null;
            Iterator<Drop> iter = drops.iterator();
            while (iter.hasNext() && drop == null) {
                Drop curr = iter.next();
                if (isEqualForLearning(itemStack, curr.droppedItem))
                    drop = curr;
            }

            if (drop == null && createOnFail)
                drop = new Drop(itemStack);

            return drop;
        }

        public void addSimulatedDrop(int looting, @Nonnull ItemStack itemStack) {
            LOGGER.debug("addSimulatedDrop l:{} {}", looting, itemStack);

            Drop drop = getDrop(itemStack, true);
            if (drop == null)
                return;

            if (!drops.contains(drop))
                drops.add(drop);

            drop.incrementSimulatedDropCount(looting);
            Integer count = drop.simulatedStackSizes.get(looting).getOrDefault(itemStack.getCount(), 0);
            LOGGER.debug("addSimulatedDrop stacksize:{} count:{}", itemStack.getCount(), count + 1);
            drop.simulatedStackSizes.get(looting).put(itemStack.getCount(), count + 1);
        }

        public void addCustomDrop(int looting, @Nonnull ItemStack itemStack, float dropChance) {

            Drop drop = getDrop(itemStack, true);
            if (drop == null)
                return;

            if (!drops.contains(drop))
                drops.add(drop);

            drop.customChanceToDrop[looting] = dropChance;
        }

        public void addCustomDropStackSize(int looting, @Nonnull ItemStack itemStack, float dropChance) {

            Drop drop = getDrop(itemStack, true);
            if (drop == null)
                return;

            if (!drops.contains(drop))
                drops.add(drop);

            drop.customStackSizes.get(looting).put(itemStack.getCount(), dropChance);
        }

        public void loadDrop(ItemStack itemStack, int count0, int count1, int count2, int count3) {
            Drop drop = getOrCreateDrop(itemStack);
            if (drop == null)
                return;

            if (!drops.contains(drop))
                drops.add(drop);
            drop.simulatedDropCount[0] = count0;
            drop.simulatedDropCount[1] = count1;
            drop.simulatedDropCount[2] = count2;
            drop.simulatedDropCount[3] = count3;
        }

        public void loadStackSize(ItemStack itemStack, int looting, int size, int count) {

            looting = MathHelper.clampLooting(looting);
            Drop drop = getDrop(itemStack, false);
            if (drop == null)
                return;

            drop.simulatedStackSizes.get(looting).put(size, count);
        }

        class Drop {
            ItemStack droppedItem;

            // number of times the item has dropped
            int[] simulatedDropCount = new int[MAX_LOOTING];

            // number of times a specific stack size has dropped
            List<HashMap<Integer,Integer>> simulatedStackSizes = new ArrayList<>(MAX_LOOTING);

            // chance to drop the item for each looting level
            float[] customChanceToDrop = new float[MAX_LOOTING];

            // chance to drop a specific stack size
            List<HashMap<Integer,Float>> customStackSizes = new ArrayList<>(MAX_LOOTING);

            public void incrementSimulatedDropCount(int looting) { simulatedDropCount[looting]++; }

            public Drop() {}
            public Drop(ItemStack itemStack) {
                this.droppedItem = itemStack.copy();
                for (int i = 0; i < MAX_LOOTING; i++) {
                    simulatedDropCount[i] = 0;
                    simulatedStackSizes.add(i, new HashMap<>());
                    customStackSizes.add(i, new HashMap<>());
                }
            }

            public @Nonnull MobDrop getMobDrop(int looting, int sampleSize) {
                float dropChance = 0.0F;
                if (sampleSize == 0)
                    dropChance = -1.0F; // means that we haven't simulated anything yet
                else
                    dropChance = (100.0F / (float) sampleSize) * simulatedDropCount[looting];
                MobDrop mobDrop = new MobDrop(droppedItem, dropChance);
                for (Integer stackSize : simulatedStackSizes.get(looting).keySet())
                    mobDrop.addSizeChance(stackSize, (100.0F / (float)simulatedDropCount[looting]) * simulatedStackSizes.get(looting).get(stackSize));

                return mobDrop;
            }
        }
    }

    /**
     * Save/load
     */
    private static final String VERSION_TAG = "version";
    private static final String MOBS_TAG = "mobs";
    private static final String SIMULATED_MOB_TAG = "mob";
    private static final String SIMULATED_COUNT_TAG = "simMobCount";
    private static final String DROPS_TAG = "drops";
    private static final String DROPPED_STACK_TAG = "drop";
    private static final String SIMULATED_DROP_COUNT_TAG = "simDropCount";
    private static final String[] LOOT_COUNT_TAGS = { "loot0", "loot1", "loot2", "loot3" };

    public void fromJson() {

        LOGGER.debug("Loading drop registry from " + ModFiles.INSTANCE.getLootFile());
        File dropFile = ModFiles.INSTANCE.getLootFile();
        Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        try {
            JsonObject jsonObject = JSONUtils.fromJson(GSON, new FileReader(dropFile), JsonObject.class);
            parseConfig(jsonObject);
        } catch (Exception e) {
            LOGGER.error("Could not load loot file " + dropFile.getAbsolutePath());
            e.printStackTrace();
        }
    }

    public void parseConfig(JsonObject jsonObject) {
        if (jsonObject == null || jsonObject.isJsonNull())
            throw new JsonSyntaxException("JsonObject cannot be null");

        int version = JSONUtils.getInt(jsonObject, VERSION_TAG);
        if (version != 1)
            throw new JsonSyntaxException("Loot file version missing or invalid");

        // Mobs
        for (JsonElement e : JSONUtils.getJsonArray(jsonObject, MOBS_TAG)) {
            if (e == null || !e.isJsonObject())
                throw new JsonSyntaxException("Mob must be an object");

            JsonObject o1 = (JsonObject) e;
            String mob = JSONUtils.getString(o1, SIMULATED_MOB_TAG);
            FakeMob fakeMob = new FakeMob(mob);
            if (!fakeMob.isValid()) {
                LOGGER.info("Invalid mob {}", mob);
                continue;
            }

            JsonArray simulatedCountArray = JSONUtils.getJsonArray(o1, SIMULATED_COUNT_TAG);
            if (simulatedCountArray.size() != Mob.MAX_LOOTING)
                throw new JsonSyntaxException("Simulated count array must be of size " + Mob.MAX_LOOTING);

            int sim0 = simulatedCountArray.get(0).getAsInt();
            int sim1 = simulatedCountArray.get(1).getAsInt();
            int sim2 = simulatedCountArray.get(2).getAsInt();
            int sim3 = simulatedCountArray.get(3).getAsInt();

            LOGGER.info("mob:{}", fakeMob);
            LOGGER.info("sim0:{} sim1:{} sim2:{} sim3:{}", sim0, sim1, sim2, sim3);

            Mob simulatedMob = new Mob(fakeMob);
            simulatedMob.simulatedDropEventCount[0] = sim0;
            simulatedMob.simulatedDropEventCount[1] = sim1;
            simulatedMob.simulatedDropEventCount[2] = sim2;
            simulatedMob.simulatedDropEventCount[3] = sim3;

            //Drops
            for (JsonElement e1 : JSONUtils.getJsonArray(o1, DROPS_TAG)) {
                if (e == null || !e.isJsonObject())
                    throw new JsonSyntaxException("Drop must be an object");

                JsonObject o2 = (JsonObject) e1;
                ItemStack itemStack;
                try {
                    itemStack = CraftingHelper.getItemStack(o2.getAsJsonObject(DROPPED_STACK_TAG), false);
                } catch (JsonSyntaxException e2) {
                    continue;
                }
                LOGGER.info("item:{}", itemStack.getItem());

                JsonArray simulatedDropCountArray = JSONUtils.getJsonArray(o2, SIMULATED_DROP_COUNT_TAG);
                if (simulatedDropCountArray.size() != Mob.MAX_LOOTING)
                    throw new JsonSyntaxException("Simulated drop count array must be of size " + Mob.MAX_LOOTING);

                int count0 = simulatedDropCountArray.get(0).getAsInt();
                int count1 = simulatedDropCountArray.get(1).getAsInt();
                int count2 = simulatedDropCountArray.get(2).getAsInt();
                int count3 = simulatedDropCountArray.get(3).getAsInt();

                LOGGER.info("count0:{} count1:{} count2{} count3{}", count0, count1, count2, count3);

                simulatedMob.loadDrop(itemStack, count0, count1, count2, count3);

                for (int i = 0; i < Mob.MAX_LOOTING; i++) {
                    JsonArray lootSizeArray = JSONUtils.getJsonArray(o2, LOOT_COUNT_TAGS[i]);
                    if (lootSizeArray.size() % 2 != 0)
                        throw new JsonSyntaxException("Loot stack size array must be of event size");

                    for (int j = 0; j < lootSizeArray.size() / 2; j += 2) {
                        int size = lootSizeArray.get(j).getAsInt();
                        int count = lootSizeArray.get(j + 1).getAsInt();
                        LOGGER.info("looting:{} size:{} count:{}", i, size, count);
                        simulatedMob.loadStackSize(itemStack, i, size, count);
                    }
                }
            }
            mobs.put(fakeMob, simulatedMob);
        }
    }

    public JsonObject toJson() {

        JsonObject json = new JsonObject();
        json.addProperty(VERSION_TAG, 1);

        JsonArray mobsArray = new JsonArray();
        for (Map.Entry<FakeMob, Mob> entry : mobs.entrySet()) {
            FakeMob fakeMob = entry.getKey();
            Mob mob = entry.getValue();

            if (!fakeMob.isValid())
                continue;

            if (!PolicyRegistry.get().canSimulate(fakeMob.getResourceLocation()))
                continue;

            JsonObject mobObject = new JsonObject();
            {
                mobObject.addProperty(SIMULATED_MOB_TAG, fakeMob.toString());
                JsonArray simulatedCountArray = new JsonArray();
                for (int i = 0; i < Mob.MAX_LOOTING; i++)
                    simulatedCountArray.add(mob.simulatedDropEventCount[i]);
                mobObject.add(SIMULATED_COUNT_TAG, simulatedCountArray);

                JsonArray dropsArray = new JsonArray();
                {
                    for (Mob.Drop drop : mob.drops) {
                        if (drop.droppedItem.isEmpty())
                            continue;

                        JsonObject dropObject = new JsonObject();
                        JsonObject itemObject = JsonHelper.toJsonObject(drop.droppedItem);
                        dropObject.add(DROPPED_STACK_TAG, itemObject);

                        JsonArray simulatedDropCountArray = new JsonArray();
                        for (int i = 0; i < Mob.MAX_LOOTING; i++)
                            simulatedDropCountArray.add(drop.simulatedDropCount[i]);

                        dropObject.add(SIMULATED_DROP_COUNT_TAG, simulatedDropCountArray);

                        /**
                         * Each entry is a pair - stacksize, dropcount
                         */
                        for (int i = 0; i < Mob.MAX_LOOTING; i++) {
                            JsonArray lootSizeArray = new JsonArray();
                            for (Map.Entry<Integer, Integer> entry1 : drop.simulatedStackSizes.get(i).entrySet()) {
                                lootSizeArray.add(entry1.getKey());
                                lootSizeArray.add(entry1.getValue());
                            }
                            dropObject.add(LOOT_COUNT_TAGS[i], lootSizeArray);
                        }
                        dropsArray.add(dropObject);
                    }
                }
                mobObject.add(DROPS_TAG, dropsArray);
            }
            mobsArray.add(mobObject);
        }
        json.add(MOBS_TAG, mobsArray);

        File dropFile = ModFiles.INSTANCE.getLootFile();
        LOGGER.debug("Saving drop registry to " + dropFile);
        Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        SerializationHelper.writeJsonFile(dropFile, GSON.toJson(json));
        return json;
    }
}

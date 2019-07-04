package ipsis.woot.loot;

import ipsis.woot.Woot;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.FakeMobKey;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class DropRegistry {

    static DropRegistry INSTANCE = new DropRegistry();
    public static DropRegistry get() { return INSTANCE; }

    HashMap<FakeMob, Mob> mobs = new HashMap<>();

    public void learnSilent(@Nonnull FakeMobKey fakeMobKey, @Nonnull List<ItemStack> drops) {
        Mob mob = getOrCreateMob(fakeMobKey.getMob());
        drops.forEach((drop)-> mob.addSimulatedDrop(fakeMobKey.getLooting(), drop));
    }

    public void learn(@Nonnull FakeMobKey fakeMobKey, @Nonnull List<ItemStack> drops) {
        Mob mob = getOrCreateMob(fakeMobKey.getMob());
        mob.incrementSimulatedCount(fakeMobKey.getLooting());
        learnSilent(fakeMobKey, drops);
    }

    public void learnCustomDrop(@Nonnull FakeMobKey fakeMobKey, @Nonnull ItemStack droppedItem, float dropChance) {
        Mob mob = getOrCreateMob(fakeMobKey.getMob());
        mob.addCustomDrop(fakeMobKey.getLooting(), droppedItem, dropChance);
    }

    public void learnCustomDropStackSize(@Nonnull FakeMobKey fakeMobKey, @Nonnull ItemStack droppedItem, int stackSize, float dropChance) {
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
        if (mob == null || mob.getSimulatedDropCount(fakeMobKey.getLooting()) < learningCap)
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

    private boolean isEqualForLearning(ItemStack itemStackA, ItemStack itemStackB) {

        boolean isEqual = ItemStack.areItemsEqualIgnoreDurability(itemStackA, itemStackB);
        Woot.LOGGER.info("isEqualForLearning {} {} {}", isEqual, itemStackA, itemStackB);
        return isEqual;
    }

    /**
     * Collection of mob drop information covering both custom and learned information
     */
    class Mob {

        FakeMob fakeMob;
        private static final int MAX_LOOTING = 4;

        // number of learn events for this mob, dropping nothing is still an event
        int[] simulatedDropEventCount = new int[MAX_LOOTING];
        List<Drop> drops = new ArrayList<>();

        private Mob() {}
        public Mob(FakeMob fakeMob) {
            this.fakeMob = fakeMob;
        }

        public void incrementSimulatedCount(int looting) { simulatedDropEventCount[looting]++; }

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

        public void addSimulatedDrop(int looting, @Nonnull ItemStack itemStack) {
            Woot.LOGGER.info("addSimulatedDrop l:{} {}", looting, itemStack);

            Drop drop = getOrCreateDrop(itemStack);
            if (drop == null)
                return;

            if (!drops.contains(drop))
                drops.add(drop);

            drop.incrementSimulatedDropCount(looting);
            Integer count = drop.simulatedStackSizes.get(looting).getOrDefault(itemStack.getCount(), 0);
            Woot.LOGGER.info("addSimulatedDrop stacksize:{} count:{}", itemStack.getCount(), count + 1);
            drop.simulatedStackSizes.get(looting).put(itemStack.getCount(), count + 1);
        }

        public void addCustomDrop(int looting, @Nonnull ItemStack itemStack, float dropChance) {

            Drop drop = getOrCreateDrop(itemStack);
            if (drop == null)
                return;

            if (!drops.contains(drop))
                drops.add(drop);

            drop.customChanceToDrop[looting] = dropChance;
        }

        public void addCustomDropStackSize(int looting, @Nonnull ItemStack itemStack, float dropChance) {

            Drop drop = getOrCreateDrop(itemStack);
            if (drop == null)
                return;

            if (!drops.contains(drop))
                drops.add(drop);

            drop.customStackSizes.get(looting).put(itemStack.getCount(), dropChance);
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
                    simulatedStackSizes.add(i, new HashMap<>());
                    customStackSizes.add(i, new HashMap<>());
                }
            }

            public @Nonnull MobDrop getMobDrop(int looting, int sampleSize) {
                // TODO add the custom drops
                float dropChance = (100.0F / (float)sampleSize) * simulatedDropCount[looting];
                MobDrop mobDrop = new MobDrop(droppedItem, dropChance);
                for (Integer stackSize : simulatedStackSizes.get(looting).keySet())
                    mobDrop.addSizeChance(stackSize, (100.0F / (float)simulatedDropCount[looting]) * simulatedStackSizes.get(looting).get(stackSize));

                return mobDrop;
            }
        }
    }

}

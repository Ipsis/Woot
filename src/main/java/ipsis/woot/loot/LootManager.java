package ipsis.woot.loot;

import ipsis.Woot;
import ipsis.woot.util.Debug;
import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.MiscUtils;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * This manager stores the drops that each mob can drop.
 * It is created from in-game learning and loaded on boot.
 * It does NOT responsible for cleaning up the drops - ie flattening
 */

public class LootManager {

    private static ILootDropProvider learnedDropsProvider = new LearnedDropRepository();
    private static ILootDropProvider customDropsProvider = new CustomDropRepository();

    public static void init() {

        learnedDropsProvider.init();
        customDropsProvider.init();
    }

    public static void shutdown() {

        learnedDropsProvider.shutdown();
        customDropsProvider.shutdown();
    }

    public static @Nonnull
    MobDropData getDrops(@Nonnull FakeMobKey fakeMobKey, int looting) {

        looting = MiscUtils.clampLooting(looting);

        MobDropData mobDropData = new MobDropData(fakeMobKey, looting);

        MobDropData customDrops = customDropsProvider.getDrops(fakeMobKey, looting);
        MobDropData learnedDrops = learnedDropsProvider.getDrops(fakeMobKey, looting);

        for (MobDropData.DropData dropData : learnedDrops.getDrops())
            mobDropData.addDropData(dropData);
        return mobDropData;
    }

    public static void learn(@Nonnull FakeMobKey fakeMobKey, int looting, @Nonnull List<ItemStack> drops) {

        if (Woot.debugging.isEnabled(Debug.Group.LEARN))
            Woot.debugging.trace(Debug.Group.LEARN, "LootManager:learn %s/%d/%s",fakeMobKey, looting, drops);

        ((ILootLearning)learnedDropsProvider).learn(fakeMobKey, looting, drops);
    }

    public static void learnSilent(@Nonnull FakeMobKey fakeMobKey, int looting, @Nonnull List<ItemStack> drops) {

        if (Woot.debugging.isEnabled(Debug.Group.LEARN))
            Woot.debugging.trace(Debug.Group.LEARN, "LootManager:learnSilent %s/%d/%s",fakeMobKey, looting, drops);

        ((ILootLearning)learnedDropsProvider).learnSilent(fakeMobKey, looting, drops);
    }

    public static boolean isFull(@Nonnull FakeMobKey fakeMobKey, int looting) {

        return learnedDropsProvider.isFull(fakeMobKey, looting);
    }

    public static void getStatus(@Nonnull List<String> status, String[] args) {

        status.add("LootManager");
        learnedDropsProvider.getStatus(status, args);
    }
}
